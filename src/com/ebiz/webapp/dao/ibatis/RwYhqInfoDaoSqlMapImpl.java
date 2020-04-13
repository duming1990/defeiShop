package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.RwYhqInfoDao;
import com.ebiz.webapp.domain.RwYhqInfo;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
@Service
public class RwYhqInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<RwYhqInfo> implements RwYhqInfoDao {
	@Override
	public RwYhqInfo selectRwYhqInfoStatisticaSum(RwYhqInfo t) {
		return (RwYhqInfo) super.getSqlMapClientTemplate().queryForObject("selectRwYhqInfoStatisticaSum", t);
	}
}
