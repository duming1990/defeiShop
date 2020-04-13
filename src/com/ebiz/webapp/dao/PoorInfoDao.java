package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.PoorInfo;

/**
 * @author Wu,Yang
 * @version 2018-01-20 15:08
 */
public interface PoorInfoDao extends EntityDao<PoorInfo> {

	List<PoorInfo> selectPoorInfoListWithAidMoney(PoorInfo t);

	List<PoorInfo> selectPoorMoneyReport(PoorInfo t);

	Integer selectVillageManagerPoorInfoCount(PoorInfo t);

	List<PoorInfo> selectVillageManagerPoorInfoPaginatedList(PoorInfo t);

	List<PoorInfo> selectPoorInfoListSortByCommCount(PoorInfo t);
}
