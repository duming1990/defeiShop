package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.DiscountStoresDao;
import com.ebiz.webapp.domain.DiscountStores;
import com.ebiz.webapp.service.DiscountStoresService;

/**
 * @author Wu,Yang
 * @version 2014-08-08 09:55
 */
@Service
public class DiscountStoresServiceImpl implements DiscountStoresService {

	@Resource
	private DiscountStoresDao discountStoresDao;

	public Integer createDiscountStores(DiscountStores t) {
		return this.discountStoresDao.insertEntity(t);
	}

	public DiscountStores getDiscountStores(DiscountStores t) {
		return this.discountStoresDao.selectEntity(t);
	}

	public Integer getDiscountStoresCount(DiscountStores t) {
		return this.discountStoresDao.selectEntityCount(t);
	}

	public List<DiscountStores> getDiscountStoresList(DiscountStores t) {
		return this.discountStoresDao.selectEntityList(t);
	}

	public int modifyDiscountStores(DiscountStores t) {
		return this.discountStoresDao.updateEntity(t);
	}

	public int removeDiscountStores(DiscountStores t) {
		return this.discountStoresDao.deleteEntity(t);
	}

	public List<DiscountStores> getDiscountStoresPaginatedList(DiscountStores t) {
		return this.discountStoresDao.selectEntityPaginatedList(t);
	}

}
