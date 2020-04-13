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
  <html-el:form action="/admin/BaseConfig" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveToutiaoPic" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">移动端头条“蚂蚁头条”图片</th>
      </tr>
      <tr>
      <td nowrap="nowrap" class="title_item">图片：</td>
      <td colspan="2">
        <c:set var="img" value="${ctx}/styles/imagesPublic/no_image.jpg" />
        <c:if test="${not empty af.map.save_path}">
          <c:set var="img" value="${ctx}/${af.map.save_path}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.save_path, 'http://')}">
		  <c:set var="img" value="${af.map.save_path}"/>
		 </c:if>
        <img src="${img}" height="100" id="save_path_img" />
        <html-el:hidden property="save_path" styleId="save_path" />
        <div class="files-warp" id="save_path_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="save_path_file" type="file" name="save_path_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span style="color:red;">说明：图片大小不能超过2M!建议尺寸【300*300】。</span></td>
    </tr>

      <tr>
        <td colspan="3" align="center">
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
<script type="text/javascript"><!--//<![CDATA[
$(document).ready(function(){

	$("#file_desc").attr({"hex":"true"}).mColorPicker({imageFolder: '${ctx}/scripts/colorPicker/images/'});
	
	
	var btn_name = "上传图片";
	if ("" != "${af.map.save_path}") {
		btn_name = "重新上传";
	}
	upload("save_path", "image", btn_name, "${ctx}");
	
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>