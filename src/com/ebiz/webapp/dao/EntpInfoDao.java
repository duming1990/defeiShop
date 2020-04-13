package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.EntpInfo;

/**
 * @author Wu,Yang
 * @version 2012-03-21 17:16
 */
public interface EntpInfoDao extends EntityDao<EntpInfo> {
	List<EntpInfo> selectEntpInfoDistance(EntpInfo t);
}
