package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class OrderBillQueryAction extends BaseAdminAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
 
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String trade_index_like = (String) dynaBean.get("trade_index_like");
		String pay_type = (String) dynaBean.get("pay_type");
		String add_user_name = (String) dynaBean.get("add_user_name");

		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		if (StringUtils.isNotBlank(pay_type)) {
			entity.getMap().put("pay_type_in", pay_type.trim());
		}
		if (StringUtils.isNotBlank(trade_index_like)) {
			entity.getMap().put("trade_index_like", trade_index_like.trim());
		}
		if (StringUtils.isNotBlank(add_user_name)) {
			entity.getMap().put("add_user_name_like", add_user_name.trim());
		}
		entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		entity.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex());// 商品订单
		entity.setIs_test(0);// 剔除测试订单

		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderInfo> entityList = getFacade().getOrderInfoService().getOrderInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		// request.setAttribute("orderTypeList", Keys.OrderType.values());
		request.setAttribute("payTypeList", Keys.PayType.values());
		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String trade_index_like = (String) dynaBean.get("trade_index_like");
		String pay_type = (String) dynaBean.get("pay_type");
		String add_user_name = (String) dynaBean.get("add_user_name");

		String code = (String) dynaBean.get("code");

		OrderInfo entity = new OrderInfo();
		super.copyProperties(entity, form);

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		if (StringUtils.isNotBlank(pay_type)) {
			entity.getMap().put("pay_type_in", pay_type.trim());
		}
		if (StringUtils.isNotBlank(trade_index_like)) {
			entity.getMap().put("trade_index_like", trade_index_like.trim());
		}
		if (StringUtils.isNotBlank(add_user_name)) {
			entity.getMap().put("add_user_name_like", add_user_name.trim());
		}

		entity.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
		entity.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex());// 商品订单

		List<OrderInfo> entityList = getFacade().getOrderInfoService().getOrderInfoPaginatedList(entity);

		request.setAttribute("entityList", entityList);
		request.setAttribute("payTypeList", Keys.PayType.values());

		model.put("entityList", entityList);
		model.put("title", "财务记账明细导出_日期" + sdFormat_ymd.format(new Date()));
		// 导出内容
		String content = getFacade().getTemplateService().getContent("OrderQuery/list.ftl", model);
		// String content = getFacade().getTemplateService().getContent("OrderQueryHz/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("财务记账明细导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward getRewardInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		String order_type = (String) dynaBean.get("order_type");

		if (StringUtils.isBlank(order_id) || StringUtils.isBlank(order_type)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserBiRecord entity = new UserBiRecord();
		entity.setLink_id(Integer.valueOf(order_id));
		entity.getMap().put("bi_get_types", "20,30,31,50,51,52,53,80,90,360,61,62,63,91,211");

		List<UserBiRecord> entityList = getFacade().getUserBiRecordService().getUserBiRecordList(entity);
		OrderInfo oi = new OrderInfo();
		oi.setId(Integer.valueOf(order_id));
		oi = super.getFacade().getOrderInfoService().getOrderInfo(oi);
		if (null != oi) {
			UserBiRecord userBiRecord = new UserBiRecord();
			userBiRecord.setBi_chu_or_ru(1);
			userBiRecord.setBi_no(oi.getRes_balance().divide(new BigDecimal(100)));
			userBiRecord.setAdd_date(oi.getFinish_date());
			entityList.add(userBiRecord);
		}

		request.setAttribute("entityList", entityList);

		request.setAttribute("biTypes", Keys.BiType.values());
		request.setAttribute("biGetTypes", Keys.BiGetType.values());

		return new ActionForward("/../manager/admin/OrderBillQuery/getRewardInfo.jsp");
	}
}