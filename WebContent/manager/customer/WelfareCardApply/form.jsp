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
  <html-el:form action="/customer/WelfareCardApply.do"  enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <tr>
       <th colspan="4">福利卡信息</th>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="88%">
          <html-el:text property="title" styleId="title" maxlength="255" style="width:280px" styleClass="webinput"/>
        </td>
      </tr>
      
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>额度：</td>
        <td colspan="2" width="88%">
          <html-el:text property="card_amount" styleId="card_amount" maxlength="12" style="width:280px" styleClass="webinput"/>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>数量：</td>
        <td colspan="2" width="88%">
          <html-el:text property="card_count" styleId="card_count" maxlength="8" style="width:280px" styleClass="webinput"/>
        </td>
      </tr>
      <tr id="comm_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>选择县域：</td>
        <td colspan="2" width="88%">
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left"  id="append_table">
                <tr>
                  <th nowrap="nowrap" colspan="3"><b><a class="butbase" onclick="getService();"><span class="icon-search">选择</span></a></b></th>
                </tr>
                <tr id="comm_list_tr">
                	<th nowrap="nowrap" width="40%">县域名称</th>
                	<th nowrap="nowrap" width="50%">所属区域</th>
                	<th nowrap="nowrap" width="10%">操作</th>
                </tr>
                <c:forEach items="${list}" var="cur">
	                 <tr class="html">
		    			 <input type="hidden" name="service_id" id="service_id" value="${cur.service_id}" />
		    			 <input type="hidden" name="service_name" id="service_name" value="${cur.service_name}" />
		    			 <input type="hidden" name="p_index" id="p_index" value="${cur.p_index}" />
		    			 <input type="hidden" name="p_name" id="p_name" value="${cur.p_name}" />
		    			 <td align="center">${cur.service_name}</td>
		    			 <td align="center">${cur.p_name}</td>
		    			 <td align="center">
		    			 	<b>
			    			 	<a class="butbase" onclick="delComm_info(this,${cur.id});">
			    			 		<span class="icon-configure">删除</span>
		    			 		</a>
		    			 	</b>
		    			 </td>
	    			 </tr>
                </c:forEach>
          </table>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否需要实体卡：</td>
        <td colspan="2" width="88%">
          <html-el:select property="is_entity" styleId="is_entity">
            <html-el:option value="1">是</html-el:option>
            <html-el:option value="0">否</html-el:option>
          </html-el:select>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>有效期开始时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.start_date}" pattern="yyyy-MM-dd" var="_start_date" />
          <html-el:text property="start_date" styleId="start_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_start_date}" />
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>优惠卷结束时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.end_date}" pattern="yyyy-MM-dd" var="_end_date" />
          <html-el:text property="end_date" styleId="end_date" size="12" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker()" style="cursor:pointer;" value="${_end_date}" />
        </td>
      </tr>
      <tr id="addr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>收货地址：</td>
        <td colspan="2" width="88%">
          <html-el:text property="rel_address" styleId="rel_address" maxlength="255" style="width:280px" styleClass="webinput"/>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>联系人：</td>
        <td colspan="2" width="88%">
          <html-el:text property="rel_name" styleId="rel_name" maxlength="64" style="width:280px" styleClass="webinput"/>
        </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>联系电话：</td>
        <td colspan="2" width="88%">
          <html-el:text property="rel_mobile" styleId="rel_mobile" maxlength="32" style="width:280px" styleClass="webinput"/>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00" id="span_main_pic">*</span>支付凭证：</td>
        <td colspan="2"><c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
          <c:if test="${not empty af.map.main_pic}">
            <c:set var="img" value=" ${ctx}/${af.map.main_pic}@s400x400" />
          </c:if>
          <img src="${img}" height="80" id="main_pic_img" />
          <html-el:hidden property="main_pic" styleId="main_pic" />
          <div class="files-warp" id="main_pic_warp">
            <div class="btn-files"> <span>上传凭证</span>
              <input id="main_pic_file" type="file" name="main_pic_file" />
            </div>
            <div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
          </div>
         </td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item">备注：</td>
        <td colspan="2" width="88%"><html-el:text property="remark" styleId="remark" maxlength="60" style="width:280px" styleClass="webinput" /></td>
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
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	var btn_name = "上传凭证";
	if ("" != "${af.map.main_pic}") {
		btn_name = "重新上传";
	}
	upload("main_pic", "image", btn_name, "${ctx}");
	
	$("#title").attr("datatype", "Require").attr("msg", "请填写标题");	
	$("#card_amount").attr("datatype", "Require").attr("msg", "请填写额度");	
	$("#card_count").attr("datatype", "Number").attr("msg", "请填写数量！");
	$("#start_date").attr("datatype", "Require").attr("msg", "请填写有效期开始时间！");
	$("#end_date").attr("datatype", "Require").attr("msg", "请填写有效期结束时间！");
	$("#rel_address").attr("datatype", "Require").attr("msg", "请填写收货地址！");
	$("#rel_name").attr("datatype", "Require").attr("msg", "请填写联系人！");
	$("#rel_mobile").attr("datatype", "Mobile").attr("msg", "请填写联系电话！");
	$("#main_pic").attr("datatype", "Require").attr("msg", "请上传支付凭证！");

	
	$("#is_entity").change(function(){
		if($(this).val() == 0){//虚拟卡无需填写收货地址
			$("#rel_address").removeAttr("datatype", "Require");
			$("#addr").hide();
			
		}else{
			$("#rel_address").attr("datatype", "Require").attr("msg", "请填写收货地址！");
			$("#addr").show();
		}
	})
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
			if(Validator.Validate(f, 3)){
				if($("#end_date").val()<=$("#start_date").val()){
					alert("有效期结束时间必须大于优惠卷开始时间");
					return false;
				}
				 var service_ids = document.getElementsByName("service_id");
				 console.log(service_ids);
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


function getService() {
	var service_ids=[];
	$("input[name='service_id']").each(function(){
		service_ids.push($(this).val());
	});
	var url = "${ctx}/manager/customer/WelfareCardApply.do?method=chooseService&service_ids="+service_ids;
	$.dialog({
		title:  "选择县域",
		width:  "80%",
		height: "90%",
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
};

function returnVal(service_ids,service_names,p_indexs,full_names){
    if(null!=service_ids&&''!=service_ids){
   	 $(".html").empty();
   	 
   	 for(var i=0;i<service_ids.length;i++){
		 var html = "";	
		 html = '<tr class="html">';
		 html += '<input type="hidden" name="service_id" value="'+service_ids[i]+'" />';
		 html += '<input type="hidden" name="service_name" value="'+service_names[i]+'" />';
		 html += '<input type="hidden" name="p_name" value="'+full_names[i]+'" />';
		 html += '<input type="hidden" name="p_index" value="'+p_indexs[i]+'" />';
		 html += '<td align="center">'+service_names[i]+'</td>';
		 html += '<td align="center">'+full_names[i]+'</td>';
		 html += '<td align="center"><b><a class="butbase" onclick="delService(this);"><span class="icon-configure">删除</span></a></b></td>';
		 html += '</tr>';
		 $("#comm_list_tr").last().after(html);
   	 }
    }
}

function delService(obj){
	 $(obj).parent().parent().parent().remove();
}
//]]></script>
</body>
</html>
