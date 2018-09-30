package com.fan.entity;


public class BaseFile {
    private Integer id;

    private String fnna;

    private String ofna;

    private String updt;

    private String scrm;

    private String stat;

    private String gwf1;

    private String gwf2;

    private String gwf3;

    private String gwf4;

    private String gwf5;

    private String rema;

    private String flag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFnna() {
        return fnna;
    }

    public void setFnna(String fnna) {
        this.fnna = fnna == null ? null : fnna.trim();
    }

    public String getOfna() {
        return ofna;
    }

    public void setOfna(String ofna) {
        this.ofna = ofna == null ? null : ofna.trim();
    }

    public String getUpdt() {
        return updt;
    }

    public void setUpdt(String updt) {
        this.updt = updt == null ? null : updt.trim();
    }

    public String getScrm() {
        return scrm;
    }

    public void setScrm(String scrm) {
        this.scrm = scrm == null ? null : scrm.trim();
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat == null ? null : stat.trim();
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

    public String getGwf4() {
        return gwf4;
    }

    public void setGwf4(String gwf4) {
        this.gwf4 = gwf4 == null ? null : gwf4.trim();
    }

    public String getGwf5() {
        return gwf5;
    }

    public void setGwf5(String gwf5) {
        this.gwf5 = gwf5 == null ? null : gwf5.trim();
    }

    public String getRema() {
        return rema;
    }

    public void setRema(String rema) {
        this.rema = rema == null ? null : rema.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

	@Override
	public String toString() {
		return "BaseFile [id=" + id + ", fnna=" + fnna + ", ofna=" + ofna + ", updt=" + updt + ", scrm=" + scrm
				+ ", stat=" + stat + ", gwf1=" + gwf1 + ", gwf2=" + gwf2 + ", gwf3=" + gwf3 + ", gwf4=" + gwf4
				+ ", gwf5=" + gwf5 + ", rema=" + rema + ", flag=" + flag + "]";
	}
    
}