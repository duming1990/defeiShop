package com.ebiz.webapp.web.struts.allinpay;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.pay.util.ExpireSet;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BasePayAction;

/**
 * @author liujia
 * @version 2019-01-10
 */
public class NotifyAllinpayAction extends BasePayAction {

	// 重复通知过滤 时效60秒
	private static ExpireSet<String> expireSet = new ExpireSet<String>(60);

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		logger.warn("接收到通知");
		request.setCharacterEncoding("gbk");// 通知传输的编码为GBK
		response.setCharacterEncoding("gbk");
		TreeMap<String, String> params = getParams(request);// 动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容
		try {
			boolean isSign = SybUtil.validSign(params, Keys.allinpay_key);// 接受到推送通知,首先验签
			logger.warn("验签结果:" + isSign);

			if (isSign) {

				String transaction_id = params.get("trxid");// 通联订单号
				String out_trade_no = params.get("cusorderid");// 商户订单号
				Integer order_type = Integer.valueOf(params.get("trxreserved"));// 订单类型
				String total_fee = params.get("trxamt");// 订单金额

				super.modifyOrderPublic(request, out_trade_no, transaction_id, Keys.PayType.PAY_TYPE_4.getIndex(),
						order_type, String.valueOf(Double.valueOf(total_fee) / (Double.valueOf(100))), null);
			}

			// 验签完毕进行业务处理
		} catch (Exception e) {// 处理异常
			// TODO: handle exception
			e.printStackTrace();
		} finally {// 收到通知,返回success
			response.getOutputStream().write("success".getBytes());
			response.flushBuffer();
		}
		return null;
	}

	/**
	 * 动态遍历获取所有收到的参数,此步非常关键,因为收银宝以后可能会加字段,动态获取可以兼容由于收银宝加字段而引起的签名异常
	 * 
	 * @param request
	 * @return
	 */
	private TreeMap<String, String> getParams(HttpServletRequest request) {
		TreeMap<String, String> map = new TreeMap<String, String>();
		Map reqMap = request.getParameterMap();
		for (Object key : reqMap.keySet()) {
			String value = ((String[]) reqMap.get(key))[0];
			map.put(key.toString(), value);
		}
		return map;
	}

}
