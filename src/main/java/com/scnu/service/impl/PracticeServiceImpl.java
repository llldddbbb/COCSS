package com.scnu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.dao.PracticeMapper;
import com.scnu.dao.StuPraMapper;
import com.scnu.dao.StudentMapper;
import com.scnu.dao.cache.RedisDao;
import com.scnu.dto.*;
import com.scnu.entity.Practice;
import com.scnu.entity.StuCou;
import com.scnu.entity.StuPra;
import com.scnu.entity.Student;
import com.scnu.enums.StateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.CourseException;
import com.scnu.exception.RepeatException;
import com.scnu.service.PracticeService;
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
public class PracticeServiceImpl implements PracticeService {

    @Autowired
    private PracticeMapper practiceMapper;

    @Autowired
    private StuPraMapper stuPraMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisDao redisDao;

    @Value("${REDIS_PRACTICE_KEY}")
    private String REDIS_PRACTICE_KEY;


    @Override
    public PageResult<Practice> listPractice(PageBean pageBean) {
        PageResult<Practice> result=new PageResult<>();
        //PageHelper封装分页逻辑
        PageHelper.startPage(pageBean.getStart(),pageBean.getPageSize());
        //获取分页后列表
        List<Practice> practiceList = practiceMapper.selectAll();

        // 取分页信息
        PageInfo<Practice> pageInfo = new PageInfo<>(practiceList);
        long total = pageInfo.getTotal(); //获取总记录数
        //填充值
        result.setRows(practiceList);
        result.setTotal(total);
        return result;
    }

    @Override
    public Practice getPractice(int id) {
        return practiceMapper.selectByPrimaryKey(id);
    }

    @Override
    public Exposer exportUrl(int id) {
        //添加Redis缓存
        Practice practice = null;
        //添加原则:不影响业务逻辑，用try catch捕获异常
        try{
            practice= JsonUtil.jsonToPojo(redisDao.get(REDIS_PRACTICE_KEY+":"+id),Practice.class);
        }catch (Exception e){
        }
        if(practice==null){
            //如果缓存中为空，则从数据库中查询
            practice = practiceMapper.selectByPrimaryKey(id);
            //查不到该记录
            if (practice == null) {
                return new Exposer(false, id);
            }else{
                //将数据序列化存进缓存中
                //添加原则:不影响业务逻辑，用try catch捕获异常
                try{
                    redisDao.set(REDIS_PRACTICE_KEY+":"+id,JsonUtil.objectToJson(practice));
                }catch (Exception e){
                }
            }
        }

        Date startTime = practice.getStartTime();
        Date endTime = practice.getEndTime();

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
    public Execution executePractice(int id, int studentId, String md5, String studentMD5) throws CloseException, RepeatException, CourseException {
        if (md5 == null || !md5.equals(SecureUtil.getMD5(id))||studentMD5==null) {
            throw new CourseException("practice data rewrite");//数据被重写了
        }
        //判断是否token是否正确，根据studentId获取student，生成studentMD5，与页面传来的进行对比
        Student student = studentMapper.selectByPrimaryKey(studentId);
        if(!studentMD5.equals(SecureUtil.getMD5(student))){
            throw new CourseException("practice data rewrite");//数据被重写了
        }
        try {
            //判断是否重复选课
            StuPra example=new StuPra(studentId);
            List<StuPra> list = stuPraMapper.select(example);
            //如果已经选课，则抛出异常
            if(list!=null&&list.size()>0){
                throw new RepeatException("practice repeat");
            }
            //插入抢课信息
            int resultNum = stuPraMapper.insertStuPra(id, studentId);
            if (resultNum <= 0) {
                throw new RepeatException("practice repeat");
            } else {
                //减库存
                Date nowTime = new Date();
                int updateCount = practiceMapper.reduceNumber(id, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束 rollback
                    throw new CloseException("practice is closed");
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
    public Result addPractice(Practice practice) {
        practice.setCreateTime(new Date());
        int result = practiceMapper.insert(practice);
        if(result>0){
            return Result.ok();
        }else{
            return Result.isNotOK();
        }
    }

    @Transactional//开启事务
    @Override
    public Result updatePractice(Practice practice) throws CourseException{
        //添加原则:不影响业务逻辑，用try catch捕获异常
        int result = practiceMapper.updateByPrimaryKey(practice);
        if(result>0){
            try{
                //重新设置缓存
                redisDao.set(REDIS_PRACTICE_KEY+":"+practice.getId(), JsonUtil.objectToJson(practice));
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
    public Result deletePractice(Integer id) throws CourseException{
        //查询是否有学生选课
        StuPra stuPra=new StuPra();
        stuPra.setPracticeId(id);
        List<StuPra> list = stuPraMapper.select(stuPra);
        if(list.size()>0){
            return Result.isNotOK("该课程有学生选课，不能删除");
        }
        int result = practiceMapper.deleteByPrimaryKey(id);
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
    public List<Practice> listPracticeByStudentId(Integer studentId) {
        //根据学生id查询获取所选实习的Id
        List<Practice> result=practiceMapper.listPracticeByStudentId(studentId);
        return result;
    }

    @Transactional//开启事务
    @Override
    public Execution rollBackPractice(int id, int studentId, String studentMD5) throws CloseException, CourseException {
        if (studentMD5==null) {
            throw new CourseException("no login");//未登录
        }
        //判断token是否正确，根据studentId获取student，生成studentMD5，与页面传来的进行对比
        Student student = studentMapper.selectByPrimaryKey(studentId);
        if(!studentMD5.equals(SecureUtil.getMD5(student))){
            throw new CourseException("practice data rewrite");//数据被重写了
        }
        try {
            //删除抢课信息
            int resultNum = stuPraMapper.delete(new StuPra(id,studentId));
            if (resultNum <= 0) {
                throw new CourseException("inner error");
            } else {
                //库存+1
                Date nowTime = new Date();
                int updateCount = practiceMapper.addNumber(id, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束
                    throw new CloseException("practice is closed");
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
