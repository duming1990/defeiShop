package com.ebiz.webapp.web.struts.manager.admin;

import java.math.BigDecimal;
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
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.web.Keys;

public class VillageCommInfoAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String add_user_name = (String) dynaBean.get("add_user_name");
		String is_del = (String) dynaBean.get("is_del");
		String comm_no_like = (String) dynaBean.get("comm_no_like");
		String audit_state = (String) dynaBean.get("audit_state");
		String cls_id = (String) dynaBean.get("cls_id");
		String country = (String) dynaBean.get("country");
		String city = (String) dynaBean.get("city");
		String province = (String) dynaBean.get("province");
		String own_village_id = (String) dynaBean.get("own_entp_id");// 村圈子产品为所属村庄id
		String today_date = (String) dynaBean.get("today_date");
		String comm_type = (String) dynaBean.get("comm_type");

		CommInfo entity = new CommInfo();
		super.copyProperties(entity, form);

		if (StringUtils.isNotBlank(today_date)) {
			entity.getMap().put("today_date", today_date);
		}
		// if (StringUtils.isNotBlank(country)) {
		// entity.setP_index(Integer.valueOf(country));
		// } else if (StringUtils.isNotBlank(city)) {
		// entity.getMap().put("p_index_city", StringUtils.substring(city, 0, 4));
		// } else if (StringUtils.isNotBlank(province)) {
		// entity.getMap().put("p_index_province", StringUtils.substring(province, 0, 2));
		// }

		if (StringUtils.isBlank(comm_type)) {
			entity.setComm_type(Keys.CommType.COMM_TYPE_7.getIndex());
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
		if (StringUtils.isNotBlank(add_user_name)) {
			entity.getMap().put("add_user_name", add_user_name);
		}
		if (StringUtils.isNotBlank(comm_no_like)) {
			entity.getMap().put("comm_no_like", comm_no_like);
		}

		Integer recordCount = getFacade().getCommInfoService().getCommInfoNameCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPageList(entity);
		for (CommInfo ci : entityList) {
			VillageDynamic vDynamic = new VillageDynamic();
			vDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
			vDynamic.setComm_id(ci.getId());
			vDynamic = super.getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
			if (vDynamic != null) {
				ci.getMap().put("village_dynamic", vDynamic);
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
		}
		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");

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
				// 动态信息
				VillageDynamic vDynamic = new VillageDynamic();
				vDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
				vDynamic.setComm_id(entity.getId());
				vDynamic = super.getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
				if (vDynamic != null) {
					request.setAttribute("village_dynamic", vDynamic);
				}
				request.setAttribute("entity", entity);
			}

			entity.setQueryString(super.serialize(request, "id", "method"));
			super.copyProperties(form, entity);

			// 套餐管理
			CommTczhPrice param_ctp = new CommTczhPrice();
			param_ctp.setComm_id(id);
			List<CommTczhPrice> list_CommTczhPrice = super.getFacade().getCommTczhPriceService()
					.getCommTczhPriceList(param_ctp);
			request.setAttribute("list_CommTczhPrice", list_CommTczhPrice);

			return mapping.findForward("input");
		} else {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}
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
		String cls_id = (String) dynaBean.get("cls_id");
		String[] file_path_lbts = request.getParameterValues("file_path");
		String qq = (String) dynaBean.get("qq");

		String tczh_names = (String) dynaBean.get("tczh_names");
		String tczh_prices = (String) dynaBean.get("tczh_prices");
		String inventorys = (String) dynaBean.get("inventorys");
		String orig_prices = (String) dynaBean.get("orig_prices");
		String buyer_limit_nums = (String) dynaBean.get("buyer_limit_nums");

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

		BigDecimal rebate_scale = new BigDecimal(0);
		if (null != entity.getRebate_scale()) {
			rebate_scale = entity.getRebate_scale();
		}
		BigDecimal aid_scale = new BigDecimal(0);
		if (null != entity.getAid_scale()) {
			aid_scale = entity.getAid_scale();
		}

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

		if (StringUtils.isNotBlank(tczh_names) && StringUtils.isNotBlank(tczh_prices)
				&& StringUtils.isNotBlank(inventorys)) {
			entity.setIs_has_tc(1);
			entity.getMap().put("tczh_names", tczh_names);
			entity.getMap().put("tczh_prices", tczh_prices);
			entity.getMap().put("inventorys", inventorys);
			entity.getMap().put("update_comm_tczh_price", true);
		}

		if (StringUtils.isNotBlank(orig_prices) && StringUtils.isNotBlank(buyer_limit_nums)) {
			entity.getMap().put("orig_prices", orig_prices);
			entity.getMap().put("buyer_limit_nums", buyer_limit_nums);
			entity.getMap().put("update_comm_tczh_price", true);
		}

		if (StringUtils.isNotBlank(id)) {
			entity.setUpdate_date(date);
			entity.setUpdate_user_id(ui.getId());
			if (StringUtils.isNotBlank(audit_state)) {
				entity.setAudit_user_id(ui.getId());
				entity.setAudit_date(new Date());
				entity.setAudit_user_id(ui.getId());
				entity.setAudit_date(date);

				VillageDynamic vDynamic = new VillageDynamic();
				vDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
				vDynamic.setComm_id(entity.getId());
				vDynamic = super.getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
				if (vDynamic != null) {
					vDynamic.setAudit_user_id(ui.getId());
					vDynamic.setAudit_date(new Date());
					vDynamic.setAudit_user_id(ui.getId());
					vDynamic.setAudit_date(date);
					entity.getMap().put("update_dynamic", vDynamic);
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
			entity.setAdd_date(date);
			entity.setIs_del(0);
			entity.setAudit_state(1);
			entity.setAudit_user_id(ui.getId());
			entity.setAudit_date(date);

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

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

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
						List<CommTczhAttribute> list_CommTczhAttribute = super.getFacade()
								.getCommTczhAttributeService().getCommTczhAttributeList(param_ctattr);
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

					// 动态信息
					VillageDynamic vDynamic = new VillageDynamic();
					vDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
					vDynamic.setComm_id(entity.getId());
					vDynamic = super.getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
					if (vDynamic != null) {
						request.setAttribute("village_dynamic", vDynamic);
					}
				}

				request.setAttribute("commTypeList", Keys.CommType.values());
				request.setAttribute("fanXianTypes", Keys.FanXianTypeComm.values());

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
			return new ActionForward("/../manager/admin/VillageCommInfo/audit.jsp");
		} else {
			saveError(request, "errors.Integer", comm_id);
			return mapping.findForward("list");
		}
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
			// 动态信息
			VillageDynamic vDynamic = new VillageDynamic();
			vDynamic.setType(Keys.DynamicType.dynamic_type_2.getIndex());
			vDynamic.setComm_id(entity.getId());
			vDynamic = super.getFacade().getVillageDynamicService().getVillageDynamic(vDynamic);
			if (vDynamic != null) {
				request.setAttribute("village_dynamic", vDynamic);
			}

			return mapping.findForward("view");
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
		if (StringUtils.isNotBlank(id) && GenericValidator.isLong(id)) {

			entity.setId(Integer.valueOf(id));
			entity.getMap().put("commType7DeleteLinkVillageDynamic", "true");
			super.getFacade().getCommInfoService().modifyCommInfo(entity);

			saveMessage(request, "entity.deleted");

		} else if (ArrayUtils.isNotEmpty(pks)) {

			String[] pd_ids = new String[pks.length];
			for (int i = 0; i < pks.length; i++) {
				pd_ids[i] = pks[i].split("_")[0];
			}

			entity.getMap().put("forEach_pks_and_deleteVillageDynamic", pd_ids);
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

}
