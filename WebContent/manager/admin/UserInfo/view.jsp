<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="2">用户基本信息</th>
    </tr>
    <tr>
      <td width="15%" class="title_item">登录名：</td>
      <td width="85%">${fn:escapeXml(af.map.user_name)}</td>
    </tr>
    <tr>
      <td class="title_item">真实姓名：</td>
      <td>${fn:escapeXml(af.map.real_name)}</td>
    </tr>
    <tr>
      <td class="title_item">所属区域：</td>
      <td><c:if test="${not empty af.map.full_name}">${fn:escapeXml(af.map.full_name)}</c:if></td>
    </tr>
    <tr>
      <td class="title_item">用户类型：</td>
      <td>${type_name}</td>
    </tr>
    <tr>
      <td class="title_item">性别：</td>
      <td><c:out value="${af.map.sex eq 0 ? '男' : '女'}" /></td>
    </tr>
    <tr>
      <td class="title_item">生日：</td>
      <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-M-d" /></td>
    </tr>
    <tr>
      <td class="title_item">用户头像：</td>
      <td width="85%"><c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
        <c:if test="${not empty af.map.user_logo}">
          <c:set var="img" value="${ctx}/${af.map.user_logo}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.user_logo, 'http://')}">
          <c:set var="img" value="${af.map.user_logo}"/>
        </c:if>
        <a href="${img}" target="_blank"><img src="${img}" height="100" id="user_logo_img" /></a></td>
    </tr>
    <tr>
      <td class="title_item">总消费金额：</td>
      <td width="85%"><fmt:formatNumber var="bi" value="${af.map.leiji_money_user/100}" pattern="0.########"/>
        ${bi}</td>
    </tr>
    <tr>
      <th colspan="2">联系方式</th>
    </tr>
    <tr>
      <td class="title_item">手机：</td>
      <td>${fn:escapeXml(af.map.mobile)}</td>
    </tr>
    <tr>
      <td class="title_item">办公电话：</td>
      <td>${fn:escapeXml(af.map.office_tel)}</td>
    </tr>
    <tr>
      <td class="title_item">电子信箱：</td>
      <td>${fn:escapeXml(af.map.email)}</td>
    </tr>
    <tr>
      <th colspan="2">银行信息</th>
    </tr>
    <tr>
      <td class="title_item">开户银行：</td>
      <td width="85%">${fn:escapeXml(af.map.bank_name)}</td>
    </tr>
    <tr>
      <td class="title_item">开户账号：</td>
      <td>${fn:escapeXml(af.map.bank_account)}</td>
    </tr>
    <tr>
      <td class="title_item">开户名：</td>
      <td width="85%">${fn:escapeXml(af.map.bank_account_name)}</td>
    </tr>
    <tr>
      <th colspan="2">第三方信息</th>
    </tr>
    <tr>
      <td class="title_item">微信ID：</td>
      <td width="85%">${fn:escapeXml(af.map.appid_weixin)}</td>
    </tr>
<!--     <tr> -->
<!--       <td class="title_item">微信唯一ID：</td> -->
<%--       <td width="85%">${fn:escapeXml(af.map.appid_weixin_unionid)}</td> --%>
<!--     </tr> -->
<!--     <tr> -->
<!--       <td class="title_item">是否关注微信平台：</td> -->
<%--       <td width="85%"><c:choose> --%>
<%--           <c:when test="${not empty (af.map.appid_weixin_is_follow)}">是</c:when> --%>
<%--           <c:when test="${empty (af.map.appid_weixin_is_follow)}">否</c:when> --%>
<%--         </c:choose> --%>
<!--       </td> -->
<!--     </tr> -->
<!--     <tr> -->
<!--       <td class="title_item">QQID：</td> -->
<%--       <td width="85%">${fn:escapeXml(af.map.appid_qq)}</td> --%>
<!--     </tr> -->
<!--     <tr> -->
<!--       <td class="title_item">新浪微博ID：</td> -->
<%--       <td width="85%">${fn:escapeXml(af.map.appid_weibo)}</td> --%>
<!--     </tr> -->
    <tr>
      <td class="title_item">排序值：</td>
      <td>${fn:escapeXml(af.map.order_value)}</td>
    </tr>
    <tr>
      <td class="title_item">是否删除：</td>
      <td><c:choose>
          <c:when test="${af.map.is_del eq 1}">是</c:when>
          <c:when test="${af.map.is_del eq 0}">否</c:when>
        </c:choose></td>
    </tr>
    <tr>
      <td colspan="2" style="text-align: center"><html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
function villageInfo(id){
	var url = "${ctx}/manager/admin/UserInfo.do?method=villageInfo&id=" + id;
	$.dialog({
		title:  "村站信息",
		width:  1000,
		height: 700,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
