package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseFilesDao;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.service.BaseFilesService;

/**
 * @author Wu,Yang
 * @version 2013-07-02 11:42
 */
@Service
public class BaseFilesServiceImpl implements BaseFilesService {

	@Resource
	private BaseFilesDao baseFilesDao;

	public Integer createBaseFiles(BaseFiles t) {
		return this.baseFilesDao.insertEntity(t);
	}

	public BaseFiles getBaseFiles(BaseFiles t) {
		return this.baseFilesDao.selectEntity(t);
	}

	public Integer getBaseFilesCount(BaseFiles t) {
		return this.baseFilesDao.selectEntityCount(t);
	}

	public List<BaseFiles> getBaseFilesList(BaseFiles t) {
		return this.baseFilesDao.selectEntityList(t);
	}

	public int modifyBaseFiles(BaseFiles t) {
		return this.baseFilesDao.updateEntity(t);
	}

	public int removeBaseFiles(BaseFiles t) {
		return this.baseFilesDao.deleteEntity(t);
	}

	public List<BaseFiles> getBaseFilesPaginatedList(BaseFiles t) {
		return this.baseFilesDao.selectEntityPaginatedList(t);
	}

}
