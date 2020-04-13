package com.ebiz.webapp.service;

import java.util.List;

import com.ebiz.webapp.domain.CommentInfoSon;

/**
 * @author Wu,Yang
 * @version 2017-05-08 17:24
 */
public interface CommentInfoSonService {

	Integer createCommentInfoSon(CommentInfoSon t);

	int modifyCommentInfoSon(CommentInfoSon t);

	int removeCommentInfoSon(CommentInfoSon t);

	CommentInfoSon getCommentInfoSon(CommentInfoSon t);

	List<CommentInfoSon> getCommentInfoSonList(CommentInfoSon t);

	Integer getCommentInfoSonCount(CommentInfoSon t);

	List<CommentInfoSon> getCommentInfoSonPaginatedList(CommentInfoSon t);

}