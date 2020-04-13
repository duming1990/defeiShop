<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <table style="display: none;">
    <tr id="xzHidden" >
      <td align="center" nowrap="nowrap"><input class="flag_has_items" name="type_name" id="type_name" type="text" /></td>
      <td align="center" nowrap="nowrap"><input name="order_value_son" id="order_value_son" type="text"  maxlength="4" size="4" /></td>
      <td align="center" nowrap="nowrap"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></td>
    </tr>
  </table>
  <html-el:form action="/admin/NewsInfoCustomFields" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">自定义字段基本信息</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>字段名称：</td>
        <td width="88%"><html-el:text property="title_name" styleId="title_name" maxlength="125" styleClass="webinput" />
          <span id="title_name_tip" style="display:none;color:red;">该字段名已存在</span></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>字段类型：</td>
        <td><html-el:select property="type" styleId="type" disabled="${not empty af.map.id}">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="1">简单文本框</html-el:option>
            <html-el:option value="2">可编辑文本框</html-el:option>
            <html-el:option value="3">单选</html-el:option>
            <html-el:option value="4">多选</html-el:option>
            <html-el:option value="5">下拉框选择</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="xz_hidden_tbody" style="display: none;">
        <td colspan="2"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
            <tr>
              <th>选项名称</th>
              <th>排序值</th>
              <th><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="imgAddTr" title="再添加一个" /></th>
            </tr>
            <tbody id="xzShow">
              <c:if test="${not empty zdy_son_list}">
                <c:forEach var="cur" items="${zdy_son_list}" varStatus="vs">
                  <tr>
                    <td align="center" nowrap="nowrap"><input class="flag_has_items" name="type_name" id="type_name_${cur.id}" type="text"  value="${cur.type_name}"  /></td>
                    <td align="center" nowrap="nowrap"><input name="order_value_son" id="order_value_son_${cur.id}" type="text" value="${cur.order_value }"  maxlength="4" size="4" /></td>
                    <td align="center" nowrap="nowrap"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></td>
                  </tr>
                </c:forEach>
              </c:if>
            </tbody>
          </table></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否必填：</td>
        <td><html-el:select property="is_required" styleId="is_required">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="1">是</html-el:option>
            <html-el:option value="0">否</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">备注：</td>
        <td><html-el:text property="remark" styleId="remark" maxlength="30" style="width:120px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">排序值：</td>
        <td><html-el:text property="order_value" styleId="order_value" maxlength="4" size="4" styleClass="webinput" />
          值越大，显示越靠前。</td>
      </tr>
      <tr>
        <td colspan="2" align="center"><html-el:button property="" value="预 览 效 果" styleClass="bgButton" styleId="btn_preview" />
          &nbsp;
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      <tr id= "tr_img_preview" style="display:none">
        <td colspan="2" id="img_preview" ></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>

<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#type").attr("dataType", "Require").attr("msg", "字段类型必须选择");
	$("#title_name").attr("dataType", "Require").attr("msg", "字段名称必须填写");
	$("#is_required").attr("dataType", "Require").attr("msg", "是否必填必须选择");
	$("#order_value").attr("dataType", "Integer").attr("msg", "排序值必须为整数");
	$("#order_value").focus(setOnlyNum);

	//显示输入选项
	$("#type").change(function(){
		var type_value = Number($("#type").val());
		if(type_value == 3 || type_value == 4 || type_value == 5){
			$("#xz_hidden_tbody").show();
		}else{
			$("#xz_hidden_tbody").hide();
		}
		$("#tr_img_preview").hide();
	});
	//修改时显示
	var type = Number($("#type").val());
	if(type == 3 || type == 4 || type == 5){
		$("#xz_hidden_tbody").show();
	}else{
		$("#xz_hidden_tbody").hide();
	}

	 $("#imgAddTr").click(function (){
			$("#xzHidden").clone().appendTo($("#xzShow")).show();
			var lastTR = $("tr:last", "#xzShow");
			$("#type_name", lastTR).attr("datatype", "Require").attr("msg", "请填写选项名称！");
	    	$("#order_value_son", lastTR).attr("datatype", "Integer").attr("msg", "请填写排序值，且必须为整数！").focus(setOnlyNum);
		    $("img[id='imgDelTr']").each(function(){
				$(this).click(function (){
					$(this).parent().parent().remove();
				});
			});
		});

	//预览
	$("#btn_preview").click(function(){
		var type = Number($("#type").val());
		if(type != ""){
			$("#tr_img_preview").show();
			$("#img_preview").empty().append("<img src='../../images/field_type_"+type+".gif' style='vertical-align:middle;' />");
		}else{
			$("#tr_img_preview").hide();
		}
	});

	 <c:if test="${not empty zdy_son_list}">
     	<c:forEach var="cur" items="${zdy_son_list}" >
     		$("#type_name_${cur.id}").attr("datatype", "Require").attr("msg", "请填写选项名称！");
     		$("#order_value_son_${cur.id}").attr("datatype", "Integer").attr("msg", "请填写排序值，且必须为整数！").focus(setOnlyNum);
     	</c:forEach>
     </c:if> 
	// 提交
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 3)){
		    var type_value = Number($("#type").val());
			if(type_value == 3 || type_value == 4 || type_value == 5){
				if ($(".flag_has_items").length <= 1) {
					alert("请至少填写一个选项！");
					return false;
				}
			}
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	});
});

function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	})
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}
		//if(this.value.length == 0) this.value = 0;
	})
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>