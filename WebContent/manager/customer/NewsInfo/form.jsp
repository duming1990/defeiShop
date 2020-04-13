<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/admin/css/admin.css"  />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/NewsInfo" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
     <html-el:hidden property="column_ids" styleId="column_ids" />
    <html-el:hidden property="upload_image_files" styleId="upload_image_files"/>
    <html-el:hidden property="link_id" styleId="link_id"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">新闻基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
          <a href="javascript:void(0);" id="show_win">选择标题颜色</a>
          <html-el:checkbox property="title_is_strong" styleId="title_is_strong" value="1" onclick="checkweight();" />
          <label for="title_is_strong">加粗</label></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">所属语言：</td>
        <td colspan="2" width="85%"><html-el:select property="locale_name" styleId="locale_name">
            <html-el:option value="zh_CN">中文简体</html-el:option>
            <html-el:option value="zh_TW">中文繁体</html-el:option>
            <html-el:option value="en">英文</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">短标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title_short" styleId="title_short" maxlength="125" style="width:597px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">副标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title_sub" styleId="title_sub" maxlength="125" style="width:597px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">作者：</td>
        <td colspan="2" width="85%"><html-el:text property="author" styleId="author" maxlength="30" style="width:120px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">信息来源：</td>
        <td colspan="2" width="85%"><html-el:text property="info_source" styleId="info_source" maxlength="30" style="width:240px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">摘要：</td>
        <td colspan="2" width="85%"><html-el:textarea styleId="summary" property="summary" rows="7" style="width:597px" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>新闻内容：</td>
        <td colspan="2" width="85%"><html-el:hidden property="content" styleId="content" />
          <script id="content_ue" name="content_ue" type="text/plain" style="width:100%;height:200px;">${af.map.content}</script></td>
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
        <td width="15%" class="title_item" nowrap="nowrap">主图说明：</td>
        <td colspan="2" width="85%"><html-el:text property="image_desc" styleId="image_desc" maxlength="120" style="width:450px" styleClass="webinput" /></td>
      </tr>
      <tr id="trFile">
        <td width="15%" class="title_item" nowrap="nowrap">上传附件：</td>
        <td colspan="2" width="85%"><div id="divFileHidden" style="display: none;">
            <input name="file_hidden" type="file" id="file_hidden" style="width: 500px;" />
            <img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></div>
          <div id="divFile">
            <input name="file_show" type="file" id="file_show" style="width: 500px;" />
            <img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="imgAddTr" title="再添加一个" /><br/>
            </div>
          <c:forEach var="cur" items="${attachmentList}" varStatus="vs"> <span> <a href="${ctx}/${cur.save_path}">${cur.file_name}</a> &nbsp;
            (<a href="javascript:void(0);" id="del_${cur.id}" onclick= "deleteFile('${cur.id}','${cur.save_path}');">删</a>) <br />
            </span></c:forEach></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">发布时间：</td>
        <td  colspan="2" height="24" width="85%"><fmt:formatDate value="${af.map.pub_time}" pattern="yyyy-MM-dd" var="_pub_time" />
          <html-el:text property="pub_time" styleId="pub_time" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker();" style="cursor:pointer;" value="${_pub_time}" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">是否使用失效时间：</td>
        <td colspan="2" height="24" width="85%"><html-el:select property="is_use_invalid_date" styleId="is_use_invalid_date" >
            <html-el:option value="0">不使用</html-el:option>
            <html-el:option value="1">使用</html-el:option>
            </html-el:select></td>
      </tr>
      <tr id="tr_invalid_date" style="display: none;">
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>失效时间：</td>
        <td colspan="2" height="24" width="85%"><fmt:formatDate value="${af.map.invalid_date}" pattern="yyyy-MM-dd" var="_invalid_date" />
          <html-el:text property="invalid_date" styleId="invalid_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker();" value="${_invalid_date}" /></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">排序值：</td>
        <td colspan="2" width="85%"><html-el:text property="order_value" styleId="order_value" maxlength="4" size="4" styleClass="webinput" />
          值越大，显示越靠前，范围：0-9999</td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
    <jsp:include page="../../../public/public_color_select.jsp" flush="true"/>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>

<script type="text/javascript"><!--//<![CDATA[
var column_ids = [];  //需要的自定义字段      
//var editor = KindEditor.create("textarea[id='content']");
//var editor = KindEditor.create("textarea[name='content']",{items:KindEditor.simpleItems});
var editor = UE.getEditor('content_ue');
editor.ready(function() {editor.on('showmessage', function(type, m){if (m['content'] == '本地保存成功') {return true;}});});

$(document).ready(function(){

	$("#is_use_invalid_date").change(function(){
		var value = $(this).val();
		//alert(value);
		if("1" == value){
			$("#tr_invalid_date").show();
			$("#invalid_date").attr("dataType", "Require").attr("msg", "失效时间必须填写");
		}else{
			$("#tr_invalid_date").hide();
			$("#invalid_date").removeAttr("dataType");
		}
	});

	if($("#is_use_invalid_date").val() == "1"){
		$("#tr_invalid_date").show();
		$("#invalid_date").attr("dataType", "Require").attr("msg", "失效时间必须填写");
	}
	
	var $t = $("#title");
	<c:if test="${not empty af.map.title_color}">
		$t.css("color", '${af.map.title_color}');
	</c:if>
	<c:if test="${1 eq (af.map.title_is_strong)}">
		$t.css("font-weight", "bold");
	</c:if>
	
    $("#imgAddTr").click(function (){
		$("#divFileHidden").clone().find("#file_hidden").attr("name", "file_" + new Date().getTime()).end().appendTo($("#divFile")).show();
	    $("img[id='imgDelTr']").each(function(){
			$(this).click(function (){
				$(this).parent().remove();
			});
		});
	});

	var acceptUploadFileExts = "7z, aiff, asf, avi, bmp, csv, doc, docx, fla, flv, gif, gz, gzip, jpeg, jpg, mid, mov, mp3, mp4, mpc, mpeg, mpg, ods, odt, pdf, png, ppt,pptx, pxd, qt, ram, rar, rm, rmi, rmvb, rtf, sdc, sitd, swf, sxc, sxw, tar, tgz, tif, tiff, txt, vsd, wav, wma, wmv, xls,xlsx, xml, zip";
	$("#title"		 ).attr("dataType", "Require").attr("msg", "标题必须填写");
	//$("#info_source" ).attr("dataType", "Require").attr("msg", "信息来源必须填写");
	$("#order_value" ).attr("dataType", "Number").attr("msg", "排序值必须为正整数");
	$("#direct_uri"  ).attr("dataType", "Url").attr("msg", "直接URI格式不合法").attr("require", "false");
	//$("#invalid_date").attr("dataType", "Require").attr("msg", "失效时间必须填写").attr("require", "false");
	$("#image_path"  ).attr("dataType", "Filter" ).attr("msg", "图片的格式必须是(bmp,gif,jpeg,jpg,png)").attr("require", "false").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#file_show"   ).attr("dataType", "Filter" ).attr("msg", "您上传的文件格式不受支持。支持格式：" + acceptUploadFileExts).attr("require", "false").attr("accept", acceptUploadFileExts);
	$("#file_hidden" ).attr("dataType", "Filter" ).attr("msg", "您上传的文件格式不受支持。支持格式：" + acceptUploadFileExts).attr("require", "false").attr("accept", acceptUploadFileExts);
	$("#summary"     ).attr("datatype", "Limit"  ).attr("max", "500").attr("msg", "摘要内容必须在500个字之内");
	// $("#content"	 ).attr("dataType", "Require").attr("msg", "新闻内容必须填写");
	
	String.prototype.trim = function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	$t.blur(function() {
        $(this).val(this.value.trim());                           
    });
	
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
		if (!editor.hasContents()) {
			alert("新闻内容必须填写");
			editor.focus();
			return false;
		}

		if(Validator.Validate(this, 1)){
			$("#content").val(editor.getContent()); 
			$("#addZdy_tbody").remove();
			$("#column_ids").val(column_ids.join(","));
            $("#b_s").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	}

	$("#imgAddZdy").click(function(){
		$("#addZdy_tbody").dialog({
			width: 280,
			height: 150,
			buttons : {"关闭" : function() {$(this).dialog("close");}},
			show: 'blind',
			hide: 'blind',
			position:'middle',
			modal : false
		}).dialog("open");
		//$("#addZdy_tbody").show();
	});
});

function checkweight() {
	var c = document.getElementById('title_is_strong');
	var t = document.getElementById('title');
	if (c.checked) {
		$(t).css("font-weight", "bold");
	} else {
		$(t).css("font-weight", "normal");
	}
}

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

//]]></script>
</body>
</html>