package com.ebiz.webapp.web.struts;

import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2011-12-14
 */
public class SearchAction extends BaseWebAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String htype = (String) dynaBean.get("htype");
		String is_lianmeng = (String) dynaBean.get("is_lianmeng");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String son_cls_id = (String) dynaBean.get("son_cls_id");
		Boolean judgeIsMoblie = super.JudgeIsMoblie(request);
		if (judgeIsMoblie) {
			if (StringUtils.isBlank(htype)) {
				htype = "0";
			}
			return new ActionForward("/m/MSearch.do?root_cls_id=" + root_cls_id + "&par_cls_id=" + par_cls_id
					+ "&son_cls_id=" + son_cls_id + "&htype=" + htype + "&is_lianmeng=" + is_lianmeng, true);
		} else {
			return this.search(mapping, form, request, response);
		}
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String htype = (String) dynaBean.get("htype");
		if (StringUtils.isBlank(htype)) {
			htype = "0";
		}
		dynaBean.set("htype", htype);
		if ("0".equals(htype)) {
			return this.listPd(mapping, form, request, response);
		}
		if ("1".equals(htype)) {
			return this.listEntp(mapping, form, request, response);
		}
		return mapping.findForward("list");
	}

	// 产品
	public ActionForward listPd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String keyword = (String) dynaBean.get("keyword");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String son_cls_id = (String) dynaBean.get("son_cls_id");
		String p_index = (String) dynaBean.get("p_index");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String comm_type = (String) dynaBean.get("comm_type");
		String is_aid = (String) dynaBean.get("is_aid");
		boolean isMoblie = super.JudgeIsMoblie(request);
		if (isMoblie && GenericValidator.isInt(par_cls_id)) {// 手机版 导航，点击用
			String ctx = super.getCtxPath(request);
			return new ActionForward(ctx + "/m/MSearch.do?method=listPd&par_cls_id=" + par_cls_id, true);
		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		Pager pager = (Pager) dynaBean.get("pager");

		if (Keys.app_cls_level.equals("3")) {

			if (StringUtils.isNotBlank(comm_type) && comm_type.equals(Keys.CommType.COMM_TYPE_4.getIndex() + "")) { // 获取活动
				List<CommInfo> entityList = super.getCommInfoHdList(12, false, null, false, false, orderByParam, true,
						pager, 12, keyword, p_index, root_cls_id, par_cls_id, son_cls_id, null);
				request.setAttribute("entityList", entityList);
				request.setAttribute("canSearchHd", "true");

			} else {// 获取正常的
				List<CommInfo> entityList = super.getCommInfoList(12, false, null, false, false, orderByParam, true,
						pager, 12, keyword, p_index, root_cls_id, par_cls_id, son_cls_id, null, is_aid, null);
				request.setAttribute("entityList", entityList);
			}
		}
		if (Keys.app_cls_level.equals("2")) {
			List<CommInfo> entityList = super.getCommInfoList(12, false, null, false, false, orderByParam, true, pager,
					12, keyword, p_index, root_cls_id, par_cls_id, son_cls_id, null, is_aid, null);
			request.setAttribute("entityList", entityList);
		}

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		if (null == current_p_index) {
			setPindexCookies(response, Keys.QUANGUO_P_INDEX, Keys.QUANGUO_P_NAME);
		}
		String city = Keys.P_INDEX_CITY;

		if (null != current_p_index) {
			city = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}

		super.getBaseProvinceCityList(request, "sonBaseProList", Integer.valueOf(city));

		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(1);
		List<BaseClass> baseRootClassList = super.getBaseClassParList(baseClass, 1, null);
		request.setAttribute("baseRootClassList", baseRootClassList);

		if (StringUtils.isNotBlank(root_cls_id)) {
			BaseClass baseClassSon = new BaseClass();
			baseClassSon.setCls_id(Integer.valueOf(root_cls_id));
			List<BaseClass> baseParClassList = super.getBaseClassParList(baseClassSon, 1, null);
			request.setAttribute("baseParClassList", baseParClassList);

			BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(root_cls_id));
			if (null != parClassTemp) {
				request.setAttribute("root_cls_name", parClassTemp.getCls_name());
			}
		}

		if (StringUtils.isNotBlank(par_cls_id)) {
			BaseClass baseClassSon = new BaseClass();
			baseClassSon.setCls_id(Integer.valueOf(par_cls_id));
			List<BaseClass> baseSonClassList = super.getBaseClassParList(baseClassSon, 1, null);
			request.setAttribute("baseSonClassList", baseSonClassList);

			BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(par_cls_id));
			if (null != parClassTemp) {
				request.setAttribute("par_cls_name", parClassTemp.getCls_name());
			}
		}
		if (StringUtils.isNotBlank(son_cls_id)) {
			BaseClass sonClassTemp = super.getBaseClass(Integer.valueOf(son_cls_id));
			if (null != sonClassTemp) {
				request.setAttribute("son_cls_name", sonClassTemp.getCls_name());
			}
		}

		if (StringUtils.isNotBlank(p_index)) {
			BaseProvince bp = super.getBaseProvince(Long.valueOf(p_index));
			if (null != bp) {
				request.setAttribute("p_name", bp.getP_name());
			}
		}
		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}

		return new ActionForward("/index/Search/list_pd.jsp");
	}

	// 企业搜索
	public ActionForward listEntp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String p_index = (String) dynaBean.get("p_index");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String son_cls_id = (String) dynaBean.get("son_cls_id");
		String keyword = (String) dynaBean.get("keyword");
		String is_lianmeng = (String) dynaBean.get("is_lianmeng");
		String orderByParam = (String) dynaBean.get("orderByParam");

		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setAudit_state(2);
		if (StringUtils.isNotBlank(is_lianmeng)) {
			entity.setIs_lianmeng(1);
		}

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

		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.endsWith("0000")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 2));
			} else if (p_index.endsWith("00")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 4));
			} else {
				entity.setP_index(Integer.valueOf(p_index));
			}
		}

		if (StringUtils.isNotBlank(par_cls_id))
			entity.setHy_cls_id(Integer.valueOf(par_cls_id));
		if (StringUtils.isNotBlank(son_cls_id))
			entity.getMap().put("main_pd_class_ids_like", son_cls_id);

		if (StringUtils.isNotBlank(keyword))
			entity.getMap().put("entp_name_like", keyword);

		entity.getMap().put(orderByParam, orderByParam);

		entity.setIs_show(1);
		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("12"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<EntpInfo> entpInfoList = getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		request.setAttribute("entpInfoList", entpInfoList);

		// EntpInfo entityTj = new EntpInfo();
		// entityTj.setIs_del(0);
		// entityTj.setAudit_state(2);
		// entityTj.getRow().setCount(5);
		// entityTj.setIs_show(1);
		// if (StringUtils.isNotBlank(p_index)) {
		// entityTj.getMap().put("p_index_like", p_index);
		// if (p_index.endsWith("00")) {
		// entityTj.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 4));
		// } else if (p_index.endsWith("0000")) {
		// entityTj.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 2));
		// }
		// }
		// if (StringUtils.isNotBlank(par_cls_id))
		// entityTj.setHy_cls_id(Integer.valueOf(par_cls_id));
		// if (StringUtils.isNotBlank(son_cls_id))
		// entityTj.getMap().put("main_pd_class_ids_like", son_cls_id);
		//
		// List<EntpInfo> entpInfoTjList = getFacade().getEntpInfoService().getEntpInfoList(entityTj);
		// request.setAttribute("entpInfoTjList", entpInfoTjList);

		String city = Keys.P_INDEX_CITY;
		if (null != current_p_index) {
			city = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}
		super.getBaseProvinceCityList(request, "sonBaseProList", Integer.valueOf(city));

		BaseClass baseClass = new BaseClass();
		baseClass.setCls_id(1);
		List<BaseClass> baseParClassList = super.getBaseClassParList(baseClass, 1, null);
		request.setAttribute("baseParClassList", baseParClassList);

		if (StringUtils.isNotBlank(par_cls_id)) {
			BaseClass baseClassSon = new BaseClass();
			baseClassSon.setCls_id(Integer.valueOf(par_cls_id));
			List<BaseClass> baseSonClassList = super.getBaseClassParList(baseClassSon, 1, null);
			request.setAttribute("baseSonClassList", baseSonClassList);

			BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(par_cls_id));
			if (null != parClassTemp) {
				request.setAttribute("par_cls_name", parClassTemp.getCls_name());
			}
		}
		if (StringUtils.isNotBlank(son_cls_id)) {
			BaseClass sonClassTemp = super.getBaseClass(Integer.valueOf(son_cls_id));
			if (null != sonClassTemp) {
				request.setAttribute("son_cls_name", sonClassTemp.getCls_name());
			}
		}
		if (StringUtils.isNotBlank(p_index)) {
			BaseProvince bp = super.getBaseProvince(Long.valueOf(p_index));
			if (null != bp) {
				request.setAttribute("p_name", bp.getP_name());
			}
		}
		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}
		return new ActionForward("/index/Search/list_entp.jsp");
	}
}
