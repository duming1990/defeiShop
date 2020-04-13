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
  <html-el:form action="/admin/BigPinShow" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="link_id" styleId="link_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">大屏显示图片</th>
      </tr>
       <tr>
        <td class="title_item"><span style="color: #F00;">*</span>所属区域：</td>
        <td id="city_div">
        <html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select></td>
      </tr>
       <tr>
       <td class="title_item"><span style="color: #F00;">*</span>大屏显示标题： </td>
        <td><html-el:text property="file_name" styleId="file_name" styleClass="webinput" maxlength="20" style="width:200px;"/></td>
      </tr>
      <tr>
      <td nowrap="nowrap" class="title_item" width="10%"><span style="color: #F00;">*</span>大屏显示图片：</td>
      <td colspan="2">
		<c:if test="${not empty af.map.save_path}">
         	<c:set var="img" value="${ctx}/${af.map.save_path}" />
       	</c:if>
       	<img src="${img}" height="100" id="save_path_img" />
        <html-el:hidden property="save_path" styleId="save_path" />
        <div class="files-warp" id="save_path_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="save_path_file" type="file" name="save_path_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span style="color:red;">说明：图片大小不能超过2M!建议尺寸【826*940】。</span>
        </td>
      </tr>
      <tr>
        <td colspan="3" align="center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript"><!--//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];
	
	$("#city_div").citySelect({
	    data:getAreaDic(),
	    province:"${af.map.province}",
	    city:"${af.map.city}",
	    country:"${af.map.country}",
	    province_required:true,
	    city_required:true,
	    country_required:true,
	    callback:function(selectValue,selectText){
	    	if(null != selectValue && "" != selectValue){
	    		var p_indexs = selectValue.split(",");
	    		if(null != p_indexs && p_indexs.length > 0){
	    			$("#link_id").val(p_indexs[p_indexs.length - 1]);
	    		}
	    	}
	    }
	});
	
	var btn_name = "上传相片";
	if ("" != "${af.map.save_path}") {
		btn_name = "重新上传";
	}
	upload("save_path", "image", btn_name, "${ctx}");
	
	$("#save_path").attr("dataType", "Filter" ).attr("msg", "请上传格式为（bmp, gif, jpeg, jpg, png）的大屏显示图片地址！").attr("require", "true").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#file_name").attr("datatype", "Require").attr("msg", "请填写大屏显示标题");
	// 提交
	$("#btn_submit").click(function() {
	 if (Validator.Validate(f, 1)) {
		 $("#btn_submit").attr("value","正在提交...").attr("disabled","true");
		 $("#btn_back").attr("disabled","true");
		 f.submit();
	 }
	});
});

//]]></script>
</body>
</html>