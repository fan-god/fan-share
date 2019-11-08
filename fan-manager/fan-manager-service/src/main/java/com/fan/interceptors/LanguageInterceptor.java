package com.fan.interceptors;

import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author fan
 * @title: LanguageInterceptor
 * @projectName fan-share
 * @description: 重写多语言拦截器，把语言控制参数放到请求头部
 * @date 2019/8/21/00219:35
 */
public class LanguageInterceptor extends LocaleChangeInterceptor {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException {
        String newLocale = request.getHeader(this.getParamName());
        if (newLocale != null && this.checkHttpMethod(request.getMethod())) {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            if (localeResolver == null) {
                throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
            }

            try {
                localeResolver.setLocale(request, response, this.parseLocaleValue(newLocale));
            } catch (IllegalArgumentException var7) {
                if (!this.isIgnoreInvalidLocale()) {
                    throw var7;
                }

                this.logger.debug("Ignoring invalid locale value [" + newLocale + "]: " + var7.getMessage());
            }
        }else {
            LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
            localeResolver.setLocale(request, response, Locale.getDefault());
        }

        /**
         * 解决跨域问题
         */
//        response.setHeader("Access-Control-Allow-Origin", "http://127.0.0.1:8081");
//        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With");
//        response.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
//        response.setHeader("X-Powered-By","Jetty");
//
//        String method= request.getMethod();
//        if (method.equals("OPTIONS")){
//            response.setStatus(200);
//            return false;
//        }
//        System.out.println(method);
        return true;
    }

    private boolean checkHttpMethod(String currentMethod) {
        String[] configuredMethods = this.getHttpMethods();
        if (ObjectUtils.isEmpty(configuredMethods)) {
            return true;
        } else {
            String[] var3 = configuredMethods;
            int var4 = configuredMethods.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                String configuredMethod = var3[var5];
                if (configuredMethod.equalsIgnoreCase(currentMethod)) {
                    return true;
                }
            }

            return false;
        }
    }
}
