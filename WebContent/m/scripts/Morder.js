function updateState(action, mtd, id, state, obj) {
	var submit2 = function() {
		
		mui.toast("加载中...");
		$.ajax({
			type : "POST",
			cache : false,
			url : action,
			data : "method=" + mtd + "&id=" + id + "&state=" + state,
			dataType : "json",
			error : function(request, settings) {
				mui.toast("系统繁忙，请稍后重试！",2000);
			},
			success : function(data) {
				if (data.ret == "1") {
					mui.toast(data.msg, 1000);
					 window.setTimeout(function () {
						 $("#bottomPageForm").submit();
					 }, 1500);
				} else {
					mui.toast(data.msg, 2000);
				}
			}
		})
		
	};
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
	Common.confirm(tip,["确定","取消"],function(){
		submit2
	},function(){
	});
}
function updateStateForCancel(action, mtd, id, state,flag) {
	var submit2 = function() {

		$.ajax({
			type : "POST",
			cache : false,
			url : action,
			data : "method=" + mtd + "&id=" + id + "&state=" + state+ "&flag=" + flag,
			dataType : "json",
			error : function(request, settings) {
				mui.toast("系统繁忙，请稍后重试！",2000);
			},
			success : function(data) {
				mui.toast("加载中...");
				if (data.ret == "1") {
					mui.toast(data.msg,1500);
					 window.setTimeout(function () {
						 $("#bottomPageForm").submit();
					 }, 2000);
					//$("#order_" + id).fadeOut();
				} else {
					mui.toast(data.msg,2000);
				}
			}
		})
	};
	var tip = "确定执行该操作吗？";
	if(flag ==1){
		tip ="你确定要进行取消订单吗?";
	}
	Common.confirm(tip,["确定","取消"],function(){
		submit2
	},function(){
	});
}
function confirmDelete(action, mtd, queryString) {
	var submit2 = function() {

		location.href = action + "?method=delete&" + queryString;
	
	};
	Common.confirm("订单删除后将无法还原，确定执行该操作吗？",["确定","取消"],function(){
		submit2
	},function(){
	});
}

