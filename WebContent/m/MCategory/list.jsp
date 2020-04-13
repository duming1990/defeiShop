<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name_min}触屏版</title>
<meta content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" name="viewport"/>
<meta content="yes" name="apple-mobile-web-app-capable"/>
<meta content="black" name="apple-mobile-web-app-status-bar-style"/>
<meta content="telephone=no" name="format-detection"/>
<jsp:include page="../_public_in_head_new.jsp" flush="true" />
<style type="text/css">
.aui-header-center-clear{margin:0 5%;}
</style>
</head>
<body>
<div id="app" v-cloak>
<header class="aui-header-default aui-header-fixed">
		<div class="aui-header-center aui-header-center-clear"  @click="btnSearch()">
			<div class="aui-header-search-box">
				<i class="aui-icon aui-icon-small-search"></i>
				<input type="text" class="aui-header-search" placeholder="搜索商品" />
			</div>
		</div>
	</header>
   <section class="aui-scroll-contents mui-content mui-row mui-fullscreen">
		<div class="aui-scroll-box">
		   <div class="aui-scroll-nav mui-col-xs-3" id="leftContent">
				<a class="aui-scroll-item" :class="{'aui-crt crt': index === isActive}" v-for="item,index in datas.basePdClass1List">
					<div class="aui-scroll-item-icon"></div>
					<div class="aui-scroll-item-text">{{item.cls_name}}</div>
				</a>
			</div>
			<div class="aui-scroll-content mui-col-xs-9">
				<div class="aui-scroll-content-item mui-control-content" :id="'content' + index" :class="{'mui-active': index === isActive}" v-for="item,index in datas.basePdClass1List">
					 <div class="aui-class-img">
					  <a :href="ctx + 'm/MSearch.do?method=listPd&root_cls_id=' + item.cls_id">
						<img :src="ctx + item.image_path" />
					  </a>
					 </div>
					<h2 class="aui-scroll-content-title">{{item.cls_name}}</h2>
					<section class="aui-grid-content">
						<div class="aui-grid-row aui-grid-row-clear">
					          <a :href="ctx + 'm/MSearch.do?method=listPd&root_cls_id=' + item.cls_id +'&par_cls_id=' + item2.cls_id" class="aui-grid-row-item" v-for="item2 in item.map.basePdClass2List">
						          <i class="aui-icon-large aui-icon-sign"><img :src="ctx + item2.image_path" /></i>
				          		  <p class="aui-grid-row-label">{{item2.cls_name}}</p>
					          </a>
							</div>
					</section>
				</div>
		</div>
		</div>
   </section>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var vm = new Vue({
    el: '#app',
    data: {
      isActive: 0,	
      ctx:Common.api,
      datas: new Array()
    },
    mounted: function() {
     Common.loading();	
   	 this.$nextTick(function() {
      	  this.getAjaxData();
     });
    },
    updated: function() {
    	Common.hide();	
    	$("#leftContent a").each(function(index){
  			 $(this).click(function(){
  				vm.isActive = index;
  			 });
  		  });
    },
    methods: {
   	  getAjaxData: function() {
   		Common.getData({
   			route: 'm/MCategory.do?method=getAjaxData',
   			success: function(data) {
   				if(data.code == 0) {
   				  mui.alert(data.msg);
   	              return false;
   	            } else if(data.code == 1) {
   	            	vm.datas = data.datas;
   	            }
   			},
   			error: function() {
   				mui.alert('好像出错了哦~');
   			}
   		});
       },
      btnSearch:function(){
  		 var url = vm.ctx + "/m/MSearch.do";
  		 goUrl(url);
  	  },
    }
  });                                          
//]]></script>
</body>
</html>