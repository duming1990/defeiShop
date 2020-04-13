package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.BaseProvinceDao;
import com.ebiz.webapp.domain.BaseProvince;

/**
 * @author Wu,Yang
 * @version 2011-04-20 15:36
 */
@Service
public class BaseProvinceDaoSqlMapImpl extends EntityDaoSqlMapImpl<BaseProvince> implements BaseProvinceDao {

	@SuppressWarnings("unchecked")
	public List<BaseProvince> selectBaseProvinceResultForGroupByPalpha(BaseProvince t) {
		return this.getSqlMapClientTemplate().queryForList("selectBaseProvinceGroupByPalpha", t);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseProvince> selectBaseProvinceHasWeiHuList(BaseProvince t) {
		return this.getSqlMapClientTemplate().queryForList("selectBaseProvinceHasWeiHuList", t);
	}
}
