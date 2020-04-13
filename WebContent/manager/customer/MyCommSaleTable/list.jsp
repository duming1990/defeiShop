<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}" name="keywords" />
<meta content="${app_name}" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyCommSaleTable" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id"/>
    <html-el:hidden property="par_id"/>
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
        	&nbsp;直接查询：
            <html-el:select property="orderDay" styleId="orderDay">
              <html-el:option value="0">总报表</html-el:option>
              <html-el:option value="1">上月报表</html-el:option>
              <html-el:option value="2">上周报表</html-el:option>
              <html-el:option value="3">昨日报表</html-el:option>
            </html-el:select>
            &nbsp;销售类型：
            <html-el:select property="order_type" styleId="order_type">
              <html-el:option value="">全部</html-el:option>
              <html-el:option value="0">线上</html-el:option>
              <html-el:option value="90">线下</html-el:option>
            </html-el:select>
            &nbsp;查询时间 从:
            <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" />
           	 至：
            <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" />
                          
            <c:if test="${userInfo.is_fuwu eq 1}">
             &nbsp;所属店铺：
            <html-el:hidden property="own_entp_id" styleId="own_entp_id" />                                                                    
            <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" readonly="true" onclick="openEntpChild()" />
            </c:if>
            &nbsp;&nbsp;
             <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button>
          </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post">
  
   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
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
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="10%">微信支付金额</th>
        <th width="10%">支付宝支付金额</th>
      </tr>
        <tr align="center">
          <td align="center"><fmt:formatNumber value="${payType3SumMoney}" pattern="0.##"/>元</td>
          <td align="center"><fmt:formatNumber value="${payType1SumMoney}" pattern="0.##"/>元</td>
        </tr>
    </table>
    <br/>
     <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="10%">线下活动订单数量</th>
        <th width="10%">线上订单数量</th>
      </tr>
        <tr align="center">
          <td align="center">${orderInfoAppCount}单</td>
          <td align="center">${orderInfoWeixinCount}单</td>
        </tr>
    </table>
   
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
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
	
	 $("#order_type").change(function(){
			f.submit();
		});
});
         
function openEntpChild(){
	                                                            
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo2&dir=customer";
	$.dialog({
		title:  "选择企业",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

function DayFunc(){
	$("#orderDay").val("");
}


//]]></script>
</body>
</html>
