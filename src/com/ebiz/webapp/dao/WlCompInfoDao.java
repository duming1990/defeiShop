package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.WlCompInfo;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public interface WlCompInfoDao extends EntityDao<WlCompInfo> {
	public List<WlCompInfo> selectWlCompInfoGroupByPalpha(WlCompInfo t);
}
