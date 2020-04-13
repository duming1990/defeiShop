package com.ebiz.webapp.web.struts.manager.admin;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ebiz.ssi.web.struts.bean.Pager;
import com.ebiz.ssi.web.struts.bean.UploadFile;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ReturnsInfo;
import com.ebiz.webapp.domain.ReturnsInfoFj;
import com.ebiz.webapp.domain.ReturnsSwapDetail;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.EncryptUtilsV2;

/**
 * @author chenzhen
 * @version 2014-06-09
 */
public class ReturnsInfoAction extends BaseAdminAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.add(mapping, form, request, response);
	}

	public ActionForward add(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		setNaviStringToRequestScope(request);

		logger.info(">>>>>>>>>>>>>>>退货处理类");
		DynaBean dynaBean = (DynaBean) form;
		// 订单号
		String order_id = (String) dynaBean.get("order_id");
		// 订单明细的id
		String order_detail_id = (String) dynaBean.get("id");
		// 商品号
		String comm_id = (String) dynaBean.get("comm_id");
		// 商品类型
		String comm_type = (String) dynaBean.get("comm_type");

		if (GenericValidator.isLong(order_id) && GenericValidator.isLong(comm_id)) {

			// 订单信息
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(Integer.valueOf(order_id));
			orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
			request.setAttribute("orderInfo", orderInfo);

			OrderInfoDetails oid = new OrderInfoDetails();
			oid.setId(Integer.valueOf(order_detail_id));
			// oid.setOrder_id(Integer.valueOf(order_id));
			oid = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetails(oid);
			request.setAttribute("orderDetailInfo", oid);

			/*
			 * // 查询该订单产品详细是否已在退货流程中 ReturnsInfo returnsInfo = new ReturnsInfo();
			 * returnsInfo.setOrder_info_details_id(order_info_details_id); returnsInfo =
			 * super.getFacade().getReturnsInfoService().getReturnsInfo(returnsInfo);
			 * request.setAttribute("returnsInfo", returnsInfo);
			 */

		} else {
			String msg = "参数有误，请联系管理员！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		logger.info(">>>>>>>>>>>>>" + mapping.findForward("input"));

		return mapping.findForward("input");

	}

	public ActionForward addSuccess(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.info("添加成功跳转action");

		request.setAttribute("hadSave", "yes");

		logger.info(request.getAttribute("hadSave") + "");
		return mapping.findForward("input");

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
		resetToken(request);
		HttpSession session = request.getSession(false);
		UserInfo ui = (UserInfo) session.getAttribute(Keys.SESSION_USERINFO_KEY);

		DynaBean dynaBean = (DynaBean) form;
		// 订单号
		String order_id = (String) dynaBean.get("order_id");
		// 订单明细的id
		String order_detail_id = (String) dynaBean.get("order_detail_id");
		// 商品号
		String comm_id = (String) dynaBean.get("comm_id");
		// 商品类型
		String comm_type = (String) dynaBean.get("comm_type");

		// 退款原因
		String return_type = (String) dynaBean.get("return_type");
		// 退订数量

		String comm_count = (String) dynaBean.get("comm_count");
		// 退订总价格
		String totle_price = (String) dynaBean.get("totle_price");

		// 退款说明

		String return_desc = (String) dynaBean.get("return_desc");
		// 取得订单明细
		OrderInfoDetails oid = new OrderInfoDetails();
		oid.setId(Integer.valueOf(order_detail_id));
		// oid.setOrder_id(Integer.valueOf(order_id));
		oid = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetails(oid);
		// request.setAttribute("orderDetailInfo", oid);

		Date date = new Date();
		// 退货表
		ReturnsInfo rinfo = new ReturnsInfo();
		// 订单详细id
		rinfo.setOrder_info_details_id(oid.getId());
		// 退货标志
		rinfo.setApply_type(0);
		// 商品id
		rinfo.setComm_id(Integer.valueOf(comm_id));
		// 商品名称
		rinfo.setComm_name(oid.getComm_name());
		// 提交数量
		rinfo.setComm_count(Integer.valueOf(comm_count));
		// 申请凭证
		rinfo.setApply_proof(UUID.randomUUID().toString().replace("-", ""));
		// 物流公司编码
		// 物流公司名称
		// 运单号
		// 退货描述
		rinfo.setReturn_desc(return_desc);
		// 问题描述
		// 退货地址
		// 审核状态 0:等待商家审核; 1:商家审核通过,等待买家确认; -1:商家审核不通过; 2:买家审核通过,并返货;-2:买家审核不通过,强制结束,线下协商解决;3:卖家收获确认;
		String audit_status = "0";
		rinfo.setAudit_status(Integer.valueOf(audit_status));
		// 用户id
		rinfo.setUser_id(ui.getId());
		// 用户名称
		rinfo.setUser_name(ui.getUser_name());
		// 添加日期
		rinfo.setAdd_date(date);
		// 删除标记
		// 删除日期
		// 退货商品总金额,需要一个逻辑算法来处理
		rinfo.setTotal_price(BigDecimal.valueOf(Float.valueOf(totle_price)));

		// 订单退货运费
		rinfo.setReturn_wl_price(oid.getMatflow_price());
		// 退货运费支付方 0 买家 1 卖家,
		// 订单发货运费
		// 买货运费支付方 0 买家 1 卖家,
		// 退货成功标志 0:未确认 1:已确认
		rinfo.setIs_confirm(0);
		// 商家备注
		// 退货原因
		rinfo.setReturn_type(Integer.valueOf(return_type));

		super.getFacade().getReturnsInfoService().createReturnsInfo(rinfo);

		List<ReturnsInfoFj> rfj = new ArrayList<ReturnsInfoFj>();

		// 将数据插入 退货实体中

		List<UploadFile> uploadFileList = super.uploadFile(form, true, true, Keys.NEWS_INFO_IMAGE_SIZE);
		for (UploadFile uploadFile : uploadFileList) {

			logger.info("地址=[" + uploadFile.getFileSavePath() + "]" + "[" + uploadFile.getContentType() + "]");
			// image/png
			ReturnsInfoFj fj = new ReturnsInfoFj();
			fj.setReturns_info_id(rinfo.getId());
			fj.setFj_type(0);
			fj.setFj_addr(uploadFile.getFileSavePath());
			fj.setAdd_date(new Date());
			super.getFacade().getReturnsInfoFjService().createReturnsInfoFj(fj);

		}

		request.setAttribute("hadSave", "yes");

		/*
		 * StringBuffer pathBuffer = new StringBuffer(); pathBuffer.append("/all/ReturnsInfo.do?method=addSuccess");
		 * logger.info(">>>>>>>>>>>>>>跳转url=[" + pathBuffer.toString() + "]"); // /all/ReturnsInfo.do?method=list
		 * ActionForward forward = new ActionForward(pathBuffer.toString(), true); return forward;
		 */
		return mapping.findForward("input");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String audit_state_check = (String) dynaBean.get("audit_state_check");
		String trade_merger_index = (String) dynaBean.get("trade_merger_index");
		String trade_index = (String) dynaBean.get("trade_index");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String pay_type = (String) dynaBean.get("pay_type");
		String th_type = (String) dynaBean.get("th_type");

		Pager pager = (Pager) dynaBean.get("pager");
		ReturnsInfo entity = new ReturnsInfo();

		entity.getMap().put("st_date", st_date);
		entity.getMap().put("en_date", en_date);
		entity.getMap().put("audit_state_check", audit_state_check);

		if (StringUtils.isNotBlank(th_type)) {
			entity.setTh_type(Integer.valueOf(th_type));
		}

		entity.getMap().put("trade_merger_index", trade_merger_index);
		entity.getMap().put("entp_name_like", entp_name_like);
		entity.getMap().put("trade_index", trade_index);
		entity.getMap().put("pay_type", pay_type);

		Integer recordCount = getFacade().getReturnsInfoService().getReturnsInfoCount(entity);
		pager.init(recordCount.longValue(), Integer.valueOf("10"), pager.getRequestPage());
		entity.getRow().setFirst(pager.getFirstRow());
		entity.getRow().setCount(pager.getRowCount());

		List<ReturnsInfo> entityList = super.getFacade().getReturnsInfoService().getReturnsInfoPaginatedList(entity);

		for (ReturnsInfo r : entityList) {
			OrderInfo oid = new OrderInfo();
			oid.setId(r.getOrder_info_details_id());
			oid = super.getFacade().getOrderInfoService().getOrderInfo(oid);
			if (null != oid) {
				r.setOrderInfo(oid);

				BaseData baseData = new BaseData();
				baseData.setId((Integer) r.getReturn_type());
				baseData.setIs_del(0);

				baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
				r.setBaseData(baseData);

			}
		}

		request.setAttribute("entityList", entityList);
		return mapping.findForward("list");

	}

	/**
	 * 平台审核页面展示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sysAudit_show(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));
			entity = super.getFacade().getReturnsInfoService().getReturnsInfo(entity);
			// 获得订单
			OrderInfo oid = new OrderInfo();
			oid.setId(entity.getOrder_info_details_id());
			oid = super.getFacade().getOrderInfoService().getOrderInfo(oid);
			entity.setOrderInfo(oid);

			List<ReturnsSwapDetail> rsdList = new ArrayList<ReturnsSwapDetail>();
			ReturnsSwapDetail rsd = new ReturnsSwapDetail();
			rsd.setReturns_info_id(entity.getId());
			rsd.setIs_del(0);

			// 类型0退货，1换货
			rsd.setApply_type(0);

			rsdList = super.getFacade().getReturnsSwapDetailService().getReturnsSwapDetailList(rsd);

			for (ReturnsSwapDetail de : rsdList) {

				// 附件
				List<ReturnsInfoFj> fjlist = new ArrayList<ReturnsInfoFj>();
				ReturnsInfoFj fj = new ReturnsInfoFj();
				fj.setC_returns_info_id(de.getId());

				fjlist = super.getFacade().getReturnsInfoFjService().getReturnsInfoFjList(fj);

				de.setFjList(fjlist);

				OrderInfoDetails detail = new OrderInfoDetails();
				detail.setId(de.getOrder_info_details_id());
				detail = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetails(detail);
				de.setDe(detail);
			}

			entity.setRsdetail(rsdList);

			/**
			 * 装入baseData
			 */

			BaseData baseData = new BaseData();
			baseData.setId((Integer) entity.getReturn_type());
			baseData.setIs_del(0);
			baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
			entity.setBaseData(baseData);

			super.copyProperties(form, entity);
			request.setAttribute("cur", entity);
		}

		// return mapping.findForward("list");
		return new ActionForward("/admin/ReturnsInfo/sysAuditform.jsp");

	}

	/**
	 * 显示平台审核页面展示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showsysAudit_show(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));
			entity = super.getFacade().getReturnsInfoService().getReturnsInfo(entity);
			// 获得订单
			OrderInfo oid = new OrderInfo();
			oid.setId(entity.getOrder_info_details_id());
			oid = super.getFacade().getOrderInfoService().getOrderInfo(oid);
			if (null != oid) {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(oid.getAdd_user_id());
				userInfo.setIs_del(0);
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				entity.getMap().put("userInfo", userInfo);
			}
			entity.setOrderInfo(oid);

			List<ReturnsSwapDetail> rsdList = new ArrayList<ReturnsSwapDetail>();
			ReturnsSwapDetail rsd = new ReturnsSwapDetail();
			rsd.setReturns_info_id(entity.getId());
			rsd.setIs_del(0);

			// 类型0退货，1换货
			rsd.setApply_type(0);

			rsdList = super.getFacade().getReturnsSwapDetailService().getReturnsSwapDetailList(rsd);

			for (ReturnsSwapDetail de : rsdList) {

				// 附件
				List<ReturnsInfoFj> fjlist = new ArrayList<ReturnsInfoFj>();
				ReturnsInfoFj fj = new ReturnsInfoFj();
				fj.setC_returns_info_id(de.getId());

				fjlist = super.getFacade().getReturnsInfoFjService().getReturnsInfoFjList(fj);

				de.setFjList(fjlist);

				OrderInfoDetails detail = new OrderInfoDetails();
				detail.setId(de.getOrder_info_details_id());
				detail = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetails(detail);
				de.setDe(detail);

			}

			entity.setRsdetail(rsdList);

			/**
			 * 装入baseData
			 */

			BaseData baseData = new BaseData();
			baseData.setId((Integer) entity.getReturn_type());
			baseData.setIs_del(0);
			baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
			entity.setBaseData(baseData);

			super.copyProperties(form, entity);
			request.setAttribute("cur", entity);
		}

		// return mapping.findForward("list");
		return new ActionForward("/admin/ReturnsInfo/showsysAuditform.jsp");

	}

	/**
	 * 商家审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sysAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		String memo = (String) dynaBean.get("memo");
		String auditStatus = (String) dynaBean.get("auditStatus");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));
			entity.setMemo(memo);
			// 设置状态
			if (auditStatus.equals("Y")) {
				entity.setAudit_status(1);
			} else if (auditStatus.equals("N")) {
				entity.setAudit_status(-1);
				entity.setIs_confirm(1);

			}
			super.getFacade().getReturnsInfoService().modifyReturnsInfo(entity);

		}

		String msg = "审核完成!";

		super.renderJavaScript(response,
				"window.onload=function(){alert('" + msg + "');location.href='" + request.getContextPath()
						+ "/manager/admin/ReturnsInfo.do?method=list&mod_id=" + mod_id + "'}");
		return null;

	}

	/**
	 * 买家检查审核页面展示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkAudit_show(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));
			entity = super.getFacade().getReturnsInfoService().getReturnsInfo(entity);
			// 获得订单
			OrderInfo oid = new OrderInfo();
			Map map = new HashMap();
			map.put("detailId", entity.getOrder_info_details_id());
			// 取得订单的信息
			oid = super.getFacade().getOrderInfoService().getOrderInfoByDetailId(map);
			entity.setOrderInfo(oid);
			super.copyProperties(form, entity);
			request.setAttribute("returnsInfo", entity);

			OrderInfoDetails oid1 = new OrderInfoDetails();
			oid1.setId(Integer.valueOf(entity.getOrder_info_details_id()));
			// oid.setOrder_id(Integer.valueOf(order_id));
			oid1 = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetails(oid1);
			request.setAttribute("orderDetailInfo", oid1);

			// 附件
			List<ReturnsInfoFj> fjlist = new ArrayList<ReturnsInfoFj>();
			ReturnsInfoFj fj = new ReturnsInfoFj();
			fj.setReturns_info_id(entity.getId());

			fjlist = super.getFacade().getReturnsInfoFjService().getReturnsInfoFjList(fj);
			request.setAttribute("fjList", fjlist);
		}

		// return mapping.findForward("list");
		return new ActionForward("/all/ReturnsInfo/checkAuditform.jsp");

	}

	/**
	 * 买家检查商家审核结果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		String mod_id = (String) dynaBean.get("mod_id");
		// 物流公司编号
		String wl_code = (String) dynaBean.get("id");
		// 运送单号
		String waybill_no = (String) dynaBean.get("waybill_no");
		// 物流公司名称
		String wl_comp = "";
		if (wl_code.equals("0")) {
			wl_comp = "顺风";
		} else if (wl_code.equals("1")) {
			wl_comp = "EMS";
		}

		// 是否通过标志
		String checkAuditStatus = (String) dynaBean.get("checkAuditStatus");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));

			// 设置状态

			if (checkAuditStatus.equals("Y")) {

				entity.setAudit_status(2);
				entity.setWl_comp(wl_comp);
				entity.setWl_code(wl_code);
				entity.setWaybill_no(waybill_no);

			} else if (checkAuditStatus.equals("N")) {
				entity.setAudit_status(-2);

			}

			super.getFacade().getReturnsInfoService().modifyReturnsInfo(entity);

		}

		String msg = "审核完成!";

		super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.parent.location.href='"
				+ request.getContextPath() + "/manager/all/ReturnsInfo.do?method=list&mod_id=" + mod_id + "'}");
		return null;

	}

	/**
	 * 卖家收获确认页面展示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward finishAudit_show(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));
			entity = super.getFacade().getReturnsInfoService().getReturnsInfo(entity);
			// 获得订单
			OrderInfo oid = new OrderInfo();
			Map map = new HashMap();
			map.put("detailId", entity.getOrder_info_details_id());
			// 取得订单的信息
			oid = super.getFacade().getOrderInfoService().getOrderInfoByDetailId(map);
			entity.setOrderInfo(oid);
			super.copyProperties(form, entity);
			request.setAttribute("returnsInfo", entity);

			OrderInfoDetails oid1 = new OrderInfoDetails();
			oid1.setId(Integer.valueOf(entity.getOrder_info_details_id()));
			// oid.setOrder_id(Integer.valueOf(order_id));
			oid1 = super.getFacade().getOrderInfoDetailsService().getOrderInfoDetails(oid1);
			request.setAttribute("orderDetailInfo", oid1);

			// 附件
			List<ReturnsInfoFj> fjlist = new ArrayList<ReturnsInfoFj>();
			ReturnsInfoFj fj = new ReturnsInfoFj();
			fj.setReturns_info_id(entity.getId());

			fjlist = super.getFacade().getReturnsInfoFjService().getReturnsInfoFjList(fj);
			request.setAttribute("fjList", fjlist);
		}

		// return mapping.findForward("list");
		return new ActionForward("/all/ReturnsInfo/finishAuditform.jsp");

	}

	/**
	 * 卖家收获确认
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward finishAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		if (isCancelled(request)) {
			return list(mapping, form, request, response);
		}
		if (!isTokenValid(request)) {
			saveError(request, "errors.token");
			return list(mapping, form, request, response);
		}
		resetToken(request);
		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");

		String mod_id = (String) dynaBean.get("mod_id");

		// 是否通过标志
		String finishAuditStatus = (String) dynaBean.get("finishAuditStatus");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));
			// 设置状态
			if (finishAuditStatus.equals("Y")) {
				entity.setAudit_status(3);
			}
			super.getFacade().getReturnsInfoService().modifyReturnsInfo(entity);
		}
		String msg = "审核完成!";
		super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.parent.location.href='"
				+ request.getContextPath() + "/manager/all/ReturnsInfo.do?method=list&mod_id=" + mod_id + "'}");
		return null;
	}

	public ActionForward toExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		setNaviStringToRequestScope(request);

		Map<String, Object> model = new HashMap<String, Object>();

		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String audit_state_check = (String) dynaBean.get("audit_state_check");
		String trade_merger_index = (String) dynaBean.get("trade_merger_index");
		String entp_name_like = (String) dynaBean.get("entp_name_like");
		String trade_index = (String) dynaBean.get("trade_index");
		String pay_type = (String) dynaBean.get("pay_type");

		ReturnsInfo entity = new ReturnsInfo();

		entity.getMap().put("st_date", st_date);
		entity.getMap().put("en_date", en_date);
		entity.getMap().put("audit_state_check", audit_state_check);

		entity.getMap().put("trade_merger_index", trade_merger_index);
		entity.getMap().put("trade_index", trade_index);
		entity.getMap().put("pay_type", pay_type);

		List<ReturnsInfo> entityList = super.getFacade().getReturnsInfoService().getReturnsInfoList(entity);

		for (ReturnsInfo r : entityList) {
			OrderInfo oid = new OrderInfo();
			oid.setId(r.getOrder_info_details_id());
			oid = super.getFacade().getOrderInfoService().getOrderInfo(oid);
			r.setOrderInfo(oid);

			BaseData baseData = new BaseData();
			baseData.setId((Integer) r.getReturn_type());
			baseData.setIs_del(0);

			baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
			r.setBaseData(baseData);
		}
		model.put("entityList", entityList);
		String content = getFacade().getTemplateService().getContent("ReturnsInfo/list.ftl", model);
		// 下载文件出现乱码时，请参见此处
		String fname = EncryptUtilsV2.encodingFileName("退货订单信息.xls");

		response.setCharacterEncoding("GBK");
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment;filename=" + fname);

		PrintWriter out = response.getWriter();
		out.println(content);
		out.flush();
		out.close();
		return null;
	}

}
