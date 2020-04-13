package com.ebiz.webapp.web.struts.manager.customer;

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

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.manager.admin.BaseAdminAction;

public class SpecialtyAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String link_type = (String) dynaBean.get("link_type");
		String type = (String) dynaBean.get("type");
		dynaBean.set("order_value", 0);
		if (StringUtils.isNotBlank(link_type) && StringUtils.isNotBlank(type)) {
			if (type.equals("price")) {// 带有图片和价格
				return new ActionForward("/customer/MBaseLink/formPicAndPrice.jsp");
			}
			if (type.equals("formTxtWithPindex")) {// 不带有图片的
				return new ActionForward("/customer/MBaseLink/formTxtWithPindex.jsp");
			}
			if (type.equals("txt")) {// 不带有图片的
				return new ActionForward("/customer/MBaseLink/formTxt.jsp");
			}

		}

		// 默认跳转带图片的
		return new ActionForward("/customer/MBaseLink/formPic.jsp");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");

		String title_like = (String) dynaBean.get("title_like");
		String link_type = (String) dynaBean.get("link_type");

		Pager pager = (Pager) dynaBean.get("pager");

		MBaseLink entity = new MBaseLink();
		entity.setLink_type(Integer.valueOf(link_type));
		entity.getMap().put("title_like", title_like);
		if (GenericValidator.isInt(link_type)
				&& Integer.valueOf(link_type) == Keys.M_BASE_LINK_TYPE.LINK_TYPE_100.getIndex()) {
			UserInfo ui = super.getUserInfoFromSession(request);
			entity.setLink_id(ui.getId());
		}

		if (null == is_del) {
			entity.setIs_del(Integer.valueOf("0"));
			dynaBean.set("is_del", "0");
		}

		super.copyProperties(entity, form);

		Integer recordCount = getFacade().getMBaseLinkService().getMBaseLinkCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<MBaseLink> MBaseLinkList = getFacade().getMBaseLinkService().getMBaseLinkPaginatedList(entity);

		request.setAttribute("entityList", MBaseLinkList);
		return mapping.findForward("list");

	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
		String id = (String) dynaBean.get("id");
		String cls_id = (String) dynaBean.get("cls_id");
		String h_mod_id = (String) dynaBean.get("h_mod_id");
		String par_id = (String) dynaBean.get("par_id");
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

		List<MBaseLink> baseLinkList = new ArrayList<MBaseLink>();
		List<CommInfo> commInfoList = new ArrayList<CommInfo>();
		CommInfo commInfo = null;
		if (ArrayUtils.isNotEmpty(titles)) {
			for (int i = 0; i < titles.length; i++) {
				MBaseLink entity = new MBaseLink();
				super.copyProperties(entity, form);
				if (entity.getLink_type() == Keys.M_BASE_LINK_TYPE.LINK_TYPE_100.getIndex()) {
					entity.setLink_id(ui.getId());
				}
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
				getFacade().getMBaseLinkService().createMBaseLinkByList(baseLinkList);

			} else {
				for (MBaseLink b : baseLinkList) {
					getFacade().getMBaseLinkService().modifyMBaseLink(b);
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
			MBaseLink entity = new MBaseLink();

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
				getFacade().getMBaseLinkService().createMBaseLink(entity);
				saveMessage(request, "entity.inerted");

			} else {
				entity.setUpdate_time(new Date());
				entity.setUpdate_uid(new Integer(ui.getId()));

				getFacade().getMBaseLinkService().modifyMBaseLink(entity);
				saveMessage(request, "entity.updated");
			}
		}

		if (link_type.equals("2000")) {
			super.renderJavaScript(response, "window.parent.location.href='HomeM.do?mod_id=" + mod_id + "'");
			return null;
		} else {
			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&mod_id=" + mod_id);
			pathBuffer.append("&link_type=" + link_type);
			pathBuffer.append("&type=" + type);
			if (StringUtils.isNotBlank(pre_number_flag)) {
				pathBuffer.append("&pre_number=" + pre_number_flag);
			}
			if (StringUtils.isNotBlank(pre_number)) {
				pathBuffer.append("&pre_number=" + pre_number);
			}
			if (StringUtils.isNotBlank(par_id)) {
				pathBuffer.append("&par_id=" + par_id);
			}
			if (StringUtils.isNotBlank(par_son_type)) {
				pathBuffer.append("&par_son_type=" + par_son_type);
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
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		MBaseLink entity = new MBaseLink();
		entity.setUpdate_time(new Date());
		entity.setUpdate_uid(new Integer(ui.getId()));
		entity.setDel_time(new Date());
		entity.setDel_uid(new Integer(ui.getId()));
		entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			getFacade().getMBaseLinkService().modifyMBaseLink(entity);

			saveMessage(request, "entity.deleted");

		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			getFacade().getMBaseLinkService().modifyMBaseLink(entity);
			saveMessage(request, "entity.deleted");

		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_type=" + link_type);
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
		String type = (String) dynaBean.get("type");
		if (GenericValidator.isLong(id)) {
			MBaseLink MBaseLink = new MBaseLink();
			MBaseLink.setId(Integer.valueOf(id));
			MBaseLink = getFacade().getMBaseLinkService().getMBaseLink(MBaseLink);

			if (null == MBaseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			super.copyProperties(form, MBaseLink);
			// if (StringUtils.isNotBlank(link_type)) {
			// int link_type_int = Integer.valueOf(link_type);
			//
			// if ((link_type_int == 20) || (link_type_int == 30) || (link_type_int == 70)) {
			// return new ActionForward("/customer/MBaseLink/formPic.jsp");
			// } else if ((link_type_int == 90)) {
			// return new ActionForward("/customer/MBaseLink/formText.jsp");
			// } else {
			System.out.println("===type:" + type);
			if (type.equals("price")) {// 带有图片和价格
				return new ActionForward("/customer/MBaseLink/editPicAndPrice.jsp");
			}

			return new ActionForward("/customer/MBaseLink/editPic.jsp");// 默认调跳转页面
			// }
			// }
			// return mapping.findForward("input");
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
			MBaseLink MBaseLink = new MBaseLink();
			MBaseLink.setId(Integer.valueOf(id));
			MBaseLink = getFacade().getMBaseLinkService().getMBaseLink(MBaseLink);

			if (null == MBaseLink) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, MBaseLink);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward listCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.setAudit_state(1);
		entity.setIs_sell(1);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		entity.getMap().put("not_out_sell_time", "true");

		entity.getMap().put("comm_name_like", comm_name_like);

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return new ActionForward("/../manager/customer/MBaseLink/choose_commInfo.jsp");

	}

}