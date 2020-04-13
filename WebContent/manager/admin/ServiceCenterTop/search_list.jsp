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
  <html-el:form action="/admin/ServiceCenterTop" styleClass="searchForm">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td><!--            &nbsp; 商品名称： -->
          <%--             <html-el:text property="comm_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/> --%>
          &nbsp;排序优先级：
          <html-el:select property="right_join" styleId="right_join">
            <html-el:option value="">合伙人优先</html-el:option>
            <html-el:option value="1">商家销售额优先 </html-el:option>
          </html-el:select>
          &nbsp;
          &nbsp;  时间：(默认为总排名)
          <html-el:text property="sereach_servecenter_date" styleClass="webinput"  styleId="sereach_servecenter_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM'});"/>
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
          <!--            &nbsp; -->
          <%--            <html-el:button value="导 出" styleClass="bgButton" property="download" styleId="download" /> --%>
        </td>
      </tr>
    </table>
  </html-el:form>
  
  <html-el:form action="/admin/ServiceCenterTop" styleClass="searchForm">
    <html-el:hidden property="method" value="search" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
          &nbsp; 合伙人名称：
          <html-el:text property="service_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
          &nbsp;  时间：
                     从<html-el:text property="sereach_servecenter_st_date" styleClass="webinput"  styleId="sereach_servecenter_st_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
         -
          <html-el:text property="sereach_servecenter_en_date" styleClass="webinput"  styleId="sereach_servecenter_en_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
          &nbsp;
          <html-el:submit value="查 询" styleClass="bgButton" styleId="bgButton" />
        </td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr class="tite2">
        <th width="5%" nowrap="nowrap">排序</th>
        <th nowrap="20%">合伙人名称</th>
        <th nowrap="20%">所属会员</th>
        <th nowrap="10%">所属商家</th>
        <th width="10%">下级合伙人</th>
        <th width="10%">营业总额</th>
      </tr>
      <c:forEach var="cur" items="${serviceCenterInfoList}" varStatus="vs">
        <tr align="center">
          <td align="center">${vs.count}</td>
            <td>${cur.servicecenter_name}</td>
            <td><a onclick="vip(${cur.add_user_id},1,${cur.map.p_index_like} );">${cur.map.userInfoCountVip}</a></td>
            <td><a onclick="vip(${cur.add_user_id},0,${cur.map.p_index_like} );">${cur.map.userInfoCountEntp}</a></td>
            <td><a onclick="vip(${cur.add_user_id},3,${cur.map.p_index_like} );">${cur.map.userInfoCountFuwu}</a></td>
            <td><fmt:formatNumber pattern="#,##0.00" value="${cur.map.serviceSaleMoney}"/>元</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	 $("#download").click(function(){
			var submit = function (v, h, f) {
			    if (v == true) {
			    	location.href = "${ctx}/manager/admin/CommSaleTop.do?method=toExcel&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
			    } else {
			    	location.href = "${ctx}/manager/admin/CommSaleTop.do?method=toExcel&code=GBK&mod_id=${af.map.mod_id}&" + $('.searchForm').serialize();
			    }
			    return true;
			};
			var tip = "确认导出EXCEL格式数据？如果UTF-8编码格式乱码，请选择GBK编码格式下载！";
			$.jBox.confirm(tip, "系统提示", submit, { buttons: { '下载(UTF-8编码)': true, '下载(GBK编码)': false} });
		});
	 
});  
var f = document.forms[0];
function vip(user_par_id,is_vip,p_index_like){
	var sereach_servecenter_st_date=$("#sereach_servecenter_st_date").val();
	var sereach_servecenter_en_date=$("#sereach_servecenter_en_date").val();
	
	var url = "${ctx}/manager/admin/ServiceCenterTop.do?method=vipList&user_par_id="+user_par_id+"&p_index_like="+p_index_like+"&is_vip="+is_vip+"&sereach_servecenter_st_date="+sereach_servecenter_st_date+"&sereach_servecenter_en_date="+sereach_servecenter_en_date;
	$.dialog({
		title:  "明细",
		width:  1500,
		height: 800,
        lock:true ,
		content:"url:"+url
	});
}
</script>
</body>
</html>
