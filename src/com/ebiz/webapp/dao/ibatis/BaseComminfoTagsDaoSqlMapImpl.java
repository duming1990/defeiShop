package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseComminfoTagsDao;
import com.ebiz.webapp.domain.BaseComminfoTags;

/**
 * @author Wu,Yang
 * @version 2017-04-21 14:39
 */
@Service
public class BaseComminfoTagsDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseComminfoTags> implements BaseComminfoTagsDao {
	@SuppressWarnings("unchecked")
	public List<BaseComminfoTags> selectBaseComminfoTagsPaginatedListByPIndex(BaseComminfoTags t) {
		return this.getSqlMapClientTemplate().queryForList("selectBaseComminfoTagsPaginatedListByPIndex", t);
	}
}
