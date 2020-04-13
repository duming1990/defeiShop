//banner轮播
jQuery(".index_banner").slide({mainCell:".bd ul",autoPlay:true,interTime:6000,mouseOverStop:false,effect:"fold"});
//限制高度
var Wheight=$(window).height();
$('.index_banner,.index_banner .item,.bannerWrap,.index_banner .bd,.index_banner .bd ul,.index_banner .bd ul li,.index_o,.index_t,.index_s,.index_f,.index_w,.cuowu').height(Wheight);	
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
	//集团分布
    $('.index_sub .list').owlCarousel({
    items: 3,
    navigationText: ["<img src='/public/static/index/images/index_fbz.png'/>","<img src='/public/static/index/images/index_fby.png' />"],
    pagination: true,
    //paginationNumbers:true,
    navigation: true,
    beforeUpdate:true
   });
   $("#fp-nav ul").addClass("nav");
	//导航条只在第一屏展示
   if($(".bannerWrap").offset().top==0){
		$(".header").slideDown(600)
	}else if($(".bannerWrap").offset().top > 0){
		$(".header").slideUp(600)
	}
	$(".nav li").click(function(){
		var index = $(this).index();
		if(index>0){
			$('.header').hide();
		}else{
			$('.header').show();
		}
	})
	//集团分布
	$(".index_sub .owl-theme .owl-controls .owl-buttons .owl-next img").hover(function(){
		$(this).attr("src","/public/static/index/images/index_fby2.png")
	},function(){
		$(this).attr("src","/public/static/index/images/index_fby.png")
	})
	$(".index_sub .owl-theme .owl-controls .owl-buttons .owl-prev img").hover(function(){
		$(this).attr("src","/public/static/index/images/index_fbz2.png")
	},function(){
		$(this).attr("src","/public/static/index/images/index_fbz.png")
	})
	//新闻中心
	$(".index_f_lg_s .y").hover(function(){
		$(this).children("img").attr("src","/public/static/index/images/index_yj2.png")
	},function(){
		$(this).children("img").attr("src","/public/static/index/images/index_yj.png")
	})
})




