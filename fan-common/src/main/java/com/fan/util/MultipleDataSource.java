package com.fan.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;


/**
* @ClassName: MultipleDataSource
* @Description:  该类用于切换多个mysql数据源 
* @author boway
* @date 2018年9月11日
*
*/  
public class MultipleDataSource extends AbstractRoutingDataSource {
	@Override
    protected Object determineCurrentLookupKey() {
        // 从自定义的位置获取数据源标识
        return DbContextHolder.getDataSource();
    }
}
