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
    <html-el:hidden property="method" value="vipList" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index_like" />
    <html-el:hidden property="is_vip" />
    <html-el:hidden property="sereach_servecenter_st_date" />
    <html-el:hidden property="sereach_servecenter_en_date" />
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="tableClassSearch">
      <tr>
        <td>
          &nbsp; 名称：
          <html-el:text property="user_name_like" styleClass="webinput" maxlength="50" style="width:200px;"/>
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
        
        <c:if test="${not empty is_entp}">
         <th nowrap="20%">商家名称</th>
        </c:if>
        <c:if test="${not empty is_fuwu}">
         <th nowrap="20%">合伙人名称</th>
        </c:if>
        <th nowrap="20%">登陆名称</th>
        <th nowrap="20%">邀请人</th>
        <th nowrap="10%">认证时间</th>
        <th width="10%">添加时间</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr align="center">
          <td align="center">${vs.count}</td>
            <c:if test="${not empty is_entp}"><td>${cur.map.entp_name}</td></c:if>
            <c:if test="${not empty is_fuwu}"><td>${cur.map.service_name}</td></c:if>
            <td>${fn:escapeXml(cur.user_name)}</td>
            <td>${fn:escapeXml(cur.ymid)}</td>
            <td><fmt:formatDate value="${cur.map.audit_date}"pattern="yyyy-MM-dd" /></td>
            <td><fmt:formatDate value="${cur.add_date}"pattern="yyyy-MM-dd" /></td>
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

</script>
</body>
</html>
