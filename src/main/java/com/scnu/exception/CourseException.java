package com.scnu.exception;

/**
 * Created by ldb on 2017/5/30.
 * 抢课所有异常父类
 */
public class CourseException extends RuntimeException {
    public CourseException(String message) {
        super(message);
    }

    public CourseException(String message, Throwable cause) {
        super(message, cause);
    }
}
