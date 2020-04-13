$(document).ready(function(){
	if($('#search_input').val()){
		$('.c-form-search em').show();
	}
    $('#search_input').focus(function(){
        if ($(this).val) {
            $('.c-form-search em').show();
        }
    }).blur(function(){
        //搜索框删除图标
        if ($(this).val() == '') {
            $('.c-form-search em').hide();
        } else {
            $('.c-form-search em').show();
        }
    });
    $('.c-form-search em').click(function(a){
        a && a.preventDefault();
        $("#search_input").focus();
        $("#search_input").val('');
        $(this).hide();
    });
	
	
//    $("#changeSearchType a").each(function(){
//		$(this).click(function(){
//			var data_flag = $(this).attr("data-flag");
//			if($(this).siblings().is(":hidden")){
//				$(this).siblings().show();
//			}else{
//				
//				$("#htype").val(data_flag);
//				if(data_flag == 0){
//					$("#search_input").attr("placeholder","请输入商品名...");
//				}else{
//					$("#search_input").attr("placeholder","请输入商家名...");
//				}
//				
//				$(this).insertBefore($(this).prev());
//				$(this).siblings().hide();
//			}
//		});
//	});
    
    $("#changeSearchType").click(function(){
    	$showSearchType = $("#showSearchType");
    	if($showSearchType.hasClass("on")){
    		$showSearchType.removeClass("on").addClass("off");
    	}else{
    		$showSearchType.removeClass("off").addClass("on");
    	}
    });
    $("#changeSearchTypeHd").click(function(){
    	$showSearchType = $("#showSearchTypeHd");
    	if($showSearchType.hasClass("on")){
    		$showSearchType.removeClass("on").addClass("off");
    	}else{
    		$showSearchType.removeClass("off").addClass("on");
    	}
    });
    
    $("#showSearchType ul li").each(function(){
		$(this).click(function(){
		    var data_flag = $(this).attr("data-flag");
			$("#htype").val(data_flag);
			if(data_flag == 0){
				$("#search_input").attr("placeholder","请输入商品名...");
				$("#typeShowName").text("商品");
			}
			if(data_flag == 1){
				$("#search_input").attr("placeholder","请输入店铺名...");
				$("#typeShowName").text("店铺");
			}
			if(data_flag == 2){
				$("#search_input").attr("placeholder","请输入县域名...");
				$("#typeShowName").text("县域");
			}
			if(data_flag == 3){
				$("#search_input").attr("placeholder","请输入村子名...");
				$("#typeShowName").text("村子");
			}
			$("#showSearchType").removeClass("on").addClass("off");
	  });
    });
    $("#showSearchTypeHd ul li").each(function(){
    	$(this).click(function(){
    		var data_flag = $(this).attr("data-flag");
    		$("#hdtype").val(data_flag);
    		if(data_flag == 0){
    			$("#typeShowName").text("搜活动");
    		}else{
    			$("#typeShowName").text("搜全场");
    		}
    		$("#showSearchTypeHd").removeClass("on").addClass("off");
    	});
    });
    
    
    
});

function putInHistoryKeywords_all(keyword){
	$("#searchScope").val("1");
	Common.loading();
	_putInHistoryKeywords(keyword);
	return true;
	
}

function _putInHistoryKeywords(keyword){

	if(!keyword){
		keyword = $("#search_input").val();
	}
	if(keyword){
		var search_history_keywords = $.cookie("search_history_keywords");
		var history_words = search_history_keywords, history_words = history_words ? search_history_keywords.split(",") : [];
		history_words.push(keyword);
		history_words = uniqArray(history_words);
		history_words.length = 25 > history_words.length ? history_words.length : 25;
		if ($.isFunction($.cookie)) $.cookie("search_history_keywords", history_words.join(","), { path: '/' });
	}
}

function putInHistoryKeywords(keyword){
	$("#searchScope").val("0");
	Common.loading();	
	_putInHistoryKeywords(keyword);
	return true;
}
function delSearchHistory(keyword){
	if ($.isFunction($.cookie)) $.cookie("search_history_keywords", "", { path: '/' });
	M._loading()
	window.setTimeout(function () {
		window.location.reload()
	}, 1000);
}

function uniqArray(arr){
    var d = [], b = {}, a, f, len = arr.length;
    if (2 > len) 
        return arr;
    for (a = 0; a < len; a++) 
        f = arr[a], 1 !== b[f] && (d.push(f), b[f] = 1);
    return d
}
