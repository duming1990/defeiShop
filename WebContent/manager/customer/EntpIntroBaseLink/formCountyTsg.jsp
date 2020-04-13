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
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/EntpIntroBaseLink" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveTsg" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="link_id" styleId="link_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <html-el:hidden property="main_type" styleId="main_type" />
    <html-el:hidden property="link_type" styleId="link_type" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">图片维护</th>
      </tr>
      <tr>
      <td nowrap="nowrap" class="title_item"> <span style="color: #F00;">*</span>图片：</td>
      <td colspan="2">
        <c:set var="img" value="${ctx}/styles/imagesPublic/no_image.jpg" />
        <c:if test="${not empty af.map.image_path}">
          <c:set var="img" value="${ctx}/${af.map.image_path}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.image_path, 'http://')}">
		  <c:set var="img" value="${af.map.image_path}"/>
		 </c:if>
        <img src="${img}" height="100" id="image_path_img" />
        <html-el:hidden property="image_path" styleId="image_path" />
        <div class="files-warp" id="image_path_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="image_path_file" type="file" name="image_path_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span style="color:red;">说明：图片大小不能超过2M!建议尺寸宽度：1000px，高度按实际需求定义</span></td>
    </tr>
      <tr>
        <td colspan="3" align="center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript"><!--//<![CDATA[
$(document).ready(function(){	
	var f = document.forms[0];
	$("#image_path").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的图片！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
	var btn_name = "上传图片";
	if ("" != "${af.map.image_path}") {
		btn_name = "重新上传";
	}
	upload("image_path", "image", btn_name, "${ctx}");
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			f.submit();
		}
	})
});
//]]></script>
</body>
</html>