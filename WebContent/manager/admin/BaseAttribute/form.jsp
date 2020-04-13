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
      <td align="center" width="25%"><input type="hidden" name="brand_ids" id="brand_ids" class="isHaveSon" />
        <input name="type_name" id="type_name" type="text" class="webinput"/></td>
      <td align="center" width="25%"><input name="type_show_name" id="type_show_name" type="text" class="webinput"/></td>
      <td align="center" width="25%"><input name="order_value_son" id="order_value_son" type="text"  maxlength="4" size="4" class="webinput" value="0"/></td>
      <td align="center" width="25%"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></td>
    </tr>
  </table>
  <html-el:form action="/admin/BaseAttribute" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="attr_scope" styleId="attr_scope" />
    <html-el:hidden property="del_attr_id" styleId="del_attr_id" />
    <html-el:hidden property="cls_id" styleId="cls_id" value="${baseClass.cls_id}"/>
    <html-el:hidden property="class_mod_id" styleId="class_mod_id" value="${class_mod_id}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">类别属性基本信息</th>
      </tr>
      <c:if test="${not empty baseClass}">
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">绑定类别名称：</td>
          <td width="88%"><c:out value="${baseClass.cls_name}"></c:out></td>
        </tr>
      </c:if>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>属性名称：</td>
        <td width="88%"><html-el:text property="attr_name" styleId="attr_name" maxlength="24" styleClass="webinput" />
          <span id="title_name_tip" style="display:none;color:red;">该字段名已存在</span></td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>属性显示名称：</td>
        <td width="88%"><html-el:text property="attr_show_name" styleId="attr_show_name" maxlength="24" styleClass="webinput" />
          <span class="sysTip">说明：此处填写的名称为首页显示的名称</span></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>属性类型：</td>
        <td><html-el:select property="type" styleId="type" disabled="${not empty af.map.id}">
            <html-el:option value="3">单选</html-el:option>
            <html-el:option value="1">简单文本框</html-el:option>
            <html-el:option value="2">可编辑文本框</html-el:option>
            <html-el:option value="4">多选</html-el:option>
            <html-el:option value="5">下拉框选择</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否必填：</td>
        <td><html-el:select property="is_required" styleId="is_required">
            <html-el:option value="1">是</html-el:option>
            <html-el:option value="0">否</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否显示：</td>
        <td><html-el:select property="is_show" styleId="is_show">
            <html-el:option value="1">是</html-el:option>
            <html-el:option value="0">否</html-el:option>
          </html-el:select></td>
      </tr>
      <!-- 
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>该属性是否是品牌：</td>
        <td><html-el:select property="is_brand" styleId="is_brand">
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select>
          <span class="sysTip">说明：如果此属性是品牌，请选择“是”子属性则从品牌库中取数据。</span> <span style="padding-left: 5px;display: none;" id="selectAllBrandSpan">
          <html-el:button onclick="selectAllBrand()"  property="" value="选择品牌" styleClass="bgButton" />
          </span></td>
      </tr>
       -->
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否锁定：</td>
        <td><html-el:select property="is_lock" styleId="is_lock">
            <html-el:option value="0">未锁定</html-el:option>
            <html-el:option value="1">已锁定</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">备注：</td>
        <td><html-el:text property="remark" styleId="remark" maxlength="100" style="width:120px" styleClass="webinput" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">排序值：</td>
        <td><html-el:text property="order_value" styleId="order_value" maxlength="4" size="4" styleClass="webinput" />
          值越大，显示越靠前。</td>
      </tr>
      <tr id="xz_hidden_tbody" >
        <td colspan="2"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
            <tr>
              <th width="25%">子属性名称</th>
              <th width="25%">子属性显示名称</th>
              <th width="25%">排序值</th>
              <th width="25%"><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="imgAddTr" title="再添加一个" /></th>
            </tr>
            <tbody id="xzShow">
              <c:if test="${not empty sonList}">
                <c:forEach var="cur" items="${sonList}" varStatus="vs">
                  <tr>
                    <td align="center" id="edit_son" width="25%"><c:out value="${cur.attr_name}"></c:out>
                      <input type="hidden" name="brand_ids" id="brand_ids" class="isHaveSon" /></td>
                    <td align="center" width="25%"><c:out value="${cur.attr_show_name}"></c:out></td>
                    <td align="center" width="25%"><c:out value="${cur.order_value }"></c:out></td>
                    <td align="center" width="25%"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="delSonAttr" class="${cur.id }" title="删除"/></td>
                  </tr>
                </c:forEach>
              </c:if>
            </tbody>
          </table></td>
      </tr>
      <tr>
        <td colspan="2" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
          <c:if test="${not empty baseClass}"> &nbsp; <span style="color: #F00;">此属性保存后会直接绑定到${baseClass.cls_name}</span> </c:if></td>
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
	$("#attr_name").attr("dataType", "Require").attr("msg", "属性名称不能为空");
	$("#attr_show_name").attr("dataType", "Require").attr("msg", "属性显示名称不能为空");
	$("#type").attr("dataType", "Require").attr("msg", "属性类型必须选择");
	$("#order_value").attr("dataType", "Integer").attr("msg", "排序值必须为整数").focus(setOnlyNum);
	if('${edit}'==''){
		$("#xzHidden").clone().appendTo($("#xzShow")).show();
		$("#xzHidden").clone().appendTo($("#xzShow")).show();
	}
	var del_id="";
	$("img[id='delSonAttr']").each(function(){
		$(this).click(function (){
			var id=$(this).attr('class');
			$.ajax({
				type: "POST",
				url: "${ctx}/CsAjax.do?method=checkDel",
				data: "id="+id,
				dataType: "json",
				error: function(request, settings) {alert("数据导出失败！");},
				success: function(returnDataArray) {
						if(confirm("确定删除？")){
							$("."+id).parent().parent().remove();
							del_id=del_id+id+",";
							$("#del_attr_id").val(del_id);
						}
				}
			});	
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
			$(this).attr("datatype", "Require").attr("msg", "请填写子属性名称！");
		}
	});
	$("input[id='type_show_name']").each(function(){
		j=j+1;
		if(j>1){
			$(this).attr("datatype", "Require").attr("msg", "请填写子属性显示名称！");
		}
	});
	$("input[id='order_value_son']").each(function(){
		k=k+1;
		if(k>1){
			$(this).attr("datatype", "Integer").attr("msg", "请填写排序值，且必须为整数！").focus(setOnlyNum);
		}
	});
	$("#imgAddTr").click(function (){
			$("#xzHidden").clone().appendTo($("#xzShow")).show();
			var lastTR = $("tr:last", "#xzShow");
			$("#type_name", lastTR).attr("datatype", "Require").attr("msg", "请填写子属性名称！");
			$("#type_name", lastTR).attr("datatype", "Require").attr("msg", "请填写子属性名称！");
			$("#type_show_name", lastTR).attr("datatype", "Require").attr("msg", "请填写子属性显示名称！");
	    	$("#order_value_son", lastTR).attr("datatype", "Integer").attr("msg", "请填写排序值，且必须为整数！").focus(setOnlyNum);
	    	$("#imgDelTr", lastTR).click(function (){
				$(this).parent().parent().remove();
			});
	});
	$("#is_brand").change(function(){
		if (this.value == 1) {
			$("#selectAllBrandSpan").show();
			$("#xz_hidden_tbody").hide();
		} else {
			$("#selectAllBrandSpan").hide();
			$("#xz_hidden_tbody").show();
		}
		$("#xzShow").empty();
	});
    if ("1" == "${af.map.is_brand}") {
    	$("#selectAllBrandSpan").show();
    }
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 3)){
			if ($(".isHaveSon").length <= 1) {
				var msg = "请至少填写一个子属性！";
				var is_brand = false;
				if ($("#is_brand").val() == 1) {
					msg = "请选择品牌！";
					is_brand = true;
				}
				alert(msg);
				if (is_brand) {
					selectAllBrand();
				}
				return false;
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

function selectAllBrand() { 
	var returnValue = window.showModalDialog("BaseBrandInfo.do?method=listSelectAllBrand&t=" + new Date().getTime(),window,"dialogWidth:600px;status:no;dialogHeight:450px");
	if (returnValue != null) {
		var brandIdAndBrandNames = returnValue.split("#$");
		$("#xzShow").empty();
		for (var i = 0; i < brandIdAndBrandNames.length; i++){
			var brand_id = brandIdAndBrandNames[i].split("_")[0];
			var brand_name = brandIdAndBrandNames[i].split("_")[1];
			$("#xzHidden").clone().appendTo($("#xzShow")).show();
			var lastTR = $("tr:last", "#xzShow");
			$("#brand_ids", lastTR).val(brand_id);
			$("#type_name", lastTR).val(brand_name).attr("datatype", "Require").attr("msg", "请填写子属性名称！");
			$("#type_show_name", lastTR).val(brand_name).attr("datatype", "Require").attr("msg", "请填写子属性显示名称！");
	    	$("#order_value_son", lastTR).attr("datatype", "Integer").attr("msg", "请填写排序值，且必须为整数！").focus(setOnlyNum);
	    	$("#imgDelTr", lastTR).click(function (){
				$(this).parent().parent().remove();
			});
		}
		$("#xz_hidden_tbody").show();
	};
};
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>