package com.ebiz.webapp.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import push.AppPush;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ebiz.webapp.dao.BaseAuditRecordDao;
import com.ebiz.webapp.dao.BaseDataDao;
import com.ebiz.webapp.dao.CommInfoDao;
import com.ebiz.webapp.dao.CommInfoPoorsDao;
import com.ebiz.webapp.dao.CommTczhPriceDao;
import com.ebiz.webapp.dao.CommentInfoDao;
import com.ebiz.webapp.dao.EntpInfoDao;
import com.ebiz.webapp.dao.OrderInfoDao;
import com.ebiz.webapp.dao.OrderInfoDetailsDao;
import com.ebiz.webapp.dao.OrderReturnInfoDao;
import com.ebiz.webapp.dao.PoorCuoShiDao;
import com.ebiz.webapp.dao.ServiceCenterInfoDao;
import com.ebiz.webapp.dao.SysOperLogDao;
import com.ebiz.webapp.dao.TongjiDao;
import com.ebiz.webapp.dao.UserBiRecordDao;
import com.ebiz.webapp.dao.UserInfoDao;
import com.ebiz.webapp.dao.UserJifenRecordDao;
import com.ebiz.webapp.dao.UserRelationParDao;
import com.ebiz.webapp.dao.UserScoreRecordDao;
import com.ebiz.webapp.dao.VillageInfoDao;
import com.ebiz.webapp.domain.BaseAuditRecord;
import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.CommInfo;
import com.ebiz.webapp.domain.CommInfoPoors;
import com.ebiz.webapp.domain.CommTczhPrice;
import com.ebiz.webapp.domain.CommentInfo;
import com.ebiz.webapp.domain.EntpInfo;
import com.ebiz.webapp.domain.JdAreas;
import com.ebiz.webapp.domain.OrderInfo;
import com.ebiz.webapp.domain.OrderInfoDetails;
import com.ebiz.webapp.domain.OrderReturnInfo;
import com.ebiz.webapp.domain.PdImgs;
import com.ebiz.webapp.domain.ServiceCenterInfo;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.domain.UserBiRecord;
import com.ebiz.webapp.domain.UserInfo;
import com.ebiz.webapp.service.AutoRunService;
import com.ebiz.webapp.service.Facade;
import com.ebiz.webapp.util.JdApiUtil;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.util.DateTools;
import com.ebiz.webapp.web.util.FileTools;
import com.ebiz.webapp.web.util.HttpUtils;
import com.ebiz.webapp.web.util.WeiXinSendMessageUtils;
import com.ebiz.webapp.web.util.dysms.DySmsUtils;
import com.ebiz.webapp.web.util.dysms.SmsTemplate;

/**
 * @author XingXiuDong
 * @version 2010.07.06
 */
@Service
@SuppressWarnings("unused")
public class AutoRunServiceImpl extends BaseImpl implements AutoRunService, ApplicationContextAware,
		ServletContextAware {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String DEFAULT_HTML_FILE_NAME = "index.html";

	private Facade facade;

	private ServletContext servletContext;

	private HttpServletRequest request;

	private HttpServletResponse response;

	@Resource
	private OrderInfoDao orderInfoDao;

	@Resource
	private BaseDataDao baseDataDao;

	@Resource
	private UserInfoDao userInfoDao;

	@Resource
	private UserScoreRecordDao userScoreRecordDao;

	@Resource
	private UserRelationParDao userRelationParDao;

	@Resource
	private UserBiRecordDao userBiRecordDao;

	@Resource
	private EntpInfoDao entpInfoDao;

	@Resource
	private ServiceCenterInfoDao serviceCenterInfoDao;

	@Resource
	private UserJifenRecordDao userJifenRecordDao;

	@Resource
	private TongjiDao tongjiDao;

	@Resource
	private BaseAuditRecordDao baseAuditRecordDao;

	@Resource
	private OrderReturnInfoDao orderReturnInfoDao;

	@Resource
	private OrderInfoDetailsDao orderInfoDetailsDao;

	@Resource
	private CommTczhPriceDao commTczhPriceDao;

	@Resource
	private CommInfoDao commInfoDao;

	@Resource
	private SysOperLogDao sysOperLogDao;

	@Resource
	private CommInfoPoorsDao commInfoPoorsDao;

	@Resource
	private VillageInfoDao villageInfoDao;

	@Resource
	private PoorCuoShiDao poorCuoShiDao;

	@Resource
	private CommentInfoDao commentInfoDao;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.facade = (Facade) applicationContext.getBean("facade");
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 获取自身所在区域信息
	 */
	public BaseProvince getBaseProvinceByUserId(Integer user_id) {

		UserInfo ui = new UserInfo();
		ui.setId(user_id);
		ui = facade.getUserInfoService().getUserInfo(ui);
		if (null != ui && null != ui.getP_index()) {
			if (!"00".equals(ui.getP_index().toString().substring(4, 6))) {// 会员所在区域到区县
				BaseProvince baseProvince = new BaseProvince();
				baseProvince.setP_index(ui.getP_index().longValue());
				baseProvince = facade.getBaseProvinceService().getBaseProvince(baseProvince);
				if (null != baseProvince) {
					return baseProvince;
				}
			}
		}

		return null;
	}

	// 5、自动确认收货更新，每天晚上3点半执行
	@Override
	public void autoConfirmReceipt() {
		logger.warn("==autoConfirmReceipt begin==");

		SysSetting sysSetting = new SysSetting();
		sysSetting.setTitle("autoConfirmReceiptDays");
		sysSetting = facade.getSysSettingService().getSysSetting(sysSetting);// 从sysSetting获取天数

		// 从sysSetting获取延迟收货天数
		SysSetting sysSettingFaHuoYanDays = new SysSetting();
		sysSettingFaHuoYanDays.setTitle("fahuoShouhuoYanDays");
		sysSettingFaHuoYanDays = facade.getSysSettingService().getSysSetting(sysSettingFaHuoYanDays);

		if ((null != sysSetting) && (null != sysSetting.getContent())) {

			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_20.getIndex());
			List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);
			if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
				for (OrderInfo oi : orderInfoList) {
					// 判断当前时间和下单时间相差的天数
					int autoDays = Integer.valueOf(sysSetting.getContent()).intValue();
					Integer days = Integer.valueOf(DurationFormatUtils.formatDuration(new Date().getTime()
							- oi.getOrder_date().getTime(), "d"));

					// 证明延迟收货
					if (oi.getDelay_shouhuo().intValue() == 1) {
						autoDays = autoDays + Integer.valueOf(sysSettingFaHuoYanDays.getContent()).intValue();
					}

					logger.warn("==new Date().getTime()==" + new Date().getTime() + " order_date"
							+ oi.getOrder_date().getTime() + "差距" + days + " --days >= autoDays:" + (days >= autoDays)
							+ " oi.getDelay_shouhuo().intValue()=" + oi.getDelay_shouhuo().intValue());
					logger.warn("autoDays" + autoDays);

					// 判断如果进行了
					if (days >= autoDays) {
						oi.setOrder_state(Keys.OrderState.ORDER_STATE_40.getIndex());
						oi.setQrsh_date(new Date());
						oi.getMap().put("xiaofei_success_update_link_table", true);
						int count = facade.getOrderInfoService().modifyOrderInfo(oi);
					}

				}
			}
		}
		logger.warn("==autoConfirmReceipt end==");
	}

	// 5、24小时自动退款
	@Override
	public void autoTuiKuanAudit() {
		logger.warn("==autoTuiKuanAudit begin==");
		BaseAuditRecord baseAuditRecord = new BaseAuditRecord();
		baseAuditRecord.setOpt_type(Keys.OptType.OPT_TYPE_11.getIndex());
		baseAuditRecord.setLink_table("OrderReturnInfo");
		baseAuditRecord.setAudit_state(0);
		List<BaseAuditRecord> baseAuditRecordList = baseAuditRecordDao.selectEntityList(baseAuditRecord);

		if (null != baseAuditRecordList && baseAuditRecordList.size() > 0) {
			for (BaseAuditRecord temp : baseAuditRecordList) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(temp.getAdd_date());
				SimpleDateFormat sd_ymd = new SimpleDateFormat("yyyy-MM-dd");
				String add_time = sd_ymd.format(cal.getTime());
				cal.add(Calendar.DAY_OF_MONTH, 1);
				String now_time = sd_ymd.format(new Date());
				try {
					Date add_date = sd_ymd.parse(add_time);
					Date now_date = sd_ymd.parse(now_time);
					String days = DurationFormatUtils.formatDuration(now_date.getTime() - add_date.getTime(), "H");
					logger.info("===now_time:" + now_time);
					logger.info("===add_date:" + add_date);
					logger.info("===days:" + days);
					// 目前只有未发货退货 24小时直接退货
					if (StringUtils.isNotBlank(days) && Integer.valueOf(days).intValue() >= 24) {
						OrderReturnInfo orderReturnInfo = new OrderReturnInfo();
						orderReturnInfo.setId(temp.getLink_id());
						orderReturnInfo.setAudit_state(Keys.audit_state.audit_state_0.getIndex());
						orderReturnInfo.setIs_del(0);
						orderReturnInfo = orderReturnInfoDao.selectEntity(orderReturnInfo);
						if (null != orderReturnInfo) {
							OrderInfo orderInfo = new OrderInfo();
							orderInfo.setId(orderReturnInfo.getOrder_id());
							orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_15.getIndex());
							orderInfo = orderInfoDao.selectEntity(orderInfo);
							if (null != orderInfo) {
								// 更新订单状态为退货
								OrderInfo updateOrder = new OrderInfo();
								updateOrder.setId(orderInfo.getId());
								updateOrder.setOrder_state(Keys.OrderState.ORDER_STATE_X20.getIndex());
								updateOrder.setUpdate_date(new Date());
								updateOrder.setUpdate_user_id(1);
								orderInfoDao.updateEntity(updateOrder);

								// 更新订单明细退货字段为1
								OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
								orderInfoDetails.setOrder_id(orderInfo.getId());
								List<OrderInfoDetails> orderInfoDetailsList = this.orderInfoDetailsDao
										.selectEntityList(orderInfoDetails);
								if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
									for (OrderInfoDetails cur : orderInfoDetailsList) {
										OrderInfoDetails updateIsTuohuo = cur;
										updateIsTuohuo.setIs_tuihuo(1);
										this.orderInfoDetailsDao.updateEntity(updateIsTuohuo);
									}
								}

								// 更新退货表审核通过
								OrderReturnInfo updateOrderReturnInfo = new OrderReturnInfo();
								updateOrderReturnInfo.setId(orderReturnInfo.getId());
								updateOrderReturnInfo.setAudit_state(Keys.audit_state.audit_state_2.getIndex());
								updateOrderReturnInfo.setIs_confirm(1);
								updateOrderReturnInfo.setTk_status(1);
								updateOrderReturnInfo.setAudit_date(new Date());
								updateOrderReturnInfo.setAudit_user_id(Keys.SYS_ADMIN_ID);
								orderReturnInfoDao.updateEntity(updateOrderReturnInfo);

								// 审核表审核通过
								BaseAuditRecord updateBaseAuditRecord = new BaseAuditRecord();
								updateBaseAuditRecord.setId(temp.getId());
								updateBaseAuditRecord.setAudit_state(1);
								updateBaseAuditRecord.setAudit_date(new Date());
								updateBaseAuditRecord.setAudit_user_id(1);
								updateBaseAuditRecord.setAudit_note("24小时自动审核通过");
								baseAuditRecordDao.updateEntity(updateBaseAuditRecord);

								modifyTuiKuanAuditState(orderReturnInfo.getId(), null, orderInfo, null,
										orderReturnInfoDao, baseAuditRecordDao);
								modifyOrderInfoForTk(orderInfo.getId(), orderInfoDao, userInfoDao, baseDataDao,
										userBiRecordDao);
								modifyOrderInfoCommSaleCountAndInventory(orderInfo.getId(), 1, orderInfoDetailsDao,
										commTczhPriceDao, commInfoDao, entpInfoDao);
							}
						}
					}
				} catch (ParseException e1) {
					e1.printStackTrace();
				}

			}
		}
		logger.warn("==autoTuiKuanAudit end==");
	}

	// 6、自动取消订单更新，每1个小时执行一次
	@Override
	public void autoOrderCancel() {
		logger.warn("==autoOrderCancel==");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());// 已预订
		// TODO 自动取消订单更新，每1个小时执行一次
		orderInfo.getMap().put("order_type_in", "10,7,30,100");
		// orderInfo.getMap().put("order_date_diff_ge_1",
		// DateTools.getStringDate(new Date()));// 下单时间与当前时间差大于等于1小时
		orderInfo.getMap().put("order_date_diff_ge_h_72", DateTools.getStringDate(new Date()));// 下单时间与当前时间差大于等于30分钟

		List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo order : orderInfoList) {
				facade.getOrderInfoService().modifyOrderInfoForCancel(order);
			}
		}

	}

	// 9、合伙人到期自动取消，每天4:10点执行一次
	@Override
	public void autoServiceCancel() {
		logger.warn("==autoServiceCancel==");
		ServiceCenterInfo serviceCenterInfo = new ServiceCenterInfo();
		serviceCenterInfo.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		serviceCenterInfo.setAudit_state(1);// 审核通过
		serviceCenterInfo.setPay_success(1);// 已支付
		serviceCenterInfo.setEffect_state(1);// 已生效
		serviceCenterInfo.getMap().put("effect_end_date_not_null", "true");
		serviceCenterInfo.getMap().put("effect_end_date_le",
				DateTools.getStringDate(new Date(), "yyyy-MM-dd") + " 23:59:59");
		List<ServiceCenterInfo> serviceCenterInfoList = facade.getServiceCenterInfoService().getServiceCenterInfoList(
				serviceCenterInfo);
		if (null != serviceCenterInfoList && serviceCenterInfoList.size() > 0) {
			for (ServiceCenterInfo t : serviceCenterInfoList) {
				ServiceCenterInfo temp = new ServiceCenterInfo();
				temp.setId(t.getId());
				temp.setAudit_state(-1);// 审核状态
				temp.setPay_success(0);// 支付
				temp.setEffect_state(0);// 生效
				temp.getMap().put("cancel_link_user_info_is_fuwu", "true");
				temp.getMap().put("user_id", t.getAdd_user_id());
				temp.setP_index(t.getP_index());

				temp.setUpdate_date(new Date());
				temp.setUpdate_user_id(1);
				temp.setAudit_date(new Date());
				temp.setAudit_user_id(1);
				temp.setAudit_desc("审核不通过");

				// ---操作日志---
				temp.getMap().put("cancel_oper_uid", "-1");
				temp.getMap().put("cancel_oper_uname", "定时任务");
				// ---操作日志---
				facade.getServiceCenterInfoService().modifyServiceCenterInfo(temp);
			}
		}
	}

	// 交易完成后7天，关闭订单并发放推广奖励和计算待返余额，每天1点执行一次
	@Override
	public void modifyAutoCloseOrder() {
		logger.warn("==autoCloseOrder start==");
		SysSetting sysSetting = new SysSetting();
		sysSetting.setTitle("closeOrderDays");
		sysSetting = facade.getSysSettingService().getSysSetting(sysSetting);// 从sysSetting获取天数

		if ((null != sysSetting) && (null != sysSetting.getContent())) {
			int autoDays = Integer.valueOf(sysSetting.getContent()).intValue();
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_40.getIndex());
			List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);
			if ((null != orderInfoList) && (orderInfoList.size() > 0)) {
				for (OrderInfo oi : orderInfoList) {
					// 判断当前时间和下单时间相差的天数
					if (null != oi.getQrsh_date()) {
						Calendar cal = Calendar.getInstance();
						cal.setTime(oi.getQrsh_date());
						SimpleDateFormat sd_ymd = new SimpleDateFormat("yyyy-MM-dd");
						String day_prev = sd_ymd.format(cal.getTime());
						cal.setTime(new Date());
						String day_now = sd_ymd.format(cal.getTime());
						try {
							Date qrsh_date = sd_ymd.parse(day_prev);
							Date now = sd_ymd.parse(day_now);
							String days = DurationFormatUtils.formatDuration(now.getTime() - qrsh_date.getTime(), "d");
							if (StringUtils.isNotBlank(days) && Integer.valueOf(days).intValue() >= autoDays) {
								oi.setOrder_state(Keys.OrderState.ORDER_STATE_50.getIndex());
								oi.setFinish_date(new Date());
								oi.getMap().put("opt_order_state", "40");
								int count = facade.getOrderInfoService().modifyOrderInfo(oi);

								if (count > 0) {
									// 生成商家服务费订单、商家货款发放、推广奖励和计算待返余额
									// super.reckonBonusAndBalance(oi.getId(),
									// orderInfoDao, orderInfoDetailsDao,
									// userInfoDao,
									// userScoreRecordDao, userRelationParDao,
									// userBiRecordDao, entpInfoDao,
									// serviceCenterInfoDao, new Date(),
									// baseDataDao, userJifenRecordDao,
									// tongjiDao, commInfoDao,commInfoPoorsDao,
									// villageInfoDao);

									// 待返金额正式发放给用户
									super.grantBiLockToUser(oi.getId(), orderInfoDao, orderInfoDetailsDao, userInfoDao,
											userBiRecordDao, entpInfoDao, baseDataDao, userJifenRecordDao,
											poorCuoShiDao);
								}
							}
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

		}
		logger.warn("==autoCloseOrder end==");
	}

	@Override
	public void newOrderTip() {

		logger.warn("==newOrderTip end==");
		String title = "";
		String content = "";
		String url = "";
		boolean is_product = true;// TODO 正式发布的时候改成ture

		OrderInfo orderInfo = new OrderInfo();
		orderInfo.getMap().put("st_pay_date_queryNewOrder", getTimeByMinute(-5));
		orderInfo.getMap().put("en_pay_date_queryNewOrder", new Date());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());
		orderInfo.getMap().put("order_type_in", Keys.OrderType.ORDER_TYPE_10.getIndex() + "");

		List<OrderInfo> list = this.orderInfoDao.selectEntityList(orderInfo);
		if (null != list && list.size() > 0) {
			UserInfo userInfo = null;
			for (OrderInfo cur : list) {

				logger.warn("==newOrderTip ==" + cur.getTrade_index());

				userInfo = new UserInfo();
				userInfo.setOwn_entp_id(cur.getEntp_id());
				userInfo.setIs_del(0);
				userInfo = this.userInfoDao.selectEntity(userInfo);
				if (null != userInfo) {
					title = "您有新的订单，请注意查收。";
					content = "您有新的订单，请注意查收。";
					// share.put("share_url", "http://" + Keys.app_domain +
					// "/m/MEntpInfo.do?method=index&id=" +
					url = "http://" + Keys.app_domain + "/m/MMyOrderEntp.do?method=list&order_type="
							+ cur.getOrder_type();

					// 插入推送记录
					super.createSysOperLog(Keys.SysOperType.SysOperType_60.getIndex(), "订单APP推送记录", "订单APP推送记录",
							userInfo, sysOperLogDao);

					if (null != userInfo.getDevice_token_app()) {
						if (userInfo.getDevice_token_app().equals("android")) {
							try {
								AppPush.sendAndroidUnicastForNews(userInfo.getDevice_token(), title, content, url,
										is_product);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else if (userInfo.getDevice_token_app().equals("ios")) {
							try {
								AppPush.sendIOSUnicastForNews(userInfo.getDevice_token(), title, url, is_product);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

				}
			}
		}
	}

	/**
	 * 自动同步京东商品
	 */
	@Override
	public void autoSyncJdProduct() {
		logger.warn("==autoSyncJdProduct start==");
		String info = "";
		String save_path = Keys.web_local_dir;
		try {
			HttpClient httpclient = new HttpClient();

			boolean next_page_flag = true;// 满足查询下一页条件
			// int current_page = 1;// 当前页数
			int skipCount = 0;// 忽略记录条数默认为0
			int limitCount = 100;// 每次查询的记录条数
			while (true) {
				// **************查询京东自营商品列表 start***************//

				info = JdApiUtil.getJdProductList(skipCount, limitCount);

				// logger.info(info);
				// **************查询京东自营商品列表 end***************//
				if (StringUtils.isNotBlank(info)) {
					JSONObject obj = JSONObject.parseObject(info);
					if (null != obj && null != obj.get("StatusCode")) {
						if (Keys.JD_API_RESULT_STATUS_CODE.equals(obj.get("StatusCode").toString())) {// 接口查询成功
							String data_str = JSONObject.toJSONString(obj.get("Data"));
							JSONArray products = JSONArray.parseArray(data_str);// 商品列表
							if (null != products && products.size() > 0) {
								for (int j = 0; j < products.size(); j++) {
									// **************根据skuid查询京东自营商品详情 start***************//
									JSONObject jo = products.getJSONObject(j);

									String product_str = JdApiUtil
											.getJdProductInfo(Integer.valueOf(jo.getString("sku")));

									// **************根据skuid查询京东自营商品详情 end***************//
									JSONObject product = JSONObject.parseObject(product_str);
									if (null != product && null != product.get("StatusCode")) {
										if (Keys.JD_API_RESULT_STATUS_CODE.equals(product.get("StatusCode").toString())) {// 接口查询成功
											// **************同步京东自营商品 start***************//
											String product_result_str = JSONObject.toJSONString(product.get("Data"));
											JSONObject pobj = JSONObject.parseObject(product_result_str);
											if (null != pobj) {
												CommInfo commInfo = new CommInfo();
												commInfo.setJd_skuid(null != pobj.get("sku") ? pobj.get("sku")
														.toString() : "");
												commInfo.setIs_zingying(Keys.CommZyType.COMM_ZY_TYPE_1.getIndex());// 京东自营商品
												commInfo.setIs_del(0);
												int count = facade.getCommInfoService().getCommInfoCount(commInfo);
												if (count == 0) {// 未同步商品，需进行同步

													// 默认商品分类
													commInfo.setCls_id(20045);
													commInfo.setCls_name("其他");
													commInfo.setPar_cls_id(20044);
													commInfo.setRoot_cls_id(20043);

													// **************查询京东的商品分类 start***************//
													// 京东商品分类匹配
													String category = null != pobj.get("category") ? pobj.get(
															"category").toString() : "";
													String[] categorys = category.split(";");
													if (null != categorys && categorys.length > 0) {
														// 京东商品分类接口调用

														String category_str = JdApiUtil
																.getJdCategoryInfo(categorys[categorys.length - 1]);// 获取三级分类信息
														JSONObject category_result = JSONObject
																.parseObject(category_str);
														if (null != category_result
																&& null != category_result.get("StatusCode")) {
															if (Keys.JD_API_RESULT_STATUS_CODE.equals(category_result
																	.get("StatusCode").toString())) {// 接口查询成功
																String category_result_str = JSONObject
																		.toJSONString(category_result.get("Data"));
																JSONObject cobj = JSONObject
																		.parseObject(category_result_str);
																if (null != cobj && null != cobj.get("name")) {
																	BaseClass baseClass = new BaseClass();
																	baseClass.setCls_level(3);
																	baseClass.setIs_del(0);
																	baseClass.getMap().put("cls_name_like",
																			cobj.get("name").toString());
																	List<BaseClass> baseClassList = facade
																			.getBaseClassService().getBaseClassList(
																					baseClass);
																	if (null != baseClassList
																			&& baseClassList.size() > 0) {
																		commInfo.setCls_id(baseClassList.get(0)
																				.getCls_id());
																		commInfo.setCls_name(baseClassList.get(0)
																				.getCls_name());
																		commInfo.setPar_cls_id(baseClassList.get(0)
																				.getPar_id());
																		commInfo.setRoot_cls_id(baseClassList.get(0)
																				.getRoot_id());
																	}
																}

															}
														}
													}
													// **************查询京东的商品分类 end***************//

													commInfo.setComm_name(null != pobj.get("name") ? pobj.get("name")
															.toString() : "");
													commInfo.setComm_no("JD" + commInfo.getJd_skuid());
													commInfo.setComm_type(Keys.CommType.COMM_TYPE_2.getIndex());// 实物商品
													commInfo.setOwn_entp_id(Integer.valueOf(Keys.jd_entp_id));// 京东自营店铺

													PdImgs pdImgs = new PdImgs();
													String resFilePath = (null != pobj.get("imagePath") ? pobj.get(
															"imagePath").toString() : "");
													if (StringUtils.isNotBlank(resFilePath)) {// 主图下载保存
														String[] array = resFilePath.split("/");
														String img_name = array[array.length - 1];
														// String distFolder = save_path+"files/jdimgs/"+img_name;
														try {
															FileTools.downLoadFromUrl(resFilePath, img_name, save_path
																	+ "files/jdimgs");
														} catch (IOException e) {
															logger.warn("==Exception: " + e.getMessage());
															e.printStackTrace();
														}

														commInfo.setMain_pic("files/jdimgs/" + img_name);
														// 商品轮播图
														pdImgs.setFile_path(commInfo.getMain_pic());
													}
													String sale_price = null != jo.get("jdprice") ? jo.get("jdprice")
															.toString() : "";
													String jd_agent_price = null != jo.get("price") ? jo.get("price")
															.toString() : "";
													commInfo.setJd_agent_price(new BigDecimal(jd_agent_price));
													commInfo.setSale_price(new BigDecimal(sale_price));
													// 返点大于6%的商品才上架、返点大于6%的商品才返现
													// 京东价*94%-京东给我们的价
													BigDecimal rate = commInfo.getSale_price()
															.subtract(commInfo.getJd_agent_price())
															.divide(commInfo.getSale_price(), 2, BigDecimal.ROUND_DOWN);
													if ("1".equals(pobj.getString("state"))
															&& rate.compareTo(new BigDecimal("0.06")) == 1) {
														commInfo.setIs_sell(1);
														commInfo.setUp_date(new Date());
														Calendar cal = Calendar.getInstance();
														cal.setTime(new Date());
														cal.add(Calendar.YEAR, 2);
														commInfo.setDown_date(cal.getTime());
														commInfo.setIs_rebate(1);
														BigDecimal rebate_scale = commInfo
																.getSale_price()
																.multiply(new BigDecimal("0.94"))
																.subtract(commInfo.getJd_agent_price())
																.divide(commInfo.getSale_price(), 4,
																		BigDecimal.ROUND_DOWN)
																.multiply(new BigDecimal(100));
														if (rebate_scale.compareTo(new BigDecimal(0)) == 1) {
															commInfo.setRebate_scale(rebate_scale);
															commInfo.setMultiple_order_value(rebate_scale.multiply(
																	new BigDecimal(100 * 65)).intValue());
														}
													} else {
														commInfo.setIs_sell(0);
														commInfo.setMultiple_order_value(0);
													}

													commInfo.setP_index(Integer.valueOf(Keys.P_INDEX_INIT));
													commInfo.setAdd_date(new Date());
													commInfo.setAdd_user_id(1);
													commInfo.setAdd_user_name("admin");
													commInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
													commInfo.setAudit_date(new Date());
													commInfo.setAudit_user_id(1);
													commInfo.setAudit_desc("自动审核通过");
													commInfo.setFreight_id(Integer.valueOf(Keys.jd_freight_id));
													commInfo.setIs_has_tc(1);
													commInfo.setComm_weight(new BigDecimal(
															null != pobj.get("weight") ? pobj.get("weight").toString()
																	: "0"));
													commInfo.setComm_content(null != pobj.get("introduction") ? pobj
															.get("introduction").toString() : "");

													List<PdImgs> commImgsList = new ArrayList<PdImgs>();
													commImgsList.add(pdImgs);
													commInfo.setCommImgsList(commImgsList);
													int comm_id = facade.getCommInfoService().createCommInfo(commInfo);

													// 套餐同步保存
													CommTczhPrice commTczhPrice = new CommTczhPrice();
													commTczhPrice.setComm_id(String.valueOf(comm_id));
													commTczhPrice.setComm_price(commInfo.getSale_price());
													commTczhPrice.setAdd_date(new Date());
													commTczhPrice.setAdd_user_id(1);
													commTczhPrice.setTczh_name("默认套餐");
													commTczhPrice.setInventory(999);
													commTczhPrice.setComm_weight(new BigDecimal(null != pobj
															.get("weight") ? pobj.get("weight").toString() : "0"));
													facade.getCommTczhPriceService().createCommTczhPrice(commTczhPrice);
												} else if (count > 0) {// 京东商品匹配多个记录，错误商品统一审核不通过
													// CommInfo commUpdata = new CommInfo();
													// commUpdata.getMap().put("jd_skuid",
													// null != pobj.get("sku") ? pobj.get("sku").toString() : "");
													// commUpdata.getMap().put("is_zingying",
													// Keys.CommZyType.COMM_ZY_TYPE_1.getIndex());// 京东自营商品
													// commUpdata.setAudit_state(
													// Keys.audit_state.audit_state_fu_1.getIndex());
													// commUpdata.setAudit_date(new Date());
													// commUpdata.setAudit_user_id(1);
													// commUpdata.setAudit_desc("自动审核不通过");
													// facade.getCommInfoService().modifyCommInfo(commUpdata);
												} else {// 更新商品价格，名称
													commInfo = facade.getCommInfoService().getCommInfo(commInfo);

													String sale_price = null != jo.get("jdprice") ? jo.get("jdprice")
															.toString() : "";
													String jd_agent_price = null != jo.get("price") ? jo.get("price")
															.toString() : "";

													if (commInfo.getSale_price().compareTo(new BigDecimal(sale_price)) != 0
															|| commInfo.getJd_agent_price().compareTo(
																	new BigDecimal(jd_agent_price)) != 0) {// 京东商品价格与第三方接口系统不一致，需更新价格

														CommInfo commInfoUpdate = new CommInfo();
														commInfoUpdate.setId(commInfo.getId());
														commInfoUpdate
																.setJd_agent_price(new BigDecimal(jd_agent_price));
														commInfoUpdate.setSale_price(new BigDecimal(sale_price));
														commInfoUpdate.setInventory(999);
														commInfoUpdate.setUpdate_date(new Date());

														// 返点大于6%的商品才上架、返点大于6%的商品才返现
														// 京东价*94%-京东给我们的价
														BigDecimal rate = commInfoUpdate
																.getSale_price()
																.subtract(commInfoUpdate.getJd_agent_price())
																.divide(commInfoUpdate.getSale_price(), 2,
																		BigDecimal.ROUND_DOWN);
														if ("1".equals(pobj.getString("state"))
																&& rate.compareTo(new BigDecimal("0.06")) == 1) {
															commInfoUpdate.setIs_sell(1);
															commInfoUpdate.setUp_date(new Date());
															Calendar cal = Calendar.getInstance();
															cal.setTime(new Date());
															cal.add(Calendar.YEAR, 2);
															commInfoUpdate.setDown_date(cal.getTime());
															commInfoUpdate.setIs_rebate(1);
															BigDecimal rebate_scale = commInfoUpdate
																	.getSale_price()
																	.multiply(new BigDecimal("0.94"))
																	.subtract(commInfoUpdate.getJd_agent_price())
																	.divide(commInfoUpdate.getSale_price(), 4,
																			BigDecimal.ROUND_DOWN)
																	.multiply(new BigDecimal(100));
															if (rebate_scale.compareTo(new BigDecimal(0)) == 1) {
																commInfoUpdate.setRebate_scale(rebate_scale);
																commInfoUpdate.setMultiple_order_value(rebate_scale
																		.multiply(new BigDecimal(100 * 65)).intValue());
															}
														} else {
															commInfoUpdate.setIs_sell(0);
															commInfoUpdate.setMultiple_order_value(0);
														}
														facade.getCommInfoService().modifyCommInfo(commInfoUpdate);

														// 套餐同步更新
														CommTczhPrice commTczhPrice = new CommTczhPrice();
														commTczhPrice
																.setComm_id(String.valueOf(commInfoUpdate.getId()));
														commTczhPrice.setComm_price(commInfoUpdate.getSale_price());
														commTczhPrice.setInventory(999);
														commTczhPrice.setUpdate_date(new Date());
														facade.getCommTczhPriceService().modifyCommTczhPrice(
																commTczhPrice);
													}
												}
											}
											// **************同步京东自营商品 end***************//
										}
									}
								}

								// logger.info("当前页数："+current_page);
							} else {
								// 京东商品已同步完成，结束循环
								logger.warn("==autoSyncJdProduct complete: stop while==");
								break;
							}
						} else {
							// 接口调用失败
							logger.warn("==autoSyncJdProduct fail: skipCount=" + skipCount);
							continue;
						}
					} else {
						logger.warn("==autoSyncJdProduct fail: skipCount=" + skipCount);
						continue;
					}
				} else {
					logger.warn("==autoSyncJdProduct fail: skipCount=" + skipCount);
					continue;
				}
				skipCount += limitCount;
			}

		} catch (Exception e) {
			logger.warn("==Exception: " + e.getMessage());
			e.printStackTrace();
		}

		logger.warn("==autoSyncJdProduct end==");
	}

	/**
	 * 自动同步创建京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	@Override
	public void autoSyncUploadJdOrder() throws HttpException, IOException {
		logger.warn("==autoSyncUploadJdOrder start==");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());// 京东订单
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_0.getIndex());// 已预订，待支付
		orderInfo.setIs_sync_jd(0);// 未同步，现在进行同步
		List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo t : orderInfoList) {
				super.syncUploadJdOrder(t.getId(), orderInfoDao, orderInfoDetailsDao);
			}
		}
		logger.warn("==autoSyncUploadJdOrder end==");
	}

	/**
	 * 自动同步确认京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	@Override
	public void autoSyncConfirmJdOrder() throws HttpException, IOException {
		logger.warn("==autoSyncConfirmJdOrder start==");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());// 京东订单
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());// 已支付，待发货
		orderInfo.setIs_sync_jd(1);// 京东订单已创建
		orderInfo.setIs_confirm_jd(0);// 京东订单未确认，则现在进行同步确认
		List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo t : orderInfoList) {
				super.syncConfirmJdOrder(t.getId(), orderInfoDao);
			}
		}
		logger.warn("==autoSyncConfirmJdOrder end==");
	}

	/**
	 * 自动同步下载更新京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	@Override
	public void autoSyncDownloadJdOrder() throws HttpException, IOException {
		logger.warn("==autoSyncDownloadJdOrder start==");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_30.getIndex());// 京东订单
		orderInfo.getMap().put("order_state_in", "10,20");
		orderInfo.setIs_sync_jd(1);// 已创建
		orderInfo.setIs_confirm_jd(1);// 已确认，现在更新订单状态
		List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo t : orderInfoList) {
				super.syncDownloadJdOrder(t.getId(), orderInfoDao);
			}
		}
		logger.warn("==autoSyncDownloadJdOrder end==");
	}

	/**
	 * 同步下载京东地区
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	public void autoSyncDownloadJdArea(String pid) throws HttpException, IOException {
		String info = JdApiUtil.getJdAreaListByPid(pid);

		if (StringUtils.isNotBlank(info)) {
			JSONObject obj = JSONObject.parseObject(info);
			if (Keys.JD_API_RESULT_STATUS_CODE.equals(obj.get("StatusCode").toString())) {// 接口查询成功
				String data_str = obj.getString("Data");
				JSONObject data = JSONObject.parseObject(data_str);
				String result_str = JSONObject.toJSONString(data.get("value"));
				List<JdAreas> jdAreasList = JSONObject.parseArray(result_str, JdAreas.class);
				if (null != jdAreasList && jdAreasList.size() > 0) {
					for (JdAreas t : jdAreasList) {
						this.autoSyncDownloadJdArea(t.getId().toString());
						facade.getJdAreasService().createJdAreas(t);
					}
				}
			}
		}
	}

	/**
	 * 手动计算奖励
	 */
	public void autoReckonRebateAndAid(Integer order_id) {
		super.reckonRebateAndAid(order_id, orderInfoDao, orderInfoDetailsDao, userInfoDao, userScoreRecordDao,
				userRelationParDao, userBiRecordDao, entpInfoDao, serviceCenterInfoDao, new Date(), baseDataDao,
				userJifenRecordDao, tongjiDao, commInfoDao, commInfoPoorsDao, villageInfoDao);
	}

	/**
	 * 9.9付费会员到期关闭 2018-3-13
	 */
	public void autoUserCartClose() {
		UserInfo ui = new UserInfo();
		ui.getMap().put("user_level_gt", Keys.USER_LEVEL_FX);
		ui.getMap().put("card_end_date_le", DateTools.getStringDate(new Date(), "yyyy-MM-dd"));
		List<UserInfo> userInfoList = facade.getUserInfoService().getUserInfoList(ui);
		if (null != userInfoList && userInfoList.size() > 0) {
			for (UserInfo t : userInfoList) {
				UserInfo temp = new UserInfo();
				temp.setId(t.getId());
				temp.setUser_level(Keys.USER_LEVEL_FX);
				facade.getUserInfoService().modifyUserInfo(temp);
			}
		}
	}

	/**
	 * 9.9付费会员30天到期提醒 2018-3-24
	 */
	public void autoUserCartTip() {
		UserInfo ui = new UserInfo();
		ui.getMap().put("user_level_gt", Keys.USER_LEVEL_FX);
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, 30);

		ui.getMap().put("card_end_date_le", DateTools.getStringDate(cal.getTime(), "yyyy-MM-dd"));
		ui.getMap().put("card_end_date_ge", DateTools.getStringDate(cal.getTime(), "yyyy-MM-dd"));
		List<UserInfo> userInfoList = facade.getUserInfoService().getUserInfoList(ui);
		if (null != userInfoList && userInfoList.size() > 0) {
			for (UserInfo t : userInfoList) {
				// 微信公众号消息推送
				WeiXinSendMessageUtils.vipEnd(t);
			}
		}
	}

	/**
	 * 本年已获取扶贫金超出限制金额的贫困户自动从商品扶贫对象中移除，并给企业发站内消息 2018-4-11 liuzhixiang
	 */
	public void autoRemovePoorFromComm() {
		UserInfo ui = new UserInfo();
		ui.setUser_type(Keys.UserType.USER_TYPE_2.getIndex());
		ui.setIs_poor(1);
		ui.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
		List<UserInfo> userInfoList = facade.getUserInfoService().getUserInfoList(ui);
		if (null != userInfoList && userInfoList.size() > 0) {
			BaseData baseDate = new BaseData();
			baseDate.setId(Keys.BASE_DATA_ID.BASE_DATA_ID_1903.getIndex());
			baseDate = facade.getBaseDataService().getBaseData(baseDate);// 一个贫困户年度获取扶贫金额限制

			for (UserInfo t : userInfoList) {
				if (null != t.getPoor_id()) {
					UserBiRecord userBiRecord = new UserBiRecord();
					userBiRecord.setBi_type(Keys.BiType.BI_TYPE_500.getIndex());// 扶贫金
					userBiRecord.setBi_chu_or_ru(Keys.BI_CHU_OR_RU.BASE_DATA_ID_1.getIndex());// 入
					userBiRecord.setIs_del(Keys.IsDel.IS_DEL_0.getIndex());
					userBiRecord.setAdd_user_id(t.getId());// 贫困户的账号
					Calendar cal = GregorianCalendar.getInstance();
					cal.setTime(new Date());
					String year = cal.get(Calendar.YEAR) + "";
					userBiRecord.getMap().put("begin_date", year + "-01-01");
					userBiRecord.getMap().put("end_date", year + "-12-31");
					BigDecimal sum = facade.getUserBiRecordService().getUserBiSum(userBiRecord);
					if (sum.compareTo(new BigDecimal(baseDate.getPre_number())) >= 0) {// 年度已获取扶贫金大于等于限制金额，商品的扶贫对象中移除贫困户
						CommInfoPoors commInfoPoors = new CommInfoPoors();
						commInfoPoors.getMap().put("poor_id", t.getPoor_id());
						facade.getCommInfoPoorsService().removeCommInfoPoors(commInfoPoors);
					}
				}
			}
		}
	}

	/**
	 * 商家订单12小时未发货提醒 2018-5-6 liuzhixiang
	 */
	public void autoOrderInfoFhTip() {
		logger.warn("==autoOrderInfoFhTip start==");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_10.getIndex());// 商品订单
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());// 等待发货
		orderInfo.setIs_ziti(0);// 非自提订单
		List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);
		if (null != orderInfoList && orderInfoList.size() > 0) {
			for (OrderInfo t : orderInfoList) {
				// 时间超过12小时发短信提醒
				Integer days = Integer.valueOf(DurationFormatUtils.formatDuration(new Date().getTime()
						- t.getOrder_date().getTime(), "H"));
				if (days > 12) {
					EntpInfo ei = new EntpInfo();
					ei.setId(t.getEntp_id());
					ei = facade.getEntpInfoService().getEntpInfo(ei);
					if (null != ei && StringUtils.isNotBlank(ei.getEntp_tel())) {
						// // 1、创蓝短信
						// // String msg = SMS.sms_fh;
						// // SmsUtils.sendMessage(msg, ei.getEntp_tel());
						// 2、阿里云短信
						StringBuffer message = new StringBuffer("{\"entp\":\"" + ei.getEntp_name() + "\",");
						message.append("\"order\":\"" + t.getTrade_index() + "\",");
						message.append("}");
						DySmsUtils.sendMessage(message.toString(), ei.getEntp_tel(), SmsTemplate.sms_fh_01);
					}
				}
			}
		}
		logger.warn("==autoOrderInfoFhTip end==");
	}

	/**
	 * 99元会员卡有效期的自动续费微信消息
	 */
	public void autoUserCardEndDateRenewTip() {
		// 消息内容如下：
		// 会员续期提醒：尊敬的会员您好，您的九个挑夫会员身份，挑夫总部已自动帮您续期，请知悉！感谢您对九个挑夫的支持和信任！如有疑问请联系我们的客服人员，客服电话4009123009
		UserInfo ui = new UserInfo();
		ui.getMap().put("appid_weixin_is_not_null", "true");
		ui.getMap().put("card_end_date_eq", "2099-12-31 23:59:59");
		ui.setIs_del(0);
		List<UserInfo> userInfoList = userInfoDao.selectEntityList(ui);
		if (null != userInfoList && userInfoList.size() > 0) {
			for (UserInfo t : userInfoList) {
				WeiXinSendMessageUtils.vipEnd(t);
			}
		}
	}

	/**
	 * 订单交易完成后，用户未评价，则系统默认生成好评
	 */
	public void autoCommentByOrder() {
		OrderInfoDetails orderInfoDetails = new OrderInfoDetails();
		orderInfoDetails.setIs_comment(0);// 未评价
		orderInfoDetails.getMap().put(
				"order_type_in",
				Keys.OrderType.ORDER_TYPE_10.getIndex() + "," + Keys.OrderType.ORDER_TYPE_30.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_40.getIndex() + "," + Keys.OrderType.ORDER_TYPE_50.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_60.getIndex() + "," + Keys.OrderType.ORDER_TYPE_70.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_80.getIndex() + "," + Keys.OrderType.ORDER_TYPE_90.getIndex() + ","
						+ Keys.OrderType.ORDER_TYPE_100.getIndex());
		orderInfoDetails.getMap().put("order_state", Keys.OrderState.ORDER_STATE_50.getIndex());
		// 查询未评价的订单明细记录
		List<OrderInfoDetails> orderInfoDetailsList = orderInfoDetailsDao
				.selectOrderInfoDetailsListByOrder(orderInfoDetails);
		if (null != orderInfoDetailsList && orderInfoDetailsList.size() > 0) {
			for (OrderInfoDetails t : orderInfoDetailsList) {
				CommentInfo entity = new CommentInfo();
				entity.setComm_type(Keys.CommentType.COMMENT_TYPE_10.getIndex());
				entity.setOrder_details_id(t.getId());
				entity.setOrder_id(t.getOrder_id());
				entity.setComm_name(t.getComm_name());
				entity.setComm_tczh_name(t.getComm_tczh_name());
				entity.setComm_tczh_id(t.getComm_tczh_id());
				entity.setBuy_date(t.getAdd_date());
				entity.setComm_score(5);// 默认5分
				entity.setComm_level(1);// 默认好评
				entity.setComm_experience("系统默认好评");// 系统默认好评

				entity.setLink_id(t.getComm_id());
				entity.setComm_ip("127.0.0.1");

				entity.setComm_time(new Date());
				if (null != t.getMap().get("finish_date")) {// 交易完成时间不未空，则评价时间以此为准
					entity.setComm_time(DateTools.changeString2DateTime(t.getMap().get("finish_date").toString()));
				}
				entity.setComm_state(1);// 发布
				entity.setLink_f_id(t.getEntp_id());
				entity.setEntp_id(t.getEntp_id());
				entity.setHas_pic(0);
				entity.setOrder_value(0);
				entity.setComm_uid(null == t.getMap().get("add_user_id") ? -1 : Integer.valueOf(t.getMap()
						.get("add_user_id").toString()));
				entity.setComm_uname(null == t.getMap().get("add_user_name") ? "匿名" : t.getMap().get("add_user_name")
						.toString());
				entity.getMap().put("add_comment_count", "true");

				// 创建系统默认好评
				int id = this.commentInfoDao.insertEntity(entity);
				if (id > 0) {// 更新订单、订单明细、
					// 当前明细改为：已评价
					OrderInfoDetails updateOds = new OrderInfoDetails();
					updateOds.setId(entity.getOrder_details_id());
					updateOds.setIs_comment(1);
					updateOds.setHas_comment(1);
					this.orderInfoDetailsDao.updateEntity(updateOds);

					// 查询未评价数量
					OrderInfoDetails odsCount = new OrderInfoDetails();
					odsCount.setOrder_id(t.getOrder_id());
					odsCount.setIs_comment(0);
					int notCommentCount = this.orderInfoDetailsDao.selectEntityCount(odsCount);
					if (notCommentCount == 0) {
						// 订单明细全评价修改订单为：已评价
						OrderInfo updateOrder = new OrderInfo();
						updateOrder.setId(t.getOrder_id());
						updateOrder.setIs_comment(1);
						this.orderInfoDao.updateEntity(updateOrder);
					}

					if (null != entity.getLink_id()) {
						CommInfo commInfoUpdate = new CommInfo();
						commInfoUpdate.setId(entity.getLink_id());
						commInfoUpdate.getMap().put("add_comment_count", 1);
						this.commInfoDao.updateEntity(commInfoUpdate);
					}

					// 更新评论数
					if (null != t.getMap().get("add_comment_count")) {
						if (null != t.getEntp_id()) {
							EntpInfo ei = new EntpInfo();
							ei.setId(t.getEntp_id());
							ei.getMap().put("add_comment_count", 1);// 评论数+1
							this.entpInfoDao.updateEntity(ei);
						}
					}

				}

			}
		}
	}

	/**
	 * 自动更新拼团订单状态
	 */
	@Override
	public void autoSyncUpdateAllPtOrder() {

		OrderInfo orderInfo = new OrderInfo();

		// 查询所有未完成的拼团主订单,主订单状态为已支付的
		orderInfo.setIs_leader(1);
		orderInfo.setIs_group_success(0);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());

		List<OrderInfo> leaderOrderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);

		for (OrderInfo order : leaderOrderInfoList) {
			// 查询主订单下的所有拼团订单
			OrderInfo orderInfoQuery = new OrderInfo();
			orderInfoQuery.setLeader_order_id(order.getId());// 主订单id
			orderInfoQuery.setIs_group_success(0);// 拼团状态
			orderInfoQuery.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());// 订单类型
			orderInfoQuery.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());// 订单状态，已支付

			List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfoQuery);

			CommInfo commInfo = commInfoDao.selectCommInfoByOrderId(order.getId());// 查询拼团商品
			Integer group_count = commInfo.getGroup_count();

			// 判断主订单 end_date 是否超出当前时间
			Date end_date = order.getEnd_date();
			OrderInfo orderInfoUpdate = new OrderInfo();
			if (end_date != null && end_date.before(new Date())) {// 主订单结束时间已到

				if (group_count != null && group_count == orderInfoList.size()) {
					// 已达到拼团商品的拼团数量
					// 拼团成功修改 is_group_success 状态
					orderInfoUpdate.setIs_group_success(1);
					orderInfoUpdate.setUpdate_date(new Date());
					orderInfoUpdate.getMap().put("leaderOrderId", order.getId());
					orderInfoUpdate.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());
					orderInfoUpdate.getMap().put("opt_group_state", 0);
					orderInfoDao.updateEntity(orderInfoUpdate);
				} else {
					// 未达到拼团商品的拼团数量
					// 拼团失败
					// 退款流程
					for (OrderInfo o1 : orderInfoList) {
						OrderInfo orderInfo1 = new OrderInfo();
						orderInfo1.setId(o1.getId());
						orderInfo1 = this.orderInfoDao.selectEntity(orderInfo1);

						BigDecimal price = new BigDecimal(0);
						price = price.add(o1.getOrder_money().add(o1.getMoney_bi()));

						// ods = list.get(0);
						OrderReturnInfo returnInfo = new OrderReturnInfo();
						returnInfo.setReturn_no(getReturnNo());
						returnInfo.setOrder_id(orderInfo1.getId());
						returnInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
						returnInfo.setOrder_state(orderInfo1.getOrder_state());
						returnInfo.setUser_id(o1.getAdd_user_id());
						returnInfo.setUser_name(o1.getAdd_user_name());
						returnInfo.setReturn_type(Keys.return_why.return_why_11630.getIndex());
						returnInfo.setExpect_return_way(Keys.return_type.return_type_4_tuikuan.getIndex());
						returnInfo.setPrice(price);
						returnInfo.setNum(orderInfo1.getOrder_num());
						returnInfo.setTk_status(0);
						returnInfo.setIs_del(0);
						returnInfo.setAdd_date(new Date());
						returnInfo.setEntp_id(orderInfo1.getEntp_id());
						// returnInfo.setComm_name(ods.getComm_name());
						int id = orderReturnInfoDao.insertEntity(returnInfo);// 插入退款记录，自动审核通过
						returnInfo.setId(id);

						int row = this.facade.getOrderReturnInfoService().modifyOrderReturnInfo(returnInfo);// 退款

					}
					orderInfoUpdate.setIs_group_success(-1);// 时间已到未完成
					orderInfoUpdate.setUpdate_date(new Date());
					orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_X20.getIndex());// 设置订单状态，已退款
					orderInfoUpdate.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());
					orderInfoUpdate.getMap().put("opt_group_state", 0);
					orderInfoUpdate.getMap().put("leaderOrderId", order.getId());
					orderInfoDao.updateEntity(orderInfoUpdate);
				}
			} else {
				// 未到结束时间，判断拼团订单数量是否达到商品group_count
				if (group_count != null && group_count == orderInfoList.size()) {
					// 已达到拼团商品的拼团数量
					// 拼团成功修改 is_group_success 状态
					orderInfoUpdate.setIs_group_success(1);
					orderInfoUpdate.setUpdate_date(new Date());
					orderInfoUpdate.getMap().put("leaderOrderId", order.getId());
					orderInfoUpdate.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());
					orderInfoUpdate.getMap().put("opt_group_state", 0);
					orderInfoDao.updateEntity(orderInfoUpdate);
				}
			}

		}
	}

	/**
	 * 自动更新拼团订单状态
	 */
	@Override
	public void autoSyncUpdatePtOrder(Map map) {

		OrderInfo orderInfo = new OrderInfo();
		if (map != null && map.get("comm_id") != null) {
			// 根据商品更新拼团订单
			orderInfo.getMap().put("details_comm_ids", map.get("comm_id"));
			orderInfo.getMap().put("details_order_types", Keys.OrderType.ORDER_TYPE_100.getIndex());
		}
		if (map != null && map.get("leaderOrderId") != null) {
			orderInfo.setId(Integer.valueOf(map.get("leaderOrderId") + ""));
		}
		if (map != null && map.get("entpId") != null) {
			// 根据商家id更新
			orderInfo.setEntp_id(Integer.valueOf(map.get("entpId") + ""));
		}

		// 查询所有未完成的拼团主订单,主订单状态为已支付的
		orderInfo.setIs_leader(1);
		orderInfo.setIs_group_success(0);
		orderInfo.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());
		orderInfo.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());

		List<OrderInfo> leaderOrderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfo);

		for (OrderInfo order : leaderOrderInfoList) {
			// 查询主订单下的所有拼团订单
			OrderInfo orderInfoQuery = new OrderInfo();
			orderInfoQuery.setLeader_order_id(order.getId());// 主订单id
			orderInfoQuery.setIs_group_success(0);// 拼团状态
			orderInfoQuery.setOrder_type(Keys.OrderType.ORDER_TYPE_100.getIndex());// 订单类型
			orderInfoQuery.setOrder_state(Keys.OrderState.ORDER_STATE_10.getIndex());// 订单状态，已支付

			List<OrderInfo> orderInfoList = facade.getOrderInfoService().getOrderInfoList(orderInfoQuery);

			CommInfo commInfo = commInfoDao.selectCommInfoByOrderId(order.getId());// 查询拼团商品
			Integer group_count = commInfo.getGroup_count();

			// 判断主订单 end_date 是否超出当前时间
			Date end_date = order.getEnd_date();
			OrderInfo orderInfoUpdate = new OrderInfo();
			if (end_date != null && end_date.before(new Date())) {// 主订单结束时间已到

				if (group_count != null && group_count == orderInfoList.size()) {
					// 已达到拼团商品的拼团数量
					// 拼团成功修改 is_group_success 状态
					orderInfoUpdate.setIs_group_success(1);
					orderInfoUpdate.setUpdate_date(new Date());
					orderInfoUpdate.getMap().put("leaderOrderId", order.getId());
					orderInfoUpdate.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());
					orderInfoUpdate.getMap().put("opt_group_state", 0);
					orderInfoDao.updateEntity(orderInfoUpdate);
				} else {
					// 未达到拼团商品的拼团数量
					// 拼团失败
					// 退款流程
					for (OrderInfo o1 : orderInfoList) {
						OrderInfo orderInfo1 = new OrderInfo();
						orderInfo1.setId(o1.getId());
						orderInfo1 = this.orderInfoDao.selectEntity(orderInfo1);

						BigDecimal price = new BigDecimal(0);
						price = price.add(o1.getOrder_money().add(o1.getMoney_bi()));

						// ods = list.get(0);
						OrderReturnInfo returnInfo = new OrderReturnInfo();
						returnInfo.setReturn_no(getReturnNo());
						returnInfo.setOrder_id(orderInfo1.getId());
						returnInfo.setAudit_state(Keys.audit_state.audit_state_1.getIndex());
						returnInfo.setOrder_state(orderInfo1.getOrder_state());
						returnInfo.setUser_id(o1.getAdd_user_id());
						returnInfo.setUser_name(o1.getAdd_user_name());
						returnInfo.setReturn_type(Keys.return_why.return_why_11630.getIndex());
						returnInfo.setExpect_return_way(Keys.return_type.return_type_4_tuikuan.getIndex());
						returnInfo.setPrice(price);
						returnInfo.setNum(orderInfo1.getOrder_num());
						returnInfo.setTk_status(0);
						returnInfo.setIs_del(0);
						returnInfo.setAdd_date(new Date());
						returnInfo.setEntp_id(orderInfo1.getEntp_id());
						// returnInfo.setComm_name(ods.getComm_name());
						int id = orderReturnInfoDao.insertEntity(returnInfo);// 插入退款记录，自动审核通过
						returnInfo.setId(id);

						int row = this.facade.getOrderReturnInfoService().modifyOrderReturnInfo(returnInfo);// 退款

					}
					orderInfoUpdate.setIs_group_success(-1);// 时间已到未完成
					orderInfoUpdate.setUpdate_date(new Date());
					orderInfoUpdate.setOrder_state(Keys.OrderState.ORDER_STATE_X20.getIndex());// 设置订单状态，已退款
					orderInfoUpdate.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());
					orderInfoUpdate.getMap().put("opt_group_state", 0);
					orderInfoUpdate.getMap().put("leaderOrderId", order.getId());
					orderInfoDao.updateEntity(orderInfoUpdate);
				}
			} else {
				// 未到结束时间，判断拼团订单数量是否达到商品group_count
				if (group_count != null && group_count == orderInfoList.size()) {
					// 已达到拼团商品的拼团数量
					// 拼团成功修改 is_group_success 状态
					orderInfoUpdate.setIs_group_success(1);
					orderInfoUpdate.setUpdate_date(new Date());
					orderInfoUpdate.getMap().put("leaderOrderId", order.getId());
					orderInfoUpdate.getMap().put("opt_order_state", Keys.OrderState.ORDER_STATE_10.getIndex());
					orderInfoUpdate.getMap().put("opt_group_state", 0);
					orderInfoDao.updateEntity(orderInfoUpdate);
				}
			}

		}
	}

	@Override
	public void autoSyncUpdatePtOrderThread(Map map) {
		final Map map2 = map;
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("================更新拼团订单================");
				autoSyncUpdatePtOrder(map2);
			}
		});
		thread.start();
	}

	@Override
	public void autoGetWeixinToken() {
		logger.info("====autoGetWeixinToken=====");
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", Keys.APP_ID);
		params.put("secret", "ee8905decfeb5ec2d9316a6d54b2bff7");
		params.put("grant_type", "client_credential");
		String url = "https://api.weixin.qq.com/cgi-bin/token";

		String returnResult = HttpUtils.sendPost(url, params);

		// logger.warn("旧的Keys.weixin_token_key:" + Keys.weixin_token_key);
		// Keys.weixin_token_key = "111";
		// logger.warn("新的Keys.weixin_token_key:" + Keys.weixin_token_key);
		// SysSetting s = new SysSetting();
		// s.setTitle(Keys.weixin_token);
		// s.setContent(Keys.weixin_token_key);
		// facade.getSysSettingService().modifySysSetting(s);

		logger.warn("returnResult:" + returnResult);
		if (StringUtils.isNotBlank(returnResult)) {
			JSONObject data = JSONObject.parseObject(returnResult);
			String access_token = (String) data.get("access_token");

			SysSetting s = new SysSetting();
			s.setTitle(Keys.weixin_token);
			s.setContent(access_token);
			facade.getSysSettingService().modifySysSetting(s);

			logger.warn("旧的Keys.weixin_token_key:" + Keys.weixin_token_key);
			Keys.weixin_token_key = access_token;

			logger.warn("access_token:" + access_token);
		}
		logger.info("====autoGetWeixinToken=====end");
	}
}
