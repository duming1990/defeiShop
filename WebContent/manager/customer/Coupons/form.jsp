<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
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
</style>
</head>
<body  style="height:2000px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/Coupons.do"  enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="comm_id" styleId="comm_id"  value="${af.map.comm_id}"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>优惠卷名称：</td>
        <td colspan="2" width="88%">
          <html-el:text property="yhq_name" styleId="yhq_name" maxlength="128" style="width:280px" styleClass="webinput"/>
      </tr>
      <tr id="comm_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>商品选择：</td>
        <td colspan="2" width="88%">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" >
                <tr>
                  <th nowrap="nowrap" colspan="3"><b><a class="butbase" onclick="getCommInfo();"><span class="icon-search">选择</span></a></b></th>
                </tr>
                <tr id="comm_list_tr">
                	<th nowrap="nowrap" width="40%">商品名称</th>
                	<th nowrap="nowrap" width="50%">价格</th>
                	<th nowrap="nowrap" width="10%">操作</th>
                </tr>
                <c:forEach items="${commInfoList}" var="cur">
                <tr class="html">
    			 <input type="hidden" name="comm_ids" id="comm_ids" value="${cur.comm_id}" />
    			 <td align="center">${cur.map.commInfo.comm_name}</td>
    			 <td align="center">${cur.map.price}</td>
    			 <td align="center"><b><a class="butbase" onclick="delComm_info(this,${cur.id});"><span class="icon-configure">删除</span></a></b></td>
    			 </tr>
                </c:forEach>
          </table>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>优惠卷使用规则，满多少钱可用：</td>
        <td colspan="2" width="88%"><html-el:text property="yhq_sytj" styleId="yhq_sytj" maxlength="25" style="width:280px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>优惠卷金额：</td>
        <td colspan="2" width="88%"><html-el:text property="yhq_money" styleId="yhq_money" maxlength="25" style="width:280px" styleClass="webinput" /></td>
      </tr>
      <tr >
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>优惠卷使用时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.yhq_start_date}" pattern="yyyy-MM-dd" var="_yhq_start_date" />
          <html-el:text property="yhq_start_date" styleId="yhq_start_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_yhq_start_date}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>优惠卷结束时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.yhq_end_date}" pattern="yyyy-MM-dd" var="_yhq_end_date" />
          <html-el:text property="yhq_end_date" styleId="yhq_end_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_yhq_end_date}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否限制领取数量：</td>
        <td colspan="2" width="88%"><html-el:select property="is_limited" styleId="is_limited">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="limited_number_tr">
      <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>限制领取数量：</td>
        <td colspan="2" width="88%"><html-el:text property="limited_number" styleId="limited_number" maxlength="25" style="width:280px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td colspan="3" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
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
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/swfupload.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/handlers.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#yhq_name").attr("datatype", "Require").attr("msg", "请填写优惠卷名称！例如：满99使用");	
	$("#yhq_sytj").attr("datatype", "Double").attr("msg", "请填写优惠卷使用规则，满多少可用，且必须是正数！");	
	$("#yhq_money").attr("datatype", "Double").attr("msg", "请填写优惠卷金额且 必须是正数！");	
	$("#yhq_start_date").attr("datatype", "Require").attr("msg", "请填写优惠卷使用时间！");	
	$("#yhq_end_date").attr("datatype", "Require").attr("msg", "请填写优惠卷结束时间！");	
	$("#is_limited").attr("datatype", "Number").attr("msg", "请填写是否限制领取数量！");
	

	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
			 if($("#yhq_end_date").val()<=$("#yhq_start_date").val()){
				alert("优惠卷结束时间必须大于优惠卷开始时间");
				return false;
			}
			 var comm_ids = document.getElementsByName("comm_ids");
			 if(null == comm_ids || comm_ids.length == 0){
				 alert("请至少选择一个商品！");
					return false;
			 }
           $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
           $("#btn_reset").attr("disabled", "true");
           $("#btn_back").attr("disabled", "true");
		f.submit();
		}
	});
	});

if("1" != $("#is_limited").val()){
	$("#limited_number_tr").hide();
}
$("#is_limited").change(function(){
    if("1" == $(this).val()){
        $("#limited_number_tr").show();
    	$("#limited_number").attr("datatype", "Number").attr("msg", "请填写限时领取数量！");
    }else{
    	$("#limited_number_tr").hide();
       	$("#limited_number").remove();
    }
});

function getCommInfo() {
	var url = "${ctx}/manager/customer/Coupons.do?method=chooseCommInfo&dir=customer&ajax=selectCommType3&own_entp_id=${af.map.own_entp_id}&yhq_id=${af.map.id}&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  1000,
		height: 650,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};
function returnVal(ids,names,prices){
	console.info(ids);
	
	var temp_comm_ids = $("#temp_comm_ids").val() || "";
	
	
	 $("input[name='comm_ids").each(function(){
		console.info($(this).val());
		});
	
	console.info("temp_comm_ids:"+temp_comm_ids)
    if(null!=ids&&''!=ids){
   	 var id_array = ids.split(',');
   	 var name_array = names.split(',');
   	 var price_array = prices.split(',');
   	 $(".html").empty();
   	 for(var i=0;i<id_array.length;i++){
   		console.info("id_array[i]:"+id_array[i]);
   		
//    		 if(temp_comm_ids.indexOf(','+id_array[i]+',')==-1){
   			 var html = "";	
   			 html = '<tr class="html">';
   			 html += '<input type="hidden" name="comm_ids" value="'+id_array[i]+'" />';
   			 html += '<td align="center">'+name_array[i]+'</td>';
   			 html += '<td align="center">'+price_array[i]+'</td>';
   			 html += '<td align="center"><b><a class="butbase" onclick="delComm_info(this,'+id_array[i]+');"><span class="icon-configure">删除</span></a></b></td>';
   			 html += '</tr>';
   			 console.info(html)
   			 $("#comm_list_tr").last().after(html);
   			 temp_comm_ids = temp_comm_ids+","+id_array[i]+',';
   			 $("#temp_comm_ids").val(temp_comm_ids);
//    		 }
   	 }
    }
}
function delComm_info(obj,id){
	 $(obj).parent().parent().parent().remove();
 	 var str = $("#temp_comm_ids").val().replace(','+id+',',',');
	 $("#temp_comm_ids").val(str);
}
//]]></script>
</body>
</html>
