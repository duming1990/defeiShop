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
	<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/show/css/public.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/core.css">
	<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css"  />
	<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css"  />
    
</head>
<body>
    <!--导航条-->
	<div class="header">
			<div class="header_n clearfix">
				<div class="logo fl"><a href="index.html"><img src="${ctx}/styles/indexEntp/images/logo.png" alt="logo" width="200" height="57"></a></div>
				<ul class="fl clearfix">
					
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">首页</span></a> </li>
          			<li class="navbar__item-w "><a href="${ctx}/manager/admin/BaseLinkNew.do?method=about" class="navbar__item"><span class="nav-label">关于我们</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">核心业务</span></a> </li>
          			<li class="navbar__item-w "><a href="${ctx}/manager/admin/BaseLinkNew.do?method=news" class="navbar__item"><span class="nav-label">新闻中心</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">客户服务</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">联系我们</span></a> </li>
          			<li class="navbar__item-w "><a class="navbar__item navbar__item-hots"><span class="nav-label">人才招聘</span></a> </li>
				</ul>
				<div class="tel fr">
					<img src="${ctx}/styles/indexEntp/images/tel.png" alt="tel" width="33" height="33">
					400-807-9909
				</div>
			</div>
			
			<span class="line"></span>
		</div>
	<!--导航条-->
    <!--关于我们开始-->
     <div class="wrap">
    <div class="col_banner about_banner bgSize" style="background-image:url(${ctx}/styles/indexEntp/images/gywm_banner.jpg);">
    	<div class="inner_index w1200" >
	        <h4 style="color: white;">关于我们</h4>
	        <p class="en" style="color: white;">ABOUT US</p>
	    </div>
    </div>
    
	    <div class="about_t clearfix"  style="background: url(${ctx}/${cur.image_path }) no-repeat center top;">
	  <a  style="cursor:pointer" class="beautybg" onclick="editFloor(10150);">编辑</a>
	      <div class="about_tit fr"><h3>${cur.title}</h3><p>Group Profile</p></div>
	      <div class="about_txt fr">
	      	<p>
	      		企业简介
	      	</p>
	      </div>
	    </div>
    
    
    <div class="about_s clearfix">
    <div class="section" >
	   		<a  style="cursor:pointer" class="beautybg" onclick="editFloor(10160);">编辑</a>
	  </div>
    <c:forEach items="${baseLink10160List}" var="cur">
      <dl class="dlo">
        <p></p>
        <dt style="background-image: url(${ctx}/${cur.image_path });"></dt>
        <dd>
        <p>
          ${cur.content}
          </p>
        </dd>
      </dl>
      </c:forEach>
    </div>
    
    
    <div class="about_f w1080">
	   		<a  style="cursor:pointer" class="beautybg" onclick="editFloor(10170);">编辑</a>
      <div class="about_tit"><h3>总部环境</h3><p>The company environment</p></div>
      <c:forEach items="${baseLink10170List}" var="cur">
      <div class="x">
        <img title="总部环境" alt="总部环境" src="${ctx}/${cur.image_path}">
      </div>
      </c:forEach>
    </div>
    
    <div class="about_event col_banner"> 
    <a  style="cursor:pointer" class="beautybg" onclick="editFloor(10180);">编辑</a>
      <div class="event-line-main">
      	<div class="about_tit"><h3>大事记</h3><p> chronicle of events</p></div>
          <div class="event-line-warp">
            <div class="event-list-main">
                <ul>
                <c:forEach items="${baseLink10180List}" var="cur">
                    <li data-content='${cur.content}' data-src="${ctx}/${cur.image_path}"><i>${cur.title}</i></li>
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
	  		

	  			<div class="footer_o clearfix">
	  				<div class="footer_o_o fl">
	  					<img src="${ctx}/styles/indexEntp/images/logo.png" alt="logo" width="200" height="57">
	  				</div>
	  				<div class="footer_o_t fl">
	  					<p>全国热线</p>
	  					<span>400-696-4500</span>
	  				</div>
	  				<div class="footer_o_t footer_o_s fl">
	  					<p>市场合作</p>
	  					<span>gongweijian@qidada.com</span>
	  				</div>
	  				<div class="footer_o_f fr">
	  					<img src="${ctx}/styles/indexEntp/images/index_ewm.png" alt="ewm">
	  				</div>
	  			</div>
	  			<div class="footer_t">
	  				网站备案：京ICP备16057408号-1 版权所有：中财华商集团版权所有COPYRIGHT     All rights reserved 
	  			</div>
	  		
	  		
	<script src="${ctx}/styles/indexEntp/js/jquery-1.10.2.min.js"></script>
   	<script src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/fullpagecode.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/main.js"></script>
		<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
	<script type="text/javascript">
	function editFloor(link_type){
		var url = "${ctx}/manager/admin/About.do?&link_type="+link_type+"&method=baseLinkList";
		
		$.dialog({
			title:  "编辑",
			width:  1600,
			height:700,
			padding: 0,
			max: false,
	        min: false,
	        fixed: true,
	        lock: true,
			content:"url:"+ encodeURI(url),
// 	  		close:function(){  
// 	             location.reload(true);
// 	            }  

		});
	}
	
	</script>
</body>
</html>