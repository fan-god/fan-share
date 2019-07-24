package com.fan.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author fan
 * @title: Swagger2Config
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/3/000315:58
 */
@EnableWebMvc
@EnableSwagger2
@Configuration
public class Swagger2Config {

    @Bean
    public Docket userApis() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .enable(true);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("接口列表 v1.0") // 任意，请稍微规范点
                .description("接口测试") // 任意，请稍微规范点
                .termsOfServiceUrl("https://www.baidu.com") //
                .version("1.0")
                .contact(new Contact("王帆","https://www.baidu.com","1129984165@qq.com"))
                .build();
    }
}
