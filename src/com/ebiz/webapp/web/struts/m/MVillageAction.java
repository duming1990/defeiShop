package com.ebiz.webapp.web.struts.m;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicRecord;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;

/**
 * @author 王志雄
 * @date 2018年1月22日
 */
public class MVillageAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// UserInfo ui = super.getUserInfoFromSession(request);
		// if (null == ui) {
		// String msg = "您还未登录，请先登录系统！";
		// return showTipNotLogin(mapping, form, request, response, msg);
		// }

		return new ActionForward("/../m/MVillage/MVillage_list.jsp");
	}

	public ActionForward getAjaxDataList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String village_name_like = (String) dynaBean.get("village_name_like");
		String p_index = (String) dynaBean.get("p_index");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserInfo ui = super.getUserInfoFromSession(request);

		VillageInfo villageInfo = new VillageInfo();
		villageInfo.getMap().put("p_index_like", p_index);
		villageInfo.getMap().put("village_name_like", village_name_like);
		if (StringUtils.isNotBlank(p_index) && p_index.length() == 12) {
			villageInfo.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 9));
		}

		villageInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		villageInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());

		Integer recordCount = getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		villageInfo.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		villageInfo.getRow().setCount(pager.getRowCount());

		List<VillageInfo> list = getFacade().getVillageInfoService().getVillageInfoPaginatedList(villageInfo);
		if (null != list && list.size() > 0) {
			for (VillageInfo item : list) {
				// 判断是否加入村子
				if (ui != null) {
					VillageMember applyVillageInfo = getApplyVillage(item.getId(), ui.getId());
					if (null != applyVillageInfo) {
						if (applyVillageInfo.getAudit_state() == 1) {
							item.getMap().put("is_apply", 1);
						} else if (applyVillageInfo.getAudit_state() == 0) {
							item.getMap().put("is_apply", 0);
						} else {
							item.getMap().put("is_apply", -1);
						}
					}
				}
			}
		}
		data.put("villageInfoList", list);
		super.returnInfo(response, code, msg, data);
		return null;

	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (super.isWeixin(request)) {
			super.setJsApiTicketRetrunParamToSession(request);
		}

		// UserInfo ui = super.getUserInfoFromSession(request);
		// if (null == ui) {
		// String msg = "您还未登录，请先登录系统！";
		// return showTipNotLogin(mapping, form, request, response, msg);
		// }

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (GenericValidator.isInt(id)) {
			VillageInfo entity = new VillageInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getVillageInfoService().getVillageInfo(entity);
			if (null != entity) {
				String logo = "styles/imagesPublic/user_header.png";
				if (null != entity.getVillage_logo()) {
					logo = entity.getVillage_logo();
				}
				request.setAttribute("logo", logo);
			}
		}

		request.setAttribute("ShareVillagePortalDesc", super.getSysSetting(Keys.ShareVillagePortalDesc));

		return new ActionForward("/../m/MVillage/MVillage_index.jsp");
	}

	public ActionForward photo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "村相册");

		return new ActionForward("/../m/MVillage/MVillage_photo.jsp");
	}

	public ActionForward cunqing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		return new ActionForward("/../m/MVillage/MVillage_cunqing.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		VillageInfo villageInfo = getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == villageInfo) {
			msg = "村子已删除或者审核未通过！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		data.put("villageInfo", villageInfo);
		data.put("isApp", super.isApp(request));

		// 查询该村属于哪个县域
		if (null != villageInfo.getP_index()) {
			ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
			serviceCenterInfo.setP_index(Integer.valueOf(StringUtils.substring(villageInfo.getP_index().toString(), 0,
					6)));
			serviceCenterInfo.setIs_del(0);
			serviceCenterInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
			serviceCenterInfo = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(serviceCenterInfo);
			data.put("serviceCenterInfo", serviceCenterInfo);
		}

		// 村管理员
		UserInfo villageManageUser = getVillageManageUser(villageInfo);
		data.put("villageManageUser", villageManageUser);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			data.put("ui", ui);
			// 是否加入村子
			VillageMember applyVillageInfo = getApplyVillage(villageInfo.getId(), ui.getId());
			data.put("applyVillageInfo", applyVillageInfo);

			applyVillageInfo = super.getApplyVillage(villageInfo.getId(), ui.getId(),
					Keys.audit_state.audit_state_0.getIndex());

			// 村名数量
			VillageMember member = new VillageMember();
			member.setVillage_id(villageInfo.getId());
			member.setIs_del(0);
			int memberCount = getFacade().getVillageMemberService().getVillageMemberCount(member);
			data.put("villageMemberCount", memberCount);

			// 下级会员
			UserRelationPar userRelationPar = new UserRelationPar();
			userRelationPar.getMap().put("my_lower_by_user_par_id", ui.getId());
			int userRelationParCount = getFacade().getUserRelationParService().getUserRelationParCount(userRelationPar);
			data.put("userRelationParCount", userRelationParCount);

			// 是否关注村管理员
			data.put("guanzhu_count", isGuanzhu(villageManageUser.getId(), ui.getId()));
			// 昨日增加数量
			int yesterdayAddCount = getVillageYesterdayAddCount(member);
			data.put("yesterdayAddCount", yesterdayAddCount);
		}
		// 村头条
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setInfo_state(Keys.INFO_STATE.INFO_STATE_1.getIndex());
		newsInfo.setLink_id(villageInfo.getId());
		newsInfo.setMod_id(Keys.mod_id.mod_id_cun.getIndex() + "");
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
		data.put("newsInfoList", newsInfoList);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);

		code = setAjaxDataPage(data, dynaBean, Integer.valueOf(id), ui);

		super.returnInfo(response, code, msg, data);
		return null;
	}

	public String setAjaxDataPage(JSONObject data, DynaBean dynaBean, Integer village_id, UserInfo ui) {

		String code = "1";
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		// 村动态
		VillageDynamic villageDynamic = new VillageDynamic();
		villageDynamic.setVillage_id(village_id);
		villageDynamic.setIs_del(0);
		villageDynamic.setAudit_state(1);
		villageDynamic.setIs_public(1);// 公开
		villageDynamic.setType(Keys.DynamicType.dynamic_type_1.getIndex());

		Integer recordCount = getFacade().getVillageDynamicService().getVillageDynamicCount(villageDynamic);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		villageDynamic.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		// villageDynamic.getRow().setCount(3);
		villageDynamic.getRow().setCount(pager.getRowCount());

		List<VillageDynamic> villageDynamicList = getFacade().getVillageDynamicService()
				.getVillageDynamicPaginatedList(villageDynamic);
		super.setVillageDynamic(ui, villageDynamicList);
		data.put("villageDynamicList", villageDynamicList);

		if (villageDynamicList.size() < Integer.valueOf(pageSize)) {
			code = "2";
		}
		return code;
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

	public ActionForward getAjaxDataComm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String cls_id = (String) dynaBean.get("cls_id");
		VillageInfo villageInfo = getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == villageInfo) {
			msg = "村子已删除或者审核未通过！";
			return super.returnAjaxData(response, code, msg, data);
		}
		data.put("villageInfo", villageInfo);

		UserInfo ui = super.getUserInfoFromSession(request);

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");

		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		Integer pageSize = 10;
		Integer newPageSize = 10;
		Integer count = 3;
		if (null != newPageSize) {
			pageSize = newPageSize;
		}

		// 村子商品分类 commClassDataList
		super.setVillageCommClass(data, villageInfo.getId().toString());

		// 村动态
		VillageDynamic villageDynamic = new VillageDynamic();
		villageDynamic.setVillage_id(villageInfo.getId());
		villageDynamic.setIs_del(0);
		villageDynamic.setAudit_state(1);
		villageDynamic.setIs_public(1);
		villageDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
		villageDynamic.getMap().put("left_join_comm_info", true);
		villageDynamic.getMap().put("not_out_sell_time", true);
		if (GenericValidator.isInt(cls_id) && Integer.valueOf(cls_id) != 0) {
			villageDynamic.setCls_id(Integer.valueOf(cls_id));
		}

		Integer recordCount = getFacade().getVillageDynamicService().getVillageDynamicCount(villageDynamic);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		if (null != startPage) {
			villageDynamic.getRow().setFirst(Integer.valueOf(startPage) * count);
		} else {
			villageDynamic.getRow().setFirst(pager.getFirstRow());
		}
		villageDynamic.getRow().setCount(pager.getRowCount());
		if (null != count) {
			villageDynamic.getRow().setCount(count);
		}

		List<VillageDynamic> villageDynamicList = getFacade().getVillageDynamicService()
				.getVillageDynamicPaginatedList(villageDynamic);
		super.setVillageDynamic(ui, villageDynamicList);
		data.put("villageDynamicCommList", villageDynamicList);

		code = "1";
		if (villageDynamicList.size() < Integer.valueOf(pageSize)) {
			code = "2";
		}
		return super.returnAjaxData(response, code, msg, data);
	}

	/**
	 * @desc 扶贫商品列表
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAjaxDataFuPinComm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "3";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		VillageInfo villageInfo = getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == villageInfo) {
			msg = "村子已删除或者审核未通过！";
			return super.returnAjaxData(response, code, msg, data);
		}
		data.put("villageInfo", villageInfo);

		CommInfo commInfo = new CommInfo();
		commInfo.getMap().put("village_id", id);
		commInfo.setAudit_state(1);
		commInfo.setIs_sell(1);
		commInfo.setIs_has_tc(1);
		commInfo.setIs_aid(1);// 扶贫
		commInfo.getMap().put("not_out_sell_time", "true");

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCountForFuPin(commInfo);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		commInfo.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		// villageDynamic.getRow().setCount(3);
		commInfo.getRow().setCount(pager.getRowCount());

		List<CommInfo> commInfoList = getFacade().getCommInfoService().getCommInfoPaginatedListForFuPin(commInfo);
		if (null != commInfoList && commInfoList.size() > 0) {
			for (CommInfo item : commInfoList) {
				PdImgs img = new PdImgs();
				img.setPd_id(item.getId());
				List<PdImgs> imgs = getFacade().getPdImgsService().getPdImgsList(img);

				CommTczhPrice tc = new CommTczhPrice();
				tc.setComm_id(item.getId().toString());
				List<CommTczhPrice> tclist = getFacade().getCommTczhPriceService().getCommTczhPriceList(tc);
				if (null != tclist && tclist.size() > 0) {
					item.getMap().put("commInfoTczh", tclist.get(0));
				}
				item.setCommImgsList(imgs);

				CommInfoPoors poors = new CommInfoPoors();
				poors.setComm_id(item.getId());
				List<CommInfoPoors> poorList = getFacade().getCommInfoPoorsService().getCommInfoPoorsList(poors);
				item.getMap().put("poorList", poorList);
			}
		}
		data.put("commInfoList", commInfoList);

		code = "1";
		if (commInfoList.size() < Integer.valueOf(pageSize)) {
			code = "2";
		}

		log.info("(commInfoList.size():" + commInfoList.size());
		log.info("pageSize:" + pageSize);
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataRecord(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		VillageInfo villageInfo = getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == villageInfo) {
			msg = "村子已删除或者审核未通过！";
			super.returnInfo(response, code, msg, data);
			return null;
		}
		data.put("villageInfo", villageInfo);

		UserInfo ui = super.getUserInfoFromSession(request);

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String newPageSize = (String) dynaBean.get("newPageSize");

		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		Integer pageSize = 10;
		Integer count = 3;
		if (null != newPageSize) {
			pageSize = Integer.valueOf(newPageSize);
			count = Integer.valueOf(newPageSize);
		}

		if (null != ui) {
			VillageDynamicRecord villageDynamicRecord = new VillageDynamicRecord();
			villageDynamicRecord.setTo_user_id(ui.getId());
			villageDynamicRecord.setIs_del(0);
			villageDynamicRecord.setVillage_id(Integer.valueOf(id));

			Integer recordCount = getFacade().getVillageDynamicRecordService().getVillageDynamicRecordCount(
					villageDynamicRecord);
			pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
			if (null != startPage) {
				villageDynamicRecord.getRow().setFirst(Integer.valueOf(startPage) * count);
			} else {
				villageDynamicRecord.getRow().setFirst(pager.getFirstRow());
			}
			villageDynamicRecord.getRow().setCount(pager.getRowCount());
			if (null != count) {
				villageDynamicRecord.getRow().setCount(count);
			}

			List<VillageDynamicRecord> villageDynamicRecordList = getFacade().getVillageDynamicRecordService()
					.getVillageDynamicRecordPaginatedList(villageDynamicRecord);

			code = "1";
			if (villageDynamicRecordList.size() < Integer.valueOf(pageSize)) {
				code = "2";
			}
			log.info("villageDynamicRecordList.size():" + villageDynamicRecordList.size());
			log.info("pageSize:" + pageSize);
			log.info("code:" + code);

			data.put("villageDynamicRecordList", villageDynamicRecordList);
		}
		// code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward team(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		super.getModNameForMobile(request);

		Pager pager = (Pager) dynaBean.get("pager");

		List<BaseData> baseDataList = super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		if (null != baseDataList && baseDataList.size() > 0) {
			request.setAttribute("baseDataList", baseDataList);
		}
		String title = "下级会员";

		UserRelationPar entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");

		Integer pageSize = 10;
		Integer recordCount = getFacade().getUserRelationParService().getUserRelationParCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserRelationPar> entityList = super.getFacade().getUserRelationParService()
				.getUserRelationParPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (UserRelationPar t : entityList) {
				UserInfo u = new UserInfo();
				u.setId(t.getUser_id());
				u = super.getFacade().getUserInfoService().getUserInfo(u);
				if (u != null) {
					UserRelationPar uRelationPar = new UserRelationPar();
					uRelationPar.getMap().put("my_lower_by_user_par_id", u.getId());
					uRelationPar.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");
					Integer Count = getFacade().getUserRelationParService().getUserRelationParCount(uRelationPar);
					t.getMap().put("userInfo", u);
					t.getMap().put("team_count", Count);
				}
			}

			if (entityList.size() == Integer.valueOf(pageSize)) {
				request.setAttribute("appendMore", 1);
			}
		}

		request.setAttribute("entityList", entityList);

		request.setAttribute("header_title", title);
		return new ActionForward("/../m/MVillage/MVillage_team.jsp");
	}

	/**
	 * @desc 申请加入村子
	 */
	public ActionForward isApplyVillage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "-1";
			msg = "您还未登录，请先登录系统！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		VillageInfo villageInfo = super.getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == villageInfo) {
			code = "-2";
			msg = "村子已删除或者审核未通过！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		// 是否加入村子
		// VillageMember applyVillageInfo = super.getApplyVillage(villageInfo.getId(), ui.getId());
		// if (null == applyVillageInfo) {
		// code = "-2";
		// msg = "请先加入村子";
		// return super.returnAjaxData(response, code, msg, data);
		// }

		// applyVillageInfo = super.getApplyVillage(villageInfo.getId(), ui.getId(),
		// Keys.Audit_Status.Audit_Status_0.getIndex());
		// if (null != applyVillageInfo) {
		// code = "-3";
		// msg = "已申请，等待审核通过中";
		// return super.returnAjaxData(response, code, msg, data);
		// }

		super.returnInfo(response, code, msg, data);
		return null;
	}

	/**
	 * @desc 申请加入村子
	 */
	public ActionForward applyAdd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "-3";
			msg = "您还未登录，请先登录系统！";
			super.returnInfo(response, code, msg, data);
			return null;
		}
		// 判断是否实名认证
		if (ui.getIs_renzheng() != 1) {
			msg = "请先实名认证";
			super.returnInfo(response, "-2", msg, data);
			return null;
		}
		// 村信息
		VillageInfo entity = super.getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == entity) {
			msg = "村子已删除或者审核未通过！";
			super.returnInfo(response, "-1", msg, data);
			return null;
		}
		data.put("entity", entity);

		VillageMember member = new VillageMember();
		member.setVillage_id(entity.getId());
		member.setUser_id(ui.getId());
		member.setIs_del(0);
		member = getFacade().getVillageMemberService().getVillageMember(member);
		if (null == member) {
			// 插入关注信息
			VillageMember insertVillageMember = new VillageMember();
			insertVillageMember.setVillage_id(entity.getId());
			insertVillageMember.setUser_id(ui.getId());
			insertVillageMember.setUser_name(ui.getReal_name());
			insertVillageMember.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
			insertVillageMember.setAdd_date(new Date());
			insertVillageMember.setAdd_user_id(ui.getId());
			insertVillageMember.setAdd_user_name(ui.getReal_name());
			insertVillageMember.setIs_del(0);
			insertVillageMember.setMobile(ui.getMobile());
			int i = getFacade().getVillageMemberService().createVillageMember(insertVillageMember);
			if (i > 0) {
				code = "1";
				msg = "申请成功！等待管理员审核";
			} else {
				msg = "申请失败！请联系管理员";
			}
			super.returnInfo(response, code, msg, data);
			return null;
		}
		if (member.getAudit_state().intValue() == Keys.audit_state.audit_state_0.getIndex()) {
			code = "0";
			msg = "已申请！等待管理员审核";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		if (member.getAudit_state().intValue() == Keys.audit_state.audit_state_fu_1.getIndex()) {
			member.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
			member.setUpdate_date(new Date());
			member.setUpdate_user_id(ui.getId());
			member.setUpdate_user_name(ui.getReal_name());
			getFacade().getVillageMemberService().modifyVillageMember(member);
			code = "1";
			msg = "已申请！等待管理员审核";
		}

		super.returnInfo(response, code, msg, data);
		return null;
	}

	/**
	 * @desc 退出加入村子
	 */
	public ActionForward quit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "-1";
			msg = "您还未登录，请先登录系统！";
			super.returnInfo(response, code, msg, data);
			return null;
		}
		// 村信息
		VillageInfo entity = super.getVillageInfo(id, Keys.audit_state.audit_state_1.getIndex());
		if (null == entity) {
			msg = "村子已删除或者审核未通过！";
			super.returnInfo(response, "-1", msg, data);
			return null;
		}
		data.put("entity", entity);

		// 插入关注信息
		VillageMember member = new VillageMember();
		member.setVillage_id(entity.getId());
		member.setUser_id(ui.getId());
		member.setIs_del(0);
		member = getFacade().getVillageMemberService().getVillageMember(member);
		if (null == member) {
			msg = "村员信息不存";
			return super.returnAjaxData(response, code, msg, data);
		}
		member.setIs_del(1);
		member.setUpdate_date(new Date());
		member.setUpdate_user_id(ui.getId());
		member.setUpdate_user_name(ui.getUser_name());
		int i = getFacade().getVillageMemberService().modifyVillageMember(member);

		if (i > 0) {
			code = "0";
			msg = "退出成功";
		} else {
			msg = "退出失败";
		}

		return super.returnAjaxData(response, code, msg, data);
	}

	/**
	 * @desc 关注
	 */
	public ActionForward follow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");// 关联user_id

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "0";
			msg = "您还未登录，请先登录系统！";
			super.returnInfo(response, code, msg, data);
			return null;
		}
		UserInfo linkUser = super.getUserInfo(Integer.valueOf(id));
		if (null == linkUser) {
			msg = "用户不存在";
			super.returnInfo(response, "-1", msg, data);
			return null;
		}

		VillageContactList insert = new VillageContactList();
		insert.setContact_user_id(linkUser.getId());
		insert.setContact_user_name(linkUser.getReal_name());
		insert.setNick_name(linkUser.getReal_name());
		insert.setGroup_id(Keys.GroupType.GroupType_0.getIndex());
		insert.setAdd_date(new Date());
		insert.setAdd_user_id(ui.getId());
		insert.setAdd_user_name(ui.getReal_name());
		int i = getFacade().getVillageContactListService().createVillageContactList(insert);

		if (i > 0) {
			msg = "关注成功！";
		} else {
			msg = "关注失败！请联系管理员";
		}

		super.returnInfo(response, code, msg, data);
		return null;
	}

	/**
	 * @desc 取消关注
	 */
	public ActionForward noFollow(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");// 关联user_id

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			code = "0";
			msg = "您还未登录，请先登录系统！";
			super.returnInfo(response, code, msg, data);
			return null;
		}
		UserInfo linkUser = super.getUserInfo(Integer.valueOf(id));
		if (null == linkUser) {
			msg = "用户不存在";
			super.returnInfo(response, "-1", msg, data);
			return null;
		}

		VillageContactList entity = new VillageContactList();
		entity.setContact_user_id(linkUser.getId());
		entity.setAdd_user_id(ui.getId());
		entity = getFacade().getVillageContactListService().getVillageContactList(entity);
		if (null != entity) {
			VillageContactList del = new VillageContactList();
			del.setId(entity.getId());
			getFacade().getVillageContactListService().removeVillageContactList(del);
		}

		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String village_id = (String) dynaBean.get("village_id");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");

		if (StringUtils.isBlank(village_id)) {
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

		VillageInfo village = super.getVillageInfo(Integer.valueOf(village_id));
		if (null == village) {
			msg = "村子不存在！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		BaseFiles entity = new BaseFiles();
		entity.setLink_id(village.getId());
		entity.setType(Keys.BaseFilesType.Base_Files_TYPE_50.getIndex());
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

	/**
	 * @desc 是否设置支付密码
	 */
	public ActionForward isSetUpPasswordPay(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			msg = "请先登录！";
			code = "-2";
			return returnAjaxData(response, code, msg, data);
		}
		if (null == ui.getPassword_pay()) {
			msg = "请先设置支付密码";
			return returnAjaxData(response, code, msg, data);
		}

		code = "1";
		return returnAjaxData(response, code, msg, data);
	}

	/**
	 * @desc 村情
	 */
	public ActionForward getAjaxDataCunQing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String village_id = (String) dynaBean.get("village_id");

		VillageInfo village = super.getVillageInfo(village_id);
		if (null == village) {
			msg = "村子不存在或审核未通过";
			return returnAjaxData(response, code, msg, data);
		}
		data.put("villageInfo", village);

		code = "1";
		return returnAjaxData(response, code, msg, data);
	}
}
