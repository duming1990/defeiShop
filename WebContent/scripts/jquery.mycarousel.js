(function($) {
	
	$.fn.mycarousel = function(options) {
		var opts = $.extend($.fn.mycarousel.defaults, options || {});
		
		//var $info = $("#info");//degug  信息
		var $thisUl = $(this);
		var $content = $thisUl.parent().parent().parent().parent();
		var $prev = $content.find("#prev");
		var $next = $content.find("#next");
		$prev.attr("disabled", "true").addClass("navleftgray");
		return this.each(function() {
			var $this = $(this);
			var $li = $this.find("li");
			var width = $li.eq(0).width();
			opts.horizontalMarigin = parseFloat($li.eq(0).css("padding-right")) + parseFloat($li.eq(0).css("padding-left"));
			opts.realWidth = width + opts.horizontalMarigin;
			opts.liLength = $li.length;
			// alert(opts.liLength);
			opts.isShowButton = opts.liLength > opts.dispItems ? true : false;
			opts.horizontalMarigin = parseFloat($li.eq(0).css("margin-right"));

			//$info.append("[li width]:" + width).append(
			//		" [shownum]:" + opts.dispItems).append(
			//		"  [realWidth]:" + (opts.realWidth)).append("[opts.liLength]:" + (opts.liLength)).append("<br />");

			//$prev.removeAttr("disabled");
			//$next.removeAttr("disabled");
			
			// alert($prev.attr("class"));
			if (opts.isMinus && $prev.is(":visible") && "navleftgray" != $prev.attr("class")) {
				//alert(1);
				go(--opts.startindex);
			}
			if (opts.isShowButton) {
				//$prev.show();
				//$next.show();
			} else {
				$prev.attr("disabled", "true").addClass("navleftgray");
				$next.attr("disabled", "true").addClass("navrightgray");
			}
			// alert(opts.isPlus + "$prev.is(:visible):" +$prev.is(":visible"));
			if (opts.isPlus && $prev.is(":visible")) {
				//alert(2);
				go(++opts.startindex);
			}

			$prev.unbind("click").bind("click", function() {
				//alert(2);
				go(--opts.startindex);
			});

			$next.unbind("click").bind("click", function() {
				//alert(1);
				go(++opts.startindex);
			});
			
	


		});
		
		function go(i) {
			var length = $thisUl.find("li").length;
			$prev.removeAttr("disabled").removeClass("navleftgray").addClass("navleft");
			$next.removeAttr("disabled").removeClass("navrightgray").addClass("navright");

			var move = (opts.realWidth) * 1 * i;
			$thisUl.parent().animate( {//修改在ie6下无法滚动的问题
				marginLeft : -move
			}, 1000);
			//$info.append("[i]:" + i).append("[move]:" + move).append("[length]:" + (length)).append( "<br />");

			if (i == 0) {
				$prev.attr("disabled", "true").removeClass().addClass("navleftgray");
				return false;
			}
			if (i == (length - opts.dispItems)) {
				$next.attr("disabled", "true").removeClass().addClass("navrightgray");
				return false;
			}

		}

	};
	
	$.fn.mycarousel.defaults = {
			startindex : 0,
			dispItems : 6,
			horizontalMarigin : 0,
			realWidth : 0,
			liLength : 0,
			isShowButton : true,
			isPlus : false,
			isMinus : false,
			nextBtn : '<input type="button" value="Next" />',
			prevBtn : '<input type="button" value="Previous" />'
	};
	
})(jQuery);
