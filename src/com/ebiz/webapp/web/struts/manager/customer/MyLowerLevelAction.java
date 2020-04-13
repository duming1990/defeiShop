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
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,yang
 * @version 2015-12-6
 */
public class MyLowerLevelAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);
		Pager pager = (Pager) dynaBean.get("pager");
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		List<BaseData> baseDataList = super.getBaseDataList(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		if (null != baseDataList && baseDataList.size() > 0) {
			request.setAttribute("baseDataList", baseDataList);
		}

		UserRelationPar entity = new UserRelationPar();
		entity.getMap().put("my_lower_by_user_par_id", ui.getId());
		entity.getMap().put("orderByCommon", " a.USER_PAR_LEVLE asc, ");

		Integer recordCount = getFacade().getUserRelationParService().getUserRelationParCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
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
		}
		request.setAttribute("entityList", entityList);

		String ymid = ui.getYmid();
		if (StringUtils.isNotBlank(ymid)) {
			UserInfo uipar = new UserInfo();
			// uipar.setUser_name(ymid);
			uipar.getMap().put("ym_id", ymid);
			uipar.setIs_del(0);
			uipar = getFacade().getUserInfoService().getUserInfo(uipar);
			request.setAttribute("uipar", uipar);
		}

		return mapping.findForward("list");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		getsonSysModuleList(request, dynaBean);

		if (!GenericValidator.isLong(id)) {
			String msg = "id参数不正确";
			return super.showMsgForCustomer(request, response, msg);
		}

		UserRelationPar entity = new UserRelationPar();
		entity.setId(new Integer(id));
		entity = super.getFacade().getUserRelationParService().getUserRelationPar(entity);
		if (null != entity) {

			UserInfo userInfoSon = new UserInfo();
			userInfoSon.setId(entity.getUser_id());
			userInfoSon.setIs_del(0);
			userInfoSon = super.getFacade().getUserInfoService().getUserInfo(userInfoSon);
			request.setAttribute("userInfoSon", userInfoSon);

		}
		super.copyProperties(form, entity);
		return mapping.findForward("view");
	}

	public ActionForward myOffline(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		return new ActionForward("/customer/MyLowerLevel/myOffline.jsp");
	}

	public ActionForward ajaxGetMyLowerLevelDataList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		JSONObject josonObject = new JSONObject();

		UserRelation entity = new UserRelation();
		entity.setUser_id(ui.getId());
		entity = super.getFacade().getUserRelationService().getUserRelation(entity);

		UserInfo uiTemp = new UserInfo();
		uiTemp.setId(ui.getId());
		uiTemp = super.getFacade().getUserInfoService().getUserInfo(uiTemp);

		JSONArray seriesList = new JSONArray();

		JSONObject josonObjectRoot = new JSONObject();
		if (null != entity) {
			josonObjectRoot.put("name", uiTemp.getReal_name());
			josonObjectRoot.put("value", entity.getUser_id());
			josonObjectRoot.put("user_level", super.getUserLevel(uiTemp.getUser_level()) + "级");
			josonObjectRoot.put("user_par_levle", "顶级");
			josonObjectRoot.put("cur_score", uiTemp.getCur_score());
			josonObjectRoot.put("user_score_union", uiTemp.getUser_score_union());
			josonObjectRoot.put("user_score_sum", uiTemp.getUser_score_union().add(uiTemp.getCur_score()));
		}
		josonObjectRoot = this.getJSON(josonObjectRoot, ui.getId(), 3);
		seriesList.add(josonObjectRoot);
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
						josonObjectSon.put("name", userInfo.getReal_name());
						josonObjectSon.put("value", sonTemp.getUser_id());
						josonObjectSon.put("user_level", super.getUserLevel(userInfo.getUser_level()) + "级");
						josonObjectSon.put("user_par_levle", 3 - flag);
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
