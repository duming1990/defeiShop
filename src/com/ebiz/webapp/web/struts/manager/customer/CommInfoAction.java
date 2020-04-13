package com.ebiz.webapp.web.struts.manager.customer;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommInfoTags;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpCommClass;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.util.IndexCartesianProductIterator;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;
import com.ebiz.webapp.web.util.ZipUtils;

/**
 * @author minyg
 * @version 2013-09-26
 */

public class CommInfoAction extends BaseCustomerAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		if (userInfo.getId() == 2) {// 京东自营
			request.setAttribute("is_jd", true);
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

		// 商品列表
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
		String today_date = (String) dynaBean.get("today_date");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());

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
		if (null != userInfo.getOwn_entp_id()) {
			entity.setOwn_entp_id(userInfo.getOwn_entp_id());
			entity.getMap().put("order_value", true);

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
		}

		return mapping.findForward("list");
	}

	public ActionForward sellList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
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
		if (null != userInfo.getOwn_entp_id()) {
			entity.setOwn_entp_id(userInfo.getOwn_entp_id());
			entity.getMap().put("order_value", true);

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
		}
		return new ActionForward("/../manager/customer/CommInfo/sellList.jsp");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		UserInfo userInfoTemp = super.getUserInfo(userInfo.getId());

		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		getsonSysModuleList(request, dynaBean);
		String comm_type = (String) dynaBean.get("comm_type");
		dynaBean.set("comm_type", comm_type);
		request.setAttribute("comm_type", comm_type);

		List<PdImgs> CommImgsList = new ArrayList<PdImgs>();
		request.setAttribute("CommImgsListCount", 0);
		for (int i = 0; i < 5; i++) {
			PdImgs pdImgs = new PdImgs();
			CommImgsList.add(pdImgs);
		}

		request.setAttribute("CommImgsList", CommImgsList);

		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("city", Keys.P_INDEX_CITY);
		dynaBean.set("is_sell", "1");
		dynaBean.set("is_has_tc", "0");

		super.getSessionId(request);

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfoTemp.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "商家不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		dynaBean.set("own_entp_id", entpInfo.getId());
		dynaBean.set("entp_name", entpInfo.getEntp_name());
		dynaBean.set("up_date", new Date());
		dynaBean.set("down_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));

		dynaBean.set("p_index", entpInfo.getP_index());

		request.setAttribute("fanXianTypes", Keys.FanXianTypeComm.values());
		// 一个商品最多扶贫对象数量
		BaseData baseData1901 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1901.getIndex());
		request.setAttribute("baseData1901", baseData1901);

		EntpCommClass entity = new EntpCommClass();// 商家分类
		entity.setEntp_id(entpInfo.getId());
		entity.setIs_del(0);
		List<EntpCommClass> commClasslist = super.getFacade().getEntpCommClassService().getEntpCommClassList(entity);
		request.setAttribute("commClasslist", commClasslist);

		return mapping.findForward("input");
	}

	public ActionForward selectCommAddForType4(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		UserInfo userInfoTemp = super.getUserInfo(userInfo.getId());

		if (userInfoTemp.getIs_entp().intValue() != 1) {
			String msg = "非商家用户,无此权限！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		saveToken(request);
		getsonSysModuleList(request, dynaBean);
		String comm_id = (String) dynaBean.get("comm_id");
		String comm_type = (String) dynaBean.get("comm_type");

		if (StringUtils.isBlank(comm_id)) {
			String msg = "参数有误，请联系管理员！";
			super.showMsgForCustomer(request, response, msg);
			return null;
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfoTemp.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (null == entpInfo) {
			String msg = "商家不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		CommInfo commInfo = super.getCommInfoOnlyById(Integer.valueOf(comm_id));

		// int count = super.validateCommName(null, commInfo.getComm_name(), userInfoTemp.getOwn_entp_id());
		// if (count > 0) {
		// String msg = "商品已上架，请重新选择！";
		// super.showMsgForManager(request, response, msg);
		// return null;
		// }

		super.copyProperties(form, commInfo);

		dynaBean.set("province", Keys.P_INDEX_INIT);
		dynaBean.set("city", Keys.P_INDEX_CITY);
		dynaBean.set("is_sell", 1);
		dynaBean.set("own_entp_id", entpInfo.getId());
		dynaBean.set("entp_name", entpInfo.getEntp_name());
		dynaBean.set("up_date", new Date());
		dynaBean.set("down_date", DateUtils.addDays(new Date(), Keys.COMM_UP_DATE));

		if (StringUtils.isNotBlank(comm_type)) {
			dynaBean.set("comm_type", comm_type);
		} else {
			dynaBean.set("comm_type", Keys.CommType.COMM_TYPE_2.getIndex());
		}

		dynaBean.set("audit_state", null);
		dynaBean.set("audit_desc", null);
		dynaBean.set("audit_service_desc", null);
		dynaBean.set("id", null);

		// 判断是否有套餐

		CommTczhPrice param_ctp = new CommTczhPrice();
		param_ctp.setComm_id(commInfo.getId().toString());
		List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(param_ctp);

		request.setAttribute("list_CommTczhPrice", list_CommTczhPrice);

		List<PdImgs> commImgsList = commInfo.getCommImgsList();
		int CommImgsListCount = 0;
		if (commImgsList != null) {
			CommImgsListCount = commImgsList.size();
		}
		request.setAttribute("CommImgsListCount", CommImgsListCount);

		if (commImgsList != null) {
			if (commImgsList.size() < 5) {// 添加CommImgsList
				for (int i = 0; i < (5 - CommImgsListCount); i++) {
					PdImgs pdImgs = new PdImgs();
					commImgsList.add(pdImgs);
				}
			}
		}
		// 重新赛入CommImgsList
		commInfo.setPdImgsList(commImgsList);
		request.setAttribute("CommImgsList", commImgsList);

		if (null != commInfo.getP_index()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, commInfo.getP_index());
		}

		// 物流
		if (null != commInfo.getFreight_id()) {
			Freight fre = super.getFreightInfo(commInfo.getFreight_id());
			if (null != fre) {
				dynaBean.set("fre_title", fre.getFre_title());
			}
		}

		// 商品频道
		CommInfoTags commInfoTags = new CommInfoTags();
		commInfoTags.setComm_id(commInfo.getId());
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

		// 一个商品最多扶贫对象数量
		BaseData baseData1901 = super.getBaseData(Keys.BASE_DATA_ID.BASE_DATA_ID_1901.getIndex());
		request.setAttribute("baseData1901", baseData1901);

		EntpCommClass CommClass = new EntpCommClass();// 商家分类
		CommClass.setEntp_id(entpInfo.getId());
		CommClass.setIs_del(0);
		List<EntpCommClass> commClasslist = super.getFacade().getEntpCommClassService().getEntpCommClassList(CommClass);
		request.setAttribute("commClasslist", commClasslist);

		return mapping.findForward("input");
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
		text = buffer.toString().substring(0, buffer.toString().length() - 3);

		super.renderText(response, text);

		return null;
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		UserInfo userInfoTemp = super.getUserInfo(ui.getId());

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		String cls_id = (String) dynaBean.get("cls_id");
		String id = (String) dynaBean.get("id");
		String comm_type = (String) dynaBean.get("comm_type");
		String show_notes = (String) dynaBean.get("show_notes");
		String[] file_path_lbts = request.getParameterValues("file_path");

		String tczh_names = (String) dynaBean.get("tczh_names");
		String tczh_prices = (String) dynaBean.get("tczh_prices");
		String inventorys = (String) dynaBean.get("inventorys");
		String orig_prices = (String) dynaBean.get("orig_prices");
		String comm_weights = (String) dynaBean.get("comm_weights");
		String buyer_limit_nums = (String) dynaBean.get("buyer_limit_nums");

		Integer group_count = null;
		Integer group_type = null;
		Integer group_time = null;
		if (StringUtils.isNotBlank(dynaBean.get("group_count").toString())) {
			group_count = new Integer(dynaBean.get("group_count") + "");
		}
		if (StringUtils.isNotBlank(dynaBean.get("group_type").toString())) {
			group_type = new Integer(dynaBean.get("group_type") + "");
		}
		if (StringUtils.isNotBlank(dynaBean.get("group_time").toString())) {
			group_time = new Integer(dynaBean.get("group_time") + "");
		}
		String group_price = (String) dynaBean.get("group_price");
		System.out.println(group_price);

		String tag_ids = (String) dynaBean.get("tag_ids");// 选择频道拼接字符串
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

		if (!GenericValidator.isInt(cls_id)) {
			String msg = "cls_id参数不正确！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		BaseClass bc = super.getBaseClass(Integer.valueOf(cls_id));
		if (null == bc) {
			String msg = "类别不存在！";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		int count = super.validateCommName(id, entity.getComm_name(), userInfoTemp.getOwn_entp_id());
		if (count > 0) {
			String msg = "商品名称已使用，请重新填写";
			super.showMsgForManager(request, response, msg);
			return null;
		}

		entity.setPar_cls_id(bc.getPar_id());
		entity.setRoot_cls_id(bc.getRoot_id());

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(userInfoTemp.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);

		entity.setP_index(Integer.valueOf(entpInfo.getP_index()));
		entity.setOwn_entp_id(entpInfo.getId());

		if (ArrayUtils.isNotEmpty(file_path_lbts)) {
			for (String file_path_lbt : file_path_lbts) {
				if (StringUtils.isNotBlank(file_path_lbt)) {
					PdImgs pdImgs = new PdImgs();
					pdImgs.setFile_path(file_path_lbt);
					commImgsList.add(pdImgs);
				}
			}
		}
		if (commImgsList.size() > 0) {
			entity.setCommImgsList(commImgsList);
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

		if (!"1".equals(show_notes)) {
			entity.setShow_notes(new Integer(0));
		}

		if (StringUtils.isNotBlank(tczh_names) && StringUtils.isNotBlank(tczh_prices)
				&& StringUtils.isNotBlank(inventorys)) {
			entity.getMap().put("tczh_names", tczh_names);
			entity.getMap().put("tczh_prices", tczh_prices);
			entity.getMap().put("inventorys", inventorys);
			entity.getMap().put("update_comm_tczh_price", true);

			if (Integer.valueOf(comm_type) == Keys.CommType.COMM_TYPE_20.getIndex()
					&& StringUtils.isNotBlank(group_price)) {
				entity.getMap().put("group_price", group_price);
			}

		}

		if (StringUtils.isNotBlank(comm_weights)) {
			entity.getMap().put("comm_weights", comm_weights);
		}

		if (StringUtils.isNotBlank(orig_prices) && StringUtils.isNotBlank(buyer_limit_nums)) {
			entity.getMap().put("orig_prices", orig_prices);
			entity.getMap().put("buyer_limit_nums", buyer_limit_nums);
			entity.getMap().put("update_comm_tczh_price", true);
		}

		// 选择频道tag_ids
		if (StringUtils.isNotBlank(tag_ids)) {
			entity.getMap().put("tag_ids", tag_ids);
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
			entity.setAudit_state(Keys.audit_state.audit_state_0.getIndex());// 商家修改商品信息 ，状态改变为 ：待审核
			super.getFacade().getCommInfoService().modifyCommInfo(entity);
			saveMessage(request, "entity.updated");

			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&mod_id=" + mod_id);
			pathBuffer.append("&par_id=" + par_id);
			pathBuffer.append("&comm_type=" + comm_type);
			pathBuffer.append("&");
			pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;

		} else {
			entity.setAdd_date(date);
			entity.setAdd_user_id(ui.getId());
			entity.setAdd_user_name(ui.getUser_name());
			entity.setIs_del(0);
			entity.setAudit_state(Keys.audit_state.audit_state_0.getIndex());// 默认待审核
			entity.setAudit_user_id(ui.getId());
			entity.setAudit_date(date);
			entity.setGroup_count(group_count);
			entity.setGroup_type(group_type);
			entity.setGroup_time(group_time);

			if (ui.getIs_entp().intValue() == 1) {
				entity.setIs_zingying(Keys.CommZyType.COMM_ZY_TYPE_3.getIndex());
			} else if (ui.getIs_fuwu().intValue() == 1) {
				entity.setIs_zingying(Keys.CommZyType.COMM_ZY_TYPE_4.getIndex());
			}

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
		String par_id = (String) dynaBean.get("par_id");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_type = (String) dynaBean.get("comm_type");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
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
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&comm_type=" + comm_type);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "cls_id", "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;

		saveToken(request);
		getsonSysModuleList(request, dynaBean);

		// 存入商品类型
		String comm_type = (String) dynaBean.get("comm_type");
		request.setAttribute("comm_type", comm_type);

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}

		super.getSessionId(request);

		CommInfo entity = new CommInfo();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getCommInfoService().getCommInfo(entity);

		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}

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

		if (null != entity.getP_index()) {
			super.setprovinceAndcityAndcountryToFrom(dynaBean, entity.getP_index());
		}
		// 物流
		if (null != entity.getFreight_id()) {
			Freight fre = super.getFreightInfo(entity.getFreight_id());
			if (null != fre) {
				dynaBean.set("fre_title", fre.getFre_title());
			}
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setId(entity.getOwn_entp_id());
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		String entp_name = entpInfo.getEntp_name();
		dynaBean.set("entp_name", entp_name);
		dynaBean.set("is_self_support_entp", entpInfo.getIs_self_support());
		dynaBean.set("qq", entpInfo.getQq());
		request.setAttribute("entity", entity);

		entity.setQueryString(super.serialize(request, "id", "method"));
		super.copyProperties(form, entity);

		// 套餐管理
		CommTczhPrice param_ctp = new CommTczhPrice();
		param_ctp.setComm_id(id);
		List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(param_ctp);
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

		EntpCommClass CommClass = new EntpCommClass();// 商家分类
		CommClass.setEntp_id(entpInfo.getId());
		CommClass.setIs_del(0);
		List<EntpCommClass> commClasslist = super.getFacade().getEntpCommClassService().getEntpCommClassList(CommClass);
		request.setAttribute("commClasslist", commClasslist);

		return mapping.findForward("input");
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);

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
			dynaBean.set("is_freeship", 0);

			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(id);
			List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);
			request.setAttribute("list_CommTczhPrice", list_CommTczhPrice); // 套餐价格及对应各属性

			return mapping.findForward("view");
		} else {
			saveError(request, "errors.Integer", id);
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
							List<BaseClass> baseClass4List = getFacade().getBaseClassService().getBaseClassList(
									baseClass4);
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
		super.render(response, result.toString(), "text/x-json;charset=UTF-8");

		return null;
	}

	public ActionForward edittcfw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		getsonSysModuleList(request, dynaBean);
		String comm_id = (String) dynaBean.get("comm_id");
		String price_ref = (String) dynaBean.get("price_ref");
		String comm_type = (String) dynaBean.get("comm_type");

		saveToken(request);
		getsonSysModuleList(request, dynaBean);
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

		// if (comm_type.equals(String.valueOf(Keys.CommType.COMM_TYPE_4.getIndex()))
		// || comm_type.equals(String.valueOf(Keys.CommType.COMM_TYPE_5.getIndex()))) {
		// return new ActionForward("/../manager/admin/CommInfo/tcfwformForType4.jsp");
		// }

		return new ActionForward("/../manager/customer/CommInfo/tcfwform.jsp");
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
		logger.info("==comm_id:" + comm_id);
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		request.setAttribute("commInfo", commInfo);

		return new ActionForward("/../manager/customer/CommInfo/attrlist.jsp");
	}

	public ActionForward addattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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

		return new ActionForward("/../manager/customer/CommInfo/attrform.jsp");
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

		return new ActionForward("/../manager/customer/CommInfo/attrform.jsp");
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
			entity.getMap().put("update_link_table", "true");
			entity.getMap().put("pks", pks);
			getFacade().getBaseAttributeService().modifyBaseAttribute(entity);
			saveMessage(request, "entity.deleted");
		}
		dynaBean.set("comm_id", comm_id);
		return listattr(mapping, form, request, response);
	}

	public ActionForward viewattr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
			return new ActionForward("/../manager/customer/CommInfo/attrview.jsp");
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
		String par_id = (String) dynaBean.get("par_id");
		String comm_id = (String) dynaBean.get("comm_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String is_work = (String) dynaBean.get("is_work");
		String queryString = (String) dynaBean.get("queryString");

		String[] inventory = request.getParameterValues("inventory");
		String[] buyer_limit_num = request.getParameterValues("buyer_limit_num");
		String[] orig_prices = request.getParameterValues("orig_price");
		String[] cost_prices = request.getParameterValues("cost_price");
		String[] tczh_names = request.getParameterValues("tczh_name");
		String[] tczh_prices = request.getParameterValues("tczh_price");
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

				list_CommTczhPrice.add(ctp);
			}
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
	 * @version 2013-10-06
	 * @desc 套餐是否需要重新排列组合,如果需要,排列组合新的
	 */
	public ActionForward tczh(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
			commTczhAttribute = getFacade().getCommTczhAttributeService().getCommTczhAttributeForGetCommTczhId(
					commTczhAttribute);
			if (null != commTczhAttribute && null != commTczhAttribute.getComm_tczh_id()) {
				logger.info("==commTczhAttribute.getComm_tczh_id():{}", commTczhAttribute.getComm_tczh_id());
				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setId(commTczhAttribute.getComm_tczh_id());
				commTczhPrice = getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
				if (null != commTczhPrice) {

					json_son.put("comm_price", commTczhPrice.getComm_price());
					json_son.put("orig_price", commTczhPrice.getOrig_price());
					json_son.put("inventory", commTczhPrice.getInventory());
				}
			}

			json_array.add(json_son);
		}

		datas.put("list", json_array);
		code = "1";
		msg = "数据加载成功";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;

	}

	public ActionForward issell(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		saveToken(request);

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
			return new ActionForward("/../manager/customer/CommInfo/issell.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return null;
		}
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
		String attr_name_like = (String) dynaBean.get("attr_name_like");
		String ids = (String) dynaBean.get("ids");

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}
		if (StringUtils.isBlank(cls_id)) {
			String msg = "参数有误！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		BaseAttribute entity = new BaseAttribute();
		entity.setCls_id(Integer.valueOf(cls_id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setAttr_scope(0);
		entity.getMap().put("link_has_attr_id_not_in", ids);
		entity.getMap().put("attr_name_like", attr_name_like);
		entity.setOwn_entp_id(Integer.valueOf(userInfo.getOwn_entp_id()));
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

		return new ActionForward("/../manager/customer/CommInfo/selectHasAttr.jsp");
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

	public ActionForward downloadQrcode(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		HttpSession session = request.getSession(false);

		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_type = (String) dynaBean.get("comm_type");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");
		String today_date = (String) dynaBean.get("today_date");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);
		entity.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());

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

		List<CommInfo> commInfoList = super.getFacade().getCommInfoService().getCommInfoList(entity);
		String realPath = session.getServletContext().getRealPath(String.valueOf(File.separatorChar));
		String fname = EncryptUtilsV2.encodingFileName("商家二维码导出_日期" + sdFormat_ymd.format(new Date()) + ".zip");
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
}