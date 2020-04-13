package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.CommInfoTags;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
public interface CommInfoTagsDao extends EntityDao<CommInfoTags> {

	Integer selectCommInfoTagsJiaZaiCount(CommInfoTags t);

	List<CommInfoTags> selectCommInfoTagsJiaZaiPaginatedList(CommInfoTags t);

}
