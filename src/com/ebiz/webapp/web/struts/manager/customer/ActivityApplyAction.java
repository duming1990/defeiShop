package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;

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
import com.ebiz.webapp.domain.Activity;
import com.ebiz.webapp.domain.ActivityApply;
import com.ebiz.webapp.domain.ActivityApplyComm;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.ZipUtils;

/**
 * @author Qin,Yue
 * @version 2011-12-01
 */
public class ActivityApplyAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;

		String mod_id = (String) dynaBean.get("mod_id");
		Activity entity = new Activity();

		super.copyProperties(form, entity);
		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		request.setAttribute("thisAction", "ActivityApply");

		super.setActivitiListPublic(form, request, "entp");

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

		String is_audit = (String) dynaBean.get("is_audit");// 区别审核(只需要修改一个状态) 和 修改、添加
		Activity entity = new Activity();
		super.copyProperties(entity, form);
		if (StringUtils.isBlank(is_audit)) {

			if (null == entity.getId()) {

				Date sysDate = new Date();
				entity.setIs_del(0);
				// entity.setAdd_date(new Date());
				getFacade().getActivityService().createActivity(entity);
				saveMessage(request, "entity.inerted");

			} else {
				entity.getMap().put("update_content", "true");
				entity.getMap().put("update_attachment", "true");
				getFacade().getActivityService().modifyActivity(entity);
				saveMessage(request, "entity.updated");

			}
		} else {
			// 审核，只修改状态

			getFacade().getActivityService().modifyActivity(entity);
			saveMessage(request, "entity.updated");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		Activity entity = new Activity();
		// entity.setIs_del(1);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			// entity.setInfo_state(-1);
			getFacade().getActivityService().removeActivity(entity);

			saveMessage(request, "entity.deleted");

		} else if (!ArrayUtils.isEmpty(pks)) {
			entity.getMap().put("pks", pks);
			// entity.setInfo_state(-1);
			getFacade().getActivityService().removeActivity(entity);
			saveMessage(request, "entity.deleted");

		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (GenericValidator.isLong(id)) {
			Activity newsInfo = new Activity();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getActivityService().getActivity(newsInfo);

			NewsAttachment attachment = new NewsAttachment();
			attachment.setLink_id(Integer.valueOf(id));
			request.setAttribute("attachmentList",
					getFacade().getNewsAttachmentService().getNewsAttachmentList(attachment));

			if (null == newsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			// the line below is added for pagination
			newsInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, newsInfo);
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = super.getUserInfo(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			Activity newsInfo = new Activity();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getActivityService().getActivity(newsInfo);

			ActivityApply entity = new ActivityApply();
			entity.setLink_id(newsInfo.getId());
			entity.setUser_id(userInfo.getId());
			entity = getFacade().getActivityApplyService().getActivityApply(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			ActivityApplyComm a = new ActivityApplyComm();
			a.setActivity_id(entity.getLink_id());
			a.setActivity_apply_id(entity.getId());
			List<ActivityApplyComm> list = getFacade().getActivityApplyCommService().getActivityApplyCommList(a);
			request.setAttribute("list", list);

			super.copyProperties(form, entity);
			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public String formatDate(Date date) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
		return f.format(date);
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "POST")) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		if (GenericValidator.isLong(id)) {
			Activity newsInfo = new Activity();
			newsInfo.setId(Integer.valueOf(id));
			newsInfo = getFacade().getActivityService().getActivity(newsInfo);

			NewsAttachment attachment = new NewsAttachment();
			attachment.setLink_id(Integer.valueOf(id));
			request.setAttribute("attachmentList",
					getFacade().getNewsAttachmentService().getNewsAttachmentList(attachment));

			if (null == newsInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			// the line below is added for pagination
			newsInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, newsInfo);
			return new ActionForward("/../manager/customer/Activity/audit.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward chooseCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfo(request);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String activity_id = (String) dynaBean.get("activity_id");
		String[] comm_ids = (String[]) request.getAttribute("comm_ids");

		if (StringUtils.isNotBlank(activity_id)) {
			String discount_comm_ids = "";
			String discount_comm_names = "";
			String discount_comm_prices = "";
			ActivityApplyComm a = new ActivityApplyComm();
			a.setActivity_id(Integer.valueOf(activity_id));
			a.setEntp_id(ui.getOwn_entp_id());
			List<ActivityApplyComm> list = getFacade().getActivityApplyCommService().getActivityApplyCommList(a);
			if (null != list && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					CommInfo commInfo = super.getCommInfo(list.get(i).getComm_id());

					if (i == 0) {
						discount_comm_ids += list.get(i).getComm_id().toString();
						discount_comm_names += list.get(i).getComm_name().toString();
						discount_comm_prices += commInfo.getSale_price();
					} else {
						discount_comm_ids += "," + list.get(i).getComm_id().toString();
						discount_comm_names += "," + list.get(i).getComm_name().toString();
						discount_comm_prices += "," + commInfo.getSale_price();
					}
				}
			}

			dynaBean.set("discount_comm_ids", discount_comm_ids);
			dynaBean.set("discount_comm_names", discount_comm_names);
			dynaBean.set("discount_comm_prices", discount_comm_prices);
			request.setAttribute("detailList", list);
		}

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		entity.setIs_del(0);
		entity.setIs_sell(1);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setAdd_user_id(ui.getId());
		entity.getMap().put("keyword", comm_name_like);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
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

			}
		}
		request.setAttribute("commInfoList", commInfoList);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/customer/ActivityApply/choose_comminfo.jsp");
	}

	public ActionForward saveApply(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "提交失败";
		JSONObject data = new JSONObject();

		UserInfo userInfo = super.getUserInfo(request);
		if (null == userInfo) {
			msg = " 请先登录系统";
			return returnAjaxData(response, code, msg, data);
		}

		EntpInfo entpInfo = super.getEntpInfo(userInfo.getOwn_entp_id());
		if (null == entpInfo) {
			msg = "企业不存在或已被删除";
			return returnAjaxData(response, code, msg, data);
		}

		DynaBean dynaBean = (DynaBean) form;
		String _comm_ids = (String) dynaBean.get("comm_ids");
		String _comm_names = (String) dynaBean.get("comm_names");
		String activity_id = (String) dynaBean.get("activity_id");

		if (StringUtils.isBlank(_comm_ids) || StringUtils.isBlank(_comm_names)) {
			msg = "商品参数不正确";
			return returnAjaxData(response, code, msg, data);
		}
		if (StringUtils.isBlank(activity_id)) {
			msg = "活动参数不正确";
			return returnAjaxData(response, code, msg, data);
		}

		Activity activity = new Activity();
		activity.setId(Integer.valueOf(activity_id));
		activity.setIs_del(0);
		activity = getFacade().getActivityService().getActivity(activity);
		if (null == activity) {
			msg = "活动不存在或已被删除";
			return returnAjaxData(response, code, msg, data);
		}

		// 商家申请的商品
		List<ActivityApplyComm> list = new ArrayList<ActivityApplyComm>();

		String comm_ids[] = _comm_ids.split(",");
		String comm_names[] = _comm_names.split(",");
		ActivityApplyComm creart = null;
		for (int i = 0; i < comm_ids.length; i++) {
			creart = new ActivityApplyComm();
			creart.setComm_id(Integer.valueOf(comm_ids[i]));
			creart.setComm_name(comm_names[i]);
			creart.setActivity_id(activity.getId());
			creart.setEntp_id(entpInfo.getId());
			list.add(creart);
		}

		// 商家申请
		ActivityApply insert = new ActivityApply();
		insert.setAdd_date(new Date());
		insert.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		insert.setEntp_id(entpInfo.getId());
		insert.setEntp_name(entpInfo.getEntp_name());
		insert.setLink_id(activity.getId());
		insert.setUser_id(userInfo.getId());
		insert.setUser_name(userInfo.getUser_name());
		insert.setList(list);
		int i = getFacade().getActivityApplyService().createActivityApply(insert);
		if (i > 0) {
			msg = "申请成功!";
			code = "1";
		}

		return returnAjaxData(response, code, msg, data);
	}

	public ActionForward chooseActivityCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfo(request);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String activity_id = (String) dynaBean.get("activity_id");
		// 支付类型
		String pay_type = (String) dynaBean.get("pay_type");
		// 判断是否为空
		if (StringUtils.isNotBlank(activity_id)) {
			// 所选商品回显
			String discount_comm_ids = "";
			String discount_comm_names = "";
			String discount_comm_prices = "";
			// 判断是否存在活动申请记录
			ActivityApply activityApply = new ActivityApply();
			activityApply.setLink_id(Integer.valueOf(activity_id));
			activityApply.setEntp_id(ui.getOwn_entp_id());
			if (StringUtils.isNotBlank(pay_type)) {
				activityApply.setPay_type(Integer.valueOf(pay_type));
			}
			activityApply = getFacade().getActivityApplyService().getActivityApply(activityApply);

			if (null != activityApply) {
				// 活动申请存在，查询商品
				ActivityApplyComm activityApplyComm = new ActivityApplyComm();
				activityApplyComm.setActivity_id(Integer.valueOf(activity_id));
				activityApplyComm.setActivity_apply_id(activityApply.getId());
				activityApplyComm.setEntp_id(ui.getOwn_entp_id());
				List<ActivityApplyComm> activityApplyCommList = getFacade().getActivityApplyCommService()
						.getActivityApplyCommList(activityApplyComm);
				// 判断是否存在活动商品
				if (null != activityApplyCommList && activityApplyCommList.size() > 0) {
					// 集合不为空，循环集合取值
					for (int i = 0; i < activityApplyCommList.size(); i++) {
						if (i == 0) {
							// 取第一条活动商品，方便下面的操作
							discount_comm_ids += getCommInfo(activityApplyCommList.get(i).getComm_id()).getCopy_id()
									.toString();
							discount_comm_names += activityApplyCommList.get(i).getComm_name().toString();
							activityApplyCommList.get(i).setComm_id(
									getCommInfo(activityApplyCommList.get(i).getComm_id()).getCopy_id());
						} else {
							// 追加","转换成数组
							discount_comm_ids += ","
									+ getCommInfo(activityApplyCommList.get(i).getComm_id()).getCopy_id().toString();
							discount_comm_names += "," + activityApplyCommList.get(i).getComm_name().toString();
							activityApplyCommList.get(i).setComm_id(
									getCommInfo(activityApplyCommList.get(i).getComm_id()).getCopy_id());
						}
					}
				}
				// 所选商品回显
				dynaBean.set("discount_comm_ids", discount_comm_ids);
				dynaBean.set("discount_comm_names", discount_comm_names);
				dynaBean.set("discount_comm_prices", discount_comm_prices);
				request.setAttribute("detailList", activityApplyCommList);

				// 支付类型回显
				if (StringUtils.isNotBlank(pay_type)) {
					// 支付类型改变，已经选择，放现在选的
					dynaBean.set("pay_type", pay_type);
				} else {
					// 修改商品时，支付类型回显
					dynaBean.set("pay_type", activityApply.getPay_type());
				}
			}

			CommInfo entity = new CommInfo();
			super.copyProperties(entity, form);
			entity.setIs_del(0);
			entity.setIs_sell(1);
			entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
			entity.setOwn_entp_id(ui.getOwn_entp_id());
			entity.getMap().put("keyword", comm_name_like);
			entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
			// Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
			// pager.init(recordCount.longValue(), Integer.valueOf("5"), pager.getRequestPage());
			// entity.getRow().setFirst(pager.getFirstRow());
			// entity.getRow().setCount(pager.getRowCount());

			List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoList(entity);
			if (null != entityList && entityList.size() > 0) {
				for (CommInfo t : entityList) {
					CommTczhPrice ctp = new CommTczhPrice();
					ctp.setComm_id(t.getId().toString());
					List<CommTczhPrice> ctpList = super.getFacade().getCommTczhPriceService().getCommTczhPriceList(ctp);
					t.getMap().put("ctpList", ctpList);
				}
			}
			request.setAttribute("entityList", entityList);
		}
		return new ActionForward("/../manager/customer/ActivityApply/choose_activity_comminfo.jsp");
	}

	public ActionForward saveActivityComminfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "提交失败";
		JSONObject data = new JSONObject();

		UserInfo userInfo = super.getUserInfo(request);
		if (null == userInfo) {
			msg = " 请先登录系统";
			return returnAjaxData(response, code, msg, data);
		}

		EntpInfo entpInfo = super.getEntpInfo(userInfo.getOwn_entp_id());
		if (null == entpInfo) {
			msg = "企业不存在或已被删除";
			return returnAjaxData(response, code, msg, data);
		}

		DynaBean dynaBean = (DynaBean) form;
		String _comm_ids = (String) dynaBean.get("comm_ids");
		String _comm_names = (String) dynaBean.get("comm_names");
		String pay_type = (String) dynaBean.get("pay_type");
		String activity_id = (String) dynaBean.get("activity_id");

		logger.info("===_comm_ids:" + _comm_ids);

		if (Integer.valueOf(pay_type) == 0) {
			if (StringUtils.isBlank(_comm_ids) || StringUtils.isBlank(_comm_names)) {
				msg = "商品参数不正确";
				return returnAjaxData(response, code, msg, data);
			}
		}
		if (StringUtils.isBlank(activity_id)) {
			msg = "活动参数不正确";
			return returnAjaxData(response, code, msg, data);
		}

		Activity activity = new Activity();
		activity.setId(Integer.valueOf(activity_id));
		activity.setIs_del(0);
		activity = getFacade().getActivityService().getActivity(activity);
		if (null == activity) {
			msg = "活动不存在或已被删除";
			return returnAjaxData(response, code, msg, data);
		}

		List<ActivityApplyComm> list = new ArrayList<ActivityApplyComm>();
		if (Integer.valueOf(pay_type) == 0) {
			// 商家申请的商品

			String comm_ids[] = _comm_ids.split(",");
			String comm_names[] = _comm_names.split(",");
			ActivityApplyComm creart = null;
			for (int i = 0; i < comm_ids.length; i++) {
				creart = new ActivityApplyComm();
				creart.setComm_id(Integer.valueOf(comm_ids[i]));
				creart.setComm_name(comm_names[i]);
				creart.setActivity_id(activity.getId());
				creart.setEntp_id(entpInfo.getId());
				list.add(creart);
			}
		}

		// 商家申请
		// 判断是否存在活动申请记录
		ActivityApply activityApplyTemp = new ActivityApply();
		activityApplyTemp.setEntp_id(entpInfo.getId());
		activityApplyTemp.setLink_id(activity.getId());
		ActivityApply activityApplyCheck = getFacade().getActivityApplyService().getActivityApply(activityApplyTemp);

		ActivityApply insert = new ActivityApply();
		if (null == activityApplyCheck) {
			// 插入
			insert.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
			insert.setEntp_id(entpInfo.getId());
			insert.setEntp_name(entpInfo.getEntp_name());
			insert.setLink_id(activity.getId());
			insert.setPay_type(Integer.valueOf(pay_type));
			insert.setAdd_date(new Date());
			insert.setUser_id(userInfo.getId());
			insert.setUser_name(userInfo.getUser_name());
		} else {
			// 更新
			insert = activityApplyCheck;
			insert.setPay_type(Integer.valueOf(pay_type));
		}
		if (Integer.valueOf(pay_type) == 0) {
			insert.setList(list);
		}
		// 插入和删除都放在一个方法中
		int i = getFacade().getActivityApplyService().createActivityApply(insert);
		if (i > 0) {
			msg = "申请成功!";
			code = "1";
		}

		return returnAjaxData(response, code, msg, data);
	}

	public ActionForward editActivityCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfo(request);

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String activity_id = (String) dynaBean.get("activity_id");

		if (StringUtils.isNotBlank(activity_id)) {
			ActivityApply activityApply = new ActivityApply();
			activityApply.setEntp_id(ui.getOwn_entp_id());
			activityApply.setLink_id(Integer.valueOf(activity_id));
			activityApply = getFacade().getActivityApplyService().getActivityApply(activityApply);
			if (null != activityApply) {
				ActivityApplyComm a = new ActivityApplyComm();
				a.setActivity_id(Integer.valueOf(activity_id));
				a.setActivity_apply_id(activityApply.getId());
				a.setEntp_id(ui.getOwn_entp_id());
				a.getMap().put("is_aid", true);
				List<ActivityApplyComm> activityApplyCommList = getFacade().getActivityApplyCommService()
						.getActivityApplyCommList(a);
				if (null != activityApplyCommList && activityApplyCommList.size() > 0) {
					for (int i = 0; i < activityApplyCommList.size(); i++) {
						CommTczhPrice ctp = new CommTczhPrice();
						ctp.setComm_id(activityApplyCommList.get(i).getComm_id().toString());
						ctp = super.getFacade().getCommTczhPriceService().getCommTczhPrice(ctp);
						activityApplyCommList.get(i).getMap().put("commtczhprice_id", ctp.getId());
						activityApplyCommList.get(i).getMap().put("comm_price", ctp.getComm_price());
						activityApplyCommList.get(i).getMap().put("inventory", ctp.getInventory());
						activityApplyCommList
								.get(i)
								.getMap()
								.put("aid_scale",
										super.getCommInfo(activityApplyCommList.get(i).getComm_id()).getAid_scale());
					}
				}
				request.setAttribute("activityApplyCommList", activityApplyCommList);
				dynaBean.set("activityApply_id", activityApply.getId());
				// dynaBean.set("activityApply_audit", activityApply.getAudit_state());
				// request.setAttribute("activityApply_audit", activityApply.getAudit_state());
			}
		}

		return new ActionForward("/../manager/customer/ActivityApply/edit_comminfo_price.jsp");
	}

	public ActionForward saveComminfoPrice(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "提交失败";
		JSONObject data = new JSONObject();

		UserInfo userInfo = super.getUserInfo(request);
		if (null == userInfo) {
			msg = " 请先登录系统";
			return returnAjaxData(response, code, msg, data);
		}

		EntpInfo entpInfo = super.getEntpInfo(userInfo.getOwn_entp_id());
		if (null == entpInfo) {
			msg = "企业不存在或已被删除";
			return returnAjaxData(response, code, msg, data);
		}

		DynaBean dynaBean = (DynaBean) form;

		String commtczhprice_ids[] = request.getParameterValues("commtczhprice_id");
		String comm_prices[] = request.getParameterValues("comm_price");
		String inventorys[] = request.getParameterValues("inventory");
		String aid_scales[] = request.getParameterValues("aid_scale");
		String is_aids[] = request.getParameterValues("is_aid");
		String activity_id = (String) dynaBean.get("activity_id");
		String activityApply_id = (String) dynaBean.get("activityApply_id");

		if (null == commtczhprice_ids || commtczhprice_ids.length == 0) {
			msg = "商品参数不正确";
			return returnAjaxData(response, code, msg, data);
		}

		if (null == inventorys || inventorys.length == 0) {
			msg = "库存数量参数不正确";
			return returnAjaxData(response, code, msg, data);
		}
		if (StringUtils.isBlank(activity_id)) {
			msg = "活动参数不正确";
			return returnAjaxData(response, code, msg, data);
		}

		Activity activity = new Activity();
		activity.setId(Integer.valueOf(activity_id));
		activity.setIs_del(0);
		activity = getFacade().getActivityService().getActivity(activity);
		if (null == activity) {
			msg = "活动不存在或已被删除";
			return returnAjaxData(response, code, msg, data);
		}

		// 商家申请的商品
		List<CommTczhPrice> commTczhPriceList = new ArrayList<CommTczhPrice>();
		CommTczhPrice commTczhPrice = null;
		for (int i = 0; i < commtczhprice_ids.length; i++) {
			commTczhPrice = new CommTczhPrice();
			commTczhPrice.setId(Integer.valueOf(commtczhprice_ids[i]));
			commTczhPrice.setComm_price(new BigDecimal(comm_prices[i]));
			commTczhPrice.setInventory(Integer.valueOf(inventorys[i]));

			if (Integer.valueOf(is_aids[i]) == 1) {// 在进行线下活动修改商品时，如果选择的是是扶贫商品才可以设置扶贫比例
				commTczhPrice.getMap().put("aid_scale", aid_scales[i]);
			}
			commTczhPrice.getMap().put("is_aid", is_aids[i]);
			commTczhPriceList.add(commTczhPrice);
		}

		// 商家申请
		ActivityApply update = new ActivityApply();
		update.setId(Integer.valueOf(activityApply_id));
		update.setCommTczhPriceList(commTczhPriceList);
		update.getMap().put("commTczhPrice_update", true);
		int i = getFacade().getActivityApplyService().modifyActivityApply(update);
		if (i > 0) {
			msg = "申请成功!";
			code = "1";
		}

		return returnAjaxData(response, code, msg, data);
	}

	public ActionForward downloadPoorInfoQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);

		UserInfo ui = getUserInfo(request);
		DynaBean dynaBean = (DynaBean) form;
		String en_end_date = (String) dynaBean.get("en_end_date");
		String st_start_date = (String) dynaBean.get("st_start_date");
		String title_like = (String) dynaBean.get("title_like");
		String id = (String) dynaBean.get("id");

		ActivityApply entity = new ActivityApply();
		entity.getMap().put("title_like", title_like);
		entity.getMap().put("st_start_date", st_start_date);
		entity.getMap().put("en_end_date", en_end_date);
		entity.setEntp_id(ui.getOwn_entp_id());
		if (StringUtils.isNotBlank(id)) {
			entity.setId(Integer.valueOf(id));
		}

		super.copyProperties(entity, form);

		entity.setAudit_state(1);
		List<ActivityApply> entityList = super.getFacade().getActivityApplyService().getActivityApplyList(entity);
		String realPath = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String fname = EncryptUtilsV2.encodingFileName("线下活动商家二维码导出_日期" + sdFormat_ymd.format(new Date()) + ".zip");
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);

		if (null != entityList && entityList.size() > 0) {
			ZipOutputStream zipout = new ZipOutputStream(response.getOutputStream());
			System.out.println("==zipout:" + zipout);
			File[] files = new File[entityList.size()];
			int i = 0;
			for (ActivityApply temp : entityList) {
				if (null != temp.getQrcode_path()) {
					File savePath = new File(realPath + temp.getQrcode_path());
					if (savePath.exists())
						files[i] = savePath;
				}
				i++;
			}
			logger.info("==files:" + files.length);
			ZipUtils.zipFile(files, "", zipout);
			zipout.flush();
			zipout.close();
			return null;
		} else {
			return null;
		}
	}

	public ActionForward getPoorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "提交失败";
		JSONObject data = new JSONObject();

		UserInfo userInfo = super.getUserInfo(request);
		if (null == userInfo) {
			msg = " 请先登录系统";
			return returnAjaxData(response, code, msg, data);
		}

		String comm_id = request.getParameter("comm_id");
		if (null != comm_id) {
			CommInfo commInfo = super.getCommInfo(Integer.valueOf(comm_id));
			if (null == commInfo) {
				msg = "商品不存在或已被删除";
				return returnAjaxData(response, code, msg, data);
			}
		}
		// 扶贫对象
		CommInfoPoors commInfoPoors = new CommInfoPoors();
		commInfoPoors.setComm_id(Integer.valueOf(comm_id));
		List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
				.getCommInfoPoorsList(commInfoPoors);
		if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
			String temp_poor_ids = ",";
			for (CommInfoPoors temp : commInfoPoorsList) {
				temp_poor_ids += temp.getPoor_id().toString() + ",";
			}
			request.setAttribute("temp_poor_ids", temp_poor_ids);
		}

		// 一个商品最多扶贫对象数量
		BaseData baseData1901 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1901.getIndex());
		request.setAttribute("baseData1901", baseData1901);

		request.setAttribute("commInfoPoorsList", commInfoPoorsList);
		// end

		// DynaBean dynaBean = (DynaBean) form;
		// String _comm_ids = (String) dynaBean.get("comm_ids");
		// String _comm_names = (String) dynaBean.get("comm_names");
		// String activity_id = (String) dynaBean.get("activity_id");

		return new ActionForward("/../manager/customer/ActivityApply/fupin.jsp");
	}

	public ActionForward savePoorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "修改失败";
		JSONObject data = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String _comm_id = (String) dynaBean.get("comm_id");
		UserInfo userInfo = super.getUserInfo(request);
		if (null == userInfo) {
			msg = " 请先登录系统";
			return returnAjaxData(response, code, msg, data);
		}

		String comm_id = request.getParameter("comm_id");// 对应商品的id
		String[] poor_ids = request.getParameterValues("poor_ids");// 扶贫对象数组
		// 扶贫对象
		CommInfoPoors commInfoPoors = new CommInfoPoors();

		super.copyProperties(commInfoPoors, form);

		commInfoPoors.setComm_id(Integer.valueOf(comm_id));
		commInfoPoors.getMap().put("poor_ids", poor_ids);
		commInfoPoors.setAdd_user_id(userInfo.getId());
		Integer i = super.getFacade().getCommInfoPoorsService().removeAndInsertCommInfoPoors(commInfoPoors);
		if (i > 0) {
			msg = "修改成功!";
			code = "1";
			return returnAjaxData(response, code, msg, data);
		}

		return returnAjaxData(response, code, msg, data);
		// end

		// return new ActionForward("/../manager/customer/ActivityApply/fupin.jsp");
	}
}