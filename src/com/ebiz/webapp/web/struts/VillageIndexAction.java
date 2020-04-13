package com.ebiz.webapp.web.struts;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicComment;
import com.ebiz.webapp.domain.VillageDynamicRecord;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;

public class VillageIndexAction extends BaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.countryIndex(mapping, form, request, response);
	}

	public ActionForward countryIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取最上面的导航条
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, null, null);
		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}

		request.setAttribute("base3000LinkList", base3000LinkList);
		return new ActionForward("/index/VillageIndex/country_index.jsp");
	}

	public ActionForward serviceIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		// 取最上面的导航条
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, null, null);

		request.setAttribute("base3000LinkList", base3000LinkList);
		return new ActionForward("/index/VillageIndex/service_index.jsp");
	}

	public ActionForward villageIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 取最上面的导航条
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, null, null);

		request.setAttribute("base3000LinkList", base3000LinkList);
		return new ActionForward("/index/VillageIndex/village_index.jsp");
	}

	public ActionForward myIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 取最上面的导航条
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, null, null);

		request.setAttribute("base3000LinkList", base3000LinkList);
		return new ActionForward("/index/VillageIndex/my_index.jsp");
	}

	public ActionForward getDynamicList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		if (null != ui) {
			dynaBean.set("user_id", ui.getId().toString());
		}
		super.setAjaxDataPage(data, dynaBean, StringUtils.substring(p_index, 0, 4));
		code = "1";
		data.put("code", code);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getMemberDynamicList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		if (null != ui) {
			dynaBean.set("user_id", ui.getId().toString());
		}

		this.getAjaxDataPage(data, dynaBean, null);

		code = "1";
		data.put("code", code);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getServiceDynamicList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");
		if (null != ui) {
			dynaBean.set("user_id", ui.getId().toString());
		}
		super.setAjaxDataPage(data, dynaBean, StringUtils.substring(p_index, 0, 6));
		code = "1";
		data.put("code", code);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getVillageDynamicList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		if (null != ui) {
			dynaBean.set("user_id", ui.getId().toString());
		}
		this.getAjaxDataPage(data, dynaBean, null);
		code = "1";
		data.put("code", code);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getPoorCommList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		String member_id = (String) dynaBean.get("member_id");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		if (StringUtils.isBlank(member_id)) {
			msg = "参数有误！";
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo memberUser = super.getUserInfo(member_id);
		if (memberUser == null) {
			msg = "该成员不存在！";
			data.put("code", code);
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		if (memberUser.getIs_poor() == 1 && memberUser.getPoor_id() != null) {
			CommInfoPoors cPoors = new CommInfoPoors();
			cPoors.setPoor_id(memberUser.getPoor_id());

			Integer recordCount = getFacade().getCommInfoPoorsService().getCommInfoPoorsCount(cPoors);

			pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
			cPoors.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
			cPoors.getRow().setCount(Integer.valueOf(pageSize));
			List<CommInfoPoors> poorCommList = getFacade().getCommInfoPoorsService().getCommInfoPoorsPaginatedList(
					cPoors);
			data.put("poorCommList", poorCommList);
			code = "1";
		}

		data.put("code", code);
		data.put("msg", msg);
		super.renderJson(response, data.toString());
		return null;
	}

	public void getAjaxDataPage(JSONObject data, DynaBean dynaBean, String p_index) {

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		String user_id = (String) dynaBean.get("user_id");
		String village_id = (String) dynaBean.get("village_id");
		String member_id = (String) dynaBean.get("member_id");
		String comment_show_count = (String) dynaBean.get("comment_show_count");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		VillageDynamic villageDynamic = new VillageDynamic();
		if (StringUtils.isNotBlank(village_id)) {
			villageDynamic.setVillage_id(Integer.valueOf(village_id));
			villageDynamic.getMap().put("order_by_id_desc", "true");
		}
		if (StringUtils.isNotBlank(member_id)) {
			villageDynamic.setAdd_user_id(Integer.valueOf(member_id));
		}
		villageDynamic.setIs_del(0);
		// villageDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
		villageDynamic.setAudit_state(1);
		villageDynamic.setIs_public(1);// 公开

		Integer recordCount = getFacade().getVillageDynamicService().getVillageDynamicCount(villageDynamic);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		villageDynamic.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		villageDynamic.getRow().setCount(Integer.valueOf(pageSize));

		List<VillageDynamic> villageDynamicList = getFacade().getVillageDynamicService()
				.getVillageDynamicPaginatedList(villageDynamic);
		log.info("===villageDynamicList:" + villageDynamicList.size());
		if (null != villageDynamicList && villageDynamicList.size() > 0) {
			for (VillageDynamic item : villageDynamicList) {
				this.setMapDynamicImgs(item);// 查询图片
				if (item.getType().intValue() == Keys.DynamicType.dynamic_type_2.getIndex()) {
					CommInfo commInfo = new CommInfo();
					commInfo.setId(item.getComm_id());
					commInfo.setIs_del(0);
					commInfo.setIs_sell(1);
					commInfo.setIs_has_tc(1);
					commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
					log.info("===commInfo");
					commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
					if (null != commInfo) {
						CommTczhPrice tc = new CommTczhPrice();
						tc.setComm_id(commInfo.getId().toString());
						log.info("===CommTczhPrice");
						tc = getFacade().getCommTczhPriceService().getCommTczhPrice(tc);
						item.getMap().put("commInfoTczh", tc);
					}
					item.getMap().put("commInfo", commInfo);

				}
				// 评论列表
				if (StringUtils.isNotBlank(comment_show_count)) {
					List<VillageDynamicComment> commentList = this.getVillageDynamicCommentList(item.getId(), null,
							Integer.valueOf(comment_show_count), "true");
					item.getMap().put("commentList", commentList);

				} else {
					List<VillageDynamicComment> commentList = this.getVillageDynamicCommentList(item.getId(), null,
							null, "true");
					item.getMap().put("commentList", commentList);
				}

				// 用户动态点在数
				int zan_count = isDynamicUserZan(null, item, Keys.CommentType.COMMENT_TYPE_3.getIndex());
				item.getMap().put("zan_count", zan_count);

				// 用户是否点赞该条动态
				if (StringUtils.isNotBlank(user_id)) {
					int count = isDynamicUserZan(Integer.valueOf(user_id), item,
							Keys.CommentType.COMMENT_TYPE_3.getIndex());
					if (count > 0) {
						item.getMap().put("is_zan", 1);
					} else {
						item.getMap().put("is_zan", 0);
					}
				} else {
					item.getMap().put("is_zan", 0);
				}
				// 点赞用户列表
				String zanNameList = "";
				List<VillageDynamicComment> zanList = this.getVillageDynamicCommentList(item.getId(),
						Keys.CommentType.COMMENT_TYPE_3.getIndex(), 5, null);
				if (null != zanList && zanList.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (VillageDynamicComment zan : zanList) {
						sb.append(zan.getAdd_user_name()).append("、");
					}
					zanNameList = sb.toString();
					if (zanNameList.length() > 0) {
						zanNameList = zanNameList.substring(0, zanNameList.lastIndexOf("、"));
					}
				}
				item.getMap().put("zanList", zanList);
				item.getMap().put("zanNameList", zanNameList);
			}
		}
		data.put("villageDynamicList", villageDynamicList);
	}

	public ActionForward getCountryInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		UserInfo ui = super.getUserInfoFromSession(request);
		data.put("user", ui);
		if (null == ui) {
			data.put("user", "");
		}

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);

		if (StringUtils.isBlank(p_index) && null != current_p_index) {
			p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}

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

		// 市管理员
		UserInfo manageUser = new UserInfo();
		manageUser.setP_index(Integer.valueOf(p_index));
		manageUser.setIs_del(0);
		manageUser.setUser_type(Keys.UserType.USER_TYPE_19.getIndex());
		manageUser = getFacade().getUserInfoService().getUserInfo(manageUser);
		data.put("manageUser", manageUser);

		if (null != manageUser) {
			// 市头条新闻
			NewsInfo newsInfo = new NewsInfo();
			newsInfo.setIs_del(0);
			newsInfo.setInfo_state(Keys.INFO_STATE.INFO_STATE_1.getIndex());
			newsInfo.setAdd_uid(manageUser.getId());
			newsInfo.setMod_id(Keys.mod_id.mod_id_shi.getIndex() + "");
			List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
			data.put("newsInfoList", newsInfoList);

			// 市头条新闻轮播图
			newsInfo.getMap().put("image_path", true);
			List<NewsInfo> nInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
			data.put("newsImgList", nInfoList);

			// 背景图
			BaseFiles a = new BaseFiles();
			a.setLink_id(manageUser.getId());
			a.setType(Keys.BaseFilesType.BASE_FILES_TYPE_CITY_BACKGROUND.getIndex());
			a = getFacade().getBaseFilesService().getBaseFiles(a);
			if (a != null) {
				data.put("banner", a.getSave_path());
			}
		}
		data.put("mod_code", Keys.mod_id.mod_id_shi.getIndex());
		data.put("par_code", "1600100100");

		// 销售排行
		saleRankList(data, StringUtils.substring(p_index, 0, 4), 10);

		// 分类
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		BaseData baseData = new BaseData();
		baseData.setType(Keys.BaseDataType.Base_Data_type_1123.getIndex());
		baseData.getMap().put("pre_number2_like", StringUtils.substring(p_index, 0, 4));
		baseData.getMap().put("distinct_type_name", "true");
		baseData.setIs_del(0);
		System.out.println("====base");
		baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		data.put("baseDataList", baseDataList);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward serviceList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/service_list.jsp");
	}

	public ActionForward countryPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/country_photo.jsp");
	}

	public ActionForward servicePhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/service_photo.jsp");
	}

	public ActionForward xianQing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/xian_qing.jsp");
	}

	public ActionForward villagePhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/village_photo.jsp");
	}

	public ActionForward cunQing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/cun_qing.jsp");
	}

	public ActionForward villageList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/village_list.jsp");
	}

	public ActionForward memberList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/member_list.jsp");
	}

	public ActionForward getServiceInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		UserInfo ui = super.getUserInfoFromSession(request);
		data.put("user", ui);
		if (null == ui) {
			data.put("user", "");
		}
		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);

		if (StringUtils.isBlank(p_index) && null != current_p_index) {
			p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}

		ServiceCenterInfo serviceCenter = new ServiceCenterInfo();
		serviceCenter.setP_index(Integer.valueOf(p_index));
		serviceCenter.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		serviceCenter.setIs_del(0);
		serviceCenter = getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenter);

		if (null == serviceCenter) {
			msg = "合伙人不存在或审核未通过";
			return returnAjaxData(response, code, msg, data);
		}
		data.put("entity", serviceCenter);

		VillageInfo villageInfo = new VillageInfo();
		villageInfo.getMap().put("p_index_like", p_index);
		villageInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		villageInfo.setIs_del(0);
		int villageCount = getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
		data.put("list_count", villageCount);

		// 昨日增加数量
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(time);
		villageInfo.getMap().put("st_add_date", date);
		villageInfo.getMap().put("en_add_date", date);
		int yesterdayAddCount = getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
		data.put("yesterdayAddCount", yesterdayAddCount);

		// 县市
		BaseProvince basePindex = new BaseProvince();
		basePindex.setPar_index(new Long(p_index));
		basePindex.setIs_del(0);
		List<BaseProvince> pIndexList = getFacade().getBaseProvinceService().getBaseProvinceList(basePindex);
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

		// 县管理员
		UserInfo manageUser = super.getUserInfo(serviceCenter.getAdd_user_id());
		data.put("manageUser", manageUser);

		// 县头条
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setInfo_state(Keys.INFO_STATE.INFO_STATE_1.getIndex());
		newsInfo.setLink_id(serviceCenter.getId());
		newsInfo.setMod_id(Keys.mod_id.mod_id_xian.getIndex() + "");
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
		data.put("newsInfoList", newsInfoList);
		data.put("mod_code", Keys.mod_id.mod_id_xian.getIndex());
		data.put("par_code", 1500501000);

		// 县头条新闻轮播图
		newsInfo.getMap().put("image_path", true);
		List<NewsInfo> nInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
		data.put("newsImgList", nInfoList);

		// 销售排行
		saleRankList(data, StringUtils.substring(p_index, 0, 6), 10);

		// 分类
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		BaseData baseData = new BaseData();
		baseData.setType(Keys.BaseDataType.Base_Data_type_1123.getIndex());
		baseData.getMap().put("pre_number2_like", StringUtils.substring(p_index, 0, 4));
		baseData.getMap().put("distinct_type_name", "true");
		baseData.setIs_del(0);
		System.out.println("====base");
		baseDataList = getFacade().getBaseDataService().getBaseDataList(baseData);
		data.put("baseDataList", baseDataList);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getVillageInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);
		data.put("user", ui);
		if (null == ui) {
			data.put("user", "");
		}

		VillageInfo villageInfo = getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == villageInfo) {
			msg = "村子已删除或者审核未通过！";
			super.returnInfo(response, code, msg, data);
			return null;
		}
		data.put("villageInfo", villageInfo);

		if (null != ui) {
			// 是否加入村子
			VillageMember applyVillageInfo = getApplyVillage(villageInfo.getId(), ui.getId());
			// if (null == applyVillageInfo) {
			// code = "-2";
			// msg = "请先加入村子";
			// return super.returnAjaxData(response, code, msg, data);
			// }
			data.put("applyVillageInfo", applyVillageInfo);

			applyVillageInfo = super.getApplyVillage(villageInfo.getId(), ui.getId(),
					Keys.audit_state.audit_state_0.getIndex());
		}
		// 村管理员
		UserInfo villageManageUser = getVillageManageUser(villageInfo);
		data.put("villageManageUser", villageManageUser);

		// 村名数量
		VillageMember member = new VillageMember();
		member.setVillage_id(villageInfo.getId());
		member.setIs_del(0);
		int memberCount = getFacade().getVillageMemberService().getVillageMemberCount(member);
		data.put("villageMemberCount", memberCount);

		// 昨日增加数量
		int yesterdayAddCount = getVillageYesterdayAddCount(member);
		data.put("yesterdayAddCount", yesterdayAddCount);

		// 村头条
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setInfo_state(Keys.INFO_STATE.INFO_STATE_1.getIndex());
		newsInfo.setLink_id(villageInfo.getId());
		newsInfo.setMod_id(Keys.mod_id.mod_id_cun.getIndex() + "");
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
		data.put("newsInfoList", newsInfoList);
		data.put("mod_code", Keys.mod_id.mod_id_cun.getIndex());
		data.put("par_code", 1500303000);

		// 村头条新闻轮播图
		newsInfo.getMap().put("image_path", true);
		List<NewsInfo> nInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
		data.put("newsImgList", nInfoList);

		List<VillageDynamicRecord> villageDynamicRecordList = new ArrayList<VillageDynamicRecord>();
		data.put("villageDynamicRecordList", villageDynamicRecordList);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getMemberInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String village_id = (String) dynaBean.get("village_id");
		String type = (String) dynaBean.get("type");

		UserInfo ui = super.getUserInfoFromSession(request);
		data.put("ui", ui);
		if (null == ui) {
			data.put("ui", "");
		}

		if (StringUtils.isBlank(user_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserInfo linkUser = super.getUserInfo(user_id);
		if (null == linkUser) {
			msg = "用户不存在！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		data.put("user", linkUser);

		if (null != ui && StringUtils.isNotBlank(village_id)) {
			// 是否加入村子
			VillageMember applyVillageInfo = getApplyVillage(Integer.valueOf(village_id), ui.getId());
			// if (null == applyVillageInfo) {
			// code = "-2";
			// msg = "请先加入村子";
			// return super.returnAjaxData(response, code, msg, data);
			// }
			data.put("applyVillageInfo", applyVillageInfo);
		}

		if (linkUser.getIs_poor() == 1 && null != linkUser.getPoor_id()) {
			PoorInfo poorInfo = new PoorInfo();
			poorInfo.setId(linkUser.getPoor_id());
			// poorInfo.setMobile(linkUser.getMobile());
			poorInfo.setIs_del(0);
			poorInfo = getFacade().getPoorInfoService().getPoorInfo(poorInfo);
			data.put("poorInfo", poorInfo);
		}

		// 是否关注用户
		if (ui != null) {
			int is_guanzhu_cout = isGuanzhu(linkUser.getId(), ui.getId());
			data.put("is_guanzhu_cout", is_guanzhu_cout);
			if (is_guanzhu_cout > 0) {
				// 判断是否互关
				int is_huguan = isGuanzhu(ui.getId(), linkUser.getId());
				data.put("is_huguan", is_huguan);
			}
		}

		// 获赞数量
		int zan_count = getUserDynamicCommentCount(linkUser, Keys.CommentType.COMMENT_TYPE_3.getIndex(), null);
		data.put("zan_count", zan_count);
		// 关注数量
		int guanzhu_count = getGuanZhuCount(linkUser);
		data.put("guanzhu_count", guanzhu_count);
		// 粉丝数量
		int fensi_count = getFenSiCount(linkUser);
		data.put("fensi_count", fensi_count);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public int getFenSiCount(UserInfo userInfo) {
		VillageContactList a = new VillageContactList();
		a.setContact_user_id(userInfo.getId());
		a.setIs_del(0);
		int fensi_count = getFacade().getVillageContactListService().getVillageContactListCount(a);
		return fensi_count;
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

	public int getUserDynamicCommentCount(UserInfo userInfo, Integer type, String map_types) {
		VillageDynamicComment entity = new VillageDynamicComment();
		entity.setLink_user_id(userInfo.getId());
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		if (null != type) {
			entity.setComment_type(type);
		}
		if (null != map_types) {
			entity.getMap().put("comment_type_in", map_types);
		}
		entity.setIs_del(0);
		int count = getFacade().getVillageDynamicCommentService().getVillageDynamicCommentCount(entity);
		return count;
	}

	// 我加入的村
	public ActionForward myJoinVillageList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);
		if (ui == null) {
			msg = "请先登录！";
			super.renderJson(response, data.toString());
			return null;
		}
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "6";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		VillageMember myJoinVillage = new VillageMember();
		myJoinVillage.setAdd_user_id(ui.getId());
		myJoinVillage.getMap().put("audit_state_in", "1,2");
		myJoinVillage.setIs_del(0);
		Integer recordCount = getFacade().getVillageMemberService().getVillageMemberCount(myJoinVillage);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		myJoinVillage.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		myJoinVillage.getRow().setCount(Integer.valueOf(pageSize));
		List<VillageMember> myJoinVillageList = getFacade().getVillageMemberService().getVillageMemberPaginatedList(
				myJoinVillage);
		data.put("myJoinVillageList", myJoinVillageList);

		super.renderJson(response, data.toString());
		return null;
	}

	// 爱心扶贫
	public ActionForward poorList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		String p_index = (String) dynaBean.get("p_index");
		String city = (String) dynaBean.get("city");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "6";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		if (StringUtils.isBlank(p_index)) {
			msg = "参数错误！";
			super.renderJson(response, data.toString());
			return null;
		}
		PoorInfo poorInfo = new PoorInfo();
		if (StringUtils.isNotBlank(city)) {
			poorInfo.getMap().put("p_index_like", p_index.substring(0, 4));
		} else {
			poorInfo.getMap().put("p_index_like", p_index.substring(0, 6));
		}

		poorInfo.setIs_del(0);
		poorInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(poorInfo);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		poorInfo.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		poorInfo.getRow().setCount(Integer.valueOf(pageSize));
		List<PoorInfo> poorInfoList = getFacade().getPoorInfoService().getPoorInfoPaginatedList(poorInfo);
		data.put("poorInfoList", poorInfoList);

		super.renderJson(response, data.toString());
		return null;
	}

	// 村爱心扶贫
	public ActionForward villagePoorList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();
		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		String p_index = (String) dynaBean.get("p_index");
		String village_id = (String) dynaBean.get("village_id");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "6";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		if (StringUtils.isBlank(village_id)) {
			msg = "参数错误！";
			super.renderJson(response, data.toString());
			return null;
		}
		VillageInfo village = super.getVillageInfo(Integer.valueOf(village_id));
		if (village == null) {
			msg = "该村站不存在！";
			super.renderJson(response, data.toString());
			return null;
		}

		PoorInfo poorInfo = new PoorInfo();
		poorInfo.setP_index(village.getP_index());
		poorInfo.setIs_del(0);
		poorInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		Integer recordCount = getFacade().getPoorInfoService().getPoorInfoCount(poorInfo);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		poorInfo.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		poorInfo.getRow().setCount(Integer.valueOf(pageSize));
		List<PoorInfo> poorInfoList = getFacade().getPoorInfoService().getPoorInfoPaginatedList(poorInfo);
		data.put("poorInfoList", poorInfoList);

		super.renderJson(response, data.toString());
		return null;
	}

	// 个人用户与我相关
	public ActionForward aboutMe(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			msg = "参数不正确";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");

		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		Integer pageSize = 5;

		if (null != ui) {
			VillageDynamicRecord villageDynamicRecord = new VillageDynamicRecord();
			villageDynamicRecord.setTo_user_id(ui.getId());
			villageDynamicRecord.setIs_del(0);
			villageDynamicRecord.setAdd_user_id(Integer.valueOf(id));

			Integer recordCount = getFacade().getVillageDynamicRecordService().getVillageDynamicRecordCount(
					villageDynamicRecord);

			pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
			villageDynamicRecord.getRow().setFirst(Integer.valueOf(startPage) * pageSize);
			villageDynamicRecord.getRow().setCount(pager.getRowCount());
			villageDynamicRecord.getRow().setCount(pageSize);

			List<VillageDynamicRecord> villageDynamicRecordList = getFacade().getVillageDynamicRecordService()
					.getVillageDynamicRecordPaginatedList(villageDynamicRecord);

			data.put("villageDynamicRecordList", villageDynamicRecordList);
		}

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward share(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ActionForward("/index/VillageIndex/share.jsp");
	}

	public ActionForward delDynamic(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "删除不成功！";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (ui == null) {
			msg = "请先登录！";
			data.put("msg", msg);
			data.put("code", code);
			super.renderJson(response, data.toString());
			return null;
		}

		VillageDynamic villageDynamic = new VillageDynamic();
		villageDynamic.setId(Integer.valueOf(id));
		villageDynamic.setIs_del(1);
		villageDynamic.setDel_date(new Date());
		villageDynamic.setDel_user_id(ui.getId());
		villageDynamic.setDel_user_name(ui.getUser_name());
		super.getFacade().getVillageDynamicService().modifyVillageDynamic(villageDynamic);

		code = "1";
		msg = "动态已删除！";
		data.put("msg", msg);
		data.put("code", code);
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getShareInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String member_id = (String) dynaBean.get("member_id");
		String dynamic_id = (String) dynaBean.get("dynamic_id");
		if (StringUtils.isBlank(dynamic_id)) {
			msg = "动态参数错误！";
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}

		VillageDynamic villageDynamic = new VillageDynamic();
		villageDynamic.setId(Integer.valueOf(dynamic_id));
		villageDynamic.setIs_del(0);
		villageDynamic.setAudit_state(1);
		villageDynamic.setAdd_user_id(Integer.valueOf(member_id));
		List<VillageDynamic> villageDynamicList = getFacade().getVillageDynamicService().getVillageDynamicList(
				villageDynamic);
		if (villageDynamicList == null || villageDynamicList.size() <= 0) {
			msg = "动态不存在！";
			data.put("msg", msg);
			super.renderJson(response, data.toString());
			return null;
		}
		for (VillageDynamic item : villageDynamicList) {
			this.setMapDynamicImgs(item);// 查询图片
			if (item.getType().intValue() == Keys.DynamicType.dynamic_type_2.getIndex()) {
				CommInfo commInfo = new CommInfo();
				commInfo.setId(item.getComm_id());
				commInfo.setIs_del(0);
				commInfo.setIs_sell(1);
				commInfo.setIs_has_tc(1);
				commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
				log.info("===commInfo");
				commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
				if (null != commInfo) {
					CommTczhPrice tc = new CommTczhPrice();
					tc.setComm_id(commInfo.getId().toString());
					log.info("===CommTczhPrice");
					tc = getFacade().getCommTczhPriceService().getCommTczhPrice(tc);
					item.getMap().put("commInfoTczh", tc);
				}
				item.getMap().put("commInfo", commInfo);

			}
			// 评论列表
			List<VillageDynamicComment> commentList = this.getVillageDynamicCommentList(item.getId(), null, null,
					"true");
			item.getMap().put("commentList", commentList);

			// 用户动态点在数
			int zan_count = isDynamicUserZan(null, item, Keys.CommentType.COMMENT_TYPE_3.getIndex());
			item.getMap().put("zan_count", zan_count);

			// 用户是否点赞该条动态
			if (ui != null) {
				int count = isDynamicUserZan(ui.getId(), item, Keys.CommentType.COMMENT_TYPE_3.getIndex());
				if (count > 0) {
					item.getMap().put("is_zan", 1);
				} else {
					item.getMap().put("is_zan", 0);
				}
			} else {
				item.getMap().put("is_zan", 0);
			}
			// 点赞用户列表
			String zanNameList = "";
			List<VillageDynamicComment> zanList = this.getVillageDynamicCommentList(item.getId(),
					Keys.CommentType.COMMENT_TYPE_3.getIndex(), 5, null);
			if (null != zanList && zanList.size() > 0) {
				StringBuffer sb = new StringBuffer();
				for (VillageDynamicComment zan : zanList) {
					sb.append(zan.getAdd_user_name()).append("、");
				}
				zanNameList = sb.toString();
				if (zanNameList.length() > 0) {
					zanNameList = zanNameList.substring(0, zanNameList.lastIndexOf("、"));
				}
			}
			item.getMap().put("zanList", zanList);
			item.getMap().put("zanNameList", zanNameList);
		}

		code = "1";
		data.put("villageDynamicList", villageDynamicList);
		data.put("code", code);
		super.renderJson(response, data.toString());
		return null;
	}

	// 本县特产
	public ActionForward teChanList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code = "0", msg = "";
		JSONObject data = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		// String add_user_id = (String) dynaBean.get("add_user_id");
		String p_index = (String) dynaBean.get("p_index");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "2";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		if (StringUtils.isBlank(p_index)) {
			msg = "参数错误！";
			super.renderJson(response, data.toString());
			return null;
		}
		ServiceCenterInfo serviceCenter = new ServiceCenterInfo();
		serviceCenter.setP_index(Integer.valueOf(p_index));
		serviceCenter.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		serviceCenter.setIs_del(0);
		serviceCenter = getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenter);

		MBaseLink mBaseLink = new MBaseLink();
		mBaseLink.setLink_type(100);
		mBaseLink.setIs_del(0);
		mBaseLink.setLink_id(serviceCenter.getAdd_user_id());
		Integer recordCount = getFacade().getMBaseLinkService().getMBaseLinkCount(mBaseLink);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		mBaseLink.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		mBaseLink.getRow().setCount(Integer.valueOf(pageSize));
		List<MBaseLink> teChanInfoList = getFacade().getMBaseLinkService().getMBaseLinkPaginatedList(mBaseLink);
		data.put("teChanInfoList", teChanInfoList);
		super.renderJson(response, data.toString());
		return null;
	}
}
