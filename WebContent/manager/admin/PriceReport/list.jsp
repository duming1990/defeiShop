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
  <html-el:form action="/admin/PriceReport" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" value="${af.map.p_index}" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
        <div style="margin-top:5px;" id="city_div">所在地区：
            <html-el:select property="province" styleId="province" style="width:120px;" styleClass="pi_prov webinput">
              <html-el:option value="">请选择...</html-el:option>
            </html-el:select>
            &nbsp;
            <html-el:select property="city" styleId="city" style="width:120px;" styleClass="pi_city webinput">
              <html-el:option value="">请选择...</html-el:option>
            </html-el:select>
            &nbsp;
            <html-el:select property="country" styleId="country" style="width:120px;" styleClass="pi_dist webinput">
              <html-el:option value="">请选择...</html-el:option>
            </html-el:select>
            &nbsp;查询时间从:
            <html-el:text property="st_add_date_fmt" styleId="st_add_date_fmt" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
           	 至：
            <html-el:text property="en_add_date_fmt" styleId="en_add_date_fmt" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
            &nbsp;
            <html-el:button value="查 询" styleClass="bgButton" property="btn_submit" styleId="btn_submit" />
            &nbsp;
           <html-el:button value="导 出" styleClass="bgButton" property="download" styleId="download" />
          </div>
          </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="10%">区域</th>
        <th width="10%">用户个数</th>
        <th width="10%">代言人奖励</th>
        <th width="10%">驿站合伙人奖励</th>
        <th width="10%">普通县域合伙人奖励</th>
        <th width="10%">股份县域合伙人奖励</th>
      </tr>
      <c:set var="user_num" value="0"></c:set>
      <c:set var="price_money_1002" value="0"></c:set>
      <c:set var="price_money_1003" value="0"></c:set>
      <c:set var="price_money_1004" value="0"></c:set>
      <c:set var="price_money_1005" value="0"></c:set>
      <c:forEach var="cur" items="${entityList}">
      <tr align="center">
        <td align="center"><a href="${ctx}/manager/admin/PriceReport.do?p_index=${cur.map.p_index}">${cur.map.full_name}</a></td>
        <td align="center">${cur.map.user_num}</td>
        <td align="center"><fmt:formatNumber value="${cur.map.price_money_1002}" pattern="0.##"/>元</td>
        <td align="center"><fmt:formatNumber value="${cur.map.price_money_1003}" pattern="0.##"/>元</td>
        <td align="center"><fmt:formatNumber value="${cur.map.price_money_1004}" pattern="0.##"/>元</td>
        <td align="center"><fmt:formatNumber value="${cur.map.price_money_1005}" pattern="0.##"/>元</td>
        
        <c:set var="user_num" value="${user_num + cur.map.user_num}"></c:set>
        <c:set var="price_money_1002" value="${price_money_1002 + cur.map.price_money_1002}"></c:set>
        <c:set var="price_money_1003" value="${price_money_1003 + cur.map.price_money_1003}"></c:set>
        <c:set var="price_money_1004" value="${price_money_1004 + cur.map.price_money_1004}"></c:set>
        <c:set var="price_money_1005" value="${price_money_1005 + cur.map.price_money_1005}"></c:set>
      </tr>
      </c:forEach>
      <tr align="center">
       <td align="center" style="color: red;">合计:</td>
        <td align="center"><span id="user_num">${user_num}</span></td>
        <td align="center"><span id="price_money_1002"><fmt:formatNumber value="${price_money_1002}" pattern="0.##"/></span>元</td>
        <td align="center"><span id="price_money_1003"><fmt:formatNumber value="${price_money_1003}" pattern="0.##"/></span>元</td>
        <td align="center"><span id="price_money_1004"><fmt:formatNumber value="${price_money_1004}" pattern="0.##"/></span>元</td>
        <td align="center"><span id="price_money_1005"><fmt:formatNumber value="${price_money_1005}" pattern="0.##"/></span>元</td>
      </tr>
    </table>
  </form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        required:false
    });
	
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
	var user_num=$("#user_num").text();
	var price_money_1002=$("#price_money_1002").text();
	var price_money_1003=$("#price_money_1003").text();
	var price_money_1004=$("#price_money_1004").text();
	var price_money_1005=$("#price_money_1005").text();
	
	var link="user_num="+user_num +"&price_money_1002="+price_money_1002+"&price_money_1003="+price_money_1003
			+"&price_money_1004="+price_money_1004+"&price_money_1005="+price_money_1005+"&"
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/PriceReport.do?method=toExcel&mod_id=${af.map.mod_id}&" +link+ $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/PriceReport.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&"+link + $('.searchForm').serialize();
	    }
	    return true;
	};
	var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
	$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
});
function DayFunc(){
	$("#orderDay").val("");
}

function orderList(st_add_date,en_add_date,entp_name_like){
	alert(entp_name_like)
	var url = "${ctx}/manager/admin/OrderQuery.do?st_add_date="+st_add_date+"&en_add_date="+en_add_date+"&entp_name_like="+entp_name_like;
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
