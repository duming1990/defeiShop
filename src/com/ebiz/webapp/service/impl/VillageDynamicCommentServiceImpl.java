package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.VillageDynamicCommentDao;
import com.ebiz.webapp.dao.VillageDynamicDao;
import com.ebiz.webapp.dao.VillageDynamicRecordDao;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicComment;
import com.ebiz.webapp.service.VillageDynamicCommentService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2018-01-26 15:45
 */
@Service
public class VillageDynamicCommentServiceImpl extends BaseImpl implements VillageDynamicCommentService {

	@Resource
	private VillageDynamicCommentDao villageDynamicCommentDao;

	@Resource
	private VillageDynamicRecordDao villageDynamicRecordDao;

	@Resource
	private VillageDynamicDao villageDynamicDao;

	public Integer createVillageDynamicComment(VillageDynamicComment t) {
		// 1.评论 3.点赞
		// 2.回复

		int id = this.villageDynamicCommentDao.insertEntity(t);

		Integer recordType = null;
		if (t.getComment_type().intValue() == Keys.CommentType.COMMENT_TYPE_1.getIndex()) {
			// 更新动态评论点赞数
			addVillageDynamicCommentCount(t.getLink_id(), "add_comment_count", 1);

			recordType = Keys.DynamicRecordType.DynamicRecordType_3.getIndex();
		}
		if (t.getComment_type().intValue() == Keys.CommentType.COMMENT_TYPE_2.getIndex()) {
			// 更新动态评论点赞数
			addVillageDynamicCommentCount(t.getLink_id(), "add_comment_count", 1);
			recordType = Keys.DynamicRecordType.DynamicRecordType_4.getIndex();
		}
		if (t.getComment_type().intValue() == Keys.CommentType.COMMENT_TYPE_3.getIndex()) {
			// 更新动态评论点赞数
			addVillageDynamicCommentCount(t.getLink_id(), "add_comment_count", 1);
			recordType = Keys.DynamicRecordType.DynamicRecordType_5.getIndex();
		}

		// 插入记录
		insertRecord(null, id, recordType, t.getLink_user_id(), t.getLink_user_name(), t.getAdd_user_id(),
				t.getAdd_user_name(), Integer.valueOf(t.getMap().get("village_id").toString()), villageDynamicRecordDao);

		return id;
	}

	public void addVillageDynamicCommentCount(Integer id, String map, Integer count) {
		System.out.println("===addVillageDynamicCommentCount===");
		VillageDynamic update = new VillageDynamic();
		update.setId(id);
		update.getMap().put(map, count);
		villageDynamicDao.updateEntity(update);
	}

	public VillageDynamicComment getVillageDynamicComment(VillageDynamicComment t) {
		return this.villageDynamicCommentDao.selectEntity(t);
	}

	public Integer getVillageDynamicCommentCount(VillageDynamicComment t) {
		return this.villageDynamicCommentDao.selectEntityCount(t);
	}

	public List<VillageDynamicComment> getVillageDynamicCommentList(VillageDynamicComment t) {
		return this.villageDynamicCommentDao.selectEntityList(t);
	}

	public int modifyVillageDynamicComment(VillageDynamicComment t) {
		return this.villageDynamicCommentDao.updateEntity(t);
	}

	public int removeVillageDynamicComment(VillageDynamicComment t) {
		return this.villageDynamicCommentDao.deleteEntity(t);
	}

	public List<VillageDynamicComment> getVillageDynamicCommentPaginatedList(VillageDynamicComment t) {
		return this.villageDynamicCommentDao.selectEntityPaginatedList(t);
	}

}
