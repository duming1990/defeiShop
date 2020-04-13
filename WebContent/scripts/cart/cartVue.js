// 创建一个vue的实例
var vm = new Vue({
    el:'#app' , // 构造参数，实例需要监听的模型范围，对象 #app 下的任何元素都可以背vue操控
    data:{      // data 重点 vue的模型
        totalMoney:0,         // 总金额
        productList:new Array(), // 定义一个数组
        checkAllFlag:false,   // 定义是否全选
        curProduct:'',        // 保存删除的商品信息
        delFlag:false,        // 默认弹出框为false
        comm_barcode:"",
        pay_type:"5",
        user_mobile:"",
        disTotalMoney:0,
        money_bl:"",
        disMoney:'',
        remark:'',
        total_count:0,
    },
    filters:{ // 过滤器 对数据实现转换 可以定义全局的 也可以定义局部的 这个是局部的 只有vue的实例才可以使用
        formatMoney:function (value) { 
            return "¥ " + value.toFixed(2) +" 元";
        },
        formatMoneyNoChina:function (value) { 
            return value.toFixed(2);
        }
    },
    // 这个方法就相当于jq的ready()方法
    mounted:function () {
        this.$nextTick(function () {
        });
    },
    watch: {
    	comm_barcode: function(val, oldval) {
			var comm_barcode = vm.comm_barcode;
			if(comm_barcode != "") {
				document.onkeydown = function(event) {
					var e = event || window.event || arguments.callee.caller.arguments[0];
					if(e && e.keyCode == 13) {//enter 键
						$.post(app_ctx + "/manager/customer/MyPos.do?method=getCommInfoByBarcodeForPos",{comm_barcode : vm.comm_barcode},function(data){
				    		if (data.code == 1) {
				    			var canAdd = true;
				    			vm.productList.forEach(function (item,index) {
				    				if(data.datas.commInfo.id == item.commInfo.id){
				    					item.commInfo.map.count++;
				    					canAdd = false;
				    					return;
				    				}
				    			});
				    			if(canAdd){
				    				vm.productList.push(data.datas);
				    			}
				    			vm.caleTotalPrice();
				        	}else{
				        		$.jBox.tip(data.msg,"error",{timeout:1000});
				        	}
						});
						//这个地方请求数据
						$("#comm_barcode").focus();
						vm.comm_barcode = "";
					}
				};
			}
		},
		money_bl: function(val, oldval) {
			
			if(null != vm.disMoney && "" != vm.disMoney){
				vm.money_bl = "";
				$.jBox.tip("立减和折扣只能享受一种优惠！","error",{timeout:1000});
				return false;
			}
			
			if(vm.totalMoney == 0){
				vm.money_bl = "";
				$.jBox.tip("未发现商品！","error",{timeout:1000});
				return false;
			}
			if(null != val && "" != val){
			 var money_bl = Number(val);
			 if(isNaN(money_bl)){
				 $.jBox.tip("请填写正确的折扣比例！","error",{timeout:1000});
				 return false;
			 }
			 vm.remark = "折扣：" + money_bl + "折";
			 vm.disTotalMoney = vm.totalMoney - vm.totalMoney*money_bl/10;
			}
		},
		disMoney: function(val, oldval) {
			
			if(null != vm.money_bl && "" != vm.money_bl){
				vm.disMoney = "";
				$.jBox.tip("立减和折扣只能享受一种优惠！","error",{timeout:1000});
				return false;
			}
			
			if(vm.totalMoney == 0){
				vm.money_bl = "";
				$.jBox.tip("未发现商品！","error",{timeout:1000});
				return false;
			}
			
			if(null != val && "" != val){
				var disMoney = Number(val);
				if(isNaN(disMoney)){
					 $.jBox.tip("请填写正确的金额！","error",{timeout:1000});
					 return false;
				 }
				
				if(disMoney > totalMoney){
					$.jBox.tip("立减金额不能大于订单总金额！","error",{timeout:1000});
					 return false;
				}
				
				
				vm.remark = "立减：" + disMoney + "元";
				vm.disTotalMoney = disMoney;
			}
		},
	},
    methods:{
    	 // 立即购买
        buyNow:function(){
        	console.info(vm.user_mobile);
        	console.info(vm.remark);
        	$.jBox.tip("正在结算...", 'loading');
        	$.ajax({
				type: "POST" , 
				url: app_ctx + "/manager/customer/MyPos.do" , 
				data:"method=buyNow&productList=" +  JSON.stringify(vm.productList) + "&pay_type=" + vm.pay_type + "&user_mobile=" + vm.user_mobile + "&remark=" + vm.remark + "&disTotalMoney=" + vm.disTotalMoney,
				dataType: "json", 
		        error: function (request, settings) {}, 
		        success: function (data) {
		        	window.setTimeout(function(){
					if (data.code == 0) {
						$.jBox.tip(data.msg,"error",{timeout:1000});
						return false;
					} else if (data.code == 1) {
						$.jBox.tip(data.msg,"success",{timeout:1000});
						vm.productList.splice(0,vm.productList.length);
						vm.caleTotalPrice();
			            vm.pay_type="5";
			            vm.user_mobile="";
			            vm.disTotalMoney=0;
			            vm.money_bl="";
			            vm.disMoney='';
			            vm.remark='';
			            vm.total_count=0;
			            $("#comm_barcode").focus();
					}
		        	},1000);
		        }
			});
        },
        // 点击 加减 的方法
        changeMoney:function (product, way) {
            if( way > 0){
                product.commInfo.map.count++;
            }else {
            	if(product.commInfo.map.count > 1){
            		 product.commInfo.map.count--;
                     if(product.commInfo.map.count < 0){
                         product.commInfo.map.count =0;
                     }
            	}
            }
            this.caleTotalPrice();
        },
        // 计算选中商品的总价
        caleTotalPrice:function () {
            var _this = this;
            this.totalMoney = 0;
            this.total_count = 0;
            this.productList.forEach(function (item,index) {
                _this.totalMoney += item.commInfo.map.commTczhPrice.comm_price * item.commInfo.map.count;
                _this.total_count = _this.total_count + item.commInfo.map.count;
            });
        },
        // 点击删除 出现弹框
        delConfirm:function (item) {
            this.delFlag = true;
            this.curProduct = item; // 保存当前删除的对象
        },
        // 点击弹框里面的 ok 确认删除
        delProduct:function () {
            // 通过indexof 来搜索当前选中的商品 找到索引 index
            var index = this.productList.indexOf(this.curProduct);
            // 获取索引 后删除元素 splice(index，1) 两个参数  第一个参数索引 第二个参数 删除个数
            this.productList.splice(index ,1);// 从当前索引开始删，删除一个元素
            this.delFlag = false; // 删除后 弹框消失
            vm.caleTotalPrice();
        },
        //验证手机号
        valMobile:function () {
        	if(null != vm.user_mobile && "" != vm.user_mobile){
				var reg = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
				if (!vm.user_mobile.match(reg)) {
					$.jBox.tip("请填写正确的手机号！","error",{timeout:1000});
					return false;
				}
			}
        },
        
    }
});

