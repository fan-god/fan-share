package com.fan.entity;

import com.fan.util.InternationalUtil;
import com.fan.util.LangConstant;
import com.fan.util.SignUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * @author fan
 * @ClassName: Msg
 * @Description:该类作为通用的返回类使用，进行统一的包装
 * @date 2018年7月10日
 */
public class ResponseMsg {

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
    private Map<String, Object> extend = new HashMap<>();
    //返回数据
    private Object datas;
    //接口版本
    private Double version;
    //消息ID用于定位追踪
    private String msgId;

    //成功信息
    public static ResponseMsg success() {
        ResponseMsg result = new ResponseMsg();
        result.setCode(SUCCESS_CODE);
        result.setVersion(1.0);
        result.setMsg("处理成功！");
        result.setMsgId(SignUtil.getSHA1(String.valueOf(System.currentTimeMillis())));
        return result;
    }

    //失败信息
    public static ResponseMsg fail() {
        ResponseMsg result = new ResponseMsg();
        result.setCode(FAIL_CODE);
        result.setVersion(1.0);
        result.setMsg("处理失败！");
        result.setMsgId(SignUtil.getSHA1(String.valueOf(System.currentTimeMillis())));
        return result;
    }

    //失败信息
    public static ResponseMsg loginFail() {
        ResponseMsg result = new ResponseMsg();
        result.setCode(LOGIN_CODE);
        result.setMsg(InternationalUtil.getMessage(LangConstant.MsgData.login_out.name()));
        return result;
    }

    public ResponseMsg add(String key, Object value) {
        this.getExtend().put(key, value);
        return this;
    }

    public int getCode() {
        return code;
    }

    public ResponseMsg setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseMsg setMsg(String msg) {
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

    public ResponseMsg setDatas(Object datas) {
        this.datas = datas;
        return this;
    }

    public Double getVersion() {
        return version;
    }

    public String getMsgId() {
        return msgId;
    }

    public ResponseMsg setVersion(Double version) {
        this.version = version;
        return this;
    }

    private void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * Creates a new instance of Msg.
     *
     * @param code
     * @param msg
     * @param extend
     */
    public ResponseMsg(int code, String msg, Map<String, Object> extend, Object datas, Double version, String msgId) {
        this.code = code;
        this.msg = msg;
        this.extend = extend;
        this.datas = datas;
        this.version = version;
        this.msgId = msgId;
    }

    /**
     * Creates a new instance of Msg.
     */
    public ResponseMsg() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    public String toString() {
        return "ResponseMsg{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", extend=" + extend +
                ", datas=" + datas +
                ", version=" + version +
                ", msgId='" + msgId + '\'' +
                '}';
    }
}
