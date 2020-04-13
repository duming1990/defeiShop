package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommTczhPrice;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
public interface CommTczhPriceService {

	Integer createCommTczhPrice(CommTczhPrice t);

	int modifyCommTczhPrice(CommTczhPrice t);

	int removeCommTczhPrice(CommTczhPrice t);

	CommTczhPrice getCommTczhPrice(CommTczhPrice t);

	List<CommTczhPrice> getCommTczhPriceList(CommTczhPrice t);

	Integer getCommTczhPriceCount(CommTczhPrice t);

	List<CommTczhPrice> getCommTczhPricePaginatedList(CommTczhPrice t);

	Integer createCommTczhPriceAndAttr(CommTczhPrice t);

	int modifyCommTczhPriceInventory(CommTczhPrice t);

	List<CommTczhPrice> getCommTczhJoinCommInfoList(CommTczhPrice t);

}