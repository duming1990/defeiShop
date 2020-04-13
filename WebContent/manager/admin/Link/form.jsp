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
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/Link" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="upload_image_files" styleId="upload_image_files"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">新闻基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:50%" styleClass="webinput" />
          <a href="javascript:void(0);" id="show_win">选择标题颜色</a>
          <html-el:checkbox property="title_is_strong" styleId="title_is_strong" value="1" onclick="checkweight();" />
          <label for="title_is_strong">加粗</label></td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>链接：</td>
        <td colspan="2" width="85%"><html-el:text property="direct_uri" styleId="direct_uri" maxlength="125" style="width:50%" styleClass="webinput" />&nbsp;请用“http://”开头</td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">主图地址：</td>
        <td colspan="2" width="85%"><c:if test="${not empty (af.map.image_path)}" var="hasImage" ><br/>
            <img src="${ctx}/${af.map.image_path}@s400x400" height="100" title="${af.map.image_desc}" /><br />
            <input type="checkbox" name="chkReUploadImage" id="chkReUploadImage" value="1" onclick="$('#image_path').toggle();" />
            <label for="chkReUploadImage">重新上传主图</label>
            <br/>
            <html-el:file property="image_path" style="display:none;width:500px;" styleId="image_path" />
          </c:if>
          <c:if test="${empty (af.map.image_path)}">
            <html-el:file property="image_path" style="width:500px;" styleId="image_path" />
          </c:if>
          <div>上传的主图会自动缩放成合适的尺寸，主图宽高比例最好是4:3，否则在幻灯片显示中会变形。</div></td>
      </tr>
      <tr>
        <td width="15%" class="title_item">排序值：</td>
        <td colspan="2" width="85%"><html-el:text property="order_value" styleId="order_value" maxlength="4" size="4" styleClass="webinput" />
          值越大，显示越靠前。</td>
      </tr>
      <tr style="display:none;">
        <td width="15%" class="title_item">信息状态：</td>
        <td colspan="2" width="85%"><html-el:select property="info_state" styleId="info_state" >
            <html-el:option value="0">默认（待审核）</html-el:option>
            <html-el:option value="3">已审核（已发布）</html-el:option>
            <html-el:option value="-3">已审核（未发布）</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
    <jsp:include page="public_color_select.jsp" flush="true"/>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script>  
<script type="text/javascript"><!--//<![CDATA[
$(document).ready(function(){
	$("#title").attr("dataType", "Require").attr("msg", "标题必须填写");
	//$("#info_source" ).attr("dataType", "Require").attr("msg", "信息来源必须填写");
	$("#image_path"  ).attr("dataType", "Filter" ).attr("msg", "图片的格式必须是(bmp,gif,jpeg,jpg,png)").attr("require", "false").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#order_value").attr("dataType", "Integer").attr("msg", "排序值必须为整数");
	$("#direct_uri").attr("dataType", "Url").attr("msg", "直接URI格式不合法,例如：http://www.baidu.com").attr("require", "true");
	
	$("#order_value").focus(setOnlyNum);
	$("#show_win").click(function() {
		$("#win").dialog( {
			open : function() {
				$("body > div[role=dialog]").appendTo($(document.forms[0]));
			},
			buttons : {
				"确定" : function() {
							$(this).dialog("close");
							var c = $("input[name='title_color']:checked").val();
							$("#title").css("color", c);
						},
				"取消" : function() {$(this).dialog("close");}
			},
			modal : true,
			title : '选择标题颜色'
		}).dialog("open");
	});
	var f = document.forms[0];
	f.onsubmit = function () {
		if(Validator.Validate(this, 3)){
            $("#b_s").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	}
});

function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = 0;
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>