package com.scnu.dto;

/**
 * Created by ldb on 2017/5/30.
 * 暴露秒杀地址
 */
public class Exposer {

    private long id;

    //是否开启秒杀
    private boolean exposed;

    //加密措施
    private String md5;

    //系统当前时间(毫秒)
    private long now;

    //秒杀的开启时间
    private long start;

    //秒杀的结束时间
    private long end;

    public Exposer(boolean exposed, String md5, long id) {
        this.exposed = exposed;
        this.md5 = md5;
        this.id = id;
    }

    public Exposer(boolean exposed, long id,long now, long start, long end) {
        this.exposed = exposed;
        this.id=id;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, long id) {
        this.exposed = exposed;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }
}
