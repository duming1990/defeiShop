<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en" class="hb-loaded">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta content="telephone=no" name="format-detection">
    <title>我的钱包</title>
    <jsp:include page="../_public_in_head.jsp" flush="true" />
    <link href="${ctx}/styles/my_wallet/css/main.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/styles/my_wallet/css/style.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/styles/my_wallet/css/shake.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/styles/my_wallet/css/animate.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/styles/my_wallet/css/idangerous.swiper.css" rel="stylesheet" type="text/css">
    <style type="text/css">
     .showBip{
       font-size: 1.8rem;
     }
    </style>
</head>
<body>
<div class="warpe">
    <div class="head">
     <c:url var="url" value="/m/MMyHome.do" />
     <a onclick="goUrl('${url}')" style="font-size: 1.6rem;color: #fff;position: absolute;left: 1rem;top: 0rem;">返回</a>
             我的钱包
    </div>
    <div class="pro_t" style="height: 100px;">
    <div class="showBiTop"> 
     <fmt:formatNumber var="bi" value="${userInfo.bi_dianzi-userInfo.bi_welfare}" pattern="0.##"/>
	 <c:if test="${(bi-10000) gt 0}">
	   <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万"/>
	 </c:if>
    <div class="showBi">
	    <p class="showBip">代言费<div style="font-size: 1.5rem;">${bi}</div></p>
	    <div class="pro_b">
	       <c:url var="url" value="/m/MTiXianDianZiBi.do?method=add&mod_id=1100400100" />
	       <a id="ti_xian" onclick="goUrl('${url}')">提现</a>
	       <c:url var="url" value="/m/MMyQianBao.do"/>
	       <a id="ti_xian" onclick="goUrl('${url}')">明细</a>
	    </div>
    </div>
    <div class="showBi">
	    <p class="showBip">福利金
		    <div style="font-size: 1.5rem;">
		    	<fmt:formatNumber value="${userInfo.bi_welfare}" pattern="0.##"/>
		    </div>
	    </p>
	    <div class="pro_b">
	       <c:url var="url" value="/m/MTiXianDianZiBi.do?method=addWelfare&mod_id=1100400100" />
	       <a id="ti_xian" onclick="goUrl('${url}')">提现</a>
	       <c:url var="url" value="/m/MMyQianBao.do?method=welfareList"/>
	       <a id="ti_xian" onclick="goUrl('${url}')">明细</a>
	    </div>
    </div>
    <c:if test="${userInfo.is_poor eq 1}">
    <fmt:formatNumber var="bi" value="${userInfo.bi_aid}" pattern="0.##"/>
	 <c:if test="${(bi-10000) gt 0}">
	   <fmt:formatNumber var="bi" value="${bi/10000}" pattern="#.##万"/>
	 </c:if>
    <div class="showBi">
     <p class="showBip">扶贫金<div style="font-size: 1.5rem;">${bi}</div></p>
     <div class="pro_b">
       <c:url var="url" value="/m/MTiXianAidBi.do?method=add&&par_id=1300100000&mod_id=1300300400"/>
       <a id="ti_xian" onclick="goUrl('${url}')">提现</a>
       <c:url var="url" value="/m/MMyQianBao.do?bi_type=500"/>
       <a id="ti_xian" onclick="goUrl('${url}')">明细</a>
     </div>
    </div>
    </c:if>
    </div>
    </div>
    <div class="tabs my_tab pro_tab">
        <a class="active">收入</a>
        <a class="">支出</a>
    </div>
    <div class="swiper-container swiper-container-horizontal">
        <div class="swiper-wrapper">
			<!-- 收入 -->
            <div class="swiper-slide swiper-slide-active" style="width: 375px;">
                <div class="content-slide">
                    <div class="I_list">
                        <ul id="shouru">
                        <c:forEach var="cur" items="${entityList}" varStatus="vs">
                        <c:if test="${cur.bi_chu_or_ru eq 1}">
                        <c:set var="_class" value="animated fadeInRight"></c:set>
                        <c:if test="${(vs.count mod 2) eq 0}">
                        <c:set var="_class" value="animated fadeInLeft"></c:set>
                        </c:if>
                        <fmt:formatNumber var="yu_e" pattern="#0.##" value="${cur.bi_no}"/>
						<fmt:formatNumber var="bi_yu_e_before" pattern="#0.##" value="${cur.bi_no_before}"/>
						<fmt:formatNumber var="bi_yu_e_after" pattern="#0.##" value="${cur.bi_no_after}"/>
                        <li class="${_class}">
                            <a>
                           	<p style="width: 50%;float: left;height: 2rem;"> 
                             <c:forEach items="${biGetTypes}" var="keys">
	                            <c:if test="${cur.bi_get_type eq keys.index}">${keys.name}</c:if>
      						 </c:forEach>
     						</p>
     						<c:if test="${cur.bi_type ne 200}">
     						<p style="font-size: 1.2rem;"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
     						<p style="position: absolute;color: green;left:50%;top: 62%;">+<em><fmt:formatNumber var="bi" pattern="#0.##" value="${cur.bi_no}"/>${bi}</em></p>
                            <p style="width: 50%;"><font>操作前 &nbsp;&nbsp;&nbsp;&nbsp;<b class="color_y"><em><fmt:formatNumber var="bi_before" pattern="#0.##" value="${cur.bi_no_before}"/>${bi_before}</em> </b>元</font></p>
                            <p style="width: 50%;float: left;"><font>操作后&nbsp;&nbsp;&nbsp;&nbsp;<b class="color_y">
                            <em><fmt:formatNumber var="bi_after" pattern="#0.##" value="${cur.bi_no_after}"/>${bi_after}</em> </b>元</font></p>
                            </c:if>
                            <c:if test="${cur.bi_type eq 200}">
                            <p style="font-size: 1.2rem;">订单：${cur.map.trade_index}</p>
                            <p style="width: 50%;float: left;height: 2rem;font-size: 1.2rem;"><fmt:formatDate value="${cur.map.order_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                            <p style="font-size: 1.2rem;">消费会员：${cur.map.xf_user_name}</p>
                            <p style="width: 50%;float: left;height: 2rem;font-size: 1.2rem;"><fmt:formatDate value="${cur.map.qrsh_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
                            <p style="font-size: 1.2rem;">代言费：<em style="color: #dcac20;"><fmt:formatNumber var="bi" pattern="#0.##" value="${cur.bi_no}"/>${bi}(待到帐)</em></p>
                            </c:if>
                            </a>
                        </li>
                        </c:if>
                       </c:forEach>  
                        </ul>
                    </div>
                </div>
            </div>
			<!--支出 -->
            <div class="swiper-slide swiper-slide-next" style="width: 375px;">
                <div class="content-slide">
                    <div class="I_list">
                        <ul id="zhichu">
                        <c:forEach var="cur" items="${entityList}" varStatus="vs">
                        <c:if test="${cur.bi_chu_or_ru eq -1}">
                        <c:set var="_class" value="animated fadeInRight"></c:set>
                        <c:if test="${(vs.count mod 2) eq 0}">
                        <c:set var="_class" value="animated fadeInLeft"></c:set>
                        </c:if>
                        <fmt:formatNumber var="yu_e" pattern="#0.##" value="${cur.bi_no-cur.fuxiao_no}"/>
						<fmt:formatNumber var="bi_yu_e_before" pattern="#0.##" value="${cur.bi_no_before}"/>
						<fmt:formatNumber var="bi_yu_e_after" pattern="#0.##" value="${cur.bi_no_after}"/>
                        <li class="${_class}">
                        <a>
                        	<p style="width: 50%;float: left;height: 2rem;"> 
                                  <c:forEach items="${biGetTypes}" var="keys">
	                             <c:if test="${cur.bi_get_type eq keys.index}">${keys.name}</c:if>
      						</c:forEach>
  						</p>
  						<p style="font-size: 1.2rem;"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
  						<p style="position: absolute;color: red;left:50%;top: 62%;">
  							-<em><fmt:formatNumber var="bi" pattern="#0.##" value="${cur.bi_no}"/>${bi}</em>
							</p>
                        <p style="width: 50%;">
                         	<font>操作前 &nbsp;&nbsp;&nbsp;&nbsp;<b class="color_y">
                         	<em><fmt:formatNumber var="bi_before" pattern="#0.##" value="${cur.bi_no_before}"/>${bi_before}</em></b>元
                         	</font>
                        	</p>
                        <p style="width: 50%;float: left;">
                         	<font>操作后&nbsp;&nbsp;&nbsp;&nbsp;<b class="color_y">
                         	<em><fmt:formatNumber var="bi_after" pattern="#0.##" value="${cur.bi_no_after}"/>${bi_after}</em></b>元
                         </font>
                        </p>
                        </a>
                        </li>
                        </c:if>
                       </c:forEach>  
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="${ctx}/styles/my_wallet/js/idangerous.swiper.min.js"></script>
<script type="text/javascript">
var tabsSwiper = new Swiper('.swiper-container',{
    speed:500,
    onSlideChangeStart: function(){
        $(".tabs .active").removeClass('active');
        $(".tabs a").eq(tabsSwiper.activeIndex).addClass('active');
    }
});
 $(".tabs a").on('touchstart mousedown',function(e){
     e.preventDefault();
     $(".tabs .active").removeClass('active');
     $(this).addClass('active');
     tabsSwiper.slideTo( $(this).index() )
 });
 
 function getBi600(){
	 
 }
</script>
</body>
</html>