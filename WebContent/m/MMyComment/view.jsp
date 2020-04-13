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
<style type="text/css">
.textarea-new{
    width: 80%;
    height: 20vh;
    border: 1px solid #e2e2e2;
    border-radius: 0.2rem;
}
</style>
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
<form action="/m/MMyAccount" enctype="multipart/form-data" method="post" class="ajaxForm0">
    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="method" value="save" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}"/>
    <div class="set-site">
      <ul class="formUl">
      	<c:url var="url" value="/m/MEntpInfo.do?id=${af.map.link_id}" />
      	<li><a onclick="goUrl('${url}')">${af.map.comm_name}<c:if test="${af.map.comm_tczh_name}">[${af.map.comm_tczh_name}]</c:if></a></li>
        <li><span>商品评分：</span>
	       <div>
	       	<c:forEach step="1" end="${af.map.comm_score}" begin="1"><img src="${ctx}/scripts/rate/img/star-on.png" class="comm_good_rate"/></c:forEach>
	       </div>
        </li>
        <li><span>购买心得：</span><span>${af.map.comm_experience }</span>
        </li>
        <li><span>上传评论图片：</span>
        <div style="display: inline-flex;">
        <c:forEach items="${baseFilesList}" var="cur" varStatus="vs">
	        <div style="width:20%;">
	        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
	        <c:if test="${not empty cur.save_path}">
		        <c:set var="img" value="${ctx}/${cur.save_path}@s400x400" />
		    </c:if>
	        <img src="${img}" width="90%" id="base_files${vs.count}_img" />
	        </div>
		 </c:forEach>
		</div>
        </li>
        <c:forEach items="${commentInfoSonList}" var="cur">
         <li><span style="color: #999;">
         <c:if test="${cur.is_entp eq 1}">${cur.entp_name}</c:if>
         <c:if test="${cur.is_entp eq 0}">${cur.add_user_name}</c:if>
         	:</span>${cur.content}</li>
        </c:forEach>
      </ul>
    </div>
    <div class="box submit-btn"> 
<%--     <input type="hidden" name="par_id" id="par_id" value="${af.map.id}"> --%>
<!--     <input type="button" class="com-btn" id="btn_submit" value="保存" /></div> -->
    </form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rate/min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
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
	
	var btn_name = "上传";
	upload("base_files5", "image", btn_name, "${ctx}");
	
	$("#comm_experience").attr("datatype","Limit").attr("min","1").attr("max","100").attr("msg","请填写购买商品心得且在100以内。");
	
	
// 	$("#comm_good_rate").raty({
// 		path:"${ctx}/scripts/rate/img/",
// 		start: ${af.map.comm_score}
// 	});
	var f0 = $(".ajaxForm0").get(0);
	
	$("#btn_submit").click(function(){
		var id = $("#par_id").val();
				if (Validator.Validate(f0, 1)) {
					Common.loading();
				window.setTimeout(function () {
					$.ajax({
						type: "POST",
						url: "?method=saveHuifu&",
						data: $(f0).serialize(),
						dataType: "json",
						error: function(request, settings) {},
						success: function(data) {
							if(data.ret == "1"){
								mui.toast(data.msg);
								window.setTimeout(function () {
									location.href="${ctx}/m/MMyComment.do?method=getEntpCommentList";
//	 								location.href="${ctx}/m/MMyOrder.do?method=list&order_type=${af.map.order_type}&mod_id=1100500100";
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
