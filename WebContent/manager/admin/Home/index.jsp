<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/main_all.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/load_iconfont.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/widget.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css?v=20161130"  />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-nav.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/index.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css"  />
<style type="">
.pg-floor-new-v2 .content__body{width:1206px;}
.main_image li{width:100%!important;}
.deal-tile__cover img{margin:0 auto;}
</style>
</head>
<body  class="pg-floor-new-v2 forIndex" style="position: static;">
<jsp:include page="_header.jsp" flush="true" />
<div class="bdw">
  <div class="cf">
    <div class="content">
      <div class="content__header">
         <div class="flicking_con">
	        <div class="flicking_inner">
	            <a>1</a>
			</div>
         </div>
        <div class="main_image">
	        <div class="areasSmall" style="padding-left:510px;padding-top:5px;z-index:999;"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=40" class="beautybg">编辑轮播图</a></div>				
	        <ul>	
	           <li><span class="img_3" style="background:url('${ctx}/styles/huizhuan/images/main_1.jpg');background-repeat:no-repeat;background-position:50% 0px;"></span></li>
	        </ul>
			<a href="javascript:;" id="btn_prev"></a>
			<a href="javascript:;" id="btn_next"></a>
        </div>
<!--        <div class="membercont"> -->
<!--     	<div class="memberbox"> -->
<!--          <div class="member_top"> -->
<!--            <div class="right"> -->
<%--             <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=90" class="beautybg">编辑</a></div> --%>
<!-- 	         <div class="list"> -->
<%-- 	             <img class="img1" src="${ctx}/styles/index/images/new_index/img23.jpg" /> --%>
<!-- 	         </div> -->
<!-- 	         <div class="list"> -->
<%-- 	             <img class="img1" src="${ctx}/styles/index/images/new_index/img24.jpg" /> --%>
<!-- 	         </div> -->
<!--           </div> -->
<!--          </div> -->
<!--         </div> -->
<!--        </div> -->
	<div class="membercont">
	    	<div class="memberbox">
	        <div class="member_top">
	        <h1 class="current">商城公告</h1>
	        <h1 class="member_top_h1_2">新闻资讯</h1>
	          <ul>
	          <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=90" class="beautybg">编辑公告</a></div>
	          <div class="areas" style="left:100px;"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=91" class="beautybg">编辑资讯</a></div>
	          	<li><a href="#" title="商城2016年春节运营公告">商城2016年春节运</a></li>
	          	<li><a href="#" title="公告：雨雪天来临，物流延迟！">公告：雨雪天来临，物流延</a></li>
	          	<li><a href="#" title="商城APP上线啦">商城APP上线啦</a></li>
	          	<li><a href="#" title="江西崇义县考察团慕名一探究竟——“农村电商模式”为何遍布全国？" >江西崇义县考察团慕名一探</a></li>
	          	<li><a href="#" title="重庆市城口县农村电商考察团一行莅临调研考察">重庆市城口县农村电商考察</a></li>
	          	<li><a href="#" title="媒体再关注！黑龙江电视台对电商“富裕模式”进行专访" > 媒体再关注！黑龙江电视台</a></li>
	          </ul>
	          </div>
	        </div>
	  </div>
   </div>
    <div class="container">
<!--      <div class="div1"> -->
<!--         <div class="left"> -->
<!--             <p class="p1">挑夫爆品</p> -->
<!--             <p class="p1">限时秒杀</p> -->
<!--             <div class="p2"></div> -->
<!--             <div class="p3">本场距离结束还剩</div> -->
<!--             <div class="p4"> -->
<!--                 <p class="p5">01<span></span></p> -->
<!--                 <p class="p5">56<span></span></p> -->
<!--                 <p class="p5">53<span></span></p> -->
<!--             </div> -->
<!--         </div> -->
<!--         <div class="right" id="swiper2"> -->
<!--             <div class="swiper-container swiper-container-horizontal"> -->
<!--                 <div class="swiper-wrapper"> -->
<!--                 <div class="swiper-slide swiper-slide-duplicate swiper-slide-active"> -->
<!--                         <div class="list"> -->
<%--                             <img class="img" src="${ctx}/styles/index/images/new_index/img2.jpg"> --%>
<!--                             <p class="p1">粒上皇 无添加板栗200g</p> -->
<!--                             <p class="p2">会员立减</p> -->
<!--                             <p class="p3">￥43.8</p> -->
<!--                             <p class="p4">￥21.9</p> -->
<!--                         </div> -->
<!--                         <div class="list"> -->
<%--                             <img class="img" src="${ctx}/styles/index/images/new_index/img2.jpg"> --%>
<!--                             <p class="p1">粒上皇 无添加板栗200g</p> -->
<!--                             <p class="p2">会员立减</p> -->
<!--                             <p class="p3">￥43.8</p> -->
<!--                             <p class="p4">￥21.9</p> -->
<!--                         </div> -->
<!--                         <div class="list"> -->
<%--                             <img class="img" src="${ctx}/styles/index/images/new_index/img2.jpg"> --%>
<!--                             <p class="p1">粒上皇 无添加板栗200g</p> -->
<!--                             <p class="p2">会员立减</p> -->
<!--                             <p class="p3">￥43.8</p> -->
<!--                             <p class="p4">￥21.9</p> -->
<!--                         </div> -->
<!--                         <div class="list"> -->
<%--                             <img class="img" src="${ctx}/styles/index/images/new_index/img2.jpg"> --%>
<!--                             <p class="p1">粒上皇 无添加板栗200g</p> -->
<!--                             <p class="p2">会员立减</p> -->
<!--                             <p class="p3">￥43.8</p> -->
<!--                             <p class="p4">￥21.9</p> -->
<!--                         </div> -->
<!--                         <div class="list"> -->
<%--                             <img class="img" src="${ctx}/styles/index/images/new_index/img2.jpg"> --%>
<!--                             <p class="p1">粒上皇 无添加板栗200g</p> -->
<!--                             <p class="p2">会员立减</p> -->
<!--                             <p class="p3">￥43.8</p> -->
<!--                             <p class="p4">￥21.9</p> -->
<!--                         </div> -->
<!--                     </div> -->
<!--                 </div> -->
<!--                 <div class="swiper-button-prev swiper-button-white" style="top:35%;"></div> -->
<!--                 <div class="swiper-button-next swiper-button-white" style="top:35%;"></div> -->
<!--             </div> -->
<!--         </div> -->
<!--     </div> -->
    
    <div class="div2">
        <div class="list list1">
            <div class="title">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=200" class="beautybg">编辑</a></div>
                <p class="p1">推荐榜</p>
                <p class="p2"></p>
                <p class="p3">专属你的购物指南</p>
            </div>
            <div class="list1_item">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=210" class="beautybg">编辑</a></div>
                <div class="item active">手机</div>
                <div class="item">平板电视</div>
                <div class="item">厨房小店</div>
                <div class="item">个护健康</div>
                <div class="item">运动鞋包</div>
            </div>
            <div class="list1_swiper" id="swiper3">
                <div class="swiper-container swiper-container-horizontal">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=220" class="beautybg">编辑</a></div>
                    <div class="swiper-wrapper">
                    <div class="swiper-slide swiper-slide-duplicate swiper-slide-next swiper-slide-duplicate-prev">
                            <div class="list1_shop">
                                <p class="p1">1</p>
                                <img class="img" src="${ctx}/styles/index/images/new_index/img15.jpg">
                                <p class="p2">Apple iPhone 8 Plus (A1864) 64GB 深空灰色 移动联通电信4G手机</p>
                            </div>
                            <div class="list1_shop">
                                <p class="p1">2</p>
                                <img class="img" src="${ctx}/styles/index/images/new_index/img15.jpg">
                                <p class="p2">Apple iPhone 8 Plus (A1864) 64GB 深空灰色 移动联通电信4G手机</p>
                            </div>
                            <div class="list1_shop">
                                <p class="p1">3</p>
                                <img class="img" src="${ctx}/styles/index/images/new_index/img15.jpg">
                                <p class="p2">Apple iPhone 8 Plus (A1864) 64GB 深空灰色 移动联通电信4G手机</p>
                            </div>
                        </div>
                      </div>
                </div>
            </div>
        </div>
        <div class="list list2">
            <div class="title">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=300" class="beautybg">编辑</a></div>
                <p class="p1">村商品</p>
                <p class="p2"></p>
                <p class="p3">专属你的购物指南</p>
            </div>
            <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=310" class="beautybg">编辑</a></div>
            <img class="list2_img" src="${ctx}/styles/index/images/new_index/img29.jpg">
            <div class="list2_swiper" id="swiper4">
                <div class="swiper-container swiper-container-horizontal">
                    <div class="swiper-wrapper">
                    <div class="swiper-slide swiper-slide-duplicate swiper-slide-prev swiper-slide-duplicate-next">
                        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=320" class="beautybg">编辑</a></div>
                            <div class="list2_shop">
                                <img class="img" src="${ctx}/styles/index/images/new_index/img5.jpg">
                                <p class="p1">移动联通电信4G手机移动联通电信4G手机</p>
                                <p class="p2">￥19.9</p>
                            </div>
                            <div class="list2_shop">
                                <img class="img" src="${ctx}/styles/index/images/new_index/img6.jpg">
                                <p class="p1">移动联通电信4G手机移动联通电信4G手机</p>
                                <p class="p2">￥19.9</p>
                            </div>
                            <div class="list2_shop">
                                <img class="img" src="${ctx}/styles/index/images/new_index/img7.jpg">
                                <p class="p1">移动联通电信4G手机移动联通电信4G手机</p>
                                <p class="p2">￥19.9</p>
                            </div>
                            <div class="list_msg">
                                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=330" class="beautybg">编辑</a></div>
                                <p class="p1">村新闻村新闻</p>
                                <p class="p2">
                                    村新闻村新闻村新闻村村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻村新闻</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="list list3">
            <div class="title">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=400" class="beautybg">编辑</a></div>
                <p class="p1">品牌活动</p>
                <p class="p2"></p>
                <p class="p3">专属你的购物指南</p>
            </div>
            <div class="list3_swiper" id="swiper5">
                <div class="swiper-container swiper-container-horizontal">
                       <div class="swiper-wrapper">
                       <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=410" class="beautybg">编辑</a></div>
                       <div class="swiper-slide swiper-slide-duplicate swiper-slide-active">
                            <img class="img1" src="${ctx}/styles/index/images/new_index/img3.jpg">
                            <img class="img1" src="${ctx}/styles/index/images/new_index/img4.jpg">
                        </div>
                       </div>
                </div>
            </div>
        </div>
    </div>
     </div>
      <!--------产品内容--------->
      <div class="content__body">
        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=70" class="beautybg">编辑楼层</a></div>
        <div class="areas" style="left:400px;" title="刷新楼层"><span id="reload" onclick="location.reload()" style="cursor:pointer;" class="beautybg">刷新楼层</span></div>
        <div class="component-category-list mt-component--booted" style="padding-top:40px;">
          <div class="category-list log-mod-viewed" id="category-floor-div"> 
          <c:forEach items="${base70LinkList}" var="cur" varStatus="vs">
            <!------产品楼层${vs.count}----->
            <c:if test="${cur.pre_number eq 1}">
            <div class="category-floor">
              <div id="floor-category--meishi" class="category-floor__head cf">
                <ul class="sub-categories">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a></div>
                  <li class="sub-categories__cell "> <a class="link">生活店铺</a> </li>
                  <li class="sub-categories__cell "> <a class="link">鲜花婚庆</a> </li>
                  <li class="sub-categories__cell "> <a class="link">婚纱摄影</a> </li>
                  <li class="sub-categories__cell "> <a class="link">个性写真</a> </li>
                  <li class="sub-categories__cell "> <a class="link">球类用具</a> </li>
                  <li class="sub-categories__cell "> <a class="link">法律咨询</a> </li>
                </ul>
                <a class="title"> 
                ${vs.count}F
                ${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 10, ""))} </a> </div>
              <div class="category-floor__body cf deal-list--floor-new">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a></div>
                  <div class="deal-tile first" style="height: 576px;"> 
                   <img class="J-webp" width="241" height="576" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/flowleft.jpg" /> 
                  </div>
                  <div class="deal-tile"> 
                  <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a></div>
                  <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span> </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                  <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span>  </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="柴掌柜串串"> <span class="xtitle">【宿州路】柴掌柜串串</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0.3</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="川味蟹王" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="川味蟹王"> <span class="xtitle">【和平路】川味蟹王</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>9.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                 </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span>  </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span> </p>
                </div>
              </div>
              <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="margin-top:5px;">
			    <ul class="common-banner__sheets mt-slider-sheet-container">
			      <li class="common-banner__sheet cf mt-slider-sheet" style="opacity: 1;">
			        <div class="color color--left" style="background:#d0f1f8"></div>
			        <div class="color color--right" style="background:#d0f1f8"></div>
			        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}08&par_id=${cur.id}&par_son_type=8" class="beautybg">编辑</a></div>
			        <a class="common-banner__link">
			        <img alt="【多城市】1元起吃喝玩乐" src="${ctx}/styles/index/images/up_imgs/aa6cd606986692900582816b97fce70747794.jpg" width="980" height="60" /></a> </li>
			    </ul>
			  </div>
            </div>
            </c:if>
            <!------产品楼层${vs.count}结束-----> 
           <c:if test="${cur.pre_number eq 2}"> 
            <div class="category-floor">
              <div id="floor-category--meishi" class="category-floor__head cf">
                <ul class="sub-categories">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a></div>
                  <li class="sub-categories__cell "> <a class="link">生活店铺</a> </li>
                  <li class="sub-categories__cell "> <a class="link">鲜花婚庆</a> </li>
                  <li class="sub-categories__cell "> <a class="link">婚纱摄影</a> </li>
                  <li class="sub-categories__cell "> <a class="link">个性写真</a> </li>
                  <li class="sub-categories__cell "> <a class="link">球类用具</a> </li>
                  <li class="sub-categories__cell "> <a class="link">法律咨询</a> </li>
                </ul>
                <a class="title"> 
                ${vs.count}F
                ${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 10, ""))} </a> </div>
              <div class="category-floor__body cf deal-list--floor-new">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a></div>
                  <div class="deal-tile first" style="height: 576px;"> 
                   <img class="J-webp" width="241" height="576" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/flowleft.jpg" /> 
                  </div>
                  <div class="deal-tile" style="width:481px;"> 
                  <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a></div>
                  <a class="deal-tile__cover"> <img class="J-webp" width="440" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img11.jpg" /> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span> </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                <div class="deal-tile"> 
                 <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}04&par_id=${cur.id}&par_son_type=4" class="beautybg">编辑</a></div>
                 <a class="deal-tile__cover"> 
                 <img class="J-webp" width="200" height="200" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                 </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="柴掌柜串串"> <span class="xtitle">【宿州路】柴掌柜串串</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0.3</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="川味蟹王" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="川味蟹王"> <span class="xtitle">【和平路】川味蟹王</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>9.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                 </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span>  </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span> </p>
                </div>
              </div>
              <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="margin-top:5px;">
			    <ul class="common-banner__sheets mt-slider-sheet-container">
			      <li class="common-banner__sheet cf mt-slider-sheet" style="opacity: 1;">
			        <div class="color color--left" style="background:#d0f1f8"></div>
			        <div class="color color--right" style="background:#d0f1f8"></div>
			        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}08&par_id=${cur.id}&par_son_type=8" class="beautybg">编辑</a></div>
			        <a class="common-banner__link">
			        <img alt="【多城市】1元起吃喝玩乐" src="${ctx}/styles/index/images/up_imgs/aa6cd606986692900582816b97fce70747794.jpg" width="980" height="60" /></a> </li>
			    </ul>
			  </div>
            </div>
            </c:if>
            
           <c:if test="${cur.pre_number eq 3}"> 
            <div class="category-floor">
              <div id="floor-category--meishi" class="category-floor__head cf">
                <ul class="sub-categories">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a></div>
                  <li class="sub-categories__cell "> <a class="link">生活店铺</a> </li>
                  <li class="sub-categories__cell "> <a class="link">鲜花婚庆</a> </li>
                  <li class="sub-categories__cell "> <a class="link">婚纱摄影</a> </li>
                  <li class="sub-categories__cell "> <a class="link">个性写真</a> </li>
                  <li class="sub-categories__cell "> <a class="link">球类用具</a> </li>
                  <li class="sub-categories__cell "> <a class="link">法律咨询</a> </li>
                </ul>
                <a class="title"> 
                ${vs.count}F
                ${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 10, ""))} </a> </div>
              <div class="category-floor__body cf deal-list--floor-new">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a></div>
                  <div class="deal-tile first" style="height: 576px;"> 
                   <img class="J-webp" width="241" height="576" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/flowleft.jpg" /> 
                  </div>
                  <div class="deal-tile" style="width:481px;"> 
                  <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a></div>
                  <a class="deal-tile__cover"> <img class="J-webp" width="440" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img11.jpg" /> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span> </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                  <div class="deal-tile" style="width:481px;"> 
                  <a class="deal-tile__cover"> <img class="J-webp" width="440" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img11.jpg" /> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span> </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                <div class="deal-tile"> 
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}04&par_id=${cur.id}&par_son_type=4" class="beautybg">编辑</a></div>
                <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                 </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span>  </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span> </p>
                </div>
              </div>
              <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="margin-top:5px;">
			    <ul class="common-banner__sheets mt-slider-sheet-container">
			      <li class="common-banner__sheet cf mt-slider-sheet" style="opacity: 1;">
			        <div class="color color--left" style="background:#d0f1f8"></div>
			        <div class="color color--right" style="background:#d0f1f8"></div>
			        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}08&par_id=${cur.id}&par_son_type=8" class="beautybg">编辑</a></div>
			        <a class="common-banner__link">
			        <img alt="【多城市】1元起吃喝玩乐" src="${ctx}/styles/index/images/up_imgs/aa6cd606986692900582816b97fce70747794.jpg" width="980" height="60" /></a> </li>
			    </ul>
			  </div>
            </div>
            </c:if>
            
            <c:if test="${cur.pre_number eq 4}"> 
            <div class="category-floor">
              <div id="floor-category--meishi" class="category-floor__head cf">
                <ul class="sub-categories">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a></div>
                  <li class="sub-categories__cell "> <a class="link">生活店铺</a> </li>
                  <li class="sub-categories__cell "> <a class="link">鲜花婚庆</a> </li>
                  <li class="sub-categories__cell "> <a class="link">婚纱摄影</a> </li>
                  <li class="sub-categories__cell "> <a class="link">个性写真</a> </li>
                  <li class="sub-categories__cell "> <a class="link">球类用具</a> </li>
                  <li class="sub-categories__cell "> <a class="link">法律咨询</a> </li>
                </ul>
                <a class="title"> 
                ${vs.count}F
                ${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 10, ""))} </a> </div>
              <div class="category-floor__body cf deal-list--floor-new">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a></div>
                   <div class="deal-tile first" style="height: 576px;"> 
                   <img class="J-webp" width="241" height="576" alt="柴掌柜串串" src="/styles/index/images/up_imgs/flowleft.jpg"> 
                  </div>
                <div class="deal-tile" style="height: 576px;"> 
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a></div>
                <a class="deal-tile__cover" style="padding: 125px 15px 0;"> <img class="J-webp" width="220" height="260" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/img14.jpg"> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="柴掌柜串串"> <span class="xtitle">【宿州路】柴掌柜串串</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0.3</strong></span></p>
                </div>
                <div class="deal-tile"> 
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}04&par_id=${cur.id}&par_son_type=4" class="beautybg">编辑</a></div>
                <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="川味蟹王" src="${ctx}/styles/index/images/up_imgs/img12.jpg"> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="川味蟹王"> <span class="xtitle">【和平路】川味蟹王</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>9.9</strong></span></p>
                </div>
                <div class="deal-tile" style="width:481px;"> 
                  <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}05&par_id=${cur.id}&par_son_type=5" class="beautybg">编辑</a></div>
                  <a class="deal-tile__cover"> <img class="J-webp" width="440" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img11.jpg"> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span> </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg"> 
                 </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg"> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg"> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
              </div>
              <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="margin-top:5px;">
			    <ul class="common-banner__sheets mt-slider-sheet-container">
			      <li class="common-banner__sheet cf mt-slider-sheet" style="opacity: 1;">
			        <div class="color color--left" style="background:#d0f1f8"></div>
			        <div class="color color--right" style="background:#d0f1f8"></div>
			        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}08&par_id=${cur.id}&par_son_type=8" class="beautybg">编辑</a></div>
			        <a class="common-banner__link">
			        <img alt="【多城市】1元起吃喝玩乐" src="${ctx}/styles/index/images/up_imgs/aa6cd606986692900582816b97fce70747794.jpg" width="980" height="60" /></a> </li>
			    </ul>
			  </div>
            </div>
            </c:if>
            
            
            <c:if test="${cur.pre_number eq 5}"> 
            <div class="category-floor">
              <div id="floor-category--meishi" class="category-floor__head cf">
                <ul class="sub-categories">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a></div>
                  <li class="sub-categories__cell "> <a class="link">生活店铺</a> </li>
                  <li class="sub-categories__cell "> <a class="link">鲜花婚庆</a> </li>
                  <li class="sub-categories__cell "> <a class="link">婚纱摄影</a> </li>
                  <li class="sub-categories__cell "> <a class="link">个性写真</a> </li>
                  <li class="sub-categories__cell "> <a class="link">球类用具</a> </li>
                  <li class="sub-categories__cell "> <a class="link">法律咨询</a> </li>
                </ul>
                <a class="title"> 
                ${vs.count}F
                ${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 10, ""))} </a> </div>
              <div class="category-floor__body cf deal-list--floor-new">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a></div>
                   <div class="deal-tile first" style="height: 576px;"> 
                   <img class="J-webp" width="241" height="576" alt="柴掌柜串串" src="/styles/index/images/up_imgs/flowleft.jpg"> 
                  </div>
                <div class="deal-tile" style="height: 576px;"> 
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a></div>
                <a class="deal-tile__cover" style="padding: 125px 15px 0;"> <img class="J-webp" width="220" height="260" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/img14.jpg"> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="柴掌柜串串"> <span class="xtitle">【宿州路】柴掌柜串串</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0.3</strong></span></p>
                </div>
                
                <div style="width:482px;height: 576px;float:left;">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}04&par_id=${cur.id}&par_son_type=4" class="beautybg">编辑</a></div>
                <div class="deal-tile" style="width:481px;"> 
                  <a class="deal-tile__cover"> <img class="J-webp" width="440" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img11.jpg"> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span> </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                
                <div class="deal-tile"> 
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}05&par_id=${cur.id}&par_son_type=5" class="beautybg">编辑</a></div>
                <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg"> 
                 </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg"> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                
                </div>
                
               <div class="deal-tile" style="height: 576px;"> <a class="deal-tile__cover" style="padding: 125px 15px 0;"> <img class="J-webp" width="220" height="260" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/img14.jpg"> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="柴掌柜串串"> <span class="xtitle">【宿州路】柴掌柜串串</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0.3</strong></span></p>
                </div>
              </div>
              <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="margin-top:5px;">
			    <ul class="common-banner__sheets mt-slider-sheet-container">
			      <li class="common-banner__sheet cf mt-slider-sheet" style="opacity: 1;">
			        <div class="color color--left" style="background:#d0f1f8"></div>
			        <div class="color color--right" style="background:#d0f1f8"></div>
			        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}08&par_id=${cur.id}&par_son_type=8" class="beautybg">编辑</a></div>
			        <a class="common-banner__link">
			        <img alt="【多城市】1元起吃喝玩乐" src="${ctx}/styles/index/images/up_imgs/aa6cd606986692900582816b97fce70747794.jpg" width="980" height="60" /></a> </li>
			    </ul>
			  </div>
            </div>
            </c:if>
            
            <c:if test="${cur.pre_number eq 6}">
            <div class="category-floor">
              <div id="floor-category--meishi" class="category-floor__head cf">
                <ul class="sub-categories">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&par_id=${cur.id}&par_son_type=1" class="beautybg">编辑</a></div>
                  <li class="sub-categories__cell "> <a class="link">生活店铺</a> </li>
                  <li class="sub-categories__cell "> <a class="link">鲜花婚庆</a> </li>
                  <li class="sub-categories__cell "> <a class="link">婚纱摄影</a> </li>
                  <li class="sub-categories__cell "> <a class="link">个性写真</a> </li>
                  <li class="sub-categories__cell "> <a class="link">球类用具</a> </li>
                  <li class="sub-categories__cell "> <a class="link">法律咨询</a> </li>
                </ul>
                <a class="title"> 
                ${vs.count}F
                ${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 10, ""))} </a> </div>
              <div class="category-floor__body cf deal-list--floor-new">
                <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&par_id=${cur.id}&par_son_type=3" class="beautybg">编辑</a></div>
                  <div class="deal-tile first" style="height: 576px;"> 
                   <img class="J-webp" width="241" height="576" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/flowleft.jpg" /> 
                  </div>
                  <div class="deal-tile"> 
                  <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&par_id=${cur.id}&par_son_type=2" class="beautybg">编辑</a></div>
                  <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span> </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                  <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                  </a>
                    <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span>  </a> </h3>
                    <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                  </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="柴掌柜串串" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="柴掌柜串串"> <span class="xtitle">【宿州路】柴掌柜串串</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0.3</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="川味蟹王" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="川味蟹王"> <span class="xtitle">【和平路】川味蟹王</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>9.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="赤道火锅" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                 </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="赤道火锅"> <span class="xtitle">【合肥】赤道火锅</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>0</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span></a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span> </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span></p>
                </div>
                <div class="deal-tile"> <a class="deal-tile__cover"> <img class="J-webp" width="200" height="200" alt="熙顺紫菜包饭" src="${ctx}/styles/index/images/up_imgs/img12.jpg" /> 
                </a>
                  <h3 class="deal-tile__title"> <a class="w-link" title="熙顺紫菜包饭"> <span class="xtitle">【5店通用】熙顺紫菜包饭</span>  </a> </h3>
                  <p class="deal-tile__detail"> <span class="price">¥<strong>4.9</strong></span> </p>
                </div>
              </div>
              <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="margin-top:5px;">
			    <ul class="common-banner__sheets mt-slider-sheet-container">
			      <li class="common-banner__sheet cf mt-slider-sheet" style="opacity: 1;">
			        <div class="color color--left" style="background:#d0f1f8"></div>
			        <div class="color color--right" style="background:#d0f1f8"></div>
			        <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}08&par_id=${cur.id}&par_son_type=8" class="beautybg">编辑</a></div>
			        <a class="common-banner__link">
			        <img alt="【多城市】1元起吃喝玩乐" src="${ctx}/styles/index/images/up_imgs/aa6cd606986692900582816b97fce70747794.jpg" width="980" height="60" /></a> </li>
			    </ul>
			  </div>
            </div>
            </c:if>
            
            </c:forEach>
          </div>
        </div>
        <!-------楼层工具条开始-------->
        <div class="component-floor-elevator mt-component--booted">
          <div class="J-elevator floor-elevator" style="top:0px;z-index:999;" id="floor-elevator">
            <ul class="elevator">
            <div class="areas"><a href="${ctx}/manager/admin/BaseLink.do?mod_id=${af.map.mod_id}&link_type=80" class="beautybg">编辑</a></div>
              <li class="elevator__floor "><a class="link current">1F<span>生活</span> </a> </li>
              <li class="elevator__floor "><a class="link">2F<span>口碑</span> </a> </li>
              <li class="elevator__floor "><a class="link">3F<span>购物</span> </a> </li>
              <li class="elevator__floor "><a class="link">4F<span>汽车</span> </a> </li>
              <li class="elevator__floor "><a class="link">5F<span>娱乐</span> </a> </li>
              <li class="elevator__floor "><a class="link">6F<span>教育</span> </a> </li>
              <li class="elevator__floor "><a class="link">7F<span>美容</span> </a> </li>
              <li class="elevator__floor "><a class="link">8F<span>手机数码</span> </a> </li>
              <li class="elevator__floor "><a class="link">9F<span>其他</span> </a> </li>
            </ul>
          </div>
        </div>
        <!-------楼层工具条结束--------> 
      </div>
      <!--------产品内容结束---------> 
    </div>
  </div>
  <!---------首页内容结束--------> 
</div>
<jsp:include page="_footer.jsp" />
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[
	$("a.beautybg").colorbox({width:"90%", height:"80%", iframe:true});
	function genIndexHtml(){ 
		var submit = function (v, h, f) {
		    if (v == true) {
		     $.jBox.tip("更新中...", 'loading');
		     window.setTimeout(function () { 
		     $.post("?method=genStaticIndex",{},function(data){
		     $.jBox.tip(data, "success");
		     });
		     }, 1000);
		    } 
		    return true;
		};
		myConfirm("确定静态化【首页】吗？",submit);
		}

	
function refreshCls(){
	 $.jBox.tip("更新中...", 'loading');
     window.setTimeout(function () { 
     $.post("?method=refreshCls",{},function(data){
     $.jBox.tip(data, "success");
     });
     }, 1000);
}	
	
function myConfirm(tip, submit){ 
		$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}

$(".site-mast__user-w li").each(function(index){
	$(this).mouseover(function(){
		$(this).addClass("dropdown--open");
	}).mouseout(function(){
		$(this).removeClass("dropdown--open");
	});
});
$(".basic-info li").each(function(index){
	$(this).mouseover(function(){
		$(this).addClass("dropdown--open dropdown--open-app");
	}).mouseout(function(){
		$(this).removeClass("dropdown--open dropdown--open-app");
	});
});
$(".search-box__tabs-container").mouseover(function(){
	$(this).addClass("search-box__tabs-container--over");
}).mouseout(function(){
	$(this).removeClass("search-box__tabs-container--over");
});
$(".search-box__tabs li").each(function(index){
	if(index == 0){
		$(this).mouseover(function(){
			$(this).parent().parent().addClass("search-box__tabs-container--over");
		}).mouseout(function(){
			$(this).parent().parent().removeClass("search-box__tabs-container--over");
		});
	}
	$(this).click(function(){
		$(this).addClass("search-box__tab--current").siblings().removeClass("search-box__tab--current");
		$(this).insertBefore($(this).prev());
		$(this).parent().parent().removeClass("search-box__tabs-container--over");
		if(index == 0){
			$(".search-box__input").attr("placeholder","请输入商品名称、地址等");
		}else{
			$(".search-box__input").attr("placeholder","请输入店铺名称、地址等");
		}
	});
});

var options = {
serviceUrl : "${ctx}/CsAjax.do?method=getCommInfoListForSeach&&htype=" + $("input[name='htype']").val(),
maxHeight : 245,
width: 402,
delimiter : /(,|;)\s*/,
pageCount : 20,
animate : true,
forCommInfoList: true,
onSelect : function(value, data) {
	  location.href = "${ctx}/Search.do?htype=" + $("input[name='htype']").val() + "&keyword=" + value;
},
deferRequestBy : 0, 
params : {country : "Yes"}
};
var se = $("#keyword").autocomplete(options);


$("#ctrl-prv").click(function(){
	var silderCount = parseInt($("#silderCount").text());
	var liLength = $("#scrollListUl li").length;
	$("#scrollListUl li").each(function(index){
		if(silderCount ==(index + 1) ){
			if((silderCount-1) > 0){
			    $(this).prev().fadeIn().siblings().fadeOut();
				$("#silderCount").text(silderCount - 1);
			}else{
				$("#scrollListUl li").last().fadeIn().siblings().fadeOut();
				$("#silderCount").text(liLength);
			}
			return;
		}
	});
});

$("#ctrl-next").click(function(){
	var silderCount = parseInt($("#silderCount").text());
	var liLength = $("#scrollListUl li").length;
	$("#scrollListUl li").each(function(index){
		if(silderCount ==(index + 1) ){
			if(silderCount < liLength){
			    $(this).next().fadeIn().siblings().fadeOut();
				$("#silderCount").text(silderCount + 1);
			}else{
				$("#scrollListUl li").first().fadeIn().siblings().fadeOut();
				$("#silderCount").text(1);
			}
			return;
		}
	});
});

//]]></script>
</body>
</html>
