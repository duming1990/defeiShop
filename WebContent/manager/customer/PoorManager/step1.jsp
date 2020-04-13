<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
<div class="mainbox mine" style="height: 1300px;">
  <jsp:include page="../_nav.jsp" flush="true"/> 
  <html-el:form action="/customer/PoorManager.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveStep1" />
    <html-el:hidden property="mod_id" styleId="mod_id"/>
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="p_index" styleId="p_index" value="${p_index}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <div class="all">
      <ul class="nav nav-tabs" id="nav_ul">
          <li class="active" onclick="goStep(1)"><a><span>基本信息</span></a></li>
          <li onclick="goStep(2)"><a><span>家庭成员</span></a></li>
          <li onclick="goStep(3)"><a><span>帮扶措施</span></a></li>
          <li onclick="goStep(4)"><a><span>帮扶责任人</span></a></li>
      </ul>
    </div>
      <tr>
        <th colspan="4">贫困户基本信息</th>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>户主姓名：</td>
        <td><html-el:text property="real_name" styleId="real_name" maxlength="10" style="width:240px" styleClass="webinput"/></td>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>性别：</td>
        <td>
		  <html-el:select property="sex" styleId="sex">
            <html-el:option value="0">男</html-el:option>
            <html-el:option value="1">女</html-el:option>
          </html-el:select>
		</td>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>家庭人口：</td>
         <td><html-el:text property="family_num" maxlength="50" styleId="family_num"/></td>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>民族：</td>
         <td><html-el:text property="nation" maxlength="50" styleId="nation" style="width:100px"/>&nbsp;
         </td>
       </tr>
      <tr>
       <td class="title_item"><span style="color: #F00;">*</span>出生日期：</td>
        <td><fmt:formatDate value="${af.map.brithday}" pattern="yyyy-MM-dd" var="brithday" />
        <html-el:text property="brithday" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${brithday}" style="cursor:pointer;text-align:center;" title="点击选择日期" /></td>
      	<td class="title_item">联系电话：</td>
        <td><html-el:text property="mobile" maxlength="80" styleClass="webinput" styleId="mobile" style="width:200px;" /></td>
      </tr>
      <tr>
       <td class="title_item"><span style="color: #F00;">*</span>家庭住址：</td>
       <td colspan="3"><html-el:text property="addr" styleClass="webinput" styleId="addr" style="width:800px;" /></td>
      </tr> 
      <tr>
       <td class="title_item"><span style="color: #F00;">*</span>致贫原因：</td>
       <td colspan="3"><html-el:textarea property="poor_reason"  rows="8" styleClass="webinput" styleId="poor_reason" style="width:800px;"/></td>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>耕地面积：</td>
         <td><html-el:text property="gendi_arear" maxlength="50" styleId="gendi_arear"/>(亩)</td>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>林地面积：</td>
         <td><html-el:text property="lindi_arear" maxlength="50" styleId="lindi_arear" style="width:100px"/>(亩)</td>
       </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>牧草地面积：</td>
         <td><html-el:text property="mucaodi_arear" maxlength="50" styleId="mucaodi_arear"/>(亩)</td>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>住房面积：</td>
         <td><html-el:text property="house_arear" maxlength="50" styleId="house_arear" style="width:100px"/>(平方米)</td>
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
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>档案照：</td>
          <td>
              <div style="float:left;width:50%;">
              <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:set var="img_max" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
              <c:if test="${not empty af.map.dang_an_img}">
                <c:set var="img" value="${ctx}/${af.map.dang_an_img}@s400x400" />
                <c:set var="img_max" value="${ctx}/${af.map.dang_an_img}" />
              </c:if>
              <a href="${img_max}" id="dang_an_img_a" target="_blank"><img src="${img}" height="50" id="dang_an_img_img" /></a>
              <html-el:hidden property="dang_an_img" styleId="dang_an_img" value="${af.map.dang_an_img}"/>
              <div class="files-warp" id="dang_an_img_warp"> <span>档案照:</span><br />
                <div class="btn-files"> <span>添加附件</span>
                  <input id="dang_an_img_file" type="file" name="dang_an_img_file"/>
                </div>
                <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
              </div>
            <span style="color: #F00;float:left">&nbsp;档案照图片，大小500K以内，文字须清晰可见。</span>
            </div>
          </td>
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>头像：</td>
       	 <td>
       	 <div style="float:left;width:50%;">
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
       <td class="title_item"><span style="color: #F00;">*</span>识别贫困户日期：</td>
        <td><fmt:formatDate value="${af.map.jian_dang_date}" pattern="yyyy-MM-dd" var="jian_dang_date" />
        <html-el:text property="jian_dang_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${jian_dang_date}" style="cursor:pointer;text-align:center;" title="点击选择日期" /></td>
      	<td class="title_item"><span style="color: #F00;">*</span>计划脱贫日期：</td>
      	 <td><fmt:formatDate value="${af.map.tuo_pin_plan_date}" pattern="yyyy-MM-dd" var="tuo_pin_plan_date" />
        <html-el:text property="tuo_pin_plan_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${tuo_pin_plan_date}" style="cursor:pointer;text-align:center;" title="点击选择日期" /></td>
      </tr>
      
      <tr id="is_tuo_pin_tr">
      	<td class="title_item"><span style="color:#F00;">*</span>是否脱贫</td>
      	<td><html-el:select property="is_tuo_pin" styleId="is_tuo_pin" onchange="PoorHide()">
      		<html-el:option value="0">未脱贫</html-el:option>
      		<html-el:option value="1">已脱贫</html-el:option>
      	</html-el:select></td>
      	<td class="title_item"><span style="color: #F00;">*</span>脱贫日期：</td>
      	<td><fmt:formatDate value="${af.map.tuo_pin_date}" pattern="yyyy-MM-dd" var="tuo_pin_date" />
      	<html-el:text property="tuo_pin_date" styleId="tuo_pin_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${tuo_pin_date}" style="cursor:pointer;text-align:center;" title="点击选择日期" />
      	</td>
      </tr>
      
      <tr>
      <td colspan="4" align="center">
      <c:if test="${af.map.audit_state ne 1}">
       <html-el:submit property="" value="保存" styleClass="bgButton" styleId="btn_submit" />
      </c:if>
      <c:if test="${af.map.audit_state eq 1}">
      	<html-el:button property="" value="保存" onclick="tip();" styleClass="bgButton but-disabled" title="已被上级审核，无法修改"/>
      </c:if>
      &nbsp;
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
      </td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript"><!--//<![CDATA[
var user_exits = '<span id="tip" style="color:red;">对不起，此用户名已被注册</span>';
var user_idCard_exits = '<span id="tip_idcard" style="color:red;">对不起，身份证号码重复！</span>';                                              
var user_Mobile_exits = '<span id="tip_mobile" style="color:red;">对不起，手机号码重复！</span>';                                              
                                 
var f = document.forms[0];
$("#is_tuo_pin_tr td:gt(1)").show();
<c:if test="${af.map.is_tuo_pin ne 1}">
	$("#is_tuo_pin_tr td:eq(1)").attr("colspan","3");
	$("#is_tuo_pin_tr td:gt(1)").hide();
</c:if>
$(".toolbar a").removeClass("on");
$(".toolbar a:eq(0)").addClass("on");


$("#real_name").attr("datatype","Require").attr("msg","贫困户姓名必须填写");
$("#family_num").attr("datatype","Require").attr("msg","家庭人口必须填写");
$("#brithday").attr("datatype","Require").attr("msg","出生日期必须填写");
$("#jian_dang_date").attr("datatype","Require").attr("msg","识别贫困户日期必须填写");
$("#tuo_pin_plan_date").attr("datatype","Require").attr("msg","计划脱贫日期必须填写");
$("#addr").attr("datatype","Require").attr("msg","家庭住址必须填写");
$("#poor_reason").attr("datatype","Require").attr("msg","请填写致贫原因");
// $("#mobile").attr("datatype","Mobile").attr("msg","手机格式不正确");
$("#nation").attr("datatype","Require").attr("msg","民族必须填写");
$("#gendi_arear").attr("datatype","Require").attr("msg","耕地面积必须填写");
$("#lindi_arear").attr("datatype","Require").attr("msg","林地面积必须填写");
$("#mucaodi_arear").attr("datatype","Require").attr("msg","牧草地面积必须填写");
$("#house_arear").attr("datatype","Require").attr("msg","住房面积必须填写");
$("#id_card").attr("datatype","IdCard").attr("msg","请填写正确格式的身份证号码！");
$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
$("#dang_an_img").attr("dataType", "Filter" ).attr("msg", "档案照必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
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

var btn_name = "上传档案照";
if ("" != "${dang_an_img}") {
	btn_name = "重新上传档案照";
}
upload("dang_an_img", "image", btn_name, "${ctx}");

var btn_name = "上传头像";
if ("" != "${af.map.head_logo}") {
	btn_name = "重新上传头像";
}
upload("head_logo", "image", btn_name, "${ctx}");

// $("#mobile").change(function(){
// 	var mobile = $(this).val();
// 	var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
// 	if (mobile.match(reg)) {
// 		$.ajax({
// 			type: "POST" , 
// 			url: "${ctx}/manager/customer/PoorManager.do" , 
// 			data:"method=validateMobileForPoor&mobile=" + mobile + "&t=" + new Date(),
// 			dataType: "json", 
// 	        async: true, 
// 	        error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
// 	        success: function (data) {
// 				if (data.ret == 0) {
// 					$.jBox.tip('参数丢失！');
// 					$("#mobile").val("");
// 				}else if (data.ret == 2) {
// 				  var submit = function (v, h, f) {
// 	                    if (v == true){
// 	                    	$("#id_card").val(data.id_card);
// 	                    	$("#img_id_card_zm_img").attr("src","${ctx}/"+data.id_img_zm+"@s400x400");
// 	                    	$("#img_id_card_fm_img").attr("src","${ctx}/"+data.id_img_fm+"@s400x400");
// 	                    	$("#img_id_card_zm_a").attr("href","${ctx}/"+data.id_img_zm);
// 	                    	$("#img_id_card_fm_a").attr("href","${ctx}/"+data.id_img_fm);
// 	                    	$("#img_id_card_zm").val(data.id_img_zm);
// 	                    	$("#img_id_card_fm").val(+data.id_img_fm);
// 	                    }else{
// 	                    	$("#mobile").val("");
// 	                    }
// 	                    return true;
// 	                };
// 	                $.jBox.confirm("该手机用户已存在，是否确定将其变更为贫困户？", "提示", submit, { buttons: { '确定': true, '取消': false } });
// 				}else if (data.ret ==3) {
// 					$.jBox.tip('该手机用户已存在,若需变更为贫困户请先前往实名认证！');
// 					$("#mobile").val("");
// 				}else if (data.ret ==4){
// 					$.jBox.tip('该手机用户已存在,且该用户类型无法成为贫困户！');
// 					$("#mobile").val("");
// 				}else if (data.ret ==5){
// 					$.jBox.tip('该手机用户已经是贫困户！');
// 					$("#mobile").val("");
// 				}
// 	        }
// 		});
// 	} else {
// 		$.jBox.tip('手机格式不正确！');
// 		$("#servicecenter_linkman_tel").val("");
// 	}
// });
function tip(){
	$.jBox.tip('已被上级审核，无法修改！');
}
$("#id_card").change(function(){
	var id_card= $(this).val();
	$.ajax({
		type: "POST" , 
		url: "${ctx}/manager/customer/PoorManager.do" , 
		data:"method=validateIdCardForPoor&id_card=" + id_card,
		dataType: "json", 
        async: true, 
        error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
        success: function (data) {
			if (data.ret == 0) {
				$.jBox.tip('参数丢失！');
				$("#id_card").val("");
			}else if (data.ret == 2) {
				$.jBox.tip('绑定的身份证已被使用！');
				$("#id_card").val("");
			}
        }
	});
});

//提交
f.onsubmit = function(){
	if(Validator.Validate(f, 3)){
           $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
           $("#btn_reset").attr("disabled", "true");
           $("#btn_back").attr("disabled", "true");
		f.submit();
	}
return false;
};
	
function goStep(step){
	var id = document.getElementById('id').value;
	var mod_id = document.getElementById('mod_id').value;
	if(id != "" && id != null){
		location.href="${ctx}/manager/customer/PoorManager.do?method=step"+step+"&poor_id="+id+"&mod_id="+mod_id;
	}else{
		alert("请先完成贫困户基本信息填写!");
	}
}
function PoorHide(){
	var val=$("#is_tuo_pin").val();
	if(val=="1"){
		$("#is_tuo_pin_tr td:gt(1)").show();
		$("#is_tuo_pin_tr td:eq(1)").removeAttr("colspan");
		$("#tuo_pin_date").attr("datatype","Require").attr("msg","脱贫日期必填");
	}else{
		$("#is_tuo_pin_tr td:gt(1)").hide();
		$("#is_tuo_pin_tr td:eq(1)").attr("colspan","3");
		$("#tuo_pin_date").removeAttr("datatype");
		$("#tuo_pin_date").val("");
	}
}
//]]></script> 
</body>
</html>