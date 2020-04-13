package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.MServiceBaseLink;
import com.ebiz.webapp.domain.ServiceBaseLink;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Liu,Jia
 * @version 2012-02-22
 */
public class ServiceInfoAuditAction extends BaseAdminAction {
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
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");

		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_3100.getIndex(), request);
		request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());

		return mapping.findForward("input");
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String servicecenter_name_like = (String) dynaBean.get("servicecenter_name_like");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String update_date_eq = (String) dynaBean.get("update_date_eq");
		String orderByInfo = (String) dynaBean.get("orderByInfo");
		String wl_order_state = (String) dynaBean.get("wl_order_state");
		String is_del = (String) dynaBean.get("is_del");
		String add_date_st = (String) dynaBean.get("add_date_st");
		String add_date_en = (String) dynaBean.get("add_date_en");
		String update_date_st = (String) dynaBean.get("update_date_st");
		String update_date_en = (String) dynaBean.get("update_date_en");
		String audit_date_st = (String) dynaBean.get("audit_date_st");
		String audit_date_en = (String) dynaBean.get("audit_date_en");
		String pay_date_st = (String) dynaBean.get("pay_date_st");
		String pay_date_en = (String) dynaBean.get("pay_date_en");
		String code = (String) dynaBean.get("code");

		ServiceCenterInfo entity = new ServiceCenterInfo();

		super.copyProperties(entity, form);

		entity.setIs_del(0);
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

		entity.getMap().put("add_date_st", add_date_st);
		entity.getMap().put("add_date_en", add_date_en);
		entity.getMap().put("update_date_st", update_date_st);
		entity.getMap().put("update_date_en", update_date_en);
		entity.getMap().put("audit_date_st", audit_date_st);
		entity.getMap().put("audit_date_en", audit_date_en);
		entity.getMap().put("pay_date_st", pay_date_st);
		entity.getMap().put("pay_date_en", pay_date_en);

		List<ServiceCenterInfo> entityList = getFacade().getServiceCenterInfoService().getServiceCenterInfoList(entity);

		model.put("entityList", entityList);
		String content = getFacade().getTemplateService().getContent("ServiceinfoAudit/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("合伙人信息.xls");
		if (StringUtils.isBlank(code)) {
			code = "UTF-8";
		}
		response.setCharacterEncoding(code);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
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
		super.copyProperties(entity, form);

		BaseProvince baseProvince = new BaseProvince();

		if (GenericValidator.isLong(country) && country.length() >= 6) {
			entity.setP_index(Integer.valueOf(country));
		}
		if (StringUtils.isBlank(is_audit)) {
			if (null == entity.getP_index()) {
				String msg = "请选择区域";
				super.showMsgForManager(request, response, msg);
				return null;
			}
		}
		String server_name = "";
		if (null != entity.getP_index()) {
			baseProvince.setP_index(entity.getP_index().longValue());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			server_name = StringUtils.replace(baseProvince.getFull_name(), ",", "") + "合伙人";
		}

		if (!GenericValidator.isLong(id)) {
			entity.setAdd_date(new Date());
			entity.setServicecenter_name(server_name);
			super.getFacade().getServiceCenterInfoService().createServiceCenterInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {
			if (StringUtils.isNotBlank(is_audit)) {
				entity.setAudit_user_id(ui.getId());
				entity.setAudit_date(new Date());
				ServiceCenterInfo centerInfo = new ServiceCenterInfo();
				centerInfo.setId(Integer.valueOf(id));
				centerInfo.setIs_del(0);
				centerInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(centerInfo);
				if (null != centerInfo) {
					entity.getMap().put("user_id", centerInfo.getAdd_user_id());
				}
			}
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());

			if (StringUtils.isBlank(is_audit)) {
				entity.setServicecenter_name(server_name);
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
			entityQuery = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entityQuery);
			if (null != entityQuery) {
				if (entityQuery.getAudit_state() == 1 && StringUtils.isBlank(entityQuery.getServicecenter_no())) {
					entity.getMap().put("need_create_user_info_by_serViceNo", "true");
					int serviceNo = super.createServiceCenterNo(entityQuery.getId());
					entity.setServicecenter_no("J" + serviceNo);

					entity.getMap().put("need_create_entp_info_by_serViceNo", "true");
				}
			}

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

	public ActionForward cancelServiceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		ServiceCenterInfo entity = new ServiceCenterInfo();
		// super.copyProperties(entity, form);

		if (GenericValidator.isInt(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			entity.setAudit_state(-1);// 审核不通过
			entity.setAudit_date(new Date());
			entity.setAudit_user_id(userInfo.getId());

			entity.getMap().put("cancel_link_user_info_is_fuwu", "true");

			ServiceCenterInfo entityQuery = new ServiceCenterInfo();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entityQuery);
			if (null != entityQuery) {
				entity.getMap().put("user_id", entityQuery.getAdd_user_id());
				entity.setP_index(entityQuery.getP_index());
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

			return new ActionForward("/../manager/admin/ServiceInfoAudit/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
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

		return mapping.findForward("view");
	}

	public ActionForward viewWithUserid(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");

		if (!GenericValidator.isLong(user_id)) {
			String msg = "参数错误";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setAdd_user_id(new Integer(user_id));
		entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);

		super.copyProperties(form, entity);

		super.setBaseDataListToSession(Keys.BaseDataType.Base_Data_type_3100.getIndex(), request);

		return mapping.findForward("view");
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
			entity.getMap().put("update_link_user_info", "true");

			ServiceCenterInfo entityQuery = new ServiceCenterInfo();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entityQuery);
			if (null != entityQuery) {
				entity.getMap().put("user_id", entityQuery.getAdd_user_id());
			}

			getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {
				ServiceCenterInfo entity = new ServiceCenterInfo();
				entity.setIs_del(1);
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());
				entity.setId(Integer.valueOf(cur_id));
				entity.getMap().put("update_link_user_info", "true");

				ServiceCenterInfo entityQuery = new ServiceCenterInfo();
				entityQuery.setId(Integer.valueOf(id));
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

	public ActionForward cancelFuWu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
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

			return new ActionForward("/../manager/admin/ServiceInfoAudit/cancelFuWu.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward xufeiFuWu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
			// entity.setIs_del(0);
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end

			if (1 == entity.getEffect_state() && null != entity.getEffect_end_date()) {

				BaseData baseData = new BaseData();
				if (entity.getServicecenter_level() == 1)
					baseData.setId(Keys.BaseDataServiceCenter.BASE_DATA_SERVICE_CENTER_1801.getIndex());
				if (entity.getServicecenter_level() == 2)
					baseData.setId(Keys.BaseDataServiceCenter.BASE_DATA_SERVICE_CENTER_1802.getIndex());
				if (entity.getServicecenter_level() == 3)
					baseData.setId(Keys.BaseDataServiceCenter.BASE_DATA_SERVICE_CENTER_1803.getIndex());
				baseData = super.getFacade().getBaseDataService().getBaseData(baseData);

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(entity.getEffect_end_date());
				calendar.add(Calendar.YEAR, 1);

				entity.setPay_success(1);
				entity.setPay_date(new Date());
				entity.setPay_type(Keys.PayType.PAY_TYPE_1.getIndex());
				entity.getMap().put("user_id", entity.getAdd_user_id());
				// entity.getMap().put("pay_success_update_link_table", "true");//支付成功减余额
				entity.getMap().put("pay_success_create_orderInfo", "true");
				entity.getMap().put("effect_eq_1_update_link_table", "true");
				entity.getMap().put("order_money", baseData.getPre_number());
				entity.getMap().put("out_trade_no", super.creatTradeIndex());
				entity.setEffect_end_date(calendar.getTime());

				super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);

			} else {
				this.saveError(request, "errors.parm");
				return mapping.findForward("list");
			}

			saveMessage(request, "entity.updated");
			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&");
			pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
			// pathBuffer.append("&mod_id=" + mod_id);
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;
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

	public ActionForward zhifuFuWu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String order_pay_type = (String) dynaBean.get("order_pay_type");
		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
			entity.setIs_del(0);
			entity.setAudit_state(1);
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end

			if (1 == entity.getAudit_state() && 0 == entity.getEffect_state() && 0 == entity.getPay_success()) {

				BaseData baseData = new BaseData();
				if (entity.getServicecenter_level() == 1)
					baseData.setId(Keys.BaseDataServiceCenter.BASE_DATA_SERVICE_CENTER_1801.getIndex());
				if (entity.getServicecenter_level() == 2)
					baseData.setId(Keys.BaseDataServiceCenter.BASE_DATA_SERVICE_CENTER_1802.getIndex());
				if (entity.getServicecenter_level() == 3)
					baseData.setId(Keys.BaseDataServiceCenter.BASE_DATA_SERVICE_CENTER_1803.getIndex());
				baseData = super.getFacade().getBaseDataService().getBaseData(baseData);

				int pay_type = Keys.PayType.PAY_TYPE_1.getIndex();
				if (StringUtils.isNotBlank(order_pay_type) && GenericValidator.isInt(order_pay_type)) {
					pay_type = Integer.valueOf(order_pay_type);
				}
				ServiceCenterInfo serviceCenterInfoUpdate = new ServiceCenterInfo();
				serviceCenterInfoUpdate.setId(Integer.valueOf(id));
				serviceCenterInfoUpdate.setPay_success(1);
				serviceCenterInfoUpdate.setPay_date(new Date());
				serviceCenterInfoUpdate.setPay_type(pay_type);
				serviceCenterInfoUpdate.getMap().put("user_id", entity.getAdd_user_id());
				serviceCenterInfoUpdate.getMap().put("pay_success_create_orderInfo", "true");
				serviceCenterInfoUpdate.getMap().put("order_money", baseData.getPre_number());
				serviceCenterInfoUpdate.getMap().put("out_trade_no", super.creatTradeIndex());
				serviceCenterInfoUpdate.setServicecenter_level(entity.getServicecenter_level());

				super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(serviceCenterInfoUpdate);

			} else {
				this.saveError(request, "errors.parm");
				return mapping.findForward("list");
			}

			saveMessage(request, "entity.updated");
			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&");
			pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
			// pathBuffer.append("&mod_id=" + mod_id);
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
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
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			String msg = "未查到该合伙人！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		request.setAttribute("payTypeList", Keys.PayType.values());

		dynaBean.set("effect_date", new Date());
		dynaBean.set("effect_end_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));

		return new ActionForward("/../manager/admin/ServiceInfoAudit/setPayInfo.jsp");
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
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			String msg = "未查到该合伙人！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		return new ActionForward("/../manager/admin/ServiceInfoAudit/add_Brougth_Account.jsp");
	}

	public ActionForward addDomainSite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setId(Integer.valueOf(id));
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		request.setAttribute("serviceCenterInfo", serviceCenterInfo);
		if (null == serviceCenterInfo) {
			String msg = "未查到该合伙人！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		return new ActionForward("/../manager/admin/ServiceInfoAudit/addDomainSite.jsp");
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

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

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

	public ActionForward saveDomainSite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String domain_site = (String) dynaBean.get("domain_site");

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
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			msg = "合伙人不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		if (StringUtils.isBlank(domain_site)) {
			msg = "二级域名不能为空";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		ServiceCenterInfo serviceCenterInfoSelect = new ServiceCenterInfo();
		serviceCenterInfoSelect.setDomain_site(domain_site);
		serviceCenterInfoSelect.getMap().put("id_not_in", Integer.parseInt(id));
		int count = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(serviceCenterInfoSelect);
		if (count > 0) {
			msg = "二级域名重复，请核对后再操作！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		ServiceCenterInfo scinfo = new ServiceCenterInfo();
		scinfo.setId(Integer.valueOf(id));
		scinfo.setDomain_site(domain_site);
		super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(scinfo);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward delDomainSite(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (null == serviceCenterInfo) {
			msg = "合伙人不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}
		ServiceCenterInfo scinfo = new ServiceCenterInfo();
		scinfo.setId(Integer.valueOf(id));
		scinfo.setDomain_site("");
		super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(scinfo);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward validatePindex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isNotBlank(p_index)) {
			ServiceCenterInfo sInfo = new ServiceCenterInfo();
			sInfo.setP_index(Integer.valueOf(p_index));
			sInfo.setIs_del(0);
			int count = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(sInfo);
			if (Integer.valueOf(count) > 0) {
				ret = "1";
				msg = "该区域合伙人已存在,请前往查看！";
			}
			data.put("ret", ret);
			data.put("msg", msg);
		}
		super.renderJson(response, data.toJSONString());
		return null;
	}

	public ActionForward indexTsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		String link_id = (String) dynaBean.get("link_id");
		ServiceBaseLink entity = new ServiceBaseLink();
		if (StringUtils.isNotEmpty(link_id)) {
			entity.setLink_id(Integer.valueOf(link_id));
		}
		entity.setIs_del(0);
		entity.setLink_type(0);
		entity = getFacade().getServiceBaseLinkService().getServiceBaseLink(entity);
		super.copyProperties(form, entity);
		return new ActionForward("/../manager/admin/ServiceInfoAudit/formTsg.jsp");
	}

	public ActionForward saveTsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String link_id = (String) dynaBean.get("link_id");
		ServiceBaseLink entity = new ServiceBaseLink();
		super.copyProperties(entity, form);
		if (StringUtils.isNotEmpty(link_id)) {
			entity.setLink_id(Integer.valueOf(link_id));
		}
		ServiceCenterInfo serviceInfo = super.getServiceCenterInfo(link_id);
		if (null == serviceInfo) {
			String msg = "该合伙人不存在";
			return super.showMsgForManager(request, response, msg);
		}
		String TsgName = serviceInfo.getServicecenter_name();
		int p_index = serviceInfo.getP_index();
		String ctx = super.getCtxPath(request, true);
		String link_url = ctx + "/IndexTsg.do?method=index&Link_id=" + link_id + "&p_index=" + p_index;
		if (null == entity.getId()) {
			entity.setIs_del(0);
			entity.setAdd_time(new Date());
			entity.setLink_type(0);
			entity.setTitle(TsgName);
			entity.setP_index(p_index);
			entity.setLink_url(link_url);
			getFacade().getServiceBaseLinkService().createServiceBaseLink(entity);
			saveMessage(request, "entity.inerted");
		} else {
			getFacade().getServiceBaseLinkService().modifyServiceBaseLink(entity);
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/admin/ServiceInfoAudit.do?method=indexTsg");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_id=" + link_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward indexMTsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		String link_id = (String) dynaBean.get("link_id");
		MServiceBaseLink entity = new MServiceBaseLink();
		if (StringUtils.isNotEmpty(link_id)) {
			entity.setLink_id(Integer.valueOf(link_id));
		}
		entity.setIs_del(0);
		entity.setLink_type(0);
		entity = getFacade().getMServiceBaseLinkService().getMServiceBaseLink(entity);
		super.copyProperties(form, entity);
		return new ActionForward("/../manager/admin/ServiceInfoAudit/formMTsg.jsp");
	}

	public ActionForward saveMTsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String link_id = (String) dynaBean.get("link_id");
		MServiceBaseLink entity = new MServiceBaseLink();
		super.copyProperties(entity, form);
		if (StringUtils.isNotEmpty(link_id)) {
			entity.setLink_id(Integer.valueOf(link_id));
		}
		ServiceCenterInfo serviceInfo = super.getServiceCenterInfo(link_id);
		if (null == serviceInfo) {
			String msg = "该合伙人不存在";
			return super.showMsgForManager(request, response, msg);
		}
		String TsgName = serviceInfo.getServicecenter_name();
		int p_index = serviceInfo.getP_index();
		String ctx = super.getCtxPath(request, true);
		String link_url = ctx + "/m/IndexMTsg.do?method=index&link_id=" + link_id + "&p_index=" + p_index;
		if (null == entity.getId()) {
			entity.setIs_del(0);
			entity.setAdd_time(new Date());
			entity.setLink_type(0);
			entity.setTitle(TsgName);
			entity.setP_index(p_index);
			entity.setLink_url(link_url);
			getFacade().getMServiceBaseLinkService().createMServiceBaseLink(entity);
			saveMessage(request, "entity.inerted");
		} else {
			getFacade().getMServiceBaseLinkService().modifyMServiceBaseLink(entity);
			saveMessage(request, "entity.updated");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/admin/ServiceInfoAudit.do?method=indexMTsg");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&link_id=" + link_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}
}
