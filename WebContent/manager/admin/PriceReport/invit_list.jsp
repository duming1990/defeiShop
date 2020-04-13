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
    <html-el:hidden property="method" value="villageUserInvitReport" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
        <div style="margin-top:5px;" id="city_div">所在地区：
             <html-el:select property="province" styleId="province" styleClass="pi_prov">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		          &nbsp;
		          <html-el:select property="city" styleId="city" styleClass="pi_city">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		          &nbsp;
		          <html-el:select property="country" styleId="country" styleClass="pi_dist">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		           &nbsp; 	
		          <html-el:select property="town" styleId="town" styleClass="pi_town">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
		           &nbsp;
		          <html-el:select property="village" styleId="village" styleClass="pi_village">
		            <html-el:option value="">请选择...</html-el:option>
		          </html-el:select>
            &nbsp;查询时间从:
            <html-el:text property="st_date" styleId="st_date" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" />
           	 至：
            <html-el:text property="en_date" styleId="en_date" size="16" maxlength="16" readonly="true" styleClass="webinput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})"/>
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
      </tr>
      <c:set var="user_num" value="0"></c:set>
      <c:forEach var="cur" items="${list}">
      <tr align="center">
        <td align="center"><a href="${ctx}/manager/admin/PriceReport.do?method=villageUserInvitReport&p_index=${cur.map.p_index}&st_date=${af.map.st_date}&en_date=${af.map.en_date}">${cur.map.full_name}</a>
        	<c:if test="${show_village}">
        		(${cur.map.village_name})
        	</c:if>
        </td>
        <td align="center">${cur.map.user_num}</td>
        <c:set var="user_num" value="${user_num + cur.map.user_num}"></c:set>
      </tr>
      </c:forEach>
       <tr align="center">
        <td align="center" style="color: red;">合计:</td>
        <td align="center"><span id="user_num">${user_num}</span></td>
      </tr>
    </table>
  </form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);
	
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
	var link="user_num="+user_num+"&"
	var submit = function (v, h, f) {
	    if (v == true) {
	    	location.href = "${ctx}/manager/admin/PriceReport.do?method=toInvitExcel&mod_id=${af.map.mod_id}&"+link + $('.searchForm').serialize();
	    } else {
	    	location.href = "${ctx}/manager/admin/PriceReport.do?method=toInvitExcel&code=GBK&mod_id=${af.map.mod_id}&" +link+ $('.searchForm').serialize();
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
