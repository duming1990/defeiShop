<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${header_title}-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/MActivity/cart.css?v=2019" rel="stylesheet" type="text/css" />
<style type="text/css">
.mui-content {
  background-color: #efeff4!important;
}

.mui-content > .mui-card:first-child {
  margin-top: 0px;
}

.mui-card {
  margin: 0px;
  margin-bottom: 4px;
}

.delete {
  width: 30px;
  height: 30px;
}

.mui-content {
  height: calc(100vh)!important;
}

.mui-checkbox input[type=checkbox]:checked:before, .mui-radio input[type=radio]:checked:before {
  color: #dd524d;
}

.footer-input {
  top: 0px!important;
}

.no-submit {
  background: #9e9e9e!important;
  border: 1px solid #9e9e9e!important;
}

.mui-scroll-wrapper {
  overflow-y: auto;
}

.mui-popup-input input {
  padding: 0px;
}

.mui-bar-nav ~ .mui-content {
  padding-bottom: 64px;
}

.mui-scroll {
  padding-bottom: 64px;
}

.card {
  min-height: 30px;
}

.img-p {
  margin-bottom: 0px;
}

.mui-popup-input input {
  height: 30px;
  border-radius: 5px;
  text-align: center;
  font-size: 16px;
}
.comm-tczh{
    vertical-align: middle;
    color: #777;
    white-space: nowrap;
    font-size: 13px;
}
.mui-card-footer, .mui-card-header{
    padding: 5px 15px!important;
        padding-left: 40px !important;
            min-height: 34px;
        
}
.mui-checkbox.mui-left input[type=checkbox], .mui-radio.mui-left input[type=radio]{
top: 8px!important;

}
.choose-color{
    background-color: antiquewhite;
}
</style>
</head>

<body id="body">
<div id="app" v-cloak>
  <header class="mui-bar mui-bar-nav">
  <a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left"></a>
  <h1 class="mui-title">${header_title}</h1> </header>
  <div class="mui-content mui-scroll-wrapper" id="pullrefresh">
    <div class="mui-scroll">
      <div class="mui-card" v-for="item,index in cartInfoList" :class="item.map.isChoose?'choose-color':''">
        <div class="mui-card-footer mui-checkbox mui-left"  @tap="chooseComm($event,index,item.id)">
          <input name="checkbox" :checked="item.map.isChoose?'checked':''" :data-price="item.map.comm_price" :data-count="item.map.pd_count" type="checkbox">
            {{item.comm_name}}-{{item.comm_tczh_name}}
          <p class="img-p"><img class="delete" :src="ctx + item.map.main_pic" /></p>
        </div>
        <div class="mui-card-footer card"><div class="comm-price">¥{{item.map.comm_price | formatMoney}}</div>
          <div class="comm-num-div"> <button type="button" :data-index="index" :data-id="item.id" @tap="chanageCount(-1,'list',index)" class="mui-btn">-</button>
            <input type="number" size="4" maxlength="4" :data-index="index" :data-id="item.id" v-model="item.map.pd_count" @change="chanageCount(0,'list',index)" class="comm-num" />
            <button type="button" :data-index="index" :data-id="item.id" @tap="chanageCount(1,'list',index)" class="mui-btn mui-btn-danger">+</button> </div>
        </div>
<!--         <div class="mui-card-footer card">单价 -->
<!--           <div class="comm-price">¥{{item.map.comm_price | formatMoney}}</div> -->
<!--         </div> -->
<!--         <div class="mui-card-footer card">套餐 -->
<!--           <div class="comm-tczh" @tap="showComm(index);">{{item.map.tczh_name?item.map.tczh_name:'选择'}}</div> -->
<!--         </div> -->
      </div>
    </div>
  </div>
  <div class="mui-card footer">
    <div class="mui-card-content">
      <div class="mui-card-content-inner footer-div">
        <div class="sum-div">
          <div class="comm-sum-text mui-checkbox mui-left">
            <input name="checkbox" @tap="chooseComm('list',null,null)" class="footer-input" :checked="sumPdCount == 0?'':'checked'" value="" style="top: 0px!important;" type="checkbox">
            商品总额: <span class="comm-price">￥{{sumPdPrice | formatMoney}}</span> </div> <button type="button" v-if="sumPdCount != 0 && isTwoClick==1" @tap="submitThisForm"
            class="mui-btn mui-btn-danger">去结算({{sumPdCount}})</button> <button type="button" v-if="sumPdCount == 0 || isTwoClick==0" class="mui-btn mui-btn-danger no-submit">去结算({{sumPdCount}})</button>          </div>
      </div>
    </div>
  </div>

  <div class="mui-popup-backdrop mui-active" v-if="showMoney"></div>
  <div class="mui-popup mui-popup-in" v-if="showMoney">
    <div class="mui-popup-inner">
      <div class="mui-popup-title">支付</div>
      <div class="mui-popup-text">请输入支付金额</div>
      <div class="mui-popup-input">
        <input type="number" v-model="inputMoney" name="inputMoney" id="inputMoney" autofocus="" placeholder=""> </div>
    </div>
    <div class="mui-popup-buttons"><span class="mui-popup-button" @click="quxiao">取消</span><span class="mui-popup-button mui-popup-button-bold" @click="zhifu">支付</span></div>
  </div>

  <div class="tczh-div" id="s-decision-wrapper" v-if="isShow">
		   <div class="body">
			 <section id="s-decision">
				<div class="address-control" style="display: none"></div>
				  <div class="sku-control">
					<ul class="mui-sku">
					  <li class="J_SkuGroup mui-sku-group">
						<h2>套餐</h2>
						  <div class="items">
						    <label v-for="item,index in commTczhPriceList" id="item" :class="item.id == tczh_id ? 'checked':''" @click="chooseType(item,$event)">{{item.tczh_name}}</label>
							</div>
							</li>
						</ul>
					</div>
			</section>
			</div>
			<div class="summary">
				<div class="img">
					<img :src="ctx + comm_logo  + '@s100x100'" width="100" height="100" /></div>
<!-- 					<img src="https://img2.woyaogexing.com/2019/04/15/7076451fd58c44d185bdda8f7467755b!400x400.jpeg" width="100" height="100" /></div> -->
				<div class="main">
					<div class="priceContainer">
						<span class="price" id="sale_price">{{tczh_price | formatMoney}}</span>
					</div>
					<div class="stock-control">
						<span class="stock"><label class="label">库存:</label>
<!-- 						<span id="curr_stock">{{commTczhPriceList[0].inventory}}</span>件</span> -->
						<span id="curr_stock">{{tczh_kucun}}</span>件</span>
					</div>
					<div class="sku-dtips">已选择:
<!-- 						<span id="hasSelectTc">{{commTczhPriceList[0].tczh_name}}</span> -->
						<span id="hasSelectTc">{{tczh_name}}</span>
					</div>
				</div>
				<a class="sback" @click="showComm(0);"><i class="aui-icon aui-icon-close"></i></a>
			</div>
			<div class="option mui-flex">
				<c:url var="url" value="/m/MIndexLogin.do?comm_id=${af.map.id}" />
				<button class="ok cell" @click="addCartOrBuy(false);">确定</button>
			</div>
		</div>
		<div class="cover-decision" v-if="isShow" id="s-decision-wrapper-cover" style=""></div>
</div>
<script type="text/javascript">
//<![CDATA[
var vm = new Vue({
  el: '#app',
  data: {
    datas: '',
    ctx: Common.api,
    id: Common.getUrlParam("id"),
    user_id: '',
    cartInfoList: new Array(),
    currentPageCount: 0,
    ordercount: 0,
    order_money: 0,
    pageSize: 10,
    sumPdPrice: 0,
    sumPdCount: 0,
    isTwoClick: 1,
    isTwoBank: 1,
    isAllChoose: true,
    isTrue: false,
    openid: Common.getUrlParam("openid"),
    showMoney: false,
    inputMoney: "",
    cartIds: "",
    pdCount: "",
    isShow:false,
    commTczhPriceList:"",
    comm_logo:"",
    tczh_id:"",
    tczh_name:"",
    tczh_price:"",
    tczh_kucun:0,
    tczh_count:0,
    thisIndex:"",
    tczh_id_s:""

  },
  mounted: function() {
    this.$nextTick(function() {
      vm.getAjaxData();
    });
  },
  methods: {
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
          // 	          vm.sumPdPrice = data.data.cartInfo.map.sumPdPrice;
          vm.cartInfoList.forEach(function(item, index) {
            if(item.map.pd_count > item.map.pd_max_count) {
              item.map.isChoose = false;
              item.map.pd_count = 0;
              // vm.sumPdPrice = vm.sumPdPrice - item.map.pd_count * item.comm_price;
            }
            if(item.map.isChoose) {
              vm.sumPdCount++;
            }
          });
          // vm.sumPdCount = cartCount;
        },
        error: function() {
          mui.alert('好像出错了哦~');
        }
      });
    },
    
    showComm:function(index){
    	console.info(index);
    	vm.isShow = !vm.isShow;
    	vm.thisIndex = index;
    	if(vm.isShow){
    		vm.commTczhPriceList = vm.cartInfoList[index].map.tczhList;
    		vm.comm_logo = vm.cartInfoList[index].map.main_pic;

    		if(vm.tczh_id){

    		}
    		vm.tczh_id = vm.cartInfoList[vm.thisIndex].map.tczh_id;
    		 vm.tczh_name = vm.cartInfoList[vm.thisIndex].map.tczh_name
    		 vm.tczh_price = vm.cartInfoList[vm.thisIndex].map.comm_price
    		 vm.tczh_kucun = vm.cartInfoList[vm.thisIndex].map.pd_max_count
//     		 vm.tczh_count = 0
    	}else{
    		
    	}
    },
    
    chooseType:function(item, e){
    	 
    	$(e.target).siblings().removeClass("checked");
		$(e.target).addClass("checked");
		 vm.tczh_id = item.id
		 vm.tczh_name = item.tczh_name
		 vm.tczh_price = item.comm_price
		 vm.tczh_kucun = item.inventory
    },
  
    addCartOrBuy:function(){
    	//id
    	//名称
    	//价格
    	vm.cartInfoList[vm.thisIndex].map.tczh_id = vm.tczh_id
    	vm.cartInfoList[vm.thisIndex].map.comm_price = vm.tczh_price
    	vm.cartInfoList[vm.thisIndex].map.pd_max_count = vm.tczh_kucun
    	vm.cartInfoList[vm.thisIndex].map.tczh_name = vm.tczh_name
    	vm.cartInfoList[vm.thisIndex].map.isChoose = true;
    	
    	if(vm.cartInfoList[vm.thisIndex].map.pd_count == 0){
    	vm.cartInfoList[vm.thisIndex].map.pd_count = 1;
    	}

    	vm.caleTotalPrice(); //重新计算价格
    	vm.isShow = !vm.isShow;
    	

    },
    // 提交表单
    submitThisForm: function() {
    	Common.loading();
      vm.isTwoClick = 0;
      var cartIds = "";
      var tczh_id_s = "";
      var pdCount = "";
      var isTrue = true;
      var comm_name = "";
      this.cartInfoList.forEach(function(item, index) {
        if(item.map.isChoose) {
          if(item.map.pd_count > item.map.pd_max_count || item.map.pd_count == 0) {
            isTrue = false;
            comm_name = item.comm_name;
          }
          cartIds += item.id + ",";
          tczh_id_s += item.map.tczh_id + ",";
          pdCount += item.map.pd_count + ",";
        }
      });
      if(!isTrue) {
        mui.toast(comm_name + "库存不足");
        return false;
      }
      if(cartIds.length == 0) {
        mui.toast("请选择商品");
        return false;
      }
      cartIds = cartIds.substr(0, cartIds.length - 1);
      pdCount = pdCount.substr(0, pdCount.length - 1);
      tczh_id_s = tczh_id_s.substr(0, tczh_id_s.length - 1);
      vm.cartIds = cartIds;
      vm.pdCount = pdCount;
      vm.tczh_id_s = tczh_id_s;
      //更改确定支付金额
      
      vm.inputMoney = vm.sumPdPrice;
      vm.zhifu();
//       vm.showMoney = !vm.showMoney;
      //       var btnArray = ['取消', '支付'];
      //       mui.prompt('请输入支付金额', '取消', '支付', btnArray, function(e) {
      //         if(e.index == 1) {
      //         	if(!e.value){
      //         		mui.toast("请输入支付金额");
      //                 return false;
      //         	}
      //         }
      //       })
      setTimeout(function() {
        vm.isTwoClick = 1;
      }, 1000);
    },
    // 改变数量
    chanageCount: function(num, e, index) {
      var thisCount = parseInt(vm.cartInfoList[index].map.pd_count);
      if(num == 0) { //输入数值
        num = vm.cartInfoList[index].map.pd_count;
      } else {
        num == 0 ? thisCount = e.target.value : thisCount = thisCount + num;
      }
      thisCount < 1 ? thisCount = 1 : ""; //计算后的数量小于0默认为0
      var pd_max_count = vm.cartInfoList[index].map.pd_max_count;
      if(thisCount > pd_max_count) {
        thisCount = pd_max_count;
        mui.toast("库存不足");
      }
      vm.cartInfoList[index].map.isChoose = true;
      
      vm.cartInfoList[index].map.pd_count = thisCount;
     
// 	  vm.tczh_count = thisCount;
     
      vm.caleTotalPrice(); //重新计算价格
    },
    //勾选的开关
    chooseComm: function(e, index, id) {
      //       vm.isTrue = !vm.isTrue;
      //       if(vm.isTrue) {
      if(null != index && null != id) {
        var isChoose = vm.cartInfoList[index].map.isChoose;
        if(isChoose) {
          vm.cartInfoList[index].map.isChoose = false;
          vm.cartInfoList[index].map.pd_count = 0;
        }else{
          vm.cartInfoList[index].map.isChoose = true;
          vm.cartInfoList[index].map.pd_count = 1;
        }
      } else {
        if(vm.sumPdCount != 0) {
        	vm.cartInfoList.forEach(function(item, thisIndex) {
            //               item.map.isChoose = false;
            vm.cartInfoList[thisIndex].map.isChoose = false;
            vm.cartInfoList[thisIndex].map.pd_count = 0;
          });
        } else {
        	vm.cartInfoList.forEach(function(item, thisIndex) {
            vm.cartInfoList[thisIndex].map.isChoose = true;
            vm.cartInfoList[thisIndex].map.pd_count = 1;
          });
        }
        vm.isAllChoose = !vm.isAllChoose;
      }
      vm.caleTotalPrice();
      //         vm.isTrue = !vm.isTrue;
      //       }
    },
    //重新计算价格
    caleTotalPrice: function() {
      var sumPrice = 0;
      var sumCount = 0;
      vm.cartInfoList.forEach(function(item, index) {
        var isChoose = item.map.isChoose;
        if(isChoose) {
          sumPrice += parseInt(item.map.pd_count) * parseFloat(item.map.comm_price);
          sumCount++;
        }
      });
      vm.sumPdPrice = sumPrice;
      vm.sumPdCount = sumCount;
    },
    quxiao: function() {
      vm.showMoney = !vm.showMoney;
    },
    zhifu: function() {
    	console.info("===zhifu===")
      if(!vm.inputMoney) {
        mui.toast("请输入支付金额");
        return false;
      }
      var data = {
        activityApplyId: vm.id,
        cartIds: vm.cartIds,
        pdCount: vm.pdCount,
        order_money: vm.inputMoney,
        openid: vm.openid,
        tczh_id_s:vm.tczh_id_s
      }
      console.info(data);
      
      //支付
      Common.getData({
        route: 'm/MActivity.do?method=creartOrder',
        data: data,
        success: function(data) {
          console.info(data);
          if(data.code != 1) {
            mui.alert(data.msg);
            return false;
          }
          var url = "MActivity.do?method=selectPayType&trade_index=" + data.datas.trade_index + "&openid=" + vm.openid+"&id=${af.map.id}";
//           var isWeixin = "${isWeixin}"
//           if(isWeixin == "true"){
// 	          url = '/m/MActivity.do?method=pay&out_trade_no=' + data.datas.trade_index + '&pay_type=4&openid=' + vm.openid;
//           }
          location.href = url;
        },
        error: function() {
          mui.alert('好像出错了哦~');
        }
      });
    }
  }
});
//]]>
</script>
</body>

</html>