package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.domain.EntpInfo;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
@Service
public class EntpInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<EntpInfo> implements EntpInfoDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<EntpInfo> selectEntpInfoDistance(EntpInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectEntpInfoDistance", t);
	}
}
