<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
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
  <html-el:form action="/admin/Msg.do">
    <html-el:hidden property="method" value="send" />
    <html-el:hidden property="queryString" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="info_state" styleId="info_state"/>
    <html-el:hidden property="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">写信</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">发件人：</td>
        <td style="text-align:left">${userInfo.real_name}</td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>收件人：</td>
        <td style="text-align:left">
<!--           <div> -->
<%--             <html-el:checkbox property="is_send_all" value="1" styleId="is_send_all"></html-el:checkbox> --%>
<!--             <label for="is_send_all">&nbsp;发送全部人</label> -->
<!--           </div> -->
          <html-el:hidden property="user_ids" styleId="user_ids" />
          <html-el:textarea property="user_names" styleId="user_names" readonly="true" value="${af.map.user_names}" rows="2" styleClass="webtextarea" onclick="getuser_names();" style="width:80%;"/>
          &nbsp; </td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>主题： </td>
        <td width="85%"><html-el:text property="msg_title" styleId="msg_title" style="width:500px;" styleClass="webinput" maxlength="50"/></td>
      </tr>
      <tr>
        <td class="title_item">信息内容：</td>
        <td><script id="msg_content_ue" name="msg_content_ue" type="text/plain" style="width:90%;height:200px;">${af.map.comm_content}</script>
          <html-el:hidden property="msg_content" styleId="msg_content" /></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
        <td><html-el:button styleClass="bgButton" value="发 送" styleId="send" property="send" />
          &nbsp;
          <html-el:button styleClass="bgButton" property="back" value="取 消" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>

<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">//<![CDATA[
var editor = UE.getEditor('msg_content_ue', {toolbars: UEDITOR_CONFIG.toolbarsmobile,maximumWords:1000});
editor.ready(function() {editor.on('showmessage', function(type, m){if (m['content'] == '本地保存成功') {return true;}});});

var f = document.forms[0];

$("#msg_title").attr("dataType", "Require").attr("msg", "主题必须填写");
$("#user_ids").attr("dataType", "Require").attr("msg", "请至少选择一个收件人！");

$("#is_send_all").click(function(){
	if($(this).attr("checked")){
		$("#user_names").after("<span id='selectAll'>【全部用户】</span>");
		$("#user_names").hide();
		$("#user_ids").attr("require", "false")
	} else {
		$("#selectAll").remove();
		$("#user_names").show();
		$("#user_ids").attr("require", "true")
	}
});
$("#send").click(function(){
	if(Validator.Validate(f, 3)){
		$("#info_state").val("1");
		$("#msg_content").val(editor.getContent()); 
        $("#send").attr("value", "正在提交...").attr("disabled", "true");
        $("#save").attr("disabled", "true");
        $("#btn_back").attr("disabled", "true");
		f.submit();
	}
});
$("#save").click(function(){
	$("#info_state").val("0");
	$("#msg_content").val(editor.getContent()); 
    $("#save").attr("value", "正在提交...").attr("disabled", "true");
    $("#btn_reset").attr("disabled", "true");
    $("#btn_back").attr("disabled", "true");
	f.submit();
});
function getuser_names() {
	var user_ids  =$("#user_ids").val();
	var user_names  =$("#user_names").val();
	var url = "${ctx}/CsAjax.do?method=getUserInfo&type=multiple&user_ids=" + user_ids;
	$.dialog({
		title:  "选择收件人",
		width:  770,
		height: 550,
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
