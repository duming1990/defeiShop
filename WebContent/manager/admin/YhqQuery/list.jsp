<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />

</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div style="width: 99%" class="divContent">
<div class="subtitle">
  <h3>${naviString}</h3>
</div>
    <html-el:form action="/admin/YhqQuery" styleClass="searchForm">
      <html-el:hidden property="method" value="list" />
      <html-el:hidden property="mod_id" />
      <table width="100%" border="0" cellspacing="1" cellpadding="1" class="tableClassSearch">
        <tr>
          <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
              <tr>
                <td width="5%" nowrap="nowrap">优惠券名称：
                <html-el:text property="yhq_name_like" styleId="yhq_name_like" style="width:140px;"  size="12" maxlength="50" styleClass="webinput"/>
                &nbsp;&nbsp;银联订单号：
                <html-el:text property="trade_merger_index" maxlength="20" style="width:140px;" styleClass="webinput" />
                &nbsp;&nbsp;${app_name_min}订单号：
                <html-el:text property="trade_index" maxlength="20" style="width:140px;" styleClass="webinput" />
              	 <br/>
              	 <div style="margin-top:5px;">
              	  兑换企业名称：
                <html-el:text property="entp_name_like" styleId="entp_name_like" style="width:140px;"  size="12" maxlength="50" styleClass="webinput"/>
                  &nbsp;&nbsp;订单时间从：
                <html-el:text property="st_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
                	至：
                <html-el:text property="en_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" />
              &nbsp;&nbsp; 支付方式：
               <html-el:select property="pay_type" styleId="pay_type" styleClass="webinput" >
                  <html-el:option value="">请选择...</html-el:option>
                  <html-el:option value="0">货到付款</html-el:option>
                  <html-el:option value="2">银联支付</html-el:option>
                </html-el:select>
                 &nbsp;&nbsp;是否删除：
               <html-el:select property="is_del" styleId="is_del" styleClass="webinput" >
                  <html-el:option value="">请选择...</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                 &nbsp;&nbsp;
                <html-el:button property="bgButton" styleId="bgButton" value="查 询" styleClass="bgButton" />
                </div>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
    </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
   <div style="padding-bottom:5px;">  
      <input id="download" type="button" value="导出优惠券列表" class="bgButton" />
    </div>    
  <form id="listForm" name="listForm" method="post" action="YhqQuery.do">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
        <tr>
            <th width="5%">序号</th>
            <th>优惠券名称</th>
            <th width="15%">兑换企业名称</th>
            <th width="12%">银联订单号</th>
            <th width="12%">${app_name_min}订单号</th>
            <th width="7%">优惠券金额</th>
            <th width="8%">订单金额</th>
		    <th width="6%">支付方式</th>
		    <th width="7%">订单状态</th>
		    <th width="7%">订单日期</th>
		    <th width="6%">是否删除</th>
            <th width="6%">操作</th>
        </tr>
        <c:forEach var="cur" items="${entityList}" varStatus="vs">
        	<tr>
            <td align="center">${vs.count}</td>
            <td>${fn:escapeXml(cur.map.yhqCfInfo.yhq_name)}</td>
            <td>${fn:escapeXml(cur.map.orderInfo.entp_name)}</td>
            <td align="center">
            <c:if test="${cur.map.orderInfo.pay_type eq 0}">
          	  货到付款不存在银联订单号
            </c:if>
            <c:if test="${cur.map.orderInfo.pay_type eq 2}">
            ${cur.map.orderInfo.trade_merger_index}
            </c:if>
            </td>
            <td align="center">${cur.map.orderInfo.trade_index}</td>
            <td align="center"><fmt:formatNumber value="${cur.yhq_money}" pattern="0.00元"/></td>
            <td align="center" nowrap="nowrap">
          	<fmt:formatNumber value="${cur.map.orderInfo.order_money}" pattern="0.00元" />
          	</td>
         <td align="center">
         <c:if test="${cur.map.orderInfo.pay_type eq 0}">货到付款</c:if>
        <c:if test="${cur.map.orderInfo.pay_type eq 2}">银联支付</c:if></td>
        <td align="center">
        <c:choose>
             <c:when test="${cur.map.orderInfo.order_state eq -10}">
               <c:out value="已取消" />
             </c:when>
             <c:when test="${cur.map.orderInfo.order_state eq 0}">
               <c:out value="已预订" />
             </c:when>
             <c:when test="${cur.map.orderInfo.order_state eq 10}">
               <c:if test="${cur.map.orderInfo.pay_type eq 0}">
                 <c:out value="等待发货" />
               </c:if>
               <c:if test="${cur.map.orderInfo.pay_type eq 2 or cur.map.orderInfo.pay_type eq 1 or cur.map.orderInfo.pay_type eq 3}">
                 <c:out value="已付款" />
               </c:if>
             </c:when>
             <c:when test="${cur.map.orderInfo.order_state eq 20}">
               <c:out value="已发货" />
             </c:when>
             <c:when test="${cur.map.orderInfo.order_state eq 30}">
               <c:out value="已到货" />
             </c:when>
             <c:when test="${cur.map.orderInfo.order_state eq 40}"> 已收货<br />
               (<span style="color: green;">交易成功</span>) </c:when>
             <c:when test="${cur.map.orderInfo.order_state eq 90}">
               <c:out value="已关闭" />
             </c:when>
    	</c:choose>
          </td>
        <td align="center"><fmt:formatDate value="${cur.map.orderInfo.order_date}" pattern="yyyy-MM-dd" /></td>
         <td align="center">
         <c:if test="${cur.is_del eq 0}">未删除</c:if>
         <c:if test="${cur.is_del eq 1}">已删除</c:if></td>
        <td align="center"><a title="查看" href="YhqQuery.do?method=view&amp;mod_id=${af.map.mod_id}&amp;id=${cur.id}"><img src="${ctx}/styles/images/chakan.gif" width="55" height="18" /></a></td>
        </tr>
        </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="YhqQuery.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("yhq_name_like", "${af.map.yhq_name_like}");
            pager.addHiddenInputs("entp_name_like", "${af.map.entp_name_like}");
        	pager.addHiddenInputs("trade_merger_index", "${af.map.trade_merger_index}");
			pager.addHiddenInputs("trade_index", "${af.map.trade_index}");
			pager.addHiddenInputs("pay_type", "${af.map.pay_type}");
			pager.addHiddenInputs("st_date", "${af.map.st_date}");
			pager.addHiddenInputs("en_date", "${af.map.en_date}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
 
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#download").click(function(){
		location.href="YhqQuery.do?method=toExcel&"+$(".searchForm").serialize();
	});
	
	var f = document.forms[0];
	$("#bgButton").click(function(){
		f.submit();
	});
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
