package com.scnu.dto;

/**
 * Created by ldb on 2017/6/1.
 */
public class CourseResult<T> {

    private boolean success;
    private T data;
    private String error;

    public CourseResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public CourseResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
