package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.RwYhqInfo;

/**
 * @author Wu,Yang
 * @version 2016-06-16 18:18
 */
public interface RwYhqInfoDao extends EntityDao<RwYhqInfo> {
	RwYhqInfo selectRwYhqInfoStatisticaSum(RwYhqInfo t);

}
