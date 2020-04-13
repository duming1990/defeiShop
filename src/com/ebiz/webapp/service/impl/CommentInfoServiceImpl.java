package com.ebiz.webapp.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.BaseFilesDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommentInfoDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.service.CommentInfoService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2012-05-30 08:59
 */
@Service
public class CommentInfoServiceImpl implements CommentInfoService {

	@Resource
	private CommentInfoDao commentInfoDao;

	@Resource
	private BaseDataDao BaseDataDao;

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private EntpInfoDao entpInfoDao;

	@Resource
	private BaseFilesDao baseFilesDao;

	@Override
	public Integer createCommentInfo(CommentInfo t) {

		// 更新订单明细为：已评价
		if (null != t.getOrder_details_id() && null != t.getOrder_id()) {
			// 当前明细改为：已评价
			OrderInfoDetails updateOds = new OrderInfoDetails();
			updateOds.setId(t.getOrder_details_id());
			updateOds.setIs_comment(1);
			this.orderInfoDetailsDao.updateEntity(updateOds);

			// 查询未评价数量
			OrderInfoDetails odsCount = new OrderInfoDetails();
			odsCount.setOrder_id(t.getOrder_id());
			odsCount.setIs_comment(0);
			int notCommentCount = this.orderInfoDetailsDao.selectEntityCount(odsCount);
			if (notCommentCount == 0) {
				// 订单明细全评价修改订单为：已评价
				OrderInfo updateOrder = new OrderInfo();
				updateOrder.setId(t.getOrder_id());
				updateOrder.setIs_comment(1);
				this.orderInfoDao.updateEntity(updateOrder);
			}
		}

		if (null != t.getMap().get("only_creat")) {
			int id = this.commentInfoDao.insertEntity(t);
			this.updateCommInfoAndEntpInfoCommentCount(id, 1);
			return id;

		} else {

			if (null != t.getCommentInfoList()) {
				List<CommentInfo> list = t.getCommentInfoList();
				if (null != list && list.size() > 0) {
					CommentInfo commentInfo = null;
					for (CommentInfo cur : list) {
						commentInfo = cur;
						int count = this.commentInfoDao.insertEntity(commentInfo);
						if (count > 0) {
							// 评论插入成功后，修改商品，订单详情参数
							this.updateOrderInfoDetailAndCommInfo(commentInfo);
						}
					}
				}
				return 1;

			} else {

				int id = this.commentInfoDao.insertEntity(t);
				if (id > 0) {
					// 评论插入成功后，修改商品，订单详情参数
					this.updateOrderInfoDetailAndCommInfo(t);
				}

				if (null != t.getMap().get("basefiles")) {
					String[] bfs = (String[]) t.getMap().get("basefiles");
					for (int i = 0; i < bfs.length; i++) {
						if (StringUtils.isNotBlank(bfs[i])) {
							BaseFiles basefiles = new BaseFiles();
							basefiles.setLink_id(id);
							basefiles.setLink_tab("CommentInfo");
							basefiles.setType(Keys.BaseFilesType.Base_Files_TYPE_40.getIndex());
							basefiles.setSave_path(bfs[i]);
							basefiles.setIs_del(0);
							basefiles.setAdd_date(new Date());
							basefiles.setAdd_user_id(t.getComm_uid());
							baseFilesDao.insertEntity(basefiles);
						}
					}
				}
				// 更新评论数
				if (null != t.getMap().get("add_comment_count")) {
					if (null != t.getEntp_id()) {
						EntpInfo ei = new EntpInfo();
						ei.setId(t.getEntp_id());
						ei.getMap().put("add_comment_count", 1);// 评论数+1
						this.entpInfoDao.updateEntity(ei);
					}
				}

				return id;
			}
		}
	}

	public void updateOrderInfoDetailAndCommInfo(CommentInfo t) throws DataAccessException {
		if (null != t.getComm_type()) {
			if (t.getComm_type() == Keys.OrderType.ORDER_TYPE_10.getIndex()) { // 更新order_info_detail
				// 表

				OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
				orderInfoDetails.setComm_id(t.getLink_id());
				orderInfoDetails.setOrder_id(t.getOrder_id());
				orderInfoDetails.setComm_tczh_id(t.getComm_tczh_id());
				orderInfoDetails = this.orderInfoDetailsDao.selectEntity(orderInfoDetails);
				if (null != orderInfoDetails) {
					OrderInfoDetails orderInfoDetailsUpdate = new OrderInfoDetails();
					orderInfoDetailsUpdate.setId(Integer.valueOf(orderInfoDetails.getId()));
					orderInfoDetailsUpdate.setHas_comment(1);
					this.orderInfoDetailsDao.updateEntity(orderInfoDetailsUpdate);
				}
			}

			// 更新 commInfo
			this.updateCommInfoAndEntpInfoCommentCount(t.getId(), 1);
		}
	}

	public void updateCommInfoAndEntpInfoCommentCount(Integer id, int add_or_sub) throws DataAccessException {
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setId(id);
		commentInfo = this.commentInfoDao.selectEntity(commentInfo);
		if (null != commentInfo) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(commentInfo.getLink_id());
			commInfo = this.commInfoDao.selectEntity(commInfo);
			if (null != commInfo) {
				CommInfo commInfoUpdate = new CommInfo();
				commInfoUpdate.setId(commInfo.getId());
				commInfoUpdate.setComment_count(commInfo.getComment_count() + add_or_sub);
				this.commInfoDao.updateEntity(commInfoUpdate);

				if (null != commInfo.getOwn_entp_id()) {
					EntpInfo entpInfo = new EntpInfo();
					entpInfo.setId(commInfo.getOwn_entp_id());
					entpInfo = this.entpInfoDao.selectEntity(entpInfo);
					if (null != entpInfo) {
						EntpInfo entpInfoUpdate = new EntpInfo();
						entpInfoUpdate.setId(entpInfo.getId());
						entpInfoUpdate.setComment_count(entpInfo.getComment_count() + add_or_sub);
						this.entpInfoDao.updateEntity(entpInfoUpdate);
					}
				}
			}
		}

	}

	@Override
	public CommentInfo getCommentInfo(CommentInfo t) {
		return this.commentInfoDao.selectEntity(t);
	}

	@Override
	public Integer getCommentInfoCount(CommentInfo t) {
		return this.commentInfoDao.selectEntityCount(t);
	}

	@Override
	public List<CommentInfo> getCommentInfoList(CommentInfo t) {
		return this.commentInfoDao.selectEntityList(t);
	}

	@Override
	public int modifyCommentInfo(CommentInfo t) {
		if (null != t.getMap().get("basefiles")) {
			BaseFiles baseFiles = new BaseFiles();
			baseFiles.getMap().put("link_id", t.getId());
			baseFiles.getMap().put("link_tab", "CommentInfo");
			baseFiles.setIs_del(1);
			baseFilesDao.updateEntity(baseFiles);

			String[] bfs = (String[]) t.getMap().get("basefiles");
			for (int i = 0; i < bfs.length; i++) {
				if (StringUtils.isNotBlank(bfs[i])) {
					BaseFiles basefiles = new BaseFiles();
					basefiles.setLink_id(t.getId());
					basefiles.setLink_tab("CommentInfo");
					basefiles.setType(Keys.BaseFilesType.Base_Files_TYPE_40.getIndex());
					basefiles.setSave_path(bfs[i]);
					basefiles.setIs_del(0);
					basefiles.setAdd_date(new Date());
					basefiles.setAdd_user_id(t.getComm_uid());
					baseFilesDao.insertEntity(basefiles);
				} else {
					continue;
				}
			}
		}
		return this.commentInfoDao.updateEntity(t);
	}

	@Override
	public int removeCommentInfo(CommentInfo t) {
		// 更新 商品 商家 评论数量
		if (null != t.getMap().get("pks")) {
			String pks[] = t.getMap().get("pks").toString().split(",");
			if (null != pks && pks.length > 0) {
				for (String cur : pks) {
					this.updateCommInfoAndEntpInfoCommentCount(Integer.valueOf(cur), -1);
				}
			}
		} else {

			this.updateCommInfoAndEntpInfoCommentCount(t.getId(), -1);
		}

		return this.commentInfoDao.deleteEntity(t);
	}

	@Override
	public List<CommentInfo> getCommentInfoPaginatedList(CommentInfo t) {
		return this.commentInfoDao.selectEntityPaginatedList(t);
	}

	@Override
	public Integer getCommentInfoAvgCommSore(CommentInfo commentInfo) {
		return this.commentInfoDao.selectCommentInfoAvgCommSore(commentInfo);
	}

	@Override
	public Integer getCommentInfoSumCommSore(CommentInfo commentInfo) {
		return this.commentInfoDao.selectCommentInfoSumCommSore(commentInfo);
	}

}
