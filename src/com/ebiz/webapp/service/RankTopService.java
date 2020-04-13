package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.RankTop;

/**
 * @author Wu,Yang
 * @version 2014-09-22 11:54
 */
public interface RankTopService {

	Integer createRankTop(RankTop t);

	int modifyRankTop(RankTop t);

	int removeRankTop(RankTop t);

	RankTop getRankTop(RankTop t);

	List<RankTop> getRankTopList(RankTop t);

	Integer getRankTopCount(RankTop t);

	List<RankTop> getRankTopPaginatedList(RankTop t);

}