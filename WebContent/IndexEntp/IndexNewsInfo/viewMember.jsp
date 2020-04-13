<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<meta charset="UTF-8">
	<title>${view_title}</title>
	<meta name="description" content="">
    <meta name="keywords" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<!-- css -->
	<link rel="stylesheet" type="text/css" href="css/common.css"/>
	<link rel="stylesheet" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/owl.carousel.css"/>
	<link rel="stylesheet" type="text/css" href="css/owl.theme.css"/>
	<!--[if lt IE 9]>
    <script src="js/html5shiv.min.js"></script>
    <script src="js/respond.min.js"></script>
    <![endif]--> 
</head>
<body>
	<!--导航条-->
	<div class="header">
		<jsp:include page="../_header.jsp"  flush="true"/>
		
	</div>
	<!--导航条-->
	<!--新闻中心详情开始-->
    <div class="wrap showWrap">
		<div class="col_banner about_banner bgSize" style="background-image:url(${ctx}/styles/indexEntp/images/news_banner.jpg);">
	      <div class="inner_index w1200">
	        <h4>会员体系</h4>
	        <p class="en">MEMBERSHIP SERVICE SYSTEM</p>
	      </div>
	    </div>
        <div class="w1200 show">

        <div class="show_txt">
          <h3 class="show_txt_t">${entity.title }</h3>
         
          <div class="show_txt_p">
          ${entity.map.content }
          </div>
        </div>
      </div>
	</div>
   <!--新闻中心详情结束-->
	<!--底部-->
  		 <!--新闻中心结束-->
	
 		<jsp:include page="../_footer.jsp" flush="true"/>
  		
	<!--底部--> 		
	<!-- js -->
	<script src="js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="js/fullpagecode.min.js"></script>
	<script type="text/javascript" src="js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="js/owl.carousel.min.js"></script>
	<!--<script type="text/javascript" src="js/style.js"></script>-->
</body>
</html>