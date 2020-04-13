package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.NewsContentDao;
import com.ebiz.webapp.domain.NewsContent;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
@Service
public class NewsContentDaoSqlMapImpl extends EntityDaoSqlMapImpl<NewsContent> implements NewsContentDao {

}
