package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.ReturnsSwapDetail;

/**
 * @version 2014-06-20 09:48
 */
public interface ReturnsSwapDetailService {

	Integer createReturnsSwapDetail(ReturnsSwapDetail t);

	int modifyReturnsSwapDetail(ReturnsSwapDetail t);

	int removeReturnsSwapDetail(ReturnsSwapDetail t);

	ReturnsSwapDetail getReturnsSwapDetail(ReturnsSwapDetail t);

	List<ReturnsSwapDetail> getReturnsSwapDetailList(ReturnsSwapDetail t);

	Integer getReturnsSwapDetailCount(ReturnsSwapDetail t);

	List<ReturnsSwapDetail> getReturnsSwapDetailPaginatedList(ReturnsSwapDetail t);

}