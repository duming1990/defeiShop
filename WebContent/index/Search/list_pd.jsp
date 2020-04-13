<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>搜索商品列表 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/filter-deallist.css?20180613"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/pages.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/customer.css"  />
<style>
.forIndex .mall .deal-tile .J-webp {
    width: 190px;
}
</style>
</head>
<body class="pg-index forIndex" style="position: static;">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="bdw">
 <div id="bd" class="cf" style="top: 0px;">
<!-------列表顶部工具条开始--------->
<div class="component-filter-section mt-component--booted">
<c:url var="url" value="Search.do" />
 <html-el:form action="${url}" styleClass="attrForm">
 <html-el:hidden property="p_index" styleId="p_index"/>
 <html-el:hidden property="root_cls_id" styleId="root_cls_id"/>
 <html-el:hidden property="par_cls_id" styleId="par_cls_id"/>
 <html-el:hidden property="son_cls_id" styleId="son_cls_id"/>
 <html-el:hidden property="keyword" styleId="keyword"/>
 <html-el:hidden property="orderByParam" styleId="orderByParam"/>
<div id="filter">

    <div class="filter-sortbar-outer-box filter-main--attrs">
     <c:if test="${(not empty af.map.root_cls_id) or (not empty af.map.par_cls_id) or (not empty af.map.p_index)}"> 
      <div class="filter-breadcrumb">
          <span class="breadcrumb__item">
          <a class="filter-tag filter-tag--all" onclick="clickGetAll();">全部</a>
         </span>
        <c:if test="${not empty baseRootClassList and (not empty af.map.root_cls_id)}"> 
        <span class="breadcrumb__crumb"></span>
        <span class="breadcrumb__item">
            <span class="J-uix-dropdown-trigger breadcrumb_item__title filter-tag">${root_cls_name}<i class="tri"></i>
            </span><a onclick="clickedRootId('')" class="F-glob F-glob-close breadcrumb-item--delete"></a>
            <span class="J-uix-dropdown-trigger breadcrumb_item__option">
                <span class="option-list--wrap inline-block">
                    <span class="option-list--inner inline-block">
                                <a onclick="clickedRootId('')">全部</a>
                                <c:forEach var="cur" items="${baseRootClassList}" varStatus="vs">
                                <c:set var="aClass" value="" />
                                <c:if var="flag_rootId" test="${af.map.root_cls_name eq cur.cls_id}">
                                <c:set var="aClass" value="current" />
                                </c:if>
                                <a class="${aClass}" onclick="clickedRootId('${cur.cls_id}')">${cur.cls_name}</a>
                               </c:forEach>
                    </span>
                </span>
            </span>
        </span>
        </c:if>
        
        <c:if test="${not empty baseParClassList and (not empty af.map.par_cls_id)}"> 
        <span class="breadcrumb__crumb"></span>
        <span class="breadcrumb__item">
            <span class="J-uix-dropdown-trigger breadcrumb_item__title filter-tag">${par_cls_name}<i class="tri"></i>
            </span><a onclick="clickedParId('')" class="F-glob F-glob-close breadcrumb-item--delete"></a>
            <span class="J-uix-dropdown-trigger breadcrumb_item__option">
                <span class="option-list--wrap inline-block">
                    <span class="option-list--inner inline-block">
                                <a onclick="clickedParId('')">全部</a>
                                <c:forEach var="cur" items="${baseParClassList}" varStatus="vs">
                                <c:set var="aClass" value="" />
                                <c:if var="flag_parId" test="${af.map.par_cls_id eq cur.cls_id}">
                                <c:set var="aClass" value="current" />
                                </c:if>
                                <a class="${aClass}" onclick="clickedParId('${cur.cls_id}')">${cur.cls_name}</a>
                               </c:forEach>
                    </span>
                </span>
            </span>
        </span>
        </c:if>
        
       <c:if test="${not empty baseSonClassList and (not empty af.map.son_cls_id)}"> 
        <span class="breadcrumb__crumb"></span>
        <span class="breadcrumb__item">
            <span class="J-uix-dropdown-trigger breadcrumb_item__title filter-tag">${son_cls_name}<i class="tri"></i>
            </span><a onclick="clickedSonId('')" class="F-glob F-glob-close breadcrumb-item--delete"></a>
            <span class="J-uix-dropdown-trigger breadcrumb_item__option">
                <span class="option-list--wrap inline-block">
                    <span class="option-list--inner inline-block">
                                <a onclick="clickedSonId('')">全部</a>
                                <c:forEach var="cur" items="${baseSonClassList}" varStatus="vs">
                                <c:set var="aClass" value="" />
                                <c:if test="${af.map.son_cls_id eq cur.cls_id}">
                                <c:set var="aClass" value="current" />
                                </c:if>
                                <a class="${aClass}" onclick="clickedSonId('${cur.cls_id}')">${cur.cls_name}</a>
                               </c:forEach>
                    </span>
                </span>
            </span>
        </span>
      </c:if>
      
      <c:if test="${not empty sonBaseProList and (not empty af.map.p_index)}"> 
        <span class="breadcrumb__crumb"></span>
        <span class="breadcrumb__item">
            <span class="J-uix-dropdown-trigger breadcrumb_item__title filter-tag">区域:${p_name}<i class="tri"></i>
            </span><a onclick="clickedAear('')" class="F-glob F-glob-close breadcrumb-item--delete"></a>
            <span class="J-uix-dropdown-trigger breadcrumb_item__option">
                <span class="option-list--wrap inline-block">
                    <span class="option-list--inner inline-block">
                                <a onclick="clickedAear('')">全部</a>
                                <c:forEach var="cur" items="${sonBaseProList}" varStatus="vs">
                                <c:set var="aClass" value="" />
                                <c:if test="${af.map.p_index eq cur.p_index}">
                                <c:set var="aClass" value="current" />
                                </c:if>
                                <a class="${aClass}" onclick="clickedAear('${cur.p_index}')">${cur.p_name}</a>
                               </c:forEach>
                    </span>
                </span>
            </span>
        </span>
      </c:if>
	 </div>
    </c:if>
    
    
     <div class="filter-section-wrapper">
     <c:if test="${(empty af.map.root_cls_id)}">
     <div class="component-filter-geo mt-component--booted first-filter">    
        <div class="filter-label-list filter-section geo-filter-wrapper first-filter">
          <div class="label has-icon">分类：</div>
           <ul class="inline-block-list J-filter-list filter-list--fold">
            	<c:forEach var="cur" items="${baseRootClassList}" varStatus="vs">
            	<c:if var="flag_rootId" test="${af.map.root_cls_id eq cur.cls_id}">
                	<li class="item current"><a href="javascript:void(0);" onclick="clickedRootId('${cur.cls_id}')">${cur.cls_name}</a></li>
                </c:if>
            	<c:if test="${!flag_rootId}">
                	<li class="item "><a href="javascript:void(0);" onclick="clickedRootId('${cur.cls_id}')">${cur.cls_name}</a></li>
                </c:if>
                </c:forEach>
            </ul>
        </div>
	 </div>
	</c:if>
	
     <c:if test="${not empty baseParClassList and (empty af.map.par_cls_id)}">
     <div class="component-filter-geo mt-component--booted first-filter">    
        <div class="filter-label-list filter-section geo-filter-wrapper first-filter">
          <div class="label has-icon">分类：</div>
           <ul class="inline-block-list J-filter-list filter-list--fold">
            	<c:forEach var="cur" items="${baseParClassList}" varStatus="vs">
            	<c:if var="flag_parId" test="${af.map.par_cls_id eq cur.cls_id}">
                	<li class="item current"><a href="javascript:void(0);" onclick="clickedParId('${cur.cls_id}')">${cur.cls_name}</a></li>
                </c:if>
            	<c:if test="${!flag_parId}">
                	<li class="item "><a href="javascript:void(0);" onclick="clickedParId('${cur.cls_id}')">${cur.cls_name}</a></li>
                </c:if>
                </c:forEach>
            </ul>
        </div>
	 </div>
	</c:if>
	
	<c:if test="${not empty baseSonClassList and (empty af.map.son_cls_id)}">
     <div class="component-filter-geo mt-component--booted first-filter">    
      <div class="filter-label-list filter-section geo-filter-wrapper first-filter">
      	  <div class="label has-icon">分类：</div>
	            <ul class="inline-block-list">
	            <c:if var="flag_son_cls_id_empty" test="${empty af.map.son_cls_id}">
		         <li class="item current"><a href="javascript:void(0);" onclick="clickedSonId('')">全部</a></li>
			    </c:if>
			    <c:if test="${!flag_son_cls_id_empty}">
			    <li class="item"><a href="javascript:void(0);" onclick="clickedSonId('')">全部</a></li>
			    </c:if>
		        <c:forEach var="cur" items="${baseSonClassList}">
		        <c:if var="flag_son_cls_id" test="${af.map.son_cls_id eq cur.cls_id}">
		        	<li class="item current">
		        	<a href="javascript:void(0);" onclick="clickedSonId('${cur.cls_id}')">${fn:escapeXml(cur.cls_name)}</a></li></c:if>
		       	<c:if test="${!flag_son_cls_id}">
		        	<li class="item">
		        	<a href="javascript:void(0);" onclick="clickedSonId('${cur.cls_id}')">${fn:escapeXml(cur.cls_name)}</a></li></c:if>
		        </c:forEach>
	            </ul>
            </div>
	</div>
	</c:if>
	
	<c:if test="${empty af.map.p_index}">
     <div class="component-filter-geo mt-component--booted">    
        <div class="filter-label-list filter-section geo-filter-wrapper first-filter">
        <div class="label has-icon">区域：</div>
        <a class="J-filter-more filter-opt--more" href="javascript:;"></a>
        <ul class="inline-block-list J-filter-list filter-list--fold">
            <c:if var="flag_area_empty" test="${empty af.map.p_index}">
	         <li class="item current"><a href="javascript:void(0);" onclick="clickedAear('')">全部</a></li>
		    </c:if>
		    <c:if test="${!flag_area_empty}">
		    <li class="item"><a href="javascript:void(0);" onclick="clickedAear('')">全部</a></li>
		    </c:if>
	        <c:forEach var="cur" items="${sonBaseProList}">
	        <c:if var="flag_area" test="${af.map.p_index eq cur.p_index}">
	        	<li class="item current">
	        	<a href="javascript:void(0);" onclick="clickedAear('${cur.p_index}')">${fn:escapeXml(cur.p_name)}</a></li></c:if>
	       	<c:if test="${!flag_area}">
	        	<li class="item">
	        	<a href="javascript:void(0);" onclick="clickedAear('${cur.p_index}')">${fn:escapeXml(cur.p_name)}</a></li></c:if>
	        </c:forEach>
        </ul>
    </div>
	</div>
	</c:if>
	</div>
    </div>
    
    <div class="component-filter-sort mt-component--booted">
      <div class="filter-sortbar">
      <div class="button-strip inline-block">
          <a href="javascript:orderByParam();" title="默认排序" class="button-strip-item inline-block button-strip-item-right button-strip-item button-strip-item-checked">
              <span class="inline-block button-outer-box">
                  <span class="inline-block button-content">默认</span>
              </span>
          </a><a id="orderBySaleCountUpdateDesc" href="javascript:orderByParam('orderBySaleCountUpdateDesc');" title="销量从高到低" class="button-strip-item inline-block button-strip-item-left button-strip-item-right button-strip-item-desc">
             <span class="inline-block button-outer-box">
                 <span class="inline-block button-content">销量</span><span class="inline-block button-img"></span>
             </span>
          </a><a id="orderByPriceAsc" href="javascript:orderByParam('orderByPriceAsc');" title="价格从低到高" class="button-strip-item inline-block button-strip-item-left button-strip-item-right button-strip-item-asc">
              <span class="inline-block button-outer-box">
                  <span class="inline-block button-content">价格</span><span class="inline-block button-img"></span>
              </span>
          </a><a id="orderByViewCountDesc" href="javascript:orderByParam('orderByViewCountDesc');" title="浏览次数从高到低" class="button-strip-item inline-block button-strip-item-left button-strip-item-right button-strip-item-desc">
              <span class="inline-block button-outer-box">
                  <span class="inline-block button-content">浏览次数</span><span class="inline-block button-img"></span>
              </span>
          </a>
      </div>
</div>
</div>
</div>
</html-el:form>
</div>
<div id="content" class="mall mall--fixed cf J-mall log-mod-viewed">
 <div class="J-scrollloader cf J-hub">
 <div class="component-deal-list mt-component--booted">
  
  <c:if test="${not empty entityList}">
  <c:forEach var="cur" items="${entityList}" varStatus="vs">
  <c:set var="divClass" value="" />
  <c:if test="${(vs.count mod 2) eq 0}">
  <c:set var="divClass" value="deal-tile--even" />
  </c:if>
    <div class="deal-tile ${divClass}" style="width: 190px;height: 349px;">
      <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cur.id}" />
       <a href="${url}" class="deal-tile__cover" target="_blank">
        <img class="J-webp" width="190" height="190" alt="${fn:escapeXml(cur.comm_name)}" src="${file_domain}/${ctx}/${cur.main_pic}@s400x400" />
        </a>
      <h3 class="deal-tile__title">
       <a href="${url}" class="w-link" title="${fn:escapeXml(cur.comm_name)}" target="_blank">
          <span class="xtitle">${fn:escapeXml(cur.comm_name)}</span>
          <span class="short-title">${fn:escapeXml(cur.sub_title)}</span>
         </a>
      </h3>
      <p class="deal-tile__detail">
        <span class="price">¥<strong><fmt:formatNumber value="${cur.sale_price}" pattern="0.##" /></strong></span>
<%--         <c:if test="${not empty cur.map.commZyName}"> --%>
<%--          <span class="campaign">${fn:escapeXml(cur.map.commZyName)}</span> --%>
<%--         </c:if> --%>
      </p>
      <div class="deal-tile__extra">
       <p class="extra-inner">
          <c:set var="saleCount" value="${cur.sale_count_update}"/>
           <c:if test="${saleCount gt 10000}">
             <fmt:formatNumber var="saleCount" value="${saleCount/10000}" pattern="#.##万"/>
           </c:if>
          <span class="sales">已售<strong class="num">${saleCount}</strong></span>
       </p>
      </div>
    </div>
    
   </c:forEach>
  </c:if> 
   <c:if test="${empty entityList}">
   <div class="no-poilist">
    <img class="icon" width="50" height="54" src="${ctx}/styles/imagesPublic/user_header.png" />
    <span class="tip">未找到对应的优惠信息</span>
	</div>
   </c:if>
   
  </div>
 </div>
<div class="pages">
	<c:url var="url" value="/Search.do" />
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="${url}">
    <ul id="pagination" class="pagination"></ul>
     <script type="text/javascript" src="${ctx}/commons/pager/pagination.home.js">;</script>
       <script type="text/javascript">
          $.fn.pagination.addHiddenInputs("method", "listPd");
          $.fn.pagination.addHiddenInputs("p_index", "${af.map.p_index}");
          $.fn.pagination.addHiddenInputs("root_cls_id", "${af.map.root_cls_id}");
          $.fn.pagination.addHiddenInputs("par_cls_id", "${af.map.par_cls_id}");
          $.fn.pagination.addHiddenInputs("keyword", "${af.map.keyword}");
          $.fn.pagination.addHiddenInputs("son_cls_id", "${af.map.son_cls_id}");
          $.fn.pagination.addHiddenInputs("orderByParam", "${af.map.orderByParam}");
          $.fn.pagination.addHiddenInputs("comm_type", "${af.map.comm_type}");
      	  $("#pagination").pagination({
      		pageForm : document.bottomPageForm,
      		recordCount : parseFloat("${af.map.pager.recordCount}"),
      		pageSize : parseFloat("${af.map.pager.pageSize}"),
      		currentPage : parseFloat("${af.map.pager.currentPage - 1}")
      	 });
       </script>
    </form>
  </div>
</div>
 </div>
</div>

<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var f = $(".attrForm").get(0);
$(document).ready(function(){
	orderByParamSetClass("${fn:escapeXml(af.map.orderByParam)}");
	
	
	$(".filter-breadcrumb").find(".breadcrumb__item").each(function(){
		$(this).mouseover(function(){
			$(this).addClass("dropdown--open");
		}).mouseout(function(){
			$(this).removeClass("dropdown--open");
		});
	});
	
});

function clickGetAll(){
	$("#p_index").val("");
	$("#root_cls_id").val("");
	$("#par_cls_id").val("");
	$("#son_cls_id").val("");
	f.submit();
}
function clickedAear(area_p_index){
	$("#p_index").val(area_p_index);
	f.submit();
}

function clickedRootId(root_cls_id){
	$("#root_cls_id").val(root_cls_id);
	$("#son_cls_id").val("");
	$("#par_cls_id").val("");
	f.submit();
}
function clickedParId(par_cls_id){
	$("#par_cls_id").val(par_cls_id);
	$("#son_cls_id").val("");
	f.submit();
}
function clickedSonId(son_cls_id){
	$("#son_cls_id").val(son_cls_id);
	f.submit();
}
function orderByParamSetClass(orderByParam) {
	  if(null != orderByParam && '' !=orderByParam){
		$("#" + orderByParam).addClass("button-strip-item-checked").siblings().removeClass("button-strip-item-checked");
		if(orderByParam.indexOf("Desc") > -1){
			$("#" + orderByParam).addClass("button-strip-item-desc-active");
		}else{
			$("#" + orderByParam).addClass("button-strip-item-asc-active");
		}
	  }
	}


	function orderByParam(orderByParam) {
		$("#orderByParam").val(orderByParam);
		f.submit();
	}

//]]></script>
</body>
</html>