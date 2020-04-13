package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * 轮播图管理后台
 * 
 * @author Li,Yuan
 * @version 2013-05-28
 */
public class BaseLinkNewAction extends BaseAdminAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.print("*******out********");
		saveToken(request);

		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		// String link_type = (String) dynaBean.get("link_type");
		String type = (String) dynaBean.get("type");
		// String par_id = (String) dynaBean.get("par_id");
		String pre_number = (String) dynaBean.get("pre_number");
		dynaBean.set("order_value", 0);

		if (StringUtils.isNotBlank(type) && type.equals("10050")) {
			// BaseLink a = new BaseLink();
			// a.setPar_id(Integer.valueOf(par_id).intValue());
			// a.setIs_del(0);
			//
			// int count = getFacade().getBaseLinkService().getBaseLinkCount(a);
			// if (count == 1) {
			// return null;
			// }
			if (type.equals("pic")) {
				request.setAttribute("pre_number", pre_number);
				return new ActionForward("/admin/BaseLinkCase/formPic.jsp");
			}
			if (type.equals("txtAndPic")) {
				request.setAttribute("pre_number", pre_number);
				return new ActionForward("/admin/BaseLinkCase/formTxtAndPic.jsp");
			}
			if (type.equals("txt")) {
				logger.info("======================news");
				request.setAttribute("pre_number", pre_number);
				return new ActionForward("/admin/BaseLinkCase/formTxt.jsp");
			}
		}

		if (StringUtils.isNotBlank(type)) {
			if (type.equals("pic")) {
				return new ActionForward("/admin/BaseLinkNew/formPic.jsp");
			}
			if (type.equals("txtAndPic")) {
				return new ActionForward("/admin/BaseLinkNew/formTxtAndPic.jsp");
			}
			if (type.equals("txt")) {
				return new ActionForward("/admin/BaseLinkNew/formTxt.jsp");
			}
		}
		return null;
	}

	public ActionForward news(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======================news");
		return new ActionForward("/admin/EntpCase/case.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String title_like = (String) dynaBean.get("title_like");
		// String title_like = (String) dynaBean.get("title_like");
		String link_type = (String) dynaBean.get("link_type");
		String par_id = (String) dynaBean.get("par_id");
		String par_son_type = (String) dynaBean.get("par_son_type");
		Pager pager = (Pager) dynaBean.get("pager");
		BaseLink entity = new BaseLink();
		entity.setLink_type(Integer.valueOf(link_type));
		entity.getMap().put("title_like", title_like);
		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(par_id) && StringUtils.isNotBlank(par_son_type)) {
			entity.setLink_type(null);
		}
		// 取管理员的
		entity.setLink_scope(0);
		Integer recordCount = getFacade().getBaseLinkService().getBaseLinkCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkPaginatedList(entity);
		request.setAttribute("entityList", baseLinkList);
		return mapping.findForward("list");
		// return new ActionForward("/admin/baseLinkCase/list.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.print("*******in********");
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String link_type = (String) dynaBean.get("link_type");
		String type = (String) dynaBean.get("type");
		String pre_number = (String) dynaBean.get("pre_number");
		String pre_number_flag = (String) dynaBean.get("pre_number_flag");
		String img = (String) dynaBean.get("img");
		String id = (String) dynaBean.get("id");
		String cls_id = (String) dynaBean.get("cls_id");
		String h_mod_id = (String) dynaBean.get("h_mod_id");
		String par_id = (String) dynaBean.get("par_id");
		// String selectImg = (String) dynaBean.get("selectImg");
		log.info("=======img=====" + img);
		String par_son_type = (String) dynaBean.get("par_son_type");
		String[] titles = request.getParameterValues("titles");
		String[] pre_varchars = request.getParameterValues("pre_varchars");
		String[] link_urls = request.getParameterValues("link_urls");
		String[] order_values = request.getParameterValues("order_values");
		String[] file_hiddens = request.getParameterValues("file_hidden");
		String[] comm_ids = request.getParameterValues("comm_ids");
		String[] comm_names = request.getParameterValues("comm_names");
		String[] pd_prices = request.getParameterValues("pd_prices");
		String[] comm_id = request.getParameterValues("comm_id");
		List<BaseLink> baseLinkList = new ArrayList<BaseLink>();
		List<CommInfo> commInfoList = new ArrayList<CommInfo>();
		CommInfo commInfo = null;
		if (ArrayUtils.isNotEmpty(titles)) {
			for (int i = 0; i < titles.length; i++) {
				BaseLink entity = new BaseLink();
				super.copyProperties(entity, form);
				Date sysDate = new Date();
				entity.setIs_del(0);
				entity.setAdd_time(sysDate);
				entity.setAdd_uid(new Integer(new Integer(ui.getId())));
				entity.setTitle(titles[i]);
				entity.setLink_url(link_urls[i]);
				entity.setOrder_value(Integer.valueOf(order_values[i]));
				if (ArrayUtils.isNotEmpty(pd_prices))
					entity.setPd_price(new BigDecimal(pd_prices[i]));
				if (ArrayUtils.isNotEmpty(pre_varchars))
					entity.setPre_varchar(pre_varchars[i]);
				if (ArrayUtils.isNotEmpty(file_hiddens))
					entity.setImage_path(file_hiddens[i]);
				if (ArrayUtils.isNotEmpty(comm_ids))
					entity.setComm_id(Integer.valueOf(comm_ids[i]));
				if (ArrayUtils.isNotEmpty(comm_names))
					entity.setComm_name(comm_names[i]);
				if (ArrayUtils.isNotEmpty(comm_id)) {
					if (null != comm_id[i]) {
						String s_comm_id = comm_id[i];
						if (StringUtils.isNotBlank(s_comm_id)) {
							commInfo = new CommInfo();
							commInfo.setId(new Integer(s_comm_id));
							commInfo.setKey_word(titles[i]);
						}
					}
					commInfoList.add(commInfo);
				}
				baseLinkList.add(entity);
			}
			if (StringUtils.isBlank(id)) {
				getFacade().getBaseLinkService().createBaseLinkByList(baseLinkList);
			} else {
				for (BaseLink b : baseLinkList) {
					getFacade().getBaseLinkService().modifyBaseLink(b);
				}
			}
			// 修改商品关键词
			if (null != commInfoList && commInfoList.size() > 0) {
				for (CommInfo cur : commInfoList) {
					if (null != cur.getId()) {
						getFacade().getCommInfoService().modifyCommInfo(cur);
					}
				}
			}
		} else {
			BaseLink entity = new BaseLink();
			super.copyProperties(entity, form);
			if (StringUtils.isNotBlank(cls_id)) {
				entity.setContent(cls_id);
			}
			if (StringUtils.isNotBlank(h_mod_id)) {
				entity.setContent(h_mod_id);
			}
			if (StringUtils.isNotBlank(pre_number_flag))
				entity.setPre_number(Integer.valueOf(pre_number_flag));
			if (null == entity.getId()) {
				Date sysDate = new Date();
				entity.setIs_del(0);
				entity.setAdd_time(sysDate);
				entity.setAdd_uid(new Integer(new Integer(ui.getId())));
				getFacade().getBaseLinkService().createBaseLink(entity);
				saveMessage(request, "entity.inerted");
			} else {
				entity.setUpdate_time(new Date());
				entity.setUpdate_uid(new Integer(ui.getId()));
				getFacade().getBaseLinkService().modifyBaseLink(entity);
				saveMessage(request, "entity.updated");
			}
		}
		if (link_type.equals("2000")) {
			super.renderJavaScript(response, "window.parent.location.href='Home.do?mod_id=" + mod_id + "'");
			return null;
		} else {
			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&mod_id=" + mod_id);
			pathBuffer.append("&link_type=" + link_type);
			if (StringUtils.isNotBlank(pre_number_flag) && !link_type.equals("70")) {
				pathBuffer.append("&pre_number=" + pre_number_flag);
			}
			if (StringUtils.isNotBlank(pre_number)) {
				pathBuffer.append("&pre_number=" + pre_number);
			}
			if (StringUtils.isNotBlank(par_id)) {
				pathBuffer.append("&par_id=" + par_id);
			}
			if (StringUtils.isNotBlank(type)) {
				pathBuffer.append("&type=" + type);
			}
			pathBuffer.append("&");
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		String link_type = (String) dynaBean.get("link_type");
		String par_id = (String) dynaBean.get("par_id");
		String par_son_type = (String) dynaBean.get("par_son_type");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		BaseLink entity = new BaseLink();
		entity.setUpdate_time(new Date());
		entity.setUpdate_uid(new Integer(ui.getId()));
		entity.setDel_time(new Date());
		entity.setDel_uid(new Integer(ui.getId()));
		entity.setIs_del(1);
		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			getFacade().getBaseLinkService().modifyBaseLink(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getBaseLinkService().modifyBaseLink(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_type=" + link_type);
		if (StringUtils.isNotBlank(par_id)) {
			pathBuffer.append("&par_id=" + par_id);
		}
		if (StringUtils.isNotBlank(par_son_type)) {
			pathBuffer.append("&par_son_type=" + par_son_type);
		}
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String link_type = (String) dynaBean.get("link_type");
		String par_son_type = (String) dynaBean.get("par_son_type");
		String par_id = (String) dynaBean.get("par_id");
		if (GenericValidator.isLong(id)) {
			BaseLink baseLink = new BaseLink();
			baseLink.setId(Integer.valueOf(id));
			baseLink = getFacade().getBaseLinkService().getBaseLink(baseLink);
			if (null == baseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, baseLink);
			if (null != baseLink.getContent()) {// 查询类别
				if (baseLink.getLink_type() == 20) {
					String[] cls_id = StringUtils.splitByWholeSeparator(baseLink.getContent(), ",");
					if ((null != cls_id) && (cls_id.length > 0)) {
						String cls_name = "";
						for (int i = 0; i < cls_id.length; i++) {
							BaseClass baseClass = new BaseClass();
							baseClass.setCls_id(Integer.valueOf(cls_id[i]));
							baseClass.setIs_del(0);
							baseClass = super.getFacade().getBaseClassService().getBaseClass(baseClass);
							if (null != baseClass) {
								cls_name += baseClass.getCls_name();
								if ((i + 1) != cls_id.length) {
									cls_name += ",";
								}
							}
						}
						request.setAttribute("cls_name", cls_name);
					}
				}
			}
			if (StringUtils.isNotBlank(link_type)) {
				int link_type_int = Integer.valueOf(link_type);
				if (StringUtils.isNotBlank(par_son_type) && StringUtils.isNotBlank(par_id)) {
					BaseLink parBaseLink = new BaseLink();
					parBaseLink.setId(Integer.valueOf(par_id));
					parBaseLink.setIs_del(0);
					parBaseLink = super.getFacade().getBaseLinkService().getBaseLink(parBaseLink);
					request.setAttribute("parBaseLink", parBaseLink);
					if (par_son_type.equals("1")) {
						return new ActionForward("/admin/BaseLinkNew/formTxtWithPindex.jsp");
					} else if (par_son_type.equals("2") || par_son_type.equals("4") || par_son_type.equals("5")) {
						return new ActionForward("/admin/BaseLinkNew/editPicAndPrice.jsp");
					} else {
						return new ActionForward("/admin/BaseLinkNew/editPic.jsp");
					}
				} else {
					if ((link_type_int == 3000) || (link_type_int == 20) || (link_type_int == 30)
							|| (link_type_int == 70) || (link_type_int == 80) || (link_type_int == 88)) {// 不带有图片的
						return new ActionForward("/admin/BaseLinkNew/formTxt.jsp");
					} else if ((link_type_int == 1100) || (link_type_int == 91) || (link_type_int == 200)
							|| (link_type_int == 210) || (link_type_int == 300) || (link_type_int == 330)
							|| (link_type_int == 400)) {// 帮助中心
						return new ActionForward("/admin/BaseLinkNew/formTxt.jsp");
					} else if ((link_type_int == 2200) || (link_type_int == 2200) || (link_type_int == 320)) {// 带有图片和价格
						return new ActionForward("/admin/BaseLinkNew/editPicAndPrice.jsp");
					} else {// 默认跳转带图片的
						return new ActionForward("/admin/BaseLinkNew/editPic.jsp");
					}
				}
			}
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			BaseLink baseLink = new BaseLink();
			baseLink.setId(Integer.valueOf(id));
			baseLink = getFacade().getBaseLinkService().getBaseLink(baseLink);
			if (null == baseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, baseLink);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	@Override
	public String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public ActionForward listPdClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String cls_level = (String) dynaBean.get("cls_level");
		String cls_name_like = (String) dynaBean.get("cls_name_like");
		String cls_scope = (String) dynaBean.get("cls_scope");
		BaseClass baseClass = new BaseClass();
		if (StringUtils.isNotBlank(cls_scope)) {
			baseClass.setCls_scope(Integer.valueOf(cls_scope));
		}
		baseClass.setIs_del(0);
		if (StringUtils.isNotBlank(cls_name_like)) {
			baseClass.getMap().put("cls_name_like", cls_name_like);
		}
		baseClass.setCls_scope(1);
		if (StringUtils.isBlank(cls_level))
			cls_level = "1";
		baseClass.setCls_level(Integer.valueOf(cls_level));
		Pager pager = (Pager) dynaBean.get("pager");
		Integer recordCount = getFacade().getBaseClassService().getBaseClassCount(baseClass);
		pager.init(recordCount.longValue(), Integer.valueOf("20"), pager.getRequestPage());
		baseClass.getRow().setFirst(pager.getFirstRow());
		baseClass.getRow().setCount(pager.getRowCount());
		List<BaseClass> entityList = getFacade().getBaseClassService().getBaseClassPaginatedList(baseClass);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/admin/BaseLinkNew/listPdClass.jsp");
	}

	public ActionForward getCommLinkInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		if (StringUtils.isNotBlank(comm_id)) {
			CommInfo entity = new CommInfo();
			entity.setAudit_state(1);
			entity.setIs_del(0);
			entity.setId(Integer.valueOf(comm_id));
			entity = super.getFacade().getCommInfoService().getCommInfo(entity);
			JSONObject jsonObject = new JSONObject();
			if (null != entity) {
				jsonObject.put("link_url",
						"http://".concat(server.toString()).concat("/").concat("item-").concat(comm_id)
								.concat(".shtml"));
				jsonObject.put("pre_number", entity.getSale_count());
				jsonObject.put("pd_price", entity.getSale_price());
				// jsonObject.put("title", entity.getComm_name());
				jsonObject.put("comm_name", entity.getComm_name().replace("\"", ""));
				if (StringUtils.isBlank(entity.getKey_word())) {
					jsonObject.put("title", entity.getComm_name().replace("\"", ""));
				} else {
					jsonObject.put("title", entity.getKey_word().replace("\"", ""));
				}
				jsonObject.put("pre_varchar", entity.getSub_title());
				jsonObject.put("id", entity.getId());
				jsonObject.put("main_pic", entity.getMain_pic());
				jsonObject.put("pre_varchar", entity.getKey_word());
			}
			super.renderJson(response, jsonObject.toString());
			return null;
		}
		return null;
	}
}
