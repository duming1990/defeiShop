function updateState(action, mtd, id, state, obj) {
//	var submit2 = function(v, h, f) {
//		if (v == "ok") {}
//		return true
//	};
	var tip = "确定执行该操作吗？";
	var tip1 = "";
	var isshixiao = $("#order_state_" + id).attr("date-isshixiao");
	if(isshixiao == 1){
		tip1 = "<br/>订单已失效，将扣除3%的手续费！";
	}
	if(state == -10){
		tip = "确定要取消订单吗？" + tip1;
	}
	if(state == -20){
		tip = "确定要退款吗？" + tip1;
	}
	//$.jBox.confirm(tip, "确定提示", submit2)
	$.dialog.confirm(tip, function(){

		$.ajax({
			type : "POST",
			cache : false,
			url : action,
			data : "method=" + mtd + "&id=" + id + "&state=" + state,
			dataType : "json",
			error : function(request, settings) {
				alert("Ajax提交错误！");
			},
			success : function(data) {
				//$.jBox.tip("加载中...", "loading");
				$.dialog.tips("加载中...",1, "loading.gif"); 
				if (data.ret == "1") {
					 // $.jBox.tip(data.msg, "success",{timeout:1000});
					 $.dialog.tips(data.msg,2, "success.gif");
					 window.setTimeout(function () {
						 $("#bottomPageForm").submit();
					 }, 1500);
//					
//					 if(state == -10){
//						 $("#order_" + id).fadeOut();
//					 } else {
//						 var order_state = $("#order_state_" + id).attr("data-order-state");
//						 var pay_type = $("#order_state_" + id).attr("data-pay-type");
//						 var order_type = $("#order_state_" + id).attr("data-order-type");
//						 if(state != order_state){
//							 order_state = state;
//						 }
//						 var txt = getOrderStateString(order_state, pay_type, order_type);
//						 $("#order_state_" + id).html(txt);
//						 $(obj).fadeOut();
//					 }
				} else {
					// $.jBox.tip(data.msg, "error",{timeout:2000});
					$.dialog.tips(data.msg,2, "tips.gif");
				}
			}
		})
	
	}, function(){
	    
	});
}

function confirmDelete(action, mtd, queryString) {
	$.dialog.confirm('订单删除后将无法还原，确定执行该操作吗？', function(){
		location.href = action + "?method=delete&" + queryString;
	}, function(){
	  
	});
}

$("#pay_money").click(function() {
	$.dialog({
	    id: 'testID',
	    content: 'hello world!',
	    button: [
	        {
	            name: '支付成功',
	            callback: function () {
	            	$("#bottomPageForm").submit();
	            },
	            focus: true
	        },
	        {
	            name: '支付失败',
	            callback: function () {
	            	var helpUrl = "http://"+app_domain+"/IndexHelpInfo.do?method=view&h_mod_id=10020300";
	    			parent.window.open(helpUrl);
	            }
	        }
	    ]
	});
	
});