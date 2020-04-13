package com.ebiz.webapp.web.struts;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.aiisen.weixin.CommonApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseAttributeSon;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.domain.BaseImgs;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CartInfo;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPromotion;
import com.ebiz.webapp.domain.CommTczhAttribute;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.DiscountStores;
import com.ebiz.webapp.domain.EntpBaseLink;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.Freight;
import com.ebiz.webapp.domain.HelpModule;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.MServiceBaseLink;
import com.ebiz.webapp.domain.MsgReceiver;
import com.ebiz.webapp.domain.NewsInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.PdContent;
import com.ebiz.webapp.domain.QaInfo;
import com.ebiz.webapp.domain.ServiceBaseLink;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.SysOperLog;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.util.GetApiUtils;
import com.ebiz.webapp.util.JdApiUtil;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.CommZyType;
import com.ebiz.webapp.web.util.DateTools;

public class BaseWebAction extends BaseAction {
	/**
	 * @author Wu,Yang
	 * @version 2011-12-19
	 * @desc 首页 公用数据
	 */
	public void setPublicInfoWithSearchList(HttpServletRequest request) {
		super.setBaseDataListToSession(30, request);// 热门搜索
	}

	public void setPublicInfoWithHelpList(HttpServletRequest request) {
		this.setHelpModuleListByTail(request, 10000000);// 帮助中心
	}

	/**
	 * @author Wu,Yang
	 * @version 2011-12-19
	 * @throws UnsupportedEncodingException
	 * @desc 首页 公用数据
	 */
	public void setPublicInfoList(HttpServletRequest request) throws UnsupportedEncodingException {
		StringBuffer dateWithWeek = new StringBuffer(DateFormatUtils.format(new Date(), "yyyy年MM月dd日"));
		dateWithWeek.append(" ").append(DateTools.getWeekOfDate(new Date()));
		request.setAttribute("dateWithWeek", dateWithWeek.toString());

		// 帮助中心
		// 1、项目信息
		// this.setHelpModuleListByParId(request, 10010000);
		// 2、商务合作
		// this.setHelpModuleListByParId(request, 10020000);

		this.setHelpModuleListByNew(request);

		// 二级域名
		StringBuffer server = new StringBuffer();
		server.append(request.getHeader("host")).append(request.getContextPath());
		String server_min_domain = StringUtils.substringAfter(server.toString(), ".");
		request.setAttribute("server_min_domain", server_min_domain);
		// 如果以后换成com域名，则为：xxxx.com

		if (StringUtils.contains(server_min_domain, "localhost:8080") || StringUtils.contains(server_min_domain, "0.1")
				|| StringUtils.contains(server_min_domain, "26.22")
				|| StringUtils.contains(server_min_domain, "235.86")
				|| StringUtils.contains(server_min_domain, "14.25")) {
			request.setAttribute("server_min_domain", null);
			// 118.145.26.22
			// 220.178.14.25本地或者测试地址 设置为空
		}

		// 取最上面的导航条
		List<BaseLink> base3000LinkList = this.getBaseLinkList(3000, null, null);

		request.setAttribute("base3000LinkList", base3000LinkList);

		List<BaseLink> base10LinkList = this.getBaseLinkListWithPindex(10, 4, "no_null_image_path", null);
		request.setAttribute("base10LinkList", base10LinkList);

		request.setAttribute("user_msg", 0);
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != ui && ui.getUser_type().intValue() != 1) {// 用户消息,管理员不查询
			MsgReceiver entity = new MsgReceiver();
			entity.setReceiver_user_id(ui.getId());
			entity.setIs_read(0);
			entity.getMap().put("select_only_msg_re", "true");
			int user_msg = getFacade().getMsgReceiverService().getMsgReceiverCount(entity);
			request.setAttribute("user_msg", user_msg);
		}

		this.controlCookie(null, request, null); // 最近浏览商品
	}

	/**
	 * @author Wu,Yang
	 * @version 2011-12-19
	 * @desc 商务资讯、 诚信企业库 、商务展会、互动咨询 公用数据
	 */
	public void setPublicInfoOtherList(HttpServletRequest request) {
		// 右侧公用信息（热门搜索）
		request.setAttribute("newsInfoForRank", this.getNewsInfoForRank(10, null));// 推荐资讯（浏览量排行）

		// 首页分类
		// this.setBasePdClassToRequestForCategory(request, "baseClassForCategoryList", -1, 2, null, null, null);
	}

	/**
	 * @author Wu,Yang
	 * @version 2011-12-16
	 * @desc 首页公用数据,新闻信息
	 */
	public List<NewsInfo> getNewsInfoList(HttpServletRequest request, String mod_id, Integer count) {
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		entity.getMap().put("is_pub", "0");
		entity.getMap().put("no_invalid", "no_invalid_date");
		entity.setIs_del(0);
		entity.getRow().setCount(count);
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(entity);
		return newsInfoList;
	}

	/**
	 * @author libaiqiang
	 * @version 2019-4-10
	 * @desc 首页公用数据,新闻信息
	 */
	public List<NewsInfo> getIndexNewsInfoList(HttpServletRequest request, String mod_id, Integer count) {
		NewsInfo entity = new NewsInfo();
		entity.getRow().setCount(count);
		if (StringUtils.isNotBlank(mod_id)) {
			entity.setMod_id(mod_id);// 商务资讯
		}
		entity.setIs_del(0);
		entity.setInfo_state(3);// 已审核已发布

		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(entity);
		return newsInfoList;
	}

	/**
	 * 获取已审核已发布新闻
	 * 
	 * @author ding,ning
	 * @param request
	 * @param mod_id
	 * @return
	 * @throws Exception
	 */
	public List<NewsInfo> getIndexNewsInfoList(HttpServletRequest request, String mod_id) throws Exception {

		NewsInfo entity = new NewsInfo();
		if (StringUtils.isNotBlank(mod_id)) {
			entity.setMod_id(mod_id);// 商务资讯
		}
		entity.setIs_del(0);
		entity.setInfo_state(3);// 已审核已发布

		return getFacade().getNewsInfoService().getNewsInfoList(entity);

	}

	public Integer getPIndexFromIp(String ip, HttpServletRequest request) throws Exception {
		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		String result = GetApiUtils.getApiWithUrl(url);
		logger.info("----- result---->{}", result);

		// 公司内部开发，禁止淘宝访问处理返回值
		try {
			JSONObject.parse(result.toString());
		} catch (Exception e) {
			// result =
			// "{\"code\":0,\"data\":{\"country\":\"\u4e2d\u56fd\",\"country_id\":\"CN\",\"area\":\"\u534e\u4e1c\",\"area_id\":\"300000\",\"region\":\"\u5b89\u5fbd\u7701\",\"region_id\":\"Keys.P_INDEX_INIT\",\"city\":\"\u5408\u80a5\u5e02\",\"city_id\":\"340100\",\"county\":\"\",\"county_id\":\"-1\",\"isp\":\"\u7535\u4fe1\",\"isp_id\":\"100017\",\"ip\":\"220.178.14.98\"}}";
			result = "{\"code\":0,\"data\":{\"country\":\"\u4e2d\u56fd\",\"country_id\":\"CN\",\"area\":\"\",\"area_id\":\"-1\",\"region\":\"\",\"region_id\":\"-1\",\"city\":\"\",\"city_id\":\"-1\",\"county\":\"\",\"county_id\":\"-1\",\"isp\":\"\",\"isp_id\":\"-1\",\"ip\":\"103.224.235.71\"}}";
		}
		JSONObject json = (JSONObject) JSONObject.parse(result.toString());
		String code = json.get("code").toString();
		if (code.equals("0")) {// 0代表成功
			JSONObject json_data = json.getJSONObject("data");
			String p_name = (String) json_data.get("city");
			String region = (String) json_data.get("region");
			BaseProvince bp = new BaseProvince();
			bp.setIs_del(0);
			if (StringUtils.isNotBlank(p_name)) {
				bp.setP_name(p_name);
			} else {
				bp.setP_name(Keys.P_INDEX_CITY_NAME);
				region = Keys.P_INDEX_CITY_NAME;
			}
			bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
			if (null != bp) {
				request.setAttribute("p_name", region);
				request.setAttribute("full_name", bp.getFull_name());
				return bp.getP_index().intValue();
			} else {
				return null;
			}
		} else {
			BaseProvince bp = new BaseProvince();
			bp.setIs_del(0);
			bp.setP_name(Keys.P_INDEX_CITY_NAME);
			String region = Keys.P_INDEX_CITY_NAME;
			bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
			if (null != bp) {
				request.setAttribute("p_name", region);
				request.setAttribute("full_name", bp.getFull_name());
				return bp.getP_index().intValue();
			} else {
				return null;
			}
		}
	}

	public Integer getPIndexFromIpForMobile(DynaBean dynaBean, String ip, HttpServletRequest request) throws Exception {

		String url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip;
		String result = GetApiUtils.getApiWithUrl(url);
		logger.info("----- result---->{}", result);

		// 公司内部开发，禁止淘宝访问处理返回值
		try {
			JSONObject.parse(result.toString());
		} catch (Exception e) {
			result = "{\"code\":0,\"data\":{\"country\":\"\u4e2d\u56fd\",\"country_id\":\"CN\",\"area\":\"\",\"area_id\":\"-1\",\"region\":\"\",\"region_id\":\"-1\",\"city\":\"\",\"city_id\":\"-1\",\"county\":\"\",\"county_id\":\"-1\",\"isp\":\"\",\"isp_id\":\"-1\",\"ip\":\"103.224.235.71\"}}";
		}
		JSONObject json = (JSONObject) JSONObject.parse(result.toString());
		String code = json.get("code").toString();

		if (code.equals("0")) {// 0代表成功
			JSONObject json_data = json.getJSONObject("data");
			String p_name = (String) json_data.get("city");
			BaseProvince bp = new BaseProvince();
			bp.setIs_del(0);
			if (StringUtils.isNotBlank(p_name)) {
				bp.setP_name(p_name);
			} else {
				bp.setP_name(Keys.P_INDEX_CITY_NAME);
			}
			bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
			if (null != bp) {
				request.setAttribute("city_name", bp.getP_name());
				request.setAttribute("full_name", bp.getFull_name());
				BaseProvince bp_father = new BaseProvince();
				bp_father.setIs_del(0);
				bp_father.setP_index(bp.getPar_index());
				bp_father = getFacade().getBaseProvinceService().getBaseProvince(bp_father);
				if (null != bp_father) {
					dynaBean.set("province", bp_father.getP_index());
					dynaBean.set("city", bp.getP_index());
					dynaBean.set("province_name", bp_father.getP_name());
					dynaBean.set("city_name", bp.getP_name());
				}
				return bp.getP_index().intValue();
			} else {
				return null;
			}
		} else {
			BaseProvince bp = new BaseProvince();
			bp.setIs_del(0);
			bp.setP_name(Keys.P_INDEX_CITY_NAME);
			String region = Keys.P_INDEX_CITY_NAME;
			bp = getFacade().getBaseProvinceService().getBaseProvince(bp);
			if (null != bp) {
				request.setAttribute("city_name", region);
				request.setAttribute("full_name", bp.getFull_name());
				dynaBean.set("province", Keys.P_INDEX_INIT);
				dynaBean.set("city", bp.getP_index());
				dynaBean.set("city_name", region);
				dynaBean.set("province_name", Keys.P_INDEX_P_NAME);
				return bp.getP_index().intValue();
			} else {
				return null;
			}
		}
	}

	/**
	 * @author Qin,Yue
	 * @version 2011-12-01
	 * @desc 首页公用数据,获取 新闻资讯
	 */
	public void setNewsInfoListToSession(HttpServletRequest request, String mod_id, Integer count) {
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		entity.getMap().put("is_pub", "0");
		entity.getMap().put("no_invalid", "no_invalid_date");
		entity.setIs_del(0);
		entity.getRow().setCount(count);
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoList(entity);
		request.setAttribute("newsInfo" + mod_id + "List", newsInfoList);
	}

	/**
	 * @author Ren,peng
	 * @version 2011-12-16
	 * @desc 获取省份列表
	 */
	public List<BaseProvince> getProvinceList() {
		BaseProvince province = new BaseProvince();
		province.setIs_del(0);
		province.setPar_index(Long.valueOf(0));
		List<BaseProvince> provinceList = super.getFacade().getBaseProvinceService().getBaseProvinceList(province);
		return provinceList;
	}

	/**
	 * @author Ren,peng
	 * @version 2011-12-21
	 * @desc 根据地区 p_index 获取所在省份
	 */
	public BaseProvince getProvinceAccordP_index(Integer p_index) {
		BaseProvince baseProvince = new BaseProvince();
		baseProvince.setIs_del(0);
		baseProvince.setP_index(Long.valueOf(p_index.toString().substring(0, 2) + "0000"));
		baseProvince = super.getFacade().getBaseProvinceService().getBaseProvince(baseProvince);
		return baseProvince;
	}

	/**
	 * @author liujia
	 * @version 2017-11-22
	 * @desc 通过合伙人id查找对应的企业
	 */
	public List<EntpInfo> getEntpInfoListByServiceId(Integer link_service_center_id) {
		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setAudit_state(Keys.audit_state.audit_state_2.getIndex());
		entity.setLink_service_center_id(link_service_center_id);
		List<EntpInfo> entpInfoList = super.getFacade().getEntpInfoService().getEntpInfoList(entity);

		return entpInfoList;

	}

	/**
	 * @author Qin,Yue
	 * @version 2012-3-28
	 * @desc 首页公用数据,获取互动咨询信息
	 */
	public List<QaInfo> getQaInfoList(HttpServletRequest request, Integer count, Integer q_type, Integer is_nx) {
		QaInfo entity = new QaInfo();
		if (null != is_nx) {
			entity.setIs_nx(is_nx);
		}
		if (null != q_type) {
			entity.setQ_type(q_type);
		}
		entity.setQa_state(1);
		entity.getRow().setCount(count);
		List<QaInfo> qaInfoList = super.getFacade().getQaInfoService().getQaInfoList(entity);

		return qaInfoList;

	}

	/**
	 * @author Zhang,Xufeng
	 * @version 2012-03-29
	 * @desc 首页公用数据,（右侧公用数据）,获取 热门搜索（推荐资讯）
	 */
	public List<NewsInfo> getNewsInfoForRank(Integer count, String mod_id) {
		NewsInfo entity = new NewsInfo();
		entity.getMap().put("no_invalid", "no_invalid");
		entity.setIs_del(0);
		entity.setInfo_state(3);
		if (null != mod_id) {
			entity.setMod_id(mod_id);
		} else {
			entity.setMod_id("1003003000");
		}
		entity.getRow().setCount(count);
		List<NewsInfo> newsInfoList = getFacade().getNewsInfoService().getNewsInfoListForRank(entity);
		return newsInfoList;
	}

	/**
	 * @author Li,Ka
	 * @version 2012-03-29
	 * @desc 取商务新闻
	 * @param count 条数
	 */
	public List<NewsInfo> getNewsInfoSwxwList(String mod_id, Integer count) {
		NewsInfo entity = new NewsInfo();
		entity.getMap().put("no_invalid", "no_invalid");
		entity.setIs_del(0);
		entity.setInfo_state(3);
		entity.getRow().setCount(count);
		if (null == mod_id) {
			entity.setMod_id("1003001000");
		} else {
			entity.setMod_id(mod_id);
		}
		List<NewsInfo> newsInfoSwxwList = getFacade().getNewsInfoService().getNewsInfoListForRank(entity);
		return newsInfoSwxwList;
	}

	/**
	 * @author Li,Ka
	 * @version 2012-03-30
	 * @desc 取轮播新闻（图）
	 * @param count 条数,no_null_image_path 不为空的字符串（即取有图的轮播新闻）
	 */
	public List<NewsInfo> getNewsInfoLbtpList(String mod_id, Integer count, String no_null_image_path) {
		NewsInfo entity = new NewsInfo();
		entity.getMap().put("no_invalid", "no_invalid");
		entity.setIs_del(0);
		entity.setInfo_state(3);
		entity.getRow().setCount(count);
		if (null == mod_id) {
			entity.setMod_id("1060000100");
		} else {
			entity.setMod_id(mod_id);
		}
		if (null != no_null_image_path) {
			entity.getMap().put("no_null_image_path", "true");
		}
		List<NewsInfo> newsInfoLbtpList = getFacade().getNewsInfoService().getNewsInfoList(entity);
		return newsInfoLbtpList;
	}

	/**
	 * @author Zhang,Xufeng
	 * @version 2012-03-30
	 * @desc 注册用户后台首页--企业投诉信息
	 */
	public List<QaInfo> getCustomerQaInfoList(UserInfo userInfo, Integer count) {
		QaInfo entity = new QaInfo();
		entity.setQ_user_id(userInfo.getId());
		entity.setQa_state(1);// 已发布
		entity.getRow().setCount(count);
		List<QaInfo> qaInfoList = super.getFacade().getQaInfoService().getQaInfoList(entity);

		return qaInfoList;
	}

	/**
	 * @author Cheng,JiaRui
	 * @version 2012-03-31
	 * @desc 首页--企业目录
	 * @param count
	 * @param types 餐饮企业:21, 住宿企业:22, 娱乐企业:23， 旅游企业 ：24 ，产品企业：25
	 * @return
	 */
	public List<EntpInfo> setEntpInfoListToForm(HttpServletRequest request, Integer count, Integer... types) {
		List<EntpInfo> list = new ArrayList<EntpInfo>();
		for (Integer type : types) {
			EntpInfo entpInfo = new EntpInfo();
			entpInfo.setIs_del(0);
			entpInfo.setAudit_state(1);
			entpInfo.getMap().put("sy_select_entp", true);
			entpInfo.setEntp_type(type);
			entpInfo.getRow().setCount(count);
			request.setAttribute("entpInfo" + type + "List",
					super.getFacade().getEntpInfoService().getEntpInfoList(entpInfo));
		}

		return list;
	}

	/**
	 * @author LiuQi
	 * @version 2014-5-29
	 * @desc 热门搜索产品
	 */
	public List<CommInfo> getPdInfoForHotSeacherList(Integer cls_id, Integer count) {
		CommInfo commInfo = new CommInfo();
		commInfo.setIs_del(0);
		commInfo.setAudit_state(1);
		commInfo.setIs_sell(1); // 是否上架
		commInfo.getMap().put("not_out_sell_time", "true");
		if (null != cls_id) {
			commInfo.getMap().put("allPd", "true");
			commInfo.getMap().put("par_cls_id", cls_id);// 包含子类在内的所有产品
		}
		commInfo.getRow().setCount(count);
		List<CommInfo> pdInfoList = super.getFacade().getCommInfoService().getCommInfoList(commInfo);
		return pdInfoList;
		/*
		 * PdInfo entity = new PdInfo(); entity.setIs_del(0); entity.setPd_type(0);// 普通商品 entity.setAudit_state(1);
		 * entity.setIs_sell(1);// 上架 有效时间范围内 entity.getMap().put("not_out_sell_time", "true"); if (null != cls_id) {
		 * entity.getMap().put("allPd", "true"); entity.getMap().put("par_cls_id", cls_id);// 包含子类在内的所有产品 }
		 * entity.getRow().setCount(count); List<PdInfo> pdInfoList =
		 * super.getFacade().getPdInfoService().getPdInfoForHotSeacherList(entity); return pdInfoList;
		 */
	}

	/**
	 * @author Li,Ka
	 * @version 2012-04-16
	 * @desc 首页--友情链接
	 * @param request
	 * @return
	 */
	public void setFriendsLink(HttpServletRequest request) {
		NewsInfo newsInfo = new NewsInfo();
		newsInfo.setMod_id("2010000100"); // 国家部委网站
		newsInfo.setIs_del(0);
		newsInfo.setInfo_state(3);
		List<NewsInfo> gjbwLinknewsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo);
		request.setAttribute("gjbwLinknewsInfoList", gjbwLinknewsInfoList);

		NewsInfo newsInfo2 = new NewsInfo();
		newsInfo2.setMod_id("2010000200"); // 省市商务厅局网站
		newsInfo2.setIs_del(0);
		newsInfo2.setInfo_state(3);
		List<NewsInfo> ssswtjLinknewsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo2);
		request.setAttribute("ssswtjLinknewsInfoList", ssswtjLinknewsInfoList);

		NewsInfo newsInfo3 = new NewsInfo();
		newsInfo3.setMod_id("2010000300"); // 自治区委办局网站
		newsInfo3.setIs_del(0);
		newsInfo3.setInfo_state(3);
		List<NewsInfo> zzqwbjLinknewsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo3);
		request.setAttribute("zzqwbjLinknewsInfoList", zzqwbjLinknewsInfoList);

		NewsInfo newsInfo4 = new NewsInfo();
		newsInfo4.setMod_id("2010000400"); // 盟市商务局网站
		newsInfo4.setIs_del(0);
		newsInfo4.setInfo_state(3);
		List<NewsInfo> msswjLinknewsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo4);
		request.setAttribute("msswjLinknewsInfoList", msswjLinknewsInfoList);

		NewsInfo newsInfo5 = new NewsInfo();
		newsInfo5.setMod_id("2010000500"); // 其他相关网站
		newsInfo5.setIs_del(0);
		newsInfo5.setInfo_state(3);
		List<NewsInfo> qtxgLinknewsInfoList = getFacade().getNewsInfoService().getNewsInfoList(newsInfo5);
		request.setAttribute("qtxgLinknewsInfoList", qtxgLinknewsInfoList);
	}

	public void setHelpModuleListByParId(HttpServletRequest request, Integer par_id) {
		if (null != par_id) {
			List<HelpModule> helpModuleList = new ArrayList<HelpModule>();
			HelpModule helpModule = new HelpModule();
			helpModule.setPar_id(par_id);
			helpModuleList = getFacade().getHelpModuleService().getHelpModuleList(helpModule);
			request.setAttribute("helpModule" + par_id + "List", helpModuleList);
		}
	}

	public void setHelpModuleListByTail(HttpServletRequest request, Integer par_id) {
		if (null != par_id) {
			List<HelpModule> helpModuleList = new ArrayList<HelpModule>();
			HelpModule helpModule = new HelpModule();
			helpModule.setPar_id(par_id);
			helpModuleList = getFacade().getHelpModuleService().getHelpModuleList(helpModule);
			if (null != helpModuleList && helpModuleList.size() > 0) {
				for (HelpModule temp : helpModuleList) {
					helpModule = new HelpModule();
					helpModule.setPar_id(temp.getH_mod_id());
					List<HelpModule> helpModuleChiList = super.getFacade().getHelpModuleService()
							.getHelpModuleList(helpModule);
					temp.getMap().put("helpModuleChiList", helpModuleChiList);
				}
			}
			request.setAttribute("helpModule" + par_id + "List", helpModuleList);
		}
	}

	public void setHelpModuleListByNew(HttpServletRequest request) {

		List<BaseLink> baseLinkList;

		baseLinkList = this.getBaseLinkList(1100, 4, null);
		if (null != baseLinkList && baseLinkList.size() > 0) {
			for (BaseLink temp : baseLinkList) {
				HelpModule helpModule = new HelpModule();
				helpModule.setPar_id(Integer.valueOf(temp.getContent()));
				helpModule.getRow().setCount(4);
				List<HelpModule> helpModuleChiList = super.getFacade().getHelpModuleService()
						.getHelpModuleList(helpModule);
				temp.getMap().put("helpModuleChiList", helpModuleChiList);
			}
		}
		request.setAttribute("baseLinkList", baseLinkList);
	}

	public void getBaseProvinceCityList(HttpServletRequest request, String setName, Integer par_index) {
		BaseProvince baseProvince = new BaseProvince();
		if (null != par_index) {
			baseProvince.setPar_index(Long.valueOf(par_index));
		} else {
			baseProvince.setPar_index(Long.valueOf(Keys.P_INDEX_INIT));
		}
		baseProvince.setIs_del(new Integer("0"));

		List<BaseProvince> baseProvinceList = getFacade().getBaseProvinceService().getBaseProvinceList(baseProvince);
		// return baseProvinceList;
		request.setAttribute(setName, baseProvinceList);
	}

	/**
	 * @author Zhang,Xufeng
	 * @version 2012-06-25 商务预订--取所属企业信息
	 */
	public EntpInfo getEntpInfoForEntpHome(Integer own_entp_id) {
		if (null == own_entp_id) {
			return null;
		}
		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setAudit_state(1);
		entpInfo.setId(own_entp_id);
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		return entpInfo;
	}

	/**
	 * 商务预订--详细页面，轮播图
	 */
	protected String getCxImageForEntpHome(HttpServletRequest request, List<BaseImgs> baseImgsList) throws Exception {
		return this.getPptJsonStringForEntpHome(baseImgsList, "file_path");
	}

	/**
	 * 商务预订--详细页面，轮播图
	 */
	private String getPptJsonStringForEntpHome(List<BaseImgs> baseImgsList, String imgProperty) throws Exception {
		StringBuffer buffer = new StringBuffer();
		for (BaseImgs o : baseImgsList) {
			String img = "";
			try {
				img = BeanUtils.getProperty(o, imgProperty);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
			buffer.append("{");
			buffer.append("img:'").append("").append(img).append("'},");
		}
		buffer.append("{}");
		return buffer.toString();
	}

	/**
	 * @author Li,Ka
	 * @version 2012-06-26 轮播图---公用方法
	 * @param c
	 * @param imgProperty
	 * @param txtProperty
	 * @param lnkProperty
	 * @param linkURI
	 * @return
	 * @throws Exception
	 * @see #getPptJsonString(Collection, String, String, String,String, String)
	 */
	protected String getPptJsonString(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
			String lnkProperty, String linkURI, String siteURI) throws Exception {
		return this.getPptJsonStringByList(newsInfoList, imgProperty, txtProperty, lnkProperty, linkURI, siteURI);
	}

	/**
	 * @param c Collection
	 * @param imgProperty
	 * @param txtProperty
	 * @param lnkProperty
	 * @param linkURI default is "news/view.tdb"
	 * @param siteURI default is "" link to other or absoult uri e.g. http://photo.163.com/
	 * @return String
	 * @throws Exception
	 */
	private String getPptJsonStringByList(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
			String lnkProperty, String directURI, String siteURI) throws Exception {

		// linkURI = StringUtils.isNotBlank(linkURI) ? linkURI :
		// "news/view.tdb";
		// linkURI = linkURI.indexOf("?") == -1 ? linkURI + "?uuid=" : linkURI +
		// "&uuid=";
		siteURI = StringUtils.isNotBlank(siteURI) ? siteURI : "";

		StringBuffer buffer = new StringBuffer();
		for (NewsInfo o : newsInfoList) {
			String img = "";
			String txt = "";
			String lnk = "";
			String direct_uri = "";
			try {
				img = BeanUtils.getProperty(o, imgProperty);
				txt = BeanUtils.getProperty(o, txtProperty);
				direct_uri = BeanUtils.getProperty(o, directURI);// 直接URL地址
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			buffer.append("{");
			buffer.append("img:'").append(siteURI).append(img).append("',");
			buffer.append("txt:'").append(URLEncoder.encode(StringUtils.replace(txt, "\'", "\\\'"), "UTF-8"))
					.append("',");

			buffer.append("lnk:escape('").append(direct_uri).append(lnk).append("')},");
		}
		buffer.append("{}");
		return buffer.toString();
	}

	/**
	 * 专题页面轮播图
	 * 
	 * @author Cheng,JiaRui
	 * @param newsInfoList
	 * @param imgProperty
	 * @param txtProperty
	 * @param lnkProperty
	 * @param linkProperty2
	 * @param linkURI
	 * @param siteURI
	 * @return
	 * @throws Exception
	 */
	protected String getPptJsonStringTopic(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
			String lnkProperty, String linkProperty2, String linkURI, String siteURI) throws Exception {
		return this.getPptJsonStringByListTopic(newsInfoList, imgProperty, txtProperty, lnkProperty, linkProperty2,
				linkURI, siteURI);
	}

	/**
	 * 专题页面轮播图
	 * 
	 * @author Cheng,JiaRui
	 * @param newsInfoList
	 * @param imgProperty
	 * @param txtProperty
	 * @param lnkProperty
	 * @param linkProperty2
	 * @param directURI
	 * @param siteURI
	 * @return
	 * @throws Exception
	 */
	private String getPptJsonStringByListTopic(List<NewsInfo> newsInfoList, String imgProperty, String txtProperty,
			String lnkProperty, String linkProperty2, String directURI, String siteURI) throws Exception {

		// linkURI = StringUtils.isNotBlank(linkURI) ? linkURI :
		// "news/view.tdb";
		// linkURI = linkURI.indexOf("?") == -1 ? linkURI + "?uuid=" : linkURI +
		// "&uuid=";
		siteURI = StringUtils.isNotBlank(siteURI) ? siteURI : "";

		StringBuffer buffer = new StringBuffer();
		for (NewsInfo o : newsInfoList) {
			String img = "";
			String txt = "";
			String lnk = "";
			String lnk2 = "";
			// String direct_uri = "";
			try {
				img = BeanUtils.getProperty(o, imgProperty);
				txt = BeanUtils.getProperty(o, txtProperty);
				lnk = BeanUtils.getProperty(o, lnkProperty);
				lnk2 = (String) o.getMap().get(linkProperty2);
				// direct_uri = BeanUtils.getProperty(o, directURI);// 直接URL地址
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			buffer.append("{");
			buffer.append("img:'").append(siteURI).append(img).append("',");
			buffer.append("txt:'").append(URLEncoder.encode(StringUtils.replace(txt, "\'", "\\\'"), "UTF-8"))
					.append("',");

			// buffer.append("lnk:escape('").append(direct_uri).append(lnk).append("')},");
			buffer.append("lnk:escape('").append(directURI + "&uuid=").append(lnk).append("&mod_code=").append(lnk2)
					.append("')},");
		}
		buffer.append("{}");

		return buffer.toString();
	}

	/**
	 * 取出cookie中指定名称的值
	 * 
	 * @param cookieName
	 */
	protected String getCookieValueByCookieName(HttpServletRequest request, String cookieName)
			throws UnsupportedEncodingException {
		Cookie cookie = WebUtils.getCookie(request, cookieName);
		String cookieValue = null != cookie ? cookie.getValue() : "";
		return URLDecoder.decode(cookieValue, "UTF-8");
	}

	/**
	 * 将新的Long类型的cookie值加入cookie中
	 * 
	 * @param cookieValue 原cookie值
	 * @param addLongValue 需要加入的值
	 * @param cookieMaxAge
	 * @param cookieName
	 */
	protected void addCookieToResponse(HttpServletResponse response, String cookieValue, Integer addLongValue,
			String cookieName) throws UnsupportedEncodingException {
		if (!cookieValue.contains(addLongValue.toString())) {// 不重复，则存入
			cookieValue = cookieValue.concat(",").concat(addLongValue.toString());
			CookieGenerator cg = new CookieGenerator();
			cg.setCookieMaxAge(1 * 24 * 60 * 60);
			cg.setCookieName(cookieName);
			cg.addCookie(response, URLEncoder.encode(cookieValue, "UTF-8"));
		}
	}

	/**
	 * 取评论
	 */

	protected List<CommentInfo> getCommentInfoListByLinkId(Integer link_id) {
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setLink_id(link_id);
		commentInfo.setComm_state(1);
		commentInfo.getRow().setCount(10);
		List<CommentInfo> commentInfoList = getFacade().getCommentInfoService().getCommentInfoList(commentInfo);
		return commentInfoList;
	}

	public void setCartInfoListToFrom(HttpServletRequest request) {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != (ui)) {
			CartInfo cartInfo = new CartInfo();
			cartInfo.setIs_del(0);
			cartInfo.setUser_id(ui.getId());
			List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
			request.setAttribute("cartInfoList", cartInfoList);
		}
	}

	/**
	 * 过滤购物车的数据
	 * 
	 * @param request
	 */
	public void filterCartInfoListToFrom(HttpServletRequest request, String... cart_type) {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null != (ui)) {
			CartInfo cartInfo = new CartInfo();
			cartInfo.setIs_del(0);
			cartInfo.setUser_id(ui.getId());
			cartInfo.getMap().put("cart_types_not_in", cart_type);
			List<CartInfo> cartInfoList = getFacade().getCartInfoService().getCartInfoList(cartInfo);
			request.setAttribute("cartInfoList", cartInfoList);
		}
	}

	/**
	 * 去自定义数据表Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<BaseLink> getBaseLinkList(Integer linkType, Integer count, String no_null_image_path) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	public ServiceBaseLink getServiceBaseLinkBg(Integer linkType, Integer link_id, Integer main_type,
			String no_null_image_path) {
		ServiceBaseLink blk = new ServiceBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setLink_id(link_id);
		blk.setMain_type(main_type);
		blk.setIs_del(0);
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		ServiceBaseLink baseLinkBg = getFacade().getServiceBaseLinkService().getServiceBaseLink(blk);
		return baseLinkBg;
	}

	public EntpBaseLink getEntpBaseLinkBg(Integer linkType, Integer link_id, Integer main_type,
			String no_null_image_path) {
		EntpBaseLink blk = new EntpBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setLink_id(link_id);
		blk.setMain_type(main_type);
		blk.setIs_del(0);
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		EntpBaseLink baseLinkBg = getFacade().getEntpBaseLinkService().getEntpBaseLink(blk);
		return baseLinkBg;
	}

	/**
	 * PC端市级馆维护
	 * 
	 * @param linkType
	 * @param count
	 * @param link_id
	 * @param no_null_image_path
	 * @return
	 */
	public List<ServiceBaseLink> getServiceBaseLinkCityList(Integer linkType, String p_index, String no_null_image_path) {
		ServiceBaseLink blk = new ServiceBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != p_index) {
			blk.getMap().put("p_index_like", p_index.substring(0, 4));
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<ServiceBaseLink> baseLinkList = getFacade().getServiceBaseLinkService().getServiceBaseLinkList(blk);
		return baseLinkList;
	}

	public MServiceBaseLink getServiceMBaseLinkBg(Integer linkType, Integer link_id, Integer main_type,
			String no_null_image_path) {
		MServiceBaseLink blk = new MServiceBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setLink_id(link_id);
		blk.setMain_type(main_type);
		blk.setIs_del(0);
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		MServiceBaseLink baseLinkBg = getFacade().getMServiceBaseLinkService().getMServiceBaseLink(blk);
		return baseLinkBg;
	}

	/**
	 * M端市级馆维护
	 * 
	 * @param linkType
	 * @param count
	 * @param link_id
	 * @param no_null_image_path
	 * @return
	 */
	public List<MServiceBaseLink> getMServiceBaseLinkCityList(Integer linkType, Integer id, String no_null_image_path) {
		String p_index = null;
		if (null != id) {
			UserInfo userInfo = super.getUserInfo(id);
			if (null != userInfo) {
				Integer p_index1 = userInfo.getP_index();
				p_index = String.valueOf(p_index1);
			}
		}
		MServiceBaseLink blk = new MServiceBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != p_index) {
			blk.getMap().put("p_index_like", p_index.substring(0, 4));
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<MServiceBaseLink> baseLinkList = getFacade().getMServiceBaseLinkService().getMServiceBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * PC端县域馆楼层
	 * 
	 * @param linkType
	 * @param count
	 * @param link_id
	 * @param main_type
	 * @param no_null_image_path
	 * @return
	 */

	public List<ServiceBaseLink> getServiceBaseLinkCityWithList(Integer linkType, Integer count, Integer link_id,
			Integer main_type, String no_null_image_path) {
		ServiceBaseLink blk = new ServiceBaseLink();
		blk.setLink_id(link_id);
		blk.setMain_type(main_type);
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<ServiceBaseLink> baseLinkList = getFacade().getServiceBaseLinkService().getServiceBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * M端县域馆楼层
	 * 
	 * @param linkType
	 * @param count
	 * @param link_id
	 * @param main_type
	 * @param no_null_image_path
	 * @return
	 */

	public List<MServiceBaseLink> getMServiceBaseLinkCityWithList(Integer linkType, Integer count, Integer link_id,
			Integer main_type, String no_null_image_path) {
		MServiceBaseLink blk = new MServiceBaseLink();
		blk.setLink_id(link_id);
		blk.setMain_type(main_type);
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<MServiceBaseLink> baseLinkList = getFacade().getMServiceBaseLinkService().getMServiceBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 店铺楼层
	 * 
	 * @param linkType
	 * @param count
	 * @param link_id
	 * @param main_type
	 * @param no_null_image_path
	 * @return
	 */

	public List<EntpBaseLink> getentpBaseLinkList(Integer linkType, Integer count, Integer link_id, Integer main_type,
			String no_null_image_path) {
		EntpBaseLink blk = new EntpBaseLink();
		blk.setLink_id(link_id);
		blk.setMain_type(main_type);
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<EntpBaseLink> baseLinkList = getFacade().getEntpBaseLinkService().getEntpBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 扶贫馆头像维护
	 * 
	 * @param linkType
	 * @param count
	 * @param no_null_image_path
	 * @return
	 */
	public List<ServiceBaseLink> getServiceBaseLinkTsgList(Integer linkType, String no_null_image_path) {
		ServiceBaseLink blk = new ServiceBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<ServiceBaseLink> baseLinkList = getFacade().getServiceBaseLinkService().getServiceBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表Service_Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<ServiceBaseLink> getServiceBaseLinkListWithParIdAndParSonType(Integer link_id, Integer par_id,
			Integer par_son_type, Integer count, String no_null_image_path) {
		ServiceBaseLink blk = new ServiceBaseLink();
		blk.setLink_id(link_id);
		blk.setPar_id(par_id);
		blk.setPar_son_type(par_son_type);
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<ServiceBaseLink> baseLinkList = getFacade().getServiceBaseLinkService().getServiceBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表M_Service_Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<MServiceBaseLink> getMServiceBaseLinkListWithParIdAndParSonType(Integer link_id, Integer par_id,
			Integer par_son_type, Integer count, String no_null_image_path) {
		MServiceBaseLink blk = new MServiceBaseLink();
		blk.setLink_id(link_id);
		blk.setPar_id(par_id);
		blk.setPar_son_type(par_son_type);
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<MServiceBaseLink> baseLinkList = getFacade().getMServiceBaseLinkService().getMServiceBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表entp_Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<EntpBaseLink> getentpBaseLinkListWithParIdAndParSonType(Integer link_id, Integer par_id,
			Integer par_son_type, Integer count, String no_null_image_path) {
		EntpBaseLink blk = new EntpBaseLink();
		blk.setLink_id(link_id);
		blk.setPar_id(par_id);
		blk.setPar_son_type(par_son_type);
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<EntpBaseLink> baseLinkList = getFacade().getEntpBaseLinkService().getEntpBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<BaseLink> getBaseLinkListWithParIdAndParSonType(Integer par_id, Integer par_son_type, Integer count,
			String no_null_image_path) {
		BaseLink blk = new BaseLink();
		blk.setPar_id(par_id);
		blk.setPar_son_type(par_son_type);
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<MBaseLink> getMBaseLinkListWithParIdAndParSonType(Integer par_id, Integer link_type,
			Integer par_son_type, Integer count, String no_null_image_path) {
		MBaseLink blk = new MBaseLink();
		blk.setPar_id(par_id);
		if (null != link_type) {
			blk.setLink_type(link_type);
		}
		if (null != par_son_type) {
			blk.setPar_son_type(par_son_type);
		}
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<MBaseLink> baseLinkList = getFacade().getMBaseLinkService().getMBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表M_Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<MBaseLink> getMBaseLinkListWithParId(Integer par_id, Integer par_son_type, Integer first,
			Integer count, String no_null_image_path) {
		MBaseLink blk = new MBaseLink();
		blk.setPar_id(par_id);
		if (null != par_son_type) {
			blk.setPar_son_type(par_son_type);
		}
		blk.setIs_del(0);
		if (null != first) {
			blk.getRow().setFirst(first);
		} else {
			blk.getRow().setFirst(0);
		}
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<MBaseLink> baseLinkList = getFacade().getMBaseLinkService().getMBaseLinkPaginatedList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @param p_index
	 * @return
	 */
	public List<BaseLink> getBaseLinkListWithPindex(Integer linkType, Integer count, String no_null_image_path,
			String p_index) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		// if (StringUtils.isNotBlank(p_index)) {
		// blk.setP_index(Integer.valueOf(p_index));
		// } else {
		// blk.setIs_quanguo(1);
		// }
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @param p_index
	 * @return
	 */
	public List<BaseLink> getBaseLinkListWithPindexAndBuqi(Integer linkType, Integer count, String no_null_image_path,
			String p_index, Boolean left_join_comm_info) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		// if (StringUtils.isNotBlank(p_index)) {
		// blk.setP_index(Integer.valueOf(p_index));
		// } else {
		// blk.setIs_quanguo(1);
		// }
		if (left_join_comm_info) {
			blk.getMap().put("left_join_comm_info", "true");
		}

		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);

		if (null != baseLinkList && baseLinkList.size() > 0 && baseLinkList.size() <= count) { // 不足够几个 TODO 可能会有问题
			// int buqiCount = count - baseLinkList.size();
			// for (int i = 0; i < buqiCount; i++) {
			// BaseLink blkBuqi = new BaseLink();
			// baseLinkList.add(blkBuqi);
			// }
		} else {
			for (int i = 0; i < count; i++) {
				BaseLink blkBuqi = new BaseLink();
				blkBuqi.setTitle("暂无商品");
				blkBuqi.setPd_price(new BigDecimal(0));
				blkBuqi.setPd_discount(new BigDecimal(0));
				blkBuqi.setPre_number(0);
				blkBuqi.getMap().put("commentCount", 0);
				blkBuqi.getMap().put("saleCount", 0);
				baseLinkList.add(blkBuqi);
			}
		}
		return baseLinkList;
	}

	public List<BaseLink> getBaseLinkListWithPindexAndBuqiWithParIdAndParSonType(Integer par_id, Integer par_son_type,
			Integer count, String no_null_image_path, String p_index, Boolean left_join_comm_info) {
		BaseLink blk = new BaseLink();
		blk.setPar_id(par_id);

		if (null != par_son_type) {
			blk.setPar_son_type(par_son_type);
		}
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		if (left_join_comm_info) {
			blk.getMap().put("left_join_comm_info", "true");
		}

		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);

		if (left_join_comm_info && null != baseLinkList && baseLinkList.size() > 0) {
			for (BaseLink temp : baseLinkList) {
				if (null != temp.getMap().get("own_entp_id")) {
					EntpInfo entpInfo = super.getEntpInfo(Integer.valueOf(temp.getMap().get("own_entp_id").toString()));
					temp.getMap().put("entpInfo", entpInfo);
				}
			}
		}
		return baseLinkList;
	}

	public List<MBaseLink> getMBaseLinkListWithParIdAndParSonType(Integer par_id, Integer link_type,
			Integer par_son_type, Integer count, String no_null_image_path, Boolean left_join_comm_info) {
		MBaseLink blk = new MBaseLink();
		blk.setPar_id(par_id);
		if (null != link_type) {
			blk.setLink_type(link_type);
		}
		if (null != par_son_type) {
			blk.setPar_son_type(par_son_type);
		}
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		if (left_join_comm_info) {
			blk.getMap().put("left_join_comm_info", "true");
		}

		List<MBaseLink> baseLinkList = getFacade().getMBaseLinkService().getMBaseLinkList(blk);

		if (left_join_comm_info && null != baseLinkList && baseLinkList.size() > 0) {
			for (MBaseLink temp : baseLinkList) {
				if (null != temp.getMap().get("own_entp_id")) {
					EntpInfo entpInfo = super.getEntpInfo(Integer.valueOf(temp.getMap().get("own_entp_id").toString()));
					temp.getMap().put("entpInfo", entpInfo);
				}
			}
		}
		return baseLinkList;
	}

	public List<BaseLink> getBaseLinkListWithPindexParIdAndParSonType(Integer par_id, Integer par_son_type,
			Integer count, String no_null_image_path, String p_index) {
		BaseLink blk = new BaseLink();
		blk.setPar_id(par_id);
		blk.setPar_son_type(par_son_type);
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);

		return baseLinkList;
	}

	public List<BaseLink> getBaseLinkListWithPindexRowAndFirst(Integer linkType, Integer count, Integer first,
			String no_null_image_path, String p_index) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != first) {
			blk.getRow().setFirst(first);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkPaginatedList(blk);
		return baseLinkList;
	}

	public List<BaseLink> getBaseLinkListWithPindexRowAndFirstAndBuqi(Integer linkType, Integer count, Integer first,
			String no_null_image_path, String p_index, Boolean left_join_comm_info) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != first) {
			blk.getRow().setFirst(first);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		if (StringUtils.isNotBlank(p_index)) {
			blk.setP_index(Integer.valueOf(p_index));
		}
		if (left_join_comm_info) {
			blk.getMap().put("left_join_comm_info", "true");
		}

		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		if (null != baseLinkList && baseLinkList.size() > 0 && baseLinkList.size() < count) {
			// int buqiCount = count - baseLinkList.size();
			// for (int i = 0; i < buqiCount; i++) {
			// BaseLink blkBuqi = new BaseLink();
			// baseLinkList.add(blkBuqi);
			// }
		} else {
			for (int i = 0; i < count; i++) {
				BaseLink blkBuqi = new BaseLink();
				blkBuqi.setTitle("暂无商品");
				blkBuqi.setPd_price(new BigDecimal(0));
				blkBuqi.setPd_discount(new BigDecimal(0));
				blkBuqi.setPre_number(0);
				blkBuqi.getMap().put("commentCount", 0);
				blkBuqi.getMap().put("saleCount", 0);
				baseLinkList.add(blkBuqi);
			}
		}
		return baseLinkList;
	}

	// 区分企业和adminlink_type 冲突问题
	public List<BaseLink> getBaseLinkList(Integer linkType, Integer count, String no_null_image_path, Integer link_scope) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		blk.setLink_scope(link_scope);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	public List<BaseLink> getBaseLinkListWithRowAndFirst(Integer linkType, Integer count, Integer first,
			String no_null_image_path) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != first) {
			blk.getRow().setFirst(first);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	// 触屏版的M_BASE_LINK
	public List<MBaseLink> getMBaseLinkList(Integer linkType, Integer count, String no_null_image_path) {
		MBaseLink blk = new MBaseLink();
		blk.setLink_type(Integer.valueOf(linkType));
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<MBaseLink> baseLinkList = getFacade().getMBaseLinkService().getMBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * 去自定义数据表Base_link表
	 * 
	 * @param link_type
	 * @param count
	 * @return
	 */
	public List<BaseLink> getBaseLinkList(Integer linkType, Integer count, String no_null_image_path,
			Integer link_scope, Integer entp_id) {
		BaseLink blk = new BaseLink();
		blk.setLink_type(linkType);
		blk.setIs_del(0);
		if (null != count) {
			blk.getRow().setCount(count);
		}
		if (null != entp_id) {
			blk.setEntp_id(entp_id);
		}
		if (null != link_scope) {
			blk.setLink_scope(link_scope);
		}
		if (null != no_null_image_path) {
			blk.getMap().put("no_null_image_path", "true");
		}
		List<BaseLink> baseLinkList = getFacade().getBaseLinkService().getBaseLinkList(blk);
		return baseLinkList;
	}

	/**
	 * @param c Collection
	 * @param imgProperty
	 * @param txtProperty
	 * @param lnkProperty
	 * @param linkURI default is "news/view.tdb"
	 * @param siteURI default is "" link to other or absoult uri e.g. http://photo.163.com/
	 * @return String
	 * @throws Exception
	 */
	protected String getPptJsonStringByLinkList(List<BaseLink> baseLinkList, String imgProperty, String txtProperty,
			String lnkProperty, String linkURL, String siteURI) throws Exception {

		// linkURI = StringUtils.isNotBlank(linkURI) ? linkURI :
		// "news/view.tdb";
		// linkURI = linkURI.indexOf("?") == -1 ? linkURI + "?uuid=" : linkURI +
		// "&uuid=";
		siteURI = StringUtils.isNotBlank(siteURI) ? siteURI : "";

		StringBuffer buffer = new StringBuffer();
		for (BaseLink o : baseLinkList) {
			String img = "";
			String txt = "";
			String lnk = "";
			String direct_uri = "";
			try {
				img = BeanUtils.getProperty(o, imgProperty);
				txt = BeanUtils.getProperty(o, txtProperty);
				direct_uri = BeanUtils.getProperty(o, linkURL);// 直接URL地址
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			buffer.append("{");
			buffer.append("img:'").append(siteURI).append(img).append("',");
			buffer.append("txt:'").append(URLEncoder.encode(StringUtils.replace(txt, "\'", "\\\'"), "UTF-8"))
					.append("',");

			buffer.append("lnk:escape('").append(direct_uri).append(lnk).append("')},");
		}
		buffer.append("{}");
		return buffer.toString();
	}

	public void controlCookie(HttpServletResponse response, HttpServletRequest request, CommInfo commInfo)
			throws UnsupportedEncodingException { // 最近浏览的商品
		String separatorChar = "卍卐"; // 分隔符
		String cookieName = "browseCommInfos";
		String latestCookieValue = "";
		Cookie[] cookies = request.getCookies();
		Cookie mainCokie = null;
		if ((null != cookies) && (cookies.length > 0)) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					mainCokie = cookie;
					latestCookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
					break;
				}
			}
			if (StringUtils.isNotBlank(latestCookieValue)) {
				String[] latestCookieValues = StringUtils.splitByWholeSeparator(latestCookieValue, "@#");
				if ((null != latestCookieValues) && (latestCookieValues.length > 0)) {
					List<CommInfo> cookiesCommInfoList = new ArrayList<CommInfo>();
					for (String latestCookieValue2 : latestCookieValues) {
						String[] cookiesList = StringUtils.splitByWholeSeparator(latestCookieValue2, separatorChar);

						if (cookiesList.length == 5) {
							CommInfo commInfo2 = new CommInfo();
							commInfo2.setId(Integer.valueOf(cookiesList[0]));
							commInfo2.setComm_name(cookiesList[1]);
							commInfo2.setMain_pic(cookiesList[2]);
							commInfo2.setSale_price(new BigDecimal(cookiesList[3]));
							commInfo2.setPrice_ref(new BigDecimal(cookiesList[4]));
							cookiesCommInfoList.add(commInfo2);
						}
					}
					request.setAttribute("cookiesCommList", cookiesCommInfoList);
				}
			}
		}
		if (null != commInfo) {
			String newCookieValue = commInfo.getId() + separatorChar + commInfo.getComm_name() + separatorChar
					+ commInfo.getMain_pic() + separatorChar + commInfo.getSale_price() + separatorChar
					+ commInfo.getPrice_ref() + "@#";
			if (mainCokie == null) {
				mainCokie = new Cookie(cookieName, URLEncoder.encode(newCookieValue, "UTF-8"));
			} else {
				if (latestCookieValue.contains(newCookieValue)) {
					return;
				}
				latestCookieValue = latestCookieValue.concat(newCookieValue);
			}
			mainCokie.setValue(URLEncoder.encode(latestCookieValue, "UTF-8"));
			mainCokie.setPath("/");
			response.addCookie(mainCokie);
		}
	}

	public void getCookie(HttpServletRequest request, Map<String, Object> model) throws UnsupportedEncodingException { // 最近浏览的商品
		String separatorChar = "卍卐"; // 分隔符
		String cookieName = "browseCommInfos";
		String latestCookieValue = "";
		Cookie[] cookies = request.getCookies();
		if ((null != cookies) && (cookies.length > 0)) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					latestCookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
					break;
				}
			}
			if (StringUtils.isNotBlank(latestCookieValue)) {
				String[] latestCookieValues = StringUtils.splitByWholeSeparator(latestCookieValue, "@#");
				if ((null != latestCookieValues) && (latestCookieValues.length > 0)) {
					List<CommInfo> cookiesCommInfoList = new ArrayList<CommInfo>();
					for (String latestCookieValue2 : latestCookieValues) {
						String[] cookiesList = StringUtils.splitByWholeSeparator(latestCookieValue2, separatorChar);

						if (cookiesList.length == 5) {
							CommInfo commInfo2 = new CommInfo();
							for (String element : cookiesList) {
								commInfo2.setId(Integer.valueOf(cookiesList[0]));
								commInfo2.setComm_name(cookiesList[1]);
								commInfo2.setMain_pic(cookiesList[2]);
								commInfo2.setSale_price(new BigDecimal(cookiesList[3]));
								commInfo2.setPrice_ref(new BigDecimal(cookiesList[4]));
							}
							cookiesCommInfoList.add(commInfo2);
						}
					}
					model.put("cookies", cookiesCommInfoList);
				}
			}
		}
	}

	/**
	 * @author Liu,Jia
	 * @version 2014-07-24
	 * @desc 购买匿名
	 */
	public String setSecretUserName(String user_name) {
		if (StringUtils.isNotBlank(user_name)) {
			String secretString = "";
			int user_name_length = user_name.length();
			if (user_name_length == 1) {// 长度为1 就默认显示
				secretString = user_name;
			} else if (user_name_length == 2) {// 长度为2 替换最后一个
				// 截取第一位
				String user_name_first = user_name.substring(0, 1);
				// 截取最后一位
				String user_name_last = user_name.substring((user_name_length - 1));
				// 然后中间的都替换成*
				user_name_last = user_name_last.replaceAll(user_name_last, "*");
				secretString = user_name_first + user_name_last;

			} else {// 长度大于2 就替换中间的都为*
				// 截取第一位
				String user_name_first = user_name.substring(0, 1);
				// 截取最后一位
				String user_name_last = user_name.substring((user_name_length - 1));
				// 先截取2-倒数第二位
				String user_name_2_2 = user_name.substring(1, (user_name_length - 2));
				// 然后中间的都替换成*
				user_name_2_2 = StringUtils.leftPad(user_name_2_2.replaceAll(user_name_2_2, "*"),
						user_name_2_2.length() + 1, "*");
				// 这个是如果手机号
				if (user_name_length >= 11) {

					user_name_first = user_name.substring(0, 3);

					user_name_last = user_name.substring((user_name_length - 4));

					user_name_2_2 = user_name.substring(1, (user_name_length - 7));
					// 然后中间的都替换成*
					user_name_2_2 = StringUtils.leftPad(user_name_2_2.replaceAll(user_name_2_2, "*"),
							user_name_2_2.length() + 1, "*");
				}

				secretString = user_name_first + user_name_2_2 + user_name_last;
			}
			return secretString;
		} else {
			return null;
		}
	}

	public void setPublicTitle(Map<String, Object> model) {

		model.put("app_name", Keys.app_name);
		model.put("app_name_min", Keys.app_name_min);
		model.put("app_domain", Keys.app_domain);
		model.put("file_domain", Keys.file_domain);
		model.put("app_domain_m", Keys.app_domain_m);
		model.put("app_keywords", Keys.app_keywords);
		model.put("app_description", Keys.app_description);
		model.put("app_copyright", Keys.app_copyright);
		model.put("p_index_city_name", Keys.P_INDEX_CITY_NAME);

	}

	/**
	 * @author Wu,Yang
	 * @date 2011-09-06 生成订单交易流水号,长度：22位，（前17位：4年2月2日2时2分2秒3毫秒） + （后三位：3SEQ）
	 * @desc 后三位根据SEQ_ORDER_INFO_TRADE_INDEX生成，最大999循环生成
	 */
	public String creatTradeIndex() {
		// BigDecimal trade_index = getFacade().getOrderInfoService().genOrderInfoTradeIndex(new OrderInfo());
		String trade_no = sdFormatymdhmsS.format(new Date());

		return trade_no;
	}

	public ActionForward showTipMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String msg) throws Exception {
		String refererUrl = request.getHeader("Referer");
		logger.info("==refererUrl:{}", refererUrl);

		request.setAttribute("header_title", "系统提示");
		String tip_msg = msg;
		request.setAttribute("tip_msg", tip_msg);
		request.setAttribute("tip_url", refererUrl);

		return new ActionForward("/../index/Tip/index.jsp");
	}

	public ActionForward showTipMsg(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String msg, String toGoUrl) throws Exception {

		request.setAttribute("header_title", "系统提示");
		String tip_msg = msg;
		request.setAttribute("tip_msg", tip_msg);
		request.setAttribute("tip_url", toGoUrl);

		return new ActionForward("/../index/Tip/index.jsp");
	}

	public boolean isLocal(HttpServletRequest request) {
		boolean isLocal = false;

		String ip = this.getIpAddr(request);
		logger.warn("==ip:{}", ip);
		if (StringUtils.equals("0:0:0:0:0:0:0:1", ip) || StringUtils.equals("127.0.0.1", ip)
				|| StringUtils.startsWith(ip, "192.168.3.") || StringUtils.startsWith(ip, "192.168.1.")
				|| StringUtils.startsWith(ip, "192.168.2.") || StringUtils.startsWith(ip, "192.168.0.")
				|| StringUtils.startsWith(ip, "223.215.57.28") || StringUtils.startsWith(ip, "192.168.2.160")
				|| StringUtils.startsWith(ip, "192.168.2.127")) {
			isLocal = true;
		}
		logger.warn("isLocal:{}", isLocal);
		return isLocal;
	}

	/**
	 * 获取团购列表
	 * 
	 * @return
	 */
	public List<CommInfoPromotion> tgCommList() {
		List<CommInfoPromotion> tgCommList = new ArrayList<CommInfoPromotion>();
		CommInfoPromotion tgComm = new CommInfoPromotion();
		tgComm.setIs_del(0);
		tgComm.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
		// 结束时间 > 现在时间
		tgComm.getMap().put("end_time_dayu_new_date", "true");
		tgComm.getRow().setCount(3);
		tgCommList = getFacade().getCommInfoPromotionService().getCommInfoPromotionList(tgComm);
		if (null != tgCommList && tgCommList.size() > 0) {
			List<CommInfoPromotion> delList = new ArrayList<CommInfoPromotion>();
			CommInfo cInfo = null;
			for (CommInfoPromotion temp : tgCommList) {

				CommTczhPrice commTczhPrice = new CommTczhPrice();
				commTczhPrice.setComm_id(temp.getId().toString());
				commTczhPrice.setTczh_type(Keys.tczh_type.tczh_type_1.getIndex());
				List<CommTczhPrice> CommTczhPriceList = getFacade().getCommTczhPriceService().getCommTczhPriceList(
						commTczhPrice);
				if ((CommTczhPriceList != null) && (CommTczhPriceList.size() > 0)) {
					Integer kucun = 0;
					for (int i = 0; i < CommTczhPriceList.size(); i++) {
						kucun += CommTczhPriceList.get(i).getInventory();
					}
					if (Integer.valueOf(kucun) <= 0) {
						delList.add(temp);
					} else {
						cInfo = new CommInfo();
						cInfo.setId(temp.getComm_id());
						cInfo = getFacade().getCommInfoService().getCommInfo(cInfo);
						temp.getMap().put("cInfo", cInfo);
					}
				}

			}
			tgCommList.removeAll(delList);

		}
		return tgCommList;

	}

	public List<CommInfo> getCommInfoList(Integer count, Boolean is_commend, Integer own_entp_id,
			Boolean getCommentInfo, boolean isHot, String orderBy, boolean isPages, Pager pager, Integer newPageSize,
			String comm_name_like, String p_index_like, String root_cls_id, String par_cls_id, String son_cls_id,
			String is_zingying, String is_aid, Integer commType) {
		return this.getCommInfoListForJson(count, is_commend, own_entp_id, getCommentInfo, isHot, orderBy, isPages,
				pager, newPageSize, comm_name_like, p_index_like, root_cls_id, par_cls_id, son_cls_id, null, false,
				is_zingying, is_aid, null, commType);
	}

	// 加一个农产品base_id
	public List<CommInfo> getCommInfoList(Integer count, Boolean is_commend, Integer own_entp_id,
			Boolean getCommentInfo, boolean isHot, String orderBy, boolean isPages, Pager pager, Integer newPageSize,
			String comm_name_like, String p_index_like, String root_cls_id, String par_cls_id, String son_cls_id,
			String is_zingying, String is_aid, String base_id, String entp_comm_class_id, Integer commType) {
		return this.getCommInfoListForJson(count, is_commend, own_entp_id, getCommentInfo, isHot, orderBy, isPages,
				pager, newPageSize, comm_name_like, p_index_like, root_cls_id, par_cls_id, son_cls_id, null, false,
				is_zingying, is_aid, entp_comm_class_id, commType);
	}

	public List<CommInfo> getCommInfoHdList(Integer count, Boolean is_commend, Integer own_entp_id,
			Boolean getCommentInfo, boolean isHot, String orderBy, boolean isPages, Pager pager, Integer newPageSize,
			String comm_name_like, String p_index_like, String root_cls_id, String par_cls_id, String son_cls_id,
			Integer commType) {
		return this.getCommInfoListForJson(count, is_commend, own_entp_id, getCommentInfo, isHot, orderBy, isPages,
				pager, newPageSize, comm_name_like, p_index_like, root_cls_id, par_cls_id, son_cls_id, null, true,
				null, null, null, commType);
	}

	public List<CommInfo> getCommInfoListForJson(Integer count, Boolean is_commend, Integer own_entp_id,
			boolean getCommentInfo, boolean isHot, String orderBy, boolean isPages, Pager pager, Integer newPageSize,
			String comm_name_like, String p_index_like, String root_cls_id, String par_cls_id, String son_cls_id,
			Integer startPage, boolean getHd, String is_zingying, String is_aid, String entp_comm_class_id,
			Integer commType) {
		List<CommInfo> commmInfoList = new ArrayList<CommInfo>();

		CommInfo commInfo = new CommInfo();
		commInfo.setOwn_entp_id(own_entp_id);
		if (getHd) {
			commInfo.setComm_type(Keys.CommType.COMM_TYPE_4.getIndex());
		} else {
			commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());
		}
		if (null != commType) {
			commInfo.setComm_type(commType);
		}
		if (null != is_aid && GenericValidator.isInt(is_aid)) {
			commInfo.setIs_aid(Integer.valueOf(is_aid));
		}
		commInfo.getMap().put("comm_type_not_in", Keys.CommType.COMM_TYPE_30.getIndex());
		commInfo.setAudit_state(1);
		commInfo.setIs_sell(1);
		commInfo.setIs_has_tc(1);
		commInfo.setIs_del(0);
		commInfo.getMap().put("not_out_sell_time", "true");

		if (GenericValidator.isInt(is_zingying)) {
			commInfo.setIs_zingying(Integer.valueOf(is_zingying));
		}
		if (GenericValidator.isInt(root_cls_id)) {
			commInfo.setRoot_cls_id(Integer.valueOf(root_cls_id));
		}
		if (GenericValidator.isInt(par_cls_id)) {
			commInfo.setPar_cls_id(Integer.valueOf(par_cls_id));
		}
		if (GenericValidator.isInt(son_cls_id)) {
			commInfo.setPar_cls_id(null);
			commInfo.setCls_id(Integer.valueOf(son_cls_id));
		}
		if (GenericValidator.isInt(entp_comm_class_id)) {
			commInfo.setEntp_comm_class_id(Integer.valueOf(entp_comm_class_id));
		}

		if (GenericValidator.isInt(p_index_like)) {
			if (p_index_like.endsWith("0000")) {
				commInfo.getMap().put("province_index", StringUtils.substring(p_index_like, 0, 2));
			} else if (p_index_like.endsWith("00")) {
				commInfo.getMap().put("province_index", StringUtils.substring(p_index_like, 0, 4));
			} else {
				commInfo.setP_index(Integer.valueOf(p_index_like));
			}
		}

		if (StringUtils.isNotBlank(orderBy)) {
			commInfo.getMap().put(orderBy, orderBy);
		} else {
			commInfo.getMap().put("orderByMultipleOrderValueDesc", true);
		}
		commInfo.getMap().put("keyword", comm_name_like);
		if (null != count) {
			commInfo.getRow().setCount(count);
		}
		if (is_commend) {
			commInfo.setIs_commend(1);
		}
		if (isHot) {
			commInfo.getMap().put("isHot", "true");
		}

		if (isPages) {
			int pageSize = 10;
			if (null != newPageSize)
				pageSize = newPageSize;

			Integer recordCount = getFacade().getCommInfoService().getCommInfoCount(commInfo);
			pager.init(recordCount.longValue(), pageSize, pager.getRequestPage());
			if (null != startPage) {
				commInfo.getRow().setFirst(Integer.valueOf(startPage) * (pageSize));
			} else {
				commInfo.getRow().setFirst(pager.getFirstRow());
			}
			commInfo.getRow().setCount(pager.getRowCount());
			if (null != count) {
				commInfo.getRow().setCount(count);
			}
			commmInfoList = super.getFacade().getCommInfoService().getCommInfoPaginatedList(commInfo);
		} else {
			commmInfoList = super.getFacade().getCommInfoService().getCommInfoList(commInfo);
		}

		if (null != commmInfoList && commmInfoList.size() > 0) {
			for (CommInfo temp : commmInfoList) {
				if (temp.getIs_zingying().intValue() != 0) {
					temp.getMap().put("commZyName", CommZyType.getName(temp.getIs_zingying()));
				}
				if (getCommentInfo) {
					CommentInfo commentInfo = new CommentInfo();
					commentInfo.setLink_id(temp.getId());
					temp.getMap().put("commentCount", temp.getComment_count());
					List<CommentInfo> commentInfoList = getFacade().getCommentInfoService().getCommentInfoList(
							commentInfo);
					if ((null != commentInfoList) && (commentInfoList.size() > 0)) {
						for (CommentInfo ci : commentInfoList) {
							String secretString = this.setSecretUserName(ci.getComm_uname());
							ci.getMap().put("secretString", secretString);
						}
					}
					temp.getMap().put("commentInfoList", commentInfoList);
					Integer commentScore = getFacade().getCommentInfoService().getCommentInfoAvgCommSore(commentInfo);
					temp.getMap().put("commentScore", commentScore);
				}
				if (temp.getComm_type() == 20) {
					CommTczhPrice commTczhPrice = new CommTczhPrice();
					commTczhPrice.setComm_id(temp.getId().toString());
					List<CommTczhPrice> commTczhPriceList = super.getFacade().getCommTczhPriceService()
							.getCommTczhPriceList(commTczhPrice);
					temp.getMap().put("commTczhPriceList", commTczhPriceList);
				}
			}
		}
		return commmInfoList;
	}

	public PdContent getPdContent(Integer pd_id, Integer type) {
		PdContent pdContent = new PdContent();
		pdContent.setPd_id(pd_id);
		pdContent.setType(type);
		pdContent = super.getFacade().getPdContentService().getPdContent(pdContent);
		return pdContent;
	}

	public static String getRandomNumber(int numberCount) {
		Integer[] rands = new Integer[numberCount];
		for (int i = 0; i < numberCount; i++) {
			int rand = (int) (Math.random() * 10);
			rands[i] = rand;
		}
		return StringUtils.join(rands, "");
	}

	public void setPindexCookies(HttpServletResponse response, String p_index, String p_name) throws Exception {

		CookieGenerator cg_p_index = new CookieGenerator();
		cg_p_index.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_index.setCookieName(Keys.COOKIE_P_INDEX);

		CookieGenerator cg_p_name = new CookieGenerator();
		cg_p_name.setCookieMaxAge(7 * 24 * 60 * 60);
		cg_p_name.setCookieName(Keys.COOKIE_P_NAME);

		try {
			cg_p_index.addCookie(response, URLEncoder.encode(p_index, "UTF-8"));
			cg_p_name.addCookie(response, URLEncoder.encode(p_name, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.warn("==setCookiePindex:" + e.getMessage());
		}
	}

	/**
	 * @author Wu,Yang
	 * @version 2011-12-19
	 * @desc 微信JS应用
	 */
	public void setJsApiTicketRetrunParamToSession(HttpServletRequest request) {
		StringBuffer requestUrl = request.getRequestURL();
		String queryString = request.getQueryString();
		String url = requestUrl.toString();
		url = url.replace("m/index.do", "m/index.shtml");// 因为rewrite原因导致重新，需要改写下名字，要不分享会出现签名错误
		if (StringUtils.isNotBlank(queryString)) {
			url += "?" + queryString;
		}

		JSONObject jsonObject = CommonApi.getJsApiTicketRetrunParam(url);
		if (null != jsonObject) {
			request.setAttribute("appid", jsonObject.getString("appid"));
			request.setAttribute("nonceStr", jsonObject.getString("nonceStr"));
			request.setAttribute("timestamp", jsonObject.getString("timestamp"));
			request.setAttribute("signature", jsonObject.getString("signature"));
		}
	}

	public boolean isWeixin(HttpServletRequest request) {
		boolean isWeixin = false;

		String aiisen = request.getParameter("aiisen");
		if (StringUtils.isNotBlank(aiisen) && StringUtils.equals(aiisen, "aiisen2015")) {
			isWeixin = true;
			return isWeixin;
		}
		// MicroMessenger
		String user_agent = request.getHeader("User-Agent");
		logger.info("====user_agent:{}", user_agent);
		if (StringUtils.isNotBlank(user_agent)) {
			user_agent = StringUtils.lowerCase(user_agent);
			if (user_agent.indexOf("micromessenger") >= 0) {
				isWeixin = true;
			}
		}
		// isWeixin = true;// TODO 发布的时候去掉下面的隐藏

		// String ip = this.getIpAddr(request);
		// logger.info("==ip:{}", ip);
		// if (StringUtils.equals("127.0.0.1", ip) || StringUtils.startsWith(ip, "192.168.3.")
		// || StringUtils.startsWith(ip, "192.168.1.")) {
		// isWeixin = true;
		// }
		return isWeixin;
	}

	public boolean isAlipay(HttpServletRequest request) {
		boolean isAlipay = false;

		// AlipayClient
		String user_agent = request.getHeader("User-Agent");
		logger.info("====user_agent:{}", user_agent);
		if (StringUtils.isNotBlank(user_agent)) {
			user_agent = StringUtils.lowerCase(user_agent);
			if (user_agent.indexOf("alipayclient") >= 0) {
				isAlipay = true;
			}
		}
		return isAlipay;
	}

	// 判断是否是APP调用的webview
	public boolean isApp(HttpServletRequest request) {
		boolean isApp = false;
		// MobaXiuqingchun
		String user_agent = request.getHeader("User-Agent");
		logger.info("====user_agent:{}", user_agent);
		if (StringUtils.isNotBlank(user_agent) && ((user_agent.indexOf(Keys.USER_AGENT) >= 0))) {
			isApp = true;
		}
		// if (super.isLocal(request)) {
		// isApp = true;
		// }
		logger.info("====isApp:{}", isApp);
		request.setAttribute("is_app", isApp);
		return isApp;
	}

	public static String jugdeAppPt(HttpServletRequest request) {
		String appShow = "android";

		String user_agent = request.getHeader("User-Agent");
		String[] keywords = { "android", "iphone", "ipod", "ipad", "windows phone", "mqqbrowser" };
		for (String item : keywords) {
			user_agent = StringUtils.lowerCase(user_agent);
			if (user_agent.contains(item)) {
				appShow = item;
				break;
			}
		}
		return appShow;
	}

	public static Boolean isAndroid(HttpServletRequest request) {
		Boolean isAndroid = false;

		String user_agent = request.getHeader("User-Agent");
		String[] keywords = { "android" };
		for (String item : keywords) {
			user_agent = StringUtils.lowerCase(user_agent);
			if (user_agent.contains(item)) {
				isAndroid = true;
				break;
			}
		}
		return isAndroid;
	}

	public ActionForward showTipNotLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String msg) throws Exception {
		String refererUrl = request.getHeader("Referer");
		logger.info("==refererUrl:{}", refererUrl);

		request.setAttribute("header_title", "系统提示");
		String tip_msg = msg;
		request.setAttribute("tip_msg", tip_msg);
		request.setAttribute("tip_url", "MIndexLogin.do");

		return new ActionForward("/../m/Tip/index.jsp");
	}

	public BigDecimal calMatflowMoney(Integer p_index, Integer user_id, Integer entp_id) {

		CartInfo cartInfo = new CartInfo();
		cartInfo.getMap().put("user_id", user_id);
		cartInfo.getMap().put("entp_id", entp_id);
		List<CartInfo> cartInfoGroupList = super.getFacade().getCartInfoService().getFreIdCartInfoGroupByEntp(cartInfo);

		BigDecimal totalMatflowMoney = new BigDecimal("0.0");

		if (null != cartInfoGroupList && cartInfoGroupList.size() > 0) {

			for (CartInfo temp : cartInfoGroupList) {
				int totalCount = Integer.valueOf(temp.getMap().get("sum_pd_count").toString());
				BigDecimal totalCommWeight = new BigDecimal(temp.getMap().get("sum_comm_weight").toString());
				BigDecimal totalCommVolume = new BigDecimal("0");
				BigDecimal totalSumPrice = new BigDecimal(temp.getMap().get("sum_pd_price").toString());

				Freight fre = super.getFreight(temp.getFre_id(), p_index, totalCount, totalCommWeight, totalCommVolume,
						totalSumPrice);
				if (fre != null) {
					if ("1".equals(fre.getMap().get("delivery_way1")) && "1".equals(fre.getMap().get("is_delivery1"))) {
						totalMatflowMoney = totalMatflowMoney.add((BigDecimal) fre.getMap().get("fre_price1"));
					} else {// 证明 有商品配送不到
						totalMatflowMoney = new BigDecimal(-1);
						break;
					}
				} else {
					totalMatflowMoney = new BigDecimal(-1);
					break;
				}
			}
		}

		return totalMatflowMoney;
	}

	public BigDecimal calMatflowMoneyNew(Integer is_ziti, Integer p_index, Integer user_id,
			Integer shipping_address_id, Integer entp_id, List<CartInfo> cartInfoTempList, String cart_ids)
			throws Exception {

		BigDecimal totalMatflowMoney = new BigDecimal("0.0");

		if (entp_id == Integer.valueOf(Keys.jd_entp_id).intValue()) {
			JSONObject jsonKc = new JSONObject();
			JSONArray jsonArraySkus = new JSONArray();
			for (CartInfo temp : cartInfoTempList) {

				CommInfo commInfo = new CommInfo();
				commInfo.setId(temp.getComm_id());
				commInfo = super.getFacade().getCommInfoService().getCommInfo(commInfo);
				if ((is_ziti == 1 && commInfo.getIs_ziti() == 0) || (is_ziti == 0)) {
					// 1、如果选择了自提，商品不是自提，需要计算运费
					// 2、没有选择自提
					JSONObject jsonComm = new JSONObject();
					jsonComm.put("sku", commInfo.getJd_skuid());
					jsonComm.put("count", temp.getPd_count());
					jsonArraySkus.add(jsonComm);
				}
			}
			jsonKc.put("skus", JSON.toJSON(jsonArraySkus));

			ShippingAddress shippingAddress = new ShippingAddress();
			shippingAddress.setId(shipping_address_id);
			shippingAddress = getFacade().getShippingAddressService().getShippingAddress(shippingAddress);

			String jdArea[] = super.getJdArea(shippingAddress).split(",");

			if (null != jdArea && jdArea.length == 4) {
				jsonKc.put("p", jdArea[0]);
				jsonKc.put("c", jdArea[1]);
				jsonKc.put("d", jdArea[2]);
				jsonKc.put("t", jdArea[3]);
			}

			logger.info("======jsonKc=======" + jsonKc.toJSONString());
			JSONObject stocksJson = this.getJdProductFreight(jsonKc.toJSONString());// 调用京东接口查询运费
			logger.info("======stocksJson=======" + stocksJson.toJSONString());

			if (stocksJson.get("StatusCode").toString().equals(Keys.JD_API_RESULT_STATUS_CODE)) {
				totalMatflowMoney = totalMatflowMoney.add(new BigDecimal(stocksJson.get("Data").toString()).divide(
						new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
			} else {
				totalMatflowMoney = new BigDecimal(-1);
			}
		} else {

			CartInfo cartInfo = new CartInfo();
			cartInfo.getMap().put("user_id", user_id);
			cartInfo.getMap().put("entp_id", entp_id);
			cartInfo.getMap().put("cart_ids_in", cart_ids);
			if (is_ziti == 1) {
				cartInfo.getMap().put("left_join_comm_info", "true");
			}
			List<CartInfo> cartInfoGroupList = super.getFacade().getCartInfoService()
					.getFreIdCartInfoGroupByEntp(cartInfo);

			logger.info("=================" + cartInfoGroupList.size());

			if (null != cartInfoGroupList && cartInfoGroupList.size() > 0) {

				for (CartInfo temp : cartInfoGroupList) {

					// 1、如果选择了自提，商品不是自提，需要计算运费
					// 2、没有选择自提
					int totalCount = Integer.valueOf(temp.getMap().get("sum_pd_count").toString());
					BigDecimal totalCommWeight = new BigDecimal(temp.getMap().get("sum_comm_weight").toString());
					BigDecimal totalCommVolume = new BigDecimal("0");
					BigDecimal totalSumPrice = new BigDecimal(temp.getMap().get("sum_pd_price").toString());

					Freight fre = super.getFreight(temp.getFre_id(), p_index, totalCount, totalCommWeight,
							totalCommVolume, totalSumPrice);
					if (fre != null) {
						if ("1".equals(fre.getMap().get("delivery_way1"))
								&& "1".equals(fre.getMap().get("is_delivery1"))) {
							totalMatflowMoney = totalMatflowMoney.add((BigDecimal) fre.getMap().get("fre_price1"));
						} else {// 证明 有商品配送不到
							totalMatflowMoney = new BigDecimal(-1);
							break;
						}
					} else {
						totalMatflowMoney = new BigDecimal(-1);
						break;
					}
				}
			}
		}

		return totalMatflowMoney;
	}

	// 确认收货之后 发放奖励剩余时间
	public JSONObject getOrderGiveMoneyTxt(Date date) {
		JSONObject json = new JSONObject();
		Date nowDate = new Date();
		int xcDays = DateTools.getXcDaysBetweenTwoDay(DateUtils.addDays(date, Keys.ORDER_END_DATE), nowDate);
		json.put("day_tip", xcDays);
		return json;
	}

	public ActionForward goPcHome(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ActionForward("/customer/Index.do", true);
	}

	public void setUserInfoBiLockDivid100(UserInfo userInfo) {
		BigDecimal bi_dianzi_lock = userInfo.getBi_dianzi_lock()
				.divide(new BigDecimal(100), BigDecimal.ROUND_HALF_DOWN);
		userInfo.setBi_dianzi_lock(bi_dianzi_lock);
	}

	// 如果出现输入密码错误，则需要插入该记录
	public void passErrorUpdateOrInsertSysOperLog(HttpServletRequest request, Integer user_id, String user_name,
			Boolean addLockOrRemoveLock) {
		if (addLockOrRemoveLock) {// 证明是进行上锁操作
			SysOperLog sysOperLog = new SysOperLog();
			sysOperLog.setLink_id(user_id);
			sysOperLog.setOper_type(Keys.SysOperType.SysOperType_30.getIndex());
			sysOperLog.setIs_del(0);
			sysOperLog = super.getFacade().getSysOperLogService().getSysOperLog(sysOperLog);
			if (null == sysOperLog) {// 说明没有记录 则直接插入
				SysOperLog sysOperLogInsert = new SysOperLog();
				sysOperLogInsert.setLink_id(user_id);
				sysOperLogInsert.setOper_type(Keys.SysOperType.SysOperType_30.getIndex());
				sysOperLogInsert.setOper_memo(Keys.SysOperType.SysOperType_30.getName());
				sysOperLogInsert.setOper_name(Keys.SysOperType.SysOperType_30.getName());
				sysOperLogInsert.setOper_time(new Date());
				String ip = this.getIpAddr(request);
				sysOperLogInsert.setOper_uip(ip);
				sysOperLogInsert.setOper_uid(user_id);
				sysOperLogInsert.setOper_uname(user_name);
				sysOperLogInsert.setPre_number(1);
				super.getFacade().getSysOperLogService().createSysOperLog(sysOperLogInsert);
			} else {// 说明有记录 则update 根据 user_id和oper_type
				SysOperLog sysOperLogUpdate = new SysOperLog();
				sysOperLogUpdate.setOper_time(new Date());
				sysOperLogUpdate.getMap().put("link_id", user_id);
				sysOperLogUpdate.getMap().put("oper_type", Keys.SysOperType.SysOperType_30.getIndex());
				sysOperLogUpdate.setPre_number(sysOperLog.getPre_number() + 1);
				super.getFacade().getSysOperLogService().modifySysOperLog(sysOperLogUpdate);
			}
		} else {// 解锁操作
			SysOperLog sysOperLogUpdate = new SysOperLog();
			sysOperLogUpdate.getMap().put("link_id", user_id);
			sysOperLogUpdate.getMap().put("oper_type", Keys.SysOperType.SysOperType_30.getIndex());
			sysOperLogUpdate.setPre_number(0);
			super.getFacade().getSysOperLogService().modifySysOperLog(sysOperLogUpdate);
		}
	}

	public void setBaseBrandInfoList(HttpServletRequest request) {

		BaseBrandInfo brand = new BaseBrandInfo();
		brand.setIs_del(0);
		brand.setAudit_state(1);
		List<BaseBrandInfo> brandList = getFacade().getBaseBrandInfoService().getBaseBrandInfoList(brand);

		request.setAttribute("brandList", brandList);

	}

	/**
	 * @param request
	 * @param commInfo
	 */
	public void getTGCommInfoTczh(HttpServletRequest request, CommInfo commInfo, Integer link_id, Integer tczh_type) {
		String hasSelectTc = "";
		String old_price = "";

		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setComm_id(link_id.toString());
		commTczhPrice.setTczh_type(Keys.tczh_type.tczh_type_1.getIndex());
		logger.info("====CommTczhPrice");
		List<CommTczhPrice> CommTczhPriceList = getFacade().getCommTczhPriceService().getCommTczhPriceList(
				commTczhPrice);
		if ((CommTczhPriceList != null) && (CommTczhPriceList.size() > 0)) {
			for (CommTczhPrice cur : CommTczhPriceList) {
				CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
				commTczhAttribute.setComm_tczh_id(cur.getId());
				commTczhAttribute.setComm_id(link_id.toString());
				commTczhAttribute.setType(Keys.tczh_type.tczh_type_1.getIndex());
				commTczhAttribute.getMap().put("orderby_order_value_asc", "true");
				List<CommTczhAttribute> commTczhAttributeList = getFacade().getCommTczhAttributeService()
						.getCommTczhAttributeList(commTczhAttribute);
				List<String> attr_tczh_ids = new ArrayList<String>();
				List<String> attr_tczh_names = new ArrayList<String>();
				if (null != commTczhAttributeList && commTczhAttributeList.size() > 0) {
					for (CommTczhAttribute temp_cta : commTczhAttributeList) {
						// temp_cta.getMap().put("attr_name", temp_cta.getAttr_name());
						BaseAttributeSon baseAttributeSon = new BaseAttributeSon();
						baseAttributeSon.setId(Integer.valueOf(temp_cta.getAttr_id()));
						baseAttributeSon.setType(Keys.tczh_type.tczh_type_1.getIndex());
						BaseAttributeSon bas = getFacade().getBaseAttributeSonService().getBaseAttributeSon(
								baseAttributeSon);
						// if (bas != null) {
						// temp_cta.setAttr_name(bas.getAttr_name());
						// }
						attr_tczh_ids.add(temp_cta.getAttr_id().toString());
						logger.info("====id:" + temp_cta.getId());
						logger.info("====attr_name:" + temp_cta.getAttr_name());
						attr_tczh_names.add(temp_cta.getAttr_name());
					}
					hasSelectTc = commTczhAttributeList.get(commTczhAttributeList.size() - 1).getAttr_name();
					if (null != CommTczhPriceList.get(CommTczhPriceList.size() - 1).getCost_price()) {
						old_price = CommTczhPriceList.get(CommTczhPriceList.size() - 1).getCost_price().toString();
					}
				}

				cur.getMap().put("attr_tczh_ids", StringUtils.join(attr_tczh_ids, ","));
				cur.getMap().put("attr_tczh_names", StringUtils.join(attr_tczh_names, " "));
			}

			request.setAttribute("hasSelectTc", hasSelectTc);
			request.setAttribute("old_price", old_price);
			logger.info("===old_price" + old_price);
			request.setAttribute("list_CommTczhPrice", CommTczhPriceList);// 套餐价格及对应各属性
			request.setAttribute("comm_tczh_id", CommTczhPriceList.get(0).getId());
			request.setAttribute("orig_price", CommTczhPriceList.get(0).getOrig_price());
			request.setAttribute("sale_price", CommTczhPriceList.get(0).getComm_price());
			request.setAttribute("curr_stock", CommTczhPriceList.get(0).getInventory());
			CommTczhAttribute commTczhAttribute = new CommTczhAttribute();
			commTczhAttribute.setComm_tczh_id(CommTczhPriceList.get(0).getId());
			commTczhAttribute = getFacade().getCommTczhAttributeService().getCommTczhAttribute(commTczhAttribute);
			if (commTczhAttribute != null) {
				request.setAttribute("attr_name", commTczhAttribute.getAttr_name());
			}
		}
	}

	/**
	 * @author Liu,Jia
	 * @desc 全店优惠功能的判断
	 */
	protected void getDiscountStoreList(List<CartInfo> cartInfoList, Integer user_id, HttpServletRequest request) {

		// 查询企业是否添加了优惠政策 去查询出对应的信息
		// 第一层过滤掉购物车中商品是否被包含在打折信息中

		Date date2 = new Date();
		String nowDate = sdFormat_ymdhms.format(date2);
		DiscountStores discountStores = new DiscountStores();
		discountStores.setIs_del(0);
		discountStores.setDiscount_type(1);
		discountStores.setEntp_id(cartInfoList.get(0).getEntp_id());
		discountStores.getMap().put("now_date", nowDate);
		discountStores.getMap().put("time_gift_inventory_gt_0", true);
		List<DiscountStores> discountStoresList = super.getFacade().getDiscountStoresService()
				.getDiscountStoresList(discountStores);

		List<DiscountStores> _discountStoresList = new ArrayList<DiscountStores>();
		if (null != discountStoresList && discountStoresList.size() > 0) {// 取出来多个
			for (DiscountStores ds : discountStoresList) {
				List<Integer> comm_ids = new ArrayList<Integer>();
				for (CartInfo tempCart : cartInfoList) {
					Boolean canDis = true;

					// if (ds.getDiscount_comm_type() != 1) {// 证明选择了所有的商品 全店优惠
					// }

					String[] cls_ids = StringUtils.splitByWholeSeparator(ds.getCls_ids(), ",");
					if (ds.getDiscount_comm_type() == 2) {// 全店部门商品
						if (StringUtils.isNotBlank(ds.getDiscount_comm_ids())) {// 如果选择商品 证明只有这些选择的商品被包含
							// 循环商品是否被添加在这个打折信息中
							String[] Discount_comm_ids = StringUtils.splitByWholeSeparator(ds.getDiscount_comm_ids(),
									",");
							if (null != Discount_comm_ids && Discount_comm_ids.length > 0) {
								if (!ArrayUtils.contains(Discount_comm_ids, tempCart.getComm_id().toString())) {// 如果打折信息不包含该商品
									canDis = false;
									break;
								}
							}
						} else {// 如果没选择商品，根据类别来判断
							if (null != cls_ids && cls_ids.length > 0) {
								if (!ArrayUtils.contains(cls_ids, tempCart.getCls_id().toString())) {// 如果商品不被包含在该类别下面
									canDis = false;
									break;
								}
							}
						}
					} else if (ds.getDiscount_comm_type() == 3) {// 选择排除商品
						if (StringUtils.isNotBlank(ds.getDiscount_comm_ids())) {
							// 循环商品是否被添加在这个打折信息中
							String[] Discount_comm_ids = StringUtils.splitByWholeSeparator(ds.getDiscount_comm_ids(),
									",");
							if (null != Discount_comm_ids && Discount_comm_ids.length > 0) {
								if (ArrayUtils.contains(Discount_comm_ids, tempCart.getComm_id().toString())) {// 如果打折信息包含该商品
									canDis = false;
									break;
								}
							}
						} else {// 如果没选择商品，根据类别来判断
							if (null != cls_ids && cls_ids.length > 0) {
								if (ArrayUtils.contains(cls_ids, tempCart.getCls_id().toString())) {// 如果商品被包含在该类别下面
									canDis = false;
									break;
								}
							}
						}
					}

					if (canDis) {
						comm_ids.add(tempCart.getComm_id());
					}

				}

				BigDecimal totalMoney = new BigDecimal("0");
				Integer totalCount = 0;

				if (comm_ids.size() > 0) { // 证明这些商品满足该优惠条件
					for (Integer tempCommIds : comm_ids) {

						CartInfo getTotalMoneyAndCount = new CartInfo();
						getTotalMoneyAndCount.setIs_del(0);
						getTotalMoneyAndCount.setUser_id(user_id);
						getTotalMoneyAndCount.setComm_id(tempCommIds);
						getTotalMoneyAndCount = super.getFacade().getCartInfoService()
								.getCartInfoCountForSumCount(getTotalMoneyAndCount);
						if (null != getTotalMoneyAndCount) {
							totalMoney = totalMoney.add(new BigDecimal(getTotalMoneyAndCount.getMap().get("sumPdPrice")
									.toString()));
							totalCount = totalCount
									+ Integer.valueOf(getTotalMoneyAndCount.getMap().get("sumPdCount").toString());
						}
					}

					Integer discount_tj = ds.getDiscount_tj_content();

					if (ds.getDiscount_tj() == 1) {// 属于满多少元

						if (totalMoney.compareTo(new BigDecimal(discount_tj)) >= 0) {
							_discountStoresList.add(ds);
						}
					} else if (ds.getDiscount_tj() == 2) {// 属于满多少件
						if (totalCount >= discount_tj) {
							_discountStoresList.add(ds);
						}
					}

				}

			}
		}

		List<DiscountStores> discountStoresListNoContBy = new ArrayList<DiscountStores>();

		Set<DiscountStores> _discountStoresListSet = new HashSet<DiscountStores>();
		_discountStoresListSet.addAll(_discountStoresList);
		_discountStoresList.clear();
		_discountStoresList.addAll(_discountStoresListSet);
		if (_discountStoresList.size() > 0) {
			for (DiscountStores temp : _discountStoresList) {
				if (temp.getDiscount_method() == 1) {
					// 查询赠送的商品
					if (null != temp.getDiscount_type_content()) {
						CommInfo commInfoZp = super.getCommInfo(Integer.valueOf(temp.getDiscount_type_content()),
								Keys.CommType.COMM_TYPE_4.getIndex());
						if (null != commInfoZp) {
							temp.getMap().put("comm_name", commInfoZp.getComm_name());
						}
					}
					discountStoresListNoContBy.add(temp);
				}
			}
		}

		request.setAttribute("discountStoresListNoContBy", discountStoresListNoContBy);

		if (discountStoresListNoContBy.size() > 0) {
			request.setAttribute("qdyh_id", discountStoresListNoContBy.get(0).getId());
		}
	}

	/**
	 * @author Liu,Jia
	 * @desc 计算该订单的利润以及利润率
	 */
	protected void calThisOrderprofitMoney(OrderInfo orderInfo, BigDecimal order_money, BigDecimal order_cost_money) {
		BigDecimal profit_money = order_money.subtract(order_cost_money);
		BigDecimal profit_bl = profit_money.divide(order_money, 10, BigDecimal.ROUND_HALF_UP).multiply(
				new BigDecimal(100));
		orderInfo.setProfit_money(profit_money);
		orderInfo.setProfit_bl(profit_bl);
	}

	/**
	 * @author D&M
	 * @desc 计算订单数量,订单状态大于0（预定）小于40（已完成）（订单状态:OrderState，订单添加人ID:user_id，订单类型:OrderType，商家ID:entp_id）
	 */
	public Integer getOrderInfoNum(Integer OrderState, Integer user_id, Integer OrderType, Integer entp_id,
			Integer is_comment) {
		OrderInfo orderInfo = new OrderInfo();
		if (user_id != null) {
			orderInfo.setAdd_user_id(user_id);
		}
		if (OrderState != null) {
			orderInfo.setOrder_state(OrderState);
		} else {
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
		}
		if (OrderType != null) {
			orderInfo.setOrder_type(OrderType);
		}
		if (entp_id != null) {
			orderInfo.setEntp_id(entp_id);
		}
		if (null != is_comment) {
			orderInfo.setIs_comment(is_comment);
		}
		orderInfo.getMap().put(
				"order_type_in",
				Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_7.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_30.getIndex() + "," + Keys.OrderType.ORDER_TYPE_100.getIndex());
		// orderInfo.getMap().put("order_state_ge", Keys.OrderState.ORDER_STATE_10.getIndex());
		// orderInfo.getMap().put("order_state_le", Keys.OrderState.ORDER_STATE_15.getIndex());
		Integer recordCount = getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		return recordCount;
	}

	public Integer getOrderInfoNum(Integer OrderState, Integer user_id, Integer OrderType, Integer entp_id) {
		return getOrderInfoNum(OrderState, user_id, OrderType, entp_id, null);
	}

	public NewsInfo getNewsInfoByModIdAndEntpId(String mod_id, Integer entp_id) {
		NewsInfo entity = new NewsInfo();
		entity.setMod_id(mod_id);
		entity.getMap().put("is_pub", "0");
		entity.getMap().put("no_invalid", "no_invalid_date");
		entity.setIs_del(0);
		entity.setEntp_id(entp_id);
		entity = getFacade().getNewsInfoService().getNewsInfo(entity);
		return entity;
	}

	public JSONObject getJdProductStocks(HttpServletResponse response, String json) throws Exception {
		String info = "";
		/*
		 * try { HttpClient httpclient = new HttpClient(); PostMethod post = new
		 * PostMethod("http://jd.weite.xcfngc.cn/api/products/getstock");
		 * post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		 * post.setRequestHeader("Content-type", "application/json; charset=utf-8"); post.setRequestHeader("appkey",
		 * Keys.jd_yuan_appkey); String token = JdApiUtil.getToken(); post.setRequestHeader("token", token);
		 * post.setRequestBody(json); httpclient.executeMethod(post); info = new String(post.getResponseBody(),
		 * "utf-8"); } catch (HttpException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		logger.info("======getJdProductStocks=====请求参数: " + json);
		info = JdApiUtil.getJdProductStocks(json);// 获取京东商品库存
		logger.info("======getJdProductStocks=====响应结果: " + info);
		return JSON.parseObject(info);
	}

	public JSONObject getJdProductFreight(String json) throws Exception {
		String info = "";
		/*
		 * try { HttpClient httpclient = new HttpClient(); PostMethod post = new
		 * PostMethod("http://jd.weite.xcfngc.cn/api/products/getstock");
		 * post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		 * post.setRequestHeader("Content-type", "application/json; charset=utf-8"); post.setRequestHeader("appkey",
		 * Keys.jd_yuan_appkey); String token = JdApiUtil.getToken(); post.setRequestHeader("token", token);
		 * post.setRequestBody(json); httpclient.executeMethod(post); info = new String(post.getResponseBody(),
		 * "utf-8"); } catch (HttpException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */
		logger.info("======getJdProductFreight=====请求参数: " + json);
		info = JdApiUtil.getJdProductFreight(json);// 获取京东商品运费
		logger.info("======getJdProductFreight=====响应结果: " + info);
		return JSON.parseObject(info);

	}

	/**
	 * 判断京东商品价格是否与第三方接口系统一致
	 */
	public String judgeJdProductPrice(String sku) {
		String flag = "0";
		String info = "";
		/*
		 * HttpClient httpclient = new HttpClient(); GetMethod get = new GetMethod("http://api.jd.yuan.cn/product/sku/"
		 * + sku); get.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		 * get.setRequestHeader("Content-type", "application/json; charset=utf-8");
		 * get.setRequestHeader("X-YUAN-APPKEY", Keys.jd_yuan_appkey); httpclient.executeMethod(get);
		 */
		info = JdApiUtil.getJdProductInfo(Integer.valueOf(sku));// 获取京东商品信息
		if (StringUtils.isNotBlank(info)) {
			JSONObject obj = JSONObject.parseObject(info);
			if (null != obj && null != obj.get("StatusCode")) {
				if (Keys.JD_API_RESULT_STATUS_CODE.equals(obj.get("StatusCode").toString())) {// 接口成功
					String result_str = JSONObject.toJSONString(obj.get("Data"));
					JSONObject result = JSONObject.parseObject(result_str);
					if (null != result && "1".equals(result.getString("state"))) {
						String sale_price = null != result.get("jdprice") ? result.get("jdprice").toString() : "";
						String jd_agent_price = null != result.get("price") ? result.get("price").toString() : "";

						CommInfo commInfo = new CommInfo();
						commInfo.setJd_skuid(sku);
						commInfo.setIs_del(0);
						commInfo.setAudit_state(1);
						commInfo = this.getFacade().getCommInfoService().getCommInfo(commInfo);

						logger.warn("=========judgeJdProductPrice:sku=========" + sku);
						logger.warn("=========judgeJdProductPrice:sale_price=========" + sale_price);
						logger.warn("=========judgeJdProductPrice:jd_agent_price=========" + jd_agent_price);
						logger.warn("=========judgeJdProductPrice:commInfo.getSale_price()========="
								+ commInfo.getSale_price());
						logger.warn("=========judgeJdProductPrice:commInfo.getJd_agent_price()========="
								+ commInfo.getJd_agent_price());
						if (commInfo.getSale_price().compareTo(new BigDecimal(sale_price)) == 0
								&& commInfo.getJd_agent_price().compareTo(new BigDecimal(jd_agent_price)) == 0) {// 京东商品价格与第三方接口一致
							flag = "1";
							logger.warn("=========judgeJdProductPrice:money identical=========");
						} else {// 京东商品价格与第三方接口不一致，则更新价格
							CommInfo commInfoUpdate = new CommInfo();
							commInfoUpdate.setId(commInfo.getId());
							commInfoUpdate.setJd_agent_price(new BigDecimal(jd_agent_price));
							commInfoUpdate.setSale_price(new BigDecimal(sale_price));
							commInfoUpdate.setInventory(999);
							commInfoUpdate.setUpdate_date(new Date());

							// 返点大于6%的商品才上架、返点大于6%的商品才返现
							// 京东价*94%-京东给我们的价
							BigDecimal rate = commInfoUpdate.getSale_price()
									.subtract(commInfoUpdate.getJd_agent_price())
									.divide(commInfoUpdate.getSale_price(), 2, BigDecimal.ROUND_DOWN);
							if (rate.compareTo(new BigDecimal("0.06")) == 1) {
								commInfoUpdate.setIs_sell(1);
								commInfoUpdate.setUp_date(new Date());
								Calendar cal = Calendar.getInstance();
								cal.setTime(new Date());
								cal.add(Calendar.YEAR, 2);
								commInfoUpdate.setDown_date(cal.getTime());
								commInfoUpdate.setIs_rebate(1);
								BigDecimal rebate_scale = commInfoUpdate.getSale_price()
										.multiply(new BigDecimal("0.94")).subtract(commInfoUpdate.getJd_agent_price())
										.divide(commInfoUpdate.getSale_price(), 4, BigDecimal.ROUND_DOWN)
										.multiply(new BigDecimal(100));
								if (rebate_scale.compareTo(new BigDecimal(0)) == 1) {
									commInfoUpdate.setRebate_scale(rebate_scale);
									commInfoUpdate.setMultiple_order_value(rebate_scale.multiply(
											new BigDecimal(100 * 65)).intValue());
								}
							} else {
								commInfoUpdate.setIs_sell(0);
								commInfoUpdate.setMultiple_order_value(0);
							}

							// 套餐同步更新
							CommTczhPrice commTczhPrice = new CommTczhPrice();
							commTczhPrice.setComm_id(String.valueOf(commInfoUpdate.getId()));
							commTczhPrice.setComm_price(commInfoUpdate.getSale_price());
							commTczhPrice.setInventory(999);
							commTczhPrice.setUpdate_date(new Date());

							commInfoUpdate.setCommTczhPrice(commTczhPrice);
							commInfoUpdate.getMap().put("update_comm_tczh_price_by_comm_id", "true");

							getFacade().getCommInfoService().modifyCommInfo(commInfoUpdate);

							// 更新购物车京东商品价格
							CartInfo cartInfo = new CartInfo();
							cartInfo.getMap().put("comm_id", commInfoUpdate.getId());
							cartInfo.setPd_price(commInfoUpdate.getSale_price());
							getFacade().getCartInfoService().modifyCartInfo(cartInfo);
						}
					}
				}
			}
		}

		return flag;
	}

	/**
	 * @author libaiqiang
	 * @version 2019-3-18
	 * @desc 获取导航
	 */
	public Map<String, List<BaseLink>> common(Integer type) throws Exception {
		BaseLink b = new BaseLink();
		Map<String, List<BaseLink>> map = new HashMap<String, List<BaseLink>>();
		b.setLink_type(type);
		b.setIs_del(0);
		List<BaseLink> baseLinkList = this.getFacade().getBaseLinkService().getBaseLinkList(b);
		if (null == baseLinkList) {
			return null;
		}
		map.put("baseLinkList", baseLinkList);
		return map;
	}

}
