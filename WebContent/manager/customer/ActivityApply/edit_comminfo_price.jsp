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
  <html-el:form action="/customer/ActivityApply.do" styleClass="saveForm">
    <html-el:hidden property="method" value="saveActivityComminfo" />
    <html-el:hidden property="activity_id" styleId="activity_id"/>
    <html-el:hidden property="activityApply_id" styleId="activityApply_id"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr class="tite2">
           <th width="8%" align="center" nowrap="nowrap">序号</th>
           <th align="center">商品名称</th>
           <th align="center">活动价格</th>
           <th align="center">库存数量</th>
           <th align="center">是否扶贫</th>
           <th align="center" nowrap="nowrap">扶贫比例</th>
           <th align="center">选择扶贫对象</th>
           
       </tr>
       <c:forEach var="cur" items="${activityApplyCommList}" varStatus="vs" >
           <tr>
               <td align="center">${vs.count}</td>
               <td align="left"><c:out value="${fnx:abbreviate(cur.comm_name, 60, '...')}" /></td>
               <html-el:hidden property="commtczhprice_id" styleId="commtczhprice_id" value="${cur.map.commtczhprice_id}"/>
               <td align="center"><html-el:text property="comm_price" styleId="comm_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.map.comm_price}" onfocus='javascript:setOnlyNum3(this);'/>&nbsp;元</td>
               <td align="center"><html-el:text property="inventory" styleId="inventory" styleClass="webinput"  size="10" maxlength="10" value="${cur.map.inventory}" onfocus='javascript:setOnlyNum3(this);'/></td>
               <td align="center">
                <html-el:select property="is_aid" styleId="is_aid${vs.count}" styleClass="webinput" value="${cur.map.is_aid}" onchange="setIs_aid(${cur.map.is_aid},${vs.count});">
<%--                 			   <html-el:option value="">请选择...</html-el:option> --%>
               				  <html-el:option value="0" >否</html-el:option>
				              <html-el:option value="1" >是</html-el:option>
				              
               </html-el:select>
               </td>
               
                <c:set var="bianliang" value="block"  />
               
               <c:if test="${cur.map.is_aid eq 0}">
               <c:set var="bianliang" value="none"  />
               </c:if>
               <td align="center" >
               <p id="aid_scale_tr${vs.count}" style="display:${bianliang}"><html-el:text property="aid_scale" styleId="aid_scale" styleClass="webinput"  size="10" maxlength="10" value="${cur.map.aid_scale}" onfocus='javascript:setOnlyNum3(this);'/>%</p></td>
               <td align="center">
<%--                <a class="butbase" onclick="getPoorInfo(${cur.comm_id});"><span id="poor_tr" class="icon-search">选择</span></a> --%>
              <p id="poor_tr${vs.count}" style="display:${bianliang}">
              <html-el:button property="poor_tr" value="选择" styleClass="bgButton" styleId="poor_tr" onclick="getPoorInfo(${cur.comm_id});" />
              </p>
               </td>

           </tr>
       </c:forEach>
       <tr>
        <td colspan="7" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" /></td>
<!--           &nbsp; -->
<%--           <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" /> --%>
<!--           &nbsp; -->
<%--           <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td> --%>
      </tr>
    </table>
  </html-el:form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function() {
	var api = frameElement.api, W = api.opener;
  $("#comm_price").attr("datatype", "Require").attr("msg", "请填写活动价格！");
  $("#inventory").attr("datatype", "Require").attr("msg", "请填写库存数量！");
  $("#aid_scale").attr("datatype", "Require").attr("msg", "请填写抚贫比例！");
  // 提交
  $("#btn_submit").click(function() {
	  
    if(Validator.Validate(f, 3)) {
      
      $.ajax({
        type: 'POST',
        url: 'ActivityApply.do?method=saveComminfoPrice',
        data: $(".saveForm").serialize(),
        traditional: true,
        success: function(data) {
          if(data.code == "1") {
            $.jBox.tip("修改成功", "success");
            window.setTimeout(function() {
            	 W.reloadJsp();
				 api.close();
            }, 1000);
          } else {
            $.jBox.tip(data.msg, "error", { timeout: 500 });
          }
        },
      });
    }
    
  //通过判断是否扶贫控制扶贫比例的显示
// 	if("1" != $("#is_aid").val()){
// 		$("#aid_scale_tr").hide();
// 		$("#poor_tr").hide();
// 	}else {
// 		$("#aid_scale").attr("dataType", "Double").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的扶贫比例！");
// 		$("#pet_name").attr("dataType", "Require").attr("msg", "请选择扶贫对象！");
// 	}
// 	$("#is_aid").change(function(){
// 		if("1" == $(this).val()){
// 			$("#aid_scale").attr("dataType", "Double").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的扶贫比例！");
// 			$("#aid_scale_tr").show();
// 			$("#poor_tr").show();
// 		}
// 	});
	
  });
  

});

function getPoorInfo(id){
	var url = "${ctx}/manager/customer/ActivityApply.do?method=getPoorInfo&comm_id="+id;
	var windowapi = frameElement==null?window.top:frameElement.api, W = windowapi==null?window.top:windowapi.opener;//内容页中调用窗口实例对象接口
	var zIndex = W==null?1976:W.$.dialog.setting.zIndex+1;

	$.dialog({
		title:  "扶贫列表",
		width:  1000,
		height: 800,
		zIndex:zIndex,
      	 parent:windowapi,	

        lock:true ,
		content:"url:"+url
	});
}

function setIs_aid(is_aid,i) {
	//通过判断是否扶贫控制扶贫比例的显示
	
	
  var is_aid = parseFloat($("#is_aid" + i).val());
//   var thisDanwei_count = parseInt($("#danwei_count" + thisNum).val());
  
  
	console.info("#is_aid"+i);
	if("1" != is_aid){
		$("#aid_scale_tr"+i).hide();
		$("#poor_tr"+i).hide();
	}
	if("0" != is_aid){
		$("#aid_scale_tr"+i).show();
		$("#poor_tr"+i).show();
	}
	
// 	$("#is_aid"+i).change(function(){
// 		if("1" == $(this).val()){
// 			$("#aid_scale_tr"+i).show();
// 			$("#poor_tr"+i).show();
// 		}else {
// 			$("#aid_scale_tr"+i).hide();
// 			$("#poor_tr"+i).hide();
// 		}
// 	});
	
}


function setOnlyNum3(obj) {
	$(obj).css("ime-mode", "disabled");
	$(obj).attr("t_value", "");
	$(obj).attr("o_value", "");
	$(obj).bind("dragenter",function(){
		return false;
	})
	$(obj).keypress(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value
	}).keyup(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value
	}).blur(function (){
		if(!obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))obj.value=obj.o_value;else{if(obj.value.match(/^\.\d+$/))obj.value=0+obj.value;if(obj.value.match(/^\.$/))obj.value=0;obj.o_value=obj.value}
		if(obj.value.length == 0) obj.value = 0;
	})
}
//]]></script>
</body>
</html>
