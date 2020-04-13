package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.NewsAttachmentDao;
import com.ebiz.webapp.domain.NewsAttachment;

/**
 * @author Wu,Yang
 * @version 2011-11-22 16:51
 */
@Service
public class NewsAttachmentDaoSqlMapImpl extends EntityDaoSqlMapImpl<NewsAttachment> implements NewsAttachmentDao {

}
