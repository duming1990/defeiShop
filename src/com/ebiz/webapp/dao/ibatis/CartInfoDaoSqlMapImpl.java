package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CartInfoDao;
import com.ebiz.webapp.domain.CartInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class CartInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<CartInfo> implements CartInfoDao {

	@SuppressWarnings("unchecked")
	public List<CartInfo> selectFreIdCartInfoGroupByEntp(CartInfo t) {
		return this.getSqlMapClientTemplate().queryForList("selectFreIdCartInfoGroupByEntp", t);
	}

	@SuppressWarnings("unchecked")
	public CartInfo selectCartInfoCountForSumCount(CartInfo t) {
		return (CartInfo) this.getSqlMapClientTemplate().queryForObject("selectCartInfoCountForSumCount", t);
	}
}
