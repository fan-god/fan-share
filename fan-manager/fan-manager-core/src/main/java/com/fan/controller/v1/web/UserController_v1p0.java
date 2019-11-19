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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @PostMapping("/updateUser")
    public ResponseMsg updateUser(User user) {
        try {
            Integer count = userService.updateUser(user);
            if (count > 0) {
                return ResponseMsg.success();
            }
        } catch (Exception e) {
            log.error("UserController error:{}", e);
        }
        return ResponseMsg.fail();
    }

    @GetMapping("/deleteUser")
    public ResponseMsg deleteUser(Long id) {
        try {
            Integer count = userService.deleteUserById(id);
            if (count > 0) {
                return ResponseMsg.success();
            }
        } catch (Exception e) {
            log.error("UserController error:{}", e);
        }
        return ResponseMsg.fail();
    }

    @PostMapping("/queryUserById")
    public ResponseMsg queryUserById(Long id) {
        try {
            User user = userService.queryUserById(id);
            return ResponseMsg.success().setDatas(user);
        } catch (Exception e) {
            log.error("{}", e);
        }
        return ResponseMsg.fail();
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("/login")
    public ResponseMsg login(User user, HttpServletResponse response, HttpServletRequest request) {
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            if (StringUtils.isNoneBlank(username, password)) {
//                if (isReLogin()) {
//                    response.sendRedirect("/fan-manager-web/loginSuccess.jsp");
//                    return ResponseMsg.success(); // 如果已经登陆，无需重新登录
//                }
                if (userService.login(user)) {
                    String s = DataConvertUtil.beanToString(user, ConstantFan.JSON);
                    request.getSession().setAttribute(ConstantFan.USER_SESSION, s);
                    return ResponseMsg.success();
                } else {
                    return ResponseMsg.fail().setMsg("账号或密码错误");
                }
//                response.sendRedirect("/fan-manager-web/loginSuccess.jsp");
//                // 调用shiro的登陆验证
//                return shiroLogin(user);
            }
//            response.sendRedirect("/fan-manager-web/error.jsp");
            return ResponseMsg.fail().setMsg("账号或密码为空");

        } catch (Exception e) {
            log.error("login error{}", e);
            return ResponseMsg.fail();
        }
    }

    @PostMapping("/register")
    public ResponseMsg register(@Validated User user, BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors()) {
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

    private ResponseMsg shiroLogin(User user) {
        // 组装token，包括客户公司名称、简称、客户编号、用户名称；密码
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword().toCharArray(), null);
        token.setRememberMe(true);

        // shiro登陆验证
        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException e) {
            return ResponseMsg.fail().setMsg("用户不存在或者密码错误！");
        } catch (IncorrectCredentialsException e) {
            return ResponseMsg.fail().setMsg("用户不存在或者密码错误！");
        } catch (Exception e) {
            return ResponseMsg.fail();
        }
        return ResponseMsg.success();
    }

    /**
     * 查看是否重复登录
     *
     * @return
     */
    private boolean isReLogin() {
        Subject us = SecurityUtils.getSubject();
        if (us.isAuthenticated()) {
            return true; // 参数未改变，无需重新登录，默认为已经登录成功
        }
        return false; // 需要重新登陆
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("/logout")
    public ResponseMsg logout(HttpServletResponse response) {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            try {
                subject.logout();
                response.sendRedirect("/fan-manager-web");
                return ResponseMsg.success();
            } catch (Exception e) {
                log.error("{}", e);
            }
        }
        return ResponseMsg.fail();
    }
}
