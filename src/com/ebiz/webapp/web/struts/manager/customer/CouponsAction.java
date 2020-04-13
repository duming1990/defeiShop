package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqLink;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.ZxingUtils;

/**
 * @author 戴诗学
 * @version 2018-05-17
 */

public class CouponsAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		EntpInfo entpInfo = super.getEntpInfo(userInfo.getOwn_entp_id());
		if (null != entpInfo) {
			if (1 != entpInfo.getIs_coupons()) {
				String msg = "功能还未开通，请向管理员申请开通！";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');}");
				return null;
			}
		} else {
			String msg = "功能还未开通，请向管理员申请开通！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');}");
			return null;
		}
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
		String yhq_name = (String) dynaBean.get("yhq_name");

		YhqInfo entity = new YhqInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		if (null != userInfo.getOwn_entp_id()) {
			entity.setOwn_entp_id(userInfo.getOwn_entp_id());
			Integer recordCount = getFacade().getYhqInfoService().getYhqInfoCount(entity);
			pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
			entity.getRow().setFirst(pager.getFirstRow());
			entity.getRow().setCount(pager.getRowCount());

			List<YhqInfo> entityList = super.getFacade().getYhqInfoService().getYhqInfoPaginatedList(entity);
			if (null != entityList && entityList.size() > 0) {
				request.setAttribute("entityList", entityList);
			}
		}
		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		UserInfo userInfoTemp = super.getUserInfo(userInfo.getId());

		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		getsonSysModuleList(request, dynaBean);
		super.getSessionId(request);

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfoTemp.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "商家不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		dynaBean.set("own_entp_id", entpInfo.getId());
		dynaBean.set("yhq_start_date", new Date());
		dynaBean.set("yhq_end_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));
		return mapping.findForward("input");
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

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		UserInfo userInfoTemp = super.getUserInfo(ui.getId());
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfoTemp.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		String is_limited = (String) dynaBean.get("is_limited");
		String[] comm_ids = request.getParameterValues("comm_ids");// 商品id
		if (null == comm_ids || comm_ids.length <= 0) {
			String msg = "请至少选择一个商品！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		YhqInfo entity = new YhqInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isEmpty(id)) {
			entity.setYhq_number(0);
			entity.setAdd_date(new Date());
			entity.setYhq_type(Keys.YhqType.YHQ_TYPE_10.getIndex());
			entity.setYhq_sygz(Keys.YhqSygz.YHQ_SYGZ_40.getIndex());
			entity.setAdd_user_id(entpInfo.getId());
			entity.setYhq_number_now(0);
			entity.setAdd_user_name(entpInfo.getEntp_name());
			entity.setOwn_entp_id(entpInfo.getId());
			entity.setOwn_entp_name(entpInfo.getEntp_name());
			entity.getMap().put("comm_ids", comm_ids);
			int yhj_id = super.getFacade().getYhqInfoService().createYhqInfo(entity);
			String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
			if (!ctxDir.endsWith(File.separator)) {
				ctxDir = ctxDir + File.separator;
			}
			String ctx = super.getCtxPath(request, false);

			String uploadDir = "files" + File.separator + "yhqQrcode";

			if (!ctxDir.endsWith(File.separator)) {
				ctxDir = ctxDir + File.separator;
			}
			File savePath = new File(ctxDir + uploadDir);
			if (!savePath.exists()) {
				savePath.mkdirs();
			}

			String imgPath = ctxDir + uploadDir + File.separator + yhj_id + ".png";
			String share_url = ctx + "/m/GetCoupons.do?method=getYhqQrcode&id=" + yhj_id;
			File imgFile = new File(imgPath);
			if (!imgFile.exists()) {
				String waterMarkPath = ctxDir + "styles/imagesPublic/qrCodeWater.png";
				ZxingUtils.encodeQrcode(share_url, imgPath, 400, 400, null);
				YhqInfo yhqInfoUpdate = new YhqInfo();
				yhqInfoUpdate.setId(yhj_id);
				yhqInfoUpdate.setQrcpde_path(uploadDir + File.separator + yhj_id + ".png");
				super.getFacade().getYhqInfoService().modifyYhqInfo(yhqInfoUpdate);
			}
			saveMessage(request, "entity.inerted");
		} else {
			YhqInfo info = getYhqInfo(Integer.valueOf(id));
			if (null != is_limited) {
				if (Integer.valueOf(is_limited) < info.getYhq_number_now()) {
					String msg = "限领优惠券的数量必须大于已使用的优惠券数量";
					super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
					return null;
				} else {
					entity.setUpdate_date(new Date());
					entity.setUpdate_user_id(entpInfo.getId());
					entity.setUpdate_user_name(entpInfo.getEntp_name());
					super.getFacade().getYhqInfoService().modifyYhqInfo(entity);
					YhqLink yhqLink = new YhqLink();
					yhqLink.getMap().put("yhq_delete", id);
					yhqLink.getMap().put("comm_ids", comm_ids);
					yhqLink.getMap().put("id", id);
					super.getFacade().getYhqLinkService().removeYhqLink(yhqLink);
					saveMessage(request, "entity.updated");
				}
			}
		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		YhqInfo entity = new YhqInfo();
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setIs_del(1);
			entity.setDel_date(new Date());
			entity.setDel_user_id(ui.getId());
			entity.getMap().put("link_id", id);
			super.getFacade().getYhqInfoService().modifyYhqInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (ArrayUtils.isNotEmpty(pks)) {
			for (String entity_id : pks) {
				entity.setId(Integer.valueOf(entity_id));
				entity.setIs_del(1);
				entity.setDel_date(new Date());
				entity.setDel_user_id(ui.getId());
				entity.getMap().put("link_id", entity_id);
				super.getFacade().getYhqInfoService().modifyYhqInfo(entity);
			}
			saveMessage(request, "entity.deleted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			YhqInfo entity = new YhqInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getYhqInfoService().getYhqInfo(entity);
			super.copyProperties(form, entity);

			YhqLink coupons = new YhqLink();
			coupons.setYhq_id(entity.getId());
			List<YhqLink> couponslist = super.getFacade().getYhqLinkService().getYhqLinkList(coupons);
			String comm_name = "";
			if (null != couponslist) {
				for (YhqLink yhqLink : couponslist) {
					CommInfo commInfo = super.getCommInfo(yhqLink.getComm_id());
					comm_name += commInfo.getComm_name() + "<br>";
				}
				request.setAttribute("comm_name", comm_name);
			}
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;

		saveToken(request);
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}

		super.getSessionId(request);
		YhqInfo entity = new YhqInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getYhqInfoService().getYhqInfo(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		YhqLink coupons = new YhqLink();
		coupons.setYhq_id(entity.getId());
		List<YhqLink> couponslist = super.getFacade().getYhqLinkService().getYhqLinkList(coupons);
		String temp_comm_ids = ",";
		for (YhqLink yhqLink : couponslist) {
			CommInfo commInfo = super.getCommInfo(yhqLink.getComm_id());
			CommTczhPrice price = new CommTczhPrice();
			price.setComm_id(yhqLink.getComm_id().toString());
			List<CommTczhPrice> commTczhPriceList = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(price);
			if (null != commTczhPriceList) {
				yhqLink.getMap().put("price", commTczhPriceList.get(0).getComm_price());
			}
			yhqLink.getMap().put("commInfo", commInfo);
			temp_comm_ids += yhqLink.getComm_id().toString() + ",";
		}
		request.setAttribute("temp_comm_ids", temp_comm_ids);
		request.setAttribute("commInfoList", couponslist);
		super.copyProperties(form, entity);
		return mapping.findForward("input");
	}

	public ActionForward chooseCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String comm_type = (String) dynaBean.get("comm_type");
		String yhq_id = (String) dynaBean.get("yhq_id");
		String ajax = (String) dynaBean.get("ajax");
		CommInfo entity = new CommInfo();
		entity.getMap().put("ajax", ajax);
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		// entity.setIs_rebate(0);// 不返利
		// entity.setIs_aid(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.getMap().put("keyword", comm_name_like);

		if (StringUtils.isNotBlank(comm_type)) {
			entity.getMap().put("comm_type_in", comm_type);
		} else {
			entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		}
		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CommInfo t : entityList) {
				CommTczhPrice ctp = new CommTczhPrice();
				ctp.setComm_id(t.getId().toString());
				List<CommTczhPrice> ctpList = super.getFacade().getCommTczhPriceService().getCommTczhPriceList(ctp);
				if (null != ctpList && ctpList.size() > 0) {
					ctp = ctpList.get(0);
					t.getMap().put("price", ctp.getComm_price());
				} else {
					t.getMap().put("price", t.getPrice_ref());
				}
				t.getMap().put("count", 1);
				t.setComm_name(t.getComm_name().replace("\"", ""));

				if (StringUtils.isNotBlank(yhq_id)) {
					YhqLink a = new YhqLink();
					a.setYhq_id(Integer.valueOf(yhq_id));
					a.setComm_id(t.getId());
					int count = getFacade().getYhqLinkService().getYhqLinkCount(a);
					if (count > 0) {
						t.getMap().put("choose", "true");
						t.getMap().put("comm_info", t);
					}

				}

			}
		}
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/Coupons/choose_comminfo.jsp");
	}
}