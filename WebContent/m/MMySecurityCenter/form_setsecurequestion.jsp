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
<link href="${ctx}/m/js/date/app1/css/date.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <!--article-->
  <form action="/m/MMyAccount" enctype="multipart/form-data" method="post" class="ajaxForm">
    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="method" value="save" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}"/>
    <input type="hidden" name="queryString" id="queryString" />
    <input type="hidden" name="p_index" id="p_index" value="${p_index}"/>
    <input type="hidden" name="is_secret" id="is_secret" value="${af.map.is_secret}" />
    <div class="set-site">
      <ul>
      <c:forEach var="cur" items="${userSecurityList}" varStatus="vs">
        <li class="select">
        <span style="width: 30%" class="grey-name">问题${vs.count}：</span>
          <select style="width: 70%" name="question_id" id="question_id_${vs.count}" value="${cur.question_id}">
            <option value="">请选择...</option>
            <c:forEach var="cur2" items="${base3200DataList}" varStatus="vs2">
            <option value="${cur2.id}" <c:if test="${cur2.id eq cur.question_id}">selected</c:if>>${cur2.type_name}</option>
            </c:forEach>
          </select>
        </li>
        <li> <span style="width: 30%" class="grey-name">答案：</span>
          <input style="width: 70%" name="answer_name" id="answer_name_${vs.count}" value="${cur.answer_name}" type="text" autocomplete="off" maxlength="38" value="${af.map.real_name}" class="buy_input">
        </li>
        </c:forEach>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保存</a> </div>
  </form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script> 
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#question_id_1").attr("dataType", "Require").attr("msg", "请填写问题一");
	$("#answer_name_1").attr("dataType", "Require").attr("msg", "请填写答案");
	$("#question_id_2").attr("dataType", "Require").attr("msg", "请填写问题二");
	$("#answer_name_2").attr("dataType", "Require").attr("msg", "请填写答案");
	$("#question_id_3").attr("dataType", "Require").attr("msg", "请填写问题三");
	$("#answer_name_3").attr("dataType", "Require").attr("msg", "请填写答案");
	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 1)) {
			
			var arr_question_id=[];
			$("select[id*='question_id']").each(function(){
				arr_question_id.push($(this).val());
			});
			var nary=arr_question_id.sort();
			var has_xiangtong = true;
			for(var i=0;i<arr_question_id.length;i++){
				if (nary[i]==nary[i+1]){
					    has_xiangtong=false;
						break;
					}
			 }
			if(!has_xiangtong){
				mui.toast("所选问题不能相同");
				return false;
			}
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifySecurequestion",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							mui.toast(data.msg);
							window.setTimeout(function () {
								location.href="${ctx}/m/MMySecurityCenter.do?mod_id=1100620100";
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
