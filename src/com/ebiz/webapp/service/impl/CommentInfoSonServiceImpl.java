package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.CommentInfoSonDao;
import com.ebiz.webapp.domain.CommentInfoSon;
import com.ebiz.webapp.service.CommentInfoSonService;

/**
 * @author Wu,Yang
 * @version 2017-05-08 17:24
 */
@Service
public class CommentInfoSonServiceImpl implements CommentInfoSonService {

	@Resource
	private CommentInfoSonDao commentInfoSonDao;
	

	public Integer createCommentInfoSon(CommentInfoSon t) {
		return this.commentInfoSonDao.insertEntity(t);
	}

	public CommentInfoSon getCommentInfoSon(CommentInfoSon t) {
		return this.commentInfoSonDao.selectEntity(t);
	}

	public Integer getCommentInfoSonCount(CommentInfoSon t) {
		return this.commentInfoSonDao.selectEntityCount(t);
	}

	public List<CommentInfoSon> getCommentInfoSonList(CommentInfoSon t) {
		return this.commentInfoSonDao.selectEntityList(t);
	}

	public int modifyCommentInfoSon(CommentInfoSon t) {
		return this.commentInfoSonDao.updateEntity(t);
	}

	public int removeCommentInfoSon(CommentInfoSon t) {
		return this.commentInfoSonDao.deleteEntity(t);
	}

	public List<CommentInfoSon> getCommentInfoSonPaginatedList(CommentInfoSon t) {
		return this.commentInfoSonDao.selectEntityPaginatedList(t);
	}

}
