package com.fan.util;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: shil20
 * @date: 2018/7/25 14:25
 **/
public class DateConverter implements Converter<String, Date> {

    private String pattern;

    public void setPattern(String pattern){
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

}
