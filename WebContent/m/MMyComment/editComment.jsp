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
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id"/>
    <html-el:hidden property="order_id" styleId="order_id" />
    <html-el:hidden property="link_id" styleId="link_id" />
    <html-el:hidden property="comm_tczh_id" styleId="comm_tczh_id" />
    <html-el:hidden property="comm_type" styleId="comm_type" />
    <html-el:hidden property="comm_score" styleId="comm_score" />
    <html-el:hidden property="comm_level" styleId="comm_level" />
    <html-el:hidden property="order_type" styleId="order_type" />
    <html-el:hidden property="tip" styleId="tip" />
    <div class="set-site">
      <ul class="formUl">
        <li><span>商品评分：</span>
	       <div id="comm_good_rate"></div>
        </li>
        <li><span>购买心得：</span>
         <html-el:text property="comm_experience" maxlength="100" styleId="comm_experience" />
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
	        <html-el:hidden property="base_files${vs.count}" styleId="base_files${vs.count}" value="${cur.save_path}"/>
	        <div class="files-warp" id="base_files${vs.count}_warp">
	          <div class="btn-files"> <span>添加附件</span>
	            <input id="base_files${vs.count}_file" type="file" name="base_files${vs.count}_file" value="${ctx}/${cur.save_path}"/>
	          </div>
	        </div>
	        </div>
		 </c:forEach>
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
	
	
	$("#comm_good_rate").raty({
		path:"${ctx}/scripts/rate/img/",
		start: ${af.map.comm_score},
		click: function(score, evt) {
			$("#comm_score").val(score);
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
		if(Validator.Validate(f0, 1)){
			 Common.loading();
			 f0.submit();
			return true;
		}
	});
});
//]]></script>
</body>
</html>
