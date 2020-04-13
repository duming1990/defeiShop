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
  <c:set var="base_name" value="内容" />
  <c:set var="base_name1" value="" />
  <c:set var="base_name1_unit" value="" />
  <c:set var="base_name2" value="" />
  <c:set var="base_name2_unit" value="" />
  <c:set var="base_name3" value="" />
  <c:set var="base_name3_unit" value="" />
 <c:if test="${af.map.type eq 10}"><c:set var="base_name" value="用户类型" /></c:if>
  <c:if test="${af.map.type eq 30}"><c:set var="base_name" value="热门搜索" /></c:if>
  <c:if test="${af.map.type eq 200}">
    <c:set var="base_name" value="会员等级" />
  </c:if>
  <c:if test="${af.map.type eq 500}">
    <c:set var="base_name" value="积分获取规则" />
    <c:set var="base_name1" value="获取积分" />
    <c:set var="base_name1_unit" value="个" />
  </c:if>
  <c:if test="${af.map.type eq 600}">
    <c:set var="base_name" value="手续费管理" />
    <c:set var="base_name1" value="手续费" />
    <c:set var="base_name1_unit" value="%" />
  </c:if>
  <c:if test="${af.map.type eq 600 and (af.map.id eq 604 or af.map.id eq 605 or af.map.id eq 606 or af.map.id eq 609 or af.map.id eq 610 or af.map.id eq 611)}">
    <c:set var="base_name" value="手续费管理" />
    <c:set var="base_name1" value="额度" />
    <c:set var="base_name1_unit" value="元" />
  </c:if>
  <c:if test="${af.map.id eq 602 or af.map.id eq 608}">
    <c:set var="base_name" value="手续费管理" />
    <c:set var="base_name1" value="额度" />
    <c:set var="base_name1_unit" value="‰" />
  </c:if>
  <c:if test="${af.map.type eq 800}">
    <c:set var="base_name" value="消费币获取规则" />
    <c:set var="base_name1" value="获取消费币" />
    <c:set var="base_name1_unit" value="个" />
  </c:if>
  <c:if test="${af.map.type eq 900}">
    <c:set var="base_name" value="币种兑换规则" />
    <c:set var="base_name1" value="兑换前" />
    <c:set var="base_name1_unit" value="元" />
    <c:set var="base_name2" value="兑换后" />
    <c:set var="base_name2_unit" value="元" />
  </c:if>
  <c:if test="${af.map.type eq 1000}">
    <c:set var="base_name" value="余额获取规则" />
    <c:set var="base_name2" value="返现比例" />
    <c:set var="base_name2_unit" value="%" />
  </c:if>
   
  <c:if test="${af.map.type eq 1100}">
    <c:set var="base_name" value="时间管理" />
    <c:set var="base_name1" value="时间" />
    <c:set var="base_name1_unit" value="小时" />
  </c:if>
  <c:if test="${af.map.type eq 1800}">
    <c:set var="base_name" value="合伙人缴费金额" />
    <c:set var="base_name1" value="缴费金额" />
    <c:set var="base_name1_unit" value="元" />
  </c:if>
  <c:if test="${af.map.type eq 1900}">
    <c:set var="base_name" value="扶贫规则" />
    <c:set var="base_name1" value="限制数量" />
    <c:set var="base_name1_unit" value="个" />
  </c:if>
  <c:if test="${af.map.type eq 2200}">
    <c:set var="base_name" value="规则名称" />
    <c:set var="base_name1_2200" value="比例数值" />
  </c:if>
  <c:if test="${af.map.type eq 5000}"><c:set var="base_name" value="包邮价格" /></c:if>
  <c:if test="${af.map.type eq 9000}"><c:set var="base_name" value="敏感词库" /></c:if>
  <c:if test="${af.map.type eq 3100}"><c:set var="base_name" value="公司性质" /></c:if>
  <c:if test="${af.map.type eq 3200}"><c:set var="base_name" value="安全问题" /></c:if>
  <c:if test="${af.map.type eq 2100}"><c:set var="base_name" value="促销时间设置" /></c:if>
  <c:if test="${af.map.type eq 16000}"><c:set var="base_name" value="设备厂商名称" /></c:if>
  <c:if test="${af.map.type eq 17000}"><c:set var="base_name" value="商品进出库类型" /></c:if>
  <c:if test="${af.map.type eq 100}">
  <c:set var="base_name" value="消费可提现金额设置" />
  <c:set var="base_name1" value="消费金额" />
  <c:set var="base_name2" value="可提现金额" />
  <c:set var="base_name1_unit" value="元" />
  <c:set var="base_name2_unit" value="元" />
  </c:if>
  <c:if test="${af.map.type eq 101}">
  <c:set var="base_name" value="余额复销券分红比例" />
  <c:set var="base_name1" value="余额分红比例" />
  <c:set var="base_name1_unit" value="%" />
  </c:if>
  <c:if test="${af.map.type eq 301}"><c:set var="base_name" value="标签类型" /></c:if>
  <html-el:form action="/admin/BaseData.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">${base_name}</th>
      </tr>
      <tr>
      <td width="15%" class="title_item"><span style="color: #F00;">*</span>${base_name}：</td>
        <td width="85%"><html-el:text property="type_name" maxlength="50" styleClass="webinput" styleId="type_name" style="width:200px" />
          &nbsp;<span id="type_name_tip" style="display:none;"></span></td>
      </tr>
      <c:if test="${af.map.type eq 2200}">
      	<tr>
        <td class="title_item"><span style="color: #F00;">*</span>${base_name1_2200}：</td>
        <td>
<%--         <c:if test="${empty af.map.per_number}"><c:set var="number1" value="0" /></c:if> --%>
<%--         <c:if test="${not empty af.map.pre_number}"><c:set var="number1" value="${af.map.pre_number}" /></c:if> --%>
<%--          <c:if test="${empty af.map.per_number2}"><c:set var="number2" value="0" /></c:if> --%>
<%--         <c:if test="${not empty af.map.pre_number2}"><c:set var="number2" value="${af.map.pre_number2}" /></c:if> --%>
<%--         <html-el:text property="pre_number" maxlength="10"  styleClass="webinput" styleId="pre_number" value="${number1}"  style="width:100px" onchange="change();" />&nbsp; --%>
<!--         	/ -->
<%--         <html-el:text property="pre_number2" maxlength="10"  styleClass="webinput" styleId="pre_number2" value="${number2}" style="width:100px" onchange="change();" />&nbsp; --%>
<!--         = <span id="calculate_value" style="color: #E80909;"></span> -->
        <html-el:text property="pre_decimal1" styleId="pre_decimal1" styleClass="webinput" style="width:200px" />
        </td>
      </tr>
      </c:if>
      <c:if test="${not empty base_name1}">
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>${base_name1}：</td>
        <td><html-el:text property="pre_number" maxlength="10"  styleClass="webinput" styleId="pre_number" style="width:200px" />&nbsp;${base_name1_unit}</td>
      </tr>
      </c:if>
      <c:if test="${not empty base_name2}">
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>${base_name2}：</td>
        <td><html-el:text property="pre_number2" maxlength="10"  styleClass="webinput" styleId="pre_number2" style="width:200px" />&nbsp;${base_name2_unit}</td>
      </tr>
      </c:if>
      <c:if test="${not empty base_name3}">
      <fmt:parseNumber var="pn3" pattern="0.00"  value="${af.map.pre_number2/af.map.pre_number}"/>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>${base_name3}：</td>
        <td><html-el:text property="pre_number3" maxlength="10" value="${pn3}"  styleClass="webinput" styleId="pre_number3" style="width:200px" />&nbsp;${base_name3_unit}</td>
      </tr>
      </c:if>
      <c:if test="${not empty base_name1_decimal}">
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>${base_name1_decimal}：</td>
        <fmt:formatNumber var="number" value="${af.map.pre_decimal1}" pattern="0.########"/>
        <td><html-el:text property="pre_decimal1" maxlength="10"  styleClass="webinput" value="${number}" styleId="pre_decimal1" style="width:200px" />&nbsp;${base_name1_unit}</td>
      </tr>
      </c:if>
      <c:if test="${not empty base_name2_decimal}">
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>${base_name2_decimal}：</td>
        <td><html-el:text property="pre_decimal2" maxlength="10"  styleClass="webinput" styleId="pre_decimal2" style="width:200px" />&nbsp;${base_name1_unit}</td>
      </tr>
      </c:if>
      
      <c:if test="${af.map.type eq 2100}">
          <tr>
            <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动开始时间：</td>
            <td>
              <html-el:text styleId="pre_varchar_1" property="pre_varchar_1" size="20" maxlength="15" readonly="true" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" style="cursor:pointer;text-align:center;" value="${af.map.pre_varchar_1}" styleClass="webinput" /></td>
          </tr>
          <tr>
            <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动结束时间：</td>
            <td>
              <html-el:text styleId="pre_varchar_2" property="pre_varchar_2" size="20" maxlength="15" readonly="true" onclick="WdatePicker({dateFmt:'HH:mm:ss'})" style="cursor:pointer;text-align:center;" value="${af.map.pre_varchar_2}" styleClass="webinput" /></td>
          </tr>
      </c:if>
   
      <tr>
        <td class="title_item">备注：</td>
        <td><html-el:text property="remark" maxlength="200"  styleClass="webinput" styleId="remark" style="width:200px" />
        
        <c:if test="${af.map.type == 2200 }"></br><span id="tip_remark" style="color: #E80909;"></span></c:if>
        </td>
      </tr>
       <c:if test="${af.map.type eq 17000}">
       <tr>
        <td class="title_item">进/出库：</td>
        <td><html-el:select property="pre_number" styleId="pre_number">
              <html-el:option value="0">进库</html-el:option>
              <html-el:option value="1">出库</html-el:option>
            </html-el:select>（进库还是出库） </td>
      </tr>
      </c:if>
      <c:if test="${af.map.is_lock eq 0}">
      <tr>
        <td class="title_item">是否锁定：</td>
        <td><html-el:select property="is_lock" styleId="is_lock">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>（锁定后，数据将无法删除） </td>
      </tr>
      </c:if>
      <tr>
        <td class="title_item">排序值：</td>
        <td><html-el:text property="order_value"  maxlength="4" size="4" styleClass="webinput" styleId="order_value"  />
          值越大，显示越靠前，范围：0-9999 </td>
      </tr>
      <tr>
        <td class="title_item">提示：</td>
        <td> <span style="color: #E80909;">请勿随意修改基础数据，如需修改请和管理员确认！</span> </td>
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
       <c:if test="${af.map.type ne 300}">
       <tr>
        <td colspan="2" style="text-align:center">
        <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
       </c:if>
      
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript">//<![CDATA[
var type_name_exits = '<span id="tip" style="color:red;">对不起，此类型已被注册</span>';
var type_name_not_exits = '<span id="tip" style="color:#33CC33;">恭喜你，该类型名可以使用</span>';

var f = document.forms[0];


<c:if test="${af.map.type eq 2100}">
$("#pre_varchar_1").attr("dataType", "Require").attr("msg", "请选择活动开始时间");
$("#pre_varchar_2").attr("dataType", "Require").attr("msg", "请选择活动结束时间");
</c:if>

$("#type_name").attr("datatype","Require").attr("msg","${base_name}必须填写");
$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
<c:if test="${not empty base_name1}">
$("#pre_number").attr("datatype","Number").attr("msg","${base_name1}必须填写，且为数字");
</c:if>
<c:if test="${not empty base_name2}">
$("#pre_number2").attr("datatype","Number").attr("msg","${base_name2}必须填写，且为数字")
</c:if>
<c:if test="${not empty base_name3}">
$("#pre_number3").attr("datatype","Currency").attr("msg","请正确填写${base_name3}");
</c:if>
<c:if test="${not empty base_name1_decimal}">
$("#pre_decimal1").attr("datatype","Currency").attr("msg","${base_name1_decimal}必须填写，且格式正确");
</c:if>
<c:if test="${not empty base_name2_decimal}">
$("#pre_decimal2").attr("datatype","Currency").attr("msg","${base_name2_decimal}必须填写，且格式正确");
</c:if>

// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		var t_name = $.trim($("#type_name").val());
		var t_id = $("#id").val();
		if("" == t_name) {
			alert("请填写用户类型");
			return false;
		}
		$("#tip").remove();
		$.post("BaseData.do?method=checkTypeName",{type_name : t_name, id : t_id,type:"${af.map.type}"},function(data){
			if(data == "1"){
				$("#type_name_tip").show().append(type_name_exits);
			} else {
		        $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		        $("#btn_reset").attr("disabled", "true");
		        $("#btn_back").attr("disabled", "true");
				f.submit();
			}
		});
	}
});

// function change(){
// 	var num1 = $("#pre_number").val();
// 	var num2 = $("#pre_number2").val();
// 	if(null != num1 && null != num2 && !isNaN(num1) && !isNaN(num2)){
// 		var index = parseInt(num1)/parseInt(num2);
// 		console.log("index:"+index)
// 		if(isNaN(index)){
// 			index = 0;
// 			console.log("isNaN--index改为0:"+index)
// 		}
// 		if(!isFinite(index)){
// 			index = 0;
// 			console.log("isFinite---index改为0:"+index)
// 		}
		
// 		$("#calculate_value").text(index);
// 		$("#pre_decimal1").val(index);
		
// 		$("#tip_remark").text($("#type_name").val()+":"+num1 +"/"+num2+"="+index);
// 	}
// }

function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = 0;
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
