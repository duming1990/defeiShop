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
    <html-el:hidden property="method" value="savePoorInfo" />
    <html-el:hidden property="comm_id" styleId="comm_id" value="${af.map.comm_id }"/>
    <html-el:hidden property="activityApply_id" styleId="activityApply_id"/>
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr id="poor_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>扶贫对象：</td>
        <td colspan="2" width="88%">
       	  <html-el:hidden property="temp_poor_ids" styleId="temp_poor_ids" value="${temp_poor_ids}" />
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" id="poor_table">
                <tr>
                  <th nowrap="nowrap" colspan="3"><a class="butbase" onclick="choosePoorInfo();"><span class="icon-search">选择</span></a></th>
                </tr>
                <tr id="poor_list_tr">
                	<th nowrap="nowrap" width="40%">贫困户姓名</th>
                	<th nowrap="nowrap" width="50%">行政区划</th>
                	<th nowrap="nowrap" width="10%">操作</th>
                </tr>
                <c:forEach items="${commInfoPoorsList}" var="cur">
                	<tr>
    			 <input type="hidden" name="poor_ids" value="${cur.poor_id}" />
    			 <td align="center">${cur.map.real_name}</td>
    			 <td align="center">${cur.map.p_name}</td>
    			 <td align="center"><b><a class="butbase" onclick="delPoorInfo(this,${cur.poor_id});"><span class="icon-configure">删除</span></a></b></td>
    			 </tr>
                </c:forEach>
                <td colspan="7" style="text-align:center"><html-el:button property="btn_submit" value="保 存" styleClass="bgButton" styleId="btn_submit" /></td>
          </table>
        </td>
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
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];
$(document).ready(function() {
	var api = frameElement.api, W = api.opener;
  $("#comm_price").attr("datatype", "Require").attr("msg", "请填写活动价格！");
  $("#inventory").attr("datatype", "Require").attr("msg", "请填写库存数量！");
  $("#aid_scale").attr("datatype", "Require").attr("msg", "请填写抚贫比例！");
  // 提交
  $("#btn_submit").click(function() {
	  
	  if("1" == $("#is_aid").val()){
			if($("input[name='poor_ids']").length==0){
				alert("请选择扶贫对象");
				return false;
			}
		}
	  
    if(Validator.Validate(f, 3)) {
    	
    	
    	if("1" == $("#is_aid").val()&&$("input[name^='poor_ids']").length>${baseData1901.pre_number}){
			alert("所选扶贫对象数量大于系统限制，请重新选择！");
			return false;
		}
    	// 提交
    	      
    	      $.ajax({
    	        type: 'POST',
    	        url: 'ActivityApply.do?method=savePoorInfo',
    	        data: $(".saveForm").serialize(),
    	        traditional: true,
    	        success: function(data) {
    	          if(data.code == "1") {
    	            $.jBox.tip("修改成功", "success");
    	            window.setTimeout(function() {
//     	            	 W.reloadJsp();
    					 api.close();
    	            }, 500);
    	          } else {
    	            $.jBox.tip(data.msg, "error", { timeout: 500 });
    	          }
    	        },
    	      });
    	 
//       $.ajax({
//         type: 'POST',
//         url: 'ActivityApply.do?method=saveComminfoPrice',
//         data: $(".saveForm").serialize(),
//         traditional: true,
//         success: function(data) {
//           if(data.code == "1") {
//             $.jBox.tip("修改成功", "success");
//             window.setTimeout(function() {
//             	 W.reloadJsp();
// 				 api.close();
//             }, 1000);
//           } else {
//             $.jBox.tip(data.msg, "error", { timeout: 500 });
//           }
//         },
//       });
    }
  });
  

	
	
});

function choosePoorInfo(){
// 	var p_index = $("#p_index").val();
	var province = '';
	var city = '';
	var country = '';
// 	alert(p_index.length);
// 	if('' != p_index && p_index.length == 6){
// 		province = p_index.substring(0,2) + '0000';
// 		city = p_index.substring(0,4) + '00';
// 		country = p_index;
// 	}
	var url = "${ctx}/BaseCsAjax.do?method=choosePoorInfo&dir=admin&type=multiple&province="+province+"&city="+city+"&country="+country;
	var windowapi = frameElement==null?window.top:frameElement.api, W = windowapi==null?window.top:windowapi.opener;//内容页中调用窗口实例对象接口
	var zIndex = W==null?1976:W.$.dialog.setting.zIndex+1;
	$.dialog({
		title:  "选择贫困户",
		width:  850,
		height: 550,
		zIndex:zIndex,
     	 parent:windowapi,	
        lock:true ,
		content:"url:"+url
	});
}


function returnPoorInfo(ids,names,p_names){
	 var temp_poor_ids = $("#temp_poor_ids").val() || "";
    if(null!=ids&&''!=ids){
   	 var id_array = ids.split(',');
   	 var name_array = names.split(',');
   	 var pname_array = p_names.split(',');
   	 for(var i=0;i<id_array.length;i++){
   		 if(temp_poor_ids.indexOf(','+id_array[i]+',')==-1){
   			 var html = "";	
   			 html = '<tr>';
   			 html += '<input type="hidden" name="poor_ids" value="'+id_array[i]+'" />';
   			 html += '<td align="center">'+name_array[i]+'</td>';
   			 html += '<td align="center">'+pname_array[i]+'</td>';
   			 html += '<td align="center"><b><a class="butbase" onclick="delPoorInfo(this,'+id_array[i]+');"><span class="icon-configure">删除</span></a></b></td>';
   			 html += '</tr>';
   			 $("#poor_list_tr").last().after(html);
   			 temp_poor_ids = temp_poor_ids+","+id_array[i]+',';
   			 $("#temp_poor_ids").val(temp_poor_ids);
   		 }
   	 }
    }
}
function delPoorInfo(obj,id){
	 $(obj).parent().parent().parent().remove();
	 var str = $("#temp_poor_ids").val().replace(','+id+',',',');
	 $("#temp_poor_ids").val(str);
}
 
//]]></script>
</body>
</html>
