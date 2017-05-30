package com.scnu.exception;

/**
 * Created by ldb on 2017/5/30.
 * 抢课关闭，用户再抢课则出现此异常
 */
public class CloseException extends CourseException {
    public CloseException(String message) {
        super(message);
    }

    public CloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
