<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/OrderInfoReport" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
<!--         	企业名称： -->
<%--             <html-el:hidden property="own_entp_id" styleId="own_entp_id" /> --%>
<%--             <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" readonly="true" onclick="openEntpChild()"/> --%>
        	&nbsp;直接查询：
            <html-el:select property="orderDay" styleId="orderDay">
              <html-el:option value="">总报表</html-el:option>
              <html-el:option value="1">上月报表</html-el:option>
              <html-el:option value="2">上周报表</html-el:option>
              <html-el:option value="3">昨日报表</html-el:option>
            </html-el:select>
            &nbsp;下单时间从:
            <html-el:text property="st_order_date_fmt" styleId="st_order_date_fmt" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',onpicked:DayFunc})" />
           	 至：
            <html-el:text property="en_order_date_fmt" styleId="en_order_date_fmt" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',onpicked:DayFunc})"/>
<!--             &nbsp;支付时间从: -->
<%--             <html-el:text property="st_pay_date_fmt" styleId="st_pay_date_fmt" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" /> --%>
<!--            	 至： -->
<%--             <html-el:text property="en_pay_date_fmt" styleId="en_pay_date_fmt" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/> --%>
            &nbsp;订单状态：
            <html-el:select property="order_state" styleId="order_state">
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="10">待发货</html-el:option>
              <html-el:option value="20">已发货</html-el:option>
              <html-el:option value="40">已收货</html-el:option>
              <html-el:option value="50">交易完成</html-el:option>
              <html-el:option value="-20">已退款</html-el:option>
            </html-el:select>
            &nbsp;
            <html-el:button value="查 询" styleClass="bgButton" property="btn_submit" styleId="btn_submit" />
<!--             &nbsp; -->
<%--            <html-el:button value="导 出" styleClass="bgButton" property="download" styleId="download" /> --%>
          </td>
      </tr>
      
      
      
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="10%">商家名称</th>
        <th width="10%">订单总数</th>
<!--         <th width="10%">实际支付金额</th> -->
<!--         <th width="10%">余额抵扣金额</th> -->
<!--         <th width="10%">使用福利金金额</th> -->
        <th width="10%">总金额</th>
      </tr>
      <c:set var="cnt_all" value="0"></c:set>
      <c:set var="sum_money_all" value="0"></c:set>
      <c:set var="sum_ye_all" value="0"></c:set>
      <c:set var="sum_pay_money_all" value="0"></c:set>
      <c:set var="sum_welfare_pay_money_all" value="0"></c:set>
      <c:set var="price_money_1002_all" value="0"></c:set>
      <c:set var="price_money_1003_all" value="0"></c:set>
      <c:set var="price_money_1004_all" value="0"></c:set>
      <c:set var="price_money_1005_all" value="0"></c:set>
      <c:forEach var="cur" items="${orderInfoTodaySum}">
        <tr align="center">
          <td align="center">${cur.map.entp_name}</td>
          <td align="center"><a onclick="orderList('${af.map.st_order_date_fmt}','${af.map.en_order_date_fmt}','${af.map.st_pay_date_fmt}','${af.map.en_pay_date_fmt}','${af.map.order_state}','${cur.map.entp_id}');" style="color: green;cursor:pointer;">${cur.map.cnt}单</a></td>
<%--           <td align="center"><fmt:formatNumber value="${cur.map.sum_money}" pattern="0.##"/>元</td> --%>
<%--           <td align="center"><fmt:formatNumber value="${cur.map.sum_ye}" pattern="0.##"/>元</td> --%>
<%--           <td align="center"><fmt:formatNumber value="${cur.map.sum_welfare_pay_money}" pattern="0.##"/>元</td> --%>
          <td align="center"><fmt:formatNumber value="${cur.map.sum_pay_money}" pattern="0.##"/>元</td>
<%--           <td align="center"><fmt:formatNumber value="${cur.map.price_money_1002}" pattern="0.##"/>元</td> --%>
<%--           <td align="center"><fmt:formatNumber value="${cur.map.price_money_1003}" pattern="0.##"/>元</td> --%>
<%--           <td align="center"><fmt:formatNumber value="${cur.map.price_money_1004}" pattern="0.##"/>元</td> --%>
<%--           <td align="center"><fmt:formatNumber value="${cur.map.price_money_1005}" pattern="0.##"/>元</td> --%>
          
          <c:set var="cnt_all" value="${cnt_all + cur.map.cnt}"></c:set>
          <c:set var="sum_money_all" value="${sum_money_all + cur.map.sum_money}"></c:set>
          <c:set var="sum_ye_all" value="${sum_ye_all + cur.map.sum_ye}"></c:set>
          <c:set var="sum_welfare_pay_money_all" value="${sum_welfare_pay_money_all + cur.map.sum_welfare_pay_money}"></c:set>
          <c:set var="sum_pay_money_all" value="${sum_pay_money_all + cur.map.sum_pay_money}"></c:set>
          <c:set var="price_money_1002_all" value="${price_money_1002_all + cur.map.price_money_1002}"></c:set>
          <c:set var="price_money_1003_all" value="${price_money_1003_all + cur.map.price_money_1003}"></c:set>
          <c:set var="price_money_1004_all" value="${price_money_1004_all + cur.map.price_money_1004}"></c:set>
          <c:set var="price_money_1005_all" value="${price_money_1005_all + cur.map.price_money_1005}"></c:set>
        </tr>
       </c:forEach>
        <tr align="center">
          <td align="center" style="color: red;">合计:</td>
          <td align="center"><span id="cnt">${cnt_all}</span>单</td>
<%--           <td align="center"><span id="sum_money"><fmt:formatNumber value="${sum_money_all}" pattern="0.##"/></span>元</td> --%>
<%--           <td align="center"><span id="sum_ye"><fmt:formatNumber value="${sum_ye_all}" pattern="0.##"/></span>元</td> --%>
<%--           <td align="center"><span id="sum_welfare_pay_money"><fmt:formatNumber value="${sum_welfare_pay_money_all}" pattern="0.##"/></span>元</td> --%>
          <td align="center"><span id="sum_pay_money"><fmt:formatNumber value="${sum_pay_money_all}" pattern="0.##"/></span>元</td>
<%--           <td align="center"><span id="price_money_1002"><fmt:formatNumber value="${price_money_1002_all}" pattern="0.##"/></span>元</td> --%>
<%--           <td align="center"><span id="price_money_1003"><fmt:formatNumber value="${price_money_1003_all}" pattern="0.##"/></span>元</td> --%>
<%--           <td align="center"><span id="price_money_1004"><fmt:formatNumber value="${price_money_1004_all}" pattern="0.##"/></span>元</td> --%>
<%--           <td align="center"><span id="price_money_1005"><fmt:formatNumber value="${price_money_1005_all}" pattern="0.##"/></span>元</td> --%>
        </tr>
    </table>
  </form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		var start_date = $("#st_date").val();
		var end_date = $("#en_date").val();
		if(end_date != ""){
			  if(start_date > end_date){
				  alert("时间选择有错误！");
				  return false;
			  }
			}
		f.submit();
	});
	$("#orderDay").click(function(){
		$("#st_date").val("");
		$("#en_date").val("");
	});
});

$("#download").click(function(){
	var cnt=$("#cnt").text();
	var sum_money=$("#sum_money").text();
	var sum_ye=$("#sum_ye").text();
	var sum_pay_money=$("#sum_pay_money").text();
	var sum_welfare_pay_money=$("#sum_welfare_pay_money").text();
	var price_money_1002=$("#price_money_1002").text();
	var price_money_1003=$("#price_money_1003").text();
	var price_money_1004=$("#price_money_1004").text();
	var price_money_1005=$("#price_money_1005").text();
	
	var link="cnt="+cnt+"&sum_money="+sum_money+"&sum_ye="+sum_ye+"&sum_pay_money="+sum_pay_money
				+"&price_money_1002="+price_money_1002+"&price_money_1003="+price_money_1003+"&price_money_1004="+price_money_1004
				+"&price_money_1005="+price_money_1005+"&sum_welfare_pay_money="+sum_welfare_pay_money+"&"
	
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	var url="${ctx}/manager/admin/OrderInfoReport.do?method=toExcel&mod_id=${af.map.mod_id}&" +link+ $('.searchForm').serialize();
	    	location.href = url;
	    } else {
	    	location.href = "${ctx}/manager/admin/OrderInfoReport.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&"+link + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});

function DayFunc(){
	$("#orderDay").val("");
}

function orderList(st_order_date_fmt,en_order_date_fmt,st_pay_date_fmt,en_pay_date_fmt,order_state,entp_id){
	
	var url = "${ctx}/manager/admin/OrderInfoReport.do?method=reportOrderList&st_order_date_fmt="+st_order_date_fmt+"&en_order_date_fmt="+en_order_date_fmt+"&st_pay_date_fmt="+st_pay_date_fmt+"&en_pay_date_fmt="+en_pay_date_fmt+"&order_state="+order_state+"&own_entp_id="+entp_id;
	$.dialog({
		title:  "查看订单",
		width:  1300,
		height: 900,
        lock:true ,
		content:"url:"+url
	});
}

function openEntpChild(){
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin";
	$.dialog({
		title:  "选择企业",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}
//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>
