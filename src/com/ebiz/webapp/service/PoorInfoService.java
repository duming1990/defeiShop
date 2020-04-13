package com.ebiz.webapp.service;

import java.math.BigDecimal;
import java.util.List;

import com.ebiz.webapp.domain.PoorInfo;

/**
 * @author Wu,Yang
 * @version 2018-01-20 15:08
 */
public interface PoorInfoService {

	Integer createPoorInfo(PoorInfo t);

	Integer createImportPoorInfo(PoorInfo t);

	int modifyPoorInfo(PoorInfo t);

	int removePoorInfo(PoorInfo t);

	PoorInfo getPoorInfo(PoorInfo t);

	List<PoorInfo> getPoorInfoList(PoorInfo t);

	Integer getPoorInfoCount(PoorInfo t);

	List<PoorInfo> getPoorInfoPaginatedList(PoorInfo t);

	List<PoorInfo> getPoorInfoListWithAidMoney(PoorInfo t);

	List<PoorInfo> getPoorMoneyReport(PoorInfo t);

	int createPoorInfoAid(Integer user_id, Integer bi_get_type, BigDecimal money, String entp_name, String add_user_name);

	Integer getVillageManagerPoorInfoCount(PoorInfo t);

	List<PoorInfo> getVillageManagerPoorInfoPaginatedList(PoorInfo t);

	List<PoorInfo> getPoorInfoListSortByCommCount(PoorInfo entity);
}