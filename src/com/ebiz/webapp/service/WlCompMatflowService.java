package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.WlCompMatflow;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:54
 */
public interface WlCompMatflowService {

	Integer createWlCompMatflow(WlCompMatflow t);

	int modifyWlCompMatflow(WlCompMatflow t);

	int removeWlCompMatflow(WlCompMatflow t);

	WlCompMatflow getWlCompMatflow(WlCompMatflow t);

	List<WlCompMatflow> getWlCompMatflowList(WlCompMatflow t);

	Integer getWlCompMatflowCount(WlCompMatflow t);

	List<WlCompMatflow> getWlCompMatflowPaginatedList(WlCompMatflow t);

}