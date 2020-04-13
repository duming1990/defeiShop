<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
  <html-el:form action="/admin/HBRule.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">红包规则信息 </th>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 标题： </td>
        <td width="85%"><html-el:text property="title" maxlength="20" styleClass="webinput" styleId="title" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>红包大类：</td>
        <td><html-el:select property="hb_class" styleId="hb_class">
            <c:forEach items="${hbClass}" var="keys">
              <html-el:option value="${keys.index}">${keys.name}</html-el:option>
            </c:forEach>
          </html-el:select>
        </td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>红包类型：</td>
        <td><html-el:select property="hb_type" styleId="hb_type">
            <c:forEach items="${hbTypes}" var="keys">
              <html-el:option value="${keys.index}">${keys.name}</html-el:option>
            </c:forEach>
          </html-el:select>
        </td>
      </tr>
      <tr id="hb_type_variable3">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 分享人获得额度： </td>
        <td width="85%"><html-el:text property="share_user_money" maxlength="20" styleClass="webinput" styleId="share_user_money" style="width:200px" />
          &nbsp;元</td>
      </tr>
      <tr id="hb_type_constant">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 定额红包额度： </td>
        <td width="85%"><html-el:text property="hb_money" maxlength="20" styleClass="webinput" styleId="hb_money" style="width:200px" />
          &nbsp;元</td>
      </tr>
      <tr id="hb_type_variable1">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 最小红包额度： </td>
        <td width="85%"><html-el:text property="min_money" maxlength="20" styleClass="webinput" styleId="min_money" style="width:200px" />
          &nbsp;元</td>
      </tr>
      <tr id="hb_type_variable2">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 最大红包额度： </td>
        <td width="85%"><html-el:text property="max_money" maxlength="20" styleClass="webinput" styleId="max_money" style="width:200px" />
          &nbsp;元</td>
      </tr>
      <tr id="hb_class_show">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 红包总量： </td>
        <td width="85%"><html-el:text property="hb_max_count" maxlength="20" styleClass="webinput" styleId="hb_max_count" style="width:200px" />
          &nbsp;个</td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span> 有效期周期： </td>
        <td width="85%"><html-el:text property="effect_count" maxlength="20" styleClass="webinput" styleId="effect_count" style="width:200px" />
          &nbsp;天</td>
      </tr>
      <tr>
        <td class="title_item">是否关闭：</td>
        <td><html-el:select property="is_closed" styleId="is_closed">
            <html-el:option value="0">打开</html-el:option>
            <html-el:option value="1">关闭</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td width="15%" class="title_item"> 备注： </td>
        <td width="85%"><html-el:text property="remark" maxlength="20" styleClass="webinput" styleId="remark" style="width:200px" /></td>
      </tr>
      <c:if test="${af.map.is_del eq 1}">
        <tr>
          <td class="title_item">是否删除：</td>
          <td><html-el:select property="is_del" styleId="is_del">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select></td>
        </tr>
      </c:if>
      <tr>
        <td colspan="2" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
var f = document.forms[0];

$("#title").attr("datatype","Require").attr("msg","标题必须填写");
$("#effect_count").attr("datatype","Require").attr("msg","有效期周期必须填写");

//通过判断红包类型，随机或者定额控制定额和最大最小额度的显示
if("10" == $("#hb_type").val()){
	$("#hb_type_constant").show();
    $("#hb_type_variable1").hide();
    $("#min_money").removeAttr("dataType");
    $("#hb_type_variable2").hide();
    $("#max_money").removeAttr("dataType");
    $("#hb_money").attr("datatype","Require").attr("msg","定额红包额度必须填写");
} else if("20" == $("#hb_type").val()){
	$("#hb_type_variable1").show();
	$("#hb_type_variable2").show();
    $("#hb_type_constant").hide();
	$("#hb_money").removeAttr("dataType");
    $("#min_money").attr("datatype","Require").attr("msg","最小红包额度必须填写");
    $("#max_money").attr("datatype","Require").attr("msg","最大红包额度必须填写");
}
$("#hb_type").change(function(){
     if("10" == $(this).val()){
    	 $("#hb_type_constant").show();
    	 $("#hb_type_variable1").hide();
    	 $("#min_money").removeAttr("dataType");
    	 $("#hb_type_variable2").hide();
    	 $("#max_money").removeAttr("dataType");
    	 $("#hb_money").attr("datatype","Require").attr("msg","定额红包额度必须填写");
     }else if("20" == $("#hb_type").val()){
    	 $("#hb_type_variable1").show();
    	 $("#hb_type_variable2").show();
    	 $("#hb_type_constant").hide();
    	 $("#hb_money").removeAttr("dataType");
    	 $("#min_money").attr("datatype","Require").attr("msg","最小红包额度必须填写");
    	 $("#max_money").attr("datatype","Require").attr("msg","最大红包额度必须填写");
     }
});

if("10" == $("#hb_class").val()){
	$("#hb_class_show").hide();
	$("#share_user_money").attr("datatype","Require").attr("msg","分享人获得额度必须填写");
} else {
	$("#hb_class_show").show();
	$("#hb_max_count").attr("datatype","Require").attr("msg","红包总量必须填写");
	$("#hb_type_variable3").hide();
    $("#share_user_money").removeAttr("dataType");
}
$("#hb_class").change(function(){
     if("10" == $(this).val()){
    	 $("#hb_type_variable3").show();
    	 $("#share_user_money").attr("datatype","Require").attr("msg","分享人获得额度必须填写");
    	 $("#hb_class_show").hide();
    	 $("#hb_max_count").removeAttr("dataType");
     }else{
    	 $("#hb_type_variable3").hide();
    	 $("#share_user_money").removeAttr("dataType");
    	 $("#hb_class_show").show();
    	 $("#hb_max_count").attr("datatype","Require").attr("msg","红包总量必须填写");
     }
});

$("#btn_submit").click(function(){
	
	if(Validator.Validate(f, 3)){
		var hb_type = $("#hb_type").val();
		var share_user_money = $("#share_user_money").val();
		var hb_money = $("#hb_money").val();
		var min_money = $("#min_money").val();
		var max_money = $("#max_money").val();
		var hb_class = $("#hb_class").val();
		if(hb_type == 20 && min_money > max_money) {
			alert("最小红包额度不能大于最大红包额度！");
			return false;
		}
		$.post("HBRule.do?method=checkYhqRoleForHBMoney",{hb_type : hb_type, share_user_money : share_user_money, hb_money : hb_money, min_money : min_money, max_money : max_money,hb_class : hb_class},function(data){

			if (data.code == "0") {
				$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
 		        $("#btn_reset").attr("disabled", "true");
 		        $("#btn_back").attr("disabled", "true");
 				f.submit();
			} else {
				alert(data.msg);
			}
			// 			if(data == "1"){
// 				alert("不存在对应分享人获得额度的优惠券规则，请先前往优惠券规则管理维护相应额度的优惠券规则！");
// 				return false;
// 			} else if(data == "2"){
// 				alert("不存在对应定额红包额度的优惠券规则，请先前往优惠券规则管理维护相应额度的优惠券规则！");
// 				return false;
// 			} else if(data == "3"){
// 				alert("最大最小红包额度范围内的优惠券规则不完整，请先前往优惠券规则管理维护相应额度的优惠券规则！");
// 				return false;
// 			} else if(data == "4"){
// 				alert("请选择红包类型！");
// 				return false;
// 			} else {
// 				alert(1);
// 				$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
// 		        $("#btn_reset").attr("disabled", "true");
// 		        $("#btn_back").attr("disabled", "true");
// 				f.submit();
// 			}
		});
	}
});




});


//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
