package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.NewsAttachmentDao;
import com.ebiz.webapp.dao.NewsContentDao;
import com.ebiz.webapp.dao.NewsInfoDao;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.NewsContent;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.service.NewsInfoService;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
@Service
public class NewsInfoServiceImpl implements NewsInfoService {
	@Resource
	private NewsInfoDao newsInfoDao;

	@Resource
	private NewsContentDao newsContentDao;

	@Resource
	private NewsAttachmentDao newsAttachmentDao;

	public Integer createNewsInfo(NewsInfo t) {
		Integer id = this.newsInfoDao.insertEntity(t);

		NewsContent newsContent = new NewsContent();
		newsContent.setId(id);
		newsContent.setContent(t.getContent());
		this.newsContentDao.insertEntity(newsContent);

		List<NewsAttachment> webattachmentList = t.getNewsAttachmentList();
		if (null != webattachmentList) {
			for (NewsAttachment newsAttachment : webattachmentList) {
				newsAttachment.setLink_id(id);
				newsAttachment.setLink_tab("NEWS_INFO");
				newsAttachment.setIs_del(0);
				this.newsAttachmentDao.insertEntity(newsAttachment);
			}
		}
		return id;
	}

	public int modifyNewsInfo(NewsInfo t) {
		int id = this.newsInfoDao.updateEntity(t);

		String update_content = (String) t.getMap().get("update_content");
		if (StringUtils.isNotBlank(update_content)) {
			if (null != t.getContent()) {
				NewsContent newsContent = new NewsContent();
				newsContent.setId(t.getId());
				newsContent.setContent(t.getContent());
				this.newsContentDao.updateEntity(newsContent);
			}
		}

		String update_attachment = (String) t.getMap().get("update_attachment");
		if (StringUtils.isNotBlank(update_attachment)) {
			List<NewsAttachment> webattachmentList = t.getNewsAttachmentList();
			if (null != webattachmentList) {
				for (NewsAttachment webattachment : webattachmentList) {
					webattachment.setLink_id(t.getId());
					webattachment.setLink_tab("NEWS_INFO");
					webattachment.setIs_del(0);
					this.newsAttachmentDao.insertEntity(webattachment);
				}
			}
		}

		return id;
	}

	public int removeNewsInfo(NewsInfo t) {
		return this.newsInfoDao.deleteEntity(t);
	}

	public NewsInfo getNewsInfo(NewsInfo t) {
		return this.newsInfoDao.selectEntity(t);
	}

	public Integer getNewsInfoCount(NewsInfo t) {
		return this.newsInfoDao.selectEntityCount(t);
	}

	public List<NewsInfo> getNewsInfoList(NewsInfo t) {
		return this.newsInfoDao.selectEntityList(t);
	}

	public List<NewsInfo> getNewsInfoPaginatedList(NewsInfo t) {
		return this.newsInfoDao.selectEntityPaginatedList(t);
	}

	public List<NewsInfo> getNewsInfoListForRank(NewsInfo t) {
		return this.newsInfoDao.selectNewsInfoListForRank(t);
	}

	@Override
	public List<NewsInfo> getNewsInfoListForTopicPpt(NewsInfo t) {
		return this.newsInfoDao.selectNewsInfoListForTopicPpt(t);
	}

}
