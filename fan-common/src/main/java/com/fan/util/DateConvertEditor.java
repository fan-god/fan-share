package com.fan.util;

import com.mchange.lang.IntegerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:日期转化器
 * @author: fan
 * @date: 2018/7/25 15:00
 **/
@Slf4j
public class DateConvertEditor extends PropertyEditorSupport {

    private static SimpleDateFormat datetimeFormat = new SimpleDateFormat(ConstantFan.DATE_PATTERN_19);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat(ConstantFan.DATE_PATTERN_10);

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.hasText(text)) {
            try {
                if (text.indexOf(":") == -1 && text.length() == 10) {
                    setValue(this.dateFormat.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 19) {
                    setValue(this.datetimeFormat.parse(text));
                } else if (text.indexOf(":") > 0 && text.length() == 21) {
                    text = text.replace(".0", "");
                    setValue(this.datetimeFormat.parse(text));
                } else {
                    throw new IllegalArgumentException("Could not parse date, date format is error ");
                }
            } catch (ParseException e) {
                IllegalArgumentException iae = new IllegalArgumentException("Could not parse date: " + e);
                iae.initCause(e);
                throw iae;
            }
        } else {
            setValue(null);
        }
    }

    public static String getDateTime() {
        try {
            return datetimeFormat.format(new Date());
        } catch (Exception e) {
            log.error("DateConvertEditor getDateTime error:", e);
            return null;
        }
    }

    public static String getDate() {
        try {
            return dateFormat.format(new Date());
        } catch (Exception e) {
            log.error("DateConvertEditor getDate error:", e);
            return null;
        }
    }

    public static String getDateTime(Date date) {
        try {
            return datetimeFormat.format(date);
        } catch (Exception e) {
            log.error("DateConvertEditor getDateTime error:", e);
            return null;
        }
    }

    public static String getDate(Date date) {
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            log.error("DateConvertEditor getDate error:", e);
            return null;
        }
    }

    public static String getDateTime(Long date) {
        try {
            return datetimeFormat.format(date);
        } catch (Exception e) {
            log.error("DateConvertEditor getDateTime error:", e);
            return null;
        }
    }

    public static String getDate(Long date) {
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            log.error("DateConvertEditor getDate error:", e);
            return null;
        }
    }

}
