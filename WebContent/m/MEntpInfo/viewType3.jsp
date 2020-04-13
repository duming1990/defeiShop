<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${fn:escapeXml(commInfo.comm_name)}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" href="${ctx}/m/styles/css/my/comm-details.css?v=20161122"/>
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.carousel.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/m/scripts/owl-carousel/owl.theme.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<style type="text/css">
#hasSelectTc {
	width: 70%;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
}

#content {
	display: block;
	width: 97%;
	font-size: .3rem;
	padding: 5px;
}
#content img {
    width: 97%;
}
</style>
</head>
<body id="body">
<header id="s-header" class="nav">
	 <c:url var="url" value="/m/MEntpInfo.do?id=${commInfo.id}" />
    <a class="summary current" data-url="${url}">基本信息</a>
    <c:url var="url" value="/m/MEntpInfo.do?method=viewDetails&id=${commInfo.id}" />
    <a class="desc" data-url="${url}">商品详情</a>
    <c:url var="url" value="/m/MEntpInfo.do?method=getCommentList&comm_id=${commInfo.id}" />
    <a class="review" data-url="${url}">评价</a>
</header>
<div class="content" id="content1">
  <div class="d3" style="padding-top:2.8em;">
    <div class="section-detailbox">
      <section class="deal-pic">
        <div id="imgSlider">
          <c:forEach items="${commInfo.commImgsList}" var="cur">
            <div style="padding: 0px;"><img src="${ctx}/${cur.file_path}@s400x400" alt="${fn:escapeXml(commInfo.comm_name)}" /></div>
            <c:set var="img_url" value="${ctx}/${cur.file_path}@s400x400"/>
          </c:forEach>
        </div>
      </section>
    </div>
    
    <div class="section-detailbox">
      <section class="box deal-site">
       <section>
       <h4>${fn:escapeXml(commInfo.comm_name)}</h4>
       <div class="price_comm_detail"><fmt:formatNumber value="${commInfo.sale_price}" pattern="￥0.00" /></div>
       </section>
      </section>
    </div>
    
    <div class="section-detailbox">
      <section class="title">
        <c:url var="entp_url" value="/m/MEntpInfo.do?method=index&entp_id=${entpInfo.id}" />
        <h2 class="t"><a href="${entp_url}"><i class="t1"></i>商家信息</a> </h2>
      </section>
      <section class="box deal-site">
        <c:url var="url" value="/m/MEntpInfo.do?method=viewEntpAddr&entp_id=${entpInfo.id}&latlng=${entpInfo.entp_latlng}" />
        <a class="box-flex site-wz" href="${url}">
        <p class="t">${fn:escapeXml(entpInfo.entp_name)}</p>
        <p class="s">${entpInfo.map.full_name} ${entpInfo.entp_addr}</p>
        <p class="n" id="gotoDituP"> <span class="site" onClick="location.href='${url}'"></span><span class="nearest">商家位置</span></p>
        </a> <a href="tel:${entpInfo.entp_tel}" id="address_phone" class="box-flex site-phone"><i></i></a> </section>
      <section class="box deal-mark"> </section>
    </div>
    <div class="section-detailbox"> 
    <a class="box pjroom" onclick="showBuyTip(1);">当前选中套餐： <span id="hasSelectTc">${hasSelectTc}</span></a>
    </div>
    <c:if test="${(commInfo.show_notes eq 1) and not empty commInfo.comm_notes}">
      <div class="section-detailbox">
        <section class="title">
          <h2 class="t"><i class="t4"></i>重要通知</h2>
        </section>
        <section class="title">${commInfo.comm_notes}</section>
      </div>
    </c:if>
    <!-- 下拉刷新详情页面 -->
    <div id="content" style="background-color: #FFF;">
				<!-- 容器 -->

	</div>
	<a id="next"
		href="${ctx }/m/MEntpInfo.do?method=viewDetails&result=json&id=${commInfo.id}&page=12">上拉查看商品详情</a>
	<p></p>
	<p></p>
	<p></p>
  </div>
</div>
<div id="content2" style="display:none;padding-top:2.8em;"></div>
<div id="content3" style="display:none;padding-top:2.8em;"></div>

<section id="s-actionBar-container">
   <div id="s-actionbar" class="action-bar mui-flex align-center">
       
       <input type="hidden" id="comm_tczh_id" value="${comm_tczh_id}"/>
       <input type="hidden" id="freight_id" value="${commInfo.freight_id}"/>
       <input type="hidden" name="pd_stock" id="pd_stock" value="${commInfo.inventory}"/>
       <input type="hidden" name="addCartOrBuy" id="addCartOrBuy" value="1"/>
       <c:set var="kefu_url" value="http://wpa.qq.com/msgrd?v=3&uin=${entpInfo.qq}&site=qq&menu=yes"/>
       <c:if test="${isApp}">
       <c:set var="kefu_url" value="mqqwpa://imat?chat_type=wpa&uin=${entpInfo.qq}&version=1&src_type=web&web_src=oicqzone.com"/>
       </c:if>
       <div class="support cell" onclick="location.href='${kefu_url}';">
        <i class="fa fa-qq" style="color:#4A90E2;"></i>
       	<div>客服</div></div>
       	
       <c:url var="entp_url" value="/m/MEntpInfo.do?method=index&entp_id=${entpInfo.id}" />
       <div class="toshop cell"  onclick="goUrl('${entp_url}');">
        <i class="fa fa-home"></i>
        <div>进店</div></div>
        
        <c:if test="${not empty userInfo}">
         <div class="addfav cell j-mdv" onclick="addFav('${userInfo.id}','${commInfo.id}',this)">
          <c:set var="iStyle" value="" />
          <c:if test="${hasFavCount gt 0}">
          <c:set var="iStyle" value="color:#4a90e2;" />
          </c:if>
          <i class="fa fa-star" style="${iStyle}"></i>
       	 <div>收藏</div>
       	 </div>
        </c:if>
        <c:if test="${empty userInfo}">
         <c:url var="url" value="/m/MIndexLogin.do" />
         <div class="addfav cell j-mdv" onclick="toLogin('${url}');">
          <i class="fa fa-star"></i>
       	 <div>收藏</div></div>
        </c:if>
       <c:if test="${empty userInfo}">
        <c:url var="url" value="/m/MIndexLogin.do" />
        <button class="buy cell"  onclick="toLogin('${url}');">立即购买</button>
       </c:if>	
       	
       <c:if test="${not empty userInfo}">
        <c:set var="datas" value="${fn:escapeXml(commInfo.pd_name)}#@${commInfo.pd_id}#@${fn:escapeXml(commInfo.cls_name)}#@${commInfo.cls_id}#@10#@${commInfo.own_entp_id}#@${commInfo.id}#@${commInfo.comm_name}#@${commInfo.comm_weight}#@${entpInfo.id}" />
        <input type="hidden" id="pd_${commInfo.id}" longdesc="${datas}"/>
        <button class="buy cell" onclick="showBuyTip(1);">立即购买</button>
       </c:if>
   </div>
   
		<div class="Headerfixed" styles="height:40px;">
			<c:if test="${not empty userInfo}">
				<c:url var="url" value="/m/MMyCartInfo.do" />
				<a class="cart-link" title="购物车" onclick="goUrl('${url}');"><i
					class="fa fa-shopping-cart"></i></a>
			</c:if>
			<c:if test="${empty userInfo}">
				<a class="cart-link" title="购物车" onclick="toLogin('${url}');"><i
					class="fa fa-shopping-cart"></i></a>
			</c:if>
<%-- 			<a class="cart-link goBack" title="返回" href="${ctx}/m/index.do"><i class="fa fa-angle-left"></i></a> --%>
			     <c:choose>
			      <c:when test="${is_app}">
			       		<a class="cart-link goBack" title="返回" href="appreturn://;"><i class="fa fa-angle-left"></i></a>
			      </c:when>
			       <c:otherwise>
						<a class="cart-link goBack" title="返回" href="javascript:history.go(-1)"><i class="fa fa-angle-left"></i></a>
			      </c:otherwise>
			      </c:choose>			<c:if test="${not empty userInfo}">
				<div class="cart-link goShare" title="分享">
					<a class="-mob-share-open" styles="" title="分享"
						href="javascript:share('${userInfo.id}',${is_app},'${userInfo.user_name}')">
						<img src="${ctx}/m/styles/img/share.png" width="16" alt="">
					</a>
				</div>
			</c:if>
		</div>
  </section>
<div class="mui-cover" id="s-decision-wrapper">
 <div class="body">
 <section id="s-decision">
 <div class="address-control" style="display:none"></div> 
 <div class="sku-control">
 <ul class="mui-sku" id="choose-type">
 <c:forEach items="${baseAttributeList}" var="cur" varStatus="vs">
 <li class="J_SkuGroup mui-sku-group">
 <h2>${cur.attr_name}</h2>
 <div class="items">
 <c:forEach items="${cur.map.baseAttributeSonList}" var="cur_son" varStatus="vs_son">
    <c:set var="sel_class" value="" />
       <c:if test="${vs_son.count eq 1}">
       <c:set var="sel_class" value="checked" />
   </c:if>
   <label title="${cur_son.attr_name}" id="item" class="${sel_class}" data-sonAttr="&nbsp;${cur_son.attr_name}" data-sonId="${cur_son.id}">${cur_son.attr_name}</label>
 </c:forEach>
 </div>
 </li>
 </c:forEach>
 </ul>
 </div> 
 <div class="number">
 <h2>数量</h2>
 <div class="content">
 <div class="number-control">
 <div class="mui-number">
 <button type="button" class="decrease" onclick="calcCartMoney($('#pd_count'), -1);">-</button>
 <input type="number" class="num" value="1" min="1" max="2" name="pd_count" id="pd_count" onkeyup="calcCartMoney($('#pd_count'),null);" onblur="calcCartMoney($('#pd_count'),null,true);"/>
 <button type="button" class="increase" onclick="calcCartMoney($('#pd_count'),1);">+</button>
 </div>
 </div>
 </div>
 </div>
 </section>
 </div>
 <div class="summary">         
 <div class="img">             
 <img src="${ctx}/${commInfo.main_pic}@s400x400" width="100" height="100"/>         
 </div>         
 <div class="main">            
 <div class="priceContainer">
 <span class="price" id="sale_price">
 <fmt:formatNumber value="${commInfo.sale_price}" pattern="0.00" /></span> </div>            
 <div class="stock-control"><span class="stock"><label class="label">库存</label><span id="curr_stock">${commInfo.inventory}</span>件</span></div>  
 <div class="sku-dtips">已选择:<span id="hasSelectTc2">${hasSelectTc}</span></div>           
 </div>
 <a class="sback" onclick="hideWrapper();"><i class="fa fa-times-circle-o"></i></a></div>
 <div class="option mui-flex">
  <c:if test="${empty userInfo}">
   <button class="ok cell" onclick="toLogin('${url}');">确定</button> 
 </c:if>
 <c:if test="${not empty userInfo}">
   <button class="ok cell" onclick="addCartOrBuy('pd_${commInfo.id}',true);">确定</button> 
 </c:if>
 </div>
 </div>
 
<div class="cover-decision" id="s-decision-wrapper-cover" style="display:none;"></div>
<script type="text/javascript" src="${ctx}/scripts/cart/mCartCommInfo.js"></script>
	<script type="text/javascript" src="${ctx}/scripts/jquery.infinitescroll.js"></script>
	<script type="text/javascript" src="${ctx}/m/scripts/owl-carousel/owl.carousel.min.js"></script>
	<script>
		var wxData = {
			imgUrl : 'http://${app_domain}/${img_url}',
			link : 'http://${app_domain}/m/MEntpInfo.do?id=${commInfo.id}',
			desc : '${fn:escapeXml(commInfo.comm_name)}',
			title : "${fn:escapeXml(commInfo.comm_name)}"
		};

		function share(user_id, is_app, user_name) {
			if (is_app) {
				window.location.href = "appshare://" + "${share_string}";
			} else {
				mobShare.config({
					params : {
						url : wxData.link, // 分享链接
						title : wxData.title, // 分享标题
						description : wxData.desc, // 分享内容
						pic : wxData.imgUrl, // 分享图片，使用逗号,隔开
						reason : wxData.desc,//只应用与QZone与朋友网
					}
				});
			}
		}
		$('#content').infinitescroll(
						{
							//callback		: function () { alert("A");console.log('using opts.callback'); },
							navSelector : "a#next",
							nextSelector : "a#next",
							itemSelector : "#content",
							debug : false,
							dataType : 'json',
							loading : {
								finished : undefined,
								finishedMsg : "<em>刷新成功</em>",
								img : 'data:image/gif;base64,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',
								msg : null,
								msgText : '<em>上拉查看商品详情</em>',
								selector : null,
								speed : 'fast',
								start : undefined
							},
							// behavior		: 'twitter',
							appendCallback : false, // USE FOR PREPENDING
						}, function(response) {
							var jsonData = response.content;
							$theCntr = $("#content");
							$theCntr.append(jsonData);
							$('#content').infinitescroll('unbind');

						});
	</script>
	<c:if test="${not is_app}">
		<jsp:include page="../_share_wap.jsp" flush="true" />
	</c:if>


</body>
</html>
