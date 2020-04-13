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
  <html-el:form action="/admin/SettlementReport" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
            &nbsp;结算时间从:
            <html-el:text property="st_add_date" styleId="st_add_date" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
           	 至：
            <html-el:text property="en_add_date" styleId="en_add_date" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
            &nbsp;
            <html-el:button value="查 询" styleClass="bgButton" property="btn_submit" styleId="btn_submit" />
            &nbsp;
           <html-el:button value="导 出" styleClass="bgButton" property="download" styleId="download" />
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
        <th width="10%">订单金额</th>
        <th width="10%">结算金额</th>
        <th width="10%">结算时间</th>
      </tr>
      <c:set var="order_count" value="0"></c:set>
      <c:set var="sum_order_money" value="0"></c:set>
      <c:set var="sum_money" value="0"></c:set>
      
      <c:forEach var="cur" items="${entityList}">
        <tr align="center">
          <td align="center">${cur.entp_name}</td>
          <td align="center"><a onclick="orderList('${af.map.st_add_date}','${af.map.en_add_date}','${cur.id}');" style="color: green;cursor:pointer;">${cur.map.order_count}单</a></td>
          <td align="center"><fmt:formatNumber value="${cur.sum_order_money}" pattern="0.##"/>元</td>
          <td align="center"><fmt:formatNumber value="${cur.sum_money}" pattern="0.##"/>元</td>
          <td align="center"><fmt:formatDate value="${cur.map.add_date}" pattern="yyyy-MM-dd"/></td>
          
          <c:set var="order_count" value="${order_count + cur.map.order_count}"></c:set>
          <c:set var="sum_order_money" value="${sum_order_money + cur.sum_order_money}"></c:set>
          <c:set var="sum_money" value="${sum_money + cur.sum_money}"></c:set>
          
        </tr>
       </c:forEach>
       
       <tr align="center">
          <td align="center" style="color: red;">合计:</td>
          <td align="center"><span id="order_count">${order_count}单</span></td>
          <td align="center"><span id="sum_order_money"><fmt:formatNumber value="${sum_order_money}" pattern="0.##"/></span>元</td>
          <td align="center"><span id="sum_money"><fmt:formatNumber value="${sum_money}" pattern="0.##"/></span>元</td>
          <td align="center"></td>
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
		var start_date = $("#st_add_date").val();
		var end_date = $("#en_add_date").val();
		if(end_date != ""){
			  if(start_date > end_date){
				  alert("时间选择有错误！");
				  return false;
			  }
			}
		f.submit();
	});
});

$("#download").click(function(){
	var sum_money=$("#sum_money").text();
	var order_count=$("#order_count").text();
	var sum_order_money=$("#sum_order_money").text();
	var link="order_count="+order_count+"&sum_money="+sum_money+"&sum_order_money="+sum_order_money+"&"
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/SettlementReport.do?method=toExcel&mod_id=${af.map.mod_id}&" +link+ $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/SettlementReport.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&"+link + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});

function orderList(st_add_date,en_add_date,id){
	
	var url = "${ctx}/manager/admin/SettlementReport.do?method=listDetails&st_add_date="+st_add_date+"&en_add_date="+en_add_date+"&id="+id;
	$.dialog({
		title:  "查看订单",
		width:  1000,
		height: 800,
        lock:true ,
		content:"url:"+url
	});
}

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>
