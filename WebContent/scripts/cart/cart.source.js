/**
 * cart.js 
 * Date: 2013/03/29
 * @author wuyang@aiisen.com
 * @version 1.0.0
 **/

function delCart(thisobj, cart_id) {
	if(confirm("\u786e\u5b9a\u4ece\u8d2d\u7269\u8f66\u4e2d\u5220\u9664\u6b64\u5546\u54c1\uff1f")) {
		if (null != cart_id || undefined != cart_id) {
			$.post("CsAjax.do?method=delCart",{cart_id : cart_id},function(data){
				if (data.result) {
					$(thisobj).parent().parent().remove();
					var total_money = 0;
					$(".minSumPrice").map(function(){
						total_money += parseFloat($(this).text());
					});
					$(".minServiceSumPrice").map(function(){
						total_money += parseFloat($(this).text());
					});
					$("#total_money").text(total_money.toFixed(2));
				}
			    if($(".carts_tr").size() <= 0) {
			    	$("#cart_content").empty().prepend('<div class="cart-inner cart-empty"><div class="message"><p>\u8d2d\u7269\u8f66\u5185\u6682\u65f6\u6ca1\u6709\u5546\u54c1\uff0c\u60a8\u53ef\u4ee5<a href="http://'+app_domain+'" target="_blank">\u53bb\u9996\u9875</a>\u6311\u9009\u559c\u6b22\u7684\u5546\u54c1</p></div></div>');
			    }
			});
		} 
	}
}
function delAllCart() {
	if(confirm("\u786e\u5b9a\u4ece\u8d2d\u7269\u8f66\u4e2d\u5220\u9664\u6240\u6709\u5546\u54c1\uff1f")) {
		$.post("CsAjax.do?method=delCart",{isClearAll : "true"},function(data){
			if (data.result) {
				$("#cart_content").empty().prepend('<div class="cart-inner cart-empty"><div class="message"><p>\u8d2d\u7269\u8f66\u5185\u6682\u65f6\u6ca1\u6709\u5546\u54c1\uff0c\u60a8\u53ef\u4ee5<a href="http://'+app_domain+'" target="_blank">\u53bb\u9996\u9875</a>\u6311\u9009\u559c\u6b22\u7684\u5546\u54c1</p></div></div>');
			}
		});
	}
}

function calcCartMoney (JqObj, pd_price, cart_id, addNum, isBlur) {
	if (null == addNum || undefined == addNum) {addNum = 0;}
	if (null == isBlur || undefined == isBlur) {isBlur = false;}
	var ajax = true;
	var _regInt = /^[1-9]\d{0,2}$/g;;
	var _regCurr = /^[\+]?\d*?\.?\d*?$/;
	var $this = JqObj;
	var count = parseFloat($this.val());
	if (!_regInt.test(count)) {
		alert("输入的数量有误,应为[1-999]");
		$this.val(1);
		count = 1;
		ajax = false;
	}
	var pd_max_count = $this.next().val();
	if (null != pd_max_count && undefined != pd_max_count&& "" != pd_max_count) {
		if ((count > pd_max_count)) {
			alert("很抱歉，该产品库存不足，货到后我们将第一时间通知您！");
			$this.val(1);
			count = 1;
			ajax = false;
		}
	}
	count += parseFloat(addNum);
	if(count<=0){count=1;ajax = false;}
	$this.val(count);
	var price = parseFloat(pd_price);
	if(isNaN(price)){ ajax = false;return false;}
	
	var matflow_price = 0;
	var cur_v = $("#" + cart_id + "matflow_price").val();
	if (!(null == cur_v || undefined == cur_v)) {
		var cur_v_array = cur_v.split(",");
	    matflow_price = parseFloat(cur_v_array[1]); ;
		if (null == matflow_price || undefined == matflow_price) {matflow_price =0;}
	}
	
	$("#pdcount_id_"+ cart_id).html(count);
	$("#" + cart_id + "minSumPrice").text((count * price) + parseFloat(matflow_price));

	var cart_service_sum_price = 0;
	 $("input[name^='service_" + cart_id + "_']").map(function(){
		cart_service_sum_price += parseFloat($(this).val());
		});

	
	
	
	$("#" + cart_id + "_minServiceSumPrice").text((count * cart_service_sum_price));
	var total_money = 0;
	$(".minSumPrice").map(function(){
		// alert($(this).text());
		total_money += parseFloat($(this).text());
	});
	$(".minServiceSumPrice").map(function(){
		//alert("====="+$(this).text());
		total_money += parseFloat($(this).text());
	});
	$("#total_money").text(total_money.toFixed(2));
	
	if(ajax && !isBlur){
		$.post("CsAjax.do?method=updateCartInfo",{id : cart_id,pd_count:count},function(data){
    		if (!data.result) {
    			alert("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
        	}
		});
	}
}

function showAreaName(){
	var rel_province = $("#rel_province option:selected").val() =="" ? "" :$("#rel_province option:selected").text();
	var rel_city = $("#rel_city option:selected").val() =="" ? "" :$("#rel_city option:selected").text();
	var rel_region = $("#rel_region option:selected").val() =="" ? "" :$("#rel_region option:selected").text();
	var rel_region_four = $("#rel_region_four option:selected").val() =="" ? "" :$("#rel_region_four option:selected").text();
	$("#area_span").empty().html(rel_province+"&nbsp;"+rel_city+"&nbsp;"+rel_region+"&nbsp;"+rel_region_four+"&nbsp;");
}

function saveShippingAddress(JQform) {
	if(Validator.Validate(JQform.get(0), 3)){
        $.ajax({
    		type: "POST",
    		url: "CsAjax.do",
    		data: "method=saveShippingAddress&" + JQform.serialize(),
    		dataType: "json",
    		error: function(request, settings) {alert("\u6570\u636e\u52a0\u8f7d\u8bf7\u6c42\u5931\u8d25\uff01"); },
    		success: function(data) {
        		if (data.result) {
        			var id = data.result;
        			var data = [];
        			data.push(id);
        			data.push($("#rel_name").val());
        			data.push($("#rel_phone").val());
        			data.push($("#rel_province option:selected").val());
        			data.push($("#rel_city option:selected").val());
        			data.push($("#rel_region option:selected").val());
        			data.push($("#rel_addr").val());
        			data.push($("#rel_email").val()=="" ?" ":$("#rel_email").val());
        			data.push($("#rel_zip").val()=="" ?" ":$("#rel_zip").val());
        			data.push($("#area_span").html());
           			data.push($("#rel_region_four option:selected").val());
        			var datas = data.join("@#");
        			$(".wd_shr_nr").removeClass("sjrxx");
        			var html = 
        			 '<div class="wd_shr_nr sjrxx" ><div>' +
        		     '<input checked="checked" onclick="showShippingAddress('+id+');" title="'+datas+'" type="radio" name="radio_sa" id="radio_sa_'+id+'" value="'+id+'" />'
        		     +'<label for="radio_sa_'+id+'">&nbsp;'+$("#area_span").html()+$("#rel_addr").val()+'（收货人：'+$("#rel_name").val()+'&nbsp;&nbsp;手机号码：'+$("#rel_phone").val()+'）</label>'
        		     +'[<a href="javascript:void(0)" onclick="delShippingAddress(this,'+id+')">删除</a>]</div>'
        		    +'</div>';
        			$("#shipping_address").prepend(html);
        			$("#shipping_address_id").val(id);
        			$("#div_address_detail").dialog( "close" );
        			var p_index = $("#rel_region").val();
        			var foru_p_index = $("#rel_region_four").val();
        			if(foru_p_index!=""&&foru_p_index!=null){
        				p_index = foru_p_index;
        			}
        			getMatflowPrice(p_index);

        			
        			
        			
            	} else{
            		alert("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
                }
    		}
   		 });
	}
}

function delShippingAddress(thisobj, id) {
	if (null != id || undefined != id) {
		$.post("CsAjax.do?method=delShippingAddress", {
			id : id
		}, function(data) {
			if (data.result) {
				if($("input[name='radio_sa']", $(thisobj).parent()).attr("checked")=='checked'){
					$("#shipping_address_id").val("");
				}
				$(thisobj).parent().parent().remove();
			}
		});
	}
}

function showShippingAddress(id) {
	$(".wd_shr_nr").removeClass("sjrxx");
	var $this = $("#radio_sa_" + id);
	$this.parent().parent().addClass("sjrxx");
	var datas = $this.attr('alt').split("@#");
	var id = datas[0];
	var rel_name = datas[1];
	var rel_phone = datas[2];
	var rel_province = datas[3];
	var rel_city = datas[4];
	var rel_region = datas[5];
	var rel_addr = datas[6];
	var rel_email = datas[7];
	var rel_zip = datas[8];
	var p_name = datas[9];
	var rel_region_four = datas[10]; 
	//alert(datas);
	$("#rel_name").val(rel_name);
	$("#rel_phone").val(rel_phone);
	$("#rel_province").attr("selectedValue",rel_province).val(rel_province);
	$("#rel_city").attr("selectedValue",rel_city).val(rel_city);
	$("#rel_region").attr("selectedValue",rel_region).val(rel_region);
	$("#rel_region_four").attr("selectedValue",rel_region_four).val(rel_region_four);
	$("#rel_province").change();
	$("#rel_addr").val(rel_addr);
	$("#rel_email").val(rel_email);
	$("#rel_zip").val(rel_zip);
	$("#area_span").html(p_name);
	$("#shipping_address_id").val(id);

	var p_index = $("#rel_region").val();
	var foru_p_index = $("#rel_region_four").val();
	if(foru_p_index!=""&&foru_p_index!=null){
		p_index = foru_p_index;
	}
	getMatflowPrice(p_index);
}

function selectMatflowPrice(obj, cart_id) {
	$this = $(obj);
	var cur_v = $this.val();
	var cur_v_array = cur_v.split(",");
	
	pd_matflow_id = cur_v_array[0];
	cur_val = parseFloat(cur_v_array[1]);
	
	$.post("CsAjax.do?method=updatePdMatflow",{pd_matflow_id : pd_matflow_id, cart_id:cart_id},function(data){
		if (!data.result) {
			alert("\u5f88\u9057\u61be\uff0c\u7531\u4e8e\u7cfb\u7edf\u7e41\u5fd9\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
		} else {
			var last_s_f = parseFloat($this.next().val());
			var $sumprice = $("#" + cart_id + "minSumPrice");
			var cur_sum_p = parseFloat($sumprice.text());
			$sumprice.text(cur_sum_p-last_s_f+cur_val);
			$this.next().val(cur_val);
			var total_money = 0;
			$(".minSumPrice").map(function(){
				total_money += parseFloat($(this).text());
			});
			$(".minServiceSumPrice").map(function(){
				total_money += parseFloat($(this).text());
			});
			$("#total_money").text(total_money.toFixed(2));
		}
	});

}
