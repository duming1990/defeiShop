<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<jsp:include page="../_nav.jsp" flush="true"/>
<%@ include file="/commons/pages/messages.jsp" %>
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
  <tr>
    <th width="18%">交易流水号</th>
    <th width="13%">商家名</th>
	<th width="8%">产品数量</th>
	<th width="10%">订单金额</th>
	 <th width="10%">订单货款</th>
	<th width="15%">交易完成时间</th>
	<th width="12%">下单人</th>
  </tr>
  <c:forEach var="cur" items="${entityList}">
     	<tr>
     		<td align="center"><a href="MyOrderDetail.do?mod_id=${af.map.mod_id}&amp;order_id=${cur.id}&amp;from=entp">${cur.trade_index}</a></td>
     		<td align="center">${fn:escapeXml(cur.entp_name)}</td>
     		<td align="center">${cur.order_num}</td>
     		<td align="center"><fmt:formatNumber value="${cur.order_money+cur.money_bi}" pattern="0.00"></fmt:formatNumber></td>
     		<td align="center"><fmt:formatNumber value="${cur.entp_huokuan_bi}" pattern="0.00"></fmt:formatNumber></td>
     		 <fmt:formatDate var="_finish_date" value="${cur.finish_date}" pattern="yyyy-MM-dd HH:mm:ss" />
     		<td align="center">${_finish_date}</td>
     		<td align="center">${fn:escapeXml(cur.add_user_name)}</td>
     	</tr>
     </c:forEach>
</table>
</div>
<div class="black">
  <form id="bottomPageForm" name="bottomPageForm" method="post" action="MerchantCheck.do">
    <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
      <tr>
        <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
          <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "listDetails");
            pager.addHiddenInputs("id", "${af.map.id}");
            document.write(pager.toString());
        </script></td>
      </tr>
    </table>
  </form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
  var f = document.forms[0];
  $("#btn_submit").click(function(){
  	f.submit();
  });
  </script>
</body>
</html>
