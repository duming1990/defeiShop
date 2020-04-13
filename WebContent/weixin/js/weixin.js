/*
 * 注意：
 * 1. 所有的JS接口只能在公众号绑定的域名下调用，公众号开发者需要先登录微信公众平台进入“公众号设置”的“功能设置”里填写“JS接口安全域名”。
 * 2. 如果发现在 Android 不能分享自定义内容，请到官网下载最新的包覆盖安装，Android 自定义分享接口需升级至 6.0.2.58 版本及以上。
 * 3. 完整 JS-SDK 文档地址：http://mp.weixin.qq.com/wiki/7/aaa137b55fb2e0456bf8dd9148dd613f.html
 *
 * 如有问题请通过以下渠道反馈：
 * 邮箱地址：weixin-open@qq.com
 * 邮件主题：【微信JS-SDK反馈】具体问题
 * 邮件内容说明：用简明的语言描述问题所在，并交代清楚遇到该问题的场景，可附上截屏图片，微信团队会尽快处理你的反馈。
 */
wx.ready(function() {
	wx.onMenuShareAppMessage({
		title : wxData.title,
		desc : wxData.desc,
		link : wxData.link,
		imgUrl : wxData.imgUrl,
		trigger : function(res) {
			// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			//alert('用户点击发送给朋友');
		},
		success : function(res) {
			//alert('已分享');
		},
		cancel : function(res) {
			//alert('已取消');
		},
		fail : function(res) {
			alert(JSON.stringify(res));
		}
	});
	
	wx.onMenuShareTimeline({
		title : wxData.title,
		link : wxData.link,
		imgUrl : wxData.imgUrl,
		trigger : function(res) {
			// 不要尝试在trigger中使用ajax异步请求修改本次分享的内容，因为客户端分享操作是一个同步操作，这时候使用ajax的回包会还没有返回
			//alert('用户点击分享到朋友圈');
		},
		success : function(res) {
			//alert('已分享');
		},
		cancel : function(res) {
			//alert('已取消');
		},
		fail : function(res) {
			alert(JSON.stringify(res));
		}
	});
	
	wx.onMenuShareQQ({
		title : wxData.title,
		desc : wxData.desc,
		link : wxData.link,
		imgUrl : wxData.imgUrl,
		trigger : function(res) {
			//alert('用户点击分享到QQ');
		},
		complete : function(res) {
			//alert(JSON.stringify(res));
		},
		success : function(res) {
			//alert('已分享');
		},
		cancel : function(res) {
			//alert('已取消');
		},
		fail : function(res) {
			alert(JSON.stringify(res));
		}
	});
//			var shareData = {
//				title : '微信JS-SDK Demo',
//				desc : '微信JS-SDK,帮助第三方为用户提供更优质的移动web服务',
//				link : 'http://demo.open.weixin.qq.com/jssdk/',
//				imgUrl : 'http://mmbiz.qpic.cn/mmbiz/icTdbqWNOwNRt8Qia4lv7k3M9J1SKqKCImxJCt7j9rHYicKDI45jRPBxdzdyREWnk0ia0N5TMnMfth7SdxtzMvVgXg/0'
//			};

			//wx.onMenuShareAppMessage(wxData);
			//wx.onMenuShareTimeline(wxData);
		});

wx.error(function(res) {
	alert(res.errMsg);
});
