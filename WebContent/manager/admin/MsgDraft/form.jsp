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
  <html-el:form action="/admin/MsgDraft">
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="queryString" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="info_state" value="1" styleId="info_state"  />
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
        <td style="text-align:left">【吴洋说明：这里改成lhgdialog弹出选择人】
          </td>
      </tr>
      <tr>
        <td width="15%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>主题： </td>
        <td width="85%"><html-el:text property="msg_title" styleId="msg_title" style="width:500px;" styleClass="webinput" maxlength="50"/></td>
      </tr>
      <tr>
        <td class="title_item">信息内容：</td>
        <td><html-el:textarea property="msg_content" styleId="msg_content" style="width:650px;height:200px;visibility:hidden;"></html-el:textarea>
          <div>1、此处上传的图片不会自动缩放，请用相关做图软件缩放成您想要的大小！</div>
          <div>2、点击【第一排】顺数【最后一个】按钮可实现全屏编辑</div></td>
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
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script> 
<script type="text/javascript">//<![CDATA[
var editor = KindEditor.create("textarea[name='msg_content']",{items:KindEditor.simpleItems});

var f = document.forms[0];

$("#msg_title").attr("dataType", "Require").attr("msg", "主题必须填写");
$("#user_ids").attr("dataType", "Require").attr("msg", "请至少选择一个收件人！");

$("#send").click(function(){
	if(Validator.Validate(f, 3)){
		$("#msg_content").val(editor.html());
        $("#send").attr("value", "正在提交...").attr("disabled", "true");
        $("#save").attr("disabled", "true");
        $("#btn_back").attr("disabled", "true");
		f.submit();
	}
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
