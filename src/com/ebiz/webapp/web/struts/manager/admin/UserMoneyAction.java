package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserScoreRecord;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.BiGetType;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class UserMoneyAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.search(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name = (String) dynaBean.get("user_name");
		String mobile = (String) dynaBean.get("mobile");
		String user_no = (String) dynaBean.get("user_no");
		String bi_type = (String) dynaBean.get("bi_type");
		String bi_get_type = (String) dynaBean.get("bi_get_type");
		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		String id = (String) dynaBean.get("id");

		UserInfo userInfo = new UserInfo();
		if (StringUtils.isNotBlank(id) && GenericValidator.isInt(id)) {
			userInfo.setId(Integer.valueOf(id));
		}
		if (StringUtils.isNotBlank(user_name)) {
			userInfo.setUser_name(user_name);
		}

		if (StringUtils.isNotBlank(mobile)) {
			userInfo.setMobile(mobile);
		}

		if (StringUtils.isNotBlank(user_no)) {
			userInfo.setUser_no(user_no);
		}
		userInfo.setIs_del(0);
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		super.copyProperties(form, userInfo);

		if (null == userInfo) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("dianzibi", userInfo.getBi_dianzi());
		request.setAttribute("huokuanbi", userInfo.getBi_huokuan());
		// request.setAttribute("xiaofeibi", userInfo.getBi_xiaofei());
		request.setAttribute("dianzibilock", userInfo.getBi_dianzi_lock());

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.getMap().put("begin_date", begin_date);
		userBiRecord.getMap().put("end_date", end_date);
		// userBiRecord.setLink_id();
		userBiRecord.setAdd_user_id(userInfo.getId());
		userBiRecord.setIs_del(0);
		userBiRecord.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
		if (StringUtils.isNotBlank(bi_type) && GenericValidator.isInt(bi_type)) {
			userBiRecord.setBi_type(Integer.valueOf(bi_type));
			dynaBean.set("bi_type", bi_type);
		}
		if (GenericValidator.isInt(bi_get_type)) {
			userBiRecord.setBi_get_type(Integer.valueOf(bi_get_type));
		}

		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(userBiRecord);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		userBiRecord.getRow().setFirst(pager.getFirstRow());
		userBiRecord.getRow().setCount(pager.getRowCount());

		List<UserBiRecord> userBiRecordList = super.getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(userBiRecord);

		if (null != userBiRecordList && userBiRecordList.size() > 0) {
			for (UserBiRecord temp : userBiRecordList) {
				userInfo = new UserInfo();
				// userInfo.setId(temp.getLink_id());
				userInfo.setId(temp.getAdd_user_id());
				userInfo.setIs_del(0);
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				temp.getMap().put("userName", userInfo.getUser_name());
				// 余额和扶贫金都需要显示对应的订单信息 300余额，500是扶贫金
				// if ((temp.getBi_type() == BiType.BI_TYPE_300.getIndex() || temp.getBi_type() == BiType.BI_TYPE_500
				// .getIndex()) && temp.getLink_id() != null) {
				//
				// }

				if (null != temp.getOrder_id()) {
					OrderInfo order = new OrderInfo();
					order.setId(temp.getOrder_id());
					order = super.getFacade().getOrderInfoService().getOrderInfo(order);
					temp.getMap().put("orderInfo", order);
				}
			}
		}
		request.setAttribute("userBiRecordList", userBiRecordList);

		request.setAttribute("biGetTypes", Keys.BiGetType.values());
		return mapping.findForward("list");
	}

	public ActionForward welfareList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String bi_get_type = (String) dynaBean.get("bi_get_type");
		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = "参数有误";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setId(Integer.valueOf(id));
		userInfo.setIs_del(0);
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		super.copyProperties(form, userInfo);

		if (null == userInfo) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserBiRecord userBiRecord = new UserBiRecord();
		userBiRecord.getMap().put("begin_date", begin_date);
		userBiRecord.getMap().put("end_date", end_date);
		userBiRecord.setAdd_user_id(userInfo.getId());
		userBiRecord.setIs_del(0);
		userBiRecord.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());
		userBiRecord.getMap().put("welfare_no_gt", 0);
		if (GenericValidator.isInt(bi_get_type)) {
			userBiRecord.setBi_get_type(Integer.valueOf(bi_get_type));
		}

		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(userBiRecord);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		userBiRecord.getRow().setFirst(pager.getFirstRow());
		userBiRecord.getRow().setCount(pager.getRowCount());

		List<UserBiRecord> userBiRecordList = super.getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(userBiRecord);

		if (null != userBiRecordList && userBiRecordList.size() > 0) {
			for (UserBiRecord temp : userBiRecordList) {
				// 添加对应的订单信息
				if (temp.getOrder_id() != null) {
					OrderInfo order = new OrderInfo();
					order.setId(temp.getOrder_id());
					order = super.getFacade().getOrderInfoService().getOrderInfo(order);
					temp.getMap().put("orderInfo", order);
				}
			}
		}
		request.setAttribute("userBiRecordList", userBiRecordList);
		List<BiGetType> biGetTypes = new ArrayList<BiGetType>();
		biGetTypes.add(Keys.BiGetType.BI_GET_TYPE_4000);
		biGetTypes.add(Keys.BiGetType.BI_OUT_TYPE_52);
		biGetTypes.add(Keys.BiGetType.BI_GET_TYPE_114);
		biGetTypes.add(Keys.BiGetType.BI_OUT_TYPE_10);
		biGetTypes.add(Keys.BiGetType.BI_GET_TYPE_160);

		request.setAttribute("biGetTypes", biGetTypes);
		return new ActionForward("/../manager/admin/UserMoney/welfare_list.jsp");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		return mapping.findForward("input");
	}

	public ActionForward saveScore(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String score = (String) dynaBean.get("score");

		BigDecimal curScore = new BigDecimal(0);
		Integer userLevel = null;
		if (StringUtils.isNotBlank(id) && GenericValidator.isInt(id) && GenericValidator.isInt(score)) {
			// 添加一条用户积分记录
			UserScoreRecord userScoreRecord = new UserScoreRecord();
			userScoreRecord.setLink_id(Integer.valueOf(id));
			userScoreRecord.setHd_score(new BigDecimal(score));
			userScoreRecord.setAdd_user_id(Integer.valueOf(id));
			userScoreRecord.setAdd_date(new Date());
			super.getFacade().getUserScoreRecordService().createUserScoreRecord(userScoreRecord);

			// 更新用户当前积分
			UserInfo userInfo = new UserInfo();
			userInfo.setId(Integer.valueOf(id));
			userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
			curScore = userInfo.getCur_score();
			userLevel = userInfo.getUser_level();

			curScore = curScore.add(new BigDecimal(score));
			userInfo = new UserInfo();
			userInfo.setId(Integer.valueOf(id));
			userInfo.setCur_score(curScore);

			// 判断是否达到升级标准
			if (userLevel <= 3) {

			} else {

			}

		}

		return mapping.findForward("list");
	}

	public ActionForward checkUserExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_name = (String) dynaBean.get("user_name");
		String mobile = (String) dynaBean.get("mobile");
		String user_no = (String) dynaBean.get("user_no");

		UserInfo entity1 = null;
		UserInfo entity2 = null;
		UserInfo entity3 = null;
		String flag = "1";
		if (StringUtils.isNotBlank(user_name)) {
			entity1 = new UserInfo();
			entity1.setUser_name(user_name);
			entity1.setIs_del(0);
			entity1 = super.getFacade().getUserInfoService().getUserInfo(entity1);
		}

		if (StringUtils.isNotBlank(mobile)) {
			entity2 = new UserInfo();
			entity2.setMobile(mobile);
			entity2.setIs_del(0);
			entity2 = super.getFacade().getUserInfoService().getUserInfo(entity2);
		}

		if (StringUtils.isNotBlank(user_no)) {
			entity3 = new UserInfo();
			entity3.setUser_no(user_no);
			entity3.setIs_del(0);
			entity3 = super.getFacade().getUserInfoService().getUserInfo(entity3);
		}
		if (null != entity1 || null != entity2 || null != entity3) {
			flag = "0";
		}
		super.renderJson(response, flag);
		return null;
	}
}