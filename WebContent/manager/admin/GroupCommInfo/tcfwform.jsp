<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>企业会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">

   <div class="subtitle">
      <h3>商品管理 -&gt; 规格管理</h3>
  </div>
      <html-el:form action="/admin/GroupCommInfo.do"  enctype="multipart/form-data">
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="savetcfw" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="comm_id" styleId="comm_id" />
        <html-el:hidden property="comm_type" styleId="comm_type" />     
        <html-el:hidden property="is_work" styleId="is_work" />     
      <table width="100%" border="0" >
      <tr><td>
      <div style="margin-bottom: 2px;">
	</div>
         <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="tableClass">
          <tr>
            <td> <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0" class="tableClass">
<!--                 <tr> -->
<%--                   <th nowrap="nowrap" <c:if test="${is_buyer_limit ne 0 }">colspan="5"</c:if><c:if test="${is_buyer_limit eq 0}">colspan="4"</c:if> > --%>
<%--                   <b><a class="butbase" onclick="listattr('${af.map.comm_id}')"><span class="icon-configure">规格维护</span></a></b></th> --%>
<!--                 </tr> -->
                <tr>
                  <th nowrap="nowrap" width="50%">套餐组合</th>
                  <th nowrap="nowrap">
                  	活动价格
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
                  <th nowrap="nowrap">原价格 
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置价格：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('cost_price',this);">设置</a>
	                    </div>
	                  </div>
                  </th>
                  <th nowrap="nowrap">活动数量 
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
<%--                   <c:if test="${is_buyer_limit ne 0}"> --%>
                  <th nowrap="nowrap">限购数量
                  <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置数量：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('buyer_limit_num',this);">设置</a>
	                    </div>
	               </div>
                  </th>
<%--                   </c:if> --%>
                </tr>
                <tbody id="tczh_tbody">
                  <c:forEach var="cur" items="${list_CommTczhPrice}" varStatus="vs" >
                    <tr>
                      <td align="center" nowrap="nowrap">${cur.map.attr_tczh_names}</td>
                      <td align="center">
                    	<html-el:text property="tczh_price" styleId="tczh_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.comm_price}" onfocus='javascript:setOnlyNum(this);'/>&nbsp;元
                        <input type="hidden" name="comm_tczh_name" id="comm_tczh_name" value="${cur.map.attr_tczh_names}"/>
                        <input type="hidden" name="comm_tczh_id" id="comm_tczh_id" value="${cur.id}"/>
                        <input type="hidden" name="attr_tczh_id" id="attr_tczh_id" value="${cur.map.attr_tczh_ids}"/></td>
<%--                       <td align="center"><html-el:text property="cost_price" styleId="cost_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.cost_price}" />&nbsp;元</td> --%>
                      <td align="center"><html-el:text property="orig_price" styleId="orig_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.orig_price}" />&nbsp;元</td>
                      <td align="center">
                    	<html-el:text property="inventory" styleId="inventory" styleClass="webinput"  size="8" maxlength="8" value="${cur.inventory}" onfocus='javascript:setOnlyNum(this);'/>&nbsp;件
                      </td>
<%--                       <c:if test="${is_buyer_limit ne 0}"> --%>
                      <td align="center">
                    	<html-el:text property="buyer_limit_num" styleId="buyer_limit_num" styleClass="webinput"  size="8" maxlength="8" value="${cur.buyer_limit_num}" onfocus='javascript:setOnlyNum(this);'/>&nbsp;件
                      </td>
<%--                       </c:if> --%>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
              	<span style="color:red;">点击<i class="fa fa-edit icon"></i>可批量修改所在列的值。</span>
              </td>
          </tr>
        </table>
      </td></tr>
         <tr>
        <td colspan="6" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
        </table>
     </html-el:form>
    <div class="clear"></div>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
                                         
                                          
$(document).ready(function() {
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		$("tr", "#tczh_tbody").each(function(){
			$("#tczh_price", $(this)).attr("datatype", "Currency").attr("msg", "请填写套餐价格，且必须为数字！").focus(setOnlyNum);
// 			$("#cost_price", $(this)).attr("datatype", "Currency").attr("msg", "请填写成本价格，且必须为数字！").focus(setOnlyNum);
			$("#orig_price", $(this)).attr("datatype", "Currency").attr("msg", "请填写原价格，且必须为数字！").focus(setOnlyNum);
			$("#inventory", $(this)).attr("datatype", "Integer").attr("msg", "请填写套餐库存，且必须为数字！").focus(setOnlyNum);
// 			<c:if test="${is_buyer_limit ne 0}">
				$("#buyer_limit_num", $(this)).attr("datatype", "Integer").attr("msg", "请填写限购数量，且必须为数字！").focus(setOnlyNum);
// 			</c:if>
		}); 
		
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
            f.submit();
		}
	});
});


function listattr(val){
	$.dialog({
		title:  "规格管理",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:555,
		content:"url:${ctx}/manager/admin/CommInfo.do?method=listattr&comm_id="+val
	});
}



function SetTczh(){
	alert("settczh");
	$.ajax({
		type: "POST",
		url: "${ctx}/manager/admin/CommInfo.do",
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
// 						var cost_price = (null == cur.cost_price) ? "":cur.cost_price;
						var orig_price = (null == cur.orig_price) ? "":cur.orig_price;
						var inventory = (null == cur.inventory) ? "":cur.inventory;;
						
						html += "<tr>";
						html += "<td align='center' nowrap='nowrap'>" + cur.attr_tczh + "</td>";
						html += "<td align='center'><input value='" + comm_price + "' type='text' name='tczh_price' id='tczh_price' size='10' maxlength='10' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;元<input type='hidden' name='attr_tczh_id' id='attr_tczh_id' value='" + cur.attr_tczh_id + "' /><input type='hidden' name='comm_tczh_name' id='comm_tczh_name' value='" + cur.attr_tczh + "' /></td>";
// 						html += "<td align='center'><input value='" + cost_price + "' type='text' name='cost_price' id='cost_price' size='10' maxlength='10' onfocus='javascript:setOnlyNum(this);' class='webinput'/>&nbsp;元</td>";
						html += "<td align='center'><input value='" + orig_price + "' type='text' name='orig_price' id='orig_price' size='10' maxlength='10' onfocus='javascript:setOnlyNum(this);' class='webinput'/>&nbsp;元</td>";
						html += "<td align='center'><input value='" + inventory + "'  type='text' name='inventory' id='inventory' size='8' maxlength='8' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;件</td>";
						
// 						html += "<td><input type='text' name='user_score_percent' id='user_score_percent' size='20' maxlength='3' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;%</td>";
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
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>