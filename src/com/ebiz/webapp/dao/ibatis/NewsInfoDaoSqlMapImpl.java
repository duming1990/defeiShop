package com.ebiz.webapp.dao.ibatis;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.NewsInfoDao;
import com.ebiz.webapp.domain.NewsInfo;

/**
 * @author Wu,Yang
 * @version 2011-11-23 09:33
 */
@Service
public class NewsInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<NewsInfo> implements NewsInfoDao {

	@SuppressWarnings("unchecked")
	public List<NewsInfo> selectNewsInfoListForRank(NewsInfo newsInfo) throws DataAccessException {
		return super.getSqlMapClientTemplate().queryForList("selectNewsInfoListForRank", newsInfo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NewsInfo> selectNewsInfoListForTopicPpt(NewsInfo newsInfo) {
		return super.getSqlMapClientTemplate().queryForList("selectNewsInfoListForTopicPpt", newsInfo);
	}

}
