package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommInfo;

/**
 * @author Wu,Yang
 * @version 2013-09-25 20:27
 */
public interface CommInfoService {

	Integer createCommInfo(CommInfo t);

	int modifyCommInfo(CommInfo t);

	int removeCommInfo(CommInfo t);

	CommInfo getCommInfo(CommInfo t);

	List<CommInfo> getCommInfoList(CommInfo t);

	Integer getCommInfoCount(CommInfo t);

	List<CommInfo> getCommInfoPaginatedList(CommInfo t);

	List<CommInfo> getCommInfoForDistinct(CommInfo t);

	List<CommInfo> getCommInfoPageList(CommInfo t);

	/**
	 * @author Li,Ka
	 * @version 2012-04-28
	 * @desc 根据属性组合查询
	 */
	Integer getCommInfoForSearchAttrCount(CommInfo t);

	/**
	 * @author Li,Ka
	 * @version 2012-04-28
	 * @desc 根据属性组合查询
	 */
	Integer getCommInfoNameCount(CommInfo t);

	/**
	 * @author Li,Ka
	 * @version 2012-04-28
	 * @desc 根据属性组合查询分页
	 */
	List<CommInfo> getCommInfoForSearchAttrPaginatedList(CommInfo t);

	/**
	 * @author Myg
	 * @version 2013-11-05
	 * @desc 将最小套餐价格存入商品表的min_price表中
	 */
	int modifyCommInfoMinPrice(CommInfo t);

	int modifyCommInfoInventory(CommInfo t);

	List<CommInfo> getCommInfoListTwo(CommInfo t);

	Integer getClassRankingListCount(CommInfo t);

	List<CommInfo> getClassRankingList(CommInfo t);

	List<CommInfo> getCommInfoRootClsId(CommInfo t);

	Integer getCommInfoCountForFuPin(CommInfo t);

	List<CommInfo> getCommInfoPaginatedListForFuPin(CommInfo t);

	List<CommInfo> getWelfareCommInfoList(CommInfo t);

	CommInfo getCommInfoByOrderId(Integer orderId);

}