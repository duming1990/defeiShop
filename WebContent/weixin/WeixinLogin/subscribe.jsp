<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"/>
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta content="telephone=no" name="format-detection"/>
<meta name="wap-font-scale" content="no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${header_title}</title>
<jsp:include page="../../m/_public_in_head.jsp" flush="true" />
<style type="text/css">
.news-title{font-size:.4rem;line-height:.5rem;font-weight: bold;}
</style>
</head>
<body>
<jsp:include page="../../m/_header.jsp" flush="true" />	
<div class="w">
<div class="section-detailbox">
 <section class="title">
 <h1 class="news-title">长按二维码关注公众号</h1>
  <div class="subinfo">
    <div class="fr"></div>
    <div class="fl">
    	<span style="padding-right:0.2rem;">来源：${app_name}</span>
   	</div>
  </div>
  <img src="${ctx}/styles/imagesPublic/qr_code.png" width="100%" alt="长按二维码关注公众号" />
  </section>
</div>
</div>
<jsp:include page="../../m/_footer.jsp" flush="true" />
<script type="text/javascript"> 

</script> 
</body>
</html>

