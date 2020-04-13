package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.ReturnsInfoOrderRelationDao;
import com.ebiz.webapp.dao.ReturnsSwapDetailDao;
import com.ebiz.webapp.dao.SwapsInfoDao;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ReturnsInfoOrderRelation;
import com.ebiz.webapp.domain.ReturnsSwapDetail;
import com.ebiz.webapp.domain.SwapsInfo;
import com.ebiz.webapp.service.SwapsInfoService;

/**
 * @version 2014-06-18 16:13
 */
@Service
public class SwapsInfoServiceImpl implements SwapsInfoService {
	@Resource
	private ReturnsSwapDetailDao returnsSwapDetailDao;

	@Resource
	private SwapsInfoDao swapsInfoDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private ReturnsInfoOrderRelationDao returnsInfoOrderRelationDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	public Integer createSwapsInfo(SwapsInfo t) {
		return this.swapsInfoDao.insertEntity(t);
	}

	public SwapsInfo getSwapsInfo(SwapsInfo t) {
		return this.swapsInfoDao.selectEntity(t);
	}

	public Integer getSwapsInfoCount(SwapsInfo t) {
		return this.swapsInfoDao.selectEntityCount(t);
	}

	public List<SwapsInfo> getSwapsInfoList(SwapsInfo t) {
		return this.swapsInfoDao.selectEntityList(t);
	}

	public int modifySwapsInfo(SwapsInfo t) {
		return this.swapsInfoDao.updateEntity(t);
	}

	public int removeSwapsInfo(SwapsInfo t) {
		return this.swapsInfoDao.deleteEntity(t);
	}

	public List<SwapsInfo> getSwapsInfoPaginatedList(SwapsInfo t) {
		return this.swapsInfoDao.selectEntityPaginatedList(t);
	}

	public void sendGoods(SwapsInfo entity) {
		entity.setAudit_status(3);
		entity.setIs_confirm(1);

		// 取得老的订单的内容, 修改其内容,
		String rel_name = entity.getMap().get("rel_name") == null ? "" : entity.getMap().get("rel_name").toString();
		String rel_phone = entity.getMap().get("rel_phone") == null ? "" : entity.getMap().get("rel_phone").toString();
		String rel_email = entity.getMap().get("rel_email") == null ? "" : entity.getMap().get("rel_email").toString();
		String rel_addr = entity.getMap().get("rel_addr") == null ? "" : entity.getMap().get("rel_addr").toString();
		String rel_zip = entity.getMap().get("rel_zip") == null ? "0" : entity.getMap().get("rel_zip").toString();

		SwapsInfo sinfo = new SwapsInfo();
		sinfo.setId(entity.getId());
		sinfo = this.swapsInfoDao.selectEntity(sinfo);
		// 取得订购人
		// 获得订单
		OrderInfo oid = new OrderInfo();
		oid.setId(sinfo.getOrder_info_details_id());
		// 取得订单的信息
		oid = this.orderInfoDao.selectEntity(oid);
		if (null != oid) {

			oid.setRel_addr(rel_addr);
			oid.setRel_email(rel_email);
			oid.setRel_name(rel_name);
			oid.setRel_phone(rel_phone);
			if (StringUtils.isNotBlank(rel_zip)) {

				oid.setRel_zip(Integer.valueOf(rel_zip));
			}

			// 将外键存储到关联表中 returns_info_order_relation

			ReturnsInfoOrderRelation relation = new ReturnsInfoOrderRelation();

			/**
			 * 检测是否包含'H','Y' ,如果存在,则截取之前的
			 */
			String a = oid.getTrade_index();
			int index = a.indexOf("H") > -1 ? a.indexOf("H") : a.indexOf("Y");
			if (index > -1) {

				a = a.substring(0, index);
			}

			relation.setP_trade_index(a);

			// relation.setP_trade_index(oid.getTrade_index());
			// 0:异常订单 1:换货
			relation.setTrade_type(1);
			String maxId = this.returnsInfoOrderRelationDao.selectReturnsInfoOrderRelationMaxId(relation);
			String trade_index = "";
			if (StringUtils.isBlank(maxId)) {
				trade_index = a + "H001";

			} else {

				maxId = maxId.substring(maxId.length() - 3);

				String newSeq = Integer.parseInt(maxId) + 1 + "";
				int len = newSeq.length();

				if (len == 1) {
					newSeq = "00" + newSeq;

				} else if (len == 2) {
					newSeq = "0" + newSeq;

				}

				trade_index = a + "H" + newSeq;

			}

			relation.setTrade_index(trade_index);

			this.returnsInfoOrderRelationDao.insertEntity(relation);

			/**
			 * 1.修改流水号 2.修改 订购数量 order_num 3.修改 总价格 4.修改订单状态 10 6.修改新增日期 7.
			 */
			// oid.setId(null);
			oid.setTrade_index(trade_index);

			ReturnsSwapDetail rsd = new ReturnsSwapDetail();
			rsd.setReturns_info_id(sinfo.getId());
			rsd.setApply_type(1);

			List<ReturnsSwapDetail> rsdList = new ArrayList<ReturnsSwapDetail>();
			List<OrderInfoDetails> detailList = new ArrayList<OrderInfoDetails>();
			rsdList = this.returnsSwapDetailDao.selectEntityList(rsd);
			float order_money_total = 0f;
			Integer comm_count = 0;
			// 循环遍历 退订订单的每个产品
			for (ReturnsSwapDetail rsde : rsdList) {
				OrderInfoDetails detail = new OrderInfoDetails();
				detail.setId(rsde.getOrder_info_details_id());
				detail = this.orderInfoDetailsDao.selectEntity(detail);
				if (null != detail) {
					comm_count = comm_count + rsde.getComm_count();

					if (detail.getGood_price() != null) {
						// 退回的物品数量* 每个物品的价格
						float order_money = (rsde.getComm_count().longValue()) * (detail.getGood_price().floatValue());

						order_money_total = order_money_total + order_money;
						/**
						 * 修改订单明细,新增订单
						 */

						// 修改订单明细的 order_id,good_count,good_sum_price,
						detail.setGood_count(rsde.getComm_count().intValue());
						detail.setGood_sum_price(new BigDecimal(order_money));
						detail.setId(null);
						detailList.add(detail);

					}
				}

			}

			// 需要添加上运费

			BigDecimal order_money_total_ = new BigDecimal(order_money_total).add(oid.getMatflow_price());

			oid.setOrder_num(comm_count);
			// 修改订单,得到该订单的所有的价格
			oid.setOrder_money(order_money_total_);
			oid.setReal_pay_money(oid.getOrder_money());

			// 修改订单的状态 为 已付款
			oid.setOrder_state(10);
			oid.setAdd_date(new Date());
			oid.setOrder_date(new Date());
			// 优惠功能全消除
			oid.setId(null);
			oid.setYhq_id(null);
			oid.setYhq_tip_desc(null);
			oid.setQdyh_id(null);
			oid.setQdyh_tip_desc(null);

			Integer orderId = this.orderInfoDao.insertEntity(oid);

			for (OrderInfoDetails de : detailList) {
				de.setOrder_id(orderId);
				de.setAdd_date(new Date());
				this.orderInfoDetailsDao.insertEntity(de);
			}

		}

		this.swapsInfoDao.updateEntity(entity);

	}
}
