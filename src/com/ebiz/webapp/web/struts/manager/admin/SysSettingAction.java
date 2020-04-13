package com.ebiz.webapp.web.struts.manager.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.menu.ClickMenu;
import com.aiisen.weixin.menu.Menu;
import com.aiisen.weixin.menu.MenuApi;
import com.aiisen.weixin.menu.SubMenu;
import com.aiisen.weixin.menu.ViewMenu;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.ShareSettings;
import com.ebiz.webapp.web.struts.weixin.Config;

/**
 * @author Wu,Yang
 * @version 2011-04-25
 */
public class SysSettingAction extends BaseAdminAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.edit(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.edit(mapping, form, request, response);
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;

		SysSetting sysSetting = new SysSetting();

		sysSetting.setTitle("isEnabledCode");
		dynaBean.set("isEnabledCode", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle("isEnabledNewsCustomFields");
		dynaBean.set("isEnabledNewsCustomFields", getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());

		sysSetting.setTitle(Keys.IS_ENABLED_INDEX_STATIC);
		dynaBean.set(Keys.IS_ENABLED_INDEX_STATIC, getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());

		sysSetting.setTitle("isEnabledMobileVeriCode");
		dynaBean.set("isEnabledMobileVeriCode", getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());

		sysSetting.setTitle(Keys.autoConfirmReceiptDays);
		dynaBean.set(Keys.autoConfirmReceiptDays, getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());

		sysSetting.setTitle(Keys.fahuoShouhuoYanDays);
		dynaBean.set(Keys.fahuoShouhuoYanDays, getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());

		sysSetting.setTitle(Keys.websiteCopyright);
		dynaBean.set(Keys.websiteCopyright, getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle("isEnabledAppScanLogin");
		dynaBean.set("isEnabledAppScanLogin", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());
		// 是否开启电子币支付
		sysSetting.setTitle("isPayDianzi");
		dynaBean.set("isPayDianzi", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle(Keys.upLevelNeedPayMoney);
		dynaBean.set(Keys.upLevelNeedPayMoney, getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());
		sysSetting.setTitle(Keys.upLevelNeedPayMoney);
		dynaBean.set(Keys.upLevelNeedPayMoney, getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());
		sysSetting.setTitle(Keys.upLevelNeedPayMoney);
		dynaBean.set(Keys.upLevelNeedPayMoney, getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());
		sysSetting.setTitle(Keys.ShareIndexDesc);
		dynaBean.set(Keys.ShareIndexDesc, getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());
		sysSetting.setTitle(Keys.ShareQrCodeDesc);
		dynaBean.set(Keys.ShareQrCodeDesc, getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());
		sysSetting.setTitle(Keys.ShareVillagePortalDesc);
		dynaBean.set(Keys.ShareVillagePortalDesc, getFacade().getSysSettingService().getSysSetting(sysSetting)
				.getContent());

		sysSetting.setTitle(Keys.APP_DESCRIPTION);
		dynaBean.set(Keys.APP_DESCRIPTION, getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle(Keys.labelManager);
		dynaBean.set(Keys.labelManager, getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());
		// sysSetting.setTitle("weixinMenu");
		// dynaBean.set("weixinMenu", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle(Keys.adminMobile);
		dynaBean.set(Keys.adminMobile, getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle(Keys.financialMobile);
		dynaBean.set(Keys.financialMobile, getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle(Keys.tuijian_cun);
		dynaBean.set(Keys.tuijian_cun, getFacade().getSysSettingService().getSysSetting(sysSetting).getMemo());
		dynaBean.set("cun_id", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());
		sysSetting.setTitle(Keys.tuijian_xian);
		dynaBean.set(Keys.tuijian_xian, getFacade().getSysSettingService().getSysSetting(sysSetting).getMemo());
		dynaBean.set("xian_id", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		request.setAttribute("ShareSettings", Keys.ShareSettings.values());

		List<ShareSettings> ShareSettings = new ArrayList<Keys.ShareSettings>();
		ShareSettings.add(Keys.ShareSettings.SystemSettings_10);
		request.setAttribute("ShareSettings", ShareSettings);

		sysSetting.setTitle("index_entp_tel");
		dynaBean.set("index_entp_tel", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());
		sysSetting.setTitle("index_entp_addr");
		dynaBean.set("index_entp_addr", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle("index_entp_mail");
		dynaBean.set("index_entp_mail", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle("index_show_pt");
		dynaBean.set("index_show_pt", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());

		sysSetting.setTitle("index_show_ys");
		dynaBean.set("index_show_ys", getFacade().getSysSettingService().getSysSetting(sysSetting).getContent());
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (isCancelled(request))
			return unspecified(mapping, form, request, response);
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return mapping.findForward("input");
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String isEnabledCode = (String) dynaBean.get("isEnabledCode");
		String isEnabledNewsCustomFields = (String) dynaBean.get("isEnabledNewsCustomFields");
		String isEnabledIndexStatic = (String) dynaBean.get("isEnabledIndexStatic");
		String isEnabledMobileVeriCode = (String) dynaBean.get("isEnabledMobileVeriCode");
		String indexAd = (String) dynaBean.get("indexAd");
		String autoConfirmReceiptDays = (String) dynaBean.get("autoConfirmReceiptDays");
		String fahuoShouhuoYanDays = (String) dynaBean.get("fahuoShouhuoYanDays");
		String websiteCopyright = (String) dynaBean.get(Keys.websiteCopyright);
		String weixinMenu = (String) dynaBean.get("weixinMenu");
		String isEnabledAppScanLogin = (String) dynaBean.get("isEnabledAppScanLogin");
		String ptRegisterPersionCount = (String) dynaBean.get("ptRegisterPersionCount");
		String everyDayMaxSend = (String) dynaBean.get("everyDayMaxSend");
		String isPayDianzi = (String) dynaBean.get("isPayDianzi");
		String upLevelNeedPayMoney = (String) dynaBean.get("upLevelNeedPayMoney");
		String ShareIndexDesc = (String) dynaBean.get("ShareIndexDesc");
		String ShareQrCodeDesc = (String) dynaBean.get("ShareQrCodeDesc");
		String ShareVillagePortalDesc = (String) dynaBean.get("ShareVillagePortalDesc");
		String app_description = (String) dynaBean.get("app_description");
		String labelManager = (String) dynaBean.get("labelManager");
		String adminMobile = (String) dynaBean.get("adminMobile");
		String financialMobile = (String) dynaBean.get("financialMobile");
		String tuijian_cun = (String) dynaBean.get("tuijian_cun");
		String cun_id = (String) dynaBean.get("cun_id");
		String tuijian_xian = (String) dynaBean.get("tuijian_xian");
		String xian_id = (String) dynaBean.get("xian_id");

		String index_entp_tel = (String) dynaBean.get("index_entp_tel");
		String index_entp_addr = (String) dynaBean.get("index_entp_addr");
		String index_entp_mail = (String) dynaBean.get("index_entp_mail");

		String index_show_pt = (String) dynaBean.get("index_show_pt");
		String index_show_ys = (String) dynaBean.get("index_show_ys");

		HttpSession session = request.getSession();
		session.setAttribute("isEnabledNewsCustomFields", isEnabledNewsCustomFields);

		SysSetting sysSetting = new SysSetting();

		sysSetting.setTitle("index_show_pt");// 首页是否显示拼团专区
		sysSetting.setContent(index_show_pt);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("index_show_ys");// 首页是否显示预售专区
		sysSetting.setContent(index_show_ys);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("ShareIndexDesc");// 首页分享
		sysSetting.setContent(ShareIndexDesc);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("index_entp_tel");// 联系电话
		sysSetting.setContent(index_entp_tel);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("index_entp_addr");// 公司地址
		sysSetting.setContent(index_entp_addr);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("index_entp_mail");// 合作邮箱
		sysSetting.setContent(index_entp_mail);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("ShareQrCodeDesc");// 二维码分享
		sysSetting.setContent(ShareQrCodeDesc);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		sysSetting.setTitle("ShareVillagePortalDesc");// 村门户分享
		sysSetting.setContent(ShareVillagePortalDesc);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		// 是否开启电子币支付
		sysSetting.setTitle("isPayDianzi");
		sysSetting.setContent(isPayDianzi);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("isEnabledCode");
		sysSetting.setContent(isEnabledCode);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("isEnabledNewsCustomFields");
		sysSetting.setContent(isEnabledNewsCustomFields);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("isEnabledIndexStatic");
		sysSetting.setContent(isEnabledIndexStatic);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		// 是否启用手机短信验证码
		sysSetting.setTitle("isEnabledMobileVeriCode");
		sysSetting.setContent(isEnabledMobileVeriCode);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		// 首页广告

		sysSetting.setTitle(Keys.autoConfirmReceiptDays);
		sysSetting.setContent(autoConfirmReceiptDays);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle(Keys.fahuoShouhuoYanDays);
		sysSetting.setContent(fahuoShouhuoYanDays);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle(Keys.websiteCopyright);
		sysSetting.setContent(websiteCopyright);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle(Keys.upLevelNeedPayMoney);
		sysSetting.setContent(upLevelNeedPayMoney);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		sysSetting.setTitle("isEnabledAppScanLogin");
		sysSetting.setContent(isEnabledAppScanLogin);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		// SEO优化
		sysSetting.setTitle(Keys.APP_DESCRIPTION);
		sysSetting.setContent(app_description);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		request.getSession().getServletContext().setAttribute(Keys.APP_DESCRIPTION, app_description);
		// 店铺便签数量
		sysSetting.setTitle(Keys.labelManager);
		sysSetting.setContent(labelManager);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		request.getSession().getServletContext().setAttribute(Keys.labelManager, labelManager);
		// 系统管理员手机
		sysSetting.setTitle(Keys.adminMobile);
		sysSetting.setContent(adminMobile);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		request.getSession().getServletContext().setAttribute(Keys.adminMobile, adminMobile);

		// 财务手机
		sysSetting.setTitle(Keys.financialMobile);
		sysSetting.setContent(financialMobile);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		request.getSession().getServletContext().setAttribute(Keys.financialMobile, financialMobile);
		// sysSetting.setTitle("weixinMenu");
		// sysSetting.setContent(weixinMenu);
		// super.getFacade().getSysSettingService().modifySysSetting(sysSetting);

		// 推荐村站
		sysSetting.setTitle(Keys.tuijian_cun);
		sysSetting.setContent(cun_id);
		sysSetting.setMemo(tuijian_cun);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		request.getSession().getServletContext().setAttribute(Keys.tuijian_cun, tuijian_cun);
		// 推荐县
		sysSetting.setTitle(Keys.tuijian_xian);
		sysSetting.setContent(xian_id);
		sysSetting.setMemo(tuijian_xian);
		super.getFacade().getSysSettingService().modifySysSetting(sysSetting);
		request.getSession().getServletContext().setAttribute(Keys.tuijian_xian, tuijian_xian);

		saveMessage(request, "entity.updated");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward insertWeixinMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String menuJson = (String) dynaBean.get("menuJson");

		if (StringUtils.isBlank(menuJson)) {
			super.renderText(response, "插入失败menuJson为空");
			return null;
		}
		JSONObject json = JSONObject.parseObject(menuJson);
		JSONObject menu = json.getJSONObject("menu");

		JSONArray buttons = menu.getJSONArray("button");

		Menu weixinMenu = new Menu();
		for (int i = 0; i < buttons.size(); i++) {
			JSONObject button = (JSONObject) buttons.get(i);
			String name = button.getString("name");
			logger.info("【父类：name】:{}", name);
			JSONArray sub_buttons = button.getJSONArray("sub_button");
			SubMenu subMenu = new SubMenu(name);

			for (int jjj = 0; jjj < sub_buttons.size(); jjj++) {
				JSONObject sub_button = (JSONObject) sub_buttons.get(jjj);
				String type = sub_button.getString("type");
				String sub_name = sub_button.getString("name");
				String url = "";
				if (null != sub_button.get("url")) {
					url = sub_button.getString("url");
				}
				String key = "";
				if (null != sub_button.get("key")) {
					key = sub_button.getString("key");
				}
				logger.info("【{}】 的子类 type:{}", name, type);
				logger.info("【{}】 的子类 sub_name:{}", name, sub_name);
				logger.info("【{}】 的子类 url:{}", name, url);
				logger.info("【{}】 的子类 key:{}", name, key);
				if (StringUtils.equalsIgnoreCase(type, "view")) {
					subMenu.addSubButton(new ViewMenu(sub_name, url));
				} else {
					subMenu.addSubButton(new ClickMenu(sub_name, key));
				}
			}
			// logger.info("");
			weixinMenu.addButton(subMenu);
		}

		// logger.info("==={}", net.sf.json.JSONObject.fromObject(weixinMenu));

		Config.init();

		String result = "insertWeixinMenu result:" + MenuApi.menuCreate(weixinMenu);

		super.renderText(response, result);
		return null;
	}

	public ActionForward deleteWeixinMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Config.init();

		String result = "deleteWeixinMenu result:" + MenuApi.menuDelete();

		super.renderText(response, result);
		return null;
	}

}
