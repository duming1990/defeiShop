package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.NewsContentDao;
import com.ebiz.webapp.domain.NewsContent;
import com.ebiz.webapp.service.NewsContentService;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
@Service
public class NewsContentServiceImpl implements NewsContentService {

	@Resource
	private NewsContentDao newsContentDao;

	public Integer createNewsContent(NewsContent t) {
		return this.newsContentDao.insertEntity(t);
	}

	public NewsContent getNewsContent(NewsContent t) {
		return this.newsContentDao.selectEntity(t);
	}

	public Integer getNewsContentCount(NewsContent t) {
		return this.newsContentDao.selectEntityCount(t);
	}

	public List<NewsContent> getNewsContentList(NewsContent t) {
		return this.newsContentDao.selectEntityList(t);
	}

	public int modifyNewsContent(NewsContent t) {
		return this.newsContentDao.updateEntity(t);
	}

	public int removeNewsContent(NewsContent t) {
		return this.newsContentDao.deleteEntity(t);
	}

	public List<NewsContent> getNewsContentPaginatedList(NewsContent t) {
		return this.newsContentDao.selectEntityPaginatedList(t);
	}

}
