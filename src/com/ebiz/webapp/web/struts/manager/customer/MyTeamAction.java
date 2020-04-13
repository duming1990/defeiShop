package com.ebiz.webapp.web.struts.manager.customer;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSONArray;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelation;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;

public class MyTeamAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String st_add_date = (String) dynaBean.get("st_add_date");
		String en_add_date = (String) dynaBean.get("en_add_date");
		Pager pager = (Pager) dynaBean.get("pager");

		UserInfo user = super.getUserInfoFromSession(request);
		int user_id = user.getId();
		UserInfo ui = super.getUserInfo(user.getId());

		request.setAttribute("user_name_par", ui.getUser_name());
		UserInfo entity = new UserInfo();

		entity.getMap().put("st_add_date", st_add_date);
		entity.getMap().put("en_add_date", en_add_date);
		entity.getMap().put("user_par_levle_le", 3);
		entity.setIs_del(0);
		entity.getMap().put("left_join_user_relation_par", "true");

		List<UserInfo> userInfoList = null;
		Integer recordCount = 0;
		if (user.getIs_village() == 1) {
			VillageInfo info = super.getVillageInfo(user.getOwn_village_id());
			if (null != info) {
				UserInfo userSpecial = new UserInfo();
				userSpecial.setIs_del(0);
				userSpecial.setIs_village(1);
				userSpecial.setOwn_village_id(info.getId());

				List<UserInfo> userSpecialList = getFacade().getUserInfoService().getUserInfoList(userSpecial);
				if (null != userSpecialList && userSpecialList.size() > 0) {
					String special = String.valueOf(userSpecialList.get(0).getId());
					if (null != userSpecialList.get(1)) {
						special += "," + userSpecialList.get(1).getId();
					}
					entity.getMap().put("user_par_id_special", special);
					entity.getMap().put("not_me", user.getId());// 去除下级是自己的
				}
			}
		} else {
			entity.getMap().put("user_par_id", user_id);
		}

		recordCount = getFacade().getUserInfoService().getUserSpeciaCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		userInfoList = getFacade().getUserInfoService().getUserSpeciaList(entity);

		for (UserInfo u : userInfoList) {
			UserRelationPar userRelationPar = new UserRelationPar();
			userRelationPar.setUser_par_id(u.getId());
			int user_son_count = getFacade().getUserRelationParService().getUserRelationParCount(userRelationPar);
			u.getMap().put("user_son_count", user_son_count);
		}
		request.setAttribute("user_id", user_id);
		request.setAttribute("userInfoList", userInfoList);
		request.setAttribute("userSonLevel3Count", recordCount);

		// UserRelationPar userRelationPar = new UserRelationPar();
		// userRelationPar.setUser_par_id(Integer.valueOf(user_id));
		// int userSonLevelCount = getFacade().getUserRelationParService().getUserRelationParCount(userRelationPar);
		// request.setAttribute("userSonLevelCount", userSonLevelCount);

		String ymid = ui.getYmid();
		if (StringUtils.isNotBlank(ymid)) {
			UserInfo uipar = new UserInfo();
			// uipar.setUser_name(ymid);
			uipar.getMap().put("ym_id", ymid);
			uipar.setIs_del(0);
			uipar = getFacade().getUserInfoService().getUserInfo(uipar);
			request.setAttribute("uipar", uipar);
		}

		List<BaseData> baseDataList = super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		if (null != baseDataList && baseDataList.size() > 0) {
			request.setAttribute("baseDataList", baseDataList);
		}

		UserRelationPar entity1 = new UserRelationPar();
		entity1.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity1.getMap().put("user_info_not_null", true);
		entity1.getMap().put("st_add_date", st_add_date);
		entity1.getMap().put("en_add_date", en_add_date);
		Integer recordCountAll = getFacade().getUserRelationParService().getUserRelationParCount(entity1);

		UserRelationPar uRelationPar = new UserRelationPar();
		uRelationPar.getMap().put("my_lower_by_user_par_id", ui.getId());
		uRelationPar.getMap().put("my_lower_by_level", 1);
		uRelationPar.getMap().put("user_info_not_null", true);
		uRelationPar.getMap().put("st_add_date", st_add_date);
		uRelationPar.getMap().put("en_add_date", en_add_date);
		Integer recordCount1 = getFacade().getUserRelationParService().getUserRelationParCount(uRelationPar);

		uRelationPar = new UserRelationPar();
		uRelationPar.getMap().put("my_lower_by_user_par_id", ui.getId());
		uRelationPar.getMap().put("my_lower_by_level", 2);
		uRelationPar.getMap().put("user_info_not_null", true);
		uRelationPar.getMap().put("st_add_date", st_add_date);
		uRelationPar.getMap().put("en_add_date", en_add_date);
		Integer recordCount2 = getFacade().getUserRelationParService().getUserRelationParCount(uRelationPar);

		uRelationPar = new UserRelationPar();
		uRelationPar.getMap().put("my_lower_by_user_par_id", ui.getId());
		uRelationPar.getMap().put("my_lower_by_level", 3);
		uRelationPar.getMap().put("st_add_date", st_add_date);
		uRelationPar.getMap().put("en_add_date", en_add_date);
		uRelationPar.getMap().put("user_info_not_null", true);
		Integer recordCount3 = getFacade().getUserRelationParService().getUserRelationParCount(uRelationPar);

		request.setAttribute("recordCount1", recordCount1);
		request.setAttribute("recordCount2", recordCount2);
		request.setAttribute("recordCount3", recordCount3);
		request.setAttribute("recordCountAll", recordCountAll);

		return mapping.findForward("list");
	}

	public ActionForward lowerlist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		if (StringUtils.isBlank(user_id)) {
			String msg = "参数错误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		UserInfo ui = super.getUserInfo(Integer.valueOf(user_id));
		if (ui == null) {
			String msg = "用户不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("user_name_par", ui.getUser_name());

		String ymid = ui.getYmid();
		if (StringUtils.isNotBlank(ymid)) {
			UserInfo uipar = new UserInfo();
			// uipar.setUser_name(ymid);
			uipar.getMap().put("ym_id", ymid);
			uipar.setIs_del(0);
			uipar = getFacade().getUserInfoService().getUserInfo(uipar);
			request.setAttribute("uipar", uipar);
		}

		UserRelationPar entity1 = new UserRelationPar();
		entity1.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity1.getMap().put("user_info_not_null", true);
		Integer recordCountAll = getFacade().getUserRelationParService().getUserRelationParCount(entity1);

		entity1 = new UserRelationPar();
		entity1.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity1.getMap().put("my_lower_by_level", 1);
		entity1.getMap().put("user_info_not_null", true);
		Integer recordCount1 = getFacade().getUserRelationParService().getUserRelationParCount(entity1);

		entity1 = new UserRelationPar();
		entity1.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity1.getMap().put("my_lower_by_level", 2);
		entity1.getMap().put("user_info_not_null", true);
		Integer recordCount2 = getFacade().getUserRelationParService().getUserRelationParCount(entity1);

		entity1 = new UserRelationPar();
		entity1.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity1.getMap().put("my_lower_by_level", 3);
		entity1.getMap().put("user_info_not_null", true);
		Integer recordCount3 = getFacade().getUserRelationParService().getUserRelationParCount(entity1);

		request.setAttribute("userSonLevelCount", recordCountAll);
		request.setAttribute("recordCount1", recordCount1);
		request.setAttribute("recordCount2", recordCount2);
		request.setAttribute("recordCount3", recordCount3);

		Pager pager = (Pager) dynaBean.get("pager");

		List<BaseData> baseDataList = super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		if (null != baseDataList && baseDataList.size() > 0) {
			request.setAttribute("baseDataList", baseDataList);
		}
		String title = "我的团队";
		String par_level = (String) dynaBean.get("par_level");

		UserRelationPar entityUserRelationPar = new UserRelationPar();
		entityUserRelationPar.getMap().put("my_lower_by_user_par_id", ui.getId());
		entityUserRelationPar.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");
		if (par_level != null && par_level != "") {
			entityUserRelationPar.getMap().put("my_lower_by_level", par_level);
			title = title + "-我的第" + par_level + "级";
		}
		UserInfo entity = new UserInfo();

		entity.getMap().put("user_par_levle_special", par_level);
		entity.setIs_del(0);
		entity.getMap().put("left_join_user_relation_par", "true");

		List<UserInfo> userInfoList = null;
		Integer recordCount = 0;

		entity.getMap().put("user_par_id", user_id);

		recordCount = getFacade().getUserInfoService().getUserSpeciaCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		userInfoList = getFacade().getUserInfoService().getUserSpeciaList(entity);

		for (UserInfo u : userInfoList) {
			UserRelationPar userRelationPar = new UserRelationPar();
			userRelationPar.setUser_par_id(u.getId());
			int user_son_count = getFacade().getUserRelationParService().getUserRelationParCount(userRelationPar);
			u.getMap().put("user_son_count", user_son_count);
		}
		request.setAttribute("user_id", user_id);
		request.setAttribute("userInfoList", userInfoList);
		request.setAttribute("userSonLevel3Count", recordCount);
		return new ActionForward("/../manager/customer/MyTeam/lower_list.jsp");
	}

	public ActionForward listChart(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		if (!GenericValidator.isInt(user_id)) {
			String msg = "user_id参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		UserInfo ui = super.getUserInfo(Integer.valueOf(user_id));
		request.setAttribute("user_name_par", ui.getUser_name());

		return new ActionForward("/../manager/customer/MyTeam/list_chart.jsp");
	}

	public ActionForward ajaxGetUserRelationDataList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		if (null == user_id) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		JSONObject josonObject = new JSONObject();

		UserRelation entity = new UserRelation();
		entity.setUser_id(Integer.valueOf(user_id));
		entity = super.getFacade().getUserRelationService().getUserRelation(entity);

		// 存数据
		JSONArray seriesList = new JSONArray();

		if (null != entity) {

			UserInfo uiTemp = new UserInfo();
			uiTemp.setId(Integer.valueOf(user_id));
			uiTemp = super.getFacade().getUserInfoService().getUserInfo(uiTemp);
			JSONObject josonObjectRoot = new JSONObject();
			if (null != uiTemp) {
				josonObjectRoot.put("name", uiTemp.getUser_name());
				josonObjectRoot.put("value", entity.getUser_id());
				josonObjectRoot.put("mobile", uiTemp.getMobile());
				josonObjectRoot.put("user_level", super.getUserLevel(uiTemp.getUser_level()) + "级");
				josonObjectRoot.put("cur_score", uiTemp.getCur_score());
				josonObjectRoot.put("user_score_union", uiTemp.getUser_score_union());
				josonObjectRoot.put("user_score_sum", uiTemp.getUser_score_union().add(uiTemp.getCur_score()));
			}

			josonObjectRoot = this.getJSON(josonObjectRoot, entity.getUser_id(), 3);

			seriesList.add(josonObjectRoot);
		}

		josonObject.put("seriesData", seriesList);

		super.renderJson(response, josonObject.toString());

		return null;
	}

	public JSONObject getJSON(JSONObject josonObjectRoot, Integer user_id, Integer flag) throws JSONException {

		List<UserRelation> sonList = this.getNextLevelSonList(user_id);
		if (flag > 0) {
			flag--;

			JSONArray seriesListSon = new JSONArray();
			if (null != sonList && sonList.size() > 0) {
				for (UserRelation sonTemp : sonList) {
					UserInfo userInfo = new UserInfo();
					userInfo.setId(sonTemp.getUser_id());
					userInfo.setIs_del(0);
					userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
					if (null != userInfo) {
						JSONObject josonObjectSon = new JSONObject();
						josonObjectSon.put("name", userInfo.getUser_name());
						josonObjectSon.put("value", sonTemp.getUser_id());
						josonObjectSon.put("mobile", userInfo.getMobile());
						josonObjectSon.put("user_level", super.getUserLevel(userInfo.getUser_level()) + "级");
						josonObjectSon.put("cur_score", userInfo.getCur_score());
						josonObjectSon.put("user_score_union", userInfo.getUser_score_union());
						josonObjectSon.put("user_score_sum", userInfo.getUser_score_union()
								.add(userInfo.getCur_score()));
						seriesListSon.add(josonObjectSon);
						this.getJSON(josonObjectSon, sonTemp.getUser_id(), flag);
					}
				}
				josonObjectRoot.put("children", seriesListSon);
			} else {
				josonObjectRoot.put("children", seriesListSon);
				return josonObjectRoot;
			}
		}
		return josonObjectRoot;

	}

	// 获取子节点
	public List<UserRelation> getNextLevelSonList(Integer user_par_id) {

		List<UserRelation> sonList = new ArrayList<UserRelation>();
		UserRelation sonEntity = new UserRelation();
		sonEntity.setUser_par_id(user_par_id);
		sonList = super.getFacade().getUserRelationService().getUserRelationList(sonEntity);
		return sonList;
	}
}
