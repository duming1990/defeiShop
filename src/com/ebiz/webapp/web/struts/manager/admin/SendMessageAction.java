package com.ebiz.webapp.web.struts.manager.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.ShortMessage;
import com.ebiz.webapp.domain.ShortMessageReceiver;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author luzr
 */
public class SendMessageAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);// 导航

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String title = (String) dynaBean.get("title");
		String is_del = (String) dynaBean.get("is_del");
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");

		ShortMessage entity = new ShortMessage();
		if (StringUtils.isNotBlank(title)) {
			entity.getMap().put("title_like", title);
		}
		if (StringUtils.isNotBlank(is_del)) {
			entity.setIs_del(Integer.parseInt(is_del));
		}
		if (StringUtils.isNotBlank(st_date)) {
			entity.getMap().put("st_date", st_date);
		}
		if (StringUtils.isNotBlank(en_date)) {
			entity.getMap().put("en_date", en_date);
		}

		Integer recordCount = getFacade().getShortMessageService().getShortMessageCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		List<ShortMessage> entityList = getFacade().getShortMessageService().getShortMessagePaginatedList(entity);
		if (entityList != null && entityList.size() > 0) {
			for (ShortMessage sm : entityList) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(sm.getAdd_user_id());
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				if (userInfo != null) {
					sm.getMap().put("add_name", userInfo.getReal_name());
				}
			}
		}

		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		setNaviStringToRequestScope(request);// 导航

		return mapping.findForward("input");
	}

	public ActionForward send(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_all = (String) dynaBean.get("is_all");
		String user_ids = (String) dynaBean.get("user_ids");
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		ShortMessage entity = new ShortMessage();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(is_all)) {// 全部发送
			entity.setIs_all(1);
		} else if (StringUtils.isNotBlank(user_ids)) {
			List<ShortMessageReceiver> mrList = new ArrayList<ShortMessageReceiver>();
			String[] uids = StringUtils.split(user_ids, ",");
			for (String uid : uids) {
				ShortMessageReceiver smr = new ShortMessageReceiver();
				UserInfo userInfo = new UserInfo();
				smr.setReceiver_user_id(Integer.parseInt(uid));
				userInfo.setId(Integer.parseInt(uid));
				userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
				smr.getMap().put("mobile", userInfo.getMobile());
				mrList.add(smr);
			}
			entity.setShortMessageReceiver(mrList);
		}

		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setAdd_user_id(ui.getId());
		entity.setAdd_date(new Date());
		getFacade().getShortMessageService().createShortMessage(entity);

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			ShortMessage sm = new ShortMessage();
			sm.setId(Integer.parseInt(id));
			sm = getFacade().getShortMessageService().getShortMessage(sm);
			if (null == sm) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}
			super.copyProperties(form, sm);

			if (sm.getIs_all().intValue() == 0) {
				ShortMessageReceiver smr = new ShortMessageReceiver();
				smr.setMsg_id(sm.getId());
				List<ShortMessageReceiver> smrList = super.getFacade().getShortMessageReceiverService()
						.getShortMessageReceiverList(smr);
				for (ShortMessageReceiver shortMessageReceiver : smrList) {
					UserInfo userInfo = new UserInfo();
					userInfo.setId(shortMessageReceiver.getReceiver_user_id());
					userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
					shortMessageReceiver.getMap().put("user_name", userInfo.getUser_name());
				}
				request.setAttribute("smrList", smrList);
			}
			return mapping.findForward("view");
		} else {
			this.saveError(request, "errors.long", new String[] { id });
			return mapping.findForward("list");
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			ShortMessage entity = new ShortMessage();
			entity.setId(new Integer(id));
			getFacade().getShortMessageService().removeShortMessage(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			ShortMessage entity = new ShortMessage();
			entity.getMap().put("pks", pks);
			getFacade().getShortMessageService().removeShortMessage(entity);
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

}
