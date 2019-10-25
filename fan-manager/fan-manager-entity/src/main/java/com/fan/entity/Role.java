package com.fan.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Role {
    private Long id;

    private String roleId;

    private String roleName;

    private String roleEnName;

    private String remark;

    private Integer roleType;


}