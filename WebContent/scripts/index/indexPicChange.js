(function($) {
	$.fn.picChange = function(options) {
		var defaults = {
				id : "focus"
			}, opts = $.extend( {}, defaults, options);
	var sWidth = $("#" + opts.id).width(); 
	var len = $("#" + opts.id + " ul li").length;
	var index = 0;
	var picTimer;
	
	
	var btn = "<div class='btnBg'></div><div class='btn'>";
	for(var i=0; i < len; i++) {
		btn += "<span></span>";
	}
	btn += "</div>"
	$("#" + opts.id).append(btn);
	$("#" + opts.id + " .btnBg").css("opacity",0.5);
	
	
	$("#" + opts.id + " .btn span").mouseenter(function() {
		index = $("#" + opts.id + " .btn span").index(this);
		showPics(index);
	}).eq(0).trigger("mouseenter");
	
	
	$("#" + opts.id + " ul").css("width",sWidth * (len + 1000));
	
	
	$("#" + opts.id + " ul li div").hover(function() {
		$(this).siblings().css("opacity",0.7);
	},function() {
		$("#" + opts.id + " ul li div").css("opacity",1);
	});
	
	
	$("#" + opts.id).hover(function() {
		clearInterval(picTimer);
	},function() {
		picTimer = setInterval(function() {
			if(index == len) {
				showFirPic();
				index = 0;
			} else { 
				showPics(index);
			}
			index++;
		},3000);
	}).trigger("mouseleave");
	
	
	function showPics(index) { 
		var nowLeft = -index*sWidth; 
		$("#" + opts.id + " ul").stop(true,false).animate({"left":nowLeft},500); 
		$("#" + opts.id + " .btn span").removeClass("on").eq(index).addClass("on");
	}
	
	function showFirPic() {
		$("#" + opts.id + " ul").append($("#" + opts.id + " ul li:first").clone());
		var nowLeft = -len*sWidth; 
		$("#" + opts.id + " ul").stop(true,false).animate({"left":nowLeft},500,function() {
			
			$("#" + opts.id + " ul").css("left","0");
			$("#" + opts.id + " ul li:last").remove();
		}); 
		$("#" + opts.id + ".btn span").removeClass("on").eq(0).addClass("on"); 
	}
	};
})(jQuery);