<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>仁义头条-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/m/nmny/css/font-awesome.min.css" /> --%>
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/css/news/main2.css" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />

<style type="text/css">
 .dropload-up{padding-top: 1rem;z-index: 99999} 
</style>
</head>

<body>
<section class="w tt-channel"> 
  <!--头部Tab[[-->
  <div class="tab-box-area">
    <section class="channel-tab pr bbox hide fixed-class" id="tabBox" style="display: block;">
      <div class="tab-box">
        <ul class="tab-nav">
          <a href="${ctx}/m/MNewsInfo.do?method=testNews&mod_id=1018000001"><li class="cur" name="cls_id" value="" id="nav_" style="font-size: 0.8rem;">全部</li></a>
          <c:forEach items="${basePdClassList}" var="cur">
          <li id="nav_${cur.cls_id}"  class="" onclick="curClass(${cur.cls_id})" style="font-size: 0.8rem;">${cur.cls_name}</li>
          </c:forEach>
        </ul>
      </div>
    </section>
  </div>
  <!--]] 头部Tab-->
  <section class="floor lazyimg">
    <section class="cate-count toutiao-adv" data-cpage="1" data-pcount="20" style="" id="append_more">
    </section>
    
		<div id="datePlugin"></div>
		<div class="goodslist">
			<div class="items">
			</div>
		</div>
  </section>
</section>
</body>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script>
<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
<script type="text/javascript">
var ctx = "${ctx}";

var page =-1;
$(function(){
//         dropload
        var ajax_url = "${ctx}/m/MNewsInfo.do?method=getListJson&cls_id=${af.map.cls_id}&mod_id=${af.map.mod_id}";  //上拉下拉跳转链接
        $('body').dropload({
            scrollArea : window,
            domUp : {
                domClass   : 'dropload-up',
                domRefresh : '<div class="dropload-refresh">↓下拉刷新</div>',
                domUpdate  : '<div class="dropload-update">↑释放更新</div>',
                domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>'
            },
            domDown : {
                domClass   : 'dropload-down',
                domRefresh : '<div class="dropload-refresh">↑上拉加载更多</div>',
                domLoad    : '<div class="dropload-load"><span class="loading"></span>加载中...</div>',
                domNoData  : '<div class="dropload-noData">已显示全部内容</div>'
            },
            loadUpFn : function(me){
            	page =0;
                $.ajax({
                    type: 'GET',
                    url: ajax_url,
                    data: "startPage=0",
                    dataType: 'json',
                    success: function(data){
                        var html = '';
                        var dataList = eval(data.dataList);
                        
        				$.each(dataList, function(i,data){
        					
        					var url = "${ctx}/m/MNewsInfo.do?method=view&news_id="+ data.id+"&uuid="+data.uuid;      
        					
        					html += '<div class="floor02 bbox ppd" content-id="4090870471"><a class="wbox" name="toutiao_none_content_nr4090870471" href="' + url + '">';
        					html += '<div class="pic-img"><img data-src="done" class="opa1" src="' + data.main_pic + '"/></div>';
        					html += '<div class="wbox-flex mt20"><div class="h170"><p class="line-clamp-2 f32 bold line-35">' + data.title  + '</p></div><div class="small-icon">';
        					html += '<p class="c1 f26 op">' + data.summary + '</div><div class="newsviewcount c1"><i class="bg-before browse"></i>浏览数：'+data.view_count;
        					html += '</p></div></div> </a></div>';
        					
                        });
                        // 为了测试，延迟1秒加载
                        setTimeout(function(){
                        	$("#append_more").html(html);  //html和append的区别：append是在已有的页面后面拼接  ，html是直接替换
                          //  lazyload();  //懒加载  拉到哪里，图片加载到哪里
                            // 每次数据加载完，必须重置
                            me.resetload();
                            // 解锁
                            me.unlock();
                            me.noData(false); 
                        },1000);
                    },
                });
            },
            loadDownFn : function(me){
                $.ajax({
                    type: 'GET',
                    url: ajax_url,
                    data: 'startPage=' + (page+1),
                    dataType: 'json',
                    success: function(data){
                    	var html = '';
                        var dataList = eval(data.dataList); 
        				$.each(dataList, function(i,data){
        					
                           var url = "${ctx}/m/MNewsInfo.do?method=view&news_id="+ data.id+"&uuid="+data.uuid;  
        					
       					html += '<div class="floor02 bbox ppd" content-id="4090870471"><a class="wbox" name="toutiao_none_content_nr4090870471" href="' + url + '">';
    					html += '<div class="pic-img"><img data-src="done" class="opa1" src="' + data.main_pic + '"/></div>';
    					html += '<div class="wbox-flex mt20"><div class="h170"><p class="line-clamp-2 f32 bold line-35">' + data.title  + '</p></div><div class="small-icon">';
    					html += '<p class="c1 f26 op">' + data.summary + '</div><div class="newsviewcount c1"><i class="bg-before browse"></i>浏览数：'+data.view_count;
    					html += '</p></div></div> </a></div>';
        					
                        });
                     
                        // 为了测试，延迟1秒加载
                        setTimeout(function(){
                        	if(html!=''){
                                $('#append_more').append(html);
                            	page++;
                        	}
                           // lazyload();
                            // 每次数据加载完，必须重置
                            me.resetload();
                        },1000); 
                    	if (data.appendMore != 1) {
      					  // 锁定
                          me.lock();
                          // 无数据
                          me.noData();
    					} 
                    },
                });
            },
            threshold : 50
        });
    });
   
    function curClass(cls_id){
    	location.href="${ctx}/m/MNewsInfo.do?method=testNews&cls_id="+cls_id;
    	
    }
    var cls_id_="${af.map.cls_id}";
    $(".tab-nav li").removeClass("cur");
    $("#nav_"+cls_id_).addClass("cur");
</script>

</body>
</html>
