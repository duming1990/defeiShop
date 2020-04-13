<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的账号 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
<jsp:include page="../_nav.jsp" flush="true"/>
<%@ include file="/commons/pages/messages.jsp" %>
<html-el:form action="/customer/MyAccount" enctype="multipart/form-data" method="post" styleClass="ajaxForm0">
  <html-el:hidden property="id" styleId="id" />
  <html-el:hidden property="method" value="save" />
  <html-el:hidden property="mod_id" styleId="mod_id" />
  <html-el:hidden property="queryString" styleId="queryString" />
  <html-el:hidden property="p_index" styleId="p_index" />
  <input type="hidden" name="is_secret" id="is_secret" value="${af.map.is_secret}" />
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" id="form0">
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">登录名：</td>
      <td><c:out value="${af.map.user_name}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>真实姓名：</td>
      <td><html-el:text property="real_name" maxlength="20" styleClass="webinput" styleId="real_name" style="width:200px" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">头像：</td>
      <td colspan="2"><c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
        <c:if test="${not empty af.map.user_logo}">
          <c:set var="img" value="${ctx}/${af.map.user_logo}@s400x400" />
        </c:if>
        <c:if test="${fn:contains(af.map.user_logo, 'http://')}">
		  <c:set var="img" value="${af.map.user_logo}"/>
		 </c:if>
        <img src="${img}" height="100" id="user_logo_img" />
        <html-el:hidden property="user_logo" styleId="user_logo" />
        <div class="files-warp" id="user_logo_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="user_logo_file" type="file" name="user_logo_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
        <span>说明：头像大小不能超过2M!图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span></td>
    </tr>
    <tr id="address">
      <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>所属区域：</td>
      <td id="city_div"><html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;" disabled="disabled">
          <html-el:option value="">请选择省...</html-el:option>
        </html-el:select>
        &nbsp;
        <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
          <html-el:option value="">请选择市...</html-el:option>
        </html-el:select>
        &nbsp;
        <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
          <html-el:option value="">请选择区/县...</html-el:option>
        </html-el:select></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">性别：</td>
      <td><html-el:select property="sex" styleId="sex">
          <html-el:option value="0">男</html-el:option>
          <html-el:option value="1">女</html-el:option>
        </html-el:select></td>
    </tr>
    <tr>
      <td  nowrap="nowrap" class="title_item">生日：</td>
      <td><fmt:formatDate value="${af.map.birthday}" pattern="yyyy-MM-dd" var="_add_birthday" />
        <html-el:text property="birthday" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${_add_birthday}" style="cursor:pointer;text-align:center;" title="点击选择日期" styleClass="webinput" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">身份证号：</td>
      <td><c:set var="display_id_card" value="" />
        <c:set var="display_id_card_span" value="none" />
        <c:if test="${not empty af.map.id_card}">
          <c:set var="display_id_card" value="none" />
          <c:set var="display_id_card_span" value="" />
        </c:if>
        <html-el:text property="id_card" maxlength="80" styleClass="webinput" styleId="id_card" style="width:135px;display:${display_id_card}" value="${af.map.id_card}"/>
        <span id="id_card_span" style="display:${display_id_card_span}">${af.map.encryptIdCard}&nbsp;<a class="label label-warning" onclick="$('#id_card').show();$('#id_card_span').hide();">修改</a></span> </td>
    </tr>
    <tr>
      <td  nowrap="nowrap" class="title_item">办公电话：</td>
      <td><html-el:text property="office_tel" maxlength="80" styleClass="webinput" styleId="office_tel" style="width:400px;" value="${af.map.office_tel}" />
        多个办公电话请用逗号分隔</td>
    </tr>
<!--     <tr> -->
<!--       <td  nowrap="nowrap" class="title_item">微信id：</td> -->
<%--       <td>${af.map.appid_weixin}</td> --%>
<!--     </tr> -->
    <tr>
      <td class="title_item">&nbsp;</td>
      <td colspan="3" style="text-align:left"><button class="bgButtonFontAwesome" type="button" id="btn_submit0"><i class="fa fa-save"></i>保 存</button></td>
    </tr>
  </table>
</html-el:form>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<c:url var="urlhome" value="/manager/customer/Index.do?method=welcome" />
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	
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
        	}
        	
        }
    });
	
	var btn_name = "上传头像";
	if ("" != "${af.map.user_logo}") {
		btn_name = "重新上传";
	}
	upload("user_logo", "image", btn_name, "${ctx}");
	
	$("#real_name").attr("dataType", "Require").attr("msg", "真实姓名不能为空");
	$("#old_password").attr("dataType", "Require").attr("msg", "原密码不能为空");
	$("#new_password").attr("dataType", "Require").attr("msg", "新密码不能为空");
	$("#repeat").attr("datatype", "Repeat").attr("to", "new_password").attr("msg", "两次输入的密码不一致");
	$("#real_name").attr("datatype","Require").attr("msg","真实姓名不能为空！");

	$("#id_card").attr("datatype","IdCard").attr("Require","false").attr("msg","请输入正确的身份证号！");
	
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit0").click(function(){
		if (Validator.Validate(f0, 1)) {
			$.dialog.tips("数据提交中...",1, "loading.gif"); 
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=saveUserInfo",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if (data.code == "1") {
							$.dialog.tips(data.msg,1, "success.gif");
							window.setTimeout(function () {
								goUrl('${urlhome}',0);
							},2000);
						} else {
							$.dialog.tips(data.msg,2, "tips.gif");
						}
					}
				});	
			}, 1000);
			return true;
		}
		return false;
	});

	
	$("#id_card_").change(function(){
		var thisVal = $(this).val();
		if(null != thisVal && '' != thisVal){
			$("#id_card").val(thisVal);
		}
	});

	$("#userul").find("li").click(function(){
		$(this).siblings().removeClass("xur");
		var id = "form" + $(this).attr("data_id");
		$(".backTable").hide();
		$(this).addClass("xur");
		$("#" + id).fadeIn();
		
	});
	
});

//]]></script>
</body>
</html>
