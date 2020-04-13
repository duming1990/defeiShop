package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.Tongji;

/**
 * @author Wu,Yang
 * @version 2015-12-23 17:15
 */
public interface TongjiService {

	Integer createTongji(Tongji t);

	int modifyTongji(Tongji t);

	int removeTongji(Tongji t);

	Tongji getTongji(Tongji t);

	List<Tongji> getTongjiList(Tongji t);

	Integer getTongjiCount(Tongji t);

	List<Tongji> getTongjiPaginatedList(Tongji t);

}