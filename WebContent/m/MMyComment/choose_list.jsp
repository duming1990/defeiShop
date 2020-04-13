<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<div class="content"> 
  <!--article-->
  <div class="list-view">
    <ul class="list-ul" id="ul_data">
      <!-- 未付款、已付款、待评价、物流单、退款单  start -->
      <c:forEach items="${orderInfoDetailsList}" var="cur">
      	<li data-id="${cur.id}"  id="order_state_${cur.id}">
      		<div class="list-item list-room">
	          <div class="info">
	            <h2 class="title">${cur.comm_name}</h2>
	            <div class="ordermain">
	              <p>总价：<em>¥<fmt:formatNumber value="${cur.good_sum_price}" pattern="0.##"/></em></p>
	              <p>数量：<em>${cur.good_count}</em></p>
	            </div>
	            <c:url var="url" value="MMyComment.do?method=add&id=${cur.id}" />
	            <c:if test="${(cur.has_comment eq 0)}">
           		 <div class="orderedit"> <span></span> <span> <a href="javascript:goUrl('${url}');" class="buy">选择</a> </span> </div>
	            </c:if>
	            <c:if test="${(cur.has_comment eq 1)}">
	            <c:url var="url" value="MMyComment.do?method=edit&id=${cur.map.comment_id}" />
           		 <div class="orderedit"> <span></span> <span> <a href="javascript:goUrl('${url}');" style="background-color: rgb(0, 162, 48);" class="buy color-green">已评论(修改)</a> </span> </div>
	            </c:if>
	          </div>
	        </div>
      	</li>
      </c:forEach>
    </ul>
  </div>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
//]]></script>
</body>
</html>