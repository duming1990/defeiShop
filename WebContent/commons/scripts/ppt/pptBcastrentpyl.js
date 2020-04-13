/**
 * @param ctxPath as a context path
 * @param bcastr_file 图片地址参数，多个使用|分开，与方法2配合使用 空 
 * @param bcastr_title 图片标题参数，多个使用|分开，与方法2配合使用 空 
 * @param bcastr_link 图片连接参数，多个使用|分开，与方法2配合使用 空 
 * @param bcastr_xml 方法3 传递图片参数，样板参考 http://www.ruochi.com/product/bcastr3/bcastr.xml  bcastr.xml 
 * @param TitleTextColor 图片名称文字颜色 0xFFFFFF 
 * @param TitleBgColor 图片名称文字背景颜色 0xFF6600 0x7BB7F5
 * @param TitleBgAlpha 图片名称文字背景颜色透明度：0-100值，0表示全部透明 60 
 * @param TitleBgPosition 图片名称文字位置，0表示文字在顶端，1表示文字在底部，2表示文字在顶端浮动 100 
 * @param BtnDefaultColor 按键默认的颜色 0xFF6600 
 * @param BtnOverColor 按键当前的颜色 0x000033 
 * @param AutoPlayTime 自动播放时间：单位是秒 8 
 * @param Tween 图片过渡效果：0，表示亮度过渡，1表示透明度过渡，2表示模糊过渡，3表示运动模糊过渡 2 
 * @param IsShowBtn 是否显示按钮：1表示显示按键，0表示隐藏按键，更适合做广告挑轮换 1 
 * @param WinOpen 打开窗口：_blank表示新窗口打开。_self表示在当前窗口打开 _blank 
 */
function showPptPlayer(jsonString, ctxPath, imgCount, swfWidth, swfHeight, isShowTitle, isNews) {
	String.prototype.lengthW = function() {
		return this.replace(/[^\x00-\xff]/g, "**").length;
	};
	String.prototype.sub = function(n) {
		var r = /[^\x00-\xff]/g;
		if (this.replace(r, "mm").length <= n) {
			return this;
		}
		var m = Math.floor(n / 2);
		for (var i = m; i < this.length; i++) {
			if (this.substr(0, i).replace(r, "mm").length >= n) {
				return this.substr(0, i);
			}
		}
		return this;
	};
	jsonString = "[" + jsonString + "]" || "";
	imgCount = imgCount || 5;
	if (ctxPath != "") {
		ctxPath = ctxPath || "./";
	}
	swfWidth = swfWidth || 270;
	swfHeight = swfHeight || 220;
	var TitleBgColor = 0x000000;
	//var TitleBgColor = 0x30A7DB;
	var BtnOverColor = 0xFF6600;
	var TitleBgPosition = 3;
	var a = eval(jsonString);
	var imgArray = [];
	var txtArray = [];
	var lnkArray = [];
	if (a.length == 1) {
		imgArray[0] = ctxPath + "commons/scripts/ppt/images/entp_yl.jpg";
		txtArray[0] = "";
		lnkArray[0] = "";
	} else {
		var count = 1;
		for (var i = 0; i < a.length; i++) {
			if (count > imgCount) {
				break;
			}
			if (a[i].img == null) {
				continue;
			} 
			imgArray[imgArray.length] = ctxPath + a[i].img;
			// imgArray[imgArray.length] = ctxPath + (a[i].img).split(".")[0].concat("_400.jpg");
			if(isShowTitle && a[i].txt) {
				txtArray[txtArray.length] = a[i].txt;
			}
			if (a[i].lnk) {
				if (isNews) {
					lnkArray[lnkArray.length] = ctxPath + a[i].lnk;
				} else {
					lnkArray[lnkArray.length] = a[i].lnk;
				}
			}
			count++;
		}
	}
	var files = imgArray.join("|");
	var texts = txtArray.join("|");
	var links = lnkArray.join("|"); 
	
	document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0" width="' + swfWidth + '" height="' + swfHeight + '">');
	document.write('<param name="movie" value="' + ctxPath + 'commons/scripts/ppt/images/bcastr31.swf"><param name="quality" value="high">');
	document.write('<param name="menu" value="false"><param name=wmode value="opaque">');
	document.write('<param name="FlashVars" value="AutoPlayTime=4&Tween=0&TitleBgAlpha=40&TitleBgPosition=' + TitleBgPosition + '&BtnOverColor=' + BtnOverColor + '&TitleBgColor=' + TitleBgColor + '&bcastr_file=' + files + '&bcastr_link=' + links + '&bcastr_title=' + texts + '">');
	document.write('<embed wmode="opaque" FlashVars="AutoPlayTime=4&Tween=0&TitleBgAlpha=40&TitleBgPosition=' + TitleBgPosition + '&BtnOverColor=' + BtnOverColor + '&TitleBgColor=' + TitleBgColor + '&bcastr_file=' + files + '&bcastr_link=' + links + '&bcastr_title=' + texts + '& menu="false" quality="high" width="' + swfWidth + '" height="' + swfHeight + '" type="application/x-shockwave-flash" src="' + ctxPath + 'commons/scripts/ppt/images/bcastr31.swf" pluginspage="http://www.macromedia.com/go/getflashplayer" />');
	document.write('</object>');
}
