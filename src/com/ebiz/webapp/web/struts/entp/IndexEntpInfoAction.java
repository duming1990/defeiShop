package com.ebiz.webapp.web.struts.entp;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.CookieGenerator;
import org.springframework.web.util.WebUtils;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseBrandInfo;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.EntpBaseLink;
import com.ebiz.webapp.domain.EntpCommClass;
import com.ebiz.webapp.domain.EntpContent;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.PdContent;
import com.ebiz.webapp.domain.ScEntpComm;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.Keys.CommZyType;

/**
 * @author Liu,Jia
 * @version 2012-4-24
 */
public class IndexEntpInfoAction extends BaseEntpAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");

		Boolean judgeIsMoblie = super.JudgeIsMoblie(request);
		if (judgeIsMoblie) {
			String ctx = super.getCtxPath(request);
			return new ActionForward(ctx + "/m/MEntpInfo.do?method=index&entp_id=" + entp_id, true);
		}

		return this.entpIndex(mapping, form, request, response);
	}

	/** 企业首页 */
	public ActionForward entpIndex(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");
		String Link_id = (String) dynaBean.get("Link_id");

		EntpInfo entpInfo = initPublic(mapping, form, request, entp_id, response);
		if (null == entpInfo) {
			return null;
		}
		EntpContent entpContent = new EntpContent();
		entpContent.setType(0);
		entpContent.setEntp_id(entpInfo.getId());
		entpContent = super.getFacade().getEntpContentService().getEntpContent(entpContent);
		if (null != entpContent) {
			dynaBean.set("entp_content", entpContent.getContent());
		}

		// BaseFiles entpPic = new BaseFiles();
		// entpPic.setIs_del(0);
		// entpPic.setType(Keys.BaseFilesType.Base_Files_TYPE_50.getIndex());
		// entpPic.setLink_id(entpInfo.getId());
		// entpPic.getRow().setCount(6);
		// List<BaseFiles> entpPicList = super.getFacade().getBaseFilesService().getBaseFilesList(entpPic);
		// request.setAttribute("entpPicList", entpPicList);
		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}
		
		

		List<CommInfo> commmInfoList = super.getCommInfoList(3, false, entpInfo.getId(), false, false, null, false,
				null, null, null, null, null, null, null, null, null, null);
		request.setAttribute("commmInfoList", commmInfoList);

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setEntp_id(entpInfo.getId());
		Integer entpInfoSaleCount = super.getFacade().getOrderInfoService().getOrderInfoCount(orderInfo);
		request.setAttribute("entpInfoSaleCount", entpInfoSaleCount);

		// 店铺推荐商品
		List<CommInfo> commmInfoTjList = super.getCommInfoList(6, true, entpInfo.getId(), true, false, null, false,
				null, null, null, null, null, null, null, null, null, null);
		request.setAttribute("commmInfoTjList", commmInfoTjList);

		// 更新浏览量
		EntpInfo ei = new EntpInfo();
		ei.setId(entpInfo.getId());
		ei.getMap().put("add_view_count", 1);// 浏览量+1
		super.getFacade().getEntpInfoService().modifyEntpInfo(ei);
		// 是否使用模板展示商品
		EntpInfo entity = new EntpInfo();
		if (StringUtils.isNotBlank(entp_id)) {
			entity.setId(Integer.valueOf(entp_id));
		}
		entity = super.getFacade().getEntpInfoService().getEntpInfo(entity);
		Integer link_id = Integer.valueOf(entp_id);
		if (entity.getIs_entpstyle().intValue() == 1) {
			return this.indexStyle(mapping, form, request, response, link_id);
		} else {
			return this.getCommList(mapping, form, request, response);
		}
	}

	public ActionForward subdomain(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		DynaBean dynaBean = (DynaBean) form;

		String custom_url = (String) dynaBean.get("custom_url");
		if (StringUtils.isBlank(custom_url)) {
			String msg = "传入的参数为空";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		EntpInfo entpInfo = new EntpInfo();
		entpInfo.setIs_del(0);
		entpInfo.setAudit_state(1);
		entpInfo.setCustom_url(custom_url);
		entpInfo = super.getFacade().getEntpInfoService().getEntpInfo(entpInfo);
		if (entpInfo == null) {
			String msg = "企业不存在";
			super.renderJavaScript(response, "alert('" + msg + "');history.back();");
			return null;
		}

		Boolean judgeIsMoblie = super.JudgeIsMoblie(request);
		if (judgeIsMoblie) {
			String ctx = super.getCtxPath(request);
			return new ActionForward(ctx + "/m/MEntpInfo.do?method=index&entp_id=" + entpInfo.getId(), true);
		}

		request.setAttribute("entpInfo", entpInfo);

		this.shareSubDomainSession(request, response);// session二级域名共享

		return this.indexStyle(mapping, form, request, response, entpInfo.getId());
	}

	public ActionForward indexStyle(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response, Integer link_id) throws Exception {
		List<EntpBaseLink> baseLink20List = this.getentpBaseLinkList(20, null, link_id, 10, "no_null_image_path");// 楼层
		List<EntpBaseLink> baseLink10List = this.getentpBaseLinkList(10, null, link_id, 10, "no_null_image_path");// 商品
		EntpBaseLink baseLinkBg = this.getEntpBaseLinkBg(30, link_id, 10, "no_null_image_path");// 背景
		if ((null != baseLink20List) && (baseLink20List.size() > 0)) {
			for (EntpBaseLink temp : baseLink20List) {

				if (temp.getPre_number() == 1) {
					List<EntpBaseLink> baseLink101List = this.getentpBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 3, "no_null_image_path");
					temp.getMap().put("baseLink101List", baseLink101List);
				}
				if (temp.getPre_number() == 2) {
					List<EntpBaseLink> baseLink201List = this.getentpBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 1, "no_null_image_path");
					temp.getMap().put("baseLink201List", baseLink201List);

					List<EntpBaseLink> baseLink202List = this.getentpBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 2, 2, "no_null_image_path");
					temp.getMap().put("baseLink202List", baseLink202List);

					List<EntpBaseLink> baseLink203List = this.getentpBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 3, 1, "no_null_image_path");
					temp.getMap().put("baseLink203List", baseLink203List);
				}
				if (temp.getPre_number() == 3) {
					List<EntpBaseLink> baseLink301List = this.getentpBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 3, "no_null_image_path");
					temp.getMap().put("baseLink301List", baseLink301List);
				}
				if (temp.getPre_number() == 4) {
					List<EntpBaseLink> baseLink401List = this.getentpBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 4, "no_null_image_path");
					temp.getMap().put("baseLink401List", baseLink401List);

				}
				if (temp.getPre_number() == 5) {
					List<EntpBaseLink> baseLink501List = this.getentpBaseLinkListWithParIdAndParSonType(link_id,
							temp.getId(), 1, 12, "no_null_image_path");
					temp.getMap().put("baseLink501List", baseLink501List);
				}
			}
		}
		request.setAttribute("baseLink20List", baseLink20List);// 楼层
		request.setAttribute("baseLink10List", baseLink10List);// 轮播图
		request.setAttribute("baseLinkBg", baseLinkBg);// 背景图
		return new ActionForward("/IndexEntpInfo/entpBaselink.jsp");

	}

	public ActionForward getCommList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		DynaBean dynaBean = (DynaBean) form;
		String keyword = (String) dynaBean.get("keyword");
		String entp_id = (String) dynaBean.get("entp_id");
		String orderByParam = (String) dynaBean.get("orderByParam");
		String comm_name_like = (String) dynaBean.get("comm_name_like");
		String root_cls_id = (String) dynaBean.get("root_cls_id");
		String par_cls_id = (String) dynaBean.get("par_cls_id");
		String son_cls_id = (String) dynaBean.get("son_cls_id");
		String entp_comm_class_id = (String) dynaBean.get("entp_comm_class_id");
		String p_index = (String) dynaBean.get("p_index");
		String is_aid = (String) dynaBean.get("is_aid");

		EntpInfo entpInfo = initPublic(mapping, form, request, entp_id, response);
		if (null == entpInfo) {
			return null;
		}

		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		String city = Keys.QUANGUO_P_INDEX;

		if (null != current_p_index) {
			city = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}

		dynaBean.set("orderByParam", orderByParam);

		Pager pager = (Pager) dynaBean.get("pager");
		// if (StringUtils.isBlank(p_index)) {
		// p_index = city;
		// }
		//
		// if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
		// p_index = null;
		// }
		// 平台分类
		// List<CommInfo> rootClassList = super.getCommInfoRoot_cls_id("true", entp_id, null, null);
		// request.setAttribute("baseRootClassList", rootClassList);
		//
		// if (StringUtils.isNotBlank(root_cls_id)) {
		// List<CommInfo> baseParClassList = super.getCommInfoRoot_cls_id(null, entp_id, root_cls_id, null);
		// request.setAttribute("baseParClassList", baseParClassList);
		//
		// BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(root_cls_id));
		// if (null != parClassTemp) {
		// request.setAttribute("root_cls_name", parClassTemp.getCls_name());
		// }
		// }
		//
		// if (StringUtils.isNotBlank(par_cls_id)) {
		// List<CommInfo> baseSonClassList = super.getCommInfoRoot_cls_id(null, entp_id, null, par_cls_id);
		// request.setAttribute("baseSonClassList", baseSonClassList);
		//
		// BaseClass parClassTemp = super.getBaseClass(Integer.valueOf(par_cls_id));
		// if (null != parClassTemp) {
		// request.setAttribute("par_cls_name", parClassTemp.getCls_name());
		// }
		// }
		// if (StringUtils.isNotBlank(son_cls_id)) {
		// BaseClass sonClassTemp = super.getBaseClass(Integer.valueOf(son_cls_id));
		// if (null != sonClassTemp) {
		// request.setAttribute("son_cls_name", sonClassTemp.getCls_name());
		// }
		// }
		EntpCommClass entity = new EntpCommClass();// 商家分类
		entity.setEntp_id(entpInfo.getId());
		entity.setIs_del(0);
		List<EntpCommClass> commClasslist = super.getFacade().getEntpCommClassService().getEntpCommClassList(entity);
		request.setAttribute("commClasslist", commClasslist);
		List<CommInfo> entityList = super.getCommInfoList(12, false, entpInfo.getId(), true, false, orderByParam, true,
				pager, null, comm_name_like, p_index, null, null, null, null, is_aid, null, entp_comm_class_id, null);
		request.setAttribute("entityList", entityList);

		super.getBaseProvinceCityList(request, "sonBaseProList", Integer.valueOf(city));

		return new ActionForward("/IndexEntpInfo/list.jsp");

	}

	/** 商品信息 */
	public ActionForward getCommInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.setPublicInfoWithSearchList(request);
		super.setPublicInfoList(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (!GenericValidator.isLong(id)) {
			String msg = "传入的商品参数为空";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');history.back();}");
			return null;
		}

		List<BaseLink> base0LinkList = this.getBaseLinkListWithPindex(0, 1, "no_null_image_path", null);
		if (null != base0LinkList && base0LinkList.size() > 0) {
			request.setAttribute("base0Link", base0LinkList.get(base0LinkList.size() - 1));
		}

		Boolean judgeIsMoblie = super.JudgeIsMoblie(request);
		if (judgeIsMoblie) {
			String ctx = super.getCtxPath(request);
			return new ActionForward(ctx + "/m/MEntpInfo.do?id=" + id, true);
		}

		
		
		Cookie current_p_index = WebUtils.getCookie(request, Keys.COOKIE_P_INDEX);
		String p_index = Keys.QUANGUO_P_INDEX;

		if (null != current_p_index) {
			p_index = URLDecoder.decode(current_p_index.getValue(), "UTF-8");
		}
		if (p_index.equals(Keys.QUANGUO_P_INDEX.toString())) {
			p_index = null;
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
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.close();}");
			return null;
		}

		UserInfo ui = super.getUserInfoFromSession(request);

		logger.info("commInfo.getOwn_entp_id().intValue()"+commInfo.getOwn_entp_id().intValue());
		if(commInfo.getOwn_entp_id().intValue() == Integer.valueOf(Keys.jd_entp_id)){
			String judgeJdProductPriceFlag = super.judgeJdProductPrice(commInfo.getJd_skuid());//更新jd商品价格
		}
		
		commInfo = super.getCommInfo(Integer.valueOf(id));//重新商品信息
		
		if ((commInfo == null) || ((commInfo.getIs_del().intValue() != 0))
				|| ((commInfo.getAudit_state().intValue() != 1)) || ((commInfo.getIs_sell().intValue() == 0))
				|| ((commInfo.getIs_sell().intValue() == 1) && (commInfo.getDown_date().compareTo(new Date()) < 0))) {
			String msg = "商品不存在或审核未通过，或商品已经下架，或商品未维护套餐。";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.close();}");
			return null;
		}
		if(commInfo != null){
			if (null != commInfo.getBrand_id()) {
				BaseBrandInfo baseBrandInfo = super.getBaseBrandInfo(commInfo.getBrand_id());
				request.setAttribute("baseBrandInfo", baseBrandInfo);
			}
	
			PdContent pdContentXxxx = super.getPdContent(commInfo.getId(), 1);
			if (null != pdContentXxxx) {
				request.setAttribute("pdContentXxxx", pdContentXxxx.getContent());
			}
		}

		// 更新浏览次数
		CommInfo commInfoForViewCount = new CommInfo();
		commInfoForViewCount.setId(Integer.valueOf(id));
		if(commInfoForViewCount != null){			
			if (null != commInfo.getView_count()) {
				commInfoForViewCount.setView_count(commInfo.getView_count() + 1);
			} else {
				commInfoForViewCount.setView_count(0);
			}
			getFacade().getCommInfoService().modifyCommInfo(commInfoForViewCount);

		}
		EntpInfo entpInfo = initPublic(mapping, form, request, commInfo.getOwn_entp_id().toString(), response);
		if (null == entpInfo) {
			return null;
		}

		// 评论信息
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setLink_id(commInfo.getId());
		Integer commentCount = commInfo.getComment_count();
		List<CommentInfo> commentInfoList = getFacade().getCommentInfoService().getCommentInfoList(commentInfo);
		if ((null != commentInfoList) && (commentInfoList.size() > 0)) {
			for (CommentInfo ci : commentInfoList) {
				String secretString = super.setSecretUserName(ci.getComm_uname());
				ci.getMap().put("secretString", secretString);
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
					super.setMapCommentSonList(ci);
				}
			}
		}
		request.setAttribute("commentInfoList", commentInfoList);

		Integer commentScore = getFacade().getCommentInfoService().getCommentInfoAvgCommSore(commentInfo);
		request.setAttribute("commentScore", commentScore);

		commentInfo.setComm_level(1);// 1、好评（评分：4、5分）
		Integer commentLevel1Count = getFacade().getCommentInfoService().getCommentInfoCount(commentInfo);
		request.setAttribute("commentLevel1Count", commentLevel1Count);
		request.setAttribute("commentLevel1Rate",
				dfFormat0.format((commentLevel1Count.doubleValue() / commentCount.doubleValue()) * 100));

		commentInfo.setComm_level(2);// 2、中评（评分2、3分）
		Integer commentLevel2Count = getFacade().getCommentInfoService().getCommentInfoCount(commentInfo);
		request.setAttribute("commentLevel2Count", commentLevel2Count);
		request.setAttribute("commentLevel2Rate",
				dfFormat0.format((commentLevel2Count.doubleValue() / commentCount.doubleValue()) * 100));

		commentInfo.setComm_level(3);// 3 、差评（评分：1分）
		Integer commentLevel3Count = getFacade().getCommentInfoService().getCommentInfoCount(commentInfo);
		request.setAttribute("commentLevel3Count", commentLevel3Count);
		request.setAttribute("commentLevel3Rate",
				dfFormat0.format((commentLevel3Count.doubleValue() / commentCount.doubleValue()) * 100));

		if (commentCount.intValue() == 0) {
			request.setAttribute("commentLevel1Rate", "0");
			request.setAttribute("commentLevel2Rate", "0");
			request.setAttribute("commentLevel3Rate", "0");
		}

		BaseClass bpz = new BaseClass();
		bpz.setCls_id(commInfo.getCls_id());
		bpz.setIs_del(0);
		bpz.setCls_scope(1);
		List<BaseClass> bpzList = super.getFacade().getBaseClassService().proGetBaseClassParentList(bpz);
		if (bpzList != null && bpzList.size() > 0) {
			bpzList.remove(bpzList.size() - 1);
			Collections.reverse(bpzList);
			int i = 0;
			for (BaseClass temp : bpzList) {
				if (i == 0) {
					request.setAttribute("root_cls_name", temp.getCls_name());
				} else if (i == 1) {
					// 查询子的
					BaseClass bpzSon = new BaseClass();
					bpzSon.setPar_id(temp.getCls_id());
					bpzSon.setIs_del(0);
					bpzSon.setCls_scope(1);
					List<BaseClass> bpzSonList = super.getFacade().getBaseClassService().getBaseClassList(bpzSon);
					temp.getMap().put("bpzSonList", bpzSonList);
					request.setAttribute("parBaseClass", temp);
				}
				i++;
			}
			request.setAttribute("bpzList", bpzList);
		}

		// 店铺新品
		List<CommInfo> commmInfoNewList = super.getCommInfoList(3, false, entpInfo.getId(), true, false,
				"orderByAddDateDesc", false, null, null, null, p_index, null, null, null, null, null, null);
		request.setAttribute("commmInfoNewList", commmInfoNewList);
		// 店铺推荐商品
		List<CommInfo> commmInfoTjList = super.getCommInfoList(3, true, entpInfo.getId(), true, false, null, false,
				null, null, null, p_index, null, null, null, null, null, null);
		request.setAttribute("commmInfoTjList", commmInfoTjList);
		// 店铺热销
		List<CommInfo> commmInfoHotList = super.getCommInfoList(3, false, entpInfo.getId(), true, false,
				"orderBySaleDesc", false, null, null, null, p_index, null, null, null, null, null, null);
		request.setAttribute("commmInfoHotList", commmInfoHotList);

		// 取商品收藏数、
		ScEntpComm scEntpComm = new ScEntpComm();
		scEntpComm.setLink_id(commInfo.getId());
		scEntpComm.setIs_del(0);
		Integer count = super.getFacade().getScEntpCommService().getScEntpCommCount(scEntpComm);
		request.setAttribute("sc_count", count);

		// 显示套餐
		CommTczhPrice commTczhPrice = new CommTczhPrice();
		commTczhPrice.setComm_id(commInfo.getId().toString());
		List<CommTczhPrice> commTczhPriceList = super.getFacade().getCommTczhPriceService()
				.getCommTczhPriceList(commTczhPrice);
		request.setAttribute("commTczhPriceList", commTczhPriceList);

		if (commInfo.getIs_zingying().intValue() != 0) {
			commInfo.getMap().put("commZyName", CommZyType.getName(commInfo.getIs_zingying()));
		}

		// 是扶贫商品的时候需要去查询扶贫对象
		if (commInfo.getIs_aid() == 1) {
			CommInfoPoors commInfoPoors = new CommInfoPoors();
			commInfoPoors.setComm_id(commInfo.getId());
			List<CommInfoPoors> commInfoPoorsList = super.getFacade().getCommInfoPoorsService()
					.getCommInfoPoorsList(commInfoPoors);
			commInfo.setPoorsList(commInfoPoorsList);
		}

		request.setAttribute("commInfo", commInfo);

		request.setAttribute("reBate1001",
				super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1001.getIndex()).getPre_number2());
		request.setAttribute("reBate1002",
				super.getBaseData(Keys.REBATE_BASE_DATA_ID.REBATE_BASE_DATA_ID_1002.getIndex()).getPre_number2());
		request.setAttribute("upLevelNeedPayMoney", super.getSysSetting(Keys.upLevelNeedPayMoney));

		UserInfo userInfo = super.getUserInfoFromSession(request);
		if (null != userInfo) {
			request.setAttribute("userInfo", super.getUserInfo(userInfo.getId()));
		}

		super.controlCookie(response, request, commInfo); // 最近浏览商品

		return super.returnJsp("index", commInfo.getComm_type());

	}

	public ActionForward getCommInfoBuyHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String link_id = (String) dynaBean.get("link_id");
		Pager pager = (Pager) dynaBean.get("pager");

		dynaBean.set("link_id", link_id);
		OrderInfoDetails entity = new OrderInfoDetails();
		entity.setComm_id(Integer.valueOf(link_id));
		super.copyProperties(entity, dynaBean);
		Integer recordCount = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetailsBuyHistoryCount(entity);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		entity.getRow().setCount(pager.getRowCount());
		entity.getRow().setFirst(pager.getFirstRow());

		List<OrderInfoDetails> entityList = getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsBuyHistoryPaginatedList(entity);
		if ((null != entityList) && (entityList.size() > 0)) {
			for (OrderInfoDetails oi : entityList) {
				if (null != oi.getComm_tczh_name()) {
					String[] comm_tczh_nameList = StringUtils.splitByWholeSeparator(oi.getComm_tczh_name(), " ");
					oi.getMap().put("comm_tczh_nameList", comm_tczh_nameList);
				}
				String secretString = super.setSecretUserName((String) oi.getMap().get("user_name"));
				oi.getMap().put("secretString", secretString);

			}

		}
		request.setAttribute("entityList", entityList);

		return new ActionForward("/IndexEntpInfo/list_buy_history.jsp");
	}

	public ActionForward getKeFuInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		DynaBean dynaBean = (DynaBean) form;
		String entp_id = (String) dynaBean.get("entp_id");

		EntpInfo entity = new EntpInfo();
		entity.setId(Integer.valueOf(entp_id));
		entity = super.getFacade().getEntpInfoService().getEntpInfo(entity);
		if (null != entity) {
			request.setAttribute("entpInfo", entity);
		}
		return new ActionForward("/IndexEntpInfo/_public_kefu_new.jsp");
	}

	public void shareSubDomainSession(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Cookie cookie = WebUtils.getCookie(request, Keys.COOKIE_USERINFO_KEY_JSESSIONID);
		logger.info("========cookie:{}", cookie);
		if (null != cookie) {
			String cookieValue = cookie.getValue();
			if (!StringUtils.equals(request.getSession().getId(), cookieValue)) {
				CookieGenerator cg1 = new CookieGenerator();
				cg1.setCookieMaxAge(1 * 24 * 60 * 60);
				cg1.setCookieName("JSESSIONID");
				cg1.addCookie(response, URLEncoder.encode(cookieValue, "UTF-8"));
			}
		}

	}

}
