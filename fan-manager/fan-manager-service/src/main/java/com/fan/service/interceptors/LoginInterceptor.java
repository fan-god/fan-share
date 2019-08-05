package com.fan.service.interceptors;

import com.fan.entity.Msg;
import com.fan.entity.User;
import com.fan.util.ConstantFan;
import com.fan.util.DataConvertUtil;
import com.fan.util.HttpCilentUtil;
import com.fan.util.InternationalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author:fan
 * @Date:2018/9/29.
 * @Description:
 * @PACKAGE_NAME:com.fan.service.intecepters
 * @PROJECT_NAME:fansys
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        User user = (User) request.getSession().getAttribute(ConstantFan.USER_SESSION);
        Msg msg;
        if (null == user) {
            msg = Msg.loginFail();
            HttpCilentUtil.printOut(response, DataConvertUtil.beanToString(msg, ConstantFan.JSON));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
