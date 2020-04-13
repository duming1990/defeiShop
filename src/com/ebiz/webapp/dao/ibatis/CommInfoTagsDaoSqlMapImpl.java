package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CommInfoTagsDao;
import com.ebiz.webapp.domain.CommInfoTags;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
@Service
public class CommInfoTagsDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommInfoTags> implements CommInfoTagsDao {
	@Override
	public Integer selectCommInfoTagsJiaZaiCount(CommInfoTags t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCommInfoTagsJiaZaiCount", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CommInfoTags> selectCommInfoTagsJiaZaiPaginatedList(CommInfoTags t) {
		return this.getSqlMapClientTemplate().queryForList("selectCommInfoTagsJiaZaiPaginatedList", t);
	}
}
