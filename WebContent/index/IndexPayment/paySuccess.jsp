<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Keywords" content="${app_name}," />
<title>购物成功 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css"  />
<style type="">
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body class="pg-buy-process" id="cart-check">
<jsp:include page="../../_header_order.jsp" flush="true" />
<div class="bdw">
 <div id="bd" class="cf">
 <div id="content" class="pg-check pg-cart-check">
        <div class="mainbox">
              <div class="finish-box">
              	<p style=" font-size:18px; line-height:40px; font-weight:bold;display:block;">您已支付完成，感谢您在${app_name}消费。</p>
              </div>
            <div class="form-submit" style="text-align:center;">
               <input id="J-order-pay-button" type="submit" class="btn btn-large btn-pay" name="commit" value="关 闭" onclick="window.close();"/>
            </div>
      <div>
    </div>
 </div>
</div>
 </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[

//]]></script>
</body>
</html>