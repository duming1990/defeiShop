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
public class IndexEntpAction extends BaseWebAction {

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
		// 获取楼层
		List<BaseLink> list = this.getBaseLinkList(Keys.LinkType.LINK_TYPE_10000.getIndex(), 9, null);
		// for (BaseLink cur : list) {
		BaseLink a = new BaseLink();
		// a.setPar_id(cur.getId());
		a.setLink_type(Keys.LinkType.LINK_TYPE_10010.getIndex());
		a.setIs_del(0);
		// 核心优势
		List<BaseLink> baseLink10010List = getFacade().getBaseLinkService().getBaseLinkList(a);
		request.setAttribute("baseLink10010List", baseLink10010List);

		// 会员体系
		a.setLink_type(10200);
		List<BaseLink> baseLink10200List = getFacade().getBaseLinkService().getBaseLinkList(a);
		request.setAttribute("baseLink10200List", baseLink10200List);
		// 集团优势
		a.setLink_type(Keys.LinkType.LINK_TYPE_10020.getIndex());
		List<BaseLink> baseLink10020List = getFacade().getBaseLinkService().getBaseLinkList(a);
		request.setAttribute("baseLink10020List", baseLink10020List);
		// 轮播图
		a.setLink_type(Keys.LinkType.LINK_TYPE_10080.getIndex());
		List<BaseLink> baseLink10080List = getFacade().getBaseLinkService().getBaseLinkList(a);
		request.setAttribute("baseLink10080List", baseLink10080List);
		// 集团分布
		a.setLink_type(Keys.LinkType.LINK_TYPE_10030.getIndex());
		List<BaseLink> baseLink10030List = getFacade().getBaseLinkService().getBaseLinkList(a);
		request.setAttribute("baseLink10030List", baseLink10030List);
		// 公司动态
		a.setLink_type(Keys.LinkType.LINK_TYPE_10040.getIndex());
		BaseLink baseLink10040List = getFacade().getBaseLinkService().getBaseLink(a);
		request.setAttribute("baseLink10040List", baseLink10040List);
		// 合作伙伴
		a.setLink_type(Keys.LinkType.LINK_TYPE_10060.getIndex());
		List<BaseLink> baseLink10060List = getFacade().getBaseLinkService().getBaseLinkList(a);
		request.setAttribute("baseLink10060List", baseLink10060List);

		a.setLink_type(Keys.LinkType.LINK_TYPE_10100.getIndex());
		List<BaseLink> baseLink10100List = getFacade().getBaseLinkService().getBaseLinkList(a);
		request.setAttribute("baseLink10100List", baseLink10100List);

		// }

		request.setAttribute("baseLink10000List", list);
		request.setAttribute("baseLink10070List", super.common(10070).get("baseLinkList"));
		List<BaseLink> list1 = super.common(10071).get("baseLinkList");
		if (null != list1 && list1.size() > 0) {
			request.setAttribute("baseLink10071", super.common(10071).get("baseLinkList").get(0));
		}
		// request.setAttribute("baseLink10071", super.common(10071).get("baseLinkList").get(0));
		// 底部
		request.setAttribute("baseLink10090List", super.common(10090).get("baseLinkList"));

		List<BaseLink> list2 = super.common(10091).get("baseLinkList");
		if (null != list2 && list2.size() > 0) {
			request.setAttribute("baseLink10091", super.common(10091).get("baseLinkList").get(0));
		}

		// 行业新闻
		List<NewsInfo> newsList = super.getIndexNewsInfoList(request, "1808004100", 2);
		if (null != newsList && newsList.size() > 0) {
			request.setAttribute("newsList0", newsList.get(0));
			if (newsList.size() > 1) {
				request.setAttribute("newsList1", newsList.get(1));
			}

		}
		// 公司动态
		List<NewsInfo> comList = super.getIndexNewsInfoList(request, "1808004200", 2);
		if (null != comList && comList.size() > 0) {

			request.setAttribute("comList0", comList.get(0));
			if (comList.size() > 1) {
				request.setAttribute("comList1", comList.get(1));
			}
		}
		// Date day = newsList.get(0).getPub_time().getDay();
		SysSetting sysSetting = new SysSetting();
		sysSetting.setTitle("index_entp_tel");
		sysSetting = super.getFacade().getSysSettingService().getSysSetting(sysSetting);
		request.setAttribute("sysSetting", sysSetting);
		request.setAttribute("isIndex", "true");
		return new ActionForward("/IndexEntp/indexEntp.jsp");
	}

}
