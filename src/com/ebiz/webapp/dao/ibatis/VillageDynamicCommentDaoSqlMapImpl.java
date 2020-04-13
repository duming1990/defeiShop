package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.VillageDynamicCommentDao;
import com.ebiz.webapp.domain.VillageDynamicComment;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageDynamicCommentDaoSqlMapImpl extends EntityDaoSqlMapImpl<VillageDynamicComment> implements
		VillageDynamicCommentDao {
}
