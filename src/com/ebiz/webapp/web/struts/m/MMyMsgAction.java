package com.ebiz.webapp.web.struts.m;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;

/**
 * @author Wuyang
 * @version 2016-1-6
 */
public class MMyMsgAction extends MBaseWebAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		super.getModNameForMobile(request);
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		MsgReceiver entity = new MsgReceiver();

		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String read_state = (String) dynaBean.get("read_state");
		String mod_id = (String) dynaBean.get("mod_id");
		Pager pager = (Pager) dynaBean.get("pager");

		super.copyProperties(entity, form);

		entity.setReceiver_user_id(ui.getId());
		entity.getMap().put("st_date", st_date);
		entity.getMap().put("en_date", en_date);
		if (GenericValidator.isLong(read_state)) {
			if (read_state.equals("0")) {// 未读
				entity.getMap().put("is_not_read", "true");
			}
			if (read_state.equals("1")) {// 已读
				entity.setIs_read(1);
			}
		}

		Integer pageSize = 10;
		long recordCount = getFacade().getMsgReceiverService().getMsgReceiverCount(entity);
		request.setAttribute("msgCount", recordCount);
		pager.init(recordCount, pageSize, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<MsgReceiver> msgRecList = getFacade().getMsgReceiverService().getMsgReceiverPaginatedList(entity);
		if (msgRecList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}
		dynaBean.set("pageSize", pageSize);

		request.setAttribute("entityList", msgRecList);

		return mapping.findForward("list");

	}

	public ActionForward setRead(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		HttpSession session = request.getSession();
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		MsgReceiver entity = new MsgReceiver();
		entity.setReceiver_user_id(ui.getId());
		entity.setIs_read(1);
		super.getFacade().getMsgReceiverService().modifyMsgReceiver(entity);

		ActionForward forward = new ActionForward(mapping.findForward("success").getPath()
				+ response.encodeURL("&mod_id=" + mod_id + "&par_id=" + par_id), true);
		return forward;

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.getModNameForMobile(request);
		DynaBean dynaBean = (DynaBean) form;
		String msg_id = (String) dynaBean.get("msg_id");
		String msg_rec_id = (String) dynaBean.get("msg_rec_id");

		getsonSysModuleList(request, dynaBean);

		if (!GenericValidator.isLong(msg_id)) {
			String msg = "msg_id 为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		Msg msg = new Msg();
		msg.setId(new Integer(msg_id));
		Msg entity = getFacade().getMsgService().getMsg(msg);

		if (null == entity) {
			String msg1 = "信息不存在";
			return super.showTipMsg(mapping, form, request, response, msg1);
		}

		request.setAttribute("send_user_name", entity.getUser_name());

		MsgReceiver msgReceiver = new MsgReceiver();
		msgReceiver.setMsg_id(entity.getId());
		msgReceiver.setIs_read(1);
		getFacade().getMsgReceiverService().modifyMsgReceiver(msgReceiver);

		// if (GenericValidator.isLong(msg_rec_id)) {
		//
		// } else {
		// MsgReceiver mr = new MsgReceiver();
		// mr.setMsg_id(entity.getId());
		// mr.setUser_id(entity.getUser_id());
		// mr.setReceiver_user_id(ui.getId());
		// mr.setReceiver_user_mobile(ui.getUser_name() + "(" + ui.getMobile() + ")");
		// mr.setIs_del(0);
		// mr.setIs_read(1);
		// mr.setIs_reply(0);
		// super.getFacade().getMsgReceiverService().createMsgReceiver(mr);
		// }

		super.copyProperties(form, entity);
		dynaBean.set("msg_id", msg_id);

		return mapping.findForward("view");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			MsgReceiver entity = new MsgReceiver();
			entity.setId(new Integer(id));
			getFacade().getMsgReceiverService().removeMsgReceiver(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			MsgReceiver entity = new MsgReceiver();
			entity.getMap().put("pks", pks);
			getFacade().getMsgReceiverService().removeMsgReceiver(entity);
			saveMessage(request, "entity.deleted");
		}
		JSONObject data = new JSONObject();
		data.put("ret", "1");
		data.put("msg", "删除成功！");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward getMsgListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		// getsonSysModuleList(request, dynaBean);

		String trade_index = (String) dynaBean.get("trade_index");
		String order_type = (String) dynaBean.get("order_type");
		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		MsgReceiver entity = new MsgReceiver();

		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String read_state = (String) dynaBean.get("read_state");

		super.copyProperties(entity, form);

		entity.setReceiver_user_id(ui.getId());
		entity.getMap().put("st_date", st_date);
		entity.getMap().put("en_date", en_date);
		if (GenericValidator.isLong(read_state)) {
			if (read_state.equals("0")) {// 未读
				entity.getMap().put("is_not_read", "true");
			}
			if (read_state.equals("1")) {// 已读
				entity.setIs_read(1);
			}
		}

		long recordCount = getFacade().getMsgReceiverService().getMsgReceiverCount(entity);
		pager.init(recordCount, new Integer(pageSize), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<MsgReceiver> msgRecList = getFacade().getMsgReceiverService().getMsgReceiverPaginatedList(entity);

		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		// String ctx = super.getCtxPath(request);
		if ((null != msgRecList) && (msgRecList.size() > 0)) {
			for (MsgReceiver mr : msgRecList) {
				JSONObject map = new JSONObject();
				map.put("id", mr.getId());
				map.put("msg_id", mr.getMsg_id());
				map.put("msg_title", mr.getMsg_title());
				map.put("is_read", mr.getIs_read());
				map.put("user_name", mr.getUser_name());
				map.put("send_time", DateTools.getStringDate(mr.getSend_time(), "yyyy-MM-dd"));

				dataLoadList.add(map);
			}
			datas.put("ret", "1");
			datas.put("msg", "查询成功");
			datas.put("appendMore", "0");
			if (msgRecList.size() == Integer.valueOf(pageSize)) {
				datas.put("appendMore", "1");
			}
			datas.put("dataList", dataLoadList.toString());
		} else {
			datas.put("ret", "0");
			datas.put("msg", "已全部加载");
		}

		logger.info("===datas:{}", datas.toString());
		super.renderJson(response, datas.toString());

		return null;

	}
}
