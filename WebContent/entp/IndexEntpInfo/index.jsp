<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:set var="seo_title_entp" value="${fn:escapeXml(entpInfo.entp_name)}" />
<c:set var="seo_keyword" value="${fn:escapeXml(entpInfo.entp_name)}" />
<c:set var="seo_desc" value="${fn:escapeXml(fnx:abbreviate(entpInfo.entp_desc, 2 * 100, ''))}" />
<meta name="description" content="${seo_desc}" />
<meta name="keywords" content="${seo_keyword}" />
<title>${seo_title_entp} - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/shop-info.css"  />
</head>
<body class="pg-poi pg-poi-new" id="J-biz" style="position: static;">
<!--head start -->
<jsp:include page="../../_header.jsp" flush="true" />

<div class="bdw">
 <div id="bd" class="cf">
  <!----商家头部 开始----->
  <div class="summary biz-box fs-section cf">
    <div class="fs-section__left">
        <h2> 
            <span class="title">${fn:escapeXml(entpInfo.entp_name)}</span>
        </h2>
        <p class="under-title">
            <span class="geo">${fn:escapeXml(entpInfo.entp_addr)}</span>
            <i class="view-map J-view-full F-glob F-glob-map" onclick="viewMap('${entpInfo.entp_latlng}');"></i>
        </p>
        <p class="under-title">${fn:escapeXml(entpInfo.entp_tel)}</p>
    </div>
    <div class="fs-section__right">
        <div class="info">
            <div style="padding-top:10px;"> 
                <span class="biz-level" style="vertical-align:top; line-height:18px;"><strong>4.2</strong>分</span>
                <span class="common-rating common-rating-16x16--biz"> <span class="rate-stars" style="width:84%"></span></span>
            </div>
            <div>${fn:escapeXml(entpInfo.main_pd_class_names)}
            </div>
        </div>
        <div class="counts">
            <div>消费人数 <span class="num">${entpInfoSaleCount}</span></div>
            <div>评价人数 <span class="num rate-count" title="评价人数" style="text-decoration:none;">${entpInfo.comment_count}</span></div>
            <div class="shop-identity-wrapper">
                <span>商家资质</span>
                <a href="javascript:openEntpLicenceImg('${entpInfo.entp_licence_img}');" class="shop-identity-link">
                    <i class="J-shop-license shop-identity"></i>
                </a>
            </div> 
        </div>
    </div>
    <i class="sp-poi-bookmark"></i><i class="sp-bgbars"></i>
</div>
 <!----商家头部 结束----->
   <!----商家所有服务开始---->
   <div id="anchor-salelist" class="poi-section poi-section--onsale log-mod-viewed">
    <h3 class="content-title">服务消费</h3>
    <ul class="onsale-list cf">
    	<c:forEach var="cur" items="${commmInfoList}">
        <li class="item cf ">
        <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
         <a target="_blank" href="${url}" title="${fn:escapeXml(cur.comm_name)}" class="item__title">
              <img  src="${ctx}/${cur.main_pic}@s400x400" alt="${fn:escapeXml(cur.comm_name)}" width="117" height="70" />
                <span class="title">${fn:escapeXml(cur.comm_name)}</span>
<!--                 <span class="type-mark"> -->
<!--                     <span> -->
<%-- 		            <c:forEach var="curSon" items="${baseData700List}"> --%>
<%-- 		              <c:if test="${curSon.id eq cur.fanxian_rule}">${curSon.type_name}</c:if> --%>
<%-- 		            </c:forEach> --%>
<!--                     </span> -->
<!--                 </span> -->
                <span class="sale">已售${cur.sale_count_update}</span>
            </a>
            <span class="item__duedate">
             
            </span>
            <span class="item__usetime">
                ${fn:escapeXml(cur.sub_title)}
            </span>
            <span class="item__price">
                <span><em class="price">¥<strong><fmt:formatNumber value="${cur.sale_price}" pattern="0.##" /></strong></em></span>
            </span>
            <a class="btn btn-small item__btn" href="${url}" target="_blank">立即抢购</a>
        </li>
        </c:forEach>
       <c:if test="${(not empty commmInfoList)}">
        <li class="more-trigger J-more-trigger">
            <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommList&entp_id=${entpInfo.id}" />
            <a class="collapse--normal collapse--collapsed collapse--large" href="${url}">查看店铺所有商品</a>
        </li>
        </c:if>
    </ul>
</div>

<c:if test="${not empty entpPicList}">
<div id="anchor-salelist" class="poi-section poi-section--onsale log-mod-viewed">
    <h3 class="content-title" style="padding-bottom:0px;">店铺相册 
    <c:url var="url" value="/entp/IndexEntpInfo.do?method=getEntPic&entp_id=${entpInfo.id}" />
    <a style="float:right;font-size:12px;" href="${url}">查看所有>></a></h3>
    <ul class="onsale-list cf entpPic">
      <c:forEach var="cur" items="${entpPicList}">
        <li>
          <a title="${cur.file_name}">
          <img src="${ctx}/${cur.save_path}@s400x400" width="190" height="190"/></a>
       </li>
      </c:forEach>
    </ul>
</div>
</c:if>

  <!----商家所有服务结束----->
  <!---------商家信息本体内容开始------------>
  <div id="content">
    <div class="poi-section poi-section--shop">
        <h2 class="content-title">商家详情</h2>
        <div class="cf">
      <a href="javascript:void(0)" class="biz-picture">
        <span class="img-wrapper"><img src="${ctx}/${entpInfo.entp_logo}@s400x400" width="250" height="150" /></span>
<!--         <span class="view-all">查看全部图片</span> -->
    </a>
    <div>
        <!-- info field -->   
        <c:if test="${not empty entpInfo.yy_sj_between}">
        <div class="field-group">
            <span class="field-title">营业时间：</span>${fn:escapeXml(entpInfo.yy_sj_between)}
        </div>
        </c:if>
        <div class="field-group">
            <span class="field-title">企业简介：</span>
            <span class="inline-item">${fn:escapeXml(entpInfo.entp_desc)}</span>
        </div>
    </div>
</div>

    </div>
    <div class="poi-section poi-section--shop">
    	${af.map.entp_content}
    </div>
</div>
  <!---------商家信息本体内容结束------------>
 </div>
</div>

<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<%-- <jsp:include page="../_public_kefu_new.jsp" flush="true" /> --%>
<script type="text/javascript">//<![CDATA[
 $(document).ready(function(){
});
 
 function viewMap(latlng){
		var url = "${ctx}/CsAjax.do?method=viewBMap&latlng=" + latlng;
		$.dialog({
			title:  "查看坐标",
			width:  900,
			height: 520,
			padding: 0,
			max: false,
	        min: false,
	        fixed: true,
	        lock: true,
			content:"url:"+ encodeURI(url)
		});
	} 
 function openEntpLicenceImg(path){
	 if('' != path){
		 window.open("${ctx}/" + path); 
	 }
	    
	} 
//]]></script>
</body>
</html>
