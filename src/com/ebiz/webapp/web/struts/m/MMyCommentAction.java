package com.ebiz.webapp.web.struts.m;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alibaba.fastjson.JSONObject;
import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.webapp.domain.BaseFiles;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.CommentInfoSon;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;

public class MMyCommentAction extends MBaseWebAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;
		Pager pager = (Pager) dynaBean.get("pager");
		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setComm_uid(ui.getId());

		Integer recordCount = super.getFacade().getCommentInfoService().getCommentInfoCount(commentInfo);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		commentInfo.getRow().setCount(pager.getRowCount());
		commentInfo.getRow().setFirst(pager.getFirstRow());

		List<CommentInfo> commentInfoList = super.getFacade().getCommentInfoService()
				.getCommentInfoPaginatedList(commentInfo);
		if (null != commentInfoList && commentInfoList.size() > 0) {
			request.setAttribute("commentInfoList", commentInfoList);
		}

		return mapping.findForward("list");
	}

	public ActionForward getCommentInfoSonList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "我的评价");

		DynaBean dynaBean = (DynaBean) form;
		String add_user_id = (String) dynaBean.get("add_user_id");
		String to_user_id = (String) dynaBean.get("to_user_id");

		CommentInfoSon commentInfoSon = new CommentInfoSon();
		if (StringUtils.isNotBlank(add_user_id)) {
			commentInfoSon.setAdd_user_id(Integer.valueOf(add_user_id));
		}
		if (StringUtils.isNotBlank(to_user_id)) {
			commentInfoSon.setTo_user_id(Integer.valueOf(to_user_id));
		}

		Integer recordCount = super.getFacade().getCommentInfoSonService().getCommentInfoSonCount(commentInfoSon);

		// pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		// a.getRow().setCount(pager.getRowCount());
		// a.getRow().setFirst(pager.getFirstRow());

		List<CommentInfoSon> commentInfoSonList = super.getFacade().getCommentInfoSonService()
				.getCommentInfoSonList(commentInfoSon);
		if (null != commentInfoSonList && commentInfoSonList.size() > 0) {

			OrderInfoDetails orderInfoDetails = null;
			CommentInfo commentInfo = null;
			for (CommentInfoSon son : commentInfoSonList) {
				commentInfo = new CommentInfo();
				commentInfo.setId(son.getPar_id());
				commentInfo = getFacade().getCommentInfoService().getCommentInfo(commentInfo);
				if (null != commentInfo) {
					orderInfoDetails = new OrderInfoDetails();
					orderInfoDetails.setId(commentInfo.getOrder_details_id());
					orderInfoDetails = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(orderInfoDetails);
					if (null != orderInfoDetails) {
						son.getMap().put("ods", orderInfoDetails);
					}

					BaseFiles baseFiles = new BaseFiles();
					baseFiles.setLink_id(commentInfo.getId());
					baseFiles.setLink_tab("CommentInfo");
					baseFiles.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
					baseFiles.setType(Keys.BaseFilesType.Base_Files_TYPE_40.getIndex());
					List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
					if (null != baseFilesList && baseFilesList.size() > 0) {
						Integer size = 5 - baseFilesList.size();
						for (int i = 0; i < size; i++) {
							baseFilesList.add(new BaseFiles());
						}
						son.getMap().put("baseFilesList", baseFilesList);
					}

					son.getMap().put("commentInfo", commentInfo);
				}

			}
			request.setAttribute("commentInfoSonList", commentInfoSonList);
		}

		return mapping.findForward("list");
	}

	public ActionForward getCommentList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		DynaBean dynaBean = (DynaBean) form;

		request.setAttribute("header_title", "我的评价");

		Pager pager = (Pager) dynaBean.get("pager");
		String is_entp = (String) dynaBean.get("is_entp");

		CommentInfo commentInfo = new CommentInfo();
		if (StringUtils.isNotBlank(is_entp)) {
			commentInfo.setEntp_id(ui.getOwn_entp_id());
		} else {
			commentInfo.setComm_uid(ui.getId());
		}

		Integer recordCount = super.getFacade().getCommentInfoService().getCommentInfoCount(commentInfo);

		pager.init(recordCount.longValue(), pager.getPageSize(), pager.getRequestPage());
		commentInfo.getRow().setCount(pager.getRowCount());
		commentInfo.getRow().setFirst(pager.getFirstRow());

		List<CommentInfo> commentInfoList = super.getFacade().getCommentInfoService()
				.getCommentInfoPaginatedList(commentInfo);
		if (null != commentInfoList && commentInfoList.size() > 0) {
			OrderInfoDetails orderInfoDetails = null;
			for (CommentInfo cur : commentInfoList) {
				if (null != cur.getOrder_details_id()) {
					orderInfoDetails = new OrderInfoDetails();
					orderInfoDetails.setId(cur.getOrder_details_id());
					orderInfoDetails = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(orderInfoDetails);
					if (null != orderInfoDetails) {
						cur.getMap().put("ods", orderInfoDetails);
					}
				}

				BaseFiles baseFiles = new BaseFiles();
				baseFiles.setLink_id(cur.getId());
				baseFiles.setLink_tab("CommentInfo");
				baseFiles.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
				baseFiles.setType(Keys.BaseFilesType.Base_Files_TYPE_40.getIndex());
				List<BaseFiles> baseFilesList = super.getFacade().getBaseFilesService().getBaseFilesList(baseFiles);
				if (null != baseFilesList && baseFilesList.size() > 0) {
					Integer size = 5 - baseFilesList.size();
					for (int i = 0; i < size; i++) {
						baseFilesList.add(new BaseFiles());
					}
					cur.getMap().put("baseFilesList", baseFilesList);
				}

			}
			request.setAttribute("entityList", commentInfoList);
		}

		return mapping.findForward("list");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		super.getModNameForMobile(request);
		String order_id = (String) dynaBean.get("order_id");
		String link_id = (String) dynaBean.get("link_id");
		String tip = (String) dynaBean.get("tip");
		String base_files1 = (String) dynaBean.get("base_files1");
		String base_files2 = (String) dynaBean.get("base_files2");
		String base_files3 = (String) dynaBean.get("base_files3");
		String base_files4 = (String) dynaBean.get("base_files4");
		String base_files5 = (String) dynaBean.get("base_files5");
		String[] basefiles = { base_files1, base_files2, base_files3, base_files4, base_files5 };

		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, dynaBean);
		entity.setLink_id(new Integer(link_id));
		entity.setComm_ip(this.getIpAddr(request));
		entity.setComm_time(new Date());
		entity.setComm_state(1);// 发布
		if (StringUtils.isBlank(base_files1) && StringUtils.isBlank(base_files2) && StringUtils.isBlank(base_files3)
				&& StringUtils.isBlank(base_files4) && StringUtils.isBlank(base_files5)) {
			entity.setHas_pic(0);
		} else {
			entity.setHas_pic(1);
		}
		entity.setOrder_value(0);
		entity.setComm_uid(ui.getId());
		entity.setComm_uname(ui.getUser_name());

		entity.getMap().put("basefiles", basefiles);
		getFacade().getCommentInfoService().modifyCommentInfo(entity);

		saveMessage(request, "entity.updated");

		super.renderJavaScript(response, "window.onload=function(){location.href='" + request.getContextPath()
				+ "/m/MMyComment.do?method=getCommentList&add_user_id=" + ui.getId() + "&tip=1'}");
		// super.renderJavaScript(response, "window.onload=function(){window.parent.location.reload();}");

		return null;
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "评价");

		DynaBean dynaBean = (DynaBean) form;
		String order_detail_id = (String) dynaBean.get("id");

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setId(new Integer(order_detail_id));
		orderInfoDetails = getFacade().getOrderInfoDetailsService().getOrderInfoDetails(orderInfoDetails);
		if (null != orderInfoDetails) {
			copyProperties(form, orderInfoDetails);
		}

		return mapping.findForward("input");
		// return new ActionForward("/MMyComment/editComment.jsp");

	}

	public ActionForward huifu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "评价");

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setId(Integer.valueOf(id));
		commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
		super.copyProperties(form, commentInfo);
		if (null != commentInfo) {
			// 评论图片
			super.setRequestCommentPic(request, commentInfo);

			super.setRequestCommentInfoSonList(request, commentInfo.getId());

			OrderInfoDetails ods = getOrderInfoDetails(commentInfo);
			if (null != ods) {
				request.setAttribute("ods", ods);
			}

		}
		// return mapping.findForward("input");
		return new ActionForward("/MMyComment/huifu.jsp");

	}

	public ActionForward editHuifu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);

		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "评价");

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		CommentInfoSon commentInfoSon = new CommentInfoSon();
		commentInfoSon.setId(Integer.valueOf(id));
		commentInfoSon.setIs_del(0);
		commentInfoSon = getFacade().getCommentInfoSonService().getCommentInfoSon(commentInfoSon);
		if (null != commentInfoSon) {
			CommentInfo commentInfo = new CommentInfo();
			commentInfo.setId(commentInfoSon.getPar_id());
			commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
			super.copyProperties(form, commentInfo);
			if (null != commentInfo) {
				// 评论图片
				super.setRequestCommentPic(request, commentInfo);

				// 评论字表
				super.setRequestCommentInfoSonList(request, commentInfo.getId());

			}
			request.setAttribute("content", commentInfoSon.getContent());
			request.setAttribute("son_id", commentInfoSon.getId());
		}

		// return mapping.findForward("input");
		return new ActionForward("/MMyComment/huifu.jsp");

	}

	public ActionForward editComment(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(id)) {
			String msg = "参数有误,请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		super.getModNameForMobile(request);
		saveToken(request);

		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setId(Integer.valueOf(id));
		commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
		super.copyProperties(form, commentInfo);
		if (null != commentInfo) {
			// 评论图片
			super.setRequestCommentPic(request, commentInfo);

		}
		return new ActionForward("/MMyComment/editComment.jsp");
	}

	public ActionForward chooseList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}

		request.setAttribute("header_title", "选择商品");
		super.getModNameForMobile(request);
		DynaBean dynaBean = (DynaBean) form;

		String id = (String) dynaBean.get("id");
		OrderInfo entity = new OrderInfo();
		entity.setId(new Integer(id));
		entity = getFacade().getOrderInfoService().getOrderInfo(entity);

		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setOrder_id(entity.getId());
		List<OrderInfoDetails> orderInfoDetailsList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsList(orderInfoDetails);
		if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
			CommentInfo commentInfo = null;
			for (OrderInfoDetails temp : orderInfoDetailsList) {
				if (null != temp.getHas_comment() && temp.getHas_comment().intValue() > 0) {
					commentInfo = new CommentInfo();
					commentInfo.setOrder_details_id(temp.getId());
					commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
					if (null != commentInfo) {
						temp.getMap().put("comment_id", commentInfo.getId());
						logger.info("==commentInfo.getId()" + commentInfo.getId());
					}
				}
			}
		}
		request.setAttribute("entity", entity);
		request.setAttribute("orderInfoDetailsList", orderInfoDetailsList);

		return new ActionForward("/MMyComment/choose_list.jsp");

	}

	public ActionForward edit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		if (StringUtils.isBlank(id) || StringUtils.isBlank(id)) {
			String msg = "参数有误,请联系管理员！";
			return super.showTipMsg(mapping, form, request, response, msg);
		}
		super.getModNameForMobile(request);
		saveToken(request);

		OrderInfoDetails a = new OrderInfoDetails();

		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setId(Integer.valueOf(id));
		commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
		super.copyProperties(form, commentInfo);
		if (null != commentInfo) {
			// 评论图片
			super.setRequestCommentPic(request, commentInfo);

		}

		// return mapping.findForward("input");
		return new ActionForward("/MMyComment/editComment.jsp");

	}

	public ActionForward saveComm(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======进入save===");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		JSONObject data = new JSONObject();
		String msg = "";
		String ret = "0";
		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		String link_id = (String) dynaBean.get("link_id");
		String base_files1 = (String) dynaBean.get("base_files1");
		String base_files2 = (String) dynaBean.get("base_files2");
		String base_files3 = (String) dynaBean.get("base_files3");
		String base_files4 = (String) dynaBean.get("base_files4");
		String[] basefiles = { base_files1, base_files2, base_files3, base_files4 };

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(new Integer(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);

		CommentInfo entity = new CommentInfo();
		super.copyProperties(entity, dynaBean);
		if (orderInfo.getOrder_type().equals(Keys.OrderType.ORDER_TYPE_10.getIndex())) {
			entity.setComm_type(Keys.CommentType.COMMENT_TYPE_10.getIndex());
		}
		entity.setLink_id(new Integer(link_id));
		entity.setComm_ip(this.getIpAddr(request));
		entity.setComm_time(new Date());
		entity.setComm_state(1);// 发布
		entity.setLink_f_id(orderInfo.getEntp_id());
		entity.setComm_type(orderInfo.getOrder_type());
		entity.setEntp_id(orderInfo.getEntp_id());
		if (StringUtils.isBlank(base_files1) && StringUtils.isBlank(base_files2) && StringUtils.isBlank(base_files3)
				&& StringUtils.isBlank(base_files4)) {
			entity.setHas_pic(0);
		} else {
			entity.setHas_pic(1);
		}
		entity.setOrder_value(0);
		entity.setComm_uid(ui.getId());
		entity.setComm_uname(ui.getUser_name());

		entity.getMap().put("basefiles", basefiles);
		entity.setEntp_id(orderInfo.getEntp_id());

		OrderInfoDetails ods = super.getOrderInfoDetails(entity);
		if (null != ods) {
			entity.setComm_name(ods.getComm_name());
			entity.setComm_tczh_name(ods.getComm_tczh_name());
		}
		entity.getMap().put("add_comment_count", "true");
		int i = getFacade().getCommentInfoService().createCommentInfo(entity);

		if (i > 0) {
			msg = "提交成功！";
			ret = "1";
		} else {
			msg = "提交失败！";
			ret = "0";

		}

		data.put("ret", ret);
		data.put("msg", msg);
		data.put("add_user_id", ui.getId());
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward saveHuifu(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		logger.info("======进入save===");
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		JSONObject data = new JSONObject();
		String msg = "";
		String ret = "0";
		DynaBean dynaBean = (DynaBean) form;
		String par_id = (String) dynaBean.get("par_id");
		String son_id = (String) dynaBean.get("son_id");
		String content = (String) dynaBean.get("content");

		if (StringUtils.isBlank(son_id)) {
			CommentInfo commentInfo = new CommentInfo();
			commentInfo.setId(Integer.valueOf(par_id));
			commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
			if (null != commentInfo) {
				CommentInfoSon insertCommentInfoSon = new CommentInfoSon();
				super.copyProperties(insertCommentInfoSon, form);
				insertCommentInfoSon.setType(Keys.COMM_INFO_SON_TYPE.COMM_INFO_SON_TYPE_1.getIndex());
				insertCommentInfoSon.setAdd_date(new Date());
				insertCommentInfoSon.setAdd_user_id(ui.getId());
				insertCommentInfoSon.setAdd_user_ip(super.getIpAddr(request));
				insertCommentInfoSon.setAdd_user_logo(ui.getUser_logo());
				insertCommentInfoSon.setAdd_user_name(ui.getUser_name());
				insertCommentInfoSon.setIs_del(0);

				if (ui.getIs_entp() == 1) {
					insertCommentInfoSon.setIs_entp(1);
					EntpInfo b = getEntpInfo(commentInfo.getEntp_id());
					if (null != b) {
						insertCommentInfoSon.setEntp_id(b.getId());
						insertCommentInfoSon.setEntp_name(b.getEntp_name());
						insertCommentInfoSon.setEntp_logo(b.getEntp_logo());
					}

					UserInfo toUser = getUserInfo(commentInfo.getComm_uid());
					if (null != toUser) {
						insertCommentInfoSon.setTo_user_id(toUser.getId());
						insertCommentInfoSon.setTo_user_name(toUser.getUser_name());
						insertCommentInfoSon.setTo_user_logo(toUser.getUser_logo());
					}
				} else {
					insertCommentInfoSon.setIs_entp(0);
					EntpInfo b = getEntpInfo(commentInfo.getEntp_id());
					if (null != b) {
						insertCommentInfoSon.setEntp_id(b.getId());
						insertCommentInfoSon.setEntp_name(b.getEntp_name());
						insertCommentInfoSon.setEntp_logo(b.getEntp_logo());
						UserInfo toUser = getUserInfo(b.getAdd_user_id());
						if (null != toUser) {
							insertCommentInfoSon.setTo_user_id(toUser.getId());
							insertCommentInfoSon.setTo_user_name(toUser.getUser_name());
							insertCommentInfoSon.setTo_user_logo(toUser.getUser_logo());
						}
					}

				}

				int i = getFacade().getCommentInfoSonService().createCommentInfoSon(insertCommentInfoSon);
				if (i > 0) {
					msg = "提交成功！";
					ret = "1";
				} else {
					msg = "提交失败！";
					ret = "0";

				}
			}
		} else {
			CommentInfoSon modifyCommentInfoSon = new CommentInfoSon();
			modifyCommentInfoSon.setId(Integer.valueOf(son_id));
			modifyCommentInfoSon.setIs_del(0);
			modifyCommentInfoSon = getFacade().getCommentInfoSonService().getCommentInfoSon(modifyCommentInfoSon);
			if (null != modifyCommentInfoSon) {
				modifyCommentInfoSon.setContent(content);
				int i = getFacade().getCommentInfoSonService().modifyCommentInfoSon(modifyCommentInfoSon);
				if (i > 0) {
					msg = "提交成功！";
					ret = "1";
				} else {
					msg = "提交失败！";
					ret = "0";

				}
			}
		}

		data.put("ret", ret);
		data.put("msg", msg);
		super.renderJson(response, data.toJSONString());
		return null;

	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			return showTipNotLogin(mapping, form, request, response, msg);
		}
		request.setAttribute("header_title", "查看评价");

		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");

		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setId(Integer.valueOf(id));
		commentInfo = super.getFacade().getCommentInfoService().getCommentInfo(commentInfo);
		super.copyProperties(form, commentInfo);
		if (null != commentInfo) {
			// 评论图片
			super.setRequestCommentPic(request, commentInfo);

			CommentInfoSon commentInfoSon = new CommentInfoSon();
			commentInfoSon.setPar_id(commentInfo.getId());
			commentInfoSon.setIs_del(0);
			List<CommentInfoSon> commentInfoSonList = getFacade().getCommentInfoSonService().getCommentInfoSonList(
					commentInfoSon);
			if (null != commentInfoSon) {
				request.setAttribute("commentInfoSonList", commentInfoSonList);
			}

		}

		return mapping.findForward("view");
	}
}