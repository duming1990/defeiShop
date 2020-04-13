package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseContentDao;
import com.ebiz.webapp.domain.BaseContent;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
@Service
public class BaseContentDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseContent> implements BaseContentDao {

	/**
	 * @author Cheng,JiaRui
	 * @version 2012-06-04
	 */
	public int deleteBaseContentByLinkId(BaseContent t) {
		return super.getSqlMapClientTemplate().update("deleteBaseContentByLinkId", t);
	}
}
