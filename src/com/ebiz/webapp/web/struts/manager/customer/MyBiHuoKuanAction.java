package com.ebiz.webapp.web.struts.manager.customer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Msg;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.UserMoneyApply;
import com.ebiz.webapp.web.Keys;

/**
 * @author Li,Yuan
 * @version 2012-02-22
 */
public class MyBiHuoKuanAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.viewHuoKuan(mapping, form, request, response);
	}

	public ActionForward viewHuoKuan(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		saveToken(request);
		// super.setPublicInfoListWithEntpAndCustomer(request);
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		UserInfo entity = new UserInfo();
		entity.setId(ui.getId());
		entity = getFacade().getUserInfoService().getUserInfo(entity);
		if (null == entity) {
			String msg = "用户名不存在或者已经被删除！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='Index.do?method=welcome'}");
			return null;
		}
		super.copyProperties(form, entity);

		return new ActionForward("/customer/MyBiHuoKuan/viewHuoKuan.jsp");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		dynaBean.set("order_value", "0");
		dynaBean.set("province", Keys.P_INDEX_INIT);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		UserInfo ui = super.getUserInfoFromSession(request);

		Pager pager = (Pager) dynaBean.get("pager");

		UserBiRecord entity = new UserBiRecord();
		super.copyProperties(entity, form);
		// if (StringUtils.isBlank(bi_chu_or_ru)) {
		// entity.setBi_chu_or_ru(1);
		// }
		// 页面显示条件
		entity.setAdd_user_id(ui.getId());
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.getMap()
				.put("bi_type_in", Keys.BiType.BI_TYPE_300.getIndex());

		String bi_get_types = (String) dynaBean.get("bi_get_types");
		entity.getMap().put("bi_get_types", bi_get_types);// 余额转让记录

		String begin_date = (String) dynaBean.get("begin_date");
		String end_date = (String) dynaBean.get("end_date");
		entity.getMap().put("begin_date", begin_date);
		entity.getMap().put("end_date", end_date);

		entity.getMap().put("order_by_info", "add_date desc,");
		// 分页
		Integer recordCount = getFacade().getUserBiRecordService().getUserBiRecordCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());
		// 查询集合
		List<UserBiRecord> userBiRecordlList = getFacade().getUserBiRecordService()
				.getUserBiRecordPaginatedList(entity);
		List<UserBiRecord> HuoKuanRecord = new ArrayList<UserBiRecord>();
		if (null != userBiRecordlList && userBiRecordlList.size() > 0) {
			for (UserBiRecord temp : userBiRecordlList) {
				if (null != temp.getOrder_id()) {
					OrderInfo orderInfo = new OrderInfo();
					orderInfo.setId(temp.getOrder_id());
					orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
					if (null != orderInfo) {
						temp.getMap().put("buy_name", orderInfo.getAdd_user_name());
						temp.getMap().put("trade_index", orderInfo.getTrade_index());
					}
				}
				HuoKuanRecord.add(temp);
			}
		}

		request.setAttribute("biGetTypes", Keys.BiGetType.values());

		request.setAttribute("userBiRecordlList", HuoKuanRecord);
		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		UserInfo userInfo = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String info_state = (String) dynaBean.get("info_state");
		String msg_title_like = (String) dynaBean.get("msg_title_like");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

		Msg entity = new Msg();
		super.copyProperties(entity, form);

		entity.setMsg_type(10);
		entity.setUser_id(userInfo.getId());
		entity.setSend_time(new Date());

		super.getFacade().getMsgService().createMsg(entity);
		saveMessage(request, "entity.inerted");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			EntpInfo entity = new EntpInfo();
			entity.setId(new Integer(id));
			entity = getFacade().getEntpInfoService().getEntpInfo(entity);

			// the line below is added for pagination
			entity.setQueryString(super.serialize(request, "id", "method"));
			// end
			super.copyProperties(form, entity);
			setprovinceAndcityAndcountryToFrom(dynaBean, Integer.valueOf(entity.getP_index()));

		}
		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (GenericValidator.isLong(id)) {
			UserMoneyApply entity = new UserMoneyApply();
			entity.setId(new Integer(id));
			entity.setCash_type((Keys.CASH_TYPE.CASH_TYPE_30.getIndex()));
			entity = getFacade().getUserMoneyApplyService().getUserMoneyApply(entity);
			super.copyProperties(form, entity);

			UserInfo userInfo = new UserInfo();
			userInfo.setId(entity.getUser_id());
			userInfo = getFacade().getUserInfoService().getUserInfo(userInfo);
			request.setAttribute("real_name", userInfo.getReal_name());

		}
		return mapping.findForward("view");
	}
}
