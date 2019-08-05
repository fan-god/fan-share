package com.fan.entity;

import com.fan.entity.base.GeneralBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fan
 * @title: DataDictionary
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/1/000116:27
 */
@Data
@ApiModel
@Document
@Builder
public class DataDictionary extends GeneralBean {

    @ApiModelProperty("数据字典键")
    private String dicKey;
    @ApiModelProperty("数据字典值")
    private String dicValue;
}
