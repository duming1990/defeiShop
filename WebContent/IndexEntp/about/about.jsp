<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
    <meta charset="utf-8">
    <title>关于我们</title>
    <meta name="description" content="">
    <meta name="keywords" content="">
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css"/>
	<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.carousel.css"/>
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.theme.css"/>
   
</head>
<body>
    <!--导航条-->
	<div class="header">
		<jsp:include page="../_header.jsp"  flush="true"/>
	</div>
	<!--导航条-->
    <!--关于我们开始-->
    <div class="wrap">
    <div class="col_banner about_banner bgSize" style="background-image:url(${ctx}/styles/indexEntp/images/gywm_banner.jpg);">
    	<div class="inner_index w1200">
	        <h4>关于我们</h4>
	        <p class="en">ABOUT US</p>
	    </div>
    </div>
    <c:forEach items="${baseLink10150List}" var="cur">
	    <div class="about_t clearfix"  style="background: url(${ctx}/${cur.image_path }) no-repeat center top;">
	      <div class="about_tit fr"><h3>${cur.title}</h3><p>Group Profile</p></div>
	      <div class="about_txt fr">
	      	<p>
	      		${cur.content}
	      	</p>
	      </div>
	    </div>
    </c:forEach>
    
    
    <div class="about_s clearfix">
    <c:forEach items="${baseLink10160List}" var="cur">
      <dl class="dlo">
        <p></p>
        <dt ><img alt="${cur.title}" src="${ctx}/${cur.image_path}"></dt>
        
        <dd>
          <span> ${cur.title}</span>
          <p> ${cur.content}</p>
        </dd>

      </dl>
      </c:forEach>
    </div>
    
    <div class="about_f w1080">
      <div class="about_tit"><h3>总部环境</h3><p>The company environment</p></div>
      <c:forEach items="${baseLink10170List}" var="cur">
      <div class="x">
        <img title="总部环境" alt="总部环境" src="${ctx}/${cur.image_path}">
      </div>
      </c:forEach>
    </div>
    <div class="about_event col_banner"> 
      <div class="event-line-main">
      	<div class="about_tit"><h3>大事记</h3><p> chronicle of events</p></div>
          <div class="event-line-warp">
            <div class="event-list-main">
                <ul>
                <c:forEach items="${baseLink10180List}" var="cur">
                    <li data-content='${cur.content}' data-src="${ctx}/${cur.image_path}"><i>${cur.title }</i></li>
                </c:forEach>
                </ul>
            </div>
            <a href="javascript:void(0)" class="event-p-n prev"></a>
            <a href="javascript:void(0)" class="event-p-n next"></a>
          </div>
        </div>

        <div class="event-axis"></div>
        <div class="event-txt">
          <i></i>
          <div class="img"></div>
          <p class="p"></p>
        </div>
    </div>
    </div>  
   <!--关于我们结束-->
   <!--底部-->
	  		
	<jsp:include page="../_footer.jsp" flush="true"/>
	  		
   	<script src="${ctx}/styles/indexEntp/js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/fullpagecode.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/main.js"></script>
</body>
</html>