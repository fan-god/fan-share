package com.fan.entity.excelmodel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Builder;
import lombok.Data;

/**
 * @author fan
 * @title: TestExcelModel
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/25/002510:36
 */
@Data
@Builder
public class TestExcelModel extends BaseRowModel {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private Integer age;

    @ExcelProperty(value = "邮箱", index = 2)
    private String email;

}
