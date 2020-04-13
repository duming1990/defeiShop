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
  <html-el:form action="/customer/VillageTour" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="6">村装修</th>
      </tr>
       <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>标题：</td>
         <td colspan="3"><html-el:text property="tour_name" maxlength="50" style="width:400px;" styleId="tour_name" /></td>
       </tr>
       <tr>
        <td width="14%" nowrap="nowrap" class="title_item">副标题：</td>
         <td colspan="3"><html-el:text property="tour_sub_name" maxlength="50" style="width:400px;" styleId="tour_sub_name" /></td>
       </tr>
        <tr>
        <td width="14%" nowrap="nowrap" class="title_item">交通贴士：</td>
         <td colspan="3"><html-el:text property="tour_traffic" maxlength="50" style="width:400px;" styleId="tour_traffic" /></td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>所属区域：</td>
            <td colspan="3" id="city_div">
               <html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;" disabled="disabled">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	          &nbsp;
	          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="town" styleId="town" styleClass="pi_town" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="village" styleId="village" styleClass="pi_village" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
            </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动开始时间：</td>
         <td>
          <fmt:formatDate value="${af.map.tour_start_date}" pattern="yyyy-MM-dd" var="tour_start_date" />
          <html-el:text  property="tour_start_date" styleId="tour_start_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${tour_start_date}" />
         </td>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动结束时间：</td>
         <td>
          <fmt:formatDate value="${af.map.tour_end_date}" pattern="yyyy-MM-dd" var="tour_end_date" />
          <html-el:text property="tour_end_date" styleId="tour_end_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${tour_end_date}" />
         </td>
       </tr>
        <tr>
      <td nowrap="nowrap" class="title_item">主图：</td>
      <td colspan="3">
        <c:set var="img" value="${ctx}/styles/imagesPublic/no_image.jpg" />
        <c:if test="${not empty af.map.tour_logo}">
          <c:set var="img" value="${ctx}/${af.map.tour_logo}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.tour_logo, 'http://')}">
		  <c:set var="img" value="${af.map.tour_logo}"/>
		 </c:if>
        <img src="${img}" height="100" id="tour_logo_img" />
        <html-el:hidden property="tour_logo" styleId="tour_logo" />
        <div class="files-warp" id="tour_logo_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="tour_logo_file" type="file" name="tour_logo_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span style="color:red;">说明：图片大小不能超过2M!建议尺寸【1:1】。</span></td>
    </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">乡村简介：</td>
        <td colspan="3" width="85%"><html-el:hidden property="tour_content" styleId="tour_content" />
          <script id="content_ue" name="content_ue" type="text/plain" style="width:100%;height:200px;">${af.map.tour_content}</script></td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="保存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
     <jsp:include page="../NewsInfo/public_color_select.jsp" flush="true"/>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorPicker/mColorPicker.min.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript"><!--//<![CDATA[
var editor = UE.getEditor('content_ue');
editor.ready(function() {editor.on('showmessage', function(type, m){if (m['tour_content'] == '本地保存成功') {return true;}});});
var f = document.forms[0];
$(document).ready(function(){
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);
	
	$("#province").change(function(){
		$('#city').val("");
		$('#country').val("");
		$('#town').val("");
		$('#village').val("");
	});
	$("#city").change(function(){
		$('#country').val("");
		$('#town').val("");
		$('#village').val("");
	});
	$("#country").change(function(){
		$('#town').val("");
		$('#village').val("");
	});
	$("#town").change(function(){
		$('#village').val("");
	});
	
	$("#province").click(function(){
        alert("只可选择该县的地区，不能更改省市县！");
        return false;
    }); 
	$("#city").click(function(){
        alert("只可选择该县的地区，不能更改省市县！");
        return false;
    });   
	$("#country").click(function(){
        alert("只可选择该县的地区，不能更改省市县！");
        return false;
    });   
	
	var btn_name = "上传主图";
	if ("" != "${af.map.tour_logo}") {
		btn_name = "重新上传";
	}
	upload("tour_logo", "image", btn_name, "${ctx}");
	
	$("#tour_name").attr("datatype", "Require").attr("msg", "请填写标题");
	$("#tour_start_date").attr("dataType","Require").attr("msg", "请选择活动开始时间");
	$("#tour_en_date").attr("datatype","Require").attr("msg","请选择活动结束时间");
	$("#tour_logo").attr("dataType", "Filter" ).attr("msg", "主图必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	// 提交
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
			 if(null != $("#tour_start_date").val() && null != $("#tour_en_date").val()){
				if($("#tour_start_date").val()>=$("#tour_en_date").val()){
					alert("村站活动结束时间必须大于开始时间");
					return false;
				}
			 }
			 $("#tour_content").val(editor.getContent()); 
			 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			 $("#btn_reset").attr("disabled", "true");
			 $("#btn_back").attr("disabled", "true");
			 f.submit();
		}
	});
});
//]]></script>
</body>
</html>