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
  <html-el:form action="/admin/CommSaleTable" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
        	&nbsp;直接查询：
            <html-el:select property="orderDay" styleId="orderDay">
              <html-el:option value="">总报表</html-el:option>
              <html-el:option value="1">上月报表</html-el:option>
              <html-el:option value="2">上周报表</html-el:option>
              <html-el:option value="3">昨日报表</html-el:option>
            </html-el:select>
            &nbsp;查询时间从:
            <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" />
           	 至：
            <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})"/>
            &nbsp;
            <html-el:button value="查 询" styleClass="bgButton" property="btn_submit" styleId="btn_submit" />
          </td>
      </tr>
      
      
      
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post">
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="10%">销售金额</th>
        <th width="10%">订单总数</th>
      </tr>
        <tr align="center">
          <td align="center"><fmt:formatNumber value="${orderInfoTodaySum.map.sum_pay_money}" pattern="0.##"/>元</td>
          <td align="center">${orderInfoTodaySum.map.cnt}单</td>
        </tr>
    </table>
    <br/>
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="10%">微信支付金额</th>
        <th width="10%">支付宝支付金额</th>
<!--         <th width="10%">通联支付金额</th> -->
        <th width="10%">货到付款金额</th>
<!--         <th width="10%">现金支付金额</th> -->
      </tr>
        <tr align="center">
          <td align="center"><fmt:formatNumber value="${payType3SumMoney}" pattern="0.##"/>元</td>
          <td align="center"><fmt:formatNumber value="${payType1SumMoney}" pattern="0.##"/>元</td>
<%--           <td align="center"><fmt:formatNumber value="${payType4SumMoney}" pattern="0.##"/>元</td> --%>
          <td align="center"><fmt:formatNumber value="${payType0SumMoney}" pattern="0.##"/>元</td>
<%--           <td align="center"><fmt:formatNumber value="${payType2SumMoney}" pattern="0.##"/>元</td> --%>
        </tr>
    </table>
    <br/>
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="10%">新增注册数</th>
      </tr>
        <tr align="center">
          <td align="center">${userInfoTotalTodayCount}个</td>
        </tr>
    </table>
  </form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
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

function DayFunc(){
	$("#orderDay").val("");
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
