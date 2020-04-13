<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx}/commons/swfupload/style/default.css" type="text/css" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine" style="min-height: 720px;">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <div class="all">
      <button class="bgButtonFontAwesome" type="button"><i class="fa fa-history"></i>买家申请退货退款 </button>
  </div>
  <html-el:form action="/customer/MyOrderReturn.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="order_detail_id" styleId="order_detail_id"/>
    <html-el:hidden property="order_type" />
    <html-el:hidden property="order_id" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
       <th colspan="4">退货退款信息填写</th>
      </tr>
      <tr>
      	<td  width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>期望处理方式：</td>
      	<td colspan="3">
		<html-el:select property="expect_return_way" styleId="expect_return_way" styleClass="webinput" >
           		<html-el:option value="">请选择...</html-el:option>
           		<html-el:option value="1">退货退款</html-el:option>
           		<html-el:option value="2">换货</html-el:option>
<%--            		<html-el:option value="3">仅退款</html-el:option> --%>
        </html-el:select>
		</td>
      </tr>
      <tr id="_return_type">
		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>退换货原因：</td>
		<td colspan="3">
		<html-el:select property="return_type" styleId="return_type" styleClass="webinput" >
           		<html-el:option value="">请选择...</html-el:option>
			<c:forEach items="${baseDataList}" var="cur">
           		<html-el:option value="${cur.id}">${cur.type_name}</html-el:option>
           </c:forEach>
        </html-el:select>
		</td>
	  </tr>
<!--       <tr id="_price"> -->
<!-- 		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>退款金额：</td> -->
<!-- 		<td colspan="3"> -->
<%-- 			<html-el:text property="price" maxlength="10" styleId="price" value="${orderInfoDetails.good_price * orderInfoDetails.good_count}" style="width:200px"/>&nbsp;元 --%>
<!-- 		</td> -->
<!-- 	  </tr> -->
	<tr id="trFile">
        <td nowrap="nowrap" class="title_item">上传图片：</td>
        <td colspan="2">
		        <div style="float:left;width:20%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files1_img" />
		        <html-el:hidden property="base_files1" styleId="base_files1" />
		        <div class="files-warp" id="base_files1_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files1_file" type="file" name="base_files1_file"/>
		          </div>
		        </div>
		        </div>
		        <div style="float:left;width:20%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files2_img" />
		        <html-el:hidden property="base_files2" styleId="base_files2" />
		        <div class="files-warp" id="base_files2_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files2_file" type="file" name="base_files2_file"/>
		          </div>
		        </div>
		        </div>
		        <div style="float:left;width:20%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files3_img" />
		        <html-el:hidden property="base_files3" styleId="base_files3" />
		        <div class="files-warp" id="base_files3_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files3_file" type="file" name="base_files3_file"/>
		          </div>
		        </div>
		        </div>
		        <div style="float:left;width:20%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files4_img" />
		        <html-el:hidden property="base_files4" styleId="base_files4" />
		        <div class="files-warp" id="base_files4_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files4_file" type="file" name="base_files4_file"/>
		          </div>
		        </div>
		        </div>
		        <div style="float:left;width:20%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files5_img" />
		        <html-el:hidden property="base_files5" styleId="base_files5" />
		        <div class="files-warp" id="base_files5_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files5_file" type="file" name="base_files5_file"/>
		          </div>
		        </div>
		        </div>
		        说明：[图片比例]建议：1:1 [图片尺寸] 建议：[600px * 600px] ，图片大小不超过2M。
        </td>
      </tr>
	  <tr>
		<td width="14%" nowrap="nowrap" class="title_item">说明：</td>
		<td colspan="3">
			<html-el:textarea property="return_desc" styleId="return_desc" style="width:400px"/> 
		</td>
	  </tr>
<!--       <tr id="_fh_fee"> -->
<!-- 		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>买家是否承担物流费用：</td> -->
<!-- 		<td colspan="3"> -->
<%-- 			<html-el:select property="fh_fee" styleClass="webinput" > --%>
<%--            		<html-el:option value="0">否</html-el:option> --%>
<%--            		<html-el:option value="1">是</html-el:option> --%>
<%--         </html-el:select> --%>
<!-- 		</td> -->
<!-- 	  </tr> -->
	  <tr id="_th_wl_company">
		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>物流公司：</td>
		<td colspan="3">
			<html-el:select property="th_wl_company" styleId="th_wl_company" styleClass="webinput">
	         <html-el:option value="">请选择</html-el:option>
	         <c:forEach items="${wlCompInfoList}" var="cur">
                <html-el:option value="${cur.wl_comp_name}">${cur.wl_comp_name}</html-el:option>
             </c:forEach>
             </html-el:select>
		</td>
	  </tr>
	  <tr id="_th_wl_no">
		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>物流单号：</td>
		<td colspan="3">
			<html-el:text property="th_wl_no" maxlength="10" styleId="th_wl_no" style="width:200px"/>
		</td>
	  </tr>
<!--       <tr id="_th_wl_fee"> -->
<!-- 		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>物流费用：</td> -->
<%-- 		<td colspan="3"><html-el:text property="th_wl_fee" maxlength="10" styleId="th_wl_fee" style="width:200px"/></td> --%>
<!-- 	  </tr> -->
      <tr id="_return_link_man">
		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>退货联系人：</td>
		<td colspan="3"><html-el:text property="return_link_man" maxlength="10" styleId="return_link_man" style="width:200px" value="${return_link_man}"/></td>
	  </tr>
      <tr id="_return_tel">
		<td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>退货联系电话：</td>
		<td colspan="3"><html-el:text property="return_tel" maxlength="10" styleId="return_tel" style="width:200px" value="${return_tel}"/></td>
	  </tr>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
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
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/swfupload.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/handlers.js"></script>

<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	var btn_name = "上传图片";
	upload("base_files1", "image", btn_name, "${ctx}");
	
	var btn_name = "上传图片";
	upload("base_files2", "image", btn_name, "${ctx}");
	
	var btn_name = "上传图片";
	upload("base_files3", "image", btn_name, "${ctx}");
	
	var btn_name = "上传图片";
	upload("base_files4", "image", btn_name, "${ctx}");
	
	var btn_name = "上传图片";
	upload("base_files5", "image", btn_name, "${ctx}");
	
	
	
	$("#expect_return_way").attr("datatype", "Require").attr("msg", "请选择期望处理方式！");
	$("#return_type").attr("datatype", "Require").attr("msg", "请选择退货退款原因！");
	$("#return_type").attr("datatype", "Require").attr("msg", "请退货退款原因！");
	$("#th_wl_company").attr("datatype", "Require").attr("msg", "请选择物流公司！");
	$("#th_wl_no").attr("datatype", "Require").attr("msg", "请填写物流单号！");
	$("#return_link_man").attr("datatype", "Require").attr("msg", "请填写退货联系人！");
	$("#return_tel").attr("datatype", "Require").attr("msg", "请填写退货联系电话！");
	
	
});

// 提交
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
}); 

                                                                                
//]]></script>

</body>
</html>
