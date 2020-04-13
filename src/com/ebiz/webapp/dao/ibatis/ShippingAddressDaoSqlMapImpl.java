package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.ShippingAddressDao;
import com.ebiz.webapp.domain.ShippingAddress;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class ShippingAddressDaoSqlMapImpl extends EntityDaoSqlMapImpl<ShippingAddress> implements ShippingAddressDao {

}
