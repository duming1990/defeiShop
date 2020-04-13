<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<link rel="stylesheet" href="${ctx}/styles/indexEntp/css/style.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.carousel.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/owl.theme.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/jquery.fullPage.css"/>
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/category-list.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css?v=20161130"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css"/>
<style type="">
.preview{ width:50px; height:40px; border-radius:20px; border-style:none; background:#ec6941; font-size:14px; color:rgba(249,248,248,1.00); position: absolute;  top: 50px; }
</style>
</head>


<body style="overflow: hidden;">
	
	<!--导航条-->
	<div class="section-group" id="dowebok">

<%-- <c:forEach items="${baseLink10000List}" var="cur"> --%>
<%-- 	<c:if test="${cur.pre_number eq 1}"> --%>
	  <div class="section">
	  <jsp:include page="_header.jsp" flush="true" />
		<!-- banner -->
		    <div class="bannerWrap">
		       <div class="index_banner">
		          <div class="hd">
		            <ul><li><span></span></li><li><span></span></li><li><span></span></li></ul>
		          </div>
		          <div class="bd">
		          <div class="areasSmall" style="padding-left:20px;padding-top:10px;"><button class="preview"><a href="${ctx}/indexEntp/IndexEntp.do" style="color: white;text-decoration:none;" target="_blank">预览</a></button></div>
		          <div class="areasSmall" style="padding-left:120px;padding-top:111px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10000,'txt','');">编辑楼层</a></div>
		          
		          <div class="areasSmall" style="padding-left:20px;padding-top:200px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10080,'pic',${cur.id});">编辑轮播图</a></div>
		            <ul>
		          		<li style="background-image:url('${ctx}/styles/indexEntp/images/banner01_bg.jpg')" class="item bgSize"><a href="javascript:;" class="banner01">
		                 <img src="${ctx}/styles/indexEntp/images/banner01_wz01.png" alt="创业虽苦，坚持很酷。" title="创业虽苦，坚持很酷。" class="img1" />
		                 <img src="${ctx}/styles/indexEntp/images/banner01_wz02.png" alt="阳光奥美集团，伴你一路前行。" title="阳光奥美集团，伴你一路前行。" class="img2"/></a>
		              </li>
		              <li style="background-image:url(${ctx}/styles/indexEntp/images/banner02_bg.jpg" class="item bgSize"><a href="javascript:;" class="banner01">
		                 <img src="${ctx}/styles/indexEntp/images/banner02_wz01.png" alt="创业虽苦，坚持很酷。" title="创业虽苦，坚持很酷。" class="img1" />
		                 <img src="${ctx}/styles/indexEntp/images/banner02_wz02.png" alt="阳光奥美集团，伴你一路前行。" title="阳光奥美集团，伴你一路前行。" class="img2"/></a>
		              </li>
		              <li style="background-image:url(${ctx}/styles/indexEntp/images/banner03_bg.jpg)" class="item bgSize"><a href="javascript:;" class="banner01">
		                 <img src="${ctx}/styles/indexEntp/images/banner03_wz01.png" alt="创业虽苦，坚持很酷。" title="创业虽苦，坚持很酷。" class="img1" />
		                 <img src="${ctx}/styles/indexEntp/images/banner03_wz02.png" alt="阳光奥美集团，伴你一路前行。" title="阳光奥美集团，伴你一路前行。" class="img2"/></a>
		              </li> 
		            </ul>
		          </div>
		       </div>
		       <div class="down"><img src="${ctx}/styles/indexEntp/images/down.png" alt="向下点击" class="img1" /><img src="${ctx}/styles/indexEntp/images/down.png" alt="向下点击" class="img2"/></div>
		    </div>
	  </div>
<%-- 	  </c:if> --%>
	  
<%-- 	  <c:if test="${cur.pre_number eq 2}"> --%>
		  <div class="section">
		  	<div class="areas" style="padding-left:50px;padding-top:111px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10010,'pic',${cur.id});">编辑</a></div>
			<div class="index_o">
			  		<div class="index_o_o">
			  			<p>核心业务</p>
			  			<span>PRIMARY  BUSINESS</span>
			  		</div>
			  		<div class="index_o_s">
			  			<ul class="clearfix">
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx1.png" alt="公司注册" width="62" height="62"></div>
			  					<span>公司注册</span>
			  					<p>目注心营，开启事业第一步</p>
			  					<a href="gszc.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx2.png" alt="财税服务" width="62" height="42"></div>
			  					<span>财税服务</span>
			  					<p>精打细算，匠心服务，让你无忧</p>
			  					<a href="csfw.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx3.png" alt="公司变更" width="53" height="59"></div>
			  					<span>公司变更</span>
			  					<p>高效便捷，再创事业巅峰</p>
			  					<a href="gsbg.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx4.png" alt="专项审批" width="55" height="56"></div>
			  					<span>专项审批</span>
			  					<p>资料交接，即刻争分夺秒</p>
			  					<a href="zxsp.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx5.png" alt="知识产权" width="62" height="48"></div>
			  					<span>知识产权</span>
			  					<p>专业为先，捍卫企业合法权益</p>
			  					<a href="zscq.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx6.png" alt="执照收转" width="55" height="56"></div>
			  					<span>执照收转</span>
			  					<p>海量优质资源，轻松收转坐享成功</p>
			  					<a href="zzsz.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx7.png" alt="法律服务" width="62" height="62"></div>
			  					<span>法律服务</span>
			  					<p>加固企业保护伞，为企业发展保驾护航</p>
			  					<a href="flfw.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx8.png" alt="资金服务" width="58" height="57"></div>
			  					<span>资金服务</span>
			  					<p>自持大额资金，极速服务为您排忧解难</p>
			  					<a href="zjfw.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx9.png" alt="公司注销" width="56" height="56"></div>
			  					<span>公司注销</span>
			  					<p>免除后顾之忧，蓄力杨帆再启航</p>
			  					<a href="gszx.html">了解更多</a>
			  				</li>
			  			</ul>
			  		</div>
			  	</div>
		  </div>
<%-- 	  </c:if> --%>
<div class="section">
<div class="areas" style="padding-left:50px;padding-top:111px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10200,'pic',${cur.id});">编辑</a></div>
	  <div class="index_o">
			  		<div class="index_o_o">
			  			<p>会员体系</p>
			  			<span>MEMBERSHIP SYSTEM</span>
			  		</div>
			  		<div class="index_o_s">
			  			<ul class="clearfix">
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx1.png" alt="公司注册" width="62" height="62"></div>
			  					<span>公司注册</span>
			  					<p>目注心营，开启事业第一步</p>
			  					<a href="gszc.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx2.png" alt="财税服务" width="62" height="42"></div>
			  					<span>财税服务</span>
			  					<p>精打细算，匠心服务，让你无忧</p>
			  					<a href="csfw.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx3.png" alt="公司变更" width="53" height="59"></div>
			  					<span>公司变更</span>
			  					<p>高效便捷，再创事业巅峰</p>
			  					<a href="gsbg.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx4.png" alt="专项审批" width="55" height="56"></div>
			  					<span>专项审批</span>
			  					<p>资料交接，即刻争分夺秒</p>
			  					<a href="zxsp.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx5.png" alt="知识产权" width="62" height="48"></div>
			  					<span>知识产权</span>
			  					<p>专业为先，捍卫企业合法权益</p>
			  					<a href="zscq.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx6.png" alt="执照收转" width="55" height="56"></div>
			  					<span>执照收转</span>
			  					<p>海量优质资源，轻松收转坐享成功</p>
			  					<a href="zzsz.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx7.png" alt="法律服务" width="62" height="62"></div>
			  					<span>法律服务</span>
			  					<p>加固企业保护伞，为企业发展保驾护航</p>
			  					<a href="flfw.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx8.png" alt="资金服务" width="58" height="57"></div>
			  					<span>资金服务</span>
			  					<p>自持大额资金，极速服务为您排忧解难</p>
			  					<a href="zjfw.html">了解更多</a>
			  				</li>
			  				<li class="fl">
			  					<div><img src="${ctx}/styles/indexEntp/images/index_hx9.png" alt="公司注销" width="56" height="56"></div>
			  					<span>公司注销</span>
			  					<p>免除后顾之忧，蓄力杨帆再启航</p>
			  					<a href="gszx.html">了解更多</a>
			  				</li>
			  			</ul>
			  		</div>
			  	</div>
		  </div>
	  
<%-- 	   <c:if test="${cur.pre_number eq 3}"> --%>
	  		<div class="section">
	  		<div class="areas" style="padding-left:50px;padding-top:111px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10020,'pic',${cur.id});">编辑</a></div>
				<div class="index_t">
					  		<div class="index_t_o clearfix">
					  			<ul class="fl">
					  				<li class="li1"><img src="${ctx}/styles/indexEntp/images/index_ys1.png" alt="资深行业经验" width="42" height="39"></li>
					  				<li class="li2">资深行业经验 </li>
					  				<li class="li3">16年资深行业经验<br>服务客户100000+</li>
					  			</ul>
					  			<ul class="fl mar0">
					  				<li class="li1"><img src="${ctx}/styles/indexEntp/images/index_ys2.png" alt="专业团队" width="46" height="34"></li>
					  				<li class="li2">专业团队</li>
					  				<li class="li3">打造行业领军品牌<br>旗下拥有持证精英代理人近500人</li>
					  			</ul>
					  			<ul class="fl">
					  				<li class="li1"><img src="${ctx}/styles/indexEntp/images/index_ys3.png" alt="多元服务" width="42" height="42"></li>
					  				<li class="li2">多元服务 </li>
					  				<li class="li3">开启多元化服务模式<br>线上线下协同发展</li>
					  			</ul>
					  			<ul class="fl mar0">
					  				<li class="li1"><img src="${ctx}/styles/indexEntp/images/index_ys4.png" alt="规模宏大" width="42" height="42"></li>
					  				<li class="li2">规模宏大 </li>
					  				<li class="li3">旗下设有24家分公司<br>广布北京、上海、天津、广州、<br>深圳等地</li>
					  			</ul>
					  		</div>
					  		<div class="index_t_s">
					  			<p>GROUP<br>SUPERIORITY</p>
					  			<span>集团优势</span>
					  		</div>
					  	</div>
	  </div>
<%-- 	  </c:if>   --%>
	  
<%-- 	  <c:if test="${cur.pre_number eq 4}"> --%>
	  		<div class="section">
	  		<div class="areas" style="padding-left:100px;padding-top:100px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10030,'pic',${cur.id});">编辑</a></div>
				<div class="index_s index_sub">
					  		<div class="index_t_s">
					  			<p>SUBSIDIARY<br>CORPORATION</p>
					  			<span>集团分布</span>
					  		</div>
					  		<div class="index_s_s w1600">
									<div  class="owl-carousel list">
							            <div class="item"><div class="img"><img src="${ctx}/styles/indexEntp/images/index_fb1.jpg" alt="北京阳光奥美" title="北京阳光奥美"> </div><h5>北京阳光奥美</h5></div>
							            <div class="item"><div class="img"><img src="${ctx}/styles/indexEntp/images/index_fb1.jpg" alt="天津阳光奥美" title="天津阳光奥美"> </div><h5>天津阳光奥美</h5></div>
							            <div class="item"><div class="img"><img src="${ctx}/styles/indexEntp/images/index_fb1.jpg" alt="上海阳光奥美" title="上海阳光奥美"> </div><h5>上海阳光奥美</h5></div>
							            <div class="item"><div class="img"><img src="${ctx}/styles/indexEntp/images/index_fb1.jpg" alt="北京阳光奥美1" title="北京阳光奥美1"> </div><h5>北京阳光奥美1</h5></div>
							            <div class="item"><div class="img"><img src="${ctx}/styles/indexEntp/images/index_fb1.jpg" alt="北京阳光奥美2" title="北京阳光奥美2"> </div><h5>北京阳光奥美2</h5></div>
							        </div> 
					  		</div>
					  	</div>
	   </div>
<%-- 	  </c:if>  --%>
	  
<%-- 	  <c:if test="${cur.pre_number eq 5}"> --%>
	  	<div class="section">
	  	<div class="areas" style="padding-left:100px;padding-top:100px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10040,'pic',${cur.id});">编辑背景图</a></div>
<%-- 	  	<div class="index_f" style="width: 100%;background: url('${ctx}/${cur.map.baseLink10040List.image_path}') no-repeat center;overflow: hidden;mi-n-width: 1370px;"> --%>
	  		<div class="index_f" style="width: 100%;background: url('${ctx}/styles/indexEntp/images/index_xwbg_02.jpg') no-repeat center;overflow: hidden;mi-n-width: 1370px;">
	  		<div class="index_f_lg">
	  			<div class="index_f_lg_s clearfix">
	  				<div class="z fl">
	  					<p>公司动态</p>
	  					<span>NEWS INFORMATION</span>
	  				</div>
	  				<a href="news.html" class="y fr clearfix">查看全部<img src="${ctx}/styles/indexEntp/images/index_yj.png" alt="yj" class="fr"/></a>
	  			</div>
	  			<div class="index_f_lg_x clearfix">
	  				<div class="le fl">
	  					<p>24</p>
	  					<span>2018-03</span>
	  				</div>
	  				<div class="ri fl">
	  					<a href="show.html">2018年春节放假通知</a>
	  					<p>2018年2月11日（星期日）——2018年2月21日（星期三）放假休息，合计11天。正月初七（2月22日，星期四）恢复正常上班。</p>
	  				</div>
	  			</div>
	  			<div class="index_f_lg_x clearfix index_f_lg_xs">
	  				<div class="le fl">
	  					<p>24</p>
	  					<span>2018-03</span>
	  				</div>
	  				<div class="ri fl">
	  					<a href="show.html">2018年春节放假通知</a>
	  					<p>2018年2月11日（星期日）——2018年2月21日（星期三）放假休息，合计11天。正月初七（2月22日，星期四）恢复正常上班。</p>
	  				</div>
	  			</div>
	  		</div>
	  		<div class="index_f_lg index_f_lgs">
	  			<div class="index_f_lg_s clearfix">
	  				<div class="z fl">
	  					<p>行业新闻</p>
	  					<span>INDUSTRY NEWS</span>
	  				</div>
	  				<a href="news.html" class="y fr clearfix">查看全部<img src="${ctx}/styles/indexEntp/images/index_yj.png" alt="yj" class="fr"/></a>
	  			</div>
	  			<div class="index_f_lg_x clearfix">
	  				<div class="le fl">
	  					<p>24</p>
	  					<span>2018-03</span>
	  				</div>
	  				<div class="ri fl">
	  					<a href="show.html">2018年春节放假通知</a>
	  					<p>2018年2月11日（星期日）——2018年2月21日（星期三）放假休息，合计11天。正月初七（2月22日，星期四）恢复正常上班。</p>
	  				</div>
	  			</div>
	  			<div class="index_f_lg_x clearfix index_f_lg_xs">
	  				<div class="le fl">
	  					<p>24</p>
	  					<span>2018-03</span>
	  				</div>
	  				<div class="ri fl">
	  					<a href="show.html">2018年春节放假通知</a>
	  					<p>2018年2月11日（星期日）——2018年2月21日（星期三）放假休息，合计11天。正月初七（2月22日，星期四）恢复正常上班。</p>
	  				</div>
	  			</div>
	  		</div>
	  	</div>
	  </div>
<%-- 	  </c:if>  --%>

<%-- <c:if test="${cur.pre_number eq 6}"> --%>
	  <div class="section">
	  	<div class="index_w">
	  		<div class="index_hz">
	  		 <div class="areas" style="padding-left:100px;padding-top:100px;z-index:999;"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10060,'pic',${cur.id});">编辑</a></div>
	  			<div class="index_o_o index_w_o">
		  			<p>合作伙伴</p>
		  			<span>COOPERATIVE PARTNER</span>
		  		</div>
		  		<div class="index_w_s clearfix">
		  			<p class="p1"></p>
		  			<p class="p2"></p>
		  			<p class="p3"></p>
		  			<p class="p4"></p>
		  			<p class="p5"></p>
		  			<p class="p6"></p>
		  			<p class="p7"></p>
		  			<p class="p8"></p>
		  			<p class="p9"></p>
		  			<p class="p10"></p>
		  			<p class="p11"></p>
		  			<p class="p12"></p>
		  		</div>
	  		</div>
	  		
	  	</div>
	  	 <jsp:include page="_footer.jsp" flush="true" />
	  </div>
<%-- </c:if> --%>
<%-- 	 </c:forEach> --%>
	</div>
<%-- 	 <script src="${ctx}/commons/scripts/jquery.js"></script>  --%>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.min.js"></script>

	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.fullPage.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/jquery.SuperSlide.2.1.1.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/owl.carousel.min.js"></script>
	<script type="text/javascript" src="${ctx}/styles/indexEntp/js/style2.js"></script>
<%-- 	<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>  --%>
	<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>  
	<script type="text/javascript">
	
	//banner轮播
	jQuery(".index_banner").slide({mainCell:".bd ul",autoPlay:true,interTime:6000,mouseOverStop:false,effect:"fold"});
	//限制高度
	var Wheight=$(window).height();
	$('.index_banner,.index_banner .item,.bannerWrap,.index_banner .bd,.index_banner .bd ul,.index_banner .bd ul li,.index_o,.index_t,.index_s,.index_f,.index_w,.cuowu').height(Wheight);var _win = $(window);

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
	    navigationText: ["<img src='../styles/indexEntp/images/index_fbz.png'/>","<img src='../styles/indexEntp/images/index_fby.png' />"],
	    pagination: true,
	    //paginationNumbers:true,
	    navigation: true,
	    beforeUpdate:true
	   });
		//集团分布
		$(".index_sub .owl-theme .owl-controls .owl-buttons .owl-next img").hover(function(){
			$(this).attr("src", "../styles/indexEntp/images/index_fby2.png")
		},function(){
			$(this).attr("src","../styles/indexEntp/images/index_fby.png")
		})
		$(".index_sub .owl-theme .owl-controls .owl-buttons .owl-prev img").hover(function(){
			$(this).attr("src","../styles/indexEntp/images/index_fbz2.png")
		},function(){
			$(this).attr("src","../styles/indexEntp/images/index_fbz.png")
		})
		//新闻中心
		$(".index_f_lg_s .y").hover(function(){
			$(this).children("img").attr("src","../styles/indexEntp/images/index_yj2.png")
		},function(){
			$(this).children("img").attr("src","../styles/indexEntp/images/index_yj.png")
		})
	})





	
		function editFloor(id,link_type,type,par_id){
		var url = "${ctx}/manager/admin/BaseLinkNew.do?mod_id="+id+"&link_type="+link_type+"&type="+type+"&par_id="+par_id;
		
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
		$(function(){
		    $('#dowebok').fullpage({
				'navigation': true,
		    });
		});
		
		
	</script>
</body>
</html>
