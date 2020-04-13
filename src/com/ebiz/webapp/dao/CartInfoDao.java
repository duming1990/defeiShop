package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.CartInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public interface CartInfoDao extends EntityDao<CartInfo> {

	public List<CartInfo> selectFreIdCartInfoGroupByEntp(CartInfo t);

	public CartInfo selectCartInfoCountForSumCount(CartInfo t);

}
