package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageMemberDao;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageMemberDaoSqlMapImpl extends EntityDaoSqlMapImpl<VillageMember> implements VillageMemberDao {
	public Integer selectVillageMemberCountByPIndex(VillageMember t){
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectVillageMemberCountByPIndex", t);
	}
}
