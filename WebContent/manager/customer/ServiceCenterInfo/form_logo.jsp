<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/admin/css/admin.css"  />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/ServiceCenterInfo" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveLogo" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="6">合伙人装修</th>
      </tr>
       <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>合伙人名称：</td>
         <td colspan="5"><html-el:text property="servicecenter_name" maxlength="50" style="width:400px;" styleId="servicecenter_name" /></td>
       </tr>
      <tr>
      <td nowrap="nowrap" class="title_item">合伙人商标：</td>
      <td colspan="2">
        <c:set var="img" value="${ctx}/styles/imagesPublic/no_image.jpg" />
        <c:if test="${not empty af.map.logo}">
          <c:set var="img" value="${ctx}/${af.map.logo}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.logo, 'http://')}">
		  <c:set var="img" value="${af.map.logo}"/>
		 </c:if>
        <img src="${img}" height="100" id="logo_img" />
        <html-el:hidden property="logo" styleId="logo" />
        <div class="files-warp" id="logo_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="logo_file" type="file" name="logo_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span style="color:red;">说明：图片大小不能超过2M!建议尺寸【1:1】。</span></td>
        
        <td nowrap="nowrap" class="title_item">背景图：</td>
      <td colspan="2">
        <c:set var="img" value="${ctx}/styles/imagesPublic/no_image.jpg" />
        <c:if test="${not empty af.map.banner}">
          <c:set var="img" value="${ctx}/${af.map.banner}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.banner, 'http://')}">
		  <c:set var="img" value="${af.map.banner}"/>
		 </c:if>
        <img src="${img}" height="100" id="banner_img" />
        <html-el:hidden property="banner" styleId="banner" />
        <div class="files-warp" id="banner_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="banner_file" type="file" name="banner_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span style="color:red;">说明：图片大小不能超过2M!建议尺寸【414*207】。</span></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">二维码：</td>
      <td colspan="6">
        <c:set var="img" value="${ctx}/styles/imagesPublic/no_image.jpg" />
        <c:if test="${not empty af.map.qrcode}">
          <c:set var="img" value="${ctx}/${af.map.qrcode}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.qrcode, 'http://')}">
		  <c:set var="img" value="${af.map.qrcode}"/>
		 </c:if>
        <img src="${img}" height="100" id="qrcode_img" />
        <html-el:hidden property="qrcode" styleId="qrcode" />
        <div class="files-warp" id="qrcode_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="qrcode_file" type="file" name="qrcode_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span style="color:red;">说明：图片大小不能超过2M!建议尺寸【1:1】。</span></td>
    </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">简介：</td>
        <td colspan="5" width="85%"><html-el:hidden property="condition_info" styleId="condition_info" />
          <script id="content_ue" name="content_ue" type="text/plain" style="width:100%;height:200px;">${af.map.condition_info}</script></td>
      </tr>
      <tr>
        <td colspan="6" align="center">
          <html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
     <jsp:include page="../NewsInfo/public_color_select.jsp" flush="true"/>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorPicker/mColorPicker.min.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript"><!--//<![CDATA[
var editor = UE.getEditor('content_ue');
editor.ready(function() {editor.on('showmessage', function(type, m){if (m['condition_info'] == '本地保存成功') {return true;}});});

$(document).ready(function(){

	$("#file_desc").attr({"hex":"true"}).mColorPicker({imageFolder: '${ctx}/scripts/colorPicker/images/'});
	
	
	var btn_name = "上传商标";
	if ("" != "${af.map.logo}") {
		btn_name = "重新上传";
	}
	upload("logo", "image", btn_name, "${ctx}");
	
	var btn_name = "上传背景图";
	if ("" != "${af.map.logo}") {
		btn_name = "重新上传";
	}
	upload("banner", "image", btn_name, "${ctx}");
	
	var btn_name = "上传二维码";
	if ("" != "${af.map.qrcode}") {
		btn_name = "重新上传";
	}
	upload("qrcode", "image", btn_name, "${ctx}");
	
	var f = document.forms[0];
	f.onsubmit = function () {
	$("#condition_info").val(editor.getContent()); 
    $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
    $("#btn_back").attr("disabled", "true");
	f.submit();
	return false;
	}
});
//]]></script>
</body>
</html>