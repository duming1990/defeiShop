<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/admin/PushInfo" enctype="multipart/form-data" styleClass="pushform">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">推送基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>推送标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
        </td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>推送摘要：</td>
        <td colspan="2" width="85%"><html-el:textarea styleId="summary" property="summary" rows="7" style="width:597px" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>推送内容：</td>
        <td colspan="2" width="85%"><html-el:textarea property="content" styleId="content" style="width:650px;height:200px;visibility:hidden;"></html-el:textarea>
        </td>
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
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript"><!--//<![CDATA[

var editor = KindEditor.create("textarea[id='content']");
//var editor = KindEditor.create("textarea[name='content']",{items:KindEditor.simpleItems});

$(document).ready(function(){

	//$("#image_path").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#title"		 ).attr("dataType", "Require").attr("msg", "标题必须填写");
	//$("#info_source" ).attr("dataType", "Require").attr("msg", "信息来源必须填写");
	//$("#invalid_date").attr("dataType", "Require").attr("msg", "失效时间必须填写").attr("require", "false");
	$("#summary"     ).attr("datatype", "Limit"  ).attr("min", "1").attr("max", "100").attr("msg", "摘要内容必须在1-100个字之内");
	
	var f = $(".pushform").get(0);
	f.onsubmit = function () {
		if (editor.isEmpty()) {
			alert("推送内容必须填写");
			editor.focus();
			return false;
		}
		$("#content").val(editor.html());
		
		if(Validator.Validate(this, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	}

});


function deleteFile(id, file_path){
	 var k = window.confirm("您确定要删除吗?");
	 if (k) {
			$.post("NewsInfo.do?method=deleteFile" , {
				id : id , file_path : file_path
			}, function(d){
				if(d == "success"){
					$("#del_" + id).parent().remove();
				}
			});
	 } 
}


function deleteImageOrVideo(id, type, file_path){
	 var k = window.confirm("您确定要删除吗?");
	 if (k) {
			$.post("NewsInfo.do?method=deleteImageOrVideo" , {
				id : id ,type: type, file_path : file_path
			}, function(d){
				if(d == "success"){
					$("#" + type + "_div").remove();
				}
			});
	 } 
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
