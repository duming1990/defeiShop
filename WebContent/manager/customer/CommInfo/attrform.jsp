<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>套餐规格管理</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div >
  <table style="display:none;">
    <tr id="xzHidden" >
      <td align="center" width="70%"><input name="type_name" id="type_name" type="text" class="webinput"/> </td>
      <td align="center" width="15%"><input name="order_value_son" id="order_value_son" type="text"  maxlength="4" size="4" class="webinput" value="0"/></td>
      <td align="center" width="15%"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></td>
    </tr>
  </table>
  <html-el:form action="/customer/CommInfo.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveattr" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="comm_id" styleId="comm_id" />
    <html-el:hidden property="attr_scope" styleId="attr_scope" value="1"/>
    <html-el:hidden property="del_attr_id" styleId="del_attr_id" />
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>套餐主规格名称：</td>
        <td width="88%"><html-el:text property="attr_name" styleId="attr_name" maxlength="8" styleClass="webinput" />
          <span id="title_name_tip" style="display:none;color:red;">该字段名已存在</span> <span style="color:red;">为了页面美观，请将内容控制在四个汉字以内，例如：颜色、重量、版本</span> </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>排序值：</td>
        <td><html-el:text property="order_value" styleId="order_value" maxlength="4" size="4" styleClass="webinput"  value="${af.map.order_value==null?0:af.map.order_value}"/>
          值越大，显示越靠前。</td>
      </tr>
      <tr id="xz_hidden_tbody" >
        <td colspan="2"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="backTable">
            <tr>
              <th width="70%">套餐子规格名称</th>
              <th width="15%">排序值</th>
              <th width="15%"><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="imgAddTr" title="再添加一个" /></th>
            </tr>
            <tbody id="xzShow">
              <c:if test="${not empty sonList}">
                <c:forEach var="cur" items="${sonList}" varStatus="vs">
                  <tr>
                    <td align="center" id="edit_son">
                      <html-el:text property="son_attr_name" onblur="updateAttrName('${cur.id}',this);" maxlength="20" value="${cur.attr_name}" style="text-align:center;border:none;"/>
                      <input type="hidden" name="brand_ids" id="brand_ids" class="isHaveSon" />
                    </td>
                    <td align="center" width="25%"><c:out value="${cur.order_value }"></c:out></td>
                    <td align="center" width="25%"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="delSonAttr" class="${cur.id }" title="删除"/></td>
                  </tr>
                </c:forEach>
              </c:if>
            </tbody>
          </table></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><input type="button" value="保存" id="btn_submit" class="bgButton" />
          &nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" value="返 回" onclick="history.back();" id="btn_back" class="bgButton" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){

	var f = document.forms[0];
	
	$("#attr_name").attr("dataType", "Require").attr("msg", "规格名称不能为空");
	$("#type").attr("dataType", "Require").attr("msg", "规格类型必须选择");
	$("#order_value").attr("dataType", "Integer").attr("msg", "排序值必须为整数").focus(setOnlyNum);
	if('${edit}'==''){
		$("#xzHidden").clone().find("#file_hidden").attr("id","file_show").attr("name", "file_" + new Date().getTime()).attr("datatype","Filter").attr("msg","请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址").attr("require","true").attr("accept","bmp, gif, jpeg, jpg, png").end().appendTo($("#xzShow")).show();
	}
	
	$("#attr_name").blur(function(){
		var attr_name = $(this).val();
		if(null != attr_name && "" != attr_name){
			$.ajax({
				type: "POST",
				url: "${ctx}/CsAjax.do?method=checkAttrNameIsExist",
				data: "attr_name="+attr_name + "&cls_id=${commInfo.cls_id}&own_entp_id=${commInfo.own_entp_id}",
				dataType: "json",
				error: function(request, settings) {alert("删除数据失败！");},
				success: function(data) {
					if(data.code == 1){
						var submit = function (v, h, f) {
						    if (v == true) {
						    	selectHasAttr("${commInfo.cls_id}","${ids}","${commInfo.id}");
						    } 
						    return true;
						};
						myConfirm(data.msg,submit);
					}else if(data.code == 0){
						alert(data.msg);
					}
				}
			});	
		}
	});
	
	var del_id="";
	$("img[id='delSonAttr']").each(function(){
		$(this).click(function (){
			var id=$(this).attr('class');
			if(null != id && "" != id){
				if(confirm("你确定要删除吗？")){
					$.ajax({
						type: "POST",
						url: "${ctx}/CsAjax.do?method=checkDelCommSonAttr",
						data: "id="+id,
						dataType: "json",
						error: function(request, settings) {alert("删除数据失败！");},
						success: function(data) {
							if(data.code == 1){
								$("."+id).parent().parent().remove();
								del_id=del_id+id+",";
								$("#del_attr_id").val(del_id);
							}else{
								alert(data.msg);
							}
						}
					});	
				}
			}
		});
	});
	$("img[id='imgDelTr']").each(function(){
		$(this).click(function (){
			$(this).parent().parent().remove();
		});
	});
	var i=0;
	var j=0;
	var k=0;
	$("input[id='type_name']").each(function(){
		i=i+1;
		if(i>1){
			$(this).attr("datatype", "Require").attr("msg", "请填写子规格名称！");
		}
	});

	$("input[id='order_value_son']").each(function(){
		k=k+1;
		if(k>1){
			$(this).attr("datatype", "Integer").attr("msg", "请填写排序值，且必须为整数！").focus(setOnlyNum);
		}
	});
	$("#imgAddTr").click(function (){
			$("#xzHidden").clone().find("#file_hidden").attr("id","file_show").attr("name", "file_" + new Date().getTime()).attr("datatype","Filter").attr("msg","请上传格式为（bmp, gif, jpeg, jpg, png）的主图地址").attr("require","true").attr("accept","bmp, gif, jpeg, jpg, png").end().appendTo($("#xzShow")).show();
			var lastTR = $("tr:last", "#xzShow");
			$("#type_name", lastTR).attr("datatype", "Require").attr("msg", "请填写子规格名称！");
			$("#type_name", lastTR).attr("datatype", "Require").attr("msg", "请填写子规格名称！");
	    	$("#order_value_son", lastTR).attr("datatype", "Integer").attr("msg", "请填写排序值，且必须为整数！").focus(setOnlyNum);
	    	$("#imgDelTr", lastTR).click(function (){
				$(this).parent().parent().remove();
			});
	});
	
	
	$("#btn_submit").click(function(){
		
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
            f.submit();
		}
	});
});


function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		//if(this.value.length == 0) this.value = 0;
	});
}

function updateAttrName(id,obj){
	var attr_name = $(obj).val();
	if(null != id && "" != id && null != attr_name && "" != attr_name){
		$.ajax({
			type: "POST",
			url: "${ctx}/CsAjax.do?method=updateAttrName&id=" + id + "&attr_name=" + attr_name,
			dataType: "json",
			error: function(request, settings) {alert("删除数据失败！");},
			success: function(data) {
				if(data.ret == 0){
					alert(data.msg);
				}
			}
		});	
	}
}


//]]></script>
</body>
</html>
