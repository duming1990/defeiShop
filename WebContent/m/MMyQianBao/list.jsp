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
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/account-sys.css?v20160106"  />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />	
<fmt:formatNumber var="bi" value="${userInfo.bi_dianzi}" pattern="0.##"/>
<fmt:formatNumber var="bi1" value="${userInfo.bi_dianzi}" pattern="0"/>
<c:if test="${bi1 gt 10000}">
  <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万"/>
</c:if>
<c:url var="url1" value="/m/MTiXianDianZiBi.do?method=add&mod_id=1100400100" />
<c:url var="url2" value="/m/MTiXianDianZiBi.do?method=list&mod_id=1100400100" />
<c:url var="url3" value="/m/MChongZhiXiaoFeiBi.do?&mod_id=1100400400" />
<c:url var="url4" value="/m/MBiDianZiTransfer.do?mod_id=1100400500" />
<div class="content"> 
  <div class="wrap-2w" style="width:100%;background: #fff;">
	  <div class="icon-yohob dzb">余额</div>
	  <p class="text-16">我的余额</p>
	  <p class="text-20" style="color:#f9b600">${bi}余额</p>
	  <input type="button" class="com-btn btn" value="提现" onclick="goUrl('${url1}')" style="width:80%;margin-bottom:10px;"/>
	  <c:if test="${((!pay_type_is_audit_success) and (userInfo.is_entp eq 1 or userInfo.is_fuwu eq 1)) or (pay_type_is_audit_success)}">
	   <input type="button" class="com-btn" value="充值"  onclick="goUrl('${url3}')" style="width:80%;margin-bottom: 10px;"/>
	  </c:if>
	  
	  <input type="button" class="com-btn" value="转账" onclick="goUrl('${url4}')" style="width:80%;margin-bottom:10px;"/>
	  
	  <a onclick="goUrl('${url2}')" class="yohob-history">查看提现历史</a>
  </div>
  
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[

//]]></script>
</body>
</html>