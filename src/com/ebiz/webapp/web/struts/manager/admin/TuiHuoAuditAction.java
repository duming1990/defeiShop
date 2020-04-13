package com.ebiz.webapp.web.struts.manager.admin;

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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.UserInfo;

public class TuiHuoAuditAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (null == super.checkUserModPopeDom(form, request, "0")) { return super
				.checkPopedomInvalid(request, response); }
		setNaviStringToRequestScope(request);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;

		super.setTuihuoAudit(form, request, ui, dynaBean);

		return mapping.findForward("list");
	}

	// public ActionForward list(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// if (null == super.checkUserModPopeDom(form, request, "0")) {
	// return super.checkPopedomInvalid(request, response);
	// }
	// setNaviStringToRequestScope(request);
	//
	// DynaBean dynaBean = (DynaBean) form;
	// Pager pager = (Pager) dynaBean.get("pager");
	// String user_name_like = (String) dynaBean.get("user_name_like");
	//
	// OrderReturnInfo entity = new OrderReturnInfo();
	// super.copyProperties(entity, form);
	// entity.getMap().put("user_name_like", user_name_like);
	//
	// String st_add_date = (String) dynaBean.get("st_add_date");
	// String en_add_date = (String) dynaBean.get("en_add_date");
	// String st_audit_date = (String) dynaBean.get("st_audit_date");
	// String en_audit_date = (String) dynaBean.get("en_audit_date");
	// String update_date_st = (String) dynaBean.get("update_date_st");
	// String update_date_en = (String) dynaBean.get("update_date_en");
	// String audit_date_st = (String) dynaBean.get("audit_date_st");
	// String audit_date_en = (String) dynaBean.get("audit_date_en");
	// String is_del = (String) dynaBean.get("is_del");
	// if (StringUtils.isNotBlank(is_del)) {
	// entity.setIs_del(Integer.valueOf(is_del));
	// } else {
	// dynaBean.set("is_del", 0);
	// }
	//
	// entity.getMap().put("st_add_date", st_add_date);
	// entity.getMap().put("en_add_date", en_add_date);
	// entity.getMap().put("st_audit_date", st_audit_date);
	// entity.getMap().put("en_audit_date", en_audit_date);
	// entity.getMap().put("update_date_st", update_date_st);
	// entity.getMap().put("update_date_en", update_date_en);
	// entity.getMap().put("audit_date_st", audit_date_st);
	// entity.getMap().put("audit_date_en", audit_date_en);
	// // entity.getMap().put("expect_return_way_in", "4");//只能看到已经发货申请退款的订单申请
	// Integer recordCount =
	// getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(entity);
	// pager.init(recordCount.longValue(), pager.getPageSize(),
	// pager.getRequestPage());
	// entity.getRow().setFirst(pager.getFirstRow());
	// entity.getRow().setCount(pager.getRowCount());
	//
	// List<OrderReturnInfo> list =
	// getFacade().getOrderReturnInfoService().getOrderReturnInfoPaginatedList(entity);
	// if (null != list && list.size() > 0) {
	// for (OrderReturnInfo temp : list) {
	// if (temp.getReturn_type() != null) {
	// BaseData returnType = new BaseData();
	// returnType.setId(temp.getReturn_type());
	// returnType.setIs_del(0);
	// returnType = getFacade().getBaseDataService().getBaseData(returnType);
	// if (null != returnType) {
	// temp.getMap().put("returnTypeName", returnType.getType_name());
	// }
	// }
	// if (null != temp.getAudit_user_id()) {
	// UserInfo audit_user_info = super.getUserInfo(temp.getAudit_user_id());
	// temp.getMap().put("audit_user_info", audit_user_info);
	// }
	// }
	//
	// }
	// request.setAttribute("entityList", list);
	//
	// // 换货原因
	// BaseData baseData = new BaseData();
	// baseData.setType(10000);
	// List<BaseData> baseDataList =
	// getFacade().getBaseDataService().getBaseDataList(baseData);
	// request.setAttribute("baseDataList", baseDataList);
	//
	// return mapping.findForward("list");
	// }

	public ActionForward list_yfh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) { return super
				.checkPopedomInvalid(request, response); }
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name_like = (String) dynaBean.get("user_name_like");

		OrderReturnInfo entity = new OrderReturnInfo();
		super.copyProperties(entity, form);
		entity.getMap().put("user_name_like", user_name_like);
		entity.getMap().put("expect_return_way_in", "1,2,3");// 只能看到已经发货申请退款的订单申请
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		String st_audit_date = (String) dynaBean.get("st_audit_date");
		String en_audit_date = (String) dynaBean.get("en_audit_date");
		String update_date_st = (String) dynaBean.get("update_date_st");
		String update_date_en = (String) dynaBean.get("update_date_en");
		String audit_date_st = (String) dynaBean.get("audit_date_st");
		String audit_date_en = (String) dynaBean.get("audit_date_en");
		String is_del = (String) dynaBean.get("is_del");
		if (StringUtils.isNotBlank(is_del)) {
			entity.setIs_del(Integer.valueOf(is_del));
		} else {
			dynaBean.set("is_del", 0);
		}

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("st_audit_date", st_audit_date);
		entity.getMap().put("en_audit_date", en_audit_date);
		entity.getMap().put("update_date_st", update_date_st);
		entity.getMap().put("update_date_en", update_date_en);
		entity.getMap().put("audit_date_st", audit_date_st);
		entity.getMap().put("audit_date_en", audit_date_en);

		Integer recordCount = getFacade().getOrderReturnInfoService().getOrderReturnInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<OrderReturnInfo> list = getFacade().getOrderReturnInfoService().getOrderReturnInfoPaginatedList(entity);
		if (null != list && list.size() > 0) {
			for (OrderReturnInfo temp : list) {
				if (temp.getReturn_type() != null) {
					BaseData returnType = new BaseData();
					returnType.setId(temp.getReturn_type());
					returnType.setIs_del(0);
					returnType = getFacade().getBaseDataService().getBaseData(returnType);
					if (null != returnType) {
						temp.getMap().put("returnTypeName", returnType.getType_name());
					}
				}
				if (null != temp.getAudit_user_id()) {
					UserInfo audit_user_info = super.getUserInfo(temp.getAudit_user_id());
					temp.getMap().put("audit_user_info", audit_user_info);
				}
			}

		}
		request.setAttribute("entityList", list);

		// 换货原因
		BaseData baseData = new BaseData();
		baseData.setType(10000);
		List<BaseData> baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		request.setAttribute("baseDataList", baseDataList);

		// 跳转到已经发货功能
		return new ActionForward("/../manager/admin/TuiHuoAudit/list_yfh.jsp");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request)) { return list(mapping, form, request, response); }
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		return saveTuiHuoAudit(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) { return super
				.checkPopedomInvalid(request, response); }
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			int i = viewOrderReturnInfo(mapping, form, request, response, id);
			if (i == -1) {
				String msg = "订单不存在。";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back(-1);}");
				return null;
			}
		}
		return mapping.findForward("view");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) { return super
				.checkPopedomInvalid(request, response); }
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
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			saveToken(request);

			int i = viewOrderReturnInfo(mapping, form, request, response, id);
			if (i == -1) {
				String msg = "订单不存在。";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back(-1);}");
				return null;
			}

			return new ActionForward("/../manager/admin/TuiHuoAudit/audit.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) { return super
				.checkPopedomInvalid(request, response); }
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			OrderReturnInfo entity = new OrderReturnInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);

			// UserInfo userInfo = new UserInfo();
			// userInfo.setId(entity.getLink_id());
			// userInfo =
			// getFacade().getUserInfoService().getUserInfo(userInfo);
			// super.copyProperties(form, userInfo);
			//
			// BaseImgs imgs = new BaseImgs();
			// imgs.setLink_id(entity.getLink_id());
			// imgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_10.getIndex());
			// List<BaseImgs> imgsList =
			// getFacade().getBaseImgsService().getBaseImgsList(imgs);
			// if (null != imgsList && imgsList.size() > 0) {
			// // for (BaseImgs temp : imgsList) {
			// request.setAttribute("img_id_card_zm",
			// imgsList.get(0).getFile_path());
			// request.setAttribute("img_id_card_fm",
			// imgsList.get(1).getFile_path());
			// // }
			// }

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

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

		// String ctx =
		// request.getSession().getServletContext().getRealPath(String.valueOf(File.separatorChar));
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
		entity.setAudit_state(0);
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

	public ActionForward saveAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject data = new JSONObject();
		String code = "-1";
		String msg = "退款审核失败，请重新提交。";

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String remark = (String) dynaBean.get("remark");

		if (StringUtils.isBlank(id) || !GenericValidator.isLong(id)) {
			msg = "参数有误！";
			return returnAjaxData(response, code, msg, data);
		}

		UserInfo ui = super.getUserInfoFromSession(request);

		OrderReturnInfo entity = new OrderReturnInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getOrderReturnInfoService().getOrderReturnInfo(entity);
		super.copyProperties(entity, form);
		entity.getMap().put("remark", remark);

		entity.setId(new Integer(id));
		entity.setAudit_date(new Date());
		entity.setAudit_user_id(ui.getId());
		entity.setAudit_date(new Date());

		int i = getFacade().getOrderReturnInfoService().modifyOrderReturnInfo(entity);
		if (i > 0) {
			code = "1";
			msg = "退款审核成功！";
		}

		return returnAjaxData(response, code, msg, data);

	}
}
