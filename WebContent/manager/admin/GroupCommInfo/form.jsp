<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/commons/swfupload/style/default.css" type="text/css" />
<style type="text/css">
.file_hidden_class {
	height:24px;
	border-top:1px #a8a8a8 solid;
	line-height:24px;
	padding-left:6px;
	border-left:1px #a8a8a8 solid;
	border-right:1px #e6e6e6 solid;
	border-bottom:1px #e6e6e6 solid;
}
ul{margin: 0;padding: 0;}
</style>
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
      <html-el:form action="/admin/GroupCommInfo.do"  enctype="multipart/form-data">
        <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <html-el:hidden property="audit_state" styleId="audit_state" value="0"/>
    <html-el:hidden property="own_entp_id" styleId="own_entp_id" value="2" />
    <html-el:hidden property="audit_state_old" styleId="audit_state_old" value="${af.map.audit_state}"/>
    <html-el:hidden property="id" styleId="id" />
        <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="tableClass">
        <c:if test="${(not empty af.map.audit_state)}">
        <tr>
          <td nowrap="nowrap" class="title_item">审核状态：</td>
          <td colspan="2">
          <c:if test="${af.map.audit_state eq -1}"> <span style="color:#F00;">
              <c:out value="审核不通过"/>
              </span> </c:if>
            <c:if test="${af.map.audit_state eq 0}">
              <c:out value="待审核"/>
            </c:if>
            <c:if test="${af.map.audit_state eq 1}"> <span style="color:#060;">
              <c:out value="审核通过"/>
              </span> </c:if>
          </td>
        </tr>
      </c:if>
        <c:if test="${(not empty af.map.audit_desc)}">
          <tr>
            <td nowrap="nowrap" class="title_item">审核说明：</td>
            <td colspan="2">${fn:escapeXml(af.map.audit_desc)}</td>
          </tr>
        </c:if>
<!--         <tr> -->
<!--             <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>企业：</td> -->
<%--             <td colspan="2" width="88%"><html-el:text property="entp_name" styleId="entp_name" maxlength="125" style="width:280px" styleClass="webinput" readonly="true"/> --%>
<!--               &nbsp; -->
<!--           	 <a class="butbase" onclick="openEntpChild();"><span class="icon-search">选择</span></a> -->
<!--           </td> -->
          </tr>
          <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>选择商品：</td>
	        <td colspan="2" width="88%">
	        <html-el:hidden property="comm_id" styleId="comm_id" />
	        <html-el:text property="comm_name" styleId="comm_name" maxlength="128" style="width:280px;cursor:pointer;" styleClass="webinput" onclick="chooseCommInfo('${af.map.own_entp_id}')" readonly="true"/>
	        &nbsp;<a class="butbase" onclick="chooseCommInfo('${af.map.own_entp_id}')" ><span class="icon-search">选择商品</span></a>
	        </td>
      </tr>
<!--       <tr> -->
<!--         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>选择商品套餐：</td> -->
<!-- 	        <td colspan="2" width="88%"> -->
<%-- 	        <html-el:hidden property="comm_tczh_id" styleId="comm_tczh_id" /> --%>
<%-- 	        <html-el:text property="comm_tczh_name" styleId="comm_tczh_name" maxlength="128" style="width:280px;cursor:pointer;" styleClass="webinput" onclick="chooseCommTczhInfo()" readonly="true"/> --%>
<!-- 	        &nbsp;<a class="butbase" onclick="chooseCommTczhInfo()" ><span class="icon-search">选择商品套餐</span></a> -->
<!-- 	        </td> -->
<!--       </tr> -->
      <tr id="tr_pdType">
          <td class="title_item"><font color="red">*</font>对应类别：</td>
          <td >
          <div>
          <textarea name="comm_tczh_names"  id="comm_tczh_names" cols="38" rows="5" readonly="readonly" style="width:300px;">${af.map.comm_tczh_name}</textarea>
           <html-el:hidden property="comm_tczh_ids" styleId="comm_tczh_ids" value="${af.map.comm_tczh_ids}"/>
           <html-el:hidden property="tg_tczh_ids" styleId="tg_tczh_ids" value="${af.map.tg_tczh_ids}"/>
          &nbsp;
          <a class="butbase" style="cursor:pointer;" onclick="getSygzNames();" ><span class="icon-search">选择</span></a>
          </div>
          </td>
        </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动名称：</td>
        <td colspan="2" width="88%"><html-el:text property="comm_title" styleId="comm_title" maxlength="125" style="width:280px" styleClass="webinput"/></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动副标题：</td>
        <td colspan="2" width="88%"><html-el:text property="comm_sub_title" styleId="comm_sub_title" maxlength="125" style="width:280px" styleClass="webinput"/></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>原价格：</td>
        <td colspan="2" width="88%"><html-el:text property="no_dist_price" styleId="no_dist_price" maxlength="10" style="width:120px" styleClass="webinput" />&nbsp;元</td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动价格：</td>
        <td colspan="2" width="88%"><html-el:text property="prom_price" styleId="prom_price" maxlength="10" style="width:120px" styleClass="webinput" />&nbsp;元</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>开始时间：</td>
        <td  colspan="2"><fmt:formatDate value="${af.map.start_time}" pattern="yyyy-MM-dd HH:mm:ss" var="_start_time"/>
          <html-el:text property="start_time" styleId="start_time" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor:pointer;width:120px" value="${_start_time}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>结束时间：</td>
        <td  colspan="2"><fmt:formatDate value="${af.map.end_time}" pattern="yyyy-MM-dd HH:mm:ss" var="_end_time" />
          <html-el:text property="end_time" styleId="end_time" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="cursor:pointer;width:120px" value="${_end_time}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否开启限购：</td>
        <td  colspan="2">
          <html-el:select property="is_buyer_limit" styleId="is_buyer_limit">
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select>
        </td>
      </tr>
            <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>销售数量：</td>
        <td colspan="2">
         <html-el:text property="prom_sale_acount" styleId="prom_sale_acount" size="4" maxlength="4" styleClass="webinput" style="width:120px" />
        </td>
      </tr>
<!--       <tr id="tr_buyer_limit_num" style="display:none;"> -->
<!--         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>每人限购数量：</td> -->
<!--         <td  colspan="2"> -->
<%--          <html-el:text property="buyer_limit_num" styleId="buyer_limit_num" size="4" maxlength="4" styleClass="webinput" style="width:120px" /> --%>
<!--         </td> -->
<!--       </tr> -->
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>活动数量：</td>
        <td colspan="2">
         <html-el:text property="prom_inventory" styleId="prom_inventory" size="4" maxlength="4" styleClass="webinput" style="width:120px" />
        </td>
      </tr>
        <tr>
        <td colspan="3" style="text-align:center"><html-el:submit property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
        </table>
      </html-el:form>
    <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
// 	<c:if test="${af.map.is_buyer_limit eq 1}">
// 	  $("#tr_buyer_limit_num").show();
// 	  $("#buyer_limit_num").attr("dataType", "Integer").attr("msg", "请填写每人限购数量！");
// 	</c:if>
// <c:if test="${not empty af.map.comm_tczh_name}">
// var comm_tczh_name = ${af.map.comm_tczh_name};
// alert(comm_tczh_name)
// $("input[name='comm_tczh_name']").val(comm_tczh_name);
// </c:if>
	
	
	$("#comm_name").attr("datatype", "Require").attr("msg", "请选择商品！");
	$("#comm_tczh_name").attr("datatype", "Require").attr("msg", "请选择商品套餐！");
	$("#comm_title").attr("datatype", "Require").attr("msg", "请填写活动名称！");
	$("#comm_sub_title").attr("datatype", "Require").attr("msg", "请填写活动副标题！");	
	$("#prom_price").attr("datatype", "Currency").attr("msg", "请填写活动价格！");	
	$("#start_time").attr("dataType", "Require").attr("msg", "请选择结束时间！");
	$("#end_time").attr("dataType", "Require").attr("msg", "请选择结束时间！");
	$("#is_buyer_limit").attr("dataType", "Require").attr("msg", "请选择是否开启限购！");
	$("#prom_inventory").attr("dataType", "Integer").attr("msg", "请填写活动数量！");
	
	var f = document.forms[0];
	
	$("#order_value").focus(setOnlyNum2);
	$("#buyer_limit_num").focus(setOnlyNum2);
	$("#prom_inventory").focus(setOnlyNum2);
	
	
// 	$("#is_buyer_limit").change(function(){
// 		var thisVal = $(this).val();
// 		if(null != thisVal && "" != thisVal){
// 			if(thisVal == 1){
// 				$("#tr_buyer_limit_num").show();
// 				$("#buyer_limit_num").attr("dataType", "Integer").attr("msg", "请填写每人限购数量！");
// 			}else{
// 				$("#tr_buyer_limit_num").hide();
// 				$("#buyer_limit_num").removeAttr("dataType");
// 			}
// 		}
// 	});
	

	f.onsubmit= function(){
		if(Validator.Validate(f, 1)){
			
			 if($("#start_time").val()>=$("#end_time").val()){
					alert("结束时间必须大于开始时间");
					return false;
			 }
			 
			 if($("#audit_state_old").val() == 1){
				var submit2 = function(v, h, f) {
					if (v == "ok") {
						$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
				        $("#btn_reset").attr("disabled", "true");
				        $("#btn_back").attr("disabled", "true");
						document.forms[0].submit();
					}
				};
				$.jBox.confirm("商品修改后，需重新审核！", "确定提示", submit2);
			}else{
				 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		         $("#btn_reset").attr("disabled", "true");
		         $("#btn_back").attr("disabled", "true");
				f.submit();
			}
		}
		return false;
	}
});
function getSygzNames() {
	var comm_id = $("#comm_id").val();
	if(null == comm_id || comm_id == ""){
		alert("请先选择商品!");
		return false;
	}
	
	var comm_tczh_ids  =$("#comm_tczh_id").val();
	var tg_tczh_ids  =$("#tg_tczh_ids").val();
	var comm_tczh_names  =$("#comm_tczh_names").val();
	var url = "${ctx}/BaseCsAjax.do?method=getCommInfoTcList&comm_id="+ comm_id +"&tg_tczh_ids="+tg_tczh_ids+"&comm_tczh_ids="+comm_tczh_ids+"&comm_tczh_names="+comm_tczh_names+"t=" + new Date().getTime();
	$.dialog({
		title:  "选择对应类别",
		width:  770,
		height: 650,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:"9999",
		content:"url:"+ encodeURI(url)
	});
}  


function chooseCommTczhInfo(){
	var comm_id = $("#comm_id").val();
	if(null == comm_id || comm_id == ""){
		alert("请先选择商品!");
		return false;
	}
	
	var url = "${ctx}/BaseCsAjax.do?method=getCommInfoTc&comm_id="+ comm_id +"&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:9999,
		content:"url:"+url
	});
}
function chooseCommInfo(own_entp_id){
	var url = "${ctx}/BaseCsAjax.do?method=chooseCommInfo&ajax=fromGroup&comm_type=5&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:9999,
		content:"url:"+url
	});
}

function getCommLinkInfo(comm_id){
	if(null != comm_id && "" != comm_id){
		$.ajax({
			type: "POST",
			url: "GroupCommInfo.do?method=getCommInfoById&comm_id="+comm_id,
			success: function(data){
				if(data.code == 1){
// 					$("#comm_title").val(data.datas.commInfo.comm_name);
// 					$("#comm_sub_title").val(data.datas.commInfo.sub_title);
				}else{
					alert(data.msg);
				}
			}
		});
	}
}
function openEntpChild(){
	
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin";
	$.dialog({
		title:  "选择企业",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

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

function setOnlyNum2() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+]?\d+(?:\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^$/))this.value=0;this.o_value=this.value};
		if(this.value.length == 0) this.value = "0";
	});
	//this.text.selected;
}

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>