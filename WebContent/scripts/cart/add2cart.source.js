/**
 * jQuery.add2cart 
 * Date: 2013/03/29
 * @author ethanwooblog@gmail.com
 * @version 1.0.0
 **/

(function($) {

	$.extend( {
		add2cartbuynow : function(source_id, url) {
			
			var source = $('#' + source_id);
			
			var shadow = $('#' + source_id + '_shadow');
			var datas = source.attr('longdesc').split("#@");
			var pd_name = datas[0];
			var pd_price = datas[1];
			var pd_id = datas[2];
			var cls_name = datas[3];
			var cls_id = datas[4];
			var cart_type = datas[5];
			var entp_id = datas[6];
			var comm_id = datas[7];
			var comm_name = datas[8];
			var comm_weight = datas[9];
			var comm_tczh_id = $("#comm_tczh_id").val();
			var comm_tczh_price = $("#comm_tczh_price").val();
			var delivery_p_index = $("#delivery_p_index").val();
			var comm_services = $("#comm_services").val();
			var pd_count = $("#pd_count").val();	
			
			
			//如果有套餐,则用套餐价格代替"商品参考价格(默认)"
			if(comm_tczh_price!=''&&comm_tczh_price!=null){
				pd_price = comm_tczh_price;
			}
			
			$.ajax({
				type: "POST",
				url: "CsAjax.do",
				data: "method=saveCartInfo&pd_id=" + pd_id + "&pd_price="  + pd_price + "&pd_name=" +pd_name+ "&cls_id="  + cls_id + "&cls_name=" + cls_name + "&cart_type=" + cart_type + "&entp_id=" + entp_id + "&comm_id=" + comm_id + "&comm_name=" + comm_name + "&comm_tczh_id=" + comm_tczh_id + "&comm_tczh_price=" + comm_tczh_price + "&delivery_p_index=" + delivery_p_index + "&comm_services=" + comm_services + "&comm_weight=" + comm_weight + "&pd_count=" + pd_count ,
				dataType: "json",
				error: function(request, settings) {alert("\u6570\u636e\u52a0\u8f7d\u8bf7\u6c42\u5931\u8d25\uff01"); },
				success: function(data) {
					if (data.result) {
						location.href = url;
					} else{
						alert("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
					}
				}
			});
			
		},
		add2cart : function(source_id, target_id, callback) {

			var source = $('#' + source_id);
			var target = $('#' + target_id);

			var shadow = $('#' + source_id + '_shadow');
			var datas = source.attr('longdesc').split("#@");
			var pd_name = datas[0];
			var pd_price = datas[1];
			var pd_id = datas[2];
			var cls_name = datas[3];
			var cls_id = datas[4];
			var cart_type = datas[5];
			var entp_id = datas[6];
			var comm_id = datas[7];
			var comm_name = datas[8];
			var comm_weight = datas[9];

			var comm_tczh_id = $("#comm_tczh_id").val();
			var comm_tczh_price = $("#comm_tczh_price").val();
			var delivery_p_index = $("#delivery_p_index").val();
			var comm_services = $("#comm_services").val();
			var pd_count = $("#pd_count").val();	
			
			var gwcCount= $("#gwcCount").text();
			$("#gwcCount").text(parseFloat(gwcCount)+parseFloat(pd_count));
			
			//如果有套餐,则用套餐价格代替"商品参考价格(默认)"
			if(comm_tczh_price!=''&&comm_tczh_price!=null){
				pd_price = comm_tczh_price;
			}
			
			
			if (!shadow.attr('id')) {
				$('body').prepend(
						'<div id="' + source.attr('id')
								+ '_shadow" class="flyEffect">' + pd_name
								+ '</div>');
				var shadow = $('#' + source.attr('id') + '_shadow');
			}
			if (!shadow) {
				alert('Cannot create the shadow div');
			}

			shadow.width(source.css('width')).css(
					'top', source.offset().top).css('left',
					source.offset().left).css('opacity', 1).show();
			shadow.css('position', 'absolute');
	        $.ajax({
	    		type: "POST",
	    		url: "CsAjax.do",
	    		data: "method=saveCartInfo&pd_id=" + pd_id + "&pd_price="  + pd_price + "&pd_name=" +pd_name+ "&cls_id="  + cls_id + "&cls_name=" + cls_name + "&cart_type=" + cart_type + "&entp_id=" + entp_id + "&comm_id=" + comm_id + "&comm_name=" + comm_name + "&comm_tczh_id=" + comm_tczh_id + "&comm_tczh_price=" + comm_tczh_price + "&delivery_p_index=" + delivery_p_index + "&comm_services=" + comm_services + "&comm_weight=" + comm_weight + "&pd_count=" + pd_count ,
	    		dataType: "json",
	    		error: function(request, settings) {alert("\u6570\u636e\u52a0\u8f7d\u8bf7\u6c42\u5931\u8d25\uff01"); },
	    		success: function(data) {
	        		if (data.result) {
	        			shadow.animate( {
	        				width : source.innerWidth(),
	        				top : target.offset().top,
	        				left : target.offset().left
	        			}, {
	        				duration : 500
	        			}).animate( {
	        				opacity : 0
	        			}, {
	        				duration : 500,
	        				complete : function(){
	        				shadow.remove();
	        				$("#cartnopd").remove();
	        				if ($('#li_'+comm_tczh_id).length <=0){
	        					var htmldata = '<li id="li_'+comm_tczh_id+'"><span><a class="del" onclick="delCart(this,'+data.result+');">\u5220\u9664</a></span><span class="cartcount" id="pd_count_'+comm_tczh_id+'">'+pd_count+'</span><span> ￥'+pd_price+'&nbsp;×</span><font class="ofCart" title="'+pd_name+'">'+pd_name+'</font></li>';
	        					$("#cartul").prepend(htmldata);
	        				} else {
	        					var $pc = $('#pd_count_'+comm_tczh_id);
	        					var pd_count_text = $pc.text();
	        					$pc.text(parseFloat(parseFloat(pd_count_text)+parseFloat(pd_count)));
	        				}
	        			}
	        			});
	  
	            	} else{
	            		alert("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
	                }
	    		}
	   		 });

		}
	});
})(jQuery);

function delCart(thisobj, cart_id) {
	if (null != cart_id || undefined != cart_id) {
		$.post("CsAjax.do?method=delCart",{cart_id : cart_id},function(data){
			if (data.result) {
				$(thisobj).parent().parent().remove();
				var gwcCount= $("#gwcCount").text();
				var cartCount = data.cartCount;
				if(isNaN(cartCount)){
					cartCount = 0;
				}
				$("#gwcCount").text(cartCount);
			}
		    if($("li", "#cartul").size() <= 0) {
		    	$("#cartul").prepend('<li id="cartnopd">\u8d2d\u7269\u8f66\u4e2d\u8fd8\u6ca1\u6709\u5546\u54c1\uff0c\u8d76\u7d27\u9009\u8d2d\u5427\uff01</li>');
		    }
		});
	} 


}
