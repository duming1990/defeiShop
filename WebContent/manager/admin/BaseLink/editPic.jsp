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
  <html-el:form action="/admin/BaseLink" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="link_type" styleId="link_type" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <html-el:hidden property="par_son_type" styleId="par_son_type" />
    <html-el:hidden property="id" styleId="id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="3">基本信息</th>
      </tr>
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>标题：</td>
        <td colspan="2" width="85%"><html-el:text property="titles" styleId="titles" maxlength="125" style="width:480px" styleClass="webinput" value="${af.map.title}"/>
        </td>
      </tr>
      <c:if test="${af.map.link_type eq 90}">
      <tr>
        <td width="15%" class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>副标题：</td>
        <td colspan="2" width="5%"><html-el:text property="pre_varchar" styleId="pre_varchar" maxlength="125" style="width:480px" styleClass="webinput" />
        </td>
      </tr>
      </c:if>
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
              <c:if test="${af.map.link_type eq 90}">[图片尺寸] 建议：[140px * 140px] </c:if>
              <c:if test="${af.map.link_type eq 10}">[图片尺寸] 建议：[1920px * 60px] </c:if>
         	  <c:if test="${af.map.link_type eq 40}">[图片尺寸] 建议：[1920px * 467px] </c:if>
         	  <c:if test="${af.map.link_type eq 220}">[图片尺寸] 建议：[314px * 80px] </c:if>
         	  <c:if test="${af.map.link_type eq 2100}">[图片尺寸] 建议：[1190px * 300px] </c:if>
         	  <c:if test="${af.map.link_type eq 310}">[图片尺寸] 建议：[358px * 95px] </c:if>
         	  <c:if test="${af.map.link_type eq 410}">[图片尺寸] 建议：[360px * 165px] </c:if>
         	  <c:if test="${af.map.link_type eq 410}">[图片尺寸] 建议：[360px * 165px] </c:if>
         	  <c:if test="${af.map.link_type eq 0}">[图片尺寸] 建议：[286px * 56px] </c:if>
              <c:if test="${af.map.link_type eq 102 or af.map.link_type eq 202 or af.map.link_type eq 302 or af.map.link_type eq 402
           			 or af.map.link_type eq 502 or af.map.link_type eq 602 or af.map.link_type eq 702 or af.map.link_type eq 802 or af.map.link_type eq 902}">[图片尺寸] 建议：[330px * 390px] </c:if>
           			 
             <c:if test="${af.map.link_type eq 103 or af.map.link_type eq 203 or af.map.link_type eq 303 or af.map.link_type eq 403
           			 or af.map.link_type eq 503 or af.map.link_type eq 603 or af.map.link_type eq 703 or af.map.link_type eq 803 or af.map.link_type eq 903}">[图片尺寸] 建议：[238px * 573px] </c:if>
             
              <c:if test="${af.map.link_type eq 104 or af.map.link_type eq 204 or af.map.link_type eq 304 or af.map.link_type eq 404
           			 or af.map.link_type eq 504 or af.map.link_type eq 604 or af.map.link_type eq 704 or af.map.link_type eq 804 or af.map.link_type eq 904}">[图片尺寸] 建议：[580px * 390px] </c:if>
              
              <c:if test="${af.map.link_type eq 105 or af.map.link_type eq 205 or af.map.link_type eq 305 or af.map.link_type eq 405
           			 or af.map.link_type eq 505 or af.map.link_type eq 605 or af.map.link_type eq 705 or af.map.link_type eq 805 or af.map.link_type eq 905}">[图片尺寸] 建议：[289px * 194px] </c:if>
              
              <c:if test="${af.map.link_type eq 106 or af.map.link_type eq 206 or af.map.link_type eq 306 or af.map.link_type eq 406
           			 or af.map.link_type eq 506 or af.map.link_type eq 606 or af.map.link_type eq 706 or af.map.link_type eq 806 or af.map.link_type eq 906}">[图片尺寸] 建议：[290px * 195px] </c:if>
              
              <c:if test="${af.map.link_type eq 108 or af.map.link_type eq 208 or af.map.link_type eq 308 or af.map.link_type eq 408
           			 or af.map.link_type eq 508 or af.map.link_type eq 608 or af.map.link_type eq 704 or af.map.link_type eq 808 or af.map.link_type eq 908}">[图片尺寸] 建议：[1200px * 100px] </c:if>
              
         </div>
         </td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>链接URL：</td>
        <td colspan="2" width="85%"><html-el:text property="link_urls" styleId="link_urls" maxlength="200" style="width:50%" styleClass="webinput" value="${af.map.link_url}"/>&nbsp;请用"http://"或者"https://"开头</td>
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
	 <c:if test="${af.map.link_type eq 90}">
		$("#pre_varchar").attr("dataType", "Require").attr("msg", "副标题必须填写");     
   </c:if>
    $("#file_hidden_file").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "图片的格式必须是(gif,jpeg,jpg,png)").attr("accept", "gif, jpeg, jpg,png");
	$("#link_urls").attr("dataType", "Url2").attr("msg", "直接链接地址格式不合法,例如：http://baidu.com").attr("require", "true");
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