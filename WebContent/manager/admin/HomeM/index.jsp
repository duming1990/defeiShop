<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>触屏版</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">
		<meta name="format-detection" content="telephone=no">
		<meta http-equiv="Expires" content="-1">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Pragma" content="no-cache">
		<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/styles/public.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/core.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/icon.css">
		<%-- <link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/home.css"> --%>
		<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/index.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/show/css/index.css?20180529">
		<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/show/css/public.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/show/css/swiper-3.4.1.min.css">
		<style type="text/css">
		.beautybg1{
			    box-shadow: inset 0px 1px 0px 0px #a4e271;
			    background: -webkit-gradient(linear, left top, left bottom, color-stop(0.05, #89c403), color-stop(1, #77a809));
			    background: -moz-linear-gradient(center top, #89c403 5%, #77a809 100%);
			    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#89c403',endColorstr='#77a809');
			    background-color: #89c403;
			    -moz-border-radius: 5px;
			    -webkit-border-radius: 5px;
			    border-radius: 5px;
			    border: 1px solid #74b807;
			    display: inline-block;
			    color: #ffffff;
			    font-family: arial;
			    font-size: 12px;
			    font-weight: bold;
			    padding: 4px 10px;
			    text-decoration: none;
			    text-shadow: 1px 1px 0px #528009;
			    line-height: 15px;
			    cursor: pointer;
		
		}
		.index .div3 img{
		    width: 33.333% !important;
  		   height: 100% !important; 
		}
		</style>
	</head>

	<body style="width: 700px;margin: 0 auto;">
		<div class="aui-content-box" style="padding-top:0;">
			<div class="areasSmall">
				<a href="${ctx}/manager/admin/AppBaseLink.do?mod_id=${af.map.mod_id}&link_type=10" class="beautybg">编辑轮播图</a>
			</div>
			<div class="aui-banner-content " data-aui-slider>
				<div class="aui-banner-wrapper">
					<div class="aui-banner-wrapper-item">
						<a>
							<img src="${ctx}/m/styles/themes/img/banner/news-banner2.jpg">
						</a>
					</div>
					<div class="aui-banner-wrapper-item">
						<a>
							<img src="${ctx}/m/styles/themes/img/banner/news-banner1.jpg">
						</a>
					</div>
					<div class="aui-banner-wrapper-item">
						<a>
							<img src="${ctx}/m/styles/themes/img/banner/news-banner3.jpg">
						</a>
					</div>
					<div class="aui-banner-wrapper-item">
						<a>
							<img src="${ctx}/m/styles/themes/img/banner/news-banner1.jpg">
						</a>
					</div>
				</div>
				<div class="aui-banner-pagination"></div>
			</div>
			<section class="aui-grid-content">
				<div class="areasSmall">
					<a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=20" class="beautybg">编辑导航</a>
				</div>
				<%-- 			<c:if test="${not empty mBaseLinkList20}"> --%>
				<!-- 				<div class="aui-grid-row aui-grid-row-clears"> aui-grid-row-clear 清除 a标签 上下的边距 -->
				<%-- 					<c:forEach items="${mBaseLinkList20}" var="cur" varStatus="vs"> --%>
				<%-- 					<a href="${cur.link_url}" class="aui-grid-row-item"> --%>
				<%-- 						<i class="aui-icon-large" style="background-image: url(${ctx}/${cur.image_path});"></i> --%>
				<%-- 						<p class="aui-grid-row-label">${cur.title}</p> --%>
				<!-- 					</a> -->
				<%-- 					</c:forEach> --%>
				<!-- 				</div> -->
				<%-- 			</c:if> --%>
				<%-- 			<c:if test="${empty mBaseLinkList20}"> --%>
				<div class="aui-grid-row aui-grid-row-clears">
					<!-- aui-grid-row-clear 清除 a标签 上下的边距 -->
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-sign"></i>
						<p class="aui-grid-row-label">每日签到</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-time"></i>
						<p class="aui-grid-row-label">限时抢购</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-vip"></i>
						<p class="aui-grid-row-label">会员专享</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-group"></i>
						<p class="aui-grid-row-label">好货拼团</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-share"></i>
						<p class="aui-grid-row-label">分享领券</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-recharges"></i>
						<p class="aui-grid-row-label">手机充值</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-finance"></i>
						<p class="aui-grid-row-label">金融理财</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-appliance"></i>
						<p class="aui-grid-row-label">电器商城</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-supermarket"></i>
						<p class="aui-grid-row-label">萌宝超市</p>
					</a>
					<a class="aui-grid-row-item">
						<i class="aui-icon-large aui-icon-personal"></i>
						<p class="aui-grid-row-label">个人中心</p>
					</a>
				</div>
				<%-- 			</c:if> --%>
			</section>

			<!-- 维护楼层 -->
			<!-- 图片标题 -->
			<div class="aui-title-head">
				<div class="areas">
					<a  class="beautybg1" onclick="editFloor(${af.map.mod_id});">编辑【楼层】</a>
				</div>
				<div class="areas" style="left:400px;" title="刷新楼层"><span id="reload" onclick="location.reload()" style="cursor:pointer;" class="beautybg">刷新楼层</span></div>
			</div>
			<c:if test="${not empty mBaseLinkList30}">
				<c:forEach items="${mBaseLinkList30}" var="cur" varStatus="vs">
					<div class="container index">
						<c:if test="${cur.pre_number eq 1}">
							<div class="div1">
								<div class="areas" style="position: relative;top: 100px;">
									<a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1&type=price" class="beautybg">编辑</a>
								</div>
								<img src="${ctx}/m/styles/show/img/img1.jpg" />
								<img src="${ctx}/m/styles/show/img/img2.jpg" />
								<img src="${ctx}/m/styles/show/img/img3.jpg" />
								<img src="${ctx}/m/styles/show/img/img4.jpg" />
								<img src="${ctx}/m/styles/show/img/img5.jpg" />
								<img src="${ctx}/m/styles/show/img/img6.jpg" />
							</div>
						</c:if>
						<c:if test="${cur.pre_number eq 2}">
							<div class="div1">
								<img src="${ctx}/m/styles/show/img/img1.jpg" />
							</div>
							<div class="div2">
								<div class="div2_img div2_img1">
									<div class="areas" style="top: 1px;position: absolute;">
										<a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1&type=price" class="beautybg">编辑</a>
									</div>
									<img src="${ctx}/m/styles/show/img/img7.jpg" />
								</div>
								<div class="div2_img div2_img2">
									<div class="areas" style="top:3px;position: absolute;">
										<a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=2&type=price" class="beautybg">编辑</a>
									</div>
									<img src="${ctx}/m/styles/show/img/img8.jpg" />
									<img src="${ctx}/m/styles/show/img/img9.jpg" />
								</div>
							</div>
						</c:if>
						<c:if test="${cur.pre_number eq 3}">
							<div class="div1">
								<img src="${ctx}/m/styles/show/img/img1.jpg" />
							</div>
<!-- 							<div class="swiper-container"> -->
<!-- 								<div class="swiper-wrapper"> -->
<!-- 									<div class="swiper-slide"> -->
										<div class="div3 div31">
											<div class="areas" style="top:3px;position: absolute;width: 63px">
												<a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1&type=price" class="beautybg">编辑1</a>
											</div>
											<img src="${ctx}/m/styles/show/img/img10.jpg" />
											<img src="${ctx}/m/styles/show/img/img11.jpg" />
											<img src="${ctx}/m/styles/show/img/img12.jpg" />
		                </div>
<!-- 		            </div> -->
<!-- 		            <div class="swiper-slide "> -->
<!-- 		                <div class="div3 div31 "> -->
<%-- 		                    <img src="${ctx}/m/styles/show/img/img10.jpg "/> --%>
<%-- 		                    <img src="${ctx}/m/styles/show/img/img11.jpg "/> --%>
<%-- 		                    <img src="${ctx}/m/styles/show/img/img12.jpg "/> --%>
<!-- 		                </div> -->
<!-- 		            </div> -->
<!-- 		            <div class="swiper-slide "> -->
<!-- 		                <div class="div3 div31 "> -->
<%-- 		                    <img src="${ctx}/m/styles/show/img/img10.jpg "/> --%>
<%-- 		                    <img src="${ctx}/m/styles/show/img/img11.jpg "/> --%>
<%-- 		                    <img src="${ctx}/m/styles/show/img/img12.jpg "/> --%>
<!-- 		                </div> -->
<!-- 		            </div> -->
<!-- 		        </div> -->
<!-- 		    </div> -->
		    <div class="div1 ">
		    	<div class="areas " style="position: absolute; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}04&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=4&type=price " class="beautybg ">编辑</a></div>
		        <img src="${ctx}/m/styles/show/img/img13.jpg "/>
		    </div>
    </c:if>
	<c:if test="${cur.pre_number eq 4}">
	    <div class="div1 ">
	        <img src="${ctx}/m/styles/show/img/img1.jpg "/>
	    </div>
	    <div class="div4 div41 ">
	        <div class="div4_img1 ">
	        	<div class="areas " style="top:3px;position: relative; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1&type=price " class="beautybg ">编辑</a></div>
	            <img src="${ctx}/m/styles/show/img/img14.jpg "/>
	        </div>
	        <div class="div4_img2 ">
	        	<div class="areas " style="top:3px;position: relative; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=2&type=price " class="beautybg ">编辑</a></div>
	            <img src="${ctx}/m/styles/show/img/img15.jpg "/>
	        </div>
	    </div>
	
	    <div class="div1 ">
	        <div class="areas " style="position: absolute; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=3&type=price " class="beautybg ">编辑</a></div>
	        <img src="${ctx}/m/styles/show/img/img16.jpg "/>
	    </div>
    </c:if>
	<c:if test="${cur.pre_number eq 5}">
	    <div class="div1 ">
	        <img src="${ctx}/m/styles/show/img/img1.jpg "/>
	   		<div class="areas " style="position: absolute; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1&type=price " class="beautybg ">编辑</a></div>
	        <img src="${ctx}/m/styles/show/img/img17.jpg "/>
	    </div>
	    <div class="div4 div42 ">
	        <div class="div4_img2 ">
	        	<div class="areas " style="position: absolute; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=2&type=price " class="beautybg ">编辑</a></div>
	            <img src="${ctx}/m/styles/show/img/img18.jpg "/>
	        </div>
	        <div class="div4_img1 ">
	        	<div class="areas " style="position: absolute; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}03&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=3&type=price " class="beautybg ">编辑</a></div>
	            <img src="${ctx}/m/styles/show/img/img19.jpg "/>
	        </div>
	    </div>
    </c:if>
	 <c:if test="${cur.pre_number eq 6}">
		    <div class="div1 ">
		       <img src="${ctx}/m/styles/show/img/img1.jpg "/>
		       	<div class="areas " style="position: absolute; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}01&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=1&type=price " class="beautybg ">编辑</a></div>
		        <img src="${ctx}/m/styles/show/img/img20.jpg "/>
		    </div>
	<!-- 						<div class="swiper-container"> -->
	<!-- 								<div class="swiper-wrapper"> -->
	<!-- 									<div class="swiper-slide"> -->
											<div class="div3 div31">
												<div class="areas" style="top:3px;position: absolute;width: 63px">
													<a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=${vs.count}02&pre_number=${cur.pre_number}&par_id=${cur.id}&par_son_type=2&type=price" class="beautybg">编辑1</a>
												</div>
												<img src="${ctx}/m/styles/show/img/img21.jpg" />
												<img src="${ctx}/m/styles/show/img/img22.jpg" />
												<img src="${ctx}/m/styles/show/img/img23.jpg" />
			                </div>
	<!-- 		            </div> -->
	<!-- 		            <div class="swiper-slide "> -->
	<!-- 		                <div class="div3 div31 "> -->
	<%--      					    <img src="${ctx}/m/styles/show/img/img21.jpg" /> --%>
	<%-- 							<img src="${ctx}/m/styles/show/img/img22.jpg" /> --%>
	<%-- 							<img src="${ctx}/m/styles/show/img/img23.jpg" /> --%>
	<!-- 		                </div> -->
	<!-- 		            </div> -->
	<!-- 		            <div class="swiper-slide "> -->
	<!-- 		                <div class="div3 div31 "> -->
	<%-- 		           			<img src="${ctx}/m/styles/show/img/img21.jpg" /> --%>
	<%-- 							<img src="${ctx}/m/styles/show/img/img22.jpg" /> --%>
	<%-- 							<img src="${ctx}/m/styles/show/img/img23.jpg" /> --%>
	<!-- 		                </div> -->
	<!-- 		            </div> -->
	<!-- 		        </div> -->
	<!-- 		    </div> -->
		</c:if>
	</div>
			</c:forEach>
			</c:if>		

		<div class="aui-recommend " style="position: relative; ">
			<img src="${ctx}/m/styles/themes/img/bg/icon-tj1.jpg " />
		    <div class="areas " style="position: absolute;top: 0; "><a href="${ctx}/manager/admin/MBaseLink.do?mod_id=${af.map.mod_id}&link_type=60 " class="beautybg ">编辑</a></div>
		</div>
		<section class="aui-list-product ">
			<div class="aui-list-product-box ">
				<a  class="aui-list-product-item ">
					<div class="aui-list-product-item-img ">
						<img src="${ctx}/m/styles/themes/img/pd/sf-15.jpg " alt=" ">
					</div>
					<div class="aui-list-product-item-text ">
						<h3>KOBE LETTUCE 秋冬新款 女士日系甜美纯色半高领宽松套头毛衣针织衫</h3>
						<div class="aui-list-product-mes-box ">
							<div>
							<span class="aui-list-product-item-price ">
								<em>¥</em>
								399.99
							</span>
<!-- 								<span class="aui-list-product-item-del-price "> -->
<!-- 								¥495.65 -->
<!-- 							</span> -->
							</div>
<!-- 							<div class="aui-comment ">986评论</div> -->
						</div>
					</div>
				</a>
				<a  class="aui-list-product-item ">
					<div class="aui-list-product-item-img ">
						<img src="${ctx}/m/styles/themes/img/pd/sf-14.jpg " alt=" ">
					</div>
					<div class="aui-list-product-item-text ">
						<h3>KOBE LETTUCE 秋冬新款 女士日系甜美纯色半高领宽松套头毛衣针织衫</h3>
						<div class="aui-list-product-mes-box ">
							<div>
							<span class="aui-list-product-item-price ">
								<em>¥</em>
								399.99
							</span>
<!-- 								<span class="aui-list-product-item-del-price "> -->
<!-- 								¥495.65 -->
<!-- 							</span> -->
							</div>
						</div>
					</div>
				</a>
				<a  class="aui-list-product-item ">
					<div class="aui-list-product-item-img ">
						<img src="${ctx}/m/styles/themes/img/pd/sf-13.jpg " alt=" ">
					</div>
					<div class="aui-list-product-item-text ">
						<h3>KOBE LETTUCE 秋冬新款 女士日系甜美纯色半高领宽松套头毛衣针织衫</h3>
						<div class="aui-list-product-mes-box ">
							<div>
							<span class="aui-list-product-item-price ">
								<em>¥</em>
								399.99
							</span>
							</div>
						</div>
					</div>
				</a>
				<a  class="aui-list-product-item ">
					<div class="aui-list-product-item-img ">
						<img src="${ctx}/m/styles/themes/img/pd/sf-12.jpg " alt=" ">
					</div>
					<div class="aui-list-product-item-text ">
						<h3>KOBE LETTUCE 秋冬新款 女士日系甜美纯色半高领宽松套头毛衣针织衫</h3>
						<div class="aui-list-product-mes-box ">
							<div>
							<span class="aui-list-product-item-price ">
								<em>¥</em>
								399.99
							</span>
							</div>
						</div>
					</div>
				</a>
			</div>
		</section>
	</div>

	<footer class="aui-footer-default aui-footer-fixed ">
		<a href="style.html " class="aui-footer-item aui-footer-active ">
			<span class="aui-footer-item-icon aui-icon aui-footer-icon-home "></span>
			<span class="aui-footer-item-text ">首页</span>
		</a>
		<a href="class.html " class="aui-footer-item ">
			<span class="aui-footer-item-icon aui-icon aui-footer-icon-class "></span>
			<span class="aui-footer-item-text ">分类</span>
		</a>
		<a href="find.html " class="aui-footer-item ">
			<span class="aui-footer-item-icon aui-icon aui-footer-icon-find "></span>
			<span class="aui-footer-item-text ">发现</span>
		</a>
		<a href="car.html " class="aui-footer-item ">
			<span class="aui-footer-item-icon aui-icon aui-footer-icon-car "></span>
			<span class="aui-footer-item-text ">购物车</span>
		</a>
		<a href="ui-me.html " class="aui-footer-item ">
			<span class="aui-footer-item-icon aui-icon aui-footer-icon-me "></span>
			<span class="aui-footer-item-text ">我的</span>
		</a>
	</footer>
	<script type="text/javascript " src="${ctx}/commons/scripts/jquery.js "></script>
	<script type="text/javascript " src="${ctx}/scripts/colorbox/jquery.colorbox.min.js "></script>
	<script type="text/javascript " src="${ctx}/scripts/jBox/jbox.min.manager.js "></script>
	<script type="text/javascript " src="${ctx}/scripts/swiper/swiper.min.js "></script>
	<script type="text/javascript " src="${ctx}/m/styles/themes/js/aui.js "></script>
	<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script> 
	<script type="text/javascript ">
		$("a.beautybg ").colorbox({width:"90% ", height:"80% ", iframe:true});
	function editFloor(id){
	var url = "${ctx}/manager/admin/MBaseLink.do?mod_id="+id+"&link_type=30&type=Style";
	$.dialog({
		title:  "编辑楼层",
		width:  1600,
		height:700,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url),
  		close:function(){  
             location.reload();
            }  

	});
}
        $(function () {
	        var mySwiper = new Swiper('.swiper-container', {
	            direction: 'horizontal',
	            loop: true,
	            // 如果需要前进后退按钮
	            nextButton: '.swiper-button-next',
	            prevButton: '.swiper-button-prev',
	        })
            //绑定滚动条事件
            //绑定滚动条事件
            $(window).bind("scroll ", function () {
                var sTop = $(window).scrollTop();
                var sTop = parseInt(sTop);
                if (sTop >= 40) {
                    if (!$("#scrollBg ").is(":visible ")) {
                        try {
                            $("#scrollBg ").slideDown();
                        } catch (e) {
                            $("#scrollBg ").show();
                        }
                    }
                }
                else {
                    if ($("#scrollBg ").is(":visible ")) {
                        try {
                            $("#scrollBg ").slideUp();
                        } catch (e) {
                            $("#scrollBg ").hide();
                        }
                    }
                }
            });
        })
	</script>
</body>
</html>