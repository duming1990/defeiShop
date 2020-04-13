package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CartInfoDao;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.service.CartInfoService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
@Service
public class CartInfoServiceImpl implements CartInfoService {

	@Resource
	private CartInfoDao cartInfoDao;

	public Integer createCartInfo(CartInfo t) {
		return this.cartInfoDao.insertEntity(t);
	}

	public CartInfo getCartInfo(CartInfo t) {
		return this.cartInfoDao.selectEntity(t);
	}

	public Integer getCartInfoCount(CartInfo t) {
		return this.cartInfoDao.selectEntityCount(t);
	}

	public List<CartInfo> getCartInfoList(CartInfo t) {
		return this.cartInfoDao.selectEntityList(t);
	}

	public int modifyCartInfo(CartInfo t) {

		int count = this.cartInfoDao.updateEntity(t);

		// 删除购物车里面pdCount <=0
		if (null != t.getMap().get("modify_remove_pd_count_le_0")) {
			CartInfo cartInfoUpdate = new CartInfo();
			cartInfoUpdate.setUser_id(Integer.valueOf(t.getMap().get("user_id").toString()));
			cartInfoUpdate.getMap().put("remove_pd_count_le_0", true);
			this.cartInfoDao.deleteEntity(cartInfoUpdate);
		}

		return count;
	}

	public int removeCartInfo(CartInfo t) {
		return this.cartInfoDao.deleteEntity(t);
	}

	public List<CartInfo> getCartInfoPaginatedList(CartInfo t) {
		return this.cartInfoDao.selectEntityPaginatedList(t);
	}

	public List<CartInfo> getFreIdCartInfoGroupByEntp(CartInfo t) {
		return this.cartInfoDao.selectFreIdCartInfoGroupByEntp(t);
	}

	public CartInfo getCartInfoCountForSumCount(CartInfo t) {
		return this.cartInfoDao.selectCartInfoCountForSumCount(t);
	}
}
