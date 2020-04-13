package com.ebiz.webapp.web.struts.manager.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.InvoiceInfo;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.ReturnsInfo;
import com.ebiz.webapp.domain.ReturnsInfoFj;
import com.ebiz.webapp.domain.ReturnsSwapDetail;
import com.ebiz.webapp.domain.ShippingAddress;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.domain.WlCompInfo;
import com.ebiz.webapp.domain.WlOrderInfo;
import com.ebiz.webapp.web.Keys;

/**
 * @author chenzhen
 * @version 2014-06-09
 */
public class ReturnsInfoAction extends BaseCustomerAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return this.add(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		String order_id = (String) dynaBean.get("order_id");
		String order_state = (String) dynaBean.get("order_state");
		String sal = (String) dynaBean.get("sal");
		if (StringUtils.isNotBlank(order_state)) {
			dynaBean.set("order_state", order_state);
		}
		if (StringUtils.isNotBlank(sal)) {
			request.setAttribute("sal", true);
		}
		if (!GenericValidator.isLong(order_id)) {
			String msg = "参数有误，请联系管理员！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../login.shtml'}");
			return null;
		}
		String haveThis = request.getParameter("haveThis");
		if (haveThis != null) {

			request.setAttribute("isExists", "Y");

		}

		ReturnsInfo test = new ReturnsInfo();
		test.setOrder_info_details_id(Integer.valueOf(order_id));
		test.setIs_del(0);
		test.getMap().put("audit_status_notequals", -1);
		test = super.getFacade().getReturnsInfoService().getReturnsInfo(test);

		if ((test != null) && (haveThis == null)) {

			request.setAttribute("haveThis", "Y");

			return new ActionForward("/customer/ReturnsInfo.do?method=view&order_id=" + order_id + "&haveThis=Y", true);

		}

		/**
		 * 从基础数据表中取得type= 10000 的数据 type =10000 ,退货类型
		 */

		BaseData baseData = new BaseData();
		baseData.setType(10000);
		baseData.setIs_del(0);
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		baseDataList = super.getFacade().getBaseDataService().getBaseDataList(baseData);
		request.setAttribute("baseDataList", baseDataList);
		// 订单信息
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setId(Integer.valueOf(order_id));
		orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
		if (orderInfo.getRel_region() != null) {
			BaseProvince p = new BaseProvince();
			p.setP_index(orderInfo.getRel_region().longValue());
			p = super.getFacade().getBaseProvinceService().getBaseProvince(p);
			if (p != null) {
				request.setAttribute("full_name", p.getFull_name());
			}
		}
		request.setAttribute("orderInfo", orderInfo);
		WlOrderInfo wlOrderInfo = new WlOrderInfo();
		wlOrderInfo.setOrder_id(orderInfo.getId());
		wlOrderInfo.setIs_del(0);
		wlOrderInfo = super.getFacade().getWlOrderInfoService().getWlOrderInfo(wlOrderInfo);
		if (null != wlOrderInfo) {
			WlCompInfo wlCompInfo = new WlCompInfo();
			wlCompInfo.setId(wlOrderInfo.getWl_comp_id());
			wlCompInfo.setIs_del(0);
			wlCompInfo = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo);
			if (null != wlCompInfo) {
				wlOrderInfo.getMap().put("comm_type", wlCompInfo.getComp_type());
				wlOrderInfo.getMap().put("wl_comp_id", wlCompInfo.getId());
				wlOrderInfo.getMap().put("wl_comp_url", wlCompInfo.getWl_comp_url());
				wlOrderInfo.getMap().put("wl_comp_name", wlOrderInfo.getWl_comp_name());
			} else {
				UserInfo userInfo = new UserInfo();
				userInfo.setId(wlOrderInfo.getWl_comp_id());
				userInfo.setIs_del(0);
				userInfo = super.getFacade().getUserInfoService().getUserInfo(userInfo);
				if (null != userInfo) {
					WlCompInfo wlCompInfo2 = new WlCompInfo();
					wlCompInfo2.setId(userInfo.getOwn_entp_id());
					wlCompInfo2.setIs_del(0);
					wlCompInfo2 = super.getFacade().getWlCompInfoService().getWlCompInfo(wlCompInfo2);
					if (null != wlCompInfo2) {
						wlOrderInfo.getMap().put("comm_type", wlCompInfo2.getComp_type());
						wlOrderInfo.getMap().put("wl_comp_url", wlCompInfo2.getWl_comp_url());
						wlOrderInfo.getMap().put("wl_comp_id", wlCompInfo2.getId());
						wlOrderInfo.getMap().put("wl_comp_name", wlCompInfo2.getWl_comp_name());
					}
				}
			}
		}
		request.setAttribute("wlOrderInfo", wlOrderInfo);
		// 收获地址表
		ShippingAddress spa = new ShippingAddress();
		spa.setId(orderInfo.getShipping_address_id());
		spa = super.getFacade().getShippingAddressService().getShippingAddress(spa);
		request.setAttribute("shippingAddress", spa);
		if (null != spa) {
			// 发票信息
			InvoiceInfo invoiceInfo = new InvoiceInfo();
			invoiceInfo.setShipping_id(spa.getId());
			invoiceInfo = super.getFacade().getInvoiceInfoService().getInvoiceInfo(invoiceInfo);
			request.setAttribute("invoiceInfo", invoiceInfo);
		}
		// 产品详细
		OrderInfoDetails orderInfoDetail = new OrderInfoDetails();
		orderInfoDetail.setOrder_id(Integer.valueOf(order_id));
		List<OrderInfoDetails> orderInfoDetailList = super.getFacade().getOrderInfoDetailsService()
				.getOrderInfoDetailsList(orderInfoDetail);
		if ((null != orderInfoDetailList) && (orderInfoDetailList.size() > 0)) {
			for (OrderInfoDetails oid : orderInfoDetailList) {

				if (oid.getOrder_type() == 10) {
					ReturnsInfo entity = new ReturnsInfo();
					entity.setIs_del(0);
					entity.setOrder_info_details_id(Integer.valueOf(oid.getId()));
					entity = super.getFacade().getReturnsInfoService().getReturnsInfo(entity);
					if (null != entity) {
						request.setAttribute("state", true);
						oid.getMap().put("entity", entity);
					}
				}
			}
		}
		request.setAttribute("orderInfoDetailList", orderInfoDetailList);
		return mapping.findForward("view");
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

		super.setPublicInfoListWithEntpAndCustomer(request);
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

		// 检测订单是否已经执行过一次退货操作.如果执行则不能够再次退货
		boolean isExist = false;
		// isExist = testIsExist(order_detail_id, order_id);

		if (!isExist) {

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

			} else {
				String msg = "参数有误，请联系管理员！";
				super.renderJavaScript(response, "window.onload=function(){alert('" + msg
						+ "');location.href='../../login.shtml'}");
				return null;
			}
		} else {

			request.setAttribute("isExist", "Y");
		}

		logger.info(">>>>>>>>>>>>>" + mapping.findForward("input"));

		return mapping.findForward("input");

	}

	/**
	 * 一个订单只能退货一次
	 * 
	 * @param order_detail_id
	 * @param order_id
	 * @return
	 */
	private boolean testIsExist(String order_detail_id, String order_id) {
		boolean isExist = false;
		ReturnsInfo rinfo = new ReturnsInfo();
		rinfo.setOrder_info_details_id(Integer.valueOf(order_detail_id));
		rinfo = super.getFacade().getReturnsInfoService().getReturnsInfo(rinfo);

		if (null != rinfo) {
			OrderInfo oi = new OrderInfo();
			Map map = new HashMap();
			map.put("detailId", rinfo.getOrder_info_details_id());
			// 取得订单的信息
			oi = super.getFacade().getOrderInfoService().getOrderInfoByDetailId(map);

			if (null != oi) {
				String orderid = oi.getId() + "";
				if (orderid.equals(order_id)) {
					isExist = true;

				}

			}
		}

		return isExist;
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
		 * StringBuffer pathBuffer = new StringBuffer();
		 * pathBuffer.append("/customer/ReturnsInfo.do?method=addSuccess"); logger.info(">>>>>>>>>>>>>>跳转url=[" +
		 * pathBuffer.toString() + "]"); // /customer/ReturnsInfo.do?method=list ActionForward forward = new
		 * ActionForward(pathBuffer.toString(), true); return forward;
		 */
		return mapping.findForward("input");
	}

	public ActionForward saveAll(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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
		String[] pks = request.getParameterValues("pks");
		// 定位勾选了哪些 checkbox
		String[] _index_check = request.getParameterValues("_index_check");
		// [1727, 1726]
		String[] comm_count = filterArray(_index_check, request.getParameterValues("comm_count"));
		// [2, 1, 1]
		String[] comm_id = filterArray(_index_check, request.getParameterValues("comm_id"));
		// [1085, 1085, 1062]
		String[] comm_name = filterArray(_index_check, request.getParameterValues("comm_name"));
		String return_type = (String) dynaBean.get("return_type");

		String[] comm_price = filterArray(_index_check, request.getParameterValues("comm_price"));
		// [9.99, 15, 2000]
		String[] activity_price = filterArray(_index_check, request.getParameterValues("activity_price"));

		String[] return_desc = filterArray(_index_check, request.getParameterValues("return_desc"));
		// [, , ]
		String order_id = (String) dynaBean.get("order_id");
		// 订单明细的id

		if (StringUtils.isNotBlank(order_id)) {
			ReturnsInfo rinfo = new ReturnsInfo();
			// 订单详细id
			rinfo.setOrder_info_details_id(Integer.valueOf(order_id));
			// 退货标志
			rinfo.setApply_type(0);
			// 申请凭证
			rinfo.setReturn_type(Integer.valueOf(return_type));
			rinfo.setApply_proof(UUID.randomUUID().toString().replace("-", ""));
			String audit_status = "0";
			rinfo.setAudit_status(Integer.valueOf(audit_status));
			// 用户id
			rinfo.setUser_id(ui.getId());
			// 用户名称
			rinfo.setUser_name(ui.getUser_name());
			// 添加日期
			rinfo.setAdd_date(new Date());

			/**
			 * 计算总价格
			 */
			BigDecimal total_price_ = new BigDecimal(0);

			for (int i = 0; i < comm_price.length; i++) {
				String price = comm_price[i];
				String ap = activity_price[i];
				// 如果有促销价格,则用促销价格*商品数量
				String p = price;
				if (!ap.equals("0")) {
					p = ap;
				}

				if (StringUtils.isNotBlank(price)) {
					BigDecimal price_ = new BigDecimal(p);
					BigDecimal count_ = new BigDecimal(comm_count[i]);
					total_price_ = total_price_.add(price_.multiply(count_));

				}

			}

			// 如果使用优惠券，并且在线支付，只能退回除去优惠券的钱
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setId(Integer.valueOf(order_id));
			orderInfo = super.getFacade().getOrderInfoService().getOrderInfo(orderInfo);
			if (null != orderInfo) {
			}

			/**
			 * 处理附件信息
			 */

			Map newMap = new HashMap();
			List<UploadFile> uploadFileList = super.uploadFile(form, true, true, Keys.NEWS_INFO_IMAGE_SIZE);
			for (UploadFile uploadFile : uploadFileList) {
				logger.info("地址=[" + uploadFile.getFormName() + "][" + uploadFile.getFileSavePath() + "]" + "["
						+ uploadFile.getContentType() + "]"); // image/png

				newMap = getFileInfoByName(newMap, uploadFile.getFormName(), null, 2, uploadFile.getFileSavePath());

			}

			// String totle_price = "100";
			// 退货商品总金额,需要一个逻辑算法来处理
			rinfo.setTotal_price(total_price_);
			// 退货成功标志 0:未确认 1:已确认
			rinfo.setIs_confirm(0);

			rinfo.setComm_count(0);

			Integer rinfo_id = super.getFacade().getReturnsInfoService().createReturnsInfo(rinfo);

			if (null != pks) {
				int len = pks.length;
				for (int i = 0; i < len; i++) {
					String id = pks[i];

					ReturnsSwapDetail rsd = new ReturnsSwapDetail();
					// 订单明细id
					rsd.setOrder_info_details_id(Integer.valueOf(id));
					// apply_type
					rsd.setApply_type(0);
					// comm_id
					rsd.setComm_id(Integer.valueOf(comm_id[i]));

					String _comm_price = filterNull(comm_price[i], "0");
					rsd.setComm_price(new BigDecimal(_comm_price));

					String _comm_name = filterNull(comm_name[i], "");
					rsd.setComm_name(_comm_name);

					String _comm_count = filterNull(comm_count[i], "");
					rsd.setComm_count(Integer.valueOf(_comm_count));

					// return_desc
					rsd.setReturns_info_id(rinfo_id);
					rsd.setReturn_desc(return_desc[i]);
					// 用户id
					rsd.setUser_id(ui.getId());
					// 用户名称
					rsd.setUser_name(ui.getUser_name());
					// 添加日期
					rsd.setAdd_date(new Date());

					Integer rsd_id = super.getFacade().getReturnsSwapDetailService().createReturnsSwapDetail(rsd);

					Iterator it = newMap.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<String, List> entry = (Map.Entry<String, List>) it.next();
						// image/png
						if (entry.getKey().equals(id)) {
							List<String> list = new ArrayList<String>();
							list = entry.getValue();
							for (String fileSavePath : list) {
								ReturnsInfoFj fj = new ReturnsInfoFj();
								fj.setAdd_date(new Date());
								// 父表的id
								fj.setC_returns_info_id(rsd_id);
								// 父表的父表的id
								fj.setReturns_info_id(rinfo_id);
								fj.setFj_type(0);
								fj.setFj_addr(fileSavePath);

								super.getFacade().getReturnsInfoFjService().createReturnsInfoFj(fj);

							}

						}

					}

					// }

				}

			}
		}

		return new ActionForward("/customer/ReturnsInfo.do?method=list", true);
	}

	/**
	 * List 去重并保持顺序
	 * 
	 * @param list
	 * @return
	 */
	private List removeDuplicateWithOrder(List list) {

		Set set = new HashSet();

		List newList = new ArrayList();

		for (Iterator iter = list.iterator(); iter.hasNext();) {

			Object element = iter.next();

			if (set.add(element)) {
				newList.add(element);
			}

		}

		list.clear();

		list.addAll(newList);
		return list;

	}

	/**
	 * 解决n行数据中,每行数据包含m个附件, 一般情况下,会使用双重for循环,这样, 图片上传的方法会遍历 n*m次, 但此方法只需要m次即可,
	 * 
	 * @param map 存储id和附件列表的映射集合
	 * @param fileName 附件的表单名称,
	 * @param separator 分隔符 ,如果不传,或者为null ,则默认给定"_"
	 * @param id_index 表单名称中 唯一标识 id 所在的位置
	 * @param fileSavePath 附件上传的地址
	 * @return 存储id和附件列表的映射集合
	 */
	private Map getFileInfoByName(Map map, String fileName, String separator, int id_index, String fileSavePath) {

		separator = filterNull(separator, "_");
		// Map map = new HashMap();
		String id = fileName.split(separator)[id_index];
		// 先检测map中是否包含此id的数据,如果没有,则新增,如果有,则取出,添加,
		if (map.size() == 0) {
			// 如果结合为空,则添加第一个
			List<String> list = new ArrayList<String>();
			list.add(fileSavePath);
			list = removeDuplicateWithOrder(list);
			map.put(id, list);

		} else {
			List<String> list = new ArrayList();
			if (map.get(id) != null) {
				// 如果在集合中找到了 key=id的数据,则取出,添加完毕后,再次放入
				list = (List) map.get(id);
				list.add(fileSavePath);
				list = removeDuplicateWithOrder(list);
				map.put(id, list);

			} else {
				// 如果没有key =id 的数据,则新增数据,放入集合
				list.add(fileSavePath);
				list = removeDuplicateWithOrder(list);
				map.put(id, list);
			}

		}

		return map;

	}

	private String[] filterArray(String[] index, String str[]) {
		String[] newstr = new String[index.length];

		for (int i = 0; i < index.length; i++) {

			int _index = Integer.parseInt(index[i]);

			newstr[i] = str[_index];

		}
		return newstr;

	}

	private String filterNull(String str, String defaultvalue) {
		String v = "";
		if (str == null) {
			v = defaultvalue;
		} else {
			if (str.trim().equals("") || str.trim().equalsIgnoreCase("null")) {
				v = defaultvalue;
			} else {
				v = str;
			}
		}
		return v;
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
		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		String st_date = (String) dynaBean.get("st_date");
		String en_date = (String) dynaBean.get("en_date");
		String audit_state_check = (String) dynaBean.get("audit_state_check");
		String trade_index = (String) dynaBean.get("trade_index");
		String pay_type = (String) dynaBean.get("pay_type");
		String _audit_status = request.getParameter("audit_status");
		String th_type = (String) dynaBean.get("th_type");

		Pager pager = (Pager) dynaBean.get("pager");
		ReturnsInfo entity = new ReturnsInfo();

		entity.getMap().put("st_date", st_date);
		entity.getMap().put("en_date", en_date);
		entity.getMap().put("audit_state_check", audit_state_check);

		if (ui.getUser_type() == 3l) {

			String[] audit_status = { "0", "1", "-1", "2", "-2", "3", "-3" };
			entity.getMap().put("has_audit_status", audit_status);
		}
		if (StringUtils.isNotBlank(_audit_status)) {
			entity.setAudit_status(Integer.valueOf(_audit_status));
			request.setAttribute("_audit_status", _audit_status);
		}

		if (StringUtils.isNotBlank(th_type)) {
			entity.setTh_type(Integer.valueOf(th_type));
		}

		entity.getMap().put("trade_index", trade_index);
		entity.getMap().put("pay_type", pay_type);

		if (ui.getUser_type() == 3l) {

			Integer ent_id = ui.getOwn_entp_id();
			if (ent_id != null) {
				entity.getMap().put("entp_id_equal", ent_id);
			}
			Integer userId = ui.getId();
			if (userId != null) {
				entity.getMap().put("owner_id_equal", userId);
			}
		} else if (ui.getUser_type() == 2l) {
			Integer userId = ui.getId();
			if (userId != null) {
				entity.getMap().put("owner_id_equal", userId);
			}
		}

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
	 * 商家审核页面展示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dealerAudit_show(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		super.setPublicInfoListWithEntpAndCustomer(request);

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
		return new ActionForward("/customer/ReturnsInfo/dealerAuditform.jsp");

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
	public ActionForward dealerAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
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

		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		String send_wl = (String) dynaBean.get("send_wl");
		String send_wl_price = (String) dynaBean.get("send_wl_price");
		String return_wl = (String) dynaBean.get("return_wl");
		String return_wl_price = (String) dynaBean.get("return_wl_price");
		String total_price = (String) dynaBean.get("total_price");
		String memo = (String) dynaBean.get("memo");
		// 是否通过标志
		String dealerAuditStatus = (String) dynaBean.get("dealerAuditStatus");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));

			entity.setReturn_wl(Integer.valueOf(return_wl));
			entity.setReturn_wl_price(new BigDecimal(return_wl_price));
			entity.setSend_wl(Integer.valueOf(send_wl));
			entity.setSend_wl_price(new BigDecimal(send_wl_price));

			entity.setTotal_price(new BigDecimal(total_price));
			entity.setMemo(memo);

			// 设置状态

			if (dealerAuditStatus.equals("Y")) {

				entity.setAudit_status(1);

			} else if (dealerAuditStatus.equals("N")) {
				entity.setAudit_status(-1);
				entity.setIs_confirm(1);
			}

			super.getFacade().getReturnsInfoService().modifyReturnsInfo(entity);

		}

		String msg = "审核完成!";

		super.renderJavaScript(response, "window.onload=function(){alert('" + msg + "');window.parent.location.href='"
				+ request.getContextPath() + "/manager/customer/ReturnsInfo.do?method=list'}");
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

		super.setPublicInfoListWithEntpAndCustomer(request);

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
		return new ActionForward("/customer/ReturnsInfo/checkAuditform.jsp");

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

		super.setPublicInfoListWithEntpAndCustomer(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
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
				+ request.getContextPath() + "/manager/customer/ReturnsInfo.do?method=list'}");
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

		super.setPublicInfoListWithEntpAndCustomer(request);
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
		return new ActionForward("/customer/ReturnsInfo/finishAuditform.jsp");

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

		setNaviStringToRequestScope(request);

		DynaBean dynaBean = (DynaBean) form;
		// 退货的id
		String id = (String) dynaBean.get("id");
		// String id = request.getParameter("id");

		// 是否通过标志
		String finishAuditStatus = (String) dynaBean.get("finishAuditStatus");
		// String id_1 = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));

			// 设置状态

			if (finishAuditStatus.equals("Y")) {

				ReturnsInfo rinfo = new ReturnsInfo();
				rinfo.setId(Integer.valueOf(id));
				rinfo = super.getFacade().getReturnsInfoService().getReturnsInfo(rinfo);
				// +库存 -销量 -积分
				super.getFacade().getReturnsInfoService().caculateCreditsAndStockAndSales(rinfo);
				// 资金结算

			}

		}

		return new ActionForward("/customer/ReturnsInfo.do?method=list", true);

	}

	public ActionForward audit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		saveToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		if (StringUtils.isNotBlank(id)) {
			ReturnsInfo entity = new ReturnsInfo();
			entity.setId(Integer.valueOf(id));
			entity = super.getFacade().getReturnsInfoService().getReturnsInfo(entity);
			if (null != entity) {
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

				BaseData baseData = new BaseData();
				baseData.setId((Integer) entity.getReturn_type());
				baseData.setIs_del(0);
				baseData = super.getFacade().getBaseDataService().getBaseData(baseData);
				entity.setBaseData(baseData);

				request.setAttribute("entity", entity);
			}
		}

		return new ActionForward("/customer/ReturnsInfo/audit.jsp");

	}

	public ActionForward saveAudit(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UserInfo ui = super.getUserInfoFromSession(request);
		if (null == ui) {
			String msg = "您还未登录，请先登录系统！";
			super.renderJavaScript(response, "window.onload=function(){alert('" + msg
					+ "');location.href='../../login.shtml'}");
			return null;
		}
		resetToken(request);
		DynaBean dynaBean = (DynaBean) form;
		String id = (String) dynaBean.get("id");
		ReturnsInfo entity = new ReturnsInfo();
		super.copyProperties(entity, form);
		if (StringUtils.isNotBlank(id)) {
			entity.setId(Integer.valueOf(id));
			super.getFacade().getReturnsInfoService().modifyReturnsInfo(entity);
		}

		super.renderJavaScript(response, "window.parent.location.href='ReturnsInfo.do?method=list" + "'");
		return null;

	}

}
