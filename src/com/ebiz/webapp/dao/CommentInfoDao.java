package com.ebiz.webapp.dao;

import com.ebiz.ssi.dao.EntityDao;
import com.ebiz.webapp.domain.CommentInfo;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
public interface CommentInfoDao extends EntityDao<CommentInfo> {
	public Integer selectCommentInfoAvgCommSore(CommentInfo commentInfo);

	public Integer selectCommentInfoSumCommSore(CommentInfo commentInfo);
}
