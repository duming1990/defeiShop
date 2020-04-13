package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class VirtualServiceInfoAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		setNaviStringToRequestScope(request);

		Pager pager = (Pager) dynaBean.get("pager");
		String servicecenter_name_like = (String) dynaBean.get("servicecenter_name_like");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String update_date_eq = (String) dynaBean.get("update_date_eq");
		String orderByInfo = (String) dynaBean.get("orderByInfo");
		String wl_order_state = (String) dynaBean.get("wl_order_state");
		String is_del = (String) dynaBean.get("is_del");

		ServiceCenterInfo entity = new ServiceCenterInfo();

		super.copyProperties(entity, form);

		entity.setIs_del(0);
		entity.setIs_virtual(1);// 是虚拟村站
		if (GenericValidator.isInt(is_del)) {
			entity.setIs_del(Integer.valueOf(is_del));
		}
		entity.getMap().put("servicecenter_name_like", servicecenter_name_like);
		entity.getMap().put("add_user_name_like", add_user_name_like);

		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}

		entity.getMap().put("update_date_eq", update_date_eq);

		if (StringUtils.isNotBlank(wl_order_state) && GenericValidator.isInt(wl_order_state)) {
			entity.setWl_order_state(new Integer(wl_order_state));
		}
		entity.getMap().put("orderByInfo", orderByInfo);

		String add_date_st = (String) dynaBean.get("add_date_st");
		String add_date_en = (String) dynaBean.get("add_date_en");
		String update_date_st = (String) dynaBean.get("update_date_st");
		String update_date_en = (String) dynaBean.get("update_date_en");
		String audit_date_st = (String) dynaBean.get("audit_date_st");
		String audit_date_en = (String) dynaBean.get("audit_date_en");
		String pay_date_st = (String) dynaBean.get("pay_date_st");
		String pay_date_en = (String) dynaBean.get("pay_date_en");
		entity.getMap().put("add_date_st", add_date_st);
		entity.getMap().put("add_date_en", add_date_en);
		entity.getMap().put("update_date_st", update_date_st);
		entity.getMap().put("update_date_en", update_date_en);
		entity.getMap().put("audit_date_st", audit_date_st);
		entity.getMap().put("audit_date_en", audit_date_en);
		entity.getMap().put("pay_date_st", pay_date_st);
		entity.getMap().put("pay_date_en", pay_date_en);

		Integer recordCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ServiceCenterInfo> entityList = getFacade().getServiceCenterInfoService()
				.getServiceCenterInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		request.setAttribute("payTypeList", Keys.PayType.values());
		request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());

		request.setAttribute("init_pwd", Keys.INIT_PWD);

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");

		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_3100.getIndex(), request);
		request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());

		return mapping.findForward("input");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
			entity.setIs_virtual(1);// 是虚拟村站
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));

			super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_3100.getIndex(), request);

			BaseImgs baseImgs = new BaseImgs();
			baseImgs.setLink_id(Integer.valueOf(id));
			baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
			List<BaseImgs> list = super.getFacade().getBaseImgsService().getBaseImgsList(baseImgs);
			if (list != null && list.size() > 0) {
				request.setAttribute("img_id_card_zm", list.get(0).getFile_path());
				request.setAttribute("img_id_card_fm", list.get(1).getFile_path());
			}

			request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());

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

		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String img_id_card_zm = (String) dynaBean.get("img_id_card_zm");
		String img_id_card_fm = (String) dynaBean.get("img_id_card_fm");

		String country = (String) dynaBean.get("country");

		String[] basefiles = { img_id_card_zm, img_id_card_fm };

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setIs_virtual(1);

		super.copyProperties(entity, form);

		if (!GenericValidator.isLong(id)) {
			ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
			serviceCenterInfo.setIs_virtual(1);
			int count = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(serviceCenterInfo);
			if (count > 0) {
				int p_index = 990000 + (count + 1);
				entity.setP_index(Integer.valueOf(p_index));
			} else {
				entity.setP_index(Integer.valueOf(990001));
			}
			entity.setAdd_date(new Date());
			super.getFacade().getServiceCenterInfoService().createServiceCenterInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {
			if (StringUtils.isNotBlank(is_audit)) {
				entity.setAudit_user_id(ui.getId());
				entity.setAudit_date(new Date());
			}
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());

			if (StringUtils.isBlank(is_audit)) {
				BaseImgs baseImg = new BaseImgs();
				baseImg.getMap().put("link_id", id);
				baseImg.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
				super.getFacade().getBaseImgsService().removeBaseImgs(baseImg);
				for (int i = 0; i < basefiles.length; i++) {
					if (StringUtils.isNotBlank(basefiles[i])) {
						BaseImgs baseImgs = new BaseImgs();
						baseImgs.setLink_id(Integer.valueOf(id));
						baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
						baseImgs.setFile_path(basefiles[i]);
						super.getFacade().getBaseImgsService().createBaseImgs(baseImgs);
					}
				}
			}

			super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);
			if (StringUtils.isNotBlank(is_audit))
				saveMessage(request, "entity.audit");
			else
				saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
			entity.setIs_virtual(1);
			// entity.setIs_del(0);
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			entity.setAudit_desc(null);
			super.copyProperties(form, entity);

			super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_3100.getIndex(), request);

			request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());

			BaseImgs baseImgs = new BaseImgs();
			baseImgs.setLink_id(Integer.valueOf(id));
			baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
			List<BaseImgs> list = super.getFacade().getBaseImgsService().getBaseImgsList(baseImgs);
			if (list != null && list.size() > 0) {
				request.setAttribute("img_id_card_zm", list.get(0).getFile_path());
				request.setAttribute("img_id_card_fm", list.get(1).getFile_path());
			}

			return new ActionForward("/../manager/admin/VirtualServiceInfo/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward confirmServiceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		ServiceCenterInfo entity = new ServiceCenterInfo();

		// 确认的时候生成对应的用户信息
		if (GenericValidator.isInt(id)) {

			ServiceCenterInfo entityQuery = new ServiceCenterInfo();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery.setIs_virtual(1);// 是虚拟村站
			entityQuery = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entityQuery);
			if (null != entityQuery) {
				if (entityQuery.getAudit_state() == 1 && StringUtils.isBlank(entityQuery.getServicecenter_no())) {
					entity.getMap().put("need_create_user_info_by_serViceNo", "true");
					int serviceNo = super.createServiceCenterNo(entityQuery.getId(), 1);
					entity.setServicecenter_no("D" + serviceNo);
					entity.getMap().put("need_create_entp_info_by_serViceNo", "true");
				}
			}
			entity.setIs_virtual(1);// 是虚拟村站
			entity.setId(Integer.valueOf(id));
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			entity.setEffect_state(1);
			entity.setEffect_date(new Date());
			entity.getMap().put("update_link_user_info", "true");

			if (null != entityQuery.getAdd_user_id()) {
				entity.getMap().put("user_id", entityQuery.getAdd_user_id());
			}

			super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);

			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward setPayInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setId(Integer.valueOf(id));
		serviceCenterInfo.setIs_virtual(1);// 是虚拟村站
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			String msg = "未查到该合伙人！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		request.setAttribute("payTypeList", Keys.PayType.values());

		dynaBean.set("effect_date", new Date());
		dynaBean.set("effect_end_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));

		return new ActionForward("/../manager/admin/VirtualServiceInfo/setPayInfo.jsp");
	}

	public ActionForward savePayInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setId(Integer.valueOf(id));
		serviceCenterInfo.setIs_virtual(1);// 是虚拟村站
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			msg = "合伙人不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		ServiceCenterInfo serviceCenterInfoUpdate = new ServiceCenterInfo();
		serviceCenterInfoUpdate.setId(Integer.valueOf(id));
		serviceCenterInfoUpdate.setIs_virtual(1);// 是虚拟村站
		super.copyProperties(serviceCenterInfoUpdate, form);
		super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(serviceCenterInfoUpdate);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward cancelFuWu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
			entity.setIs_virtual(1);// 是虚拟村站
			// entity.setIs_del(0);
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			return new ActionForward("/../manager/admin/VirtualServiceInfo/cancelFuWu.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward saveCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String audit_note = (String) dynaBean.get("audit_note");

		ServiceCenterInfo entity = new ServiceCenterInfo();

		if (GenericValidator.isInt(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			// entity.setEffect_date(new Date());
			entity.setEffect_state(0);// 未生效
			entity.setPay_success(0);// 未支付
			// entity.setEffect_end_date(new Date());
			entity.setAudit_state(-1);// 审核不通过
			entity.setAudit_date(new Date());
			entity.setAudit_user_id(userInfo.getId());
			entity.setAudit_desc(audit_note);
			entity.setIs_virtual(1);// 是虚拟村站
			entity.getMap().put("cancel_link_user_info_is_fuwu", "true");

			ServiceCenterInfo entityQuery = new ServiceCenterInfo();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entityQuery);
			if (null != entityQuery) {
				entity.getMap().put("user_id", entityQuery.getAdd_user_id());
				entity.setP_index(entityQuery.getP_index());
			}
			// ---操作日志---
			entity.getMap().put("cancel_oper_uid", userInfo.getId());
			entity.getMap().put("cancel_oper_uname", userInfo.getUser_name());
			// ---操作日志---
			// entity.setIs_del(1);// 取消后，设置成已删除
			super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);

			saveMessage(request, "entity.updated");
		}
		super.copyProperties(entity, form);

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward add_Brougth_Account(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setId(Integer.valueOf(id));
		serviceCenterInfo.setIs_virtual(1);// 是虚拟村站
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			String msg = "未查到该合伙人！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		return new ActionForward("/../manager/admin/VirtualServiceInfo/add_Brougth_Account.jsp");
	}

	public ActionForward saveBrought(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setId(Integer.valueOf(id));
		serviceCenterInfo.setIs_virtual(1);// 是虚拟村站
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			msg = "合伙人不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		ServiceCenterInfo serviceCenterInfoUpdate = new ServiceCenterInfo();
		serviceCenterInfoUpdate.setId(Integer.valueOf(id));
		super.copyProperties(serviceCenterInfoUpdate, form);
		super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(serviceCenterInfoUpdate);

		msg = "添加成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			entity.setIs_virtual(1);// 是虚拟村站
			entity.getMap().put("update_link_user_info", "true");

			ServiceCenterInfo entityQuery = new ServiceCenterInfo();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery.setIs_virtual(1);// 是虚拟村站
			entityQuery = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entityQuery);
			if (null != entityQuery && null != entityQuery.getAdd_user_id()) {
				entity.getMap().put("user_id", entityQuery.getAdd_user_id());
			}

			getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {
				ServiceCenterInfo entity = new ServiceCenterInfo();
				entity.setIs_virtual(1);// 是虚拟
				entity.setIs_del(1);
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());
				entity.setId(Integer.valueOf(cur_id));
				entity.getMap().put("update_link_user_info", "true");

				ServiceCenterInfo entityQuery = new ServiceCenterInfo();
				entityQuery.setId(Integer.valueOf(id));
				entityQuery.setIs_virtual(1);// 是虚拟
				entityQuery = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entityQuery);
				if (null != entityQuery) {
					entity.getMap().put("user_id", entityQuery.getAdd_user_id());
				}
				getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);

			}
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
			entity.setIs_virtual(1);
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_3100.getIndex(), request);

			// UserInfo userInfo = new UserInfo();
			// userInfo.setId(entity.getAdd_user_id());
			// userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

			BaseImgs baseImgs = new BaseImgs();
			baseImgs.setLink_id(Integer.valueOf(id));
			baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
			List<BaseImgs> list = super.getFacade().getBaseImgsService().getBaseImgsList(baseImgs);
			if (list != null && list.size() > 0) {
				request.setAttribute("img_id_card_zm", list.get(0).getFile_path());
				request.setAttribute("img_id_card_fm", list.get(1).getFile_path());
			}
		}

		request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());

		return new ActionForward("/../manager/admin/ServiceInfoAudit/view.jsp");
	}

}
