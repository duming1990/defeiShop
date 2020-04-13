package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
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
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.PoorCuoShi;
import com.ebiz.webapp.domain.PoorFamily;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.PoorZeRen;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.util.ExcelUtils;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.ZipUtils;

public class PoorManagerAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String real_name_like = (String) dynaBean.get("real_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String id_card_like = (String) dynaBean.get("id_card_like");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);
		if (ui.getIs_village() != null || ui.getIs_village() == 1) {
			entity.setVillage_id(ui.getOwn_village_id());
		}

		entity.setIs_del(0);
		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		if (StringUtils.isNotBlank(id_card_like)) {
			entity.getMap().put("id_card_like", id_card_like.toUpperCase().trim());
		}

		Integer recordCount = getFacade().getPoorInfoService().getVillageManagerPoorInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<PoorInfo> list = getFacade().getPoorInfoService().getVillageManagerPoorInfoPaginatedList(entity);
		if (list != null && list.size() > 0) {
			for (PoorInfo temp : list) {
				UserInfo userInfo = new UserInfo();
				userInfo.setPoor_id(temp.getId());
				userInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
				userInfo.setIs_poor(1);
				userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {
					temp.getMap().put("user_name", userInfo.getUser_name());
					temp.getMap().put("user_id", userInfo.getId());
				}
			}
		}
		request.setAttribute("entityList", list);
		request.setAttribute("init_pwd", Keys.INIT_PWD);
		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui || ui.getOwn_village_id() == null || ui.getIs_village() == 0) {
			String msg = "您还未登录或当前用户不是村用户，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		UserInfo uInfo = new UserInfo();
		uInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
		uInfo.setIs_del(0);
		uInfo.setIs_village(1);
		uInfo.setOwn_village_id(ui.getOwn_village_id());
		uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");

		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		BaseProvince baseProvince = new BaseProvince();
		String addr = "";
		if (StringUtils.isNotBlank(province)) {
			baseProvince.setP_index(Long.valueOf(province));
		}
		if (StringUtils.isNotBlank(city)) {
			baseProvince.setP_index(Long.valueOf(city));
		}
		if (StringUtils.isNotBlank(country)) {
			baseProvince.setP_index(Long.valueOf(country));
		}
		if (StringUtils.isNotBlank(town)) {
			baseProvince.setP_index(Long.valueOf(town));
		}
		if (StringUtils.isNotBlank(village)) {
			baseProvince.setP_index(Long.valueOf(village));
		}
		if (baseProvince.getP_index() != null) {
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			addr = baseProvince.getFull_name();
			entity.setP_index(baseProvince.getP_index());
		}

		String password = Keys.INIT_PWD;
		if (!GenericValidator.isLong(id)) {
			entity.setAddr(addr);
			entity.setAdd_user_id(uInfo.getId());
			entity.setAdd_user_name(uInfo.getUser_name());
			entity.setAdd_date(new Date());
			super.getFacade().getPoorInfoService().createPoorInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {

			if (StringUtils.isNotBlank(is_audit)) {
				PoorInfo poorInfo = new PoorInfo();
				poorInfo.setId(Integer.valueOf(id));
				poorInfo = super.getFacade().getPoorInfoService().getPoorInfo(poorInfo);
				if (entity.getAudit_state() == Keys.audit_state.audit_state_1.getIndex()) {
					if (poorInfo != null && poorInfo.getAudit_state() != Keys.audit_state.audit_state_1.getIndex()) {

						UserInfo user = new UserInfo();
						user.setUser_name("pk" + super.createUserNo());
						DESPlus des = new DESPlus();
						user.setPassword(des.encrypt(password));
						user.setLogin_count(new Integer("0"));
						user.setAdd_user_id(ui.getId());
						user.setAdd_date(new Date());
						user.setIs_active(1);// 已激活
						user.setPoor_id(poorInfo.getId());
						user.setIs_poor(1);
						user.setIs_renzheng(1);
						user.setId_card(poorInfo.getId_card());
						user.setReal_name(poorInfo.getReal_name());
						user.setImg_id_card_fm(poorInfo.getImg_id_card_fm());
						user.setImg_id_card_zm(poorInfo.getImg_id_card_zm());
						if (poorInfo.getQq() != null) {
							user.setAppid_qq(poorInfo.getQq());
						}
						if (poorInfo.getSex() != null) {
							user.setSex(poorInfo.getSex());
						}
						if (poorInfo.getBrithday() != null) {
							user.setBirthday(poorInfo.getBrithday());
						}
						if (poorInfo.getEmail() != null) {
							user.setEmail(poorInfo.getEmail());
						}
						user.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
						user.setYmid(ui.getId().toString());
						entity.getMap().put("creat_poor_user", user);
					}
				}
				entity.setAudit_user_id(ui.getId());
				entity.setAudit_date(new Date());
			}
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());

			if (StringUtils.isBlank(is_audit)) {
				entity.setAddr(addr);
			}
			super.getFacade().getPoorInfoService().modifyPoorInfo(entity);
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

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {

			PoorInfo entity = new PoorInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getPoorInfoService().getPoorInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			if (entity.getP_index() != null) {
				if (entity.getP_index() != null) {
					BaseProvince baseProvince = new BaseProvince();
					baseProvince.setP_index(entity.getP_index());
					baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
					request.setAttribute("full_name", baseProvince.getFull_name());
					request.setAttribute("p_index", entity.getP_index());
				}
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			return new ActionForward("/../manager/customer/PoorManager/form.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
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
			PoorInfo entity = new PoorInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getPoorInfoService().getPoorInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			if (entity.getP_index() != null) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(entity.getP_index());
				baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				request.setAttribute("full_name", baseProvince.getFull_name());
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/customer/PoorManager/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward cancelPoor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		PoorInfo entity = new PoorInfo();
		// super.copyProperties(entity, form);

		if (GenericValidator.isInt(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			entity.setAudit_state(-1);// 审核不通过
			entity.setAudit_date(new Date());
			entity.setAudit_user_id(userInfo.getId());
			entity.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());

			entity.getMap().put("cancel_link_user_info_is_poor", "true");
			entity.getMap().put("cancel_oper_uid", userInfo.getId());
			entity.getMap().put("cancel_oper_uname", userInfo.getUser_name());

			super.getFacade().getPoorInfoService().modifyPoorInfo(entity);

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

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			PoorInfo entity = new PoorInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getPoorInfoService().getPoorInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			if (entity.getP_index() != null) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(entity.getP_index());
				baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				request.setAttribute("full_name", baseProvince.getFull_name());
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/customer/PoorManager/view.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
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
			PoorInfo entity = new PoorInfo();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			getFacade().getPoorInfoService().modifyPoorInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			for (String cur_id : pks) {
				PoorInfo entity = new PoorInfo();
				entity.setIs_del(1);
				entity.setId(new Integer(cur_id));
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());
				getFacade().getPoorInfoService().modifyPoorInfo(entity);
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

	public ActionForward step1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("poor_id");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		getsonSysModuleList(request, dynaBean);

		if (ui.getIs_village() == null || ui.getIs_village() != 1) {
			String msg = "非村站用户，无此权限！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		VillageInfo entity = new VillageInfo();
		entity.getMap().put("is_virtual_no_def", true);
		entity.setId(new Integer(ui.getOwn_village_id()));
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getVillageInfoService().getVillageInfo(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}

		if (entity.getP_index() != null) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(entity.getP_index());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			if (baseProvince != null) {
				request.setAttribute("full_name", baseProvince.getFull_name());
			}
			request.setAttribute("p_index", entity.getP_index());
		}

		if (StringUtils.isNotBlank(id)) {// 修改
			PoorInfo pInfo = new PoorInfo();
			pInfo.setId(Integer.valueOf(id));
			pInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			pInfo = getFacade().getPoorInfoService().getPoorInfo(pInfo);
			if (pInfo != null) {
				super.copyProperties(form, pInfo);
			}
		}
		return new ActionForward("/../manager/customer/PoorManager/step1.jsp");
	}

	public ActionForward step2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		getsonSysModuleList(request, dynaBean);

		PoorInfo entity = new PoorInfo();
		entity.setId(Integer.valueOf(poor_id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getPoorInfoService().getPoorInfo(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		request.setAttribute("poor_info", entity);
		PoorFamily family = new PoorFamily();
		family.setLink_id(entity.getId());
		family.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<PoorFamily> familyList = getFacade().getPoorFamilyService().getPoorFamilyList(family);
		if (familyList != null && familyList.size() > 0) {
			request.setAttribute("familyList", familyList);
		}
		return new ActionForward("/../manager/customer/PoorManager/step2.jsp");
	}

	public ActionForward step3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		getsonSysModuleList(request, dynaBean);

		PoorInfo entity = new PoorInfo();
		entity.setId(Integer.valueOf(poor_id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getPoorInfoService().getPoorInfo(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		request.setAttribute("poor_info", entity);
		PoorCuoShi pCuoShi = new PoorCuoShi();
		pCuoShi.setLink_id(entity.getId());
		pCuoShi.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<PoorCuoShi> cuoshiList = getFacade().getPoorCuoShiService().getPoorCuoShiList(pCuoShi);
		if (cuoshiList != null && cuoshiList.size() > 0) {
			request.setAttribute("cuoshiList", cuoshiList);
		}
		return new ActionForward("/../manager/customer/PoorManager/step3.jsp");
	}

	public ActionForward step4(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		getsonSysModuleList(request, dynaBean);

		PoorInfo entity = new PoorInfo();
		entity.setId(Integer.valueOf(poor_id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getPoorInfoService().getPoorInfo(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		request.setAttribute("poor_info", entity);
		PoorZeRen pZeRen = new PoorZeRen();
		pZeRen.setLink_id(entity.getId());
		pZeRen.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<PoorZeRen> pZeRenList = getFacade().getPoorZeRenService().getPoorZeRenList(pZeRen);
		if (pZeRenList != null && pZeRenList.size() > 0) {
			request.setAttribute("pZeRenList", pZeRenList);
		}
		return new ActionForward("/../manager/customer/PoorManager/step4.jsp");
	}

	public ActionForward saveStep1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String id_card = (String) dynaBean.get("id_card");

		if (StringUtils.isBlank(id_card)) {
			String msg = "参数错误！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		if (ui.getIs_village() == null || ui.getIs_village() != 1) {
			String msg = "非村站用户，无此权限！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		VillageInfo village = new VillageInfo();
		village.setId(new Integer(ui.getOwn_village_id()));
		village.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		village.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		village = getFacade().getVillageInfoService().getVillageInfo(village);
		if (null == village) {
			String msg = "村站不存在！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		String addr = "";
		if (village.getP_index() != null) {
			BaseProvince baseProvince = new BaseProvince();
			baseProvince.setP_index(village.getP_index());
			baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
			addr = baseProvince.getFull_name();
		}

		if (!GenericValidator.isLong(id)) {

			PoorInfo pInfo = new PoorInfo();
			pInfo.setIs_del(0);
			pInfo.setId_card(id_card);
			int count = getFacade().getPoorInfoService().getPoorInfoCount(pInfo);
			if (count > 0) {// 身份证重复
				String msg = "身份证重复！";
				super.showMsgForCustomer(request, response, msg);
				return null;
			}

			entity.setVillage_id(village.getId());
			entity.setAddr(addr);
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_date(new Date());
			entity.setReport_step(1);
			int flag = super.getFacade().getPoorInfoService().createPoorInfo(entity);
			dynaBean.set("poor_id", String.valueOf(flag));
			saveMessage(request, "entity.inerted");
		} else {
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());
			entity.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
			super.getFacade().getPoorInfoService().modifyPoorInfo(entity);
			dynaBean.set("poor_id", entity.getId().toString());
			saveMessage(request, "entity.updated");
		}
		return this.step2(mapping, form, request, response);
	}

	public ActionForward saveStep2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");

		String[] family_name = request.getParameterValues("family_name");
		String[] age = request.getParameterValues("age");
		String[] id_card = request.getParameterValues("id_card");
		String[] relation_ship = request.getParameterValues("relation_ship");
		String[] work_power = request.getParameterValues("work_power");

		PoorInfo poor = new PoorInfo();
		poor.setId(Integer.valueOf(poor_id));
		poor.setIs_del(0);
		poor = getFacade().getPoorInfoService().getPoorInfo(poor);
		if (poor == null) {
			String msg = "贫困户不存在！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		dynaBean.set("poor_id", poor_id.toString());

		if (family_name.length >= 2) {
			PoorFamily entity = new PoorFamily();
			entity.setLink_id(Integer.valueOf(poor_id));
			entity.getMap().put("add_poor_family", "true");
			List<PoorFamily> familyList = new ArrayList<PoorFamily>();
			for (int i = 0; i < family_name.length; i++) {
				if (StringUtils.isNotBlank(family_name[i])) {
					PoorFamily temp = new PoorFamily();
					temp.setFamily_name(family_name[i]);
					temp.setLink_id(Integer.valueOf(poor_id));
					temp.setRelation_ship(relation_ship[i]);
					temp.setId_card(id_card[i]);
					temp.setAge(Integer.valueOf(age[i]));
					temp.setWork_power(work_power[i]);
					temp.setAdd_date(new Date());
					temp.setAdd_user_id(ui.getId());
					temp.setAdd_user_name(ui.getUser_name());
					familyList.add(temp);
				}
			}
			entity.setFamilyList(familyList);
			getFacade().getPoorFamilyService().createPoorFamily(entity);
		}

		// 更新上传步骤
		if (poor.getReport_step() < 2) {
			poor.setReport_step(2);
			poor.setUpdate_date(new Date());
			poor.setUpdate_user_id(ui.getId());
		}
		poor.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		getFacade().getPoorInfoService().modifyPoorInfo(poor);

		return this.step3(mapping, form, request, response);
	}

	public ActionForward saveStep3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");

		String[] dan_wei_name = request.getParameterValues("dan_wei_name");
		String[] content = request.getParameterValues("content");
		String[] help_date = request.getParameterValues("help_date");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		PoorInfo poor = new PoorInfo();
		poor.setId(Integer.valueOf(poor_id));
		poor.setIs_del(0);
		poor = getFacade().getPoorInfoService().getPoorInfo(poor);
		if (poor == null) {
			String msg = "贫困户不存在！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		dynaBean.set("poor_id", poor_id.toString());
		if (dan_wei_name.length >= 2) {
			PoorCuoShi entity = new PoorCuoShi();
			entity.setLink_id(Integer.valueOf(poor_id));
			entity.getMap().put("add_poor_cuo_shi", "true");
			List<PoorCuoShi> entityList = new ArrayList<PoorCuoShi>();
			for (int i = 0; i < dan_wei_name.length; i++) {
				if (StringUtils.isNotBlank(dan_wei_name[i])) {
					PoorCuoShi temp = new PoorCuoShi();
					temp.setDan_wei_name(dan_wei_name[i]);
					temp.setContent(content[i]);
					temp.setHelp_date(sdf.parse(help_date[i]));
					temp.setLink_id(Integer.valueOf(poor_id));
					temp.setAdd_date(new Date());
					temp.setAdd_user_id(ui.getId());
					temp.setAdd_user_name(ui.getUser_name());
					entityList.add(temp);
				}
			}
			entity.setCuoShiList(entityList);
			getFacade().getPoorCuoShiService().createPoorCuoShi(entity);
		}

		// 更新上传步骤
		if (poor.getReport_step() < 3) {
			poor.setReport_step(3);
			poor.setUpdate_date(new Date());
			poor.setUpdate_user_id(ui.getId());
		}
		// poor.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		getFacade().getPoorInfoService().modifyPoorInfo(poor);
		return this.step4(mapping, form, request, response);
	}

	public ActionForward saveStep4(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession(false);
		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");
		String mod_id = (String) dynaBean.get("mod_id");

		String[] name = request.getParameterValues("name");
		String[] dan_wei_name = request.getParameterValues("dan_wei_name");
		String[] mobile = request.getParameterValues("mobile");
		String[] dan_wei_relation = request.getParameterValues("dan_wei_relation");

		PoorInfo poor = new PoorInfo();
		poor.setId(Integer.valueOf(poor_id));
		poor.setIs_del(0);
		poor = getFacade().getPoorInfoService().getPoorInfo(poor);
		if (poor == null) {
			String msg = "贫困户不存在！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		dynaBean.set("poor_id", poor_id.toString());
		if (name.length >= 2) {
			PoorZeRen entity = new PoorZeRen();
			entity.setLink_id(Integer.valueOf(poor_id));
			entity.getMap().put("add_poor_ze_ren", "true");
			List<PoorZeRen> entityList = new ArrayList<PoorZeRen>();
			for (int i = 0; i < name.length; i++) {
				if (StringUtils.isNotBlank(name[i])) {
					PoorZeRen temp = new PoorZeRen();
					temp.setDan_wei_name(dan_wei_name[i]);
					temp.setName(name[i]);
					temp.setDan_wei_relation(dan_wei_relation[i]);
					temp.setMobile(mobile[i]);
					temp.setLink_id(Integer.valueOf(poor_id));
					temp.setAdd_date(new Date());
					temp.setAdd_user_id(ui.getId());
					temp.setAdd_user_name(ui.getUser_name());
					entityList.add(temp);
				}
			}
			entity.setZeRenList(entityList);
			getFacade().getPoorZeRenService().createPoorZeRen(entity);
		}
		// 更新上传步骤
		if (poor.getReport_step() < 4) {
			poor.setReport_step(4);
			poor.setReport_state(1);
			poor.setUpdate_date(new Date());
			poor.setUpdate_user_id(ui.getId());
		}
		// poor.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
		getFacade().getPoorInfoService().modifyPoorInfo(poor);

		saveMessage(request, "entity.inerted");
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		// pathBuffer.append("&");
		// pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward validateMobileForPoor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String mobile = (String) dynaBean.get("mobile");
		String ret = "0";

		JSONObject data = new JSONObject();

		if (StringUtils.isNotBlank(mobile)) {
			UserInfo entity = new UserInfo();
			entity.setMobile(mobile);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getUserInfoService().getUserInfoCount(entity);
			if (recordCount.intValue() <= 0) { // 手机号可用
				ret = "1";
			} else if (recordCount.intValue() > 0) { // 手机号重复
				List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(entity);
				if (userInfoList.get(0).getUser_type() == Keys.UserType.USER_TYPE_2.getIndex()) {
					if (userInfoList.get(0).getIs_poor() == 0) {
						// 手机号存在，用户类型可以成为贫困户
						if (userInfoList.get(0).getIs_renzheng() == 1) {// 已实名认证
							ret = "2";
							data.put("id_card", userInfoList.get(0).getId_card());

							BaseImgs img = new BaseImgs();
							img.getMap().put("link_id", userInfoList.get(0).getId());
							img.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
							List<BaseImgs> imgs = getFacade().getBaseImgsService().getBaseImgsList(img);
							if (imgs != null || imgs.size() >= 2) {
								data.put("id_img_zm", imgs.get(0).getFile_path());
								data.put("id_img_fm", imgs.get(1).getFile_path());
							}

						} else {// 未实名认证
							ret = "3";
						}
					} else {
						ret = "5";
					}
				} else {// 手机号存在，用户类型不可以成为贫困户
					ret = "4";
				}
			}
		}

		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward validateIdCardForPoor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id_card = (String) dynaBean.get("id_card");
		String ret = "0";

		JSONObject data = new JSONObject();

		if (StringUtils.isNotBlank(id_card)) {
			PoorInfo entity = new PoorInfo();
			entity.setId_card(id_card);
			entity.setIs_del(0);
			Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(entity);
			if (recordCount.intValue() <= 0) { // 身份证可用
				ret = "1";
			} else if (recordCount.intValue() > 0) { // 身份证重复
				ret = "2";
			}
		}

		data.put("ret", ret);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward importExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		return new ActionForward("/../manager/customer/PoorManager/import.jsp");
	}

	public ActionForward saveExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		logger.info("============saveExcel====");
		String uploadDir = StringUtils.join(new String[] { "files", "import", "" }, File.separator);
		List<UploadFile> uploadFileList = super.uploadFile(form, uploadDir, false, false, null);
		// List<UploadFile> uploadFileList = super.uploadFile(form, uploadDir, false);
		String fileSavePath = "";
		for (UploadFile uploadFile : uploadFileList) {
			// logger.info("==========uploadFile.getFormName()====" + uploadFile.getFormName());
			if ("file_show".equals(uploadFile.getFormName())) {
				fileSavePath = uploadFile.getFileSavePath();
			}
		}

		String ctxDir = getServlet().getServletContext().getRealPath(String.valueOf(File.separatorChar));
		if (!ctxDir.endsWith(String.valueOf(File.separatorChar))) {
			ctxDir = ctxDir + File.separatorChar;
		}
		// fileSavePath = fileSavePath.replaceAll("/", "\\\\");
		logger.info("#########################################:" + ctxDir + fileSavePath);

		if (StringUtils.isNotBlank(fileSavePath)) {
			try {
				String[] rowLines = ExcelUtils.readExcelRowLines(ctxDir + fileSavePath, 0, 1);
				logger.info("rowLines长度" + rowLines.length);
				if (null != rowLines) {
					// for (int i = 1; i < rowLines.length; i++)
					List<PoorInfo> poorList = new ArrayList<PoorInfo>();
					List<UserInfo> errPoorInfo = new ArrayList<UserInfo>();

					int win_count = 0;
					for (int i = 3; i < rowLines.length; i++) {
						logger.info("===当前循环i:" + i);
						if (StringUtils.isNotBlank(rowLines[i])) {
							logger.info("当前temp:" + rowLines[i]);
							String values[] = StringUtils.split(rowLines[i], ExcelUtils.EXCEL_LINE_DELIMITER);
							logger.info("===values[0]:" + values[0]);
							logger.info("===values.length:" + values.length);
							if (values.length > 0) {
								if (StringUtils.isBlank(values[0])) {
									break;
								}
								String p_name_like = "";
								// 1：县(市、区、旗)
								p_name_like = values[1];
								// 2：乡(镇)
								p_name_like = p_name_like + "," + values[2];
								// 3:行政村
								p_name_like = p_name_like + "," + values[3];

								BaseProvince bProvince = new BaseProvince();
								bProvince.getMap().put("full_name_like", p_name_like);
								bProvince.setP_level(5);
								bProvince = getFacade().getBaseProvinceService().getBaseProvince(bProvince);
								if (bProvince != null) {
									List<PoorFamily> familyList = new ArrayList<PoorFamily>();
									PoorInfo poor = new PoorInfo();
									// 1：县(市、区、旗) 2：乡(镇) 3:行政村 4.自然村 5.户编号 6.人编号 7.姓名 8.证件号码 9.人数 10.与户主关系 11.民族
									// 12.文化程度 13.在校生状况 14.健康状况 15.劳动技能 16.务工状况 17.务工时间（月） 18.参加大病医疗 19.脱贫属性
									// 20.贫困户属性 21.主要致贫原因 22.危房户 23.饮水安全 24.饮水困难 25.人均纯收入 26.联系电话
									VillageInfo villageInfo = new VillageInfo();
									villageInfo.setP_index(bProvince.getP_index());
									villageInfo.setIs_del(0);
									villageInfo.setAudit_state(1);
									villageInfo = getFacade().getVillageInfoService().getVillageInfo(villageInfo);

									if (villageInfo != null) {
										if (villageInfo.getId().equals(ui.getOwn_village_id())) {
											poor.setVillage_id(ui.getOwn_village_id());
										} else {
											UserInfo erroUser = new UserInfo();
											erroUser.setId(Integer.valueOf(values[0]));// Execl序号
											erroUser.setUser_name(values[7]);
											erroUser.setMobile(values[26]);
											erroUser.setAutograph("所属村站不匹配");// 导入错误原因
											errPoorInfo.add(erroUser);
											continue;
										}
									} else {
										UserInfo erroUser = new UserInfo();
										erroUser.setId(Integer.valueOf(values[0]));// Execl序号
										erroUser.setUser_name(values[7]);
										erroUser.setMobile(values[26]);
										erroUser.setAutograph("所属村站不存在");// 导入错误原因
										errPoorInfo.add(erroUser);
										continue;
									}

									if (values[10].equals("户主")) {

										poor.setP_index(bProvince.getP_index());
										poor.setAddr(bProvince.getFull_name());
										// 7.姓名
										poor.setReal_name(values[7]);
										// 8.证件号码
										poor.setId_card(values[8]);
										// 9.人数
										poor.setFamily_num(Integer.valueOf(values[9]));
										// 11.民族
										poor.setNation(values[11]);
										// 12.文化程度
										poor.setEducation(values[12]);
										// 21.主要致贫原因
										poor.setPoor_reason(values[21]);
										// 26.联系电话
										poor.setMobile(values[26]);// 是否需要判断手机号码已存在
										poor.setIs_del(0);
										poor.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
										poor.setAdd_date(new Date());
										poor.setAdd_user_id(ui.getId());
										poor.setAdd_user_name(ui.getUser_name());

										poor.setReport_step(4);
										poor.setRemark("后台导入");
										poor.setPoorFamilyList(familyList);
										poorList.add(poor);

									} else {

										PoorFamily poorFamily = new PoorFamily();
										// 7.姓名
										poorFamily.setFamily_name(values[7]);
										// 8.证件号码
										poorFamily.setId_card(values[8]);
										// 10.与户主关系
										poorFamily.setRelation_ship(values[10]);
										// 15.劳动技能
										poorFamily.setWork_power(values[15]);

										poorFamily.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
										poorFamily.setAdd_date(new Date());
										poorFamily.setAdd_user_id(ui.getId());
										poorFamily.setAdd_user_name(ui.getUser_name());
										poorList.get(poorList.size() - 1).getPoorFamilyList().add(poorFamily);// poorFamily加入最后一个
									}
								} else {
									UserInfo erroUser = new UserInfo();
									erroUser.setId(Integer.valueOf(values[0]));// Execl序号
									erroUser.setUser_name(values[7]);
									erroUser.setMobile(values[26]);
									erroUser.setAutograph("省市县不存在");// 导入错误原因
									errPoorInfo.add(erroUser);
									continue;
								}
							}
						}
					}
					PoorInfo insetPoor = new PoorInfo();
					logger.info("====poorList.size:" + poorList.size());
					insetPoor.setPoorInfoList(poorList);
					win_count = getFacade().getPoorInfoService().createImportPoorInfo(insetPoor);
					request.setAttribute("win_count", win_count);

					if (errPoorInfo != null && errPoorInfo.size() > 0) {
						request.setAttribute("errPoorInfo", errPoorInfo);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// return this.list(mapping, form, request, response);
		return new ActionForward("/../manager/customer/PoorManager/import.jsp");
	}

	public ActionForward listAidMoney(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		PoorInfo entity = new PoorInfo();
		if (null != ui.getOwn_entp_id()) {
			entity.getMap().put("own_entp_id", ui.getOwn_entp_id());
		} else {
			entity.getMap().put("own_entp_id", "-9999");
		}

		List<PoorInfo> list = getFacade().getPoorInfoService().getPoorInfoListWithAidMoney(entity);
		request.setAttribute("entityList", list);
		return new ActionForward("/../manager/customer/PoorManager/list_aid_money.jsp");
	}

	public ActionForward downloadPoorInfoQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String real_name_like = (String) dynaBean.get("real_name_like");
		String id_card_like = (String) dynaBean.get("id_card_like");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		int village_id = super.getUserInfo(ui.getId()).getOwn_village_id();
		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		entity.getMap().put("real_name_like", real_name_like);
		if (StringUtils.isNotBlank(id_card_like)) {
			entity.getMap().put("id_card_like", id_card_like.toUpperCase().trim());
		}
		entity.setVillage_id(village_id);
		entity.setAudit_state(1);
		entity.setIs_del(0);
		List<PoorInfo> entityList = super.getFacade().getPoorInfoService().getPoorInfoList(entity);
		String realPath = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String fname = EncryptUtilsV2.encodingFileName("贫困户二维码导出_日期" + sdFormat_ymd.format(new Date()) + ".zip");
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);

		if (null != entityList && entityList.size() > 0) {
			ZipOutputStream zipout = new ZipOutputStream(response.getOutputStream());
			File[] files = new File[entityList.size()];
			int i = 0;
			for (PoorInfo temp : entityList) {
				if (null != temp.getPoor_qrcode()) {
					File savePath = new File(realPath + temp.getPoor_qrcode());
					if (savePath.exists())
						files[i] = savePath;
				}
				i++;
			}
			ZipUtils.zipFile(files, "", zipout);
			zipout.flush();
			zipout.close();
			return null;
		} else {
			return null;
		}
	}
}
