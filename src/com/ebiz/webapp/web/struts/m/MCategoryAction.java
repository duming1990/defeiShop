package com.ebiz.webapp.web.struts.m;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.BaseClass;

/**
 * @author Wu,Yang
 * @version 2011-12-14
 */
public class MCategoryAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("footerFlag", 1);

		return mapping.findForward("list");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "1", msg = "";
		JSONObject datas = new JSONObject();
		// 取分类列表一级和二级的
		BaseClass basePdClass = new BaseClass();
		basePdClass.setIs_del(0);
		basePdClass.setPar_id(1);
		basePdClass.setCls_scope(1);
		List<BaseClass> basePdClass1List = super.getFacade().getBaseClassService().getBaseClassList(basePdClass);
		if (null != basePdClass1List && basePdClass1List.size() > 0) {
			for (BaseClass bpc : basePdClass1List) {
				BaseClass entity = new BaseClass();
				entity.setIs_del(0);
				entity.setPar_id(bpc.getCls_id());
				entity.setCls_scope(1);
				List<BaseClass> basePdClass2List = super.getFacade().getBaseClassService().getBaseClassList(entity);
				bpc.getMap().put("basePdClass2List", basePdClass2List);
			}
		}
		datas.put("basePdClass1List", basePdClass1List);
		super.returnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");
		if (StringUtils.isNotBlank(cls_id)) {

			BaseClass basePdClassEntity = new BaseClass();
			basePdClassEntity.setIs_del(0);
			basePdClassEntity.setCls_scope(1);
			basePdClassEntity.setCls_id(Integer.valueOf(cls_id));
			basePdClassEntity = super.getFacade().getBaseClassService().getBaseClass(basePdClassEntity);
			if (null != basePdClassEntity) {
				// 取出二级类的子类
				BaseClass basePdClass = new BaseClass();
				basePdClass.setIs_del(0);
				basePdClass.setPar_id(Integer.valueOf(cls_id));
				basePdClass.setCls_scope(1);
				List<BaseClass> basePdClass3List = super.getFacade().getBaseClassService()
						.getBaseClassList(basePdClass);
				request.setAttribute("basePdClass3List", basePdClass3List);
			}
		}

		request.setAttribute("header_title", "商品分类");

		return mapping.findForward("view");

	}
}
