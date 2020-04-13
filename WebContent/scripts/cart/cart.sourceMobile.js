/**
 * cart.js 
 * Date: 2013/03/29
 * @author wuyang@aiisen.com
 * @version 1.0.0
 **/

function calcCartMoney (JqObj, pd_price, cart_id, addNum, isBlur) {
	if (null == addNum || undefined == addNum) {addNum = 0;}
	if (null == isBlur || undefined == isBlur) {isBlur = false;}
	var ajax = true;
	var _regInt = /^[1-9]\d{0,2}$/g;;
	var _regCurr = /^[\+]?\d*?\.?\d*?$/;
	var $this = JqObj;
	var count = parseFloat($this.val());
	if (!_regInt.test(count)) {
		mui.toast("输入的数量有误,应为[1-999]",2000);
		$this.val(1);
		count = 0;
		$this.prev().removeClass("amount_do").addClass("amount_dis");
		ajax = true;
	}
	var pd_max_count = $this.next().val();
	if (null != pd_max_count && undefined != pd_max_count&& "" != pd_max_count) {
		if (((count+addNum) > pd_max_count)) {
			mui.toast("很抱歉，购买数量大于该产品当前库存，请调整购买数量！",2000);
			$("#msg"+ cart_id).html("库存不足");
			$this.val(1);
			$this.prev().removeClass("amount_do").addClass("amount_dis");
			count = 0;
			ajax = true;
		}
	} 
	
	count += parseFloat(addNum); 
	if(count<=0){count=1;$this.prev().addClass("amount_dis");ajax = false;}
	$this.val(count);
	if(count == 1){
		$this.prev().removeClass("amount_do").addClass("amount_dis");
	}else{
		$this.prev().removeClass("amount_dis").addClass("amount_do");
	}
	var price = parseFloat(pd_price);
	if(isNaN(price)){ ajax = false;return false;}
	
	var matflow_price = 0;
	var cur_v = $("#" + cart_id + "matflow_price").val();
	if (!(null == cur_v || undefined == cur_v)) {
		var cur_v_array = cur_v.split(",");
	    matflow_price = parseFloat(cur_v_array[1]); ;
		if (null == matflow_price || undefined == matflow_price) {matflow_price =0;}
	}
	$("#" + cart_id + "minSumPrice").val((count * price) + parseFloat(matflow_price));
	
	var total_money = 0;
	$(".order-conent input[class*='minSumPrice']").each(function(){
		if ($(this).parent().parent().prev().find("#cart_id").hasClass("checked")) {
			total_money = total_money + parseFloat($(this).val());
		}
	});
	
	$("#cart_oriPrice").text(total_money.toFixed(2));
	if(ajax && !isBlur||parseFloat(addNum)<0){
		$.post(app_path+"/CsAjax.do?method=updateCartInfo",{id : cart_id,pd_count:count},function(data){
    		if (!data.result) {
    			mui.toast("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01",2000);
        	}
		});
	}
}

function calcCartMoneyTG (JqObj, pd_price, cart_id, addNum, isBlur) {
	if (null == addNum || undefined == addNum) {addNum = 0;}
	if (null == isBlur || undefined == isBlur) {isBlur = false;}
	var ajax = true;
	var _regInt = /^[1-9]\d{0,2}$/g;;
	var _regCurr = /^[\+]?\d*?\.?\d*?$/;
	var $this = JqObj;
	var count = parseFloat($this.val());
	if (!_regInt.test(count)) {
		mui.toast("输入的数量有误,应为[1-999]",2000);
		$this.val(1);
		count = 0;
		$this.prev().removeClass("amount_do").addClass("amount_dis");
		ajax = true;
	}
	var pd_max_count = $this.next().val();
	if (null != pd_max_count && undefined != pd_max_count&& "" != pd_max_count) {
		if (((count+1) > pd_max_count)) {
			mui.toast("很抱歉，购买数量大于该产品当前库存，请调整购买数量！",2000);
			$("#msg"+ cart_id).html("库存不足");
			$this.val(1);
			$this.prev().removeClass("amount_do").addClass("amount_dis");
			count = 0;
			ajax = true;
		}
	} 
	var is_buyer_limit = $("#is_buyer_limit_"+cart_id).val();
	if(is_buyer_limit != 0){
		var buyer_limit_num = $("#buyer_limit_num_"+cart_id).val();
		if(null != buyer_limit_num || buyer_limit_num != ''){
			var new_count = count + parseFloat(addNum); 
			if(new_count > buyer_limit_num){
				mui.toast("超出限购数量！",2000);
				$this.val(buyer_limit_num);
				return false;
			}
		}
	}
	
	
	
	count += parseFloat(addNum); 
	
	
	if(count<=0){count=1;$this.prev().addClass("amount_dis");ajax = false;}
	$this.val(count);
	if(count == 1){
		$this.prev().removeClass("amount_do").addClass("amount_dis");
	}else{
		$this.prev().removeClass("amount_dis").addClass("amount_do");
	}
	var price = parseFloat(pd_price);
	if(isNaN(price)){ ajax = false;return false;}
	
	var matflow_price = 0;
	var cur_v = $("#" + cart_id + "matflow_price").val();
	if (!(null == cur_v || undefined == cur_v)) {
		var cur_v_array = cur_v.split(",");
	    matflow_price = parseFloat(cur_v_array[1]); ;
		if (null == matflow_price || undefined == matflow_price) {matflow_price =0;}
	}
	$("#" + cart_id + "minSumPrice").val((count * price) + parseFloat(matflow_price));
	
	var total_money = 0;
	$(".order-conent input[class*='minSumPrice']").each(function(){
		total_money = total_money + parseFloat($(this).val());
	});
	$("#cart_oriPrice").text(total_money.toFixed(2));
	if(ajax && !isBlur||parseFloat(addNum)<0){
		$.post(app_path+"/CsAjax.do?method=updateCartInfoTG",{id : cart_id,pd_count:count},function(data){
    		if (!data.result) {
    			mui.toast("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01",2000);
        	}
		});
	}
}

function delCart(thisobj, cart_id) {
	Common.confirm("是否确认删除?",["确定","取消"],function(){
		if (null != cart_id || undefined != cart_id) {
			$.post(app_path+"/CsAjax.do?method=delCart",{cart_id : cart_id},function(data){
				if (data.result) {
					$(thisobj).parent().parent().parent().remove();
					var total_money = 0;
					$(".minSumPrice").map(function(){
						total_money += parseFloat($(this).val());
					});
					$("#total_money").text(total_money.toFixed(2));
					$("#cart_oriPrice").text(total_money.toFixed(2));
				}
			    if($(".carts_tr").size() <= 0) {
			    	$("#notEmptyCart").hide();
			    	$(".cart_empty").show();
			    }
			   
			});
		}
	},function(){
		
	});
}

function selectAll(obj){
	 if ($("#checkIcon-1").hasClass("checked")) {
	        $("#checkIcon-1").removeClass("checked");
	        
	        $("#notEmptyCart #cart_id").each(function(){
	        	$(this).removeClass("checked");
	        	$(this).find("#cart_ids").val("");
	        });
	        
	        $("#submitForm").addClass("disabled");
	        $("#submitForm").removeAttr("onclick");
			$("#cart_oriPrice").text("0.00");
	    } else {
	        $("#checkIcon-1").addClass("checked");
	        
	        $("#notEmptyCart #cart_id").each(function(){
	        	$(this).addClass("checked");
	        	$(this).find("#cart_ids").val($(this).attr("flag_id"));
	        });
	        
	        $("#submitForm").removeClass("disabled");
	        $("#submitForm").attr("onclick","submitThisForm();");
	        var total_money = 0;
	    	$(".minSumPrice").map(function(){
	    		total_money += parseFloat($(this).val());
	    	});
	    	$("#cart_oriPrice").text(total_money.toFixed(2));
	   }
}

function changeSelected(obj,cart_id){
	 if ($(obj).hasClass("checked")) {
		 	$(obj).removeClass("checked");
		 	$(obj).find("#cart_ids").val("");
		 	var flag = 0;
		 	$("#notEmptyCart #cart_id").each(function(){
		 		if(($(this).hasClass("checked"))){
		 			flag= 1;
		 		}
			});
		 	
		 	if(flag == 0){
		 		 $("#checkIcon-1").removeClass("checked");
		 		 $("#submitForm").addClass("disabled");
		         $("#submitForm").removeAttr("onclick");
		 	}
		 	var total_money = parseFloat($("#cart_oriPrice").text());
		 	var total_money_every = parseFloat($(obj).parent().parent().next().find(".minSumPrice").val());
		 	
	    	$("#cart_oriPrice").text((total_money - total_money_every).toFixed(2));
	    	
	    } else {
	    	$(obj).addClass("checked");
	    	$(obj).find("#cart_ids").val(cart_id);
	    	var flag = 0;
	    	$("#notEmptyCart #cart_id").each(function(){
		 		if(!$(this).hasClass("checked")){
		 			flag= 1;
		 		}
			});
		 	if(flag == 0){
		 		 $("#checkIcon-1").addClass("checked");
		 	}
		 	$("#submitForm").removeClass("disabled");
		    $("#submitForm").attr("onclick","submitThisForm();");
		 	
			var total_money = parseFloat($("#cart_oriPrice").text());
			var total_money_every = parseFloat($(obj).parent().parent().next().find(".minSumPrice").val());
	    	$("#cart_oriPrice").text((total_money + total_money_every).toFixed(2));
	   }
}






