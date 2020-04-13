<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/customer/BaseComminfoTags.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="saveRank" />
    <html-el:hidden property="comm_id" value="${af.map.id}"/>
    <html-el:hidden property="tag_id" value="${af.map.tag_id}"/>
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <th colspan="2">排序</th>
      </tr>
      <tr>
        <td class="title_item">标签名：</td>
        <td>${tag_name}</td>
      </tr>
      <tr>
        <td class="title_item">商品名：</td>
        <td>${comm_name}</td>
      </tr>
      <tr>
        <td class="title_item">排序值：</td>
        <td><html-el:text property="order_value"  maxlength="4" size="4" styleClass="webinput" styleId="order_value"  style="width: 20%;" value="${order_value}"/>
          值越大，显示越靠前，范围：0-9999 </td>
      </tr>
       <tr>
        <td colspan="2" style="text-align:center">
        <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
        </td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
var f = document.forms[0];

$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");

// 提交
$("#btn_submit").click(function(){
	
	if(Validator.Validate(f, 3)){
		var order_value=$("#order_value").val()
			comm_id=${af.map.id}
			tag_id=${af.map.tag_id};
			
		$.ajax({
			type: "POST",
			url: "${ctx}/manager/customer/BaseComminfoTags.do?order_value="+order_value+"&",
			data: $(f).serialize(),
			dataType: "json",
			error: function(request, settings) {},
			success: function(data) {
				
				if (data.ret == 1) {
					returnTo();
				} else{
					alert(data.msg)
				}
			}
		});
	}
});

function returnTo(){
	var api = frameElement.api, W = api.opener;
	W.refreshPage();
	api.close();
}
</script>
</body>
</html>
