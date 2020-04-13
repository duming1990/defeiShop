package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.BaseAttribute;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommInfoTags;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.util.IndexCartesianProductIterator;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.CommZyType;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.ZipUtils;

/**
 * @author Li,Yuan
 * @version 2014-05-21
 */

public class CustomerSuperMarketCommInfoAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}
		EntpInfo entpInfo = super.getEntpInfo(userInfo.getOwn_entp_id());
		if (null == entpInfo) {
			String msg = "功能还未开通，请向管理员申请开通！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');}");
			return null;
		}
		if (entpInfo.getEntp_type() != Keys.EntpType.ENTP_TYPE_30.getIndex()) {
			String msg = "功能还未开通，请向管理员申请开通！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');}");
			return null;
		}
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String today_date = (String) dynaBean.get("today_date");

		UserInfo userInfoQuery = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		EntpInfo entpInfoQuery = super.getEntpInfo(userInfoQuery.getOwn_entp_id());

		CommInfo entity = new CommInfo();

		super.copyProperties(entity, form);
		entity.setOwn_entp_id(entpInfoQuery.getId());
		entity.setComm_type(Keys.CommType.COMM_TYPE_9.getIndex());

		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		}
		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}
		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.setCls_name(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		for (CommInfo ci : entityList) {
			EntpInfo entpInfo = super.getEntpInfo(ci.getOwn_entp_id(), null, null);
			if (null != entpInfo) {
				ci.getMap().put("entp_name", entpInfo.getEntp_name());
			}
			if (ci.getIs_zingying().intValue() != 0) {
				ci.getMap().put("commZyName", CommZyType.getName(ci.getIs_zingying()));
			}

			UserInfo userInfo = super.getUserInfo(ci.getAdd_user_id());
			if (null != userInfo) {
				ci.getMap().put("mobile", userInfo.getMobile());
				ci.getMap().put("user_name", userInfo.getUser_name());
				ci.getMap().put("real_name", userInfo.getReal_name());
			}
			if (null != ci.getAudit_user_id()) {
				UserInfo auditUserInfo = super.getUserInfo(ci.getAudit_user_id());
				if (null != auditUserInfo) {
					ci.getMap().put("auditUserInfo", auditUserInfo.getUser_name());
				}
			}
			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(ci.getId().toString());
			param_ctp.getMap().put("order_by_inventory_asc", "true");
			List<CommTczhPrice> CommTczhPriceList = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);
			if ((null != CommTczhPriceList) && (CommTczhPriceList.size() > 0)) {
				ci.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
			}

			// 是否设置扶贫对象
			if (ci.getIs_aid() == 1 && ci.getAid_scale().compareTo(new BigDecimal(0)) == 1) {
				CommInfoPoors commInfoPoors = new CommInfoPoors();
				commInfoPoors.setComm_id(ci.getId());
				int count = super.getFacade().getCommInfoPoorsService().getCommInfoPoorsCount(commInfoPoors);
				if (count > 0) {
					ci.getMap().put("set_poors", count);
				}
			}
		}
		request.setAttribute("entityList", entityList);
		request.setAttribute("CommZyTypeList", Keys.CommZyType.values());
		return mapping.findForward("list");

	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		saveToken(request);
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		List<PdImgs> CommImgsList = new ArrayList<PdImgs>();
		request.setAttribute("CommImgsListCount", 0);
		for (int i = 0; i < 5; i++) {
			PdImgs pdImgs = new PdImgs();
			CommImgsList.add(pdImgs);
		}

		request.setAttribute("CommImgsList", CommImgsList);

		super.getSessionId(request);

		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("city", Keys.P_INDEX_CITY);
		dynaBean.set("p_index", Keys.P_INDEX_CITY);
		dynaBean.set("own_entp_id", Keys.ENTP_ID);
		dynaBean.set("is_sell", "1");

		dynaBean.set("up_date", new Date());
		dynaBean.set("down_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));

		request.setAttribute("fanXianTypes", Keys.FanXianTypeComm.values());

		// 客服QQ
		if (ui.getOwn_entp_id() != null) {
			EntpInfo a = getEntpInfo(ui.getOwn_entp_id());
			if (null != a) {
				dynaBean.set("qq", a.getQq());
			}
		}
		// 一个商品最多扶贫对象数量
		BaseData baseData1901 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1901.getIndex());
		request.setAttribute("baseData1901", baseData1901);

		request.setAttribute("reBate1001",
				super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex()).getPre_number2());
		request.setAttribute("reBate1002",
				super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex()).getPre_number2());

		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			CommInfo entity = new CommInfo();
			super.copyProperties(form, entity);
			return list(mapping, form, request, response);
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String id = (String) dynaBean.get("id");
		String audit_state = (String) dynaBean.get("audit_state");
		String comm_type = (String) dynaBean.get("comm_type");
		String is_audit = (String) dynaBean.get("is_audit");

		String[] poor_ids = request.getParameterValues("poor_ids");// 扶贫对象数组

		Date date = new Date();
		CommInfo entity = new CommInfo();

		String freight_id = (String) dynaBean.get("freight_id");
		if (StringUtils.isNotBlank(freight_id)) {
			if (freight_id.equals("none")) {
				entity.getMap().put("freight_id_is_null", true);
			}
		}

		List<PdImgs> commImgsList = new ArrayList<PdImgs>();
		super.copyProperties(entity, form);

		BigDecimal rebate_scale = new BigDecimal(0);
		if (null != entity.getRebate_scale()) {
			rebate_scale = entity.getRebate_scale();
		}
		BigDecimal aid_scale = new BigDecimal(0);
		if (null != entity.getAid_scale()) {
			aid_scale = entity.getAid_scale();
		}
		if (rebate_scale.add(aid_scale).compareTo(new BigDecimal(100)) >= 0) {// 返利比例+扶贫比例不能超过100
			saveMessage(request, "errors.range", entity.getRebate_scale().add(entity.getAid_scale()).toPlainString(),
					"0", "100");
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&mod_id=" + mod_id);
			pathBuffer.append("&comm_type=" + comm_type);
			pathBuffer.append("&");
			pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;
		}

		List<CommInfoPoors> poorList = new ArrayList<CommInfoPoors>();
		if (ArrayUtils.isNotEmpty(poor_ids)) {
			for (String poor_id : poor_ids) {
				CommInfoPoors poor = new CommInfoPoors();
				poor.setPoor_id(Integer.valueOf(poor_id));
				poorList.add(poor);
			}
		}
		if (null != poorList && poorList.size() > 0) {
			entity.setPoorsList(poorList);
		}
		if (null != entity.getIs_rebate() && 0 == entity.getIs_rebate() && null != entity.getIs_aid()
				&& 0 == entity.getIs_aid()) {// 非返现和扶贫商品，综合排序下降
			entity.setMultiple_order_value(0);
		} else {// 计算返现和扶贫商品的综合排序
			int multiple_order_value = 0;
			if (null != entity.getIs_rebate() && 1 == entity.getIs_rebate()) {
				multiple_order_value += entity.getRebate_scale().multiply(new BigDecimal(100 * 65)).intValue();
			}
			if (null != entity.getIs_aid() && 1 == entity.getIs_aid()) {
				multiple_order_value += entity.getAid_scale().multiply(new BigDecimal(100 * 35)).intValue();
			}
			entity.setMultiple_order_value(multiple_order_value);
		}

		if (StringUtils.isNotBlank(id)) {
			entity.setUpdate_date(date);
			entity.setUpdate_user_id(ui.getId());
			if (StringUtils.isNotBlank(audit_state)) {
				entity.setAudit_user_id(ui.getId());
				entity.setAudit_date(new Date());
				if (Integer.valueOf(audit_state) == 1) {// 审核通过
					entity.getMap().put("send_audit_msg", 1);
				}
				if (Integer.valueOf(audit_state) == -1) {// 审核不通过
					entity.getMap().put("send_audit_msg", -1);
				}
			}
			super.getFacade().getCommInfoService().modifyCommInfo(entity);
			saveMessage(request, "entity.updated");

			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&mod_id=" + mod_id);
			pathBuffer.append("&comm_type=" + comm_type);
			pathBuffer.append("&");
			pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;

		} else {
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_date(date);
			entity.setIs_del(0);
			entity.setAudit_state(1);
			entity.setAudit_user_id(ui.getId());
			entity.setAudit_date(date);
			entity.setIs_zingying(Keys.CommZyType.COMM_ZY_TYPE_2.getIndex());

			Integer insert_id = super.getFacade().getCommInfoService().createCommInfo(entity);
			saveMessage(request, "entity.inerted");

			saveToken(request);
			dynaBean.set("comm_id", insert_id);
			dynaBean.set("comm_type", comm_type);

		}
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&comm_type=" + comm_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;

	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_type = (String) dynaBean.get("comm_type");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.close();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		CommInfo entity = new CommInfo();
		Date date = new Date();
		entity.setIs_del(1);
		entity.setDel_date(date);
		entity.setDel_user_id(ui.getId());
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id) && StringUtils.isNotBlank(cls_id)) {
			entity.setId(Integer.valueOf(id));
			entity.setCls_id(Integer.valueOf(cls_id));
			super.getFacade().getCommInfoService().modifyCommInfo(entity);

			saveMessage(request, "entity.deleted");

		} else if (ArrayUtils.isNotEmpty(pks)) {

			String[] pd_ids = new String[pks.length];
			String[] cls_ids = new String[pks.length];
			for (int i = 0; i < pks.length; i++) {
				pd_ids[i] = pks[i].split("_")[0];
				cls_ids[i] = pks[i].split("_")[1];
			}

			entity.getMap().put("pks", pd_ids);
			entity.getMap().put("cls_ids", cls_ids);
			super.getFacade().getCommInfoService().modifyCommInfo(entity);

			saveMessage(request, "entity.deleted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&comm_type=" + comm_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "cls_id", "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		super.getSessionId(request);

		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);

			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			} else {
				List<PdImgs> CommImgsList = entity.getCommImgsList();
				for (int i = 1; i <= CommImgsList.size(); i++) {
					dynaBean.set("base_files" + i + "_file", CommImgsList.get(i - 1).getFile_path());
				}
				int CommImgsListCount = 0;
				if (CommImgsList != null) {
					CommImgsListCount = CommImgsList.size();
				}
				request.setAttribute("CommImgsListCount", CommImgsListCount);

				if (CommImgsList != null) {
					if (CommImgsList.size() < 5) {// 添加CommImgsList
						for (int i = 0; i < (5 - CommImgsListCount); i++) {
							PdImgs pdImgs = new PdImgs();
							CommImgsList.add(pdImgs);
						}
					}
				}

				// 重新赛入CommImgsList
				entity.setPdImgsList(CommImgsList);
				request.setAttribute("CommImgsList", CommImgsList);

				EntpInfo entpInfo = super.getEntpInfo(entity.getOwn_entp_id(), null, null);
				if (null != entpInfo && StringUtils.isNotBlank(entpInfo.getEntp_name())) {
					dynaBean.set("entp_name", entpInfo.getEntp_name());
					request.setAttribute("p_index", entpInfo.getP_index());
				}
				// 物流
				if (null != entity.getFreight_id()) {
					Freight fre = super.getFreightInfo(entity.getFreight_id());
					if (null != fre) {
						dynaBean.set("fre_title", fre.getFre_title());
					}
				}

				// 品牌
				if (null != entity.getBrand_id()) {
					BaseBrandInfo baseBrandInfo = super.getBaseBrandInfo(entity.getBrand_id());
					if (null != baseBrandInfo) {
						dynaBean.set("brand_name", baseBrandInfo.getBrand_name());
					}
				}
				request.setAttribute("entity", entity);
				// 客服QQ
				EntpInfo a = getEntpInfo(entity.getAdd_user_id());
				if (null != a) {
					dynaBean.set("qq", a.getQq());
				}
			}
			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);

			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(id);
			List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);

			request.setAttribute("reBate1001",
					super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex()).getPre_number2());
			request.setAttribute("reBate1002",
					super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex()).getPre_number2());

			request.setAttribute("list_CommTczhPrice", list_CommTczhPrice);

			// 商品频道
			CommInfoTags commInfoTags = new CommInfoTags();
			commInfoTags.setComm_id(Integer.valueOf(id));
			List<CommInfoTags> commInfoTagsList = super.getFacade().getCommInfoTagsService()
					.getCommInfoTagsList(commInfoTags);
			if (null != commInfoTagsList && commInfoTagsList.size() > 0) {
				String tag_ids_str = ",";
				for (CommInfoTags t : commInfoTagsList) {
					tag_ids_str += t.getTag_id().toString() + ",";
				}
				request.setAttribute("tag_ids_str", tag_ids_str);
			}

			// 扶贫对象
			CommInfoPoors commInfoPoors = new CommInfoPoors();
			commInfoPoors.setComm_id(Integer.valueOf(id));
			List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
					.getCommInfoPoorsList(commInfoPoors);
			if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
				String temp_poor_ids = ",";
				for (CommInfoPoors temp : commInfoPoorsList) {
					temp_poor_ids += temp.getPoor_id().toString() + ",";
				}
				request.setAttribute("temp_poor_ids", temp_poor_ids);
			}
			request.setAttribute("commInfoPoorsList", commInfoPoorsList);

			// 一个商品最多扶贫对象数量
			BaseData baseData1901 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1901.getIndex());
			request.setAttribute("baseData1901", baseData1901);
			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward copy_add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String from_id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(from_id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(from_id)) {
			CommInfo from_entity = new CommInfo();
			from_entity.setId(Integer.valueOf(from_id));
			from_entity = getFacade().getCommInfoService().getCommInfo(from_entity);
			Date date = new Date();

			from_entity.setId(null); // 因为是复制,所以保存时id设为空,新插入记录
			// entity.setOwn_entp_id(uitOwn_entp_id()); //admin端为页面选择的entp_id
			from_entity.setComm_name(from_entity.getComm_name() + "  复制");
			from_entity.setAdd_date(date);
			from_entity.setAdd_user_id(ui.getId());
			from_entity.setAdd_user_name(ui.getUser_name());
			from_entity.setIs_del(0);
			from_entity.setAudit_state(0); // 后台默认待审核
			from_entity.setAudit_user_id(ui.getId());
			from_entity.setAudit_date(date);

			from_entity.getMap().put("from_id", from_id);

			Integer to_comm_id = getFacade().getCommInfoService().createCommInfo(from_entity);

			dynaBean.set("id", String.valueOf(to_comm_id));
			return this.edit(mapping, form, request, response);
		} else {
			saveError(request, "errors.Integer", from_id);
			return mapping.findForward("list");
		}
	}

	public ActionForward hf(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		CommInfo entity = new CommInfo();
		entity.setIs_del(0);
		entity.setId(Integer.valueOf(id));
		super.getFacade().getCommInfoService().modifyCommInfo(entity);

		// saveMessage(request, "entity.deleted");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);

			UserInfo userInfo = super.getUserInfo(entity.getAdd_user_id());
			if (null != userInfo) {
				request.setAttribute("userInfo", userInfo);
			}

			super.copyProperties(form, entity);
			dynaBean.set("is_freeship", 0);

			request.setAttribute("fanXianTypes", Keys.FanXianTypeComm.values());
			request.setAttribute("commTypeList", Keys.CommType.values());

			if (null != entity.getFreight_id()) {
				Freight fre = super.getFreightInfo(entity.getFreight_id());
				if (null != fre) {
					dynaBean.set("fre_title", fre.getFre_title());
				}
			}

			if (null != entity.getBrand_id()) {
				BaseBrandInfo baseBrandInfo = super.getBaseBrandInfo(entity.getBrand_id());
				if (null != baseBrandInfo && StringUtils.isNotBlank(baseBrandInfo.getBrand_name())) {
					dynaBean.set("brand_name", baseBrandInfo.getBrand_name());
				}
			}

			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "8")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;

		String comm_id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(comm_id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(comm_id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(comm_id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);
			if (null != entity) {
				if (null != entity.getFreight_id()) {
					Freight fre = super.getFreightInfo(entity.getFreight_id());
					if (null != fre) {
						dynaBean.set("fre_title", fre.getFre_title());
					}
				}

				if (null != entity.getBrand_id()) {
					BaseBrandInfo baseBrandInfo = super.getBaseBrandInfo(entity.getBrand_id());
					if (null != baseBrandInfo && StringUtils.isNotBlank(baseBrandInfo.getBrand_name())) {
						dynaBean.set("brand_name", baseBrandInfo.getBrand_name());
					}
				}

				UserInfo userInfo = super.getUserInfo(entity.getAdd_user_id());
				if (null != userInfo) {
					request.setAttribute("userInfo", userInfo);
				}

				CommTczhPrice param_ctp = new CommTczhPrice();
				param_ctp.setComm_id(comm_id);
				List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
						.getCommTczhPriceList(param_ctp);
				if ((list_CommTczhPrice != null) && (list_CommTczhPrice.size() > 0)) {

					for (CommTczhPrice ctp : list_CommTczhPrice) {
						CommTczhAttribute param_ctattr = new CommTczhAttribute();
						param_ctattr.setComm_tczh_id(ctp.getId());
						param_ctattr.setComm_id(comm_id);
						param_ctattr.getMap().put("orderby_order_value_asc", "true");
						List<CommTczhAttribute> list_CommTczhAttribute = super.getFacade().getCommTczhAttributeService()
								.getCommTczhAttributeList(param_ctattr);
						List<String> attr_tczh_ids = new ArrayList<String>();
						List<String> attr_tczh_names = new ArrayList<String>();
						for (CommTczhAttribute temp_cta : list_CommTczhAttribute) {
							BaseAttributeSon param_bas = new BaseAttributeSon();
							param_bas.setId(Integer.valueOf(temp_cta.getAttr_id()));
							BaseAttributeSon bas = super.getFacade().getBaseAttributeSonService()
									.getBaseAttributeSon(param_bas);
							if (bas != null) {
								temp_cta.setAttr_name(bas.getAttr_name());
							}
							attr_tczh_ids.add(temp_cta.getAttr_id().toString());
							attr_tczh_names.add(temp_cta.getAttr_name());
						}

						ctp.getMap().put("attr_tczh_ids", StringUtils.join(attr_tczh_ids, ","));
						ctp.getMap().put("attr_tczh_names", StringUtils.join(attr_tczh_names, " "));

					}
					request.setAttribute("list_CommTczhPrice", list_CommTczhPrice); // 套餐价格及对应各属性
				}

				request.setAttribute("reBate1001",
						super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex())
								.getPre_number2());
				request.setAttribute("reBate1002",
						super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex())
								.getPre_number2());

				request.setAttribute("commTypeList", Keys.CommType.values());

				// 扶贫对象
				CommInfoPoors commInfoPoors = new CommInfoPoors();
				commInfoPoors.setComm_id(Integer.valueOf(comm_id));
				List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
						.getCommInfoPoorsList(commInfoPoors);
				if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
					String temp_poor_ids = ",";
					for (CommInfoPoors temp : commInfoPoorsList) {
						temp_poor_ids += temp.getPoor_id().toString() + ",";
					}
					request.setAttribute("temp_poor_ids", temp_poor_ids);
				}
				request.setAttribute("commInfoPoorsList", commInfoPoorsList);

				entity.setQueryString(super.serialize(request, "id", "method"));
				super.copyProperties(form, entity);
			}
			return new ActionForward("/../manager/admin/CommInfo/audit.jsp");
		} else {
			saveError(request, "errors.Integer", comm_id);
			return mapping.findForward("list");
		}
	}

	public List<CommInfo> getCommInfo() {
		CommInfo entity = new CommInfo();
		entity.setIs_del(0);
		return super.getFacade().getCommInfoService().getCommInfoList(entity);
	}

	public ActionForward deleteFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			PdImgs entity = new PdImgs();
			entity.setId(new Integer(id));
			super.getFacade().getPdImgsService().removePdImgs(entity);
			saveMessage(request, "entity.deleted");
		}

		super.renderText(response, "success");
		return null;
	}

	/**
	 * @author minyg
	 * @desc 弹出框选择供应商/网点
	 * @since 2013-09-26
	 */

	public ActionForward chooseEntpInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String wl_name_like = (String) dynaBean.get("wl_name_like");
		String _entp_type = (String) dynaBean.get("_entp_type");
		String own_sys = (String) dynaBean.get("own_sys");
		EntpInfo entity = new EntpInfo();
		super.copyProperties(entity, form);
		entity.setAudit_state(1);
		entity.setIs_del(0);

		if (null == own_sys) {
			entity.setOwn_sys(0);
			dynaBean.set("own_sys", "0");
		}
		if (StringUtils.isNotBlank(entp_name_like)) {
			entity.getMap().put("entp_name_like", entp_name_like);
		}

		if (StringUtils.isNotBlank(_entp_type)) {
			dynaBean.set("_entp_type", _entp_type);
			if (_entp_type.equals("5")) {
				WlCompInfo wlCompInfo = new WlCompInfo();
				wlCompInfo.setIs_del(0);
				wlCompInfo.setIs_collaborate(1);
				if (StringUtils.isNotBlank(wl_name_like)) {
					wlCompInfo.getMap().put("wl_comp_name_like", wl_name_like);
				}
				Integer recordCount = getFacade().getWlCompInfoService().getWlCompInfoCount(wlCompInfo);
				pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
				wlCompInfo.getRow().setFirst(pager.getFirstRow());
				wlCompInfo.getRow().setCount(pager.getRowCount());
				List<WlCompInfo> entityList = super.getFacade().getWlCompInfoService()
						.getWlCompInfoPaginatedList(wlCompInfo);
				request.setAttribute("wlCompInfoList", entityList);
				request.setAttribute("wl_comp_info", true);
				return new ActionForward("/../manager/admin/CommInfo/choose_wlCompInfo.jsp");

			} else {
				Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
				pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
				entity.getRow().setFirst(pager.getFirstRow());
				entity.getRow().setCount(pager.getRowCount());

				List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
				request.setAttribute("entityList", entityList);
				return new ActionForward("/../manager/admin/CommInfo/choose_entpinfo.jsp");
			}
		} else {
			Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
			pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
			entity.getRow().setFirst(pager.getFirstRow());
			entity.getRow().setCount(pager.getRowCount());

			List<EntpInfo> entityList = super.getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
			request.setAttribute("entityList", entityList);
			return new ActionForward("/../manager/admin/CommInfo/choose_entpinfo.jsp");
		}

	}

	public ActionForward getComm_no(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String cls_id = (String) dynaBean.get("cls_id");
		logger.info("cls_id:{" + cls_id + "}");
		JSONObject result = new JSONObject();
		String comm_no = "";
		if (StringUtils.isNotBlank(cls_id)) {
			BaseClass baseClass = new BaseClass();
			baseClass.setCls_id(Integer.valueOf(cls_id));
			baseClass.setIs_del(0);
			baseClass = super.getFacade().getBaseClassService().getBaseClass(baseClass);
			if (null != baseClass) {
				CommInfo commInfo = new CommInfo();
				Integer count = super.getFacade().getCommInfoService().getCommInfoCount(commInfo);
				if (StringUtils.isBlank(baseClass.getCls_code())) {// 如果为空的话，主动调用
					Integer step_2 = 1;

					BaseClass baseClass2 = new BaseClass();
					baseClass2.setCls_level(2);
					List<BaseClass> baseClass2List = getFacade().getBaseClassService().getBaseClassList(baseClass2);
					for (BaseClass bp2 : baseClass2List) {

						String level_1 = StringUtils.leftPad(String.valueOf(step_2++), 2, "0");
						String level_2 = "00";
						String level_3 = "000";
						String clscode2 = level_1.concat(level_2).concat(level_3);

						BaseClass tmp_update_2 = new BaseClass();
						tmp_update_2.setCls_id(bp2.getCls_id());
						tmp_update_2.setCls_code(clscode2);
						getFacade().getBaseClassService().modifyBaseClass(tmp_update_2);

						BaseClass baseClass3 = new BaseClass();
						baseClass3.setCls_level(3);
						baseClass3.setPar_id(tmp_update_2.getCls_id());
						List<BaseClass> baseClass3List = getFacade().getBaseClassService().getBaseClassList(baseClass3);
						Integer step_3 = 1;
						for (BaseClass bp3 : baseClass3List) {
							level_1 = StringUtils.substring(clscode2, 0, 2);
							level_2 = StringUtils.leftPad(String.valueOf(step_3++), 2, "0");
							level_3 = "000";

							String clscode3 = level_1.concat(level_2).concat(level_3);
							BaseClass tmp_update_3 = new BaseClass();
							tmp_update_3.setCls_id(bp3.getCls_id());
							tmp_update_3.setCls_code(clscode3);
							getFacade().getBaseClassService().modifyBaseClass(tmp_update_3);

							BaseClass baseClass4 = new BaseClass();
							baseClass4.setCls_level(4);
							baseClass4.setPar_id(tmp_update_3.getCls_id());
							List<BaseClass> baseClass4List = getFacade().getBaseClassService()
									.getBaseClassList(baseClass4);
							Integer step_4 = 1;
							for (BaseClass bp4 : baseClass4List) {
								level_1 = StringUtils.substring(clscode3, 0, 2);
								level_2 = StringUtils.substring(clscode3, 2, 4);
								level_3 = StringUtils.leftPad(String.valueOf(step_4++), 3, "0");

								String clscode4 = level_1.concat(level_2).concat(level_3);
								BaseClass tmp_update_4 = new BaseClass();
								tmp_update_4.setCls_id(bp4.getCls_id());
								tmp_update_4.setCls_code(clscode4);
								getFacade().getBaseClassService().modifyBaseClass(tmp_update_4);
							}

						}
					}
				}
				BaseClass bpz = new BaseClass();
				bpz.setCls_id(Integer.valueOf(cls_id));
				bpz.setIs_del(0);
				bpz = super.getFacade().getBaseClassService().getBaseClass(bpz);
				if (null != bpz) {
					comm_no = bpz.getCls_code() + StringUtils.leftPad(String.valueOf(count), 4, "0");
				}
				logger.info(comm_no);
				result.put("comm_no", comm_no);
			}
		}
		logger.info(result.toString());
		super.render(response, result.toString(), "text/x-json;charset=UTF-8");

		return null;
	}

	public ActionForward toExcelForCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		super.setBaseDataListToSession(10, request);// 用户类型

		DynaBean dynaBean = (DynaBean) form;
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		CommInfo commInfo = new CommInfo();
		StringBuffer queryCondition = new StringBuffer();
		super.copyProperties(commInfo, form);
		if (StringUtils.isNotBlank(comm_name_like)) {
			commInfo.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			commInfo.getMap().put("comm_no_like", comm_no_like);
		}

		commInfo.setIs_del(0);
		Map<String, Object> model = new HashMap<String, Object>();
		List<CommInfo> commInfoList = super.getFacade().getCommInfoService().getCommInfoList(commInfo);
		if ((null != commInfoList) && (commInfoList.size() > 0)) {
			model.put("commInfoList", commInfoList);
		}
		logger.info("query:{}" + queryCondition.toString());
		model.put("title", "商品信息表");
		model.put("queryCondition", queryCondition.toString());
		String content = getFacade().getTemplateService().getContent("commInfo/commInfo_list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("商品信息表.xls");

		response.setCharacterEncoding("GBK");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}

	public List<Freight> getFreightList() {
		Freight f = new Freight();
		f.setIs_del(0);
		List<Freight> list = super.getFacade().getFreightService().getFreightList(f);
		return list;
	}

	public List<Freight> getFreightList(Integer entp_id) {
		Freight f = new Freight();
		f.setIs_del(0);
		f.setEntp_id(entp_id);
		List<Freight> list = super.getFacade().getFreightService().getFreightList(f);
		return list;
	}

	public ActionForward showFreight(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		Freight f = new Freight();
		f.setIs_del(0);
		String entp_id = (String) dynaBean.get("entp_id");
		f.setEntp_id(Integer.valueOf(entp_id));
		List<Freight> list = super.getFacade().getFreightService().getFreightList(f);
		StringBuffer buffer = new StringBuffer();
		String text = "";
		for (Freight f1 : list) {
			buffer.append(f1.getId()).append("#@#").append(f1.getFre_title()).append("#$#");
		}
		if (buffer.length() != 0) {
			text = buffer.toString().substring(0, buffer.toString().length() - 3);
		}

		super.renderText(response, text);

		return null;
	}

	public ActionForward edittcfw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String price_ref = (String) dynaBean.get("price_ref");
		String comm_type = (String) dynaBean.get("comm_type");
		saveToken(request);

		// 套餐管理
		// 套餐管理
		CommTczhPrice param_ctp = new CommTczhPrice();
		param_ctp.setComm_id(comm_id);
		List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(param_ctp);
		request.setAttribute("list_CommTczhPrice", list_CommTczhPrice); // 套餐价格及对应各属性

		// comm_id
		dynaBean.set("comm_id", comm_id);
		dynaBean.set("price_ref", price_ref);

		dynaBean.set("queryString", super.serialize(request, "id", "method"));

		// if
		// (comm_type.equals(String.valueOf(Keys.CommType.COMM_TYPE_4.getIndex()))
		// ||
		// comm_type.equals(String.valueOf(Keys.CommType.COMM_TYPE_5.getIndex())))
		// {
		// return new
		// ActionForward("/../manager/admin/CommInfo/tcfwformForType4.jsp");
		// }

		return new ActionForward("/../manager/admin/CommInfo/tcfwform.jsp");
	}

	/**
	 * @author minyg
	 * @versiion 2013-10-06
	 * @desc 商品自己的属性管理
	 */

	public ActionForward listattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		BaseAttribute param_battr = new BaseAttribute(); // 取商品拥有的属性和子属性
		param_battr.setIs_del(0);
		param_battr.setLink_id(Integer.valueOf(comm_id));
		param_battr.setAttr_scope(1); // 1为商品套餐属性
		List<BaseAttribute> list_BaseAttribute = super.getFacade().getBaseAttributeService()
				.getBaseAttributeList(param_battr);
		for (BaseAttribute battr : list_BaseAttribute) {
			BaseAttributeSon param_battrson = new BaseAttributeSon();
			param_battrson.setAttr_id(battr.getId());
			List<BaseAttributeSon> list_baseAttributeSon = super.getFacade().getBaseAttributeSonService()
					.getBaseAttributeSonList(param_battrson);
			battr.getMap().put("list_baseAttributeSon", list_baseAttributeSon);
		}
		request.setAttribute("list_BaseAttribute", list_BaseAttribute);

		BaseAttribute param_battr2 = new BaseAttribute(); // 取商品拥有的属性和子属性
		param_battr2.setIs_del(0);
		param_battr2.setLink_id(Integer.valueOf(comm_id));
		param_battr2.setAttr_scope(1); // 1为商品套餐属性
		param_battr2.getMap().put("link_has_attr_id_not_null", true);
		List<BaseAttribute> list_BaseAttribute2 = super.getFacade().getBaseAttributeService()
				.getBaseAttributeList(param_battr2);
		String ids = "";
		int i = 0;
		for (BaseAttribute battr : list_BaseAttribute2) {
			ids += battr.getLink_has_attr_id();
			if (i + 1 != list_BaseAttribute2.size()) {
				ids += ",";
			}
			i++;
		}
		request.setAttribute("ids", ids);

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		request.setAttribute("commInfo", commInfo);

		return new ActionForward("/../manager/admin/CommInfo/attrlist.jsp");
	}

	public ActionForward addattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		request.setAttribute("commInfo", commInfo);
		dynaBean.set("comm_id", comm_id);

		BaseAttribute param_battr = new BaseAttribute(); // 取商品拥有的属性和子属性
		param_battr.setIs_del(0);
		param_battr.setLink_id(Integer.valueOf(comm_id));
		param_battr.setAttr_scope(1); // 1为商品套餐属性
		param_battr.getMap().put("link_has_attr_id_not_null", true);
		List<BaseAttribute> list_BaseAttribute = super.getFacade().getBaseAttributeService()
				.getBaseAttributeList(param_battr);
		String ids = "";
		int i = 0;
		for (BaseAttribute battr : list_BaseAttribute) {
			ids += battr.getLink_has_attr_id();
			if (i + 1 != list_BaseAttribute.size()) {
				ids += ",";
			}
			i++;
		}
		request.setAttribute("ids", ids);

		return new ActionForward("/../manager/admin/CommInfo/attrform.jsp");
	}

	public ActionForward saveattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		BaseAttribute entity = new BaseAttribute();
		super.copyProperties(entity, form);
		entity.setLink_id(Integer.valueOf(comm_id));
		String[] attr_name = request.getParameterValues("type_name");
		String[] order_value = request.getParameterValues("order_value_son");

		String del_attr_id = (String) dynaBean.get("del_attr_id");
		if (StringUtils.isNotBlank(del_attr_id)) {
			entity.getMap().put("del_attr_id", del_attr_id);
		}
		if (ArrayUtils.isNotEmpty(attr_name) && ArrayUtils.isNotEmpty(order_value)) {
			List<BaseAttributeSon> BaseAttributeSonList = new ArrayList<BaseAttributeSon>();

			List<UploadFile> uploadFileList = super.uploadFile(form, true, false, Keys.NEWS_INFO_IMAGE_SIZE);

			for (int i = 0; i < attr_name.length; i++) {
				BaseAttributeSon bpa_son = new BaseAttributeSon();
				bpa_son.setOrder_value(Integer.valueOf(order_value[i]));
				bpa_son.setAttr_name(attr_name[i]);
				bpa_son.setAttr_show_name(attr_name[i]);
				if ((uploadFileList != null) && (uploadFileList.size() > 0)) {
					UploadFile uploadFile = uploadFileList.get(i);
					bpa_son.setPic_path(uploadFile.getFileSavePath());
				}
				BaseAttributeSonList.add(bpa_son);
			}
			entity.setBaseAttributeSonList(BaseAttributeSonList);
		}
		entity.setAdd_user_id(ui.getId());
		entity.setAttr_show_name(entity.getAttr_name());

		if (null != entity.getId()) {// update
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(new Integer(ui.getId()));
			getFacade().getBaseAttributeService().modifyBaseAttribute(entity);
			saveMessage(request, "entity.updated");
		} else {// add
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(new Integer(ui.getId()));
			entity.getMap().put("need_insert_for_base", true);
			getFacade().getBaseAttributeService().createBaseAttribute(entity);
			saveMessage(request, "entity.inerted");
		}
		return listattr(mapping, form, request, response);
	}

	public ActionForward editattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		BaseAttribute entity = new BaseAttribute();
		BaseAttributeSon son = new BaseAttributeSon();
		if (null != id) {
			entity.setId(Integer.valueOf(id));
			son.setAttr_id(Integer.valueOf(id));
		}
		entity = getFacade().getBaseAttributeService().getBaseAttribute(entity);

		if (null != entity.getLink_id()) {
			CommInfo commInfo = new CommInfo();
			commInfo.setId(entity.getLink_id());
			commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
			request.setAttribute("commInfo", commInfo);

			BaseAttribute param_battr = new BaseAttribute(); // 取商品拥有的属性和子属性
			param_battr.setIs_del(0);
			param_battr.setLink_id(entity.getLink_id());
			param_battr.setAttr_scope(1); // 1为商品套餐属性
			param_battr.getMap().put("link_has_attr_id_not_null", true);
			List<BaseAttribute> list_BaseAttribute = super.getFacade().getBaseAttributeService()
					.getBaseAttributeList(param_battr);
			String ids = "";
			int i = 0;
			for (BaseAttribute battr : list_BaseAttribute) {
				if (null != battr.getLink_has_attr_id()) {
					ids += battr.getLink_has_attr_id();
					if (i + 1 != list_BaseAttribute.size()) {
						ids += ",";
					}
				}
				i++;
			}
			request.setAttribute("ids", ids);

		}

		super.copyProperties(form, entity);
		List<BaseAttributeSon> sonList = super.getFacade().getBaseAttributeSonService().getBaseAttributeSonList(son);
		request.setAttribute("sonList", sonList);
		request.setAttribute("edit", "edit");
		return new ActionForward("/../manager/admin/CommInfo/attrform.jsp");
	}

	public ActionForward deleteattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String comm_id = (String) dynaBean.get("comm_id");
		String[] pks = (String[]) dynaBean.get("pks");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			BaseAttribute entity = new BaseAttribute();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setLink_id(Integer.valueOf(comm_id));
			entity.setDel_user_id(sessionUi.getId());
			entity.getMap().put("update_link_table", "true");
			getFacade().getBaseAttributeService().modifyBaseAttribute(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			BaseAttribute entity = new BaseAttribute();
			entity.setIs_del(1);
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			entity.setLink_id(Integer.valueOf(comm_id));
			entity.getMap().put("pks", pks);
			entity.getMap().put("update_link_table", "true");
			getFacade().getBaseAttributeService().modifyBaseAttribute(entity);
			saveMessage(request, "entity.deleted");
		}
		dynaBean.set("comm_id", comm_id);
		return listattr(mapping, form, request, response);
	}

	public ActionForward viewattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String comm_id = (String) dynaBean.get("comm_id");
		dynaBean.set("comm_id", comm_id);
		if (GenericValidator.isLong(id)) {
			BaseAttribute entity = new BaseAttribute();
			BaseAttributeSon entity_son = new BaseAttributeSon();
			entity.setId(new Integer(id));
			entity_son.setAttr_id(new Integer(id));
			entity = getFacade().getBaseAttributeService().getBaseAttribute(entity);
			List<BaseAttributeSon> sonList = super.getFacade().getBaseAttributeSonService()
					.getBaseAttributeSonList(entity_son);
			request.setAttribute("sonList", sonList);
			if (null == entity) {
				saveMessage(request, "entity.missing");
				return listattr(mapping, form, request, response);
			}
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/admin/CommInfo/attrview.jsp");
		} else {
			this.saveError(request, "errors.Integer", new String[] { id });
			return listattr(mapping, form, request, response);
		}
	}

	public ActionForward savetcfw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String comm_id = (String) dynaBean.get("comm_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String is_work = (String) dynaBean.get("is_work");
		String queryString = (String) dynaBean.get("queryString");

		String[] inventory = request.getParameterValues("inventory");
		String[] buyer_limit_num = request.getParameterValues("buyer_limit_num");
		String[] orig_prices = request.getParameterValues("orig_price");
		String[] tczh_names = request.getParameterValues("tczh_name");
		String[] tczh_prices = request.getParameterValues("tczh_price");
		String[] cost_prices = request.getParameterValues("cost_price");
		String[] attr_tczh_ids = request.getParameterValues("attr_tczh_id");
		String[] comm_tczh_ids = request.getParameterValues("comm_tczh_id");
		String[] comm_tczh_names = request.getParameterValues("comm_tczh_name");

		// 保存商品套餐属性
		boolean modifyCommFlag = false;
		List<CommTczhPrice> list_CommTczhPrice = new ArrayList<CommTczhPrice>();
		CommTczhPrice ctp_entity = new CommTczhPrice();
		if (null != tczh_prices) {
			for (int i = 0; i < tczh_prices.length; i++) {
				CommTczhPrice ctp = new CommTczhPrice();
				ctp.setAdd_user_id(ui.getId());
				ctp.setComm_id(comm_id);
				ctp.setAdd_date(new Date());
				ctp.setAdd_user_id(ui.getId());

				ctp.setComm_price(new BigDecimal(tczh_prices[i]));
				ctp.setInventory(Integer.valueOf(inventory[i])); // 套餐库存
				ctp.setTczh_name(tczh_names[i]);

				if (ArrayUtils.isNotEmpty(orig_prices)) {
					ctp.setOrig_price(new BigDecimal(orig_prices[i]));
				}

				if (Integer.valueOf(comm_type) == 5) {
					if (ArrayUtils.isNotEmpty(cost_prices)) {
						ctp.setCost_price(new BigDecimal(cost_prices[i]));
					}
					if (ArrayUtils.isNotEmpty(buyer_limit_num)) {
						ctp.setBuyer_limit_num(Integer.valueOf(buyer_limit_num[i])); // 活动限购
					}

				}

				if ((comm_tczh_ids != null) && StringUtils.isNotBlank(comm_tczh_ids[i])) {
					ctp.setId(Integer.valueOf(comm_tczh_ids[i]));
					modifyCommFlag = true;
				}

				list_CommTczhPrice.add(ctp);
			}
		}

		if (StringUtils.isNotBlank(is_work) && is_work.endsWith("1")) {
			ctp_entity.getMap().put("updateAttrAndSonIsWork", true);
		}

		ctp_entity.setComm_id(comm_id);
		ctp_entity.setCommTczhPriceList(list_CommTczhPrice);
		ctp_entity.getMap().put("update_link_info", "true");

		if (modifyCommFlag) {
			super.getFacade().getCommTczhPriceService().modifyCommTczhPrice(ctp_entity);
		} else {
			ctp_entity.setAdd_date(new Date());
			ctp_entity.setAdd_user_id(ui.getId());
			super.getFacade().getCommTczhPriceService().createCommTczhPriceAndAttr(ctp_entity);
		}
		saveMessage(request, "entity.updated");

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&comm_type=" + comm_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(queryString));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	/**
	 * @author minyg
	 * @version 2013-10-06,修改by Wuyang：2016-06-21优化代码结构
	 * @desc 套餐是否需要重新排列组合,如果需要,排列组合新的
	 */
	public ActionForward tczh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("=====进入tczh====");
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");

		String code = "0";
		String msg = "参数错误";
		if (!GenericValidator.isInt(comm_id)) {
			code = "0";
			msg = "comm_id参数错误";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		BaseAttribute baseAttribute = new BaseAttribute();
		baseAttribute.setIs_del(0);
		baseAttribute.setLink_id(Integer.valueOf(comm_id));
		List<BaseAttribute> baseAttributeList = super.getFacade().getBaseAttributeService()
				.getBaseAttributeList(baseAttribute);
		if (null == baseAttributeList || baseAttributeList.size() <= 0) {
			code = "0";
			msg = "主属性没有数据";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		// 属性内容主数组
		List<String[]> array_par = new ArrayList<String[]>(baseAttributeList.size());
		// 属性二维主数组
		Integer[] counts_par = new Integer[baseAttributeList.size()];
		int counts_index = 0;
		for (BaseAttribute ba : baseAttributeList) {
			BaseAttributeSon baseAttributeSon = new BaseAttributeSon();
			baseAttributeSon.setAttr_id(ba.getId());
			List<BaseAttributeSon> baseAttributeSonList = super.getFacade().getBaseAttributeSonService()
					.getBaseAttributeSonList(baseAttributeSon);

			if (baseAttributeSonList == null || baseAttributeSonList.size() <= 0) {
				code = "0";
				msg = "子属性没有数据";
				super.ajaxReturnInfo(response, code, msg, null);
				return null;
			}
			int attrson_index = 0;
			String[] attrson = new String[(baseAttributeSonList.size())];
			for (BaseAttributeSon bas : baseAttributeSonList) {
				attrson[attrson_index++] = bas.getId() + "," + bas.getAttr_name();
			}
			array_par.add(attrson);
			counts_par[counts_index] = baseAttributeSonList.size();
			counts_index++;
		}

		JSONObject datas = new JSONObject();
		datas.put("link_id", comm_id);// 排列组合,具体进入IndexCartesianProductIterator查看例子
		Iterator<Integer[]> it = new IndexCartesianProductIterator(counts_par);
		JSONArray json_array = new JSONArray();
		while (it.hasNext()) {

			Integer[] result = it.next();
			int ii = 0;
			List<String> attr_ids = new ArrayList<String>();
			List<String> attr_names = new ArrayList<String>();
			for (String[] ss : array_par) {
				String text = ss[result[ii++]];
				String[] textAndValue = StringUtils.split(text, ",");
				attr_ids.add(textAndValue[0]);
				attr_names.add(textAndValue[1]);
			}

			JSONObject json_son = new JSONObject();
			json_son.put("attr_tczh", StringUtils.join(attr_names, " + "));
			json_son.put("attr_tczh_id", StringUtils.join(attr_ids, ","));

			// 回显历史价格、成本价格、库存...有需要可以增加
			// BigDecimal comm_price = new BigDecimal("0");
			// BigDecimal inventory = new BigDecimal("99");
			CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
			commTczhAttribute.getMap().put("att_ids", StringUtils.join(attr_ids, ","));
			commTczhAttribute.getMap().put("att_size", attr_ids.size());
			commTczhAttribute = getFacade().getCommTczhAttributeService()
					.getCommTczhAttributeForGetCommTczhId(commTczhAttribute);
			if (null != commTczhAttribute && null != commTczhAttribute.getComm_tczh_id()) {
				logger.info("==commTczhAttribute.getComm_tczh_id():{}", commTczhAttribute.getComm_tczh_id());
				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setId(commTczhAttribute.getComm_tczh_id());
				commTczhPrice = getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
				if (null != commTczhPrice) {

					json_son.put("comm_price", commTczhPrice.getComm_price());
					json_son.put("orig_price", commTczhPrice.getOrig_price());
					json_son.put("inventory", commTczhPrice.getInventory());
					if (null != commTczhPrice.getBuyer_limit_num()) {
						json_son.put("inventory", commTczhPrice.getBuyer_limit_num());
					}

				}
			}
			json_array.add(json_son);
		}

		datas.put("list", json_array);
		code = "1";
		msg = "数据加载成功";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;

		// {"link_id":"13","list":[{"attr_tczh":"白色 16G","attr_tczh_id":"-1044-1046"},{"attr_tczh":"白色
		// 32G","attr_tczh_id":"-1044-1047"},{"attr_tczh":"黑色
		// 16G","attr_tczh_id":"-1045-1046"},{"attr_tczh":"黑色
		// 32G","attr_tczh_id":"-1045-1047"}]}
		// //////==========
		// List<Integer> list_isNeedReSort_CommTczh = new ArrayList<Integer>();
		// CommTczhAttribute cta = new CommTczhAttribute();
		// cta.setComm_id(comm_id);
		// List<CommTczhAttribute> list_CommTczhAttribute =
		// super.getFacade().getCommTczhAttributeService()
		// .getCommTczhAttributeList(cta);
		// if (list_CommTczhAttribute != null) {
		// for (CommTczhAttribute for_cta : list_CommTczhAttribute) {
		// list_isNeedReSort_CommTczh.add(new Integer(for_cta.getAttr_id()));
		// }
		// // 去掉重复attr_id
		// HashSet<Integer> h = new
		// HashSet<Integer>(list_isNeedReSort_CommTczh);
		// list_isNeedReSort_CommTczh.clear();
		// list_isNeedReSort_CommTczh.addAll(h);
		// Collections.sort(list_isNeedReSort_CommTczh);
		// }
		//
		// StringBuffer sb = new StringBuffer("{");
		// if (StringUtils.isBlank(comm_id)) {
		// sb = sb.append("}");
		// super.renderJson(response, sb.toString());
		// return null;
		// }
		//
		// List<Integer> list_isNeedReSort_Base = new ArrayList<Integer>();
		// BaseAttribute param_BaseAttribute = new BaseAttribute();
		// param_BaseAttribute.setIs_del(0);
		// param_BaseAttribute.setLink_id(Integer.valueOf(comm_id));
		// List<BaseAttribute> list_BaseAttribute =
		// super.getFacade().getBaseAttributeService()
		// .getBaseAttributeList(param_BaseAttribute);
		// String sb_str = "";
		// if (list_BaseAttribute != null) {
		// int count = list_BaseAttribute.size();
		// List<String[]> arraylist = new ArrayList<String[]>(count);
		// Integer[] counts = new Integer[count];
		// int counts_index = 0;
		// for (BaseAttribute baseAttribute : list_BaseAttribute) {
		// BaseAttributeSon param_BaseAttributeSon = new BaseAttributeSon();
		// param_BaseAttributeSon.setAttr_id(baseAttribute.getId());
		// List<BaseAttributeSon> list_BaseAttributeSon =
		// super.getFacade().getBaseAttributeSonService()
		// .getBaseAttributeSonList(param_BaseAttributeSon);
		// int attrson_index = 0;
		// if (list_BaseAttributeSon != null) {
		// String[] attrson = new String[(list_BaseAttributeSon.size())];
		// for (BaseAttributeSon baseAttributeSon : list_BaseAttributeSon) {
		// attrson[attrson_index++] = baseAttributeSon.getId() + ","
		// + baseAttributeSon.getAttr_show_name();
		// list_isNeedReSort_Base.add(new Integer(baseAttributeSon.getId()));
		// }
		// arraylist.add(attrson);
		// counts[counts_index] = attrson.length;
		// }
		// counts_index++;
		// }
		//
		// Collections.sort(list_isNeedReSort_Base);
		//
		// boolean isNeedReSort = false;
		// if ((list_CommTczhAttribute != null)) {
		// if (list_isNeedReSort_CommTczh.size() !=
		// list_isNeedReSort_Base.size()) {
		// isNeedReSort = true;
		// } else {
		// int index_compare = 0;
		// for (Integer temp_CommTczh : list_isNeedReSort_CommTczh) {
		// Integer temp_Base = list_isNeedReSort_Base.get(index_compare);
		// if (!temp_Base.equals(temp_CommTczh)) {
		// isNeedReSort = true;
		// break;
		// }
		// }
		// }
		// }
		//
		// sb = sb.append("\"link_id\":\"").append(comm_id).append("\",");
		// sb = sb.append("\"list\":[");
		// if (!isNeedReSort) {// 不需要重新排序也要重新加载,防止修改了子属性名称
		// sb_str = sb.append("]}").toString();
		// } else {
		// if (counts.length > 0) {
		// Iterator<Integer[]> it = new IndexCartesianProductIterator(counts);
		// while (it.hasNext()) {
		// Integer[] result = it.next();
		// int ii = 0;
		// sb = sb.append("{\"attr_tczh\":\"");
		// StringBuffer attrId_str = new StringBuffer("");
		// List<String> attr_names = new ArrayList<String>();
		// for (String[] ss : arraylist) {
		// String text = ss[result[ii++]];
		// String[] textAndValue = text.split(",", 2);
		// attrId_str = attrId_str.append("-" + textAndValue[0]);
		// attr_names.add(textAndValue[1]);
		// }
		// sb = sb.append(StringUtils.join(attr_names, " "));
		// sb = sb.append("\",\"attr_tczh_id\":\"" +
		// attrId_str).append("\"").append("},");
		// }
		// sb_str = StringUtils.removeEnd(sb.toString(), ",") + "]}";
		// }
		// }
		// }
		// logger.info("sb_str {}", sb_str);
		// super.renderJson(response, sb_str);
		// return null;

	}

	public ActionForward sellList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}

		if (StringUtils.isNotBlank(comm_type)) {
			entity.setComm_type(Integer.valueOf(comm_type));
			dynaBean.set("comm_type", comm_type);
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		}
		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}

		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}
		entity.getMap().put("order_value", true);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (CommInfo ci : entityList) {
				// 套餐管理
				CommTczhPrice param_ctp = new CommTczhPrice();
				param_ctp.setComm_id(ci.getId().toString());
				param_ctp.getMap().put("order_by_inventory_asc", "true");
				List<CommTczhPrice> CommTczhPriceList = super.getFacade().getCommTczhPriceService()
						.getCommTczhPriceList(param_ctp);
				if ((null != CommTczhPriceList) && (CommTczhPriceList.size() > 0)) {
					ci.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
				}
			}
		}
		request.setAttribute("entityList", entityList);
		return new ActionForward("/../manager/admin/CommInfo/sellList.jsp");
	}

	public ActionForward issell(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);

			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/admin/CommInfo/issell.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return null;
		}
	}

	public ActionForward updateSaleCount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/admin/CommInfo/updateSaleCount.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return null;
		}
	}

	public ActionForward saveSaleCount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String sale_count_update_add = (String) dynaBean.get("sale_count_update_add");

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getCommInfoService().getCommInfo(entity);
		if (null == entity) {
			msg = "商品不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		CommInfo commInfoUpdate = new CommInfo();
		commInfoUpdate.setId(Integer.valueOf(id));
		commInfoUpdate.getMap().put("sale_count_update_add", sale_count_update_add);
		commInfoUpdate.getMap().put("add_sale_count_update_link_table", "true");
		super.getFacade().getCommInfoService().modifyCommInfo(commInfoUpdate);

		msg = "修改成功";
		ret = "1";
		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward evaluate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}
		if (GenericValidator.isLong(id)) {
			CommInfo entity = new CommInfo();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoService().getCommInfo(entity);
			super.copyProperties(form, entity);
			return new ActionForward("/../manager/admin/CommInfo/evaluate.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return null;
		}
	}

	public ActionForward saveEvaluate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		JSONObject data = new JSONObject();
		String ret = "0";
		String msg = "";

		if (StringUtils.isBlank(id)) {
			msg = "参数有误";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		CommInfo entity1 = new CommInfo();
		entity1.setId(Integer.valueOf(id));
		entity1 = getFacade().getCommInfoService().getCommInfo(entity1);
		if (null == entity1) {
			msg = "商品不存在！";
			data.put("ret", ret);
			data.put("msg", msg);
			super.renderJson(response, data.toJSONString());
			return null;
		}

		String base_files1 = (String) dynaBean.get("base_files1");
		String base_files2 = (String) dynaBean.get("base_files2");
		String base_files3 = (String) dynaBean.get("base_files3");
		String base_files4 = (String) dynaBean.get("base_files4");
		String[] basefiles = { base_files1, base_files2, base_files3, base_files4 };

		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, dynaBean);
		entity.setId(null);
		if (entity.getComm_level() == null) {
			entity.setComm_level(5);
		}
		entity.setComm_ip(this.getIpAddr(request));
		entity.setComm_time(new Date());
		entity.setComm_state(1);// 发布
		entity.setLink_id(entity1.getId());
		entity.setComm_type(entity1.getComm_type());
		entity.setEntp_id(entity1.getOwn_entp_id());
		if (StringUtils.isBlank(base_files1) && StringUtils.isBlank(base_files2) && StringUtils.isBlank(base_files3)
				&& StringUtils.isBlank(base_files4)) {
			entity.setHas_pic(0);
		} else {
			entity.setHas_pic(1);
		}
		entity.setOrder_value(0);

		entity.setComm_uid(ui.getId());

		// entity.setComm_uname(ui.getUser_name());

		entity.getMap().put("basefiles", basefiles);
		entity.getMap().put("only_creat", true);
		int i = getFacade().getCommentInfoService().createCommentInfo(entity);
		if (i > 0) {
			msg = "提交成功！";
			ret = "1";
		} else {
			msg = "提交失败！";
			ret = "0";

		}

		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward saveSell(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		JSONObject data = new JSONObject();
		if (!GenericValidator.isLong(id)) {
			data.put("ret", "0");
			data.put("msg", "参数有误！");
			super.renderJson(response, data.toString());
			return null;
		}

		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(id));
		entity = super.getFacade().getCommInfoService().getCommInfo(entity);
		if (entity == null) {
			data.put("ret", "0");
			data.put("msg", "商品不存在！");
			super.renderJson(response, data.toString());
			return null;
		}

		CommInfo entityUpdate = new CommInfo();
		super.copyProperties(entityUpdate, form);
		entityUpdate.setId(Integer.valueOf(id));
		int count = super.getFacade().getCommInfoService().modifyCommInfo(entityUpdate);
		if (count > 0) {
			data.put("ret", "1");
			data.put("msg", "操作成功");
			super.renderJson(response, data.toString());
			return null;
		} else {
			data.put("ret", "0");
			data.put("msg", "操作失败，请联系管理员！");
			super.renderJson(response, data.toString());
			return null;
		}
	}

	public ActionForward selectHasAttr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;

		String cls_id = (String) dynaBean.get("cls_id");
		String comm_id = (String) dynaBean.get("comm_id");
		String attr_name_like = (String) dynaBean.get("attr_name_like");
		String ids = (String) dynaBean.get("ids");

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response,
					"window.onload=function(){alert('" + msg + "');location.href='login.shtml'}");
			return null;
		}
		if (StringUtils.isBlank(cls_id)) {
			String msg = "参数有误！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
		if (null == commInfo) {
			String msg = "对应商品不存在！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		BaseAttribute entity = new BaseAttribute();
		entity.setCls_id(Integer.valueOf(cls_id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setAttr_scope(0);
		entity.getMap().put("link_has_attr_id_not_in", ids);
		entity.getMap().put("attr_name_like", attr_name_like);
		entity.setOwn_entp_id(commInfo.getOwn_entp_id());
		List<BaseAttribute> entityList = getFacade().getBaseAttributeService().getBaseAttributeList(entity);
		if (null != entityList && entityList.size() > 0) {
			for (BaseAttribute battr : entityList) {
				BaseAttributeSon param_battrson = new BaseAttributeSon();
				param_battrson.setAttr_id(battr.getId());
				List<BaseAttributeSon> listBaseAttributeSon = super.getFacade().getBaseAttributeSonService()
						.getBaseAttributeSonList(param_battrson);
				battr.getMap().put("listBaseAttributeSon", listBaseAttributeSon);
			}
		}
		request.setAttribute("entityList", entityList);

		return new ActionForward("/../manager/admin/CommInfo/selectHasAttr.jsp");
	}

	public ActionForward saveHasSelectAttr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String[] pks = (String[]) dynaBean.get("pks");

		if (ArrayUtils.isEmpty(pks)) {
			msg = "参数不能为空";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);

		for (String temp : pks) {

			BaseAttribute entityQuery = new BaseAttribute();
			entityQuery.setId(Integer.valueOf(temp));
			entityQuery = super.getFacade().getBaseAttributeService().getBaseAttribute(entityQuery);
			if (null != entityQuery) {

				BaseAttribute entity = new BaseAttribute();
				super.copyProperties(entity, entityQuery);
				entity.setId(null);
				entity.setOwn_entp_id(null);
				entity.setCls_id(null);
				entity.setLink_id(Integer.valueOf(comm_id));
				entity.setAttr_scope(1);
				entity.setIs_del(0);
				entity.setLink_has_attr_id(entityQuery.getId());
				entity.setAdd_date(new Date());
				entity.setAdd_user_id(new Integer(ui.getId()));

				BaseAttributeSon bpa_son = new BaseAttributeSon();
				bpa_son.setAttr_id(entityQuery.getId());
				List<BaseAttributeSon> BaseAttributeSonList = super.getFacade().getBaseAttributeSonService()
						.getBaseAttributeSonList(bpa_son);
				entity.setBaseAttributeSonList(BaseAttributeSonList);

				getFacade().getBaseAttributeService().createBaseAttribute(entity);
			}
		}
		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward selectCommAddForType4(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		getsonSysModuleList(request, dynaBean);
		String comm_id = (String) dynaBean.get("comm_id");

		if (StringUtils.isBlank(comm_id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		super.getSessionId(request);

		CommInfo commInfo = super.getCommInfoOnlyById(Integer.valueOf(comm_id));
		if (null == commInfo) {
			String msg = "商品不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(commInfo.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "商家不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		super.copyProperties(form, commInfo);

		// 把物流模板插入
		Freight freight = new Freight();
		freight.setId(commInfo.getFreight_id());
		freight = getFacade().getFreightService().getFreight(freight);
		if (freight != null) {
			dynaBean.set("fre_title", freight.getFre_title());
		}
		// 将商品套餐插--套餐管理

		CommTczhPrice param_ctp = new CommTczhPrice();
		param_ctp.setComm_id(String.valueOf(commInfo.getId()));
		List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(param_ctp);

		request.setAttribute("reBate1001",
				super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex()).getPre_number2());
		request.setAttribute("reBate1002",
				super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex()).getPre_number2());
		request.setAttribute("list_CommTczhPrice", list_CommTczhPrice);

		// 扶贫对象
		CommInfoPoors commInfoPoors = new CommInfoPoors();
		commInfoPoors.setComm_id(commInfo.getId());
		List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
				.getCommInfoPoorsList(commInfoPoors);
		if (null != commInfoPoorsList && commInfoPoorsList.size() > 0) {
			String temp_poor_ids = ",";
			for (CommInfoPoors temp : commInfoPoorsList) {
				temp_poor_ids += temp.getPoor_id().toString() + ",";
			}
			request.setAttribute("temp_poor_ids", temp_poor_ids);
		}
		request.setAttribute("commInfoPoorsList", commInfoPoorsList);
		// 将图片插入---图片管理
		// 重新赛入CommImgsList
		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(commInfo.getId()));
		entity = getFacade().getCommInfoService().getCommInfo(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		} else {
			List<PdImgs> CommImgsList = entity.getCommImgsList();
			for (int i = 1; i <= CommImgsList.size(); i++) {
				dynaBean.set("base_files" + i + "_file", CommImgsList.get(i - 1).getFile_path());
			}
			int CommImgsListCount = 0;
			if (CommImgsList != null) {
				CommImgsListCount = CommImgsList.size();
			}
			request.setAttribute("CommImgsListCount", CommImgsListCount);

			if (CommImgsList != null) {
				if (CommImgsList.size() < 5) {// 添加CommImgsList
					for (int i = 0; i < (5 - CommImgsListCount); i++) {
						PdImgs pdImgs = new PdImgs();
						CommImgsList.add(pdImgs);
					}
				}
			}

			// 重新赛入CommImgsList
			entity.setPdImgsList(CommImgsList);
			request.setAttribute("CommImgsList", CommImgsList);

			entpInfo = super.getEntpInfo(entity.getOwn_entp_id(), null, null);
			if (null != entpInfo && StringUtils.isNotBlank(entpInfo.getEntp_name())) {
				dynaBean.set("entp_name", entpInfo.getEntp_name());
			}

			// 物流
			if (null != entity.getFreight_id()) {
				Freight fre = super.getFreightInfo(entity.getFreight_id());
				if (null != fre) {
					dynaBean.set("fre_title", fre.getFre_title());
				}
			}

			// 品牌
			if (null != entity.getBrand_id()) {
				BaseBrandInfo baseBrandInfo = super.getBaseBrandInfo(entity.getBrand_id());
				if (null != baseBrandInfo) {
					dynaBean.set("brand_name", baseBrandInfo.getBrand_name());
				}
			}
			request.setAttribute("entity", entity);
			// 客服QQ
			EntpInfo a = getEntpInfo(entity.getAdd_user_id());
			if (null != a) {
				dynaBean.set("qq", a.getQq());
			}
		}

		// -------------------------------------------------------------------------------

		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("city", Keys.P_INDEX_CITY);
		dynaBean.set("is_sell", 1);
		dynaBean.set("own_entp_id", entpInfo.getId());
		dynaBean.set("entp_name", entpInfo.getEntp_name());
		dynaBean.set("up_date", new Date());
		dynaBean.set("down_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));

		dynaBean.set("comm_type", Keys.CommType.COMM_TYPE_4.getIndex());
		dynaBean.set("audit_state", null);
		dynaBean.set("audit_desc", null);
		dynaBean.set("audit_service_desc", null);
		dynaBean.set("id", null);
		return mapping.findForward("input");
	}

	public ActionForward selectForCommInfoUrl(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String today_date = (String) dynaBean.get("today_date");
		String link_url_num = (String) dynaBean.get("link_url_num");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(own_entp_id)) {
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.setId(Integer.valueOf(own_entp_id));
			entpInfo.setIs_del(0);
			entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
			if (null != entpInfo) {
				request.setAttribute("entp_name", entpInfo.getEntp_name());
			}
		}

		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}

		if (StringUtils.isNotBlank(comm_type)) {
			entity.setComm_type(Integer.valueOf(comm_type));
			dynaBean.set("comm_type", comm_type);
		}

		entity.setAudit_state(Integer.parseInt("1"));
		entity.setIs_del(0);
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}
		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.setCls_name(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		for (CommInfo ci : entityList) {
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.setIs_del(0);
			entpInfo.setId(ci.getOwn_entp_id());
			entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
			if (null != entpInfo) {
				ci.getMap().put("entp_name", entpInfo.getEntp_name());
			}
			// CommDelivery commDelivery = new CommDelivery();
			// commDelivery.setComm_id(ci.getId());
			// Integer count_delivery =
			// super.getFacade().getCommDeliveryService().getCommDeliveryCount(commDelivery);
			// if (count_delivery > 0) {
			// ci.getMap().put("count_delivery", count_delivery);
			// }
			// CommPutPrice commPutPrice = new CommPutPrice();
			// commPutPrice.setComm_id(ci.getId());
			// commPutPrice.setIs_del(0);
			// Integer count_put_price =
			// super.getFacade().getCommPutPriceService().getCommPutPriceCount(commPutPrice);
			// if (count_put_price > 0) {
			// ci.getMap().put("count_put_price", count_put_price);
			// }

			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(ci.getId().toString());
			param_ctp.getMap().put("order_by_inventory_asc", "true");
			List<CommTczhPrice> CommTczhPriceList = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);
			if (null != CommTczhPriceList && CommTczhPriceList.size() > 0) {
				ci.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
			}
		}
		request.setAttribute("entityList", entityList);

		BaseClass baseClass = new BaseClass();
		baseClass.setIs_del(0);
		baseClass.getMap().put("no_have_self", "1");
		List<BaseClass> baseClassTreeList = super.getFacade().getBaseClassService().getBaseClassList(baseClass);
		request.setAttribute("baseClassTreeList", baseClassTreeList);

		if (StringUtils.isNotBlank(link_url_num)) {
			return new ActionForward("/admin/CommInfo/selectForCommInfoUrls.jsp");
		} else {
			return new ActionForward("/admin/CommInfo/selectForCommInfoUrl.jsp");
		}

	}

	public ActionForward editBaseClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");
		String own_entp_id = (String) dynaBean.get("own_entp_id");
		String today_date = (String) dynaBean.get("today_date");
		String comm_type = (String) dynaBean.get("comm_type");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(own_entp_id)) {
			EntpInfo entpInfo = super.getEntpInfo(Integer.valueOf(own_entp_id), null, null);
			if (null != entpInfo) {
				request.setAttribute("entp_name", entpInfo.getEntp_name());
			}
		}

		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}

		if (StringUtils.isBlank(comm_type)) {
			entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		} else {
			entity.setComm_type(Integer.valueOf(comm_type));
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		}
		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}
		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.setCls_name(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);
		for (CommInfo ci : entityList) {
			EntpInfo entpInfo = super.getEntpInfo(ci.getOwn_entp_id(), null, null);
			if (null != entpInfo) {
				ci.getMap().put("entp_name", entpInfo.getEntp_name());
			}
			if (ci.getIs_zingying().intValue() != 0) {
				ci.getMap().put("commZyName", CommZyType.getName(ci.getIs_zingying()));
			}

			UserInfo userInfo = super.getUserInfo(ci.getAdd_user_id());
			if (null != userInfo) {
				ci.getMap().put("mobile", userInfo.getMobile());
				ci.getMap().put("user_name", userInfo.getUser_name());
				ci.getMap().put("real_name", userInfo.getReal_name());
			}
			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(ci.getId().toString());
			param_ctp.getMap().put("order_by_inventory_asc", "true");
			List<CommTczhPrice> CommTczhPriceList = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);
			if ((null != CommTczhPriceList) && (CommTczhPriceList.size() > 0)) {
				ci.getMap().put("count_tczh_price_inventory", CommTczhPriceList.get(0).getInventory());
			}
		}
		request.setAttribute("entityList", entityList);
		request.setAttribute("CommZyTypeList", Keys.CommZyType.values());
		return new ActionForward("/admin/CommInfo/editBaseClass.jsp");

	}

	public ActionForward saveNewBaseClass(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String[] pks = (String[]) dynaBean.get("pks");
		String hy_cls_id = (String) dynaBean.get("hy_cls_id");
		String hy_cls_name = (String) dynaBean.get("hy_cls_name");
		String queryString = (String) dynaBean.get("queryString");
		CommInfo entity = new CommInfo();
		if (StringUtils.isNotBlank(hy_cls_id) && StringUtils.isNotBlank(hy_cls_name) && ArrayUtils.isNotEmpty(pks)) {
			BaseClass baseClass = new BaseClass();
			baseClass.setCls_id(Integer.valueOf(hy_cls_id));
			baseClass = super.getFacade().getBaseClassService().getBaseClass(baseClass);

			entity.setCls_id(Integer.valueOf(hy_cls_id));
			entity.setCls_name(hy_cls_name);
			entity.setPar_cls_id(baseClass.getPar_id());
			entity.setRoot_cls_id(baseClass.getRoot_id());
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(ui.getId());
			entity.getMap().put("pks", pks);
			super.getFacade().getCommInfoService().modifyCommInfo(entity);
			saveMessage(request, "entity.updated");
		} else {
			saveError(request, "errors.parm");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append("/admin/CommInfo.do?method=editBaseClass");
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&comm_type=" + comm_type);
		pathBuffer.append("&");
		pathBuffer.append(queryString);
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward downloadQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		HttpSession session = request.getSession(false);
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");
		String today_date = (String) dynaBean.get("today_date");
		String comm_type = (String) dynaBean.get("comm_type");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		if (StringUtils.isNotBlank(country)) {
			entity.setP_index(Integer.valueOf(country));
		} else if (StringUtils.isNotBlank(city)) {
			entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		} else if (StringUtils.isNotBlank(province)) {
			entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		}

		if (StringUtils.isBlank(comm_type)) {
			entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		} else {
			entity.setComm_type(Integer.valueOf(comm_type));
		}
		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		}
		if (StringUtils.isNotBlank(is_del)) {
			if ("-1".equals(is_del)) {
				entity.setIs_del(null);
			}
			if ("0".equals(is_del)) {
				entity.setIs_del(0);
			}
			if ("1".equals(is_del)) {
				entity.setIs_del(1);
			}
		} else {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(comm_name_like)) {
			entity.getMap().put("comm_name_like", comm_name_like);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}
		if (StringUtils.isNotBlank(cls_id) && !cls_id.equals("1")) {
			entity.setCls_id(null);
			entity.setCls_name(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", cls_id);
		}
		List<CommInfo> commInfoList = super.getFacade().getCommInfoService().getCommInfoList(entity);
		String realPath = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String fname = EncryptUtilsV2.encodingFileName("二维码导出_日期" + sdFormat_ymd.format(new Date()) + ".zip");
		response.setContentType("APPLICATION/OCTET-STREAM");
		response.setHeader("Content-Disposition", "attachment; filename=" + fname);

		if (null != commInfoList && commInfoList.size() > 0) {
			ZipOutputStream zipout = new ZipOutputStream(response.getOutputStream());
			File[] files = new File[commInfoList.size()];
			int i = 0;
			for (CommInfo temp : commInfoList) {
				if (null != temp.getComm_qrcode_path()) {
					File savePath = new File(realPath + temp.getComm_qrcode_path());
					if (savePath.exists())
						files[i] = savePath;
				}
				i++;
			}

			ZipUtils.zipFile(files, "", zipout);
			zipout.flush();
			zipout.close();
			return null;
		} else {
			return null;
		}

	}

	public ActionForward fapiao(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String is_fapiao = (String) dynaBean.get("is_fapiao");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		if (StringUtils.isBlank(comm_id)) {
			msg = "参数不能为空";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		CommInfo comm_info = super.getCommInfoOnlyById(Integer.valueOf(comm_id));
		if (comm_info == null) {
			msg = "商品不存在";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		comm_info.setIs_fapiao(Integer.valueOf(is_fapiao));
		super.getFacade().getCommInfoService().modifyCommInfo(comm_info);
		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward ziti(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String is_ziti = (String) dynaBean.get("is_ziti");
		UserInfo ui = super.getUserInfoFromSession(request);

		JSONObject datas = new JSONObject();
		String code = "0", msg = "";
		if (StringUtils.isBlank(comm_id)) {
			msg = "参数不能为空";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		CommInfo comm_info = super.getCommInfoOnlyById(Integer.valueOf(comm_id));
		if (comm_info == null) {
			msg = "商品不存在";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		comm_info.setIs_ziti(Integer.valueOf(is_ziti));
		super.getFacade().getCommInfoService().modifyCommInfo(comm_info);
		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}
}
