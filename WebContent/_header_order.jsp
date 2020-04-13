<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cookie.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocompleteForSeach.js"></script>
<!-- login info start -->
<c:set var="pulicUserName" value="" />
<c:set var="pulicTitle" value="欢迎您，点击返回用户管理中心" />
<c:url var="m_url" value="/manager/customer/index.do" />
<c:set var="userType" value="${userInfo.user_type}" />
<c:set var="userLevel" value="0" />
<c:if test="${not empty userInfo}">
  <c:set var="pulicUserName" value="${fn:escapeXml(userInfo.real_name)}" />
  <c:set var="userLevel" value="${fn:escapeXml(userInfo.map.user_level)}" />
</c:if>
<c:if test="${userType eq 1}">
  <c:set var="pulicTitle" value="欢迎您，点击返回系统管理员管理中心" />
  <c:url var="m_url" value="/manager/admin/Frames.do" />
  <c:set var="pulicUserName" value="" />
</c:if>
<div id="site-mast" class="site-mast">
<div class="site-mast__user-nav-w">
  <div class="site-mast__user-nav cf">
    <ul class="basic-info">
      <li class="site-mast__keep">
      <a class="fav log-mod-viewed" onclick="addFavorite('http://${app_domain}','${app_name}')"><i class="F-glob icon-keep F-glob-star-border"></i> 收藏${app_name_min} </a> </li>
      <li class="user-info cf"> 
      <c:if test="${empty userInfo}">
      <c:url var="url" value="/IndexLogin.do" />
      <a class="user-info__login" href="${url}" onclick="this.href;return true;" title="登录">登录</a>
      </c:if>
      <c:if test="${not empty userInfo}">
      <a rel="nofollow" class="username" style="cursor:pointer;" title="[${fn:escapeXml(pulicUserName)}]${pulicTitle}" onclick="location.href='${m_url}'">${fn:escapeXml(fnx:abbreviate(pulicUserName, 2 * 5, ""))}</a>
      <span class="growth-info"><i class="sp-growth-icons level-icon level-icon-${userLevel}"></i></span>
      <c:url var="url" value="/IndexLogin.do?method=logout" />
      <a class="user-info__login" href="${url}" title="退出">退出</a>
      <c:url var="url" value="/manager/customer/MyMsg.do?par_id=1100630000&mod_id=1100630100&read_state=0" />
      <c:set var="class_msg" value="" />
      <c:if test="${user_msg gt 0}">
      <c:set var="class_msg" value="blink" />
      </c:if>
      <a class="user-info-msg" onclick="goUrlForIndex('${ctx}','${url}',1100630000)">消息<em class="${class_msg}">(${user_msg})</em></a>
      </c:if>
      <c:if test="${empty userInfo}">
      <c:url var="url" value="/Register.do" />
      <a class="user-info__signup" href="${url}">注册</a> 
      </c:if>
      </li>
       <c:url var="url" value="/app" />
       <li class="mobile-info__item dropdown"> <a class="dropdown__toggle" href="${url}"><i class="icon-mobile F-glob F-glob-phone"></i>手机${app_name_min}<i class="tri tri--dropdown"></i></a>
        <div class="dropdown-menu dropdown-menu--app"> <a class="app-block" href="${url}" target="_blank"> 
        <span class="app-block__title">免费下载${app_name_min}手机版</span> 
        <span class="app-block__content"></span> 
        <i class="app-block__arrow F-glob F-glob-caret-right"></i> </a> </div>
      </li>
    </ul>
    <ul class="site-mast__user-w">
      <li class="dropdown dropdown--account"> 
       <c:if test="${empty userInfo}">
          <c:url var="m_url" value="/IndexLogin.do" />
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
      <c:if test="${not empty userInfo}">
        <c:url var="url" value="/IndexShoppingCar.do" />
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
            <c:url var="url" value="/IndexWebsiteIntroduction.do?method=view&mod_code=1014002000" />
            <li><a class="J-selfservice-tab dropdown-menu__item" href="${url}">关于我们</a></li>
            <c:url var="url" value="/ChangeCity.do" />
            <li><a class="J-selfservice-tab dropdown-menu__item" href="${url}">服务地图</a></li>
            <c:url var="url" value="/IndexHelpInfo.do?method=view&h_mod_id=10010100" />
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

<div class="site-mast__branding cf">
  <c:url var="url" value="/index.do" />
  <h1><a class="site-logo" href="${url}">${app_name_min}</a></h1>
  <div class="buy-process-bar-container">
    <ol class="buy-process-desc steps-desc">
        <li class="step current" style="width:33.333333333333%" id="step1">1. 提交订单</li>
        <li class="step" style="width:33.333333333333%" id="step2">2. 选择支付方式</li>
        <li class="step" style="width:33.333333333333%" id="step3">3. 购买成功</li>
    </ol>
    <div class="buyProgress">
        <div class="progress-bar" style="width:33.333333333333%"></div>
    </div>
</div>
</div>
</div>
<script type="text/javascript">//<![CDATA[
 
 $(document).ready(function(){
	
	 $("#step${stepFlag}").addClass("current").siblings().removeClass("current");
	 $(".progress-bar").css("width",(33.333333333333*${stepFlag})+"%");
		
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
		getCookiePindex();
		getGwcCount();
	});

	function getGwcCount(){                                          
		$.post("${ctx}/CsAjax.do?method=getGwcCount",{user_id : '${userInfo.id}'},function(data){
			 $("#gwcCount").text(data.result);
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
<!--head end -->