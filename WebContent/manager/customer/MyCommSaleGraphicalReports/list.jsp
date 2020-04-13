<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/MyCommSaleGraphicalReports" styleClass="ajaxForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>
        	&nbsp;直接查询：
            <html-el:select property="orderDay" styleId="orderDay">
              <html-el:option value="0">总报表</html-el:option>
              <html-el:option value="1">上月报表</html-el:option>
              <html-el:option value="2">最近七天</html-el:option>
            </html-el:select>
            &nbsp;查询时间 从:
            <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" />
           	 至：
            <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" />
            &nbsp;&nbsp;
             <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button>
          </td>
      </tr>
    </table>
  </html-el:form>
  
  <div class="pic" id="main" style="width:100%;height:600px;margin-top:10px;"></div>
  
</div>
<script type="text/javascript" src="${ctx}/scripts/setEchartData.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script src="${ctx}/scripts/echarts/dist/echarts-all.js" type="text/javascript"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var myChartLine = echarts.init(document.getElementById('main')); 
	var optionsLine = drawEchartsLine(myChartLine);
	optionsLine.title.text = "销售额统计";
	var urlLine = "${ctx}/CsAjax.do?method=getOrderInfoSumMoneyAndCountAjaxData&"+$(".ajaxForm").serialize();
	setDataEcharts(myChartLine,optionsLine,urlLine,true);
	
	$("#orderDay").click(function(){
		$("#st_date").val("");
		$("#en_date").val("");
	});
	
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		var start_date = $("#st_date").val();
		var end_date = $("#en_date").val();
		var orderDay = $("#orderDay").val();
		
		
		if(null != orderDay && "" != orderDay){
			if(orderDay == 0 && ("" == start_date || "" == end_date)){
				 alert("请选择自定义时间！");
				 return false;
			}
		}
		
		if(end_date != ""){
			  if(start_date > end_date){
				  alert("时间选择有错误！");
				  return false;
			  }
			}
		f.submit();
	});
});

function DayFunc(){
	$("#orderDay").val("");
}


//]]></script>
</body>
</html>
