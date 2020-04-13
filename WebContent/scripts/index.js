$(document).ready(function(){
	
//	$("#J-nav__trigger").mouseover(function(){
//		if($("#nav-Parent-div").is(":hidden")){
//			 $(".F-glob-caret-down").hide();
//			 $(".F-glob-caret-up").show();
//			 $("#nav-Parent-div").slideDown();
//		 }
//	}).mouseleave(function(){
//		 $(".F-glob-caret-down").show();
//		 $(".F-glob-caret-up").hide();
//		$("#nav-Parent-div").slideUp();
//	});
//	$(".memberbox").mouseover(function(){
//		if($(".memberbox").is(":hidden")){
//			$(".memberbox").slideDown();
//		}
//	}).mouseleave(function(){
//		$(".memberbox").slideUp();
//	});
	
	
	$("#member_top_news").find("h1").each(function(index){
		$(this).click(function(){
			$(this).addClass("current").siblings().removeClass("current");
			$("#newsContent" + index).show().siblings().hide();
		});
	});
	
	
	$("#closedBannerRight").click(function(){
		$("#membercont").hide();
	});
	
//	$('#wrap2').marquee({
//        auto: true,
//        interval: 1000,
//        speed: 2000,
//        showNum: 9,
//        stepLen: 1,
//        type: 'vertical'
//    });
	
	$("#gotoUserCenter li").each(function(){
	$(this).click(function(){
		var modId = $(this).attr("data-mod-id");
		var parParId = $(this).attr("data-parPar-id");
		var parId= $(this).attr("data-par-id");
	    $.post("CsAjax.do?method=getUrlLinkModId",{mod_id:modId},function(data){
			if(data.ret == 1){
				var parId_cookie =parParId +"," + parId + ","+ modId + "," + data.data_url;
				if ($.isFunction($.cookie)) $.cookie("parId_cookie", parId_cookie, { path: '/' });
				location.href= "manager/customer/index.shtml";
			}
		});
	});
   });
	getCookiePindex();
	
	$("#showAreaMore").live("click",function(){
		if($(this).hasClass("getMore")){
			$("#showArea").css("height","auto");
			$(this).removeClass("getMore");
			$("#areaMore").text("隐藏");
		}else{
			$("#showArea").css("height","24px");
			$(this).addClass("getMore");
			$("#areaMore").text("更多");
		}
	});
	
	
	$(".main_visual").hover(function(){
		$("#btn_prev,#btn_next").fadeIn()
		},function(){
		$("#btn_prev,#btn_next").fadeOut()
		})
	$("#main_imageUl").touchSlider({
		flexible : true,
		speed : 200,
		btn_prev : $("#btn_prev"),
		btn_next : $("#btn_next"),
		paging : $(".flicking_con a"),
		counter : function (e) {
			$(".flicking_con a").removeClass("on").eq(e.current-1).addClass("on");
		}
	});
	
	$("#main_imageUl").bind("mousedown", function() {
		$dragBln = false;
	})
	$("#main_imageUl").bind("dragstart", function() {
		$dragBln = true;
	})
	$("#main_imageUl a").click(function() {
		if($dragBln) {
			return false;
		}
	})
	timer = setInterval(function() { $("#btn_next").click();}, 5000);
	$(".main_visual").hover(function() {
		clearInterval(timer);
	}, function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	})
	$("#main_imageUl").bind("touchstart", function() {
		clearInterval(timer);
	}).bind("touchend", function() {
		timer = setInterval(function() { $("#btn_next").click();}, 5000);
	})
});

function clearCommHistory(){
	$.cookie("browseCommInfos",null,{path:"/"});
	$("#J-my-history-menu").remove();
	$(".dropdown--history").removeClass("dropdown--open");
}

var timeConfig = null;
$("#view_history_info").mouseover(function(){
	timeConfig = setTimeout(function(){
	$.ajax({
		type: "POST",
		url: "CsAjax.do",
		data: "method=getCommInfoCoookies",
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
			var html="";
			if(datas.list != "" && null != datas.list && datas.list.length > 0){
			    html = '<ul class="list-wrapper">';
				for(var x in datas.list){
					var cur = datas.list[x];
					html += '<li class="dropdown-menu__item">';
					html += '<a href="item-'+cur.comm_id+'.shtml" title="' + cur.comm_name + '" target="_blank"  class="deal-link">';
					html += '<img class="deal-cover" src="'+ cur.comm_pic +'" width="80" height="50"></a>';
					html += '<h5 class="deal-title">';
					html += '<a href="" title="" target="_blank" class="deal-link">' + cur.comm_name + '</a></h5>';
					html += '<a class="deal-price-w" target="_blank" href="item-'+cur.comm_id+'.shtml">';
					html += '<em class="deal-price">¥'+ cur.comm_price +'</em>';
					html += '<span class="old-price color-weaken">'+ cur.price_ref +'</span></a>';
					html += '</li>';
				   }
	              html += "</ul>";
				  html += '<p id="J-clear-my-history" class="clear"><a class="clear__btn" href="javascript:void(0)" onclick="clearCommHistory();">清空最近浏览记录</a></p>';
			}else{
				html += '<p class="dropdown-menu--empty">暂无浏览记录</p>';
			}
			$("#J-my-history-menu").html(html);
			$("#J-my-history-menu").show();
		}
	});
	}, 1000);
}).mouseout(function(){
	 if (timeConfig) {
	        clearTimeout(timeConfig);
	 }
});

var timeConfigTwo = null;
$("#view_cart_info").mouseover(function(){
	timeConfigTwo = setTimeout(function(){
	$.ajax({
		type: "POST",
		url: "CsAjax.do",
		data: "method=ViewGwc",
		dataType: "json",
		error: function(request, settings) {},
		success: function(datas) {
			var html="";
			if(datas.list != "" && null != datas.list && datas.list.length > 0){
			    html = '<ul class="list-wrapper">';
				for(var x in datas.list){
					var cur = datas.list[x];
					html += '<li class="dropdown-menu__item">';
					html += '<a href="item-'+cur.comm_id+'.shtml" title="' + cur.comm_name + '" target="_blank"  class="deal-link">';
					html += '<img class="deal-cover" src="'+ cur.comm_pic +'" width="80" height="50"></a>';
					html += '<h5 class="deal-title">';
					html += '<a href="" title="" target="_blank" class="deal-link">' + cur.comm_name + '</a></h5>';
					html += '<p class="deal-price-w"><a href="javascript:void(0);" class="delete link--black__green" onclick=deleteOrder(this,' + cur.cart_id + ')>删除</a>';
					html += '<em class="deal-price">¥'+ cur.comm_price +'</em></p>';
					html += '</li>';
				   }
	              html += "</ul>";
				  html += '<p class="check-my-cart"><a class="btn btn-hot btn-small" href="cart.shtml">查看我的购物车</a></p>';
			}else{
				html += '<p class="dropdown-menu--empty">暂时没有商品</p>';
			}
			$("#J-my-cart-menu").html(html);
			$("#J-my-cart-menu").show();
		}
	});
	}, 1000);
}).mouseout(function(){
	 if (timeConfigTwo) {
	        clearTimeout(timeConfigTwo);
	 }
});

function deleteOrder(thisobj,cart_id){
	 $.post("CsAjax.do?method=delCart",{cart_id : cart_id},function(data){
			if (data.result) {
				$(thisobj).parent().parent().remove();
				var gwcCount= $("#gwcCount").text();
				var cartCount = data.cartCount;
				var pd_price_count = data.pd_price_count;
				if(isNaN(cartCount)){
					cartCount = 0;
				}
				$("#gwcCount").text(cartCount);
				var size = $("#J-my-cart-menu").find("ul li").length;
				if(size ==0){
					var html = '<p class="dropdown-menu--empty">暂时没有商品</p>';
					$("#J-my-cart-menu").html(html);
				}
				
			}
	});
}


$("#searchbtn").click(function(){
		var htype = $("#htype").val();
		location.href = "Search.do?htype=" + htype + "&keyword=" + $("input[name='search']").val();
});

$("input[name='search']").keydown(function(e){
	var htype = $("#htype").val();
	if(e.keyCode==13){
		location.href = "Search.do?htype=" + htype + "&keyword=" + $("input[name='search']").val();
	}
});

	
$(".site-mast__user-w li").each(function(index){
	$(this).mouseover(function(){
		$(this).addClass("dropdown--open");
	}).mouseout(function(){
		$(this).removeClass("dropdown--open");
	});
});
$(".basic-info li").each(function(index){
	$(this).mouseover(function(){
		$(this).addClass("dropdown--open dropdown--open-app");
	}).mouseout(function(){
		$(this).removeClass("dropdown--open dropdown--open-app");
	});
});
$(".search-box__tabs-container").mouseover(function(){
	$(this).addClass("search-box__tabs-container--over");
}).mouseout(function(){
	$(this).removeClass("search-box__tabs-container--over");
});
$(".search-box__tabs li").each(function(index){
	if(index == 0){
		$(this).mouseover(function(){
			$(this).parent().parent().addClass("search-box__tabs-container--over");
		}).mouseout(function(){
			$(this).parent().parent().removeClass("search-box__tabs-container--over");
		});
	}
	$(this).click(function(){
		$(this).addClass("search-box__tab--current").siblings().removeClass("search-box__tab--current");
		$(this).insertBefore($(this).prev());
		$(this).parent().parent().removeClass("search-box__tabs-container--over");
		$("#htype").val(index);
		if(index == 0){
			$(".search-box__input").attr("placeholder","请输入商品名称等");
		}else{
			$(".search-box__input").attr("placeholder","请输入商家名称等");
		}
	});
});

var options = {
serviceUrl : "CsAjax.do?method=getCommInfoListForSeach&&htype=" + $("input[name='htype']").val(),
maxHeight : 245,
width: 402,
delimiter : /(,|;)\s*/,
pageCount : 20,
animate : true,
forCommInfoList: true,
onSelect : function(value, data) {
	  location.href = "Search.do?htype=" + $("input[name='htype']").val() + "&keyword=" + value;
},
deferRequestBy : 0, 
params : {country : "Yes"}
};
var se = $("#keyword").autocomplete(options);


$("#ctrl-prv").click(function(){
	var silderCount = parseInt($("#silderCount").text());
	var liLength = $("#scrollListUl li").length;
	$("#scrollListUl li").each(function(index){
		if(silderCount ==(index + 1) ){
			if((silderCount-1) > 0){
			    $(this).prev().fadeIn().siblings().fadeOut();
				$("#silderCount").text(silderCount - 1);
			}else{
				$("#scrollListUl li").last().fadeIn().siblings().fadeOut();
				$("#silderCount").text(liLength);
			}
			return;
		}
	});
});

$("#ctrl-next").click(function(){
	var silderCount = parseInt($("#silderCount").text());
	var liLength = $("#scrollListUl li").length;
	$("#scrollListUl li").each(function(index){
		if(silderCount ==(index + 1) ){
			if(silderCount < liLength){
			    $(this).next().fadeIn().siblings().fadeOut();
				$("#silderCount").text(silderCount + 1);
			}else{
				$("#scrollListUl li").first().fadeIn().siblings().fadeOut();
				$("#silderCount").text(1);
			}
			return;
		}
	});
});

$("#nav").onePageNav();

$(window).scroll(function(){ 
	if ($(window).scrollTop()>500){ 
		$("#floor-elevator").css("top","0px");
	} else { 
		$("#floor-elevator").css("top","700px");
	} 
}); 
//$(window).scroll(function(){
//	var scrollHeight = ($(".content__header").height());
//	if($(window).scrollTop() >= scrollHeight){
//		$("#floor-elevator").css("top","0px");
//	}else{
//		$("#floor-elevator").css("top","700px");	
//	}
//});

$("#cata-nav .item").each(function(){
	$(this).mouseover(function(){
		$(this).addClass("selected");
		$(this).find(".cata-nav-layer").css("top",$(this).position().top - 1);
		$(this).find(".cata-nav-layer").show();
	}).mouseout(function(){
		$(this).removeClass("selected");
	    $(this).find(".cata-nav-layer").hide();
	});
});

function getCookiePindex(){
	  $.post("CsAjax.do?method=getCookiePindex",{},function(datas){
			if(datas.ret == "1"){
				var city_name = datas.p_name;
				if(city_name){
					$("#city_name").text(city_name);
					localStorage.setItem('p_index_city_info',datas.p_index);
					localStorage.setItem('p_name_city_info',datas.p_name);
					$.post("CsAjax.do?method=getAreaList",{p_index:datas.p_index},function(datas2){
						if(datas2.list){
						  var html='';
						    if(datas2.list.length > 9){
						    	 html +='<a class="filter-strip__all J-geo-more getMore" id="showAreaMore"><span id="areaMore">更多</span><span class="tri"></span></a>';
						    }
							for(var x in datas2.list){
							var cur = datas2.list[x];
							 html +='<li><a href="search.shtml?p_index='+ cur.p_index + '">' + cur.p_name +'</a></li>';
							}
							
							$("#areaList").append(html);
						}
					})
				}
			}
		});
	}

$("#colseAd").click(function(){
	$("#topAd").remove();
});

