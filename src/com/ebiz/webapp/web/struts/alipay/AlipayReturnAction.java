package com.ebiz.webapp.web.struts.alipay;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.alipay.util.AlipayNotify;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BasePayAction;

public class AlipayReturnAction extends BasePayAction {

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (!Keys.is_open_pay_alipay) {
			logger.warn("==alipay is closed==");
			response.getWriter().write("fail");
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == (values.length - 1)) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		// 商户订单号
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝交易号
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"), "UTF-8");
		// 交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
		// 支付宝返回订单价格
		String total_fee = new String(request.getParameter("total_fee").getBytes("ISO-8859-1"), "UTF-8");

		// 自定义参数订单类型

		Integer order_type = null;
		String attach = null;
		String body = request.getParameter("body");
		attach = new String(body.getBytes("ISO-8859-1"), "UTF-8");

		logger.warn("==========attach====" + attach);

		String[] attachs = attach.split("_");
		order_type = Integer.valueOf(attachs[0]);
		if (order_type.intValue() == Keys.OrderType.ORDER_TYPE_20.getIndex()) {
			attach = attachs[1];
		}
		// 获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		// 计算得出通知验证结果
		boolean verify_result = AlipayNotify.verify(params);

		if (verify_result) {// 验证成功
			if (trade_status.equals("WAIT_SELLER_SEND_GOODS") || trade_status.equals("TRADE_FINISHED")) {

				// super.modifyOrderPublic(request, out_trade_no, trade_no, Keys.PayType.PAY_TYPE_1.getIndex(),
				// order_type, total_fee, attach);

			}
			response.getWriter().write("验证成功<br />");
		} else {
			response.getWriter().write("验证失败");
		}

		// 这个地方需要回到我的订单
		String ctx = super.getCtxPath(request);
		String returnUrl = ctx + "/manager/customer/index.shtml";
		response.sendRedirect(returnUrl);
		return null;

	}
}
