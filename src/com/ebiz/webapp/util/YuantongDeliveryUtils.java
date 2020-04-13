package com.ebiz.webapp.util;

import com.ebiz.webapp.web.struts.BaseAction;

public class YuantongDeliveryUtils extends BaseAction {
	// public static String apiUrl = "http://jingangtest.yto56.com.cn/ordws/Vip15Servlet";// 测试地址
	//
	// public static String parternId = "123456"; // 加密id
	//
	// public static String clientId = "TEST"; // 客户编码
	//
	// public static String customerId = "TEST"; // 客户编码
	//
	// @SuppressWarnings("unchecked")
	// public static boolean getCreateOrderInfo(OrderInfo orderInfo, WlOrderInfo wlOrderInfo) {
	//
	// boolean flag = false;
	// List<OrderInfoDetails> orderInfoDetailsList = (List<OrderInfoDetails>) orderInfo.getMap().get(
	// "orderInfoDetailsList");
	// EntpInfo entpInfo = (EntpInfo) orderInfo.getMap().get("entpInfo");
	//
	// String mailNo = wlOrderInfo.getWaybill_no(); // 运单号
	// String order_id = orderInfo.getTrade_index().toString(); // 订单号
	// try {
	// // 打开连接
	// URL url = new URL(apiUrl);
	// HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	// connection.setDoOutput(true);
	// connection.setRequestMethod("POST");
	// OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	// // 数据
	// StringBuilder xmlBuilder = new StringBuilder();
	// xmlBuilder.append("<RequestOrder>");
	// xmlBuilder.append("    <clientID>" + clientId + "</clientID>");
	// xmlBuilder.append("    <logisticProviderID>YTO</logisticProviderID>");
	// xmlBuilder.append("    <customerId>" + customerId + "</customerId>");
	// xmlBuilder.append("    <txLogisticID>" + order_id + "</txLogisticID>");
	// xmlBuilder.append("    <mailNo>" + mailNo + "</mailNo>");
	// xmlBuilder.append("    <orderType>1</orderType>");
	// xmlBuilder.append("    <serviceType>1</serviceType>");
	// xmlBuilder.append("    <flag>0</flag>");
	// xmlBuilder.append("    <sender>");
	// xmlBuilder.append("        <name>" + entpInfo.getEntp_name() + "</name>");
	// xmlBuilder.append("        <postCode>" + entpInfo.getEntp_postcode() + "</postCode>");
	// xmlBuilder.append("        <mobile>" + entpInfo.getEntp_tel() + "</mobile>");
	// xmlBuilder.append("        <prov>" + entpInfo.getMap().get("prov") + "</prov>");
	// xmlBuilder.append("        <city>" + entpInfo.getMap().get("city") + "</city>");
	// xmlBuilder.append("        <address></address>");
	// xmlBuilder.append("    </sender>");
	// xmlBuilder.append("    <receiver>");
	// xmlBuilder.append("        <name>" + orderInfo.getRel_name() + "</name>");
	// xmlBuilder.append("        <postCode>" + orderInfo.getRel_zip() + "</postCode>");
	// xmlBuilder.append("        <phone>" + orderInfo.getRel_tel() + "</phone>");
	// xmlBuilder.append("        <mobile>" + orderInfo.getRel_phone() + "</mobile>");
	// xmlBuilder.append("        <prov>" + orderInfo.getRel_province() + "</prov>");
	// xmlBuilder.append("        <city>" + orderInfo.getRel_city() + "</city>");
	// xmlBuilder.append("        <address>" + orderInfo.getRel_addr() + "</address>");
	// xmlBuilder.append("    </receiver>");
	// xmlBuilder.append("    <sendStartTime>" + wlOrderInfo.getAdd_date() + "</sendStartTime>");// 配送开始时间
	// xmlBuilder.append("    <sendEndTime></sendEndTime>");// 配送结束时间
	// xmlBuilder.append("    <goodsValue>" + orderInfo.getMap().get("total_money") + "</goodsValue>");
	// xmlBuilder.append("    <totalValue>0</totalValue>");
	// xmlBuilder.append("    <itemsValue>1</itemsValue>");
	// xmlBuilder.append("    <itemsWeight>0</itemsWeight>");
	// xmlBuilder.append("    <items>");
	// for (OrderInfoDetails entity : orderInfoDetailsList) {
	// xmlBuilder.append("        <item>");
	// xmlBuilder.append("            <itemName>" + entity.getPd_name() + "</itemName>");
	// xmlBuilder.append("            <number>" + entity.getGood_count() + "</number>");
	// xmlBuilder.append("            <itemValue>" + entity.getGood_price() + "</itemValue>");
	// xmlBuilder.append("        </item>");
	// }
	// xmlBuilder.append("    </items>");
	// xmlBuilder.append("    <special>1</special>");
	// xmlBuilder.append("    <remark>1</remark>");
	// xmlBuilder.append("    <insuranceValue>0.0</insuranceValue>");
	// xmlBuilder.append("    <totalServiceFee>0.0</totalServiceFee>");
	// xmlBuilder.append("    <codSplitFee>0.0</codSplitFee>");
	// xmlBuilder.append("</RequestOrder>");
	//
	// // 加密
	// MessageDigest messagedigest = MessageDigest.getInstance("MD5");
	// messagedigest.update((xmlBuilder.toString() + parternId).getBytes("UTF-8"));
	// byte[] abyte0 = messagedigest.digest();
	// String data_digest = new String(Base64.encode(abyte0));
	// // 最终的xml
	// String queryString = "logistics_interface=" + URLEncoder.encode(xmlBuilder.toString(), "UTF-8")
	// + "&data_digest=" + URLEncoder.encode(data_digest, "UTF-8") + "&clientId="
	// + URLEncoder.encode(clientId, "UTF-8");
	// out.write(queryString);
	// out.flush();
	// out.close();
	// // 获取服务端的反馈
	// String responseString = "";
	// String strLine = "";
	// InputStream in = connection.getInputStream();
	// BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	// while ((strLine = reader.readLine()) != null) {
	// responseString += strLine + "\n";
	// }
	// in.close();
	// // System.err.print("请求的返回信息：" + responseString);
	// // 解析返回的数据
	// Document dom = DocumentHelper.parseText(responseString);
	// Element root = dom.getRootElement();
	// // String logisticProviderID = root.element("logisticProviderID").getText();
	// // String txLogisticID = root.element("txLogisticID").getText();
	// String success = root.element("success").getText();
	// if (success.equals("true")) {
	// flag = true;
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return flag;
	// }
	//
	// @SuppressWarnings("unchecked")
	// public static String queryDeliveryInfo(String waybill_no) {
	// StringBuffer table = new StringBuffer();
	// try {
	// // 打开连接
	// URL url = new URL(apiUrl);
	// HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	// connection.setDoOutput(true);
	// connection.setRequestMethod("POST");
	// OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
	// // 数据
	// StringBuilder xmlBuilder = new StringBuilder();
	// xmlBuilder.append("<BatchQueryRequest>");
	// xmlBuilder.append("    <logisticProviderID>YTO</logisticProviderID>");
	// xmlBuilder.append("    <clientID>" + clientId + "</clientID>");
	// xmlBuilder.append("    <orders>");
	// xmlBuilder.append("        <order>");
	// xmlBuilder.append("            <mailNo>" + waybill_no + "</mailNo>");
	// xmlBuilder.append("        </order>");
	// xmlBuilder.append("    </orders>");
	// xmlBuilder.append("</BatchQueryRequest>");
	//
	// // 加密
	// MessageDigest messagedigest = MessageDigest.getInstance("MD5");
	// messagedigest.update((xmlBuilder.toString() + parternId).getBytes("UTF-8"));
	// byte[] abyte0 = messagedigest.digest();
	// String data_digest = new String(Base64.encode(abyte0));
	// // 查询
	// String queryString = "logistics_interface=" + URLEncoder.encode(xmlBuilder.toString(), "UTF-8")
	// + "&data_digest=" + URLEncoder.encode(data_digest, "UTF-8") + "&clientId="
	// + URLEncoder.encode(clientId, "UTF-8");
	// out.write(queryString);
	// out.flush();
	// out.close();
	// // 获取服务端的反馈
	// String responseString = "";
	// String strLine = "";
	// InputStream in = connection.getInputStream();
	// BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	// while ((strLine = reader.readLine()) != null) {
	// responseString += strLine + "\n";
	// }
	// in.close();
	// // 解析返回的数据
	// String test = "<BatchQueryResponse>" + "<logisticProviderID>YTO</logisticProviderID>" + "<orders>"
	// + "<order>" + "<mailNo>268123456789</mailNo>" + "<txLogisticID> LP07082300225710</txLogisticID>"
	// + "<mailType>EXPRESS </mailType>" + "<orderStatus>TRANSIT</orderStatus>" + "<steps>" + "<step>"
	// + "<acceptTime>2007-08-24 08:00:00.0 CST</acceptTime>" + "<acceptAddress>南京</acceptAddress>"
	// + "<name>张三</name>" + "<status>true</status>" + "<remark>接受</remark>" + "</step>" + "<step>"
	// + "<acceptTime>2007-08-25 05:00:00.0 CST</acceptTime>" + "<acceptAddress>南京物流中心扫描</acceptAddress>"
	// + "<name>李四</name>" + " <status>true</status>" + " <remark>接受</remark>" + "</step>" + "<step>"
	// + "<acceptTime>2007-08-26 13:00:00.0 CST</acceptTime>" + "  <acceptAddress>南京</acceptAddress>"
	// + "<name>张三</name>" + "   <status>true</status>" + "   <remark>从站点出发</remark>" + "</step>"
	// + "<step>" + "    <acceptTime>2007-08-26 23:00:00.0 CST</acceptTime>"
	// + "    <acceptAddress>内蒙物流集散中心</acceptAddress>" + "	<name>张三</name>" + "     <status>true</status>"
	// + "    <remark>进入站点</remark>" + " </step>" + "</steps>" + " </order>" + " </orders>"
	// + "</BatchQueryResponse>";
	//
	// Document dom = DocumentHelper.parseText(test);
	// Element root = dom.getRootElement();
	// Element orders = root.element("orders");
	// List<Element> order = orders.elements("order");
	// SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// if (null != order && order.size() > 0) {
	//
	// table.append("<table width=\"100%\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\" class=\"tableClass\">");
	// table.append("<tr >");
	// table.append("<th nowrap=\"nowrap\">时间</th>");
	// table.append("<th nowrap=\"nowrap\">详情</th>");
	// table.append("</tr>");
	// for (Element el : order) {
	// Element steps = el.element("steps");
	// if (null != steps) {
	// List<Element> step = steps.elements("step");
	// if (null != step && step.size() > 0) {
	// for (Element el2 : step) {
	// String acceptTime = sf.format((Date) sf.parse(el2.element("acceptTime").getText()));
	// String acceptAddress = el2.element("acceptAddress").getText();
	// String remark = el2.element("remark").getText();
	// table.append("<tr >");
	// table.append("<th nowrap=\"nowrap\">" + acceptTime + "</th>");
	// table.append("<th nowrap=\"nowrap\">" + acceptAddress + " " + remark + "</th>");
	// table.append("</tr>");
	// }
	// }
	// }
	// }
	// table.append("</table>");
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// return table.toString();
	// }
	//
	// public static void main(String[] args) throws IOException, Exception {
	//
	// System.out.println(queryDeliveryInfo("TEST100419421"));
	// }
}
