<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="seo_title" value="${fn:escapeXml(commInfo.comm_name)}" />
<c:set var="seo_title_entp" value="${fn:escapeXml(entpInfo.entp_name)}" />
<c:set var="seo_entp_shop" value="${fn:escapeXml(entpInfo.entp_name)}" />
<c:set var="seo_keyword" value="${fn:escapeXml(commInfo.key_word)}" />
<c:set var="seo_desc" value="${fn:escapeXml(fnx:abbreviate(pdContentXxxx, 2 * 100, ''))}" />
<meta name="description" content="${seo_desc}" />
<meta name="keywords" content="${seo_keyword}" />
<title>${(seo_title)} -${seo_title_entp}- 【${app_name}】</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/business-info-new.css?v=20180426"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/quickLinks.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/areaSelect/css/hzw-city-picker.css" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/zoom/css/zoom.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/themes/css/icon.css?v=20180319" />
<style>
.campaign {
    margin: 12px 5px 0 0;
    padding: 2px 4px;
    font-size: 14px;
    color: #fff;
    background-color: #ff8e3f;
    vertical-align: bottom;
    border-radius: 5px;
}
.m-item-inner .name{padding-bottom:10px;}
.aui-product-boutique {
	width: auto;
	padding: 10px 10px;
	margin: 0 0px 5px 0px;
	background: #eee;
}

.aui-product-boutique img {
	float: left;
	display: block;
	width: 16px;
	height: 16px;
	margin-right: 6px;
}

.aui-product-boutique i {
	float: left;
	margin-right: 0.3rem;
}

.aui-product-boutique .aui-product-tag-text {
	font-size: 14px !important;
	color: #d81e06 !important;
}
#s-poor-rebate .sback{position:absolute;width:24px;height:28px;display:block;top:6px;right: 0rem;font-size: 1.3rem;}
#s-poor-rebate{background-color:#f5f5f5;}
#s-poor-rebate h4{text-align: center;
    background-color: #fff;
    padding: 10px 0px;
    margin: 0;
    border-bottom: 1px solid #ececec;
}
#s-poor-rebate .aui-product-boutique{
    padding: 10px 10px;
    background: #fff;margin:0px;
}
#s-poor-rebate .cover-content{
    background-color: #fff;
    margin: 10px 0px;
    padding: 10px;
    font-size: 15px;
}
#s-poor-wrapper{background-color:#f5f5f5;}
#s-poor-wrapper h4{text-align: center;
    background-color: #fff;
    padding: 10px 0px;
    margin: 0;
    border-bottom: 1px solid #ececec;
}
#s-poor-wrapper .aui-product-boutique{
    padding: 10px 10px;
    background: #fff;margin:0px;
}
#s-poor-wrapper .cover-content{
    background-color: #fff;
    margin: 10px 0px;
    padding: 10px;
    font-size: 15px;
}
#s-poor-wrapper .aui-slide-box .aui-slide-list{
    margin: 0px!important; 
    background-color: #fff;
    padding: 10px;
}
#s-poor-wrapper .aui-slide-box .aui-slide-item-list .aui-slide-item-item .v-img{
    width: 5rem;
    height: 5rem;
    border-radius: 50%;
    margin: 0 auto;
}
.aui-slide-box .aui-slide-item-list {
	width: auto;
	white-space: nowrap;
	height: 9.9rem;
	font-size: 0;
	-webkit-overflow-scrolling: touch;
}
.aui-slide-box-clear .aui-slide-list {
	height: 7.5rem;
	margin-top: 0;
}
.aui-slide-box-clear .aui-slide-item-list {
	height: 7.8rem;
}
.aui-slide-box .aui-slide-item-list .aui-slide-item-item {
	display: inline-block;
	width: 6rem;
	margin-right: 12px;
	vertical-align: top;
}
.aui-slide-box .aui-slide-item-list .aui-slide-item-item .v-link {
	display: block;
}
.aui-slide-box .aui-slide-item-list .aui-slide-item-item .v-img {
	display: block;
	width: 6rem;
	height: 6rem;
	background-size: 62px;
}

.aui-slide-box .aui-slide-item-list .aui-slide-item-item .aui-slide-item-title
	{
	text-align: center;
	line-height: 1rem;
	word-break: break-word;
	height: 2rem;
	white-space: normal;
	margin: 6px 0 4px;
	font-size: 12px;
	color: #333;
}

.aui-slide-box .aui-slide-item-list .aui-slide-item-item .aui-slide-item-info
	{
	text-align: center;
	height: 0.373333rem;
	line-height: 0.373333rem;
	margin-top: 6px;
	font-size: 12px;
}
body, button, input, select, textarea {
font: 12px/150% Microsoft YaHei;
}
</style>
</head>
<body class="pg-deal pg-deal-default pg-deal-detail forIndex" id="deal-default" style="position: static;">
<jsp:include page="../../_header.jsp" flush="true" />
<div id="p-box">
  <div class="w1200" style="padding-top:10px;">
    <div class="product-intro">
      <div class="preview">
        <div class="gallery_wrap"> 
         <c:forEach items="${commInfo.commImgsList}" var="cur" begin="0" end="0"> 
          <a class="MagicZoomPlus jqzoom">
          <img id="_middleImage" src="${ctx}/${cur.file_path}@s400x400" longdesc="${ctx}/${cur.file_path}" alt="${fn:escapeXml(commInfo.comm_name)}" width="405" height="405" />
          </a> 
         </c:forEach>  
        </div>
        <div class="spec-list"> 
        <a href="javascript:void(0);" class="spec-prev prevStop"></a> 
        <a href="javascript:void(0);" class="spec-next nextStop"></a>
          <div class="spec-items">
            <ul id="mycarousel">
            <c:forEach items="${commInfo.commImgsList}" var="cur" varStatus="vs"> 
            <c:set var="aClass" value="" />
            <c:if test="${vs.count eq 1}">
            <c:set var="aClass" value="img-hover" />
            </c:if>
              <li><a class="${aClass} MagicThumb-swap">
              <img src="${ctx}/${cur.file_path}@s400x400" name="${ctx}/${cur.file_path}@s400x400" alt="${fn:escapeXml(commInfo.comm_name)}" width="60" height="60" /></a></li>
            </c:forEach>
            </ul>
          </div>
        </div>
        <div class="short-share">
          <div class="short-share-r">商品货号：${fn:escapeXml(commInfo.comm_no)}</div>
          <c:if test="${empty userInfo}"> 
          <c:url var="url" value="/IndexLogin.do" />
          <div class="collecting">
          <a onclick="alert('很抱歉！您还没有登录，请先登录！');location.href='${url}?returnUrl=' + escape(location.href);return true;" class="choose-btn-coll">
          <b></b>收藏<em id="collect_count">(<em>${sc_count}</em>)</em></a></div>
          </c:if>
          <c:if test="${not empty userInfo}"> 
          <c:url var="url" value="/IndexLogin.do" />
          <div class="collecting">
          <a href="javascript:void(0);" class="choose-btn-coll add_fav">
          <b></b>收藏<em id="collect_count">(<em id="J-fav-count">${sc_count}</em>)</em></a></div>
          </c:if>
          <jsp:include page="../../public/_index_share_item.jsp" flush="true" />
        </div>
      </div>
      <div class="m-item-inner">
        <div class="itemInfo">
            <div class="name">
              <h1>
<%--               <c:if test="${not empty commInfo.map.commZyName}"> --%>
<%-- 	           <span class="campaign">${fn:escapeXml(commInfo.map.commZyName)}</span> --%>
<%-- 	          </c:if> --%>
              ${fn:escapeXml(commInfo.comm_name)}</h1>
              <c:if test="${not empty commInfo.sub_title}">
               <h2>${fn:escapeXml(commInfo.sub_title)}</h2>
              </c:if>
            </div>
            <c:if test="${commInfo.is_rebate eq 1}">
            <div class="name">
              <fmt:formatNumber var="rebateScale" value="${commInfo.rebate_scale * commInfo.sale_price * reBate1001/10000}" pattern="#.##"/>
             <c:choose>
             <c:when test="${((not empty userInfo) and (userInfo.user_level eq 201)) or (empty userInfo)}">
              <h2 onclick="toUpLevel();">挑夫会员：立减${rebateScale}元</h2>
             </c:when>
             <c:otherwise>
              <h2>挑夫会员：立减${rebateScale}元</h2>
             </c:otherwise>
             </c:choose>
            </div>
            </c:if>
            <c:if test="${commInfo.is_zingying eq 1}">  
             <div class="aui-product-boutique clearfix">
			 <div style="margin-bottom:8px;">
			  <i class="aui-icon aui-icon-fahuo"></i>
			  <span class="aui-product-tag-text" style="float:none;">由京东发货并提供售后</span></div>
			  <div>
			   <i class="aui-icon aui-icon-jingdong"></i>
			  <span class="aui-product-tag-text">京东商品不支持七天无理由退货</span></div>
		     </div>
            </c:if>
            <div class="summary clearfix">
              <div class="summary-list">
                <div class="tr border-dashed-bottom">
                  <div class="th m-price">本店价</div>
                  <div class="th p-eva">评价</div>
                  <div class="th p-sales">销量</div>
                </div>
                <div class="tr">
                  <div class="td m-price summary-price">
                  <strong id="sale_price"><em>¥</em><fmt:formatNumber value="${commTczhPriceList[0].comm_price}" pattern="0.00" /></strong></div>
                  <div class="td p-eva"><a onclick="getCommtent();">${commInfo.comment_count}</a></div>
                  <div class="td p-sales">
                  <c:set var="saleCount" value="${commInfo.sale_count_update}"/>
		          <c:if test="${saleCount gt 10000}">
		             <fmt:formatNumber var="saleCount" value="${saleCount/10000}" pattern="#.##万"/>
		          </c:if>
	              ${saleCount}</div>
                </div>
              </div>
              
              <c:if test="${commInfo.is_rebate eq 1}">
               <div class="summary-stock">
                <div class="dt">代　　言：</div>
                <div class="dd" style="overflow:inherit">
                  <div class="store-selector" onclick="showRebate();">
                    <div class="text-select">
                      <div class="area_tit"><span>查询代言奖励</span><i class="sc-icon-right"></i></div>
                    </div>
                  </div>
                </div>
               </div>
              </c:if>
              
              <c:if test="${commInfo.is_aid eq 1}">
               <div class="summary-stock">
                <div class="dt">扶　　贫：</div>
                <div class="dd" style="overflow:inherit">
                  <div class="store-selector" onclick="showPoor();">
                    <div class="text-select">
                      <div class="area_tit"><span>${fn:length(commInfo.poorsList)}人</span><i class="sc-icon-right"></i></div>
                    </div>
                  </div>
                </div>
               </div>
              </c:if>
              <div class="summary-stock">
                <div class="dt">配　　送：</div>
                <div class="dd" style="overflow:inherit">
                  <div class="store-selector">
                    <div class="text-select" id="area_address">
                      <div class="area_tit"> <span id="cityChoice">选择区域</span><i class="sc-icon-right"></i> </div>
                    </div>
                  </div>
                  <div class="store-warehouse" id="showHasInvTip" style="display:none;">
                    <div id="isHas_warehouse_num" class="store-prompt"><strong>有货</strong>，下单后立即发货</div>
                  </div>
                </div>
              </div>
              <div class="summary-service">
                <div class="dt">运　　费：</div>
                <div class="dd"><span>[<em id="showyf">计算中...</em>]</span> </div>
              </div>
            </div>
            <div class="choose p-choose-wrap">
              <div class="choose-version attr-radio li" id="choose-type">
                <div class="dt">套　　餐：</div>
                <div class="dd">
                  <ul>
                   <c:forEach items="${commTczhPriceList}" var="cur" varStatus="vs_son">
                   <c:set var="liClass" value="" />
                   <c:if test="${vs_son.count eq 1}">
                    <c:set var="liClass" value="selected" />
                   </c:if>
                    <li class="item ${liClass}" data-sonId="${cur.id}" ><b></b> 
                    <a href="javascript:;" class="noimg">${cur.tczh_name}</a> 
                    </li>
                   </c:forEach>   
                  </ul>
                </div>
              </div>
              
              <div class="choose-num choose-xznum li">
                <div class="dt">数　　量：</div>
                <div class="dd"> <a class="btn-reduce" onclick="calcCartMoney($('#pd_count'), -1);"  href="javascript:;">-</a>
                  <input class="text buy-num" name="pd_count" id="pd_count" value="1" maxlength="9" onkeyup="calcCartMoney($('#pd_count'),null);" onblur="calcCartMoney($('#pd_count'),null,true);" />
                  <a class="btn-add" href="javascript:;" onclick="calcCartMoney($('#pd_count'),1);">+</a> 
                  <span>库存：<em id="pd_stock_span">${commInfo.inventory}</em>&nbsp;</span> 
                  <input type="hidden" name="pd_stock" id="pd_stock" value="${commInfo.inventory}"/>
                  <input type="hidden" id="freight_id" value="${commInfo.freight_id}"/>
                  <input type="hidden" id="comm_tczh_id" value="${commTczhPriceList[0].id}"/> 
                 </div>
              </div>
              <div class="choose-btns">
              <c:set var="datas" value="${fn:escapeXml(commInfo.pd_name)}#@${commInfo.pd_id}#@${fn:escapeXml(commInfo.cls_name)}#@${commInfo.cls_id}#@10#@${commInfo.own_entp_id}#@${commInfo.id}#@${commInfo.comm_name}#@${commInfo.comm_weight}#@${entpInfo.id}" />
               <c:if test="${empty userInfo}"> 
                 <c:url var="url" value="/IndexLogin.do" />
                 <a onclick="alert('很抱歉！您还没有登录，请先登录！');location.href='${url}?returnUrl=' + escape(location.href);return true;" class="buynow btn-buynow">立即购买</a> 
                 <a onclick="alert('很抱歉！您还没有登录，请先登录！');location.href='${url}?returnUrl=' + escape(location.href);return true;" class="btn-append"><i class="icon"></i>加入购物车</a> 
               </c:if>
               <c:if test="${not empty userInfo}"> 
                 <a onclick="addcartAndBuy('pd_${commInfo.id}');" class="buynow btn-buynow">立即购买</a> 
                 <a id="pd_${commInfo.id}" longdesc="${datas}" onclick="addcart('pd_${commInfo.id}')" class="btn-append"><i class="icon"></i>加入购物车</a> 
               </c:if>
               </div>
              <div class="choose-desc"> <c:if test="${(commInfo.show_notes eq 1) and not empty commInfo.comm_notes}"><p><i class="fa fa-bullhorn" style="font-size: 20px;"></i> ${commInfo.comm_notes}</p></c:if> </div>
            </div>
        </div>
      </div>
      <div class="seller-pop">
        <div class="seller-logo">
        <c:url var="entp_url" value="/entp/IndexEntpInfo.do?entp_id=${entpInfo.id}" />
        <a href="${entp_url}" target="_blank">
         <img src="${ctx}/${entpInfo.entp_logo}@s400x400" height="52" width="52"/></a> </div>
        <div class="seller-infor"><a href="${entp_url}" title="${fn:escapeXml(entpInfo.entp_name)}" target="_blank" class="name">${fn:escapeXml(entpInfo.entp_name)}</a><i class="icon arrow-show-more" id="entpMore"></i></div>
        <div id="entpDetails">
        <div class="seller-address">
          <div class="item"> <span class="label">联系电话：</span> <span class="text">${entpInfo.entp_tel}</span> </div>
          <div class="item"> <span class="label">所在地：</span> <span class="text">${entpInfo.map.full_name}</span> </div>
        </div>
        </div>
        <div class="seller-kefu"> 
        <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=${fn:escapeXml(entpInfo.qq)}&amp;site=qq&amp;menu=yes" class="seller-btn"><i class="icon"></i>在线客服</a> </div>
        <div class="pop-shop-enter"> 
        <a href="${entp_url}" class="btn-gray btn-shop-access">进店逛逛</a> 
        <c:if test="${empty userInfo}">
          <c:url var="url" value="/IndexLogin.do" />
          <a onclick="alert('很抱歉！您还没有登录，请先登录！');location.href='${url}?returnUrl=' + escape(location.href);return true;" class="btn-gray btn-shop-followey">关注店铺</a>
        </c:if>
        <c:if test="${not empty userInfo}">
          <a href="javascript:;" class="btn-gray btn-shop-followey add_entp_fav">关注店铺</a>
        </c:if>
        </div>
      </div>
    </div>
  </div>
</div>
<div class="w1200">
  <div class="ecsc-goods-layout">
    <div class="ecsc-layout-210">
      <div class="m service_list">
        <div class="mt">
          <h2>店内客服</h2>
        </div>
        <div class="mc">
          <ul>
            <li>
            <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin=${fn:escapeXml(entpInfo.qq)}&amp;site=qq&amp;menu=yes">
            <i class="icon icon_service_qq"></i><span>客服</span></a></li>
          </ul>
        </div>
      </div>
      <c:url var="url" value="IndexEntpInfo.do?method=getCommList&entp_id=${entpInfo.id}" />
      <html-el:form action="${url}">
        <div id="sp-search" class="m">
          <div class="mt">
            <h2>店内搜索</h2>
          </div>
          <div class="mc">
            <p class="sp-form-item1">
              <input type="text" name="comm_name_like" id="comm_name_like" placeholder="关键字" />
            </p>
            <p class="sp-form-item3"><i class="icon"></i>
              <input type="submit" value="搜索" id="btnShopSearch" />
            </p>
          </div>
        </div>
      </html-el:form>
      <div id="charts" class="m">
        <div class="mt">
          <h2>最新排行榜</h2>
        </div>
        <div class="mc">
          <ul class="charts-tab" id="charts-tab">
            <li class="first on">新品<i></i></li>
            <li>推荐<i></i></li>
            <li class="last">热销<i></i></li>
          </ul>
          <div class="charts-list" id="charts-tab-content_0">
			<div class="charts-item">
			    <ul>
			     <c:forEach var="cur" items="${commmInfoNewList}" varStatus="vs">
			     <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
			 	   <li>
			         <b class="sales-num sales-num-1">${vs.count}</b>
			         <div class="sales-product-img">
			         <a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
			         <img src="${ctx}/${cur.main_pic}@s400x400" width="52" height="52"/></a></div>
			         <div class="p-name"><a href="${url}" title="${fn:escapeXml(cur.comm_name)}">${fn:escapeXml(cur.comm_name)}</a></div>
			         <div class="p-price"><em>¥</em><fmt:formatNumber value="${cur.sale_price}" pattern="0.##" /></div>
			        </li>
			        </c:forEach>
			    </ul>
			</div>
         </div>
          <div class="charts-list" style="display: none;" id="charts-tab-content_1">
			<div class="charts-item">
			    <ul>
			     <c:forEach var="cur" items="${commmInfoTjList}" varStatus="vs">
			     <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
			 	   <li>
			         <b class="sales-num sales-num-1">${vs.count}</b>
			         <div class="sales-product-img">
			         <a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
			         <img src="${ctx}/${cur.main_pic}@s400x400" width="52" height="52"/></a></div>
			         <div class="p-name"><a href="${url}" title="${fn:escapeXml(cur.comm_name)}">${fn:escapeXml(cur.comm_name)}</a></div>
			         <div class="p-price"><em>¥</em><fmt:formatNumber value="${cur.sale_price}" pattern="0.##" /></div>
			        </li>
			        </c:forEach>
			    </ul>
			</div>
         </div>
          <div class="charts-list" style="display:none;" id="charts-tab-content_2">
			<div class="charts-item">
			    <ul>
			     <c:forEach var="cur" items="${commmInfoHotList}" varStatus="vs">
			     <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
			 	   <li>
			         <b class="sales-num sales-num-1">${vs.count}</b>
			         <div class="sales-product-img">
			         <a href="${url}" title="${fn:escapeXml(cur.comm_name)}">
			         <img src="${ctx}/${cur.main_pic}@s400x400" width="52" height="52"/></a></div>
			         <div class="p-name"><a href="${url}" title="${fn:escapeXml(cur.comm_name)}">${fn:escapeXml(cur.comm_name)}</a></div>
			         <div class="p-price"><em>¥</em><fmt:formatNumber value="${cur.sale_price}" pattern="0.##" /></div>
			        </li>
			        </c:forEach>
			    </ul>
			</div>
         </div>
        </div>
      </div>
      <c:if test="${not empty cookiesCommList}">
      <div class="history" id="history" style="display: block;">
        <div class="mt">
          <h2>最近浏览</h2></div>
        <div class="mc" id="history_list">
          <ul>
          <c:forEach var="cur" items="${cookiesCommList}">
            <li>
              <div class="item">
                <div class="p-img">
                <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
                <a href="${url}" title="${cur.comm_name}" target="_blank">
                <img src="${ctx}/${cur.main_pic}" width="72" height="72" /></a></div>
                <div style="width:90px; float:left;">
                  <div class="p-name"><a href="${url}" target="_blank">${cur.comm_name}</a></div>
                  <div class="p-price"> <em>¥</em><fmt:formatNumber pattern="0.00" value="${cur.sale_price}"/></div>
                </div>
              </div>
            </li>
            </c:forEach>
          </ul>
        </div>
      </div>
      </c:if>
    </div>
    <div class="ecsc-goods-main">
      <div class="ecsc-single-desc desc">
        <div class="goods-detail-title goods-detail-mt" id="J-content-navbar">
          <ul class="tabs" id="navUl">
            <li class="tab_item first"  id="navLi_1"><a class="current">商品详情</a></li>
            <li class="tab_item" id="navLi_0"><a>累计评论 <em class="J_ReviewsCount">${commInfo.comment_count}</em></a></li>
          </ul>
        </div>
        <div class="floors">
        
          <div class="ecsc-goods-item fment" id="anchor-detail_0">
            <dl class="goods-para">
              <dd class="column"><span>商品名称：${fn:escapeXml(commInfo.comm_name)}</span></dd>
              <dd class="column"><span>商品编号：${fn:escapeXml(commInfo.comm_no)}</span></dd>
              <dd class="column"><span>店铺：
              <a href="${entp_url}" title="${fn:escapeXml(entpInfo.entp_name)}" target="_blank">${fn:escapeXml(entpInfo.entp_name)}</a></span></dd>
              <dd class="column"><span>重量：${commInfo.comm_weight}千克</span></dd>
              <dd class="column"><span>上架时间：<fmt:formatDate value="${commInfo.up_date}" pattern="yyyy-MM-dd" /></span></dd>
            </dl>
            <div class="goods-detail-banner">${pdContentXxxx}</div>
          </div>
          
          <div class="ecsc-goods-item fment pinglun" id="anchor-detail_1" style="display:none;">
            <div class="goods-detail-title"><h2>商品评价(${commInfo.comment_count})</h2></div>
            <div class="overview-detail">
              <div class="rate"> <strong>${commentLevel1Rate}<span>%</span></strong> <span class="hp">好评率</span> </div>
              <div class="detailPercent">
                <dl>
                  <dt>好评<span>(${commentLevel1Rate}%)</span></dt>
                  <dd>
                    <div style="width: ${commentLevel1Rate}px;"></div>
                  </dd>
                </dl>
                <dl>
                  <dt>中评<span>(${commentLevel2Rate}%)</span></dt>
                  <dd>
                    <div style="width: ${commentLevel2Rate}px;"></div>
                  </dd>
                </dl>
                <dl>
                  <dt>差评<span>(${commentLevel3Rate}%)</span></dt>
                  <dd>
                    <div style="width: ${commentLevel3Rate}px;"></div>
                  </dd>
                </dl>
              </div>
            </div>
            <div class="comments-list">
              <div class="ratelist-content">
                <div>
                  <iframe width="100%" id="iframeComment" height="600px;" frameborder="0" src="${ctx}/IndexCommentInfo.do?link_id=${(commInfo.id)}" style="background-color:#fff;"></iframe>
                </div>
              </div>
            </div>
          </div>
          
        </div>
      </div>
    </div>
  </div>
</div>
<div id="addCartSuccess" style="display:none;">
  <div class="mt-dialog" style="border:none;">
    <div class="body">
      <div class="result-wrapper cf">
        <div class="result-box result-box--success pngfix" style="float: left;"> <i></i>
          <h5 style="margin-top:10px;">成功加入购物车</h5>
        </div>
        <div class="operate"> <a class="close btn btn-cart btn-cart--left" onclick="closeCartSuccessTip();">«继续浏览</a> <a href="${ctx}/cart.shtml" class="btn btn-hot btn-cart">去购物车结算»</a></div>
      </div>
    </div>
  </div>
</div>


<div class="mui-mbar-tabs">
 <div class="quick_links_wrap" style="width:40px;">
   <div class="quick_link_mian">
        <div class="quick_links_panel">
            <div id="quick_links" class="quick_links">
            	<ul id="quick_links_ul">
                    <li class="quick_links_li" data-mod-id="">
                        <a href="javascript:void(0);"><i class="setting"></i></a>
                        <div class="ibar_login_box status_login quickContent">
                            <div class="avatar_box">
                                <p class="avatar_imgbox">
                                <c:set var="user_name" value="暂无" />
                                <c:set var="user_level" value="暂无" />
                                <c:set var="imgSrc" value="${ctx}/styles/imagesPublic/user_header.png" />
                                <c:if test="${not empty userInfo and not empty userInfo.user_logo}">
                                <c:set var="imgSrc" value="${ctx}/${userInfo.user_logo}@s400x400" />
                                <c:set var="user_name" value="${userInfo.user_name}" />
                                <c:set var="user_level" value="${userInfo.map.user_level_name}" />
                                </c:if>
							    <c:if test="${fn:contains(userInfo.user_logo, 'http://')}">
							      <c:set var="imgSrc" value="${ctx}/${userInfo.user_logo}"/>
							    </c:if>
                                <img src="${imgSrc}" width="100" height="100" /></p>
                                <ul class="user_info">
                                    <li>用户名：${user_name}</li>
<%--                                     <li>等&nbsp;级：${user_level}</li> --%>
                                </ul>
                            </div>
                            <i class="icon_arrow_white"></i>
                        </div>
                    </li>
                    
                    <li id="shopCart" class="quick_links_li">
                        <c:url var="url" value="/IndexLogin.do" />
                        <c:if test="${not empty userInfo}">
			             <c:url var="url" value="/IndexShoppingCar.do" />
			            </c:if>
                        <a href="${url}" class="cart_list">
                            <i class="message"></i>
                            <div class="span">购物车</div>
                            <span class="cart_num" id="gwcCountComm">0</span>
                        </a>
                    </li>
                    <li class="quick_links_li" data-mod-id="1100500100">
                        <a href="javascript:void(0);" class="mpbtn_order"><i class="chongzhi"></i></a>
                        <div class="mp_tooltip quickContent">
                            <font id="mpbtn_money" style="font-size:12px; cursor:pointer;">我的订单</font>
                            <i class="icon_arrow_right_black"></i>
                        </div>
                    </li>
<!--                     <li class="quick_links_li" data-mod-id="1100400050"> -->
<!--                         <a href="javascript:void(0);" class="mpbtn_total"><i class="view"></i></a> -->
<!--                         <div class="mp_tooltip quickContent"> -->
<!--                             <font id="mpbtn_myMoney" style="font-size:12px; cursor:pointer;">我的钱包</font> -->
<!--                             <i class="icon_arrow_right_black"></i> -->
<!--                         </div> -->
<!--                     </li> -->
                    <li class="quick_links_li" data-mod-id="1100600200">
                        <a href="javascript:void(0);" class="mpbtn_collection"><i class="wdsc"></i></a>
                        <div class="mp_tooltip quickContent">
                            <font id="mpbtn_wdsc" style="font-size:12px; cursor:pointer;">我的账号</font>
                            <i class="icon_arrow_right_black"></i>
                        </div>
                    </li>
                </ul>
            </div>
            <div class="quick_toggle quick_links_allow_gotop">
            	<ul>
                    <li class="returnTop" onclick="goTop();">
                        <a href="javascript:void(0);" class="return_top"><i class="top"></i></a>
                    </li>
                </ul>
            </div>
        </div>
    <div id="quick_links_pop" class="quick_links_pop"></div>
    
    
  </div>
 </div>
</div>
<c:if test="${commInfo.is_rebate eq 1}">
<div id="s-poor-rebate-content" style="display: none;">
<div class="mui-cover" id="s-poor-rebate">
 <h4>代言奖励</h4>
 <div class="aui-product-boutique clearfix">
   <span>
    <i class="aui-icon aui-icon-fanxian"></i>
    <fmt:formatNumber var="rebateScale" value="${(commInfo.rebate_scale * commInfo.sale_price * reBate1002/10000)}" pattern="#.##"/>
    <span class="aui-product-tag-text">代言奖励：${rebateScale}元</span>
 </div>
 <div class="cover-content">代言奖励是指九个挑夫平台会员通过代言分享，让朋友也注册成为会员，当该会员在平台消费后，平台会给予代言人一定的分享奖励，该奖励可用于代言人在平台的消费抵扣，或提现到银行卡。</div>
</div>
</div>
</c:if>

<c:if test="${commInfo.is_aid eq 1}">
<div id="s-poor-wrapper-content" style="display: none;">
<div class="mui-cover" id="s-poor-wrapper">
 <h4>扶贫商品</h4>
 <div class="aui-product-boutique clearfix">
 <span>
    <i class="aui-icon aui-icon-fupin"></i>
    <fmt:formatNumber var="rebateScale" value="${(commInfo.aid_scale * commInfo.sale_price/100)}" pattern="#.##"/>
    <span class="aui-product-tag-text">本商品扶贫金：${rebateScale}元</span></span>
    </div>
 <div class="cover-content">扶贫金是指平台商户为贫困户设定的资助金，本商品在订单成交后，扶贫金会自动转入扶贫对象的账户，没有中间方。</div>
 <div class="aui-slide-box">
	<div class="aui-slide-list" style="height:auto;">
		<ul class="aui-slide-item-list" style="height:auto;">
		    <c:forEach var="cur" items="${commInfo.poorsList}" varStatus="vs">
			 <li class="aui-slide-item-item" style="width:30%;float:left;">
			    <c:url var="url" value="/VillageIndex.do?method=myIndex&member_id=${cur.map.user_id}&noRight=true" />
				<a class="v-link" href="${url}">
				    <c:set var="imgSrc" value="${ctx}/styles/images/user.png" />
				    <c:if test="${not empty cur.map.head_logo}">
				    <c:set var="imgSrc" value="${ctx}/${cur.map.head_logo}" />
				    </c:if>
					<img class="v-img" src="${imgSrc}" />
					<p class="aui-slide-item-title aui-slide-item-f-els">${cur.map.real_name}</p>
				</a>
			 </li>
			</c:forEach>
		</ul>
	</div>
  </div>
</div>
</div>
</c:if>

<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/zoom/zoom.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/zoom/jquery.jcarousel.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.nav.js"></script>
<script type="text/javascript" src="${ctx}/scripts/areaSelect/js/city-data.js"></script>
<script type="text/javascript" src="${ctx}/scripts/areaSelect/js/hzw-city-picker.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/cart/new_add2cart.js?v=20180526"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$(window).scroll(function(e){
		scrollDetail();		
	});
	
	<c:if test="${commInfo.inventory gt 0}">
		$("#showHasInvTip").show();
	</c:if>
	$("#fixbar-item-top").hide();
	
	var cityPicker = new HzwCityPicker({
        data: data,
        target: 'cityChoice',
        valType: 'k',
		hot_city: false,
        hideCityInput: {
            name: 'city',
            id: 'city'
        },
        callback: function(){
        	var p_index = $("#city").val();
        	doCalculateYF(p_index);
        }
    });

    cityPicker.init();
    
    $.post("${ctx}/CsAjax.do?method=getPIndexFromIp",{},function(data){
		$("#cityChoice").text(data.full_name);
		if(null != data.p_index && '' != data.p_index){
		  doCalculateYF(data.p_index);
		}
	});  
    
    
    $("#quick_links_ul .quick_links_li").each(function(index){
    	if(index == 0){
    		$(this).hover(function(){
   		        $(this).find(".quickContent").show();   
   		    },function(){
   		    	$(this).find(".quickContent").hide();   
   		    });
    	}else{
   			$(this).hover(function(){
   		        $(this).find(".quickContent").animate({"left":"-92px"},200).css("visibility","visible");   
   		    },function(){
   		    	$(this).find(".quickContent").animate({"left":"-121px"},200).css("visibility","hidden");   
   		    });
    	}
    	
    	if(index != 1){
    		$(this).click(function(){
    			var modId = $(this).attr("data-mod-id");
    		    $.post("${ctx}/CsAjax.do?method=getUrlLinkModId",{mod_id:modId},function(data){
    				if(data.ret == 1){
    					var parId_cookie = data.par_id + "," + data.data_url;
    					if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
    					location.href= "${ctx}/manager/customer/index.shtml";
    				}else{
    					location.href= "${ctx}/manager/customer/index.shtml";
    				}
    			});
    		});
    	}
    	
    });
	
	//setIFrameHeight("iframeComment");
	
	$("#entpMore").click(function(){
		$("#entpDetails").toggle("slow");
	});
	
	$("#charts-tab li").each(function(index){
		$(this).click(function(){
			$(this).addClass("on").siblings().removeClass("on");
			$("#charts-tab-content_" + index).show().siblings().hide();
			$("#charts-tab").show();
		});
	});
	
	$("#navUl li").each(function(index){
		$(this).click(function(){
			$(this).find("a").addClass("current");
			$("#navLi_" + index).find("a").removeClass("current");
			$("#anchor-detail_" + index).show().siblings().hide();
		});
	});
	
	$("#hoverParCls").hover(function(){
		if($(this).hasClass("hover")){
		  $(this).removeClass("hover");
		  $("#dorpdowncss").css("width","0px");
		}else{
		  $(this).addClass("hover");
		  $("#dorpdowncss").css("width",$(this).width() - 2);
		}
	});
	
	$("#mycarousel").jcarousel({initCallback:mycarousel_initCallback});
	$(".jqzoom").jqueryzoom();	
	
});

function goTop(){
    $('html,body').animate({'scrollTop':0},600);
}
function toUpLevel(){
	$.jBox.confirm("付费会员将缴费${upLevelNeedPayMoney}元,你确定要升级成为付费会员吗？", "${app_name}", submit2, { buttons: { '确定': true, '取消': false} });
}

var submit2 = function (v, h, f) {
	 if (v == true) {
		 var url = "${ctx}/IndexPayment.do?method=PayForUpLevel";
		 goUrl(url);
	 }
	 return true;
};

function getCommtent(){
	$(this).find("a")
	$("#navLi_0").find("a").addClass("current");;
	$("#navLi_1").find("a").removeClass("current");
	$("#anchor-detail_1").show().siblings().hide();
	$('body,html').animate({'scrollTop':710},400)
}

function showRebate() {
	var html =$("#s-poor-rebate-content").html();
	$.dialog({
		title:  "查询代言奖励",
		padding: 0,
		width:  400,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:html
	});
}

function showPoor() {
	var html =$("#s-poor-wrapper-content").html();
	$.dialog({
		title:  "查询扶贫信息",
		padding: 0,
		width:  400,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:html
	});
}

function scrollDetail(){
	var t = $(document).scrollTop();
	var navbarTop = $("#J-content-navbar").offset().top;
	if(t > 777){
		$("#J-content-navbar").addClass("common-fixed");
	}else{
		$("#J-content-navbar").removeClass("common-fixed");
	}
}

function setIFrameHeight(obj){
	$("#" + obj).load(function(){
		var mainheight = $("#" + obj).contents().find('body').height();
		$("#" + obj).height(mainheight);
	});
}

function mycarousel_initCallback(carousel){
	$("#mycarousel li").mouseover(function(){
		var JQ_img = $("img", this);
		var middle_img = JQ_img.attr("name");
		var large_img = middle_img.replace("_400", "");
		$("#_middleImage").attr("src", middle_img).attr("longdesc", large_img); 
		$(this).siblings().each(function(){
			$("a", this).removeClass("img-hover");
		})
		 $("a", this).addClass("img-hover");
	});
}	

function closeCartSuccessTip(){
	$.dialog({id:'cartSuccessTip'}).close();
}
$("#choose-type .item").click(function(){
	$(this).siblings().removeClass("selected");
	$(this).addClass("selected");
	setPrice(); 
});

function setPrice(){
	
	var attr_sonIds =[];
	$("#choose-type .item").each(function(){
		if($(this).hasClass("selected")){
			attr_sonIds.push($(this).attr("data-sonid"));
		}
	});
	$.ajax({
		type: "POST",
		url: "${ctx}/entp/CsAjax.do",
		data: "method=getTcPrice&attr_sonIds=" + attr_sonIds,
		dataType: "json",
		error: function(request, settings) {},
		success: function(data) {
			if(data.ret == 1){
				$("#sale_price").html("<em>¥</em>" + parseFloat(data.comm_price).toFixed(2));
				$("#pd_stock_span").text(data.inventory);
				$("#pd_stock").val(data.inventory);
				$("#comm_tczh_id").val(data.comm_tczh_id);
				if(data.inventory > 0){
					$("#showHasInvTip").show();
				}else{
					$("#showHasInvTip").hide();
				}
			}else{
				$.jBox.alert('参数有误，请联系管理员！', '提示');
			}
		}
	});
}

$(".add_fav").click(function(){
	$.ajax({
		type: "POST",
		url: "${ctx}/entp/CsAjax.do",
		data: "method=addFav&sc_type=2&link_id=${commInfo.id}",
		dataType: "json",
		error: function(request, settings) {},
		success: function(oper) {
			if(null != oper.result && oper.result != ''){
				var html;
				if(oper.result == 1){
					html="<span style='font-szie：14px;'>恭喜你，收藏成功！</span>";
					$("#J-fav-count").text(parseInt($("#J-fav-count").text()) + 1);
				}else{
					html="<span style='font-szie：14px;'>取消收藏成功！</span>";
					$("#J-fav-count").text(parseInt($("#J-fav-count").text()) - 1);
				}
				 $.jBox.tip(html, 'info',{timeout:2000});
			}else{
				alert("参数有误，请联系管理员！");
			}
		}
	});
});

$(".add_entp_fav").click(function(){
	$.ajax({
		type: "POST",
		url: "${ctx}/entp/CsAjax.do",
		data: "method=addFav&sc_type=1&link_user_id=${userInfo.id}&link_id=${entpInfo.id}",
		dataType: "json",
		error: function(request, settings) {},
		success: function(oper) {
			if(null != oper.result && oper.result != ''){
				var html;
				if(oper.result == 1){
					html="<span style='font-szie：14px;'>恭喜你，收藏店铺成功！</span>";
				}else{
					html="<span style='font-szie：14px;'>你已经收藏过该店铺！</span>";
				}
				 $.jBox.tip(html, 'info',{timeout:2000});
			}else{
				alert("参数有误，请联系管理员！");
			}
		}
	});
});

function doCalculateYF(p_index){
	var pd_count = $("#pd_count").val();
	$.ajax({
		type: "POST",
		url: "${ctx}/CsAjax.do",
		data: { "method":"ajaxCalculateFreightRatesNew", "p_index":p_index,"comm_id":"${commInfo.id}","pd_count":pd_count},
		dataType: "json",
		sync: true,
		success: function(data) {
			if(data.ret == 1){
				if(data.is_send == 0){
					$("#showyf").html(" 无法送达 ");
				}else{
					var msg = " 包邮 ";
					if(data.delivery_way1== 1 && data.is_delivery1== 1){
						msg = data.kd + "元";
					}
					$("#showyf").html(msg);
				}
			}else{
				$.jBox.tip(data.msg, "info");
			}
		}
	});
}

//]]></script>
</body>
</html>
