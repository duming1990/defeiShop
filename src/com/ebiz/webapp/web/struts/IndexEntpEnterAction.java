package com.ebiz.webapp.web.struts;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class IndexEntpEnterAction extends BaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return new ActionForward("/IndexLogin.do", true);
		}

		if (StringUtils.isBlank(ui.getMobile())) {
			String msg = "未绑定手机号，请先去绑定手机号！";
			return super.showMsgForManager(request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String redo = (String) dynaBean.get("redo");

		if (StringUtils.isNotBlank(redo)) {
			return new ActionForward("/IndexEntpEnter.do?method=step2", true);
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setAdd_user_id(ui.getId());
		entpInfo.setIs_del(0);
		entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null != entpInfo) {

			if (StringUtils.isNotBlank(entpInfo.getSeo_keyword()) || null != entpInfo.getAudit_date()) { // 跳转到setpend自己做的特殊标志
				return new ActionForward("/IndexEntpEnter.do?method=stepend", true);
			}
			if (null != entpInfo.getFanxian_rule()) { // 跳转到setp4
				return new ActionForward("/IndexEntpEnter.do?method=step4", true);
			}
			if (null != entpInfo.getP_index()) { // 跳转到setp3
				return new ActionForward("/IndexEntpEnter.do?method=step3", true);
			}
			if (StringUtils.isNotBlank(entpInfo.getEntp_tel()) && StringUtils.isNotBlank(entpInfo.getEntp_linkman())) {
				// 跳转到setp2
				return new ActionForward("/IndexEntpEnter.do?method=step2", true);
			}

		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		// 商家入驻电子协议
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setMod_id("1019015001");
		newsInfo = super.getFacade().getNewsInfoService().getNewsInfo(newsInfo);
		request.setAttribute("newsInfo", newsInfo);

		// request.setAttribute("isEnabledMobileVeriCode", super.getSysSetting("isEnabledMobileVeriCode"));
		return new ActionForward("/index/IndexEntpEnter/step1.jsp");
	}

	public ActionForward step2(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return new ActionForward("/IndexLogin.do", true);
		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		request.setAttribute("sex", ui.getSex());

		this.setEntpInfoToForm(form, ui);

		// request.setAttribute("isEnabledMobileVeriCode", super.getSysSetting("isEnabledMobileVeriCode"));
		return new ActionForward("/index/IndexEntpEnter/step2.jsp");
	}

	public ActionForward step3(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return new ActionForward("/IndexLogin.do", true);
		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		String sex = (String) dynaBean.get("sex");
		String entp_id = (String) dynaBean.get("entp_id");
		if (StringUtils.isNotBlank(sex)) {
			if (!GenericValidator.isInt(sex)) {
				String msg = "性别参数错误";
				return super.showMsgForManager(request, response, msg);
			}
			UserInfo uiu = new UserInfo();
			uiu.setId(ui.getId());
			uiu.setSex(Integer.valueOf(sex));// 修改性别
			getFacade().getUserInfoService().modifyUserInfo(uiu);
		}

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setAdd_user_id(ui.getId());
		entpInfo.setIs_del(0);
		entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);

		if (StringUtils.isNotBlank(entp_id)) {// 修改企业信息
			if (!GenericValidator.isLong(entp_id)) {
				String msg = "entp_id参数错误";
				return super.showMsgForManager(request, response, msg);
			}
			entity.setId(Integer.valueOf(entp_id));
			super.getFacade().getEntpInfoService().modifyEntpInfo(entity);

		} else if (StringUtils.isNotBlank(entity.getEntp_tel()) && StringUtils.isNotBlank(entity.getEntp_linkman())
				&& null == entpInfo) {
			entity.setIs_del(0);
			entity.setUuid(UUID.randomUUID().toString());
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(ui.getId());
			logger.info("==ui.getUser_name():{}", ui.getUser_name());
			entity.setAdd_user_name(ui.getUser_name());
			entity.setEntp_type(10);
			super.getFacade().getEntpInfoService().createEntpInfo(entity);
		}

		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("city", Keys.P_INDEX_CITY);
		if (null != entpInfo && null != entpInfo.getP_index()) {
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entpInfo.getP_index()));
		}
		this.setEntpInfoToForm(form, ui);

		return new ActionForward("/index/IndexEntpEnter/step3.jsp");
	}

	public ActionForward step4(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return new ActionForward("/IndexLogin.do", true);
		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");

		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(entp_id)) {// 修改企业信息.只存在修改情况
			if (!GenericValidator.isLong(entp_id)) {
				String msg = "entp_id参数错误";
				return super.showMsgForManager(request, response, msg);
			}
			entity.setId(Integer.valueOf(entp_id));
			super.getFacade().getEntpInfoService().modifyEntpInfo(entity);

		}

		request.setAttribute("shopTypes", Keys.ShopType.values());// 店铺类型，用的企业表is_nx_entp这个字段，没有增加字段了

		// 查询商家从事行业，线下店铺从事行业
		super.setEntpBaseClassList(request);

		BaseClass baseClass_XianXia = new BaseClass();
		baseClass_XianXia.setCls_id(Integer.valueOf(Keys.XIANXIA_CLS_ID));
		List<BaseClass> baseClass_XianXia_List = super.getBaseClassParList(baseClass_XianXia, 2, null);
		request.setAttribute("baseClass_XianXia_List", baseClass_XianXia_List);

		this.setEntpInfoToForm(form, ui);

		return new ActionForward("/index/IndexEntpEnter/step4.jsp");
	}

	public ActionForward step5(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return new ActionForward("/IndexLogin.do", true);
		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String entp_name = (String) dynaBean.get("entp_name");
		logger.info("==entp_id" + entp_id);
		logger.info("==entp_name" + entp_name);
		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(entp_id)) {// 修改企业信息.只存在修改情况
			if (!GenericValidator.isLong(entp_id)) {
				String msg = "entp_id参数错误";
				return super.showMsgForManager(request, response, msg);
			}
			if (StringUtils.isBlank(entity.getEntp_name())) {
				String msg = "请填写店铺名称";
				return super.showMsgForManager(request, response, msg);
			}

			int count = validateEntpName(entp_id, entp_name);
			if (count > 0) {
				String msg = "店铺名称已使用，请重新填写";
				return super.showMsgForManager(request, response, msg);
			}

			logger.info("==entp_name" + entity.getEntp_name());
			entity.setId(Integer.valueOf(entp_id));
			entity.setAudit_state(0);
			entity.setSeo_keyword("end");
			entity.getMap().put("update_user_info", "true");

			EntpInfo entityQuery = new EntpInfo();
			entityQuery.setId(Integer.valueOf(Integer.valueOf(entp_id)));
			entityQuery = super.getFacade().getEntpInfoService().getEntpInfo(entityQuery);
			if (null != entityQuery) {
				entity.getMap().put("user_id", entityQuery.getAdd_user_id());
			}

			super.getFacade().getEntpInfoService().modifyEntpInfo(entity);

		}

		return new ActionForward("/IndexEntpEnter.do?method=stepend", true);
	}

	public ActionForward stepend(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			return new ActionForward("/IndexLogin.do", true);
		}

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		setEntpInfoToForm(form, ui);

		return new ActionForward("/index/IndexEntpEnter/step_end.jsp");
	}

	public void setEntpInfoToForm(ActionForm form, UserInfo ui) {
		if (null != ui.getOwn_entp_id()) {
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.setId(new Integer(ui.getOwn_entp_id()));
			entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
			super.copyProperties(form, entpInfo);
		} else {
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.setAdd_user_id(ui.getId());
			entpInfo.setIs_del(0);
			entpInfo = getFacade().getEntpInfoService().getEntpInfo(entpInfo);
			super.copyProperties(form, entpInfo);
		}
	}

}
