package com.fan.controller.v1.web;

import com.fan.annotation.ApiVersion;
import com.fan.entity.ResponseMsg;
import com.fan.entity.User;
import com.fan.service.IUserService;
import com.fan.util.ConstantFan;
import com.fan.util.DataConvertUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author fan
 * @title: UserController
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/3/000315:35
 */
@ApiVersion
@RestController
@RequestMapping("/web/user/{version}")
@Slf4j
@Api(description = "用户管理")
public class UserController_v1p0 {
    @Autowired
    private IUserService userService;

    @PostMapping("/listPageAll")
    @ApiOperation(value = "查询用户", notes = "分页查询用户")
    public ResponseMsg listPageAll(Integer pageNo, Integer pageSize, User user) {
        try {
            PageInfo<User> pageInfo = userService.listPageAll(pageNo, pageSize, user);
            return ResponseMsg.success().setDatas(pageInfo);
        } catch (Exception e) {
            log.error("UserController error:{}", e);
        }
        return ResponseMsg.fail();
    }

    @PostMapping("/login")
    public ResponseMsg login(HttpServletRequest request, User user) {
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            if (StringUtils.isNoneBlank(username, password)) {
                if(userService.login(user)){
                    String s = DataConvertUtil.beanToString(user, ConstantFan.JSON);
                    request.getSession().setAttribute(ConstantFan.USER_SESSION, s);
                    return ResponseMsg.success();
                }else {
                    return ResponseMsg.fail().setMsg("账号或密码错误");
                }
            }
            return ResponseMsg.fail().setMsg("账号或密码为空");
        } catch (Exception e) {
            log.error("login error{}", e);
            return ResponseMsg.fail();
        }
    }

    @PostMapping("/register")
    public ResponseMsg register(@Validated User user, BindingResult bindingResult){
        try {
            if(bindingResult.hasErrors()){
                for (FieldError fieldError : bindingResult.getFieldErrors()) {
                    return ResponseMsg.fail().setMsg(fieldError.getDefaultMessage());
                }
            }
            return userService.register(user);
        } catch (Exception e) {
            log.error("register error:{}", e);
        }
        return ResponseMsg.fail();
    }
}