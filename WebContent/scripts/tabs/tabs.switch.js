/*
 * jQuery plugin "tabs switch" by Ethanwoo 
 * mail:ethanwooblog@gmail.com
 *
 * Copyright , Ethanwoo
 * version 1.0 
 * Date : 2011-12-13
 * version 1.1 add classtabs ; date 2011-12-14
 * version 1.2 add isanimal ; date 2011-12-15
 * version 1.3 add event and timeshow; date 2012-5-24
 * version 1.4 add event flow; date 2013-5-29
 */

(function($) {
	$.fn.tytabs = function(options) {
		var defaults = {
			prefixtabs : "tab",
			prefixcontent : "content",
			classcontent : "tabscontent",
			classtabs : "tabs",
			classselectedtab : "current",
			tabinit : "1",
			catchget : "tab",
			fadespeed : "fast",
			isanimal : true,
			isflow : false,
			event : 'click',
			timeshow : 200
		}, opts = $.extend( {}, defaults, options);
		// iterate and reformat each matched element
		return this.each(function() {
			var obj = $(this);
			opts.classcontent = "." + opts.classcontent;
			opts.prefixcontent = "#" + opts.prefixcontent;
			//var items = $("." + opts.prefixtabs + "class", obj), eventTimer;
			var items = $("ul." + opts.classtabs + " li", obj), eventTimer;
			var flowobj = $("#" + opts.prefixtabs + "flow");
			function showTab(id) {
				// Contre les stress-click
				$(opts.classcontent, obj).stop(true, true);
				var contentvisible = $(opts.classcontent + ":visible", obj);
				if (contentvisible.length > 0) {
					if (opts.isanimal) {
						contentvisible.fadeOut(opts.fadespeed, function() {
							fadeincontent(id);
						});
					} else {
						contentvisible.hide();
						fadeincontent(id);
					}
				} else {
					fadeincontent(id);
				}
				$("#" + opts.prefixtabs + opts.tabinit).removeAttr("class");
				$("#" + opts.prefixtabs + id).attr("class", opts.classselectedtab);
				
				if (opts.isflow) {
					var left = (id - 1) * $("#" + opts.prefixtabs + id).width();
					flowobj.animate({left:  left + "px"});
				}
				
				// Update tab courant
				opts.tabinit = id;
			}
			function fadeincontent(id) {
				if (opts.isanimal) {
					$(opts.prefixcontent + id, obj).fadeIn(opts.fadespeed);
				} else {
					$(opts.prefixcontent + id, obj).show();
				}
			}

			
			var delay = function(elem, time){
				var tab_id = elem.attr("id").replace(opts.prefixtabs, "");
				time ? eventTimer = setTimeout(function(){ showTab(tab_id); }, time) : showTab(tab_id);
			};
			if (opts.event == 'hover') {
				opts.isanimal = false;
				items.hover(function(){
					//alert(items.size());
					delay($(this), opts.timeshow );
				},function(){
					clearTimeout(eventTimer);
				});
			} else { //Click sur les onglets
				items.click(function() {
					showTab($(this).attr("id").replace(opts.prefixtabs, ""));
					return false;
				});
			}
	
			
			var tab = getvars(opts.catchget);
			showTab(((tab && $(opts.prefixcontent + tab).length == 1) ? tab
					: ($(opts.prefixcontent + opts.tabinit).length == 1) ? opts.tabinit
							: "1"));
		}); // end each
	};
	// Source : http://www.onlineaspect.com
	function getvars(q, s) {
		s = (s) ? s : window.location.search;
		var re = new RegExp('&' + q + '=([^&]*)', 'i');
		return (s = s.replace(/^\?/, '&').match(re)) ? s = s[1] : s = '';
	}
	;
})(jQuery);

/** page demo
 * <!-- Tabs --> 
 * <div id="tabsholder"> 
 * <ul class="tabs"> 
 * 	<li id="tab1">Spiderman</li>
 * 	<li id="tab2">Batman</li> 
 * 	<li id="tab3">Hulk</li> 
 * 	<li id="tab4">Daredevil</li>
 * </ul> 
 * <div> 
 * 	<div id="content1" class="tabscontent">Spiderman.</div> 
 * 	<div id="content2" class="tabscontent"> Batman.</div> 
 * 	<div id="content3" class="tabscontent"> Hulk.</div> 
 * <div id="content4" class="tabscontent">Daredevil </div> 
 * </div> 
 * </div> 
 * <!-- /Tabs -->
 * 
 */