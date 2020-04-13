<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div align="center" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/OrderStatistica" styleClass="searchForm">
    <html-el:hidden property="method" value="orderInfoDetailsList" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="is_query" value="1"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">订单状态：
                <html-el:select property="order_state" styleId="order_state">
                  <html-el:option value="">请选择...</html-el:option>
                  <html-el:option value="0">已预订</html-el:option>
                  <html-el:option value="10">已付款</html-el:option>
                  <html-el:option value="-10">已取消</html-el:option>
                  <html-el:option value="20">已发货</html-el:option>
                  <html-el:option value="40">已收货</html-el:option>
                </html-el:select>
                 &nbsp;&nbsp;企业名称：
                 <html-el:text property="entp_name_like" maxlength="20" style="width:80px;" styleClass="webinput" />
                &nbsp;&nbsp;时间 从：
                <html-el:text property="st_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                至：
                <html-el:text property="en_add_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                &nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
     <div style="padding-bottom:5px;">
      <input type="button" name="download" id="download" class="bgButton" value="导出Excel"/>
      <c:if test="${ not empty entity2.map.sum_good_num}">
      <c:set var="Sl" value="${entity2.map.sum_good_num}"/>
      </c:if>
      <c:if test="${empty entity2.map.sum_good_num}">
      <c:set var="Sl" value="0"/>
      </c:if>
      <c:if test="${ not empty entity2.map.sum_good_money}">
      <c:set var="Je" value="${entity2.map.sum_good_money}"/>
      </c:if>
      <c:if test="${empty entity2.map.sum_good_money}">
      <c:set var="Je" value="0"/>
      </c:if>
      &nbsp;&nbsp;<span style="color:#F00;">所有产品合计销售数量为${Sl}，所有产品合计销售金额为${Je}元。</span>
    </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th width="10%" nowrap="nowrap">序号</th>
      <th width="20%" nowrap="nowrap">企业名称</th>
      <th>产品统计</th>
    </tr>
    <c:forEach var="cur" items="${entityList}" varStatus="vs">
      <tr align="center">
        <td align="center"> ${vs.count}</td>
        <td align="center"> ${fn:escapeXml(cur.map.entp_name)}</td>
        <td nowrap="nowrap"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
            <tr>
              <th width="40%">产品名称</th>
              <th width="30%" nowrap="nowrap">产品合计销售数量</th>
              <th width="30%" nowrap="nowrap">产品合计销售金额</th>
            </tr>
            <c:forEach var="cur1" items="${cur.entpOrderInfoDetailslist}" varStatus="vs">
              <tr align="center">
                <td align="left">${fn:escapeXml(fnx:abbreviate(cur1.comm_name, 40, '...'))}</td>
                <td align="center">${cur1.map.sum_good_num}</td>
                <td align="center">${cur1.map.sum_good_money}</td>
              </tr>
            </c:forEach>
          </table></td>
      </tr>
    </c:forEach>
  </table>
  <c:if test="${is_query eq 1}">
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="OrderStatistica.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
					var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
					pager.addHiddenInputs("method", "orderInfoDetailsList");
					pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
					pager.addHiddenInputs("order_state", "${af.map.order_state}");
					pager.addHiddenInputs("st_add_date", "${af.map.st_add_date}");
					pager.addHiddenInputs("en_add_date", "${af.map.en_add_date}");
					pager.addHiddenInputs("entp_name_like", "${af.map.entp_name_like}");
					pager.addHiddenInputs("is_query", "1");
					document.write(pager.toString());
	            	</script></td>
        </tr>
      </table>
    </form>
  </div>
  </c:if>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#download").click(function(){
		location.href = "OrderStatistica.do?method=toExcelForOrderInfoDetails&" + $(".searchForm").serialize();
	});
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
