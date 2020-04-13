package com.ebiz.webapp.web.struts.manager.customer;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author QiNengFei
 * @version 2017-11-29
 */

public class CommTczhPriceAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		// 商品列表
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_barcode_like = (String) dynaBean.get("comm_barcode_like");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		String critical = (String) dynaBean.get("critical");
		CommTczhPrice entity = new CommTczhPrice();

		entity.getMap().put("comm_barcode_like", comm_barcode_like);
		entity.getMap().put("comm_name_like", comm_name_like);
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);
		entity.getMap().put("critical", critical);

		if (null != userInfo.getOwn_entp_id()) {
			entity.getMap().put("own_entp_id", userInfo.getOwn_entp_id());
		}

		Integer recordCount = getFacade().getCommTczhPriceService().getCommTczhPriceCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommTczhPrice> entityList = super.getFacade().getCommTczhPriceService()
				.getCommTczhPricePaginatedList(entity);

		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		Map<String, Object> model = new HashMap<String, Object>();
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		// 商品列表
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		String code = (String) dynaBean.get("code");
		String critical = (String) dynaBean.get("critical");
		CommTczhPrice entity = new CommTczhPrice();

		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(begin_date)) {
			entity.getMap().put("begin_date", begin_date);
		}
		if (StringUtils.isNotBlank(end_date)) {
			entity.getMap().put("end_date", end_date);
		}
		if (StringUtils.isNotBlank(critical)) {
			entity.getMap().put("critical", critical);
		}

		if (null != userInfo.getOwn_entp_id()) {
			entity.getMap().put("own_entp_id", userInfo.getOwn_entp_id());

			List<CommTczhPrice> entityList = super.getFacade().getCommTczhPriceService()
					.getCommTczhPricePaginatedList(entity);
			model.put("entityList", entityList);

			String content = getFacade().getTemplateService().getContent("CommTczhPrice/list.ftl", model);
			// 下载文件出现乱码时，请参见此处
			String fname = EncryptUtilsV2.encodingFileName("商品规格信息.xls");
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
		}
		return null;
	}

	public ActionForward createEarlyWarningValue(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String id = (String) dynaBean.get("id");
		if (!GenericValidator.isInt(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
		CommTczhPrice entity = new CommTczhPrice();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getCommTczhPriceService().getCommTczhPrice(entity);

		entity.setQueryString(super.serialize(request, "id", "method"));

		super.copyProperties(form, entity);
		return new ActionForward("/../manager/customer/CommTczhPrice/earlyWarningValue.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		String code = "0", msg = "";

		if (StringUtils.isBlank(id)) {
			msg = "参数错误！";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		CommTczhPrice entity = new CommTczhPrice();
		super.copyProperties(entity, dynaBean);
		getFacade().getCommTczhPriceService().modifyCommTczhPrice(entity);

		code = "1";
		super.ajaxReturnInfo(response, code, msg, null);
		return null;
	}
}