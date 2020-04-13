package com.ebiz.webapp.web.struts.m;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.BiGetType;
import com.ebiz.webapp.web.util.DateTools;

public class MMyQianBaoAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "我的钱包");

		DynaBean dynaBean = (DynaBean) form;
		String par_id = (String) dynaBean.get("par_id");
		if (StringUtils.isNotBlank(par_id)) {
			getsonSysModuleListForMobile(request, dynaBean);
			return new ActionForward("/../m/MMyQianBao/index.jsp");
		} else {
			return list(mapping, form, request, response);
		}
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		super.getModNameForMobile(request);

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("userInfo", userInfo);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String bi_type = (String) dynaBean.get("bi_type");

		if (StringUtils.isNotBlank(id)) {

			UserBiRecord entity = new UserBiRecord();
			entity.setId(Integer.valueOf(id));
			entity.setBi_chu_or_ru(1);
			entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<UserBiRecord> list = super.getFacade().getUserBiRecordService().getUserBiRecordList(entity);
			request.setAttribute("entityList", list);

		} else {
			// 收入
			List<UserBiRecord> incommRecord = new ArrayList<UserBiRecord>();
			UserBiRecord entity = new UserBiRecord();
			if (StringUtils.isNotBlank(bi_type)) {
				entity.setBi_type(Integer.valueOf(bi_type));
			} else {
				entity.getMap().put("bi_type_in", "100,200");
			}
			entity.setAdd_user_id(ui.getId());
			// entity.setBi_chu_or_ru(1);
			entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -1);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

			entity.getMap().put("bi_no_gt", 0);
			entity.getMap().put("begin_date", df.format(calendar.getTime()));
			entity.getMap().put("order_by", "add_date desc");
			List<UserBiRecord> userBiRecordList = super.getFacade().getUserBiRecordService()
					.getUserBiRecordList(entity);
			if (userBiRecordList != null && userBiRecordList.size() > 0) {
				for (UserBiRecord temp : userBiRecordList) {
					if (temp.getBi_type() == Keys.BiType.BI_TYPE_200.getIndex()) {
						// 如果是待返余额需判断订单状态
						OrderInfo order = new OrderInfo();
						order.setId(temp.getOrder_id());
						order = getFacade().getOrderInfoService().getOrderInfo(order);
						if (order != null && (10 <= order.getOrder_state() && order.getOrder_state() < 50)) {
							temp.getMap().put("trade_index", order.getTrade_index());
							temp.getMap().put("order_date", order.getOrder_date());
							temp.getMap().put("qrsh_date", order.getQrsh_date());
							String xf_user_name = "******";
							if (order.getAdd_user_name().length() >= 11) {
								xf_user_name = order.getAdd_user_name().substring(0, 3) + "****"
										+ order.getAdd_user_name().substring(7);
							} else if (order.getAdd_user_name().length() > 3) {
								xf_user_name = order.getAdd_user_name().substring(0, 3) + "****";
							}
							temp.getMap().put("xf_user_name", xf_user_name);
							incommRecord.add(temp);
						}
					} else {
						incommRecord.add(temp);
					}
				}
			}
			request.setAttribute("entityList", incommRecord);
			// 支出
			// UserBiRecord uRecord = new UserBiRecord();
			// uRecord.setAdd_user_id(ui.getId());
			// uRecord.setBi_chu_or_ru(-1);
			// uRecord.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
			// uRecord.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			// uRecord.getMap().put("bi_no_gt", 0);
			// uRecord.getMap().put("begin_date", df.format(calendar.getTime()));
			// uRecord.getMap().put("order_by", "add_date desc");
			// List<UserBiRecord> uRecordList = super.getFacade().getUserBiRecordService().getUserBiRecordList(uRecord);
			//
			// request.setAttribute("uRecordList", uRecordList);
		}
		request.setAttribute("biGetTypes", Keys.BiGetType.values());
		return new ActionForward("/../m/MMyQianBao/my_wallet.jsp");
	}

	public ActionForward welfareList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		super.getModNameForMobile(request);

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("userInfo", userInfo);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isNotBlank(id)) {
			UserBiRecord entity = new UserBiRecord();
			entity.setId(Integer.valueOf(id));
			entity.setBi_chu_or_ru(1);
			entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<UserBiRecord> list = super.getFacade().getUserBiRecordService().getUserBiRecordList(entity);
			request.setAttribute("entityList", list);

		} else {
			UserBiRecord entity = new UserBiRecord();
			entity.setAdd_user_id(userInfo.getId());
			entity.setIs_del(0);
			entity.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
			entity.getMap().put("welfare_no_gt", 0);
			entity.getMap().put("order_by", "id desc");
			List<UserBiRecord> entityList = super.getFacade().getUserBiRecordService().getUserBiRecordList(entity);
			request.setAttribute("entityList", entityList);
		}
		request.setAttribute("biGetTypes", Keys.BiGetType.values());
		return new ActionForward("/../m/MMyQianBao/welfare_ist.jsp");
	}

	public ActionForward huoKuanBi(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		super.getModNameForMobile(request);

		// 用户信息
		UserInfo userInfo = new UserInfo();
		userInfo.setId(ui.getId());
		userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			super.showTipMsg(mapping, form, request, response, msg);
		}
		request.setAttribute("userInfo", userInfo);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String id = (String) dynaBean.get("id");

		if (StringUtils.isNotBlank(id)) {

			UserBiRecord entity = new UserBiRecord();
			entity.setId(Integer.valueOf(id));
			entity.setBi_chu_or_ru(1);
			entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<UserBiRecord> list = super.getFacade().getUserBiRecordService().getUserBiRecordList(entity);
			request.setAttribute("entityList", list);

		} else {

			UserBiRecord entity = new UserBiRecord();
			entity.setAdd_user_id(ui.getId());
			entity.setBi_chu_or_ru(1);
			entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			entity.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.MONTH, -1);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			// entity.getMap().put("begin_date", df.format(calendar.getTime()));
			entity.getMap().put("order_by", "add_date desc");
			// entity.getMap().put("bi_get_types", "150,112,220,380");
			List<UserBiRecord> userBiRecordList = super.getFacade().getUserBiRecordService()
					.getUserBiRecordList(entity);

			request.setAttribute("entityList", userBiRecordList);

			UserBiRecord uRecord = new UserBiRecord();
			uRecord.setAdd_user_id(ui.getId());
			uRecord.setBi_chu_or_ru(-1);
			uRecord.setBi_type(Keys.BiType.BI_TYPE_300.getIndex());
			uRecord.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			// uRecord.getMap().put("begin_date", df.format(calendar.getTime()));
			uRecord.getMap().put("order_by", "add_date desc");
			// uRecord.getMap().put("bi_get_types", "100,-30,-60,-120,-160,-400");
			List<UserBiRecord> uRecordList = super.getFacade().getUserBiRecordService().getUserBiRecordList(uRecord);

			request.setAttribute("uRecordList", uRecordList);
		}
		// dynaBean.set("pageSize", pageSize);

		request.setAttribute("biGetTypes", Keys.BiGetType.values());
		// return mapping.findForward("list");
		return new ActionForward("/../m/MMyQianBao/my_huokuanbi.jsp");
	}

	public ActionForward walletList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);
		super.getModNameForMobile(request);
		request.setAttribute("canSearch", true);
		request.setAttribute("header_title", "红包记录");
		String bi_get_type = (String) dynaBean.get("bi_get_type");
		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		Pager pager = (Pager) dynaBean.get("pager");

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		UserBiRecord entity = new UserBiRecord();
		entity.setAdd_user_id(ui.getId());
		entity.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
		if (StringUtils.isNotBlank(bi_get_type)) {
			entity.setBi_get_type(Integer.valueOf(bi_get_type));
		} else {
			entity.getMap().put("bi_get_types", "20,30,31,51,52,53,61,62,63,70,80,90,91,111,130,160,200,360");
		}
		entity.getMap().put("order_by", "ID desc");
		if (StringUtils.isNotBlank(begin_date)) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			request.setAttribute("begin_date", df.parse(begin_date));
		}
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);
		logger.info("===========begin_date==============" + begin_date);
		Integer pageSize = 10;
		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserBiRecord> UserBiRecordList = super.getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(entity);
		if (null != UserBiRecordList && UserBiRecordList.size() > 0) {

			for (UserBiRecord dm : UserBiRecordList) {
				if (dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_130.getIndex()
						|| dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_111.getIndex()
						|| dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_160.getIndex()) {
					dm.getMap().put("laiyuan", "平台");
				} else if (dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_140.getIndex()) {
					dm.getMap().put("laiyuan", "微信、支付宝");
				} else {
					dm.getMap().put("laiyuan", dm.getAdd_uname());
				}
			}

			request.setAttribute("UserBiRecordList", UserBiRecordList);
			if (UserBiRecordList.size() == Integer.valueOf(pageSize)) {
				request.setAttribute("appendMore", 1);
			}
			dynaBean.set("pageSize", pageSize);
		}

		request.setAttribute("biGetTypes", Keys.BiGetType.values());
		request.setAttribute("go_home", true);
		request.setAttribute("d_year", new Date().getYear() + 1900);
		return new ActionForward("/../m/MMyQianBao/my_wallet_list.jsp");
	}

	public ActionForward getListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		// getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserBiRecord entity = new UserBiRecord();
		entity.setAdd_user_id(ui.getId());
		entity.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
		entity.getMap().put("bi_get_types", "20,30,31,51,52,53,61,62,63,70,80,90,111,130,160,200,360");
		entity.getMap().put("order_by", "ID desc");

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);
		logger.info("===========begin_date==============" + begin_date);
		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(entity);
		pager.init(recordCount.longValue(), new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());
		List<UserBiRecord> UserBiRecordList = super.getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		// String ctx = super.getCtxPath(request);
		if ((null != UserBiRecordList) && (UserBiRecordList.size() > 0)) {
			for (UserBiRecord usr : UserBiRecordList) {
				JSONObject map = new JSONObject();

				String score_type_name = "";
				for (BiGetType temp : Keys.BiGetType.values()) {
					if (usr.getBi_get_type() == temp.getIndex()) {
						score_type_name = temp.getName();
					}
				}
				map.put("bi_get_type_name", score_type_name);
				map.put("wallet_money", usr.getBi_no());
				map.put("user_bi_record", usr.getId());
				map.put("add_date", DateTools.getStringDate(usr.getAdd_date(), "yyyy-MM-dd HH：mm"));

				for (UserBiRecord dm : UserBiRecordList) {
					if (dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_130.getIndex()
							|| dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_111.getIndex()
							|| dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_160.getIndex()) {
						map.put("laiyuan", "平台");
					} else if (dm.getBi_get_type() == Keys.BiGetType.BI_GET_TYPE_140.getIndex()) {
						map.put("laiyuan", "微信、支付宝");
					} else {
						map.put("laiyuan", dm.getAdd_uname());
					}

				}

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (UserBiRecordList.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		logger.info("===datas:{}", datas.toString());
		super.renderJson(response, datas.toString());
		return null;

	}
}