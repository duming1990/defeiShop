(function($) {
	$.fn.hoverForIE6 = function(option) {
		var s = $.extend( {
			current : "hover"
		}, option || {});
		$.each(this, function() {
			$(this).bind("mouseover", function() {
				$(this).addClass(s.current);
			}).bind("mouseleave", function() {
				$(this).removeClass(s.current);
			})
		})
	}
})(jQuery);
(function($) {
	$.extend( {
		_jsonp : {
			scripts : {},
			counter : 1,
			head : document.getElementsByTagName("head")[0],
			name : function(callback) {
				var name = '_jsonp_' + (new Date).getTime() + '_'
						+ this.counter;
				this.counter++;
				var cb = function(json) {
					eval('delete ' + name);
					callback(json);
					$._jsonp.head.removeChild($._jsonp.scripts[name]);
					delete $._jsonp.scripts[name];
				};
				eval(name + ' = cb');
				return name;
			},
			load : function(url, name) {
				var script = document.createElement('script');
				script.type = 'text/javascript';
				script.charset = this.charset;
				script.src = url;
				this.head.appendChild(script);
				this.scripts[name] = script;
			}
		},
		getJSONP : function(url, callback) {
			var name = $._jsonp.name(callback);
			var url = url.replace(/{callback};/, name);
			$._jsonp.load(url, name);
			return this;
		}
	});
})(jQuery);
(function($) {
	$.fn.jdTab = function(option, callback) {
		if (typeof option == "function") {
			callback = option;
			option = {};
		}
		;
		var s = $.extend( {
			type : "static",
			auto : false,
			source : "data",
			event : "mouseover",
			currClass : "curr",
			tab : ".tab",
			content : ".tabcon",
			itemTag : "li",
			stay : 5000,
			delay : 100,
			mainTimer : null,
			subTimer : null,
			index : 0
		}, option || {});
		var tabItem = $(this).find(s.tab).eq(0).find(s.itemTag), contentItem = $(
				this).find(s.content);
		if (tabItem.length != contentItem.length)
			return false;
		var reg = s.source
				.toLowerCase()
				.match(
						/http:\/\/|\d|\.aspx|\.ascx|\.asp|\.php|\.html\.htm|.shtml|.js|\W/g);
		var init = function(n, tag) {
			s.subTimer = setTimeout(
					function() {
						hide();
						if (tag) {
							s.index++;
							if (s.index == tabItem.length)
								s.index = 0;
						} else {
							s.index = n;
						}
						;
						s.type = (tabItem.eq(s.index).attr(s.source) != null) ? "dynamic"
								: "static";
						show();
					}, s.delay);
		};
		var autoSwitch = function() {
			s.mainTimer = setInterval(function() {
				init(s.index, true);
			}, s.stay);
		};
		var show = function() {
			tabItem.eq(s.index).addClass(s.currClass);
			switch (s.type) {
			default:
			case "static":
				var source = "";
				break;
			case "dynamic":
				var source = (reg == null) ? tabItem.eq(s.index).attr(s.source)
						: s.source;
				tabItem.eq(s.index).removeAttr(s.source);
				break;
			}
			;
			if (callback) {
				callback(source, contentItem.eq(s.index), s.index);
			}
			;
			contentItem.eq(s.index).show();
		};
		var hide = function() {
			tabItem.eq(s.index).removeClass(s.currClass);
			contentItem.eq(s.index).hide();
		};
		tabItem.each(function(n) {
			$(this).bind(s.event, function() {
				clearTimeout(s.subTimer);
				clearInterval(s.mainTimer);
				init(n, false);
				return false;
			}).bind("mouseleave", function() {
				if (s.auto) {
					autoSwitch();
				} else {
					return;
				}
			})
		});
		if (s.type == "dynamic") {
			init(s.index, false);
		}
		;
		if (s.auto) {
			autoSwitch();
		}
	}
})(jQuery);