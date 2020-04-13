package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommentInfo;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
public interface CommentInfoService {

	Integer createCommentInfo(CommentInfo t);

	int modifyCommentInfo(CommentInfo t);

	int removeCommentInfo(CommentInfo t);

	CommentInfo getCommentInfo(CommentInfo t);

	List<CommentInfo> getCommentInfoList(CommentInfo t);

	Integer getCommentInfoCount(CommentInfo t);

	List<CommentInfo> getCommentInfoPaginatedList(CommentInfo t);

	public Integer getCommentInfoAvgCommSore(CommentInfo commentInfo);

	public Integer getCommentInfoSumCommSore(CommentInfo commentInfo);
}