<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的订单- ${app_name}</title>
<meta content="${app_name}订单管理" name="keywords" />
<meta content="${app_name}订单" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
 <jsp:include page="../_nav.jsp" flush="true"/>
<fmt:formatNumber var="bi" value="${userInfo.bi_dianzi}" pattern="0.##"/>
<fmt:formatNumber var="bi1" value="${userInfo.bi_dianzi}" pattern="0"/>
<c:if test="${bi1 gt 10000}">
  <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万"/>
</c:if>
<c:url var="url1" value="/manager/customer/TiXianDianZiBi.do?method=add&par_id=1100400000&mod_id=1100400100" />
<c:url var="url2" value="/manager/customer/TiXianDianZiBi.do?method=list&par_id=1100400000&mod_id=1100400100" />
<c:url var="url3" value="/manager/customer/ChongZhiXiaoFeiBi.do?par_id=1100400000&mod_id=1100400400" />
<div class="wrap-2w boder1">
  <div class="icon-yohob dzb">余额</div>
  <p class="text-16">我的余额</p>
  <p class="text-20" style="color:#f9b600">${bi}</p>
  <a onClick="goUrl('${url1}',1100400000)" class="btn">提现</a> 
 <%--  &nbsp;
  <c:if test="${pay_type_is_audit_success}">
    <a onClick="goUrl('${url3}',1100400000)" class="btn btn-hot">充值</a>
  </c:if>
  <c:if test="${(!pay_type_is_audit_success) and (userInfo.is_entp eq 1 or userInfo.is_fuwu eq 1)}">
    <a onClick="goUrl('${url3}',1100400000)" class="btn btn-hot">充值</a>
  </c:if> --%>
  <a onClick="goUrl('${url2}',1100400000)" class="yohob-history">查看提现历史</a> </div>
<fmt:formatNumber var="bi" value="${userInfo.bi_xiaofei}" pattern="0"/>
<c:if test="${bi gt 10000}">
  <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.####万"/>
</c:if>
<%-- <c:url var="url1" value="/manager/customer/ChongZhiXiaoFeiBi.do?par_id=1100400000&mod_id=1100400400" /> --%>
<%-- <c:url var="url2" value="/manager/customer/ChongZhiXiaoFeiBi.do?method=list&par_id=1100400000&mod_id=1100400400" /> --%>
<!-- <div class="wrap-2w boder1" style="margin-left:8px;"> -->
<!--   <div class="icon-yohob xfb">消费币</div> -->
<!--   <p class="text-16">我的消费币</p> -->
<%--   <p class="text-20" style="color:#eb6400">￥${bi}</p> --%>
<%--   <a onClick="goUrl('${url1}',1100400000)" class="btn btn-hot">充值</a> <a onClick="goUrl('${url2}',1100400000)" class="yohob-history">查看充值历史</a> </div> --%>
<div class="clear"></div>
<!-- main end -->
<script type="text/javascript">//<![CDATA[

</script>
</body>
</html>
