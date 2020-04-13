<!--
/*
   Copyright 2007 KimSoft

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

/**
 * MediaPlayerFactory to create media player
 * @author jinqinghua[at]gmail.com
 * @version build 2006/11/09
 * @usage
 * 1.var mediaPlayerFactory = new MediaPlayerFactory(id, width, height, data);
 * 2.mediaPlayerFactory.addProperty(name, value);[optional]
 * 3.mediaPlayerFactory.addParameter(name, value);[optional]
 * 4.document.write(mediaPlayerFactory.getMediaPlayer());
 */
function MediaPlayerFactory(id, width, height, data) {	
	this.id = id || "__MediaPlayer";
	this.width = width || 400;
	this.height = height || 300;
	this.data = data;

	this.properties = [];
	this.parameters = [];
}

MediaPlayerFactory.prototype.init = function () {
	this.properties.length = 0;
	this.parameters.length = 0;
};

MediaPlayerFactory.prototype.addProperty = function (name, value) {
	this.properties[this.properties.length] = [name, value];
};

MediaPlayerFactory.prototype.addParameter = function (name, value) {
	this.parameters[this.parameters.length] = [name, value];
};

MediaPlayerFactory.prototype.getMediaPlayer = function (type) {
	this.addProperty("id", this.id);
	this.addProperty("width", this.width);
	this.addProperty("height", this.height);
	switch(type) {
		case 1 || "F" ://Flash
			this.addProperty("type", "application/x-shockwave-flash");
			this.addProperty("data", this.data);
			
			this.addParameter("menu","false");
			this.addParameter("wmode","opaque");
			
			this.addParameter("movie", this.data);
			this.addParameter("allowScriptAcess", "sameDomain");
			this.addParameter("quality", "height");
			this.addParameter("bgcolor", "#FFFFFF");
			this.addParameter("scale", "noScale");
			this.addParameter("FlashVars", "playerMode=embedded");
		break;
		case 2 || "W" ://Windows Media
			this.addProperty("type", "video/x-ms-wmv");
			this.addProperty("data", this.data);
			this.addProperty("onclick", "alert('press Esc to exit');this.DisplaySize=3");
			
			this.addParameter("src", this.data);
			this.addParameter("autostart", "true");
			this.addParameter("controller", "true");
		break;
		case 3 || "Q" ://QuickTime
			this.addProperty("classid", "clsid:02BF25D5-8C17-4B23-BC80-D3488ABDDC6B");
			this.addProperty("codebase", "http://www.apple.com/qtactivex/qtplugin.cab");
			
			this.addProperty("type", "video/quicktime");
			this.addProperty("data", this.data);
			
			this.addProperty("value", this.data);
			this.addParameter("controller", "true");
			this.addParameter("autoplay", "true");
		break;
		case 4 || "R" ://Real
			this.addProperty("classid", "clsid:CFCDAA03-8BE4-11cf-B84B-0020AFBBCCFA");
			this.addProperty("src", this.data);
			
			this.addParameter("controls", "imagewindow");
			this.addParameter("autostart", "0");
		break;
	}
	var playCodes = [];
	playCodes[playCodes.length] = "<object";
	for (var i = 0; i < this.properties.length; i++) {
		playCodes[playCodes.length] = " ";
		playCodes[playCodes.length] = this.properties[i][0];
		playCodes[playCodes.length] = "=\"";
		playCodes[playCodes.length] = this.properties[i][1];
		playCodes[playCodes.length] = "\"";
	}
	playCodes[playCodes.length] = ">";
	
	for (var i = 0; i < this.parameters.length; i++) {
		playCodes[playCodes.length] = "<param name=\"";
		playCodes[playCodes.length] = this.parameters[i][0];
		playCodes[playCodes.length] = "\" value=\"";
		playCodes[playCodes.length] = this.parameters[i][1];
		playCodes[playCodes.length] = "\">";
	}
	playCodes[playCodes.length] = "</object>";
	return playCodes.join("");
};
//-->