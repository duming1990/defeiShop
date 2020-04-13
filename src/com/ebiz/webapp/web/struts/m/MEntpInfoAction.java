package com.ebiz.webapp.web.struts.m;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.EntpBaseLink;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.PdContent;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.YhqInfo;
import com.ebiz.webapp.domain.YhqLink;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.CommZyType;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class MEntpInfoAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.getCommInfo(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "店铺首页");
		request.setAttribute("hasGoHome", true);
		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String entp_id = (String) dynaBean.get("entp_id");
		String p_index = (String) dynaBean.get("p_index");
		if (StringUtils.isBlank(entp_id)) {
			String msg = "传入的商品参数为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		EntpInfo entpInfo = initPublic(mapping, form, request, entp_id, response);

		if (null == entpInfo) {
			String msg = "企业信息有误！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		EntpContent entpContent = new EntpContent();
		entpContent.setType(0);
		entpContent.setEntp_id(entpInfo.getId());
		entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
		if (null != entpContent) {
			dynaBean.set("entp_content", entpContent.getContent());
		}

		request.setAttribute("header_title", entpInfo.getEntp_name());

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			// 取商品收藏数、
			ScEntpComm scEntpComm = new ScEntpComm();
			scEntpComm.setLink_id(entpInfo.getId());
			scEntpComm.setIs_del(0);
			scEntpComm.setAdd_user_id(ui.getId());
			Integer hasFavCount = super.getFacade().getScEntpCommService().getScEntpCommCount(scEntpComm);
			request.setAttribute("hasFavCount", hasFavCount);
		}
		int pageSize = 6;
		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		String city = Keys.QUANGUO_P_INDEX;

		if (null != current_p_index) {
			city = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}

		if (StringUtils.isBlank(p_index)) {
			p_index = city;
		}

		if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
			p_index = null;
		}

		List<CommInfo> commmInfoList = super.getCommInfoList(pageSize, false, entpInfo.getId(), true, false, null,
				true, pager, null, null, null, null, null, null, null, null, null);

		request.setAttribute("commmInfoList", commmInfoList);

		JSONObject share = new JSONObject();
		share.put("share_title", entpInfo.getEntp_name());
		share.put("share_content", entpInfo.getEntp_addr());

		String share_img = entpInfo.getEntp_logo();
		if (StringUtils.isBlank(share_img)) {
			share.put("main_pic", "styles/imagesPublic/no_image.jpg");
		} else {
			String min_img = share_img;
			share.put("share_img", Keys.app_domain.concat("/").concat(min_img));
		}
		share.put("share_url", "http://" + Keys.app_domain + "/m/MEntpInfo.do?method=index&entp_id=" + entpInfo.getId());

		String share_string = share.toJSONString();

		logger.info("share_string:" + share_string);
		String share_string_en = URLEncoder.encode(share_string, "UTF-8");

		logger.info("==share_string_en:" + share_string_en);
		request.setAttribute("share_string", share_string_en);

		request.setAttribute("isApp", super.isApp(request));
		request.setAttribute("isWeixin", super.isWeixin(request));
		super.setJsApiTicketRetrunParamToSession(request);
		return new ActionForward("/MEntpInfo/m_index.jsp");
	}

	public ActionForward getCommInfoList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		Pager pager = (Pager) dynaBean.get("pager");
		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();
		String msg = "", code = "0";
		if (StringUtils.isBlank(startPage)) {
			msg = "startPage参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		logger.info("=startPage={}", startPage);
		if (StringUtils.isBlank(pageSize)) {
			pageSize = "6";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		// String p_index = "";

		code = "1";

		String entp_id = (String) dynaBean.get("entp_id");
		if (StringUtils.isBlank(entp_id)) {
			msg = "传入的商品参数为空";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		EntpInfo entpInfo = initPublic(mapping, form, request, entp_id, response);

		if (null == entpInfo) {
			msg = "企业信息有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		EntpContent entpContent = new EntpContent();
		entpContent.setType(0);
		entpContent.setEntp_id(entpInfo.getId());
		entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
		if (null != entpContent) {
			dynaBean.set("entp_content", entpContent.getContent());
		}

		request.setAttribute("header_title", entpInfo.getEntp_name());

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			// 取商品收藏数、
			ScEntpComm scEntpComm = new ScEntpComm();
			scEntpComm.setLink_id(entpInfo.getId());
			scEntpComm.setIs_del(0);
			scEntpComm.setAdd_user_id(ui.getId());
			Integer hasFavCount = super.getFacade().getScEntpCommService().getScEntpCommCount(scEntpComm);
			request.setAttribute("hasFavCount", hasFavCount);
		}

		List<CommInfo> commmInfoList = super.getCommInfoListForJson(null, false, entpInfo.getId(), false, false, null,
				true, pager, Integer.valueOf(pageSize), null, null, null, null, null, Integer.valueOf(startPage),
				false, null, null, null, null);
		for (CommInfo dm : commmInfoList) {
			JSONObject map = new JSONObject();
			map.put("main_pic", dm.getMain_pic());
			map.put("comm_name", dm.getComm_name());
			map.put("sale_count", dm.getSale_count());
			map.put("sub_title", dm.getSub_title());
			map.put("sale_price", dfFormat.format(dm.getSale_price()));
			map.put("id", dm.getId());
			map.put("sale_count_update", dfFormat0.format(dm.getSale_count_update()));
			dataLoadList.add(map);
		}
		datas.put("dataList", dataLoadList.toString());
		msg = "加载完成";
		if (dataLoadList.size() < Integer.valueOf(pageSize)) {
			code = "2";
			msg = "没有更多数据";
		}
		request.setAttribute("isApp", super.isApp(request));
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	public ActionForward getCommList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "企业商品列表");

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String pd_type = (String) dynaBean.get("pd_type");
		String min_price = (String) dynaBean.get("min_price");
		String max_price = (String) dynaBean.get("max_price");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String comm_name_like = (String) dynaBean.get("comm_name_like");

		EntpInfo entpInfo = initPublic(mapping, form, request, entp_id, response);
		if (null == entpInfo) {
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setOwn_entp_id(entpInfo.getId());

		commInfo.getMap().put("min_price", min_price);
		commInfo.getMap().put("max_price", max_price);
		commInfo.getMap().put("comm_name_like", comm_name_like);

		int data_flag = 0;

		if (StringUtils.isNotBlank(orderByParam)) {
			if ("orderByPriceDesc".equalsIgnoreCase(orderByParam)) {
				commInfo.getMap().put("orderByPriceDesc", "true");
				dynaBean.set("orderByParamForPrice", "orderByPriceAsc");
				data_flag = 2;
			}
			if ("orderByPriceAsc".equalsIgnoreCase(orderByParam)) {
				commInfo.getMap().put("orderByPriceAsc", "true");
				dynaBean.set("orderByParamForPrice", "orderByPriceDesc");
				data_flag = 2;
			}
			if ("orderByUpDateDesc".equalsIgnoreCase(orderByParam)) {
				commInfo.getMap().put("orderByUpDateDesc", "true");
				data_flag = 3;
			}
			if ("orderBySaleDesc".equalsIgnoreCase(orderByParam)) {
				commInfo.getMap().put("orderBySaleDesc", "true");
				data_flag = 1;
			}
		} else {
			dynaBean.set("orderByParamForPrice", "orderByPriceAsc");
			data_flag = 2;
			commInfo.getMap().put("order_value", true);
		}
		request.setAttribute("data_flag", data_flag);

		dynaBean.set("pd_type", pd_type);

		// 更新浏览量
		EntpInfo ei = new EntpInfo();
		ei.setId(entpInfo.getId());
		ei.getMap().put("add_view_count", 1);// 浏览量+1
		super.getFacade().getEntpInfoService().modifyEntpInfo(ei);
		request.setAttribute("isApp", super.isApp(request));
		return new ActionForward("/MEntpInfo/list.jsp");

	}

	// 实例化公共信息 ，取得企业信息
	public EntpInfo initPublic(ActionMapping mapping, ActionForm form, HttpServletRequest request, String entp_id,
			HttpServletResponse response) throws Exception {
		if (StringUtils.isBlank(entp_id)) {
			String msg = "对不起，您输入的链接地址不存在";
			super.showTipMsg(mapping, form, request, response, msg);
			return null;
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setAudit_state(2);
		entpInfo.setId(Integer.valueOf(entp_id));
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (entpInfo == null) {
			String msg = "企业不存在";
			super.showTipMsg(mapping, form, request, response, msg);
			return null;
		}
		request.setAttribute("entpInfo", entpInfo);

		// 店铺关注count
		ScEntpComm sec_entity = new ScEntpComm();
		sec_entity.setIs_del(0);
		sec_entity.setLink_id(entpInfo.getId());
		Integer sec_count = super.getFacade().getScEntpCommService().getScEntpCommCount(sec_entity);
		request.setAttribute("sec_count", sec_count);

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			sec_entity.setAdd_user_id(ui.getId());
			Integer is_sc = super.getFacade().getScEntpCommService().getScEntpCommCount(sec_entity);
			request.setAttribute("is_sc", is_sc);
		}
		return entpInfo;
	}

	public ActionForward getCommInfoData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");

		JSONObject datas = new JSONObject();
		String msg = "", code = "0";

		if (StringUtils.isBlank(comm_id)) {
			msg = "参数有误！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setAudit_state(1);
		commInfo.setIs_del(0);
		commInfo.setId(Integer.valueOf(comm_id));
		commInfo.setIs_sell(1);
		commInfo.setIs_has_tc(1);
		commInfo.getMap().put("sell_date_limit", true);
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);

		if (!super.isCommInfoTrue(commInfo)) {
			msg = "商品不存在或审核未通过，或商品已经下架，或商品未维护套餐。";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}
		
		UserInfo ui = super.getUserInfoFromSession(request);

		logger.info("commInfo.getOwn_entp_id().intValue()"+commInfo.getOwn_entp_id().intValue());
		if(commInfo.getOwn_entp_id().intValue() == Integer.valueOf(Keys.jd_entp_id)){
			String judgeJdProductPriceFlag = super.judgeJdProductPrice(commInfo.getJd_skuid());//更新jd商品价格
		}
		

		EntpInfo entpInfo = initPublic(mapping, form, request, commInfo.getOwn_entp_id().toString(), response);
		if (null == entpInfo) {
			msg = "关联企业不存在！";
			super.ajaxReturnInfo(response, code, msg, datas);
			return null;
		}

		// 是扶贫商品的时候需要去查询扶贫对象
		if (commInfo.getIs_aid() == 1) {
			CommInfoPoors commInfoPoors = new CommInfoPoors();
			commInfoPoors.setComm_id(commInfo.getId());
			List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
					.getCommInfoPoorsList(commInfoPoors);
			commInfo.setPoorsList(commInfoPoorsList);
		}

		CommInfo commInfoForViewCount = new CommInfo();
		commInfoForViewCount.setId(commInfo.getId());
		commInfoForViewCount.setView_count(commInfo.getView_count() + 1);
		getFacade().getCommInfoService().modifyCommInfo(commInfoForViewCount);

		// 显示套餐
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setComm_id(commInfo.getId().toString());
		List<CommTczhPrice> commTczhPriceList = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(commTczhPrice);
		commInfo.getMap().put("commTczhPriceList", commTczhPriceList);

		if (commInfo.getIs_zingying().intValue() != 0) {
			commInfo.getMap().put("commZyName", CommZyType.getName(commInfo.getIs_zingying()));
		}

		Pager pager = (Pager) dynaBean.get("pager");

		// 这个地方拿推荐商品
		List<CommInfo> entityList = super.getCommInfoList(null, false, null, false, false, "orderBySaleDesc", true,
				pager, 8, null, null, null, null, String.valueOf(commInfo.getCls_id()), null, null, null);

		UserInfo userInfo = super.getUserInfoFromSession(request);
		Integer hasFavCount = 0;
		if (null != userInfo) {
			datas.put("isLogin", true);
			// 取商品收藏数、
			ScEntpComm scEntpComm = new ScEntpComm();
			scEntpComm.setLink_id(commInfo.getId());
			scEntpComm.setIs_del(0);
			scEntpComm.setAdd_user_id(userInfo.getId());
			hasFavCount = super.getFacade().getScEntpCommService().getScEntpCommCount(scEntpComm);
		}
		YhqLink coupons = new YhqLink();
		coupons.setComm_id(Integer.valueOf(commInfo.getId()));
		List<YhqLink> couponslist = super.getFacade().getYhqLinkService().getYhqLinkList(coupons);
		if (null != couponslist) {
			for (YhqLink temp : couponslist) {
				YhqInfo yhqInfo = new YhqInfo();
				yhqInfo.setId(temp.getYhq_id());
				yhqInfo.setIs_del(0);
				yhqInfo = super.getFacade().getYhqInfoService().getYhqInfo(yhqInfo);
				if (null != yhqInfo) {
					temp.getMap().put("yhqInfo", yhqInfo);
				}

			}
		}

		if (commInfo.getOwn_entp_id().intValue() == Integer.valueOf(Keys.jd_entp_id)) {// 京东商品,

			 ui = super.getUserInfoFromSession(request);
			ShippingAddress shippingAddress = new ShippingAddress();
			if (ui != null) {

				getShippingAddressInfo(request, ui.getId(), null);
				shippingAddress = (ShippingAddress) request.getAttribute("dftAddress");
				if (shippingAddress != null) {

					JSONObject jsonKc = new JSONObject();

					JSONObject jsonComm = new JSONObject();
					JSONArray jsonArraySkus = new JSONArray();
					jsonComm.put("sku", commInfo.getJd_skuid());
					jsonComm.put("count", 1);
					jsonArraySkus.add(jsonComm);
					jsonKc.put("skus", JSON.toJSON(jsonArraySkus));
					String[] areas = super.getJdArea(shippingAddress).split(",");
					for (int i = 0; i < areas.length; i++) {
						if (i == 0) {
							jsonKc.put("p", areas[0]);
						}
						if (i == 1) {
							jsonKc.put("c", areas[1]);
						}
						if (i == 2) {
							jsonKc.put("d", areas[2]);
						}
						if (i == 3) {
							jsonKc.put("t", areas[3]);
						}
					}

					JSONObject stocksJson = super.getJdProductStocks(response, jsonKc.toJSONString());

					if (stocksJson.get("StatusCode").toString().equals(Keys.JD_API_RESULT_STATUS_CODE)) {

						JSONArray jsonArrayResult = stocksJson.getJSONArray("Data");
						if (null != jsonArrayResult && jsonArrayResult.size() > 0) {
							for (Object temp : jsonArrayResult) {
								JSONObject jsonObjectResulut = (JSONObject) temp;
								if (Integer.valueOf(jsonObjectResulut.get("stockStateId").toString()) == Keys.JD_NO_STOCK_CODE) {
									datas.put("jd_no_stock", true);
								}
							}
						}
					} else {
						datas.put("jd_no_stock", true);
					}
				}

			}
		}
		datas.put("couponslist", couponslist);
		datas.put("isWeixin", super.isWeixin(request));
		datas.put("isApp", super.isApp(request));
		datas.put("hasFavCount", hasFavCount);
		datas.put("entityList", entityList);
		datas.put("commInfo", commInfo);
		datas.put("entpInfo", entpInfo);
		datas.put("userInfo", userInfo);
		datas.put("reBate1001", super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex())
				.getPre_number2());
		datas.put("reBate1002", super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex())
				.getPre_number2());

		datas.put("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));

		code = "1";
		super.ajaxReturnInfo(response, code, msg, datas);
		return null;
	}

	/** 商品信息 */
	public ActionForward getCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String ymid = (String) dynaBean.get("ymid");

		if (StringUtils.isBlank(id)) {
			String msg = "传入的商品参数为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		CommInfo commInfo = super.getCommInfo(Integer.valueOf(id));

		if (null == commInfo) {
			String msg = "未查询该商品，或者该商品配置有问题！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		UserInfo ui = super.getUserInfoFromSession(request);

		
		logger.info("commInfo.getOwn_entp_id().intValue()"+commInfo.getOwn_entp_id().intValue());
		if(commInfo.getOwn_entp_id().intValue() == Integer.valueOf(Keys.jd_entp_id)){
			String judgeJdProductPriceFlag = super.judgeJdProductPrice(commInfo.getJd_skuid());//更新jd商品价格
		}
		
		commInfo = super.getCommInfo(Integer.valueOf(id));//重新商品信息

		request.setAttribute("commInfo", commInfo);

		if (null == commInfo) {
			String msg = "未查询该商品，或者该商品配置有问题！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}		
		if (null != commInfo.getOwn_entp_id()) {
			request.setAttribute("entpInfo", super.getEntpInfo(commInfo.getOwn_entp_id()));
		}
		String share_img = commInfo.getMain_pic();
		String ctx = super.getCtxPath(request, false);

		String share_url = ctx + "/m/MEntpInfo.do?id=" + id;

		 ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			share_url = share_url + "&ymid=" + ui.getUser_name();
		}
		if (StringUtils.isNotBlank(ymid)) {// 如果ymid不为空，则说明是分享的商品链接，将ymid塞入session
			HttpSession session = request.getSession();
			session.setAttribute("ymid", ymid);
		}
		request.setAttribute("share_url", share_url);

		String share_imgs[] = share_img.split("\\.");

		String imgUrl = share_imgs[0] + "_400x400." + share_imgs[1];

		request.setAttribute("share_img", "http://" + Keys.app_domain.concat("/").concat(imgUrl));
		super.setJsApiTicketRetrunParamToSession(request);

		// 这个地方判断如果是微信扫描过来的，如果该没有登录的账号直接生成一个账号
		// if (super.isWeixin(request) && null == ui) {
		//
		// String ctx = super.getCtxPath(request, false);
		// String return_url = ctx + "/m/MEntpInfo.do?method=getCommInfo&id=" + id;
		//
		// StringBuilder link = new StringBuilder();
		// String scope = "snsapi_userinfo";
		// String state = "";
		//
		// // if (StringUtils.isBlank(ymid)) {
		// // String msg = "ymid为空";
		// // return super.showTipMsg(mapping, form, request, response, msg);
		// // }
		//
		// StringBuffer server = new StringBuffer();
		// server.append(request.getHeader("host")).append(request.getContextPath());
		// request.setAttribute("server_domain", server.toString());
		// String redirectUri = ctx.concat("/weixin/WeixinLogin.do?method=onlyCreateUserAndRedirect");
		// redirectUri += "&return_url=" + URLEncoder.encode(return_url, "UTF-8") + "&ymid=" + ymid;
		// link.append(ApiUrlConstants.OAUTH2_LINK + "?appid=" + CommonApi.APP_ID)
		// .append("&redirect_uri=" + URLEncoder.encode(redirectUri, "UTF-8")).append("&response_type=code")
		// .append("&scope=" + scope).append("&state=" + state).append("#wechat_redirect");
		// response.sendRedirect(link.toString());
		// }
		if (commInfo.getComm_type() == Keys.CommType.COMM_TYPE_20.getIndex()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("comm_id", commInfo.getId());
			super.getFacade().getAutoRunService().autoSyncUpdatePtOrderThread(map);
		}
		return new ActionForward("/MEntpInfo/view.jsp");
	}

	/** 商品详细信息 */
	public ActionForward viewDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setAttribute("header_title", "商品详情");
		request.setAttribute("isCommInfo", true);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		String result = (String) dynaBean.get("result");

		if (StringUtils.isBlank(id)) {
			String msg = "传入的商品参数为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		CommInfo commInfo = new CommInfo();
		commInfo.setAudit_state(1);
		commInfo.setIs_del(0);
		commInfo.setId(Integer.valueOf(id));
		commInfo.setIs_sell(1);
		commInfo.setIs_has_tc(1);
		commInfo.getMap().put("sell_date_limit", true);
		commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);

		if ((commInfo == null) || ((commInfo.getIs_del().intValue() != 0))
				|| ((commInfo.getAudit_state().intValue() != 1)) || ((commInfo.getIs_sell().intValue() == 0))
				|| ((commInfo.getIs_sell().intValue() == 1) && (commInfo.getDown_date().compareTo(new Date()) < 0))) {
			String msg = "商品不存在或审核未通过，或商品已经下架，或商品未维护套餐。";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		PdContent pdContentXxxx = super.getPdContent(commInfo.getId(), 1);
		if (null != pdContentXxxx) {
			request.setAttribute("pdContentXxxx", pdContentXxxx.getContent());
		}

		request.setAttribute("commInfo", commInfo);

		super.setJsApiTicketRetrunParamToSession(request);

		request.setAttribute("isWeixin", super.isWeixin(request));

		request.setAttribute("ctxReal", super.getCtxPath(request, false));
		String json = JSON.toJSONString(pdContentXxxx);
		request.setAttribute("isApp", super.isApp(request));
		if (result != null && result != "") {
			super.renderJson(response, json);
			return null;
		} else {
			return new ActionForward("/MEntpInfo/viewDetails.jsp");
		}

	}

	/** 评价信息 */
	public ActionForward getCommentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "商品评价");

		DynaBean dynaBean = (DynaBean) form;
		String comm_id = (String) dynaBean.get("comm_id");

		if (StringUtils.isBlank(comm_id)) {
			String msg = "传入的商品参数为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		int queryCount = 10;
		// 评论信息
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setLink_id(Integer.valueOf(comm_id));
		commentInfo.getRow().setCount(queryCount);
		List<CommentInfo> commentInfoList = getFacade().getCommentInfoService().getCommentInfoList(commentInfo);
		request.setAttribute("queryCount", queryCount);
		if ((null != commentInfoList) && (commentInfoList.size() > 0)) {
			for (CommentInfo ci : commentInfoList) {
				String secretString = super.setSecretUserName(ci.getComm_uname());
				ci.getMap().put("secretString", secretString);

				if (null != ci.getComm_uid()) {
					ci.getMap().put("userInfo", super.getUserInfo(ci.getComm_uid()));
				}

				if (1 == ci.getHas_pic()) {
					// 获取评论图片
					BaseFiles baseFiles = new BaseFiles();
					baseFiles.setLink_id(ci.getId());
					baseFiles.setLink_tab("CommentInfo");
					baseFiles.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
					List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
					if (null != baseFilesList && baseFilesList.size() > 0) {
						ci.getMap().put("baseFilesList", baseFilesList);
					}
				}

				super.setMapCommentSonList(ci);
			}
		}
		request.setAttribute("commentInfoList", commentInfoList);

		Integer commentScore = getFacade().getCommentInfoService().getCommentInfoAvgCommSore(commentInfo);
		request.setAttribute("commentScore", commentScore);

		request.setAttribute("isWeixin", super.isWeixin(request));
		request.setAttribute("isApp", super.isApp(request));
		return new ActionForward("/MEntpInfo/commentList.jsp");
	}

	/** 评价信息 */
	public ActionForward viewEntpAddr(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setAttribute("header_title", "地图详情	");
		request.setAttribute("hasGoHome", true);

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");

		if (StringUtils.isBlank(entp_id)) {
			String msg = "传入的商品参数为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		EntpInfo entpInfo = initPublic(mapping, form, request, entp_id, response);
		if (null == entpInfo) {
			String msg = "关联企业不存在！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		if (StringUtils.isNotBlank(entpInfo.getEntp_latlng())) {
			String[] entpLatLngs = entpInfo.getEntp_latlng().split(",");
			if (entpLatLngs.length == 2) {
				String entpLatLng = entpLatLngs[1] + "," + entpLatLngs[0];
				request.setAttribute("entpLatLng", entpLatLng);
			}
		}

		request.setAttribute("isApp", super.isApp(request));

		return new ActionForward("/MEntpInfo/viewEntpAddr.jsp");
	}

	/** 店铺信息 */
	public ActionForward entpIntroBaseLink(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");

		if (StringUtils.isBlank(entp_id)) {
			String msg = "传入的参数为空";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		EntpInfo entpInfo = initPublic(mapping, form, request, entp_id, response);
		if (null == entpInfo) {
			String msg = "关联企业不存在！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		EntpBaseLink entpBaseLinkQuery = new EntpBaseLink();
		entpBaseLinkQuery.setLink_type(50);
		entpBaseLinkQuery.setIs_del(0);
		entpBaseLinkQuery.setLink_id(Integer.valueOf(entp_id));
		EntpBaseLink entpBaseLink = super.getFacade().getEntpBaseLinkService().getEntpBaseLink(entpBaseLinkQuery);
		request.setAttribute("entpBaseLink", entpBaseLink);// 图片

		EntpBaseLink entpBaseLinkQuery1 = new EntpBaseLink();
		entpBaseLinkQuery1.setLink_type(60);
		entpBaseLinkQuery1.setIs_del(0);
		entpBaseLinkQuery1.setLink_id(Integer.valueOf(entp_id));
		List<EntpBaseLink> entpBaseLink1 = super.getFacade().getEntpBaseLinkService()
				.getEntpBaseLinkList(entpBaseLinkQuery1); // 楼层

		for (EntpBaseLink entpBaseLink2 : entpBaseLink1) {
			EntpBaseLink entpBaseLinkQuery2 = new EntpBaseLink();
			entpBaseLinkQuery2.setIs_del(0);
			entpBaseLinkQuery2.setLink_id(Integer.valueOf(entp_id));
			entpBaseLinkQuery2.setPar_id(Integer.valueOf(entpBaseLink2.getId()));
			List<EntpBaseLink> entpBaseLink3 = super.getFacade().getEntpBaseLinkService()
					.getEntpBaseLinkList(entpBaseLinkQuery2);
			entpBaseLink2.getMap().put("sonBaseLink", entpBaseLink3);// 楼层内容
		}

		request.setAttribute("entpBaseLink1", entpBaseLink1);
		request.setAttribute("header_title", "店铺介绍");
		return new ActionForward("/MEntpInfo/entpIntro.jsp");
	}

	/**
	 * 拼团详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPtDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("comm_id");
		String leaderOrderId = (String) dynaBean.get("leaderOrderId");
		// String id = (String) dynaBean.get("id");
		String ymid = (String) dynaBean.get("ymid");
		String isLeader = (String) dynaBean.get("isLeader");

		if (isLeader != null && StringUtils.isNotBlank(isLeader)) {
			request.setAttribute("isLeader", true);
		} else {
			request.setAttribute("isLeader", false);
		}
		CommInfo commInfo = new CommInfo();
		if (StringUtils.isBlank(id)) {
			if (StringUtils.isNotBlank(leaderOrderId)) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("leaderOrderId", leaderOrderId);
				super.getFacade().getAutoRunService().autoSyncUpdatePtOrder(map);
				commInfo = super.getFacade().getCommInfoService().getCommInfoByOrderId(Integer.valueOf(leaderOrderId));
			}
		} else {
			commInfo = super.getCommInfo(Integer.valueOf(id));
		}

		if (null == commInfo) {
			String msg = "未查询该商品，或者该商品配置有问题！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}

		request.setAttribute("commInfo", commInfo);

		if (null != commInfo.getOwn_entp_id()) {
			request.setAttribute("entpInfo", super.getEntpInfo(commInfo.getOwn_entp_id()));
		}
		String share_img = commInfo.getMain_pic();
		String ctx = super.getCtxPath(request, false);

		String share_url = ctx + "/m/MEntpInfo.do?method=getPtDetails&comm_id=" + commInfo.getId() + "&leaderOrderId="
				+ leaderOrderId;

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui) {
			share_url = share_url + "&ymid=" + ui.getUser_name();
		}
		if (StringUtils.isNotBlank(ymid)) {// 如果ymid不为空，则说明是分享的商品链接，将ymid塞入session
			HttpSession session = request.getSession();
			session.setAttribute("ymid", ymid);
		}
		request.setAttribute("share_url", share_url);

		String share_imgs[] = share_img.split("\\.");

		String imgUrl = share_imgs[0] + "_400x400." + share_imgs[1];

		request.setAttribute("share_img", "http://" + Keys.app_domain.concat("/").concat(imgUrl));
		super.setJsApiTicketRetrunParamToSession(request);

		return new ActionForward("/MEntpInfo/ptDetails.jsp");
	}
}
