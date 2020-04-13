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
    <html-el:hidden property="par_cls_id" styleId="par_cls_id"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
        <a href="javascript:void(0);" id="show_win">选择标题颜色</a>
        <html-el:text property="title_color" styleId="title_color"/>
           <html-el:checkbox property="title_is_strong" styleId="title_is_strong" value="1" onclick="checkweight();" />
          <label for="title_is_strong">加粗</label></td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>图片：</td>
        <td colspan="2" width="85%"><c:if test="${not empty (af.map.image_path)}" var="hasImage" ><br/>
            <img src="${ctx}/${af.map.image_path}" title="${af.map.image_desc}" /><br />
            <input type="checkbox" name="chkReUploadImage" id="chkReUploadImage" value="1" onclick="$('#image_path').toggle();" />
            <label for="chkReUploadImage">重新上传图片</label>
            <br/>
            <html-el:file property="image_path" style="display:none;width:500px;" styleId="image_path" />
          </c:if>
          <c:if test="${empty (af.map.image_path)}">
            <html-el:file property="image_path" style="width:500px;" styleId="image_path" />
          </c:if>
          <div style="color:rgb(241, 42, 34); padding: 5px;"> 
         <c:if test="${af.map.link_type eq 1020||af.map.link_type eq 40}">[图片尺寸] 建议：[479px * 180px] </c:if>
         <c:if test="${(af.map.link_type eq 1511) or (af.map.link_type eq 1521) or (af.map.link_type eq 1531) or (af.map.link_type eq 1541) or (af.map.link_type eq 1551) or (af.map.link_type eq 1561) or (af.map.link_type eq 1571)}">
         	[图片尺寸] 建议：[320px * 172px] </c:if>
         <c:if test="${(af.map.link_type eq 1512) or (af.map.link_type eq 1522) or (af.map.link_type eq 1532) or (af.map.link_type eq 1542) or (af.map.link_type eq 1552) or (af.map.link_type eq 1562) or (af.map.link_type eq 1572)}">
         	[图片尺寸] 建议：[320px * 346px] </c:if>
         <c:if test="${af.map.link_type eq 1600}">[图片尺寸] 建议：[129px * 129px] </c:if>
         </div>
         </td>
      </tr> 
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>链接类型：</td>
        <td colspan="2" width="85%">
        <html-el:select property="pre_number" styleId="pre_number">
        <html-el:option value="">请选择</html-el:option>
        <html-el:option value="1">商品</html-el:option>
        <html-el:option value="2">外部链接</html-el:option>
<%--         <html-el:option value="3">类别</html-el:option> --%>
<%--         <html-el:option value="4">标签</html-el:option> --%>
        </html-el:select>
        </td>
      </tr>
      <tr style="display:none;" id="selectComm">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>商品链接：</td>
        <td colspan="2" width="85%">
         <html-el:hidden property="pre_number2" styleId="pre_number2" />
         <input type="text" name="link_url" id="link_url" maxlength="125" style="width:50%" readonly="readonly" styleClass="webinput" />
        &nbsp;<a class="butbase" style="cursor:pointer;" onclick="getdiscount_comm_names();"><span class="icon-add">选择商品</span></a>
        </td>
      </tr>
      <tr style="display:none;" id="wb_link_url">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>链接URL：</td>
        <td colspan="2" width="85%">
        <html-el:text property="link_url2" styleId="link_url2" maxlength="125" style="width:50%" styleClass="webinput" value="${af.map.link_url}"/>&nbsp;请用"http://"或者"https://"开头</td>
      </tr>
      <tr style="display:none;" id="selectCls">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>选择类别：</td>
        <td colspan="2" width="85%">
        <html-el:hidden property="cls_id" styleId="cls_id"/>
        <html-el:text property="cls_name" styleId="cls_name" readonly="true" onclick="getParClsInfo();" style="cursor:pointer;width:200px;" styleClass="webinput"/>
        </td>
      </tr>
    <tr style="display:none;" id="wb_zt">
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>选择标签：</td>
        <td colspan="2" width="85%">
<%--         <html-el:select property="pre_varchar2" styleId="pre_varchar2"> --%>
        <html-el:select property="link_url3" styleId="link_url3" value="${af.map.pre_varchar2}">
        
        <html-el:option value="">请选择</html-el:option>
        <c:forEach items="${bList}" var="cur">
        	<html-el:option value="${cur.id}">${cur.tag_name}</html-el:option>
        </c:forEach>
        </html-el:select>
        </td>
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
<script type="text/javascript" src="${ctx}/styles/colorpicker/js/jquery.colorpicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">

$(document).ready(function(){
	
	$("#show_win").colorpicker({
	    fillcolor:true,
	    success:function(o,color){
	        $("#title").css("color",color);
	        $("#title_color").val(color);
	    }
	});

	
	$("#title").attr("dataType", "Require").attr("msg", "标题必须填写");
	$("#image_path").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "图片的格式必须是(gif,jpeg,jpg,png)").attr("accept", "gif, jpeg, jpg,png");
	<c:if test="${empty (af.map.image_path)}">
	$("#image_path").attr("require", "true");
	</c:if>
	$("#order_value").attr("dataType", "Number").attr("msg", "排序值必须为正整数");
	$("#pre_number").attr("dataType", "Require").attr("msg", "请选择链接类型");

	<c:if test="${not empty af.map.pre_number}">
	var pre_number = "${af.map.pre_number}";
	
	if(pre_number == 1){
		$("#selectComm").show();
		$("#wb_link_url").hide();
		$("#selectCls").hide();
		$("#wb_zt").hide();
		$("#link_url").attr("dataType", "Require").attr("msg", "请选择商品");
		$("#link_url2").removeAttr("dataType");
		$("#link_url3").removeAttr("dataType");
		$("#cls_name").removeAttr("dataType");
		$("#pre_varchar2").removeAttr("dataType");
	}else if(pre_number == 2){
		$("#wb_link_url").show();
		$("#selectComm").hide();
		$("#selectCls").hide();
		$("#wb_zt").hide();
		$("#link_url2").attr("dataType", "Url2").attr("msg", "链接地址格式不合法,例如：http://baidu.com").attr("require", "true");
		$("#link_url").removeAttr("dataType");
		$("#link_url3").removeAttr("dataType");
		$("#cls_name").removeAttr("dataType");
		$("#pre_varchar2").removeAttr("dataType");
	}else if(pre_number == 3){
		$("#wb_link_url").hide();
		$("#selectComm").hide();
		$("#selectCls").show();
		$("#wb_zt").hide();
		$("#cls_name").attr("dataType", "Require").attr("msg", "请选择类别");
		$("#link_url").removeAttr("dataType");
		$("#link_url2").removeAttr("dataType");
		$("#link_url3").removeAttr("dataType");
		$("#pre_varchar2").removeAttr("dataType");
	}else if(pre_number == 4){
		$("#wb_link_url").hide();
		$("#selectComm").hide();
		$("#selectCls").hide();
		$("#wb_zt").show();
		$("#pre_varchar2").attr("dataType", "Require").attr("msg", "请选择专题");
		$("#link_url3").attr("dataType", "Require").attr("msg", "请选择标签");
		$("#link_url").removeAttr("dataType");
		$("#link_url2").removeAttr("dataType");
		$("#cls_name").removeAttr("dataType");
	}
	</c:if>
	
	
	
	$("#pre_number").change(function(){
		var thisVal = $(this).val();
		if(thisVal != null && thisVal != ''){
			if(thisVal == 1){
				$("#selectComm").show();
				$("#wb_link_url").hide();
				$("#selectCls").hide();
				$("#wb_zt").hide();
				$("#link_url").attr("dataType", "Require").attr("msg", "请选择商品");
				$("#link_url2").removeAttr("dataType");
				$("#link_url3").removeAttr("dataType");
				$("#cls_name").removeAttr("dataType");
				$("#pre_varchar2").removeAttr("dataType");
			}else if(thisVal == 2){
				$("#wb_link_url").show();
				$("#selectComm").hide();
				$("#wb_zt").hide();
				$("#selectCls").hide();
				$("#link_url2").attr("dataType", "Url2").attr("msg", "链接地址格式不合法,例如：http://baidu.com").attr("require", "true");
				$("#link_url").removeAttr("dataType");
				$("#link_url3").removeAttr("dataType");
				$("#cls_name").removeAttr("dataType");
				$("#pre_varchar2").removeAttr("dataType");
			}else if(thisVal == 3){
				$("#wb_link_url").hide();
				$("#selectComm").hide();
				$("#wb_zt").hide();
				$("#selectCls").show();
				$("#cls_name").attr("dataType", "Require").attr("msg", "请选择类别");
				$("#link_url").removeAttr("dataType");
				$("#link_url2").removeAttr("dataType");
				$("#link_url3").removeAttr("dataType");
				$("#pre_varchar2").removeAttr("dataType");
			}else if(thisVal == 4){
				$("#wb_link_url").hide();
				$("#selectComm").hide();
				$("#selectCls").hide();
				$("#wb_zt").show();
				$("#pre_varchar2").attr("dataType", "Require").attr("msg", "请选择标签");
				$("#link_url3").attr("dataType", "Require").attr("msg", "请选择标签");
				$("#link_url").removeAttr("dataType");
				$("#link_url2").removeAttr("dataType");
				$("#cls_name").removeAttr("dataType");
			}
		}
	});
	
	
	
	
	
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
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 1)){
// 			if($("#begin_date").val() >= $("#end_date").val()){
// 				alert("开始时间必须小于失效时间!");
// 				return false;
// 			}
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

function getdiscount_comm_names(){
	var url = "AppBaseLink.do?method=listCommInfo";
	$.dialog({
		title:  "选择商品",
		width:  900,
		height: 600,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
        zIndex:"9999",
		content:"url:"+ encodeURI(url)
	});
}

function getParClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&isPd=true&noSelectFar=true&azaz=" + Math.random();
	$.dialog({
		title:  "选择产品",
		width:  450,
		height: 400,
        lock:true ,
        zIndex:"9999",
		content:"url:"+url
	});
};
</script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>