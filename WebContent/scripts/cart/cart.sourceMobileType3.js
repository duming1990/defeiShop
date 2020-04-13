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
		if (((count+1) > pd_max_count)) {
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
		total_money = total_money + parseFloat($(this).val());
	});
	$("#cart_oriPrice").text(total_money.toFixed(2));
}

