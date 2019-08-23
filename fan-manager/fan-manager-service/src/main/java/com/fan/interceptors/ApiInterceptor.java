package com.fan.interceptors;

import com.fan.annotation.ApiPassport;
import com.fan.entity.ResponseMsg;
import com.fan.service.IPWhitelistService;
import com.fan.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fan
 * @title: ApiInterceptor
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/21/002113:26
 */
public class ApiInterceptor implements HandlerInterceptor {
    @Autowired
    private IPWhitelistService ipWhitelistService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (o.getClass().isAssignableFrom(HandlerMethod.class)) {
            ApiPassport passport = ((HandlerMethod) o).getMethodAnnotation(ApiPassport.class);
            //没有声明需要权限,或者声明不验证权限
            if (passport == null || passport.apiValidate() == false)
                return true;
            else {
                //IP白名单校验
                String ip = HttpCilentUtil.getIpAddr(request);
                ResponseMsg msg = ResponseMsg.fail();
                if (ipWhitelistService.isOnIPWhitelist(ip)) {
                    return true;
                } else {
                    msg.setMsg(ip + InternationalUtil.getMessage(LangConstant.ApiData.ip_not_on_whitelist.name()));
                    HttpCilentUtil.printOut(response, DataConvertUtil.beanToString(msg, ConstantFan.JSON));
                    return false;
                }
                //TODO 权限校验，签名校验。。。。。
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
