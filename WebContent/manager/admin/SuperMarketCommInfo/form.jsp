<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/commons/styles/icons/icons.css" rel="stylesheet" type="text/css" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/btns.css"  />
<link rel="stylesheet" href="${ctx}/commons/swfupload/style/default.css" type="text/css" />
<style type="text/css">
.file_hidden_class {
	height:24px;
	border-top:1px #a8a8a8 solid;
	line-height:24px;
	padding-left:6px;
	border-left:1px #a8a8a8 solid;
	border-right:1px #e6e6e6 solid;
	border-bottom:1px #e6e6e6 solid;
}
</style>
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
      <html-el:form action="/admin/SuperMarketCommInfo.do"  enctype="multipart/form-data">
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="save" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="par_id" styleId="par_id" />
        <html-el:hidden property="p_index" styleId="p_index" />
        <html-el:hidden property="id" styleId="id" />
        <html-el:hidden property="pd_id" styleId="pd_id" />
        <html-el:hidden property="own_entp_id" styleId="own_entp_id" /> 
        <html-el:hidden property="par_cls_id" styleId="par_cls_id" />
        <html-el:hidden property="brand_id" styleId="brand_id" />
        <html-el:hidden property="comm_type" styleId="comm_type" />
        <html-el:hidden property="old_comm_id" styleId="old_comm_id" value="${af.map.comm_id}"/>
        <html-el:hidden property="tczh_names" styleId="tczh_names" />
	    <html-el:hidden property="tczh_prices" styleId="tczh_prices" />
	    <html-el:hidden property="inventorys" styleId="inventorys" />
	    <html-el:hidden property="orig_prices" styleId="orig_prices" />
	    <html-el:hidden property="buyer_limit_nums" styleId="buyer_limit_nums" />
	    <html-el:hidden property="tag_ids" styleId="tag_ids" />
        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>商品名称：</td>
        <td colspan="2" width="88%"><html-el:text property="comm_name" onblur="validate($('#comm_name').val());" styleId="comm_name" maxlength="128" style="width:280px" styleClass="webinput"/></td>
      </tr>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>商品编号：</td>
        <td colspan="2" width="88%"><html-el:text property="comm_no" styleId="comm_no" maxlength="125" style="width:280px" styleClass="webinput" readonly="true"/></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否扶贫：</td>
        <td colspan="2" width="88%"><html-el:select property="is_aid" styleId="is_aid">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="aid_scale_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>扶贫比例：</td>
        <td colspan="2" width="88%"><html-el:text property="aid_scale" styleId="aid_scale" maxlength="8" style="width:80px" styleClass="webinput" /><b>&nbsp;%</b>
        <span style="color:rgb(241, 42, 34)">（注：比例范围0~100，最多两位小数，并且返利比例+扶贫比例不能超过100）</span></td>
      </tr>
      <tr id="poor_tr">
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>扶贫对象：</td>
        <td colspan="2" width="88%">
       	  <html-el:hidden property="temp_poor_ids" styleId="temp_poor_ids" value="${temp_poor_ids}" />
       	   <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="poor_table">
                <tr>
                  <th nowrap="nowrap" colspan="3"><b><a class="butbase" onclick="choosePoorInfo();"><span class="icon-search">选择</span></a></b></th>
                </tr>
                <tr id="poor_list_tr">
                	<th nowrap="nowrap" width="40%">贫困户姓名</th>
                	<th nowrap="nowrap" width="50%">行政区划</th>
                	<th nowrap="nowrap" width="10%">操作</th>
                </tr>
                <c:forEach items="${commInfoPoorsList}" var="cur">
                	<tr>
    			 <input type="hidden" name="poor_ids" value="${cur.poor_id}" />
    			 <td align="center">${cur.map.real_name}</td>
    			 <td align="center">${cur.map.p_name}</td>
    			 <td align="center"><b><a class="butbase" onclick="delPoorInfo(this,${cur.poor_id});"><span class="icon-configure">删除</span></a></b></td>
    			 </tr>
                </c:forEach>
          </table>
        </td>
      </tr>
        <tr>
        <td colspan="3" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
        </table>
      </html-el:form>
    <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>

<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${ctx}/commons/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="${ctx}/scripts/commons.plugin.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/swfupload.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/swfupload/handlers.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript">//<![CDATA[
var del_num = 0;
$(document).ready(function(){

	$("#comm_no").attr("datatype", "Require").attr("msg", "请填写商品编号！");
	$("#comm_name").attr("datatype", "Require").attr("msg", "请填写商品名称！");
	$("#is_aid").attr("dataType", "Require").attr("msg", "请选择是否扶贫！");
	
	var f = document.forms[0];

	// 通过判断是否扶贫控制扶贫比例的显示
	if("1" != $("#is_aid").val()){
		$("#aid_scale_tr").hide();
		$("#poor_tr").hide();
	}else {
		$("#aid_scale").attr("dataType", "Range").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的扶贫比例！");
		$("#pet_name").attr("dataType", "Require").attr("msg", "请选择扶贫对象！");
	}
	$("#is_aid").change(function(){
		if("1" == $(this).val()){
			$("#aid_scale").attr("dataType", "Range").attr("min", "0").attr("max", "100").attr("require", "true").attr("msg", "请填写正确的扶贫比例！");
			$("#aid_scale_tr").show();
			$("#poor_tr").show();
		}else {
			$("#aid_scale").removeAttr("dataType");
			$("#aid_scale").val(0);//扶贫比例清0
			$("#aid_scale_tr").hide();
			$("#pet_name").val("");//扶贫对象清空
			$("#poor_tr").hide();
		}
	});
	
	$("#aid_scale").blur(function(){
		var sum_scale = (parseFloat($("#rebate_scale").val()||0) + parseFloat($("#aid_scale").val()||0)).toFixed(2);
		if(sum_scale>=100){
			alert("返利比例与扶贫比例之和不能超过100，请重新填写");
			$(this).val(0);
			$(this).focus(setOnlyNum);
		}
	});
	

	
	$("#aid_scale").focus(setOnlyNum);
	$("#order_value").focus(setOnlyNum2);



	$("#btn_submit").click(function(){
		if("1" == $("#is_aid").val()){
			if($("input[name='poor_ids']").length==0){
				alert("请选择扶贫对象");
				return false;
			}
		}
		
		
		if(Validator.Validate(f, 1)){
			if("1" == $("#is_aid").val()&&$("input[name^='poor_ids']").length>"${baseData1901.pre_number}"){
				alert("所选扶贫对象数量大于系统限制，请重新选择！");
				return false;
			}
			if($("#audit_state_old").val() == 1){
				var submit2 = function(v, h, f) {
					if (v == "ok") {
						$("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
				        $("#btn_reset").attr("disabled", "true");
				        $("#btn_back").attr("disabled", "true");
						document.forms[0].submit();
					}
				};
				$.jBox.confirm("确定修改?", "确定提示", submit2);
			}else{
				 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		         $("#btn_reset").attr("disabled", "true");
		         $("#btn_back").attr("disabled", "true");
				f.submit();
			}
		}
		return false;
	});

  $("a[id^='del_']").click(function(){
		var obj = $(this);
		$.post("CommInfo.do?method=deleteFile" , {
			id : $(obj).get(0).id.substring(4)
		}, function(d){
			if(d == "success"){
				del_num = del_num + 1;
				$(obj).parent().remove();
			}
		});
	})
});


function updateAll(obj){
	var $this = $(obj);
	if($this.next().is(":hidden")){
		$this.next().show();
	}else{
		$this.next().hide();
	}
}
function closeUpdateAll(obj){
	$(obj).parent().hide();
}

function setUpdateVal(data_flag,obj){
	var setVal = $(obj).prev().val();
	var curr = /^\d+(\.\d+)?$/;
	if(null != setVal && "" != setVal && curr.test(setVal)){
		$("#tczh_tbody").find("input[name='"+ data_flag +"']").val(setVal);
		$(obj).parent().hide();
	}else{
		alert("数据有误，请重新填写");
	}
}
function DayFunc(){
	var c = $dp.cal;
	var todate = new Date(c.getP('y'),c.getP('M')-1,c.getP('d'));
	todate.setDate(todate.getDate()+365);
	$("#down_date").val(todate.format("yyyy-MM-dd")); 
}

function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	})
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value}
		if(this.value.length == 0) this.value = 0;
	})
}

function setOnlyNum2() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^\d*?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+]?\d+(?:\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^$/))this.value=0;this.o_value=this.value};
		if(this.value.length == 0) this.value = "0";
	});
	//this.text.selected;
}

function setOnlyNum3(obj) {
	$(obj).css("ime-mode", "disabled");
	$(obj).attr("t_value", "");
	$(obj).attr("o_value", "");
	$(obj).bind("dragenter",function(){
		return false;
	})
	$(obj).keypress(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value
	}).keyup(function (){
		if(!obj.value.match(/^[\+\-]?\d*?\.?\d*?$/))obj.value=obj.t_value;else obj.t_value=obj.value;if(obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))obj.o_value=obj.value
	}).blur(function (){
		if(!obj.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))obj.value=obj.o_value;else{if(obj.value.match(/^\.\d+$/))obj.value=0+obj.value;if(obj.value.match(/^\.$/))obj.value=0;obj.o_value=obj.value}
		if(obj.value.length == 0) obj.value = 0;
	})
}

function delete_confirm(){
	 var k = window.confirm("您确定要删除该附件吗?");
	 if (k) {
	 	event.returnValue=true;
	 } else {
	    event.returnValue=false;
	 }
}


function choosePoorInfo(){
	var p_index = $("#p_index").val();
	var province = '';
	var city = '';
	var country = '';
	if('' != p_index && p_index.length == 6){
		province = p_index.substring(0,2) + '0000';
		city = p_index.substring(0,4) + '00';
		country = p_index;
	}
	var url = "${ctx}/BaseCsAjax.do?method=choosePoorInfo&dir=admin&type=multiple&province="+province+"&city="+city+"&country="+country;
	$.dialog({
		title:  "选择贫困户",
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}

function returnPoorInfo(ids,names,p_names){
	 var temp_poor_ids = $("#temp_poor_ids").val() || "";
     if(null!=ids&&''!=ids){
    	 var id_array = ids.split(',');
    	 var name_array = names.split(',');
    	 var pname_array = p_names.split(',');
    	 for(var i=0;i<id_array.length;i++){
    		 if(temp_poor_ids.indexOf(','+id_array[i]+',')==-1){
    			 var html = "";	
    			 html = '<tr>';
    			 html += '<input type="hidden" name="poor_ids" value="'+id_array[i]+'" />';
    			 html += '<td align="center">'+name_array[i]+'</td>';
    			 html += '<td align="center">'+pname_array[i]+'</td>';
    			 html += '<td align="center"><b><a class="butbase" onclick="delPoorInfo(this,'+id_array[i]+');"><span class="icon-configure">删除</span></a></b></td>';
    			 html += '</tr>';
    			 $("#poor_list_tr").last().after(html);
    			 temp_poor_ids = temp_poor_ids+","+id_array[i]+',';
    			 $("#temp_poor_ids").val(temp_poor_ids);
    		 }
    	 }
     }
}
function delPoorInfo(obj,id){
	 $(obj).parent().parent().parent().remove();
	 var str = $("#temp_poor_ids").val().replace(','+id+',',',');
	 $("#temp_poor_ids").val(str);
}


function validate(value){
	if ("" != value) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validate&type=comm_name&value="+value+ "&id=${af.map.id}&t=" + new Date(),
				dataType: "json", 
		        async: true, 
		        error: function (request, settings) {alert(" 数据加载请求失败！ ");	$("#btn_submit").attr("disabled", "true");}, 
		        success: function (data) {
					if (data.ret == 0) {
						alert('参数丢失！', '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					} else if (data.ret == 1) {
						$("#btn_submit").removeAttr("disabled");
					} else if (data.ret == -1) {
						alert(data.msg, '提示');
						$("#btn_submit").attr("disabled", "true");
						return false;
					}
		        }
			});
	}
}

function setHuokuan(obj){
	var price = $(obj).val();
	var rebate_scale = 0;
	var aid_scale = 0;
	if("1"==$("#is_rebate").val()){
		rebate_scale = $("#rebate_scale").val();
	}
	if("1"==$("#is_aid").val()){
		aid_scale = $("#aid_scale").val();
	}
	var hk_price = (parseFloat(price||0)*((parseFloat(100)-(parseFloat(rebate_scale||0) + parseFloat(aid_scale||0))).toFixed(2)/parseFloat(100)).toFixed(4)).toFixed(2);
	$(obj).parent().children("span").html(hk_price);
}

var swfu;
var settings = {
	ctx : "${ctx}",// 路径
	entity_id : "${af.map.id}",// 当前数据的id
	delete_url : "${ctx}/CsAjax.do",// ajax删除文件的url
	delete_method : "deleteFileForCosSwfupload",// ajax删除文件的method
	upload_single_file : false,// 是否上传单个文件,默认上传多个文件
	flash_url : "${ctx}/commons/swfupload/swfupload.swf",
	upload_url : "${ctx}/CosUploader.do?jsessionid=${sessionId}",
	can_upload:${5 - (CommImgsListCount)},
	no_image : "${ctx}/commons/swfupload/style/images/no_image.jpg",
	post_params : {
		"uploadTimer" : new Date()
	},
	file_size_limit : "2 MB",
	file_types : "*.jpg;*.jpeg;*.gif;*.png;*.bmp",
	file_types_description : "All Files",
	file_upload_limit : 5,//上次文件个数限制
	file_queue_limit : 5,
	custom_settings : {
		progressTarget : "fsUploadProgress",
		cancelButtonId : "btnCancel",
		upload_successful : false
	},
	// Button settings
	button_image_url : "${ctx}/commons/swfupload/style/images/upload.png",
	button_placeholder_id : "spanButtonPlaceHolder",
	button_text : '',
	button_cursor : -2,
	button_text_style: ".theFont { font-size: 12; color: #FFFFFF;}",
	button_width: 64,
	button_height : 22,
	<c:if test="${CommImgsListCount gt 5}">
	button_disabled : true,
	button_image_url : "${ctx}/commons/swfupload/style/images/upload_disabled.png",
	</c:if>
	// The event handler functions are defined in handlers.js
	file_queued_handler : fileQueued,
	file_queue_error_handler : fileQueueError,
	file_dialog_complete_handler : fileDialogComplete,
	upload_start_handler : uploadStart,
	upload_progress_handler : uploadProgress,
	upload_error_handler : uploadError,
	upload_success_handler : uploadSuccess,
	upload_complete_handler : uploadComplete,
	queue_complete_handler : queueComplete
// Queue plugin event
};
swfu = new SWFUpload(settings);
//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>