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
  <html-el:form action="/customer/CommWelfareApply.do"  enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="comm_id" styleId="comm_id"  value="${af.map.comm_id}"/>
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <tr id="comm_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>选择县域：</td>
        <td colspan="2" width="88%">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left"  id="append_table">
                <tr>
                  <th nowrap="nowrap" colspan="3"><b><a class="butbase" onclick="getServiceCenter();"><span class="icon-search">选择</span></a></b></th>
                </tr>
                <tr id="comm_list_trs">
                	<th nowrap="nowrap" width="40%">县域名称</th>
                	<th nowrap="nowrap" width="50%">所属区域</th>
                	<th nowrap="nowrap" width="10%">操作</th>
                </tr>
               
          </table>
        </td>
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
                 <tr>
			        <td colspan="3" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
			          &nbsp;
			          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
			     </tr>
          </table>
        </td>
      </tr>
      
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

	var f = document.forms[0];
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
			 
			 var comm_ids = document.getElementsByName("comm_ids");
			 if(null == comm_ids || comm_ids.length == 0){
				 alert("请至少选择一个商品！");
					return false;
			 }
			 
			 var service_ids = document.getElementsByName("service_ids");
			 if(null == service_ids || service_ids.length == 0){
				 alert("请至少选择一个县域！");
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
	var url = "${ctx}/manager/customer/CommWelfareApply.do?method=chooseCommInfo&dir=customer&ajax=selectCommType3&own_entp_id=${af.map.own_entp_id}&yhq_id=${af.map.id}&t=" + new Date().getTime();
	$.dialog({
		title:  "选择商品",
		width:  1000,
		height: 650,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};

function getServiceCenter() {
	var service_ids=[];
	$("input[name='service_ids']").each(function(){
		service_ids.push($(this).val());
	});
	var url = "${ctx}/manager/customer/CommWelfareApply.do?method=chooseService&service_ids="+service_ids;
	$.dialog({
		title:  "选择县域",
		width:  "80%",
		height: "90%",
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};

function returnVals(service_ids,service_names,p_indexs,full_names){
    if(null!=service_ids&&''!=service_ids){
   	 $(".htmls").empty();
   	 
   	 for(var i=0;i<service_ids.length;i++){
		 var html = "";	
		 html = '<tr class="htmls">';
		 html += '<input type="hidden" name="service_ids" value="'+service_ids[i]+'" />';
		 html += '<input type="hidden" name="service_names" value="'+service_names[i]+'" />';
		 html += '<input type="hidden" name="p_indexs" value="'+p_indexs[i]+'" />';
		 html += '<td align="center">'+service_names[i]+'</td>';
		 html += '<td align="center">'+full_names[i]+'</td>';
		 html += '<td align="center"><b><a class="butbase" onclick="delService(this,'+service_ids[i]+');"><span class="icon-configure">删除</span></a></b></td>';
		 html += '</tr>';
		 $("#comm_list_trs").last().after(html);
   	 }
    }
}

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
   			html += '<input type="hidden" name="comm_names" value="'+name_array[i]+'" />';
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


function delService(obj){
	 $(obj).parent().parent().parent().remove();
}

function delComm_info(obj,id){
	 $(obj).parent().parent().parent().remove();
 	 var str = $("#temp_comm_ids").val().replace(','+id+',',',');
	 $("#temp_comm_ids").val(str);
}
//]]></script>
</body>
</html>
