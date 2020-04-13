package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.ActivityApplyCommDao;
import com.ebiz.webapp.dao.ActivityApplyDao;
import com.ebiz.webapp.dao.ActivityDao;
import com.ebiz.webapp.dao.BaseClassDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.ActivityApply;
import com.ebiz.webapp.domain.ActivityApplyComm;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.service.ActivityApplyService;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2019-04-04 11:00
 */
@Service
public class ActivityApplyServiceImpl extends BaseImpl implements ActivityApplyService {

	@Resource
	private ActivityApplyDao activityApplyDao;

	@Resource
	private ActivityApplyCommDao activityApplyCommDao;

	@Resource
	private ActivityDao activityDao;

	@Resource
	private BaseClassDao baseClassDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private CommInfoPoorsDao commInfoPoorsDao;

	public Integer createActivityApply(ActivityApply t) {
		int id = 0;
		// 判断是否有记录
		ActivityApply a = new ActivityApply();
		a.setEntp_id(t.getEntp_id());
		a.setLink_id(t.getLink_id());
		a = activityApplyDao.selectEntity(a);

		if (null == a) {
			// 插入
			id = this.activityApplyDao.insertEntity(t);
		} else {
			// 更新
			// 取出id
			id = a.getId();
			a.setId(a.getId());
			// 复制数据
			a = t;
			a.setUpdate_date(new Date());
			a.setAudit_state(0);
			activityApplyDao.updateEntity(a);
		}

		BigDecimal sale_price = new BigDecimal(0);
		if (null != a) {
			// 先删除活动商品
			ActivityApplyComm activityApplyComm = new ActivityApplyComm();
			activityApplyComm.setActivity_apply_id(a.getId());
			activityApplyComm.setActivity_id(t.getLink_id());
			activityApplyComm.setEntp_id(t.getEntp_id());
			List<ActivityApplyComm> activityApplyCommList = activityApplyCommDao.selectEntityList(activityApplyComm);
			// 判断有没有商品
			if (null != activityApplyCommList && activityApplyCommList.size() > 0) {
				for (ActivityApplyComm activityApplyCommDel : activityApplyCommList) {

					// 删除商品
					CommInfo commInfoDel = new CommInfo();
					commInfoDel.setId(activityApplyCommDel.getComm_id());
					sale_price = commInfoDao.selectEntity(commInfoDel).getSale_price();// 删除之前获取商品的原价保存在sale_price
					commInfoDao.deleteEntity(commInfoDel);

					// 删除扶贫对象
					CommInfoPoors commInfoPoorsDel = new CommInfoPoors();
					commInfoPoorsDel.getMap().put("comm_id", activityApplyCommDel.getComm_id());
					commInfoPoorsDao.deleteEntity(commInfoPoorsDel);

					// 删除商品套餐
					CommTczhPrice commTczhPriceDel = new CommTczhPrice();
					commTczhPriceDel.getMap().put("comm_id", activityApplyCommDel.getComm_id());
					commTczhPriceDao.deleteEntity(commTczhPriceDel);

					// 删除活动商品
					ActivityApplyComm del = new ActivityApplyComm();
					del.setId(activityApplyCommDel.getId());
					activityApplyCommDao.deleteEntity(del);
				}
			}
		}
		if (null != t.getList() && t.getList().size() > 0) {
			for (ActivityApplyComm cur : t.getList()) {
				// 查找商品套餐
				CommTczhPrice commTczhPriceSelect = new CommTczhPrice();
				commTczhPriceSelect.setId(cur.getComm_id());
				commTczhPriceSelect = commTczhPriceDao.selectEntity(commTczhPriceSelect);

				// 查找商品
				CommInfo commInfoSelect = new CommInfo();
				commInfoSelect.setId(Integer.valueOf(commTczhPriceSelect.getComm_id()));
				commInfoSelect.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				commInfoSelect = commInfoDao.selectEntity(commInfoSelect);

				// 查找扶贫对象
				CommInfoPoors commInfoPoorsSelect = new CommInfoPoors();
				commInfoPoorsSelect.setComm_id(commInfoSelect.getId());
				List<CommInfoPoors> commInfoPoorsList = commInfoPoorsDao.selectEntityList(commInfoPoorsSelect);
				logger.info("==cur.getComm_id():" + cur.getComm_id());
				logger.info("==commInfoPoorsList:" + commInfoPoorsList.size());

				// 插入商品
				CommInfo commInfoInsert = commInfoSelect;
				commInfoInsert.setId(null);
				commInfoInsert.setComm_qrcode_path(null);
				commInfoInsert.setComm_no(getCommNoBase(commInfoSelect.getCls_id(), baseClassDao));
				commInfoInsert.setComm_name(cur.getComm_name());
				commInfoInsert.setComm_type(Keys.CommType.COMM_TYPE_30.getIndex());
				commInfoInsert.setCopy_id(commTczhPriceSelect.getId());
				commInfoInsert.setIs_rebate(0);
				Integer comm_id = commInfoDao.insertEntity(commInfoInsert);
				if (null != comm_id) {
					logger.info("commInfoInsert" + comm_id);
				}

				// 插入扶贫对象
				for (CommInfoPoors cip : commInfoPoorsList) {
					CommInfoPoors commInfoPoorsInsert = cip;
					commInfoPoorsInsert.setId(null);
					commInfoPoorsInsert.setComm_id(comm_id);
					commInfoPoorsInsert.setAdd_date(new Date());
					Integer cip_id = commInfoPoorsDao.insertEntity(commInfoPoorsInsert);
					if (null != cip_id) {
						logger.info("commInfoPoorsInsert" + cip_id);
					}

				}

				// 插入商品套餐
				CommTczhPrice commTczhPriceInsert = commTczhPriceSelect;
				commTczhPriceInsert.setId(null);
				commTczhPriceInsert.setComm_id(comm_id.toString());
				commTczhPriceInsert.setOrig_price(sale_price);// 设置商品的原价
				Integer commTczhPrice_id = commTczhPriceDao.insertEntity(commTczhPriceInsert);
				if (null != comm_id) {
					logger.info("commTczhPrice_id" + commTczhPrice_id);
				}

				cur.setActivity_apply_id(id);
				cur.setComm_id(comm_id);
				cur.setComm_tczh_id(commTczhPrice_id);
				activityApplyCommDao.insertEntity(cur);
			}

		}
		return id;
	}

	public ActivityApply getActivityApply(ActivityApply t) {
		return this.activityApplyDao.selectEntity(t);
	}

	public Integer getActivityApplyCount(ActivityApply t) {
		return this.activityApplyDao.selectEntityCount(t);
	}

	public List<ActivityApply> getActivityApplyList(ActivityApply t) {
		return this.activityApplyDao.selectEntityList(t);
	}

	public int modifyActivityApply(ActivityApply t) {

		ActivityApply entity = new ActivityApply();
		entity.setId(t.getId());
		entity = activityApplyDao.selectEntity(entity);

		Activity activity = new Activity();
		activity.setId(entity.getLink_id());
		activity.setIs_del(0);
		activity = activityDao.selectEntity(activity);

		// 插入默认支付商品
		if (null != t.getMap().get("isnert_default_comm")) {
			if (entity.getPay_type().intValue() == Keys.ActivityPayType.ActivityPayType_1.getIndex()) {

				CommInfo isHas = new CommInfo();
				isHas.setOwn_entp_id(entity.getEntp_id());
				isHas.setIs_del(0);
				isHas.setIs_activity_default(1);
				int count = commInfoDao.selectEntityCount(isHas);
				if (count == 0) {
					CommInfo a = new CommInfo();
					a.setComm_type(Keys.CommType.COMM_TYPE_30.getIndex());
					a.setIs_del(0);
					a.setAudit_state(1);
					a.setComm_name(Keys.activity_comm_name);
					a.setCls_id(Keys.qita_cls_id);
					a.setCls_name(Keys.qita_cls_name);
					a.setPar_cls_id(Keys.qita_cls_par_id);
					a.setAdd_user_id(entity.getUser_id());
					a.setAdd_user_name(entity.getUser_name());
					a.setAdd_date(new Date());
					a.setIs_activity_default(1);
					a.setOwn_entp_id(entity.getEntp_id());
					a.setInventory(99999);
					a.setComm_no(getCommNoBase(a.getCls_id(), baseClassDao));
					a.setIs_sell(1);
					a.setUp_date(activity.getStart_date());
					a.setDown_date(activity.getEnd_date());
					int comm_id = commInfoDao.insertEntity(a);

					CommTczhPrice b = new CommTczhPrice();
					b.setComm_id(comm_id + "");
					b.setAdd_date(new Date());
					b.setAdd_user_id(a.getAdd_user_id());
					b.setComm_price(new BigDecimal(1));
					b.setInventory(a.getInventory());
					b.setComm_weight(new BigDecimal(1));
					int tczh_id = commTczhPriceDao.insertEntity(b);

					// 插入到活动商品表中
					ActivityApplyComm c = new ActivityApplyComm();
					c.setActivity_id(entity.getLink_id());
					c.setActivity_apply_id(entity.getId());
					c.setComm_id(comm_id);
					c.setComm_name(a.getComm_name());
					c.setEntp_id(a.getOwn_entp_id());
					c.setComm_tczh_id(tczh_id);
					activityApplyCommDao.insertEntity(c);

				}

			}
		}
		if (null != t.getMap().get("commTczhPrice_update")) {
			for (CommTczhPrice ctp : t.getCommTczhPriceList()) {
				commTczhPriceDao.updateEntity(ctp);

				// 修改扶贫比例
				CommTczhPrice commTczhPriceSelect = new CommTczhPrice();
				commTczhPriceSelect.setId(ctp.getId());
				commTczhPriceSelect = commTczhPriceDao.selectEntity(commTczhPriceSelect);

				CommInfo commInfoselect = new CommInfo();
				commInfoselect.setId(Integer.valueOf(commTczhPriceSelect.getComm_id()));
				commInfoselect.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				commInfoselect = commInfoDao.selectEntity(commInfoselect);

				CommInfo commInfoupdate = new CommInfo();
				commInfoupdate.setId(commInfoselect.getId());
				if (null != ctp.getMap().get("aid_scale")) {// 在进行线下活动修改商品时，如果选择的是不是扶贫商品，则此处需判空
					commInfoupdate.setAid_scale(new BigDecimal(ctp.getMap().get("aid_scale").toString()));
				}
				commInfoupdate.setIs_aid(Integer.valueOf(ctp.getMap().get("is_aid").toString()));
				commInfoDao.updateEntity(commInfoupdate);
			}
		}

		return this.activityApplyDao.updateEntity(t);
	}

	public int removeActivityApply(ActivityApply t) {
		return this.activityApplyDao.deleteEntity(t);
	}

	public List<ActivityApply> getActivityApplyPaginatedList(ActivityApply t) {
		return this.activityApplyDao.selectEntityPaginatedList(t);
	}

	@Override
	public List<ActivityApply> selectActivityApplyOrderSum(ActivityApply t) {
		// TODO Auto-generated method stub
		return this.activityApplyDao.selectActivityApplyOrderSum(t);
	}

	@Override
	public Integer getActivityApplyacCount(ActivityApply t) {
		return this.activityApplyDao.selectActivityApplyacCount(t);
	}

	@Override
	public List<ActivityApply> selectActivityApplyOrderac(ActivityApply t) {
		return this.activityApplyDao.selectActivityApplyOrderac(t);
	}

}
