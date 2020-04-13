<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />

</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/BasePdClass" >
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="cls_id" styleId="cls_id" />
    <html-el:hidden property="cls_scope" styleId="cls_scope" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">产品类别基本信息</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>产品类别名称：</td>
        <td colspan="2" width="88%"><html-el:text property="cls_name" styleId="cls_name" maxlength="125" style="width:280px" styleClass="webinput" /></td>
      </tr>
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">父类产品类别名称：</td>
          <td colspan="2" width="88%">
            <html-el:hidden property="par_id" styleId="par_id"/>
            <html-el:hidden property="cls_level" styleId="cls_level"/>
            <html-el:text property="par_name" styleId="par_name" maxlength="125" style="width:280px" styleClass="webinput" onclick="getParClsInfo()" readonly="true"/>
          </td>
        </tr>
        <c:if test="${(af.map.cls_scope eq 20) or (af.map.cls_scope eq 30)}">
        <tr>
          <td width="12%" nowrap="nowrap" class="title_item">链接地址：</td>
          <td colspan="2" width="88%">
            <html-el:text property="cls_url" styleId="cls_url" maxlength="100" style="width:280px" styleClass="webinput"/>
          </td>
        </tr>
        </c:if>
        <c:if test="${(af.map.cls_level eq 1) or (af.map.cls_level eq 2)}">
	        <tr>  
	        <td nowrap="nowrap" class="title_item"><span style="color: #F00" id="span_main_pic">*</span>类别主图：</td>
	        <td colspan="2"><c:set var="img" value="${ctx}/styles/imagesPublic/user_header.png" />
	          <c:if test="${not empty af.map.image_path}">
	            <c:set var="img" value=" ${ctx}/${af.map.image_path}@s400x400" />
	          </c:if>
	          <img src="${img}" height="80" id="image_path_img" />
	          <html-el:hidden property="image_path" styleId="image_path" />
	          <div class="files-warp" id="image_path_warp">
	            <div class="btn-files"> <span>上传主图</span>
	              <input id="image_path_file" type="file" name="image_path_file" />
	            </div>
	            <div class="progress"> <span class="bar"></span><span class="percent">0%</span></div>
	          </div>
	          <c:url var="urlps" value="/IndexPs.do" />
	          <span>
	   			<c:if test="${af.map.cls_level eq 1}">
	          		说明：[图片尺寸]324px * 96px，图片大小不超过2M。
	      		</c:if>
	       		<c:if test="${af.map.cls_level eq 2}">
	          		说明：[图片尺寸]60px * 60px，图片大小不超过2M。
	       		</c:if>
	          <a href="${urlps}" class="label label-danger" target="_blank">[在线编辑图片]</a></span></td>
	      </tr>
        </c:if>
      <tr>
        <td nowrap="nowrap" class="title_item">排序值：</td>
        <td colspan="2"><html-el:text property="order_value" styleId="order_value" maxlength="4" size="4" styleClass="webinput" />
          值越大，显示越靠前。</td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">是否锁定：</td>
        <td colspan="2"><html-el:select property="is_lock" styleId="is_lock">
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>

<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var btn_name = "上传主图";
	if ("" != "${af.map.image_path}") {
		btn_name = "重新上传";
	}
	upload("image_path", "image", btn_name, "${ctx}");
	
	var f = document.forms[0];


	$("#order_value").focus(setOnlyNum2);

	$("#cls_name").attr("dataType", "Require").attr("msg", "请输入产品类别名称");
	$("#par_name").attr("dataType", "Require").attr("msg", "请输入父类产品类别名称");
// 	$("#root_name").attr("dataType", "Require").attr("msg", "请输入根类产品类别名称");
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}
	});
	});

function getParClsInfo() {
	var url = "BasePdClass.do?method=getParClsInfo&cls_scope="  + '${af.map.cls_scope}' + "&isSelectAll=true&azaz=" + Math.random();
		$.dialog({
			title:  "选择类别",
			width:  450,
			height: 400,
	        lock:true ,
			content:"url:"+url
		});
};

function setOnlyNum2() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+]?\d+(?:\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^$/))this.value=0;this.o_value=this.value};
		if(this.value.length == 0) this.value = "0";
	});
	//this.text.selected;
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
