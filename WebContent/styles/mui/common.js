(function(root, factory) {
	if(typeof module !== 'undefined' && typeof exports === 'object') {
		module.exports = factory(root.Common);
	} else if(typeof define === "function" && define.amd) {
		define(["Common"], function(Common) {
			return(root.Common = factory(Common));
		});
	} else {
		root.Common = factory(root.Common);
	}
}(this, function(Common) {
	Common = {
			api: "http://192.168.2.119:8080/ninePorters/",
			apiHttps: "http://192.168.2.119:8080/ninePorters/",
			
		open: function(id, url, extras, type) {
			mui.openWindow({
				id: id,
				url: url,
				waiting: {
					autoshow: true,
					title: '正在加载....'
				},
				extras: extras || {}
			});
		},
		showWaiting: function(msg) {
			return plus.nativeUI.showWaiting(msg ? msg : "正在加载...", {
				padlock: true
			});
		},
		closeWaiting: function(w) {
			w.close();
		},
		showLayUi: function(content, isBottom,isFull) {
			var style = '';
			if(isBottom) {
				style = 'position:fixed; bottom:0; left:0; width: 100%;border:none;';
			}
			if(isFull) {
				style='position:fixed; left:0; top:0; width:100%; height:100%; border: none; -webkit-animation-duration: .5s; animation-duration: .5s;z-index:999';
			}
			layer.open({
				content: content,
				anim: 'up',
				type: 1,
				style: style
			});
		},
		loading: function(title) {
			var title_me = "";
			if(title) {
				title_me = title;
			}
			var load = '<span class="ui-icon-loading"></span>' + title_me;
			if(mui("#_loading_bg").length) {
				mui("#_loading_content").innerHTML = load;
			} else {
				var e = window.top.document,
					f = e.createElement("div");
				f.setAttribute("id", "_loading_bg"),
					e.body.appendChild(f);
				var g = e.createElement("div");
				g.setAttribute("id", "_loading_content"),
					f.appendChild(g),
					g.innerHTML = load;
			}
		},
		hide: function() {
			if(mui("#_loading_bg").length) {
				document.getElementById("_loading_bg").parentNode.removeChild(document.getElementById("_loading_bg"));
			};
		},
		openWebView: function(url, id, type) {
			// 动画窗口
			if(null == type) {
				type = "slide-in-right";
			}
			var openw = plus.webview.create(url, id, {
				scrollIndicator: 'none',
				scalable: false,
				popGesture: 'none',
				bounceBackground: '#FFFFFF'
			}, {
				preate: true
			});
			var ws = Common.showWaiting();
			Common.loaded(openw, ws, type);
		},
		weixinConfig: function(inData,shareData) {
			wx.config({
         	      debug: false,
         	      appId: inData.appId,
         	      timestamp: inData.timestamp,
         	      nonceStr: inData.nonceStr,
         	      signature: inData.signature,
         	      jsApiList: [
         			'checkJsApi',
         	        'scanQRCode',
         	        'onMenuShareTimeline',
         	        'onMenuShareAppMessage',
         	        'onMenuShareQQ',
         	      ]
            });
    		  wx.ready(function() {
    			wx.onMenuShareAppMessage({
    				title : shareData.title,
    				desc : shareData.desc,
    				link : shareData.link,
    				imgUrl : shareData.imgUrl,
    				fail : function(res) {
    					alert(JSON.stringify(res));
    				}
    			});
    			wx.onMenuShareTimeline({
    				title : shareData.title,
    				desc : shareData.desc,
    				link : shareData.link,
    				imgUrl : shareData.imgUrl,
    				fail : function(res) {
    					alert(JSON.stringify(res));
    				}
    			});
    			wx.onMenuShareQQ({
    				title : shareData.title,
    				desc : shareData.desc,
    				link : shareData.link,
    				imgUrl : shareData.imgUrl,
    				fail : function(res) {
    					alert(JSON.stringify(res));
    				}
    			});
    			
    			document.querySelector('#scanQRCode').onclick = function() {
    				wx.scanQRCode({
    					needResult : 1,
    					desc : '二维码扫描',
    					success : function(res) {
    						var result = res.resultStr; 
             				window.location.href = result;
    					}
    				});
    			};
    			
    		 });
	  	     wx.error(function(res) {
	  	  	 // alert(res.errMsg);
	  	     });
		},
		getData: function(inData) {
			var url = inData.url || Common.api;
			inData.route = inData.route || '';
			inData.data = inData.data || {};
			mui.ajax(url + inData.route, {
				data: inData.data,
				dataType: 'json',
				type: 'get',
				success: inData.success,
				error: inData.error,
			})
		},
		confirm: function(content, btnArray, but1Function, but2Function) {
			mui.confirm(content, this.app_name, btnArray, function(e) {
				if(e.index == 1) {
					but2Function();
				} else {
					but1Function();
				}
			});
		},
		selectTime: function(docId, successCallBack) {
			document.getElementById(docId).addEventListener('tap', function() {
				var optionsJson = this.getAttribute('data-options') || '{}';
				var options = JSON.parse(optionsJson);

				var picker = new mui.DtPicker(options);
				picker.show(function(rs) {
					successCallBack(rs);
					picker.dispose();
				});
			});
		},
		showPicker: function(docId, data, successCallBack) {
			document.getElementById(docId).addEventListener('tap', function(event) {
				var userPicker = new mui.PopPicker();
				userPicker.setData(data);
				userPicker.show(function(items) {
					successCallBack(items);
				});
			}, false);
		},
		showPov: function(docId, selectData, successCallBack) {
			var node = document.createElement("div");
			node.id = docId;
			node.className = "mui-popover mui-popover-action mui-popover-bottom";
			var html = '<ul class="mui-table-view">';
			for(var x in selectData) {
				html += '<li class="mui-table-view-cell">';
				html += '<a href="#" data-value="' + selectData[x].value + '">' + selectData[x].text + '</a>';
				html += '</li>';
			}
			html += '</ul>';
			html += '<ul class="mui-table-view">';
			html += '<li class="mui-table-view-cell"> ';
			html += '<a href="#' + docId + '">取消 </a>';
			html += '</li>';
			html += '</ul>';
			
			node.innerHTML = html;
			
			document.body.appendChild(node);

			mui('body').on('tap', "#" + docId + " li>a", function() {
				var $this = this,
					parent;
				for(parent = $this.parentNode; parent != document.body; parent = parent.parentNode) {
					if(parent.classList.contains('mui-popover-action')) {
						break;
					}
				}
				mui('#' + parent.id).popover('toggle');
				if(null != $this.getAttribute("data-value") && "" != $this.getAttribute("data-value")) {
					successCallBack($this);
				}
			});
		},
		showPovScroll: function(docId, selectData, successCallBack) {
			var node = document.createElement("div");
			node.id = docId;
			node.className = "mui-popover mui-popover-action mui-popover-bottom mui-scroll-wrapper";
			var html = '<div class="mui-scroll">';
			html += '<ul class="mui-table-view">';
			for(var x in selectData) {
				html += '<li class="mui-table-view-cell">';
				html += '<a href="#" data-value="' + selectData[x].value + '">' + selectData[x].text + '</a>';
				html += '</li>';
			}
			html += '</ul>';
			html += '<ul class="mui-table-view">';
			html += '<li class="mui-table-view-cell"> ';
			html += '<a href="#' + docId + '">取消 </a>';
			html += '</li>';
			html += '</ul>';
			html += '</div>';
			
			node.innerHTML = html;
			
			document.body.appendChild(node);

			mui('body').on('tap', "#" + docId + " li>a", function() {
				var $this = this,
					parent;
				for(parent = $this.parentNode; parent != document.body; parent = parent.parentNode) {
					if(parent.classList.contains('mui-popover-action')) {
						break;
					}
				}
				mui('#' + parent.id).popover('toggle');
				if(null != $this.getAttribute("data-value") && "" != $this.getAttribute("data-value")) {
					successCallBack($this);
				}
			});
		},
		formatDate: function(time, fmt) {
			var date = new Date(parseInt(time));
			var o = {
				"M+": date.getMonth() + 1, //月份 
				"d+": date.getDate(), //日 
				"H+": date.getHours(), //小时 
				"m+": date.getMinutes(), //分 
				"s+": date.getSeconds(), //秒 
				"q+": Math.floor((date.getMonth() + 3) / 3), //季度 
				"S": date.getMilliseconds() //毫秒 
			};
			if(/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
			for(var k in o)
				if(new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
			return fmt;
		},
		goBack:function(){
			history.go(-1);
		},
		getUrlParam: function(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
			var url = decodeURI(window.location.search);
			var r = url.substr(1).match(reg);
			if(r != null) {
				return r[2];
			} else {
				return null;
			}
		},
	};
	return Common;
}));
Vue.filter('formatMoney', function(value) {
	if (null != value) {
		return parseFloat(value).toFixed(2);
	} else {
		return "0.00";
	}
});