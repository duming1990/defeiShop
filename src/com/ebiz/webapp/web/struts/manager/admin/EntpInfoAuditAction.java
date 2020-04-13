package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class EntpInfoAuditAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		dynaBean.set("order_value", "0");
		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("p_index_pro", Keys.P_INDEX_INIT);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String is_del = (String) dynaBean.get("is_del");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String user_name = (String) dynaBean.get("user_name");
		String today_date = (String) dynaBean.get("today_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String update_date_eq = (String) dynaBean.get("update_date_eq");
		String entp_type = (String) dynaBean.get("entp_type");

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("entp_name_like", entp_name_like);
		if (StringUtils.isNotBlank(entp_type)) {
			entity.setEntp_type(Integer.valueOf(entp_type));
		}

		if (StringUtils.isNotBlank(user_name)) {
			UserInfo ui = new UserInfo();
			ui.setUser_name(user_name);
			ui = getFacade().getUserInfoService().getUserInfo(ui);
			if (ui != null) {
				entity.setId(ui.getOwn_entp_id());
			} else {
				entity.setId(0);
			}
		}
		if (StringUtils.isBlank(is_del)) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		if (StringUtils.isNotBlank(update_date_eq)) {
			entity.getMap().put("update_date_eq", update_date_eq);
		}

		String add_date_st = (String) dynaBean.get("add_date_st");
		String add_date_en = (String) dynaBean.get("add_date_en");
		String update_date_st = (String) dynaBean.get("update_date_st");
		String update_date_en = (String) dynaBean.get("update_date_en");
		String audit_date_st = (String) dynaBean.get("audit_date_st");
		String audit_date_en = (String) dynaBean.get("audit_date_en");
		entity.getMap().put("add_date_st", add_date_st);
		entity.getMap().put("add_date_en", add_date_en);
		entity.getMap().put("update_date_st", update_date_st);
		entity.getMap().put("update_date_en", update_date_en);
		entity.getMap().put("audit_date_st", audit_date_st);
		entity.getMap().put("audit_date_en", audit_date_en);

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> list = getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		if (null != list && list.size() > 0) {
			for (EntpInfo temp : list) {
				UserInfo userInfoTemp = super.getUserInfoWithEntpId(temp.getId());
				temp.getMap().put("userInfoTemp", userInfoTemp);
			}
		}

		request.setAttribute("entityList", list);
		request.setAttribute("entpTypeList", Keys.EntpType.values());

		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String entp_logo_s = (String) dynaBean.get("entp_logo_s");
		String entp_licence_img_s = (String) dynaBean.get("entp_licence_img_s");
		String img_id_card_zm_s = (String) dynaBean.get("img_id_card_zm_s");
		String img_id_card_fm_s = (String) dynaBean.get("img_id_card_fm_s");
		String img_entp_mentou_s = (String) dynaBean.get("img_entp_mentou_s");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String entp_content = (String) dynaBean.get("entp_content");

		UserInfo userInfo = super.getUserInfoFromSession(request);

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isBlank(is_audit)) {
			int count = validateEntpName(id, entity.getEntp_name());
			if (count > 0) {
				String msg = "店铺名称已使用，请重新填写";
				return super.showMsgForManager(request, response, msg);
			}

		}

		entity.getMap().put("entp_content", entp_content);

		entity.setEntp_logo(entp_logo_s);
		entity.setEntp_licence_img(entp_licence_img_s);
		entity.setImg_id_card_zm(img_id_card_zm_s);
		entity.setImg_id_card_fm(img_id_card_fm_s);
		entity.setImg_entp_mentou(img_entp_mentou_s);

		List<UploadFile> uploadFileList = super.uploadFile(form, true, false, Keys.NEWS_INFO_IMAGE_SIZE);
		for (UploadFile uploadFile : uploadFileList) {
			if (StringUtils.isNotBlank(uploadFile.getFileSavePath())) {
				if ("entp_licence_img".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setEntp_licence_img(uploadFile.getFileSavePath());
				}
				if ("entp_logo".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setEntp_logo(uploadFile.getFileSavePath());
				}
				if ("img_id_card_zm".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setImg_id_card_zm(uploadFile.getFileSavePath());
				}
				if ("img_id_card_fm".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setImg_id_card_fm(uploadFile.getFileSavePath());
				}
				if ("img_entp_mentou".equalsIgnoreCase(uploadFile.getFormName())) {
					entity.setImg_entp_mentou(uploadFile.getFileSavePath());
				}
			}
		}

		if (!GenericValidator.isLong(id)) {
			entity.setIs_del(0);
			entity.setUuid(UUID.randomUUID().toString());
			entity.setAdd_date(new Date());
			entity.setEntp_type(Keys.EntpType.ENTP_TYPE_10.getIndex());
			super.getFacade().getEntpInfoService().createEntpInfo(entity);

			saveMessage(request, "entity.inerted");
		} else {
			if (StringUtils.isNotBlank(is_audit)) {
				entity.setAudit_user_id(userInfo.getId());
				entity.setAudit_date(new Date());
				entity.getMap().put("update_user_info", "true");

				EntpInfo entityQuery = new EntpInfo();
				entityQuery.setId(Integer.valueOf(id));
				entityQuery = super.getFacade().getEntpInfoService().getEntpInfo(entityQuery);
				if (null != entityQuery) {
					entity.getMap().put("user_id", entityQuery.getAdd_user_id());
					entity.setEntp_name(entityQuery.getEntp_name());

					if (entity.getAudit_state() == 2) {
						entity.setEntp_no("S" + super.createEntpNo(entity.getId()));
					}

					if (entity.getAudit_state() == -2 && entityQuery.getAudit_state() == 2) {
						// 如果商家由审核通过更改为不通过，则该商家的商品全部下架
						entity.getMap().put("update_entp_comm", true);
					}
				}
			} else {
				entity.getMap().put("update_entp_content", "true");
			}

			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			super.getFacade().getEntpInfoService().modifyEntpInfo(entity);
			if (StringUtils.isNotBlank(is_audit))
				saveMessage(request, "entity.audit");
			else
				saveMessage(request, "entity.updated");
		}
		// return null;

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

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);

			if (null != entity) {
				// the line below is added for pagination
				entity.setQueryString(super.serialize(request, "id", "method"));
				// end
				super.copyProperties(form, entity);

				EntpContent entpContent = new EntpContent();
				entpContent.setType(0);
				entpContent.setEntp_id(new Integer(id));
				entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
				if (null != entpContent) {
					dynaBean.set("entp_content", entpContent.getContent());
				}

				// 查询商家从事行业，线下店铺从事行业
				super.setEntpBaseClassList(request);
			}
		}
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
			EntpInfo entity = new EntpInfo();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(userInfo.getId());
			entity.getMap().put("update_user_info", "true");

			EntpInfo entityQuery = new EntpInfo();
			entityQuery.setId(Integer.valueOf(id));
			entityQuery = super.getFacade().getEntpInfoService().getEntpInfo(entityQuery);
			if (null != entityQuery) {
				entity.getMap().put("user_id", entityQuery.getAdd_user_id());
			}

			getFacade().getEntpInfoService().modifyEntpInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			for (String cur_id : pks) {

				EntpInfo entity = new EntpInfo();
				entity.setIs_del(1);
				entity.setId(Integer.valueOf(cur_id));
				entity.setDel_date(new Date());
				entity.setDel_user_id(userInfo.getId());

				entity.getMap().put("update_user_info", "true");
				EntpInfo entityQuery = new EntpInfo();
				entityQuery.setId(Integer.valueOf(id));
				entityQuery = super.getFacade().getEntpInfoService().getEntpInfo(entityQuery);
				if (null != entityQuery) {
					entity.getMap().put("user_id", entityQuery.getAdd_user_id());
				}

				getFacade().getEntpInfoService().modifyEntpInfo(entity);

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

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");

			}

			if (null != entity.getAdd_user_id()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setIs_del(0);
				userInfo.setId(entity.getAdd_user_id());
				List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
				request.setAttribute("userInfoList", userInfoList);

				UserInfo entpUser = new UserInfo();
				if (null != userInfoList && userInfoList.size() > 0) {
					entpUser = userInfoList.get(0);
				} // 取性别用的
				request.setAttribute("entpUser", entpUser);

				request.setAttribute("list_size", userInfoList.size());
			}

			request.setAttribute("shopTypes", Keys.ShopType.values());// 店铺类型，用的企业表is_nx_entp这个字段，没有增加字段了

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			EntpContent entpContent = new EntpContent();
			entpContent.setType(0);
			entpContent.setEntp_id(new Integer(id));
			entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
			if (null != entpContent) {
				dynaBean.set("entp_content", entpContent.getContent());
			}

			// 查询商家从事行业，线下店铺从事行业
			super.setEntpBaseClassList(request);

			return new ActionForward("/../manager/admin/EntpInfoAudit/audit.jsp");
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
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			EntpContent entpContent = new EntpContent();
			entpContent.setType(0);
			entpContent.setEntp_id(new Integer(id));
			entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
			if (null != entpContent) {
				dynaBean.set("entp_content", entpContent.getContent());
			}

			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));

			// 查询商家从事行业，线下店铺从事行业
			super.setEntpBaseClassList(request);

			request.setAttribute("shopTypes", Keys.ShopType.values());// 店铺类型，用的企业表is_nx_entp这个字段，没有增加字段了
		}

		return mapping.findForward("input");
	}

	public ActionForward deleteImageOrVideo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String type = (String) dynaBean.get("type");
		String file_path = (String) dynaBean.get("file_path");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(type) || StringUtils.isBlank(file_path)) {
			super.renderText(response, "fail");
			return null;
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(Integer.valueOf(id));
		entpInfo.getMap().put("clear_" + type, "true");// clear_image_path和clear_video_path
		getFacade().getEntpInfoService().modifyEntpInfo(entpInfo);

		// String ctx = request.getSession().getServletContext().getRealPath(String.valueOf(File.separatorChar));
		// String realFilePath = ctx + file_path;
		// FileTools.deleteFile(realFilePath);

		super.renderText(response, "success");
		return null;
	}

	public ActionForward chooseEntpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String entp_type = (String) dynaBean.get("entp_type");
		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(1);
		entity.setIs_del(0);

		entity.getMap().put("entp_name_like", entp_name_like);

		if (StringUtils.isNotBlank(entp_type)) {
			entity.setEntp_type(Integer.valueOf(entp_type));
		}
		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/admin/EntpInfoAudit/choose_entpinfo.jsp");
	}

	public ActionForward getEntpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);
			if (null != entity) {
				UserInfo userInfo = new UserInfo();
				userInfo.setOwn_entp_id(entity.getId());
				userInfo.setIs_del(0);
				List<UserInfo> userInfoList = super.getFacade().getUserInfoService().getUserInfoList(userInfo);
				request.setAttribute("userInfoList", userInfoList);
				request.setAttribute("list_size", userInfoList.size());
			}
			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			dynaBean.set("full_name", entity.getMap().get("full_name"));

		}
		return new ActionForward("/../manager/admin/EntpInfoAudit/entpInfoForColorBox.jsp");
	}

	public ActionForward updateEntpInfoForShow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_show = (String) dynaBean.get("is_show");
		String entp_id = (String) dynaBean.get("entp_id");
		JSONObject json = new JSONObject();
		int ret = 0;
		String msg = "操作失败";
		if (!GenericValidator.isLong(entp_id)) {
			msg = "entp_id参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		if (!GenericValidator.isLong(is_show)) {
			msg = "is_show参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}

		EntpInfo entity = new EntpInfo();
		entity.setId(new Integer(entp_id));
		entity.setIs_show(Integer.valueOf(is_show));
		int row = getFacade().getEntpInfoService().modifyEntpInfo(entity);
		logger.info("row:" + row);
		if (row == 1) {
			ret = 1;
			msg = "操作成功";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		json.put("ret", ret);
		json.put("msg", msg);
		super.renderJson(response, json.toJSONString());
		return null;
	}

	public ActionForward updateEntpInfoForCancel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		JSONObject json = new JSONObject();
		int ret = 0;
		String msg = "操作失败";
		if (!GenericValidator.isLong(entp_id)) {
			msg = "entp_id参数不正确";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}

		EntpInfo entity = new EntpInfo();
		entity.setId(new Integer(entp_id));
		entity.setAudit_state(Keys.audit_state.audit_state_fu_2.getIndex());
		EntpInfo entityQuery = new EntpInfo();
		entityQuery.setId(Integer.valueOf(entp_id));
		entityQuery = super.getFacade().getEntpInfoService().getEntpInfo(entityQuery);
		if (null == entityQuery) {
			msg = "企业不存在";
			json.put("ret", ret);
			json.put("msg", msg);
			super.renderJson(response, json.toJSONString());
			return null;
		}
		entity.getMap().put("user_id", entityQuery.getAdd_user_id());
		int row = getFacade().getEntpInfoService().modifyEntpInfoForCancel(entity);
		if (row == 1) {
			CommInfo commInfo = new CommInfo();
			commInfo.setIs_sell(Keys.IsSell.IS_SELL_0.getIndex());
			commInfo.getMap().put("own_entp_id", entp_id);
			int count = super.getFacade().getCommInfoService().modifyCommInfo(commInfo);
			if (count > 0) {
				ret = 1;
				msg = "操作成功";
				json.put("ret", ret);
				json.put("msg", msg);
				super.renderJson(response, json.toJSONString());
				return null;
			}
		}
		json.put("ret", ret);
		json.put("msg", msg);
		super.renderJson(response, json.toJSONString());
		return null;
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Map<String, Object> model = new HashMap<String, Object>();

		String is_del = (String) dynaBean.get("is_del");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String add_user_name_like = (String) dynaBean.get("add_user_name_like");
		String today_date = (String) dynaBean.get("today_date");
		String province = (String) dynaBean.get("province");
		String city = (String) dynaBean.get("city");
		String country = (String) dynaBean.get("country");
		String update_date_eq = (String) dynaBean.get("update_date_eq");
		String add_date_st = (String) dynaBean.get("add_date_st");
		String add_date_en = (String) dynaBean.get("add_date_en");
		String update_date_st = (String) dynaBean.get("update_date_st");
		String update_date_en = (String) dynaBean.get("update_date_en");
		String audit_date_st = (String) dynaBean.get("audit_date_st");
		String audit_date_en = (String) dynaBean.get("audit_date_en");

		String code = (String) dynaBean.get("code");

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("entp_name_like", entp_name_like);
		entity.getMap().put("add_user_name_like", add_user_name_like);
		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.getMap().put("p_index_like", country);
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_like", city.substring(0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_like", province.substring(0, 2));
		}
		if (StringUtils.isNotBlank(update_date_eq)) {
			entity.getMap().put("update_date_eq", update_date_eq);
		}

		entity.getMap().put("add_date_st", add_date_st);
		entity.getMap().put("add_date_en", add_date_en);
		entity.getMap().put("update_date_st", update_date_st);
		entity.getMap().put("update_date_en", update_date_en);
		entity.getMap().put("audit_date_st", audit_date_st);
		entity.getMap().put("audit_date_en", audit_date_en);

		List<EntpInfo> list = getFacade().getEntpInfoService().getEntpInfoList(entity);
		if (null != list && list.size() > 0) {
			for (EntpInfo temp : list) {
				UserInfo userInfoTemp = super.getUserInfoWithEntpId(temp.getId());
				temp.getMap().put("userInfoTemp", userInfoTemp);
			}
		}

		model.put("entityList", list);
		String content = getFacade().getTemplateService().getContent("EntpInfoAudit/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("商家入驻信息.xls");
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

	public ActionForward updateAd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String is_open_ad = (String) dynaBean.get("is_open_ad");

		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getEntpInfoService().getEntpInfo(entity);
		if (null == entity) {
			msg = "未查询到关联企业信息";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		// 开启商家入驻的广告权限，默认开启商家广告权限
		EntpInfo entityUpdate = new EntpInfo();
		entityUpdate.setId(Integer.valueOf(id));
		entityUpdate.setIs_open_ad(Integer.valueOf(is_open_ad));
		getFacade().getEntpInfoService().modifyEntpInfo(entityUpdate);

		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward updateShop(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String is_has_open_online_shop = (String) dynaBean.get("is_has_open_online_shop");

		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getEntpInfoService().getEntpInfo(entity);
		if (null == entity) {
			msg = "未查询到关联企业信息";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		// 开启商家入驻的网店开通情况，默认开启商家入驻的网店开通
		EntpInfo entityUpdate = new EntpInfo();
		entityUpdate.setId(Integer.valueOf(id));
		entityUpdate.setIs_has_open_online_shop(Integer.valueOf(is_has_open_online_shop));
		getFacade().getEntpInfoService().modifyEntpInfo(entityUpdate);

		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward coupons(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String is_coupons = (String) dynaBean.get("is_coupons");

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		if (StringUtils.isBlank(entp_id)) {
			msg = "参数不能为空";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(Integer.valueOf(entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null != entpInfo) {
			entpInfo.setIs_coupons(Integer.valueOf(is_coupons));
			super.getFacade().getEntpInfoService().modifyEntpInfo(entpInfo);
		} else {
			msg = "没有该商家";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

}
