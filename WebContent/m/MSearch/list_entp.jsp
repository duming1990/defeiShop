<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
</head>
<body>
<div id="wrap">
  <header class="index app_hide">
    <div class="c-hd s-hd">
      <section class="back mui-action-back"> <a><i></i></a> </section>
      <section class="hd-search">
        <c:url var="url" value="MSearch.do" />
        <html-el:form action="${url}" styleClass="box attrForm">
          <html-el:hidden property="method" value="listEntp" />
          <html-el:hidden property="htype"/>
          <html-el:hidden property="p_index" styleId="p_index"/>
          <html-el:hidden property="par_cls_id" styleId="par_cls_id"/>
          <html-el:hidden property="son_cls_id" styleId="son_cls_id"/>
          <html-el:hidden property="orderByParam" styleId="orderByParam"/>
          <html-el:hidden property="xianxiaEntp" styleId="xianxiaEntp" value="${af.map.xianxiaEntp}"/>
          <html-el:hidden property="x" styleId="x" value="${af.map.x}"/>
          <html-el:hidden property="y" styleId="y" value="${af.map.y}"/>
          <div class="box-flex c-form-search"> <em style="display:none;"></em>
            <input type="text" name="keyword" class="search-form c-ipunt-txt" placeholder="请输入商家名..." autocomplete="off" id="search_input" value="${af.map.keyword}" />
          </div>
          <input type="submit" class="searchbtn" name="dosearch" value="搜索" />
        </html-el:form>
      </section>
    </div>
  </header>
  <jsp:include page="../_slide_nav_entp.jsp" flush="true" />
  <div id="wrap">
    <c:if test="${not empty keyWordNavg}">
      <section class="com-search-tips"> <i></i>
        <p>${keyWordNavg}</p>
      </section>
    </c:if>
    <div class="conter">
      <div class="list-view" id="div_data" data-page="1" style="margin: 0px 0px 45px 0px;">
      
      </div>
      <div class="pop-shade hide" style="display: none; height: 100%;"></div>
    </div>
    <jsp:include page="../_footer.jsp" flush="true" />
  </div>
</div>
<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
<script type="text/javascript" src="${ctx}/m/js/search.m.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lazyload/min.js"></script>
<script type="text/javascript">//<![CDATA[
var ctx = "${ctx}";
var isLoad = false;
$(document).ready(function(){
	setTimeout('getLocation()',1000); //延迟1秒
	$("img.lazy").lazyload({
	    effect : "fadeIn"
	});
	refreshLocation();
});

function refreshLocation(latitude,longitude,p_name){
    setCookieByPname(p_name);
    getCookiePindex();
	isLoad = true;
 	$("#div_data").attr('data-page',0);
 	$('#div_data').innertHTML ="";
 	
	$('#all_div').dropload({
        scrollArea : window,
        autoLoad : true,     
        loadDownFn : function(me){
        	var page = $("#div_data").attr('data-page');
        	var ajax_url = ctx + "/m/MSearch.do?method=getEntpListJson&";
        	page = Number(page);
            $.ajax({
                type: 'POST',
                url: ajax_url,
                data: "x=" + x+"&y="+y+'&startPage=' + page +'&' + $(".attrForm").serialize(),
                dataType: 'json',
                success: function(data){
                	var html = "";
                	if(null != data.datas.dataList){
                		 var dataList = eval(data.datas.dataList);
         				$.each(dataList, function(i,data){
         					var url = ctx + "/m/MEntpInfo.do?method=index&entp_id=" + data.id;
        					html += '<section class="list-ul list-sort" style="margin-bottom:.02rem;"> <a onclick="goUrl(\''+url+'\')" class="z">';
        					html += '<div class="list-item">';
        					html += '<div class="box-flex pic"> <img class="lazy_'+ page +'" data-original="' + data.entp_logo + '" style="display: inline;"></div>';
        					html += '       <div class="info">';
        					html += '         <h2 class="title title-left" style="border-bottom: 0px;height: .4rem;">' + data.entp_name + '</h2>';
        					html += '        <div class="main" style="margin-top: 0.1rem;height: .4rem;"><p class="box-flex keyword">电话:'+data.entp_tel+'&nbsp;</p></div>';
        					html += '        <div class="main">';
        					html += '          <p class="box-flex keyword" style="height: .6rem;">' + data.entp_addr + '</p>';
        					html += '          <p class="num bought"><span class="site">销售额</span>' + data.sum_sale_money + '</p></div>';
        					html += '        </div>';
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
 
function getEntpInfoList(x,y){
	$("#div_data").attr('data-page',0);
	var page = $("#div_data").attr('data-page');
	var ajax_url = ctx + "/m/MSearch.do?method=getEntpListJson&";
	page = Number(page);
	if(!isLoad){
	    $.ajax({
	        type: 'POST',
	        url: ajax_url,
	        data: "x=" + x+"&y="+y+'&startPage=' + page +'&' + $(".attrForm").serialize(),
	        dataType: 'json',
	        success: function(data){
	        	var html = "";
	        	if(null != data.datas.dataList){
	        		 var dataList = eval(data.datas.dataList);
	 				$.each(dataList, function(i,data){
	 					var url = ctx + "/m/MEntpInfo.do?method=index&entp_id=" + data.id;
     					console.log(url)
    					html += '<section class="list-ul list-sort"> <a onclick="goUrl(\''+url+'\')" class="z">';
    					html += '<div class="list-item">';
    					html += '<div class="box-flex pic"> <img class="lazy_'+ page +'" data-original="' + data.entp_logo_400 + '" style="display: inline;"></div>';
    					html += '       <div class="info">';
    					html += '         <h2 class="title">' + data.entp_name + '</h2>';
    					html += '        <div class="main">';
    					html += '          <p class="box-flex keyword">' + data.entp_addr + '</p>';
    					html += '        </div>';
    					html += '        <div class="main">';
    					html += '          <p class="box-flex keyword">' + data.entp_tel + '</p>';
    					html += '          <p class="num bought"><span class="site">销售额</span>' + data.sum_sale_money + '</p>';
    					html += '        </div>';
    					html += '      </div>';
    					html += '    </div>';
    					html += '   </a> </section>';
	                 });
	        	}
	           
				setTimeout(function(){
						$('#div_data').innerHtml = html;
        								
	            },500); 
	            page += 1;
		        $("#div_data").attr('data-page',page);
	        },
	    });
	}
}

//  function getLocation(){
// 	//通过navigator.geolocation来获取设备的当前位置，返回一个位置对象
//     if(navigator.geolocation){
//        // timeout at 60000 milliseconds (60 seconds)
//        var options = {timeout:60000};
//        navigator.geolocation.getCurrentPosition(showLocation, errorHandler, options);
       
//     }else{
// //        alert("对不起，浏览器不支持地理定位!");
//     }
//  }
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
        	var ajax_url = ctx + "/m/MSearch.do?method=getEntpListJson&";
        	page = Number(page);
            $.ajax({
                type: 'POST',
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



//]]></script>
<script type="text/javascript" src="${ctx}/m/js/list_data.js?v=20161120"></script>
</body>
</html>
