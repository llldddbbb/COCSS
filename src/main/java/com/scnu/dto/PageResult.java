package com.scnu.dto;

import java.util.List;

/**
 * Created by ldb on 2017/6/5.
 * 分页返回结果封装
 */
public class PageResult<T> {
    private List<T> rows;
    private Long total;

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
