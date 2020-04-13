package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ShippingAddress;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
public interface ShippingAddressService {

	Integer createShippingAddress(ShippingAddress t);

	int modifyShippingAddress(ShippingAddress t);

	int removeShippingAddress(ShippingAddress t);

	ShippingAddress getShippingAddress(ShippingAddress t);

	List<ShippingAddress> getShippingAddressList(ShippingAddress t);

	Integer getShippingAddressCount(ShippingAddress t);

	List<ShippingAddress> getShippingAddressPaginatedList(ShippingAddress t);

}