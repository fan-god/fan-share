package com.fan.entity;

import com.fan.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

@Data
@ApiModel
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity {
    @NotBlank(message = "{username_notblank}")
    @ApiModelProperty("姓名")
    private String username;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("性别")
    private String sex;
    @Length(max = 18,message = "{phone_length_error}")
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
    @ApiModelProperty("角色")
    private String role;

}