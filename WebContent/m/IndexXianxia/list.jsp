<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name_min}触屏版</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.carousel.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.theme.css"/>
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
<style type="text/css">
.dropload-down{padding-bottom: 1rem;}
.contact-seller {position: fixed;bottom: 1.5rem;left: .24rem;font-size: 14px;color: #fff;z-index: 100;}
.contact-seller a.open-im {position: relative; display: inline-block;padding: 7px 12px;font-size: 12px;color: #30c665;background-color: #FFFFFF;
border-radius: 100px;border: 1px solid #30c665;}
.sort-nav .sort-circle img{width: .9rem;height: .9rem;}
#hideHeaderType section.hd-search:after{left: 1rem;}
.c-hd section.city #typeShowName{padding:.02rem .08rem;height: 100%;}
.index .s-input-tab-nav{left:0.8rem;}
.floor h2 .nor-title{float: left;width: 45%;height: 0.4rem;line-height: 0.4rem;margin-bottom: 5px;margin-left: 5px;font-size: 16px;color: #4a525d; border-bottom: 1px solid #eee;}


</style>
</head>
<body id="body" style="background: rgb(213, 213, 214);">
<div id="wrap">
<div id="all_div">
  <header class="index app_hide">
   
    <div class="c-hd s-hd">
      <section class="city">
        <c:url var="url" value="/m/MChangeCity.do" />
        <a onClick="goUrl('${url}')" id="city_name">${fn:replace(p_index_city_name, '市', '')}</a></section>
        
      <section class="hd-search">
        <c:url var="url" value="MSearch.do" />
        <html-el:form action="${url}" styleClass="box">
          <html-el:hidden property="params" value="${params}"/>
          <html-el:hidden property="par_cls_id"/>
          <html-el:hidden property="son_cls_id"/>
          <html-el:hidden property="htype" value="1" styleId="htype"/>
          <html-el:hidden property="orderByParam" styleId="orderByParam"/>
          <html-el:hidden property="xianxiaEntp" styleId="xianxiaEntp" value="true"/>
          <html-el:hidden property="x" styleId="x" value="${af.map.x}"/>
          <html-el:hidden property="y" styleId="y" value="${af.map.y}"/>
          <div class="box-flex c-form-search"> <em style="display:none;"></em>
            <input type="text" name="keyword" class="search-form c-ipunt-txt" placeholder="请输入店铺名称..." autocomplete="off" id="search_input" value="${af.map.search}" />
          </div>
          <input type="submit" class="searchbtn" onClick="putInHistoryKeywords()" name="dosearch" value="搜索">
        </html-el:form>
      </section>
      
      <div class="s-input-tab-nav off" id="showSearchType">
         <ul>
<!--           <li class="all"  data-flag="0">商品</li> -->
          <li class="shop" data-flag="1">店铺</li>
         </ul>
      </div>
    </div>
    
    <div class="c-hd s-hd" style="display:none;" id="hideHeaderType">
      <section class="back" onclick="backToRealHead();"> <a><i></i></a> </section>
      <section class="hd-search">
        <c:url var="url" value="MSearch.do" />
        <html-el:form action="${url}" styleClass="box">
          <html-el:hidden property="htype" styleId="htype" value="1"/>
          <html-el:hidden property="orderByParam" styleId="orderByParam"/>
          <html-el:hidden property="xianxiaEntp" styleId="xianxiaEntp" value="true"/>
          <div class="box-flex c-form-search">
            <section class="city changeSearchType" id="changeSearchType">
              <a class="arrowleft atop" id="typeShowName">店铺</a>
      	    </section>
            <input type="text" name="keyword" class="search-form c-ipunt-txt" placeholder="请输入店铺名称..." autocomplete="off" id="search_input" value="${af.map.search}" style="padding: .1rem .1rem .1rem 1.3rem;"/>
          </div>
          <input type="submit" class="searchbtn" onClick="putInHistoryKeywords()" name="dosearch" value="搜索">
        </html-el:form>
      </section>
      
      <div class="s-input-tab-nav off" id="showSearchType">
         <ul>
<!--           <li class="all"  data-flag="0">商品</li> -->
          <li class="shop" data-flag="1">店铺</li>
         </ul>
      </div>
    </div>
    
    
  </header>
  <section class="sec-warp">
<!--   轮播图 -->
    <c:if test="${not empty mBaseLink20List}">
      <div class="banner-view">
        <div class="sliderwrap">
          <ul id="banner_list">
            <c:forEach var="cur" items="${mBaseLink20List}" varStatus="vs">
              <li>
              <c:url var="url" value="${cur.link_url}" />
              <a onclick="goUrl('${url}')" title="${cur.title}">
                <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
                <img width="100%" class="lazyOwl" src="${imgurl}" data-src="${imgurl}"/></a></li>
            </c:forEach>
          </ul>
        </div>
      </div>
    </c:if>
<!--    导航分类 -->
    <div class="sort-nav" style="padding-bottom: 0.1rem;margin-bottom: 0.05rem;">
      <ul>
        <c:forEach var="cur" items="${mBaseLink30List}" varStatus="vs">
          <li> 
          <c:url var="url" value="${cur.link_url}" />
          <a onclick="goUrl('${url}')" title="${cur.title}"> 
          <c:set var="imgurl" value="${ctx}/${cur.image_path}"/>
          <span class="sort-circle"><img class="lazy" src="${imgurl}" data-original="${imgurl}"/></span>
          <span class="sort-desc">${fn:escapeXml(fnx:abbreviate(cur.title, 2 * 4, ""))}</span> </a> </li>
        </c:forEach>
      </ul>
    </div>
    
    <section class="floor">
    <h2 class="h2-height">
    	<a class="nor-title a-height" style="border-bottom: 0px;height: 100%;line-height: 0.8rem;">附近商家</a>
    	<input type="hidden" id="latitude" name="latitude">
    	<input type="hidden" id="longitude" name="longitude">
    	<a style="height: 100%;line-height: 0.8rem;" class="J_ping jd-news-more new_more a-height" href="#" onclick="gotoEntpInfoList();">
    		<i class="line"></i>更多</a>
      <c:url var="url" value="/m/MEntpEnter.do" />
      <c:if test="${userInfo.is_entp eq 1}">
		<c:set var="account_tip" value="恭喜您已成为商家"/>
        <c:url var="url" value="" />
      </c:if>
   		<a style="height: 100%;line-height: 0.8rem;" id="customer_enter" class="J_ping jd-news-more new_more a-height entp-button" data-url="${url}" data-title="${account_tip}" >
   			<i class="line"></i>商家入驻</a>
    </h2>
    <div id="wrap">
    <div class="conter">
      <div class="list-view" id="div_data" data-page="0">
        <c:forEach items="${entpInfoList}" var="cur" varStatus="vs">
          <c:url var="url" value="/m/MEntpInfo.do?method=index&entp_id=${cur.id}" />
          <c:set var="imgurl" value="${ctx}/styles/imagesPublic/no_image.jpg"/>
          <c:if test="${not empty cur.entp_logo}">
            <c:set var="imgurl" value="${ctx}/${cur.entp_logo}@s400x400"/>
          </c:if>
          
          <section class="list-ul list-sort"> <a onClick="goUrl('${url}')" class="z">
            <div class="list-item">
              <div class="box-flex pic"> <img class="lazy" data-original="${imgurl}" style="display: inline;"></div>
              <div class="info">
                <h2 class="title title-left" style="border-bottom: 0px;">${fn:escapeXml(cur.entp_name)}</h2>
                <div class="main">
                  <p class="box-flex keyword">${fn:escapeXml(cur.entp_addr)}&nbsp;</p>
                  <c:set var="sumMoney" value="${cur.sum_sale_money}"/>
		           <c:if test="${sumMoney gt 10000}">
		             <fmt:formatNumber var="sumMoney" value="${sumMoney/10000}" pattern="#.##万"/>
		           </c:if>
		           <p class="num bought title-right">123.21km</p>
                </div>
                <div class="main" style="margin-top: 0.1rem;">
                	<p class="box-flex keyword">电话:${fn:escapeXml(cur.entp_tel)}&nbsp;</p>
                	<p class="num bought"></p>
                </div>
              </div>
            </div>
            </a> </section>
        </c:forEach>
      </div>
    </div>
    <jsp:include page="../_footer.jsp" flush="true" />
  </div>
     
    </section>
    
  </section>
  
  <jsp:include page="../_footer.jsp" flush="true"/>
  <div style="clear: both;"></div>
  </div>
</div>
<c:if test="${not empty cc_kefu_id}">
<div class="js-im-icon contact-seller"><a href="https://kefu.qycn.com/vclient/chat/?m=m&websiteid=${cc_kefu_id}" class="js-open-im open-im">联系客服</a></div>
</c:if>


<script type="text/javascript" src="${ctx}/m/scripts/owl-carousel/owl.carousel.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.js"></script>
<script type="text/javascript" src="${ctx}/scripts/marquee/marquee.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
<script type="text/javascript" src="${ctx}/scripts/ScrollText.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lazyload/min.js"></script>
<script type="text/javascript" src="${ctx}/m/js/search.m.js?v=20160912"></script>
<script type="text/javascript">//<![CDATA[
var ctx = "${ctx}";
var isLoad = false;
var isExecute =false;
$(document).ready(function(){
	
	setTimeout('getLocation()',1000); //延迟1秒
	var is_app = $.cookie("is_app");
 	if(is_app){
		window.mobaAppBrigdeClient.actionFromJsGetLocation();
 	}
 	
	var scroll2 = new ScrollText("toutiaogundong","pre2","next2",true,150,true);
	scroll2.LineHeight = 33;
	
	$("#banner_list").owlCarousel({
		  autoPlay : true, 
		  slideSpeed : 200,
		  lazyLoad: true
	});
// 	canRefresh();
	
	$("img.lazy").lazyload({
	    effect : "fadeIn"
	});
	
	
	$("#changeSearchType").click(function(){
    	$showSearchType = $("#showSearchType");
    	if($showSearchType.hasClass("on")){
    		$showSearchType.removeClass("on").addClass("off");
    	}else{
    		$showSearchType.removeClass("off").addClass("on");
    	}
    });
	
	$("#showSearchType ul li").each(function(){
		$(this).click(function(){
		    var data_flag = $(this).attr("data-flag");
			$("#htype").val(data_flag);
			if(data_flag == 0){
				$("#search_input").attr("placeholder","请输入商品名...");
				$("#typeShowName").text("商品");
			}else{
				$("#search_input").attr("placeholder","请输入店铺名...");
				$("#typeShowName").text("店铺");
			}
			$("#showSearchType").removeClass("on").addClass("off");
	  });
    });
	
	
});

$("#customer_enter").click(function(){
	
	var title=$(this).attr("data-title");
	if(title){
		mui.toast(title);
		return false;
	}
	var dataurl=$(this).attr("data-url");
	if(dataurl){
		location.href=dataurl; 
		return false;
	}
});

function searchSelect(){
	$("#showHeaderType").hide();
	$("#hideHeaderType").show();
}
function backToRealHead(){
	$("#showHeaderType").show();
	$("#hideHeaderType").hide();
}
function gotoEntpInfoList(){
	var x = $("#x").val();
	var y = $("#y").val();
	location.href=app_path + "/m/MSearch.do?htype=1&xianxiaEntp=true&x="+x+"&y="+y;
}

function refreshLocation(latitude,longitude,p_name){
	isExecute =true;//执行了手机端的获取信息，不执行客户端的获取列表功能
    var x = latitude;//纬度
    var y = longitude;//经度
    $("#x").val(x);
    $("#y").val(y);
    setCookieByPname(p_name);
    getCookiePindex();
 	getEntpInfoList(x,y);
	isLoad = true;
 	$("#div_data").attr('data-page',0);
 	$('#div_data').innertHTML ="";
 	
	$('#all_div').dropload({
        scrollArea : window,
        autoLoad : true,     
        loadDownFn : function(me){
//         	getEntpInfoList(latitude,longitude);
        	var page = $("#div_data").attr('data-page');
        	var ajax_url = "${ctx}/m/IndexXianxia.do?method=getNearbyEntpList";
        	page = Number(page);
            $.ajax({
                type: 'GET',
                url: ajax_url,
                data: "x=" + x+"&y="+y+'&startPage=' + page +'&' + $(".attrForm").serialize(),
                dataType: 'json',
                success: function(data){
                	var html = "";
                	if(null != data.datas.dataList){
                		 var dataList = eval(data.datas.dataList);
         				$.each(dataList, function(i,data){
         					var url = ctx + "/m/MEntpInfo.do?method=index&entp_id=" + data.id;
         					var url_entp_latlng = ctx + "/m/MEntpInfo.do?method=viewEntpAddr&entp_id="+data.id+"&latlng=" + data.entp_latlng;
        					html += '<section class="list-ul list-sort"> <a onclick="goUrl(\''+url+'\')" class="z">';
        					html += '<div class="list-item">';
        					html += '<div class="box-flex pic"> <img class="lazy_'+ page +'" data-original="' + data.entp_logo_400 + '" style="display: inline;"></div>';
        					html += '       <div class="info">';
        					html += '         <h2 class="title title-left" style="border-bottom: 0px;">' + data.entp_name + '</h2>';
        					html += '        <div class="main">';
        					html += '          <p class="box-flex keyword">' + data.entp_addr + '</p>';
        					html += '          <p class="num bought title-right" onClick="location.href='+url_entp_latlng+'">'+data.distance+'km</p>';
        					html += '        </div>';
        					html += '        <div class="main" style="margin-top: 0.1rem;"><p class="box-flex keyword">电话:'+data.entp_tel+'&nbsp;</p>';
        					html += '          <p class="num bought"></p></div>';
        					html += '      </div>';
        					html += '    </div>';
        					html += '   </a> </section>';
                         });
                	}
                   
        			setTimeout(function(){
        				$('#div_data').append(html);
        				lazyload(page - 1);
        				
                        me.resetload();
                    },500); 
                    page += 1;
        	        $("#div_data").attr('data-page',page);
                   	if (data.code == 2) {
                         me.lock();// 锁定
                         me.noData(); // 无数据
        				} 
                },
            });
        },
        domUp : {// 上方DOM                                                       
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
        },
        loadUpFn : function(me){
            // 为了测试，延迟1秒加载
            setTimeout(function(){
                me.resetload();
                me.unlock();
                me.noData(false);
                Common.loading();
 	       		window.setTimeout(function () {
 	       			window.location.reload();
 	       		}, 1000);
            },500);
         },
        threshold : 50
    });
  
    
 }
 
 function showLocation(position) {
    var x = position.coords.latitude;//纬度
    var y = position.coords.longitude;//经度
    $("#x").val(x);
    $("#y").val(y);


 	getEntpInfoList(x,y);
 	
	$('#all_div').dropload({
        scrollArea : window,
        autoLoad : true,     
        loadDownFn : function(me){
//         	getEntpInfoList(latitude,longitude);
        	var page = $("#div_data").attr('data-page');
        	var ajax_url = "${ctx}/m/IndexXianxia.do?method=getNearbyEntpList";
        	page = Number(page);
            $.ajax({
                type: 'GET',
                url: ajax_url,
                data: "x=" + x+"&y="+y+'&startPage=' + page +'&' + $(".attrForm").serialize(),
                dataType: 'json',
                success: function(data){
                	var html = "";
                	if(null != data.datas.dataList){
                		 var dataList = eval(data.datas.dataList);
         				$.each(dataList, function(i,data){
         					var url = ctx + "/m/MEntpInfo.do?method=index&entp_id=" + data.id;
         					var url_entp_latlng = ctx + "/m/MEntpInfo.do?method=viewEntpAddr&entp_id="+data.id+"&latlng=" + data.entp_latlng;
        					html += '<section class="list-ul list-sort"> <a onclick="goUrl(\''+url+'\')" class="z">';
        					html += '<div class="list-item">';
        					html += '<div class="box-flex pic"> <img class="lazy_'+ page +'" data-original="' + data.entp_logo_400 + '" style="display: inline;"></div>';
        					html += '       <div class="info">';
        					html += '         <h2 class="title title-left" style="border-bottom: 0px;">' + data.entp_name + '</h2>';
        					html += '        <div class="main">';
        					html += '          <p class="box-flex keyword">' + data.entp_addr + '</p>';
        					html += '          <p class="num bought title-right" onClick="location.href='+url_entp_latlng+'">'+data.distance+'km</p>';
        					html += '        </div>';
        					html += '        <div class="main" style="margin-top: 0.1rem;"><p class="box-flex keyword">电话:'+data.entp_tel+'&nbsp;</p>';
        					html += '          <p class="num bought"></p></div>';
        					html += '      </div>';
        					html += '    </div>';
        					html += '   </a> </section>';
                         });
                	}
                   
        			setTimeout(function(){
        				$('#div_data').append(html);
        				
        				lazyload(page - 1);
        				
                        me.resetload();
                    },500); 
                    page += 1;
        	        $("#div_data").attr('data-page',page);
                   	if (data.code == 2) {
                         me.lock();// 锁定
                         me.noData(); // 无数据
        				} 
                },
            });
        },
        domUp : {// 上方DOM                                                       
            domClass   : 'dropload-up',
            domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
            domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
            domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
        },
        loadUpFn : function(me){
            // 为了测试，延迟1秒加载
            setTimeout(function(){
                me.resetload();
                me.unlock();
                me.noData(false);
                Common.loading();
 	       		window.setTimeout(function () {
 	       			window.location.reload();
 	       		}, 1000);
            },500);
         },
        threshold : 50
    });
  
    
 }

 function errorHandler(err) {
    if(err.code == 1) {
//        alert("拒绝访问!");
    }else if( err.code == 2) {
//        alert("位置是无效的!");
      
    }
 }

  function getLocation(){
	//通过navigator.geolocation来获取设备的当前位置，返回一个位置对象
    if(navigator.geolocation){
       // timeout at 60000 milliseconds (60 seconds)
       var options = {timeout:60000};
       if(!isExecute){
       		navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
       }
       
    }else{
//        alert("对不起，浏览器不支持地理定位!");
    }
 }

function getEntpInfoList(x,y){
	var page = $("#div_data").attr('data-page');
	var ajax_url = "${ctx}/m/IndexXianxia.do?method=getNearbyEntpList";
	page = Number(page);
	if(!isLoad){
	    $.ajax({
	        type: 'GET',
	        url: ajax_url,
	        data: "x=" + x+"&y="+y+'&startPage=' + page +'&' + $(".attrForm").serialize(),
	        dataType: 'json',
	        success: function(data){
	        	var html = "";
	        	if(null != data.datas.dataList){
	        		 var dataList = eval(data.datas.dataList);
	 				$.each(dataList, function(i,data){
	 					var url = ctx + "/m/MEntpInfo.do?method=index&entp_id=" + data.id;
	 					var url_entp_latlng = ctx + "/m/MEntpInfo.do?method=viewEntpAddr&entp_id="+data.id+"&latlng=" + data.entp_latlng;
	 					html += '<section class="list-ul list-sort"> <a onclick="goUrl(\''+url+'\')" class="z">';
						html += '<div class="list-item">';
						html += '<div class="box-flex pic"> <img class="lazy_'+ page +'" data-original="${file_domain}${ctx}' + data.entp_logo_400 + '" style="display: inline;"></div>';
						html += '       <div class="info">';
						html += '         <h2 class="title title-left" style="border-bottom: 0px;">' + data.entp_name + '</h2>';
						html += '        <div class="main">';
						html += '          <p class="box-flex keyword">' + data.entp_addr + '</p>';
						html += '          <p class="num bought title-right" onClick="location.href='+url_entp_latlng+'">'+data.distance+'km</p>';
						html += '        </div>';
						html += '        <div class="main" style="margin-top: 0.1rem;"><p class="box-flex keyword">电话:'+data.entp_tel+'&nbsp;</p>';
						html += '          <p class="num bought"><span class="site">销售额</span>' + data.sum_sale_money + '</p></div>';
						html += '      </div>';
						html += '    </div>';
						html += '   </a> </section>';
	                 });
	        	}
	           
				setTimeout(function(){
					$('#div_data').append(html);				
					lazyload(page - 1);
					
	                me.resetload();
	            },500); 
	            page += 1;
		        $("#div_data").attr('data-page',page);
	           	if (data.code == 2) {
	                 me.lock();// 锁定
	                 me.noData(); // 无数据
					} 
	        },
	    });
	}
}


//]]></script>
</body>
</html>
