package com.fan.service;


import com.fan.dao.BaseFileMapper;
import com.fan.entity.BaseFile;
import com.fan.interfaces.IBaseFileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseFileServiceImpl implements IBaseFileService {

	@Autowired
	private BaseFileMapper bfm;

	@Override
	public int insertSelective(BaseFile baseFile) {
		return bfm.insert(baseFile);
	}

	@Override
	public List<BaseFile> getAllFile() {
		return bfm.getAllFile();
	}

	@Override
	public BaseFile selectByPrimaryKey(Integer id) {
		return bfm.selectByPrimaryKey(id);
	}

	@Override
	public List<BaseFile> getFileByStat(String stat) {
		return bfm.getFileByStat(stat);
	}
}
