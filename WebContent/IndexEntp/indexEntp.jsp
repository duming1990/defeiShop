<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<link rel="stylesheet" href="../styles/indexEntp/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.carousel.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.theme.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/jquery.fullPage.css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css?v=20161130" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css" />
</head>
<body style="overflow: hidden;">
<!--导航条-->

<div class="section-group" id="dowebok">
  <c:forEach items="${baseLink10000List}" var="cur">
    <c:if test="${cur.pre_number eq 1}">
      <div class="section">
      <!-- 导航栏开始 -->
        <jsp:include page="_header.jsp" flush="true" />
         <!-- 导航栏结束-->
        <!-- 轮播图开始 -->
        <div class="bannerWrap">
          <div class="index_banner">
            <div class="hd">
              <ul>
                <li><span></span></li>
                <li><span></span></li>
                <li><span></span></li>
              </ul>
            </div>
            <div class="bd">
              <ul>
                <c:forEach items="${baseLink10080List}" var="cur2">
                  <li style="background-image:url('../${cur2.image_path}');position: absolute; width: 100%; height:100%; left: 0px; top: 0px; height: 1149px; display: list-item;" class="item bgSize">
                  </li>
                </c:forEach>
              </ul>
            </div>
          </div>
          <div class="down">
	          <img src="../styles/indexEntp/images/down.png" alt="向下点击" class="img1" />
	          <img src="../styles/indexEntp/images/down.png" alt="向下点击" class="img2" />
          </div>
        </div>
      </div>
       <!-- 轮播图结束-->
    </c:if>


    <c:if test="${cur.pre_number eq 2}">
      <!-- 核心业务开始 -->
      <div class="section">
        <div class="index_o">
          <div class="index_o_o">
            <p>${cur.title }</p>
            <span>${cur.pre_varchar}</span>
          </div>
          <div class="index_o_s">
            <ul class="clearfix">
              <c:forEach items="${baseLink10010List}" var="cur2" varStatus="status1">
                <li class="fl">
                  <div><img src="../${cur2.image_path}" alt="${cur2.title }" width="62" height="62" /></div>
                  <span>${cur2.title}</span>

                  <p>${cur2.pre_varchar}</p>
                  <a href="${cur2.link_url}">了解详情</a>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
       <!-- 核心业务结束 -->
    </c:if>


    <c:if test="${cur.pre_number eq 8}">
      <!-- 会员体系开始 -->
      <div class="section">
        <div class="index_o">
          <div class="index_o_o">
            <p>${cur.title }</p>
            <span>${cur.pre_varchar}</span>
          </div>
          <div class="index_o_s">
            <ul class="clearfix">
              <c:forEach items="${baseLink10200List}" var="cur2" varStatus="status1">
                <li class="fl">
                  <div><img src="../${cur2.image_path}" alt="${cur2.title }" width="62" height="62" /></div>
                  <span>${cur2.title}</span>

                  <p>${cur2.pre_varchar}</p>
                  <a href="${cur2.link_url}">了解详情</a>
                </li>
              </c:forEach>
            </ul>
          </div>
        </div>
      </div>
       <!-- 会员体系结束 -->
    </c:if>


    <c:if test="${cur.pre_number eq 3}">
     <!-- 集团优势开始 -->
      <div class="section">
        <div class="index_t">

          <div class="index_t_o clearfix">

            <c:forEach items="${baseLink10020List}" var="cur2" varStatus="vs2">

              <c:set var="mar0Class" value="" />
              <c:if test="${vs2.index % 2 eq 1}">
                <c:set var="mar0Class" value=" mar0" />
              </c:if>
              <ul class="fl${mar0Class}" style="margin-right:24px;">
                <li class="li1"><img src="../${cur2.image_path}" alt="${cur2.title}" width="42" height="39" /></li>
                <li class="li2">${cur2.title}</li>
                <li class="li3">${cur2.pre_varchar}</li>
              </ul>
            </c:forEach>
          </div>
          <div class="index_t_s">
            <p>${cur.pre_varchar }</p>
            <span>${cur.title}</span>
          </div>
        </div>
      </div>
      <!-- 集团优势结束 -->
    </c:if>


    <c:if test="${cur.pre_number eq 4}">
    <!-- 集团分布开始 -->
      <div class="section">
        <div class="index_s index_sub">
          <div class="index_t_s">
            <p>${cur.pre_varchar }</p>
            <span>${cur.title}</span>
          </div>

          <div class="index_s_s w1600">
            <div class="owl-carousel list">
              <c:forEach items="${baseLink10030List}" var="cur2">
                <div class="item">
                  <div class="img"><img src="../${cur2.image_path}" alt="${cur2.title}" title="${cur2.title}" width="492" height="374" /> </div>
                  <h5>${cur2.title}</h5></div>
              </c:forEach>
            </div>

          </div>
        </div>
        					<!-- 底部开始 -->

							<jsp:include page="_footer.jsp" flush="true" />
 					<!-- 底部结束 -->
					
      </div>
      <!-- 集团分布结束 -->
      
    </c:if>

    <c:if test="${cur.pre_number eq 5}">
    <!-- 公司动态开始 -->
      <div class="section">
        <div class="index_f" style="width: 100%;background: url('../${baseLink10040List.image_path}') no-repeat center;overflow: hidden;mi-n-width: 1370px;">
          <div class="index_f_lg">
            <div class="index_f_lg_s clearfix">
              <div class="z fl">
                <p>公司动态</p>
                <span>NEWS INFORMATION</span>
              </div>
              <a href="${ctx}/indexEntp/IndexNewsInfo.do?mod_id=1808004100" class="y fr clearfix">查看全部<img src="../styles/indexEntp/images/index_yj.png" alt="yj" class="fr" /></a>
            </div>
            <div class="index_f_lg_x clearfix">
              <div class="le fl">
                <p><fmt:formatDate value="${newsList0.pub_time }" pattern="dd" /></p>
                <span><fmt:formatDate value="${newsList0.pub_time }" pattern="yyyy-MM" /></span>
              </div>
              <div class="ri fl">
                <a href="${ctx}/indexEntp/IndexNewsInfo.do?method=view&id=${newsList0.id}">${newsList0.title }</a>
                <p>${newsList0.title_short}</p>
              </div>
            </div>
            <div class="index_f_lg_x clearfix index_f_lg_xs">
              <div class="le fl">
               <p><fmt:formatDate value="${newsList1.pub_time }" pattern="dd" /></p>
                <span><fmt:formatDate value="${newsList1.pub_time }" pattern="yyyy-MM" /></span>
              </div>
              <div class="ri fl">
                 <a href="${ctx}/indexEntp/IndexNewsInfo.do?method=view&id=${newsList1.id}">${newsList1.title }</a>
                <p>${newsList1.title_short}</p>
              </div>
            </div>
          </div>
          <div class="index_f_lg index_f_lgs">
            <div class="index_f_lg_s clearfix">
              <div class="z fl">
                <p>行业新闻</p>
                <span>INDUSTRY NEWS</span>
              </div>
              <a href="${ctx}/indexEntp/IndexNewsInfo.do?mod_id=1808004200" class="y fr clearfix">查看全部<img src="../styles/indexEntp/images/index_yj.png" alt="yj" class="fr"></a>
            </div>
            <div class="index_f_lg_x clearfix">
              <div class="le fl">
                <p><fmt:formatDate value="${comList0.pub_time }" pattern="dd" /></p>
                <span><fmt:formatDate value="${comList0.pub_time }" pattern="yyyy-MM" /></span>
              </div>
              <div class="ri fl">
                 <a href="${ctx}/indexEntp/IndexNewsInfo.do?method=view&id=${comList0.id}">${comList0.title }</a>
                <p>${comList0.title_short}</p>
              </div>
            </div>
            <div class="index_f_lg_x clearfix index_f_lg_xs">
              <div class="le fl">
               <p><fmt:formatDate value="${comList1.pub_time }" pattern="dd" /></p>
                <span><fmt:formatDate value="${comList1.pub_time }" pattern="yyyy-MM" /></span>
              </div>
              <div class="ri fl">
               <a href="${ctx}/indexEntp/IndexNewsInfo.do?method=view&id=${comList1.id}">${comList1.title }</a>
                <p>${comList1.title_short}</p>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 公司动态结束 -->
    </c:if>


			<c:if test="${cur.pre_number eq 6}">
			<!-- 合作伙伴开始 -->
				<div class="section">
					<div class="index_w">
						<div class="index_hz">
							<div class="index_o_o index_w_o">
								<p>${cur.title}</p>
								<span>${cur.pre_varchar }</span>
							</div>
							<div class="index_w_s clearfix">
								<c:forEach items="${baseLink10060List}" var="cur2"
									varStatus="vs2">
									<p class="p${vs2}">
										<img src="${ctx}/${cur2.image_path}" alt="${cur2.title}"
											title="${cur2.title}" width="198" height="65" />
									</p>
								</c:forEach>
							</div>
						</div>
					</div>

					

				</div>
				<!-- 合作伙伴结束 -->
			</c:if>
			
         


 	

  </c:forEach>

</div>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.fullPage.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">

//banner轮播
jQuery(".index_banner").slide({mainCell:".bd ul",autoPlay:true,interTime:6000,mouseOverStop:false,effect:"fold"});
//限制高度
var Wheight=$(window).height();
var Wwidth=$(window).width();
$('.index_banner,.index_banner .item,.bannerWrap,.index_banner .bd,.index_banner .bd ul,.index_banner .bd ul li,.index_o,.index_t,.index_s,.index_f,.index_w,.cuowu').height(Wheight);var _win = $(window);
$('.index_banner .hd,.index_banner .hd ul').width(Wwidth);
$('.index_banner .bd ul,.index_banner .bd ul li').width(Wwidth);

$('.footer').height(270);

//首页banner图上down点击
//首页banner图上down点击
$('.bannerWrap .down').on('click',function(){
    $(".section-group").css("transform","translateY(-100%)");
    $(".nav li").eq(1).children().addClass("active");
    $(".nav li").eq(1).siblings().children("span").removeClass("active");
    $('.header').hide();
   });
//核心业务
$(".index_o_s li a").animate({opacity:"0"})
$(".index_o_s li").hover(function(){
	$(this).children("div").stop().animate({"margin-top":"-20px"})
	$(this).children("a").stop().animate({opacity:"1"})
},function(){
	$(this).stop().animate({"margin-top":"16px"})
	$(this).children("div").stop().animate({"margin-top":"0px"})
	$(this).children("a").stop().animate({opacity:"0"})
})
$(window).load(function(){
	$("#fp-nav ul").addClass("nav")
	//集团分布
    $('.index_sub .list').owlCarousel({
    items: 3,
    navigationText: ["<img src='${ctx}/styles/indexEntp/images/index_fbz.png'/>","<img src='${ctx}/styles/indexEntp/images/index_fby.png' />"],
    pagination: true,
    //paginationNumbers:true,
    navigation: true,
    beforeUpdate:true
   });
	//集团分布
	$(".index_sub .owl-theme .owl-controls .owl-buttons .owl-next img").hover(function(){
		$(this).attr("src", "${ctx}/styles/indexEntp/images/index_fby2.png")
	},function(){
		$(this).attr("src","${ctx}/styles/indexEntp/images/index_fby.png")
	})
	$(".index_sub .owl-theme .owl-controls .owl-buttons .owl-prev img").hover(function(){
		$(this).attr("src","${ctx}/styles/indexEntp/images/index_fbz2.png")
	},function(){
		$(this).attr("src","${ctx}/styles/indexEntp/images/index_fbz.png")
	})
	//新闻中心
	$(".index_f_lg_s .y").hover(function(){
		$(this).children("img").attr("src","${ctx}/styles/indexEntp/images/index_yj2.png")
	},function(){
		$(this).children("img").attr("src","${ctx}/styles/indexEntp/images/index_yj.png")
	})
})



$(function () {
  $('#dowebok').fullpage({
    'navigation': true,
  });
});
</script>
</body>
</html>