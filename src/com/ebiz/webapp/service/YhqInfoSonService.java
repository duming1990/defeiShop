package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.YhqInfoSon;

/**
 * @author Wu,Yang
 * @version 2017-04-19 17:50
 */
public interface YhqInfoSonService {

	Integer createYhqInfoSon(YhqInfoSon t);

	int modifyYhqInfoSon(YhqInfoSon t);

	int removeYhqInfoSon(YhqInfoSon t);

	YhqInfoSon getYhqInfoSon(YhqInfoSon t);

	List<YhqInfoSon> getYhqInfoSonList(YhqInfoSon t);

	Integer getYhqInfoSonCount(YhqInfoSon t);

	List<YhqInfoSon> getYhqInfoSonPaginatedList(YhqInfoSon t);

}