<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
	<jsp:include page="_view.jsp" flush="true" />
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
<script type="text/javascript">//<![CDATA[
                                          
function kdcx(){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do?method=getDeliveryInfo",
		data:"order_id=${af.map.id}",
		dataType: "json",
		error: function(request, settings) {alert("系统错误，请联系管理员！");},
		success: function(result) {
			if(result.ret == 1){
				$.dialog({
					title:  "物流信息查询",
					width:  560,
					height: 280,
			        left: '100%',
			        top: '100%',
			        drag: false,
			        resize: false,
			        max: false,
			        min: false,
					content:"url:" + result.msg
				});
			}else{
				$.jBox.alert(result.msg, '提示');
			}
			
		}
	});	
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
