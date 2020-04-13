package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.BaseContent;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
public interface BaseContentDao extends EntityDao<BaseContent> {

	/**
	 * @author Cheng,JiaRui
	 * @version 2012-06-04
	 */
	public int deleteBaseContentByLinkId(BaseContent t);
}
