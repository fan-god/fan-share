package com.fan.controller.v2.web;

import com.fan.annotation.ApiVersion;
import com.fan.entity.Msg;
import com.fan.entity.User;
import com.fan.service.IUserService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fan
 * @title: UserController
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/3/000315:35
 */
@ApiVersion(2.3)
@RestController
@RequestMapping("/web/user/{version}")
@Slf4j
@Api(description = "用户管理")
public class UserController_v2p3 {
    @Autowired
    private IUserService userService;

    @PostMapping("/listPageAll")
    @ApiOperation(value = "查询用户",notes = "分页查询用户")
    public Msg listPageAll(Integer pageNo, Integer pageSize, User user) {
        try {
            PageInfo<User> pageInfo = userService.listPageAll(pageNo, pageSize, user);
            return Msg.success().setVersion(2.3).setDatas(pageInfo);
        } catch (Exception e) {
            log.error("UserController error:{}", e);
        }
        return Msg.fail();
    }

}
