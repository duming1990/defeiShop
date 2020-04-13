function search(ctx, id, val) {
	var oKeyTextN = document.getElementById(id);
	var htype = document.getElementById("htype");
	var selKey = oKeyTextN.value.replace(/#/g, "%23").replace(/\+/g, "%2b"),
	url = ctx + "/search.shtml?{0}keyword=",
	str = "";
	if ("" == selKey) {
		alert("您必须要输入要搜索的关键字，谢谢！");
		return false;
	}
	if (htype && ("" != htype.value)) {
		str += "htype=" + htype.value + "&";
	}
	url = url.replace("{0}", str);
	url += selKey;
	if ("undefined" == typeof(search.isSubmitted)) {
		setTimeout(function() {
			window.location.href = url;
		},
		10);
		search.isSubmitted = true;
	}
}
