package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseFiles;

/**
 * @author Wu,Yang
 * @version 2013-07-02 11:42
 */
public interface BaseFilesService {

	Integer createBaseFiles(BaseFiles t);

	int modifyBaseFiles(BaseFiles t);

	int removeBaseFiles(BaseFiles t);

	BaseFiles getBaseFiles(BaseFiles t);

	List<BaseFiles> getBaseFilesList(BaseFiles t);

	Integer getBaseFilesCount(BaseFiles t);

	List<BaseFiles> getBaseFilesPaginatedList(BaseFiles t);

}