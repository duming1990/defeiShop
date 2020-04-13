<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Keywords" content="${app_name}," />
<title>注册成功 - ${app_name}</title>
<link href="${ctx}/styles/indexv3/css/top.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/bottom.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/login_register.css" rel="stylesheet" type="text/css"  />
<link href="${ctx}/styles/indexv2/css/left_sort.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/nmnetwork.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<!--first start -->
<div class="registerbox" style="margin-top: 10px;">
  <div class="registertab1">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="rtable5">
      <tr>
        <td width="247"><img alt="输入注册信息" src="${ctx}/styles/index/images/step1b.gif" width="247" height="29" /></td>
        <td width="250"><img alt="选择激活方式" src="${ctx}/styles/index/images/step2b.gif" width="250" height="29" /></td>
        <td><img alt="注册成功" src="${ctx}/styles/index/images/step3.gif" width="252" height="28" /></td>
      </tr>
    </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="rtable4">
      <tr>
        <td align="center"><img src="${ctx}/styles/index/images/icon_right.gif" width="74" height="67" /></td>
        <td><font class="orange"><b>恭喜您注册成功，请耐心等待审核结果！</b>系统将在<font style="color: #f00;">5秒</font>内跳转至首页！</font></td>
      </tr>
      <tr>
        <td height="35" align="center">&nbsp;</td>
        <td align="left"><img src="${ctx}/styles/index/images/pic.gif" width="258" height="130" /></td>
      </tr>
    </table>
  </div>
  <div class="clear"></div>
</div>
<!-- friend end -->
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.min.js"></script>

<script type="text/javascript" src="${ctx}/scripts/jquery.soChange.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.mycarousel.js"></script>
<script type="text/javascript">//<![CDATA[
var i = 5;
clock();
function clock(){
    i = i - 1;
	if (i > 0) {
	  setTimeout("clock();", 1000);
	} else {
		location.href = '${ctx}/index.shtml';
	}
}
//]]></script>
</body>
</html>