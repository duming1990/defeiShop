package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CommInfoPoors;
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

public class PoorManagerAction extends BaseAdminAction {
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
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
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
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");
		String id_card_like = (String) dynaBean.get("id_card_like");
		String is_band_bank = (String) dynaBean.get("is_band_bank");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(id_card_like)) {
			entity.getMap().put("id_card_like", id_card_like.toUpperCase().trim());
		}
		if (StringUtils.isNotBlank(village)) {
			entity.getMap().put("p_index_like", village);
		} else if (StringUtils.isNotBlank(town)) {
			entity.getMap().put("p_index_like", town.substring(0, 9));
		} else if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country.substring(0, 6));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.setIs_del(0);
		// entity.setReport_step(4);

		entity.getMap().put("real_name_like", real_name_like);
		if (StringUtils.isNotBlank(is_band_bank)) {
			if (is_band_bank.equals("0")) {
				entity.getMap().put("is_band_bank_is_null", "true");
			} else {
				entity.getMap().put("is_band_bank_not_null", "true");
			}
		}

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);

		Integer recordCount = getFacade().getPoorInfoService().getVillageManagerPoorInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<PoorInfo> list = getFacade().getPoorInfoService().getVillageManagerPoorInfoPaginatedList(entity);
		if (list != null && list.size() > 0) {
			for (PoorInfo temp : list) {
				if (null != temp.getVillage_id()) {
					VillageInfo villageInfo = new VillageInfo();
					villageInfo.setId(temp.getVillage_id());
					villageInfo = super.getFacade().getVillageInfoService().getVillageInfo(villageInfo);
					temp.getMap().put("villageInfo", villageInfo);
				}
				UserInfo user = new UserInfo();
				user.setIs_poor(1);
				user.setPoor_id(temp.getId());
				user.setIs_del(0);
				user = getFacade().getUserInfoService().getUserInfo(user);
				temp.getMap().put("userInfo", user);
			}
		}
		request.setAttribute("entityList", list);
		request.setAttribute("init_pwd", Keys.INIT_PWD);
		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String village = (String) dynaBean.get("village");
		if (StringUtils.isBlank(village) || !GenericValidator.isLong(village)) {
			String msg = "所属村站必须选择！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='PoorManager.do'}");
			return null;
		}

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);
		entity.setP_index(Long.valueOf(village));

		String password = Keys.INIT_PWD;

		if (StringUtils.isNotBlank(id)) {
			VillageInfo vInfo = new VillageInfo();
			vInfo.setP_index(entity.getP_index());
			vInfo.setIs_del(0);
			vInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
			vInfo = getFacade().getVillageInfoService().getVillageInfo(vInfo);
			if (null != vInfo) {
				entity.setVillage_id(vInfo.getId());
			}

			UserInfo uInfo = new UserInfo();
			uInfo.setIs_poor(1);
			uInfo.setPoor_id(Integer.valueOf(id));
			uInfo.setIs_del(0);
			uInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
			uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);
			if (null != uInfo) {
				entity.getMap().put("update_poor_user", uInfo);
			}

			PoorInfo poorInfo = new PoorInfo();
			poorInfo.setId(Integer.valueOf(id));
			poorInfo = super.getFacade().getPoorInfoService().getPoorInfo(poorInfo);
			entity.getMap().put("poorInfo", poorInfo);

			if (entity.getAudit_state() == Keys.audit_state.audit_state_1.getIndex()) {
				if (poorInfo != null && poorInfo.getAudit_state() != Keys.audit_state.audit_state_1.getIndex()) {
					UserInfo userInfo = new UserInfo();
					userInfo.setIs_poor(1);
					userInfo.setPoor_id(poorInfo.getId());
					userInfo.setIs_del(0);
					userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
					if (null == userInfo) {
						// 生成新用户
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
						user.setP_index(Integer.valueOf(poorInfo.getP_index().toString().substring(0, 6)));
						user.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
						user.setUser_level(Keys.USER_LEVEL_FX);
						user.setReal_name(poorInfo.getReal_name());
						user.setImg_id_card_fm(poorInfo.getImg_id_card_fm());
						user.setImg_id_card_zm(poorInfo.getImg_id_card_zm());
						if (poorInfo.getSex() != null) {
							user.setSex(poorInfo.getSex());
						}
						if (poorInfo.getBrithday() != null) {
							user.setBirthday(poorInfo.getBrithday());
						}
						user.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
						UserInfo poor = super.getUserInfo(poorInfo.getAdd_user_id());
						if (poor == null || poor.getUser_name() == null) {
							UserInfo admin = new UserInfo();
							admin.setUser_type(Keys.UserType.USER_TYPE_1.getIndex());
							List<UserInfo> users = getFacade().getUserInfoService().getUserInfoList(admin);
							if (users != null && users.size() > 0) {
								user.setYmid(users.get(0).getUser_name());
							}
						} else {
							user.setYmid(poor.getUser_name());
						}
						entity.getMap().put("creat_poor_user", user);
						entity.getMap().put("add_user_name", ui.getUser_name());
					}
				}
			}
			entity.setAudit_user_id(ui.getId());
			entity.setAudit_date(new Date());
		}
		super.getFacade().getPoorInfoService().modifyPoorInfo(entity);
		if (StringUtils.isNotBlank(id)) {
			HttpSession session = request.getSession(false);
			String Path = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));// 文件绝对路径
			String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
			if (!ctxDir.endsWith(File.separator)) {
				ctxDir = ctxDir + File.separator;
			}
			String ctx = super.getCtxPath(request, false);

			PoorInfo poorInfoQuery = new PoorInfo();
			poorInfoQuery.setId(Integer.valueOf(id));
			poorInfoQuery = super.getFacade().getPoorInfoService().getPoorInfo(poorInfoQuery);
			String LogoFile = Path + "/styles/imagesPublic/user_header.png";
			if (null != poorInfoQuery && StringUtils.isNotBlank(poorInfoQuery.getHead_logo())) {
				File tempFile = new File(Path + poorInfoQuery.getHead_logo());
				if (tempFile.exists()) {
					LogoFile = Path + poorInfoQuery.getHead_logo();
				}
			}
			String Jump_path = ctx + "/m/MUserCenter.do?method=index&user_id=" + poorInfoQuery.getUser_id();// 二维码跳转的路径
			String name = poorInfoQuery.getReal_name();// 底部添加的文字
			String uploadDir = "files" + File.separator + "poorInfo";// 文件夹的名称
			if (!ctxDir.endsWith(File.separator)) {
				ctxDir = ctxDir + File.separator;
			}
			File savePath = new File(ctxDir + uploadDir);
			if (!savePath.exists()) {
				savePath.mkdirs();
			}
			String imgPath = ctxDir + uploadDir + File.separator + id + ".png";
			File imgFile = new File(imgPath);
			if (!imgFile.exists()) {
				super.createQrcode(Path, Jump_path, LogoFile, name, uploadDir, Integer.valueOf(id));
				PoorInfo poorInfoUpdate = new PoorInfo();
				poorInfoUpdate.setId(Integer.valueOf(id));
				poorInfoUpdate.setPoor_qrcode(uploadDir + File.separator + id + ".png");// 数据库储存路径
				super.getFacade().getPoorInfoService().modifyPoorInfo(poorInfoUpdate);
			}
		}
		saveMessage(request, "entity.audit");

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

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String real_name_like = (String) dynaBean.get("real_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");
		String code = (String) dynaBean.get("code");
		String id_card_like = (String) dynaBean.get("id_card_like");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(village)) {
			entity.getMap().put("p_index_like", village);
		} else if (StringUtils.isNotBlank(town)) {
			entity.getMap().put("p_index_like", town.substring(0, 9));
		} else if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country.substring(0, 6));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.setIs_del(0);
		// entity.setReport_step(4);

		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		if (StringUtils.isNotBlank(id_card_like)) {
			entity.getMap().put("id_card_like", id_card_like.toUpperCase().trim());
		}

		List<PoorInfo> entityList = getFacade().getPoorInfoService().getPoorInfoList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (PoorInfo temp : entityList) {
				UserInfo uInfo = new UserInfo();
				uInfo.setIs_poor(1);
				uInfo.setIs_del(0);
				uInfo.setPoor_id(temp.getId());
				uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);
				temp.getMap().put("user", uInfo);
				if (null != temp.getVillage_id()) {
					VillageInfo villageInfo = new VillageInfo();
					villageInfo.setId(temp.getVillage_id());
					villageInfo = super.getFacade().getVillageInfoService().getVillageInfo(villageInfo);
					temp.getMap().put("villageInfo", villageInfo);
				}
			}
		}

		model.put("entityList", entityList);
		String content = getFacade().getTemplateService().getContent("PoorManager/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("贫困户信息.xls");
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
			return new ActionForward("/../manager/admin/PoorManager/audit.jsp");
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
			// entity.setIs_del(Keys.IsDel.IS_DEL_1.getIndex());
			entity.getMap().put("cancel_link_user_info_is_poor", "true");
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
			return mapping.findForward("view");
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

		if (StringUtils.isBlank(id)) {
			String msg = "参数错误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		getsonSysModuleList(request, dynaBean);

		PoorInfo pInfo = new PoorInfo();
		pInfo.setId(Integer.valueOf(id));
		pInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		pInfo = getFacade().getPoorInfoService().getPoorInfo(pInfo);
		if (pInfo != null) {
			if (pInfo.getP_index() != null) {
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(pInfo.getP_index());
				baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
				request.setAttribute("full_name", baseProvince.getFull_name());

				dynaBean.set("province", pInfo.getP_index().toString().substring(0, 2) + "0000");
				dynaBean.set("city", pInfo.getP_index().toString().substring(0, 4) + "00");
				dynaBean.set("country", pInfo.getP_index().toString().substring(0, 6));
				dynaBean.set("town", pInfo.getP_index().toString().substring(0, 9) + "000");
				dynaBean.set("village", pInfo.getP_index().toString());
			}

			// the line below is added for pagination
			pInfo.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, pInfo);
		}
		return new ActionForward("/../manager/admin/PoorManager/step1.jsp");
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
		return new ActionForward("/../manager/admin/PoorManager/step2.jsp");
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
		return new ActionForward("/../manager/admin/PoorManager/step3.jsp");
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

		CommInfoPoors cPoors = new CommInfoPoors();
		cPoors.setPoor_id(entity.getId());
		List<CommInfoPoors> cPoorsList = getFacade().getCommInfoPoorsService().getCommInfoPoorsList(cPoors);
		if (cPoorsList != null && cPoorsList.size() > 0) {
			request.setAttribute("cPoorsList", cPoorsList);
		}

		return new ActionForward("/../manager/admin/PoorManager/step4.jsp");
	}

	public ActionForward importExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		return new ActionForward("/../manager/admin/PoorManager/import.jsp");
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
									PoorInfo poor = new PoorInfo();

									VillageInfo villageInfo = new VillageInfo();
									villageInfo.setP_index(bProvince.getP_index());
									villageInfo.setIs_del(0);
									villageInfo.setAudit_state(1);
									villageInfo = getFacade().getVillageInfoService().getVillageInfo(villageInfo);

									if (villageInfo != null) {
										poor.setVillage_id(villageInfo.getId());
									} else {
										UserInfo erroUser = new UserInfo();
										erroUser.setId(Integer.valueOf(values[0]));// Execl序号
										erroUser.setUser_name(values[7]);
										erroUser.setMobile(values[26]);
										erroUser.setAutograph("所属村站不存在");// 导入错误原因
										errPoorInfo.add(erroUser);
										continue;
									}

									List<PoorFamily> familyList = new ArrayList<PoorFamily>();
									// 1：县(市、区、旗) 2：乡(镇) 3:行政村 4.自然村 5.户编号 6.人编号 7.姓名 8.证件号码 9.人数 10.与户主关系 11.民族
									// 12.文化程度 13.在校生状况 14.健康状况 15.劳动技能 16.务工状况 17.务工时间（月） 18.参加大病医疗 19.脱贫属性
									// 20.贫困户属性 21.主要致贫原因 22.危房户 23.饮水安全 24.饮水困难 25.人均纯收入 26.联系电话
									if (values[10].equals("户主")) {

										poor.setP_index(bProvince.getP_index());
										poor.setAddr(bProvince.getFull_name());
										// 7.姓名
										poor.setReal_name(values[7]);
										// 8.证件号码
										poor.setId_card(values[8]);

										PoorInfo entity = new PoorInfo();
										entity.setId_card(values[8]);
										entity.setIs_del(0);
										Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(entity);
										if (recordCount.intValue() > 0) { // 身份证重复
											UserInfo erroUser = new UserInfo();
											erroUser.setId(Integer.valueOf(values[0]));// Execl序号
											erroUser.setUser_name(values[7]);
											erroUser.setMobile(values[26]);
											erroUser.setAutograph("该身份证已被使用，请删除后重新导入");// 导入错误原因
											errPoorInfo.add(erroUser);
											break;
										}
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

										UserInfo uInfo = new UserInfo();
										uInfo.setOwn_village_id(villageInfo.getId());
										uInfo.setIs_village(1);
										uInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
										uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);

										poor.setAdd_user_id(uInfo.getId());
										poor.setAdd_user_name(uInfo.getUser_name());
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
		return new ActionForward("/../manager/admin/PoorManager/import.jsp");
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
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
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
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
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
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
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
		getFacade().getPoorInfoService().modifyPoorInfo(poor);

		saveMessage(request, "entity.updated");
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

	public ActionForward downloadPoorInfoQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String real_name_like = (String) dynaBean.get("real_name_like");
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");
		String code = (String) dynaBean.get("code");
		String id_card_like = (String) dynaBean.get("id_card_like");

		PoorInfo entity = new PoorInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(village)) {
			entity.getMap().put("p_index_like", village);
		} else if (StringUtils.isNotBlank(town)) {
			entity.getMap().put("p_index_like", town.substring(0, 9));
		} else if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country.substring(0, 6));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		entity.setIs_del(0);

		entity.getMap().put("real_name_like", real_name_like);
		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		if (StringUtils.isNotBlank(id_card_like)) {
			entity.getMap().put("id_card_like", id_card_like.toUpperCase().trim());
		}

		HttpSession session = request.getSession(false);
		super.copyProperties(entity, form);

		entity.setAudit_state(1);
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

	// 批量脱贫
	public ActionForward cancelPoors(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		PoorInfo entity = new PoorInfo();
		UserInfo ui = super.getUserInfoFromSession(request);
		if (!StringUtils.isBlank(id) && GenericValidator.isInt(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setIs_tuo_pin(1);
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());
			super.getFacade().getPoorInfoService().modifyPoorInfo(entity);
			saveMessage(request, "entity.updated");
		} else if (!ArrayUtils.isEmpty(pks)) {
			for (String cur_id : pks) {
				entity.setId(Integer.valueOf(cur_id));
				entity.setIs_tuo_pin(1);
				super.getFacade().getPoorInfoService().modifyPoorInfo(entity);
				saveMessage(request, "entity.updated");
			}
		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);

		return forward;
	}

	public ActionForward getPoorInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String type = (String) dynaBean.get("type");

		if (GenericValidator.isLong(id)) {
			PoorInfo poorInfo = new PoorInfo();
			poorInfo.setId(new Integer(id));
			poorInfo = getFacade().getPoorInfoService().getPoorInfo(poorInfo);
			if (null == poorInfo) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			request.setAttribute("poorInfo", poorInfo);

			UserInfo user = new UserInfo();
			user.setIs_poor(1);
			user.setPoor_id(poorInfo.getId());
			user.setIs_del(0);
			user = getFacade().getUserInfoService().getUserInfo(user);

			super.copyProperties(form, user);
			if (StringUtils.isNotBlank(type) && type.equals("mobile")) {
				return new ActionForward("/../manager/admin/PoorManager/edit_mobile.jsp");
			}
			return new ActionForward("/../manager/admin/PoorManager/edit_bank_card.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward saveMobile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "-1", msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mobile = (String) dynaBean.get("mobile");

		if (!GenericValidator.isInt(id)) {
			msg = "用户参数不正确！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		UserInfo user = getUserInfo(Integer.valueOf(id));
		if (null == user) {
			msg = "用户不存在！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		UserInfo entity = new UserInfo();
		// entity.setMobile(mobile);
		entity.getMap().put("ym_id", mobile);
		entity.setIs_del(0);
		List<UserInfo> entityList = getFacade().getUserInfoService().getUserInfoList(entity);
		if (entityList.size() > 1) {
			if (entityList.get(0).getId().intValue() != user.getId().intValue()) {
				msg = "该手机号码已被使用！";
				return super.returnAjaxData(response, ret, msg, data);
			}
		}

		UserInfo update = new UserInfo();
		update.setId(user.getId());
		update.setMobile(mobile);
		int i = getFacade().getUserInfoService().modifyUserInfo(update);
		if (i == 0) {
			msg = "绑定用户手机号码失败！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		PoorInfo poor = new PoorInfo();
		poor.setId(user.getPoor_id());
		poor.setMobile(mobile);
		i = getFacade().getPoorInfoService().modifyPoorInfo(poor);
		if (i == 0) {
			msg = "绑定贫困户手机号码失败！";
			return super.returnAjaxData(response, ret, msg, data);
		}
		if (i > 0) {
			ret = "1";
			msg = "绑定成功";
		}

		return super.returnAjaxData(response, ret, msg, data);
	}

	public ActionForward saveBankCard(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String ret = "-1", msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mobile = (String) dynaBean.get("mobile");

		if (!GenericValidator.isInt(id)) {
			msg = "用户参数不正确！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		UserInfo user = getUserInfo(Integer.valueOf(id));
		if (null == user) {
			msg = "用户不存在！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		super.copyProperties(user, form);

		int i = getFacade().getUserInfoService().modifyUserInfo(user);
		if (i == 0) {
			msg = "绑定银行卡失败！";
			return super.returnAjaxData(response, ret, msg, data);
		}

		if (i > 0) {
			ret = "1";
			msg = "绑定成功";
		}

		return super.returnAjaxData(response, ret, msg, data);
	}
}
