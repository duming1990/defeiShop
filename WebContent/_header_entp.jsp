<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cookie.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jquery.hoverDelay.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/public.js?20180614"></script>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocompleteForSeach.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/swiper/swiper.min.css"/>
<style type="">

.navbar__item-w {
    background: #ec5051;
    border: solid 1px #f9f1f1;
    border-bottom-width: 0px;
    display:block;
}
 .navbar__item-w:hover{background:black ;}
.forIndex .navbar__item .nav-label{
    color: white !important;
}
</style>
<div id="navscript"></div>
<!-- login info start -->
<c:set var="pulicUserName" value="" />
<c:set var="userLevel" value="0" />
<c:set var="pulicTitle" value="欢迎您，点击返回用户管理中心" />
<c:url var="m_url" value="/manager/customer/index.do" />
<c:set var="userType" value="${userInfo.user_type}" />
<c:if test="${not empty userInfo}">
  <c:set var="pulicUserName" value="${fn:escapeXml(userInfo.real_name)}" />
  <c:set var="userLevel" value="${fn:escapeXml(userInfo.map.user_level)}" />
</c:if>
<c:if test="${userType eq 1}">
  <c:set var="pulicTitle" value="欢迎您，点击返回系统管理员管理中心" />
  <c:url var="m_url" value="/manager/admin/Frames.do" />
    <c:if test="${not empty server_min_domain}">
	   <c:url var="m_url" value="http://www.${server_min_domain}/manager/admin/Frames.do" />
	</c:if>
  <c:set var="pulicUserName" value="" />
</c:if>
<!--login info ent -->
<div id="site-mast" class="site-mast">
<div class="site-mast__user-nav-w">
  <div class="site-mast__user-nav cf">
    <ul class="basic-info">
      <li class="site-mast__keep">
      	<a class="fav log-mod-viewed" onclick="addFavorite('http://${app_domain}','${app_name}')">
      		<i class="F-glob icon-keep F-glob-star-border"></i> 收藏${app_name_min}
      	</a>
      </li>
      <li class="user-info cf"> 
      <c:if test="${empty userInfo}">
      <c:url var="url" value="/IndexLogin.do" />
      <c:if test="${not empty server_min_domain}">
	   <c:url var="url" value="http://www.${server_min_domain}/IndexLogin.do" />
	  </c:if>
      <a class="user-info__login" href="${url}" onclick="this.href;return true;" title="登录">登录</a>
      </c:if>
      <c:if test="${not empty userInfo}">
      <a rel="nofollow" class="username" style="cursor:pointer;" title="[${fn:escapeXml(pulicUserName)}]${pulicTitle}" onclick="location.href='${m_url}'">
      ${fn:escapeXml(fnx:abbreviate(pulicUserName, 2 * 5, ""))}</a>
      <span class="growth-info"><i class="sp-growth-icons level-icon level-icon-${userLevel}"></i></span>
      <c:url var="url" value="/IndexLogin.do?method=logout" />
      <c:if test="${not empty server_min_domain}">
         <c:url var="url" value="http://www.${server_min_domain}/IndexLogin.do?method=logout" />
      </c:if>
      <a class="user-info__logout" href="${url}" title="退出">退出</a>
      <c:url var="url" value="/manager/customer/MyMsg.do?par_id=1100630000&mod_id=1100630100&read_state=0" />
      <c:if test="${not empty server_min_domain}">
         <c:url var="url" value="http://www.${server_min_domain}/manager/customer/MyMsg.do?par_id=1100630000&mod_id=1100630100&read_state=0" />
      </c:if>
      <c:set var="class_msg" value="" />
      <c:if test="${user_msg gt 0}">
      <c:set var="class_msg" value="blink" />
      </c:if>
      <a class="user-info-msg" onclick="goUrlForIndex('${ctx}','${url}',1100630000)">消息<em class="${class_msg}">(${user_msg})</em></a>
      </c:if>
      <c:if test="${empty userInfo}">
      <c:url var="url" value="/Register.do" />
      <c:if test="${not empty server_min_domain}">
         <c:url var="url" value="http://www.${server_min_domain}/Register.do" />
      </c:if>
      <a class="user-info__signup" href="${url}">注册</a>
      </c:if>
      </li>
      <c:url var="url" value="/app" />
      <c:if test="${not empty server_min_domain}">
         <c:url var="url" value="http://www.${server_min_domain}/app" />
      </c:if>
       <li class="mobile-info__item dropdown"> <a class="dropdown__toggle" href="${url}"><i class="icon-mobile F-glob F-glob-phone"></i>手机${app_name_min}<i class="tri tri--dropdown"></i></a>
        <div class="dropdown-menu dropdown-menu--app"> <a class="app-block" href="${url}" target="_blank"> 
        <span class="app-block__title">免费下载${app_name_min}手机版</span> 
        <span class="app-block__content"></span> 
        <i class="app-block__arrow F-glob F-glob-caret-right"></i> </a> </div>
      </li>
      <c:url var="url" value="/manager/customer/EntpApply.do?method=add" />
     	<li class="user-info cf"> 
              <a  onclick="goUrlForIndex('${ctx}','${url}',1100630000)">我要开店</a>
        </li>
    </ul>
    <ul class="site-mast__user-w">
      <li class="dropdown dropdown--account"> 
       <c:if test="${empty userInfo}">
          <c:url var="m_url" value="/IndexLogin.do" />
       <c:if test="${not empty server_min_domain}">
         <c:url var="m_url" value="http://www.${server_min_domain}/IndexLogin.do" />
      </c:if>
       </c:if>
      <a class="dropdown__toggle" href="${m_url}"><span>我的${app_name_min}</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
        <ul class="dropdown-menu dropdown-menu--text dropdown-menu--account account-menu" id="gotoUserCenter" style="width:85px;">
          <li data-mod-id="1100500100"><a class="dropdown-menu__item first " href="javascript:void(0);">我的订单</a></li>
          <li data-mod-id="1100410010"><a class="dropdown-menu__item" href="javascript:void(0);">我的红包</a></li>
          <li data-mod-id="1100620100"><a class="dropdown-menu__item" href="javascript:void(0);">安全中心</a></li>
          <li data-mod-id="1100600200"><a class="dropdown-menu__item last" href="javascript:void(0);">账户设置</a></li>
        </ul>
      </li>
      <li class="dropdown dropdown--history">
      <a class="dropdown__toggle" href="#"> 
      <span>最近浏览</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
       <c:if test="${not empty cookiesCommList}">
       <div id="J-my-history-menu" class="dropdown-menu dropdown-menu--deal dropdown-menu--history">
        <ul>
        <c:forEach var="cur" items="${cookiesCommList}">
        <li class="dropdown-menu__item">
        <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
        <c:if test="${not empty server_min_domain}">
          <c:url var="url" value="http://www.${server_min_domain}/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
     	</c:if>
        <a class="deal-link" href="${url}" title="${cur.comm_name}" target="_blank">
        <img class="deal-cover" src="${ctx}/${cur.main_pic}" width="80" height="50"></a>
        <h5 class="deal-title"><a class="deal-link" href="${url}" title="${cur.comm_name}" target="_blank" >${cur.comm_name}</a></h5>
        <a class="deal-price-w" target="_blank" href="${url}">
        <em class="deal-price">￥<fmt:formatNumber pattern="0.00" value="${cur.sale_price}"/></em>
        </a>
        </li>
        </c:forEach>
        </ul>
        <p id="J-clear-my-history" class="clear">
        <a class="clear__btn" href="javascript:void(0)" id="clearCommHistory">清空最近浏览记录</a></p>
       </div>
       </c:if>
       <c:if test="${empty cookiesCommList}"> 
        <div class="dropdown-menu dropdown-menu--deal dropdown-menu--history">
          <p class="dropdown-menu--empty">暂无浏览记录</p>
        </div>
        </c:if>
      </li>
      <li class="dropdown dropdown--cart J-cart-updated" id="view_cart_info"> 
      <c:url var="url" value="/IndexLogin.do" />
      <c:if test="${not empty server_min_domain}">
          <c:url var="url" value="http://www.${server_min_domain}/IndexLogin.do" />
     </c:if>
      <c:if test="${not empty userInfo}">
        <c:url var="url" value="/IndexShoppingCar.do" />
      <c:if test="${not empty server_min_domain}">
          <c:url var="url" value="http://www.${server_min_domain}/IndexShoppingCar.do" />
     </c:if>
      </c:if>
      <a class="dropdown__toggle" href="${url}"> 
      <i class="icon icon-cart F-glob F-glob-cart"></i> 
      <span>购物车<em class="badge"><strong class="cart-count" id="gwcCount">0</strong>件</em></span> 
      <i class="tri tri--dropdown"></i><i class="vertical-bar"></i> </a>
       <div class="dropdown-menu dropdown-menu--deal dropdown-menu--cart" id="J-my-cart-menu">
         
        </div>
      </li>
      <li class="dropdown dropdown--help"> 
      <a class="dropdown__toggle" href="#"> <span>联系客服</span> 
      <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
        <div class="dropdown-menu dropdown-menu--text dropdown-menu--help">
          <ul class="site-help-info">
<%--             <c:url var="url" value="/IndexWebsiteIntroduction.do?method=view&mod_code=1014002000" /> --%>
            <c:url var="url" value="/IndexHelpInfo.do?method=view&h_mod_id=10090100" />
            <c:if test="${not empty server_min_domain}">
          		<c:url var="url" value="http://www.${server_min_domain}/IndexHelpInfo.do?method=view&h_mod_id=10090100" />
    	 	</c:if>
            <li><a class="J-selfservice-tab dropdown-menu__item" href="${url}">关于我们</a></li>
            <c:url var="url" value="/ChangeCity.do" />
            <c:if test="${not empty server_min_domain}">
          		<c:url var="url" value="http://www.${server_min_domain}/ChangeCity.do" />
    	 	</c:if>
            <li><a class="J-selfservice-tab dropdown-menu__item" href="${url}">服务地图</a></li>
            <c:url var="url" value="/IndexHelpInfo.do?method=view&h_mod_id=10040100" />
            <c:if test="${not empty server_min_domain}">
          		<c:url var="url" value="http://www.${server_min_domain}/IndexHelpInfo.do?method=view&h_mod_id=10040100" />
    	 	</c:if>
            <li><a class="dropdown-menu__item" href="${url}">常见问题</a></li>
          </ul>
        </div>
      </li>
<!--       <li class="dropdown dropdown--merchant">  -->
<%--       <c:url var="url" value="/IndexLogin.do" /> --%>
<%--       <c:if test="${not empty userInfo}"> --%>
<%--       <c:url var="url" value="/manager/customer/Index.do" /></c:if> --%>
<%--       <a class="dropdown__toggle dropdown__toggle--merchant" href="${url}">  --%>
<!--       <span>我是商家</span>  -->
<!--       <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a> -->
<!--         <div class="dropdown-menu dropdown-menu--text dropdown-menu--merchant"> -->
<!--           <ul> -->
<%--             <li><a class="dropdown-menu__item" href="${url}">登录商家中心</a></li> --%>
<!--           </ul> -->
<!--         </div> -->
<!--       </li> -->
    </ul>
  </div>
</div>

<c:if test="${not empty base10LinkList}">
<div class="yui3-widget mt-slider" id="topAd">
  <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" id="slide" style="height: 60px;border: none;">
    <c:forEach var="cur" items="${base10LinkList}" varStatus="vs">
        <a class="common-banner__link a_bigImgForslide" href="${cur.link_url}" title="${cur.title}" target="_blank">
        	<img alt="${cur.title}" src="${ctx}/${cur.image_path}" width="100%" height="60"></a> 
    </c:forEach>
    <a href="javascript:void(0);" class="F-glob F-glob-close common-close--iconfont-small close" title="关闭" id="colseAd"></a> 
    <ul class="trigger ui-slider__triggers ui-slider__triggers--translucent ui-slider__triggers--small mt-slider-trigger-container" id="ul_change_a2Forslide">
    <c:forEach var="cur" items="${base10LinkList}" varStatus="vs">
     <li class="trigger-item mt-slider-trigger"></li>
    </c:forEach>
    </ul>
    <script type="text/javascript" src="${ctx}/commons/scripts/ppt/pptjs/ppt.js"></script>
    <script type="text/javascript">
		$('#slide .a_bigImgForslide').soChange({thumbObj:'#slide #ul_change_a2Forslide li',thumbNowClass:'mt-slider-current-trigger'});
     </script>
    </div>
</div>
</c:if>

<div class="site-mast__branding cf">
  <h1>
  <c:url var="url" value="/Index.do" />
  <c:if test="${not empty server_min_domain}">
   <c:url var="url" value="http://www.${server_min_domain}" />
  </c:if>
   <c:if test="${empty base0Link}">
 	<a class="site-logo" href="${url}">${app_name_min}</a>
   </c:if>
   <c:if test="${not empty base0Link}">
 	<a class="site-logo" style="background-image: url('${ctx}/${base0Link.image_path}');background-repeat:repeat;" href="${url}">${app_name_min}</a>
   </c:if>
  </h1>
  <div class="city-info" style="text-align: center;">
    <h2>
    <a class="city-info__name" id="city_name">${p_index_city_name}</a></h2>
    <c:url var="url" value="/ChangeCity.do" />
   <c:if test="${not empty server_min_domain}">
   		<c:url var="url" value="http://www.${server_min_domain}/ChangeCity.do" />
   </c:if>
    <a class="city-info__toggle" href="${url}">切换城市</a> </div>
  <div class="component-search-box mt-component--booted">
    <div class="J-search-box search-box">
      <div class="search-box__form J-search-form cf">
      	<input type="hidden" name="htype" id="htype" value="0" />
        <div class="search-box__tabs-container" style="float:left"> <span class="tri"></span>
          <ul class="J-search-box__tabs search-box__tabs" id="searchUl">
            <li class="search-box__tab J-search-box__tab--deal search-box__tab--current" data-flag="0">商品</li>
            <li class="search-box__tab J-search-box__tab--shops" data-flag="1">店铺</li>
          </ul>
        </div>
        <input tabindex="1" type="text" name="search" autocomplete="off" class="s-text search-box__input J-search-box__input" value="${af.map.keyword}" placeholder="请输入商品名称等">
        <c:if test="${empty canSearchHd}">
        <input type="button" class="s-submit search-box__button log-mod-viewed" value="搜索" id="searchbtn" />
        </c:if>
        <c:if test="${not empty canSearchHd}">
        <input type="button" class="s-submit search-box__button log-mod-viewed" value="搜活动" id="searchbtnHd" />
        <input type="button" class="s-submit search-box__button log-mod-viewed" value="搜全场" id="searchbtn" />
        </c:if>
      </div>
      <ul class="search-suggest J-search-suggest" style="display:none;">
      </ul>
      <div class="smart-query-panel" style="display:none">
        <div class="smart-query-content"></div>
      </div>
      <div class="J-search-box__hot search-box__hot log-mod-viewed">
        <div class="s-hot">
         <jsp:include page="public/_index_rmss.jsp" flush="true" />
       </div>
      </div>
    </div>
  </div>
</div>
<div class="site-mast__site-nav-w categorys home_categorys" style="width: 100%;border-bottom: 2px solid #e23435;">
<div class="site-mast__site-nav">
  <div class="site-mast__site-nav-inner">
    <div class="component-cate-nav mt-component--booted" id="J-nav__trigger">
    <c:url var="url" value="/Search.do" />
   <c:if test="${not empty server_min_domain}">
   		<c:url var="url" value="http://www.${server_min_domain}/Search.do" />
   </c:if>
<%--     <a href="${url}" target="_blank"> --%>
<!--     <span class="mt-cates J-nav__trigger"> -->
<!--     <i class="F-glob F-glob-caret-down"></i> -->
<!--     <i class="F-glob F-glob-caret-up"></i>全部分类</span></a> -->
      <!--------主菜单开始---------->
      
      <div class="dd cate-nav" style="display: none;"><div class="cata-nav" id="cata-nav"></div></div>
      </div>
      <!--------主菜单结束----------> 
      <!-------导航开始------->
      <div id="swiper_class_right">
        <ul class="navbar cf  log-mod-viewed swiper-wrapper" style="margin-left: 0px; height: 38px;">
          <li class="navbar__item-w"> 
          <a class="navbar__item"  onclick="clickedSonId('')"><span class="nav-label">全部</span></a> </li>
          <c:forEach items="${commClasslist}" var="cur">
          <li class="navbar__item-w "> 
          <a class="navbar__item"  onclick="clickedSonId('${cur.id}')" title="${fn:escapeXml(cur.class_name)}" target="_blank">
          <span class="nav-label">${fn:escapeXml(fnx:abbreviate(cur.class_name, 2 * 6, ""))}</span></a> </li>
         </c:forEach>
        </ul>
      </div>
      <!-------导航结束-------> 
    </div>
  </div>
</div>
</div>
<div class="clear"></div>
<script type="text/javascript" src="${ctx}/scripts/swiper/swiper.min.js"></script>
<script type="text/javascript">//<![CDATA[
new Swiper('#swiper_class_right', {
	slidesPerView : 'auto',
	  });                                    
                                     
$(document).ready(function(){
	
	var htype_show = "${af.map.htype}";
	
	if(null != htype_show && '' != htype_show){
		$("#htype").val(${af.map.htype});
		$("#searchUl li").each(function(index){
			if(index == htype_show){
				var data_flag = $(this).attr("data-flag");
				$(this).addClass("search-box__tab--current").siblings().removeClass("search-box__tab--current");
				$(this).insertBefore($(this).prev());
				if(data_flag == 0){
					$(".search-box__input").attr("placeholder","请输入商品名称等");
				}else{
					$(".search-box__input").attr("placeholder","请输入店铺名称等");
				}
				return false;
			}
		});
	}
	
	$("#searchbtn").click(function(){
		var htype = $("#htype").val();
		location.href = "${ctx}/Search.do?htype=" + htype + "&keyword=" + $("input[name='search']").val();
	});
	$("input[name='search']").keydown(function(e){
		var htype = $("#htype").val();
		if(e.keyCode==13){
			location.href = "${ctx}/Search.do?htype=" + htype + "&keyword=" + $("input[name='search']").val();
		}
	});
	$("#searchbtnHd").click(function(){
		var htype = $("#htype").val();
		location.href = "${ctx}/Search.do?htype=" + htype + "&comm_type=4&keyword=" + $("input[name='search']").val();
	});

	$(".site-mast__user-w li").each(function(index){
		$(this).mouseover(function(){
			$(this).addClass("dropdown--open");
		}).mouseout(function(){
			$(this).removeClass("dropdown--open");
		});
	});
	$(".basic-info li").each(function(index){
		$(this).mouseover(function(){
			$(this).addClass("dropdown--open dropdown--open-app");
		}).mouseout(function(){
			$(this).removeClass("dropdown--open dropdown--open-app");
		});
	});
	$(".search-box__tabs-container").mouseover(function(){
		$(this).addClass("search-box__tabs-container--over");
	}).mouseout(function(){
		$(this).removeClass("search-box__tabs-container--over");
	});
	$(".search-box__tabs li").each(function(index){
		var data_flag = $(this).attr("data-flag");
		if(data_flag == 0){
			$(this).mouseover(function(){
				$(this).parent().parent().addClass("search-box__tabs-container--over");
			}).mouseout(function(){
				$(this).parent().parent().removeClass("search-box__tabs-container--over");
			});
		}
		$(this).click(function(){
			$(this).addClass("search-box__tab--current").siblings().removeClass("search-box__tab--current");
			$(this).insertBefore($(this).prev());
			$("#htype").val(data_flag);
			$(this).parent().parent().removeClass("search-box__tabs-container--over");
			if(data_flag == 0){
				$(".search-box__input").attr("placeholder","请输入商品名称等");
			}else{
				$(".search-box__input").attr("placeholder","请输入店铺名称等");
			}
		});
	});

	var options = {
	serviceUrl : "${ctx}/CsAjax.do?method=getCommInfoListForSeach&&htype=" + $("input[name='htype']").val(),
	maxHeight : 245,
	width: 402,
	delimiter : /(,|;)\s*/,
	pageCount : 20,
	animate : true,
	forCommInfoList: true,
	onSelect : function(value, data) {
		  location.href = "${ctx}/Search.do?htype=" + $("input[name='htype']").val() + "&keyword=" + value;
	},
	deferRequestBy : 0, 
	params : {country : "Yes"}
	};
	var se = $("#keyword").autocomplete(options);

	
	$("#J-nav__trigger").hoverDelay({
	        hoverEvent: function(){
	        	$(this).addClass("nav-unfolded");
	    	    if ($("#nav-Parent-div").children().length < 1) {
		    	    var url = "${ctx}/CsAjax.do?method=getHeaderNavNewV2";
		    	    $.ajax({type: "POST",url: url,dataType: "text",error: function(request, settings) {},
		    			success: function(result) {
		    				if (result != "") {
		    					$("#cata-nav").empty().append(result);
							    var nav = document.createElement('script'); nav.type = 'text/javascript'; nav.src = "${ctx}/scripts/nav/nav.min.js";
							    $("#navscript").append(nav);
		    				}
		    			}
		    		});
	    	    }
	    	    $(this).find(".cate-nav").show();       
	        },
	        outEvent: function(){
	        	$(this).removeClass("nav-unfolded");
	        	$(this).find(".cate-nav").hide();
	        }
	    });
	
	getCookiePindex();
	getGwcCount();
});

function getGwcCount(){                                          
	$.post("${ctx}/CsAjax.do?method=getGwcCount",{user_id : '${userInfo.id}'},function(data){
		 $("#gwcCount").text(data.result);
		 $("#gwcCountComm").text(data.result);
 });                                        
}

function getCookiePindex(){
	  $.post("${ctx}/CsAjax.do?method=getCookiePindex",{},function(datas){
			if(datas.ret == "1"){
				var city_name = datas.p_name;
				if(city_name){
					$("#city_name").text(city_name);
				}
			}
		});
	}

$("#gotoUserCenter li").each(function(){
	$(this).click(function(){
		var modId = $(this).attr("data-mod-id");
	    $.post("${ctx}/CsAjax.do?method=getUrlLinkModId",{mod_id:modId},function(data){
			if(data.ret == 1){
				var parId_cookie = data.par_id + "," + data.data_url;
				if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
				location.href= "${ctx}/manager/customer/index.shtml";
			}
		});
	});
});

$("#clearCommHistory").click(function(){
	$.cookie("browseCommInfos",null,{path:"/"});
	$("#J-my-history-menu").remove();
	$(".dropdown--history").removeClass("dropdown--open");
});

var timeConfig = null;
$("#view_cart_info").mouseover(function(){
	timeConfig = setTimeout(function(){
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do",
		data: "method=ViewGwc",
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
			var html="";
			if(datas.list != "" && null != datas.list && datas.list.length > 0){
			    html = '<ul class="list-wrapper">';
				for(var x in datas.list){
					var cur = datas.list[x];
					html += '<li class="dropdown-menu__item">';
					html += '<a href="${ctx}/item-'+cur.comm_id+'.shtml" title="' + cur.comm_name + '" target="_blank"  class="deal-link">';
					html += '<img class="deal-cover" src="${ctx}/'+ cur.comm_pic +'" width="80" height="50"></a>';
					html += '<h5 class="deal-title">';
					html += '<a href="" title="" target="_blank" class="deal-link">' + cur.comm_name + '</a></h5>';
					html += '<p class="deal-price-w"><a class="delete link--black__green" onclick=deleteOrder(this,' + cur.cart_id + ')>删除</a>';
					html += '<em class="deal-price">¥'+ cur.comm_price +'</em></p>';
					html += '</li>';
				   }
	              html += "</ul>";
				  html += '<p class="check-my-cart"><a class="btn btn-hot btn-small" href="${ctx}/cart.shtml">查看我的购物车</a></p>';
			}else{
				html += '<p class="dropdown-menu--empty">暂时没有商品</p>';
			}
			$("#J-my-cart-menu").html(html);
			$("#J-my-cart-menu").show();
		}
	});
	}, 1000);
}).mouseout(function(){
	 if (timeConfig) {
	        clearTimeout(timeConfig);
	 }
});

function deleteOrder(thisobj,cart_id){
	 $.post("${ctx}/CsAjax.do?method=delCart",{cart_id : cart_id},function(data){
			if (data.result) {
				$(thisobj).parent().parent().remove();
				var gwcCount= $("#gwcCount").text();
				var cartCount = data.cartCount;
				var pd_price_count = data.pd_price_count;
				if(isNaN(cartCount)){
					cartCount = 0;
				}
				$("#gwcCount").text(cartCount);
				var size = $("#J-my-cart-menu").find("ul li").length;
				if(size ==0){
					var html = '<p class="dropdown-menu--empty">暂时没有商品</p>';
					$("#J-my-cart-menu").html(html);
				}
				
			}
	});
} 
	
//]]></script>