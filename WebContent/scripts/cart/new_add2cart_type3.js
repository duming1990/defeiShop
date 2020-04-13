/**
 * jQuery.add2cart 
 * Date: 2013/03/29
 * @author ethanwooblog@gmail.com
 * @version 1.0.0
 **/
//立即购买
function addcartAndBuy(comm_id){
	var pd_count = $("#pd_count").val();
	var comm_tczh_id = $("#comm_tczh_id").val();
	location.href= ctxpath + "/IndexShoppingNoCar.do?comm_id=" + comm_id + "&pd_count=" + pd_count + "&comm_tczh_id=" + comm_tczh_id;
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

