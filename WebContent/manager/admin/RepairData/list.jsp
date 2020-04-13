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
  <%@ include file="/commons/pages/messages.jsp" %>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="2">数据修复</th>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">1、修复合伙人服务数量：</td>
      <td width="85%"><a onclick="repairData('updateServiceCenterFuwuCount');">执行</a></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">2、修复用户权限问题（合伙人以及企业）：</td>
      <td width="85%"><a onclick="repairData('updateUserQuanxian');">执行</a></td>
    </tr>
     <tr>
      <td width="15%" nowrap="nowrap" class="title_item">3、获取省市JSON格式数据（看控制台输出）：</td>
      <td width="85%"><a onclick="repairData('getBaseProJson');">执行</a></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">4、修复一键复销导致的bug（只能未确认收货才能使用并且先需要把orderInfo表设置好）：</td>
      <td width="85%">
      <input type="text" name="order_id" id="order_id" size="10" maxlength="10" />
      <a onclick="repairData('updateFuxiaoAll');">执行</a></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">5、修复众惠老版本切换新版本用户数据问题（先需要把userInfoTemp表设置好）：</td>
      <td width="85%">
      <a onclick="repairData('updateZhongHuiUserInfoData');">执行</a></td>
    </tr>
    <tr>
      <td colspan="2" style="text-align:center">
        <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
</div>

<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[

function repairData(method){
	
	var order_id = $("#order_id").val();
	if(method == "updateFuxiaoAll"){
		if(order_id == ""){
			$.jBox.alert("请填写订单ID","提示");
			return false;
		}
	}
	
	
	var submit = function (v, h, f) {
	    if (v == true) {
	     $.jBox.tip("执行中...", 'loading');
	     window.setTimeout(function () { 
	     $.post("?method=" + method,{order_id : order_id},function(data){
	     $.jBox.tip(data, "success");
	     });
	     }, 1000);
	    } 
	    return true;
	};
	myConfirm("操作有可能会导致数据错乱，请谨慎操作，如需操作，请于管理员联系，你确定要执行该操作吗？",submit);
 }
function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
