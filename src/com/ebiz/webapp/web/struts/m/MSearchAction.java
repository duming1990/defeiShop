package com.ebiz.webapp.web.struts.m;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.WebUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.MBaseLink;
import com.ebiz.webapp.domain.MServiceBaseLink;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 * @version 2011-12-14
 */
public class MSearchAction extends MBaseWebAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.search(mapping, form, request, response);
	}

	public ActionForward search(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		boolean is_app = super.isApp(request);
		if (is_app) {
			super.setIsAppToCookie(request, response);
		}
		String ctx = super.getCtxPath(request);

		DynaBean dynaBean = (DynaBean) form;
		String htype = (String) dynaBean.get("htype");
		String keyword = (String) dynaBean.get("keyword");
		log.info("==keyword:" + keyword);
		if ("0".equals(htype)) {
			return this.listPd(mapping, form, request, response);
		}
		if ("1".equals(htype)) {
			return this.listEntp(mapping, form, request, response);
		}
		// 县域
		if ("2".equals(htype)) {
			String url = ctx + "/m/MServiceCenterInfo.do?servicecenter_name_like=" + keyword;
			response.sendRedirect(url);
			return null;
		}
		// 村子
		if ("3".equals(htype)) {
			String url = ctx + "/m/MVillage.do?village_name_like=" + keyword;
			response.sendRedirect(url);
			return null;
		}

		super.setBaseDataListToSession(30, request);// 热门搜索
		if (StringUtils.isBlank(htype)) {
			htype = "0";// 产品
		}
		dynaBean.set("htype", htype);

		return mapping.findForward("list");
	}

	// 产品
	public ActionForward listPd(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String keyword = (String) dynaBean.get("keyword");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String p_index = (String) dynaBean.get("p_index");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String hdtype = (String) dynaBean.get("hdtype");
		String is_zingying = (String) dynaBean.get("is_zingying");
		String is_aid = (String) dynaBean.get("is_aid");
		String id = (String) dynaBean.get("id");
		log.info("===is_aid:" + is_aid);
		// 判断市域馆跟县域馆是否维护
		System.out.println();
		if (StringUtils.isNotBlank(p_index)) {// 判断是否是市域
			UserInfo userInfo = new UserInfo();
			userInfo.setP_index(Integer.parseInt(p_index));
			userInfo.setUser_type(19);
			userInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			int count = super.getFacade().getUserInfoService().getUserInfoCount(userInfo);
			if (count == 1) {
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				if (userInfo != null) {
					MServiceBaseLink msbl = new MServiceBaseLink();
					msbl.setLink_id(userInfo.getId());
					msbl.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
					msbl.setLink_type(10);
					msbl.setMain_type(20);
					int msbl_count = super.getFacade().getMServiceBaseLinkService().getMServiceBaseLinkCount(msbl);
					if (msbl_count > 0) {
						return new ActionForward("/IndexMTsg.do?method=index&link_id=" + userInfo.getId());
					}
				}
			}
		}
		if (StringUtils.isNotBlank(id)) {// 判断是否是县域
			MServiceBaseLink msbl = new MServiceBaseLink();
			msbl.setLink_id(Integer.parseInt(id));
			msbl.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			msbl.setLink_type(10);
			msbl.setMain_type(10);
			int msbl_count = super.getFacade().getMServiceBaseLinkService().getMServiceBaseLinkCount(msbl);
			if (msbl_count > 0) {
				return new ActionForward("/IndexMTsg.do?method=index&link_id=" + id);
			}
		}
		// 判断县域馆是否维护
		UserInfo ui = super.getUserInfoFromSession(request);
		if (ui != null) {
			UserInfo userInfo = new UserInfo();
			userInfo.setId(ui.getId());
			userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
			MServiceBaseLink mServideBaseLink = new MServiceBaseLink();
			mServideBaseLink.setAdd_uid(ui.getId());
			mServideBaseLink.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
			List<MServiceBaseLink> list = super.getFacade().getMServiceBaseLinkService()
					.getMServiceBaseLinkList(mServideBaseLink);
			ServiceCenterInfo entity = new ServiceCenterInfo();
			entity.setAdd_user_id(ui.getId());
			entity = super.getFacade().getServiceCenterInfoService().getServiceCenterInfo(entity);
			if (list != null && list.size() > 0 && entity != null) {
				return new ActionForward("/IndexMTsg.do?method=index&link_id=" + entity.getId());
			}
		}
		Pager pager = (Pager) dynaBean.get("pager");

		String searchScope = (String) dynaBean.get("searchScope");
		request.setAttribute("searchScope", searchScope);

		List<CommInfo> entityList = new ArrayList<CommInfo>();
		if (Keys.app_cls_level.equals("3")) {

			if (StringUtils.isNotBlank(hdtype) && hdtype.equals("0")) { // 获取活动
				entityList = super.getCommInfoHdList(12, false, null, false, false, orderByParam, true, pager, 12,
						keyword, p_index, root_cls_id, par_cls_id, null, null);
			} else {
				entityList = super.getCommInfoList(null, false, null, false, false, orderByParam, true, pager, null,
						keyword, p_index, root_cls_id, par_cls_id, null, is_zingying, is_aid, null);
			}
		}
		if (Keys.app_cls_level.equals("2")) {
			entityList = super.getCommInfoList(null, false, null, false, false, orderByParam, true, pager, null,
					keyword, p_index, null, root_cls_id, par_cls_id, is_zingying, is_aid, null);
		}

		request.setAttribute("entityList", entityList);
		Integer pageSize = 4;
		if (entityList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}

		if (StringUtils.isNotBlank(keyword)) {
			request.setAttribute("keyWordNavg", "共找到" + entityList.size() + "条<span>" + "“" + keyword + "”</span>"
					+ "相关结果");
		} else {
			// request.setAttribute("keyWordNavg", "搜索全部的信息");
		}

		String city = Keys.P_INDEX_CITY;
		if (GenericValidator.isInt(p_index)) {
			city = p_index;
		}
		super.getBaseProvinceCityList(request, "sonBaseProList", Integer.valueOf(city));

		String cls_name = "全部分类";
		if (GenericValidator.isInt(par_cls_id)) {
			BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(par_cls_id));
			if (null != parClassTemp) {
				cls_name = parClassTemp.getCls_name();
			}
		}
		if (GenericValidator.isInt(root_cls_id)) {
			BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(root_cls_id));
			if (null != parClassTemp) {
				cls_name = parClassTemp.getCls_name();
			}
		}
		request.setAttribute("cls_name", cls_name);

		dynaBean.set("pageSize", pageSize);
		dynaBean.set("htype", "0");

		super.setSlideNavForM(request);

		if (StringUtils.isNotBlank(root_cls_id)) {

			MBaseLink blk = new MBaseLink();
			blk.setLink_type(30);
			blk.setIs_del(0);
			blk.setContent(root_cls_id);
			blk.getRow().setCount(1);
			List<MBaseLink> baseLink20List = getFacade().getMBaseLinkService().getMBaseLinkList(blk);
			for (MBaseLink bi : baseLink20List) {
				if (null != bi.getContent() && GenericValidator.isInt(bi.getContent())) {
					Integer c_id = Integer.valueOf(bi.getContent());
					BaseClass baseClassSon = new BaseClass();
					baseClassSon.setPar_id(c_id);
					baseClassSon.setIs_del(0);
					baseClassSon.setIs_show(0);
					List<BaseClass> baseClassSonList = getFacade().getBaseClassService().getBaseClassList(baseClassSon);
					if (null != baseClassSonList && baseClassSonList.size() > 0) {
						for (BaseClass temp : baseClassSonList) {
							BaseClass baseClassSonSon = new BaseClass();
							baseClassSonSon.setPar_id(temp.getCls_id());
							baseClassSonSon.setIs_del(0);
							baseClassSonSon.setIs_show(0);
							List<BaseClass> baseClassSonSonList = getFacade().getBaseClassService().getBaseClassList(
									baseClassSonSon);
							temp.getMap().put("baseClassSonSonList", baseClassSonSonList);
						}
					}
					bi.setBaseClassList(baseClassSonList);

				}
			}
			request.setAttribute("slideNavList", baseLink20List);
		}

		request.setAttribute("commZyTypeList", Keys.CommZyType.values());

		return new ActionForward("/MSearch/list_pd.jsp");
	}

	public ActionForward getPdListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		DynaBean dynaBean = (DynaBean) form;
		String msg = "", code = "0";
		String keyword = (String) dynaBean.get("keyword");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String p_index = (String) dynaBean.get("p_index");
		String hdtype = (String) dynaBean.get("hdtype");
		String isIndex = (String) dynaBean.get("isIndex");
		String is_zingying = (String) dynaBean.get("is_zingying");
		String is_aid = (String) dynaBean.get("is_aid");
		String commType = (String) dynaBean.get("commType");
		log.info("===is_aid:" + is_aid);

		if (StringUtils.isBlank(commType)) {
			commType = String.valueOf(Keys.CommType.COMM_TYPE_2.getIndex());
		}

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		logger.info("=startPage={}", startPage);
		logger.info("=pageSize={}", pageSize);

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "6";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}
		List<CommInfo> entityList = new ArrayList<CommInfo>();
		if (Keys.app_cls_level.equals("3")) {

			if (StringUtils.isNotBlank(hdtype) && hdtype.equals("0")) { // 获取活动
				entityList = super.getCommInfoListForJson(null, false, null, false, false, orderByParam, true, pager,
						null, keyword, p_index, root_cls_id, par_cls_id, null, Integer.valueOf(startPage), true,
						is_zingying, is_aid, null, null);
			} else {
				entityList = super.getCommInfoListForJson(null, false, null, false, false, orderByParam, true, pager,
						null, keyword, p_index, root_cls_id, par_cls_id, null, Integer.valueOf(startPage), false,
						is_zingying, is_aid, null, Integer.valueOf(commType));
			}

		} else if (Keys.app_cls_level.equals("2")) {
			entityList = super.getCommInfoListForJson(null, false, null, false, false, orderByParam, true, pager, null,
					keyword, p_index, null, root_cls_id, par_cls_id, Integer.valueOf(startPage), false, is_zingying,
					is_aid, null, null);
		}

		String ctx = super.getCtxPath(request);
		if ((null != entityList) && (entityList.size() > 0)) {
			code = "1";
			if (StringUtils.isBlank(isIndex)) {
				for (CommInfo b : entityList) {
					JSONObject map = new JSONObject();
					map.put("id", b.getId());
					String main_pic = b.getMain_pic();
					map.put("comm_name", b.getComm_name());
					map.put("commZyName", b.getMap().get("commZyName"));
					if (StringUtils.isBlank(main_pic)) {
						map.put("main_pic", "/styles/imagesPublic/no_image.jpg");
						map.put("main_pic_400", "/styles/imagesPublic/no_image.jpg");
					} else {
						String min_img = StringUtils.substringBeforeLast(main_pic, ".") + "_400."
								+ FilenameUtils.getExtension(main_pic);
						map.put("main_pic", ctx.concat("/").concat(main_pic));
						map.put("main_pic_400", ctx.concat("/").concat(min_img));
					}
					map.put("sale_price", dfFormat.format(b.getSale_price()));
					long sale_count = b.getSale_count_update();
					if (sale_count > 10000) {
						Double sc = Double.valueOf(sale_count) / 10000;
						map.put("sale_count", dfFormat.format(sc) + "万");
					} else {
						map.put("sale_count", sale_count);
					}
					dataLoadList.add(map);
				}
			} else {
				dataLoadList.addAll(entityList);
			}

		}
		datas.put("dataList", dataLoadList);
		msg = "加载完成";

		if (dataLoadList.size() < 2) {
			code = "2";
			msg = "没有更多数据";
		}
		log.info("getCommInfoList");
		super.returnInfo(response, code, msg, datas);
		return null;

	}

	public ActionForward listEntp(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		log.info("===listEntp===");
		// super.setPublicInfoWithSearchList(request);
		// super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String p_index = (String) dynaBean.get("p_index");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String son_cls_id = (String) dynaBean.get("son_cls_id");
		String keyword = (String) dynaBean.get("keyword");
		String is_lianmeng = (String) dynaBean.get("is_lianmeng");
		String orderByParam = (String) dynaBean.get("orderByParam");

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);

		if (null == current_p_index) {
			setPindexCookies(response, Keys.QUANGUO_P_INDEX, Keys.QUANGUO_P_NAME);
		} else {
			if (StringUtils.isBlank(p_index)) {
				p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
			}
		}

		String city = Keys.P_INDEX_CITY;
		if (null != current_p_index) {
			city = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}
		super.getBaseProvinceCityList(request, "sonBaseProList", Integer.valueOf(city));

		String cls_name = "全部分类";

		Integer cls_scope = 1;

		if (GenericValidator.isInt(son_cls_id)) {
			BaseClass sonClassTemp = super.getBaseClass(Integer.valueOf(son_cls_id));
			if (null != sonClassTemp) {
				cls_name = sonClassTemp.getCls_name();
			}
		} else if (GenericValidator.isInt(par_cls_id)) {
			BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(par_cls_id));
			if (null != parClassTemp) {
				cls_name = parClassTemp.getCls_name();
			}
		}
		request.setAttribute("cls_name", cls_name);
		super.setSlideNavForM(request);

		dynaBean.set("htype", "1");

		if (StringUtils.isNotBlank(par_cls_id)) {

			MBaseLink blk = new MBaseLink();
			blk.setLink_type(300);
			blk.setIs_del(0);
			blk.setContent(par_cls_id);
			blk.getRow().setCount(1);
			List<MBaseLink> baseLink20List = getFacade().getMBaseLinkService().getMBaseLinkList(blk);
			for (MBaseLink bi : baseLink20List) {
				if (null != bi.getContent() && GenericValidator.isInt(bi.getContent())) {
					Integer c_id = Integer.valueOf(bi.getContent());
					BaseClass baseClassSon = new BaseClass();
					baseClassSon.setPar_id(c_id);
					baseClassSon.setIs_del(0);
					baseClassSon.setIs_show(0);
					List<BaseClass> baseClassSonList = getFacade().getBaseClassService().getBaseClassList(baseClassSon);
					if (null != baseClassSonList && baseClassSonList.size() > 0) {
						for (BaseClass temp : baseClassSonList) {
							BaseClass baseClassSonSon = new BaseClass();
							baseClassSonSon.setPar_id(temp.getCls_id());
							baseClassSonSon.setIs_del(0);
							baseClassSonSon.setIs_show(0);
							List<BaseClass> baseClassSonSonList = getFacade().getBaseClassService().getBaseClassList(
									baseClassSonSon);
							temp.getMap().put("baseClassSonSonList", baseClassSonSonList);
						}
					}
					bi.setBaseClassList(baseClassSonList);
				}
			}
			request.setAttribute("slideNavList", baseLink20List);
		}

		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setAudit_state(2);
		if (StringUtils.isNotBlank(is_lianmeng)) {
			entity.setIs_lianmeng(1);
		}

		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
				p_index = null;
			}
		}
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.endsWith("0000")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 2));
			} else if (p_index.endsWith("00")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 4));
			} else {
				entity.setP_index(Integer.valueOf(p_index));
			}
		}
		if (GenericValidator.isInt(par_cls_id))
			entity.setHy_cls_id(Integer.valueOf(par_cls_id));
		if (StringUtils.isNotBlank(son_cls_id))
			entity.getMap().put("main_pd_class_ids_like", son_cls_id);

		// if (GenericValidator.isInt(par_cls_id) && StringUtils.isBlank(xianxiaEntp)) {
		// entity.setHy_cls_id(Integer.valueOf(par_cls_id));
		// }
		// if (StringUtils.isNotBlank(son_cls_id) && StringUtils.isBlank(xianxiaEntp)) {
		// entity.getMap().put("main_pd_class_ids_like", son_cls_id);
		// }
		// if (StringUtils.isNotBlank(xianxiaEntp)) {
		// if (GenericValidator.isInt(par_cls_id) && StringUtils.isBlank(xianxiaEntp)) {
		// entity.getMap().put("xianxia_pd_class_ids_like", par_cls_id);
		// }
		// if (StringUtils.isNotBlank(son_cls_id)) {
		// entity.getMap().put("xianxia_pd_class_ids_like", son_cls_id);
		// }
		//
		// }

		if (StringUtils.isNotBlank(keyword))
			entity.getMap().put("entp_name_like", keyword);

		entity.setIs_show(1);

		entity.getMap().put(orderByParam, orderByParam);

		// if (orderByParam.equals("orderBydistanceDesc")) {
		// entity.getMap().put("orderBydistanceDesc", true);
		// } else {
		// entity.getMap().put(orderByParam, orderByParam);
		// }

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entpInfoList = new ArrayList<EntpInfo>();

		//
		// if (StringUtils.isNotBlank(x) && StringUtils.isNotBlank(y)) {
		// logger.info("entity.getMap().put(x, x);:" + x);
		// logger.info("entity.getMap().put(y, y);:" + y);
		// Point point = getXY(x, y);
		//
		// if (StringUtils.isNotBlank(String.valueOf(point.getLat()))) {
		// logger.info("xxxxx:" + point.getLat());
		// entity.getMap().put("x", point.getLat());
		// }
		// if (StringUtils.isNotBlank(String.valueOf(point.getLng()))) {
		// logger.info("yyyyy:" + point.getLng());
		// entity.getMap().put("y", point.getLng());
		// }
		// entpInfoList = getFacade().getEntpInfoService().getEntpInfoDistance(entity);
		// } else {
		log.info("===entpInfoList===");
		entpInfoList = getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		// }
		//
		request.setAttribute("entpInfoList", entpInfoList);

		Integer pageSize = 10;
		if (entpInfoList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}

		return new ActionForward("/MSearch/list_entp.jsp");
	}

	public ActionForward listEntpXianxia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		String p_index = (String) dynaBean.get("p_index");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String son_cls_id = (String) dynaBean.get("son_cls_id");
		String keyword = (String) dynaBean.get("keyword");
		String is_lianmeng = (String) dynaBean.get("is_lianmeng");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String xianxiaEntp = (String) dynaBean.get("xianxiaEntp");
		String x = (String) dynaBean.get("x");
		String y = (String) dynaBean.get("y");

		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setAudit_state(2);
		if (StringUtils.isNotBlank(is_lianmeng)) {
			entity.setIs_lianmeng(1);
		}
		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);

		if (null == current_p_index) {
			setPindexCookies(response, Keys.QUANGUO_P_INDEX, Keys.QUANGUO_P_NAME);
		} else {
			if (StringUtils.isBlank(p_index)) {
				p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
			}
		}
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
				p_index = null;
			}
		}
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.endsWith("0000")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 2));
			} else if (p_index.endsWith("00")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 4));
			} else {
				entity.setP_index(Integer.valueOf(p_index));
			}
		}
		BaseClass a = getBaseClass(Integer.valueOf(par_cls_id));
		if (null != a) {
			if (a.getCls_level().intValue() == 2) {
				// 二级 要查三级
				entity.getMap().put("xianxia_pd_class_ids_like", a.getCls_id());
			}
			if (a.getCls_level().intValue() == 3) {
				// 二级 要查三级
				entity.getMap().put("xianxia_pd_class_ids_like", a.getCls_id());
			}
		}

		// 三级
		if (StringUtils.isNotBlank(son_cls_id)) {
			entity.getMap().put("xianxia_pd_class_ids_like", son_cls_id);
		}

		if (StringUtils.isNotBlank(keyword))
			entity.getMap().put("entp_name_like", keyword);

		if (orderByParam.equals("orderBydistanceDesc")) {
			entity.getMap().put("orderBydistanceDesc", true);
		} else {
			entity.getMap().put(orderByParam, orderByParam);
		}

		entity.setIs_show(1);

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entpInfoList = new ArrayList<EntpInfo>();

		if (StringUtils.isNotBlank(x) && StringUtils.isNotBlank(y)) {
			entity.getMap().put("x", x);
			entity.getMap().put("y", y);
			entpInfoList = getFacade().getEntpInfoService().getEntpInfoDistance(entity);
		} else {
			entpInfoList = getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);
		}

		request.setAttribute("entpInfoList", entpInfoList);

		Integer pageSize = 10;
		if (entpInfoList.size() == Integer.valueOf(pageSize)) {
			request.setAttribute("appendMore", 1);
		}

		String city = Keys.P_INDEX_CITY;
		if (null != current_p_index) {
			city = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}
		super.getBaseProvinceCityList(request, "sonBaseProList", Integer.valueOf(city));

		String cls_name = "全部分类";

		Integer cls_scope = 1;

		if (GenericValidator.isInt(son_cls_id)) {
			BaseClass sonClassTemp = super.getBaseClass(Integer.valueOf(son_cls_id), cls_scope);
			if (null != sonClassTemp) {
				cls_name = sonClassTemp.getCls_name();
			}
		} else if (GenericValidator.isInt(par_cls_id)) {
			BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(par_cls_id), cls_scope);
			if (null != parClassTemp) {
				cls_name = parClassTemp.getCls_name();
			}
		}
		request.setAttribute("cls_name", cls_name);
		super.setSlideNavForM(request);

		dynaBean.set("htype", "1");

		if (StringUtils.isNotBlank(xianxiaEntp)) {

			cls_scope = Keys.CLS_SCOPE_TYPE.CLS_SCOPE_2.getIndex();

			MBaseLink blk = new MBaseLink();
			blk.setLink_type(300);
			blk.setIs_del(0);
			blk.getRow().setCount(16);
			List<MBaseLink> baseLink20List = getFacade().getMBaseLinkService().getMBaseLinkList(blk);
			for (MBaseLink bi : baseLink20List) {
				if (null != bi.getContent() && GenericValidator.isInt(bi.getContent())) {
					Integer c_id = Integer.valueOf(bi.getContent());
					BaseClass baseClassSon = new BaseClass();
					baseClassSon.setPar_id(c_id);
					baseClassSon.setIs_del(0);
					baseClassSon.setIs_show(0);
					List<BaseClass> baseClassSonList = getFacade().getBaseClassService().getBaseClassList(baseClassSon);
					if (null != baseClassSonList && baseClassSonList.size() > 0) {
						for (BaseClass temp : baseClassSonList) {
							BaseClass baseClassSonSon = new BaseClass();
							baseClassSonSon.setPar_id(temp.getCls_id());
							baseClassSonSon.setIs_del(0);
							baseClassSonSon.setIs_show(0);
							List<BaseClass> baseClassSonSonList = getFacade().getBaseClassService().getBaseClassList(
									baseClassSonSon);
							temp.getMap().put("baseClassSonSonList", baseClassSonSonList);
						}
					}
					bi.setBaseClassList(baseClassSonList);
				}
			}
			request.setAttribute("slideNavList", baseLink20List);
		}

		return new ActionForward("/MSearch/list_entp.jsp");
	}

	public ActionForward getEntpListJson(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String msg = "", code = "0";
		String keyword = (String) dynaBean.get("keyword");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String son_cls_id = (String) dynaBean.get("son_cls_id");
		String p_index = (String) dynaBean.get("p_index");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String xianxiaEntp = (String) dynaBean.get("xianxiaEntp");

		Pager pager = (Pager) dynaBean.get("pager");
		String startPage = (String) dynaBean.get("startPage");
		String pageSize = (String) dynaBean.get("pageSize");
		JSONObject datas = new JSONObject();
		JSONArray dataLoadList = new JSONArray();

		if (StringUtils.isBlank(pageSize)) {
			pageSize = "10";
		}
		if (StringUtils.isBlank(startPage)) {
			startPage = "0";
		}

		String is_lianmeng = (String) dynaBean.get("is_lianmeng");

		EntpInfo entity = new EntpInfo();
		entity.setIs_del(0);
		entity.setAudit_state(2);
		if (StringUtils.isNotBlank(is_lianmeng)) {
			entity.setIs_lianmeng(1);
		}
		if (StringUtils.isNotBlank(xianxiaEntp)) {
			// entity.getMap().put("xianxiaEntp", "true");
		}
		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		if (null == current_p_index) {
			setPindexCookies(response, Keys.QUANGUO_P_INDEX, Keys.QUANGUO_P_NAME);
		} else {
			if (StringUtils.isBlank(p_index)) {
				p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
			}
		}
		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
				p_index = null;
			}
		}

		if (StringUtils.isNotBlank(p_index)) {
			if (p_index.endsWith("0000")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 2));
			} else if (p_index.endsWith("00")) {
				entity.getMap().put("p_index_like", StringUtils.substring(p_index, 0, 4));
			} else {
				entity.setP_index(Integer.valueOf(p_index));
			}
		}
		if (StringUtils.isNotBlank(par_cls_id))
			entity.setHy_cls_id(Integer.valueOf(par_cls_id));
		if (StringUtils.isNotBlank(son_cls_id))
			entity.getMap().put("main_pd_class_ids_like", son_cls_id);

		if (StringUtils.isNotBlank(keyword))
			entity.getMap().put("entp_name_like", keyword);

		entity.getMap().put(orderByParam, orderByParam);

		entity.setIs_show(1);

		Integer recordCount = getFacade().getEntpInfoService().getEntpInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(Integer.valueOf(startPage) * Integer.valueOf(pageSize));
		entity.getRow().setCount(pager.getRowCount());

		List<EntpInfo> entpInfoList = new ArrayList<EntpInfo>();
		entpInfoList = getFacade().getEntpInfoService().getEntpInfoPaginatedList(entity);

		String ctx = super.getCtxPath(request);
		if ((null != entpInfoList) && (entpInfoList.size() > 0)) {
			code = "1";
			for (EntpInfo b : entpInfoList) {
				JSONObject map = new JSONObject();
				map.put("id", b.getId());
				String entp_logo = b.getEntp_logo();
				map.put("entp_name", b.getEntp_name());
				map.put("sum_sale_money", b.getSum_sale_money());
				map.put("entp_addr", b.getEntp_addr());
				map.put("entp_tel", b.getEntp_tel());
				if (null != b.getMap().get("distance")) {
					BigDecimal big_distance = super.getEntpDistance(b);
					map.put("distance", big_distance);
				}

				if (StringUtils.isBlank(entp_logo)) {
					map.put("entp_logo", "/styles/imagesPublic/no_image.jpg");
					map.put("entp_logo_400", "/styles/imagesPublic/no_image.jpg");
				} else {
					String min_img = StringUtils.substringBeforeLast(entp_logo, ".") + "_400."
							+ FilenameUtils.getExtension(entp_logo);
					map.put("entp_logo", ctx.concat("/").concat(entp_logo));
					map.put("entp_logo_400", ctx.concat("/").concat(min_img));
				}
				dataLoadList.add(map);
			}

		}
		datas.put("dataList", dataLoadList.toString());
		msg = "加载完成";

		if (dataLoadList.size() < 2) {
			code = "2";
			msg = "没有更多数据";
		}

		super.ajaxReturnInfo(response, code, msg, datas);
		return null;

	}
}
