<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/commons/styles/nav.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
</head>
<body>
<div class="divContent">
   <div style="padding:5px;" class="tip-success">
    <h3>【${user_name_par}】的上级：</h3>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="table-advanced">
      <tr>
        <th>用户名</th>
        <th>姓名</th>
        <th>手机号</th>
        <th>级别</th>
      </tr>
       <c:forEach var="cur" items="${entityList}" varStatus="vs">
        <tr>
          <td align="center">${fn:escapeXml(cur.map.userInfo.user_name)}</td>
          <td align="center">${fn:escapeXml(cur.map.userInfo.real_name)}</td>
          <td align="center">${fn:escapeXml(cur.map.userInfo.mobile)}</td>
          <td align="center">上${fn:escapeXml(cur.user_par_levle)}级</td>
        </tr>
       </c:forEach>
    </table>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
