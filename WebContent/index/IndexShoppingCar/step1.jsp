<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="MSThemeCompatible" content="no" />
<meta name="MSSmartTagsPreventParsing" content="true" />
<meta name="Description" content="${app_name}" />
<meta name="Keywords" content="${app_name}" />
<title>查看购物车 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css?v=20180408" />
<style type="">
  #bd{ width: 1200px; top:0px; } .mui-switch.mui-active{border-color: #ec5051;background-color: #ec5051;}
</style>
</head>
<body class="pg-buy pg-buy-process pg-buy pg-cart pg-buy-process" id="deal-buy">
<jsp:include page="../../_header_order.jsp" flush="true" />
<div class="component-system-message mt-component--booted" style="display:none;" id="msg_error">
  <div class="sysmsgw common-tip">
    <div class="sysmsg"> <span class="J-msg-content"><span class="J-tip-status tip-status tip-status--error"></span> </span> <span id="msg_error-tip">抱歉，数量有限，您最多只能购买 1件</span> <span class="close common-close" onclick="closedErrorMsg();">关闭</span> </div>
  </div>
</div>
<div class="bdw">
  <c:if test="${not empty entpInfoList}">
    <div id="bd" class="cf cart_not_empty" style="top:0px;">
      <c:url var="url" value="IndexShoppingCar.do" />
      <html-el:form action="${url}" styleClass="common-form form J-wwwtracker-form mt-component--booted formOrder" method="post">
        <html-el:hidden property="delivery_p_index" styleId="delivery_p_index" value="${p_index}" />
        <html-el:hidden property="shipping_address_id" styleId="shipping_address_id" value="${shipping_address_id}" />
        <html-el:hidden property="need_fp" styleId="need_fp" value="0" />
        <div class="cart-head cf">
          <div class="cart-status"> <i class="cart-status-icon status-1"></i><span class="cart-title">我的购物车</span> </div>
        </div>
        <div class="component-dealbuy-delivery mt-component--booted">
          <div class="blk-item delivery J-deal-buy-delivery dealbuy-delivery">
            <h3>收货地址<span style="float:right;"><a onclick="addAddr();" href="javascript:void(0);" style="color: #ec5051!important;">新增收货地址</a></span></h3>
            <c:if test="${not empty shippingAddressList}">
              <div class="step-cont">
                <div id="consignee-addr" class="consignee-content">
                  <div class="consignee-scrollbar">
                    <div class="ui-scrollbar-main">
                      <div class="consignee-scroll">
                        <div class="consignee-cont" id="consignee1" style="height:auto;">
                          <ul id="consignee-list">
                            <c:forEach items="${shippingAddressList}" var="cur" varStatus="vs">
                              <c:set var="shippingSelect" value="" />
                              <c:if test="${cur.id eq shipping_address_id}">
                                <c:set var="shippingSelect" value="item-selected" />
                              </c:if>
                              <li class="ui-switchable-panel ui-switchable-panel-selected" style="display:list-item;" id="addrli${cur.id}">
                                <div class="consignee-item ${shippingSelect}" id="addrliSelect${cur.id}" onclick="showShippingAddress(${cur.id});">
                                  <span title="收货人：${fn:escapeXml(cur.rel_name)}">${fn:escapeXml(cur.rel_name)}</span><b></b></div>
                                <div class="addr-detail">
                                  <%--                           <span class="addr-name" title="${fn:escapeXml(cur.rel_name)}">${fn:escapeXml(cur.rel_name)}</span>  --%>
                                  <span class="addr-info" title="${fn:escapeXml(cur.map.full_addr)}">${fn:escapeXml(cur.map.full_addr)}</span>
                                  <span class="addr-tel">${fn:escapeXml(cur.rel_phone)}</span>
                                </div>
                                <div class="op-btns">
                                  <a href="javascript:void(0);" class="ftx-05 del-consignee" onclick="delAddr('${cur.id}',this)">删除</a>
                                </div>
                              </li>
                            </c:forEach>
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <c:if test="${fn:length(shippingAddressList)> 4}">
                  <div class="addr-switch switch-on" id="showAllShippingAddr"> <span>更多地址</span><b></b> </div>
                  <div class="addr-switch switch-off" style="display:none;" id="onlyShowFourShippingAddr"> <span>收起地址</span><b></b> </div>
                </c:if>
              </div>
            </c:if>
            <c:if test="${empty shippingAddressList}">
              <div>暂无收货地址</div>
            </c:if>
          </div>
        </div>
        <c:forEach items="${entpInfoList}" var="entp">
          <div class="table-section summary-table">
            <table class="buy-table">
              <tr class="order-table-head-row">
                <th width="200">
                  <label for="cart-selectall" class="cart-select-all">店铺:${fn:escapeXml(entp.entp_name)}</label>
                </th>
                <th class="desc" style="width: 360px;">项目</th>
                <th class="unit-price">单价</th>
                <th class="amount">数量</th>
                <th class="col-total" width="100px;">实际付款</th>
              </tr>
              <tbody id="cartContentTbody">
                <c:forEach items="${entp.cartInfoList}" var="cart">
                  <tr id="cartTr${cart.id}">
                    <td width="60" rowspan="1" class="select-cartItem">
                      <input type="hidden" class="ui-checkbox" name="cart_ids" id="cart-select${cart.id}" value="${cart.id}" />
                      <img src="${ctx}/${cart.map.comm_images}" style="width: 90px;" />
                      
                    </td>
                    <td class="desc">
                      <c:url var="url" value="/entp/IndexEntpInfo.do?method=getCommInfo&id=${cart.comm_id}" />
                      <a href="${url}" target="_blank">${fn:escapeXml(cart.comm_name)}</a>[${cart.comm_tczh_name}]</td>
                    <td class="money J-deal-buy-price">¥<span id="deal-buy-price">
	            <fmt:formatNumber value="${cart.pd_price}" pattern="0.##" />
	            </span></td>
                    <td class="deal-component-quantity">
                      <div class="component-dealbuy-quantity mt-component--booted">
                        <div class="dealbuy-quantity">
                          <input type="text" class="f-text J-quantity J-cart-quantity" maxlength="4" id="${cart.id}pd_count" value="${cart.pd_count}" readonly="readonly" />
                          <c:set var="color" value="" />
                          <c:if test="${cart.pd_count > cart.map.pd_max_count}">
                            <c:set var="color" value="color:red" /></c:if>
                          <div id="msg${cart.id}" style="${color}">${cart.map.inventoryTip}</div>
                          <c:if test="${not empty cart.map.jd_no_stock}">
                            <span style="color:red;">京东配送该区域暂无库存</span>
                          </c:if>
                          <input type="hidden" name="pd_max_count" value="${cart.map.pd_max_count}" id="max${cart.id}" />
                        </div>
                      </div>
                    </td>
                    <td class="money total rightpadding col-total">¥<span id="J-deal-buy-total${cart.id}" class="thisTrCheck">${(cart.pd_count * cart.pd_price - cart.red_money )}</span> </td>
                  </tr>
                </c:forEach>
                <tr>
                  <td>合计</td>
                  <td></td>
                  <td></td>
                  <td><span style="color: #ec5051;" id="entp_yhq_money${entp.id}"></span></td>
                  <td>¥ <span id="entpTotal_${entp.id}"><fmt:formatNumber value="${entp.map.everEntpTotalMoney}" pattern="0.00"/></span></td>
                </tr>
              </tbody>
            </table>
            </div>
            <div class="cart_remark">
              <div style="float: left;">
                买家留言：<input type="text" name="remark" maxlength="100" style="width:260px;" placeholder="选填，可填写您和卖家达成一致的要求" />
              </div>
              <div style="float:right;">
                运费：<span class="inline-block money" style="color:#f76120;">¥<strong  id="entp_yunfei_${entp.id}">
       <fmt:formatNumber value="${entp.map.everEntpMatflowMoney}" pattern="0.##"/>
       </strong> </span>
              </div>
            </div>
            <c:if test="${not empty entp.map.ztCommInfoList}">
              <div class="order-order" id="ztOrder_${entp.id}" style="display: none;">
                <table class="buy-table" style="margin:20px">
                  <tr class="order-table-head-row">
                    <th class="desc" style="width: 360px;">商品名称</th>
                    <th class="unit-price">单价</th>
                  </tr>
                  <tbody>
                    <c:forEach items="${entp.map.ztCommInfoList}" var="cart">
                      <tr style="padding: 12px;">
                        <td class="desc">${fn:escapeXml(cart.comm_name)}</td>
                        <td class="deal-component-quantity">${fn:escapeXml(cart.sale_price)}</td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:if>
        </c:forEach>

        <div class="table-section summary-table">
          <table class="buy-table">
            <tr>
              <td></td>
              <td></td>
              <td colspan="4" class="extra-fee total-fee"><strong>总运费</strong>： <span class="inline-block money">¥<strong  id="total-yunfei">
          <fmt:formatNumber value="${totalMatflowMoney}" pattern="0.##"/>
          </strong> </span> </td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td colspan="4" class="extra-fee total-fee"><strong>实际支付总额（包含运费）</strong>： <span class="inline-block money">¥<strong id="pay_moeny">
          <fmt:formatNumber value="${totalMoney + totalMatflowMoney - card_user_dis_money}" pattern="0.##"/>
          </strong> </span> </td>
            </tr>
          </table>
        </div>

        <c:if test="${is_send eq 0}">
          <div class="not_send_tip">该商品无法送达</div>
        </c:if>
        <div class="form-submit">
          <c:if test="${lack_inventorty and (is_send eq 1)}">
            <input type="button" class="btn btn-large btn-buy" id="form_sbt" name="buy" value="提交订单" onclick="sbt();" />
          </c:if>
          <c:if test="${!lack_inventorty or (is_send eq 0)}">
            <input type="button" class="btn btn-large btn-disabled" id="form_sbt" name="buy" value="提交订单" />
          </c:if>
        </div>
      </html-el:form>
      <div>
      </div>
      </div>
  </c:if>

  <div id="bd" class="cf cart_empty" style="display:none;">
    <div id="content">
      <div class="mainbox">
        <div class="cart-empty">
          <div class="cart-empty-tips cf"> <i class="cart-empty-icon"></i>
            <h3>您的购物车还是空的</h3>
            <p>点击购物车可以快速添加，您可以：</p>
            <c:url var="url" value="/index.do" />
            <p class="suggestion">去
              <a href="${url}">首页</a>购买喜欢的产品</p>
          </div>
        </div>
        <div> </div>
      </div>
    </div>
  </div>
  </div>
  <jsp:include page="../../_footer.jsp" flush="true" />
  <c:url var="url_my_order" value="/manager/customer/MyOrder.do?par_id=1100500000&mod_id=1100500100"></c:url>
  <script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
  <script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
  <script type="text/javascript" src="${ctx}/scripts/jquery.timers.js"></script>
  <script type="text/javascript" src="${ctx}/styles/mui/mui.min.js"></script>
  <script type="text/javascript">
  //<![CDATA[
  var is_send = "${is_send}";
  var lack_inventorty = "${lack_inventorty}";
  $(document).ready(function() {
    <c:if test="${empty entpInfoList}">
	 	$(".cart_empty").show();
	 </c:if>

    $("#showAllShippingAddr").click(function() {
      $("#consignee-addr").css("max-height", "none");
      $("#showAllShippingAddr").hide();
      $("#onlyShowFourShippingAddr").show();
    });
    $("#onlyShowFourShippingAddr").click(function() {
      $("#consignee-addr").css("max-height", "168px");
      $("#onlyShowFourShippingAddr").hide();
      $("#showAllShippingAddr").show();
    });

    var f = $(".formOrder").get(0);
    
    

  });
  
  

  function delAddr(id, obj) {
    if (null != id && '' != id) {
      var submit = function(v, h, f) {
        if (v == true) {
          $.post("${ctx}/CsAjax.do?method=delShippingAddress", { id: id }, function(data) {
            if (data.result) {
              if ($("#addrliSelect" + id).hasClass("item-selected")) {
                $("#addrli" + id).next().find("div[id*='addrliSelect']").addClass("item-selected");
                $("#shipping_address_id").val(id);
              }
              $("#addrli" + id).remove();
            }
          });
        }
        return true;
      };
      confirmDelShippAddr("您确定要删除该收货地址吗？", submit);
    }
  }

  function addAddr() {
    var url = "?method=addAddr";
    $.dialog({
      title: "新增收货地址",
      width: 770,
      height: 277,
      padding: 50,
      lock: true,
      max: false,
      min: false,
      drag: false,
      content: "url:" + url
    });
  }

  function confirmDelShippAddr(tip, submit) {
    $.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false } });
  }

  function ErrorMsgAndClosed(msg) {
    $("#msg_error-tip").text(msg);
    $("#msg_error").show();
    setTimeout(closedErrorMsg, 2000);
  }

  function closedErrorMsg() {
    $("#msg_error").hide();
  }

  function sbt() {
    $(".formOrder").get(0).action = "?method=step2";
    $(".formOrder").get(0).submit();
  }

  $('body').everyTime('1000s', function() {
    $.ajax({
      type: "POST",
      url: "${ctx}/CsAjax.do?method=getCartInfoState",
      dataType: "json",
      error: function(request, settings) {},
      success: function(data) {
        if (data.ret == 1) {
          $.jBox.tip("页面跳转中，正在进入我的订单...", 'loading');
          window.setTimeout(function() {
            $(".cart_not_empty").empty();
            $(".cart_empty").show();

            $('body').stopTime();
            $.post("${ctx}/CsAjax.do?method=getUrlLinkModId", { mod_id: 1100500100 }, function(data) {
              if (data.ret == 1) {
                var parId_cookie = data.par_id + "," + data.data_url;
                if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
                location.href = "${ctx}/manager/customer/index.shtml";
              }
            });
          }, 2000);

        }
      }
    });
  });

  function showShippingAddress(id) {
    $("#shipping_address_id").val(id);
    $(".formOrder").get(0).action = "?method=step1";
    $(".formOrder").get(0).submit();
  }

  //]]>
  </script>
</body>
</html>