package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.CommTczhAttribute;

/**
 * @author Wu,Yang
 * @version 2014-05-21 10:41
 */
public interface CommTczhAttributeDao extends EntityDao<CommTczhAttribute> {
	public CommTczhAttribute selectCommTczhAttributeForGetCommTczhId(CommTczhAttribute t);
}
