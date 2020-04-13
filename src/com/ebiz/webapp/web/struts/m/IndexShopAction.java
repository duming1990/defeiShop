package com.ebiz.webapp.web.struts.m;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class IndexShopAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		// 轮播图
		request.setAttribute("mBaseLink20List", super.getMBaseLinkList(20, 5, "no_null_image_path"));

		request.setAttribute("mBaseLink30List", super.getBaseLinkList(20, 8, null));

		request.setAttribute("mBaseLink40List", super.getMBaseLinkList(40, 3, "no_null_image_path"));

		request.setAttribute("mBaseLink50List", super.getMBaseLinkList(50, 1, "no_null_image_path"));

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		if (null == current_p_index) {
			setPindexCookies(response, Keys.QUANGUO_P_INDEX, Keys.QUANGUO_P_NAME);
		} else {
			if (StringUtils.isBlank(p_index)) {
				p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
			}
		}
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
				p_index = null;
			}
		}

		List<CommInfo> commInfoList = super.getCommInfoList(10, false, null, false, false, "orderBySaleDesc", false,
				null, null, null, p_index, null, null, null, null, null, null);
		request.setAttribute("commInfoList", commInfoList);

		return new ActionForward("/IndexShop/index.jsp");
	}
}
