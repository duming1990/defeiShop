package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.domain.Tongji;
import com.ebiz.webapp.service.TongjiService;

/**
 * @author Wu,Yang
 * @version 2015-12-23 17:15
 */
@Service
public class TongjiServiceImpl implements TongjiService {

	@Resource
	private TongjiDao tongjiDao;
	

	public Integer createTongji(Tongji t) {
		return this.tongjiDao.insertEntity(t);
	}

	public Tongji getTongji(Tongji t) {
		return this.tongjiDao.selectEntity(t);
	}

	public Integer getTongjiCount(Tongji t) {
		return this.tongjiDao.selectEntityCount(t);
	}

	public List<Tongji> getTongjiList(Tongji t) {
		return this.tongjiDao.selectEntityList(t);
	}

	public int modifyTongji(Tongji t) {
		return this.tongjiDao.updateEntity(t);
	}

	public int removeTongji(Tongji t) {
		return this.tongjiDao.deleteEntity(t);
	}

	public List<Tongji> getTongjiPaginatedList(Tongji t) {
		return this.tongjiDao.selectEntityPaginatedList(t);
	}

}
