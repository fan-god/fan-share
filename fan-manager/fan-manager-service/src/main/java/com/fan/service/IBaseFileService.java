package com.fan.service;


import com.fan.entity.BaseFile;

import java.util.List;

public interface IBaseFileService {

	int insertSelective(BaseFile baseFile);
	BaseFile selectByPrimaryKey(Integer id);
	List<BaseFile> getAllFile();
	List<BaseFile> getFileByStat(String stat);
}
