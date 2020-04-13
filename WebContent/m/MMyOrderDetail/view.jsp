<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/order-details.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.imgclass{
	float: left;
    box-sizing: border-box;
/*     width: 48.5%; */
    height: 100px;
    background: #fff;
    box-shadow: 0 1px 1px 0 rgba(0,0,0,.12);
    margin-bottom: .05rem;
    position: relative;
    float: left;
    margin-left: 0.10rem;
    margin-bottom: 0.10rem;
    box-sizing: border-box;
    position: relative;}
</style>
</head>
<body >
<jsp:include page="../_header.jsp" flush="true" />
<div class="content pad55">
  <div class="m step1 border-1px">
    <div class="order-info-box">
      <div class="mt  cf">
        <div class="floatL">订单号:<span class="s1-num">${af.map.trade_index}</span></div>
        <div class="rightRedF">
          <script type="text/javascript">showOrderState(${af.map.order_state},${af.map.pay_type},${af.map.order_type})</script>
        </div>
      </div>
    </div>
    <a id="orderTrack" class="a-link bdt-1px">
	  <div class="mc">
       <div class="s1-l">
           <div class="cf car-box">
               <span class="car-icon"></span>
               <p class="flex-content">感谢您在${app_name}购物，欢迎您再次光临！</p>
           </div>
          <span class="s-point"></span>
       </div>   
	  </div>
	</a>
	
	<c:if test="${(userInfo.own_entp_id eq af.map.entp_id) and (userInfo.is_entp eq 1)}">
	<a id="orderTrack" class="a-link bdt-1px">
	  <div class="mc">
       <div class="s1-l">
           <div class="cf car-box">
               <p class="flex-content" style="color:#f15353;">商家实得货款 =
                                                 商品货款 (<fmt:formatNumber value="${af.map.entp_huokuan_bi - af.map.matflow_price}" pattern="0.##" />) + 
           	             商品运费 (<fmt:formatNumber value="${af.map.matflow_price}" pattern="0.##" />) = 
                <fmt:formatNumber value="${af.map.entp_huokuan_bi}" pattern="0.##" />
               </p>
           </div>
          <span class="s-point"></span>
       </div>   
	  </div>
	</a>
	</c:if>
  </div>
  
  <div class="step2 border-1px">
    <div class="m step2-in ">
      <div class="mt">
        <div class="s2-name"><i></i>${af.map.map.shippingAddress.rel_name}</div>
        <div class="s2-phone"><i></i>${af.map.map.shippingAddress.rel_phone}</div>
      </div>
      <div class="mc step2-in-con">${af.map.map.shippingAddress.map.full_name}${af.map.map.shippingAddress.rel_addr}</div>
    </div>
   <b class="s2-borderT"></b> <b class="s2-borderB"></b>
  </div>
   
   
  <div class="m step3 border-1px">
    <div class="mt cf"> <span class="shop-name">购买商品</span> 
    <c:url var="entp_url" value="/m/MEntpInfo.do?method=index&entp_id=${af.map.entp_id}" />
    <c:if test="${not empty af.map.entp_name}">
     <a onclick="goUrl('${entp_url}')" style="float:right;margin-right:10px;">商家：${af.map.entp_name}</a>
    </c:if>
    </div>
    <div class="mc" id="commDetails"> 
    <c:forEach items="${orderInfoDetailList}" var="cur" varStatus="vs">
     <c:url var="url" value="/m/MEntpInfo.do?id=${cur.comm_id}" />
     <c:if test="${af.map.order_type eq 7}"><!-- 村商品 -->
       <c:url var="url" value="#" />
     </c:if>
     <a onclick="goUrl('${url}')" class="a-link">
     <c:set var="divClass" value="display:none;" />
     <c:if test="${vs.count le 3}">
     <c:set var="divClass" value="display:block;" />
     </c:if>
     <c:set var="tuihuo-color" value="" />
     
     
      <div class="s-item bdt-1px <c:if test="${cur.is_tuihuo eq 1}">tuihuo-color</c:if>" style="${divClass}">
        <div class="pdiv">
          <div class="sitem-l">
            <div class="sl-img-box">
              <div class="sl-img">
              <c:set var="imgSrc" value="${ctx}/styles/imagesPublic/user_header.png" />
		      <c:if test="${not empty cur.map.commInfo.main_pic}">
		         <c:set var="imgSrc" value="${ctx}/${cur.map.commInfo.main_pic}@s400x400" />
		      </c:if>
              <img src="${imgSrc}" width="120" height="120"/></div>
            </div>
          </div>
          <div class="sitem-m">
            <p class="sitem-m-txt">
            ${cur.comm_name}
            <c:if test="${not empty cur.comm_tczh_name}"> &nbsp;[${cur.comm_tczh_name}] </c:if>
            </p>
            <p class="s3-num">数量：${cur.good_count}</p>
            <fmt:formatNumber pattern="0.##" var="sum_red_money" value="${cur.sum_red_money}" />
            <c:if test="${not empty sum_red_money and sum_red_money gt 0}"><p class="s3-num">消费券抵消金额:¥${sum_red_money}</p></c:if>
          </div>
          <div class="sitem-r">¥<fmt:formatNumber pattern="0.##" value="${cur.good_price}" /></div>
          <c:if test="${cur.is_tuihuo eq 1}">
          	<div class="sitem-tip">已退</div>
          </c:if>
        </div>
      </div>
      </a>
     </c:forEach>
     <c:set var="hideDetailsSize" value="${fn:length(orderInfoDetailList) - 3}" />
     <c:if test="${fn:length(orderInfoDetailList) > 3}">
      <div class="step3-more" id="step3-more" style="display: block;"><strong id="more_tip">还有<i>${fn:length(orderInfoDetailList) - 3}</i>件</strong><span class="s3-down" id="more_tip_class"></span> </div>
	</c:if>
    </div>
  </div>
  <div class="m step4 border-1px">
    <div class="mt bdb-1px cf">
      <h2 class="invoice-left">支付方式</h2>
      <span class="invoice-right">
       <c:forEach var="curPayType" items="${payTypeList}">
        <c:if test="${curPayType.index eq af.map.pay_type}">
          <c:if test="${af.map.order_type eq 30}">
            <c:if test="${af.map.pay_type ne 0}">${curPayType.name}</c:if>
            <c:if test="${af.map.pay_type eq 0}">-</c:if>
          </c:if>
          <c:if test="${af.map.order_type ne 30}">${curPayType.name}</c:if>
        </c:if>
      </c:forEach>
      </span>
      </div>
    <div class="mc">
      <div class="send01 bdb-1px change-p">
        <div class="distribe cf">
          <h3 class="invoice-left">其他信息</h3> </div>
       <c:if test="${not empty af.map.pay_date}">   
        <div class="s4-con">
          <div class="s4-l">
            <p>下单日期：<fmt:formatDate value="${af.map.order_date}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
          </div>
        </div>
        </c:if>
        
       <c:if test="${not empty wlOrderInfo}"> 
        <div class="s4-con">
          <div class="s4-l">
            <p>快递公司名称：${wlOrderInfo.wl_comp_name}</p>
            <p>运单号：${wlOrderInfo.waybill_no}</p>
          </div>
        </div>
       </c:if>
       
        <c:if test="${not empty af.map.fahuo_remark}"> 
        <div class="s4-con">
          <div class="s4-l">
            <p>发货备注：${af.map.fahuo_remark}</p>
          </div>
        </div>
       </c:if>
       
      </div>
    </div>
    <c:if test="${(af.map.is_ziti eq 1) and (af.map.from eq 'user')}">  
    <div class="mc bdb-1px change-p">
      <div class="distribe cf">
        <h3 class="invoice-left">订单密码</h3>
        <span class="invoice-right">${af.map.order_password} </span> </div>
    </div>
  </c:if>  
  </div>
  <c:if test="${not empty orderReturnInfoList}">
  <c:forEach items="${orderReturnInfoList}" var="cur" varStatus="vs">
  
  <div class="m step4 border-1px">
    <div class="mt bdb-1px cf">
      <h2 class="invoice-left">退款/换货信息</h2>
      </div>
    <div class="mc">
      <div class="send01 bdb-1px change-p">
      <div class="s4-con">
          <div class="s4-l">
            <p>是否发货：<c:if test="${cur.expect_return_way eq 4}">未发货</c:if><c:if test="${cur.expect_return_way ne 4}">已发货</c:if></p>
          </div>
        </div>
        <div class="s4-con">
          <div class="s4-l">
            <p>申请退款时间：<fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
          </div>
      </div>
      <div class="s4-con">
          <div class="s4-l">
            <p>期望处理方式：<script type="text/javascript">showTuiHuoCause(${cur.expect_return_way})</script></p>
          </div>
        </div>
      <div class="s4-con">
          <div class="s4-l">
            <p>申请原因：<script type="text/javascript">showTuiHuoReasone(${cur.return_type})</script></p>
          </div>
        </div>
<!--     	<div class="s4-con"> -->
<!--           <div class="s4-l"> -->
<%--             <p>物流公司：${cur.th_wl_company}</p> --%>
<!--           </div> -->
<!--         </div> -->
<!--         <div class="s4-con"> -->
<!--           <div class="s4-l"> -->
<%--             <p>物流订单号：${cur.th_wl_no}</p> --%>
<!--           </div> -->
<!--         </div> -->
        
<!--         <div class="s4-con"> -->
<!--           <div class="s4-l"> -->
<%--             <p>申请人：${cur.return_link_man}</p> --%>
<!--           </div> -->
<!--         </div> -->
<!--         <div class="s4-con"> -->
<!--           <div class="s4-l"> -->
<%--             <p>联系电话：${cur.return_tel}</p> --%>
<!--           </div> -->
<!--         </div> -->
        <c:if test="${(cur.expect_return_way eq 1)}" >
	        <div class="s4-con">
	          <div class="s4-l">
	            <p style="color:red;">退款金额：¥<fmt:formatNumber value="${cur.price}" pattern="0.##"/></p>
	          </div>
	        </div>
<%-- 	         <c:if test="${(af.map.entp_id eq userInfo.own_entp_id)}"> --%>
<!-- 		        <div class="s4-con"> -->
<!-- 		          <div class="s4-l"> -->
<%-- 		            <p>商家运费返还：¥<fmt:formatNumber value="${af.map.matflow_price}" pattern="0.##"/></p> --%>
<!-- 		          </div> -->
<!-- 		        </div> -->
<%-- 	  		 </c:if> --%>
		</c:if>
		<c:if test="${not empty cur.audit_date}">
		<div class="s4-con">
          <div class="s4-l">
            <p>审核时间：<fmt:formatDate value="${cur.audit_date}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
          </div>
        </div>
        </c:if>
		<div class="s4-con">
          <div class="s4-l">
            <p>退款是否成功：
            <c:if test="${cur.audit_state eq 1}">成功</c:if>
            <c:if test="${cur.audit_state eq 2}">成功</c:if>
            <c:if test="${cur.audit_state eq -1}">未成功</c:if>
            <c:if test="${cur.audit_state eq -2}">未成功</c:if>
            </p>
          </div>
        </div>


        <c:if test="${not empty cur.map.returnOrderInfoImgs}">
        
	        <div class="mt bdb-1px cf">
	     		 <h2 class="invoice-left">上传图片</h2>
	      	</div>
	         <div class="s4-con" style="margin-top:10px">
	          <div class="s4-l" style="width: 100%;">
	        	<c:forEach items="${cur.map.returnOrderInfoImgs}" var="curImg"> 
	        		<div class="imgclass">
			        	<a href="${ctx}/${curImg.file_path}"><img src="${ctx}/${curImg.file_path}" height="100px"  id="base_files1_img" /></a>
			        </div>
			    </c:forEach>
			   </div>
			 </div>
	     </c:if>
    </div>
  </div>
  </div>
  </c:forEach>
   </c:if>
  <div class="step5 border-1px">
    <div class="s5-item-w bdb-1px">
     <c:if test="${not empty af.map.yhq_tip_desc}">
       <div class="s-item size15">
        <div class="sitem-l"> 优惠金额</div>
        <div class="sitem-r">${af.map.yhq_tip_desc}</div>
       </div>
      </c:if> 
      <div class="s-item">
        <div class="sitem-l"> +运费 </div>
        <div class="sitem-r"> ¥<fmt:formatNumber value="${af.map.matflow_price}" pattern="0.##"/></div>
      </div>
    </div>
    <div class="s5-sum">
      <div>
	      实付款:<span>¥<fmt:formatNumber value="${af.map.order_money}" pattern="0.##"/></span>
      <c:if test="${af.map.money_bi gt 0}">
	      余额抵扣:<span>¥<fmt:formatNumber value="${af.map.money_bi - af.map.welfare_pay_money}" pattern="0.##"/></span>
	      <c:if test="${af.map.welfare_pay_money gt 0}">
		     	 福利金抵扣:<span>¥<fmt:formatNumber value="${af.map.welfare_pay_money}" pattern="0.##"/></span>
		  </c:if>
	  </c:if>
	  <c:if test="${af.map.card_pay_money gt 0}">
	      福利卡抵扣:<span>¥<fmt:formatNumber value="${af.map.card_pay_money}" pattern="0.##"/></span>
	  </c:if>
	  <c:if test="${af.map.yhq_sun_money gt 0}">
	      优惠券抵扣:<span>¥<fmt:formatNumber value="${af.map.yhq_sun_money}" pattern="0.##"/></span>
	  </c:if>
      </div>
      <p>下单时间:<fmt:formatDate value="${af.map.order_date}" pattern="yyyy-MM-dd HH:mm:ss" /></p>
    </div>
  </div>
  <div class="btn-bar" id="btnBar">
<%--    <c:if test="${(not empty orderReturnInfo) and (af.map.entp_id eq userInfo.own_entp_id) and (orderReturnInfo.audit_state eq 0)}"> --%>
<%-- 	  	<div class="bottom-but"><a onclick="auditTuiKuanOrder('${orderReturnInfo.id}','2');" class="bb-btn1-red">审核通过</a></div> --%>
<%-- 	  	<div class="bottom-but"><a onclick="auditTuiKuanOrder('${orderReturnInfo.id}','-2');" class="bb-btn1-red">审核不通过</a></div> --%>
<%--    </c:if> --%>
   <c:if test="${empty orderReturnInfo}">
    <div class="bb-info"  id="order_state_${af.map.id}" date-isshixiao="${af.map.is_shixiao}" data-order-type="${af.map.order_type}">
     <c:if test="${(af.map.order_type eq 10 or af.map.order_type eq 30) and (af.map.add_user_id eq userInfo.id)}">
  		<div class="bottom-but"><a onclick="orderInfoAddCart('${af.map.id}')" class="bb-btn1-red">再次购买</a></div>
  	 </c:if>
    <c:if test="${af.map.from eq 'user'}">
     <c:choose>
      	<c:when test="${af.map.order_state eq 0}">
       		<c:if test="${af.map.order_type eq 10 or af.map.order_type eq 7 or af.map.order_type eq 30}">
       		  <c:url var="payUrl" value="MMyCartInfo.do?method=selectPayType&trade_index=${af.map.trade_index}&pay_type=${af.map.pay_type}" />
       		  <div class="bottom-but"><a href="javascript:goUrl('${payUrl}');" class="bb-btn1-red">付款</a></div>
       		</c:if>
       		<div class="bottom-but"><a href="javascript:updateState('MMyOrder.do', 'updateState','${af.map.id}', -10, this);" class="bb-btn1-box"><div class="bb-btn1">取消订单</div></a></div>
      	</c:when>
      	<c:when test="${af.map.order_state eq 20}">
      	    <div class="bottom-but"><a href="javascript:updateState('MMyOrder.do', 'updateState', '${af.map.id}', 40, this);" class="bb-btn1-box"><div class="bb-btn1">确认收货</div></a></div>
      	</c:when>
      	<c:when test="${af.map.order_state eq -10}">
      	    <div class="bottom-but"><a href="javascript:confirmDelete(null,'MMyOrder.do','id=${af.map.id}&order_type=${af.map.order_type}&mod_id=1100500100&order_state=${af.map.order_state}');" class="bb-btn1-box"><div class="bb-btn1">删除订单</div></a></div>
      	</c:when>
      	
      	<c:when test="${af.map.order_state eq 10}">
           <c:if test="${(af.map.order_type eq 10) or (af.map.order_type eq 7)}">
<%--              <div class="bottom-but"><a href="javascript:updateState('MMyOrder.do', 'updateState','${cur.id}', 15, this);" class="bb-btn1-box"><div class="bb-btn1">去退款</div></a></div> --%>
           </c:if>
         </c:when>
      </c:choose>
      
      <c:if test="${(af.map.order_state ge 20) and ((af.map.order_type eq 10) || (af.map.order_type eq 7) || (af.map.order_type eq 30))} ">
      		<c:url var="returnUrl" value="MMyOrderReturn.do?method=list&id=${af.map.id}" />
<%--       		<div class="bottom-but"><a id="" href="javascript:goUrl('${returnUrl}');" class="bb-btn1-box"><div class="bb-btn1">售后</div></a></div> --%>
      </c:if>
      <c:if test="${(af.map.order_state ge 20) and (af.map.order_type eq 10 or af.map.order_type eq 7 or af.map.order_type eq 30)}">
      		<c:url var="returnUrl" value="MMyComment.do?method=chooseList&id=${af.map.id}" />
      		<div class="bottom-but"><a id="" href="javascript:goUrl('${returnUrl}');" class="bb-btn1-box"><div class="bb-btn1">评价</div></a></div>
      </c:if>
    </c:if> 
    
    <c:if test="${af.map.from eq 'entp'}">
     <c:choose>
      	<c:when test="${af.map.order_state eq 10}">
<%--       	   <c:if test="${(af.map.order_type eq 10) or (af.map.order_type eq 11 and af.map.is_ziti eq 1)}"> --%>
<%--       	     <div class="bottom-but"  onclick="orderConfirm('${af.map.id}');"><a class="bb-btn1-box"><div class="bb-btn1">订单确认</div></a></div> --%>
<%--            </c:if> --%>
           <c:if test="${(af.map.order_type eq 10) or (af.map.order_type eq 10)}">
      	     <div class="bottom-but" onclick="orderFh('${af.map.id}');"><a class="bb-btn1-box"><div class="bb-btn1">订单发货</div></a></div>
           </c:if>
      	</c:when>
      </c:choose>
    </c:if>  
    </div>
   </c:if>
  </div>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#step3-more").click(function(){
	  var obj = $(this).find("#more_tip_class");
	  
	  if($(obj).hasClass("s3-up")){
		  $(obj).removeClass("s3-up");
		  $("#more_tip").text("还有${hideDetailsSize}件");
		  $("#commDetails a").each(function(index){
			 if($(this).hasClass("a-link") && index > 2){
				 $(this).find("div:first-child").hide();
			 }
		  });
		  
	  }else{
		  $(obj).addClass("s3-up");
		  $("#more_tip").text("收起");
		  $("#commDetails a").each(function(){
			 if($(this).hasClass("a-link")){
				 $(this).find("div:first-child").show();
			 }
		  });
	  }
	});
});

function refreshPage(){
	window.location.reload();
}
//]]></script>
</body>
</html>
