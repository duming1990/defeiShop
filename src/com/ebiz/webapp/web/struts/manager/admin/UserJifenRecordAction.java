package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.BiGetType;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class UserJifenRecordAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String biGetType = (String) dynaBean.get("biGetType");
		String add_user_name = (String) dynaBean.get("add_user_name");
		String add_uname = (String) dynaBean.get("add_uname");
		String trade_index = (String) dynaBean.get("trade_index");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");

		UserBiRecord entity = new UserBiRecord();
		super.copyProperties(entity, form);

		entity.setIs_del(0);
		if (null != biGetType && biGetType != "") {
			entity.getMap().put("bi_get_type", biGetType);
		} else {
			String bi_get_type = Keys.BiGetType.BI_GET_TYPE_1002.getIndex() + ","
					+ Keys.BiGetType.BI_GET_TYPE_1003.getIndex() + "," + Keys.BiGetType.BI_GET_TYPE_1004.getIndex()
					+ "," + Keys.BiGetType.BI_GET_TYPE_1005.getIndex();
			entity.getMap().put("bi_get_type", bi_get_type);
		}
		if (null != trade_index) {
			entity.getMap().put("trade_index", trade_index);
		}
		if (null != add_user_name) {
			entity.getMap().put("add_user_name", add_user_name);
		}
		if (null != add_uname) {
			entity.getMap().put("add_uname", add_uname);
		}
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordRewardCount(entity);
		pager.init(recordCount.longValue(), 10, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserBiRecord> list = getFacade().getUserBiRecordService().getUserBiRecordRewardList(entity);
		request.setAttribute("userbiRecordList", list);

		List<BiGetType> biGetTypes = new ArrayList<Keys.BiGetType>();
		biGetTypes.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1002.getSon_type().toString()));
		biGetTypes.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1003.getSon_type().toString()));
		biGetTypes.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1004.getSon_type().toString()));
		biGetTypes.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1005.getSon_type().toString()));
		request.setAttribute("biGetTypes", biGetTypes);
		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String biGetType = (String) dynaBean.get("biGetType");
		String add_user_name = (String) dynaBean.get("add_user_name");
		String add_uname = (String) dynaBean.get("add_uname");
		String trade_index = (String) dynaBean.get("trade_index");
		String code = (String) dynaBean.get("code");

		UserBiRecord entity = new UserBiRecord();
		super.copyProperties(entity, form);

		entity.setIs_del(0);
		if (null != biGetType && biGetType != "") {
			entity.getMap().put("bi_get_type", biGetType);
		} else {
			String bi_get_type = Keys.BiGetType.BI_GET_TYPE_1002.getIndex() + ","
					+ Keys.BiGetType.BI_GET_TYPE_1003.getIndex() + "," + Keys.BiGetType.BI_GET_TYPE_1004.getIndex()
					+ "," + Keys.BiGetType.BI_GET_TYPE_1005.getIndex();
			entity.getMap().put("bi_get_type", bi_get_type);
		}
		if (null != trade_index) {
			entity.getMap().put("trade_index", trade_index);
		}
		if (null != add_user_name) {
			entity.getMap().put("add_user_name", add_user_name);
		}
		if (null != add_uname) {
			entity.getMap().put("add_uname", add_uname);
		}
		List<UserBiRecord> entityList = getFacade().getUserBiRecordService().getUserBiRecordRewardList(entity);

		request.setAttribute("userbiRecordList", entityList);
		model.put("entityList", entityList);
		model.put("title", "奖励明细导出_日期" + sdFormat_ymd.format(new Date()));
		// 导出内容
		String content = getFacade().getTemplateService().getContent("UserJifenRecord/list.ftl", model);

		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("奖励明细导出_日期" + sdFormat_ymd.format(new Date()) + ".xls");

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

}
