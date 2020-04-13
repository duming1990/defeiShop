<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript" src="${ctx}/scripts/autocomplete/autocompleteForSeach.js"></script>
<div id="site-mast" class="site-mast">
<div class="site-mast__user-nav-w">
  <div class="site-mast__user-nav cf">
    <ul class="basic-info">
      <li class="site-mast__keep"> <a class="fav log-mod-viewed"> <i class="F-glob icon-keep F-glob-star-border"></i> 收藏${app_name_min} </a> </li>
      <li class="user-info cf"> <a class="user-info__login">登录</a> <a class="user-info__signup">注册</a> </li>
<!--       <li class="dropdown dropdown--msg" style="display:;"> <a class="dropdown__toggle"> <i class="vertical-bar vertical-bar--left"></i> <span class="J-title">消息</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a> </li> -->
      <li class="mobile-info__item dropdown"> <a class="dropdown__toggle"><i class="icon-mobile F-glob F-glob-phone"></i>手机${app_name_min}<i class="tri tri--dropdown"></i></a>
        <div class="dropdown-menu dropdown-menu--app"> <a class="app-block"> <span class="app-block__title">免费下载${app_name_min}手机版</span> <span class="app-block__content"></span> <i class="app-block__arrow F-glob F-glob-caret-right"></i> </a> </div>
      </li>
    </ul>
    <ul class="site-mast__user-w">
      <li class="user-orders"> <a>我的订单</a> </li>
      <li class="dropdown dropdown--account"> 
      <a class="dropdown__toggle"> <span>我的${app_name_min}</span> 
      <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
        <ul class="dropdown-menu dropdown-menu--text dropdown-menu--account account-menu">
          <li><a class="dropdown-menu__item first ">我的订单</a></li>
          <li><a class="dropdown-menu__item">我的评价</a></li>
          <li><a class="dropdown-menu__item">我的收藏</a></li>
          <li><a class="dropdown-menu__item">我的积分</a></li>
          <li><a class="dropdown-menu__item">抵用券</a></li>
          <li><a class="dropdown-menu__item">${app_name_min}余额</a></li>
          <li><a class="dropdown-menu__item last">账户设置</a></li>
        </ul>
      </li>
      <li class="dropdown dropdown--history"> <a class="dropdown__toggle"> <span>最近浏览</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
        <div class="dropdown-menu dropdown-menu--deal dropdown-menu--history">
          <p class="dropdown-menu--empty">暂无浏览记录</p>
        </div>
      </li>
      <li class="dropdown dropdown--cart J-cart-updated"> <a class="dropdown__toggle"> <i class="icon icon-cart F-glob F-glob-cart"></i> <span>购物车<em class="badge"><strong class="cart-count">0</strong>件</em></span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
        <div class="dropdown-menu dropdown-menu--deal dropdown-menu--cart">
          <p class="dropdown-menu--empty">暂时没有商品</p>
        </div>
      </li>
      <li class="dropdown dropdown--help"> <a class="dropdown__toggle"> <span>联系客服</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
        <div class="dropdown-menu dropdown-menu--text dropdown-menu--help">
          <ul class="site-help-info">
            <li><a class="J-selfservice-tab dropdown-menu__item">申请退款</a></li>
            <li><a class="J-selfservice-tab dropdown-menu__item">申请退换货</a></li>
            <li><a class="J-selfservice-tab dropdown-menu__item">查看${app_name_min}券</a></li>
            <li><a class="J-selfservice-tab dropdown-menu__item">换绑手机号</a></li>
            <li><a class="dropdown-menu__item">常见问题</a></li>
          </ul>
        </div>
      </li>
      <li class="dropdown dropdown--merchant"> <a class="dropdown__toggle dropdown__toggle--merchant"> <span>我是商家</span> <i class="tri tri--dropdown"></i> <i class="vertical-bar"></i> </a>
        <div class="dropdown-menu dropdown-menu--text dropdown-menu--merchant">
          <ul>
            <li><a class="dropdown-menu__item">登录商家中心</a></li>
            <li><a class="dropdown-menu__item">我想合作</a></li>
            <li><a class="dropdown-menu__item">商家营销平台</a></li>
          </ul>
        </div>
      </li>
      <li class="dropdown dropdown--more dropdown--last"> <a class="dropdown__toggle dropdown__toggle--my-more"> <span>更多</span> <i class="tri tri--dropdown"></i> </a>
        <div class="dropdown-menu dropdown-menu--text dropdown-menu--more">
          <ul>
            <li> <a class="mobile dropdown-menu__item"><span></span>菜单一</a> </li>
            <li> <a class="subscribe dropdown-menu__item"><span></span>菜单二</a> </li>
            <li class="last"> <a class="refer dropdown-menu__item">菜单三</a> </li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</div>
<div class="yui3-widget mt-slider">
  <div class="J-hub J-banner-newtop ui-slider common-banner common-banner--newtop common-banner--floor log-mod-viewed J-banner-stamp-active mt-slider-content" style="height:60px;">
    <ul class="common-banner__sheets mt-slider-sheet-container">
      <li class="common-banner__sheet cf mt-slider-sheet" style="opacity: 1;">
        <div class="color color--left" style="background:#d0f1f8"></div>
        <div class="color color--right" style="background:#d0f1f8"></div>
        <a class="common-banner__link">
        <img alt="【多城市】1元起吃喝玩乐" src="${ctx}/styles/index/images/up_imgs/aa6cd606986692900582816b97fce70747794.jpg" width="980" height="60"></a> </li>
    </ul>
    <a class="F-glob F-glob-close common-close--iconfont-small close" title="关闭"></a> 
     <ul class="trigger ui-slider__triggers ui-slider__triggers--translucent ui-slider__triggers--small mt-slider-trigger-container">
	    <li class="trigger-item mt-slider-trigger"></li>
	    <li class="trigger-item mt-slider-trigger mt-slider-current-trigger"></li></ul>
    </div>
</div>
<div class="site-mast__branding cf">
  <h1><a class="site-logo">合肥${app_name_min}</a></h1>
  <div class="city-info">
    <h2><a class="city-info__name">合肥</a></h2>
    <a class="city-info__toggle">切换城市</a> </div>
  <div class="component-search-box mt-component--booted">
    <div class="J-search-box search-box ">
      <form class="search-box__form J-search-form cf" action="Seach.do">
       <input type="hidden" id="htype" name="htype" value="0">
        <div class="search-box__tabs-container" style="float:left"> <span class="tri"></span>
          <ul class="J-search-box__tabs search-box__tabs">
            <li class="search-box__tab J-search-box__tab--deal search-box__tab--current">商品</li>
            <li class="search-box__tab J-search-box__tab--shops">商家</li>
          </ul>
        </div>
        <input tabindex="1" type="text" name="keyword" id="keyword" autocomplete="off" class="s-text search-box__input J-search-box__input" value="" placeholder="请输入商品名称、地址等">
        <input type="submit" class="s-submit search-box__button log-mod-viewed" value="搜索">
      </form>
    </div>
  </div>
</div>
<div class="site-mast__site-nav-w forIndexHeader categorys home_categorys" style="width: 100%;height: 38px;">
<div class="site-mast__site-nav">
  <div class="site-mast__site-nav-inner">
    <div class="component-cate-nav mt-component--booted">
    <div>
    <span class="mt-cates J-nav__trigger">全部分类</span> 
      <!-------导航开始------->
      <div>
        <ul class="navbar cf  log-mod-viewed">
          <li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">首页</span></a> </li>
          <li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">今日新单</span></a> </li>
          <li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">身边团购</span></a> </li>
          <li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">购物</span></a> </li>
          <li class="navbar__item-w "><a class="navbar__item"><span class="nav-label">身边外卖</span></a> </li>
          <li class="navbar__item-w "><a class="navbar__item navbar__item-hots"><span class="nav-label">名店抢购</span></a> </li>
        </ul>
      </div>
      <!-------导航结束-------> 
    </div>
  </div>
</div>
</div>
</div>
<div class="clear"></div>