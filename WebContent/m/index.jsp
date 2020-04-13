<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name_min}—原产地直供商城</title>
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style"/>
<meta content="telephone=no" name="format-detection"/>
<jsp:include page="_public_in_head_new.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/show/css/swiper-3.4.1.min.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/index.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/show/css/index.css?20180606">
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/show/css/public.css">
<style type="text/css">
.c-hd section.city{padding: .14rem .42rem 0 .0rem;margin-top: .12rem;}
.c-hd section.city2{padding: .14rem .0rem 0 .42rem;margin-top: .12rem;}
.aui-list-product-box{padding:0px!important;}
.comms{
    text-align: center;
}
.pd_price{
    text-align: center;
    color: red;
}
.aui-slide-item-f-els{
	-webkit-line-clamp:1 !important;
}
.v-link{
    width: 33.333% !important;
    height: 100%;
}
.div31{
	background-color: white!important;
}
.m-back-to-top {
    display: none;
    position: fixed;
    bottom: 80px;
    right: 9px;
    width: 38px;
    height: 38px;
    background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAE4AAABOCAYAAACOqiAdAAAACXBIW…EKBvtch4MGPgClL9Hnes8JXFMZ3EggAcYwJH4iQfnq/wcAXUhTNPpF7Q0AAAAASUVORK5CYII=);
    background-size: 38px 38px;
    background-repeat: no-repeat;
    background-position: 50%;
    z-index: 20;
}
</style>
</head>
<body>
<div id="app" v-cloak>
<!-- <div class="m-back-to-top j_back_to_top" style="display: inline;"> -->
<!-- <script type="text/javascript" src="http://112.30.63.109:8887/resource/scripts/controller/common/businessLicense.js?id=348ec77d208540d7825372d3a8ab2b19"></script> -->
<!-- </div> -->
<div id="pullrefresh" class="mui-slider mui-fullscreen" style="touch-action: none;">
  <div class="m-hd">
		<div class="m-indexHd">
			<div class="line c-hd">
				<section class="city" v-show="isWeixin" id="scanQRCode"><span class="aui-icon aui-icon-scan"></span></section>
				<div class="m-topSearchIpt ipt" @click="btnSearch()"><i class="icon"></i><span class="placeholder">搜索商品</span></div>
				<section class="city2" @click="openMyCard('m/MMyCard.do')"><span class="aui-icon aui-icon-mycard"></span></section>
			</div>
			<div class="m-tabs scroll">
				<header>
				<div class="inner mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted"  id="swiper_class_right">
					<div class="list swiper-wrapper mui-scroll">
						<a class="tab mui-control-item swiper-slide" :class="{'mui-active':tag_id_index == 0}" @tap="entpTabClick(0)"  href="#itemcontent0"><span class="txt">推荐</span></a>
<%-- 						<c:if test="${index_show_ys eq 1 }"> --%>
						<a class="tab mui-control-item swiper-slide" :class="{'mui-active':tag_id_index == 1}" @tap="entpTabClick(1)"  href="#itemcontent1" v-if="index_show_ys == 1"><span class="txt">预售</span></a>
<%-- 						</c:if> --%>
<%-- 						<c:if test="${index_show_pt eq 1 }"> --%>
						<a class="tab mui-control-item swiper-slide" :class="{'mui-active':tag_id_index == 2}" @tap="entpTabClick(2)"  href="#itemcontent2" v-if="index_show_pt == 1"><span class="txt">拼团</span></a>
<%-- 						</c:if> --%>
						
						<a v-for="item,index in showTagList" :class="{'mui-active':tag_id_index == item.id}" @tap="entpTabClick(item.id)" class="tab mui-control-item swiper-slide" :href="'#itemcontent'+ (index + 3)" ><span class="txt">{{item.tag_name}}</span></a>
					</div>
				</div>
				<div class="dropdown" @click="showPinDao"><i class="aui-icon aui-icon-add" style="height:2rem;"></i></div>
				</header>
			</div>
		</div>
	</div>
  <div class="mui-slider-group" id="mui-slider-group">
  <div class="aui-content-box mui-slider-item mui-control-content" :class="{'mui-active' : tag_id_index  == 0}" id="itemcontent0" >
  	<div class="mui-scroll-wrapper" v-show="tag_id_index  == 0">
  	<div class="mui-scroll" id="index" flag="1">
  	<!-- 轮播图 -->
    <div class="aui-banner-content swiper-container" id="itemcontent1Swiper">
      <div class="aui-banner-wrapper swiper-wrapper">
        <div class="aui-banner-wrapper-item swiper-slide" v-for="item in datas.mBaseLinkList10">
          <a @click="openUrl(item)">
            <img :src="ctx + item.image_path + '@compress'" />
          </a>
        </div>
      </div>
      <div class="aui-banner-pagination swiper-pagination"></div>
    </div>
    <!-- 轮播图  end -->
    <!-- 导航 -->
    <section class="aui-grid-content">
      <div class="aui-grid-row aui-grid-row-clears">
        <a @click="openUrl(item)" class="aui-grid-row-item" v-for="item in datas.mBaseLinkList20">
          <i class="aui-icon-large" :style="{backgroundImage: 'url(\''+ctx + item.image_path+'\')'}"></i>
          <p class="aui-grid-row-label">{{item.title}}</p>
        </a>
      </div>
    </section>
    <!-- 导航 end -->
<!--     <div class="aui-list-content"> -->
<!--     <div v-for="item in datas.mBaseLinkList30"> -->
<!--      <div class="aui-title-head"> -->
<!--        <img @click="openUrl(item)" :src="ctx + item.image_path" /> -->
<!--      </div> -->
<!--       <div class="aui-list-item"> -->
<!--         <div class="aui-list-item-img"> -->
<!--           <img @click="openUrl(item40)" :src="ctx + item40.image_path" v-for="item40 in item.map.mBaseLinkList40" /> -->
<!--         </div> -->
<!--         <div class="aui-slide-box"> -->
<!--           <div class="aui-slide-list swiper-container-comm"> -->
<!--             <ul class="aui-slide-item-list swiper-wrapper"> -->
<!--               <li class="aui-slide-item-item swiper-slide" v-for="item50 in item.map.mBaseLinkList50"> -->
<!--                 <a @click="openUrl(item50)" class="v-link"> -->
<!--                   <img class="v-img" :src="ctx + item50.image_path" /> -->
<!--                   <p class="aui-slide-item-title aui-slide-item-f-els">{{item50.title}}</p> -->
<!--                   <p class="aui-slide-item-info"> -->
<!--                     <span class="aui-slide-item-price">¥{{item50.pd_price | formatMoney}}</span> -->
<!--                   </p> -->
<!--                 </a> -->
<!--               </li> -->
<!--             </ul> -->
<!--           </div> -->
<!--         </div> -->
<!--       </div> -->
<!--      </div> -->
<!--     </div> -->
    <div class="container index" v-for="item in datas.mBaseLinkList30">
		    <div v-if ="item.pre_number == 1">
		    		 <div class="div1" >
				        <img :src="ctx + item.image_path" @click="openUrl(item)"/>
				        <img :src="ctx + item101.image_path" v-for="item101 in item.map.mBaseLinkList101" @click="openUrl(item101)"/>
				    </div>
		    </div>
		    <div v-if ="item.pre_number == 2">
		        <div class = "div1">
		    		<img  :src="ctx + item.image_path" @click="openUrl(item)"/>
		   		</div>
		    <div class="div2"  >
		        <div class="div2_img div2_img1" >
		            <img :src="ctx + item201.image_path" v-for="item201 in item.map.mBaseLinkList201" @click="openUrl(item201)" />
		        </div>
		        <div class="div2_img div2_img2" >
		            <img :src="ctx + item202.image_path" v-for="item202 in item.map.mBaseLinkList202" @click="openUrl(item202)"/>
		        </div>
		    </div>
		    </div>
		    <div v-if ="item.pre_number == 3" >
			    <div class="div1" >
			        <img :src="ctx + item.image_path" @click="openUrl(item)"/>
			    </div>
		    <div class="swiper-container" :id="'floorSwiper_'+item.id">
		        <div class="swiper-wrapper">
		            <div class="swiper-slide" v-if="item.map.mBaseLinkList301 != ''">
		                <div class="div3 div31">
		                  <a @click="openUrl(item301)"  v-for="item301 in item.map.mBaseLinkList301" class="v-link">
                 			 <img class="v-img" :src="ctx + item301.image_path" />
               					<p class="aui-slide-item-title aui-slide-item-f-els  comms">{{item301.title}}</p>
                				  <p class="aui-slide-item-info pd_price">
                               <span class="aui-slide-item-price">¥{{item301.pd_price | formatMoney}}</span>
                     		</p>
                     	  </a>
		                </div>
		            </div>
		            <div class="swiper-slide" v-if="item.map.mBaseLinkList302 != ''">
		                <div class="div3 div31">
		      			  <a @click="openUrl(item302)"  v-for="item302 in item.map.mBaseLinkList302" class="v-link">
                 			 <img class="v-img" :src="ctx + item302.image_path" />
               					<p class="aui-slide-item-title aui-slide-item-f-els comms">{{item302.title}}</p>
                				  <p class="aui-slide-item-info pd_price">
                               <span class="aui-slide-item-price">¥{{item302.pd_price | formatMoney}}</span>
                     		</p>
                     	  </a>
		                </div>
		            </div>
		            <div class="swiper-slide" v-if="item.map.mBaseLinkList303 != ''">
		                <div class="div3 div31" >
		           		   <a @click="openUrl(item303)"  v-for="item303 in item.map.mBaseLinkList301" class="v-link">
                 			 <img class="v-img" :src="ctx + item303.image_path" />
               					<p class="aui-slide-item-title aui-slide-item-f-els  comms">{{item303.title}}</p>
                				  <p class="aui-slide-item-info pd_price">
                               <span class="aui-slide-item-price">¥{{item303.pd_price | formatMoney}}</span>
                     		</p>
                     	  </a>
		                </div>
		            </div>
		        </div>
		        <div class="swiper-button-prev swiper-button-white"></div>
		        <div class="swiper-button-next swiper-button-white"></div>
		    </div>
		    <div class="div1" >
		        <img :src="ctx + item304.image_path" v-for="item304 in item.map.mBaseLinkList304" @click="openUrl(item304)"/>
		    </div>
		</div>
	    <div v-if ="item.pre_number == 4" >
	   		<div class="div1">
	      		 <img :src="ctx + item.image_path" @click="openUrl(item)"/>
	        </div>
	    <div class="div4 div41" >
	        <div class="div4_img1">
	            <img :src="ctx + item401.image_path"  v-for="item401 in item.map.mBaseLinkList401" @click="openUrl(item401)"/>
	        </div>
	        <div class="div4_img2" >
	            <img :src="ctx + item402.image_path"v-for="item402 in item.map.mBaseLinkList402" @click="openUrl(item402)"/>
	        </div>
	    </div>
	    <div class="div1">
	        <img :src="ctx + item403.image_path" v-for="item403 in item.map.mBaseLinkList403" @click="openUrl(item403)"/>
	    </div>
	</div>
	<div v-if ="item.pre_number == 5" >
		    <div class="div1">
		        <img :src="ctx + item.image_path" @click="openUrl(item)"/>
		    </div>
		    <div class="div1"  v-for="item501 in item.map.mBaseLinkList501">
		        <img :src="ctx + item501.image_path"  v-for="item501 in item.map.mBaseLinkList501" @click="openUrl(item501)"/>
		    </div>
	    <div class="div4 div42">
	        <div class="div4_img2"  v-for="item502 in item.map.mBaseLinkList502">
	            <img :src="ctx + item502.image_path" v-for="item502 in item.map.mBaseLinkList502" @click="openUrl(item502)"/>
	        </div>
	        <div class="div4_img1"  v-for="item503 in item.map.mBaseLinkList503">
	            <img :src="ctx + item503.image_path" v-for="item503 in item.map.mBaseLinkList503" @click="openUrl(item503)"/>
	        </div>
	    </div>
	</div>
	<div v-if ="item.pre_number == 6" >
	    <div class="div1" >
			<img :src="ctx + item.image_path" @click="openUrl(item)"/>
	    </div>
	    <div class="div1"  >
	        <img :src="ctx + item601.image_path" v-for="item601 in item.map.mBaseLinkList601" @click="openUrl(item601)"/>
	    </div>
 		<div class="swiper-container" :id="'floorSwiper_'+item.id">
        <div class="swiper-wrapper">
            <div class="swiper-slide" v-if="item.map.mBaseLinkList602 != ''">
                <div class="div3 div31">
                  <a @click="openUrl(item602)"  v-for="item602 in item.map.mBaseLinkList602" class="v-link">
               			 <img class="v-img" :src="ctx + item602.image_path" />
             					<p class="aui-slide-item-title aui-slide-item-f-els  comms">{{item602.title}}</p>
              				  <p class="aui-slide-item-info pd_price">
                             <span class="aui-slide-item-price">¥{{item602.pd_price | formatMoney}}</span>
                   		</p>
                   	  </a>
                </div>
            </div>
            <div class="swiper-slide" v-if="item.map.mBaseLinkList603 != ''">
                <div class="div3 div31">
      			  <a @click="openUrl(item603)"  v-for="item603 in item.map.mBaseLinkList603" class="v-link">
               			 <img class="v-img" :src="ctx + item603.image_path" />
             					<p class="aui-slide-item-title aui-slide-item-f-els comms">{{item603.title}}</p>
              				  <p class="aui-slide-item-info pd_price">
                             <span class="aui-slide-item-price">¥{{item603.pd_price | formatMoney}}</span>
                   		</p>
                   	  </a>
                </div>
            </div>
            <div class="swiper-slide" v-if="item.map.mBaseLinkList604 != ''">
                <div class="div3 div31" >
           		   <a @click="openUrl(item604)"  v-for="item604 in item.map.mBaseLinkList604" class="v-link">
               			 <img class="v-img" :src="ctx + item604.image_path" />
             					<p class="aui-slide-item-title aui-slide-item-f-els  comms">{{item604.title}}</p>
              				  <p class="aui-slide-item-info pd_price">
                             <span class="aui-slide-item-price">¥{{item604.pd_price | formatMoney}}</span>
                   		</p>
                   	  </a>
                </div>
            </div>
         </div>
            <div class="swiper-button-prev swiper-button-white"></div>
		    <div class="swiper-button-next swiper-button-white"></div>
       </div>
	 </div>
    </div>
    <div class="aui-recommend commlist">
      <img :src="ctx + item60.image_path" v-for="item60 in datas.mBaseLinkList60"/>
    </div>
    <section class="aui-list-product">
      <div class="aui-list-product-box">
        <a @click="openUrl(item)" class="aui-list-product-item" v-for="item in commInfoList">
          <div class="aui-list-product-item-img">
            <img :src="ctx + item.main_pic + '@s400x400'" />
          </div>
          <div class="aui-list-product-item-text">
            <h3>{{item.comm_name}}</h3>
            <span class="commInfo_from" v-if="item.map.commZyName != null">{{item.map.commZyName}}</span>
            <div class="aui-list-product-mes-box">
              <div>
                <span class="aui-list-product-item-price"><em>¥</em>{{item.sale_price | formatMoney}}</span>
              </div>
              <div class="aui-comment">已售{{item.sale_count_update}}</div>
            </div>
          </div>
        </a>
      </div>
    </section>
   </div>
  </div>
 </div>
  <div id="itemcontent1" :class="{'mui-active' : tag_id_index  == 1}" class="aui-content-box mui-slider-item mui-control-content" v-if="index_show_ys == 1">
  <div class="mui-scroll-wrapper" v-show="tag_id_index  == 1">
    <section class="aui-list-product mui-scroll" id="ys" flag="1" style="padding: 0px 0px 150px;">
      <div class="aui-list-product-box">
        <a @click="openUrl(item)" class="aui-list-product-item" v-for="item in commInfoYsList">
          <div class="aui-list-product-item-img">
            <img :src="ctx + item.main_pic + '@s400x400'" />
          </div>
          <div class="aui-list-product-item-text">
            <h3>{{item.comm_name}}</h3>
            <span class="commInfo_from" v-if="item.map.commZyName != null">{{item.map.commZyName}}</span>
             <div class="aui-comment">{{item.comm_notes}}</div>
            <div class="aui-list-product-mes-box">
              <div>
                <span class="aui-list-product-item-price"><em>¥</em>{{item.sale_price | formatMoney}}</span>
              </div>
              <div class="aui-comment">已售{{item.sale_count_update}}</div>
            </div>
          </div>
        </a>
      </div>
    </section>
   </div>
  </div>
  <!-- 拼团 -->
  <div id="itemcontent2" :class="{'mui-active' : tag_id_index  == 2}" class="aui-content-box mui-slider-item mui-control-content" v-if="index_show_pt == 1">
  <div class="mui-scroll-wrapper" v-show="tag_id_index  == 2">
    <section class="aui-list-product mui-scroll" id="pintuan" flag="1" style="padding: 0px 0px 150px;">
      <div class="aui-list-product-box">
        <a @click="openUrl(item)" class="aui-list-product-item" v-for="item in commInfoPtList">
          <div class="aui-list-product-item-img">
            <img :src="ctx + item.main_pic + '@s400x400'" />
          </div>
          <div class="aui-list-product-item-text">
            <h3>{{item.comm_name}}</h3>
            <span class="commInfo_from" v-if="item.map.commZyName != null">{{item.map.commZyName}}</span>
            <div class="aui-list-product-mes-box">
              <div>
                <span class="aui-list-product-item-price"><em>¥</em>{{item.map.commTczhPriceList[0].group_price | formatMoney}}</span>
              </div>
              <div class="aui-comment">已售{{item.sale_count_update}}</div>
            </div>
          </div>
        </a> 
      </div>
    </section>
   </div>
  </div>
   
  <div :id="'itemcontent'+ (index + 3)" :class="{'mui-active' : tag_id_index  == item.id}" class="aui-content-box mui-slider-item mui-control-content" :tag_id="item.id" v-for="item,index in showTagList">
  <div class="mui-scroll-wrapper" v-show="tag_id_index  == item.id">
    <!-- 轮播图 -->
    <div class="aui-banner-content">
      <div class="aui-banner-wrapper">
        <div class="aui-banner-wrapper-item swiper-slide">
          <a><img :src="ctx + item.image_path" /></a>
        </div>
      </div>
    </div>
    <!-- 轮播图  end -->
<!--     <div class="aui-recommend"> -->
<%--       <img src="${ctx}/m/styles/themes/img/bg/icon-tj1.jpg" /> --%>
<!--     </div> -->
    <section class="aui-list-product mui-scroll" :tag_id="item.id" flag="1" style="padding: 0px 0px 150px;">
      <div class="aui-list-product-box">
        <a @click="openUrl(item)" class="aui-list-product-item" v-for="item in item.commInfoList">
          <div class="aui-list-product-item-img">
            <img :src="ctx + item.main_pic + '@s400x400'" />
          </div>
          <div class="aui-list-product-item-text">
            <h3>{{item.comm_name}}</h3>
            <span class="commInfo_from" v-if="item.map.commZyName != null">{{item.map.commZyName}}</span>
            <div class="aui-list-product-mes-box">
              <div>
                <span class="aui-list-product-item-price"><em>¥</em>{{item.sale_price | formatMoney}}</span>
              </div>
              <div class="aui-comment">已售{{item.sale_count_update}}</div>
            </div>
          </div>
        </a>
      </div>
    </section>
   </div>
  </div>
  </div>
  <div id="pinDaoContent">
  <div class="pindaoSelect">
	<div class="header">我的频道<a class="backBtn" @click="closePinDao"><i class="aui-icon aui-icon-close"></i></a></div>
	<div class="main">
		<div class="meta"><div><a>已选频道</a></div></div>
		<ul class="channel-list on">
			<li class="removeitem static"><a>推荐</a></li>
			<li class="removeitem nav_local" v-for="item in hasSelectList"><a>{{item.tag_name}}</a><button @click="deletePinDao(item);"></button></li>
		</ul>
		<div class="meta">
			<div><a>点击添加以下频道</a></div>
		</div>
		<div class="mui-segmented-control" style="margin: 0px 15px;width:auto;">
			<a class="mui-control-item mui-active" href="#item1" style="line-height:28px!important;">全国频道</a>
			<a class="mui-control-item" href="#item2" style="line-height:28px!important;">地方频道</a>
		</div>
		<div id="item1" class="mui-control-content mui-active">
		<div>
			<ul class="channel-list on">
				<li class="additem" v-for="item in datas.baseComminfoTagsList"><a href="javascript:void(0)" @click="selectPinDao(item);">{{item.tag_name}}</a></li>
			</ul>
		</div>
		</div>
		<div id="item2" class="mui-control-content">
 		<div>
			<ul class="channel-list on">
				<li class="additem" v-for="item in datas.baseComminfoTagsProList"><a href="javascript:void(0)" @click="selectPinDao(item);">{{item.tag_name}}</a></li>
 			</ul>
		</div> 
		</div>
	</div>
</div>
</div>
</div>
</div>  
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/mui.pullToRefresh.material.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<jsp:include page="./_footer.jsp" flush="true" />
<script type="text/javascript">
window.addEventListener('touchmove',function(e){e.preventDefault();});
  var vm = new Vue({
    el: '#app',
    data: {
      totalMoney: 0,
      ctx:Common.api,
      search:'styles/index/images/search.png',
      commInfoList:"",
      commInfoYsList:"",
      commInfoPtList:"",
      hasSelectList:new Array(),
      showTagList:new Array(),
      delItem:'', 
      datas:"",
      isWeixin:false,
      upLevelNeedPayMoney:'',
      tag_id_index: 0,
      index_show_pt:0,
      index_show_ys:0,
    },
    method:{
 
    },
    mounted: function() {
      
      Common.loading();	
      this.$nextTick(function() {
    	  var inData = { 
   			  	appId: '${appid}',
       	      	timestamp: '${timestamp}',
       	      	nonceStr: '${nonceStr}',
       	      	signature: '${signature}'
          };
    	  var shareData = { 
    		  	title : '${app_name}',
  			 	desc : '${ShareIndexDesc}',
  				link : 'http://www.9tiaofu.com/m/index.shtml',
  				imgUrl : Common.api + 'styles/imagesPublic/WeiXinShare.jpg',
          };
    	  Common.weixinConfig(inData,shareData);	  
    	  this.getAjaxData();
    	  
    	  
    	  
    	  (function($) {
  			//阻尼系数
  			var deceleration = mui.os.ios?0.003:0.0009;
  			$('.mui-scroll-wrapper').scroll({
  				bounce: false,
  				indicators: true, //是否显示滚动条
  				deceleration:deceleration
  			});
  			
  			//参数定义
  			$.ready(function() {
  				document.getElementById('pullrefresh').addEventListener('slide', function(e) {
  					console.log("fun param"+e.detail.slideNumber)
  					var tag_id;
  					if (e.detail.slideNumber != 0) {//初始化数据
  						if(vm.index_show_pt == 0 && vm.index_show_ys == 0){
  							tag_id = document.getElementById("itemcontent" +(Number(e.detail.slideNumber+2))).getAttribute("tag_id");
  						}
  						if((vm.index_show_pt == 0 && vm.index_show_ys == 1) || 
  								(vm.index_show_ys == 0 && vm.index_show_pt == 1)){
  							if(e.detail.slideNumber>1){
  								tag_id = document.getElementById("itemcontent" +(Number(e.detail.slideNumber+1))).getAttribute("tag_id");
  							}else{
  								return false;
  							}
  						}
  						if(vm.index_show_pt == 1 && vm.index_show_ys == 1){
  							if(e.detail.slideNumber<=2){
  								return false;
  							}else{
  								var tag_id = document.getElementById("itemcontent" +(Number(e.detail.slideNumber))).getAttribute("tag_id");
  							}
  						}
	  						
  					console.log("jq_get"+tag_id)
  						vm.tag_id_index  = tag_id;
  					console.log("vm,data:"+vm.tag_id_index)
  						Common.getData({
  							route: 'm/MTags.do?method=getAjaxData',
  							data: {
  								startPage:0,
  								tag_id:tag_id
  							},
  							success: function(data) {
  								if(null != data.datas.commInfoList){
  									for(var y=0;y<vm.showTagList.length;y++){
  										if(vm.showTagList[y].id == tag_id){
  											Vue.set(vm.showTagList[y], 'commInfoList', data.datas.commInfoList);
  											break;
  										}
  									}
  								}
  							},
  							error: function() {
  								mui.alert('好像出错了哦~')
  							}
  						});
  					}
  				});
  				
  			});
  		})(mui);
    	  
    	  
    	  
      });
    },
    updated: function() {
    	Common.hide();
    	
   	    new Swiper('#itemcontent1Swiper', {
   		  autoplay:true,//等同于以下设置
   		  pagination: {
  		        el: '.swiper-pagination',
  		        bulletClass : 'slider-pagination-item',
  		        bulletActiveClass: 'slider-pagination-item-active',
   		  },
   		});
	   	new Swiper('.swiper-container-comm', {
			slidesPerView: 'auto',
			paginationClickable: true,
			spaceBetween: 20
		});
		new Swiper('.swiper-container-top', {
			slidesPerView: 'auto',
			paginationClickable: true
		});
		
		if(null != vm.datas.mBaseLinkList30){
			for(var y=0;y<vm.datas.mBaseLinkList30.length;y++){
				if(vm.datas.mBaseLinkList30[y].pre_number == 3 || vm.datas.mBaseLinkList30[y].pre_number == 6){
					new Swiper('#floorSwiper_'+vm.datas.mBaseLinkList30[y].id, {
			          slidesPerView : 'auto',
					  navigation: {
						    nextEl: '.swiper-button-next',
						    prevEl: '.swiper-button-prev',
						  },
					  autoplay: {
						    disableOnInteraction: false,
						  },
				    });
				}
			}
		}
		
		new Swiper('#swiper_class_right', {
          slidesPerView : 'auto',
	    });
		
		(function($) {
			$.ready(function() {
				var pageSize=10;
				$.each(document.querySelectorAll('.mui-slider-group .mui-scroll'), function(index, pullRefreshEl) {
					
					$(pullRefreshEl).pullToRefresh({
						up: {
							callback: function() {
								var self = this;
								setTimeout(function() {
									var flag = pullRefreshEl.getAttribute("flag");
									if("index"==pullRefreshEl.id){
										Common.getData({
										route: 'm/MSearch.do?method=getPdListJson',
										data: {
											pageSize:pageSize,
											startPage:flag,
											isIndex:true
										},
										success: function(data) {
											if(null != data.datas.dataList){
												flag = Number(flag) + 1;
												pullRefreshEl.setAttribute("flag",flag);
												vm.commInfoList = vm.commInfoList.concat(data.datas.dataList);
												self.endPullUpToRefresh(data.datas.dataList.length < pageSize);
											}
										},
										error: function() {
											mui.alert('好像出错了哦~')
										}
										});
									}else if("ys"==pullRefreshEl.id){
										if(vm.index_show_ys == 1){
											Common.getData({
												route: 'm/MSearch.do?method=getPdListJson',
												data: {
													pageSize:pageSize,
													startPage:flag,
													commType:10
												},
												success: function(data) {
													if(null != data.datas.dataList){
														flag = Number(flag) + 1;
														pullRefreshEl.setAttribute("flag",flag);
														vm.commInfoYsList = vm.commInfoYsList.concat(data.datas.dataList);
														self.endPullUpToRefresh(data.datas.dataList.length < pageSize);
													}
												},
												error: function() {
													mui.alert('好像出错了哦~')
												}
												});
										}
									}else if("pintuan"==pullRefreshEl.id){
										if(vm.index_show_pt == 1){
											Common.getData({
												route: 'm/MSearch.do?method=getPdListJson',
												data: {
													pageSize:pageSize,
													startPage:flag,
													commType:20
												},
												success: function(data) {
													if(null != data.datas.dataList){
														flag = Number(flag) + 1;
														pullRefreshEl.setAttribute("flag",flag);
														vm.commInfoPtList = vm.commInfoPtList.concat(data.datas.dataList);
														self.endPullUpToRefresh(data.datas.dataList.length < pageSize);
													}
												},
												error: function() {
													mui.alert('好像出错了哦~')
												}
												});
										}
									}else{//标签内容
										var tag_id = pullRefreshEl.getAttribute("tag_id");
										
										Common.getData({
										route: 'm/MTags.do?method=getAjaxData',
										data: {
											startPage:flag,
											tag_id:tag_id
										},
										success: function(data) {
											if(null != data.datas.commInfoList){
											    flag = Number(flag) + 1;
											    pullRefreshEl.setAttribute("flag",flag);
											    for(var y=0;y<vm.showTagList.length;y++){
													if(vm.showTagList[y].id == tag_id){
														vm.showTagList[y].commInfoList = vm.showTagList[y].commInfoList.concat(data.datas.commInfoList);
													//	Vue.set(vm.showTagList[y], 'commInfoList', data.datas.commInfoList);
														break;
													}
												}
												self.endPullUpToRefresh(data.datas.commInfoList.length < pageSize);
											}
										},
										error: function() {
											mui.alert('好像出错了哦~')
										}
										});
									}
									self.endPullUpToRefresh();
								}, 1000);
							}
						}
					});
				});
			});
		})(mui);
    },
    methods: {
      getAjaxData: function() {
    	  vm.index_show_pt = '${index_show_pt}';
          vm.index_show_ys = '${index_show_ys}';
   		Common.getData({
   			route: 'm/Index.do?method=getAjaxData',
   			success: function(data) {
   				if(data.code == 0) {
   				  mui.alert(data.msg);
   	              return false;
   	            } else if(data.code == 1) {
   	            	vm.datas = data.datas;
   	            	vm.isWeixin = data.datas.isWeixin;
   	            	vm.upLevelNeedPayMoney = data.datas.upLevelNeedPayMoney;
   	            	if(null != localStorage.getItem("hasSelectList")){
   	        			var localStorageList = JSON.parse(localStorage.getItem("hasSelectList"));
   	        			vm.hasSelectList = vm.hasSelectList.concat(localStorageList);
   	        			
   	        			//这个地方替换掉原来的图片万一后台修改了图片信息
   	        			var baseCommInfoTagList = [].concat(vm.datas.baseComminfoTagsList);
   	        			var hasSelectList = [].concat(vm.hasSelectList);
   	        			for(y=0;y<baseCommInfoTagList.length;y++){//默认标签
   	        				for(x=0;x<hasSelectList.length;x++){//默认标签
   	   	   	            		if(baseCommInfoTagList[y].id == hasSelectList[x].id){
   	   	   	            			hasSelectList[x].image_path = baseCommInfoTagList[y].image_path;
   	   	   	     					break;
   	   	   	            		}
   	   	   	            	}
   	   	            	}
   	        			vm.showTagList =hasSelectList;
   	        			var arrayLists = vm.datas.baseComminfoTagsList.concat();
   	        			//这个地方需要剔除掉已经选择过的
   	        			for(var x=0;x<arrayLists.length;x++){
   	        				for(var y=0;y<vm.hasSelectList.length;y++){
   	        					if(vm.hasSelectList[y].id == arrayLists[x].id){// 包含这个
   	        		    	        vm.datas.baseComminfoTagsList.splice(vm.datas.baseComminfoTagsList.indexOf(arrayLists[x]),1);
   	        		    	        break;
   	        					}
   	        	   			}
   	        			}
   	        			var arrayLists = vm.datas.baseComminfoTagsProList.concat();
   	        			for(var x=0;x<arrayLists.length;x++){
   	        				for(var y=0;y<vm.hasSelectList.length;y++){
   	        					if(vm.hasSelectList[y].id == arrayLists[x].id){// 包含这个
   	        		    	        vm.datas.baseComminfoTagsProList.splice(vm.datas.baseComminfoTagsProList.indexOf(arrayLists[x]),1);
   	        		    	        break;
   	        					}
   	        	   			}
   	        			}
   	        		}else{
   	        			vm.showTagList = vm.datas.baseComminfoTagsList;
   	        			var baseComminfoTagsList = [] || vm.datas.baseComminfoTagsList;
   	   	            	for(y=0;y<baseComminfoTagsList.length;y++){//默认标签
   	   	            		if(baseComminfoTagsList[y].index_lock == 1){
   	   	     					vm.selectPinDao(baseComminfoTagsList[y]);
   	   	            		}
   	   	            	}
   	        		}
   	            	
   	                var tag_id = null;
   	            	if( null!=localStorage.getItem("tag_id")){
   	            		tag_id = localStorage.getItem("tag_id");
   	            		localStorage.removeItem("tag_id");
   	   	     	     }else{
   	   	     			tag_id = Common.getUrlParam("tag_id");
   	   	     	     }
   	                var flag = false;
   	                var flagPos = 1;
   	                if(null != tag_id && "" != tag_id){
   	                	for(var x=0; x<vm.showTagList.length;x++){
   	                		if(vm.showTagList[x].id == tag_id){
   	                			flagPos = x + 1;
   	                			flag = true;
   	                			break;
   	                		}
   	                	}
   	                	if(flag){
   	                		Common.getData({
	 	   	   						route: 'm/MTags.do?method=getAjaxData',
	 	   	   						data: {
	 	   	   							startPage:0,
	 	   	   							tag_id:tag_id
	 	   	   						},
	 	   	   						success: function(data) {
	 	   	   							if(null != data.datas.commInfoList){
	 	   	   							 vm.addPindao(tag_id,data.datas.commInfoList,flagPos);
	 	   	   							}
	 	   	   						},
	 	   	   						error: function() {
	 	   	   							mui.alert('好像出错了哦~')
	 	   	   						}
	 	   	   					}); 
   	                	}else{
   	                		//给提示
   	                		Common.getData({
	   	   						route: 'm/MTags.do?method=getAjaxData',
	   	   						data: {
	   	   							startPage:0,
	   	   							tag_id:tag_id
	   	   						},
	   	   						success: function(data) {
// 	   	   						mui.toast("你还没有订阅该模块,已自动帮你订阅该模块");
	   	   							vm.selectPinDao(data.datas.baseCommInfoTags);
	   	   						    flagPos = vm.showTagList.length;
	   	   							vm.addPindao(tag_id,data.datas.commInfoList,flagPos);
	   	   						},
	   	   						error: function() {
	   	   							mui.alert('好像出错了哦~')
	   	   						}
	   	   					}); 
   	                		
   	                	}
	   	            }
   	            }
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
   		Common.getData({
   			route: 'm/MSearch.do?method=getPdListJson',
   			data: {
				isIndex:true
			},
   			success: function(data) {
   				if(null != data.datas.dataList){
   				  vm.commInfoList = data.datas.dataList;
   				}
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
   		if(vm.index_show_ys == 1){//首页是否显示预售
	   		Common.getData({
	   			route: 'm/MSearch.do?method=getPdListJson',
	   			data: {
					isIndex:true,
					commType:10
				},
	   			success: function(data) {
	   				if(null != data.datas.dataList){
	   				  vm.commInfoYsList = data.datas.dataList;
	   				}
	   			},
	   			error: function() {
	   				mui.alert('好像出错了哦~');
	   			}
	   		});
   		}
   		if(vm.index_show_pt == 1){//首页是否显示拼团
	   		//页面初始化加载拼团商品
	   		Common.getData({
	   			route: 'm/MSearch.do?method=getPdListJson',
	   			data: {
					isIndex:true,
					commType:20
				},
	   			success: function(data) {
	   				if(null != data.datas.dataList){
	   				  vm.commInfoPtList = data.datas.dataList;
	   				}
	   			},
	   			error: function() {
	   				mui.alert('好像出错了哦~');
	   			}
	   		});
   		}
   		
      },
      openUrl: function(item) {
    	 localStorage.setItem("tag_id",vm.tag_id_index);
    	  if(null != item.link_url){
    	    goUrl(item.link_url);
    	  }else{
    		  var url = vm.ctx + "m/MEntpInfo.do?id=" + item.id;
    		  goUrl(url);
    	  }
	  },
	  openMyCard: function(url) {
// 		  <c:if test="${(not empty userInfo) and (userInfo.user_level eq 201)}">
// 		    Common.confirm("付费会员将缴费"+ vm.upLevelNeedPayMoney +"元,你确定要升级成为付费会员吗？",["确定","取消"],function(){
// 	    		var url = vm.ctx + "m/MIndexPayment.do?method=PayForUpLevel";
// 				goUrl(url);
			//},
// 			function(){
// 			});
// 		  </c:if>
	     <c:if test="${(empty userInfo) or ((not empty userInfo))}">
		   goUrl(vm.ctx + url);	
		 </c:if>
	  },
      selectPinDao: function(item) {
    	  if(item == null){
	    	  console.info("info==="+item);
	    	  vm.hasSelectList.push(item);
	    	  if(item.hasOwnProperty('p_index')){
	        	  if(item.p_index == 0){
	        		  var index = vm.datas.baseComminfoTagsList.indexOf(item);
	            	  vm.datas.baseComminfoTagsList.splice(index ,1);
	        	  }else{
	        		  var index = vm.datas.baseComminfoTagsProList.indexOf(item);
	            	  vm.datas.baseComminfoTagsProList.splice(index ,1);
	        	  }   	  
	    	  }else{
	    		  var index = vm.datas.baseComminfoTagsProList.indexOf(item);
	        	  vm.datas.baseComminfoTagsProList.splice(index ,1);   		  
	    	  }
    	  }

    	  localStorage.setItem('hasSelectList', JSON.stringify(vm.hasSelectList));
    	  vm.showTagList =vm.hasSelectList;
	  },
      deletePinDao: function(item) {
    	 //这个地方需要判断是全国的还是区域的
    	 if(item.p_index == 0){
    	    vm.datas.baseComminfoTagsList.push(item);
    	 }else{
    		 vm.datas.baseComminfoTagsProList.push(item);
    	 }
    	 
	   	 var index = this.hasSelectList.indexOf(item);
	   	 vm.hasSelectList.splice(index ,1);
	   	 if(vm.hasSelectList.length == 0){
	   		 localStorage.removeItem('hasSelectList');
	   		 vm.showTagList = vm.datas.baseComminfoTagsList;
	   	 }else{
	   		 localStorage.setItem('hasSelectList', JSON.stringify(vm.hasSelectList));
	   		 vm.showTagList =vm.hasSelectList;
	   	 }
	  },
	  showPinDao: function() {
		  $("#pinDaoContent").show();
	  },
	  entpTabClick: function(tag_id) {
		  vm.tag_id_index= tag_id; 
		  console.log(tag_id+"========="+vm.tag_id_index)
	  },
	  closePinDao: function() {
		  $("#pinDaoContent").hide();
	  },
	  btnSearch:function(){
		 var url = vm.ctx + "m/MSearch.do";
		 goUrl(url);
	  },
	  addPindao: function(tag_id,item,flagPos){
		vm.tag_id_index =  tag_id;
       	var width = window.screen.availWidth * flagPos;
       	$("#mui-slider-group").css("transform","translate3d(-"+ width +"px, 0px, 0px) translateZ(0px)");
		for(var y=0;y<vm.showTagList.length;y++){
			if(vm.showTagList[y].id == tag_id){
				Vue.set(vm.showTagList[y], 'commInfoList', item);
				break;
			}
		}
	  }
    }
  });
  
</script>
</body>
</html>