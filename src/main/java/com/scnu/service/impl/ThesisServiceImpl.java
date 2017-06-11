package com.scnu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.dao.ThesisMapper;
import com.scnu.dao.StuTheMapper;
import com.scnu.dao.StudentMapper;
import com.scnu.dao.cache.RedisDao;
import com.scnu.dto.*;
import com.scnu.entity.Thesis;
import com.scnu.entity.StuThe;
import com.scnu.entity.Student;
import com.scnu.enums.StateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.CourseException;
import com.scnu.exception.RepeatException;
import com.scnu.service.ThesisService;
import com.scnu.utils.JsonUtil;
import com.scnu.utils.SecureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by ldb on 2017/6/1.
 */
@Service
public class ThesisServiceImpl implements ThesisService {

    @Autowired
    private ThesisMapper thesisMapper;

    @Autowired
    private StuTheMapper stuTheMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisDao redisDao;

    @Value("${REDIS_PRACTICE_KEY}")
    private String REDIS_PRACTICE_KEY;


    @Override
    public PageResult<Thesis> listThesis(PageBean pageBean) {
        PageResult<Thesis> result=new PageResult<>();
        //PageHelper封装分页逻辑
        PageHelper.startPage(pageBean.getPage(),pageBean.getPageSize());
        //获取分页后列表
        List<Thesis> thesisList = thesisMapper.selectAll();

        // 取分页信息
        PageInfo<Thesis> pageInfo = new PageInfo<>(thesisList);
        long total = pageInfo.getTotal(); //获取总记录数
        //填充值
        result.setRows(thesisList);
        result.setTotal(total);
        return result;
    }

    @Override
    public Thesis getThesis(int id) {
        return thesisMapper.selectByPrimaryKey(id);
    }

    @Override
    public Exposer exportUrl(int id) {
        //添加Redis缓存
        Thesis thesis = null;
        //添加原则:不影响业务逻辑，用try catch捕获异常
        try{
            thesis= JsonUtil.jsonToPojo(redisDao.get(REDIS_PRACTICE_KEY+":"+id),Thesis.class);
        }catch (Exception e){
        }
        if(thesis==null){
            //如果缓存中为空，则从数据库中查询
            thesis = thesisMapper.selectByPrimaryKey(id);
            //查不到该记录
            if (thesis == null) {
                return new Exposer(false, id);
            }else{
                //将数据序列化存进缓存中
                //添加原则:不影响业务逻辑，用try catch捕获异常
                try{
                    redisDao.set(REDIS_PRACTICE_KEY+":"+id,JsonUtil.objectToJson(thesis));
                }catch (Exception e){
                }
            }
        }

        Date startTime = thesis.getStartTime();
        Date endTime = thesis.getEndTime();

        Date now = new Date();
        //抢课未开启或者抢课已结束
        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
            return new Exposer(false, id, now.getTime(), startTime.getTime(), endTime.getTime());
        }

        //抢课开启，返回秒杀商品的id、用给接口加密的md5
        String md5 = SecureUtil.getMD5(id);
        return new Exposer(true, md5, id);
    }



    @Transactional//开启事务
    @Override
    public Execution executeThesis(int id, int studentId, String md5, String studentMD5) throws CloseException, RepeatException, CourseException {
        if (md5 == null || !md5.equals(SecureUtil.getMD5(id))||studentMD5==null) {
            throw new CourseException("thesis data rewrite");//数据被重写了
        }
        //判断是否token是否正确，根据studentId获取student，生成studentMD5，与页面传来的进行对比
        Student student = studentMapper.selectByPrimaryKey(studentId);
        if(!studentMD5.equals(SecureUtil.getMD5(student))){
            throw new CourseException("thesis data rewrite");//数据被重写了
        }
        try {
            //判断是否重复选课
            StuThe example=new StuThe(studentId);
            List<StuThe> list = stuTheMapper.select(example);
            //如果已经选课，则抛出异常
            if(list!=null&&list.size()>0){
                throw new RepeatException("thesis repeat");
            }
            //插入抢课信息
            int resultNum = stuTheMapper.insertStuThe(id, studentId);
            if (resultNum <= 0) {
                throw new RepeatException("thesis repeat");
            } else {
                //减库存
                Date nowTime = new Date();
                int updateCount = thesisMapper.reduceNumber(id, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束 rollback
                    throw new CloseException("thesis is closed");
                } else {
                    //秒杀成功,返回成功秒杀的信息 commit
                    return new Execution(id, StateEnum.SUCCESS);
                }
            }
        } catch (CloseException e1) {
            throw e1;
        } catch (RepeatException e2) {
            throw e2;
        } catch (Exception e) {
            //所有编译期异常转化为运行期异常
            e.printStackTrace();
            throw new CourseException("inner error :" + e.getMessage());
        }
    }


    @Override
    public Result addThesis(Thesis thesis) {
        thesis.setCreateTime(new Date());
        int result = thesisMapper.insert(thesis);
        if(result>0){
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }

    @Transactional//开启事务
    @Override
    public Result updateThesis(Thesis thesis) throws CourseException{
        //添加原则:不影响业务逻辑，用try catch捕获异常
        int result = thesisMapper.updateByPrimaryKey(thesis);
        if(result>0){
            try{
                //重新设置缓存
                redisDao.set(REDIS_PRACTICE_KEY+":"+thesis.getId(), JsonUtil.objectToJson(thesis));
            }catch (Exception e){
                throw new CourseException("更新缓存出错");
            }
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }

    @Transactional//开启事务
    @Override
    public Result deleteThesis(Integer id) throws CourseException{
        //查询是否有学生选课
        StuThe stuThe=new StuThe();
        stuThe.setThesisId(id);
        List<StuThe> list = stuTheMapper.select(stuThe);
        if(list.size()>0){
            return Result.isNotOK("该论文有学生选课，不能删除");
        }
        int result = thesisMapper.deleteByPrimaryKey(id);
        if(result >0){
            //删除缓存
            try {
                redisDao.del(REDIS_PRACTICE_KEY+":"+id);
            }catch (Exception e){
                throw new CourseException("更新缓存出错");
            }
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }

    @Override
    public List<Thesis> listThesisByStudentId(Integer studentId) {
        //根据学生id查询获取所选论文的Id
        List<Thesis> result=thesisMapper.listThesisByStudentId(studentId);
        return result;
    }

    @Transactional//开启事务
    @Override
    public Execution rollBackThesis(int id, int studentId, String studentMD5) throws CloseException, CourseException {
        if (studentMD5==null) {
            throw new CourseException("no login");//未登录
        }
        //判断token是否正确，根据studentId获取student，生成studentMD5，与页面传来的进行对比
        Student student = studentMapper.selectByPrimaryKey(studentId);
        if(!studentMD5.equals(SecureUtil.getMD5(student))){
            throw new CourseException("thesis data rewrite");//数据被重写了
        }
        try {
            //删除抢课信息
            int resultNum = stuTheMapper.delete(new StuThe(id,studentId));
            if (resultNum <= 0) {
                throw new CourseException("inner error");
            } else {
                //库存+1
                Date nowTime = new Date();
                int updateCount = thesisMapper.addNumber(id, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束
                    throw new CloseException("thesis is closed");
                } else {
                    //执行成功,返回成功秒杀的信息
                    return new Execution(id, StateEnum.SUCCESS);
                }
            }
        } catch (CloseException e1) {
            throw e1;
        } catch (Exception e) {
            //所有编译期异常转化为运行期异常
            throw new CourseException("inner error :" + e.getMessage());
        }
    }
}
