<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine" style="min-height: 500px">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <%@ include file="/commons/pages/messages.jsp" %>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <tr>
      <th colspan="2">站内通知</th>
    </tr>
    <tr>
      <td class="title_item" width="20%">发送人：</td>
      <td>${send_user_name}</td>
    </tr>
    <tr>
      <td class="title_item" width="20%">信息主题：</td>
      <td>${fn:escapeXml(af.map.msg_title)}</td>
    </tr>
    <tr>
      <td class="title_item">信息内容：</td>
      <td>${af.map.msg_content}</td>
    </tr>
    <tr>
      <td class="title_item">发送时间：</td>
      <td><fmt:formatDate value="${af.map.send_time}" pattern="yyyy-MM-dd HH:mm" /></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><c:url var="url" value="/manager/customer/MyMsg.do?par_id=1100630000&mod_id=1100630100&read_state=${af.map.read_state}" />
<%--         <input type="button" value="返 回" class="bgButton" onclick="goUrl('${url}')" /> --%>
        <input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
    </tr>
  </table>
</div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var $msg = $(".user-info-msg", top.document).find("em");
	var minus_msg_count = parseFloat("${af.map.minus_msg_count}");
	var msg_count = $msg.text();
	msg_count = msg_count.replace("(","").replace(")","");
	msg_count = parseFloat(msg_count);
	if (!isNaN(minus_msg_count) && !isNaN(msg_count)) {
		if (minus_msg_count > 0){
			var msg_count_cur = msg_count - minus_msg_count;
			if(msg_count_cur <= 0){
				msg_count_cur = 0;
				$msg.removeClass("blink");
			}
			$msg.text("("+ Number(msg_count_cur) + ")");
		}
	}
});

//]]></script>
</body>
</html>
