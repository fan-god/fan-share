package com.fan.entity;

import com.fan.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @ApiModelProperty("姓名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("状态")
    private Integer stat;
    @ApiModelProperty("备注")
    private String remark;

    private String gwf1;

    private String gwf2;

    private String gwf3;

    private String gwf4;

    private String gwf5;

}