package com.ebiz.webapp.web.struts.manager.customer;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class CityAction extends BaseCustomerAction {
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
		String village_name_like = (String) dynaBean.get("village_name_like");

		VillageInfo entity = new VillageInfo();
		super.copyProperties(entity, form);

		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(0);
		entity.getMap().put("village_name_like", village_name_like);

		Integer recordCount = getFacade().getVillageInfoService().getVillageInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<VillageInfo> list = getFacade().getVillageInfoService().getVillageInfoPaginatedList(entity);
		if (list != null && list.size() > 0) {
			for (VillageInfo temp : list) {
				UserInfo userInfo = new UserInfo();
				userInfo.setOwn_village_id(temp.getId());
				userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				// 村用户
				userInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
				UserInfo village_user = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != village_user) {
					temp.getMap().put("village_no", village_user.getUser_name());
					temp.getMap().put("user_id", village_user.getId());
				}
				// 个人用户
				userInfo.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
				userInfo.setIs_village(1);
				UserInfo village_person_user = getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != village_person_user) {
					temp.getMap().put("village_person_no", village_person_user.getUser_name());
					temp.getMap().put("village_person_user_id", village_person_user.getId());
				}
				// 所属地区
				if (temp.getP_index() != null) {
					BaseProvince baseProvince = new BaseProvince();
					baseProvince.setP_index(temp.getP_index());
					baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
					temp.getMap().put("full_name", baseProvince.getFull_name());
				}
			}
		}
		request.setAttribute("entityList", list);

		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		if (ui.getIs_fuwu() == 1) {
			int limit_count = 0;
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setAdd_user_id(ui.getId());
			entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (entity != null) {
				limit_count = entity.getVillage_count_limit();
			}

			VillageInfo villageInfo = new VillageInfo();
			villageInfo.setIs_del(0);
			villageInfo.setAdd_user_id(ui.getId());
			villageInfo.setAudit_state(1);
			Integer village_count = getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
			if (limit_count > 0 && village_count >= limit_count) {// 添加的村站数量大于限制数量
				String msg = "添加村站的数量已满！";
				super.showMsgForCustomer(request, response, msg);
				return null;
			}

			if (entity.getP_index() != null) {
				super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index().longValue());
			}
		}

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String person_user_id = (String) dynaBean.get("person_user_id");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String town = (String) dynaBean.get("town");
		String village = (String) dynaBean.get("village");

		VillageInfo entity = new VillageInfo();
		super.copyProperties(entity, form);

		BaseProvince baseProvince = new BaseProvince();

		String village_address = "";
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
			village_address = StringUtils.replace(baseProvince.getFull_name(), ",", "");
		}

		String password = Keys.INIT_PWD;

		if (!GenericValidator.isLong(id)) {
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_date(new Date());
			entity.setVillage_address(village_address);
			entity.setP_index(baseProvince.getP_index());
			entity.getMap().put("add_circle_of_village", true);
			super.getFacade().getVillageInfoService().createVillageInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {

			if (StringUtils.isNotBlank(is_audit)) {
				VillageInfo villageInfo = new VillageInfo();
				villageInfo.setId(Integer.valueOf(id));
				villageInfo = super.getFacade().getVillageInfoService().getVillageInfo(villageInfo);
				if (villageInfo != null && entity.getAudit_state() == Keys.audit_state.audit_state_1.getIndex()) {
					// 如果原先已经生成过村站，则重新激活村用户否则重新生成
					UserInfo uInfo = new UserInfo();
					uInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
					uInfo.setOwn_village_id(Integer.valueOf(id));
					uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);
					if (uInfo != null) {// 已存在，直接激活村用户；个人用户直接选择或申请
						if (StringUtils.isNotBlank(person_user_id)) {
							uInfo.setIs_village(1);
							uInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
							uInfo.setIs_village(1);
							uInfo.setIs_active(1);// 已激活
							uInfo.setUpdate_date(new Date());
							uInfo.setUpdate_user_id(ui.getId());
							entity.getMap().put("activate_village_user", uInfo);

							UserInfo user = super.getUserInfo(Integer.valueOf(person_user_id));
							if (user != null) {
								user.setOwn_village_id(villageInfo.getId());
								user.setOwn_village_name(villageInfo.getVillage_name());
								user.setIs_village(1);
								user.setUpdate_date(new Date());
								user.setUpdate_user_id(ui.getId());
								entity.getMap().put("active_village_person_user", user);

								// 更新所选用户信息到village_info
								entity.setOwner_name(user.getUser_name());
								entity.setId_card(user.getId_card());
								entity.setImg_id_card_fm(user.getImg_id_card_fm());
								entity.setImg_id_card_zm(user.getImg_id_card_zm());
								if (user.getAppid_qq() != null) {
									entity.setAppid_qq(user.getAppid_qq());
								}
								if (user.getSex() != null) {
									entity.setSex(user.getSex());
								}
								if (user.getBirthday() != null) {
									entity.setBirthday(user.getBirthday());
								}
								if (user.getEmail() != null) {
									entity.setEmail(user.getEmail());
								}
							}
						}
					} else {// 重新生成
						// 创建村用户
						UserInfo userInfo = new UserInfo();
						userInfo.setUser_name("yz" + super.createUserNo());
						DESPlus des = new DESPlus();
						userInfo.setPassword(des.encrypt(password));
						userInfo.setLogin_count(new Integer("0"));
						userInfo.setAdd_user_id(ui.getId());
						userInfo.setAdd_date(new Date());
						userInfo.setIs_active(1);// 已激活
						userInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
						userInfo.setOwn_village_id(Integer.valueOf(id));
						userInfo.setOwn_village_name(villageInfo.getVillage_name());
						userInfo.setReal_name(villageInfo.getVillage_name());
						userInfo.setIs_village(1);
						userInfo.setYmid(ui.getUser_name());
						entity.getMap().put("creat_village_user", userInfo);
						// 创建个人用户
						if (StringUtils.isBlank(person_user_id)) {// 如果没有个人用户id，说明不是普通会员申请的，直接生成
							UserInfo user = new UserInfo();
							String personUserNo = super.createUserNo();
							user.setUser_name("P" + personUserNo);
							user.setPassword(des.encrypt(password));

							user.setReal_name("P" + personUserNo);
							user.setSex(villageInfo.getSex());
							user.setBirthday(villageInfo.getBirthday());
							user.setEmail(villageInfo.getEmail());
							user.setId_card(villageInfo.getId_card());
							user.setImg_id_card_fm(villageInfo.getImg_id_card_fm());
							user.setImg_id_card_zm(villageInfo.getImg_id_card_zm());
							user.setAppid_qq(villageInfo.getAppid_qq());
							user.setLogin_count(new Integer("0"));
							user.setAdd_user_id(ui.getId());
							user.setAdd_date(new Date());
							user.setOwn_village_id(villageInfo.getId());
							user.setOwn_village_name(villageInfo.getVillage_name());
							user.setIs_active(1);// 已激活
							user.setIs_renzheng(1);
							user.setIs_village(1);
							user.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
							user.setYmid(userInfo.getUser_name());
							entity.getMap().put("creat_village_person_user", user);

						} else {
							UserInfo user = super.getUserInfo(Integer.valueOf(person_user_id));
							if (user != null) {
								user.setOwn_village_id(villageInfo.getId());
								user.setOwn_village_name(villageInfo.getVillage_name());
								user.setIs_village(1);
								user.setUpdate_date(new Date());
								user.setUpdate_user_id(ui.getId());
								entity.getMap().put("active_village_person_user", user);
								// 更新所选用户信息到village_info
								entity.setOwner_name(user.getUser_name());
								entity.setId_card(user.getId_card());
								entity.setImg_id_card_fm(user.getImg_id_card_fm());
								entity.setImg_id_card_zm(user.getImg_id_card_zm());
								if (user.getAppid_qq() != null) {
									entity.setAppid_qq(user.getAppid_qq());
								}
								if (user.getSex() != null) {
									entity.setSex(user.getSex());
								}
								if (user.getBirthday() != null) {
									entity.setBirthday(user.getBirthday());
								}
								if (user.getEmail() != null) {
									entity.setEmail(user.getEmail());
								}

							}
						}
					}
				}
				entity.setAudit_user_id(ui.getId());
				entity.setAudit_date(new Date());
			}
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());

			super.getFacade().getVillageInfoService().modifyVillageInfo(entity);
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
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		UserInfo ui = super.getUserInfoFromSession(request);

		if (GenericValidator.isLong(id)) {
			if (ui.getIs_fuwu() == 1) {
				int limit_count = 0;
				ServiceCenterInfo entity = new ServiceCenterInfo();
				entity.setAdd_user_id(ui.getId());
				entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
				if (entity != null) {
					limit_count = entity.getVillage_count_limit();
				}

				VillageInfo villageInfo = new VillageInfo();
				villageInfo.setIs_del(0);
				villageInfo.setAdd_user_id(ui.getId());
				villageInfo.setAudit_state(1);
				Integer village_count = getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
				if (limit_count > 0 && village_count >= limit_count) {// 添加的村站数量大于限制数量
					String msg = "添加村站的数量已满！";
					super.showMsgForCustomer(request, response, msg);
					return null;
					// saveMessage(request, "entity.village_limit");
					// return mapping.findForward("list");
				}
			}

			VillageInfo entity = new VillageInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getVillageInfoService().getVillageInfo(entity);
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
			UserInfo uInfo = new UserInfo();
			uInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
			uInfo.setOwn_village_id(Integer.valueOf(id));
			uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);
			if (uInfo != null) {// 已存在村用户直接分配个人用户
				request.setAttribute("choose_person_user", true);
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/customer/ServiceCenterInfo/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {

			VillageInfo entity = new VillageInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getVillageInfoService().getVillageInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			if (entity.getP_index() != null) {
				super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index());
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			return new ActionForward("/../manager/customer/ServiceCenterInfo/form.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// if (null == super.checkUserModPopeDom(form, request, "8")) {
		// return super.checkPopedomInvalid(request, response);
		// }
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			VillageInfo entity = new VillageInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getVillageInfoService().getVillageInfo(entity);
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
			return new ActionForward("/../manager/customer/ServiceCenterInfo/view.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward cancelVillage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");

		VillageInfo entity = new VillageInfo();
		// super.copyProperties(entity, form);

		if (GenericValidator.isInt(id)) {
			entity.setId(Integer.valueOf(id));
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			entity.setAudit_state(-1);// 审核不通过
			entity.setAudit_date(new Date());
			entity.setAudit_user_id(userInfo.getId());

			entity.getMap().put("cancel_link_user_info_is_village", "true");
			entity.getMap().put("cancel_oper_uid", userInfo.getId());
			entity.getMap().put("cancel_oper_uname", userInfo.getUser_name());

			super.getFacade().getVillageInfoService().modifyVillageInfo(entity);

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
			VillageInfo entity = new VillageInfo();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());

			// 村用户
			UserInfo user = new UserInfo();
			user.setOwn_village_id(Integer.valueOf(id));
			user.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
			UserInfo village_user = super.getFacade().getUserInfoService().getUserInfo(user);
			if (null != village_user) {
				village_user.setIs_village(0);
				village_user.setIs_del(1);
				village_user.setDel_date(new Date());
				village_user.setDel_user_id(userInfo.getId());
				entity.getMap().put("del_village_user", village_user);
			}
			getFacade().getVillageInfoService().modifyVillageInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {
				VillageInfo entity = new VillageInfo();
				entity.setIs_del(1);
				entity.setId(new Integer(id));
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());

				// 村用户
				UserInfo user = new UserInfo();
				user.setOwn_village_id(Integer.valueOf(id));
				user.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
				UserInfo village_user = super.getFacade().getUserInfoService().getUserInfo(user);
				if (null != village_user) {
					village_user.setIs_village(0);
					village_user.setIs_del(0);
					village_user.setUpdate_date(new Date());
					village_user.setUpdate_user_id(userInfo.getId());
					entity.getMap().put("del_village_user", village_user);
				}
				getFacade().getVillageInfoService().modifyVillageInfo(entity);
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

	public ActionForward logo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);
		UserInfo userInfo = super.getUserInfoFromSession(request);

		BaseFiles entity = new BaseFiles();
		entity.setLink_id(userInfo.getId());
		entity.setType(Keys.BaseFilesType.BASE_FILES_TYPE_CITY_BACKGROUND.getIndex());
		entity = getFacade().getBaseFilesService().getBaseFiles(entity);

		super.copyProperties(form, entity);
		return new ActionForward("/../manager/customer/City/form_logo.jsp");
	}

	public ActionForward saveLogo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");

		BaseFiles entity = new BaseFiles();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(id)) {// 修改
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			getFacade().getBaseFilesService().modifyBaseFiles(entity);
			saveMessage(request, "entity.updated");
		} else {

			entity.setLink_id(userInfo.getId());
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(userInfo.getId());
			entity.setType(Keys.BaseFilesType.BASE_FILES_TYPE_CITY_BACKGROUND.getIndex());
			getFacade().getBaseFilesService().createBaseFiles(entity);
			saveMessage(request, "entity.inerted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		// pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("/customer/City.do?method=logo");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward villagePhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);
		UserInfo userInfo = super.getUserInfoFromSession(request);

		if (userInfo.getIs_village() == null || userInfo.getIs_village() != 1) {
			String msg = "非村站用户，无此权限！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		VillageInfo entity = new VillageInfo();
		entity.setId(userInfo.getOwn_village_id());
		entity.setIs_del(0);
		entity.setAudit_state(1);
		entity = getFacade().getVillageInfoService().getVillageInfo(entity);
		if (null == entity) {
			String msg = "村站不存在！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}
		super.copyProperties(form, entity);
		return new ActionForward("/../manager/customer/ServiceCenterInfo/form_Photo.jsp");
	}
}
