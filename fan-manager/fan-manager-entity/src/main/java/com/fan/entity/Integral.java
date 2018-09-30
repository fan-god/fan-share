package com.fan.entity;

public class Integral {
    private Integer id;

    private Integer stat;

    private String reason;

    private String modu;

    private Integer score;

    private String rema;

    private String gwf1;

    private String gwf2;

    private String gwf3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getModu() {
        return modu;
    }

    public void setModu(String modu) {
        this.modu = modu == null ? null : modu.trim();
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRema() {
        return rema;
    }

    public void setRema(String rema) {
        this.rema = rema == null ? null : rema.trim();
    }

    public String getGwf1() {
        return gwf1;
    }

    public void setGwf1(String gwf1) {
        this.gwf1 = gwf1 == null ? null : gwf1.trim();
    }

    public String getGwf2() {
        return gwf2;
    }

    public void setGwf2(String gwf2) {
        this.gwf2 = gwf2 == null ? null : gwf2.trim();
    }

    public String getGwf3() {
        return gwf3;
    }

    public void setGwf3(String gwf3) {
        this.gwf3 = gwf3 == null ? null : gwf3.trim();
    }
}