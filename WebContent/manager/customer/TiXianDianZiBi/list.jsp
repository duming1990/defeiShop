<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css?v20150103" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/TiXianDianZiBi">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">&nbsp;审核状态：
                <html-el:select property="info_state" styleClass="webinput" >
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="-1">审核不通过</html-el:option>
                  <html-el:option value="0">未审核</html-el:option>
                  <html-el:option value="1">审核通过</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <button class="bgButtonFontAwesome" type="submit"><i class="fa fa-search"></i>查 询</button></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="TiXianDianZiBi.do?method=delete">
    <div style="padding:5px;">
      <button class="bgButtonFontAwesome" type="button" onclick="location.href='TiXianDianZiBi.do?method=add&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}';" ><i class="fa fa-plus-square"></i>提现</button>
     <div class="tipmsg">
<p>余额： <b><fmt:formatNumber pattern="#0.########" value="${bi_dianzi}" /></b></p>
  </div>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th width="18%">申请时间</th>
        <th width="12%">提现金额</th>
        <th width="12%">手续费</th>
        <th width="12%">实际金额</th>
        <th width="12%">审核状态</th>
        <th width="12%">操作</th>
      </tr>
      <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
          <td align="center">${fn:escapeXml(cur.cash_count)}元</td>
          <td align="center"><fmt:formatNumber var="bi" value="${fn:escapeXml(cur.cash_rate)}" pattern="0.00" />
            ${bi}元</td>
          <td align="center">${fn:escapeXml(cur.cash_pay)}元</td>
          <td align="center"><c:choose>
              <c:when test="${cur.info_state eq 0}"><span class="label label-default">未审核</span></c:when>
              <c:when test="${cur.info_state eq 1}"><span class="label label-info">已审核(待付款)</span></c:when>
              <c:when test="${cur.info_state eq -1}"><span class="label label-danger">审核不通过(已退款)</span></c:when>
              <c:when test="${cur.info_state eq 2}"><span class="label label-success">已付款</span></c:when>
              <c:when test="${cur.info_state eq -2}">
              <span class="label label-danger">已退款</span>
              <c:if test="${not empty cur.tuikuan_memo}">
               <span class="label label-danger qtip" title="${cur.tuikuan_memo}">查看退回原因</span>
              </c:if>
              </c:when>
              <c:otherwise>未知</c:otherwise>
            </c:choose></td>
          <td align="center"><c:url var="url" value="/manager/customer/TiXianDianZiBi.do?method=view&amp;id=${cur.id}&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}" />
            <a class="label label-warning" href="${url}">查看详情</a>&nbsp; </td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="black">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="TiXianDianZiBi.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("par_id", "${af.map.par_id}");
            pager.addHiddenInputs("info_state", "${fn:escapeXml(af.map.info_state)}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
	$(".qtip").quicktip();
	
});
</script>
</body>
</html>
