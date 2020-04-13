package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.YhqInfoSonDao;
import com.ebiz.webapp.domain.YhqInfoSon;
import com.ebiz.webapp.service.YhqInfoSonService;

/**
 * @author Wu,Yang
 * @version 2017-04-19 17:50
 */
@Service
public class YhqInfoSonServiceImpl implements YhqInfoSonService {

	@Resource
	private YhqInfoSonDao yhqInfoSonDao;
	

	public Integer createYhqInfoSon(YhqInfoSon t) {
		return this.yhqInfoSonDao.insertEntity(t);
	}

	public YhqInfoSon getYhqInfoSon(YhqInfoSon t) {
		return this.yhqInfoSonDao.selectEntity(t);
	}

	public Integer getYhqInfoSonCount(YhqInfoSon t) {
		return this.yhqInfoSonDao.selectEntityCount(t);
	}

	public List<YhqInfoSon> getYhqInfoSonList(YhqInfoSon t) {
		return this.yhqInfoSonDao.selectEntityList(t);
	}

	public int modifyYhqInfoSon(YhqInfoSon t) {
		return this.yhqInfoSonDao.updateEntity(t);
	}

	public int removeYhqInfoSon(YhqInfoSon t) {
		return this.yhqInfoSonDao.deleteEntity(t);
	}

	public List<YhqInfoSon> getYhqInfoSonPaginatedList(YhqInfoSon t) {
		return this.yhqInfoSonDao.selectEntityPaginatedList(t);
	}

}
