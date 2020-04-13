<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/public.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cookie.js"></script>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocompleteForSeach.js"></script>
<style>
#searchbtn{
    font-size: inherit;
    float: left;
    width: 65px;
    margin: 0;
    height: 42px;
    border: none;
    color: #fff;
    background: #ec5051;
    cursor: pointer;
    -webkit-appearance: none;
    border-radius: 0;
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
  <c:set var="pulicUserName" value="" />
</c:if>
<!--login info ent -->
<div id="site-mast" class="site-mast">
  <div class="site-mast__user-nav-w">
    <div class="site-mast__user-nav cf">
      <ul class="basic-info">
        <li class="site-mast__keep"> <a class="fav log-mod-viewed" onclick="addFavorite('http://${app_domain}','${app_name}')"><i class="F-glob icon-keep F-glob-star-border"></i> 收藏${app_name_min} </a> </li>
        <li class="user-info cf">
          <c:if test="${empty userInfo}">
            <c:url var="url" value="/IndexLogin.do" />
            <a class="user-info__login" href="${url}" onclick="this.href;return true;" title="登录">登录</a> </c:if>
          <c:if test="${not empty userInfo}"> <a rel="nofollow" class="username" style="cursor:pointer;" title="[${fn:escapeXml(pulicUserName)}]${pulicTitle}" onclick="location.href='${m_url}'"> ${fn:escapeXml(fnx:abbreviate(pulicUserName, 2 * 5, ""))}</a> <span class="growth-info"><i class="sp-growth-icons level-icon level-icon-${userLevel}"></i></span>
            <c:url var="url" value="/IndexLogin.do?method=logout" />
            <a class="user-info__logout" href="${url}" title="退出">退出</a>
            <c:url var="url" value="/manager/customer/MyMsg.do?par_id=1100630000&mod_id=1100630100&read_state=0" />
            <c:set var="class_msg" value="" />
            <c:if test="${user_msg gt 0}">
              <c:set var="class_msg" value="blink" />
            </c:if>
            <a class="user-info-msg" onclick="goUrlForIndex('${ctx}','${url}',1100630000)">消息<em class="${class_msg}">(${user_msg})</em></a> </c:if>
          <c:if test="${empty userInfo}">
            <c:url var="url" value="/Register.do" />
            <a class="user-info__signup" href="${url}">注册</a> </c:if>
        </li>
        <c:url var="url" value="/app" />
        <li class="mobile-info__item dropdown"> <a class="dropdown__toggle" href="${url}"><i class="icon-mobile F-glob F-glob-phone"></i>手机${app_name_min}<i class="tri tri--dropdown"></i></a>
          <div class="dropdown-menu dropdown-menu--app"> <a class="app-block" href="${url}" target="_blank"> <span class="app-block__title">免费下载${app_name_min}手机版</span> <span class="app-block__content"></span> <i class="app-block__arrow F-glob F-glob-caret-right"></i> </a> </div>
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
        <li class="dropdown dropdown--history"> <a class="dropdown__toggle" href="#"> <span>最近浏览</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
          <c:if test="${not empty cookiesCommList}">
            <div id="J-my-history-menu" class="dropdown-menu dropdown-menu--deal dropdown-menu--history">
              <ul>
                <c:forEach var="cur" items="${cookiesCommList}">
                  <li class="dropdown-menu__item">
                    <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
                    <a class="deal-link" href="${url}" title="${cur.comm_name}" target="_blank"> <img class="deal-cover" src="${ctx}/${cur.main_pic}" width="80" height="50"></a>
                    <h5 class="deal-title"><a class="deal-link" href="${url}" title="${cur.comm_name}" target="_blank" >${cur.comm_name}</a></h5>
                    <a class="deal-price-w" target="_blank" href="${url}"> <em class="deal-price">￥
                    <fmt:formatNumber pattern="0.00" value="${cur.sale_price}"/>
                    </em> </a> </li>
                </c:forEach>
              </ul>
              <p id="J-clear-my-history" class="clear"> <a class="clear__btn" href="javascript:void(0)" id="clearCommHistory">清空最近浏览记录</a></p>
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
          <a class="dropdown__toggle" href="${url}"> <i class="icon icon-cart F-glob F-glob-cart"></i> <span>购物车<em class="badge"><strong class="cart-count" id="gwcCount">0</strong>件</em></span> <i class="tri tri--dropdown"></i><i class="vertical-bar"></i> </a>
          <div class="dropdown-menu dropdown-menu--deal dropdown-menu--cart" id="J-my-cart-menu"> </div>
        </li>
        <li class="dropdown dropdown--help"> <a class="dropdown__toggle" href="#"> <span>联系客服</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
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
      </ul>
    </div>
  </div>
  <c:if test="${not empty base10LinkList}">
    <div class="yui3-widget mt-slider" id="topAd">
      <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" id="slide" style="height:60px;">
        <c:forEach var="cur" items="${base10LinkList}" varStatus="vs"> <a class="common-banner__link a_bigImgForslide" href="${cur.link_url}" title="${cur.title}" target="_blank"> <img alt="${cur.title}" src="${ctx}/${cur.image_path}" width="100%" height="60"></a> </c:forEach>
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
      <a class="site-logo" href="${url}">${app_name_min}</a></h1>
    <div class="city-info">
      <h2> <a class="city-info__name" id="city_name">${p_index_city_name}</a></h2>
      <c:url var="url" value="/ChangeCity.do" />
      <a class="city-info__toggle" href="${url}">切换城市</a>
    </div>
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
          <input type="button" class="s-submit search-box__button log-mod-viewed" value="搜索" id="searchbtn">
        </div>
        <ul class="search-suggest J-search-suggest" style="display:none;">
        </ul>
        <div class="smart-query-panel" style="display:none">
          <div class="smart-query-content"></div>
        </div>
        <div class="J-search-box__hot search-box__hot log-mod-viewed">
          <div class="s-hot">
            <jsp:include page="../../public/_index_rmss.jsp" flush="true" />
          </div>
        </div>
      </div>
    </div>
  </div>
<script type="text/javascript" src="${ctx}/scripts/jquery.nav.js"></script>
<script type="text/javascript" src="${ctx}/scripts/index.js?v=20161130"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.touchSlider.js"></script>
<script type="text/javascript" src="${ctx}/scripts/marquee/marquee.js"></script>
