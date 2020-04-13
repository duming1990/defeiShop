<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<html-el:form action="/customer/WelfareCard.do" enctype="multipart/form-data">
     <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="msgBatchActive" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">额度：</td>
         <td>${af.map.card_amount}元</td>
       </tr>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item">可发放数量：</td>
	      <td>${count} 张</td>
	   </tr>
	   <tr>
	      <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>发放数量：</td>
	      <td><html-el:text property="send_num" styleId="send_num" maxlength="11" style="width:100px" styleClass="webinput" /></td>
	   </tr>
	   
	   <tr id="trFile">
        <td width="15%" class="title_item" nowrap="nowrap">上传导入文件：</td>
        <td colspan="2" width="85%"><div id="divFileHidden" style="display: none;">
            <input name="file_hidden" type="file" id="file_hidden" style="width: 500px;" />
            <img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></div>
          <div id="divFile">
            <input name="file_show" type="file" id="file_show" style="width: 500px;" />
            <a href="${ctx}/manager/customer/WelfareCard/card_moban.xls">下载模板</a><br/>
            </div>
          <c:forEach var="cur" items="${attachmentList}" varStatus="vs"> <span> <a href="${ctx}/${cur.save_path}">${cur.file_name}</a> &nbsp;
            (<a href="javascript:void(0);" id="del_${cur.id}" onclick= "deleteFile('${cur.id}','${cur.save_path}');">删</a>) <br />
            </span></c:forEach></td>
      </tr>
       <tr>
	      <td width="14%" nowrap="nowrap" class="title_item">备注：</td>
	      <td><html-el:text property="remark" styleId="remark" maxlength="60" style="width:400px" styleClass="webinput" /></td>
	   </tr>
	   
       <tr>
        <td style="text-align:center" colspan="2">
         <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#send_num").attr("datatype", "Number").attr("msg", "请填写发卡数量！");
	var acceptUploadFileExts = "7z, aiff, asf, avi, bmp, csv, doc, docx, fla, flv, gif, gz, gzip, jpeg, jpg, mid, mov, mp3, mp4, mpc, mpeg, mpg, ods, odt, pdf, png, ppt,pptx, pxd, qt, ram, rar, rm, rmi, rmvb, rtf, sdc, sitd, swf, sxc, sxw, tar, tgz, tif, tiff, txt, vsd, wav, wma, wmv, xls,xlsx, xml, zip";
	$("#file_show"   ).attr("dataType", "Filter" ).attr("msg", "您上传的文件格式不受支持。支持格式：" + acceptUploadFileExts).attr("require", "false").attr("accept", acceptUploadFileExts);
	$("#file_hidden" ).attr("dataType", "Filter" ).attr("msg", "您上传的文件格式不受支持。支持格式：" + acceptUploadFileExts).attr("require", "false").attr("accept", acceptUploadFileExts);
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交..").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	});
	
	
	$("#send_num").change(function (){
		var count="${count}";
		if($(this).val()-count>0){
			$("#send_num").val("");
			$("#send_num").focus();
			alert("该批次福利卡数量不足，请重新填写！");
		}
	})
});
function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}
//]]></script>
</body>
</html>
