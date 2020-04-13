package com.ebiz.webapp.web.struts.weixin;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.aiisen.weixin.pay.bean.paymch.MchPayNotify;
import com.aiisen.weixin.pay.util.ExpireSet;
import com.aiisen.weixin.pay.util.MapUtil;
import com.aiisen.weixin.pay.util.SignatureUtil;
import com.aiisen.weixin.pay.util.StreamUtils;
import com.aiisen.weixin.pay.util.XMLConverUtil;
import com.ebiz.webapp.web.Keys;
import com.ebiz.webapp.web.struts.BasePayAction;

/**
 * @author Wu,Yang
 * @version 2010-8-24
 */
public class NotifyWeixinAction extends BasePayAction {

	// 重复通知过滤 时效60秒
	private static ExpireSet<String> expireSet = new ExpireSet<String>(60);

	@Override
	public ActionForward unspecified(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return this.index(mapping, form, request, response);
	}

	public ActionForward index(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		// 获取请求数据
		String xml;
		try {
			xml = StreamUtils.copyToString(request.getInputStream(), Charset.forName("utf-8"));

			logger.warn("====NotifyWeixinAction xml:{}", xml);

			Map<String, String> map = MapUtil.xmlToMap(xml);
			// 已处理 去重
			logger.warn("====expireSet.contains(map.get(transaction_id) :{}",
					expireSet.contains(map.get("transaction_id")));
			if (expireSet.contains(map.get("transaction_id"))) {
				return null;
			}

			String returnxml = "<xml><return_code><![CDATA[{0}]]></return_code><return_msg><![CDATA[{1}]]></return_msg></xml>";
			// 签名验证
			String sign = SignatureUtil.generateSign(map, Keys.API_KEY);
			if (!sign.equals(map.get("sign"))) {
				returnxml = StringUtils.replace(returnxml, "{0}", "FAIL");
				returnxml = StringUtils.replace(returnxml, "{1}", "ERROR");
				response.getWriter().write(returnxml);
			} else {
				// 对象转换
				MchPayNotify payNotify = XMLConverUtil.convertToObject(MchPayNotify.class, xml);
				expireSet.add(payNotify.getTransaction_id());

				// try {
				String transaction_id = payNotify.getTransaction_id();// 微信订单号
				String out_trade_no = payNotify.getOut_trade_no();// 商户订单号
				Integer order_type = Integer.valueOf(payNotify.getNonce_str());// 订单类型
				Integer total_fee = payNotify.getTotal_fee();// 订单金额
				String attach = payNotify.getAttach();// 关联参数

				returnxml = StringUtils.replace(returnxml, "{0}", "SUCCESS");
				returnxml = StringUtils.replace(returnxml, "{1}", "OK");
				response.getWriter().write(returnxml);

				try {
					super.modifyOrderPublic(request, out_trade_no, transaction_id, Keys.PayType.PAY_TYPE_3.getIndex(),
							order_type, String.valueOf(Double.valueOf(total_fee) / (Double.valueOf(100))), attach);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.warn("====modifyOrderPublic error :" + e.getMessage());
				}

				// } catch (Exception e) {
				// logger.warn("====NotifyWeixinAction ERROR====" + e.getMessage());
				// e.printStackTrace();
				// returnxml = StringUtils.replace(returnxml, "{0}", "FAIL");
				// returnxml = StringUtils.replace(returnxml, "{1}", "ERROR");
				// response.getWriter().write(returnxml);
				// }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.warn("====modifyOrderPublic error :" + e.getMessage());
		}
		return null;
	}
}
