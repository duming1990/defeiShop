<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/admin/WebsiteIntroduction" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
        </td>
      </tr>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>内容：</td>
        <td colspan="2" width="85%"><html-el:hidden property="content" styleId="content" />
          <script id="content_ue" name="content_ue" type="text/plain" style="width:100%;height:200px;">${af.map.map.content}</script>
        </td>
      </tr>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">发布时间：</td>
        <td  colspan="2" height="24" width="85%"><fmt:formatDate value="${af.map.pub_time}" pattern="yyyy-MM-dd" var="_pub_time" />
          <html-el:text property="pub_time" styleId="pub_time" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker();" style="cursor:pointer;" value="${_pub_time}" /></td>
      </tr>
      
      <tr>
        <td colspan="3" align="center"><html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 
<script type="text/javascript"><!--//<![CDATA[
var column_ids = [];  //需要的自定义字段      
//var editor = KindEditor.create("textarea[id='content']");
var editor = UE.getEditor('content_ue');
editor.ready(function() {editor.on('showmessage', function(type, m){if (m['content'] == '本地保存成功') {return true;}});});
//var editor = KindEditor.create("textarea[name='content']",{items:KindEditor.simpleItems});
                                         
$(document).ready(function(){
	$("#title").attr("dataType", "Require").attr("msg", "标题必须填写");
	//$("#order_value" ).attr("dataType", "Number").attr("msg", "排序值必须为正整数");
	
	String.prototype.trim = function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
    };
	
	
	var f = document.forms[0];
	f.onsubmit = function () {
		if (!editor.hasContents()) {
			alert("新闻内容必须填写");
			editor.focus();
			return false;
		}
		if(Validator.Validate(this, 3)){
			$("#content").val(editor.getContent()); 
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	}

});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>