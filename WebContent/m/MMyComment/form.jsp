<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <html-el:form action="/MMyComment.do" enctype="multipart/form-data" styleClass="ajaxForm0">
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="mod_id"/>
    <html-el:hidden property="order_id" styleId="order_id"/>
    <html-el:hidden property="order_details_id" styleId="order_details_id" value="${af.map.id}" />
    <html-el:hidden property="link_id" value="${af.map.comm_id}" styleId="link_id" />
    <html-el:hidden property="comm_tczh_id" styleId="comm_tczh_id" />
    <html-el:hidden property="comm_type"  styleId="comm_type" />
    <html-el:hidden property="comm_score" styleId="comm_score" value="${af.map.comm_score}" />
    <html-el:hidden property="comm_level" styleId="comm_level" />
    <html-el:hidden property="order_type" styleId="order_type" />
    <div class="set-site">
      <ul class="formUl">
      <c:url var="url" value="/m/MEntpInfo.do?id=${af.map.comm_id}" />
      	<li><a onclick="goUrl('${url}')">${af.map.comm_name}<c:if test="${not empty af.map.comm_tczh_name}">[${af.map.comm_tczh_name}]</c:if></a></li>
        <li><span class="grey-name">商品评分：</span>
	       <div id="comm_good_rate"></div>
        </li>
        <li><span class="grey-name">购买心得：</span>
         <html-el:text property="comm_experience" maxlength="100" styleId="comm_experience" />
        </li>
<!--         <li><span class="grey-name">上传评论图片：</span> -->
        <div style="display: inline-flex;">
        <span style="width: 80%;padding-left: 60px;padding-top: 20px;">
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
		</div>
        </li>
      </ul>
    </div>
    <div class="box submit-btn"> 
    <input type="button" class="com-btn" id="btn_submit" value="保存" /></div>
  </html-el:form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rate/min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rate/min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function() {
	
	var btn_name = "上传";
	upload("base_files1", "image", btn_name, "${ctx}");
	
	var btn_name = "上传";
	upload("base_files2", "image", btn_name, "${ctx}");
	
	var btn_name = "上传";
	upload("base_files3", "image", btn_name, "${ctx}");
	
	var btn_name = "上传";
	upload("base_files4", "image", btn_name, "${ctx}");
	
	
	$("#comm_experience").attr("datatype","Limit").attr("min","1").attr("max","100").attr("msg","请填写购买商品心得且在100以内。");
	
	
	$("#comm_good_rate").raty({
		path:"${ctx}/scripts/rate/img/",
		//iconRange: [['star-on-bad.png', 1], ['star-on-bad.png', 2]],
// 		start: ${af.map.comm_score},
		click: function(score, evt) {
			$("#comm_score").val(score);
			$("#hintinfo").html("[感谢您的评价，您的评分为：" + $("#comm_score").val()+"]");
			if(score == 1){
				$("#comm_level").val(3);
			}
			if(score == 2 || score == 3){
				$("#comm_level").val(2);
			}
			if(score == 4 || score == 5){
				$("#comm_level").val(1);
			}
		}
	});
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit").click(function(){
	var order_id = $("#order_id").val();
		if (Validator.Validate(f0, 1)) {
			
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=saveComm&",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							mui.toast(data.msg);
							window.setTimeout(function () {
								location.href="${ctx}/m/MMyComment.do?method=getCommentList&add_user_id="+data.add_user_id+"&tip=1";
// 								location.href="${ctx}/m/MMyOrderDetail.do?method=view&order_id="+order_id+"&from=user";
// 								location.href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=1100500100";
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
