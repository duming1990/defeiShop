package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.NewsAttachmentDao;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.service.NewsAttachmentService;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
@Service
public class NewsAttachmentServiceImpl implements NewsAttachmentService {

	@Resource
	private NewsAttachmentDao newsAttachmentDao;

	public Integer createNewsAttachment(NewsAttachment t) {
		return this.newsAttachmentDao.insertEntity(t);
	}

	public NewsAttachment getNewsAttachment(NewsAttachment t) {
		return this.newsAttachmentDao.selectEntity(t);
	}

	public Integer getNewsAttachmentCount(NewsAttachment t) {
		return this.newsAttachmentDao.selectEntityCount(t);
	}

	public List<NewsAttachment> getNewsAttachmentList(NewsAttachment t) {
		return this.newsAttachmentDao.selectEntityList(t);
	}

	public int modifyNewsAttachment(NewsAttachment t) {
		return this.newsAttachmentDao.updateEntity(t);
	}

	public int removeNewsAttachment(NewsAttachment t) {
		return this.newsAttachmentDao.deleteEntity(t);
	}

	public List<NewsAttachment> getNewsAttachmentPaginatedList(NewsAttachment t) {
		return this.newsAttachmentDao.selectEntityPaginatedList(t);
	}

}
