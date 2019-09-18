package com.fan.controller.v1.api;

import com.fan.annotation.ApiPassport;
import com.fan.annotation.ApiVersion;
import com.fan.entity.ResponseMsg;
import com.fan.entity.User;
import com.fan.service.IUserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fan
 * @title: UserApi
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/21/002113:19
 */
@RestController
@RequestMapping("/api/user/{version}")
@Slf4j
@ApiVersion
public class UserApiController {
    @Autowired
    private IUserService userService;

    @ApiPassport
    @PostMapping("/listPageAll")
    public ResponseMsg listPageAll(Integer pageNo, Integer pageSize, User user) {
        try {
            PageInfo<User> pageInfo = userService.listPageAll(pageNo, pageSize, user);
            return ResponseMsg.success().setDatas(pageInfo);
        } catch (Exception e) {
            log.error("UserController error:{}", e);
        }
        return ResponseMsg.fail();
    }

    @ApiPassport
    @GetMapping("/getUserByName")
    public ResponseMsg getUserByName(String username) {
        try {
            User user = userService.getUserByName(username);
            ResponseMsg a = ResponseMsg.success();
            ResponseMsg b = a.setDatas(user);
            System.out.println(b);
            return b;
        } catch (Exception e) {
            log.error("{}", e);
        }
        return ResponseMsg.fail();
    }
}
