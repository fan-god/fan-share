package com.fan.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @description:
 * @author: fan
 * @date: 2018/7/25 14:25
 **/
@Slf4j
public class DateConverter implements Converter<String, Date> {

    private String pattern;

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date convert(String source) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            dateFormat.setLenient(false);
            return dateFormat.parse(source);
        } catch (ParseException e) {

        }
        return null;
    }

    /**
     * 获取某段时这里写代码片间内的所有日期
     *
     * @param dBegin
     * @param dEnd
     * @return
     */
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        try {
            List<Date> lDate = Lists.newArrayList();
            lDate.add(dBegin);
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                lDate.add(calBegin.getTime());
            }
            return lDate;
        } catch (Exception e) {
            log.error("DateConverter findDates error:{}", e);
            return null;
        }
    }

    /**
     * 获取上个星期所有日期
     *
     * @return
     */
    public static List<Date> getLastWeekInterval() {
        try {
            Calendar calendar1 = Calendar.getInstance();
            Calendar calendar7 = Calendar.getInstance();
            int dayOfWeek = calendar1.get(Calendar.DAY_OF_WEEK) - 1;
            int offset1 = 1 - dayOfWeek;
            int offset7 = 6 - dayOfWeek;
            calendar1.add(Calendar.DATE, offset1 - 7);
            calendar7.add(Calendar.DATE, offset7 - 7);
            Date beginOfWeek = calendar1.getTime();
            Date endOfWeek = calendar7.getTime();
            return findDates(beginOfWeek, endOfWeek);
        } catch (Exception e) {
            log.error("DateConverter getLastTimeInterval error:{}", e);
            return null;
        }
    }

    /**
     * 获取dayOfNum天前所有日期
     *
     * @return
     */
    public static List<Date> getDaysAgoInterval(Integer dayOfNum) {
        Date dBegin = new Date(DateConvertEditor.getDayOfLong(dayOfNum));
        Date dEnd = new Date();
        return findDates(dBegin, dEnd);
    }

    /**
     * 获取上个星期所有日期
     *
     * @return
     */
    public static List<Date> getLastWeekIntervalNew() {
        List<Date> list = DateConverter.getDaysAgoInterval(7);
        return list;
    }

    public static Date getDayOfNumAgo(Integer dayOfNum) {
        return new Date(DateConvertEditor.getDayOfLong(dayOfNum));
    }

    public static Date getDate(String source) {
        try {
            SimpleDateFormat sdf = getSdf(ConstantFan.DATE_PATTERN_10);
            return sdf.parse(source);
        } catch (ParseException e) {
            log.error("DateConverter getDate error:{}", e);
        }
        return null;
    }

    public static Date getDate(Long source) {
        try {
            SimpleDateFormat sdf = getSdf(ConstantFan.DATE_PATTERN_19);
            return sdf.parse(sdf.format(source));
        } catch (ParseException e) {
            log.error("DateConverter getDate error:{}", e);
        }
        return null;
    }

    private static SimpleDateFormat getSdf(String patten) {
        SimpleDateFormat sdf = new SimpleDateFormat(patten);
        sdf.setLenient(false);
        return sdf;
    }

}
