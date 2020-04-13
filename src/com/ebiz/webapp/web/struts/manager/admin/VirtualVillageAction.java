package com.ebiz.webapp.web.struts.manager.admin;

import java.io.File;
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
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DESPlus;

public class VirtualVillageAction extends BaseAdminAction {
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
		entity.setIs_del(0);
		entity.setIs_virtual(1);// 虚拟村站
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

				// 合伙人名称
				if (null != temp && temp.getP_index() != null) {
					ServiceCenterInfo sInfo = new ServiceCenterInfo();
					sInfo.setP_index(Integer.valueOf(temp.getP_index().toString().substring(0, 6)));
					sInfo.setIs_virtual(1);
					sInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(sInfo);
					if (null != sInfo) {
						temp.getMap().put("full_name", sInfo.getServicecenter_name());
					}
				}
			}
		}
		request.setAttribute("entityList", list);
		request.setAttribute("init_pwd", Keys.INIT_PWD);
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
			entity.setIs_virtual(1);// 虚拟村站
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (entity != null) {
				limit_count = entity.getVillage_count_limit();
			}

			VillageInfo villageInfo = new VillageInfo();
			villageInfo.setIs_del(0);
			villageInfo.setAdd_user_id(ui.getId());
			villageInfo.setAudit_state(1);
			villageInfo.setIs_virtual(1);// 虚拟村站
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
		String p_index_service = (String) dynaBean.get("p_index_service");

		VillageInfo entity = new VillageInfo();
		super.copyProperties(entity, form);

		String password = Keys.INIT_PWD;

		if (!GenericValidator.isLong(id)) {

			if (StringUtils.isBlank(p_index_service) && !p_index_service.startsWith("99")) {
				String msg = "请选择合伙人";
				super.showMsgForManager(request, response, msg);
				return null;
			}

			ServiceCenterInfo service = new ServiceCenterInfo();
			service.setP_index(Integer.valueOf(p_index_service));
			service.setIs_del(0);
			service.setIs_virtual(1);
			service = getFacade().getServiceCenterInfoService().getServiceCenterInfo(service);
			if (service == null) {
				String msg = "合伙人不存在或已删除";
				super.showMsgForManager(request, response, msg);
				return null;
			}
			String p_index = p_index_service + "000000";
			Long pIndex = Long.valueOf(p_index) + 1;

			VillageInfo vInfo = new VillageInfo();
			vInfo.setIs_virtual(1);// 虚拟村站
			vInfo.getMap().put("p_index_like", p_index_service);
			int count = getFacade().getVillageInfoService().getVillageInfoCount(vInfo);
			if (count > 0) {
				pIndex = Long.valueOf(p_index) + count + 1;
			}
			entity.setP_index(pIndex);
			entity.setIs_virtual(1);
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_user_name(ui.getUser_name());
			entity.setAdd_date(new Date());

			entity.getMap().put("add_circle_of_village", true);
			entity.getMap().put("add_fen_lei", true);
			super.getFacade().getVillageInfoService().createVillageInfo(entity);
			saveMessage(request, "entity.inerted");
		} else {
			if (StringUtils.isNotBlank(is_audit)) {
				VillageInfo villageInfo = new VillageInfo();
				villageInfo.setId(Integer.valueOf(id));
				villageInfo.setIs_virtual(1);// 虚拟村站
				villageInfo = super.getFacade().getVillageInfoService().getVillageInfo(villageInfo);
				if (villageInfo != null && entity.getAudit_state() == Keys.audit_state.audit_state_1.getIndex()) {
					// 如果原先已经生成过村站，则重新激活村用户否则重新生成
					UserInfo uInfo = new UserInfo();
					uInfo.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
					uInfo.setOwn_village_id(Integer.valueOf(id));
					uInfo = getFacade().getUserInfoService().getUserInfo(uInfo);
					if (uInfo != null) {// 已存在，直接激活村用户；个人用户直接选择或申请
						if (StringUtils.isNotBlank(person_user_id)) {
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

								BaseImgs img = new BaseImgs();
								img.getMap().put("link_id", person_user_id);
								img.getMap().put("img_type", Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
								List<BaseImgs> imgs = getFacade().getBaseImgsService().getBaseImgsList(img);
								if (imgs != null || imgs.size() >= 2) {
									entity.setImg_id_card_fm(imgs.get(0).getFile_path());
									entity.setImg_id_card_zm(imgs.get(1).getFile_path());
								}

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

						String ctxDir = getServlet().getServletContext().getRealPath(File.separator);
						if (!ctxDir.endsWith(File.separator)) {
							ctxDir = ctxDir + File.separator;
						}
						entity.getMap().put("ctxDir", ctxDir);
						String ctx = super.getCtxPath(request, false);
						entity.getMap().put("ctx", ctx);

						// 创建个人用户
						if (StringUtils.isBlank(person_user_id)) {// 如果没有个人用户id，说明不是普通会员申请的，直接生成
							UserInfo user = new UserInfo();
							String personUserNo = super.createUserNo();
							user.setUser_name("pu" + personUserNo);
							user.setPassword(des.encrypt(password));

							user.setReal_name("pu" + personUserNo);
							user.setSex(villageInfo.getSex());
							user.setBirthday(villageInfo.getBirthday());
							user.setEmail(villageInfo.getEmail());
							user.setId_card(villageInfo.getId_card());
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
							user.setImg_id_card_fm(villageInfo.getImg_id_card_fm());
							user.setImg_id_card_zm(villageInfo.getImg_id_card_zm());
							entity.getMap().put("creat_village_person_user", user);
							entity.getMap().put("add_user_name", ui.getUser_name());

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

	// 获取合伙人列表
	public ActionForward getServiceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String is_virtual = (String) dynaBean.get("is_virtual");

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

		if (StringUtils.isNotBlank(is_virtual)) {
			entity.setIs_virtual(Integer.valueOf(is_virtual));
		}
		entity.setIs_del(0);
		entity.setEffect_state(1);
		entity.setIs_virtual(1);// 虚拟村站
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

		return new ActionForward("/../manager/admin/VirtualVillage/choose_service.jsp");
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
				entity.setIs_virtual(1);
				entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
				if (entity != null) {
					limit_count = entity.getVillage_count_limit();
				}

				VillageInfo villageInfo = new VillageInfo();
				villageInfo.setIs_del(0);
				villageInfo.setAdd_user_id(ui.getId());
				villageInfo.setAudit_state(1);
				villageInfo.setIs_virtual(1);
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
			entity.setIs_virtual(1);
			entity = getFacade().getVillageInfoService().getVillageInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			if (entity.getP_index() != null) {
				ServiceCenterInfo sInfo = new ServiceCenterInfo();
				sInfo.setP_index(Integer.valueOf(entity.getP_index().toString().substring(0, 6)));
				sInfo.setIs_virtual(1);
				sInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(sInfo);
				request.setAttribute("full_name", sInfo.getServicecenter_name());
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
			return new ActionForward("/../manager/admin/VirtualVillage/audit.jsp");
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
			entity.setIs_virtual(1);
			entity = getFacade().getVillageInfoService().getVillageInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			if (entity.getP_index() != null) {
				ServiceCenterInfo sInfo = new ServiceCenterInfo();
				sInfo.setIs_virtual(1);
				sInfo.setP_index(Integer.valueOf(entity.getP_index().toString().substring(0, 6)));
				sInfo = getFacade().getServiceCenterInfoService().getServiceCenterInfo(sInfo);
				request.setAttribute("ServiceInfo", sInfo);
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			return new ActionForward("/../manager/admin/VirtualVillage/form.jsp");
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
			entity.setIs_virtual(1);
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
			entity.setIs_virtual(1);

			// 村用户
			UserInfo user = new UserInfo();
			user.setOwn_village_id(Integer.valueOf(id));
			user.setUser_type(Keys.UserType.USER_TYPE_4.getIndex());
			user.setIs_village(1);
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
				entity.setIs_virtual(1);

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
}
