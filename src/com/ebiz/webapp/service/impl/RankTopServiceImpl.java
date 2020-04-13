package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.RankTopDao;
import com.ebiz.webapp.domain.RankTop;
import com.ebiz.webapp.service.RankTopService;

/**
 * @author Wu,Yang
 * @version 2014-09-22 11:54
 */
@Service
public class RankTopServiceImpl implements RankTopService {

	@Resource
	private RankTopDao rankTopDao;

	public Integer createRankTop(RankTop t) {
		return this.rankTopDao.insertEntity(t);
	}

	public RankTop getRankTop(RankTop t) {
		return this.rankTopDao.selectEntity(t);
	}

	public Integer getRankTopCount(RankTop t) {
		return this.rankTopDao.selectEntityCount(t);
	}

	public List<RankTop> getRankTopList(RankTop t) {
		return this.rankTopDao.selectEntityList(t);
	}

	public int modifyRankTop(RankTop t) {
		return this.rankTopDao.updateEntity(t);
	}

	public int removeRankTop(RankTop t) {
		return this.rankTopDao.deleteEntity(t);
	}

	public List<RankTop> getRankTopPaginatedList(RankTop t) {
		return this.rankTopDao.selectEntityPaginatedList(t);
	}

}
