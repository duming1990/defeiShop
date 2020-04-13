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
<link href="${ctx}/m/styles/css/cp_style_v15.11.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.pay_text {
  display: block;
  text-align: center;
  color: #7a7f83;
  font-size: 16px;
}

.pay_money {
  display: block;
  text-align: center;
  font-size: 24px;
  margin-top: 14px;
}

.pay_money_input {
  width: 118px;
  border-bottom: 1px solid #c9c9c9;
  outline: none;
  background: transparent;
  outline: medium;
  text-align: center;
  font-size: 24px;
}

.mui-card-content-inner {
  padding: 55px 15px;
}
.logo-div{
    text-align: center;
    margin: 30px 10px;
}
.logo{
	width: 20%;
    text-align: center;
    border-radius: 40px;
}

.entp_name{
    color: #dd524d;
    font-size: 26px;
    margin-top: 10px;
}
.btn-pay-dianzi{
    border: 1px solid #f98b3d;
    background-color: #f98b3d;
}
.btn-weixin{
	
	 border: 1px solid #00d00b;
    background-color:#00d00b;
}
.btn-alipay{
	 border: 1px solid #00a9ee;
    background-color:#00a9ee;
}
.mui-btn-block{
   padding: 12px 0;

}
</style>
</head>

<body id="body">
<div id="app" v-cloak>
 <c:url var="url" value="MActivity.do?method=payAjax" />
<%-- <form action="${url}" class="formOrder" method="post"> --%>
<input type="hidden" name="id" id="id" value="${af.map.id}" />
<input type="hidden" name="openid" id="openid" value="${af.map.openid}" />
  <header class="mui-bar mui-bar-nav">
  <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
  <h1 class="mui-title">线下活动支付</h1> </header>
  <div class="mui-content">
  <div class="logo-div">
	<img class="logo" src="${ctx}/${entpInfo.entp_logo}" onerror="this.src='${ctx}/styles/imagesPublic/user_header.png'" >
  	<h1 class="entp_name">${entpInfo.entp_name}</h1>
  	
  </div>
  
<!--     <h5 class="mui-content-padded">选择支付方式</h5> -->
    <div class="mui-card">
      <div class="mui-input-group">
        <div class="mui-input-row mui-checkbox" v-if="is_user"> <label>使用余额抵扣 ￥ <fmt:formatNumber value="${userInfo.bi_dianzi}" pattern="0.##"/></label>
          <input name="is_use_bi_dianzi" type="checkbox" value="0" :checked="is_use_bi_dianzi?'true':'false'" v-model="is_use_bi_dianzi" /></div>
          
<%--           <c:if test="${!isWeixin }"> --%>
<!--         <div class="mui-input-row mui-radio"> <label>支付宝</label> -->
<!--           <input name="pay_type" type="radio" value="1" :checked="pay_type==1?'true':'false'" v-model="pay_type" /> </div> -->
<%--           </c:if> --%>
          
<%--           <c:if test="${isWeixin}"> --%>
<!--         <div class="mui-input-row mui-radio"> <label>微信</label> -->
<!--           <input name="pay_type" type="radio" value="4" :checked="pay_type==1?'true':'false'" v-model="pay_type" /> </div> -->
<%--           </c:if> --%>
      </div>
      
    </div>
    <div class="mui-card">
      <div class="mui-card-content">
        <div class="mui-card-content-inner"> <span class="pay_text">支付金额</span>
        <span class="pay_money">￥<input type="number" class="pay_money_input" placeholder="输入金额" name="pay_money" v-model="pay_money" id="pay_money" maxlength="10" value=""   /></span>          </div>
      </div>
    </div>
    
<!--     <div class="mui-content-padded" v-if="is_use_bi_dianzi"> -->
<!--     	<button type="button" class="mui-btn mui-btn-danger mui-btn-block btn-pay-dianzi" @tap="tijiao(1)">余额抵扣({{pay_bi_dianzi}}) 支付({{order_money}}元)</button> -->
<!--     </div> -->

    
    <div class="mui-content-padded" v-if="is_weixin"> <button type="button" class="mui-btn mui-btn-danger mui-btn-block btn-weixin" @tap="tijiao(4)">微信支付<span v-if="is_use_bi_dianzi">({{order_money}}元)</span></button> </div>
    
    <div class="mui-content-padded" v-if="!is_weixin"> <button type="button" class="mui-btn mui-btn-danger mui-btn-block btn-alipay" @tap="tijiao(1)">支付宝支付<span v-if="is_use_bi_dianzi">({{order_money}}元)</span></button> </div>
    
  </div>
<!--   </form> -->
</div>

<div v-html="ajaxFrom" id="ajaxFrom"></div>
<c:url var="urlnext" value="/m/MActivity.do?method=paySuccess" />
<c:url var="weixinPay" value="/m/MActivity.do?method=weixinPay" />
<script type="text/javascript">
//<![CDATA[
var vm = new Vue({
  el: '#app',
  data: {
    datas: '',
    ctx: Common.api,
    id: Common.getUrlParam("id"),
    pay_money: "",
    pay_type:'${pay_type}',
    ajaxFrom: "",
    bi_dianzi:0,
    pay_bi_dianzi:0,
    order_money:0,
    is_use_bi_dianzi:false,
    is_user:false,
    trade_index:"${af.map.trade_index}",
    is_weixin:false
  },
  mounted: function() {
    this.$nextTick(function() {
      //       vm.getAjaxData();
      
      <c:if test="${not empty userInfo}">
		vm.is_use_bi_dianzi = true;
		vm.is_user = true;
		vm.bi_dianzi = parseFloat("${userInfo.bi_dianzi}").toFixed(2);
      </c:if>
      
      <c:if test="${not empty orderInfo}">
		vm.pay_money = parseFloat("${orderInfo.order_money}").toFixed(2);
      </c:if>
      <c:if test="${isWeixin }">
      vm.is_weixin = true;
      
      </c:if>
    });

  },
  watch:{
	  pay_money:function(newVal){
		  newVal =  parseFloat(newVal)
		  console.info(newVal);
		 
		  if(newVal > vm.bi_dianzi){
			  newVal = vm.bi_dianzi;
		  }
		  if(!newVal){
			  newVal = 0
		  }
		  vm.pay_bi_dianzi = newVal;
		  
		  var this_money = (vm.pay_money - vm.pay_bi_dianzi).toFixed(2);
		  if(!this_money){
			  this_money = 0
		  }
		  
		  vm.order_money = this_money;
	  },
  		is_use_bi_dianzi:function(){
  			console.info(vm.is_use_bi_dianzi)
  		},
  },
  methods: {
    tijiao: function(pay_type) {
      if(!vm.pay_money) {
        mui.toast("请填写支付金额");
        return false;
      }
      
		
      
      var data= {
		pay_type: pay_type,
        pay_money: vm.pay_money,
        openid: "${af.map.openid}",
        id: "${af.map.id}",
        is_use_bi_dianzi:vm.is_use_bi_dianzi
      }
      
      
      if(vm.trade_index){
    	  data['trade_index'] = vm.trade_index;
      }
      console.info(data);
      
      var url = 'm/MActivity.do?method=payAjax';
      if(vm.trade_index){
    	  url = 'm/MActivity.do?method=pay';
      }
      
      var btnArray = ['否','是'];
		mui.confirm('支付金额'+vm.pay_money+"元", '确认支付', btnArray, function(e) {
			if (e.index == 1) {
				
				Common.loading();
				  
			      Common.getData({
			        route: url,
			        data: data,
			        success: function(data) {
			          Common.hide();
			          console.info(data);
			          if(data.code != 1) {
			            mui.alert(data.msg);
			            return false;
			          }
			          
			          var return_pay_type = data.datas.pay_type;
			          if(return_pay_type == 1){
			        	  if(null != data.datas.ajaxFrom) {
			                  vm.ajaxFrom = data.datas.ajaxFrom;
			                  console.info(vm.ajaxFrom);
			                  document.getElementById('ajaxFrom').innerHTML = vm.ajaxFrom;
			                  var form =  document.getElementsByName('alipaysubmit');
			                  console.info(form.length);
			                  form[0].submit();
			                }
			          }else if(return_pay_type == 4){
//			         	  var url ='${ctx}/index/Pay/weixinallinpay.jsp';
			        	  var out_trade_no = data.datas.out_trade_no;
			        	  var openid = data.datas.openid;
			        	  if(null == out_trade_no || out_trade_no == ''){
			        		  mui.alert("订单编号错误，请重新扫码");
			        		  return false;
			        	  }
			        	  if(null == openid || openid == ''){
			        		  mui.alert("微信参数丢失，请重新扫码");
			        		  return false;
			        	  }
			        	  var url ='${ctx}/m/MActivity.do?method=weixinPay&out_trade_no='+out_trade_no+"&openid="+openid;
						  goUrl(url);
			          }else{
						mui.toast("支付成功");
			 			setTimeout(function(){
			 			location.href="${urlnext}";}, 2000);
			          }
			        },
			        error: function() {
			          mui.alert('好像出错了哦~');
			        }
			      });
			      
			} else {
				
			}
		})
    
    },
    getAjaxData: function() {
      Common.getData({
        route: 'm/MActivity.do?method=getAjaxData',
        data: {
          id: vm.id,
        },
        success: function(data) {
          console.info(data);
          if(data.code != 1) {
            mui.alert(data.msg);
            return false;
          }
          vm.cartInfoList = data.datas.list;
          vm.cartInfoList.forEach(function(item, index) {
            if(item.map.pd_count > item.map.pd_max_count) {
              item.map.isChoose = false;
              item.map.pd_count = 0;
            }
            if(item.map.isChoose) {
              vm.sumPdCount++;
            }
          });
        },
        error: function() {
          mui.alert('好像出错了哦~');
        }
      });
    },
  }
});
//]]>
</script>
</body>

</html>