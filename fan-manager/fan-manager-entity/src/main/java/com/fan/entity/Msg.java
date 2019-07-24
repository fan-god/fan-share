package com.fan.entity;

import java.util.HashMap;
import java.util.Map;


/**
 * @author fan
 * @ClassName: Msg
 * @Description:该类作为通用的返回类使用，进行统一的包装
 * @date 2018年7月10日
 */
public class Msg {

    //成功操作码
    public static final Integer SUCCESS_CODE = 200;
    //失败操作码
    public static final Integer FAIL_CODE = 100;
    //登录失效操作码
    public static final Integer LOGIN_CODE = 700;
    //越权警告操作码
    public static final Integer PERMISSION_CODE = 401;

    //状态码   200-成功   100-失败
    private int code;
    //提示信息
    private String msg;
    //用户返回给浏览器的数据
    private Map<String, Object> extend = new HashMap<String, Object>();
    //返回数据
    private Object datas;
    //接口版本
    private Double version;

    //成功信息
    public static Msg success() {
        Msg result = new Msg();
        result.setCode(SUCCESS_CODE);
        result.setVersion(1.0);
        result.setMsg("处理成功！");
        return result;
    }

    //失败信息
    public static Msg fail() {
        Msg result = new Msg();
        result.setCode(FAIL_CODE);
        result.setVersion(1.0);
        result.setMsg("处理失败！");
        return result;
    }

    //失败信息
    public static Msg loginFail() {
        Msg result = new Msg();
        result.setCode(LOGIN_CODE);
        result.setMsg("登录已失效，请重新登录");
        return result;
    }

    public Msg add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public Msg setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Msg setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public Object getDatas() {
        return datas;
    }

    public Msg setDatas(Object datas) {
        this.datas = datas;
        return this;
    }

    public Double getVersion() {
        return version;
    }

    public Msg setVersion(Double version) {
        this.version = version;
        return this;
    }

    /**
     * Creates a new instance of Msg.
     *
     * @param code
     * @param msg
     * @param extend
     */
    public Msg(int code, String msg, Map<String, Object> extend, Double version) {
        super();
        this.code = code;
        this.msg = msg;
        this.extend = extend;
        this.version = version;
    }

    /**
     * Creates a new instance of Msg.
     */
    public Msg() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "Msg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", extend=" + extend +
                ", datas=" + datas +
                ", version=" + version +
                '}';
    }
}
