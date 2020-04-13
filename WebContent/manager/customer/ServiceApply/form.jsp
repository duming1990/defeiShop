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
  <jsp:include page="../_nav.jsp" flush="true"/> 
  <html-el:form action="/customer/ServiceApply.do" enctype="multipart/form-data">
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
            </td>
       </tr>
       <tr>
<!--          <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>合伙人名称：</td> -->
<%--          <td><html-el:text property="servicecenter_name" maxlength="50" styleId="servicecenter_name" style="width:90%"/></td> --%>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>注册地址：</td>
         <td colspan="3"><html-el:text property="servicecenter_addr" maxlength="50" styleId="servicecenter_addr" style="width:90%"/></td>
       </tr>
       <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>地理位置：</td>
        <td colspan="3"><html-el:text property="position_latlng" readonly="true" maxlength="128" style="width:250px;" styleId="position_latlng" />
          &nbsp;
          <input type="button" value="维护坐标" onclick="getLatlng('position_latlng')" class="bgButton" /></td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">公司/商户法人：</td>
         <td><html-el:text property="servicecenter_corporation" maxlength="50" styleId="servicecenter_corporation" style="width:90%"/></td>
         <td width="14%" nowrap="nowrap" class="title_item">公司性质：</td>
         <td>
         <html-el:select property="servicecenter_type" styleId="servicecenter_type">
          <html-el:option value="">请选择...</html-el:option>
            <c:forEach var="cur" items="${baseData3100List}" varStatus="vs">
            <html-el:option value="${cur.id}">${cur.type_name}</html-el:option>
            </c:forEach>
         </html-el:select>
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">注册时间：</td>
         <td>
          <fmt:formatDate value="${af.map.servicecenter_build_date}" pattern="yyyy-MM-dd" var="_servicecenter_build_date" />
          <html-el:text property="servicecenter_build_date" styleId="servicecenter_build_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_servicecenter_build_date}" />
         </td>
         <td width="14%" nowrap="nowrap" class="title_item">注册资金：</td>
         <td><html-el:text property="servicecenter_reg_money" maxlength="10" styleId="servicecenter_reg_money" style="width:100px"/>&nbsp;万元
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">员工人数：</td>
         <td><html-el:text property="servicecenter_persons" maxlength="10" styleId="servicecenter_persons" style="width:100px"/></td>
         <td width="14%" nowrap="nowrap" class="title_item">经营面积：</td>
         <td><html-el:text property="servicecenter_area" maxlength="50" styleId="servicecenter_area" style="width:100px"/>&nbsp;平方米
         </td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item">经营状况：</td>
         <td colspan="3"><html-el:text property="servicecenter_jy_desc" maxlength="120" styleId="servicecenter_jy_desc" style="width:500px"/></td>
       </tr>
      <tr>
        <th colspan="4">个人信息</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
        <td><html-el:text property="servicecenter_linkman" maxlength="10" styleId="servicecenter_linkman" style="width:200px" /></td>
        <td class="title_item"><span style="color: #F00;">*</span>籍贯：</td>
        <td><html-el:text property="servicecenter_linkman_jg" maxlength="10" styleId="servicecenter_linkman_jg" style="width:200px" /></td>
      </tr>
      
      
       <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>身份证号码：</td>
        <td colspan="3"><html-el:text property="id_card" maxlength="50" styleClass="webinput" styleId="id_card" style="width:200px" /></td>
      </tr>
        <tr>
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证正面/反面：</td>
          <td colspan="3"><div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty img_id_card_zm}">
                <c:set var="img" value="${ctx}/${img_id_card_zm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${img_id_card_zm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_zm_a" target="_blank"><img src="${img}" height="50"  id="img_id_card_zm_img" /></a>
              <html-el:hidden property="img_id_card_zm" styleId="img_id_card_zm" value="${img_id_card_zm}"/>
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
              <c:if test="${not empty img_id_card_fm}">
                <c:set var="img" value="${ctx}/${img_id_card_fm}@s400x400" />
                <c:set var="img_max" value="${ctx}/${img_id_card_fm}" />
              </c:if>
              <a href="${img_max}" id="img_id_card_fm_a" target="_blank"><img src="${img}" height="50" id="img_id_card_fm_img" /></a>
              <html-el:hidden property="img_id_card_fm" styleId="img_id_card_fm" value="${img_id_card_fm}"/>
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
        <td class="title_item"><span style="color: #F00;">*</span>联络地址：</td>
        <td><html-el:text property="servicecenter_linkman_addr" maxlength="30" styleId="servicecenter_linkman_addr" style="width:200px" /></td>
        <td class="title_item"><span style="color: #F00;">*</span>联络手机：</td>
        <td><html-el:text property="servicecenter_linkman_tel" maxlength="20" styleId="servicecenter_linkman_tel" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>微信号：</td>
        <td><html-el:text property="servicecenter_linkman_wixin_nu" maxlength="50" styleId="servicecenter_linkman_wixin_nu" style="width:200px" /></td>
        <td class="title_item">联系QQ：</td>
        <td><html-el:text property="servicecenter_linkman_qq" maxlength="15" styleId="servicecenter_linkman_qq" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item">紧急联系人：</td>
        <td><html-el:text property="servicecenter_help_linkman" maxlength="10" styleId="servicecenter_help_linkman" style="width:200px" /></td>
        <td class="title_item">紧急联系电话：</td>
        <td><html-el:text property="servicecenter_help_linkman_tel" maxlength="20" styleId="servicecenter_help_linkman_tel" style="width:200px" /></td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="同意以下协议并申请" styleClass="bgButton" styleId="btn_submit" />
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
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required:true,
        city_required:true,
        country_required:false,
        callback:function(selectValue,selectText){
        	if(null != selectValue && "" != selectValue){
        		var p_indexs = selectValue.split(",");
        		if(null != p_indexs && p_indexs.length > 0){
        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
        		}
        		var thisVal = $("#servicecenter_level").val();
        		if(thisVal){
        			if(thisVal == 1){
        				$("#city").attr("disabled",true).removeAttr("datatype");
        				$("#country").attr("disabled",true).removeAttr("datatype");
        			}else if(thisVal == 2){
        				$("#city").removeAttr("disabled").attr("datatype","Require");
        				$("#country").attr("disabled",true).removeAttr("datatype");
        			}else{
        				$("#city").removeAttr("disabled").attr("datatype","Require");
        				$("#country").removeAttr("disabled").attr("datatype","Require");
        			}
        		}
        		
        	}
        }
    });
		

	$("#servicecenter_name").attr("datatype","Require").attr("msg","合伙人名称必须填写");
	$("#servicecenter_level").attr("datatype","Require").attr("msg","请选择合伙人名称");
	
	$("#servicecenter_addr").attr("datatype","Require").attr("msg","注册地址必须填写");
	$("#servicecenter_linkman").attr("datatype","Require").attr("msg","真实姓名必须填写");
	$("#servicecenter_linkman_jg").attr("datatype","Require").attr("msg","籍贯必须填写");
	$("#servicecenter_linkman_addr").attr("datatype","Require").attr("msg","联络地址必须填写");
	$("#servicecenter_linkman_tel").attr("dataType","Mobile").attr("msg", "手机号码为空或格式不正确");
	$("#servicecenter_linkman_wixin_nu").attr("dataType","Require").attr("msg", "微信号必须填写");
	
	
	$("#kfsc_content").attr("datatype","Limit").attr("min","1").attr("max","200").attr("msg","如何开发市场在200个汉字之内");
	$("#jbys_content").attr("datatype","Limit").attr("min","1").attr("max","200").attr("msg","具备的优势在200个汉字之内");
	$("#trzy_content").attr("datatype","Limit").attr("min","1").attr("max","200").attr("msg","投入资源情况在200个汉字之内");
	$("#qdjy_content").attr("datatype","Limit").attr("min","0").attr("max","200").attr("msg","期待和建议在200个汉字之内");

	
	
	$("#id_card").attr("datatype","IdCard").attr("msg","请填写正确格式的身份证号码！");
	
	$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	
	
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
	
	
	$("#servicecenter_level").change(function(){
		$("#province").val("");
		$("#city").val("");
		$("#country").val("");
		var thisVal = $(this).val();
		if(thisVal != null && '' != thisVal){
			if(thisVal == 1){
				$("#city").attr("disabled",true).removeAttr("datatype");
				$("#country").attr("disabled",true).removeAttr("datatype");
			}else if(thisVal == 2){
				$("#city").removeAttr("disabled").attr("datatype","Require");
				$("#country").attr("disabled",true).removeAttr("datatype");
			}else{
				$("#city").removeAttr("disabled").attr("datatype","Require");
				$("#country").removeAttr("disabled").attr("datatype","Require");
			}
		}
	});
	
	<c:if test="${af.map.servicecenter_level eq 1}">
		$("#city").attr("disabled",true).removeAttr("datatype");
		$("#country").attr("disabled",true).removeAttr("datatype");
	</c:if>
	<c:if test="${af.map.servicecenter_level eq 2}">
		$("#country").attr("disabled",true).removeAttr("datatype");
	</c:if>
	<c:if test="${af.map.servicecenter_level eq 3}">
		$("#city").removeAttr("disabled").attr("datatype","Require");
		$("#country").removeAttr("disabled").attr("datatype","Require");
	</c:if>
	
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

function getServiceApplyInfo(){
	var url = "${ctx}/manager/customer/ServiceApply.do?method=getServiceApplyInfo";
	$.dialog({
		title:  "合伙人电子协议",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}  

function getLatlng(obj){
	var url = "${ctx}/CsAjax.do?method=getBMap&result_id=" + obj;
	$.dialog({
		title:  "选择坐标",
		width:  900,
		height: 520,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

//]]></script>
</body>
</html>
