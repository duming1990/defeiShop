package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.WlCompInfoDao;
import com.ebiz.webapp.domain.WlCompInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class WlCompInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<WlCompInfo> implements WlCompInfoDao {

	@SuppressWarnings("unchecked")
	public List<WlCompInfo> selectWlCompInfoGroupByPalpha(WlCompInfo t) {
		return this.getSqlMapClientTemplate().queryForList("selectWlCompInfoGroupByPalpha", t);
	}
}
