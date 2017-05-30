package com.scnu.exception;

/**
 * Created by ldb on 2017/5/30.
 * 用户重复抢课，则出现此异常
 */
public class RepeatException extends CourseException {

    public RepeatException(String message) {
        super(message);
    }

    public RepeatException(String message, Throwable cause) {
        super(message, cause);
    }
}
