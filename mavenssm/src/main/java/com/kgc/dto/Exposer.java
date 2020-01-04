package com.kgc.dto;

/**
 * @Author: CuiJunJia
 * @Date: 2019-07-25 08:58
 * @Deprecated: 暴露秒杀地址DTO
 */
public class Exposer {

    //是否开启秒杀
    private boolean exposed;

    //一种加密方式
    private String md5;

    private Integer seckillId;

    //获取系统当前时间
    private long now;

    //开始时间
    private long start;

    //结束时间
    private long end;

    public Exposer(boolean exposed, String md5, Integer seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;

    }

    public Exposer(boolean exposed,Integer seckillId,long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }

    public Exposer(boolean exposed, Integer seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
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

    public Integer getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Integer seckillId) {
        this.seckillId = seckillId;
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

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
