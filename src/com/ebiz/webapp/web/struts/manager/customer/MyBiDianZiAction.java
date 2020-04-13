package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.BiGetType;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

public class MyBiDianZiAction extends BaseCustomerAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		// super.setPublicInfoListWithEntpAndCustomer(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			String msg = "用户名不存在或者已经被删除！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Index.do?method=welcome'}");
			return null;
		}
		super.copyProperties(form, entity);

		BaseData baseData904 = this.getBaseDataFromApplation(Keys.BASE_DATA_ID.BASE_DATA_ID_904.getIndex());
		request.setAttribute("baseData904", baseData904);

		return mapping.findForward("view");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		// super.setPublicInfoListWithEntpAndCustomer(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		// String bi_chu_or_ru = (String) dynaBean.get("bi_chu_or_ru");
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		UserBiRecord entity = new UserBiRecord();
		super.copyProperties(entity, form);
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		entity.getMap().put("order_by_info", "add_date desc,");
		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserBiRecord> userBiRecordlList = super.getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(entity);
		request.setAttribute("userBiRecordlList", userBiRecordlList);

		List<BiGetType> biGetTypesList = new ArrayList<Keys.BiGetType>();
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1001.getSon_type().toString()));
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1002.getSon_type().toString()));
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1003.getSon_type().toString()));
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1004.getSon_type().toString()));
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_GET_TYPE_1005.getSon_type().toString()));

		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_OUT_TYPE_10.getSon_type().toString()));
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_OUT_TYPE_50.getSon_type().toString()));
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_OUT_TYPE_90.getSon_type().toString()));
		biGetTypesList.add(Keys.BiGetType.valueOf(Keys.BiGetType.BI_OUT_TYPE_150.getSon_type().toString()));
		request.setAttribute("biGetTypes", biGetTypesList);

		request.setAttribute("orderTypeShowList", Keys.OrderType.values());

		return mapping.findForward("list");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		Map<String, Object> model = new HashMap<String, Object>();
		DynaBean dynaBean = (DynaBean) form;
		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		String code = (String) dynaBean.get("code");

		UserBiRecord entity = new UserBiRecord();
		super.copyProperties(entity, form);
		entity.setAdd_user_id(userInfo.getId());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setBi_type(Keys.BiType.BI_TYPE_100.getIndex());

		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		entity.getMap().put("order_by_info", "add_date desc,");

		List<UserBiRecord> userBiRecordlList = super.getFacade().getUserBiRecordService().getUserBiRecordList(entity);
		if (null != userBiRecordlList && userBiRecordlList.size() > 0) {
			for (UserBiRecord temp : userBiRecordlList) {
				for (BiGetType temp2 : Keys.BiGetType.values()) {
					if (temp.getBi_get_type().intValue() == temp2.getIndex()) {
						temp.getMap().put("bi_get_name", temp2.getName());
						break;
					}
				}
			}
		}

		model.put("entityList", userBiRecordlList);
		model.put("biGetTypes", Keys.BiGetType.values());
		model.put("title", "我的余额" + sdFormat_ymd.format(new Date()));

		String content = getFacade().getTemplateService().getContent("MyBiDianZi/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("我的余额" + sdFormat_ymd.format(new Date()) + ".xls");

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
