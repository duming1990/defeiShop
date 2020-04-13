function updateState(action, mtd, id, state){
	var submit2 = function (v, h, f) {
		if (v == 'ok') {
			$.ajax({
			     type:"POST",
			  	 cache: false,
			     url:action,
			     data:"method=" + mtd + "&id=" + id + "&state=" + state,
			  	 dataType: "json",
			     error: function(request, settings){alert("Ajax提交错误！");},
			     success:function(data){
			    	$.jBox.tip("加载中...", 'loading');
			    	if(data.is_ok == '1'){
			    		
			    		jBox.tip('操作成功！','success');
			    		//jBox.tip("操作成功！");
				    	//location.reload();//重载页面
			    		$("#bottomPageForm").submit();//重载页面
				    	//$("#bg_" + id).attr("value","订单已完成").attr("disabled",true).attr("title","订单已完成");
				    	
			    	}else {
		  		    	jBox.tip('操作失败！','error');
			    	}
		   		}
		   });	
		}
		return true; //close
	};
	$.jBox.confirm("确定执行该操作吗？", "确定提示", submit2);
};

function confirmDelete(action, mtd, queryString){
	var submit2 = function (v, h, f) {
		if (v == 'ok') {
			location.href = action + "?method=delete&" + queryString;
		}else{
			return true;
		}
	};
	$.jBox.confirm("订单删除后将无法还原，确定执行该操作吗？", "确定提示", submit2);
};


$("#pay_money").click(function(){
	
	var submit = function (v, h, f) {
	    if (v == true) {
	    	//location.reload();//重载页面
	    	$("#bottomPageForm").submit();//重载页面
	    } else {
	    	var helpUrl = "http://"+app_domain+"/IndexHelpInfo.do?method=view&h_mod_id=10020300";
	    	parent.window.open(helpUrl);
	    	//parent.location.href=helpUrl;//self
	    	//alert('支付失败');
	       //$.jBox.prevState();
		}
	    return true;
	};
	// 自定义按钮
	$.jBox.confirm("请您在新打开的页面进行支付,支付完成前请不要关闭此窗口", "支付", submit, { width: 400,height: 120,buttons: { '支付成功': true, '支付失败': false}});
});