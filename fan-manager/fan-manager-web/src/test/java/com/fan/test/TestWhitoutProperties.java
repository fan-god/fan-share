package com.fan.test;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        Integer age = 0;
        Date birthDate = DateUtils.parseDate("1991-07-01","yyyy-MM-dd");
        if (null != birthDate) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthDate);
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(new Date());
            age = calendarNow.get(Calendar.YEAR) - calendar.get(Calendar.YEAR);
        }
        System.out.println(age);
    }
}
