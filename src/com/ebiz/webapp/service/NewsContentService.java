package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.NewsContent;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
public interface NewsContentService {

	Integer createNewsContent(NewsContent t);

	int modifyNewsContent(NewsContent t);

	int removeNewsContent(NewsContent t);

	NewsContent getNewsContent(NewsContent t);

	List<NewsContent> getNewsContentList(NewsContent t);

	Integer getNewsContentCount(NewsContent t);

	List<NewsContent> getNewsContentPaginatedList(NewsContent t);

}