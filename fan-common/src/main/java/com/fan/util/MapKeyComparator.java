package com.fan.util;

import java.util.Comparator;

/**
 * @description:
 * @author: shil20
 * @date: 2018/7/24 14:00
 **/
public class MapKeyComparator implements Comparator<String> {

    @Override
    public int compare(String key1, String key2) {
        return key1.compareTo(key2);
    }

}
