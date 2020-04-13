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
  <html-el:form action="/admin/NewsInfoSingle" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <c:if test="${af.map.mod_id eq 1006002200}">
    <html-el:hidden property="method" styleId="method" value="save" />
    </c:if>
    <c:if test="${af.map.mod_id eq 1006002300}">
    <html-el:hidden property="method" styleId="method" value="saveLunbo" />
    </c:if>
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="column_ids" styleId="column_ids" />
    <html-el:hidden property="upload_image_files" styleId="upload_image_files"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">广告基本信息</th>
      </tr>
     <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>广告标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
        </td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>广告图片：</td>
        <td colspan="2" width="85%"><c:if test="${not empty (af.map.image_path)}" var="hasImage" >
            <div id="image_path_div"> <img src="${ctx}/${af.map.image_path}@compress" width="200"/> (<a href="javascript:void(0);" onclick= "deleteImageOrVideo('${af.map.id}', 'image_path', '${af.map.image_path}') ">删除主图</a>)</div>
            <input type="checkbox" name="chkReUploadImage" id="chkReUploadImage" value="1" onclick="$('#image_path').toggle();" />
            <label for="chkReUploadImage">重新上传主图</label>
            <br/>
            <html-el:file property="image_path" style="display:none;width:500px;" styleId="image_path" />
          </c:if>
          <c:if test="${empty (af.map.image_path)}">
            <html-el:file property="image_path" style="width:500px;" styleId="image_path" />
          </c:if>
          <div style="padding: 5px;color: red">上传的广告图片尺寸：1242px * 2208px，否则在APP显示中会变形。</div></td>
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
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript"><!--//<![CDATA[

var column_ids = [];  //需要的自定义字段      

$(document).ready(function(){

	$("#image_path").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#title"		 ).attr("dataType", "Require").attr("msg", "标题必须填写");
	<c:if test="${not empty af.map.image_path}">
		$("#image_path").removeAttr("dataType");
	</c:if>
	
	String.prototype.trim = function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	$t.blur(function() {
        $(this).val(this.value.trim());                           
    });
	
	
	var f = document.forms[0];
	f.onsubmit = function () {
		if(Validator.Validate(this, 1)){
			$("#content").val(editor.getContent()); 
            $("#b_s").attr("value", "正在提交...").attr("disabled", "true");
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
