package com.ebiz.webapp.web.struts;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.ServiceBaseLink;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author 戴诗学
 * @version 2018-4-21
 */
public class IndexTsgAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward subdomain(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		DynaBean dynaBean = (DynaBean) form;

		String custom_url = (String) dynaBean.get("custom_url");
		if (StringUtils.isBlank(custom_url)) {
			String msg = "传入的参数为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		UserInfo info = new UserInfo();
		info.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		info.setDomain_site(custom_url);
		info = super.getFacade().getUserInfoService().getUserInfo(info);
		if (null != info) {
			this.shareSubDomainSession(request, response);// session二级域名共享
			return this.indexStyle(mapping, form, request, response, null, String.valueOf(info.getP_index()));
		}

		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setIs_del(0);
		serviceCenterInfo.setAudit_state(1);
		serviceCenterInfo.setDomain_site(custom_url);
		serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
		if (serviceCenterInfo == null) {
			String msg = "县域馆不存在";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}
		this.shareSubDomainSession(request, response);// session二级域名共享
		return this.indexStyle(mapping, form, request, response, serviceCenterInfo.getId().toString(), null);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		String Link_id = (String) dynaBean.get("Link_id");

		return this.indexStyle(mapping, form, request, response, Link_id, p_index);

	}

	public ActionForward indexStyle(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String Link_id, String p_index) throws Exception {

		Integer link_id = null;

		if (StringUtils.isBlank(Link_id)) {// 如果Link_id没传，就代表是市级馆
			if (StringUtils.isNotBlank(p_index)) {
				UserInfo userInfo = new UserInfo();
				userInfo.setP_index(Integer.valueOf(p_index));
				userInfo.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
				userInfo.setIs_del(0);
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {// 市级馆已经维护了
					ServiceBaseLink baseLink = new ServiceBaseLink();
					baseLink.setLink_id(userInfo.getId());
					baseLink.setLink_type(10);
					baseLink.setMain_type(20);
					baseLink.setIs_del(0);
					int count = super.getFacade().getServiceBaseLinkService().getServiceBaseLinkCount(baseLink);
					link_id = userInfo.getId();
					if (0 == count) {
						String msg = "市级特色馆维护中！";
						super.showMsgForManager(request, response, msg);
						return null;
					}
				} else {
					String msg = "市级特色馆维护中！";
					super.showMsgForManager(request, response, msg);
					return null;
				}
			} else {
				Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
				if (null == current_p_index || "100000".equals(URLDecoder.decode(current_p_index.getValue(), "UTF-8"))
						&& StringUtils.isBlank(p_index)) {// 没有选择城市
					String msg = "请先定位城市！";
					super.renderJavaScript(response, "parent.location.href='" + this.getCtxPath(request)
							+ "/ChangeCity.do?has_weihu=true'");
					return null;
				} else {
					if (StringUtils.isBlank(p_index)) {// 假如p_index没传过来，就从定位里获取
						p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
					}
					UserInfo userInfo = new UserInfo();
					userInfo.setP_index(Integer.valueOf(p_index));
					userInfo.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
					userInfo.setIs_del(0);
					userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
					if (null != userInfo) {// 市级馆已经维护了
						ServiceBaseLink baseLink = new ServiceBaseLink();
						baseLink.setLink_id(userInfo.getId());
						baseLink.setLink_type(10);
						baseLink.setMain_type(20);
						baseLink.setIs_del(0);
						int count = super.getFacade().getServiceBaseLinkService().getServiceBaseLinkCount(baseLink);
						link_id = userInfo.getId();
						if (0 == count) {
							String msg = "市级特色馆维护中！";
							super.showMsgForManager(request, response, msg);
							return null;
						}
					} else {
						String msg = "市级特色馆维护中！";
						super.showMsgForManager(request, response, msg);
						return null;
					}

				}
			}
		} else {
			link_id = Integer.valueOf(Link_id);
		}
		List<ServiceBaseLink> baseLink20List = null;
		List<ServiceBaseLink> baseLink10List = null;
		if (StringUtils.isBlank(Link_id)) {
			List<ServiceBaseLink> baseLink100List = this.getServiceBaseLinkCityList(0, p_index, "no_null_image_path");// 取市级下面县域馆的头像
			request.setAttribute("baseLink100List", baseLink100List);
			baseLink20List = this.getServiceBaseLinkCityWithList(20, null, link_id, 20, "no_null_image_path");// 取后台编辑的楼层
			baseLink10List = this.getServiceBaseLinkCityWithList(10, null, link_id, 20, "no_null_image_path");// 取后台编辑的轮播图
			ServiceBaseLink baseLinkBg = this.getServiceBaseLinkBg(30, Integer.valueOf(link_id), 20,
					"no_null_image_path");// 背景图
			request.setAttribute("baseLinkBg", baseLinkBg);

		} else {
			baseLink20List = this.getServiceBaseLinkCityWithList(20, null, link_id, 10, "no_null_image_path");// 取后台编辑的楼层
			baseLink10List = this.getServiceBaseLinkCityWithList(10, null, link_id, 10, "no_null_image_path");// 取后台编辑的轮播图
			ServiceBaseLink baseLinkBg = this.getServiceBaseLinkBg(30, Integer.valueOf(link_id), 10,
					"no_null_image_path");// 背景图
			request.setAttribute("baseLinkBg", baseLinkBg);
		}

		if ((null != baseLink20List) && (baseLink20List.size() > 0)) {
			for (ServiceBaseLink temp : baseLink20List) {

				if (temp.getPre_number() == 1) {
					List<ServiceBaseLink> baseLink101List = this.getServiceBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 3, "no_null_image_path");
					temp.getMap().put("baseLink101List", baseLink101List);
				}
				if (temp.getPre_number() == 2) {
					List<ServiceBaseLink> baseLink201List = this.getServiceBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 1, "no_null_image_path");
					temp.getMap().put("baseLink201List", baseLink201List);

					List<ServiceBaseLink> baseLink202List = this.getServiceBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 2, 2, "no_null_image_path");
					temp.getMap().put("baseLink202List", baseLink202List);

					List<ServiceBaseLink> baseLink203List = this.getServiceBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 3, 1, "no_null_image_path");
					temp.getMap().put("baseLink203List", baseLink203List);
				}
				if (temp.getPre_number() == 3) {
					List<ServiceBaseLink> baseLink301List = this.getServiceBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 3, "no_null_image_path");
					temp.getMap().put("baseLink301List", baseLink301List);
				}
				if (temp.getPre_number() == 4) {
					List<ServiceBaseLink> baseLink401List = this.getServiceBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 4, "no_null_image_path");
					temp.getMap().put("baseLink401List", baseLink401List);

				}
				if (temp.getPre_number() == 5) {
					List<ServiceBaseLink> baseLink501List = this.getServiceBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 12, "no_null_image_path");
					temp.getMap().put("baseLink501List", baseLink501List);
				}

			}
		}
		request.setAttribute("baseLink20List", baseLink20List);
		request.setAttribute("baseLink10List", baseLink10List);

		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}

		return new ActionForward("/index/IndexTsg/list.jsp");

	}

	public void shareSubDomainSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = WebUtils.getCookie(request, Keys.COOKIE_USERINFO_KEY_JSESSIONID);
		logger.info("========cookie:{}", cookie);
		if (null != cookie) {
			String cookieValue = cookie.getValue();
			if (!StringUtils.equals(request.getSession().getId(), cookieValue)) {
				CookieGenerator cg1 = new CookieGenerator();
				cg1.setCookieMaxAge(1 * 24 * 60 * 60);
				cg1.setCookieName("JSESSIONID");
				cg1.addCookie(response, URLEncoder.encode(cookieValue, "UTF-8"));
			}
		}
	}

}
