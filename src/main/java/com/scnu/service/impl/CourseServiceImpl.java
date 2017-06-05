package com.scnu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.scnu.dao.CourseMapper;
import com.scnu.dao.StuCouMapper;
import com.scnu.dao.StudentMapper;
import com.scnu.dao.cache.RedisDao;
import com.scnu.dto.*;
import com.scnu.entity.Course;
import com.scnu.entity.StuCou;
import com.scnu.entity.Student;
import com.scnu.enums.CourseStateEnum;
import com.scnu.exception.CloseException;
import com.scnu.exception.CourseException;
import com.scnu.exception.RepeatException;
import com.scnu.service.CourseService;
import com.scnu.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by ldb on 2017/6/1.
 */
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StuCouMapper stuCouMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private RedisDao redisDao;

    @Value("${REDIS_BASE_KEY}")
    private String REDIS_BASE_KEY;

    //加入盐值，混淆md5
    private final String salt = "asdfgasrf^&*23*&(hjkKH;sdajhkl&*(&kljf";

    @Override
    public PageResult<Course> listCourse(PageBean pageBean) {
        PageResult<Course> result=new PageResult<>();
        //PageHelper封装分页逻辑
        PageHelper.startPage(pageBean.getStart(),pageBean.getPageSize());
        //获取分页后列表
        List<Course> courseList = courseMapper.selectAll();

        // 取分页信息
        PageInfo<Course> pageInfo = new PageInfo<>(courseList);
        long total = pageInfo.getTotal(); //获取总记录数
        //填充值
        result.setRows(courseList);
        result.setTotal(total);
        return result;
    }

    @Override
    public Course getCourse(int id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    @Override
    public Exposer exportUrl(int id) {
        //添加Redis缓存
        Course course = null;
        //添加原则:不影响业务逻辑，用try catch捕获异常
        try{
            course= JsonUtil.jsonToPojo(redisDao.get(REDIS_BASE_KEY+":"+id),Course.class);
        }catch (Exception e){
        }
        if(course==null){
            //如果缓存中为空，则从数据库中查询
            course = courseMapper.selectByPrimaryKey(id);
            //查不到该记录
            if (course == null) {
                return new Exposer(false, id);
            }else{
                //将数据序列化存进缓存中
                //添加原则:不影响业务逻辑，用try catch捕获异常
                try{
                    course= JsonUtil.jsonToPojo(redisDao.get(REDIS_BASE_KEY+":"+id),Course.class);
                    redisDao.set(REDIS_BASE_KEY+":"+id,JsonUtil.objectToJson(course));
                }catch (Exception e){
                }
            }
        }

        Date startTime = course.getStartTime();
        Date endTime = course.getEndTime();

        Date now = new Date();
        //抢课未开启或者抢课已结束
        if (now.getTime() < startTime.getTime() || now.getTime() > endTime.getTime()) {
            return new Exposer(false, id, now.getTime(), startTime.getTime(), endTime.getTime());
        }

        //抢课开启，返回秒杀商品的id、用给接口加密的md5
        String md5 = getMD5(id);
        return new Exposer(true, md5, id);
    }

    private String getMD5(int id) {
        //添加盐值，混淆MD5
        String str = id + "/" + salt;
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }


    @Transactional//开启事务
    @Override
    public CourseExecution executeCourse(int id, int studentId, String md5, String studentMD5) throws CloseException, RepeatException, CourseException {
        if (md5 == null || !md5.equals(getMD5(id)) || studentMD5 == null || !studentMD5.equals(getMD5(studentId))) {
            throw new CourseException("course data rewrite");//数据被重写了
        }

        try {
            //插入抢课信息
            int resultNum = stuCouMapper.insertStuCou(id, studentId);
            if (resultNum <= 0) {
                throw new RepeatException("course repeat");
            } else {
                //减库存
                Date nowTime = new Date();
                int updateCount = courseMapper.reduceNumber(id, nowTime);
                if (updateCount <= 0) {
                    //没有更新库存记录，说明秒杀结束 rollback
                    throw new CloseException("course is closed");
                } else {
                    //秒杀成功,得到成功插入的明细记录,并返回成功秒杀的信息 commit
                    StuCou success = stuCouMapper.getByStuIdWithCourse(id, studentId);
                    return new CourseExecution(id, CourseStateEnum.SUCCESS, success);
                }
            }
        } catch (CloseException e1) {
            throw e1;
        } catch (RepeatException e2) {
            throw e2;
        } catch (Exception e) {
            //所有编译期异常转化为运行期异常
            throw new CourseException("inner error :" + e.getMessage());
        }
    }

    @Override
    public LoginResult checkLogin(Student student) {
        Student result = studentMapper.checkLogin(student.getUserName(), student.getPassword());
        if(result==null){
            return new LoginResult(student.getId(),false);
        }
        String studentMD5 = getMD5(result.getId());
        return new LoginResult(result.getId(),studentMD5,true);
    }


    @Override
    public Integer addCourse(Course course) {
        course.setCreateTime(new Date());
        return courseMapper.insert(course);
    }
}
