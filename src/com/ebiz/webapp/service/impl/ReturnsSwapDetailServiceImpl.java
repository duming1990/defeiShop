package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ReturnsSwapDetailDao;
import com.ebiz.webapp.domain.ReturnsSwapDetail;
import com.ebiz.webapp.service.ReturnsSwapDetailService;

/**
 * @version 2014-06-20 09:48
 */
@Service
public class ReturnsSwapDetailServiceImpl implements ReturnsSwapDetailService {

	@Resource
	private ReturnsSwapDetailDao returnsSwapDetailDao;

	public Integer createReturnsSwapDetail(ReturnsSwapDetail t) {
		return this.returnsSwapDetailDao.insertEntity(t);
	}

	public ReturnsSwapDetail getReturnsSwapDetail(ReturnsSwapDetail t) {
		return this.returnsSwapDetailDao.selectEntity(t);
	}

	public Integer getReturnsSwapDetailCount(ReturnsSwapDetail t) {
		return this.returnsSwapDetailDao.selectEntityCount(t);
	}

	public List<ReturnsSwapDetail> getReturnsSwapDetailList(ReturnsSwapDetail t) {
		return this.returnsSwapDetailDao.selectEntityList(t);
	}

	public int modifyReturnsSwapDetail(ReturnsSwapDetail t) {
		return this.returnsSwapDetailDao.updateEntity(t);
	}

	public int removeReturnsSwapDetail(ReturnsSwapDetail t) {
		return this.returnsSwapDetailDao.deleteEntity(t);
	}

	public List<ReturnsSwapDetail> getReturnsSwapDetailPaginatedList(ReturnsSwapDetail t) {
		return this.returnsSwapDetailDao.selectEntityPaginatedList(t);
	}

}
