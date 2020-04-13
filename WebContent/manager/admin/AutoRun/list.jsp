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
      <th colspan="2">定时任务测试</th>
    </tr>
    <!-- <tr>
      <td width="15%" nowrap="nowrap" class="title_item">1、交易完成发放奖励定时任务：</td>
      <td width="85%">
      交易完成日期：<input type="text" name="finish_date" id="finish_date" size="10" maxlength="10" readonly="readonly" onclick="WdatePicker()" />
      <button class="bgButton" onclick="AutoRun('autoCloseOrder',this);">执行</button></td>
    </tr> -->
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">2、分红定时任务：</td>
      <td width="85%">
      分红日期：<input type="text" name="tianfan_date" id="tianfan_date" size="10" maxlength="10" readonly="readonly" onclick="WdatePicker()" />
      &nbsp;&nbsp;<button class="bgButton" onclick="AutoRun('autoTianFanByUser',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">3、更新修改后的企业返现规则，使得新规则生效：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoEntpFanXianRule',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">4、更新店铺和商品状态：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoEntpAndCommState',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">5、自动确认收货更新：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoConfirmReceipt',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">6、自动取消订单更新：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoOrderCancel',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">7、用户积分考核：</td>
      <td width="85%">
      考核会员：<input type="text" name="user_name" id="user_name" size="20" />
      &nbsp;&nbsp;<button class="bgButton" onclick="AutoRun('autoUserScoreCheck',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">8、订单失效短信提醒：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoOrderFailureMsgRemind',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">9、同步京东商品：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoSyncJdProduct',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">10、自动同步创建京东订单：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoSyncUploadJdOrder',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">11、自动同步确认京东订单：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoSyncConfirmJdOrder',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">12、自动同步下载更新京东订单：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoSyncDownloadJdOrder',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">13、同步下载京东地区：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoSyncDownloadJdArea',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">14、手工核算奖励：</td>
      <td width="85%">
      订单ID：<input type="text" name="order_id" id="order_id" size="20" />
      &nbsp;&nbsp;<button class="bgButton" onclick="AutoRun('autoReckonRebateAndAid',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">15、付费会员到期：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoUserCartClose',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">16、交易完成：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('modifyAutoCloseOrder',this);">执行</button></td>
    </tr>
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">17、本年已获取扶贫金超出限制金额的贫困户自动从商品扶贫对象中移除：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoRemovePoorFromComm',this);">执行</button></td>
    </tr>
    <!-- 
    <tr>
      <td width="15%" nowrap="nowrap" class="title_item">9、合伙人到期自动取消：</td>
      <td width="85%"><button class="bgButton" onclick="AutoRun('autoServiceCancel',this);">执行</button></td>
    </tr> -->
    <tr>
      <td colspan="2" style="text-align:center">
        <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
                                          
function AutoRun(method,obj){
	$(obj).attr("disabled","true");
	var tianfan_date = $("#tianfan_date").val();
	var finish_date = $("#finish_date").val();
	var user_name = $("#user_name").val();
	var order_id = $("#order_id").val();
	if(method == "autoTianFanByUser"){
		if(tianfan_date == ""){
			$.jBox.alert("分红日期必须选择","提示");
			return false;
		}
		var this_date = tianfan_date;
	}
	
	if(method == "autoCloseOrder"){
		if(finish_date == ""){
			$.jBox.alert("交易完成日期必须选择","提示");
			return false;
		}
		var this_date = finish_date;
	}
	
	if(method == "autoReckonRebateAndAid"){
		if(order_id == ""){
			$.jBox.alert("订单ID必须填写","提示");
			return false;
		}
	}
	
	var submit = function (v, h, f) {
	    if (v == true) {
	     $.jBox.tip("执行中...", 'loading');
	     window.setTimeout(function () { 
	     $.post("?method=" + method,{this_date : this_date, user_name : user_name,order_id : order_id},function(data){
	     $.jBox.tip(data, "success");
	     $(obj).removeAttr("disabled");
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
