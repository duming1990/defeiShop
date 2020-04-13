package com.ebiz.webapp.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

/**
 * @author Wu,Yang
 * @version 2015.12.09
 */
public interface AutoRunService {

	// 5、自动确认收货更新，每天晚上3点半执行
	void autoConfirmReceipt();

	// 6、自动取消订单更新，每1个小时执行一次
	void autoOrderCancel();

	// 9、合伙人到期自动取消，每天4:10点执行一次
	void autoServiceCancel();

	// 10、交易完成后7天，关闭订单并发放推广奖励和计算待返余额，每天1点执行一次
	void modifyAutoCloseOrder();

	// 24小时自动退款
	void autoTuiKuanAudit();

	void newOrderTip();

	/**
	 * 自动同步京东商品
	 */
	void autoSyncJdProduct();

	/**
	 * 自动同步上传京东订单
	 */
	void autoSyncUploadJdOrder() throws HttpException, IOException;

	/**
	 * 自动同步下载更新京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	void autoSyncDownloadJdOrder() throws HttpException, IOException;

	/**
	 * 同步下载京东地区
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	void autoSyncDownloadJdArea(String pid) throws HttpException, IOException;

	/**
	 * 自动同步确认京东订单
	 * 
	 * @throws IOException
	 * @throws HttpException
	 */
	void autoSyncConfirmJdOrder() throws HttpException, IOException;

	/**
	 * 手动计算奖励
	 */
	void autoReckonRebateAndAid(Integer order_id);

	/**
	 * 9.9付费会员到期关闭 2018-3-13
	 */
	void autoUserCartClose();

	/**
	 * 9.9付费会员30天到期提醒 2018-3-24
	 */
	void autoUserCartTip();

	/**
	 * 本年已获取扶贫金超出限制金额的贫困户自动从商品扶贫对象中移除，并给企业发站内消息 2018-4-11 liuzhixiang
	 */
	void autoRemovePoorFromComm();

	/**
	 * 商家订单12小时未发货提醒 2018-5-6 liuzhixiang
	 */
	void autoOrderInfoFhTip();

	/**
	 * 99元会员卡有效期的自动续费微信消息
	 */
	void autoUserCardEndDateRenewTip();

	/**
	 * 订单交易完成后，用户未评价，则系统默认生成好评
	 */
	void autoCommentByOrder();

	/**
	 * 自动更新拼团订单状态
	 */
	void autoSyncUpdatePtOrder(Map map);

	void autoSyncUpdatePtOrderThread(Map map);

	void autoSyncUpdateAllPtOrder();

	// 每两个小时获取微信token
	void autoGetWeixinToken();

}
