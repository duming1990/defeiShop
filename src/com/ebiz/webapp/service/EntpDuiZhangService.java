package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.EntpDuiZhang;

/**
 * @author Wu,Yang
 * @version 2018-06-12 12:25
 */
public interface EntpDuiZhangService {

	Integer createEntpDuiZhang(EntpDuiZhang t);

	int modifyEntpDuiZhang(EntpDuiZhang t);

	int removeEntpDuiZhang(EntpDuiZhang t);

	EntpDuiZhang getEntpDuiZhang(EntpDuiZhang t);

	List<EntpDuiZhang> getEntpDuiZhangList(EntpDuiZhang t);

	Integer getEntpDuiZhangCount(EntpDuiZhang t);

	List<EntpDuiZhang> getEntpDuiZhangPaginatedList(EntpDuiZhang t);
	
	List<EntpDuiZhang> getSettlementReport(EntpDuiZhang t);

}