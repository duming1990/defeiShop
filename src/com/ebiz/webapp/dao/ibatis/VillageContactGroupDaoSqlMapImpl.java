package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.VillageContactGroupDao;
import com.ebiz.webapp.domain.VillageContactGroup;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageContactGroupDaoSqlMapImpl extends EntityDaoSqlMapImpl<VillageContactGroup> implements
		VillageContactGroupDao {

	@SuppressWarnings("unchecked")
	public List<VillageContactGroup> selectVillageContactGroupNameAndCount(VillageContactGroup t) {
		return this.getSqlMapClientTemplate().queryForList("selectVillageContactGroupNameAndCount", t);
	}
}
