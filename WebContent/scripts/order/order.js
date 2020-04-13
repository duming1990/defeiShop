function updateState(url, method, id, state, obj,is_entp) {
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("加载中...", "loading");
			$.ajax({
				type : "POST",
				cache : false,
				url : url,
				data : "method=" + method + "&id=" + id + "&state=" + state,
				dataType : "json",
				error : function(request, settings) {
					$.jBox.tip("系统繁忙，请稍后重试！", "error",{timeout:2000});
				},
				success : function(data) {
					if (data.ret == "1") {
						 $.jBox.tip(data.msg, "success",{timeout:1000});
						 window.setTimeout(function () {
							 $("#bottomPageForm").submit();
						 }, 1500);
//						
//						 if(state == -10){
//							 $("#order_" + id).fadeOut();
//						 } else {
//							 var order_state = $("#order_state_" + id).attr("data-order-state");
//							 var pay_type = $("#order_state_" + id).attr("data-pay-type");
//							 var order_type = $("#order_state_" + id).attr("data-order-type");
//							 if(state != order_state){
//								 order_state = state;
//							 }
//							 var txt = getOrderStateString(order_state, pay_type, order_type);
//							 $("#order_state_" + id).html(txt);
//							 $(obj).fadeOut();
//						 }
					} else {
						$.jBox.tip(data.msg, "error",{timeout:2000});
					}
				}
			})
		}
		return true
	};
	var tip = "确定执行该操作吗？";
	var tip1 = "";
	var isshixiao = $("#order_state_" + id).attr("date-isshixiao");
	var order_type = $("#order_state_" + id).attr("data-order-type");
	if(isshixiao == 1 && order_type == 10){// 虚拟订单才扣手续费
		tip1 = "<br/>订单已失效，将扣除3%的手续费！";
	}
	if(state == -10){
		tip = "确定要取消订单吗？" + tip1;
	}
	if(state == 15){
		tip = "确定要退款吗？退款金额将返还到您的账户余额中！" + tip1;
		if(is_entp){
			tip = "确定要取消订单吗？退款后金额将返还到消费者账户余额中！" + tip1;
		}
	}
	if(state == 50){
		tip = "确定要确认收货吗？" + tip1;
	}
	$.jBox.confirm(tip, "确定提示", submit2)
}
function updateStateForCancel(action, mtd, id, state,flag) {
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.ajax({
				type : "POST",
				cache : false,
				url : action,
				data : "method=" + mtd + "&id=" + id + "&state=" + state+ "&flag=" + flag,
				dataType : "json",
				error : function(request, settings) {
					$.jBox.tip("系统繁忙，请稍后重试！", "error",{timeout:2000});
				},
				success : function(data) {
					$.jBox.tip("加载中...", "loading");
					if (data.ret == "1") {
						$.jBox.tip(data.msg, "success",{timeout:1500});
						 window.setTimeout(function () {
							 $("#bottomPageForm").submit();
						 }, 2000);
						//$("#order_" + id).fadeOut();
					} else {
						$.jBox.tip(data.msg, "error",{timeout:2000});
					}
				}
			})
		}
		return true
	};
	var tip = "确定执行该操作吗？";
	if(flag ==1){
		tip ="你确定要进行取消订单吗?";
	}
	$.jBox.confirm(tip, "确定提示", submit2)
}
function confirmDelete(action, mtd, queryString) {
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			location.href = action + "?method=delete&" + queryString;
		} else {
			return true;
		}
	};
	$.jBox.confirm("订单删除后将无法还原，确定执行该操作吗？", "确定提示", submit2);
}
$("#pay_money").click(function() {
	var submit = function(v, h, f) {
		if (v == true) {
			$("#bottomPageForm").submit();
		} else {
			var helpUrl = "http://"+app_domain+"/IndexHelpInfo.do?method=view&h_mod_id=10020300";
			parent.window.open(helpUrl);
		}
		return true
	};
	$.jBox.confirm("请您在新打开的页面进行支付,支付完成前请不要关闭此窗口", "支付", submit,{
		width : 400,
		height : 120,
		buttons : {
			"支付成功" : true,
			"支付失败" : false
		}
	});
});

function delayShouhuo(action,id,obj) {
	var submit2 = function(v, h, f) {
		if (v == "ok") {
			$.jBox.tip("加载中...", "loading");
			$.ajax({
				type : "POST",
				cache : false,
				url : action,
				data : "method=delayShouhuo&id=" + id,
				dataType : "json",
				error : function(request, settings) {
					$.jBox.tip("系统繁忙，请稍后重试！", "error",{timeout:2000});
				},
				success : function(data) {
					if (data.ret == "1") {
						 $.jBox.tip(data.msg, "success",{timeout:1000});
						 window.setTimeout(function () {
							 $("#bottomPageForm").submit();
						 }, 1500);
					} else {
						$.jBox.tip(data.msg, "error",{timeout:2000});
					}
				}
			})
		}
		return true
	};
	
	var tip = "延迟收货将再延后3天，确定延迟收货吗？";
	$.jBox.confirm(tip, "确定提示", submit2);
}