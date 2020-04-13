package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.DiscountStores;

/**
 * @author Wu,Yang
 * @version 2014-08-08 09:55
 */
public interface DiscountStoresService {

	Integer createDiscountStores(DiscountStores t);

	int modifyDiscountStores(DiscountStores t);

	int removeDiscountStores(DiscountStores t);

	DiscountStores getDiscountStores(DiscountStores t);

	List<DiscountStores> getDiscountStoresList(DiscountStores t);

	Integer getDiscountStoresCount(DiscountStores t);

	List<DiscountStores> getDiscountStoresPaginatedList(DiscountStores t);

}