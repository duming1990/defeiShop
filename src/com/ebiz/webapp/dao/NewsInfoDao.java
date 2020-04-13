package com.ebiz.webapp.dao;

import java.util.List;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.NewsInfo;

/**
 * @author Wu,Yang
 * @version 2011-11-23 09:33
 */
public interface NewsInfoDao extends EntityDao<NewsInfo> {

	public List<NewsInfo> selectNewsInfoListForRank(NewsInfo newsInfo);

	public List<NewsInfo> selectNewsInfoListForTopicPpt(NewsInfo newsInfo);
}
