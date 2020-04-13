<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../../customer/_nav.jsp" flush="true"/> 
  <html-el:form action="/customer/PoorManager.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <html-el:hidden property="p_index_pro" styleId="p_index_pro" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>申请区域：</td>
            <td colspan="3" id="city_div">
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
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="town" styleId="town" styleClass="pi_dist" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
	           &nbsp;
	          <html-el:select property="village" styleId="village" styleClass="pi_dist" style="width:120px;">
	            <html-el:option value="">请选择...</html-el:option>
	          </html-el:select>
            </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>昵称：</td>
         <td><html-el:text property="pet_name" maxlength="50" styleId="pet_name" /></td>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>民族：</td>
         <td><html-el:text property="nation" maxlength="50" styleId="nation" style="width:100px"/>&nbsp;
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">入乡时间：</td>
         <td>
          <fmt:formatDate value="${af.map.residence_start_time}" pattern="yyyy-MM-dd" var="service_online_date" />
          <html-el:text  property="residence_start_time" styleId="residence_start_time" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_servicecenter_build_date}" />
         </td>
         <td width="14%" nowrap="nowrap" class="title_item">离乡时间：</td>
         <td>
          <fmt:formatDate value="${af.map.residence_end_time}" pattern="yyyy-MM-dd" var="service_operation_date" />
          <html-el:text property="residence_end_time" styleId="residence_end_time" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_servicecenter_build_date}" />
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>学历：</td>
         <td><html-el:text property="education" maxlength="120" styleId="education"/></td>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>毕业学校：</td>
         <td><html-el:text property="graduate_school" maxlength="50" styleId="graduate_school" />&nbsp;
         </td>
       </tr>
        <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>简介：</td>
         <td colspan="3"><html-el:textarea styleId="person_introduce" property="person_introduce" rows="7" style="width:597px" /></td>
         </td>
       </tr>
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
       <tr>
       	<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
         <td><html-el:text property="real_name" maxlength="120" styleId="real_name"/></td>
         <td width="14%" nowrap="nowrap" class="title_item">头像：</td>
       	 <td colspan="3">
       	 <div style="float:left;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.head_logo}">
                <c:set var="img" value="${ctx}/${af.map.head_logo}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.head_logo}" />
              </c:if>
              <a href="${img_max}" id="head_logo_a" target="_blank"><img src="${img}" height="50"  id="head_logo_img" /></a>
              <html-el:hidden property="head_logo" styleId="head_logo" value="${af.map.head_logo}"/>
              <div class="files-warp" id="head_logo_warp"> <span>头像:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="head_logo_file" type="file" name="head_logo_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
          </div>
          </td>
       </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>电话：</td>
        <td><html-el:text property="mobile" maxlength="80" styleClass="webinput" styleId="mobile" style="width:200px;" /></td>
         <td class="title_item">性别：</td>
        <td><html-el:select property="sex" styleId="sex">
            <html-el:option value="0">男</html-el:option>
            <html-el:option value="1">女</html-el:option>
          </html-el:select></td>
      </tr>
       <tr>
       <td class="title_item"><span style="color: #F00;">*</span>出生日期：</td>
        <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd" var="_add_birthday" />
          <html-el:text property="birthday" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${_add_birthday}" style="cursor:pointer;text-align:center;" title="点击选择日期" /></td>
      	<td class="title_item">电子邮箱：</td>
        <td><html-el:text property="email" styleClass="webinput" styleId="email" maxlength="50" />
          &nbsp;</td>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>身份证号码：</td>
         <td colspan="3"><html-el:text property="id_card" maxlength="120" styleId="id_card" style="width:200px"/></td>
         </td>
       </tr>
       <tr>
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证正面/反面：</td>
          <td colspan="3">
             <div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.img_id_card_zm}">
                <c:set var="img" value="${ctx}/${af.map.img_id_card_zm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.img_id_card_zm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_zm_a" target="_blank"><img src="${img}" height="50"  id="img_id_card_zm_img" /></a>
              <html-el:hidden property="img_id_card_zm" styleId="img_id_card_zm" value="${af.map.img_id_card_zm}"/>
              <div class="files-warp" id="img_id_card_zm_warp"> <span>身份证正面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_zm_file" type="file" name="img_id_card_zm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.img_id_card_fm}">
                <c:set var="img" value="${ctx}/${af.map.img_id_card_fm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.img_id_card_fm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_fm_a" target="_blank"><img src="${img}" height="50" id="img_id_card_fm_img" /></a>
              <html-el:hidden property="img_id_card_fm" styleId="img_id_card_fm" value="${af.map.img_id_card_fm}"/>
              <div class="files-warp" id="img_id_card_fm_warp"> <span>身份证反面扫描件:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="img_id_card_fm_file" type="file" name="img_id_card_fm_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            </div>
            <span style="color: #F00;float:left">&nbsp;用户身份证正面图片，大小500K以内，文字须清晰可见。</span></td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="保存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      <tr>
	  	<td colspan="4" style="text-align:center">
	  	<a class="f1" href="javascript:void(0);" onclick="getServiceApplyInfo();">《合伙人电子协议》</a> 
	  	</td>
	  </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	$("#province").attr({"subElement": "city", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.province}","datatype": "Require", "msg": "请选择省份"});
	$("#city").attr({"subElement": "country", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.city}","datatype": "Require", "msg": "请选择市"});
	$("#country").attr({"subElement": "town", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.country}","datatype": "Require", "msg": "请选择县"});
	$("#town").attr({"subElement": "village", "defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.town}","datatype": "Require", "msg": "请选择乡/镇"});
	$("#village" ).attr({"defaultText": "请选择...", "defaultValue": "", "selectedValue": "${af.map.village}","datatype": "Require", "msg": "请选择村"});
	$("#province").cs("${ctx}/BaseCsAjax.do?method=getBaseProvinceList", "p_index", "0", false);

	$("#pet_name").attr("datatype","Require").attr("msg","昵称必须填写");
	$("#nation").attr("datatype","Require").attr("msg","民族必须填写");
	$("#education").attr("datatype","Require").attr("msg","学历必须填写");
	$("#graduate_school").attr("datatype","Require").attr("msg","毕业学校必须填写");
	$("#mobile").attr("dataType","Mobile").attr("msg", "联络手机为空或格式不正确");
	$("#person_introduce").attr("dataType","Require").attr("msg", "个人简介必须填写");
	$("#real_name").attr("datatype","Require").attr("msg","真实姓名必须填写");
	$("#birthday").attr("datatype","Require").attr("msg","出生日期必须填写");
	
	$("#id_card").attr("datatype","IdCard").attr("msg","请填写正确格式的身份证号码！");
	$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	$("#head_logo").attr("dataType", "Filter" ).attr("msg", "头像必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	var btn_name = "上传身份证正面";
	if ("" != "${img_id_card_zm}") {
		btn_name = "重新上传身份证正面";
	}
	upload("img_id_card_zm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传身份证背面";
	if ("" != "${img_id_card_fm}") {
		btn_name = "重新上传身份证背面";
	}
	upload("img_id_card_fm", "image", btn_name, "${ctx}");
	
	var btn_name = "上传头像";
	if ("" != "${af.map.head_logo}") {
		btn_name = "重新上传头像";
	}
	upload("head_logo", "image", btn_name, "${ctx}");
});     

// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
});

//]]></script>
</body>
</html>
