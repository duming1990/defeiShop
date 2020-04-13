package com.ebiz.webapp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ebiz.webapp.dao.EntpDuiZhangDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.SysSettingDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.domain.EntpDuiZhang;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.EntpDuiZhangService;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author Wu,Yang
 * @version 2018-06-12 12:25
 */
@Service
public class EntpDuiZhangServiceImpl implements EntpDuiZhangService {

	@Resource
	private EntpDuiZhangDao entpDuiZhangDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private SysSettingDao sysSettingDao;

	public Integer createEntpDuiZhang(EntpDuiZhang t) {
		// 修改商家货币数据
		if (null != t.getMap().get("update_uInfo")) {
			UserInfo userinfo = (UserInfo) t.getMap().get("update_uInfo");
			this.userInfoDao.updateEntity(userinfo);
		}

		// insert_ubr
		if (null != t.getMap().get("insert_ubr")) {
			UserBiRecord userBirecord = (UserBiRecord) t.getMap().get("insert_ubr");
			this.userBiRecordDao.insertEntity(userBirecord);
		}
		int id = this.entpDuiZhangDao.insertEntity(t);
		// 修改order_info数据
		if (null != t.getMap().get("update_orderInfo")) {
			OrderInfo orderInfo = (OrderInfo) t.getMap().get("update_orderInfo");
			orderInfo.setLink_check_id(id);
			this.orderInfoDao.updateEntity(orderInfo);
		}
		return id;
	}

	public EntpDuiZhang getEntpDuiZhang(EntpDuiZhang t) {
		return this.entpDuiZhangDao.selectEntity(t);
	}

	public Integer getEntpDuiZhangCount(EntpDuiZhang t) {
		return this.entpDuiZhangDao.selectEntityCount(t);
	}

	public List<EntpDuiZhang> getEntpDuiZhangList(EntpDuiZhang t) {
		return this.entpDuiZhangDao.selectEntityList(t);
	}

	public int modifyEntpDuiZhang(EntpDuiZhang t) {
		// 修改商家货币数据
		if (null != t.getMap().get("update_uInfo")) {
			UserInfo userinfo = (UserInfo) t.getMap().get("update_uInfo");
			this.userInfoDao.updateEntity(userinfo);
		}

		// insert_ubr
		if (null != t.getMap().get("insert_ubr")) {
			UserBiRecord userBirecord = (UserBiRecord) t.getMap().get("insert_ubr");
			this.userBiRecordDao.insertEntity(userBirecord);
		}

		// 对账关联订单修改
		if (null != t.getMap().get("update_order_is_check_eq_0")) {
			List<OrderInfo> orderInfoList = (List<OrderInfo>) t.getMap().get("update_order_is_check_eq_0");
			for (OrderInfo o : orderInfoList) {
				OrderInfo temp = new OrderInfo();
				temp.setId(o.getId());
				temp.getMap().put("set_link_check_id_isnull", "true");
				temp.setIs_check(Keys.AddIsCleck.ADD_IS_CLECK_0.getIndex());
				this.orderInfoDao.updateEntity(temp);
			}
		}

		if (t.getIs_check() == Keys.IsCleck.IS_CLECK_1.getIndex()) {
			SysSetting sysSetting = new SysSetting();
			sysSetting.setTitle(Keys.financialMobile);
			sysSetting = sysSettingDao.selectEntity(sysSetting);
			if (null != sysSetting) {
				StringBuffer message1 = new StringBuffer("{\"name\":\"" + "商家结算" + "\",");
				message1.append("}");
				DySmsUtils.sendMessage(message1.toString(), sysSetting.getContent(), SmsTemplate.sms_23);
			}
		}
		return this.entpDuiZhangDao.updateEntity(t);
	}

	public int removeEntpDuiZhang(EntpDuiZhang t) {
		return this.entpDuiZhangDao.deleteEntity(t);
	}

	public List<EntpDuiZhang> getEntpDuiZhangPaginatedList(EntpDuiZhang t) {
		return this.entpDuiZhangDao.selectEntityPaginatedList(t);
	}

	public List<EntpDuiZhang> getSettlementReport(EntpDuiZhang t) {
		return this.entpDuiZhangDao.selectSettlementReport(t);
	}

}
