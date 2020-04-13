package com.ebiz.webapp.web.struts.manager.customer;

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
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommWelfareApply;
import com.ebiz.webapp.domain.CommWelfareDetail;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqLink;
import com.ebiz.webapp.web.Keys;

public class CommWelfareApplyAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
		if (userInfo.getId() == 2) {
			request.setAttribute("is_jd", true);
		}

		Integer entpId = null;
		// 判断是商家
		if (userInfo.getIs_entp() == 1) {

			entpId = userInfo.getOwn_entp_id();

			// 判断是县域
		} else if (userInfo.getIs_fuwu() == 1) {
			ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
			serviceInfo.setAdd_user_id(userInfo.getId());
			serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
			entpId = serviceInfo.getId();
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String service_name_like = (String) dynaBean.get("service_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");

		CommWelfareApply entity = new CommWelfareApply();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(service_name_like)) {
			entity.getMap().put("service_name_like", service_name_like);
		}

		entity.setEntp_id(entpId);
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}

		List<CommWelfareApply> commWelfareApplyList = getFacade().getCommWelfareApplyService().getCommWelfareApplyList(
				entity);

		if (commWelfareApplyList != null && commWelfareApplyList.size() > 0) {
			for (CommWelfareApply c : commWelfareApplyList) {
				ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
				serviceInfo.setId(c.getService_id());
				serviceInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
				c.getMap().put("serviceInfo", serviceInfo);
			}
		}

		Integer recordCount = getFacade().getCommWelfareApplyService().getCommWelfareApplyCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		request.setAttribute("entityList", commWelfareApplyList);
		request.setAttribute("pager", pager);

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
		DynaBean dynaBean = (DynaBean) form;

		ui = super.getUserInfo(ui.getId());

		String[] comm_ids = request.getParameterValues("comm_ids");// 商品id
		String[] comm_names = request.getParameterValues("comm_names");
		String[] service_ids = request.getParameterValues("service_ids");
		String[] p_indexs = request.getParameterValues("p_indexs");
		String[] service_names = request.getParameterValues("service_names");
		String commWelfareApply_id = (String) dynaBean.get("commWelfareApply_id");
		Integer entpId = null;
		String entpName = null;
		Integer userId = ui.getId();
		String userName = ui.getUser_name();
		// 判断是商家
		if (ui.getIs_entp() == 1) {

			entpId = ui.getOwn_entp_id();
			entpName = ui.getOwn_entp_name();

			// 判断是县域
		} else if (ui.getIs_fuwu() == 1) {
			ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
			serviceInfo.setAdd_user_id(ui.getId());
			serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
			entpId = serviceInfo.getId();
			entpName = serviceInfo.getServicecenter_name();
		}

		// 修改数据
		if (commWelfareApply_id != null && Integer.valueOf(commWelfareApply_id) > 0) {
			if (null == comm_ids || comm_ids.length <= 0) {
				String msg = "请至少选择一个商品！";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}

			CommWelfareApply comma = new CommWelfareApply();
			comma.setId(Integer.valueOf(commWelfareApply_id));
			comma.setIs_del(0);
			CommWelfareApply apply = getFacade().getCommWelfareApplyService().getCommWelfareApply(comma);
			if (null == apply) {
				String msg = "该申请不存在！";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
				return null;
			}

			comma.setUpdate_date(new Date());
			comma.setUpdate_user_id(userId);
			List<CommWelfareDetail> list = new ArrayList<CommWelfareDetail>();

			for (int i = 0; i < comm_ids.length; i++) {
				CommWelfareDetail commWD = new CommWelfareDetail();
				commWD.setComm_welfare_id(apply.getId());
				commWD.setComm_id(Integer.valueOf(comm_ids[i]));
				commWD.setComm_name(comm_names[i]);
				commWD.setEntp_id(entpId);
				commWD.setAdd_date(new Date());
				commWD.setAdd_user_id(userId);
				// 判断是否是县域，如是判断修改的县域是不是自己，如果是，直接保存成审核通过
				if (ui.getIs_fuwu() == 1) {
					ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
					serviceInfo.setAdd_user_id(ui.getId());
					serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
					entpId = serviceInfo.getId();
					if (entpId.intValue() == apply.getService_id().intValue()) {
						commWD.setAudit_state(1);
					}
					commWD.setType(Keys.STATUS_TYPE.STATUS_TYPE_2.getIndex());
				}
				if (ui.getIs_entp() == 1) {
					commWD.setType(Keys.STATUS_TYPE.STATUS_TYPE_1.getIndex());
				}
				commWD.setEntp_name(entpName);
				commWD.setAdd_date(new Date());
				commWD.setAdd_user_id(userId);
				commWD.setP_index(apply.getP_index());
				commWD.setService_id(apply.getService_id());
				commWD.setService_name(apply.getService_name());
				list.add(commWD);
			}
			comma.setAudit_state(0);
			// 判断是否是县域，如是判断修改的县域是不是自己，如果是，直接保存成审核通过
			if (ui.getIs_fuwu() == 1) {
				ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
				serviceInfo.setAdd_user_id(ui.getId());
				serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
				if (serviceInfo.getId().intValue() == apply.getService_id().intValue()) {
					comma.setAudit_state(1);
				}
			}

			comma.getMap().put("update_CommWelfareDetail", list);
			super.getFacade().getCommWelfareApplyService().modifyCommWelfareApply(comma);

			return mapping.findForward("success");
		}
		// 添加数据
		if (null == comm_ids || comm_ids.length <= 0) {
			String msg = "请至少选择一个商品！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (null == service_ids || service_ids.length <= 0) {
			String msg = "请至少选择一个县域！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		for (int j = 0; j < service_ids.length; j++) {
			List<CommWelfareDetail> welfareDetailList = new ArrayList<CommWelfareDetail>();
			CommWelfareApply comma = new CommWelfareApply();
			comma.setEntp_id(entpId);
			comma.setEntp_name(entpName);
			comma.setAdd_date(new Date());
			comma.setAdd_user_id(userId);
			if (ui.getIs_fuwu() == 1) {
				ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
				serviceInfo.setAdd_user_id(ui.getId());
				serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
				entpId = serviceInfo.getId();
				if (entpId.intValue() == Integer.valueOf(service_ids[j])) {
					comma.setAudit_state(1);
				}
				comma.setType(Keys.STATUS_TYPE.STATUS_TYPE_2.getIndex());
			}
			if (ui.getIs_entp() == 1) {
				comma.setType(Keys.STATUS_TYPE.STATUS_TYPE_1.getIndex());
			}
			comma.setAdd_user_id(userId);
			comma.setAdd_user_name(userName);
			comma.setP_index(Integer.valueOf(p_indexs[j]));
			comma.setService_id(Integer.valueOf(service_ids[j]));
			comma.setService_name(service_names[j]);

			for (int i = 0; i < comm_ids.length; i++) {

				CommWelfareDetail commd = new CommWelfareDetail();
				commd.setComm_id(Integer.valueOf(comm_ids[i]));
				commd.setComm_name(comm_names[i]);
				commd.setEntp_id(entpId);
				commd.setEntp_name(entpName);
				if (ui.getIs_fuwu() == 1) {
					ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
					serviceInfo.setAdd_user_id(ui.getId());
					serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
					entpId = serviceInfo.getId();
					if (entpId.intValue() == Integer.valueOf(service_ids[j])) {
						commd.setAudit_state(1);
					}
					commd.setType(Keys.STATUS_TYPE.STATUS_TYPE_2.getIndex());
				}
				if (ui.getIs_entp() == 1) {
					commd.setType(Keys.STATUS_TYPE.STATUS_TYPE_1.getIndex());
				}
				commd.setAdd_date(new Date());
				commd.setAdd_user_id(userId);
				commd.setAdd_user_name(userName);
				commd.setP_index(Integer.valueOf(p_indexs[j]));
				commd.setService_id(Integer.valueOf(service_ids[j]));
				commd.setService_name(service_names[j]);
				welfareDetailList.add(commd);

			}
			comma.getMap().put("welfareDetailList", welfareDetailList);
			super.getFacade().getCommWelfareApplyService().createCommWelfareApply(comma);

		}

		return mapping.findForward("success");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		CommWelfareApply welfareApply = new CommWelfareApply();
		CommWelfareDetail welfareDetail = new CommWelfareDetail();
		if (StringUtils.isNotBlank(id)) {
			welfareApply.setId(Integer.valueOf(id));
			welfareApply.setIs_del(1);
			welfareApply.setDel_date(new Date());
			welfareApply.setDel_user_id(ui.getId());

			welfareDetail.setComm_welfare_id(Integer.valueOf(id));
			welfareDetail.setIs_del(1);
			welfareDetail.setDel_date(new Date());
			welfareDetail.setDel_user_id(ui.getId());
			welfareApply.getMap().put("welfareDetail", welfareDetail);

			super.getFacade().getCommWelfareApplyService().modifyCommWelfareApply(welfareApply);

			saveMessage(request, "entity.deleted");
		} else if (ArrayUtils.isNotEmpty(pks)) {
			for (String entity_id : pks) {
				welfareApply.setId(Integer.valueOf(entity_id));
				welfareApply.setIs_del(1);
				welfareApply.setDel_date(new Date());
				welfareApply.setDel_user_id(ui.getId());

				welfareDetail.setComm_welfare_id(Integer.valueOf(entity_id));
				welfareDetail.setIs_del(1);
				welfareDetail.setDel_date(new Date());
				welfareDetail.setDel_user_id(ui.getId());
				welfareApply.getMap().put("welfareDetail", welfareDetail);

				super.getFacade().getCommWelfareApplyService().modifyCommWelfareApply(welfareApply);

			}
			saveMessage(request, "entity.deleted");
		}

		return mapping.findForward("success");
	}

	public ActionForward commInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String welfareApply_id = (String) dynaBean.get("welfareApply_id");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		if (StringUtils.isBlank(welfareApply_id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		CommWelfareDetail entity = new CommWelfareDetail();
		entity.setComm_welfare_id(Integer.valueOf(welfareApply_id));
		entity.setIs_del(0);
		entity.getMap().put("comm_name_like", comm_name_like);
		List<CommWelfareDetail> entityList = super.getFacade().getCommWelfareDetailService()
				.getCommWelfareDetailList(entity);

		List<CommInfo> commInfoList = new ArrayList<CommInfo>();

		for (CommWelfareDetail c : entityList) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(c.getComm_id());
			commInfo.setIs_del(0);
			commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
			commInfoList.add(commInfo);
		}

		request.setAttribute("commInfoList", commInfoList);

		return new ActionForward("/../manager/customer/CommWelfareApply/commInfoList.jsp");
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

		CommWelfareApply commWelfareApply = new CommWelfareApply();
		commWelfareApply.setId(Integer.valueOf(id));
		commWelfareApply.setIs_del(0);
		commWelfareApply = super.getFacade().getCommWelfareApplyService().getCommWelfareApply(commWelfareApply);

		CommWelfareDetail commWelfareDetail = new CommWelfareDetail();
		commWelfareDetail.setComm_welfare_id(Integer.valueOf(id));
		commWelfareDetail.setIs_del(0);
		List<CommWelfareDetail> commWelfareDetailList = super.getFacade().getCommWelfareDetailService()
				.getCommWelfareDetailList(commWelfareDetail);
		List<CommInfo> commList = new ArrayList<CommInfo>();
		if (commWelfareDetailList != null && commWelfareDetailList.size() > 0) {
			for (CommWelfareDetail comma : commWelfareDetailList) {
				CommInfo comm = new CommInfo();
				comm.setId(comma.getComm_id());
				comm = super.getFacade().getCommInfoService().getCommInfo(comm);
				commList.add(comm);
			}
		}
		if (null != commList && commList.size() > 0) {
			for (CommInfo t : commList) {
				CommTczhPrice ctp = new CommTczhPrice();
				ctp.setComm_id(t.getId().toString());
				List<CommTczhPrice> ctpList = super.getFacade().getCommTczhPriceService().getCommTczhPriceList(ctp);
				if (null != ctpList && ctpList.size() > 0) {
					ctp = ctpList.get(0);
					t.getMap().put("price", ctp.getComm_price());
				} else {
					t.getMap().put("price", t.getPrice_ref());
				}
			}
		}

		ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
		serviceInfo.setId(commWelfareApply.getService_id());
		serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);

		request.setAttribute("serviceInfo", serviceInfo);
		request.setAttribute("commList", commList);
		request.setAttribute("commWelfareApply_id", Integer.valueOf(id));
		return new ActionForward("/../manager/customer/CommWelfareApply/commInfoForm.jsp");
	}

	public ActionForward chooseCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String comm_type = (String) dynaBean.get("comm_type");
		String yhq_id = (String) dynaBean.get("yhq_id");
		String ajax = (String) dynaBean.get("ajax");
		String[] comm_ids = (String[]) request.getAttribute("comm_ids");
		String discount_comm_ids = (String) dynaBean.get("discount_comm_ids");
		String discount_comm_names = (String) dynaBean.get("discount_comm_names");
		String discount_comm_prices = (String) dynaBean.get("discount_comm_prices");

		if (StringUtils.isNotBlank(discount_comm_ids) && StringUtils.isNotBlank(discount_comm_names)
				&& StringUtils.isNotBlank(discount_comm_prices)) {
			logger.info("================" + discount_comm_ids + "===================");

			String[] discount_comm_id = discount_comm_ids.split(",");
			String[] discount_comm_name = discount_comm_names.split(",");
			String[] discount_comm_price = discount_comm_prices.split(",");

			List<CommInfo> commList = new ArrayList<CommInfo>();
			for (int i = 0; i < discount_comm_id.length; i++) {
				CommInfo comm = new CommInfo();
				comm.setId(Integer.valueOf(discount_comm_id[i]));
				comm.setComm_name(discount_comm_name[i]);
				comm.setPrice_ref_desc(discount_comm_price[i]);
				commList.add(comm);
			}
			dynaBean.set("discount_comm_ids", discount_comm_ids);
			dynaBean.set("discount_comm_names", discount_comm_names);
			dynaBean.set("discount_comm_prices", discount_comm_prices);
			request.setAttribute("commList", commList);
		}

		CommInfo entity = new CommInfo();
		entity.getMap().put("ajax", ajax);
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		// entity.setIs_rebate(0);// 不返利
		// entity.setIs_aid(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setAdd_user_id(ui.getId());
		entity.setIs_sell(1);
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

		List<CommInfo> commInfoList = new ArrayList<CommInfo>();
		if (ArrayUtils.isNotEmpty(comm_ids)) {
			for (int i = 0; i < comm_ids.length; i++) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(Integer.valueOf(comm_ids[i]));
				commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
				commInfoList.add(commInfo);
			}
		}
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
		request.setAttribute("commInfoList", commInfoList);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/CommWelfareApply/choose_comminfo.jsp");
	}

	public ActionForward chooseService(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String servicecenter_name_like = (String) dynaBean.get("servicecenter_name_like");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");

		Integer entpId = null;
		// 判断是商家
		if (userInfo.getIs_entp() == 1) {

			entpId = userInfo.getOwn_entp_id();

			// 判断是县域
		} else if (userInfo.getIs_fuwu() == 1) {
			ServiceCenterInfo serviceInfo = new ServiceCenterInfo();
			serviceInfo.setAdd_user_id(userInfo.getId());
			serviceInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceInfo);
			entpId = serviceInfo.getId();
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.getMap().put("servicecenter_name_like", servicecenter_name_like);
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		// Integer recordCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(entity);
		// pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		// entity.getRow().setFirst(pager.getFirstRow());
		// entity.getRow().setCount(pager.getRowCount());

		List<ServiceCenterInfo> entityList = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoList(entity);

		if (entityList != null && entityList.size() > 0) {
			for (ServiceCenterInfo ser : entityList) {

				CommWelfareApply welfareApply = new CommWelfareApply();
				welfareApply.setService_id(ser.getId());
				welfareApply.setEntp_id(entpId);
				welfareApply.setIs_del(0);
				int count = getFacade().getCommWelfareApplyService().getCommWelfareApplyCount(welfareApply);
				if (count > 0) {
					ser.getMap().put("have", true);
				}
			}
		}
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/CommWelfareApply/choose_service.jsp");
	}

	public ActionForward getService(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		System.out.println("=====================================================");
		DynaBean dynaBean = (DynaBean) form;
		String service_ids = (String) dynaBean.get("service_ids");
		JSONObject data = new JSONObject();
		if (StringUtils.isNotBlank(service_ids)) {
			String[] ids = service_ids.split(",");
			String[] service_names = new String[ids.length];
			String[] p_indexs = new String[ids.length];
			String[] full_names = new String[ids.length];
			for (int i = 0; i < ids.length; i++) {
				ServiceCenterInfo service = new ServiceCenterInfo();
				service.setId(Integer.valueOf(ids[i]));
				service = getFacade().getServiceCenterInfoService().getServiceCenterInfo(service);
				if (service != null) {
					service_names[i] = service.getServicecenter_name();
					p_indexs[i] = service.getP_index().toString();
					full_names[i] = super.getFullNameByPindex(service.getP_index());
				}
			}
			data.put("service_names", service_names);
			data.put("p_indexs", p_indexs);
			data.put("full_names", full_names);
		}
		super.renderJson(response, data.toString());
		return null;
	}

}
