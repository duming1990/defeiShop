<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Description" content="${app_description}" />
<meta name="Keywords" content="${app_name}" />
<meta http-equiv="Cache-Control" content="no-transform" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<meta name="renderer" content="webkit" />
<title>${app_name}</title>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/common.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/index.css?v=20180829" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/main_all.css?v=20180829" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/widget.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-nav.css?v=20180829" />
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css?v=20180516" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.carousel.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.theme.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/load_iconfont.css"  />
<style type="">
.memberboxOrder{top: -258px;border-top:none;height: 257px;overflow:hidden;}
.memberboxOrder .member_top ul li{clear:both;line-height: 35px;height: 35px;}
.memberboxOrder .member_top ul li a{font:400 14px/1.5 "Hiragino Sans GB", "WenQuanYi Micro Hei", tahoma, sans-serif;}
.pg-floor-new-v2 .content__body{width:1200px;}
#wrap2{width:100%!important;}
.deal-list--floor-new .deal-tile__detail{overflow:hidden;}
.deal-list--floor-new .deal-tile__detail .sales{widows:56%;}
.category-floor__head{border-bottom:none!important;}
.deal-list--floor-new .deal-tile:hover{box-shadow: 0 1px 5px #999;z-index: 1;}
.deal-list--floor-new .deal-tile__cover{padding:0px!important;}
.forIndex .content{padding-bottom:5px;}
/* .s-hot a:link, a:visited { */
/* 	color: #999!important; */
/* } */

body{background-color:#ffffff!important;font-family: Microsoft YaHei;}
</style>
</head>
<body class="pg-floor-new-v2 forIndex" style="position: static;">
<jsp:include page="./_header_index.jsp" flush="true" />
<div class="bdw">
  <div class="cf">
    <div class="content">
    <div class="content__header">

    <div class="main_visual" style="position:relative;">
	    <div class="flicking_con">
	        <div class="flicking_inner">
	         <c:forEach var="cur" items="${base40LinkList}" varStatus="vs">
	            <a href="#">${vs.count}</a>
	        </c:forEach>
			</div>
	    </div>
	   <div class="main_image">
	        <ul id="main_imageUl">
	           <li>
	            <c:forEach var="cur" items="${base40LinkList}" varStatus="vs">
	            <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
	             <a href="${cur.link_url}" title="${cur.title}" target="_blank">
	              <span class="img_3" style="background:url('${imgurl}');background-repeat:no-repeat;background-position:50% 0px;margin: 0 auto;width:1200px;"></span>
	              </a>
	            </c:forEach>
	          </li>
	        </ul>
	         <a href="javascript:;" class="btn_left_right" id="btn_prev"><i class="fa fa-chevron-left"></i></a>
	         <a href="javascript:;" class="btn_left_right" id="btn_next"><i class="fa fa-chevron-right"></i></a>
	    </div>
	  </div>
	  
<!-- 	  <div class="membercont"> -->
<!--     	<div class="memberbox"> -->
<!--          <div class="member_top"> -->
<!--            <div class="right"> -->
<%--             <c:forEach var="cur" items="${base90LinkList}" varStatus="vs"> --%>
<%-- 	         <c:set var="imgurl" value="${ctx}/${cur.image_path}"/> --%>
<!-- 	         <div class="list"> -->
<%-- 	           <a href="${cur.link_url}" title="${cur.title}" target="_blank"> --%>
<%-- 	             <img class="img1" src="${imgurl}" /></a> --%>
<!-- 	         </div> -->
<%-- 	       </c:forEach> --%>
<!--           </div> -->
<!--          </div> -->
<!--         </div> -->
<!--        </div> -->
 <div class="membercont" id="membercont" style="opacity: 0.8;">
    	<div class="memberbox">
         <div class="member_top" id="member_top_news" style="background-color: #fff;">
            <h1 class="current">商城公告</h1>
            <h1 class="member_top_h1_2">新闻资讯</h1>
             <ul id="newsContent0">
              <c:forEach var="cur" items="${base90LinkList}" varStatus="vs">
             	<li>
             	 <a href="${cur.link_url}" title="${cur.title}" target="_blank" style="color: ${cur.title_color};">${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 15, ""))}</a>
             	</li>
              </c:forEach>
             </ul>
             <ul style="display:none;" id="newsContent1">
              <c:forEach var="cur" items="${base91LinkList}" varStatus="vs">
             	<li>
             	 <a href="${cur.link_url}" title="${cur.title}" target="_blank" style="color: ${cur.title_color};">${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 15, ""))}</a>
             	</li>
              </c:forEach>
             </ul>
          </div>
        </div>
     </div>
     </div>
     
     <div class="container">
     <c:if test="${not empty commmInfoHdList}">
     <div class="div1">
        <div class="left">
            <p class="p1">挑夫爆品</p>
            <p class="p1">限时秒杀</p>
            <div class="p2"></div>
            <div class="p3">本场距离结束还剩</div>
            <div class="p4">
                <p class="p5" id="t_d">00<span></span></p>
                <p class="p5" id="t_m">00<span></span></p>
                <p class="p5" id="t_s">00<span></span></p>
            </div>
        </div>
        <div class="right" id="swiper2">
            <div class="swiper-container">
                <div class="swiper-wrapper">
                     <c:forEach var="cur" items="${commmInfoHdList}" varStatus="vs"> 
                      <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
                      <div class="list swiper-slide">
                      <a href="${url}" target="_blank">
                         <img class="img" alt="${fn:escapeXml(cur.comm_name)}" src="${ctx}/${cur.main_pic}@s400x400" />
                         <p class="p1">${fn:escapeXml(cur.comm_name)}</p>
                         <p class="p2">会员立减</p>
                         <p class="p3">￥<fmt:formatNumber value="${cur.price_ref}" pattern="0.##" /></p>
                         <p class="p4">￥<fmt:formatNumber value="${cur.sale_price}" pattern="0.##" /></p>
                      </a>   
                      </div>
                     </c:forEach>
                </div>
                <div class="swiper-button-prev swiper-button-white" style="top:35%;"></div>
                <div class="swiper-button-next swiper-button-white" style="top:35%;"></div>
            </div>
        </div>
    </div>
    </c:if>
    <div class="div2">
        <div class="list list1">
            <c:forEach var="cur" items="${base200LinkList}" varStatus="vs">
             <div class="title">
                <p class="p1">${cur.title}</p>
                <p class="p2"></p>
                <p class="p3">${cur.pre_varchar}</p>
             </div>
            </c:forEach> 
            <div class="list1_item">
              <c:forEach var="cur" items="${base210LinkList}" varStatus="vs">
                <div class="item"><a href="${cur.link_url}" title="${cur.title}" target="_blank">${cur.title}</a></div>
              </c:forEach>  
            </div>
            <div class="list1_swiper" id="swiper3">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                    
                       <div class="swiper-slide">
                         <c:forEach var="cur" items="${base220LinkList}" varStatus="vs"  begin="0" end="2">
                            <div class="list1_shop">
                                <p class="p1">${vs.count}</p>
                                <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                                <a href="${cur.link_url}" title="${cur.title}" target="_blank">
                                  <img class="img" src="${imgurl}" />
                                 <%--  <p class="p2">${cur.title}</p> --%>
                               </a> 
                            </div>
                          </c:forEach>  
                        </div>
                        
                        <c:if test="${fn:length(base220LinkList) gt 3}">
                       <div class="swiper-slide">
                         <c:forEach var="cur" items="${base220LinkList}" varStatus="vs"  begin="3" end="5">
                            <div class="list1_shop">
                                <p class="p1">${vs.count + 3}</p>
                                <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                                <a href="${cur.link_url}" title="${cur.title}" target="_blank">
                                  <img class="img" src="${imgurl}"/>
                                  <%-- <p class="p2">${cur.title}</p> --%>
                               </a> 
                            </div>
                          </c:forEach>  
                        </div>
                        </c:if>
                     </div>
                    <div class="swiper-pagination"></div>
                </div>
            </div>
        </div>
        <div class="list list2">
            <c:forEach var="cur" items="${base300LinkList}" varStatus="vs">
             <div class="title">
                <p class="p1">${cur.title}</p>
                <p class="p2"></p>
                <p class="p3">${cur.pre_varchar}</p>
             </div>
            </c:forEach> 
            <c:forEach var="cur" items="${base310LinkList}" varStatus="vs">
             <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
             <a href="${cur.link_url}" title="${cur.title}" target="_blank"><img class="list2_img" src="${imgurl}" /></a>
            </c:forEach>
            <div class="list2_swiper" id="swiper4">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                       <div class="swiper-slide">
                            <c:forEach var="cur" items="${base320LinkList}" varStatus="vs"  begin="0" end="2">
                             <div class="list2_shop">
                                 <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                                <a href="${cur.link_url}" title="${cur.comm_name}" target="_blank">
                                <img class="img" src="${imgurl}">
                                <p class="p1"  style="font-size: 16px">${cur.comm_name}</p>
                                <p class="p2"  style="font-size: 16px">￥<fmt:formatNumber value="${cur.pd_price}" pattern="0.##" /></p>
                                </a>
                             </div>
                            </c:forEach>  
                            <div class="list_msg">
                              <c:forEach var="cur" items="${base330LinkList}" varStatus="vs" begin="0" end="1">
                                <a href="${cur.link_url}" title="${cur.title}" target="_blank">
                                <p class="p1" style="text-align: left">${cur.title}</p>
                                <%-- <p class="p2">${cur.pre_varchar}</p> --%>
                                </a>
                              </c:forEach>  
                            </div>
                        </div>
                        
                         <c:if test="${fn:length(base320LinkList) gt 3}">
                        <div class="swiper-slide">
                            <c:forEach var="cur" items="${base320LinkList}" varStatus="vs"  begin="3" end="5">
                             <div class="list2_shop">
                                 <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                                <a href="${cur.link_url}" title="${cur.title}" target="_blank">
                                <img class="img" src="${imgurl}">
                                <p class="p1" style="font-size: 16px">${cur.title}</p>
                                <p class="p2" style="font-size: 16px">￥<fmt:formatNumber value="${cur.pd_price}" pattern="0.##" /></p>
                                </a>
                             </div>
                            </c:forEach>  
                            <div class="list_msg">
                              <c:forEach var="cur" items="${base330LinkList}" varStatus="vs" begin="2" end="3">
                                <a href="${cur.link_url}" title="${cur.title}" target="_blank">
                                <p class="p1" style="text-align: left">${cur.title}</p>
                                <%-- <p class="p2">${cur.pre_varchar}</p> --%>
                                </a>
                              </c:forEach>  
                            </div>
                        </div>
                        </c:if>
                    </div>
                    <div class="swiper-pagination"></div>
                </div>
            </div>
        </div>
        <div class="list list3">
           <c:forEach var="cur" items="${base400LinkList}" varStatus="vs">
             <div class="title">
                <p class="p1">${cur.title}</p>
                <p class="p2"></p>
                <p class="p3">${cur.pre_varchar}</p>
             </div>
            </c:forEach> 
            <div class="list3_swiper" id="swiper5">
                <div class="swiper-container">
                       <div class="swiper-wrapper">
                         <div class="swiper-slide">
	                      <c:forEach var="cur" items="${base410LinkList}" varStatus="vs" begin="0" end="1">
	                        <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                            <a href="${cur.link_url}" title="${cur.title}" target="_blank"><img class="img1" src="${imgurl}" /></a>
                          </c:forEach> 
                         </div>
                         
                         <c:if test="${fn:length(base410LinkList) gt 2}">
                         <div class="swiper-slide">
                          <c:forEach var="cur" items="${base410LinkList}" varStatus="vs" begin="2" end="3">
                            <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                            <a href="${cur.link_url}" title="${cur.title}" target="_blank"><img class="img1" src="${imgurl}" /></a>
                          </c:forEach> 
                         </div>
                         </c:if>
                       </div>
                     <div class="swiper-pagination"></div>
                </div>
            </div>
        </div>
       </div>
      </div>
      <div class="content__body">
        <div class="component-category-list mt-component--booted" style="padding-top:40px;">
          <div class="category-list log-mod-viewed">
            <c:forEach var="cur" items="${base70LinkList}" varStatus="vs">
            <div class="category-floor" id="contentFloor${vs.index}">
              <div id="floor-category--meishi" class="category-floor__head cf">
                <ul class="sub-categories">
                <c:forEach var="curSon" items="${cur.map.baseLink101List}" varStatus="vs2">
                   <li class="sub-categories__cell">
                   <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="link">${fn:escapeXml(fnx:abbreviate(curSon.title, 2 * 4, ""))}</a></li>
                  </c:forEach>
                </ul>
                <a class="title">${vs.count}F${cur.title}</a> 
              </div>
              
              <c:set var="cur_div_class" value="" />
<!--               楼层为6的时候 修改图片高度 -->
              <c:if test="${cur.pre_number eq 6}"><c:set var="cur_div_class" value="deal-list-6" /></c:if>
              
              <div class="category-floor__body cf deal-list--floor-new ${cur_div_class}">
              
              <c:forEach var="curSon" items="${cur.map.baseLink103List}" varStatus="vs2">
              
              <c:set var="cur_height" value="573px" />
<!--               楼层为6的时候 修改图片高度 -->
              <c:if test="${cur.pre_number eq 6}"><c:set var="cur_height" value="520px" /></c:if>
              <c:set var="cur_img_height" value="573" />
<!--               楼层为6的时候 修改图片高度 -->
              <c:if test="${cur.pre_number eq 6}"><c:set var="cur_img_height" value="522.92" /></c:if>
               <div class="deal-tile first" style="height: ${cur_height}px;border-right:none;"> 
               <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/>
<%--                <a href="${curSon.link_url}" title="${curSon.title}" target="_blank"> --%>
                 <img class="J-webp lazy" width="238" height="${cur_img_height}" data-original="${imgurl}" />
<!--                </a> -->
              </div>
              </c:forEach>
              
              <c:if test="${cur.pre_number eq 1}">
              <c:set var="curSon_len" value="${fn:length(cur.map.baseLink102List)}"  />
              <c:forEach var="curSon" items="${cur.map.baseLink102List}" varStatus="vs2">
			       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
			       <c:if test="${not empty curSon.image_path}">
			       <c:set var="style" value=""  />
			       <!-- 数量大于4，当前小于5  -->
			       <c:if test="${(curSon_len gt 4) and (vs2.count lt 5)}">
			       		<c:set var="botton_none" value="border-bottom:none;"  />
			       </c:if>
			       <c:if test="${(curSon_len gt 4) and (vs2.count gt 4)}">
			       		<c:set var="botton_none" value=""  />
			       </c:if>
			       <c:if test="${(curSon_len gt vs2.count)}">
			       		<c:set var="right_none" value="border-right:none;"  />
			       </c:if>
			        <c:if test="${(vs2.count eq 4) or (vs2.count eq 8)}">
			       		<c:set var="right_none" value=""  />
			       </c:if>
			       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
	                 <div class="deal-tile" style="${botton_none}${right_none}">
	                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
	                     <img class="J-webp lazy" width="238" height="286" data-original="${imgurl}"/>
	                  </a>
	                  </div>
               </c:forEach>
               </c:if>
               
              <c:if test="${cur.pre_number eq 2}">
              <c:set var="curSon_len" value="${fn:length(cur.map.baseLink104List)}"  />
	              <c:forEach var="curSon" items="${cur.map.baseLink102List}" varStatus="vs2">
			       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
			       <c:if test="${not empty curSon.image_path}">
			       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
	                 <div class="deal-tile none-right"  style="width: 477px;border-bottom: none;">
	                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
	                     <img class="J-webp lazy" width="477" height="285" data-original="${imgurl}"/>
	                  </a>
	                  </div>
	              </c:forEach>
              <c:forEach var="curSon" items="${cur.map.baseLink104List}" varStatus="vs2">
			       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
			       <c:if test="${not empty curSon.image_path}">
			       <c:set var="style" value=""  />
			       <c:if test="${(curSon_len gt 2) and (vs2.count lt 3)}">
			       		<c:set var="botton_none" value="border-bottom:none;"  />
			       </c:if>
			       <c:if test="${(curSon_len gt 2) and (vs2.count gt 2)}">
			       		<c:set var="botton_none" value=""  />
			       </c:if>
			       <c:if test="${(curSon_len gt vs2.count)}">
			       		<c:set var="right_none" value="border-right:none;"  />
			       </c:if>
			        <c:if test="${(vs2.count eq 2) or (vs2.count eq 6)}">
			       		<c:set var="right_none" value=""  />
			       </c:if>
			       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
	                 <div class="deal-tile" style="${botton_none}${right_none}" title="${vs2.count gt 2}">
	                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
	                     <img class="J-webp lazy" width="238" height="286" data-original="${imgurl}"/>
	                  </a>
	                  </div>
               </c:forEach>
               </c:if>
               
              <c:if test="${cur.pre_number eq 3}">
              <c:set var="curSon_len" value="${fn:length(cur.map.baseLink102List)}"  />
              <c:forEach var="curSon" items="${cur.map.baseLink102List}" varStatus="vs2">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:if test="${not empty curSon.image_path}">
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
		       	<c:if test="${(vs2.count eq 1)}">
			       		<c:set var="right_none" value="border-bottom:none;border-right:none;"  />
			       </c:if>
			        <c:if test="${(vs2.count eq 2)}">
			       		<c:set var="right_none" value="border-bottom:none;"  />
			       </c:if>
                 <div class="deal-tile"  style="width:477px;${botton_none}${right_none}">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
                     <img class="J-webp lazy" width="477" height="286" data-original="${imgurl}"/>
                  </a>
                  </div>
              </c:forEach>
              
              <c:set var="curSon_len" value="${fn:length(cur.map.baseLink104List)}"  />
              <c:forEach var="curSon" items="${cur.map.baseLink104List}" varStatus="vs2">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:if test="${not empty curSon.image_path}">
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
		       <c:set var="style" value=""  />
			      
			       <c:if test="${(curSon_len ne vs2.count)}">
			       		<c:set var="right_none" value="border-right:none;"  />
			       </c:if>
			        <c:if test="${(curSon_len eq vs2.count)}">
			       		<c:set var="right_none" value=""  />
			       </c:if>
			       
                 <div class="deal-tile" style="${botton_none}${right_none}">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
                     <img class="J-webp lazy" width="238" height="286" data-original="${imgurl}"/>
                  </a>
                  </div>
               </c:forEach>
               </c:if>
               
              <c:if test="${cur.pre_number eq 4}">
	              <c:forEach var="curSon" items="${cur.map.baseLink102List}" varStatus="vs2">
				       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
				       <c:if test="${not empty curSon.image_path}">
				       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
		                 <div class="deal-tile"  style="height:573px;border-right:none;">
		                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover" style="padding: 125px 15px 0;">
		                     <img class="J-webp lazy" width="239" height="573" data-original="${imgurl}"/>
		                  </a>
		                  </div>
	              </c:forEach>
              
              <c:forEach var="curSon" items="${cur.map.baseLink104List}" varStatus="vs2" begin="0" end="0">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:if test="${not empty curSon.image_path}">
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
                 <div class="deal-tile none">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
                     <img class="J-webp lazy" width="238" height="286" data-original="${imgurl}"/>
                  </a>
                  </div>
               </c:forEach>
               
              <c:forEach var="curSon" items="${cur.map.baseLink105List}" varStatus="vs2">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:if test="${not empty curSon.image_path}">
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
                 <div class="deal-tile none-bottom" style="width:478px;">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
                     <img class="J-webp lazy" width="478" height="286" data-original="${imgurl}"/>
                  </a>
                  </div>
               </c:forEach>
               
               <c:set var="curSon_len" value="${fn:length(cur.map.baseLink104List)}"  />
               <c:forEach var="curSon" items="${cur.map.baseLink104List}" varStatus="vs2" begin="1" end="3">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:set var="right_none" value="" />
		       <c:if test="${not empty curSon.image_path}">
		       	<c:if test="${(curSon_len ne vs2.count)}">
		       		<c:set var="right_none" value="border-right:none;"  />
		       </c:if>
		       <c:if test="${(vs2.count eq 3)}">
		       		<c:set var="right_none" value=""  />
		       </c:if>
		       
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
                 <div class="deal-tile" style="${right_none}">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
                     <img class="J-webp lazy" width="238" height="286" data-original="${imgurl}"/>
                  </a>
                  </div>
               </c:forEach>
               </c:if>
               
             <c:if test="${cur.pre_number eq 5}">
              <c:forEach var="curSon" items="${cur.map.baseLink102List}" varStatus="vs2" begin="0" end="0">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:if test="${not empty curSon.image_path}">
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
                 <div class="deal-tile none-right"  style="height:573px;">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover" style="padding: 125px 15px 0;">
                     <img class="J-webp lazy" width="240" height="573" data-original="${imgurl}"/>
                  </a>
                  </div>
              </c:forEach>
              <div style="width:480px;height: 576px;float:left;">
              <c:forEach var="curSon" items="${cur.map.baseLink104List}" varStatus="vs2">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:if test="${not empty curSon.image_path}">
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
                 <div class="deal-tile none-bottom" style="width:480px;">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
                     <img class="J-webp lazy" width="480" height="286" data-original="${imgurl}"/>
                  </a>
                  </div>
               </c:forEach>
               <c:forEach var="curSon" items="${cur.map.baseLink105List}" varStatus="vs2">
		        <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		        <c:if test="${not empty curSon.image_path}">
		        <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
                 <div class="deal-tile none-right">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
                     <img class="J-webp lazy" width="238" height="286" data-original="${imgurl}"/>
                  </a>
                  </div>
                </c:forEach>
              </div>
              <c:forEach var="curSon" items="${cur.map.baseLink102List}" varStatus="vs2" begin="1" end="2">
		       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
		       <c:if test="${not empty curSon.image_path}">
		       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
                 <div class="deal-tile"  style="height:573px;">
                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover" style="padding: 125px 15px 0;">
                     <img class="J-webp lazy" width="240" height="573" data-original="${imgurl}"/>
                  </a>
                  </div>
              </c:forEach>
              </c:if>
              
<!--               动态获取商品信息 -->
              <c:if test="${cur.pre_number eq 6}">
              <c:set var="curSon_len" value="${fn:length(cur.map.baseLink602List)}"  />
              <c:forEach var="curSon" items="${cur.map.baseLink602List}" varStatus="vs2">
			       <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image_index.jpg"/>
			       <c:if test="${not empty curSon.image_path}">
			       
			       <c:set var="imgurl" value="${ctx}/${curSon.image_path}"/></c:if>
	                 <div class="deal-tile6" style="">
	                  <a href="${curSon.link_url}" title="${curSon.title}" target="_blank" class="deal-tile__cover">
	                  <div class="item-pic">
	                     <img class="J-webp lazy" width="172" height="172" data-original="${imgurl}"/>
	                     </div>
	                  </a>
	                  
	                  <div class="text-info">
		     		 	<p class="name">${curSon.comm_name}</p> 
		     		 	<p class="price">
		     		 		${curSon.pd_price}元 
<!-- 		     				<del>298.00元</del> -->
		     			</p>
		     		</div>
<%-- 	                  <h3 class="deal-tile__title" style="margin-top: 0px;padding: 0 15px 0px;"> <a class="w-link" title="${curSon.title }"> <span class="xtitle">${curSon.title }</span> </a> </h3> --%>
<%--                     <p class="deal-tile__detail" style="margin-bottom: 0px;float: left;"> <span class="price" style="font-size: 16px;">¥<strong>${curSon.pd_price}</strong></span></p> --%>
<!--                     <p class="" style="margin-bottom: 0px;float: right;"> 购买</p> -->
	                  </div>
               </c:forEach>
               </c:if>
               
              </div>
              <c:if test="${not empty cur.map.baseLink108List}">
               <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="margin-top: 5px;">
			    <c:forEach var="curSon" items="${cur.map.baseLink108List}" varStatus="vs4">
<%-- 			        <a href="${curSon.link_url}" title="${curSon.title}" target="_blank"> --%>
			        	<img alt="${curSon.title}" class="lazy" data-original="${ctx}/${curSon.image_path}" width="1200" height="100" />
<!-- 		        	</a> -->
			    </c:forEach>
			    </div>
               </c:if>
            </div>
            </c:forEach>
          </div>
        </div>
        <div class="component-floor-elevator mt-component--booted">
          <div class="J-elevator floor-elevator" style="top:700px;" id="floor-elevator">
            <ul class="elevator" id="nav">
            <c:forEach var="cur" items="${base80LinkList}" varStatus="vs">
              <li class="elevator__floor">
              <a href="#contentFloor${vs.index}" title="${cur.title}" class="link">${vs.count}F
              <span>${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 4, ""))}</span></a> </li>
            </c:forEach>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="clear"></div>
<jsp:include page="./_footer.jsp" flush="true" />
<%-- <jsp:include page="/public/_qq_kefu.jsp" flush="true" /> --%>
<script type="text/javascript" src="${ctx}/scripts/jquery.nav.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.touchSlider.js"></script>
<script type="text/javascript" src="${ctx}/scripts/marquee/marquee.js"></script>
<script type="text/javascript" src="${ctx}/scripts/index.js?v=20180613"></script>
<script type="text/javascript" src="${ctx}/m/scripts/owl-carousel/owl.carousel.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lazyload/min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script src="${ctx}/scripts/jquery.timers.js" type="text/javascript" ></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	<c:forEach var="cur" items="${base70LinkList}" varStatus="vs">
	$("#slide${vs.count}").owlCarousel({
		  autoPlay : true,
		  slideSpeed : 200,
		  items:1,
		  itemsMobile:false
	});
	</c:forEach>

	$('#wrap2').marquee({
        auto: true,
        interval: 3000,
        speed: 2000,
        showNum: 40,
        stepLen: 2,
        type: 'vertical'
    });


	$("img.lazy").lazyload({
	    effect : "fadeIn"
	});

	var mySwiper2 = new Swiper('#swiper2 .swiper-container', {
        direction: 'horizontal',
        slidesPerView: 5,
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev'
    });
	
	 var mySwiper3 = new Swiper('#swiper3 .swiper-container', {
         direction: 'horizontal',
         loop: true,
         autoplay: {
         delay: 3000,
         stopOnLastSlide: false,
         disableOnInteraction: false,
         },
         pagination: {
     	 el: '.swiper-pagination',
     	 clickable :true,
         },
     });
	
	var mySwiper4 = new Swiper('#swiper4 .swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: {
            delay: 3000,
            stopOnLastSlide: false,
            disableOnInteraction: false,
            },
        pagination: {
    	el: '.swiper-pagination',
    	clickable :true,
        },
    });
	
	 var mySwiper5 = new Swiper('#swiper5 .swiper-container', {
        direction: 'horizontal',
        loop: true,
        autoplay: {
            delay: 3000,
            stopOnLastSlide: false,
            disableOnInteraction: false,
            },
        pagination: {
        el: '.swiper-pagination',
        clickable :true,
        },
     })
	
	GetRTime();
	setInterval(GetRTime,1000);
	
});


function GetRTime(){
    var EndTime= new Date('${endTime}');
    var NowTime = new Date();
    var t =EndTime.getTime() - NowTime.getTime();
    var h=00;
    var m=00;
    var s=00;
    if(t>=0){
	      h=Math.floor(t/1000/60/60%24);
	      m=Math.floor(t/1000/60%60);
	      s=Math.floor(t/1000%60);
	      if(h < 10){
	    	  h = "0" + h;
	      }
	      if(m < 10){
	    	  m = "0" + m;
	      }
	      if(s < 10){
	    	  s = "0" + s;
	      }
    }
    $("#t_h").html(h + "<span></span>");
    $("#t_m").html(m + "<span></span>");
    $("#t_s").html(s + "<span></span>");
}

//]]></script>
</body>
</html>
