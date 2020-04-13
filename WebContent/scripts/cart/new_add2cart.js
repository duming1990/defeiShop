/**
 * jQuery.add2cart 
 * Date: 2013/03/29
 * @author ethanwooblog@gmail.com
 * @version 1.0.0
 **/
//立即购买
function addcartAndBuy(pdid){
	add2cart(pdid,true);
}
//加入购物车
function addcart(pdid){
	add2cart(pdid,false);
}
function add2cart(source_id,buy) {
	var source = $('#' + source_id);
	var datas = source.attr('longdesc').split("#@");
	var pd_name = datas[0];
	var pd_id = datas[1];
	var cls_name = datas[2];
	var cls_id = datas[3];
	var cart_type = datas[4];
	var entp_id = datas[5];
	var comm_id = datas[6];
	var comm_name = datas[7];
	var comm_weight = datas[8];
	var entp_id = datas[9];
	
	var delivery_p_index = $("#delivery_p_index").val();
	var pd_count = $("#pd_count").val();
	var freight_id = $("#freight_id").val();
	var comm_tczh_id = $("#comm_tczh_id").val();
	
    $.ajax({
		type: "POST",
		url: "CsAjax.do",
		async:false,
		data: "method=saveCartInfo&pd_id=" + pd_id  + "&pd_name=" +pd_name+ "&cls_id="  + cls_id + "&cls_name=" + cls_name + "&cart_type=" + cart_type + "&entp_id=" + entp_id + "&comm_id=" + comm_id + "&comm_name=" + comm_name + "&delivery_p_index=" + delivery_p_index + "&comm_weight=" + comm_weight + "&gm_pd_count=" + pd_count + "&fre_id=" + freight_id  + "&entp_id=" + entp_id +"&comm_tczh_id=" + comm_tczh_id,
		dataType: "json",
		error: function(request, settings) {}, 
		success: function(data) {
    		if (data.result) {
    			if(buy){
    				location.href= ctxpath + "/cart.shtml";
    			}else{
    				var content = $("#addCartSuccess").html();
    				$.dialog({
    					title:  "",
    					id:'cartSuccessTip',
    					width:  380,
    					height: 60,
    			        lock:true ,
    			        max:false,
    			        min:false,
    			        drag: false,
    			        resize: false,
    					content:content
    				});
    			}
        	}else{
        		if(null != data.msg && "" != data.msg){
        			$.jBox.tip(data.msg, 'info',{timeout:2000});
        		}
        	}
		}
	});
}

function calcCartMoney (JqObj, addNum, isBlur) {
	var pd_max_count = $("#pd_stock").val();
	if (null == addNum || undefined == addNum) {addNum = 0;}
	if (null == isBlur || undefined == isBlur) {isBlur = false;}
	var ajax = true;
	var _regInt = /^[1-9]\d{0,2}$/g;;
	var _regCurr = /^[\+]?\d*?\.?\d*?$/;
	var $this = JqObj;
	var count = parseFloat($this.val());
	if (!_regInt.test(count)) {
		$.jBox.alert("输入的数量有误,应为[1-999]", '提示');
		$this.val(1);
		count = 1;
	}
	count += parseFloat(addNum);
	if (null != pd_max_count && undefined != pd_max_count&& "" != pd_max_count) {
		if ((count > pd_max_count)) {
			$.jBox.alert('很抱歉，购买数量大于该产品当前库存，请调整购买数量！', '提示');
			$this.val(pd_max_count);
			count = pd_max_count;
		}
	}
	if(count<=0){count=1;}
	$this.val(count);
}

