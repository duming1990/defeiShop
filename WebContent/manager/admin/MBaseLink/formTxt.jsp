<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/MBaseLink" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="link_type" styleId="link_type" />
    <html-el:hidden property="type" styleId="type" />
    <html-el:hidden property="pre_number_flag" styleId="pre_number_flag" value="${af.map.pre_number}"/>
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <html-el:hidden property="par_son_type" styleId="par_son_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">基本信息</th>
      </tr>
      <c:if test="${af.map.link_type eq 20}">
       <tr>
        <td width="15%" class="title_item">选择一级类别：</td>
        <td colspan="2" width="85%">
        <html-el:text property="cls_name" styleId="cls_name" value="${cls_name}" maxlength="125" style="width:200px" readonly="true" styleClass="webinput" />
        <html-el:hidden property="cls_id" styleId="cls_id" value="${af.map.content}"/>
        &nbsp;<a class="butbase" onclick="selectPdClass()"><span class="icon-search">选择一级类别</span></a>
        </td>
      </tr>
      </c:if>
      <c:if test="${af.map.link_type eq 1100}">
       <tr>
        <td width="15%" class="title_item">选择帮助节点：</td>
        <td colspan="2" width="85%">
        <html-el:hidden property="h_mod_id" styleId="h_mod_id" value="${af.map.content}"/>
        &nbsp;<a class="butbase" onclick="selectHelpInfo()"><span class="icon-search">选择帮助节点</span></a>
        </td>
      </tr>
      </c:if>
      
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="5%"><html-el:text property="title" styleId="title" maxlength="125" style="width:480px" styleClass="webinput" />
        <c:if test="${af.map.link_type eq 90 or af.map.link_type eq 91}">
          <html-el:text property="title_color" styleId="title_color" styleClass="webinput" readonly="true"/>
        </c:if>  
        </td>
      </tr>
      
      <c:if test="${not empty af.map.p_index}">
      <tr>
        <td class="title_item"><font color="red">*</font>开放区域：</td>
        <td colspan="2">
        <div>
        <input name="p_names" maxlength="60" id="p_names0" type="text" class="webinput" style="width: 70px;" value="${af.map.p_name}" readonly="readonly"/>
        <html-el:hidden property="p_index_hidden" styleId="p_index_hidden0"  value="${af.map.p_index}"/>
        &nbsp;
        <a class="butbase" style="cursor:pointer;" onclick="openChildForPindex();" ><span class="icon-search">选择</span></a>
        </div>
        </td>
      </tr>
      <tr>
        <td class="title_item"><font color="red">*</font>是否纳入全国：</td>
        <td colspan="2">
         <html-el:select property="is_quanguo" styleId="is_quanguo">
           <html-el:option value="0">否</html-el:option>
           <html-el:option value="1">是</html-el:option>
         </html-el:select>
        </td>
      </tr>
      </c:if>
       <c:if test="${af.map.link_type ne 1100}">
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>链接URL：</td>
        <td colspan="2" width="85%"><html-el:text property="link_url" styleId="link_url" maxlength="125" style="width:50%" styleClass="webinput" />&nbsp;请用"http://"或者"https://"开头</td>
      </tr>
      </c:if>
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
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/colorPicker/mColorPicker.min.js"></script> 
<script type="text/javascript"><!--//<![CDATA[

$(document).ready(function(){
	$("#title_color").attr({"hex":"true"}).mColorPicker({imageFolder: '${ctx}/scripts/colorPicker/images/'});
	$("#p_names0").attr("dataType", "Require").attr("msg", "请选择开放区域");
	
	$("#title").attr("dataType", "Require").attr("msg", "标题必须填写");
	$("#link_url").attr("dataType", "Url2").attr("msg", "直接链接地址格式不合法,例如：http://baidu.com").attr("require", "true");
	$("#order_value").attr("dataType", "Number").attr("msg", "排序值必须为正整数");
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	})

});

function selectPdClass() {
	var url = "BaseLink.do?method=listPdClass&link_type=${af.map.link_type}&azaz=" + Math.random();
	$.dialog({
		title:  "选择类别",
		width:  760,
		height: 360,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
}

function selectHelpInfo() {
	var url = "${ctx}/BaseCsAjax.do?method=selectHelpInfo&azaz=" + Math.random();
	$.dialog({
		title:  "选择帮助节点",
		width:  760,
		height: 360,
        lock:true ,
        zIndex:"10000",
		content:"url:"+url
	});
}

function openChildForPindex(){
	var url = "${ctx}/BaseCsAjax.do?method=choosePIndex&dir=admin&num=0&azaz=" + Math.random();
	$.dialog({
		title:  "选择开放区域",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:9999,
		content:"url:"+url
	});
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>