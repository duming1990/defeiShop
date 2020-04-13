/**
 * 
 */
package com.ebiz.webapp.web.struts.m;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.ApiUrlConstants;
import com.aiisen.weixin.CommonApi;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.PoorCuoShi;
import com.ebiz.webapp.domain.PoorFamily;
import com.ebiz.webapp.domain.PoorInfo;
import com.ebiz.webapp.domain.PoorZeRen;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.VillageContactList;
import com.ebiz.webapp.domain.VillageDynamic;
import com.ebiz.webapp.domain.VillageDynamicComment;
import com.ebiz.webapp.domain.VillageDynamicRecord;
import com.ebiz.webapp.domain.VillageInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author 刘佳
 * @date: 2018年2月5日 下午12:24:35
 */
public class MUserCenterAction extends MBaseWebAction {

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// UserInfo ui = super.getUserInfoFromSession(request);
		// if (null == ui) {
		// String msg = "您还未登录，请先登录系统！";
		// return showTipNotLogin(mapping, form, request, response, msg);
		// }

		request.setAttribute("header_title", "个人主页");

		return new ActionForward("/../m/MUserCenter/MUserCenter_index.jsp");
	}

	public ActionForward getAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "-1", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String user_id = (String) dynaBean.get("user_id");
		String type = (String) dynaBean.get("type");

		UserInfo ui = super.getUserInfoFromSession(request);
		data.put("ui", ui);

		if (!GenericValidator.isInt(user_id) || StringUtils.isBlank(type)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		UserInfo linkUser = super.getUserInfo(user_id);
		if (null == linkUser) {
			msg = "用户不存在！";
			super.ajaxReturnInfo(response, code, msg, data);
			return null;
		}
		data.put("user", linkUser);
		data.put("isApp", super.isApp(request));

		code = "1";
		// 主页
		if (Integer.valueOf(type) == 1) {

			if (linkUser.getIs_poor() == 1 && null != linkUser.getPoor_id()) {
				PoorInfo poorInfo = new PoorInfo();
				poorInfo.setId(linkUser.getPoor_id());
				poorInfo.setIs_del(0);
				poorInfo = getFacade().getPoorInfoService().getPoorInfo(poorInfo);
				data.put("poorInfo", poorInfo);
			}
			// 查询我的购买金额
			OrderInfo orderInfoTodaySumBuy = new OrderInfo();
			orderInfoTodaySumBuy.setAdd_user_id(linkUser.getId());
			orderInfoTodaySumBuy.setOrder_type(Keys.OrderType.ORDER_TYPE_7.getIndex());
			orderInfoTodaySumBuy.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
			orderInfoTodaySumBuy.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
			orderInfoTodaySumBuy = super.getFacade().getOrderInfoService()
					.getOrderInfoStatisticaSum(orderInfoTodaySumBuy);
			data.put("orderInfoTodaySumBuy", orderInfoTodaySumBuy.getMap().get("sum_money"));

			// 查询我卖出的金额
			OrderInfo orderInfoTodaySumOut = new OrderInfo();
			orderInfoTodaySumOut.setPublish_user_id(linkUser.getId());
			orderInfoTodaySumOut.getMap().put("order_state_ne", Keys.OrderState.ORDER_STATE_90.getIndex());
			orderInfoTodaySumOut.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
			orderInfoTodaySumOut = super.getFacade().getOrderInfoService()
					.getOrderInfoStatisticaSum(orderInfoTodaySumOut);
			data.put("orderInfoTodaySumOut", orderInfoTodaySumOut.getMap().get("sum_money"));

			// 是否关注用户
			if (null != ui) {
				int is_guanzhu_cout = isGuanzhu(linkUser.getId(), ui.getId());
				data.put("is_guanzhu_cout", is_guanzhu_cout);
				if (is_guanzhu_cout > 0) {
					// 判断是否互关
					int is_huguan = isGuanzhu(ui.getId(), linkUser.getId());
					data.put("is_huguan", is_huguan);
				}
			}

			// 获赞数量
			int zan_count = getUserDynamicCommentCount(linkUser, Keys.CommentType.COMMENT_TYPE_3.getIndex(), null);
			data.put("zan_count", zan_count);
			// 关注数量
			int guanzhu_count = getGuanZhuCount(linkUser);
			data.put("guanzhu_count", guanzhu_count);
			// 粉丝数量
			int fensi_count = getFenSiCount(linkUser);
			data.put("fensi_count", fensi_count);
			// 动态
			List<VillageDynamic> villageDynamicList = setUserVillageDynamic(null,
					Keys.DynamicType.dynamic_type_1.getIndex(), linkUser, startPage, Integer.valueOf(pageSize), pager);
			data.put("villageDynamicList", villageDynamicList);

			if (villageDynamicList.size() < Integer.valueOf(pageSize)) {
				code = "2";
			}

		} else if (Integer.valueOf(type) == 2) {
			// 商品
			List<VillageDynamic> villageDynamicList = setUserVillageDynamic(null,
					Keys.DynamicType.dynamic_type_2.getIndex(), linkUser, startPage, Integer.valueOf(pageSize), pager);
			data.put("villageDynamicCommList", villageDynamicList);

			if (villageDynamicList.size() < Integer.valueOf(pageSize)) {
				code = "2";
			}

		} else if (Integer.valueOf(type) == 4) {
			// 用户评论及回复
			VillageDynamicRecord villageDynamicRecord = new VillageDynamicRecord();
			// villageDynamicRecord.setTo_user_id(linkUser.getId());
			villageDynamicRecord.setAdd_user_id(linkUser.getId());
			villageDynamicRecord.setIs_del(0);

			Integer recordCount = getFacade().getVillageDynamicRecordService().getVillageDynamicRecordCount(
					villageDynamicRecord);
			pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
			villageDynamicRecord.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
			villageDynamicRecord.getRow().setCount(pager.getRowCount());

			List<VillageDynamicRecord> villageDynamicRecordList = getFacade().getVillageDynamicRecordService()
					.getVillageDynamicRecordPaginatedList(villageDynamicRecord);

			if (villageDynamicRecordList.size() < Integer.valueOf(pageSize)) {
				code = "2";
			}
			data.put("villageDynamicRecordList", villageDynamicRecordList);

		} else if (Integer.valueOf(type) == 3) {
			// 贫困户商品
			if (linkUser.getIs_poor() == 1 && null != linkUser.getPoor_id()) {
				CommInfoPoors commInfoPoors = new CommInfoPoors();
				commInfoPoors.setPoor_id(linkUser.getPoor_id());
				commInfoPoors.getMap().put("is_del", 0);
				commInfoPoors.getMap().put("is_aid", 1);
				commInfoPoors.getMap().put("audit_state", 1);
				commInfoPoors.getMap().put("is_sell", 1);
				commInfoPoors.getMap().put("is_has_tc", 1);
				commInfoPoors.getMap().put("not_out_sell_time", "true");
				Integer recordCount = getFacade().getCommInfoPoorsService().getCommInfoPoorsCount(commInfoPoors);

				pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
				commInfoPoors.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
				commInfoPoors.getRow().setCount(pager.getRowCount());

				List<CommInfoPoors> commInfoPoorsList = getFacade().getCommInfoPoorsService()
						.getCommInfoPoorsPaginatedList(commInfoPoors);
				data.put("commInfoPoorsList", commInfoPoorsList);

				if (commInfoPoorsList.size() < Integer.valueOf(pageSize)) {
					code = "2";
				}
			}
		}
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public List<VillageDynamic> setUserVillageDynamic(UserInfo userInfo, Integer dynamic_type, UserInfo linkUser,
			String startPage, Integer pageSize, Pager pager) {
		VillageDynamic villageDynamic = new VillageDynamic();
		villageDynamic.setAdd_user_id(linkUser.getId());
		villageDynamic.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		villageDynamic.setIs_del(0);
		villageDynamic.setType(dynamic_type);
		if (dynamic_type.intValue() == Keys.DynamicType.dynamic_type_2.getIndex()) {
			villageDynamic.getMap().put("left_join_comm_info", true);
			villageDynamic.getMap().put("not_out_sell_time", true);
		}

		Integer recordCount = getFacade().getVillageDynamicService().getVillageDynamicCount(villageDynamic);
		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		villageDynamic.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		villageDynamic.getRow().setCount(pager.getRowCount());

		List<VillageDynamic> villageDynamicList = getFacade().getVillageDynamicService()
				.getVillageDynamicPaginatedList(villageDynamic);
		super.setVillageDynamic(linkUser, villageDynamicList);
		return villageDynamicList;
	}

	public int getFenSiCount(UserInfo userInfo) {
		VillageContactList a = new VillageContactList();
		a.setContact_user_id(userInfo.getId());
		a.setIs_del(0);
		int fensi_count = getFacade().getVillageContactListService().getVillageContactListCount(a);
		return fensi_count;
	}

	/**
	 * @desc 获取用户被回复、点赞、评论数量
	 * @param userInfo
	 */
	public int getUserDynamicCommentCount(UserInfo userInfo, Integer type, String map_types) {
		VillageDynamicComment entity = new VillageDynamicComment();
		entity.setLink_user_id(userInfo.getId());
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		if (null != type) {
			entity.setComment_type(type);
		}
		if (null != map_types) {
			entity.getMap().put("comment_type_in", map_types);
		}
		entity.setIs_del(0);
		int count = getFacade().getVillageDynamicCommentService().getVillageDynamicCommentCount(entity);
		return count;
	}

	public ActionForward viewPoorDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");

		if (StringUtils.isBlank(poor_id)) {
			String msg = "参数有误！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		PoorInfo pInfo = new PoorInfo();
		pInfo.setId(Integer.valueOf(poor_id));
		pInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		pInfo = getFacade().getPoorInfoService().getPoorInfo(pInfo);

		if (null == pInfo) {
			String msg = "关联贫困户不存在！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "贫困户详情");
		return new ActionForward("/../m/MUserCenter/poorInfoDetails.jsp");
	}

	public ActionForward miniprogramDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");

		if (StringUtils.isBlank(poor_id)) {
			String msg = "参数有误！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		PoorInfo pInfo = new PoorInfo();
		pInfo.setId(Integer.valueOf(poor_id));
		pInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		pInfo = getFacade().getPoorInfoService().getPoorInfo(pInfo);

		if (null == pInfo) {
			String msg = "关联贫困户不存在！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "贫困户详情");
		return new ActionForward("/../m/MUserCenter/miniprogramDetails.jsp");
	}

	public ActionForward getPoorDetailsAjaxData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String poor_id = (String) dynaBean.get("poor_id");

		if (StringUtils.isBlank(poor_id)) {
			msg = "参数有误！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		PoorInfo entity = new PoorInfo();
		entity.setId(Integer.valueOf(poor_id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getPoorInfoService().getPoorInfo(entity);

		if (null == entity) {
			msg = "关联贫困户不存在！";
			super.returnInfo(response, code, msg, data);
			return null;
		}
		data.put("entity", entity);

		PoorFamily family = new PoorFamily();
		family.setLink_id(entity.getId());
		family.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<PoorFamily> familyList = getFacade().getPoorFamilyService().getPoorFamilyList(family);
		data.put("familyList", familyList);

		PoorCuoShi pCuoShi = new PoorCuoShi();
		pCuoShi.setLink_id(entity.getId());
		pCuoShi.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<PoorCuoShi> cuoshiList = getFacade().getPoorCuoShiService().getPoorCuoShiList(pCuoShi);
		data.put("cuoshiList", cuoshiList);

		PoorZeRen pZeRen = new PoorZeRen();
		pZeRen.setLink_id(entity.getId());
		pZeRen.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<PoorZeRen> pZeRenList = getFacade().getPoorZeRenService().getPoorZeRenList(pZeRen);
		data.put("pZeRenList", pZeRenList);
		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}

	public ActionForward MUserCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		UserInfo ui = super.getUserInfoFromSession(request);

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			String msg = "您还未登录，请先登录系统！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		VillageDynamic entity = new VillageDynamic();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		entity.getMap().put("left_join_comm_info", true);
		entity.getMap().put("not_out_sell_time", true);
		entity = getFacade().getVillageDynamicService().getVillageDynamic(entity);
		if (null == entity) {
			String msg = "未找到该条数据！";
			return showTipMsg(mapping, form, request, response, msg);
		}

		super.setMapDynamicImgs(entity);
		@SuppressWarnings("unchecked")
		List<BaseImgs> imgList = (List<BaseImgs>) entity.getMap().get("imgList");
		String share_img = imgList.get(0).getFile_path();
		request.setAttribute("share_img", "http://" + Keys.app_domain.concat("/").concat(share_img));
		request.setAttribute("share_url", "http://" + Keys.app_domain + "/m/MUserCenter.do?method=MUserCommInfo&id="
				+ id);
		request.setAttribute("entity", entity);

		super.setJsApiTicketRetrunParamToSession(request);

		// 这个地方判断如果是微信扫描过来的，如果该没有登录的账号直接生成一个账号
		if (super.isWeixin(request) && null == ui) {

			String ctx = super.getCtxPath(request, false);
			String return_url = ctx + "/m/MUserCenter.do?method=MUserCommInfo&id=" + id;

			StringBuilder link = new StringBuilder();
			String scope = "snsapi_userinfo";
			String state = "";

			String ymid = null;

			VillageInfo village = new VillageInfo();
			village.setId(entity.getVillage_id());
			UserInfo villageManageUser = super.getVillageManageUser(village);
			if (null != villageManageUser) {
				ymid = villageManageUser.getUser_name();
			}

			if (StringUtils.isBlank(ymid)) {
				String msg = "ymid为空";
				return super.showTipMsg(mapping, form, request, response, msg);
			}

			StringBuffer server = new StringBuffer();
			server.append(request.getHeader("host")).append(request.getContextPath());
			request.setAttribute("server_domain", server.toString());
			String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=onlyCreateUserAndRedirect");
			redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8") + "&ymid=" + ymid;
			link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
					.append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
					.append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");
			response.sendRedirect(link.toString());
		}

		return new ActionForward("/../m/MUserCenter/MUserCommInfo.jsp");
	}

	public ActionForward getCommInfoData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String code = "0", msg = "";
		JSONObject data = new JSONObject();

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id)) {
			msg = "参数有误！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		VillageDynamic entity = new VillageDynamic();
		entity.setId(Integer.valueOf(id));
		entity.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		entity = getFacade().getVillageDynamicService().getVillageDynamic(entity);

		if (null == entity) {
			msg = "该条数据不存在！";
			super.returnInfo(response, code, msg, data);
			return null;
		}

		UserInfo linkUser = super.getUserInfo(entity.getAdd_user_id());
		data.put("user", linkUser);

		VillageDynamic villageDynamic = new VillageDynamic();
		villageDynamic.setId(Integer.valueOf(id));
		villageDynamic.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		villageDynamic.setIs_del(0);
		villageDynamic.getMap().put("left_join_comm_info", true);
		villageDynamic.getMap().put("not_out_sell_time", true);
		villageDynamic = getFacade().getVillageDynamicService().getVillageDynamic(villageDynamic);

		super.setMapDynamicImgs(villageDynamic);

		CommInfo commInfo = new CommInfo();
		commInfo.setId(villageDynamic.getComm_id());
		commInfo.setIs_del(0);
		commInfo.setIs_sell(1);
		commInfo.setIs_has_tc(1);
		commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		commInfo = getFacade().getCommInfoService().getCommInfo(commInfo);
		if (null != commInfo) {
			CommTczhPrice tc = new CommTczhPrice();
			tc.setComm_id(commInfo.getId().toString());
			tc = getFacade().getCommTczhPriceService().getCommTczhPrice(tc);
			villageDynamic.getMap().put("commInfoTczh", tc);
		}

		villageDynamic.getMap().put("commInfo", commInfo);

		// 是否关注用户
		UserInfo ui = super.getUserInfoFromSession(request);
		data.put("ui", ui);
		if (null != ui) {
			int is_guanzhu_cout = isGuanzhu(linkUser.getId(), ui.getId());
			data.put("is_guanzhu_cout", is_guanzhu_cout);
			if (is_guanzhu_cout > 0) {
				// 判断是否互关
				int is_huguan = isGuanzhu(ui.getId(), linkUser.getId());
				data.put("is_huguan", is_huguan);
			}

			// 用户是否点赞该条动态
			int zan_count = isDynamicUserZan(ui.getId(), villageDynamic, Keys.CommentType.COMMENT_TYPE_3.getIndex());
			villageDynamic.getMap().put("zan_count", zan_count);

		}

		// 评论列表
		List<VillageDynamicComment> commentList = this.getVillageDynamicCommentList(villageDynamic.getId(), null, null,
				"true");
		villageDynamic.getMap().put("commentList", commentList);

		// 点赞用户列表
		String zanNameList = "";
		List<VillageDynamicComment> zanList = this.getVillageDynamicCommentList(villageDynamic.getId(),
				Keys.CommentType.COMMENT_TYPE_3.getIndex(), 5, null);
		if (null != zanList && zanList.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (VillageDynamicComment zan : zanList) {
				sb.append(zan.getAdd_user_name()).append("、");
			}
			zanNameList = sb.toString();
			if (zanNameList.length() > 0) {
				zanNameList = zanNameList.substring(0, zanNameList.lastIndexOf("、"));
			}
		}
		villageDynamic.getMap().put("zanList", zanList);
		villageDynamic.getMap().put("zanNameList", zanNameList);

		// 查询为你推荐
		VillageDynamic villageDynamicTj = new VillageDynamic();
		villageDynamicTj.setVillage_id(villageDynamic.getVillage_id());
		villageDynamicTj.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		villageDynamicTj.setIs_del(0);
		villageDynamicTj.getRow().setCount(10);
		villageDynamicTj.getMap().put("left_join_comm_info", true);
		villageDynamicTj.getMap().put("id_not_in", villageDynamic.getId());
		villageDynamicTj.getMap().put("not_out_sell_time", true);
		List<VillageDynamic> villageDynamicTjList = getFacade().getVillageDynamicService().getVillageDynamicList(
				villageDynamicTj);
		super.setVillageDynamic(null, villageDynamicTjList);
		villageDynamic.getMap().put("villageDynamicTjList", villageDynamicTjList);

		data.put("entity", villageDynamic);

		code = "1";
		super.returnInfo(response, code, msg, data);
		return null;
	}
}
