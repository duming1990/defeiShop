<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/autocomplete/autocomplete.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div style="width: 99%" class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div><br />
  <div align="center">
  	<font size="5" style="font-weight: 800">${app_name_min}企业一共有</font><font color="red" size="8">${swEntp}</font><font size="5" style="font-weight: 800">家，</font><br />
  	<!-- 
  	<font size="5" style="font-weight: 800">其中农畜企业有</font><font color="red" size="8">${ncEntp}</font><font size="5" style="font-weight: 800">家,</font><br />
  	<font size="5" style="font-weight: 800">明星企业有</font><font color="red" size="8">${mxEntp}</font><font size="5" style="font-weight: 800">家,</font><br />
  	 -->
  	<font size="5" style="font-weight: 800">诚信企业有</font><font color="red" size="8">${cxEntp}</font><font size="5" style="font-weight: 800">家。</font><br />
  	<font size="5" style="font-weight: 800">失信企业有</font><font color="red" size="8">${sxEntp}</font><font size="5" style="font-weight: 800">家。</font>
  </div>
  <div align="center"><input type="button" onclick="goUrl();" class="bgButton" value="企业详细信息查询"/></div>
</div>

<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocomplete.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
});
function goUrl(){
	location.href='EntpInfo.do?method=list&mod_id=' + 1001001000;
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
