package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;

/**
 * @author Wu,Yang2
 * @version 2010-09-26
 */
public class IndexAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("================index========================");
		UserInfo ui = super.getUserInfoFromSession(request);

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		ui = super.getUserInfo(ui.getId());

		// 这个地方判断如果是普通会员 则直接调转到IndexCustomer
		if (ui.getUser_type().intValue() == Keys.UserType.USER_TYPE_2.getIndex()) {
			return new ActionForward("/customer/IndexCustomer.do", true);
		}

		super.setUserInfoToSession(request, ui);// 在塞到session中

		SysModule sysAll = new SysModule();
		sysAll.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		sysAll.getMap().put("user_id", ui.getId());
		sysAll.getMap().put("query_customer_public", "true");
		List<SysModule> sysAllList = getFacade().getSysModuleService().getSysModuleList(sysAll);

		request.setAttribute("sysModuleParList", super.getManagerSysModuleList(sysAllList));

		// 商品库存预警
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		int commCount = 0;
		if (null != ui.getOwn_entp_id()) {
			commTczhPrice.getMap().put("own_entp_id", ui.getOwn_entp_id());

			List<CommTczhPrice> commTczhPriceList = super.getFacade().getCommTczhPriceService()
					.getCommTczhPricePaginatedList(commTczhPrice);
			for (CommTczhPrice ctp : commTczhPriceList) {
				if (ctp.getEarly_warning_value() >= ctp.getInventory()) {
					commCount++;
				}
			}
		}
		request.setAttribute("commCount", commCount);
		return mapping.findForward("index");
	}

	public ActionForward welcome(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo userInfo = super.getUserInfo(ui.getId());

		super.setUserInfoToSession(request, userInfo);

		int percent = 100;
		if (StringUtils.isBlank(userInfo.getPassword())) {
			percent -= 20;
		}
		if (StringUtils.isBlank(userInfo.getMobile())) {
			percent -= 20;
		}
		if (StringUtils.isBlank(userInfo.getEmail())) {
			percent -= 20;
		}
		if (StringUtils.isBlank(userInfo.getPassword_pay())) {
			percent -= 20;
		}
		if (userInfo.getIs_set_security() == 0) {
			percent -= 20;
		}
		super.setUserInfoBiLockDivid100(userInfo);
		request.setAttribute("percent", percent);
		request.setAttribute("is_fuwu", userInfo.getIs_fuwu());
		// 用户等级
		BaseData uiBaseData = new BaseData();
		uiBaseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		uiBaseData.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		uiBaseData.setId(userInfo.getUser_level());
		uiBaseData = getFacade().getBaseDataService().getBaseData(uiBaseData);

		int user_level = uiBaseData.getPre_number3();
		request.setAttribute("user_level", user_level);

		if (null != userInfo.getOwn_entp_id()) {
			// 查询今日订单
			OrderInfo orderInfoToday = new OrderInfo();
			orderInfoToday.setEntp_id(userInfo.getOwn_entp_id());
			orderInfoToday.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
			orderInfoToday.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
			orderInfoToday.getMap().put("st_add_date", sdFormat_ymd.format(new Date()));
			orderInfoToday.getMap().put("en_add_date", sdFormat_ymd.format(new Date()));
			int todayOrderCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfoToday);
			request.setAttribute("todayOrderCount", todayOrderCount);
		}

		// 销售金额
		OrderInfo orderInfoTodaySum = new OrderInfo();
		orderInfoTodaySum.setEntp_id(userInfo.getOwn_entp_id());
		orderInfoTodaySum.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
		orderInfoTodaySum.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfoTodaySum = super.getFacade().getOrderInfoService().getOrderInfoStatisticaSum(orderInfoTodaySum);
		request.setAttribute("orderInfoTodaySum", orderInfoTodaySum);

		// 待付款 order_state=0
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setAdd_user_id(userInfo.getId());
		orderInfo.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex() + "");
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());
		int count_daifukuan = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("count_daifukuan", count_daifukuan);

		// 待确认收货 order_state=20
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_20.getIndex());
		int count_daiquerenshouhuo = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("count_daiquerenshouhuo", count_daiquerenshouhuo);
		// 已完成 order_state=50
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_40.getIndex());
		int count_yiwancheng = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("count_yiwancheng", count_yiwancheng);

		// 近期订单
		orderInfo.setOrder_state(null);
		orderInfo.getRow().setCount(5);
		List<OrderInfo> orderInfoZuijinList = getFacade().getOrderInfoService().getOrderInfoList(orderInfo);
		request.setAttribute("orderInfoZuijinList", orderInfoZuijinList);

		// 近期收藏
		ScEntpComm scEntpComm = new ScEntpComm();
		scEntpComm.setAdd_user_id(userInfo.getId());
		scEntpComm.setIs_del(0);
		scEntpComm.setSc_type(2);
		scEntpComm.getRow().setCount(5);
		List<ScEntpComm> favList = super.getFacade().getScEntpCommService().getScEntpCommList(scEntpComm);
		if (null != favList && favList.size() > 0) {
			for (ScEntpComm sec : favList) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(sec.getLink_id());
				commInfo.setIs_del(0);
				commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
				if (null != commInfo) {
					sec.getMap().put("img_url", commInfo.getMain_pic());
					sec.getMap().put("price", commInfo.getSale_price());
				}
			}
		}
		request.setAttribute("favList", favList);

		// 村统计
		if (1 == userInfo.getIs_fuwu()) {// 县域合伙人

			ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
			serviceCenterInfo.setAdd_user_id(userInfo.getId());
			serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			serviceCenterInfo.setAudit_state(1);
			serviceCenterInfo.getRow().setCount(1);
			serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
			if (null != serviceCenterInfo) {
				// 1、村总数
				VillageInfo villageInfo = new VillageInfo();
				villageInfo.getMap().put("country", serviceCenterInfo.getP_index().toString());// 县域
				villageInfo.setAudit_state(1);
				villageInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				int village_count = super.getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
				request.setAttribute("village_count", village_count);
				// 2、今日新增村数
				villageInfo.getMap().put("st_audit_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
				villageInfo.getMap().put("en_audit_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
				int today_village_count = super.getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
				request.setAttribute("today_village_count", today_village_count);
				// 3、村成员总数
				VillageMember villageMember = new VillageMember();
				villageMember.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				villageMember.setAudit_state(1);
				villageMember.getMap().put("country", serviceCenterInfo.getP_index().toString());// 县域
				int member_count = super.getFacade().getVillageMemberService()
						.getVillageMemberCountByPIndex(villageMember);
				request.setAttribute("member_count", member_count);
				// 4、今日新增村成员数
				villageMember.getMap().put("st_audit_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
				villageMember.getMap().put("en_audit_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
				int today_member_count = super.getFacade().getVillageMemberService()
						.getVillageMemberCountByPIndex(villageMember);
				request.setAttribute("today_member_count", today_member_count);

				villageInfo = new VillageInfo();
				villageInfo.getMap().put("country", serviceCenterInfo.getP_index().toString());// 县域
				villageInfo.setAudit_state(0);// 待审核
				villageInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				int ds_village_count = super.getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
				request.setAttribute("ds_village_count", ds_village_count);

				// 产品总数
				CommInfo commInfo = new CommInfo();
				commInfo.setIs_del(0);
				commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
				commInfo.setOwn_entp_id(userInfo.getOwn_entp_id());
				Integer commInfoCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfo);
				request.setAttribute("commInfoCount", commInfoCount);

				// 待审核的商品数量
				CommInfo commInfoDsh = new CommInfo();
				commInfoDsh.setIs_del(0);
				commInfoDsh.setAudit_state(0);
				commInfoDsh.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
				commInfoDsh.setOwn_entp_id(userInfo.getOwn_entp_id());
				Integer commInfoDshCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoDsh);
				request.setAttribute("commInfoDshCount", commInfoDshCount);

				// 订单总数
				OrderInfo oi = new OrderInfo();
				oi.setEntp_id(userInfo.getOwn_entp_id());
				oi.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
				Integer orderInfoCount = super.getFacade().getOrderInfoService().getOrderInfoCount(oi);
				request.setAttribute("orderInfoCount", orderInfoCount);

				oi.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
				Integer orderInfoDfhCount = super.getFacade().getOrderInfoService().getOrderInfoCount(oi);
				request.setAttribute("orderInfoDfhCount", orderInfoDfhCount);

				// 扶贫商品数量
				CommInfo commInfoFp = new CommInfo();
				commInfoFp.setIs_aid(1);
				commInfoFp.setIs_del(0);
				commInfoFp.setAudit_state(1);
				commInfoFp.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
				commInfoFp.setOwn_entp_id(userInfo.getOwn_entp_id());
				Integer commInfoFpCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoFp);
				request.setAttribute("commInfoFpCount", commInfoFpCount);
				// 扶贫对象数量
				PoorInfo poorInfo = new PoorInfo();
				if (null != ui.getOwn_entp_id()) {
					poorInfo.getMap().put("own_entp_id", userInfo.getOwn_entp_id());
				} else {
					poorInfo.getMap().put("own_entp_id", "-9999");
				}
				List<PoorInfo> list = getFacade().getPoorInfoService().getPoorInfoListWithAidMoney(poorInfo);
				request.setAttribute("entpPoorCount", list.size());
			}
		} else if (1 == userInfo.getIs_village()) {// 村站
			// 3、村成员总数
			VillageMember villageMember = new VillageMember();
			villageMember.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			villageMember.setAudit_state(1);
			villageMember.setVillage_id(userInfo.getOwn_village_id());
			int member_count = super.getFacade().getVillageMemberService().getVillageMemberCount(villageMember);
			request.setAttribute("member_count", member_count);
			// 4、今日新增村成员数
			villageMember.getMap().put("st_audit_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
			villageMember.getMap().put("en_audit_date", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
			int today_member_count = super.getFacade().getVillageMemberService().getVillageMemberCount(villageMember);
			request.setAttribute("today_member_count", today_member_count);

			villageMember = new VillageMember();
			villageMember.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			villageMember.setAudit_state(0);
			villageMember.setVillage_id(userInfo.getOwn_village_id());
			int dsh_member_count = super.getFacade().getVillageMemberService().getVillageMemberCount(villageMember);
			request.setAttribute("dsh_member_count", dsh_member_count);

			// 订单总数
			OrderInfo oi = new OrderInfo();
			oi.setVillage_id(userInfo.getOwn_village_id());
			oi.setOrder_type(Keys.OrderType.ORDER_TYPE_7.getIndex());
			oi.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
			Integer orderInfoCount = super.getFacade().getOrderInfoService().getOrderInfoCount(oi);
			request.setAttribute("orderInfoCount", orderInfoCount);

			oi.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
			Integer orderInfoDfhCount = super.getFacade().getOrderInfoService().getOrderInfoCount(oi);
			request.setAttribute("orderInfoDfhCount", orderInfoDfhCount);

			PoorInfo poorInfo = new PoorInfo();
			poorInfo.setVillage_id(userInfo.getOwn_village_id());
			poorInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			Integer poorInfoCount = super.getFacade().getPoorInfoService().getPoorInfoCount(poorInfo);
			request.setAttribute("poorInfoCount", poorInfoCount);

			poorInfo.setAudit_state(0);// 待审核
			Integer poorInfoDshCount = super.getFacade().getPoorInfoService().getPoorInfoCount(poorInfo);
			request.setAttribute("poorInfoDshCount", poorInfoDshCount);
		} else if (1 == userInfo.getIs_entp()) {
			// 产品总数
			CommInfo commInfo = new CommInfo();
			commInfo.setIs_del(0);
			commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
			commInfo.setOwn_entp_id(userInfo.getOwn_entp_id());
			Integer commInfoCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfo);
			request.setAttribute("commInfoCount", commInfoCount);

			// 待审核的商品数量
			CommInfo commInfoDsh = new CommInfo();
			commInfoDsh.setIs_del(0);
			commInfoDsh.setAudit_state(0);
			commInfoDsh.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
			commInfoDsh.setOwn_entp_id(userInfo.getOwn_entp_id());
			Integer commInfoDshCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoDsh);
			request.setAttribute("commInfoDshCount", commInfoDshCount);

			// 订单总数
			OrderInfo oi = new OrderInfo();
			oi.setEntp_id(userInfo.getOwn_entp_id());
			oi.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
			Integer orderInfoCount = super.getFacade().getOrderInfoService().getOrderInfoCount(oi);
			request.setAttribute("orderInfoCount", orderInfoCount);

			oi.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
			Integer orderInfoDfhCount = super.getFacade().getOrderInfoService().getOrderInfoCount(oi);
			request.setAttribute("orderInfoDfhCount", orderInfoDfhCount);

			// 扶贫商品数量
			CommInfo commInfoFp = new CommInfo();
			commInfoFp.setIs_aid(1);
			commInfoFp.setIs_del(0);
			commInfoFp.setAudit_state(1);
			commInfoFp.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
			commInfoFp.setOwn_entp_id(userInfo.getOwn_entp_id());
			Integer commInfoFpCount = super.getFacade().getCommInfoService().getCommInfoCount(commInfoFp);
			request.setAttribute("commInfoFpCount", commInfoFpCount);
			// 扶贫对象数量
			PoorInfo poorInfo = new PoorInfo();
			if (null != ui.getOwn_entp_id()) {
				poorInfo.getMap().put("own_entp_id", userInfo.getOwn_entp_id());
			} else {
				poorInfo.getMap().put("own_entp_id", "-9999");
			}
			List<PoorInfo> list = getFacade().getPoorInfoService().getPoorInfoListWithAidMoney(poorInfo);
			request.setAttribute("entpPoorCount", list.size());
		}

		// 判断是否有未审核通过的申请
		super.setApplyEntpIsAudit(request, ui);
		request.setAttribute("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));
		return new ActionForward("/../manager/customer/welcome.jsp");
	}
}
