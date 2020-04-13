package com.ebiz.webapp.web.struts.manager.customer;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

//import com.ebiz.tjgis.web.util.DESPlus;

/**
 * @author Wu,yang
 * @version 2015-12-6
 */
public class MyScoreAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			String msg = "用户名不存在或者已经被删除！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, entity);
		return mapping.findForward("view");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		super.getsonSysModuleList(request, dynaBean);

		String score_type = (String) dynaBean.get("score_type");
		Pager pager = (Pager) dynaBean.get("pager");
		UserInfo ui = super.getUserInfoFromSession(request);

		UserScoreRecord entity = new UserScoreRecord();
		entity.setAdd_user_id(ui.getId());
		if (StringUtils.isNotBlank(score_type) && GenericValidator.isInt(score_type)) {
			entity.setScore_type(Integer.valueOf(score_type));
		}
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());

		entity.getMap().put("order_by_info", "add_date desc,");

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		Integer recordCount = getFacade().getUserScoreRecordService().getUserScoreRecordCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<UserScoreRecord> userScoreRecordList = super.getFacade().getUserScoreRecordService()
				.getUserScoreRecordPaginatedList(entity);
		if (null != userScoreRecordList && userScoreRecordList.size() > 0) {
			request.setAttribute("userScoreRecordList", userScoreRecordList);
		}
		request.setAttribute("scoreTypes", Keys.ScoreType.values());

		return mapping.findForward("list");
	}
}
