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
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css?20160826" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/styles/css/step1.css?20180523" rel="stylesheet" type="text/css" />
<style type="text/css">
.not_send_tip {
  color: #ec5051;
  font-weight: bolder;
}

.order-orderPay .line .price {
  vertical-align: bottom;
}

.order-orderPay .line .ziti {
  vertical-align: bottom;
  display: inline-block;
}

.mui-switch.mui-active {
  border-color: #ec5051;
  background-color: #ec5051;
}
</style>
</head>
<body id="body">
<div id="wrap">
  <jsp:include page="../_header.jsp" flush="true" />
  <div id="notEmptyCart">
    <c:if test="${not empty entpInfoList}">
      <c:url var="url" value="MMyCartInfo.do" />
      <html-el:form action="${url}" styleClass="formOrder" method="post">
        <html-el:hidden property="method" value="step2" />
        <html-el:hidden property="delivery_p_index" styleId="delivery_p_index" value="${p_index}" />
        <html-el:hidden property="shipping_address_id" styleId="shipping_address_id" value="${shipping_address_id}" />
        <html-el:hidden property="need_fp" styleId="need_fp" value="0" />
        <html-el:hidden property="isYs" styleId="isYs" value="${af.map.isYs}" />
        <html-el:hidden property="isLeader" />
	    <html-el:hidden property="leaderOrderId" />
	    <html-el:hidden property="isPt" />
	    <html-el:hidden property="comm_id" />
        <%-- 	<html-el:hidden property="is_yue_dikou" styleId="is_yue_dikou" value="0"/> --%>
        <div class="order-conent" style="margin-bottom: 65px;">

          <c:if test="${not empty dftAddress}">
            <div class="order-address mui-flex align-center" onclick="getAddressList('${dftAddress.id}');">
              <div class="cell fixed align-center">
                <div class="icon"></div>
              </div>
              <div class="cell content">
                <div class="info">
                  <span>${fn:escapeXml(dftAddress.rel_name)}</span>
                  <span class="tel">${fn:escapeXml(dftAddress.rel_phone)}</span>
                </div>
                <div class="detail">
                  <span>${fn:escapeXml(dftAddress.map.full_addr)}</span>
                </div>
              </div>
              <div class="cell fixed align-center">
                <div class="nav"></div>
              </div>
            </div>
          </c:if>
          <c:if test="${empty dftAddress}">
            <div class="common-item" style="padding:.3rem 0;width:80%;margin:0 auto;">
              <c:url var="url" value="/m/MMyCartInfo.do?method=addAddr&cart_ids=${cart_ids}" />
              <input type="button" class="j_submit" value="增加收货地址" name="sub" onclick="goUrl('${url}');"></div>
          </c:if>

          <c:forEach items="${entpInfoList}" var="entp">
            <div class="order-order">
              <header class="order-orderInfo mui-flex align-center">
              <div class="cell content">
                <div class="info">${fn:escapeXml(entp.entp_name)}</div>
              </div>
              </header>

              <c:forEach items="${entp.cartInfoList}" var="cart">
                <html-el:hidden property="cart_ids" value="${cart.id}" />
                <div class="order-item">
                  <div class="order-itemInfo mui-flex">
                    <div class="cell fixed item-pic">
                      <c:url var="url" value="/m/MEntpInfo.do?id=${cart.comm_id}" />
                      <a href="${url}">
                        <div class="img-cell">
                          <div class="img" style="background-image:url(${ctx}/${cart.map.comm_images}@s400x400);"></div>
                        </div>
                      </a>
                    </div>
                    <div class="content cell" style="margin-bottom:0px;">
                      <c:url var="url" value="/m/MEntpInfo.do?id=${cart.comm_id}" />
                      <a href="${url}">
                        <div class="title">${fn:escapeXml(cart.comm_name)}</div>
                      </a>
                      <c:if test="${not empty cart.map.jd_no_stock}">
                        <span style="color:red;">京东配送该区域暂无库存</span>
                      </c:if>
                      <div class="sku-level-info">${cart.map.inventoryTip}</div>
                      <div class="sku-info"><span><span>${cart.comm_tczh_name}</span></span><span></span></div>
                      <c:if test="${cart.map.yhqCount ne 0}">
                        <div class="yhq">
                          <span class="yhq_title" id="yhq_txt${cart.id}" style="color: #f50;">是否使用优惠券</span>
                          <html-el:hidden property="yhq_son_id" styleId="yhq_son_id${cart.id}" value="${cart.yhq_id}" />
                          <html-el:hidden property="yhq_money" styleId="yhq_money${cart.id}" value="${cart.yhq_money}" />
                          <c:set var="switch_active" value="" />
                          <div class="mui-switch mui-switch-mini" id="yhq_switch${cart.id}" style="box-sizing: border-box;float: right;" data-entp-id="${entp.id}" data-hj="${entp.map.everEntpTotalMoney}" data-yhq-count="${cart.map.yhqCount}" data-cart-id="${cart.id}">
                            <div class="mui-switch-handle" id="yhq_switch_son${cart.id}"></div>
                          </div>
                        </div>
                      </c:if>
                      <c:if test="${not empty cart.map.yhqsonlist}">
                        <div class="order-order" id="yhq_html${cart.id}" style="display: none;">
                          <header class="order-orderInfo mui-flex align-center">
                          <div class="cell content" style="text-align: center;font-size: 15px;font-weight: bold;">
                            <div class="info">选择优惠券</div>
                          </div>
                          </header>
                          <ul class="mui-table-view">
                            <c:forEach items="${cart.map.yhqsonlist}" var="cur">
                              <li class="mui-table-view-cell mui-media">
                                <a href="#" onclick="chooseYhq(${cur.id},${cart.id},'${entp.id }',${fn:escapeXml(cur.map.yhq.yhq_money)});">
                                  <%-- 						<img class="mui-media-object mui-pull-left" src="${ctx}/${cur.main_pic}@s400x400"> --%>
                                  <div class="mui-media-body">
                                    ${fn:escapeXml(cur.map.yhq.yhq_name)}
                                    <p class='mui-ellipsis'>${fn:escapeXml(cur.map.yhq.yhq_money)}</p>
                                  </div>
                                </a>
                              </li>
                            </c:forEach>
                          </ul>
                        </div>
                      </c:if>
                      <div class="icon-ext"></div>
                      <div class="icon-main mui-flex align-center"></div>
                      <span></span></div>
                    <div class="ext cell fixed item-pay">
                      <div class="price">
                        <span class="dollar">￥</span>
                        <span class="main-price"><fmt:formatNumber value="${(cart.pd_count * cart.pd_price)}" pattern="0.##" /></span>
                      </div>
                      <div class="quantity"><span>X</span><span>${cart.pd_count}</span></div>
                      <div class="weight"></div>
                      <div class="sub_yhq_money" id="sub_yhq_money${cart.id}"></div>

                    </div>
                  </div>
                  <div>
                  </div>
                  <div class="order-itemPay"></div>
                </div>
              </c:forEach>
              <section class="common-items" id="yunfei${entp.id}">
              
                <div class="common-item">
                  <span class="item-label">运费：</span>
                  <div class="item-content pro-total">
                    <b>¥
			<span id="entp_yunfei_${entp.id}">
			<fmt:formatNumber value="${entp.map.everEntpMatflowMoney}" pattern="0.00"/></span></b>
                  </div>
                </div>
                <div class="common-item">
                  <span class="item-label">给卖家留言：</span>
                  <div class="item-content pro-total">
                    <input placeholder="选填:建议填写已和卖家协商一致的内容" name="remark" class="pro_input" />
                  </div>
                </div>
              </section>
              <div class="order-orderPay buy-single-row">
                <div class="line"><span>合计：</span>
                  <div class="price">
                    <span class="dollar">￥</span>
                    <span class="main-price" id="entpTotal_${entp.id}"><fmt:formatNumber value="${entp.map.everEntpTotalMoney}" pattern="0.00"/></span></div>
                </div>
              </div>
              <html-el:hidden property="is_ziti" styleId="is_ziti_${entp.id}" value="0" />
              <c:if test="${(entp.id ne 0) and (af.map.isYs ne 1)}">
                <div class="order-orderPay buy-single-row" style="border-top: 1px solid #C6C6C6;">
                  <div class="line"><span>是否自提：</span>
                    <div class="ziti">
                      <div class="mui-switch mui-switch-mini" style="box-sizing: border-box;" data-entpId="${entp.id}" data-ziti="${entp.id}" data-yf="${entp.map.everEntpMatflowMoney}" data-hj="${entp.map.everEntpTotalMoney}">
                        <div class="mui-switch-handle"></div>
                      </div>
                    </div>
                  </div>
                </div>
              </c:if>
              <c:if test="${not empty entp.map.ztCommInfoList}">
                <div class="order-order" id="ztOrder_${entp.id}" style="display: none;">
                  <header class="order-orderInfo mui-flex align-center">
                  <div class="cell content" style="text-align: center;font-size: 15px;font-weight: bold;">
                    <div class="info">无法自提的商品</div>
                  </div>
                  </header>
                  <ul class="mui-table-view">
                    <c:forEach items="${entp.map.ztCommInfoList}" var="cur">
                      <li class="mui-table-view-cell mui-media">
                        <a href="javascript:;">
                          <img class="mui-media-object mui-pull-left" src="${ctx}/${cur.main_pic}@s400x400">
                          <div class="mui-media-body">
                            ${fn:escapeXml(cur.comm_name)}
                            <p class='mui-ellipsis'>${fn:escapeXml(cur.sale_price)}</p>
                          </div>
                        </a>
                      </li>
                    </c:forEach>
                  </ul>
                </div>
              </c:if>
            </div>
          </c:forEach>
          <div class="order-orderPay buy-single-row" style="border-top: 1px solid #C6C6C6;">
            <div class="line"><span>是否需要发票：</span>
              <div class="price">
                <div class="mui-switch mui-switch-mini" style="box-sizing: border-box;">
                  <div class="mui-switch-handle"></div>
                </div>
              </div>
            </div>
          </div>

          <section class="common-items" id="fp_type" style="display: none">
            <div class="common-item">
              <span class="item-label">发票类型：</span>
              <html-el:select property="invoice_type" styleId="invoice_type" style="color: #f50;width: 60px;">
                <html-el:option value="0">个人</html-el:option>
                <html-el:option value="1">企业</html-el:option>
              </html-el:select>
            </div>
          </section>
          <section class="common-items" id="fp_entp" style="display: none">
            <div class="common-item">
              <span class="item-label">发票抬头：</span>
              <div class="item-content pro-total">
                <input placeholder="必填:请填写发票抬头" name="invoices_payable" class="pro_input" />
              </div>
            </div>
            <div class="common-item">
              <span class="item-label">发票税号：</span>
              <div class="item-content pro-total">
                <input placeholder="必填:请填写发票税号" name="invoices_no" class="pro_input" />
              </div>
            </div>
          </section>

          <c:if test="${card_user_dis_money gt 0}">
            <div class="order-orderPay buy-single-row" style="border-top: 1px solid #C6C6C6;">
              <div class="line"><span>挑夫会员立减：</span>
                <div class="price">
                  <span class="dollar">￥</span>
                  <span class="main-price"><fmt:formatNumber value="${card_user_dis_money}" pattern="0.00"/></span></div>
              </div>
            </div>
          </c:if>
          <div id="buybox">
            <div class="section-buybox" style="border-bottom:1px solid #C6C6C6;padding-left:0.1rem;">
              <div class="deal-buyatt">
                <p class="price">
                  <input type="hidden" id="cart_base_price" value="${totalMoney + totalMatflowMoney - card_user_dis_money}" /> 总金额（含运费）：
                  <font>¥<b id="cart_oriPrice"><fmt:formatNumber value="${totalMoney + totalMatflowMoney - card_user_dis_money}" pattern="0.00"/></b></font>
                </p>
              </div>
              <div class="deal-pay">
                <c:if test="${is_send eq 0}">
                  <div class="not_send_tip" id="can_send">该商品无法送达</div>
                </c:if>
                <c:if test="${!lack_inventorty}">
                  <div class="not_send_tip">有商品库存不足</div>
                </c:if>
                <c:if test="${lack_inventorty and (is_send eq 1)}">
                  <input type="button" value="提交订单" class="pay" id="submitOrder" onclick="submitThisForm();" />
                </c:if>
                <c:if test="${!lack_inventorty or (is_send eq 0)}">
                  <input type="button" value="提交订单" id="submitOrder" class="pay disabled" />
                </c:if>
              </div>
            </div>
          </div>
        </div>
      </html-el:form>
    </c:if>
  </div>

  <div class="order-order" id="fpOrder" style="display: none;">
    <header class="order-orderInfo mui-flex align-center">
    <div class="cell content" style="text-align: center;font-size: 15px;font-weight: bold;">
      <div class="info">可以开具发票商品</div>
    </div>
    </header>
    <ul class="mui-table-view">
      <c:forEach items="${fpCommInfoList}" var="cur">
        <li class="mui-table-view-cell mui-media">
          <a href="javascript:;">
            <img class="mui-media-object mui-pull-left" src="${ctx}/${cur.main_pic}@s400x400">
            <div class="mui-media-body">
              ${fn:escapeXml(cur.comm_name)}
              <p class='mui-ellipsis'>${fn:escapeXml(cur.sale_price)}</p>
            </div>
          </a>
        </li>
      </c:forEach>
    </ul>
  </div>

  <div id="bd" class="cf cart_empty" style="display:none;">
    <div>
      <div class="allItemv2">
        <div class="o-t-error">
          <div class="img">
            <img src="${ctx}/m/styles/img/cart_empty.png" />
          </div>
          <div class="info">
            <h1 class="title">购物车快饿瘪了T.T</h1>
            <p class="sub">主人快给我挑点宝贝吧</p>
            <p class="btn">
              <c:url var="url" value="/m/Index.do" />
              <a onclick="goUrl('${url}')">去逛逛</a>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
  <jsp:include page="../_footer.jsp" flush="true" />
</div>
<script type="text/javascript" src="${ctx}/scripts/cart/cart.sourceMobile.js"></script>
<script type="text/javascript" src="${ctx}/styles/mui/layer.js"></script>
<script type="text/javascript">
//<![CDATA[
$(function() {
  <c:if test="${empty entpInfoList}">
	 	$(".cart_empty").show();
	 </c:if>

 	<c:if test = "${(userInfo.user_level eq 201) and (card_user_dis_money_tip gt 0)}">
    Common.confirm("升级成为付费会员，立减${card_user_dis_money_tip}元", ["去升级", "取消"], function() {
      location.href = app_path + "/m/MIndexPayment.do?method=PayForUpLevel";
    }, function() {}); 
    </c:if>

//     <c:if test = "${empty dftAddress}">
//     $("#submitOrder").val("添加地址").addClass("disabled").removeAttr("onclick"); 
//     </c:if>
    
  mui('.price .mui-switch').each(function() {
    this.addEventListener('toggle', function(event) {
    	var self = this;
      if ("${fpCommInfoListSize le 0 }") {
        mui.toast("购物车里面没有商品可以开具发票");
        mui(self)['switch']().toggle();
        $("#fp_type").hide();
      }
      else {
        if (event.detail.isActive) {
          $("#need_fp").val(1);
          var html = $("#fpOrder").html();
          setTimeout(function() {
            Common.showLayUi(html, true);
          }, 100);
          $("#invoice_type").val(0);
          $("#fp_type").show();
        }
        else { //这个地方
          $("#need_fp").val(0);
          $("#fp_type").hide();
          $("#fp_entp").hide();
        }
      }
    });
  });

  var f = $(".formOrder").get(0);
  mui('.yhq .mui-switch').each(function() {
    this.addEventListener('toggle', function(event) {

      var cart_id = this.getAttribute('data-cart-id');
      var entp_id = this.getAttribute('data-entp-id');
      var yhq_son_id = $("#yhq_son_id" + cart_id).val();

      if (event.detail.isActive) {
        $("#yhq_switch" + cart_id).removeClass("mui-active");
        $("#yhq_switch_son" + cart_id).attr("style", "");

        var html = $("#yhq_html" + cart_id).html();
        if (html) {
          setTimeout(function() {
            Common.showLayUi(html, true);
          }, 100);
        }

      }
      else {

        $.ajax({
          type: "POST",
          url: "${ctx}/CsAjax.do?method=setCartYhq",
          data: {
            cart_id: cart_id,
            yhq_son_id: yhq_son_id,
            index: -1
          },
          dataType: "json",
          error: function(request, settings) {},
          success: function(data) {
            if (data.ret == "-1") {
              mui.toast(data.msg);
            }
            else {
              var cart_moeny = Number($("#cart_oriPrice").text()); //总金额（含运费）
              var entpTotal_ = Number($("#entpTotal_" + entp_id).text()); //合计
              var yhq_money = Number($("#yhq_money" + cart_id).val());
              var entp_yhq_money = Number($("#entp_yhq_money"+entp_id).text());
              if(entp_yhq_money != 0){
            	  entp_yhq_money = entp_yhq_money -yhq_money;
            	  $("#entp_yhq_money"+entp_id).text(parseFloat(entp_yhq_money).toFixed(2));
              }
              if(entp_yhq_money == 0){
            	  $("#div_entp_yhq_money"+entp_id).remove();
              }

              $("#cart_oriPrice").text(parseFloat(cart_moeny + yhq_money).toFixed(2));
              $("#entpTotal_" + entp_id).text(parseFloat(entpTotal_ + yhq_money).toFixed(2));

              $("#sub_yhq_money" + cart_id).text("");
              $("#sub_yhq_money" + cart_id).removeClass().addClass("sub_yhq_money");

              $("#yhq_son_id" + cart_id).val("");
              $("#yhq_money" + cart_id).val(0);

              $(".yhq_cwith" + yhq_son_id).removeClass("yhq_cwith" + yhq_son_id);
              $(".yhq_cwith_son" + yhq_son_id).removeClass("yhq_cwith_son" + yhq_son_id);
            }
          }
        });
      }
    });
  });

  mui('.ziti .mui-switch').each(function() {
    this.addEventListener('toggle', function(event) {
      var self = this;
      var entp_id = this.getAttribute('data-entpId');

      var cart_moeny = Number($("#cart_oriPrice").text()); //总金额（含运费）
      var yunfei_all = Number(this.getAttribute('data-yf')); //运费
      var entpTotal_ = Number($("#entpTotal_" + entp_id).text()); //合计
      var shipping_address_id = $("#shipping_address_id").val();
      var delivery_p_index = $("#delivery_p_index").val();
      if (event.detail.isActive) {
        var html = $("#ztOrder_" + entp_id).html();
        $.ajax({
          type: "POST",
          url: "?method=ztYunFei&entp_id=" + entp_id + "&shipping_address_id=" + shipping_address_id + "&is_open=1&delivery_p_index=" + delivery_p_index,
          data: $(f).serialize(),
          dataType: "json",
          error: function(request, settings) {},
          success: function(data) {
            if (data.ret == "-1") {
           	  mui(self)['switch']().toggle();
              mui.toast(data.msg);
            }else {
              //不能自提运费
              var zt_no_money = data.curEntpMatflowMoney;
              $("#entp_yunfei_" + entp_id).text(parseFloat(zt_no_money).toFixed(2));
              cart_moeny = parseFloat(cart_moeny - yunfei_all + zt_no_money).toFixed(2);
              entpTotal_ = parseFloat(entpTotal_ - yunfei_all + zt_no_money).toFixed(2);
              $("#entpTotal_" + entp_id).text(entpTotal_);
              $("#cart_oriPrice").text(cart_moeny);
              
              if (html) {
                  setTimeout(function() {
                    Common.showLayUi(html, true);
                  }, 100);
                }
                mui.toast("自提订单将不收取可以自提的商品运费，支付完成之后，凭借短信验证码到店取货!", { duration: 4000, type:'div' });
                $("#is_ziti_" + entp_id).val(1);
              
                $.ajax({
                    type: "POST",
                    url: "?method=canSend&entp_id=" + entp_id + "&shipping_address_id=" + shipping_address_id + "&delivery_p_index=" + delivery_p_index,
                    data: $(f).serialize(),
                    dataType: "json",
                    error: function(request, settings) {},
                    success: function(data) {
		               if(data.is_send == 1){
		            	   $("#submitOrder").removeClass("disabled").attr("onclick","submitThisForm();");
		            	   $("#can_send").hide();
		               } 
                    }
	            });
            }
          }
        });
      }
      else { //这个地方
        $("#is_ziti_" + entp_id).val(0);
        $.ajax({
          type: "POST",
          url: "?method=ztYunFei&entp_id=" + entp_id + "&shipping_address_id=" + shipping_address_id + "&is_open=0",
          data: $(f).serialize(),
          dataType: "json",
          error: function(request, settings) {},
          success: function(data) {
            if (data.ret == "-1") {
              mui.toast(data.msg);
            }else {
              var zt_no_money = data.curEntpMatflowMoney;
              $("#entp_yunfei_" + entp_id).text(parseFloat(yunfei_all).toFixed(2));
              cart_moeny = parseFloat(cart_moeny + yunfei_all - zt_no_money).toFixed(2);
              entpTotal_ = parseFloat(entpTotal_ + yunfei_all - zt_no_money).toFixed(2);
              $("#entpTotal_" + entp_id).text(entpTotal_);
              $("#cart_oriPrice").text(cart_moeny);
             
              $.ajax({
                  type: "POST",
                  url: "?method=canSend&entp_id=" + entp_id + "&shipping_address_id=" + shipping_address_id + "&delivery_p_index=" + delivery_p_index,
                  data: $(f).serialize(),
                  dataType: "json",
                  error: function(request, settings) {},
                  success: function(data) {
                	  if(data.is_send == 0){
                      	 $("#submitOrder").addClass("disabled").removeAttr("onclick");
                      	$("#can_send").show();
                       }
                  }
	            });
            }
          }
        });
      }
    });
  });

  $("#invoice_type").change(function() {
    if ("1" == $(this).val()) { //企业发票
      $("#fp_entp").show();
    }
    else { //个人发票
      $("#fp_entp").hide();
    }
  })
});

function chooseYhq(yhq_son_id, cart_id, entp_id, yhq_money) {

  $.ajax({
    type: "POST",
    url: "${ctx}/CsAjax.do?method=setCartYhq",
    data: {
      cart_id: cart_id,
      yhq_son_id: yhq_son_id,
      index: 1
    },
    dataType: "json",
    error: function(request, settings) {},
    success: function(data) {
      if (data.ret == "-1") {
        mui.toast(data.msg);
      }
      else {
        var cart_moeny = Number($("#cart_oriPrice").text()); //总金额（含运费）
        var entpTotal_ = Number($("#entpTotal_" + entp_id).text()); //合计
        var entp_yhq_money = Number($("#entp_yhq_money"+entp_id).text());

        if (null != data.datas.update_other_yhq && data.datas.update_other_yhq == "1") {
          _yhq_son_id = $("yhq_son_id" + cart_id).val();
          //删除所有打开样式
          $(".yhq_cwith" + yhq_son_id).removeClass("mui-active");
          $(".yhq_cwith_son" + yhq_son_id).attr("style", "");

          $(".sub_yhq_money" + yhq_son_id).each(function() {
            var this_money = Number($(this).attr("data-yhq-money"));
            cart_moeny = cart_moeny + this_money;
            entpTotal_ = entpTotal_ + this_money;
            if(entp_yhq_money != 0){
            	entp_yhq_money = entp_yhq_money - this_money;
            }
            $(this).attr("data-yhq-money", 0);
          });
          $(".sub_yhq_money" + yhq_son_id).text("");
        }

        $("#entpTotal_" + entp_id).text(parseFloat(entpTotal_ - yhq_money).toFixed(2));
        $("#cart_oriPrice").text(parseFloat(cart_moeny - yhq_money).toFixed(2));

        $("#yhq_son_id" + cart_id).val(yhq_son_id);
        $("#sub_yhq_money" + cart_id).text("-" + yhq_money).addClass("sub_yhq_money" + yhq_son_id).attr("data-yhq-money", yhq_money);
        $("#yhq_money" + cart_id).val(yhq_money);

        //加上打开样式
        var yhq_switch_son_style = "transition-duration: 0.2s; transform: translate(16px, 0px);";
        $("#yhq_switch" + cart_id).removeClass().addClass("mui-switch mui-switch-mini mui-active").addClass("yhq_cwith" + yhq_son_id);
        $("#yhq_switch_son" + cart_id).attr("style", yhq_switch_son_style).addClass("yhq_cwith_son" + yhq_son_id);
        
        if(entp_yhq_money == 0){
        	$("#div_entp_yhq_money"+entp_id).remove();
        	 entp_yhq_money = entp_yhq_money + yhq_money;
        	 var html='';
             html += '<section id="div_entp_yhq_money'+entp_id+'" class="common-items" style="margin-bottom: 0;border-bottom: none;">';
             html += '<div class="common-item">';
             html += '<span class="item-label">优惠金额：</span>';
             html += '<div class="item-content pro-total">';
             html += '<b>¥<span id="entp_yhq_money'+entp_id+'">'+parseFloat(entp_yhq_money).toFixed(2)+'</span></b>';
             html += '</div>';
             html += '</div>';
             html += '</section>';
             $("#yunfei"+entp_id).before(html);
        }else{
        	 entp_yhq_money = entp_yhq_money + yhq_money;
        	$("#entp_yhq_money"+entp_id).text(parseFloat(entp_yhq_money).toFixed(2));
        }
        layer.closeAll();
      }
    }
  });
}

function submitThisForm() {
  $("#submitOrder").val("提交中").addClass("disabled").removeAttr("onclick");
  $(".formOrder").get(0).submit();
}

function getAddressList(shipping_address_id) {
  var cart_ids = "${cart_ids}";
  var isYs = "${af.map.isYs}";
  var isPt = "${af.map.isPt}";
  Common.loading();
  window.setTimeout(function() {
    location.href = "${ctx}/m/MMyCartInfo.do?method=addressList&shipping_address_id=" + shipping_address_id + "&cart_ids=" + cart_ids + "&isYs=" + isYs +"&isPt="+isPt;
  }, 1000);
}

//]]>
</script>
</body>
</html>