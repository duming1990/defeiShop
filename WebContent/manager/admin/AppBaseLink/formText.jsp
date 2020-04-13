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
  <html-el:form action="/admin/AppBaseLink" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="link_type" styleId="link_type" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
        <a href="javascript:void(0);" id="show_win">选择标题颜色</a>
          <html-el:checkbox property="title_is_strong" styleId="title_is_strong" value="1" onclick="checkweight();" />
          <label for="title_is_strong">加粗</label></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">排序值：</td>
        <td colspan="2" width="85%"><html-el:text property="order_value" styleId="order_value" maxlength="4" size="4" styleClass="webinput" />
          值越大，显示越靠前，范围：0-9999</td>
      </tr>
       <c:if test="${af.map.is_del eq 1}">
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">是否删除：</td>
        <td colspan="2" width="85%">
          <html-el:select property="is_del" styleId="is_del">
            <html-el:option value="1">是</html-el:option>
            <html-el:option value="0">否</html-el:option>
          </html-el:select>
        </td>
      </tr>
      </c:if>
      <tr>
        <td colspan="3" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
    <jsp:include page="../../../public/public_color_select.jsp" flush="true"/>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script> 
<script type="text/javascript"><!--//<![CDATA[

$(document).ready(function(){
	$("#title").attr("dataType", "Require").attr("msg", "标题必须填写");
	$("#order_value").attr("dataType", "Number").attr("msg", "排序值必须为正整数");

	var $t = $("#title");
	<c:if test="${not empty af.map.title_color}">
		$t.css("color", '${af.map.title_color}');
	</c:if>
	<c:if test="${1 eq (af.map.title_is_strong)}">
		$t.css("font-weight", "bold");
	</c:if>
	
	String.prototype.trim = function(){
        return this.replace(/(^\s*)|(\s*$)/g, "");
    }
	$t.blur(function() {
        $(this).val(this.value.trim());                           
    });

	$("#title").blur(function(){
		var this_value = $(this).val();
		if ("" != this_value) {
			$.post("AppBaseLink.do?method=getLinkUrl" , {
			        type : 3, 
			        link_type:${af.map.link_type},
				title_name : this_value
			}, function(data){
				if(data != "" || null != data){
					$("#link_url").val(data.link_url);
				}
			});
		}
	});
	
	$("#show_win").click(function() {
		$("#win").dialog( {
			open : function() {
				$("body > div[role=dialog]").appendTo($(document.forms[0]));
			},
			buttons : {
				"确定" : function() {
							$(this).dialog("close");
							var c = $("input[name='title_color']:checked").val();
							$("#title").css("color", c);
						},
				"取消" : function() {$(this).dialog("close");}
			},
			modal : true,
			title : '选择标题颜色'
		}).dialog("open");
	});
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	})

});

function checkweight() {
	var c = document.getElementById('title_is_strong');
	var t = document.getElementById('title');
	if (c.checked) {
		$(t).css("font-weight", "bold");
	} else {
		$(t).css("font-weight", "normal");
	}
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>