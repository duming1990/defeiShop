<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>触屏版-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content"> 
  <html-el:form action="MMyOrderReturn.do" method="post" styleClass="ajaxForm">
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="queryString" />
    <html-el:hidden property="order_detail_id" styleId="order_detail_id" value="${orderInfoDetails.id}"/>
    <html-el:hidden property="order_type" value="${af.map. order_type}" />
    <html-el:hidden property="order_id" value="${orderInfoDetails.order_id}"/>
    <html-el:hidden property="order_return_id" value="${af.map.order_return_id}"/>
    <html-el:hidden property="mod_id"  styleId="mod_id" value="${af.map.mod_id}"/>
    <div class="set-site">
      <ul>
      <c:if test="${orderInfo.order_state eq 10}">
      <li class="select" id="_expect_return_way">
      <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>期望处理方式：</span>
          <select style="width: 60%" name="expect_return_way" id="expect_return_way" >
            <option value="4">未发货退款</option>
          </select>
        </li>
        </c:if>
        <c:if test="${orderInfo.order_state ne 10}">
        <li class="select" id="_expect_return_way"> <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>期望处理方式：</span>
          <select style="width: 60%" name="expect_return_way" id="expect_return_way" >
            <option value="">请选择...</option>
            <option value="1">退货退款</option>
<!--             <option value="2">换货</option> -->
          </select>
        </li>
        <li class="select" id="_return_type"> <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>退货/换货原因：</span>
          <select style="width: 60%" name="return_type" id="return_type" >
            <option value="">请选择...</option>
            <c:forEach items="${baseDataList}" var="cur">
              <option value="${cur.id }">${cur.type_name }</option>
            </c:forEach>
          </select>
        </li>
        <li id="trFile">
        	<span style="width: 100%" class="grey-name">
	          <div style="float:left;width:50%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files1_img" />
		        <html-el:hidden property="base_files1" styleId="base_files1" />
		        <div class="files-warp" id="base_files1_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files1_file" type="file" name="base_files1_file" accept="image/*" />
		          </div>
		        </div>
		        </div>
		        <div style="float:left;width:50%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files2_img" />
		        <html-el:hidden property="base_files2" styleId="base_files2" />
		        <div class="files-warp" id="base_files2_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files2_file" type="file" name="base_files2_file" accept="image/*" />
		          </div>
		        </div>
		        </div>
		        <div style="float:left;width:50%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files3_img" />
		        <html-el:hidden property="base_files3" styleId="base_files3" />
		        <div class="files-warp" id="base_files3_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files3_file" type="file" name="base_files3_file" accept="image/*" />
		          </div>
		        </div>
		        </div>
		        <div style="float:left;width:50%;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <img src="${img}" width="100" height="100" id="base_files4_img" />
		        <html-el:hidden property="base_files4" styleId="base_files4" />
		        <div class="files-warp" id="base_files4_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="base_files4_file" type="file" name="base_files4_file" accept="image/*" />
		          </div>
		        </div>
		        </div>
          </span>
         </li>
        
<!--         <li class="select" id="_fh_fee" style="display:none"> <span style="width: 50%" class="grey-name"><span style="color: #F00;">*</span>买家是否承担物流费用：</span> -->
<!--           <select style="width: 50%" name="fh_fee" id="fh_fee" > -->
<!--             <option value="0">不承担</option> -->
<!--             <option value="1">承担</option> -->
<!--           </select> -->
<!--         </li> -->
<!--         <li class="select" id="_th_wl_company"> <span class="grey-name"><span style="color: #F00;">*</span>物流公司：</span> -->
<!--           <select  style="width: 60%" name="th_wl_company" id="th_wl_company" > -->
<!--             <option value="">请选择...</option> -->
<%--             <c:forEach items="${wlCompInfoList}" var="cur"> --%>
<%--               <option value="${cur.wl_comp_name}">${cur.wl_comp_name}</option> --%>
<%--             </c:forEach> --%>
<!--           </select> -->
<!--         </li> -->
<!--         <li id="_th_wl_no"> <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>物流单号：</span> -->
<!--           <input style="width: 60%" name="th_wl_no" id="th_wl_no"  type="text" autocomplete="off" maxlength="38" class="buy_input"> -->
<!--         </li> -->
<!--         <li id="_th_wl_fee" style="display:none"> <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>物流费用：</span> -->
<!--           <input style="width: 60%" name="th_wl_fee" id="th_wl_fee"  type="text" value="0" autocomplete="off" maxlength="38" class="buy_input"> -->
<!--         </li> -->
        <li id="_return_link_man"> <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>退货联系人：</span>
          <input style="width: 60%" name="return_link_man" id="return_link_man"  type="text" autocomplete="off" maxlength="38" class="buy_input" value="${return_link_man}">
        </li>
        <li id="_return_tel"> <span style="width: 40%" class="grey-name"><span style="color: #F00;">*</span>退货联系电话：</span>
          <input style="width: 60%" name="return_tel" id="return_tel"  type="text" autocomplete="off" maxlength="38" class="buy_input" value="${return_tel}">
        </li>
        <li> <span style="width: 20%" class="grey-name">说明：</span>
          <input style="width: 80%" name="return_desc" id="return_desc"   type="text" autocomplete="off" maxlength="38" class="buy_input">
        </li>
      </c:if>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit">提交</a> </div>
  </html-el:form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var btn_name = "上传图片";
	upload("base_files1", "image", btn_name, "${ctx}");
	
	var btn_name = "上传图片";
	upload("base_files2", "image", btn_name, "${ctx}");
	
	var btn_name = "上传图片";
	upload("base_files3", "image", btn_name, "${ctx}");
	
	var btn_name = "上传图片";
	upload("base_files4", "image", btn_name, "${ctx}"); 
	
	$("#return_type").attr("datatype", "Require").attr("msg", "请退货退款原因！");
// 	$("#th_wl_company").attr("datatype", "Require").attr("msg", "请选择物流公司！");
//  	$("#th_wl_no").attr("datatype", "Require").attr("msg", "请填写物流单号！");
//  	$("#return_link_man").attr("datatype", "Require").attr("msg", "请填写退货联系人！");
//  	$("#return_tel").attr("datatype", "Require").attr("msg", "请填写退货联系电话！");

// 	$("#expect_return_way").change(function(){
// 		var thisValue = $(this).val();
// 		if(thisValue == 1){
// 			$("#_price").show();
			
// 		}else{
// 			$("#_price").hide();
// 		}
// 	});
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 1)) {			
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=save&",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							mui.toast(data.msg);
							window.setTimeout(function () {
								location.href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=1100500100";
							}, 1000);
						} else {
							mui.toast(data.msg);
						}
					}
				});	
			}, 1000);
			return true;
		}
		return false;
	});
});
//]]></script>
</body>
</html>
