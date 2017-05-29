package com.scnu.dto;

/**
 * Created by ldb on 2017/5/29.
 *分页dto，目的是获取start
 */
public class PageBean {
    private int page;
    private int pageSize;
    private int start;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (page-1)*pageSize;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public PageBean() {
    }

    public PageBean(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
