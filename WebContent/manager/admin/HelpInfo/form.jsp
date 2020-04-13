<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<style type="text/css">
<!--
textarea {
	width: 79%;
	overflow-y: hidden;
	background: transparent;
}
-->
</style>
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/admin/HelpInfo">
    <html-el:hidden property="id" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="h_mod_id" />
    <html-el:hidden property="queryString" />
    <html-el:hidden property="pub_user_name" />
    <html-el:hidden property="pub_user_id" />
    <html-el:hidden property="isSingle" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">帮助中心文档信息</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>标题：</td>
        <td><html-el:text property="title" style="width:250px;color:#${af.map.title_color}" maxlength="130" onfocus="$('#tip_title').fadeIn('fast')" onblur="$('#tip_title').fadeOut('fast')" />
          &nbsp;&nbsp;&nbsp;
<%--           <html-el:select property="title_color"> --%>
<%--             <html-el:option value="">默认</html-el:option> --%>
<%--             <html-el:option value="000000" style="background-color:#000000">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="FFFFFF" style="background-color:#FFFFFF">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="008000" style="background-color:#008000">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="800000" style="background-color:#800000">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="808000" style="background-color:#808000">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="000080" style="background-color:#000080">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="800080" style="background-color:#800080">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="808080" style="background-color:#808080">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="FFFF00" style="background-color:#FFFF00">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="00FF00" style="background-color:#00FF00">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="00FFFF" style="background-color:#00FFFF">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="FF00FF" style="background-color:#FF00FF">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="FF0000" style="background-color:#FF0000">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="0000FF" style="background-color:#0000FF">&nbsp;</html-el:option> --%>
<%--             <html-el:option value="008080" style="background-color:#008080">&nbsp;</html-el:option> --%>
<%--           </html-el:select> --%>
          <span id="tip_title" style="color: #F00; font-size: 12px; display: none; margin-left: 2em;">^_^
          小提示：为了便于首页美观显示，建议字数在10~18之间为佳</span></td>
      </tr>
      <tr>
        <td width="15%" class="title_item">关键字：</td>
        <td><html-el:text property="key_word" style="width:250px;" maxlength="30" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">内容：</td>
        <td colspan="2"><html-el:hidden property="content" styleId="content" />
          <script id="content_ue" name="content_ue" type="text/plain" style="width:100%;height:200px;">${af.map.content}</script>
          <div style="color:rgb(241, 42, 34)">图片宽度建议：800(px)，高度根据图片对应比例设定，如果宽度大于800(px)请用图片处理工具处理后再上传，图片大小不超过2M。</div></td>
      </tr>
      <c:if test="${not empty af.map.pub_date}">
        <tr>
          <td class="title_item">发布时间：</td>
          <td><fmt:formatDate value="${af.map.pub_date}" pattern="yyyy-MM-dd" /></td>
        </tr>
      </c:if>
      <c:if test="${not empty af.map.modify_date}">
        <tr>
          <td class="title_item">最后修改时间：</td>
          <td><fmt:formatDate value="${af.map.modify_date}" pattern="yyyy-MM-dd" /></td>
        </tr>
      </c:if>
      <tr>
        <td class="title_item">排序值：</td>
        <td><html-el:text property="order_value" size="4" maxlength="4" />
          值越大，显示越靠前，范围：0-9999</td>
      </tr>
      <tr>
        <td class="title_item">是否是常见购物问题：</td>
        <td><label for="is_common_q_0">
            <html-el:radio styleId="is_common_q_0" property="is_common_q" value="0" />
            否</label>
          <label for="is_common_q_1">
            <html-el:radio styleId="is_common_q_1" property="is_common_q" value="1" />
            是</label></td>
      </tr>
      <tr>
        <td class="title_item">信息状态：</td>
        <td><label for="is_del_0">
            <html-el:radio styleId="is_del_0" property="is_del" value="0" />
            未删除</label>
          <label for="is_del_1">
            <html-el:radio styleId="is_del_1" property="is_del" value="1" />
            已删除</label></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><html-el:submit styleId="save" property="save" value=" 保 存 " styleClass="bgButton" ></html-el:submit>
          &nbsp;
          <input type="reset" name="reset" value=" 重 填 " onclick="this.form.reset();" class="bgButton" />
          &nbsp;
          <input type="button" name="return" value=" 返回 " class="bgButton"  onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script> 
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">//<![CDATA[
// var editor = UE.getEditor('content_ue', {
//     toolbars: UEDITOR_CONFIG.toolbarsmin
// });
var editor = UE.getEditor('content_ue');
editor.ready(function() {editor.on('showmessage', function(type, m){if (m['content'] == '本地保存成功') {return true;}});});
$(document).ready(function(){
	
	$("input[type='text'][readonly],textarea[readonly]").css("color","#999");
	$("input[type='text'],input[type='password']").css("border","1px solid #ccc");
    $("input[type='text'],textarea").not("[readonly]").focus(function(){
    	$(this).addClass("row_focus");
    }).blur(function(){
    	$(this).removeClass("row_focus");
    });
	$("textarea").each(function(){
		this.onpropertychange = function () {
			if (this.scrollHeight > 16) this.style.posHeight = this.scrollHeight;
		};
	});	
	
	$("input[name='title']"    ).attr("dataType" , "Require").attr("msg" , "标题没有填写！");
	$("input[name='order_value']").focus(setOnlyNum);

});


var f = document.forms[0];

f.onsubmit = function(){
	if(Validator.Validate(f, 3)){
		$("#content").val(editor.getContent()); 
		f.submit();
	}
	return false;
}

function setOnlyNum() {
	$(this).css("ime-mode", "disabled").attr("t_value", "").attr("o_value", "").bind("dragenter",function(){
		return false;
	}).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = "0";
	});
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>