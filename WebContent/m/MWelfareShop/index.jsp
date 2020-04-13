<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="maximum-scale=1.0, minimum-scale=1.0, user-scalable=0, initial-scale=1.0, width=device-width" />
    <meta name="format-detection" content="telephone=no, email=no, date=no, address=no">
    <title>首页</title>
    <jsp:include page="../_public_in_head.jsp" flush="true" />
    <link rel="stylesheet" type="text/css" href="${ctx}/m/styles/welfareShop/css/style.css?20190322" />
    <link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css" />
</head>
<style>
.welfare_cart{
	bottom: 28px;
    position: absolute;
    color: rgb(255, 255, 255);
/*     background-color: rgb(221, 82, 77); */
    font-size: 12px;
    padding: 3px 6px;
    border-radius: 100px;
    line-height: 1;
    left: 27px;
}
.desc{
    margin-bottom: 0px;
}
</style>
<body>
    <div id="container" class="recpage" v-cloak>
      <header id="header">
        <div class="flexbox">
          <div class="flex_item navleft">
            <a class="mui-action-back">
            	<i class="icon-back"></i>
            </a>
          </div>
          <div class="flex3 navtitle">
           		福利推荐
          </div>
          <div class="flex_item navright"></div>
        </div>
      </header>
      <div class="main_wrap" >
		<!--轮播 -->
		<div class="swiper-container" id="itemcontent1Swiper">
	        <div class="banner swiper-wrapper" >
	        	<div class="swiper-slide" v-for="banner in bannerImgs">
	          		<img :src="ctx + banner.image_path">
	          	</div>
	        </div>
        </div>
        <!--轮播结束 -->
        
        <div class="news flexbox">
          <div class="flex_item">
            <p class="title">挑夫头条:</p>
          </div>
          <div class="flex3 swiper-container" id="newSwiper">
            <div class="swiper-wrapper" @tap="openUrl(ctx+'m/MNewsInfo.do?mod_id=1015012000')">
            	<p class="desc swiper-slide" v-for="news in newsList">{{news.title}}</p>
            </div>
          </div>
        </div>

        <div class="recommend" style="padding-bottom: 50px;">
          <div class="title">
            <span class="block"></span><span>福利推荐</span>
          </div>
          <div class="list">
            <div class="item" v-for="comm in commList" style="height: auto;">
              <div class="item_wrap" @tap="openUrl(ctx+'m/MWelfareShop.do?method=getCommInfo&id='+comm.id)">
                <div class="back" style="height: auto;">
                  <img :src="ctx+comm.main_pic" style="width: 140px;height: 140px;margin-top: 0.4rem;">
                </div>
                <div class="p_name" style="margin-top: 0.4rem;height: 1.2rem;">
                  <p>{{comm.comm_name}}</p>
                  <div class="pvalue">
                    <span class="price" style="width: 45%;text-indent: .1rem;">￥{{comm.sale_price}}</span>
                    <span class="forsale" style="right: -0.25rem;">销量:{{comm.sale_count}}</span>
                  </div>
                </div>
                <div class="p_info flexbox">
                  <div class="btn" style="background-color: #c8c7cc;color:#e70049">福利商品</div>
                  <div class="btn">加入购物车</div>
                </div>
                
              </div>
            </div>
            <div class="clearfloat"></div>
          </div>
        </div>
        
        <div style="position: fixed;right: 4%;bottom: 10%;">
        <a href="${ctx}/m/MWelfareCartInfo.do">
        	<span class="welfare_cart">{{welfareCart_num}}</span>
        	<img class="aui-icon" style="width: 50px;height: 50px;" src="${ctx}/m/styles/shopcar.png">
<!-- 			<span class="aui-footer-item-icon aui-icon aui-footer-icon-car"></span> -->
		</a>
        </div>
      </div>
    </div>
    <jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/welfareShop/flexible.js"></script>
<script type="text/javascript" src="${ctx}/scripts/vue/vue.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/common.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/layer.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/public.js?v20180719"></script>
<script type="text/javascript">
var vm = new Vue({
	el: '#container',
	data: {
		ctx: Common.api,
		service_name:"",
		bannerImgs:"",
		newsList:"",
		commList:new Array(),
		welfareCart_num:"0",
	},
	mounted: function() {
		this.$nextTick(function() {
			this.getBannerImgs();
			this.getAjaxData();
			this.getWelfareCartNum();
		});
	},
	updated: function() {
		 new Swiper('#itemcontent1Swiper', {
			  observer: true,
	   		  autoplay:true,//等同于以下设置
	   		});
		 
		 new Swiper('#newSwiper', {
			 direction : 'vertical',
			  observer: true,
	   		  autoplay:true,//等同于以下设置
	   		});
	},
	methods: {
		getAjaxData: function() {
			Common.getData({
				route: 'm/MWelfareShop.do?method=getCommList&card_id='+${af.map.card_id},
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					} else if(data.code == 1) {
						vm.commList = data.commList;
						vm.service_name=data.service_name;
						console.info(vm.commList);
					}
				},
				error: function() {
					mui.toast('好像出错了哦~'); 
				}
			});
		},
		getBannerImgs: function() {
			Common.getData({
				route: 'm/MWelfareShop.do?method=getBannerImgs',
				success: function(data) {
					if(data.code == 0) {
						mui.toast(data.msg);
						return false;
					} else if(data.code == 1) {
						vm.bannerImgs = data.bannerImgs;
						vm.newsList = data.newsList;
						console.log(vm.bannerImgs)
					}
				},
				error: function() {
					mui.toast('好像出错了哦~'); 
				}
			});
		},
		getWelfareCartNum(){
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do", 
				data:"method=getCartNum&cart_type=20",
				dataType: "json", 
			    async: true, 
			    error: function (request, settings) {alert(" 数据加载请求失败！ ");}, 
			    success: function (data) {
					if (data.ret == 1) {
						vm.welfareCart_num=data.sumPdCount;
					} 
			    }
			});
		},
	    openUrl: function(url) {
			goUrl(url);
	    },
	}
});
</script>
</body>
</html>
