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
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/QaInfo?method=save">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="is_nx" styleId="is_nx" />
    <html-el:hidden property="q_type"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">问题</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">问题所属类型：</td>
        <td width="88%"><c:if test="${af.map.q_type eq 1 }">
            <c:set var="q_type_value" value="1"/>
            企业投诉</c:if>
          <c:if test="${af.map.q_type eq 2 }">
            <c:set var="q_type_value" value="2"/>
            咨询投诉</c:if>
          <html-el:hidden property="q_type" styleId="q_type" value="${q_type_value }" />
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>标题：</td>
        <td><html-el:text property="q_title" maxlength="120" style="width:597px" styleClass="webinput" styleId="q_title" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>投诉内容：</td>
        <td><html-el:textarea property="q_content" style="width:450px;height:100px" styleClass="webtextarea" styleId="q_content" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>投诉时间：</td>
        <td><fmt:formatDate value="${af.map.q_date}" pattern="yyyy-MM-dd" var="_q_date" />
          <html-el:text property="q_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${_q_date}" styleId="q_date" styleClass="webinput" /></td>
      </tr>
      <tr>
        <th colspan="2" nowrap="nowrap">投诉人基本信息</th>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>投诉人姓名：</td>
        <td><html-el:text property="q_name" maxlength="30" style="width:140px" styleClass="webinput" styleId="q_name" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">投诉人电话：</td>
        <td><html-el:text property="q_tel" maxlength="30" style="width:140px" styleClass="webinput" styleId="q_tel" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">投诉人邮箱：</td>
        <td><html-el:text property="q_email" maxlength="30" style="width:140px" styleClass="webinput" styleId="q_email" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">投诉人联系地址：</td>
        <td><html-el:text property="q_addr" maxlength="30" style="width:400px" styleClass="webinput" styleId="q_addr" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">投诉人IP：</td>
        <td><c:out value="${af.map.q_ip}" /></td>
      </tr>
      <tr>
        <th colspan="2" nowrap="nowrap">回答</th>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">回答内容：</td>
        <td><html-el:textarea property="a_content" styleId="a_content" style="width:450px;height:100px" styleClass="webtextarea" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">回答人：</td>
        <td><html-el:text property="a_uname" maxlength="30" style="width:140px" readonly="true" styleClass="webinput" styleId="a_uname" value="${af.map.user_name}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">回答时间：</td>
        <td><fmt:formatDate value="${af.map.a_date}" pattern="yyyy-MM-dd" var="_a_date" />
          <html-el:text property="a_date" size="10" maxlength="10" readonly="true" styleClass="webinput" onclick="WdatePicker();" value="${_a_date}" styleId="a_date" /></td>
      </tr>
      <tr>
        <th colspan="2" nowrap="nowrap">状态</th>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">排序值：</td>
        <td><html-el:text property="order_value" styleId="order_value" maxlength="4"  styleClass="webinput" size="4" />
          值越大，显示越靠前。</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">状态：</td>
        <td><html-el:select property="qa_state">
            <html-el:option value="0">未发布</html-el:option>
            <html-el:option value="1">已发布</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
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
	$("#q_title").attr("dataType", "Require").attr("msg", "标题必须填写");
	$("#q_date").attr("dataType", "Require").attr("msg", "投诉时间必须填写");
	$("#q_name").attr("dataType", "Require").attr("msg", "投诉人姓名必须填写");
	$("#a_uname").attr("dataType", "Require").attr("msg", "回答人姓名必须填写");
	$("#order_value").attr("dataType", "Integer").attr("msg", "排序值必须为整数").focus(setOnlyNum);
	$("#q_content").attr("dataType","Limit").attr("min","1").attr("max","500").attr("msg","内容必须填写,且在500个汉字之内");
	$("#a_content").attr("dataType","Limit").attr("min","1").attr("max","2000").attr("require","false").attr("msg","内容必须填写,且在2000个汉字之内");
	$("#q_email").attr("datatype","Email").attr("require","false").attr("msg","电子信箱填写有误！");

	//提交
	$("#btn_submit").click(function(){
		
		
		if(Validator.Validate(this.form, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	});
});
function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	})
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}
		if(this.value.length == 0) this.value = 0;
	})
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
