package com.ebiz.webapp.web.struts.m;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;

/**
 * @author 王志雄
 * @date 2018年1月22日
 */
public class MCityCenterAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String index = (String) dynaBean.get("index");
		UserInfo ui = super.getUserInfoFromSession(request);
		// if (null == ui) {
		// String msg = "您还未登录，请先登录系统！";
		// return showTipNotLogin(mapping, form, request, response, msg);
		// }

		if (null != ui) {

			if (StringUtils.isNotBlank(index)) {
				return new ActionForward("/../m/MCityCenter/MCityCenter_index.jsp");
			}

			UserInfo entity = new UserInfo();
			entity.setId(ui.getId());
			entity = super.getFacade().getUserInfoService().getUserInfo(entity);

			if ("".equals(entity.getLink_area()) || "/m/MCityCenter.do".equals(entity.getLink_area())
					|| entity.getLink_area() == null) {
				request.setAttribute("link_area", 0);
				return new ActionForward("/../m/MCityCenter/MCityCenter_index.jsp");
			}

			request.setAttribute("link_area", 1);
			return new ActionForward("/../m" + entity.getLink_area());
		}
		return new ActionForward("/../m/MCityCenter/MCityCenter_index.jsp");

	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		UserInfo ui = super.getUserInfoFromSession(request);

		ServiceCenterInfo serviceCenterCount = new ServiceCenterInfo();
		if (StringUtils.isNotBlank(p_index)) {
			serviceCenterCount.getMap().put("p_index_like", p_index.substring(0, 4));
		}
		serviceCenterCount.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		serviceCenterCount.setIs_del(0);
		int list_count = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(serviceCenterCount);
		data.put("list_count", list_count);

		// 昨日增加数量
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(time);
		serviceCenterCount.getMap().put("st_add_date", date);
		serviceCenterCount.getMap().put("en_add_date", date);
		int yesterdayAddCount = getFacade().getServiceCenterInfoService().getServiceCenterInfoCount(serviceCenterCount);
		data.put("yesterdayAddCount", yesterdayAddCount);

		// 县市
		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setPar_index(new Long(p_index));
		baseProvince.setIs_del(0);
		List<BaseProvince> pIndexList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		if (null != pIndexList && pIndexList.size() > 0) {
			JSONArray p_index_list = new JSONArray();
			for (BaseProvince item : pIndexList) {
				JSONObject p_indexs = new JSONObject();
				p_indexs.put("value", item.getP_index().toString());
				p_indexs.put("text", item.getP_name());
				p_index_list.add(p_indexs);
			}
			data.put("p_index_list", p_index_list);
		}

		BaseProvince baseProvincePar = new BaseProvince();
		baseProvincePar.setP_index(new Long(p_index));
		baseProvincePar.setIs_del(0);
		baseProvincePar = getFacade().getBaseProvinceService().getBaseProvince(baseProvincePar);
		data.put("baseProvincePar", baseProvincePar);

		// 市管理员
		UserInfo manageUser = new UserInfo();
		manageUser.setP_index(Integer.valueOf(p_index));
		manageUser.setIs_del(0);
		manageUser.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
		manageUser = getFacade().getUserInfoService().getUserInfo(manageUser);
		data.put("manageUser", manageUser);

		// 分类
		if (manageUser != null) {
			MBaseLink mBaseLink = new MBaseLink();
			mBaseLink.setLink_type(600);
			mBaseLink.setIs_del(0);
			List<MBaseLink> mBaseLinkList = getFacade().getMBaseLinkService().getMBaseLinkList(mBaseLink);
			data.put("mBaseLinkList", mBaseLinkList);
		}

		if (null != manageUser) {
			// 市头条新闻
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setIs_del(0);
			newsInfo.setInfo_state(Keys.INFO_STATE.INFO_STATE_1.getIndex());
			newsInfo.setAdd_uid(manageUser.getId());
			newsInfo.setMod_id(Keys.mod_id.mod_id_shi.getIndex() + "");
			List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
			data.put("newsInfoList", newsInfoList);

			// 背景图
			BaseFiles baseFiles = new BaseFiles();
			baseFiles.setLink_id(manageUser.getId());
			baseFiles.setType(Keys.BaseFilesType.BASE_FILES_TYPE_CITY_BACKGROUND.getIndex());
			baseFiles = getFacade().getBaseFilesService().getBaseFiles(baseFiles);
			if (baseFiles != null) {
				data.put("banner", baseFiles.getSave_path());
			}
		}

		if (null != ui) {
			// 我加入的村
			myJoinVillageList(data, ui);
		}

		// 扶贫列表
		poorInfoList(data, dynaBean, StringUtils.substring(p_index, 0, 4));

		// 销售排行
		saleRankList(data, StringUtils.substring(p_index, 0, 6), 10);

		super.setAjaxDataPage(data, dynaBean, StringUtils.substring(p_index, 0, 4));

		// 查询村民数量
		VillageMember villageMember = new VillageMember();
		villageMember.setIs_del(0);
		villageMember.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		villageMember.getMap().put("country", StringUtils.substring(p_index, 0, 4));
		int villageMemberCount = super.getFacade().getVillageMemberService()
				.getVillageMemberCountByPIndex(villageMember);
		data.put("villageMemberCount", villageMemberCount);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getPoorAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String p_index = (String) dynaBean.get("p_index");
		log.info("==getPoorAjaxData=p_index:" + p_index);

		UserInfo ui = super.getUserInfoFromSession(request);
		//
		// ServiceCenterInfo entity = getServiceCenterInfo(id);
		// if (null == entity) {
		// msg = "合伙人不存在或审核未通过";
		// return returnAjaxData(response, code, msg, data);
		// }
		// data.put("entity", entity);

		// 扶贫列表
		poorInfoList(data, dynaBean, StringUtils.substring(p_index, 0, 4));

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		//
		// UserInfo ui = super.getUserInfoFromSession(request);
		// if (null == ui) {
		// code = "-1";
		// msg = "您还未登录，请先登录系统！";
		// return super.returnAjaxData(response, code, msg, data);
		// }

		super.setAjaxDataPage(data, dynaBean, StringUtils.substring(p_index, 0, 6));

		// UserInfo entity = new UserInfo();
		// entity.setId(ui.getId());
		// entity = super.getFacade().getUserInfoService().getUserInfo(entity);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward photo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "市相册");

		return new ActionForward("/../m/MCityCenter/MCityCenter_photo.jsp");
	}

	// 村子昨天加入的人员数量
	public int getVillageYesterdayAddCount(VillageMember member) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(time);
		member.getMap().put("st_add_date", date);
		member.getMap().put("en_add_date", date);
		return getFacade().getVillageMemberService().getVillageMemberCount(member);
	}

	public ActionForward getAjaxDataPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");

		if (StringUtils.isBlank(id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		Pager pager = (Pager) dynaBean.get("pager");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		BaseFiles entity = new BaseFiles();
		entity.setLink_id(Integer.valueOf(id));
		entity.setType(Keys.BaseFilesType.Base_Files_TYPE_CITY.getIndex());
		entity.setIs_del(0);
		Integer recordCount = getFacade().getBaseFilesService().getBaseFilesCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<BaseFiles> entityList = getFacade().getBaseFilesService().getBaseFilesPaginatedList(entity);

		data.put("entityList", entityList);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward openTown(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		if (StringUtils.isBlank(p_index)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setP_index(Integer.valueOf(p_index));
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
		if (null == entity) {
			code = "-1";
			msg = "很抱歉，该区域暂未开通合伙人！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		data.put("entity", entity);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward setHomePage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "-1";
			msg = "您还未登录，请先登录系统！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		String link_area = (String) dynaBean.get("link_area");
		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		if ("/m/MCityCenter.do".equals(link_area) || "0".equals(link_area)) {
			entity.setLink_area(link_area);
			int i = super.getFacade().getUserInfoService().modifyUserInfo(entity);
			if (i > 0) {
				msg = "取消首页成功";
				super.ajaxReturnInfo(response, code, msg, data);
				return null;
			} else {
				msg = "取消首页失败";
				super.ajaxReturnInfo(response, code, msg, data);
				return null;
			}
		} else {
			entity.setLink_area(link_area);
			int i = super.getFacade().getUserInfoService().modifyUserInfo(entity);
			if (i > 0) {
				msg = "设为首页成功";
				super.ajaxReturnInfo(response, code, msg, data);
				return null;
			} else {
				msg = "设为首页失败";
				super.ajaxReturnInfo(response, code, msg, data);
				return null;
			}
		}
	}

}
