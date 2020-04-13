package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.BaseComminfoTags;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
public interface BaseComminfoTagsDao extends EntityDao<BaseComminfoTags> {
	List<BaseComminfoTags> selectBaseComminfoTagsPaginatedListByPIndex(BaseComminfoTags t);
}
