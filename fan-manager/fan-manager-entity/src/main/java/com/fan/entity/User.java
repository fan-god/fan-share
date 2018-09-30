package com.fan.entity;

public class User {
    private Integer id;

    private String username;

    private String password;

    private String sex;

    private Integer phone;

    private String address;

    private Integer stat;

    private String rema;

    private Integer gwf1;

    private String gwf2;

    private String gwf3;

    private String gwf4;

    private String gwf5;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public String getRema() {
        return rema;
    }

    public void setRema(String rema) {
        this.rema = rema == null ? null : rema.trim();
    }

    public Integer getGwf1() {
		return gwf1;
	}

	public void setGwf1(Integer gwf1) {
		this.gwf1 = gwf1;
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
}