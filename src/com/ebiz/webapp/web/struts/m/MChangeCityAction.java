package com.ebiz.webapp.web.struts.m;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MChangeCityAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return changeCity(mapping, form, request, response);
	}

	public ActionForward changeCity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		char word = 'A';
		List<BaseProvince> entityList = new ArrayList<BaseProvince>();
		for (int i = 0; i < 26; i++) {
			String p_alpha = String.valueOf(word++);
			BaseProvince tempZm = new BaseProvince();
			tempZm.setP_alpha(p_alpha);
			entityList.add(tempZm);
		}

		BaseProvince entity = new BaseProvince();
		entity.setIs_west(1);
		entity.setIs_del(0);
		List<BaseProvince> allList = super.getFacade().getBaseProvinceService().getBaseProvinceList(entity);
		if (entityList.size() > 0) {
			for (BaseProvince temp : entityList) {
				List<BaseProvince> tempSonList = new ArrayList<BaseProvince>();
				for (BaseProvince all : allList) {
					if (temp.getP_alpha().equalsIgnoreCase(all.getP_alpha())) {
						tempSonList.add(all);
					}
				}
				temp.getMap().put("tempSonList", tempSonList);
			}
		}
		request.setAttribute("entityList", entityList);
		request.setAttribute("header_title", "选择城市");

		return new ActionForward("/MChangeCity/list.jsp");
	}

	public ActionForward selectChangeCity(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		if (StringUtils.isBlank(p_index)) {
			Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
			p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}

		String ctx = super.getCtxPath(request);
		String p_name = "";

		if (p_index.equals(Keys.QUANGUO_P_INDEX)) {
			p_name = Keys.QUANGUO_P_NAME;
		} else {

			BaseProvince bp = new BaseProvince();
			bp.setP_index(Long.valueOf(p_index));
			bp.setIs_del(0);
			bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
			if (null != bp) {
				p_name = bp.getP_name();
				// 查询父节点
				if (bp.getPar_index() == 110000 || bp.getPar_index() == 120000 || bp.getPar_index() == 310000
						|| bp.getPar_index() == 500000) {
					BaseProvince bpPar = new BaseProvince();
					bpPar.setP_index(bp.getPar_index());
					bpPar.setIs_del(0);
					bpPar = getFacade().getBaseProvinceService().getBaseProvince(bpPar);
					if (null != bpPar) {
						p_index = bpPar.getP_index().toString();
						p_name = bpPar.getP_name();
					}
				}
			}
		}
		CookieGenerator cg_p_index = new CookieGenerator();
		cg_p_index.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_index.setCookieName(Keys.COOKIE_P_INDEX);

		CookieGenerator cg_p_name = new CookieGenerator();
		cg_p_name.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_name.setCookieName(Keys.COOKIE_P_NAME);

		try {
			cg_p_index.addCookie(response, URLEncoder.encode(p_index, "UTF-8"));
			cg_p_name.addCookie(response, URLEncoder.encode(p_name, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("==setCookiePindex:" + e.getMessage());
		}
		// response.sendRedirect(ctx + "/m/index.shtml");
		response.sendRedirect(ctx + "/m/MCityCenter.do");
		return null;
	}
}
