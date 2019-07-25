package com.fan.test;

import com.fan.entity.excelmodel.TestExcelModel;
import com.fan.util.ExcelFactory;
import com.google.common.collect.Lists;

import java.text.ParseException;
import java.util.List;

/**
 * @author fan
 * @title: TestWhitoutProperties
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/12/001213:59
 */
public class TestWhitoutProperties {

    public static void main(String[] args) throws ParseException {
//        Pattern pattern = Pattern.compile("v([+-]?\\d*\\.\\d*)");
//        Matcher matcher = pattern.matcher("fan-manager-web/app/user/v1.0/listPageAll");
//        System.out.println(matcher.find());
//        String group1 = matcher.group(0);
//        String group2 = matcher.group(1);
//        System.out.println(group1);
//        System.out.println(group2);

//        Integer age = 0;
//        Date birthDate = DateUtils.parseDate("1991-07-01","yyyy-MM-dd");
//        if (null != birthDate) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(birthDate);
//            Calendar calendarNow = Calendar.getInstance();
//            calendarNow.setTime(new Date());
//            age = calendarNow.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
//        }
//        System.out.println(age);

        ExcelFactory excelFactory = new ExcelFactory();
        List<TestExcelModel> list = Lists.newArrayList();
        TestExcelModel m1 = TestExcelModel.builder().age(13).email("ctv@bnj.com").name("ftgvyubh").build();
        TestExcelModel m2 = TestExcelModel.builder().age(11).email("dwfevgrtgbhn@163.com").name("qaqzzxswx").build();
        TestExcelModel m3 = TestExcelModel.builder().age(23).email("1129985465@qq.com").name("wf").build();
        list.add(m1);
        list.add(m2);
        list.add(m3);
        excelFactory.getExcelWrite(TestExcelModel.class,list);
    }
}
