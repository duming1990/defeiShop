<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script> 
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/About.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="link_type" styleId="link_type" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="85%"><html-el:text property="title" styleId="titles" maxlength="125" style="width:480px" styleClass="webinput" value="${af.map.title}"/>
        </td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>图片：</td>
        <td colspan="2" width="85%">
         <c:set var="img" value="${ctx}/images/no_img.gif" />
        <c:if test="${not empty af.map.image_path}">
          <c:set var="img" value="${ctx}/${af.map.image_path}@s400x400" />
        </c:if>
        <img src="${img}" height="100" id="image_path_img" />
          <html-el:hidden property="image_path" styleId="image_path" value="${af.map.image_path}"/>
        <div class="files-warp" id="image_path_warp">
          <div class="btn-files"> <span>添加附件</span>
            <input id="image_path_file" type="file" name="image_path_file" />
          </div>
          <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
        </div>
          <div style="color:rgb(241, 42, 34); padding: 5px;"> 
              <c:if test="${af.map.link_type eq 10150}">[图片尺寸] 建议：[456px * 370px] </c:if>
         	  <c:if test="${af.map.link_type eq 10160}">[图片尺寸] 建议：[85px * 85px] </c:if>
         	  <c:if test="${af.map.link_type eq 10170}">[图片尺寸] 建议：[1326px * 636px] </c:if>
         	  <c:if test="${af.map.link_type eq 10180}">[图片尺寸] 建议：[540px * 320px] </c:if>
         	  <c:if test="${af.map.link_type eq 10190}">[图片尺寸] 建议：[1165px * 588px] </c:if>
         </div>
         </td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>内容</td>
        <td colspan="2" width="85%"><html-el:text property="content" styleId="content" maxlength="200" style="width:50%" styleClass="webinput" value="${af.map.content}"/>&nbsp;</td>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap">排序值：</td>
        <td colspan="2" width="85%"><html-el:text property="order_values" styleId="order_values" maxlength="4" size="4" styleClass="webinput" value="${af.map.order_value}"/>
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
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript"><!--//<![CDATA[

$(document).ready(function(){
	$("#titles").attr("dataType", "Require").attr("msg", "标题必须填写");
// 	 <c:if test="${af.map.link_type eq 90}">
//    </c:if>
    $("#file_hidden_file").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "图片的格式必须是(gif,jpeg,jpg,png)").attr("accept", "gif, jpeg, jpg,png");
	$("#order_values").attr("dataType", "Number").attr("msg", "排序值必须为正整数");

	var btn_name = "上传图片";
	if ("" != "${af.map.image_path}") {
		btn_name = "重新上传图片";
	}
	upload("image_path", "image&dirName=index", btn_name, "${ctx}");
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	})

});

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>