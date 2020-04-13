package com.ebiz.webapp.dao.ibatis;

import org.springframework.stereotype.Service;

import com.ebiz.ssi.dao.ibatis.EntityDaoSqlMapImpl;
import com.ebiz.webapp.dao.CommentInfoDao;
import com.ebiz.webapp.domain.CommentInfo;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
@Service
public class CommentInfoDaoSqlMapImpl extends EntityDaoSqlMapImpl<CommentInfo> implements CommentInfoDao {

	public Integer selectCommentInfoAvgCommSore(CommentInfo commentInfo) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCommentInfoAvgCommSore", commentInfo);
	}

	public Integer selectCommentInfoSumCommSore(CommentInfo commentInfo) {
		return (Integer) super.getSqlMapClientTemplate().queryForObject("selectCommentInfoSumCommSore", commentInfo);
	}

}
