package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.PoorInfoDao;
import com.ebiz.webapp.domain.PoorInfo;

/**
 * @author Wu,Yang
 * @version 2018-01-20 15:08
 */
@Service
public class PoorInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<PoorInfo> implements PoorInfoDao {
	@Override
	@SuppressWarnings("unchecked")
	public List<PoorInfo> selectPoorInfoListWithAidMoney(PoorInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectPoorInfoListWithAidMoney", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoorInfo> selectPoorMoneyReport(PoorInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectPoorMoneyReport", t);
	}

	@Override
	public Integer selectVillageManagerPoorInfoCount(PoorInfo t) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectVillageManagerPoorInfoCount", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PoorInfo> selectVillageManagerPoorInfoPaginatedList(PoorInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectVillageManagerPoorInfoPaginatedList", t);
	}

	@Override
	public List<PoorInfo> selectPoorInfoListSortByCommCount(PoorInfo t) {
		return super.getSqlMapClientTemplate().queryForList("selectPoorInfoListSortByCommCount", t);
	}
}
