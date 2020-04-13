package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPromotion;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Liujia
 * @version 2016-08-08
 */

public class GroupCommInfoAction extends BaseAdminAction {

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

		DynaBean dynaBean = (DynaBean) form;
		setNaviStringToRequestScope(request);

		Pager pager = (Pager) dynaBean.get("pager");
		String comm_title_like = (String) dynaBean.get("comm_title_like");
		String is_del = (String) dynaBean.get("is_del");
		String own_entp_id = (String) dynaBean.get("own_entp_id");

		if (StringUtils.isNotBlank(own_entp_id)) {
			EntpInfo entpInfo = super.getEntpInfo(Integer.valueOf(own_entp_id), null, null);
			if (null != entpInfo) {
				request.setAttribute("entp_name", entpInfo.getEntp_name());
			}
		}

		CommInfoPromotion entity = new CommInfoPromotion();
		super.copyProperties(entity, form);
		entity.setPromotion_type(Keys.PromotionType.PROMOTION_TYPE_0.getIndex());

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
		entity.getMap().put("comm_title_like", comm_title_like);

		// entity.setIs_del(0);

		Integer recordCount = getFacade().getCommInfoPromotionService().getCommInfoPromotionCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfoPromotion> entityList = super.getFacade().getCommInfoPromotionService()
				.getCommInfoPromotionPaginatedList(entity);
		request.setAttribute("entityList", entityList);

		return mapping.findForward("list");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo userInfo = (UserInfo) request.getSession().getAttribute(Keys.SESSION_USERINFO_KEY);
		if (null == userInfo) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		setNaviStringToRequestScope(request);

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		saveToken(request);

		if (GenericValidator.isLong(id)) {
			CommInfoPromotion entity = new CommInfoPromotion();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoPromotionService().getCommInfoPromotion(entity);

			if (null != entity) {
				if (null != entity.getComm_id()) {
					CommInfo commInfo = super.getCommInfoOnlyById(entity.getComm_id());
					if (null != commInfo) {
						dynaBean.set("comm_name", commInfo.getComm_name());
					}
				}
				if (null != entity.getComm_tczh_id()) {
					CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
					commTczhAttribute.setComm_tczh_id(entity.getComm_tczh_id().intValue());
					commTczhAttribute.setOrder_value(0);
					commTczhAttribute = super.getFacade().getCommTczhAttributeService()
							.getCommTczhAttribute(commTczhAttribute);
					if (null != commTczhAttribute) {
						dynaBean.set("comm_tczh_name", commTczhAttribute.getAttr_name());
					}
				}
			}

			super.copyProperties(form, entity);

			return new ActionForward("/../manager/admin/GroupCommInfo/audit.jsp");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		saveToken(request);
		setNaviStringToRequestScope(request);

		return mapping.findForward("input");
	}

	public ActionForward getCommInfoById(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");

		String msg = "", code = "0";
		JSONObject jsonObject = new JSONObject();
		if (!GenericValidator.isInt(comm_id)) {
			msg = "ERROR:001,参数有误！";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
		if (null == commInfo) {

			msg = "ERROR:002,未查询到该商品！";
			super.ajaxReturnInfo(response, code, msg, null);
			return null;
		}

		code = "1";
		jsonObject.put("commInfo", commInfo);
		super.ajaxReturnInfo(response, code, msg, jsonObject);
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

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		String cls_id = (String) dynaBean.get("cls_id");
		String id = (String) dynaBean.get("id");
		String comm_tczh_ids = (String) dynaBean.get("comm_tczh_ids");
		String tg_tczh_ids = (String) dynaBean.get("tg_tczh_ids");
		logger.info("===comm_tczh_ids" + tg_tczh_ids);
		String is_audit = (String) dynaBean.get("is_audit");

		Date date = new Date();
		CommInfoPromotion entity = new CommInfoPromotion();

		super.copyProperties(entity, form);
		logger.info("=========entity======" + entity.getStart_time());

		resetToken(request);

		if (StringUtils.isNotBlank(is_audit)) {
			entity.setUpdate_date(date);
			entity.setUpdate_user_id(ui.getId());
			super.getFacade().getCommInfoPromotionService().modifyCommInfoPromotion(entity);
			saveMessage(request, "entity.updated");
		} else {
			if (StringUtils.isNotBlank(id)) {
				// 修改
				entity.setUpdate_date(date);
				entity.setUpdate_user_id(ui.getId());
				entity.setComm_tczh_ids(comm_tczh_ids);
				entity.getMap().put("update_comm_tczh_ids", true);
				entity.getMap().put("comm_tczh_id", comm_tczh_ids);
				entity.getMap().put("update_tg_commInfo", true);
				super.getFacade().getCommInfoPromotionService().modifyCommInfoPromotion(entity);
				saveMessage(request, "entity.updated");

			} else {
				// 创建
				entity.setAdd_date(date);
				entity.setAdd_user_id(ui.getId());
				entity.setIs_del(0);
				entity.getMap().put("comm_tczh_id", comm_tczh_ids);
				entity.setComm_tczh_ids(comm_tczh_ids);
				super.getFacade().getCommInfoPromotionService().createCommInfoPromotion(entity);
				saveMessage(request, "entity.inerted");

			}
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward edittcfw(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");
		String price_ref = (String) dynaBean.get("price_ref");
		saveToken(request);

		CommInfoPromotion entity = new CommInfoPromotion();
		entity.setId(Integer.valueOf(comm_id));
		entity.setIs_del(0);
		entity = getFacade().getCommInfoPromotionService().getCommInfoPromotion(entity);
		if (null == entity) {
			saveMessage(request, "entity.missing");
			return mapping.findForward("list");
		}
		// if (null != a.getIs_buyer_limit() && a.getIs_buyer_limit().intValue() == 1) {
		request.setAttribute("is_buyer_limit", entity.getIs_buyer_limit());
		logger.info("=====is_buyer_limit" + entity.getIs_buyer_limit());
		// }

		// 套餐管理
		CommTczhPrice param_ctp = new CommTczhPrice();
		param_ctp.setComm_id(comm_id);
		param_ctp.setTczh_type(Keys.tczh_type.tczh_type_1.getIndex());
		logger.info("====CommTczhPrice");
		List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(param_ctp);
		logger.info("======list_CommTczhPrice:" + list_CommTczhPrice.size());
		if ((list_CommTczhPrice != null) && (list_CommTczhPrice.size() > 0)) {
			for (CommTczhPrice ctp : list_CommTczhPrice) {
				CommTczhAttribute param_ctattr = new CommTczhAttribute();
				param_ctattr.setComm_tczh_id(ctp.getId());
				param_ctattr.setComm_id(comm_id);
				param_ctattr.setType(Keys.tczh_type.tczh_type_1.getIndex());
				param_ctattr.getMap().put("orderby_order_value_asc", "true");
				List<CommTczhAttribute> list_CommTczhAttribute = super.getFacade().getCommTczhAttributeService()
						.getCommTczhAttributeList(param_ctattr);
				List<String> attr_tczh_ids = new ArrayList<String>();
				List<String> attr_tczh_names = new ArrayList<String>();
				for (CommTczhAttribute temp_cta : list_CommTczhAttribute) {
					BaseAttributeSon param_bas = new BaseAttributeSon();
					param_bas.setId(Integer.valueOf(temp_cta.getAttr_id()));
					param_bas.setType(Keys.tczh_type.tczh_type_1.getIndex());
					BaseAttributeSon bas = super.getFacade().getBaseAttributeSonService()
							.getBaseAttributeSon(param_bas);
					attr_tczh_ids.add(temp_cta.getAttr_id().toString());
					logger.info("====id:" + temp_cta.getId());
					logger.info("====attr_name:" + temp_cta.getAttr_name());
					attr_tczh_names.add(temp_cta.getAttr_name());
				}

				ctp.getMap().put("attr_tczh_ids", StringUtils.join(attr_tczh_ids, ","));
				ctp.getMap().put("attr_tczh_names", StringUtils.join(attr_tczh_names, " "));

			}
			request.setAttribute("list_CommTczhPrice", list_CommTczhPrice); // 套餐价格及对应各属性
		}

		// comm_id
		dynaBean.set("comm_id", comm_id);
		dynaBean.set("price_ref", price_ref);
		return new ActionForward("/../manager/admin/GroupCommInfo/tcfwform.jsp");
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

		String[] inventory = request.getParameterValues("inventory");
		String[] tczh_prices = request.getParameterValues("tczh_price");
		String[] attr_tczh_ids = request.getParameterValues("attr_tczh_id");
		String[] comm_tczh_ids = request.getParameterValues("comm_tczh_id");
		String[] comm_tczh_names = request.getParameterValues("comm_tczh_name");
		String[] cost_prices = request.getParameterValues("cost_price");
		String[] buyer_limit_num = request.getParameterValues("buyer_limit_num");

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
				ctp.setTczh_type(Keys.tczh_type.tczh_type_1.getIndex());
				if (cost_prices != null) {
					ctp.setCost_price(new BigDecimal(cost_prices[i]));
				}
				ctp.setInventory(Integer.valueOf(inventory[i])); // 套餐库存

				if ((comm_tczh_ids != null) && StringUtils.isNotBlank(comm_tczh_ids[i])) {
					ctp.setId(Integer.valueOf(comm_tczh_ids[i]));
					modifyCommFlag = true;
				}
				if ((buyer_limit_num != null) && StringUtils.isNotBlank(buyer_limit_num[i])) {
					ctp.setBuyer_limit_num(Integer.valueOf(buyer_limit_num[i]));
				}
				List<CommTczhAttribute> list_CommTczhAttribute = new ArrayList<CommTczhAttribute>();
				String[] price_attr_tczh_ids = attr_tczh_ids[i].split(",");
				for (int ind = 0; ind < price_attr_tczh_ids.length; ind++) {
					CommTczhAttribute cta = new CommTczhAttribute();
					cta.setAttr_id(price_attr_tczh_ids[ind]);
					cta.setAttr_name(comm_tczh_names[i]);
					cta.setComm_id(comm_id);
					cta.setOrder_value(ind);
					list_CommTczhAttribute.add(cta);
				}
				ctp.setCommTczhAttributeList(list_CommTczhAttribute);
				list_CommTczhPrice.add(ctp);
			}
		}

		if (StringUtils.isNotBlank(is_work) && is_work.endsWith("1")) {
			ctp_entity.getMap().put("updateAttrAndSonIsWork", true);
		}

		ctp_entity.setComm_id(comm_id);
		ctp_entity.setCommTczhPriceList(list_CommTczhPrice);

		CommInfoPromotion commInfoPromotion = new CommInfoPromotion();
		commInfoPromotion.setId(Integer.valueOf(comm_id));
		commInfoPromotion = getFacade().getCommInfoPromotionService().getCommInfoPromotion(commInfoPromotion);
		if (null != commInfoPromotion) {
			CommInfoPromotion updateTGInfo = new CommInfoPromotion();
			updateTGInfo.setId(Integer.valueOf(comm_id));
			updateTGInfo.setProm_price(list_CommTczhPrice.get(0).getComm_price());
			updateTGInfo.setNo_dist_price(list_CommTczhPrice.get(0).getCost_price());
			// updateTGInfo.setProm_sale_acount(list_CommTczhPrice.get(0).getInventory());
			updateTGInfo.setBuyer_limit_num(list_CommTczhPrice.get(0).getBuyer_limit_num());
			updateTGInfo.setProm_inventory(list_CommTczhPrice.get(0).getInventory());
			logger.info("=====修改团购表信息");
			getFacade().getCommInfoPromotionService().modifyCommInfoPromotion(updateTGInfo);
		}

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
		// pathBuffer.append("&comm_type=" + comm_type);
		// pathBuffer.append("&");
		// pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward save1(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='login.shtml'}");
			return null;
		}

		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");
		String cls_id = (String) dynaBean.get("cls_id");
		String comm_tczh_id = (String) dynaBean.get("comm_tczh_id");
		String id = (String) dynaBean.get("id");

		Date date = new Date();
		CommInfoPromotion entity = new CommInfoPromotion();

		super.copyProperties(entity, form);

		resetToken(request);

		if (StringUtils.isNotBlank(id)) {
			entity.setUpdate_date(date);
			entity.setUpdate_user_id(ui.getId());
			super.getFacade().getCommInfoPromotionService().modifyCommInfoPromotion(entity);
			saveMessage(request, "entity.updated");

			// the line below is added for pagination
			StringBuffer pathBuffer = new StringBuffer();
			pathBuffer.append(mapping.findForward("success").getPath());
			pathBuffer.append("&mod_id=" + mod_id);
			pathBuffer.append("&par_id=" + par_id);
			pathBuffer.append("&");
			pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
			ActionForward forward = new ActionForward(pathBuffer.toString(), true);
			// end
			return forward;

		} else {
			entity.setAdd_date(date);
			entity.setAdd_user_id(ui.getId());
			entity.setIs_del(0);
			super.getFacade().getCommInfoPromotionService().createCommInfoPromotion(entity);
			saveMessage(request, "entity.inerted");

			saveToken(request);
			List<CommTczhPrice> commTczhPriceList = new ArrayList<CommTczhPrice>();
			String str[] = comm_tczh_id.split(",");
			for (String s : str) {

				BaseAttributeSon b = new BaseAttributeSon();

				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setId(Integer.valueOf(s));
				commTczhPrice = super.getFacade().getCommTczhPriceService().getCommTczhPrice(commTczhPrice);
				if (null != commTczhPrice) {
					CommTczhAttribute a = new CommTczhAttribute();
					a.setComm_tczh_id(Integer.valueOf(s));
					a.setComm_id(entity.getComm_id().toString());
					a = getFacade().getCommTczhAttributeService().getCommTczhAttribute(a);
					if (null != a) {
						commTczhPrice.getMap().put("attr_name", a.getAttr_name());
					}
					commTczhPriceList.add(commTczhPrice);
					// temp.getMap().put("comm_price", commTczhPrice.getComm_price());
				}

			}
			request.setAttribute("commTczhPriceList", commTczhPriceList);
			// comm_id
			// dynaBean.set("comm_id", entity.getComm_id());
			// dynaBean.set("price_ref", price_ref);
			// dynaBean.set("comm_id", insert_id);
			// dynaBean.set("comm_type", comm_type);
			return new ActionForward("/../manager/admin/GroupCommInfo/tcfwform.jsp");
		}

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

		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		if (GenericValidator.isLong(id)) {
			CommInfoPromotion entity = new CommInfoPromotion();
			entity.setId(Integer.valueOf(id));
			entity = getFacade().getCommInfoPromotionService().getCommInfoPromotion(entity);

			if (null == entity) {
				saveMessage(request, "entity.missing");
				return mapping.findForward("list");
			}

			if (null != entity.getComm_id()) {
				CommInfo commInfo = super.getCommInfoOnlyById(entity.getComm_id());
				if (null != commInfo) {
					dynaBean.set("comm_name", commInfo.getComm_name());
				}
			}
			logger.info("====entity.getComm_tczh_ids()" + entity.getComm_tczh_ids());
			if (null == entity.getComm_tczh_ids() || entity.getComm_tczh_ids().equals("")) {
				logger.info("====套餐ids为空的情况");
				CommTczhAttribute idsNullAttr = new CommTczhAttribute();
				idsNullAttr.setComm_id(id);
				idsNullAttr.setType(Keys.tczh_type.tczh_type_1.getIndex());
				List<CommTczhAttribute> commTczhAttributeList = getFacade().getCommTczhAttributeService()
						.getCommTczhAttributeList(idsNullAttr);
				if (null != commTczhAttributeList && commTczhAttributeList.size() > 0) {
					String comm_tczh_ids = "";
					for (CommTczhAttribute temp : commTczhAttributeList) {
						if ("" == comm_tczh_ids) {
							comm_tczh_ids = comm_tczh_ids + temp.getComm_tczh_id();
						} else {
							comm_tczh_ids = comm_tczh_ids + "," + temp.getComm_tczh_id();
						}
					}
					CommInfoPromotion updataTGCommInfo = new CommInfoPromotion();
					updataTGCommInfo.setId(Integer.valueOf(id));
					updataTGCommInfo.setComm_tczh_ids(comm_tczh_ids);
					logger.info("====更新团购商品");
					getFacade().getCommInfoPromotionService().modifyCommInfoPromotion(updataTGCommInfo);
					entity = getFacade().getCommInfoPromotionService().getCommInfoPromotion(entity);
				}

			}
			if (null != entity.getTg_tczh_ids()) {
				String comm_tczh_name = "";
				logger.info("===进入拼套餐名称");
				logger.info("===entity.getComm_tczh_ids()" + entity.getComm_tczh_ids());
				if (null != entity.getTg_tczh_ids()) {
					String tg_tczh_ids[] = entity.getTg_tczh_ids().split(",");
					List<String> list = Arrays.asList(tg_tczh_ids);
					// for (int i = 0; i <= comm_tczh_id.length; i++) {
					for (String temp : list) {
						if (null != temp && !temp.equals("")) {
							logger.info("===temp:" + temp);
							CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
							commTczhAttribute.setComm_tczh_id(Integer.valueOf(temp));
							// commTczhAttribute.setOrder_value(1);
							commTczhAttribute.setType(Keys.tczh_type.tczh_type_1.getIndex());
							logger.info("===查询当前套餐的名称");
							commTczhAttribute = super.getFacade().getCommTczhAttributeService()
									.getCommTczhAttribute(commTczhAttribute);
							if (null != commTczhAttribute) {
								if (comm_tczh_name.equals("")) {
									comm_tczh_name = commTczhAttribute.getAttr_name();
								} else {
									comm_tczh_name = comm_tczh_name + "," + commTczhAttribute.getAttr_name();
								}

							}
							logger.info("===套餐名称:" + comm_tczh_name);
						}
					}
				} else {
					String comm_tczh_ids[] = entity.getComm_tczh_ids().split(",");
					List<String> list = Arrays.asList(comm_tczh_ids);

					// for (int i = 0; i <= comm_tczh_id.length; i++) {
					for (String temp : list) {
						if (null != temp && !temp.equals("")) {
							logger.info("===temp:" + temp);
							CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
							commTczhAttribute.setComm_tczh_id(Integer.valueOf(temp));
							commTczhAttribute.setOrder_value(1);
							commTczhAttribute.setType(Keys.tczh_type.tczh_type_0.getIndex());
							commTczhAttribute = super.getFacade().getCommTczhAttributeService()
									.getCommTczhAttribute(commTczhAttribute);
							if (null != commTczhAttribute) {
								if (comm_tczh_name.equals("")) {
									comm_tczh_name = commTczhAttribute.getAttr_name();
								} else {
									comm_tczh_name = comm_tczh_name + "," + commTczhAttribute.getAttr_name();
								}

							}
							logger.info("===套餐名称:" + comm_tczh_name);
						}
					}

				}

				logger.info("===套餐名称:" + comm_tczh_name);
				dynaBean.set("comm_tczh_name", comm_tczh_name);

			}

			if (null != entity.getOwn_entp_id()) {
				EntpInfo entpInfo = super.getEntpInfo(entity.getOwn_entp_id(), null, null);
				if (null != entpInfo) {
					String entp_name = entpInfo.getEntp_name();
					dynaBean.set("entp_name", entp_name);
				}
			}

			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);

			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		String mod_id = (String) dynaBean.get("mod_id");
		String par_id = (String) dynaBean.get("par_id");

		if (StringUtils.isBlank(id) && ArrayUtils.isEmpty(pks)) {
			String msg = super.getMessage(request, "参数不能为空");
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		CommInfoPromotion entity = new CommInfoPromotion();
		Date date = new Date();
		entity.setIs_del(1);
		entity.setDel_date(date);
		entity.setDel_user_id(ui.getId());
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {
			entity.setId(Integer.valueOf(id));
			super.getFacade().getCommInfoPromotionService().modifyCommInfoPromotion(entity);
			saveMessage(request, "entity.deleted");

		} else if (ArrayUtils.isNotEmpty(pks)) {
			entity.getMap().put("pks", pks);
			super.getFacade().getCommInfoPromotionService().modifyCommInfoPromotion(entity);
			saveMessage(request, "entity.deleted");
		}

		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=" + mod_id);
		pathBuffer.append("&par_id=" + par_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "cls_id", "id", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward listCommTc(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String sygz_id = (String) dynaBean.get("sygz_id");
		String sygz_names = (String) dynaBean.get("sygz_names");
		String cls_name_like = (String) dynaBean.get("cls_name_like");

		BaseClass baseclass = new BaseClass();

		baseclass.setIs_del(0);
		baseclass.setCls_level(2);
		if (StringUtils.isNotBlank(cls_name_like)) {
			baseclass.getMap().put("cls_name_like", cls_name_like);
		}

		List<BaseClass> baseclassList = getFacade().getBaseClassService().getBaseClassList(baseclass);
		request.setAttribute("baseclassList", baseclassList);

		if (StringUtils.isNotBlank(sygz_id)) {
			BaseClass baseclass2 = new BaseClass();
			baseclass2.setIs_del(0);
			baseclass2.setCls_level(2);
			baseclass2.getMap().put("cls_ids", sygz_id);
			List<BaseClass> baseclass2List = getFacade().getBaseClassService().getBaseClassList(baseclass2);
			request.setAttribute("baseclass2List", baseclass2List);
		}

		dynaBean.set("sygz_id", sygz_id);
		dynaBean.set("sygz_names", sygz_names);
		return new ActionForward("/../manager/admin/YHQInfo/listPdType.jsp");
	}
}