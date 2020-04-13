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
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/business-info-new.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/quickLinks.css"  />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/scripts/zoom/css/zoom.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
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
              <h1>${fn:escapeXml(commInfo.comm_name)}</h1>
              <h2>${fn:escapeXml(commInfo.sub_title)}</h2>
            </div>
            <div class="summary clearfix">
              <div class="summary-list">
                <div class="tr border-dashed-bottom">
                  <div class="th m-price">本店价</div>
<!--                   <div class="th s-price">返现</div> -->
                  <div class="th p-eva">评价</div>
                  <div class="th p-sales">销量</div>
                </div>
                <div class="tr">
                  <div class="td m-price summary-price">
                  <strong id="sale_price"><em>¥</em><fmt:formatNumber value="${commInfo.sale_price}" pattern="0.00" /></strong></div>
<!--                   <div class="td s-price"> -->
<%--                   <c:forEach var="curSon" items="${baseData700List}"> --%>
<%--                     <c:if test="${curSon.id eq commInfo.fanxian_rule}">${curSon.type_name}</c:if> --%>
<%--                   </c:forEach> --%>
<!--                   </div> -->
                  <div class="td p-eva"><a onclick="getCommtent();">${commInfo.comment_count}</a></div>
                  <div class="td p-sales">
                  <c:set var="saleCount" value="${commInfo.sale_count_update}"/>
		          <c:if test="${saleCount gt 10000}">
		             <fmt:formatNumber var="saleCount" value="${saleCount/10000}" pattern="#.##万"/>
		          </c:if>
	              ${saleCount}</div>
                </div>
              </div>
            </div>
            <div class="choose p-choose-wrap">
<%--               <c:if test="${(not empty commInfo.brand_id) and (not empty baseBrandInfo)}"> --%>
<!-- 	              <div class="summary-service"> -->
<!-- 	                <div class="dt">商品品牌：</div> -->
<%-- 	                <div class="dd"><a style="margin-left:0px;">${baseBrandInfo.brand_name}</a> </div> --%>
<!-- 	              </div> -->
<%--               </c:if> --%>
              <c:forEach items="${baseAttributeList}" var="cur" varStatus="vs"> 
              <div class="choose-version attr-radio li" id="choose-type">
                <div class="dt">${cur.attr_name}：</div>
                <div class="dd">
                  <ul>
                   <c:forEach items="${cur.map.baseAttributeSonList}" var="cur_son" varStatus="vs_son">
                   <c:set var="liClass" value="" />
                   <c:if test="${vs_son.count eq 1}">
                    <c:set var="liClass" value="selected" />
                   </c:if>
                    <li class="item ${liClass}" data-sonId="${cur_son.id}" ><b></b> 
                    <a href="javascript:;" class="noimg">${cur_son.attr_name}</a> 
                    </li>
                   </c:forEach>   
                  </ul>
                </div>
              </div>
              </c:forEach>
              
              <div class="choose-num choose-xznum li">
                <div class="dt">数　　量：</div>
                <div class="dd"> <a class="btn-reduce" onclick="calcCartMoney($('#pd_count'), -1);"  href="javascript:;">-</a>
                  <input class="text buy-num" name="pd_count" id="pd_count" value="1" maxlength="9" onkeyup="calcCartMoney($('#pd_count'),null);" onblur="calcCartMoney($('#pd_count'),null,true);" />
                  <a class="btn-add" href="javascript:;" onclick="calcCartMoney($('#pd_count'),1);">+</a> 
                  <span>库存：<em id="pd_stock_span">${commInfo.inventory}</em>&nbsp;</span> 
                  <input type="hidden" name="pd_stock" id="pd_stock" value="${commInfo.inventory}"/>
                  <input type="hidden" id="freight_id" value="${commInfo.freight_id}"/>
                  <input type="hidden" id="comm_tczh_id" value="${comm_tczh_id}"/> 
                 </div>
              </div>
              <div class="choose-btns">
              <c:set var="datas" value="${fn:escapeXml(commInfo.pd_name)}#@${commInfo.pd_id}#@${fn:escapeXml(commInfo.cls_name)}#@${commInfo.cls_id}#@10#@${commInfo.own_entp_id}#@${commInfo.id}#@${commInfo.comm_name}#@${commInfo.comm_weight}#@${entpInfo.id}" />
               <c:if test="${empty userInfo}"> 
                 <c:url var="url" value="/IndexLogin.do" />
                 <a onclick="alert('很抱歉！您还没有登录，请先登录！');location.href='${url}?returnUrl=' + escape(location.href);return true;" class="buynow btn-buynow">立即购买</a> 
               </c:if>
               <c:if test="${not empty userInfo}"> 
                 <a onclick="addcartAndBuy('${commInfo.id}');" class="buynow btn-buynow">立即购买</a> 
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
         <img src="${ctx}/${entpInfo.entp_logo}@s400x400" height="45" /></a> </div>
        <div class="seller-infor"><a href="${entp_url}" title="${fn:escapeXml(entpInfo.entp_name)}" target="_blank" class="name">${fn:escapeXml(entpInfo.entp_name)}</a><i class="icon arrow-show-more" id="entpMore"></i></div>
        <dl class="seller-zf">
          <dt>店铺总分：</dt>
          <dd> <span class="heart-white"> <span class="heart-red h10" style="width:100%;" title="综合好评(100%)"> </span> </span> <em class="evaluate-grade"><strong title="10"><a>5.00</a></strong>分</em> </dd>
        </dl>
        <div id="entpDetails">
        <div class="seller-pop-box">
          <dl class="pop-score-detail">
            <dt class="score-title"> <span class="col1">评分明细</span> <span class="col2">与行业相比</span> </dt>
            <dd class="score-infor">
              <div class="score-part"> <span class="score-desc">商品<em title="5" class="number">5</em></span> <span class="score-change"><em class="score-percent">100%</em></span> <span class="score-trend"><i class="sprite-up"></i></span> </div>
              <div class="score-part"> <span class="score-desc">服务<em title="5" class="number">5</em></span> <span class="score-change"><em class="score-percent">100%</em></span> <span class="score-trend"><i class="sprite-up"></i></span> </div>
              <div class="score-part"> <span class="score-desc">时效<em title="5" class="number">5</em></span> <span class="score-change"><em class="score-percent">100%</em></span> <span class="score-trend"><i class="sprite-up"></i></span> </div>
            </dd>
          </dl>
        </div>
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
            <li class="tab_item first" id="navLi_1"><a class="current">商品详情</a></li>
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
              <dd class="column"><span>上架时间：<fmt:formatDate value="${commInfo.up_date}" pattern="yyyy-MM-dd" /></span></dd>
            </dl>
            <div class="goods-detail-banner">${pdContentXxxx}</div>
          </div>
          
          <div class="ecsc-goods-item fment pinglun" id="anchor-detail_1" style="display:none;">
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
                  <iframe width="100%" id="iframeComment" height="1" frameborder="0" src="${ctx}/IndexCommentInfo.do?link_id=${(commInfo.id)}" style="background-color:#fff;"></iframe>
                </div>
              </div>
            </div>
          </div>
          
        </div>
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

<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/zoom/zoom.min.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/zoom/jquery.jcarousel.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/cart/new_add2cart_type3.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.nav.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$(window).scroll(function(e){
		scrollDetail();		
	});
	
	<c:if test="${commInfo.inventory gt 0}">
		$("#showHasInvTip").show();
	</c:if>
	$("#fixbar-item-top").hide();
	
    
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
    	
    });
	
// 	$("#navUl").onePageNav({
// 		    currentClass: 'current',
// 	});
	setIFrameHeight("iframeComment");
	
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

function getCommtent(){
	$(this).find("a")
	$("#navLi_0").find("a").addClass("current");;
	$("#navLi_1").find("a").removeClass("current");
	$("#anchor-detail_1").show().siblings().hide();
	$('body,html').animate({'scrollTop':710},400)
}


function goTop(){
    $('html,body').animate({'scrollTop':0},600);
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

//]]></script>
</body>
</html>
