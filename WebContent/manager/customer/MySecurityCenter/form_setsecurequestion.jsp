<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />

</head>
<body>
<div class="mainbox mine">
<html-el:form action="/customer/MySecurityCenter.do" styleClass="ajaxForm">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
    <c:forEach var="cur" items="${userSecurityList}" varStatus="vs">
       <tr>
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>问题${vs.count}：</td>
         <td>
         <html-el:select property="question_id" styleId="question_id_${vs.count}" style="width:200px;" value="${cur.question_id}">
          <html-el:option value="">请选择...</html-el:option>
            <c:forEach var="cur2" items="${base3200DataList}" varStatus="vs2">
             <html-el:option value="${cur2.id}">${cur2.type_name}</html-el:option>
            </c:forEach>
         </html-el:select>
         </td>
       </tr>
       <tr>
         <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>答案：</td>
         <td><html-el:text property="answer_name" styleId="answer_name_${vs.count}" maxlength="50" style="width:200px" styleClass="webinput" value="${cur.answer_name}"/></td>
       </tr>
      </c:forEach>
       <tr>
        <td style="text-align:center" colspan="2">
          <button class="bgButtonFontAwesome" type="button" id="btn_submit"><i class="fa fa-save"></i>保 存</button>
       </td>
      </tr>
    </table>
</html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
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
		if (Validator.Validate(f0, 3)) {
			
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
				alert("所选问题不能相同");
				return false;
			}
			$.jBox.tip("数据提交中...", 'loading');
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=modifySecurequestion",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							$.jBox.tip(data.msg, "success");
							window.setTimeout(function () {
								returnTo();
							}, 1000);
						} else {
							$.jBox.tip(data.msg, "info");
						}
					}
				});	
			}, 1000);
			return true;
		}
		return false;
	});
});

function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
	
}
//]]></script>
</body>
</html>
