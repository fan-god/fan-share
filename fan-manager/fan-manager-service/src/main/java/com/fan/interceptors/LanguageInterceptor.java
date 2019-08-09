package com.fan.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author fan
 * @title: LanguageInterceptor
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/5/000515:55
 */
public class LanguageInterceptor implements HandlerInterceptor {
    /**
     * Default name of the locale specification parameter: "locale".
     */
    private String paramName = "locale";

    public String getParamName() {
        return this.paramName;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        Locale newLocale = getLocale(request);
        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);

        if (localeResolver == null) {
            throw new IllegalStateException("No LocaleResolver found: not in a DispatcherServlet request?");
        }
        localeResolver.setLocale(request, response, newLocale);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    //根据language 获取Locale
    public static Locale getLocale(HttpServletRequest request) {
        Locale currentLocale = RequestContextUtils.getLocale(request);
//        Locale locale = Locale.getDefault();
//        if (language != null) {
//            if (language.equals("en")) {
//                locale = Locale.ENGLISH;
//            }
//        }
        return currentLocale;
    }
}