<!--
/**
 * CascadeSelect jQuery Version JSON style : [[t1,v1],[t2,v2]]
 * 
 * @author Jin,QingHua [jinqinghua@gmail.com]
 * @version Build 20090627
 * @version Build 20090627 change the return type of json from [{text:t1,
 *          value:v1}...] to [[t1,v1]...]
 * 
 * @author jinqinghua@gmail.com
 * @see http://code.google.com/p/kimsoft-cs/
 */
jQuery.fn.extend({
	/**
	 * @param url
	 *            the url for ajax request
	 * @param reqParamName
	 *            the request parameter name
	 * @param reqParamValue
	 *            the request parameter value
	 * @param isHideNoOptionSelect
	 *            is hide no option select, default is true
	 */
	cs : function(url, reqParamName, reqParamValue, isHideNoOptionSelect, selectLen) {
		if (null == isHideNoOptionSelect) isHideNoOptionSelect = true;
		if (null == url || $.trim(url).length == 0) {
			alert("error! parameters[0] can not be empty!");
			return;
		}
		if (null == reqParamName || $.trim(reqParamName).length == 0) {
			return;
		}		
		if (null == reqParamValue || $.trim(reqParamValue).length == 0) {
			return;
		}
		
		var attrSubElement= this.attr("subElement");
		var $subElement = $("#" + attrSubElement);
		this.change(function(){
			hideAllSubElements($subElement);
			if (null != attrSubElement) {
				$subElement.cs(url, reqParamName, this.options[this.selectedIndex].value);	
			}	
	    });

		
		this[0].options.length = 0;
		if(selectLen == 1){
			this[0].options.add(new Option("请选择...", ""));
		}
		var defaultText = this.attr("defaultText");
		var defaultValue = this.attr("defaultValue");
		var defaultValue2 = this.attr("defaultValue2");
		var selectedValue = this.attr("selectedValue");
		if (defaultText != null && defaultValue != null) {
			this[0].options[this[0].options.length] = new Option(defaultText, defaultValue);
		}
		if (defaultValue != null) {
			this.next().text(defaultValue2);
		}
		$.ajaxSetup({async:false});
		var tvs; // text and value array
		var data = {}; data[reqParamName] = reqParamValue;
		$.getJSON(url, data, function(json){
			tvs = json;
		});
		if (null == tvs || tvs.length == 0) {
			return;
		}
		var isSelected = false;
        for (var i = 0; i < tvs.length; i++) {
        	this[0].options[this[0].options.length] = new Option(tvs[i][0], tvs[i][1]);
            if (selectedValue != null && selectedValue == tvs[i][1]) {
            	this[0].selectedIndex = this[0].options.length - 1;
            	isSelected = true;
            }
        } 
        if (this[0].options.length > 0) {
        	this.show();
        }
        
		if (isSelected && null != attrSubElement) {
			$subElement.cs(url, reqParamName, this[0].options[this[0].selectedIndex].value, isHideNoOptionSelect);
		}
		
		function hideAllSubElements($select) {
			if (this.multiple || !isHideNoOptionSelect) return;
			$select.hide();
			var attrSubElement= $select.attr("subElement");
			if (null != attrSubElement){
				hideAllSubElements($("#" + attrSubElement));
			}
		}
	}
});
// -->

