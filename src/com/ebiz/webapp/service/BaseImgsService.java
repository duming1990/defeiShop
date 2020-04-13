package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.BaseImgs;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
public interface BaseImgsService {

	Integer createBaseImgs(BaseImgs t);

	Integer createIdCardBaseImgs(BaseImgs t);

	int modifyBaseImgs(BaseImgs t);

	int removeBaseImgs(BaseImgs t);

	BaseImgs getBaseImgs(BaseImgs t);

	List<BaseImgs> getBaseImgsList(BaseImgs t);

	Integer getBaseImgsCount(BaseImgs t);

	List<BaseImgs> getBaseImgsPaginatedList(BaseImgs t);

}