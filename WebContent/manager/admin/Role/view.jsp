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
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">用户角色基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item">角色名称：</td>
        <td width="85%">${fn:escapeXml(af.map.role_name)}</td>
      </tr>
      <tr>
        <td class="title_item">排序值：</td>
        <td>${af.map.order_value}</td>
      </tr>
      <tr>
        <td class="title_item">成功授权用户</td>
        <td class="bg_tip">
        你已成功授权<span title="点击查看" class="label label-danger" style="cursor: pointer; margin: 0 5px" onclick="getHasShouquanUserList('${af.map.id}')">${hasShouquanCount}</span>个用户
<!--         &nbsp;<a class="butbase" onclick="getUserInfo()"><span class="icon-add">添加授权</span></a> -->
        </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align:center">
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript"><!--//<![CDATA[
$(document).ready(function(){

});

function getHasShouquanUserList(id){
	var url = "Role.do?method=getHasShouquanUserList&role_id=" + id;
	$.dialog({
		title:  "查看已授权用户",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

function getUserInfo(){
	var url = "${ctx}/BaseCsAjax.do?method=getUserInfo&type=multiple&dosomeThing=saveRoleUser&userTypeNotIn=2";
	$.dialog({
		title:  "选择用户",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

function saveRoleUser(){
	var user_ids = $("#user_ids").val();
	if(null != user_ids && '' != user_ids){
		$.post("?method=saveRoleUser",{user_ids:user_ids,id:'${af.map.id}'},function(data){
		     if(data.ret == 1){
		    	 $.jBox.tip(data.msg, "success",2000);
		    	 window.setTimeout(function () { 
		    	 	location.reload();
		    	 }, 1000);
		     }else{
		    	 $.jBox.tip(data.msg, "error",2000);
		     }
	 });
	}
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
