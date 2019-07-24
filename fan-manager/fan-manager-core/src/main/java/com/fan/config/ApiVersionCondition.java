package com.fan.config;


import com.fan.util.ConstantFan;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author fan
 * @title: ApiVersionCondition
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/12/001211:40
 */
public class ApiVersionCondition implements RequestCondition<ApiVersionCondition> {
    // 路径中版本的前缀， 这里用 /v[1-9]/的形式
    private final static Pattern VERSION_PREFIX_PATTERN = Pattern.compile(ConstantFan.VERSION_PREFIX_PATTERN);

    private Double apiVersion;

    public ApiVersionCondition(Double apiVersion) {
        this.apiVersion = apiVersion;
    }

    public ApiVersionCondition combine(ApiVersionCondition apiVersionCondition) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiVersionCondition(apiVersionCondition.getApiVersion());
    }

    /**
     * 根据request查找匹配到的筛选条件
     *
     * @param httpServletRequest
     * @return
     */
    @Override
    public ApiVersionCondition getMatchingCondition(HttpServletRequest httpServletRequest) {
        String uri = httpServletRequest.getRequestURI();
        Matcher m = VERSION_PREFIX_PATTERN.matcher(uri);
        if (m.find()) {
            Double version = Double.valueOf(m.group(1));
            if (version >= this.apiVersion) {
                return this;
            }
        }
        return null;
    }

    public int compareTo(ApiVersionCondition apiVersionCondition, HttpServletRequest request) {
        // 优先匹配最新的版本号
        return (int) (apiVersionCondition.getApiVersion() - this.apiVersion);
    }

    public Double getApiVersion() {
        return apiVersion;
    }
}
