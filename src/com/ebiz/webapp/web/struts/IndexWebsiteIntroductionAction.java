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

import com.ebiz.webapp.domain.NewsAttachment;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.web.Keys;

/**
 * @author Zhang,Xufeng
 * @version 2012-05-07 网站底部相关信息
 */
public class IndexWebsiteIntroductionAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		super.setPublicInfoOtherList(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_code");
		String htype = (String) dynaBean.get("htype");// 隐藏搜索类型

		if (StringUtils.isEmpty(mod_id) && !GenericValidator.isLong(mod_id)) {
			String msg = super.getMessage(request, "errors.parm");
			super.renderJavaScript(response, "alert('".concat(msg).concat("');history.back();"));
			return null;
		} else {
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setMod_id(mod_id);
			newsInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
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

				SysModule sModule = new SysModule();
				sModule.setPar_id(sysModule.getPar_id());
				sModule.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				List<SysModule> sModuleList = super.getFacade().getSysModuleService().getSysModuleList(sModule);
				if (null != sModuleList && sModuleList.size() > 0) {
					request.setAttribute("sModuleList", sModuleList);
				}
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

			return mapping.findForward("view");
		}
	}

}