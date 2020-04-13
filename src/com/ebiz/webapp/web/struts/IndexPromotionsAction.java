package com.ebiz.webapp.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseLink;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class IndexPromotionsAction extends BaseWebAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String htype = (String) dynaBean.get("link_type");
		if (StringUtils.isBlank(htype)) {
			htype = "0";
		}
		dynaBean.set("htype", htype);

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		List<BaseLink> baseLinkList = super.getBaseLinkList(800, 5, "no_null_image_path");
		request.setAttribute("imageLeftListJsonString",
				super.getPptJsonStringByLinkList(baseLinkList, "image_path", "title", null, "link_url", null));

		// 右侧产品
		request.setAttribute("BaseLink801List", super.getBaseLinkList(801, 4, "no_null_image_path"));
		// 促销商品
		request.setAttribute("BaseLink50List", super.getBaseLinkList(50, 6, "no_null_image_path"));
		// 热卖商品
		request.setAttribute("BaseLink51List", super.getBaseLinkList(51, 6, "no_null_image_path"));
		// 新品上架
		request.setAttribute("BaseLink52List", super.getBaseLinkList(52, 6, "no_null_image_path"));
		// 缤纷特惠
		request.setAttribute("BaseLink53List", super.getBaseLinkList(53, 6, "no_null_image_path"));

		return new ActionForward("/index/IndexPromotions/index.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String htype = (String) dynaBean.get("link_type");
		if (StringUtils.isBlank(htype)) {
			htype = "0";
		}
		dynaBean.set("htype", htype);

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		List<BaseLink> baseLinkList = super.getBaseLinkList(800, 5, "no_null_image_path");
		request.setAttribute("imageLeftListJsonString",
				super.getPptJsonStringByLinkList(baseLinkList, "image_path", "title", null, "link_url", null));

		// 右侧产品
		request.setAttribute("BaseLink801List", super.getBaseLinkList(801, 4, "no_null_image_path"));
		if (htype.equals("50")) {

			// 促销商品
			request.setAttribute("BaseLink50List", super.getBaseLinkList(50, null, "no_null_image_path"));
		}
		if (htype.equals("51")) {
			// 热卖商品
			request.setAttribute("BaseLink51List", super.getBaseLinkList(51, null, "no_null_image_path"));
		}
		if (htype.equals("52")) {
			// 新品上架
			request.setAttribute("BaseLink52List", super.getBaseLinkList(52, null, "no_null_image_path"));
		}
		if (htype.equals("53")) {
			// 缤纷特惠
			request.setAttribute("BaseLink53List", super.getBaseLinkList(53, null, "no_null_image_path"));
		}
		return mapping.findForward("list");
	}
}
