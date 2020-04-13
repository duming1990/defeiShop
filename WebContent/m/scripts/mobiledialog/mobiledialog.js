M = {
    version: "1031",
    sUrl: function(a, b, c) {
        return a ? b ? "/item.html?itemID=" + b + (c ? "&" + c: "") : "/?userid=" + a + (c ? "&" + c: "") : void 0
    },
    toJSON: function(a) {
        return JSON.stringify(a)
    },
    json: function(a) {
        return JSON.parse(a)
    },
    loadScript: function(a, b) {
        var c = document.createElement("script");
        c.readyState ? c.onreadystatechange = function() { ("loaded" == c.readyState || "complete" == c.readyState) && (c.onreadystatechange = null, b && b())
        }: c.onload = function() {
            b && b()
        },
        c.src = a.indexOf("?") > 0 ? a + "&ver=" + M.version: a + "?ver=" + M.version;
        var d = document.getElementsByTagName("script")[0];
        d.parentNode.insertBefore(c, d)
    },
    ua: function() {
        return navigator.userAgent.toLowerCase()
    },
    isMobile: function() {
        return M.ua().match(/iPhone|iPad|iPod|Android|IEMobile/i)
    },
    isAndroid: function() {
        return - 1 != M.ua().indexOf("android") ? 1 : 0
    },
    isIOS: function() {
        var a = M.ua();
        return - 1 != a.indexOf("iphone") || -1 != a.indexOf("ipad") || -1 != a.indexOf("ipod") ? 1 : 0
    },
    platform: function() {
        return M.isMobile() ? M.isIOS() ? "IOS": M.isAndroid() ? "Android": "other-mobile": "PC"
    },
    isWeixin: function() {
        return - 1 != M.ua().indexOf("micromessenger") ? 1 : 0
    },
    isWeixinPay: function() {
        if (M.isWeixin()) {
            var a = M.ua(),
            b = a.substr(a.indexOf("micromessenger"), 18).split("/");
            return Number(b[1]) >= 5 ? 1 : 0
        }
        return 0
    },
    _alert: function(a,alert_time, b, c) {
    	if(null == alert_time || '' == alert_time){
    		alert_time = 1500;
    	}
        function d(a,time) {
            c ? b && b() : setTimeout(function() {
                a.fadeOut(function() {
                    a.parent().fadeOut(function() {
                        $(this).remove()
                    }),
                    b && b()
                })
            },
            time)
        }
        if ($("#_alert_bg").length) $("#_alert_content").html(a),
        d($("#_alert_content"),alert_time);
        else {
            var e = window.top.document,
            f = e.createElement("div");
            f.setAttribute("id", "_alert_bg"),
            e.body.appendChild(f);
            var g = e.createElement("div");
            g.setAttribute("id", "_alert_content"),
            f.appendChild(g),
            $(g).html(a).fadeIn(function() {
            	if(null == alert_time || '' == alert_time){
            		alert_time = 1500;
            	}
                d($(this),alert_time)
            })
        }
    },
    _loading : function(title) {
    	var title_me = ""; 
    	if(title){
    		title_me = title;
    	}
    	var load ='<span class="ui-icon-loading"></span>' + title_me;
    	if ($("#_loading_bg").length) $("#_loading_content").html(load);
    	else {
    		var e = window.top.document,
    		f = e.createElement("div");
    		f.setAttribute("id", "_loading_bg"),
    		e.body.appendChild(f);
    		var g = e.createElement("div");
    		g.setAttribute("id", "_loading_content"),
    		f.appendChild(g),
    		$(g).html(load).fadeIn()
    	}
    },
    _hide : function(a,alert_time, b, c) {
    	if ($("#_loading_bg").length) $("#_loading_bg").remove();
    },
    _confirm: function(a, b, c, d, e) {
        var f = document,
        g = f.createElement("div");
        g.setAttribute("id", "_confirm_bg"),
        f.body.appendChild(g);
        var h = f.createElement("div");
        h.setAttribute("id", "_confirm_content"),
        g.appendChild(h);
        var i = $("#_confirm_content"),
        j = "";
        j = j + "<p>" + a + "</p>",
        j += "<div id='_confirm_btnW'>",
        c && c[0] ? (j = j + "<div id='_confirm_btnA' class='" + b[1] + "'>" + b[0] + "</div>", j = j + "<div id='_confirm_btnB' class='" + c[1] + "'>" + c[0] + "</div>") : j = j + "<div id='_confirm_btnA' class='" + b[1] + "' style='width:100%;border-right:none'>" + b[0] + "</div>",
        j += "</div>",
        i.html(j).fadeIn(),
        $("#_confirm_btnA").bind("click",
        function() {
            e && e(),
            i.fadeOut(),
            $("#_confirm_bg").fadeOut(function() {
                $(this).remove()
            })
        }),
        c && c[0] && $("#_confirm_btnB").bind("click",
        function() {
            d && d(),
            i.fadeOut(),
            $("#_confirm_bg").fadeOut(function() {
                $(this).remove()
            })
        })
    },
    _bottomNav: function(a, b) {
    	var f = document;
    	var html = '<div id="bottomNav">';
    	html += '<div id="bottomNav_list">';
    	html += '<div id="bottomNav_menu">';
    	for(var i= 0;i < a.length;i++){
    		html += '<a id="bottomNav_'+ i +'">'+ a[i] +'</a>';
    	}
    	html += '</div>';
    	html += '<div id="bottomNav_cancel">取消</div>';
    	html += '</div>';
    	html += '</div>';
    	$(body).append(html);

    	for(var i= 0;i < a.length;i++){
    		$("#bottomNav_" + i).bind("click",
        			b[i]
        	);
    	}
    	
    	$("#bottomNav_cancel").bind("click",function(){
    		$("#bottomNav").find("#bottomNav_list").css("bottom","-400px");
    		setTimeout(function(){
    			$("#bottomNav").remove();
    		},300);
   	 	});
    	
    	$("#bottomNav").show();
    	$("#bottomNav").find("#bottomNav_list").css("bottom","0px");
    }
};
