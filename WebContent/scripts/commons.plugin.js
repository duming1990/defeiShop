/**
 * @author Wu,Yang
 * @version 2010-08-30
 * @param para 需要合并的td
 * @param parentObj td所属的父元素
 * @desc 相同元素的行合并
 */
function trMerge(para, parentObj) {
	var that;
	$("." + para, parentObj).each(function(){
		if ((that != undefined) && ($(this).html() == $(that).html())) {
			rowspan = $(that).attr("rowSpan");
			if (rowspan == undefined) {
				$(that).attr("rowSpan", 1);   
				rowspan = $(that).attr("rowSpan");   
			}
			rowspan = Number(rowspan) + 1;
			$(that).attr("rowSpan", rowspan); // do your action for the colspan cell here
			$(this).remove(); // .remove(); // do your action for the old cell here
	    } else {
			that = this;
	    }
	});
}

/**
 * @author Wu,Yang
 * @version 2010-08-30
 * @desc select的ajax联动
 */
function doSelectAjax(url, parentElement, parentValue, sonElement, sonValue, methodValue){
	parentElement.change(function(){
		var TrimParentElementValue = $.trim(parentElement.val());
		if("" != TrimParentElementValue){
			$.ajax({
					type: "POST",
					url: url,
					data: "method=" + methodValue + "&parentElementValue=" + TrimParentElementValue,
					dataType: "json",
					error: function(request, settings) {alert("数据加载请求失败！"); },
					success: function(Dates) {
						sonElement.empty().show();
						sonElement.get(0).options.add(new Option("请选择...", ""));
						if (Dates.length > 1) {
							for(var i = 0; i < Dates.length - 1; i++) {
								var opt = new Option(Dates[i].date_desc, Dates[i].date_id); 
								opt.title = Dates[i].date_desc;
								sonElement.get(0).options.add(opt);
							}
							sonElement.attr("value", sonValue);
						} 
					}
			});
		}
	});
	if ("" != parentValue) {
		parentElement.change();
	}
}

/**
 * @author Wu,Yang
 * @version 2010-08-30
 * @desc select的ajax联动
 */
function doSelectAjaxWithCheckbox(url, parentElement, parentValue, sonElement, sonValue, methodValue){
	parentElement.change(function(){
		var TrimParentElementValue = $.trim(parentElement.val());
		if("" != TrimParentElementValue){
			$.ajax({
				type: "POST",
				url: url,
				data: "method=" + methodValue + "&parentElementValue=" + TrimParentElementValue,
				dataType: "json",
				error: function(request, settings) {alert("数据加载请求失败！"); },
				success: function(Dates) {
					sonElement.empty();
					if (Dates.length > 1) {
						for(var i = 0; i < Dates.length - 1; i++) {
						var checkboxHtml = '<input type="checkbox" name="pd_types" id="pd_type_id_' + Dates[i].date_id + '" value="' + Dates[i].date_id + '" />&nbsp;<label for="pd_type_id_' + Dates[i].date_id + '">' + Dates[i].date_desc + '</label>&nbsp;';					
							sonElement.append(checkboxHtml);
						}
					} else {
						sonElement.append("<span style='color:#F00;'>没有数据</span>");
					}
				}
			});
		}
	});
	if ("" != parentValue) {
		parentElement.change();
	}
}

/**
 * @author Wu,Yang
 * @version 2011-04-29
 * @desc 出库计划审核 中 修改物流公司的ID
 */
function updateWlCompId (obj, outPlanStoreId, src_wl_comp_id) {
	var wlCompId = $(obj).prev().val();
	if ("" == outPlanStoreId) {
		alert("请选择物流公司");
		return false;
	}
	if (wlCompId == src_wl_comp_id) {
		$.jGrowl("您选择的物流公司未改变，请选择不同的物流公司！",{theme:  "error"});
		return false;
	}
	$.ajax({
		type: "POST",
		url:  "OutStorePlanAudit.do",
		data: "method=updateWlCompId&wlCompId=" + wlCompId + "&outPlanStoreId=" + outPlanStoreId,
		dataType: "json",
		error: function(request, settings) {alert("数据加载请求失败！"); },
		success: function(Dates) {
			if ("0" == Dates) {
				$.jGrowl("修改物流公司失败",{theme:  "error"});
			} else {
				$.jGrowl("修改物流公司成功",{theme:  "success"});
			}
		}
	});
	
}


/**
 * @author Wu,Yang
 * @version 2011-04-29
 * @desc 入库计划审核 中 修改仓库的ID
 */
function updateStore (obj, in_storeplan_id, src_store_id) {
	var storeId = $(obj).prev().val();
	if ("" == in_storeplan_id) {
		alert("请选择仓库");
		return false;
	}
	if (src_store_id == storeId) {
		$.jGrowl("您选择的仓库未改变，请选择不同的仓库！",{theme:  "error"});
		return false;
	}
	$.ajax({
		type: "POST",
		url:  "InStorePlanAudit.do",
		data: "method=updateStore&storeId=" + storeId + "&in_storeplan_id=" + in_storeplan_id,
		dataType: "json",
		error: function(request, settings) {alert("数据加载请求失败！"); },
		success: function(Dates) {
			if ("0" == Dates) {
				$.jGrowl("修改仓库失败",{theme:  "error"});
			} else {
				$.jGrowl("修改仓库成功",{theme:  "success"});
			}
		}
	});
	
}

/**
 * @author Qin,Yue
 * @version 2011-05-02
 * @desc 从后台取主属性构造成HTML
 */
function getAttrToHtml(url,cls_id){
	$("#attr_tbody").empty();
	//请求自定义主属性
	$.ajax({
		type: "POST",
		url: url,
		data: "cls_id="+cls_id,
		dataType: "json",
		error: function(request, settings) {alert("数据导出失败！");},
		success: function(data) {
			//alert(data.list.length);
			if (data.list.length >=  1) {
				var attr_html = "";
				for(var i = 0; i < data.list.length; i++) {
					var cur=data.list[i];
					if(cur.type == 1){//input
						attr_html += "<tr id=\"tr_"+ cur.id +"\">";
						if(cur.is_show == 1){
							attr_html += " <td width=\"12%\" class=\"title_item\" nowrap=\"nowrap\" rowspan=\"2\" align=\"right\">";
						}else{
							attr_html += " <td width=\"12%\" class=\"title_item\" nowrap=\"nowrap\" align=\"right\">";
						}
						if(cur.is_required == 1){
							attr_html += "<span style=\"color: #F00;\">*</span>";
						}
						attr_html += cur.attr_show_name + "：";
						attr_html += "</td>";
						attr_html += " <td width=\"88%\" colspan=\"2\">";
						for(var j = 0; j < cur.son_list.length; j++) {
							var cur_son = cur.son_list[j];
							attr_html += " <label for=\"zdy_" + cur_son.id + "\" style=\"width:80px;\">";
							if(cur.is_required == 1 && j == 0){
								attr_html += "<input type=\"radio\" name=\"zdy_content_"+cur.id+"\" id=\"zdy_"+cur_son.id+"\" value=\"" +cur_son.id +"\" dataType=\"Group\" msg=\""+cur.attr_show_name+"必须选择！\"/>";
							}else{
								attr_html += "<input type=\"radio\" name=\"zdy_content_"+cur.id+"\" id=\"zdy_"+cur_son.id+"\" value=\"" +cur_son.id +"\" />";
							}
							attr_html += cur_son.attr_show_name;
							attr_html += "</label>";
						}
						attr_html += " <input type=\"hidden\" name=\"zdy_column\" value=\""+cur.id+","+cur.type+","+cur.attr_show_name+","+cur.is_required+","+cur.is_show+","+cur.order_value+"\" />";
						attr_html += "</td>";
						attr_html += "</tr>";
						
						if(cur.is_show == 1){
							attr_html += "<tr>";
							attr_html += " <td width=\"88%\" colspan=\"2\">";
							if(cur.is_required == 1){
								attr_html += "<input type=\"text\" name=\"zdy_content2_"+cur.id+"\" id=\"zdy_"+cur.id+"\" class=\"webinpute\" dataType=\"Require\" msg=\""+cur.attr_show_name+"必须填写！\"/>";
							}else{
								attr_html += "<input type=\"text\" name=\"zdy_content2_"+cur.id+"\" id=\"zdy_"+cur.id+"\" class=\"webinpute\"/>";
							}
							attr_html += "</td>";
							attr_html += "</tr>";
						}
					}
					if(cur.type == 3){//radio
						attr_html += "<tr id=\"tr_"+ cur.id +"\">";
						attr_html += " <td width=\"12%\" class=\"title_item\" nowrap=\"nowrap\" align=\"right\">";
						if(cur.is_required == 1){
							attr_html += "<span style=\"color: #F00;\">*</span>";
						}
						attr_html += cur.attr_show_name + "：";
						attr_html += "</td>";
						
						attr_html += " <td width=\"88%\" colspan=\"2\">";
						for(var j = 0; j < cur.son_list.length; j++) {
							var cur_son = cur.son_list[j];
							attr_html += " <label for=\"zdy_" + cur_son.id + "\" style=\"width:80px;\">";
							if(cur.is_required == 1 && j == 0){
								attr_html += "<input type=\"radio\" name=\"zdy_content_"+cur.id+"\" id=\"zdy_"+cur_son.id+"\" value=\"" +cur_son.id +"\" dataType=\"Group\" msg=\""+cur.attr_show_name+"必须选择！\"/>";
							}else{
								attr_html += "<input type=\"radio\" name=\"zdy_content_"+cur.id+"\" id=\"zdy_"+cur_son.id+"\" value=\"" +cur_son.id +"\" />";
							}
							attr_html += cur_son.attr_show_name;
							attr_html += "</label>";
						}
						attr_html += " <input type=\"hidden\" name=\"zdy_column\" value=\""+cur.id+","+cur.type+","+cur.attr_show_name+","+cur.is_required+","+cur.is_show+","+cur.order_value+"\" />";
						attr_html += "</td>";
						attr_html += "</tr>";
					}
					if(cur.type == 4){//checkbox
						attr_html += "<tr id=\"tr_"+ cur.id +"\">";
						attr_html += " <td width=\"12%\" class=\"title_item\" nowrap=\"nowrap\" align=\"right\">";
						if(cur.is_required == 1){
							attr_html += "<span style=\"color: #F00;\">*</span>";
						}
						attr_html += cur.attr_show_name + "：";
						attr_html += "</td>";
						
						attr_html += " <td width=\"88%\" colspan=\"2\">";
						for(var j = 0; j < cur.son_list.length; j++) {
							var cur_son = cur.son_list[j];
							attr_html += " <label for=\"zdy_" + cur_son.id + "\" style=\"width:80px;\">";
							if(cur.is_required == 1 && j == 0){
								attr_html += "<input type=\"checkbox\" name=\"zdy_content_"+cur.id+"\" id=\"zdy_"+cur_son.id+"\" value=\"" +cur_son.id +"\" dataType=\"Group\" msg=\""+cur.attr_show_name+"必须选择！\"/>";
							}else{
								attr_html += "<input type=\"checkbox\" name=\"zdy_content_"+cur.id+"\" id=\"zdy_"+cur_son.id+"\" value=\"" +cur_son.id +"\" />";
							}
							attr_html += cur_son.attr_show_name;
							attr_html += "</label>";
						}
						attr_html += " <input type=\"hidden\" name=\"zdy_column\" value=\""+cur.id+","+cur.type+","+cur.attr_show_name+","+cur.is_required+","+cur.is_show+","+cur.order_value+"\" />";
						attr_html += "</td>";
						attr_html += "</tr>";
					}
					if(cur.type == 5){//select
						attr_html += "<tr id=\"tr_"+ cur.id +"\">";
						attr_html += " <td width=\"12%\" class=\"title_item\" nowrap=\"nowrap\" align=\"right\">";
						if(cur.is_required == 1){
							attr_html += "<span style=\"color: #F00;\">*</span>";
						}
						attr_html += cur.attr_show_name + "：";
						attr_html += "</td>";
						
						attr_html += " <td width=\"88%\" colspan=\"2\">";
						if(cur.is_required == 1){
							attr_html += " <select name=\"zdy_content_" + cur.id + "\" id=\"zdy_"+ cur.id+"\" style=\"width:80px;\" dataType=\"Require\" msg=\""+cur.attr_show_name+"必须填写！\">";
						}else{
							attr_html += " <select name=\"zdy_content_" + cur.id + "\" id=\"zdy_"+ cur.id+"\" style=\"width:80px;\">";
						}
						attr_html += "<option value=\"\">请选择...</option>";
						for(var j = 0; j < cur.son_list.length; j++) {
							var cur_son = cur.son_list[j];
							attr_html += "<option  value=\"" +cur_son.id +"\">";
							attr_html += cur_son.attr_show_name;
							attr_html += "</option>";
						}
						attr_html += "</select>";
						attr_html += " <input type=\"hidden\" name=\"zdy_column\" value=\""+cur.id+","+cur.type+","+cur.attr_show_name+","+cur.is_required+","+cur.is_show+","+cur.order_value+"\" />";
						attr_html += "</td>";
						attr_html += "</tr>";
					}
				}
				$("#attr_tbody").append(attr_html);
			}
		}
	});
}

function setOnlyInteger() {
	$(this).css("ime-mode", "disabled").attr("t_value", "").attr("o_value", "").bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\d+)?|\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = 0;
	});
}

function setOnlyNum() {
	$(this).css("ime-mode", "disabled").attr("t_value", "").attr("o_value", "").bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = "";
	});
	//this.text.selected;
}