<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/ImportBi" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveExcel" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="upload_image_files" styleId="upload_image_files"/>
    <table width="100%" border="0" cellpadding="1" cellspacing="1" class="backTable">
      <tr>
        <th colspan="3">导入Excel</th>
      </tr>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">开户银行：</td>
        <td colspan="2" width="85%">${adminUser.bank_name }</td>
      </tr>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">开户账号：</td>
        <td colspan="2" width="85%">${adminUser.bank_account }</td>
      </tr>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">开户名：</td>
        <td colspan="2" width="85%">${adminUser.bank_account_name }</td>
      </tr>
      
      <tr id="trFile">
        <td width="15%" class="title_item" nowrap="nowrap">上传导入文件：</td>
        <td colspan="2" width="85%"><div id="divFileHidden" style="display: none;">
            <input name="file_hidden" type="file" id="file_hidden" style="width: 500px;" />
            <img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></div>
          <div id="divFile">
            <input name="file_show" type="file" id="file_show" style="width: 500px;" />
            <a href="${ctx}/manager/customer/ImportBi/bi_moban.xls">下载模板</a><br/>
            </div>
          <c:forEach var="cur" items="${attachmentList}" varStatus="vs"> <span> <a href="${ctx}/${cur.save_path}">${cur.file_name}</a> &nbsp;
            (<a href="javascript:void(0);" id="del_${cur.id}" onclick= "deleteFile('${cur.id}','${cur.save_path}');">删</a>) <br />
            </span></c:forEach></td>
      </tr>
      <tr id="trFile">
        <td width="15%" class="title_item" nowrap="nowrap">上传支付凭证：</td>
        <td colspan="2" width="85%"><div id="divFileHidden" style="display: none;">
<!--             <input name="file_hidden" type="file" id="file_hidden" style="width: 500px;" /> -->
            <img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></div>
            
          <div id="divFile">
            <input name="file_path" type="file" id="file_path" style="width: 500px;" />
            </div>
          <c:forEach var="cur" items="${attachmentList}" varStatus="vs"> <span> <a href="${ctx}/${cur.save_path}">${cur.file_name}</a> &nbsp;
            (<a href="javascript:void(0);" id="del_${cur.id}" onclick= "deleteFile('${cur.id}','${cur.save_path}');">删</a>) <br />
            </span></c:forEach></td>
      </tr>
      
      
      <tr>
      	<td width="12%" nowrap="nowrap" class="title_item">备注：</td>
      	<td colspan="2">
      		<html-el:textarea property="remark" styleClass="webinput" styleId="remark"  style="width:500px; height:80px;" ></html-el:textarea>
      	</td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      <tr>
      	<td width="12%" nowrap="nowrap" class="title_item">提示：</td>
      	<td colspan="2">
      		1、请提前获取到员工的手机号码，并且确认手机号码在系统中进行了注册，系统是按照手机号码确认为以用户的；
      		</br>
      		2、请上传打款的凭证，以免申请被打回；
      	</td>
      </tr>
    </table>
    
    <c:if test="${not empty win_count}">
   <table align="center" width="40%" border="0" cellpadding="1" cellspacing="1" class="tableClass">
   	<tr><td><span style="color:red;">成功导入${win_count}条数据</span></td></tr>
   </table>
    </c:if>
    <c:if test="${not empty errPoorInfo}">
    <table align="center" width="40%" border="0" cellpadding="1" cellspacing="1" class="tableClass">
      <tr>
      	<th colspan="3">导入失败的记录</th>
      </tr>
      <tr>
      	<td width="15%" style="text-align:center;" class="title_item" nowrap="nowrap">Execl序号</td>
      	<td width="15%" style="text-align:center;" class="title_item" nowrap="nowrap">姓名</td>
      	<td width="15%" style="text-align:center;" class="title_item" nowrap="nowrap">电话</td>
      	<td width="15%" style="text-align:center;" class="title_item" nowrap="nowrap">错误原因</td>
      </tr>
      <c:forEach var="cur" items="${errPoorInfo}">
      <tr>
      	<td>${cur.id}</td>
      	<td>${cur.user_name}</td>
      	<td>${cur.mobile}</td>
      	<td><span style="color:red;">${cur.autograph}</td>
      </tr>
      </c:forEach>
    </table>
    </c:if>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 
<script type="text/javascript"><!--//<![CDATA[
//var editor = KindEditor.create("textarea[name='content']",{items:KindEditor.simpleItems});

$(document).ready(function(){

	var acceptUploadFileExts = "7z, aiff, asf, avi, bmp, csv, doc, docx, fla, flv, gif, gz, gzip, jpeg, jpg, mid, mov, mp3, mp4, mpc, mpeg, mpg, ods, odt, pdf, png, ppt,pptx, pxd, qt, ram, rar, rm, rmi, rmvb, rtf, sdc, sitd, swf, sxc, sxw, tar, tgz, tif, tiff, txt, vsd, wav, wma, wmv, xls,xlsx, xml, zip";
	$("#file_show"   ).attr("dataType", "Filter" ).attr("msg", "您上传的文件格式不受支持。支持格式：" + acceptUploadFileExts).attr("require", "false").attr("accept", acceptUploadFileExts);
	$("#file_hidden" ).attr("dataType", "Filter" ).attr("msg", "您上传的文件格式不受支持。支持格式：" + acceptUploadFileExts).attr("require", "false").attr("accept", acceptUploadFileExts);
	// $("#content"	 ).attr("dataType", "Require").attr("msg", "新闻内容必须填写");
	
	
	var filePathExts = "jpeg, jpg,png";
	$("#file_path").attr("dataType", "Filter" ).attr("msg", "您上传的文件格式不受支持。支持格式：" + filePathExts).attr("require", "false").attr("accept", acceptUploadFileExts);
	
	String.prototype.trim = function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }

	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
            $("#btn_submit").attr("value", "正在导入..").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
		return false;
	});
});
//]]></script>
</body>
</html>