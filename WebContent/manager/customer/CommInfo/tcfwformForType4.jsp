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
<!-- main start -->
<div class="mainbox mine" style="min-height: 500px">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div style="margin-bottom: 2px;">
    <ul class="add-goods-step">
      <c:url var="url" value="#"/>
      <c:if test="${not empty af.map.comm_id}">
        <c:url var="url" value="/manager/customer/CommInfo.do?method=edit&id=${af.map.comm_id}&par_id=${af.map.par_id}&mod_id=${af.map.mod_id}"/>
      </c:if>
      <li onclick="location.href='${url}'" style="cursor: pointer;"> <i class="fa fa-list-alt icon"></i>
        <h6>第一步</h6>
        <h2>基本信息</h2>
        <i class="arrow fa fa-angle-right"></i></li>
      <li class="current"><i class="fa fa-edit icon"></i>
        <h6>第二步</h6>
        <h2>维护套餐信息</h2>
        <i class="arrow icon-angle-right"></i></li>
    </ul>
  </div>
  <%@ include file="/commons/pages/messages.jsp" %>
  <html-el:form action="/customer/CommInfo.do"  enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="savetcfw" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <html-el:hidden property="comm_id" styleId="comm_id" />
    <html-el:hidden property="comm_type" styleId="comm_type" />
    <html-el:hidden property="audit_state_old" styleId="audit_state_old" value="${af.map.audit_state}"/>
    <html-el:hidden property="is_work" styleId="is_work" />     
    <table width="100%" border="0" >
      <tr>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
             <tr>
              <td nowrap="nowrap" align="center"><span class="bgButtonFontAwesome" onclick="listattr('${af.map.comm_id}')"><a><i class="fa fa-gear"></i>规格维护</a></span></td>
             </tr>
             
	      <c:if test="${(not empty af.map.audit_state)}">
	        <tr>
	          <td>审核状态：
	          <c:choose>
	              <c:when test="${af.map.audit_state eq -1}"><span class="tip-danger">管理员审核不通过</span></c:when>
	              <c:when test="${af.map.audit_state eq -2}"><span class="tip-danger">合伙人审核不通过</span></c:when>
	              <c:when test="${af.map.audit_state eq 0}"><span class="tip-default">待审核</span></c:when>
	              <c:when test="${af.map.audit_state eq 2}"><span class="tip-success">合伙人审核通过</span></c:when>
	              <c:when test="${af.map.audit_state eq 1}"><span class="tip-success">管理员审核通过</span></c:when>
	            </c:choose>
	          </td>
	        </tr>
	      </c:if>
	        <c:if test="${(not empty af.map.audit_desc)}">
	          <tr>
	            <td>管理员审核说明：${fn:escapeXml(af.map.audit_desc)}</td>
	          </tr>
	        </c:if>
	        <c:if test="${(not empty af.map.audit_service_desc)}">
	          <tr>
	            <td>合伙人审核说明：${fn:escapeXml(af.map.audit_service_desc)}</td>
	          </tr>
	        </c:if>
             
            <tr>
              <td><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
                  <tr>
                    <th nowrap="nowrap" width="40%">套餐组合</th>
                    <th nowrap="nowrap">套餐原价格
                     <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置价格：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('orig_price',this);">设置</a>
	                    </div>
	                  </div>
                    </th>
                    <th nowrap="nowrap">
                  	折扣价格 
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
                    
                    <th nowrap="nowrap">活动限购
                     <div class="batch">
                       <i class="fa fa-edit icon" title="批量修改" onclick="updateAll(this);"></i>
	                     <div class="batch-input" style="display:none;">
		                    <h6>批量设置库存：</h6>
		                    <a class="close" onclick="closeUpdateAll(this);">X</a>
		                    <input name="updateAll" type="text" class="text price" style="width:55px;" size="8" maxlength="8"/>
		                    <a class="ncbtn-mini" onclick="setUpdateVal('buyer_limit_num',this);">设置</a>
	                    </div>
	                  </div>
                    </th>
                  </tr>
                  <tbody id="tczh_tbody">
                    <c:forEach var="cur" items="${list_CommTczhPrice}" varStatus="vs" >
                      <tr>
                        <td align="center" nowrap="nowrap">${cur.map.attr_tczh_names}</td>
                        <td align="center">
	                        <c:set var="cost_price" value="${cur.cost_price}" />
<%-- 	                        <c:set var="orig_price" value="${cur.comm_price}" /> --%>
<%-- 	                        <c:if test="${not empty cur.orig_price}"> --%>
<%-- 	                         <c:set var="orig_price" value="${cur.orig_price}" /> --%>
<%-- 	                        </c:if> --%>
	                    	<html-el:text property="cost_price" styleId="cost_price" styleClass="webinput"  size="10" maxlength="10" value="${cost_price}" />&nbsp;元
	                    </td>
                        <td align="center"><html-el:text property="tczh_price" style="width:55px;" styleId="tczh_price" styleClass="webinput"  size="10" maxlength="10" value="${cur.comm_price}" onfocus='javascript:setOnlyNum(this);'/>
                          &nbsp;元
                          <input type="hidden" name="comm_tczh_name" id="comm_tczh_name" value="${cur.map.attr_tczh_names}"/>
                          <input type="hidden" name="comm_tczh_id" id="comm_tczh_id" value="${cur.id}"/>
                          <input type="hidden" name="attr_tczh_id" id="attr_tczh_id" value="${cur.map.attr_tczh_ids}"/></td>
                        <td align="center"><html-el:text property="inventory" styleId="inventory" styleClass="webinput" style="width:55px;" size="8" maxlength="8" value="${cur.inventory}" onfocus='javascript:setOnlyNum(this);'/>
                          &nbsp;件 </td>
                        <td align="center"><html-el:text property="buyer_limit_num" styleId="buyer_limit_num" styleClass="webinput" style="width:55px;" size="8" maxlength="8" value="${cur.buyer_limit_num}" onfocus='javascript:setOnlyNum(this);'/>
                          &nbsp;件 </td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
               	 点击 <i class="fa fa-edit icon"></i>可批量修改所在列的值。
                </td>
            </tr>
            <c:set var="display" value="display:none"/>
            <c:if test="${not empty list_CommTczhPrice}">
            <c:set var="display" value=""/>
            </c:if>
            <tr id="tr_save" style="${display}">
              <td align="center"><input type="button" value="保存" id="btn_submit" class="bgButton" /> &nbsp;&nbsp;<input type="button" value="返 回" onclick="history.back();" id="btn_back" class="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
</div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
                                          
$(document).ready(function() {
	var f = document.forms[0];
	$("#btn_submit").click(function(){
		$("tr", "#tczh_tbody").each(function(){
			$("#tczh_price", $(this)).attr("datatype", "Currency").attr("msg", "请填写套餐价格，且必须为数字！");
			$("#inventory", $(this)).attr("datatype", "Integer").attr("msg", "请填写套餐库存，且必须为数字！");
			$("#buyer_limit_num", $(this)).attr("datatype", "Integer").attr("msg", "请填写活动限购，且必须为数字！");
		
		}); 
		
		if(Validator.Validate(f, 3)){
// 			 if($("#audit_state_old").val() == 1){
// 				var submit2 = function(v, h, f) {
// 					if (v == "ok") {
// 						$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
// 				        $("#btn_reset").attr("disabled", "true");
// 				        $("#btn_back").attr("disabled", "true");
// 						document.forms[0].submit();
// 					}
// 				};
// 				$.jBox.confirm("套餐修改后，需管理员重新审核！", "确定提示", submit2);
// 			}else{ 
				 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		         $("#btn_reset").attr("disabled", "true");
		         $("#btn_back").attr("disabled", "true");
				 f.submit();
			//}
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
						var orig_price = (null == cur.orig_price) ? "":cur.orig_price;
						var inventory = (null == cur.inventory) ? "":cur.inventory;;
						var buyer_limit_num = (null == cur.buyer_limit_num) ? "":cur.buyer_limit_num;;
						html += "<tr>";
						html += "<td align='center' nowrap='nowrap'>" + cur.attr_tczh + "</td>";
						html += "<td align='center'><input value='" + orig_price + "' type='text' name='orig_price' id='orig_price' size='10' maxlength='10' class='webinput' />&nbsp;元</td>";
						html += "<td align='center'><input value='" + comm_price + "' type='text' name='tczh_price' id='tczh_price' size='10' maxlength='10' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;元<input type='hidden' name='attr_tczh_id' id='attr_tczh_id' value='" + cur.attr_tczh_id + "' /><input type='hidden' name='comm_tczh_name' id='comm_tczh_name' value='" + cur.attr_tczh + "' /></td>";
						html += "<td align='center'><input value='" + inventory + "'  type='text' name='inventory' id='inventory' size='8' maxlength='8' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;件</td>";
						html += "<td align='center'><input value='" + buyer_limit_num + "'  type='text' name='buyer_limit_num' id='buyer_limit_num' size='8' maxlength='8' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;件</td>";
						//	html += "<td><input type='text' name='user_score_percent' id='user_score_percent' size='20' maxlength='3' onfocus='javascript:setOnlyNum(this);' class='webinput' />&nbsp;%</td>";
						html += "</tr>";
						$("#is_work").val(1);
					}
					$("#tczh_tbody").html(html);
					$("#tr_save").show();
				}
			} else {
				alert(ret.msg);
			}
			
		}
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
