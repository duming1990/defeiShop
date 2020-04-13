<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="text/html" charset="utf-8" http-equiv="content-type">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no" charset="utf-8">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/css/my/comm-details.css?v=20180320" />
<link rel="stylesheet" href="${ctx}/m/scripts/dropload/css/dropload.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/index.css" />
<style type="text/css">
.section-detailbox .title{border-bottom:none;padding:0;height:100%!important;}
.entp_content{padding:0.2rem;}
.entp_content img{max-width: 100% !important;height: auto!important;}
.section-detailbox .deal-pic img{
	height: 200px !important;
}
.m-topSearchIpt{
    width: 88%;
    height: 25px;
    font-size: 10px;
}
.city{
    padding: 0px;
    height:25px;
    margin-top: -6px;
}
.m-topSearchIpt{
    width: 87%;
    height: 25px;
    left: 28px;
    position: absolute;
}
.city2{ 
 	position: absolute;
    right: 1px; 
    top: 6px;  
    height: 25px; 
 } 
.aui-product-boutique{
	background: white;
}
#app{ 
 	background: white; 
}
.aui-fixed-top{
	padding-top: 0px !important;
}
.line{
    padding: 4px 0px 2px 0px;
    background: white;
    height: 30px;
}
.icon{
    width: 13.61px !important;
    height: 13.61px !important;
}
.fun{
    height: 20px;
    float: right;
    position: absolute;
    top: 62px;
    right: 87px;
    width: 25px; 
}
.entp_content{
	 font-size: .32rem!important;
	 color: #3C3C3C!important;
}
</style>
</head>
<body>
    <c:if test="${isWeixin}">
     <div class="line c-hd">
		<section class="city" id="scanQRCode"><span class="aui-icon aui-icon-scan"></span></section>
		 <c:url var="url" value="/m/MSearch.do" />
		<div class="m-topSearchIpt ipt" onclick="goUrl('${url}')"><i class="icon"></i><span class="placeholder">搜索商品</span></div>
		 <c:url var="url" value="/m/MMyCard.do" />
		<section class="city2" onclick="goUrl('${url}')"><span class="aui-icon aui-icon-mycard"></span></section>
	 </div>
    </c:if>
<jsp:include page="../_header.jsp" flush="true" />
    <div class="section-detailbox">
      <section class="box deal-site">
       <img src="${ctx}/${entpInfo.entp_logo}@s400x400"  style="display: inline;height: 79px;border-radius: 76px;"/>
      <c:url var="url" value="/m/MEntpInfo.do?method=viewEntpAddr&entp_id=${entpInfo.id}&latlng=${entpInfo.entp_latlng}" />
     	 <a class="box-flex site-wz" href="${url}" style="padding-right: 10px;padding-left: 10px;">
        	<p class="s"><span class="site">${entpInfo.map.full_name} ${entpInfo.entp_addr}</span></p>
        </a>
        <c:if test="${not empty userInfo}">
	    <input type="hidden" name="hasFavCount" id="hasFavCount" value="${hasFavCount}"/>
	         <a class="fun" title="收藏" id="add_fav" >
	       	   <span id="span1">
	       	        <img src="${ctx}/m/styles/img/love.png"/> 
	       	   </span> 
	       	   <span  id="span2">
	       	        <img src="${ctx}/m/styles/img/love_2.png"/> 
	       	   </span> 
	      	 </a>
        </c:if> 
         <c:if test="${empty userInfo}">
            <c:url var="url" value="/m/MIndexLogin.do" />
            <a class="fun" title="收藏" onClick="toLogin('${url}');">
            	<span><img src="${ctx}/m/styles/img/love.png"/></span>
          	</a> 
	    </c:if>
         <a id="shopPhone" href="tel:${entpInfo.entp_tel}" class="box-flex site-phone"><i></i></a> </section>
    </div>
    <c:if test="${not empty entityList}">
    <div class="section-detailbox">
    <section class="title" id="nearByTitle"><h2>商家详情</h2></section>
        <div class="list-view entp_content">
		<c:forEach var="cur" items="${entityList}">
         <img alt="" src="${ctx}/${cur.save_path}" style="margin-bottom: 2px;">
        </c:forEach>
        </div>
   </div>
   </c:if>
    <div class="section-detailbox" id="jiazai" style="margin-bottom: 55px;">
    
    <div class="myls-listbox" style="padding-left: 0.52rem;">
		<c:url var="url" value="/m/MEntpInfo.do?method=entpIntroBaseLink&entp_id=${entpInfo.id}" />
		<h2 onclick="goUrl('${url}')" class="name sel entp_content">店铺简介</h2>
	</div>
    <section class="title" id="nearByTitle"><h2>商品信息</h2></section>
        <div class="list-view">
        <ul class="list-ul nearByTuanGou" data-page="1"  id="ul_data">
        <c:forEach var="cur" items="${commmInfoList}" varStatus="vs">
           <li> 
            <c:url var="url" value="/m/MEntpInfo.do?id=${cur.id}"/>
           <a href="${url}">
             <div class="list-item">
               <div class="pic"><img src="${ctx}/${cur.main_pic}@s200x200" style="display: inline;"> 
             </div>
             <div class="info">
                 <h2 class="title">${fn:escapeXml(cur.comm_name)}</h2>
                 <h3 class="title">&nbsp;&nbsp;${fn:escapeXml(cur.sub_title)}</h3>
                 <div class="main">
                 <p class="price"><em>¥<fmt:formatNumber value="${cur.sale_price}" pattern="0.00" /></em></p>
                 <p class="num bought">已售${cur.sale_count_update}</p>
               </div>
               </div>
           </div>
          </a> 
          </li>
         </c:forEach>
        </ul>
	</div>
   </div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/m/scripts/dropload/js/dropload.min.js?v20160725"></script>
<script type="text/javascript" src="${ctx}/scripts/lazyload/min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script type="text/javascript">//<![CDATA[
                                   
$("#add_fav").click(function(){
	    var this_$ = $(this);
		$.ajax({
			type: "POST",
			url: "${ctx}/entp/CsAjax.do",
			data: "method=addFav&sc_type=1&link_user_id=${userInfo.id}&link_id=${entpInfo.id}",
			dataType: "json",
			error: function(request, settings) {},
			success: function(oper) {
				if(null != oper.result && oper.result != ''){
					if(oper.result == 1){
				    	document.getElementById("span1").style.display="none";
				    	document.getElementById("span2").style.display="inline";
						mui.toast("恭喜你，收藏店铺成功！");
					}else{
				    	document.getElementById("span2").style.display="none";
				    	document.getElementById("span1").style.display="inline";
						mui.toast("取消收藏成功！");
					}
				}else{
					mui.toast("参数有误，请联系管理员！");
				}
				
			}
		});
});

$(document).ready(function(){
	
	canRefresh();
	
	<c:if test="${isWeixin}">
		var inData = {
				appId : '${appid}',
				timestamp : '${timestamp}',
				nonceStr : '${nonceStr}',
				signature : '${signature}'
		};
		var shareData = {
			title : '${app_name}',
			desc : '${commInfo.comm_name}',
			link : '${share_url}',
			imgUrl : '${share_img}',
		};			
		Common.weixinConfig(inData, shareData);
	</c:if>
	
	
	$("img.lazy").lazyload({
	    effect : "fadeIn"
	});
	
    if($("#hasFavCount").val() > 0){
    	document.getElementById("span1").style.display="none";
    	document.getElementById("span2").style.display="inline";
      }else{
      	document.getElementById("span2").style.display="none";
    	document.getElementById("span1").style.display="inline";
      }
    
});
     
function canRefresh(){
	// dropload
	 var entp_id=${af.map.entp_id};
	 var ajax_url = "${ctx}/m/MEntpInfo.do?method=getCommInfoList&entp_id="+entp_id;
    $('#jiazai').dropload({
        scrollArea : window,
        autoLoad : true,     
        loadDownFn : function(me){
        	var page = $("#ul_data").attr('data-page');
        	page = Number(page);
            $.ajax({
                type: 'GET',
                url: ajax_url,
                data: "startPage=" + page,
                dataType: 'json',
                success: function(data){
                	var html = "";
                    var dataList = eval(data.datas.dataList);
    				$.each(dataList, function(i,data){
    					var url = app_path + "/m/MEntpInfo.do?id=" + data.id;
    					html += '<li><a onclick="goUrl(\''+url+'\')"><div class="list-item">';
    					html += '<div class="pic"><img  style="display: inline;" src="${ctx}/' + data.main_pic + '@s200x200"/></div>';
    					html += ' <div class="info"><h2 class="title">'+data.comm_name+'</h2>';
    					html += ' <h3 class="title">&nbsp;&nbsp;';
    					if(null != data.sub_title && "" != data.sub_title){
    					  html += data.sub_title;
    					}
    					html += '</h3>';
    					html+=' <div class="main">'
    					html += '<p class="price"><em>¥' + data.sale_price + '</em></p>';
    					html += '<p class="num bought">已售' + data.sale_count_update + '</p></div></div></div>';
    					html += '</a></li>';
                    });
    				setTimeout(function(){
    					$('#ul_data').append(html);
    					lazyload(page - 1);
                        me.resetload();
                    },500); 
                    page += 1;
			        $("#ul_data").attr('data-page',page);
                   	if (data.code == 2) {
                         me.lock();// 锁定
                         me.noData(); // 无数据
   					} 
                },
            });
        },
//         domUp : {// 上方DOM                                                       
//             domClass   : 'dropload-up',
//             domRefresh : '<div class="dropload-refresh"><i class="fa fa-long-arrow-down"></i>&nbsp;下拉刷新</div>',
//             domUpdate  : '<div class="dropload-update"><i class="fa fa-long-arrow-up"></i>&nbsp;释放更新</div>',
//             domLoad    : '<div class="dropload-load"><span class="loading"></span>刷新中...</div>'
//         },
//         loadUpFn : function(me){
//             // 为了测试，延迟1秒加载
//             setTimeout(function(){
//                 me.resetload();
//                 me.unlock();
//                 me.noData(false);
//                 Common.loading();
//  	       		window.setTimeout(function () {
//  	       			window.location.reload();
//  	       		}, 1000);
//             },500);
//          },
//         threshold : 50
    });
}
</script>
</body>
</html>
