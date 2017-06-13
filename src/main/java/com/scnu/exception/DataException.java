package com.scnu.exception;

/**
 * Created by ldb on 2017/6/12.
 */
public class DataException  extends CourseException{
    public DataException(String message) {
        super(message);
    }

    public DataException(String message, Throwable cause) {
        super(message, cause);
    }
}
