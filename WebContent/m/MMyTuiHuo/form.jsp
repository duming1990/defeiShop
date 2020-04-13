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
  <html-el:form action="MMyOrderReturn.do" method="post" styleClass="ajaxForm">
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="queryString" />
    <html-el:hidden property="order_detail_id" styleId="order_detail_id" value="${orderInfoDetails.id}"/>
    <html-el:hidden property="order_type" value="${af.map. order_type}" />
    <html-el:hidden property="order_id" value="${orderInfoDetails.order_id}"/>
    <html-el:hidden property="mod_id"  styleId="mod_id" value="${af.map.mod_id}"/>
    <div class="set-site">
      <ul>
        <li><span style="width: 30%" class="grey-name">商品名称：</span>
        <div>
        <c:forEach items="${list}" var="item">
			${item.comm_name}</br>
        </c:forEach>
       </div>
        </li>
        <li><span style="width: 30%" class="grey-name">换货原因：</span>
        <script type="text/javascript">showTuiHuoReasone('${af.map.return_type}');</script>
        </li>
        <li><span style="width: 30%" class="grey-name">申请时间：</span><span class="grey-name" style="width: 70%"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></span></li>
        <li><span style="width: 30%" class="grey-name">审核时间：</span><span class="grey-name" style="width: 70%"><fmt:formatDate value="${af.map.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" /></span></li>
        <li><span style="width: 30%" class="grey-name">审核说明：</span><span class="grey-name">${af.map.return_desc }</span></li>
<!--         <li id="_price"> <span style="width: 25%" class="grey-name"><span style="color: #F00;">*</span>退款金额：</span> -->
<%--           <input style="width: 75%" name="price" id="price" value="${orderInfoDetails.good_price * orderInfoDetails.good_count}" type="text" autocomplete="off" maxlength="38" class="buy_input"> --%>
<!--         </li> -->
        <li> <span style="width: 30%" class="grey-name"><span style="color: #F00;">*</span>反馈内容：</span>
          <input style="width: 68%" name="content" id="content"   type="text" autocomplete="off" maxlength="256" class="buy_input">
        </li>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit">保存</a> </div>
  </html-el:form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	
 	$("#content").attr("datatype", "Require").attr("msg", "请填写反馈内容！");

	
	var f0 = $(".ajaxForm").get(0);
	$("#btn_submit").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
			window.setTimeout(function () {
				$.ajax({
					type: "POST",
					url: "?method=insertOrderReturnMsg&id=${af.map.id}",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						if(data.ret == "1"){
							mui.toast(data.msg);
							window.setTimeout(function () {
								location.href="${ctx}/m/MMyTuiHuo.do?method=list&mod_id=1100500100";
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
