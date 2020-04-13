function toExcel(divId, path){	
	divId = divId || "divExcel";
	path  = path  || "";
	
	var divExcel = document.getElementById(divId);
	
	var formToPrint = document.createElement("form");
	var hiddenHtml = document.createElement("input");
	hiddenHtml.type = "hidden";
	hiddenHtml.name = "hiddenHtml";
	hiddenHtml.value = divExcel.innerHTML;
	formToPrint.appendChild(hiddenHtml);
	
	var hiddenName = document.createElement("input");
	hiddenName.type = "hidden";
	hiddenName.name = "hiddenName";
	hiddenName.value = divExcel.title;
	formToPrint.appendChild(hiddenName);
	
	document.body.appendChild(formToPrint);
	formToPrint.method = "post";
	formToPrint.action = path;
	//formToPrint.target = "_blank";
	formToPrint.submit();
}