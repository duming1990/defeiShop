<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>会员中心 - ${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body style="height:2500px;">
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  
  <html-el:form action="/customer/CommWelfareApply.do" styleClass="searchForm">
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="commWelfareApply_id" value="${commWelfareApply_id}" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
    <tr>
	    <th colspan="4">
	   		修改福利区域商品
	    </th>
    </tr>
    <tr>
    <td width="14%" nowrap="nowrap" class="title_item">福利区域名称：</td>
    <td>
    	${serviceInfo.servicecenter_name}
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
                <c:forEach items="${commList}" var="cur">
                <tr class="html">
    			 <input type="hidden" name="comm_ids" id="comm_ids" value="${cur.id}" />
    			<input type="hidden" name="comm_names" id="comm_names" value="${cur.comm_name}" />
    			 <td align="center">${cur.comm_name}</td>
    			 <td align="center">${cur.map.price}</td>
    			 <td align="center"></td>
    			 </tr>
    			 
                </c:forEach> 
                <tr>
			        <td colspan="3" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
			          &nbsp;
			          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
			          &nbsp;
			          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
			     </tr>
          </table>
        </td>
      </tr>
  </table>
  </html-el:form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/tip/jquery.quicktip.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
	
	
$(document).ready(function(){
	var f = document.forms[0];
	$(".qtip").quicktip();
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
			 
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

function viewAuditDesc(audit_desc){
	$.dialog({
		title:  "审核不通过原因",
		width:  250,
		height: 100,
        lock:true ,
		content:audit_desc
	});
}
function fapiao(id,is_tigong){
	if(is_tigong == 1){
		var fapiao = 0;
	}else{
		var fapiao = 1;
	}
	 var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("修改中！", 'loading');
			window.setTimeout(function () {
			  $.ajax({
					type: "POST" , 
					url: "${ctx}/manager/customer/CommInfo.do" , 
					data:"method=fapiao&comm_id="+id+"&is_fapiao="+fapiao,
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
			        success: function (data) {
						if (data.code == 0) {
							$.jBox.tip(data.msg, 'error');
						} else {
							$.jBox.tip("修改成功！", 'success');
							window.setTimeout(function () {
							 window.location.reload(); 
							}, 1000);
						}
			        }
			 });
		   }, 1000);	 
		}
	};
	$.jBox.confirm("您确定要修改吗?", "确定提示", submit2);
}

function ziti(id,is_ziti){
	if(is_ziti == 1){
		var ziti = 0;
	}else{
		var ziti = 1;
	}
	 var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("修改中！", 'loading');
			window.setTimeout(function () {
			 $.ajax({
					type: "POST" , 
					url: "${ctx}/manager/customer/CommInfo.do" , 
					data:"method=ziti&comm_id="+id+"&is_ziti="+ziti,
					dataType: "json", 
			        async: true, 
			        error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
			        success: function (data) {
						if (data.code == 0) {
							$.jBox.tip(data.msg, 'error');
						} else {
							$.jBox.tip("修改成功！", 'success');
							 window.setTimeout(function () {
							window.location.reload(); 
							}, 1000);
						}
			        }
				});
			}, 1000);
		}
	};
	$.jBox.confirm("您确定要修改吗?", "确定提示", submit2);
}
function getCommInfo() {
	var url = "${ctx}/manager/customer/CommWelfareApply.do?method=chooseCommInfo&dir=customer&ajax=selectCommType3&own_entp_id=${af.map.own_entp_id}&yhq_id=${af.map.id}&apply_id=${commWelfareApply_id}&t=" + new Date().getTime();
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

//]]></script>
</body>
</html>
