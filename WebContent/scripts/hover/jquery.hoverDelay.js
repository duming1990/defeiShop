(function($) {
	$.fn.hoverDelay = function(options) {
		var defaults = {
			hoverDuring : 200,
			outDuring : 200,
			hoverEvent : function() {
				$.noop();
			},
			outEvent : function() {
				$.noop();
			}
		};
		var sets = $.extend(defaults, options || {});
		var hoverTimer, outTimer, that = this;
		return $(this).each(function() {
			$(this).hover(function() {
				clearTimeout(outTimer);
				hoverTimer = setTimeout(function() {
					sets.hoverEvent.apply(that);
				}, sets.hoverDuring);
			}, function() {
				clearTimeout(hoverTimer);
				outTimer = setTimeout(function() {
					sets.outEvent.apply(that);
				}, sets.outDuring);
			});
		});
	};
})(jQuery);

/*
    <script type="text/javascript" src="${ctx}/scripts/jquery.hoverDelay.js"></script>
    $("#navCatDiv").hoverDelay({
        outDuring: 1000,
        hoverEvent: function(){
    	    $("#navCatImg").attr("src","${ctx}/styles/indexv2/images/navPd.jpg");
            $("#navTopAllCate").show();       
        },
        outEvent: function(){
        	$("#navCatImg").attr("src","${ctx}/styles/indexv2/images/navPdDown.jpg");
            $("#navTopAllCate").hide();
        }
    });
 */