package com.ebiz.webapp.web.struts.m;

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
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserRelationPar;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;

public class MMyLowerLevelAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		String title = "我的团队";
		String par_level = (String) dynaBean.get("par_level");

		UserRelationPar entity = new UserRelationPar();
		// entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");
		if (par_level != null && par_level != "") {
			// entity.getMap().put("my_lower_by_level", par_level);
			entity.setUser_par_levle(Integer.valueOf(par_level));
			title = title + "-我的第" + par_level + "级";
		}
		if (ui.getIs_village() == 1) {

			VillageInfo info = super.getVillageInfo(ui.getOwn_village_id());
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
					entity.getMap().put("not_me", ui.getId());// 去除下级是自己的
				}
			}
		} else {
			entity.getMap().put("user_par_id", ui.getId());
		}

		Integer pageSize = 10;
		Integer recordCount = getFacade().getUserRelationParService().getSpeciaCount(entity);
		pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserRelationPar> entityList = super.getFacade().getUserRelationParService().getSpeciaPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (UserRelationPar t : entityList) {
				UserInfo u = new UserInfo();
				Integer user_id = (Integer) t.getMap().get("user_id");
				u.setId(user_id);
				u = super.getFacade().getUserInfoService().getUserInfo(u);
				if (u != null) {
					// UserRelationPar uRelationPar = new UserRelationPar();
					// uRelationPar.getMap().put("my_lower_by_user_par_id", u.getId());
					// uRelationPar.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");
					// // if (par_level != null && par_level != "") {
					// // uRelationPar.getMap().put("my_lower_by_level", par_level);
					// // title = title + "-我的第" + par_level + "级";
					// // }
					//
					// Integer Count = getFacade().getUserRelationParService().getUserRelationParCount(uRelationPar);
					t.getMap().put("userInfo", u);
					// t.getMap().put("team_count", Count);
				}
			}

			if (entityList.size() == Integer.valueOf(pageSize)) {
				request.setAttribute("appendMore", 1);
			}
		}
		request.setAttribute("entityList", entityList);
		request.setAttribute("entityListSize", recordCount);
		request.setAttribute("par_level", par_level);

		request.setAttribute("header_title", title);
		return new ActionForward("/../m/MMyLowerLevel/MMyLowerLevel_list.jsp");
		// return mapping.findForward("list");
	}

	public ActionForward myTeam(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		} else {
			// return super.toAppDownload(mapping, form, request, response);
		}

		request.setAttribute("header_title", "我的团队");
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		UserRelationPar entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());

		Integer recordCountAll = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("my_lower_by_level", 1);

		Integer recordCount1 = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("my_lower_by_level", 2);

		Integer recordCount2 = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("my_lower_by_level", 3);

		Integer recordCount3 = getFacade().getUserRelationParService().getUserRelationParCount(entity);

		request.setAttribute("recordCountAll", recordCountAll);
		request.setAttribute("recordCount1", recordCount1);
		request.setAttribute("recordCount2", recordCount2);
		request.setAttribute("recordCount3", recordCount3);

		return new ActionForward("/MMyLowerLevel/myTeam.jsp");
	}

	public ActionForward listPar(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		String par_level = (String) dynaBean.get("par_level");

		UserRelationPar entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");
		if (par_level != null && par_level != "") {
			entity.getMap().put("my_lower_by_level", par_level);

		}

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
				t.getMap().put("userInfo", u);
			}

			if (entityList.size() == Integer.valueOf(pageSize)) {
				request.setAttribute("appendMore", 1);
			}
		}

		// request.setAttribute("entityList", entityList);

		String ymid = ui.getYmid();
		if (StringUtils.isNotBlank(ymid)) {
			UserInfo uipar = new UserInfo();
			// uipar.setUser_name(ymid);
			uipar.getMap().put("ym_id", ymid);
			uipar.setIs_del(0);
			uipar = getFacade().getUserInfoService().getUserInfo(uipar);
			request.setAttribute("uipar", uipar);
		}
		request.setAttribute("header_title", "我的推荐人");
		return mapping.findForward("list");
	}

	public ActionForward getListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("===========getListJson===========");
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		Pager pager = (Pager) dynaBean.get("pager");
		String par_level = (String) dynaBean.get("par_level");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserRelationPar entity = new UserRelationPar();
		// entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");
		if (par_level != null && par_level != "") {
			// entity.getMap().put("my_lower_by_level", par_level);

			entity.setUser_par_levle(Integer.valueOf(par_level));
		}
		if (ui.getIs_village() == 1) {
			VillageInfo info = super.getVillageInfo(ui.getOwn_village_id());
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
					entity.getMap().put("not_me", ui.getId());// 去除下级是自己的
				}
			}
		} else {
			entity.getMap().put("user_par_id", ui.getId());
		}

		Integer recordCount = getFacade().getUserRelationParService().getSpeciaCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<UserRelationPar> entityList = super.getFacade().getUserRelationParService().getSpeciaPaginatedList(entity);

		List<BaseData> baseDataList = super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		if ((null != entityList) && (entityList.size() > 0)) {
			for (UserRelationPar temp : entityList) {
				JSONObject map = new JSONObject();
				UserInfo u = new UserInfo();
				Integer user_id = (Integer) temp.getMap().get("user_id");
				u.setId(user_id);
				u = super.getFacade().getUserInfoService().getUserInfo(u);
				if (null != u) {
					map.put("user_name", u.getUser_name());
					map.put("cur_score", u.getCur_score());
					String user_level = "";
					for (BaseData baseDataTemp : baseDataList) {
						if (u.getUser_level().equals(baseDataTemp.getId())) {
							user_level = baseDataTemp.getType_name();
							break;
						}
					}

					map.put("user_level", user_level);
					String shenfen = "会员";
					if (u.getIs_entp() == 1) {
						shenfen = "商家";
					}
					if (u.getIs_fuwu() == 1) {
						shenfen = "运营中心";
					}
					// if (u.getIs_lianmeng() == 1) {
					// shenfen = "联盟主";
					// }

					map.put("shenfen", shenfen);

					// UserRelationPar uRelationPar = new UserRelationPar();
					// uRelationPar.getMap().put("my_lower_by_user_par_id", u.getId());
					// uRelationPar.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");
					// // if (par_level != null && par_level != "") {
					// // uRelationPar.getMap().put("my_lower_by_level", par_level);
					// // title = title + "-我的第" + par_level + "级";
					// // }
					//
					// Integer Count = getFacade().getUserRelationParService().getUserRelationParCount(uRelationPar);
					map.put("team_count", temp.getMap().get("team_count"));
					map.put("add_date", DateTools.getStringDate(u.getAdd_date(), "yyyy-MM-dd"));
				}

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (entityList.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		super.renderJson(response, datas.toString());
		return null;
	}
}
