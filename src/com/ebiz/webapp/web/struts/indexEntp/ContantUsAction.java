package com.ebiz.webapp.web.struts.indexEntp;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BaseWebAction;

/**
 * @author 李百强
 * @version 2019-3-14
 */
public class ContantUsAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward toStatic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index_static.shtml");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		BaseLink baseLink1 = new BaseLink();
		baseLink1.setLink_type(Keys.LinkType.LINK_TYPE_10150.getIndex());
		baseLink1.setIs_del(0);
		List<BaseLink> baseLink10150List = getFacade().getBaseLinkService().getBaseLinkList(baseLink1);

		BaseLink baseLink2 = new BaseLink();
		baseLink2.setLink_type(Keys.LinkType.LINK_TYPE_10160.getIndex());
		baseLink2.setIs_del(0);
		List<BaseLink> baseLink10160List = getFacade().getBaseLinkService().getBaseLinkList(baseLink2);

		BaseLink baseLink3 = new BaseLink();
		baseLink3.setLink_type(Keys.LinkType.LINK_TYPE_10170.getIndex());
		baseLink3.setIs_del(0);
		List<BaseLink> baseLink10170List = getFacade().getBaseLinkService().getBaseLinkList(baseLink3);

		BaseLink baseLink4 = new BaseLink();
		baseLink4.setLink_type(Keys.LinkType.LINK_TYPE_10180.getIndex());
		baseLink4.setIs_del(0);
		List<BaseLink> baseLink10180List = getFacade().getBaseLinkService().getBaseLinkList(baseLink4);

		BaseLink baseLink5 = new BaseLink();
		baseLink5.setLink_type(Keys.LinkType.LINK_TYPE_10190.getIndex());
		baseLink5.setIs_del(0);
		List<BaseLink> baseLink10190List = getFacade().getBaseLinkService().getBaseLinkList(baseLink5);

		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));
		request.setAttribute("baseLink10071", super.common(10071).get("baseLinkList").get(0));
		request.setAttribute("baseLink10091", super.common(10091).get("baseLinkList").get(0));

		request.setAttribute("baseLink10150List", baseLink10150List);
		request.setAttribute("baseLink10160List", baseLink10160List);
		request.setAttribute("baseLink10170List", baseLink10170List);
		request.setAttribute("baseLink10180List", baseLink10180List);
		request.setAttribute("baseLink10190List", baseLink10190List);		

		NewsInfo entity = new NewsInfo();
		entity.setMod_id("1808003100");

		
		
		List<NewsInfo> comList = super.getFacade().getNewsInfoService().getNewsInfoList(entity);
		
		
		request.setAttribute("comList", comList);
		// Date day = newsList.get(0).getPub_time().getDay();
		SysSetting sysSetting = new SysSetting();
		sysSetting.setTitle("index_entp_tel");
		sysSetting = super.getFacade().getSysSettingService().getSysSetting(sysSetting);
		request.setAttribute("index_entp_tel", sysSetting);
		
		sysSetting = new SysSetting();
		sysSetting.setTitle("index_entp_addr");
		sysSetting = super.getFacade().getSysSettingService().getSysSetting(sysSetting);
		request.setAttribute("index_entp_addr", sysSetting);
	
		sysSetting = new SysSetting();
		sysSetting.setTitle("index_entp_mail");
		sysSetting = super.getFacade().getSysSettingService().getSysSetting(sysSetting);
		request.setAttribute("index_entp_mail", sysSetting);
		
		
		return new ActionForward("/IndexEntp/ContantUs/contantUs.jsp");
	}

	public ActionForward indexEntp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		logger.info("1231313213");

		List<BaseLink> base10000LinkList = this.getBaseLinkList(10000, 6, null);// 取后台编辑的楼层
		BaseLink t = new BaseLink();
		t.getMap().put("base10000LinkList", base10000LinkList);
		request.setAttribute("isIndex", "true");
		// return new ActionForward("/IndexEntp/indexEntp.jsp");
		return mapping.findForward("indexEntp");
	}

}
