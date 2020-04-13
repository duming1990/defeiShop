<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.carousel.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.theme.css" />
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/jquery.fullPage.css"/> --%>
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css?v=20161130" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css" />
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
	        <h4>核心业务</h4>
	        <p class="en">BUSINESS</p>
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
<!-- js -->
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>

<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.fullPage.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/style2.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>  --%>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
</body>
</html>