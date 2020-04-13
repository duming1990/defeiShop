package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.NewsInfo;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
public interface NewsInfoService {

	Integer createNewsInfo(NewsInfo t);

	int modifyNewsInfo(NewsInfo t);

	int removeNewsInfo(NewsInfo t);

	NewsInfo getNewsInfo(NewsInfo t);

	List<NewsInfo> getNewsInfoList(NewsInfo t);

	Integer getNewsInfoCount(NewsInfo t);

	List<NewsInfo> getNewsInfoPaginatedList(NewsInfo t);

	List<NewsInfo> getNewsInfoListForRank(NewsInfo t);

	List<NewsInfo> getNewsInfoListForTopicPpt(NewsInfo t);

}