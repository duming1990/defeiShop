<!--
/**
 * show flash with pictures by eval json string
 * @param jsonString as a string with json structure {property1:value1,property2:value2},{},{},{}
 * @param ctxPath as a context path
 * @author kimsoft
 * @version build 2006/11/02
 * @version build 2006/11/09 fix the code to support firefox
 * @version build 2006/12/29 V2.0 important update
 * @version build 2007/07/03 add txtLength parameter, add chinese length support
 * @version build 2008/05/19 add param: menu and wmode
 */
function showPptPlayer(jsonString, ctxPath, imgCount, imgWidth, imgHeight, txtHeight, txtLength) {
	String.prototype.lengthW = function() {
	  return this.replace(/[^\x00-\xff]/g,"**").length;
	}
	String.prototype.sub = function (n) {
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
	ctxPath = ctxPath     || "./";
	imgCount = imgCount   || 4;
	imgWidth = imgWidth   || 200;
	imgHeight = imgHeight || 150;
	txtHeight = txtHeight || 20;
	
	var a = eval(jsonString);
	var imgArray = [];
	var txtArray = [];
	var lnkArray = [];
	if (a.length == 1) {
		imgArray[0] = ctxPath + "commons/scripts/ppt/images/no_image.jpg";
		txtArray[0] = "";
		lnkArray[0] = "";
	} else {
		var count = 1;
		for (var i = 0; i < a.length; i++) {
			if (count > imgCount) {break;}
			if (a[i].img == null) {continue;}
			//imgArray[imgArray.length] = ctxPath + a[i].img;
			imgArray[imgArray.length] = ctxPath + (a[i].img).split(".")[0].concat("_400.jpg");
			if (txtLength && a[i].txt.lengthW() + 2 > txtLength) {
				txtArray[txtArray.length] = a[i].txt.sub(txtLength) + "..";
			} else {
				txtArray[txtArray.length] = a[i].txt;
			}
			lnkArray[lnkArray.length] = ctxPath + a[i].lnk;
			count++;
		}
	}

	var imgs = imgArray.join("|");
	var txts = txtArray.join("|");
	var lnks = lnkArray.join("|");

	var swfWidth = imgWidth;
	var swfHeight = imgHeight + txtHeight;
	
	document.write('<object type="application/x-shockwave-flash" data="' + ctxPath + 'commons/scripts/ppt/images/ppt.swf" width="' + swfWidth + '" height="' + swfHeight + '" id="pptPlayer">');
	document.write('<param name="movie" value="' + ctxPath + 'commons/scripts/ppt/images/ppt.swf" />');
	document.write('<param name="allowScriptAcess" value="sameDomain" />');
	
	document.write('<param name="menu" value="false" />');
	document.write('<param name="wmode" value="opaque" />');
	
	document.write('<param name="quality" value="best" />');
	document.write('<param name="bgcolor" value="#F0F0F0" />');
	document.write('<param name="scale" value="noScale" />');
	document.write('<param name="FlashVars" value="playerMode=embedded&pics=' + imgs + '&links=' + lnks + '&texts=' + txts + '&borderwidth=' + imgWidth + '&borderheight=' + imgHeight + '&textheight=' + txtHeight + '">');
	document.write('</object>');
}
//-->