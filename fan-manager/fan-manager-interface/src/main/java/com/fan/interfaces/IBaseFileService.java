package com.fan.interfaces;


import com.fan.entity.BaseFile;
import org.springframework.stereotype.Service;

import java.util.List;
public interface IBaseFileService {

	int insertSelective(BaseFile baseFile);
	BaseFile selectByPrimaryKey(Integer id);
	List<BaseFile> getAllFile();
	List<BaseFile> getFileByStat(String stat);
}
