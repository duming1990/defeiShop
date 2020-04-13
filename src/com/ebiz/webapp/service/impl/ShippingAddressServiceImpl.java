package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ShippingAddressDao;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.service.ShippingAddressService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class ShippingAddressServiceImpl implements ShippingAddressService {

	@Resource
	private ShippingAddressDao shippingAddressDao;

	public Integer createShippingAddress(ShippingAddress t) {
		return this.shippingAddressDao.insertEntity(t);
	}

	public ShippingAddress getShippingAddress(ShippingAddress t) {
		return this.shippingAddressDao.selectEntity(t);
	}

	public Integer getShippingAddressCount(ShippingAddress t) {
		return this.shippingAddressDao.selectEntityCount(t);
	}

	public List<ShippingAddress> getShippingAddressList(ShippingAddress t) {
		return this.shippingAddressDao.selectEntityList(t);
	}

	public int modifyShippingAddress(ShippingAddress t) {
		return this.shippingAddressDao.updateEntity(t);
	}

	public int removeShippingAddress(ShippingAddress t) {
		return this.shippingAddressDao.deleteEntity(t);
	}

	public List<ShippingAddress> getShippingAddressPaginatedList(ShippingAddress t) {
		return this.shippingAddressDao.selectEntityPaginatedList(t);
	}

}
