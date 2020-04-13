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
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="mainbox mine">
 	<jsp:include page="../_nav.jsp" flush="true"/>
        <html-el:form action="/customer/CommInfo.do"  enctype="multipart/form-data">
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="savetcfw" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="par_id" styleId="par_id" />
        <html-el:hidden property="comm_id" styleId="comm_id" />
        <html-el:hidden property="comm_type" styleId="comm_type" />     
        <html-el:hidden property="is_work" styleId="is_work" />     
      <table width="100%" border="0" >
      <tr><td>
      <div style="margin-bottom: 2px;">
	</div>
         <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
          <tr>
            <td> <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
                <tr>
                  <th nowrap="nowrap" colspan="4"><b><a class="butbase" onclick="clone();"><span class="icon-configure">添加套餐</span></a></b></th>
                </tr>
                <tr>
                  <th nowrap="nowrap" width="50%">套餐名称</th>
                  <th nowrap="nowrap">
                  	套餐价格 
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置价格：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('tczh_price',this);">设置</a>
	                    </div>
	                  </div>
                  </th>
                  <th nowrap="nowrap">套餐库存 
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置库存：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('inventory',this);">设置</a>
	                    </div>
	               </div>
                  </th>
                  <th nowrap="nowrap">操作</th>
                </tr>
                <tbody id="tczh_tbody">
                 <c:if test="${empty list_CommTczhPrice}">
                   <tr class="clone_tr">
                      <td align="center" nowrap="nowrap"><html-el:text property="tczh_name" style="width:300px;" styleId="tczh_name" styleClass="webinput"/></td>
                      <td align="center">
                    	<html-el:text property="tczh_price" styleId="tczh_price" styleClass="webinput"  size="10" maxlength="10" onfocus='javascript:setOnlyNum(this);'/>&nbsp;元
                      </td>
                      <td align="center">
                    	<html-el:text property="inventory" styleId="inventory" styleClass="webinput"  size="8" maxlength="8"  onfocus='javascript:setOnlyNum(this);'/>&nbsp;件
                      </td>
                      <td align="center"><b><a id="icon-del-flag" class="butbase but-disabled"><span class="icon-configure">删除</span></a></b></td>
                    </tr>
                    </c:if>
                  <c:if test="${not empty list_CommTczhPrice}">
                  <c:forEach var="cur" items="${list_CommTczhPrice}" varStatus="vs" >
                    <tr class="clone_tr">
                      <td align="center" nowrap="nowrap"><html-el:text property="tczh_name" style="width:300px;" styleId="tczh_name" styleClass="webinput" value="${cur.tczh_name}" /></td>
                      <td align="center">
                    	<html-el:text property="tczh_price" styleId="tczh_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.comm_price}" onfocus='javascript:setOnlyNum(this);'/>&nbsp;元
                      </td>
                      <td align="center">
                    	<html-el:text property="inventory" styleId="inventory" styleClass="webinput" size="8" maxlength="8" value="${cur.inventory}" onfocus='javascript:setOnlyNum(this);'/>&nbsp;件
                      </td>
                      <c:if test="${vs.count eq 1}">
                      	<td align="center"><b><a id="icon-del-flag" class="butbase but-disabled" title="默认规格，不能删除"><span class="icon-configure">删除</span></a></b></td>
                      </c:if>
                      <c:if test="${vs.count gt 1}">
                      	<td align="center"><b><a class="butbase" onclick="delClone(this);"><span class="icon-configure">删除</span></a></b></td>
                      </c:if>
                    </tr>
                  </c:forEach>
                  </c:if>
                </tbody>
              </table>
              	<span style="color:red;">点击<i class="fa fa-edit icon"></i>可批量修改所在列的值。</span>
              </td>
          </tr> <tr>
        <td colspan="6" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
        </table>
        
      </td></tr>
        
        </table>
     </html-el:form>
    <div class="clear"></div>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
                                         
                                          
$(document).ready(function() {
	
	$('#tczh_price').bind('input propertychange', function() {
//    		alert("改变")
	});
	
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		$("tr", "#tczh_tbody").each(function(){
			$("#tczh_price", $(this)).attr("datatype", "Currency").attr("msg", "请填写套餐价格，且必须为数字！").focus(setOnlyNum);
			$("#inventory", $(this)).attr("datatype", "Integer").attr("msg", "请填写套餐库存，且必须为数字！").focus(setOnlyNum);
		}); 
		
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
            f.submit();
		}
	});
});

function clone(){
	var newtr = $(".clone_tr").last().clone();
	newtr.find("#icon-del-flag").removeClass("but-disabled");
	newtr.find("#tczh_name").val("").removeAttr("disabled");
	newtr.find("#tczh_price").val("").removeAttr("disabled");
	newtr.find("#tczh_inventory").val("").removeAttr("disabled");
	newtr.find("#icon-del-flag").attr("onclick","delClone(this);");
	$(".clone_tr").last().after(newtr);
}

function delClone(obj){
	 $(obj).parent().parent().parent().remove();
}


function listattr(val){
	$.dialog({
		title:  "规格管理",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:555,
		content:"url:${ctx}/manager/customer/CommInfo.do?method=listattr&comm_id="+val
	});
}



function SetTczh(){
	$.ajax({
		type: "POST",
		url: "${ctx}/manager/customer/CommInfo.do",
		data: {method : "tczh",comm_id : '${af.map.comm_id}'},
		dataType: "json",
		cache:false,
		error: function(){alert("数据加载请求失败！");},
		success: function(ret){
			if(ret.code == 1){
				var list =  ret.datas.list;
				if(null != list && list.length > 0){
					var html = "";
					for(var i = 0; i < list.length; i++){
						var cur = list[i];
						var comm_price = (null == cur.comm_price) ? "":cur.comm_price;
						var inventory = (null == cur.inventory) ? "":cur.inventory;;
						html += "<tr>";
						html += "<td align='center' nowrap='nowrap'>" + cur.attr_tczh + "</td>";
						html += "<td align='center'><input value='" + comm_price + "' type='text' name='tczh_price' id='tczh_price' size='10' maxlength='10' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;元<input type='hidden' name='attr_tczh_id' id='attr_tczh_id' value='" + cur.attr_tczh_id + "' /><input type='hidden' name='comm_tczh_name' id='comm_tczh_name' value='" + cur.attr_tczh + "' /></td>";
						html += "<td align='center'><input value='" + inventory + "'  type='text' name='inventory' id='inventory' size='8' maxlength='8' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;件</td>";
					//	html += "<td><input type='text' name='user_score_percent' id='user_score_percent' size='20' maxlength='3' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;%</td>";
						html += "</tr>";
						$("#is_work").val(1);
					}
					$("#tczh_tbody").html(html);
				}
			} else {
				alert(ret.msg);
			}
			
		}
   }); 
}


//正则表达式：只能输入数字
function setOnlyNum(obj) {
	$(obj).css("ime-mode", "disabled");
	$(obj).attr("t_value", "");
	$(obj).attr("o_value", "");
	$(obj).bind("dragenter",function(){
		return false;
	});
	$(obj).keypress(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value;
	}).keyup(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value;
	}).blur(function (){
		if(!obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))obj.value=obj.o_value;else{if(obj.value.match(/^\.\d+$/))obj.value=0+obj.value;if(obj.value.match(/^\.$/))obj.value=0;obj.o_value=obj.value;}
		if(isNaN(obj.value)) obj.value = "";
	});
}

//正则表达式：只能输入数字
function setOnlyInt(obj) {
	$(obj).css("ime-mode", "disabled");
	$(obj).attr("t_value", "");
	$(obj).attr("o_value", "");
	$(obj).bind("dragenter",function(){
		return false;
	});
	$(obj).keypress(function (){
		if(!obj.value.match(/^\d*$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:\d+(?:\d+)?)?$/))obj.o_value=obj.value;
	}).keyup(function (){
		if(!obj.value.match(/^\d*$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:\d+(?:\d+)?)?$/))obj.o_value=obj.value;
	}).blur(function (){
		if(!obj.value.match(/^(?:\d+(?:\d+)?|\d*?)?$/))obj.value=obj.o_value;else{if(obj.value.match(/^\d+$/))obj.value=obj.value;if(obj.value.match(/^\.$/))obj.value=0;obj.o_value=obj.value;}
		if(obj.value.length == 0 || isNaN(obj.value) || obj.value == 0) obj.value = "1";
	});
}

function updateAll(obj){
	var $this = $(obj);
	if($this.next().is(":hidden")){
		$this.next().show();
	}else{
		$this.next().hide();
	}
}
function closeUpdateAll(obj){
	$(obj).parent().hide();
}

function setUpdateVal(data_flag,obj){
	var setVal = $(obj).prev().val();
	var curr = /^\d+(\.\d+)?$/;
	if(null != setVal && "" != setVal && curr.test(setVal)){
		$("#tczh_tbody").find("input[name='"+ data_flag +"']").val(setVal);
		$(obj).parent().hide();
	}else{
		alert("数据有误，请重新填写");
	}
}




//]]></script>
</body>
</html>
