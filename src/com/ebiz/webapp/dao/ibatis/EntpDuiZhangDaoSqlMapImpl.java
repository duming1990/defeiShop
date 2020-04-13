package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.EntpDuiZhangDao;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;

/**
 * @author Wu,Yang
 * @version 2018-06-12 12:25
 */
@Service
public class EntpDuiZhangDaoSqlMapImpl extends EntityDaoSqlMapImpl<EntpDuiZhang> implements EntpDuiZhangDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<EntpDuiZhang> selectSettlementReport(EntpDuiZhang t){
		return super.getSqlMapClientTemplate().queryForList("selectSettlementReport", t);
	}
}
