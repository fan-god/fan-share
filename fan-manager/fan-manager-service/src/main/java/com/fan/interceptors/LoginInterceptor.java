package com.fan.interceptors;

import com.fan.entity.Msg;
import com.fan.entity.User;
import com.fan.util.ConstantFan;
import com.fan.util.DataConvertUtil;
import com.fan.util.HttpCilentUtil;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        try {
            Object userObj = request.getSession().getAttribute(ConstantFan.USER_SESSION);
//            User user = DataConvertUtil.stringToBean(User.class, userObj.toString(), ConstantFan.JSON);
            if (null == userObj) {
                HttpCilentUtil.printOut(response, DataConvertUtil.beanToString(Msg.loginFail(), ConstantFan.JSON));
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("{}", e);
            HttpCilentUtil.printOut(response, DataConvertUtil.beanToString(Msg.fail(), ConstantFan.JSON));
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
