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
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <tr>
       <th colspan="2">区域信息</th>
      </tr>
      <tr>
      	<th nowrap="nowrap" width="40%">县域名称</th>
      	<th nowrap="nowrap" width="50%">所属区域</th>
      </tr>
      <c:forEach items="${entityList}" var="cur">
       <tr class="html">
		 <td align="center">${cur.service_name}</td>
		 <td align="center">${cur.p_name}</td>
	 	</tr>
 	  </c:forEach>
    </table>
</div>
<script type="text/javascript">//<![CDATA[

</script>
</body>
</html>
