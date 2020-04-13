<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>销售明细 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/StatisticsOrderInfoDetails" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />  
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td>        
         <div style="margin-top: 5px;">
         <c:if test="${is_fuwu eq 1}">
           &nbsp;所属店铺：
            <html-el:hidden property="own_entp_id" styleId="own_entp_id" />                                                                    
            <html-el:text property="entp_name" styleId="entp_name" maxlength="125" styleClass="webinput" readonly="true" onclick="openEntpChild()" />
            </c:if>       
                              商品名称：
            <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
           &nbsp;直接查询：
            <html-el:select property="orderDay" styleId="orderDay">
              <html-el:option value="">总报表</html-el:option>
              <html-el:option value="1">上月报表</html-el:option>
              <html-el:option value="2">上周报表</html-el:option>
              <html-el:option value="3">昨日报表</html-el:option>
            </html-el:select>
             &nbsp;销售类型：
            <html-el:select property="orderType" styleId="orderType">
              <html-el:option value="0">全部</html-el:option>
              <html-el:option value="90">线下销售</html-el:option>
            </html-el:select>
            &nbsp;查询时间 从:
            <html-el:text property="st_date" styleId="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" />
           	 至：
            <html-el:text property="en_date" styleId="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" />
            &nbsp;&nbsp;
            <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-search"></i>查 询</button>
            <input id="download" type="button" value="导出" class="bgButton" />
          </div></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="StatisticsOrderInfoDetails.do?method=delete">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
        <th width="5%">序号</th>
        <c:if test="${is_fuwu eq 1}"><th width="15%">店铺</th></c:if>
        <th width="15%">订单流水号</th>
        <th width="10%">支付时间</th>
        <th nowrap="nowrap">商品名称</th>
        <th width="15%">订单状态</th>
        <th width="8%">单价</th>
        <th width="10%">商品数量</th>
        <th width="8%">总金额</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td>${vs.count}</td>
          <c:if test="${is_fuwu eq 1}"><td>${fn:escapeXml(cur.map.entp_name)}</td></c:if>
          <td>${fn:escapeXml(cur.map.trade_index)}</td>
          <td><fmt:formatDate value="${cur.map.pay_date}" pattern="yyyy-MM-dd HH:mm" /></td>
          <td>${fn:escapeXml(cur.comm_name)}</td>
          <td>
          <c:forEach var="orderState" items="${orderStateList}">
           <c:if test="${cur.map.order_state eq orderState.index}">
            ${orderState.name}
           </c:if>
          </c:forEach>
          </td>
          <td><fmt:formatNumber pattern="￥#,##0.00" value="${cur.good_price}"/></td>
          <td>${fn:escapeXml(cur.good_count)}</td>
          <td><fmt:formatNumber pattern="￥#,##0.00" value="${cur.good_sum_price}"/></td>
        </tr>
        <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="StatisticsOrderInfoDetails.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "list");
					pager.addHiddenInputs("comm_name_like", "${fn:escapeXml(af.map.comm_name_like)}");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
			        pager.addHiddenInputs("par_id", "${af.map.par_id}");
			        pager.addHiddenInputs("begin_date", "${af.map.begin_date}");
			        pager.addHiddenInputs("end_date", "${af.map.end_date}");
			        pager.addHiddenInputs("orderType", "${fn:escapeXml(af.map.orderType)}");
					document.write(pager.toString());
	            	</script>
	      </td>
        </tr>
      </table>
    </form>
  </div>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
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

$("#download").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/customer/StatisticsOrderInfoDetails.do?method=toExcel&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&" + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/customer/StatisticsOrderInfoDetails.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}&" + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
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
//]]></script>
</body>
</html>
