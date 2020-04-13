package com.ebiz.webapp.web.struts.m;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.domain.VillageMember;
import com.ebiz.webapp.web.Keys;

/**
 * @author 王志雄
 * @date 2018年1月22日
 */
public class MServiceCenterInfoAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/*
		 * UserInfo ui = super.getUserInfoFromSession(request); if (null == ui) { String msg = "您还未登录，请先登录系统！"; return
		 * showTipNotLogin(mapping, form, request, response, msg); }
		 */

		return new ActionForward("/../m/MServiceCenterInfo/MServiceCenterInfo_list.jsp");
	}

	public ActionForward getAjaxDataList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "1", msg = "";
		JSONObject data = new JSONObject();
		DynaBean dynaBean = (DynaBean) form;
		String servicecenter_name_like = (String) dynaBean.get("servicecenter_name_like");
		String p_index_like = (String) dynaBean.get("p_index_like");
		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.getMap().put("servicecenter_name_like", servicecenter_name_like);

		if (GenericValidator.isInt(p_index_like)) {
			if (p_index_like.equals(Keys.QUANGUO_P_INDEX)) {
				entity.setP_index(null);
			} else {
				if (p_index_like.endsWith("0000")) {
					entity.getMap().put("p_index_like", StringUtils.substring(p_index_like, 0, 2));
				} else if (p_index_like.endsWith("00")) {
					entity.getMap().put("p_index_like", StringUtils.substring(p_index_like, 0, 4));
				} else {
					entity.setP_index(Integer.valueOf(p_index_like));
				}
			}
		}

		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		List<ServiceCenterInfo> list = getFacade().getServiceCenterInfoService().getServiceCenterInfoList(entity);
		data.put("entityList", list);

		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		/*
		 * UserInfo ui = super.getUserInfoFromSession(request); if (null == ui) { String msg = "您还未登录，请先登录系统！"; return
		 * showTipNotLogin(mapping, form, request, response, msg); }
		 */
		UserInfo ui = super.getUserInfoFromSession(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (ui != null) {
			UserInfo entity = new UserInfo();
			entity.setId(ui.getId());
			entity = super.getFacade().getUserInfoService().getUserInfo(entity);
			if (StringUtils.isNotBlank(entity.getLink_area())) {
				String link_class = entity.getLink_area().substring(1, entity.getLink_area().indexOf("."));
				String link_area = entity.getLink_area().substring(entity.getLink_area().lastIndexOf("=") + 1);
				if (link_area != null || "".equals(link_area)) {
					if (link_area.equals(id) && "MServiceCenterInfo".equals(link_class)) {
						request.setAttribute("link_area", 1);
					}
				}
			}
		}
		return new ActionForward("/../m/MServiceCenterInfo/MServiceCenterInfo_index.jsp");
	}

	public ActionForward queryOtherCountry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String p_index = (String) dynaBean.get("p_index");

		if (StringUtils.isBlank(p_index)) {
			msg = "参数有误！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		ServiceCenterInfo entity = new ServiceCenterInfo();
		entity.setP_index(Integer.valueOf(p_index));
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity = getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);

		if (null == entity) {
			msg = "很抱歉，该区域暂未开通合伙人！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		data.put("entity", entity);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;

	}

	public ActionForward photo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "县相册");

		return new ActionForward("/../m/MServiceCenterInfo/MServiceCenterInfo_photo.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);
		/*
		 * if (null == ui) { code = "-1"; msg = "您还未登录，请先登录系统！"; return super.returnAjaxData(response, code, msg, data);
		 * }
		 */

		ServiceCenterInfo entity = getServiceCenterInfo(id);
		if (null == entity) {
			msg = "合伙人不存在或审核未通过";
			return returnAjaxData(response, code, msg, data);
		}
		data.put("entity", entity);

		BaseProvince temp = super.getBaseProvince(Long.valueOf(entity.getP_index()));
		data.put("p_name", temp.getP_name());

		// 同级别其他区域
		BaseProvince basePindex = new BaseProvince();
		basePindex.setPar_index(temp.getPar_index());
		basePindex.setIs_del(0);
		List<BaseProvince> pIndexList = getFacade().getBaseProvinceService().getBaseProvinceList(basePindex);
		if (null != pIndexList && pIndexList.size() > 0) {
			JSONArray p_index_list = new JSONArray();
			for (BaseProvince item : pIndexList) {
				JSONObject p_indexs = new JSONObject();
				p_indexs.put("value", item.getP_index().toString());
				p_indexs.put("text", item.getP_name());
				p_index_list.add(p_indexs);
			}
			data.put("p_index_list", p_index_list);
		}

		// 下一级别的镇

		BaseProvince basePindexSon = new BaseProvince();
		basePindexSon.setPar_index(temp.getP_index());
		basePindexSon.setIs_del(0);
		List<BaseProvince> nextPIndexList = getFacade().getBaseProvinceService().getBaseProvinceList(basePindexSon);
		if (null != nextPIndexList && nextPIndexList.size() > 0) {
			JSONArray jsonArrayList = new JSONArray();
			for (BaseProvince item : nextPIndexList) {
				JSONObject p_indexs = new JSONObject();
				p_indexs.put("value", item.getP_index().toString());
				p_indexs.put("text", item.getP_name());
				jsonArrayList.add(p_indexs);
			}
			data.put("nextPIndexList", jsonArrayList);
		}

		// 县管理员
		UserInfo managerUser = super.getUserInfo(entity.getAdd_user_id());
		data.put("managerUser", managerUser);

		// 昨日增加数量
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(time);

		VillageInfo villageInfo = new VillageInfo();
		villageInfo.getMap().put("p_index_like", entity.getP_index().toString());
		villageInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		villageInfo.setIs_del(0);
		villageInfo.getMap().put("st_add_date", date);
		villageInfo.getMap().put("en_add_date", date);
		int yesterdayAddCount = getFacade().getVillageInfoService().getVillageInfoCount(villageInfo);
		data.put("yesterdayAddCount", yesterdayAddCount);

		// 县头条
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setIs_del(0);
		newsInfo.setInfo_state(Keys.INFO_STATE.INFO_STATE_1.getIndex());
		newsInfo.setLink_id(entity.getId());
		newsInfo.setMod_id(Keys.mod_id.mod_id_xian.getIndex() + "");
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
		data.put("newsInfoList", newsInfoList);

		// 分类
		MBaseLink mBaseLink = new MBaseLink();
		mBaseLink.setLink_type(100);
		mBaseLink.setIs_del(0);
		mBaseLink.setLink_id(entity.getAdd_user_id());
		List<MBaseLink> mBaseLinkList = getFacade().getMBaseLinkService().getMBaseLinkList(mBaseLink);
		data.put("mBaseLinkList", mBaseLinkList);

		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "3";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		setAjaxDataPage(data, dynaBean, StringUtils.substring(entity.getP_index().toString(), 0, 6));

		// 我加入的村
		if (ui != null) {
			myJoinVillageList(data, ui);
		}

		// 扶贫列表
		// poorInfoList(data, StringUtils.substring(entity.getP_index().toString(), 0, 6));
		poorInfoList(data, dynaBean, StringUtils.substring(entity.getP_index().toString(), 0, 6));

		// 销售排行
		saleRankList(data, StringUtils.substring(entity.getP_index().toString(), 0, 6), 10);

		// 查询村民数量
		VillageMember villageMember = new VillageMember();
		villageMember.setIs_del(0);
		villageMember.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		villageMember.getMap().put("country", StringUtils.substring(entity.getP_index().toString(), 0, 6));
		int villageMemberCount = super.getFacade().getVillageMemberService()
				.getVillageMemberCountByPIndex(villageMember);
		data.put("villageMemberCount", villageMemberCount);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getPoorAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		UserInfo ui = super.getUserInfoFromSession(request);

		ServiceCenterInfo entity = getServiceCenterInfo(id);
		if (null == entity) {
			msg = "合伙人不存在或审核未通过";
			return returnAjaxData(response, code, msg, data);
		}
		data.put("entity", entity);

		// 扶贫列表
		poorInfoList(data, dynaBean, StringUtils.substring(entity.getP_index().toString(), 0, 6));

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward getAjaxDataPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		ServiceCenterInfo entity = getServiceCenterInfo(id);
		if (null == entity) {
			msg = "合伙人不存在或审核未通过";
			return returnAjaxData(response, code, msg, data);
		}
		data.put("entity", entity);

		setAjaxDataPage(data, dynaBean, StringUtils.substring(entity.getP_index().toString(), 0, 6));

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	// 村子昨天加入的人员数量
	public int getVillageYesterdayAddCount(VillageMember member) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(time);
		member.getMap().put("st_add_date", date);
		member.getMap().put("en_add_date", date);
		return getFacade().getVillageMemberService().getVillageMemberCount(member);
	}

	public ActionForward getAjaxDataPhoto(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");

		if (StringUtils.isBlank(id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		Pager pager = (Pager) dynaBean.get("pager");

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		ServiceCenterInfo serviceCenterInfo = super.getServiceCenterInfo(id);
		if (null == serviceCenterInfo) {
			msg = "村子不存在！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		BaseFiles entity = new BaseFiles();
		entity.setLink_id(serviceCenterInfo.getId());
		entity.setType(Keys.BaseFilesType.Base_Files_TYPE_60.getIndex());
		entity.setIs_del(0);

		Integer recordCount = getFacade().getBaseFilesService().getBaseFilesCount(entity);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<BaseFiles> entityList = getFacade().getBaseFilesService().getBaseFilesPaginatedList(entity);

		data.put("entityList", entityList);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward xianqing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		return new ActionForward("/../m/MServiceCenterInfo/MServiceCenterInfo_xianqing.jsp");
	}

	/**
	 * @desc 村情
	 */
	public ActionForward getAjaxDataXianQing(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		ServiceCenterInfo entity = super.getServiceCenterInfo(id);
		if (null == entity) {
			msg = "合伙人不存在或审核未通过";
			return returnAjaxData(response, code, msg, data);
		}
		data.put("entity", entity);

		code = "1";
		return returnAjaxData(response, code, msg, data);
	}
}
