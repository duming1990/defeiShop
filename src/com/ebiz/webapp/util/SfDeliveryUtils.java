package com.ebiz.webapp.util;

import com.ebiz.webapp.web.struts.BaseAction;

public class SfDeliveryUtils extends BaseAction {
	//
	// public static void main(String[] args) throws Exception {
	// System.out.println(new SfDeliveryUtils().test());
	// }
	//
	// // 获取webserviceBean
	// public static JaxWsProxyFactoryBean getFactoryBean() {
	//
	// JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
	// factory.setServiceClass(SfService.class);
	// factory.setAddress("http://bsp-test.sf-express.com:9090/bsp-ois/ws/expressService");
	// return factory;
	// }
	//
	// public static int count = 0;
	//
	// public String test() {
	// String retString = null;
	//
	// String test = "<Request service='OrderService' lang='zh-CN'><Head> NMGSW,vLIoDR=oSz5N </Head><Body><Order"
	// +
	// " orderid='NMGSW19' express_type='1' j_province='安徽省' j_city='合肥市' j_company='' j_contact='客服' j_tel='13170277069' "
	// + "j_address='合肥市明珠路9号安徽服务外包产业园3栋'"
	// + " d_province='安徽省' d_city='合肥市' d_contact='江家勇' d_tel='15155103625'"
	// +
	// " d_address='清源路中铁国际世纪城德园2期9#1103' parcel_quantity='1' pay_method='1'><OrderOption custid='4710367027'></OrderOption></Order></Body></Request>";
	// SfService basic = (SfService) getFactoryBean().create();
	// String responseString = basic.sfexpressService(test);
	// System.out.println(test);
	// System.out.println(responseString);
	// try {
	// // 解析数据
	// Document dom = DocumentHelper.parseText(responseString);
	// Element root = dom.getRootElement();
	// String result = root.element("Head").getText();
	// if (result.equals("OK")) {
	// Element body = root.element("Body");
	// if (null != body) {
	// Element orderResponse = body.element("OrderResponse");
	// if (null != orderResponse) {
	// String mailno = orderResponse.attributeValue("mailno");
	// if (StringUtils.isNotBlank(mailno)) {
	// retString = mailno;
	// } else {
	// retString = "1";// 1.表示下单成功但是不生成订单
	// }
	// }
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return retString;
	// }
	//
	// public static String getCreateOrderInfo(OrderInfo orderInfo) {
	// String retString = null;
	// UserInfo userInfo = (UserInfo) orderInfo.getMap().get("userInfo");
	// if (null != userInfo) {
	// try {
	// StringBuilder xmlBuilder = new StringBuilder();
	// xmlBuilder.append("<Request service='OrderService' lang='zh-CN'>");
	// xmlBuilder.append("<Head> NMGSW,vLIoDR=oSz5N </Head>");
	// xmlBuilder.append("<Body>");
	// xmlBuilder.append("<Order orderid='" + orderInfo.getTrade_index() + "'");
	// xmlBuilder.append(" express_type='1'");
	// xmlBuilder.append(" j_province='" + orderInfo.getMap().get("j_province") + "'");
	// xmlBuilder.append(" j_city='" + orderInfo.getMap().get("j_city") + "'");
	// xmlBuilder.append(" j_company='" + userInfo.getOwn_entp_name() + "'");
	// xmlBuilder.append(" j_contact='客服'");
	// xmlBuilder.append(" j_tel='" + userInfo.getMobile() + "'");
	// xmlBuilder.append(" j_address='" + orderInfo.getMap().get("entp_addr") + "'");
	// xmlBuilder.append(" d_province='" + orderInfo.getMap().get("d_province") + "'");
	// xmlBuilder.append(" d_city='" + orderInfo.getMap().get("d_city") + "'");
	// xmlBuilder.append(" d_contact='" + orderInfo.getRel_name() + "'");
	// xmlBuilder.append(" d_tel='" + orderInfo.getRel_phone() + "'");
	// xmlBuilder.append(" d_address='" + orderInfo.getRel_addr() + "'");
	// xmlBuilder.append(" parcel_quantity='1'");
	// // 付款方式有1 标准快递 2顺丰特惠 3电商特惠
	// xmlBuilder.append(" pay_method='1'>");
	// xmlBuilder.append("<OrderOption custid='4710367027'>");
	// xmlBuilder.append("</OrderOption> ");
	// xmlBuilder.append("</Order>");
	// xmlBuilder.append("</Body>");
	// xmlBuilder.append("</Request>");
	//
	// SfService basic = (SfService) getFactoryBean().create();
	// String responseString = basic.sfexpressService(xmlBuilder.toString());
	//
	// System.out.println(xmlBuilder.toString());
	// System.out.println(responseString);
	//
	// // 解析数据
	// Document dom = DocumentHelper.parseText(responseString);
	//
	// Element root = dom.getRootElement();
	// String result = root.element("Head").getText();
	// if (result.equals("OK")) {
	// Element body = root.element("Body");
	// if (null != body) {
	// Element orderResponse = body.element("OrderResponse");
	// if (null != orderResponse) {
	// String mailno = orderResponse.attributeValue("mailno");
	// if (StringUtils.isNotBlank(mailno)) {
	// retString = mailno;
	// } else {
	// retString = "1";// 1.表示下单成功但是不生成订单
	// }
	// }
	// }
	// }
	// } catch (DocumentException e) {
	// e.printStackTrace();
	// }
	// }
	// return retString;
	// }
	//
	// @SuppressWarnings("unchecked")
	// public static String queryDeliveryInfo(String waybill_no) {// 路由查询
	// StringBuffer table = new StringBuffer();
	//
	// String xml = "<Request service='RouteService' lang='zh-CN'>" + "<Head>NMGSW,vLIoDR=oSz5N</Head>      <Body>"
	// + "<RouteRequest tracking_type='1' tracking_number='" + waybill_no + "'/></Body> </Request>";
	//
	// SfService basic = (SfService) getFactoryBean().create();
	// String responseString = basic.sfexpressService(xml);
	// try {
	// Document dom = DocumentHelper.parseText(responseString);
	// Element root = dom.getRootElement();
	// String head = root.element("Head").getText();
	// if (StringUtils.isNotBlank(head) && head.equals("OK")) {
	// Element body = root.element("Body");
	// if (null != body) {
	// Element routeResponse = body.element("RouteResponse");
	// if (null != routeResponse) {
	// List<Element> routeList = routeResponse.elements("Route");
	// if (null != routeList && routeList.size() > 0) {
	// table
	// .append("<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" class=\"tableClass\">");
	// table.append("<tr >");
	// table.append("<th nowrap=\"nowrap\">时间</th>");
	// table.append("<th nowrap=\"nowrap\">详情</th>");
	// table.append("</tr>");
	// for (Element route : routeList) {
	// table.append("<tr><td>" + route.attributeValue("accept_time") + "</td>" + "<td>"
	// + route.attributeValue("accept_address") + " " + route.attributeValue("remark")
	// + "</td></tr>");
	// }
	// table.append("</table>");
	// }
	// } else {
	// table.append("暂无该商品的物流信息！");
	// }
	// }
	// responseString = table.toString();
	// } else {
	// responseString = root.element("ERROR").getText();
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// return responseString;
	// }
}
