package com.ebiz.webapp.web.struts.manager.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.ServiceCenterLevel;

/**
 * @author Liu,Jia
 * @version 2015-12-01
 */
public class ServiceApplyAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
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

		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("p_index_pro", Keys.P_INDEX_INIT);

		List<BaseData> baseData3100List = super.getBaseDataList(3100);
		request.setAttribute("baseData3100List", baseData3100List);

		// 查询商家从事行业，线下店铺从事行业
		super.setEntpBaseClassList(request);

		dynaBean.set("servicecenter_linkman_tel", ui.getMobile());

		List<ServiceCenterLevel> serviceLevelList = new ArrayList<ServiceCenterLevel>();
		serviceLevelList.add(Keys.ServiceCenterLevel.valueOf(Keys.ServiceCenterLevel.SERVICE_CENTER_LEVEL_2
				.getSon_type().toString()));
		request.setAttribute("serviceLevelList", serviceLevelList);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		Pager pager = (Pager) dynaBean.get("pager");
		String servicecenter_name_like = (String) dynaBean.get("servicecenter_name_like");

		ServiceCenterInfo entity = new ServiceCenterInfo();

		super.copyProperties(entity, form);
		entity.setAdd_user_id(ui.getId());
		// entity.setIs_del(0);
		entity.getMap().put("servicecenter_name_like", servicecenter_name_like);

		Integer recordCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ServiceCenterInfo> list = getFacade().getServiceCenterInfoService().getServiceCenterInfoPaginatedList(
				entity);
		if (null != list && list.size() > 0) {
			for (ServiceCenterInfo s : list) {
				if (1 == s.getEffect_state().intValue() && null != s.getEffect_end_date()) {
					String day = DurationFormatUtils.formatDuration(
							s.getEffect_end_date().getTime() - new Date().getTime(), "d");
					s.getMap().put("day_tip_give_money", day);
				}
			}
		}

		request.setAttribute("entityList", list);

		ServiceCenterInfo entityQuerySize = new ServiceCenterInfo();
		entityQuerySize.setAdd_user_id(ui.getId());
		// entityQuerySize.setIs_del(0);
		Integer entityQuerySizeCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(
				entityQuerySize);
		request.setAttribute("entityQuerySizeCount", entityQuerySizeCount);

		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String is_audit = (String) dynaBean.get("is_audit");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		String img_id_card_zm = (String) dynaBean.get("img_id_card_zm");
		String img_id_card_fm = (String) dynaBean.get("img_id_card_fm");

		String[] basefiles = { img_id_card_zm, img_id_card_fm };

		ServiceCenterInfo entity = new ServiceCenterInfo();
		super.copyProperties(entity, form);

		if (null == entity.getP_index()) {
			String msg = "请选择区域";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setP_index(entity.getP_index().longValue());
		baseProvince = getFacade().getBaseProvinceService().getBaseProvince(baseProvince);

		String server_name = StringUtils.replace(baseProvince.getFull_name(), ",", "") + "合伙人";

		ServiceCenterInfo sci = new ServiceCenterInfo();
		// sci.setP_index(p_index)
		sci.setP_index(entity.getP_index());
		sci.setIs_del(0);
		// sci.setAudit_state(1);
		// sci.setEffect_state(1);
		if (GenericValidator.isLong(id)) {
			sci.getMap().put("id_not_in", id);
		}
		int count = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(sci);
		if (count > 0) {
			String msg = "对不起，该区域已经有合伙人了，无法申请！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		resetToken(request);

		entity.setIs_del(0);
		entity.setAudit_state(0);
		if (!GenericValidator.isLong(id)) {
			entity.setId(null);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(userInfo.getId());
			entity.setAdd_user_name(userInfo.getUser_name());

			entity.setServicecenter_name(server_name);

			super.getFacade().getServiceCenterInfoService().createServiceCenterInfo(entity, basefiles);

			saveMessage(request, "entity.service.apply");
		} else {
			if (StringUtils.isNotBlank(is_audit)) {
				entity.setAudit_user_id(userInfo.getId());
				entity.setAudit_date(new Date());
			}
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(userInfo.getId());
			entity.setServicecenter_name(server_name);
			super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);

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

			if (StringUtils.isNotBlank(is_audit))
				saveMessage(request, "entity.audit");
			else
				saveMessage(request, "entity.service.apply");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
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

			List<BaseData> baseData3100List = super.getBaseDataList(3100);
			request.setAttribute("baseData3100List", baseData3100List);

			// 查询商家从事行业，线下店铺从事行业
			super.setEntpBaseClassList(request);

			request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());

			BaseImgs baseImgs = new BaseImgs();
			baseImgs.setLink_id(Integer.valueOf(id));
			baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
			List<BaseImgs> list = super.getFacade().getBaseImgsService().getBaseImgsList(baseImgs);
			if (list != null && list.size() > 0) {
				request.setAttribute("img_id_card_zm", list.get(0).getFile_path());
				request.setAttribute("img_id_card_fm", list.get(1).getFile_path());
			}
		}
		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);

			List<BaseData> baseData3100List = super.getBaseDataList(3100);
			request.setAttribute("baseData3100List", baseData3100List);

			// 查询商家从事行业，线下店铺从事行业
			super.setEntpBaseClassList(request);

			request.setAttribute("serviceLevelList", Keys.ServiceCenterLevel.values());
			BaseImgs baseImgs = new BaseImgs();
			baseImgs.setLink_id(Integer.valueOf(id));
			baseImgs.setImg_type(Keys.BaseImgsType.Base_Imgs_TYPE_30.getIndex());
			List<BaseImgs> list = super.getFacade().getBaseImgsService().getBaseImgsList(baseImgs);
			if (list != null && list.size() > 0) {
				request.setAttribute("img_id_card_zm", list.get(0).getFile_path());
				request.setAttribute("img_id_card_fm", list.get(1).getFile_path());
			}
		}
		return mapping.findForward("view");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		UserInfo userInfo = super.getUserInfoFromSession(request);

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setIs_del(1);
		entity.setId(new Integer(id));
		entity.setDel_date(new Date());
		entity.setDel_user_id(userInfo.getId());

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {

			getFacade().getServiceCenterInfoService().removeServiceCenterInfo(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {

			entity.getMap().put("pks", pks);
			getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);

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

	public ActionForward getServiceApplyInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 商家入驻电子协议
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setMod_id("1019015002");
		newsInfo = super.getFacade().getNewsInfoService().getNewsInfo(newsInfo);

		request.setAttribute("newsInfo", newsInfo);
		return new ActionForward("/../manager/customer/ServiceApply/info.jsp");
	}

	public ActionForward updateState(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String state = (String) dynaBean.get("state");

		JSONObject data = new JSONObject();

		if (!GenericValidator.isLong(id)) {
			data.put("ret", "0");
			data.put("msg", "参数有误！");
			super.renderJson(response, data.toString());
			return null;
		}

		ServiceCenterInfo serviceCenterInfoQuery = new ServiceCenterInfo();
		serviceCenterInfoQuery.setId(Integer.valueOf(id));
		serviceCenterInfoQuery = super.getFacade().getServiceCenterInfoService()
				.getServiceCenterInfo(serviceCenterInfoQuery);
		if (serviceCenterInfoQuery == null) {
			data.put("ret", "0");
			data.put("msg", "订单不存在！");
			super.renderJson(response, data.toString());
			return null;
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setId(Integer.valueOf(id));
		entity.setWl_order_state(Integer.valueOf(state));
		entity.setWl_qrsh_date(new Date());

		String msg = "操作失败或您已操作过";
		String ret = "0";
		if ("40".equals(state) && serviceCenterInfoQuery.getWl_order_state().intValue() == 20) {
			msg = "确认收货成功！";
			ret = "1";
		}

		if (ret.equals("1")) {
			super.getFacade().getServiceCenterInfoService().modifyServiceCenterInfo(entity);
		}
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}
}
