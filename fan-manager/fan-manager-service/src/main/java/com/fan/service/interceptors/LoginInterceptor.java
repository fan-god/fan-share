package com.fan.service.interceptors;

import com.fan.entity.Msg;
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
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            String msg = InternationalUtil.getMessage("test");
            HttpCilentUtil.printOut(response,  DataConvertUtil.beanToString(Msg.fail().setMsg(msg), ConstantFan.JSON));
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
