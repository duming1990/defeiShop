package com.ebiz.webapp.web.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2010-12-16
 */
public class IndexNewsInfoAction extends BaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		DynaBean dynaBean = (DynaBean) form;
		String mod_code = (String) dynaBean.get("mod_code");// 1003001000 商务新闻,1003003000 商务资讯
		String par_code = (String) dynaBean.get("par_code");// 1003001000 商务新闻,1003003000 商务资讯

		Pager pager = (Pager) dynaBean.get("pager");
		dynaBean.set("mod_code", mod_code);

		if (StringUtils.isEmpty(mod_code) && !GenericValidator.isLong(mod_code)) {
			String msg = super.getMessage(request, "errors.parm");
			super.renderJavaScript(response, "alert('".concat(msg).concat("');history.back();"));
			return null;
		}

		SysModule sysModule = new SysModule();
		sysModule.setIs_del(0);
		sysModule.setMod_id(Long.valueOf(mod_code));
		sysModule = super.getFacade().getSysModuleService().getSysModule(sysModule);
		if ((null != sysModule)) {
			String mod_name = sysModule.getMod_name();
			mod_name = StringUtils.replace(mod_name, "管理", "");
			request.setAttribute("mod_name", mod_name);
		}

		SysModule sModule = new SysModule();
		sModule.setPar_id(new Long(par_code));
		sModule.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<SysModule> sModuleList = super.getFacade().getSysModuleService().getSysModuleList(sModule);
		if (null != sModuleList && sModuleList.size() > 0) {
			request.setAttribute("sModuleList", sModuleList);
		}

		NewsInfo entity = new NewsInfo();
		super.copyProperties(entity, form);
		entity.setMod_id(mod_code);// 商务资讯
		entity.setIs_del(0);
		entity.setInfo_state(3);// 已审核已发布
		// entity.getMap().put("no_invalid", "no_invalid");
		//

		Integer recordCount = getFacade().getNewsInfoService().getNewsInfoCount(entity);

		pager.init(recordCount.longValue(), 20, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoPaginatedList(entity);
		request.setAttribute("newsInfoList", newsInfoList);

		return mapping.findForward("list");

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);

		DynaBean dynaBean = (DynaBean) form;
		String uuid = (String) dynaBean.get("uuid");
		String htype = (String) dynaBean.get("htype");// 隐藏搜索类型

		Boolean judgeIsMoblie = super.JudgeIsMoblie(request);
		if (judgeIsMoblie) {
			String ctx = super.getCtxPath(request);
			return new ActionForward(ctx + "/m/MNewsInfo.do?method=view&uuid=" + uuid, true);
		}

		if (StringUtils.isEmpty(uuid) || uuid.length() != 36) {
			String msg = super.getMessage(request, "errors.parm");
			super.renderJavaScript(response, "alert('".concat(msg).concat("');history.back();"));
			return null;
		} else {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setUuid(uuid);
			NewsInfo entity = getFacade().getNewsInfoService().getNewsInfo(newsInfo);
			if (null == entity) {
				String msg = super.getMessage(request, "entity.missing");
				super.renderJavaScript(response, "alert('".concat(msg).concat("');history.back();"));
				return null;
			}

			SysModule sysModule = new SysModule();
			sysModule.setIs_del(0);
			sysModule.setMod_id(Long.valueOf(entity.getMod_id()));
			sysModule = super.getFacade().getSysModuleService().getSysModule(sysModule);
			if ((null != sysModule)) {
				request.setAttribute("mod_name", sysModule.getMod_name());
				dynaBean.set("mod_code", sysModule.getMod_id().toString());
				dynaBean.set("par_code", sysModule.getPar_id().toString());
			}

			SysModule sModule = new SysModule();
			sModule.setPar_id(new Long(sysModule.getPar_id()));
			sModule.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<SysModule> sModuleList = super.getFacade().getSysModuleService().getSysModuleList(sModule);
			if (null != sModuleList && sModuleList.size() > 0) {
				request.setAttribute("sModuleList", sModuleList);
			}

			if (null != entity.getView_count()) {
				newsInfo.setView_count(entity.getView_count() + 1);
			} else {
				newsInfo.setView_count(0);
			}
			newsInfo.setId(entity.getId());
			getFacade().getNewsInfoService().modifyNewsInfo(newsInfo);

			NewsAttachment attachment = new NewsAttachment();
			attachment.setLink_id(entity.getId());
			request.setAttribute("attachmentList",
					getFacade().getNewsAttachmentService().getNewsAttachmentList(attachment));

			entity.setKeyword(null);// 防止页面搜索框中默认显示 预定中旅游 美食 酒店的关键字，webapp_TOURISM
			super.copyProperties(form, entity);

			request.setAttribute("xgxxList", getNewsInfoList(request, entity.getMod_id(), 10));

			// 广告位
			// this.setAdsInfoToSession(request);

			if (StringUtils.isBlank(htype)) {
				htype = "0";
			}
			dynaBean.set("htype", htype);

			String city = Keys.QUANGUO_P_INDEX;
			List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", city);
			if (null != base0LinkList && base0LinkList.size() > 0) {
				request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
			}

			return mapping.findForward("view");
		}
	}

}