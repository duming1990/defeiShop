package com.ebiz.webapp.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.ReturnsInfoDao;
import com.ebiz.webapp.dao.ReturnsSwapDetailDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ReturnsInfo;
import com.ebiz.webapp.domain.ReturnsSwapDetail;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.ReturnsInfoService;

/**
 * @author Wu,Yang
 * @version 2014-05-25 11:55
 */
@Service
public class ReturnsInfoServiceImpl implements ReturnsInfoService {

	@Resource
	private ReturnsInfoDao returnsInfoDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private ReturnsSwapDetailDao returnsSwapDetailDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	public Integer createReturnsInfo(ReturnsInfo t) {
		return this.returnsInfoDao.insertEntity(t);
	}

	public ReturnsInfo getReturnsInfo(ReturnsInfo t) {
		return this.returnsInfoDao.selectEntity(t);
	}

	public Integer getReturnsInfoCount(ReturnsInfo t) {
		return this.returnsInfoDao.selectEntityCount(t);
	}

	public List<ReturnsInfo> getReturnsInfoList(ReturnsInfo t) {
		return this.returnsInfoDao.selectEntityList(t);
	}

	public int modifyReturnsInfo(ReturnsInfo t) {
		return this.returnsInfoDao.updateEntity(t);
	}

	public int removeReturnsInfo(ReturnsInfo t) {
		return this.returnsInfoDao.deleteEntity(t);
	}

	public List<ReturnsInfo> getReturnsInfoPaginatedList(ReturnsInfo t) {
		return this.returnsInfoDao.selectEntityPaginatedList(t);
	}

	/**
	 * 计算积分, 需要取得 增加的用户信息,并且取得订单的信息 计算销量和 库存
	 * 
	 * @param ui
	 * @param orderId
	 */
	public void caculateCreditsAndStockAndSales(ReturnsInfo rinfo) {

		rinfo.setAudit_status(2);
		// 退货成功
		rinfo.setIs_confirm(1);

		// 取得用户信息
		UserInfo ui = new UserInfo();
		ui.setId(rinfo.getUser_id());
		ui = this.userInfoDao.selectEntity(ui);
		Integer userId = ui.getId();
		OrderInfo oid = new OrderInfo();
		oid.setId(rinfo.getOrder_info_details_id());
		oid = this.orderInfoDao.selectEntity(oid);

		String orderId = oid.getId() + "";

		// 先查找baseData中积分获取规则
		BaseData baseData = new BaseData();
		baseData.setType(6000);
		baseData.setIs_del(0);
		baseData = this.baseDataDao.selectEntity(baseData);
		if (null != baseData) {
			// 修改用户积分和用户等级

			BigDecimal get_jf_totle = new BigDecimal(0);
			BigDecimal get_jf_count = new BigDecimal(0);
			/**
			 * 取得退订表的 商品的价格和 退订的数量,计算退订的-积分
			 */

			ReturnsSwapDetail rsd = new ReturnsSwapDetail();
			rsd.setReturns_info_id(rinfo.getId());

			List<ReturnsSwapDetail> rsdList = new ArrayList<ReturnsSwapDetail>();

			rsdList = this.returnsSwapDetailDao.selectEntityList(rsd);

			for (ReturnsSwapDetail rsd1 : rsdList) {

				OrderInfoDetails detail = new OrderInfoDetails();

				detail.setId(rsd1.getOrder_info_details_id());
				detail = this.orderInfoDetailsDao.selectEntity(detail);

				if (null != detail) {
					Integer get_jf = 0;
					// 退订数量 * 商品总价格/商品原来数量* 积分兑换比率
					// 添加规则,如果有促销价格,则用促销价格 退订,如果没有,则按照套餐中的商品价格退订
					// 商品价格 四舍五入,保留整数位

					get_jf = (int) ((rsd1.getComm_count().longValue())
							* ((detail.getGood_sum_price().divide(new BigDecimal(detail.getGood_count()))).setScale(0,
									BigDecimal.ROUND_HALF_UP).intValue()) * baseData.getPre_number());

					// get_jf = (rsd1.getComm_count().longValue())
					// * (detail.getGood_price().setScale(0, BigDecimal.ROUND_HALF_UP).longValue())
					// * baseData.getPre_number();
					// 计算积分和销量

					CommInfo cinfo = new CommInfo();
					cinfo.setId(detail.getComm_id());
					cinfo = this.commInfoDao.selectEntity(cinfo);
					// 销量
					cinfo.setSale_count(cinfo.getSale_count() - 1);
					cinfo.setSale_count_update(cinfo.getSale_count_update() - 1);
					// 库存
					cinfo.setInventory(cinfo.getInventory() + 1);
					this.commInfoDao.updateEntity(cinfo);

					get_jf_totle = get_jf_totle.add(new BigDecimal(get_jf));

					// +库存

					/**
					 * 根据order_info_details 的comm_tczh_id 查询
					 */

					CommTczhPrice ctp = new CommTczhPrice();
					ctp.setId(detail.getComm_tczh_id());
					ctp = this.commTczhPriceDao.selectEntity(ctp);
					if (ctp != null) {
						ctp.setInventory(ctp.getInventory().intValue() + 1);
					}
					this.commTczhPriceDao.updateEntity(ctp);
				}

			}
			// 查询该用户已经有的积分
			if (null != ui.getCur_score()) {
				get_jf_count = ui.getCur_score().subtract(get_jf_totle);// 增加之后的积分数
			} else {
				get_jf_count = new BigDecimal(0).subtract(get_jf_totle);// 增加之后的积分数
			}

			UserInfo userInfo = new UserInfo();
			userInfo.setId(ui.getId());
			userInfo.setCur_score(get_jf_count);

			BaseData baseData2 = new BaseData();
			baseData2.setType(200);
			baseData2.setIs_del(0);
			baseData2.getMap().put("order_by_pre_number", true);
			List<BaseData> baseDataList = this.baseDataDao.selectEntityList(baseData2);
			for (BaseData bd : baseDataList) {// 判断用户是不是需要升级
				if (get_jf_count.compareTo(new BigDecimal(bd.getPre_number())) >= 0) {
					userInfo.setUser_level(bd.getId());
				}
			}
			this.userInfoDao.updateEntity(userInfo);

			// 撤销回积分

		}

		this.returnsInfoDao.updateEntity(rinfo);
	}
}
