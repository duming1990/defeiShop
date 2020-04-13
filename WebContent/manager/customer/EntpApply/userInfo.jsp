<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <html-el:form styleClass="ajaxForm">
    <html-el:hidden property="own_entp_id" styleId="own_entp_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
     <tr>
        <th colspan="2" style="color: red;font-size: 20px;">已开通用户</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">店名：</td>
        <td colspan="3">${fn:escapeXml(userInfo.own_entp_name)}</td>
      </tr>
      <tr>  
        <td width="15%" class="title_item">登录名：</td>
        <td colspan="3">${fn:escapeXml(userInfo.user_name)}
        &nbsp;<button class="bgButton" type="button" onclick="initPassword(${userInfo.id});">初始化密码</button></td>
      </tr>
      <tr>
        <th colspan="2">联系方式</th>
      </tr>
      <tr>  
        <td width="15%" class="title_item">手机：</td>
        <td colspan="3">${fn:escapeXml(userInfo.mobile)}</td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript">//<![CDATA[

function initPassword(id) {
	if (confirm("确认要初始化密码吗？")) {
		var password = prompt("请输入您的新密码,如果不输入,默认初始密码为“${init_pwd}”。","");
		if (null != password) {
			if (password.length == 0) {
				password = "${init_pwd}";
			}
			$.post("EntpApply.do?method=initPassword",{uid : id, password : password},function(data){
				if(null != data.result){alert(data.msg);} else {alert("初始化密码失败");}
			});
		}
	}
	return false;
}
//]]></script>
</body>
</html>
