function updateState(action, mtd, id, state) {
	M._confirm("确定执行该操作吗？",["取消",""],["确定",""],function(){
		$.ajax({
			type : "POST",
			cache : false,
			url : action,
			data : "method=" + mtd + "&id=" + id + "&state=" + state,
			dataType : "json",
			error : function(request, settings) {
				mui.toast("Ajax提交错误！")
			},
			success : function(data) {
				if (data.is_ok == "1") {
					mui.toast("操作成功！");
					window.location.reload();
				} else {
					mui.toast("操作失败！");
				}
			}
		})
	},function(){
		
	});
}

function confirmDelete(action, mtd, id) {
	M._confirm("订单删除后将无法还原，确定执行该操作吗？",["取消",""],["确定",""],function(){
		$.ajax({
			type : "POST",
			cache : false,
			url : action,
			data : "method=" + mtd + "&id=" + id,
			dataType : "json",
			error : function(request, settings) {
				mui.toast("Ajax提交错误！");
			},
			success : function(data) {
				if (data.is_ok == "1") {
					mui.toast("操作成功！");
					window.location.reload();
				} else {
					mui.toast("操作失败！");
				}
			}
		})
	},function(){
		
	});
}
