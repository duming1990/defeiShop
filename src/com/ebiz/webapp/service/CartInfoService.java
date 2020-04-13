package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CartInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public interface CartInfoService {

	Integer createCartInfo(CartInfo t);

	int modifyCartInfo(CartInfo t);

	int removeCartInfo(CartInfo t);

	CartInfo getCartInfo(CartInfo t);

	List<CartInfo> getCartInfoList(CartInfo t);

	Integer getCartInfoCount(CartInfo t);

	List<CartInfo> getCartInfoPaginatedList(CartInfo t);

	List<CartInfo> getFreIdCartInfoGroupByEntp(CartInfo t);

	CartInfo getCartInfoCountForSumCount(CartInfo getTotalMoneyAndCount);

}