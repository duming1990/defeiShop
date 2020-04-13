package com.ebiz.webapp.web.struts.manager.admin;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseComminfoTags;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoTags;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class BiaoQianGuanLiAction extends BaseAdminAction {
	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
			return super.checkPopedomInvalid(request, response);
		}
		setNaviStringToRequestScope(request);
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;

		dynaBean.set("order_value", "0");
		dynaBean.set("is_lock", "0");
		// BaseData baseData = new BaseData();
		// baseData.setType(Keys.BaseDataType.Base_Data_type_301.getIndex());
		// baseData.setIs_del(0);
		// List<BaseData> list = getFacade().getBaseDataService().getBaseDataList(baseData);
		// if (list != null && list.size() > 0) {
		// request.setAttribute("BaseDatalist", list);
		// }

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		baseProvince.setP_level(1);
		List<BaseProvince> baseProvinceList = super.getFacade().getBaseProvinceService()
				.getBaseProvinceList(baseProvince);
		request.setAttribute("baseProvinceList", baseProvinceList);

		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "0")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String is_del = (String) dynaBean.get("is_del");
		String is_lock = (String) dynaBean.get("is_lock");
		String tag_name_like = (String) dynaBean.get("tag_name_like");
		Pager pager = (Pager) dynaBean.get("pager");

		BaseComminfoTags entity = new BaseComminfoTags();

		super.copyProperties(entity, dynaBean);

		if (null == is_del) {
			entity.setIs_del(0);
			dynaBean.set("is_del", "0");
		}
		if (StringUtils.isNotBlank(is_lock)) {
			entity.setIs_lock((Integer.valueOf(is_lock)));
			dynaBean.set("is_lock", is_lock);
		}
		entity.getMap().put("tag_name_like", tag_name_like);
		Integer recordCount = getFacade().getBaseComminfoTagsService().getBaseComminfoTagsCount(entity);
		pager.init(recordCount.longValue(), 15, pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<BaseComminfoTags> list = getFacade().getBaseComminfoTagsService()
				.getBaseComminfoTagsPaginatedListByPIndex(entity);
		// TODO 新增频道类型，p_index 0：全国，340000：省级

		request.setAttribute("BaseComminfoTagsList", list);
		return mapping.findForward("list");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "2")) {
			return super.checkPopedomInvalid(request, response);
		}

		setNaviStringToRequestScope(request);
		saveToken(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (!GenericValidator.isLong(id)) {
			saveError(request, "errors.Integer", id);
			return mapping.findForward("list");
		}

		BaseComminfoTags entity = new BaseComminfoTags();
		entity.setId(Integer.valueOf(id));
		entity = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(entity);
		logger.info("========getCls_name=========" + entity.getCls_name());
		logger.info("==========getCls_id=======" + entity.getCls_id());
		super.copyProperties(form, entity);
		// BaseData baseData = new BaseData();
		// baseData.setType(Keys.BaseDataType.Base_Data_type_301.getIndex());
		// baseData.setIs_del(0);
		// List<BaseData> list = getFacade().getBaseDataService().getBaseDataList(baseData);
		// if (list != null && list.size() > 0) {
		// request.setAttribute("BaseDatalist", list);
		// }

		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		baseProvince.setP_level(1);
		List<BaseProvince> baseProvinceList = super.getFacade().getBaseProvinceService()
				.getBaseProvinceList(baseProvince);
		request.setAttribute("baseProvinceList", baseProvinceList);
		return mapping.findForward("input");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String mod_id = (String) dynaBean.get("mod_id");
		String tag_type = (String) dynaBean.get("tag_type");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		BaseComminfoTags entity = new BaseComminfoTags();
		super.copyProperties(entity, dynaBean);

		if (null != entity.getId()) {// update
			entity.setUpdate_date(new Date());
			entity.setUpdate_user_id(sessionUi.getId());
			if ((null != entity.getIs_del()) && (entity.getIs_del().intValue() == 0)) {
				entity.setDel_date(null);
			}
			getFacade().getBaseComminfoTagsService().modifyBaseComminfoTags(entity);
			saveMessage(request, "entity.updated");
		} else {// insert
			entity.setIs_del(0);
			entity.setAdd_date(new Date());
			entity.setAdd_user_id(sessionUi.getId());
			getFacade().getBaseComminfoTagsService().createBaseComminfoTags(entity);
			saveMessage(request, "entity.inerted");
		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "4")) {
			return super.checkPopedomInvalid(request, response);
		}

		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		String[] pks = (String[]) dynaBean.get("pks");
		UserInfo sessionUi = super.getUserInfoFromSession(request);

		if (!StringUtils.isBlank(id) && GenericValidator.isLong(id)) {
			BaseComminfoTags entity = new BaseComminfoTags();
			entity.setIs_del(1);
			entity.setId(new Integer(id));
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			getFacade().getBaseComminfoTagsService().modifyBaseComminfoTags(entity);
			saveMessage(request, "entity.deleted");
		} else if (!ArrayUtils.isEmpty(pks)) {
			BaseComminfoTags entity = new BaseComminfoTags();
			entity.setIs_del(1);
			entity.setDel_date(new Date());
			entity.setDel_user_id(sessionUi.getId());
			entity.getMap().put("pks", pks);
			getFacade().getBaseComminfoTagsService().modifyBaseComminfoTags(entity);
			saveMessage(request, "entity.deleted");
		}
		// the line below is added for pagination
		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&");
		pathBuffer.append(super.encodeSerializedQueryString(super.serialize(request, "id", "ids", "method")));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		// end
		return forward;
	}

	public ActionForward chooseCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (null == super.checkUserModPopeDom(form, request, "1")) {
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
		String tag_id = (String) dynaBean.get("biaoqianid");
		String is_select = (String) dynaBean.get("is_select");// 是否选择
		String entp_name_like = (String) dynaBean.get("entp_name_like");

		BaseComminfoTags baseComminfoTags = new BaseComminfoTags();
		baseComminfoTags.setId(Integer.valueOf(tag_id));
		baseComminfoTags.setIs_del(0);
		baseComminfoTags = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(baseComminfoTags);
		request.setAttribute("tag_name", baseComminfoTags.getTag_name());

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

		if (StringUtils.isNotBlank(comm_type)) {
			entity.setComm_type(Integer.valueOf(comm_type));
		}
		if (StringUtils.isNotBlank(entp_name_like)) {
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.getMap().put("entp_name_like", entp_name_like);
			List<EntpInfo> list = getFacade().getEntpInfoService().getEntpInfoList(entpInfo);
			if (list != null && list.size() > 0) {
				String entp_id_in = "1";
				for (EntpInfo temp : list) {
					entp_id_in = entp_id_in + "," + temp.getId();
				}
				entity.getMap().put("entp_id_in", entp_id_in);
			}
		}

		if (StringUtils.isNotBlank(audit_state)) {
			entity.setAudit_state(Integer.parseInt(audit_state));
		} else {
			entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
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
		if (StringUtils.isNotBlank(cls_id)) {
			if (!cls_id.equals("1")) {
				entity.setCls_id(null);
				entity.setCls_name(null);
				entity.getMap().put("allPd", "true");
				entity.getMap().put("par_cls_id", cls_id);
			} else {
				entity.setCls_id(null);
				entity.setCls_name(null);
			}
		} else if (baseComminfoTags.getCls_id() != null && baseComminfoTags.getCls_id() != 1) {
			entity.setCls_id(null);
			entity.setCls_name(null);
			entity.getMap().put("allPd", "true");
			entity.getMap().put("par_cls_id", baseComminfoTags.getCls_id());
			dynaBean.set("cls_id", baseComminfoTags.getCls_id());
			dynaBean.set("cls_name", baseComminfoTags.getCls_name());
		}
		// 默认显示已选择
		is_select = "1";
		if (StringUtils.isNotBlank(is_select)) {

			if (is_select.equals("0")) {
				// 未选择的标签商品
				entity.getMap().put("not_select", true);
				entity.getMap().put("tag_id_not_in", tag_id);
			} else {
				// 已选择的标签商品
				entity.getMap().put("is_select", true);
				entity.getMap().put("tag_id", tag_id);
				entity.getMap().put("comm_info_tags_order_value", true);
			}
			request.setAttribute("is_select", is_select);
		}

		entity.getMap().put("not_out_sell_time", true);// 在上架期间
		entity.getMap().put("comm_type_in", Keys.CommType.COMM_TYPE_2.getIndex());
		entity.setIs_sell(1);

		Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("20"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<CommInfo> entityList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(entity);

		if (entityList != null && entityList.size() > 0) {
			for (CommInfo dm : entityList) {
				CommInfoTags commInfoTags = new CommInfoTags();
				commInfoTags.setTag_id(Integer.valueOf(tag_id));
				commInfoTags.setComm_id(dm.getId());
				List<CommInfoTags> comminfotagslist = getFacade().getCommInfoTagsService().getCommInfoTagsList(
						commInfoTags);
				if (comminfotagslist != null && comminfotagslist.size() > 0) {
					dm.getMap().put("comm_tags_cunzai", dm.getId());
					dm.getMap().put("order_value", comminfotagslist.get(0).getOrder_value());
				}
			}
			super.copyProperties(form, entityList);
			request.setAttribute("entityList", entityList);
		}
		return new ActionForward("/../manager/admin/BiaoQianGuanLi/choose_.jsp");
	}

	/**
	 * @desc 保存标签商品
	 */
	public ActionForward savetags(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		resetToken(request);
		JSONObject data = new JSONObject();
		int ret = 1;
		String msg = "保存成功";
		DynaBean dynaBean = (DynaBean) form;

		String biaoqianid = (String) dynaBean.get("biaoqianid");
		String id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(id)) {
			ret = -1;
			msg = "id参数不正确";
			return returnJson(ret, msg, data, response);
		}

		UserInfo sessionUi = super.getUserInfoFromSession(request);

		BaseComminfoTags baseComminfoTags = new BaseComminfoTags();
		baseComminfoTags.setId(Integer.valueOf(biaoqianid));
		baseComminfoTags = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(baseComminfoTags);
		if (null == baseComminfoTags) {
			return returnJson(-1, "标签参数不正确", data, response);
		}

		// CommInfoTags entity = new CommInfoTags();
		// entity.getMap().put("tag_id", baseComminfoTags.getId());
		// getFacade().getCommInfoTagsService().removeCommInfoTags(entity);

		CommInfoTags entity = new CommInfoTags();
		entity.setComm_id(Integer.valueOf(id));
		entity.setTag_id(baseComminfoTags.getId());
		entity.setTag_name(baseComminfoTags.getTag_name());
		entity.setTag_type(baseComminfoTags.getTag_type());
		entity.setAdd_date(new Date());
		entity.setAdd_user_id(Long.valueOf((sessionUi.getId())));
		getFacade().getCommInfoTagsService().createCommInfoTags(entity);

		return returnJson(ret, msg, data, response);

	}

	public ActionForward saveAllTags(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		resetToken(request);

		DynaBean dynaBean = (DynaBean) form;
		JSONObject data = new JSONObject();

		String mod_id = (String) dynaBean.get("mod_id");
		String biaoqianid = (String) dynaBean.get("biaoqianid");
		String[] pks = (String[]) dynaBean.get("pks");
		if (ArrayUtils.isEmpty(pks)) {
			data.put("ret", 0);
			data.put("msg", "请至少选择一个需添加的商品！");
			super.renderJson(response, data.toString());
			return null;
		}

		UserInfo sessionUi = super.getUserInfoFromSession(request);

		BaseComminfoTags baseComminfoTags = new BaseComminfoTags();
		baseComminfoTags.setId(Integer.valueOf(biaoqianid));
		baseComminfoTags = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(baseComminfoTags);

		for (int i = 0; i < pks.length; i++) {

			CommInfoTags entity1 = new CommInfoTags();
			entity1.setComm_id(Integer.valueOf(pks[i]));
			entity1.setTag_id(baseComminfoTags.getId());
			CommInfoTags entity2 = getFacade().getCommInfoTagsService().getCommInfoTags(entity1);
			if (entity2 != null) {
				CommInfo cInfo = new CommInfo();
				cInfo.setId(Integer.valueOf(pks[i]));
				cInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				cInfo = getFacade().getCommInfoService().getCommInfo(cInfo);

				data.put("ret", 0);
				data.put("msg", cInfo.getComm_name() + "该商品在标签内已存在！");
				super.renderJson(response, data.toString());
				return null;
			}
			entity1.setTag_name(baseComminfoTags.getTag_name());
			entity1.setTag_type(baseComminfoTags.getTag_type());
			entity1.setAdd_date(new Date());
			entity1.setAdd_user_id(Long.valueOf((sessionUi.getId())));
			int flag = getFacade().getCommInfoTagsService().createCommInfoTags(entity1);
			if (Integer.valueOf(flag) < 1) {
				CommInfo cInfo = new CommInfo();
				cInfo.setId(Integer.valueOf(pks[i]));
				cInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				cInfo = getFacade().getCommInfoService().getCommInfo(cInfo);

				data.put("ret", -1);
				data.put("msg", cInfo.getComm_name() + "添加失败！");
				super.renderJson(response, data.toString());
				return null;
			}
		}
		data.put("ret", 1);
		data.put("msg", "添加成功！");
		super.renderJson(response, data.toString());
		return null;
	}

	public ActionForward delettags(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		// if (isCancelled(request)) {
		// return list(mapping, form, request, response);
		// }
		// if (!isTokenValid(request)) {
		// saveError(request, "errors.token");
		// return list(mapping, form, request, response);
		// }
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;

		String mod_id = (String) dynaBean.get("mod_id");
		String tag_id = (String) dynaBean.get("biaoqianid");
		String id = (String) dynaBean.get("id");
		JSONObject data = new JSONObject();
		if (id != null) {

			CommInfoTags entity1 = new CommInfoTags();
			entity1.setComm_id(Integer.valueOf(id));

			entity1.setTag_id(Integer.valueOf(tag_id));

			entity1 = getFacade().getCommInfoTagsService().getCommInfoTags(entity1);
			if (entity1 != null) {
				getFacade().getCommInfoTagsService().removeCommInfoTags(entity1);

				saveMessage(request, "entity.deleted");
				data.put("ret", 1);
				data.put("msg", "删除商品成功");

				super.renderJson(response, data.toString());
				return null;
			}

		}

		StringBuffer pathBuffer = new StringBuffer();
		pathBuffer.append(mapping.findForward("success").getPath());
		pathBuffer.append("&mod_id=").append(mod_id);
		pathBuffer.append("&");
		// pathBuffer.append(super.encodeSerializedQueryString(entity.getQueryString()));
		ActionForward forward = new ActionForward(pathBuffer.toString(), true);
		return forward;
	}

	public ActionForward rank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String tag_id = (String) dynaBean.get("tag_id");
		String comm_id = (String) dynaBean.get("id");
		if (StringUtils.isBlank(tag_id) || StringUtils.isBlank(comm_id)) {
			String msg = "参数错误！";
			super.showMsgForManager(request, response, msg);
			return null;
		}
		CommInfo commInfo = new CommInfo();
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		if (commInfo != null) {
			request.setAttribute("comm_name", commInfo.getComm_name());
		}

		BaseComminfoTags baseComminfoTags = new BaseComminfoTags();
		baseComminfoTags.setId(Integer.valueOf(tag_id));
		baseComminfoTags.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		baseComminfoTags = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(baseComminfoTags);
		if (baseComminfoTags != null) {
			request.setAttribute("tag_name", baseComminfoTags.getTag_name());
		}
		CommInfoTags entity = new CommInfoTags();
		entity.setComm_id(Integer.valueOf(comm_id));
		entity.setTag_id(Integer.valueOf(tag_id));
		entity = getFacade().getCommInfoTagsService().getCommInfoTags(entity);
		if (entity != null) {
			request.setAttribute("order_value", entity.getOrder_value());
		}
		return new ActionForward("/../manager/admin/BiaoQianGuanLi/rank.jsp");
	}

	public ActionForward saveRank(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("==============saveRank=============");
		setNaviStringToRequestScope(request);
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;

		String mod_id = (String) dynaBean.get("mod_id");
		String tag_id = (String) dynaBean.get("tag_id");
		String comm_id = (String) dynaBean.get("comm_id");
		String order_value = (String) dynaBean.get("order_value");
		logger.info("==============order_value=============" + order_value);

		JSONObject data = new JSONObject();
		if (StringUtils.isBlank(tag_id) || StringUtils.isBlank(comm_id)) {
			data.put("msg", "参数错误！");
			data.put("ret", 0);
			super.renderJavaScript(response, data.toString());
			return null;
		}

		CommInfoTags entity = new CommInfoTags();
		entity.setComm_id(Integer.valueOf(comm_id));
		entity.setTag_id(Integer.valueOf(tag_id));
		entity = getFacade().getCommInfoTagsService().getCommInfoTags(entity);
		if (entity != null) {
			if (StringUtils.isNotBlank(order_value)) {
				entity.setOrder_value(Integer.valueOf(order_value));
			}
			getFacade().getCommInfoTagsService().modifyCommInfoTags(entity);

			data.put("ret", 1);
			data.put("msg", "排序成功");
			super.renderJson(response, data.toString());
			return null;
		} else {
			data.put("ret", 0);
			data.put("msg", "参数错误！");
			super.renderJson(response, data.toString());
			return null;
		}

	}

	public ActionForward indexLock(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;

		String tag_id = (String) dynaBean.get("id");
		String index_loock = (String) dynaBean.get("index_loock");

		JSONObject data = new JSONObject();
		if (StringUtils.isBlank(tag_id)) {
			data.put("msg", "参数错误！");
			data.put("ret", 0);
			super.renderJavaScript(response, data.toString());
			return null;
		}
		BaseComminfoTags entity = new BaseComminfoTags();
		entity.setId(Integer.valueOf(tag_id));
		entity.setIs_del(0);
		entity = getFacade().getBaseComminfoTagsService().getBaseComminfoTags(entity);
		if (entity != null) {
			BaseComminfoTags entityUpdate = new BaseComminfoTags();
			entityUpdate.setId(Integer.valueOf(tag_id));
			entityUpdate.setIndex_lock(Integer.valueOf(index_loock));
			getFacade().getBaseComminfoTagsService().modifyBaseComminfoTags(entityUpdate);
		} else {
			data.put("msg", "没有该标签！");
			data.put("ret", 0);
			super.renderJavaScript(response, data.toString());
			return null;
		}

		data.put("ret", 1);
		data.put("msg", "操作成功");
		super.renderJson(response, data.toString());
		return null;
	}
}