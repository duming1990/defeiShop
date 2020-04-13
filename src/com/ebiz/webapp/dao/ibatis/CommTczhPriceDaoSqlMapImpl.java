package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.domain.CommTczhPrice;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
@Service
public class CommTczhPriceDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommTczhPrice> implements CommTczhPriceDao {
	public int updateCommTczhPriceInventory(CommTczhPrice t) {
		return super.getSqlMapClientTemplate().update("updateCommTczhPriceInventory", t);
	}

	@SuppressWarnings("unchecked")
	public List<CommTczhPrice> selectCommTczhJoinCommInfoList(CommTczhPrice t) {
		return super.getSqlMapClientTemplate().queryForList("selectCommTczhJoinCommInfoList", t);
	}

}
