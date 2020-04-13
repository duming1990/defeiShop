package com.ebiz.webapp.web.struts.manager.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserScoreRecord;
import com.ebiz.webapp.web.Keys;

/**
 * @author Qin,Yue
 * @version 2011-04-22
 */
public class UserScoreAction extends BaseAdminAction {

	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.search(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String user_name = (String) dynaBean.get("user_name");
		String mobile = (String) dynaBean.get("mobile");
		String user_no = (String) dynaBean.get("user_no");
		String id = (String) dynaBean.get("id");

		UserInfo userInfo = new UserInfo();
		super.copyProperties(form, userInfo);
		if (StringUtils.isNotBlank(id) && GenericValidator.isInt(id)) {
			userInfo.setId(Integer.valueOf(id));
		}
		if (StringUtils.isNotBlank(user_name)) {
			userInfo.setUser_name(user_name);
		}

		if (StringUtils.isNotBlank(mobile)) {
			userInfo.setMobile(mobile);
		}

		if (StringUtils.isNotBlank(user_no)) {
			userInfo.setUser_no(user_no);
		}
		userInfo.setIs_del(0);
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
		super.copyProperties(form, userInfo);
		if (null == userInfo) {
			String msg = "用户不存在";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		request.setAttribute("score", userInfo.getCur_score());
		UserScoreRecord userScoreRecord = new UserScoreRecord();
		userScoreRecord.setAdd_user_id(userInfo.getId());
		userScoreRecord.setIs_del(0);

		Integer recordCount = getFacade().getUserScoreRecordService().getUserScoreRecordCount(userScoreRecord);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		userScoreRecord.getRow().setFirst(pager.getFirstRow());
		userScoreRecord.getRow().setCount(pager.getRowCount());

		List<UserScoreRecord> userScoreRecordList = super.getFacade().getUserScoreRecordService()
				.getUserScoreRecordPaginatedList(userScoreRecord);

		if (null != userScoreRecordList && userScoreRecordList.size() > 0) {
			for (UserScoreRecord temp : userScoreRecordList) {
				userInfo = new UserInfo();
				userInfo.setId(temp.getAdd_user_id());
				userInfo.setIs_del(0);
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				temp.getMap().put("userName", userInfo.getUser_name());

				userInfo = new UserInfo();
				userInfo.setId(temp.getAdd_user_id());
				userInfo.setIs_del(0);
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				temp.getMap().put("addUserName", userInfo.getUser_name());
			}
		}
		if (null != userScoreRecordList && userScoreRecordList.size() > 0) {
			request.setAttribute("userScoreRecordList", userScoreRecordList);
		}
		request.setAttribute("scoreTypes", Keys.ScoreType.values());
		return mapping.findForward("list");
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		return mapping.findForward("input");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = "参数有误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.setIs_del(0);
		userInfo.setId(Integer.valueOf(id));
		userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);

		request.setAttribute("score", userInfo.getCur_score());
		request.setAttribute("user_score_union", userInfo.getUser_score_union());

		return new ActionForward("/../manager/admin/UserScore/add_form.jsp");
	}

	public ActionForward saveScore(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String score = (String) dynaBean.get("score");

		if (!GenericValidator.isInt(id)) {
			String msg = "增加积分时，请指定用户！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		if (!GenericValidator.isInt(score)) {
			String msg = "请输入合适的积分！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserInfo userInfo = new UserInfo();
		userInfo.getMap().put("addUserScore", "true");
		userInfo.setId(Integer.valueOf(id));
		userInfo.getMap().put("score", score);
		userInfo.getMap().put("uiSession", ui);
		super.getFacade().getUserInfoService().modifyUserInfo(userInfo);

		return this.list(mapping, form, request, response);
	}

	public ActionForward checkUserExist(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String user_name = (String) dynaBean.get("user_name");
		String mobile = (String) dynaBean.get("mobile");
		String user_no = (String) dynaBean.get("user_no");

		UserInfo entity1 = null;
		UserInfo entity2 = null;
		UserInfo entity3 = null;
		String flag = "1";
		if (StringUtils.isNotBlank(user_name)) {
			entity1 = new UserInfo();
			entity1.setUser_name(user_name);
			entity1.setIs_del(0);
			entity1 = super.getFacade().getUserInfoService().getUserInfo(entity1);
		}

		if (StringUtils.isNotBlank(mobile)) {
			entity2 = new UserInfo();
			entity2.setMobile(mobile);
			entity2.setIs_del(0);
			entity2 = super.getFacade().getUserInfoService().getUserInfo(entity2);
		}

		if (StringUtils.isNotBlank(user_no)) {
			entity3 = new UserInfo();
			entity3.setUser_no(user_no);
			entity3.setIs_del(0);
			entity3 = super.getFacade().getUserInfoService().getUserInfo(entity3);
		}
		if (null != entity1 || null != entity2 || null != entity3) {
			flag = "0";
		}
		super.renderJson(response, flag);
		return null;
	}

}