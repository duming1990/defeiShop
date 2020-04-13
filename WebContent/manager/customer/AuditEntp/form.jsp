<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
  <html-el:form action="/customer/AuditEntp.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
      <tr>
        <td class="title_item"><span id="p_index_span" style="color: #F00;">*</span>所在地区：</td>
        <td colspan="3">
        <html-el:select property="province" styleId="province" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="city" styleId="city" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="country" styleId="country" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select></td>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>店铺名称：</td>
            <td colspan="3"><html-el:text property="entp_name" maxlength="50" styleClass="webinput" styleId="entp_name" style="width:300px"/></td>
          </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>门头照片：</td>
        <td colspan="3">
        <c:if test="${not empty (af.map.entp_logo)}">
          <div id="image_path_div"><a href="${ctx}/${af.map.entp_logo}"  target="_blank">
          <img height="100" src="${ctx}/${af.map.entp_logo}@s400x400" /></a>(<a href="javascript:void(0);" onclick= "deleteImageOrVideo('${af.map.id}', 'image_path', '${af.map.entp_logo}') ">删除图片</a>)</div>
          <html-el:hidden styleId="entp_logo_s" property="entp_logo_s" value="${af.map.entp_logo}"/>
        </c:if>
        <br />
        <html-el:file property="entp_logo" styleId="entp_logo" style="width:45%" styleClass="webinput"/>
        </td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>商家简介：</td>
        <td colspan="3"><html-el:textarea property="entp_desc" styleId="entp_desc" rows="7" styleClass="webtextarea" /></td>
      </tr>
       <tr>
          <td class="title_item"><span style="color: #F00;">*</span>从事行业：</td>
          <td colspan="3">
           <html-el:select property="hy_cls_id" styleId="hy_cls_id">
           <html-el:option value="">请选择...</html-el:option>
            <c:forEach var="cur" items="${baseHyClassList}" varStatus="vs">
            <html-el:option value="${cur.cls_id}">${cur.cls_name}</html-el:option>
            </c:forEach>
           </html-el:select>
         </td>
       </tr>
       <tr>
            <td class="title_item"><span style="color: #F00;">*</span>主营产品类别：</td>
            <td colspan="3">
            <html-el:hidden property="main_pd_class_ids" styleId="main_pd_class_ids" />
            <html-el:textarea property="main_pd_class_names" styleId="main_pd_class_names" readonly="true" value="${af.map.main_pd_class_names}" rows="7" styleClass="webtextarea" onclick="getmain_pd_class_names();"/>
            &nbsp;
            <a class="butbase" style="cursor:pointer;" onclick="getmain_pd_class_names();" ><span class="icon-search">选择</span></a>
            </td>
       </tr>
       <tr>
       <td class="title_item"><span style="color: #F00;">*</span>详细地址：</td>
        <td colspan="3"><html-el:text property="entp_addr" maxlength="100" styleClass="webinput" styleId="entp_addr" style="width:400px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>商家地理位置：</td>
        <td colspan="3">
        <html-el:text property="entp_latlng" readonly="true" maxlength="128" style="width:250px;" styleClass="webinput" styleId="entp_latlng" />
          &nbsp;
          <input type="button" value="维护坐标" onclick="getLatlng('entp_latlng')" class="bgButton" /></td>
      </tr>
      <tr>
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>有无营业执照：</td>
        <td nowrap="nowrap" colspan="3">
        <html-el:select property="is_has_yinye_no" styleId="is_has_yinye_no">
                <html-el:option value="1">有</html-el:option>
                <html-el:option value="0">没有</html-el:option>
        </html-el:select>
      </td>
      </tr>
      <tr id="hasYinYeNo">
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>商家法人营业执照：</td>
        <td nowrap="nowrap" colspan="3">营业执照编码：
          <html-el:text property="entp_licence" maxlength="50" styleClass="webinput" styleId="entp_licence" style="width:150px" />
          &nbsp;&nbsp;&nbsp;&nbsp;营业执照扫描件：&nbsp;
          <html-el:file property="entp_licence_img" maxlength="15" styleClass="webinput" styleId="entp_licence_img" style="width:25%" />
          <c:if test="${not empty af.map.entp_licence_img}"> 
          	&nbsp;&nbsp;&nbsp;<a href="${ctx}/${af.map.entp_licence_img}" target="_blank">查看</a>
            <html-el:hidden property="entp_licence_img_s" value="${af.map.entp_licence_img}"/>
          </c:if>
      </td>
      </tr>
      <tbody id="notHasYinYeNo" style="display:none;">
      <tr>
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证正面：</td>
        <td nowrap="nowrap" colspan="3">
          <html-el:file property="img_id_card_zm" maxlength="15" styleClass="webinput" styleId="img_id_card_zm" style="width:25%" />
          <c:if test="${not empty af.map.img_id_card_zm}"> 
          	&nbsp;&nbsp;&nbsp;<a href="${ctx}/${af.map.img_id_card_zm}" target="_blank">查看</a>
            <html-el:hidden property="img_id_card_zm_s" value="${af.map.img_id_card_zm}"/>
          </c:if>
          <span style="color: #F00;">&nbsp;店铺所属人(法人)身份证正面图片，大小500K以内，文字须清晰可见。</span>
      </td>
      </tr>
      <tr>
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证背面：</td>
        <td nowrap="nowrap" colspan="3">
          <html-el:file property="img_id_card_fm" maxlength="15" styleClass="webinput" styleId="img_id_card_fm" style="width:25%" />
          <c:if test="${not empty af.map.img_id_card_fm}"> 
          	&nbsp;&nbsp;&nbsp;<a href="${ctx}/${af.map.img_id_card_fm}" target="_blank">查看</a>
            <html-el:hidden property="img_id_card_fm_s" value="${af.map.img_id_card_fm}"/>
          </c:if>
           <span style="color: #F00;">&nbsp;店铺所属人(法人)身份证背面图片，大小500K以内，文字须清晰可见。</span>
      </td>
      </tr>
      </tbody>
      <tr>
        <th colspan="4">联系方式</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>商家联系人：</td>
        <td colspan="3">
        <html-el:text property="entp_linkman" maxlength="10" styleClass="webinput" styleId="entp_linkman" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>联系电话：</td>
        <td colspan="3">
        <html-el:text property="entp_tel" maxlength="20" styleClass="webinput" styleId="entp_tel" style="width:200px" /></td>
      </tr>
       <tr>
        <td class="title_item">联系QQ：</td>
        <td colspan="3">
        <html-el:text property="qq" maxlength="200" styleClass="webinput" styleId="qq" style="width:200px" /></td>
      </tr>
       <tr>
        <td class="title_item"><span style="color: #F00;">*</span>营业时间：</td>
        <td colspan="3">
        <html-el:text property="yy_sj_between" maxlength="100" styleClass="webinput" styleId="yy_sj_between" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item">排序值：</td>
        <td colspan="3"><html-el:text property="order_value" maxlength="4" styleClass="webinput" styleId="order_value" style="width:200px" /></td>
      </tr>
        <tr>
        <th colspan="4">服务政策</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>折扣规则：</td>
        <td colspan="3">
         <html-el:select property="fanxian_rule" styleId="fanxian_rule" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
            <c:forEach var="cur" items="${baseData700List}" varStatus="vs">
            <html-el:option value="${cur.id}">${cur.type_name}</html-el:option>
            </c:forEach>
          </html-el:select>
        </td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center">
          <html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
		
	var regMobile = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
	var regPhone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 

	$("#entp_name").attr("datatype","Require").attr("msg","店铺名称必须填写");
	
	$("#entp_logo").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "门头照片必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	<c:if test="${empty (af.map.entp_logo)}">
		$("#entp_logo").attr("require", "true");
	</c:if>
	
	$("#entp_desc").attr("datatype","Limit").attr("min","1").attr("max","1500").attr("msg","商家简介在1500个汉字之内");
	$("#main_pd_class_names").attr("datatype","Require").attr("msg","主营产品必须填写");
	$("#entp_addr").attr("datatype","Require").attr("msg","详细地址必须填写");
	$("#entp_linkman").attr("datatype","Require").attr("msg","商家联系人必须填写");
	$("#entp_tel").attr("datatype","Require").attr("msg","联系电话必须填写");
	$("#yy_sj_between").attr("datatype","Require").attr("msg","营业时间必须填写");
	$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");
	$("#fanxian_rule").attr("datatype","Require").attr("msg","请选择返现政策");
	$("#hy_cls_id").attr("datatype","Require").attr("msg","请选择从事行业");
	
	
	if('' != $.trim($("#entp_tel").val()) && (regMobile.test($.trim($("#entp_tel").val())) == false) && (regPhone.test($.trim($("#entp_tel").val())) == false)){
		$("#entp_tel").attr("datatype","Mobile").attr("msg","电话格式不正确");
	}
	
	$("#entp_licence").attr("datatype","Require").attr("msg","营业执照编码必须填写");
	$("#entp_licence_img").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "营业执照扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	$("#order_value").focus(setOnlyNum);
	
	<c:if test="${af.map.is_has_yinye_no eq 1}">
	<c:if test="${empty (af.map.entp_licence_img)}">
		$("#entp_licence_img").attr("require", "true");
	</c:if>	
	</c:if>
	<c:if test="${af.map.is_has_yinye_no eq 0}">
		$("#notHasYinYeNo").show();
		$("#hasYinYeNo").hide();
		$("#img_id_card_zm").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
		$("#img_id_card_fm").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
		$("#entp_licence").attr("require",false);
		$("#entp_licence_img").attr("require",false);
		<c:if test="${empty (af.map.img_id_card_zm)}">
			$("#img_id_card_zm").attr("require", "true");
		</c:if>	
		<c:if test="${empty (af.map.img_id_card_fm)}">
			$("#img_id_card_fm").attr("require", "true");
		</c:if>	
	</c:if>
	
	
	$("#is_has_yinye_no").change(function(){
		var thisValue = $(this).val();
		if(thisValue == 1){
			$("#hasYinYeNo").show();
			$("#notHasYinYeNo").hide();
			$("#entp_licence").attr("datatype","Require").attr("msg","营业执照编码必须填写");
			$("#entp_licence_img").attr("dataType", "Filter" ).attr("msg", "营业执照扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_zm").attr("require",false);
			$("#img_id_card_fm").attr("require",false);
		}else{
			$("#notHasYinYeNo").show();
			$("#hasYinYeNo").hide();
			$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#entp_licence").attr("require",false);
			$("#entp_licence_img").attr("require",false);
		}
		
	});
	

});     

//地市选择
$("#province").citySelect({
	        data:getAreaDic(),
	        province:"${af.map.province}",
	        city:"${af.map.city}",
	        country:"${af.map.country}",
	        province_required : true,
	        city_required : true,
	        country_required : true,
	        callback:function(selectValue,selectText){
	        	if(null != selectValue && "" != selectValue){
	        		var p_indexs = selectValue.split(",");
	        		if(null != p_indexs && p_indexs.length > 0){
	        			$("#p_index").val(p_indexs[p_indexs.length - 1]);
	        		}
	        	}
	        }
	 });

$("#province").change(function(){
	if (this.value.length != 0) {
		this.form.p_index.value = this.value;
		$("#city").val("");
		$("#country").val("");
	}
});
$("#city").change(function(){
	if (this.value.length != 0) {
		this.form.p_index.value = this.value;
		$("#country").val("");
	} else {
		this.form.p_index.value = this.form.province.value;
	}
});
$("#country").change(function(){
	var thisVal = $(this).val();
	//查询该地区是否有合伙人
	if(null != thisVal && '' != thisVal){
	$.post("${ctx}/CsAjax.do?method=jugdeHasSeviceCenterByPindex",{p_index: thisVal},function(data){
	    if (!data.result) {
	    	var submit = function (v, h, f) {
			    if (v == true) {
			    	location.href="EntpMsg.do?par_id=1100100000&mod_id=1100100200";
			    } 
			    return true;
			};
			myConfirm("该地区暂无合伙人，请前往留言中心留言？",submit);
	    	 $("#btn_submit").attr("disabled", "true");
	    	return false;
	   	}else{
	   		if(thisVal.length != 0) {
	   			$("#p_index").val(thisVal);
	   		} else {
	   			$("#p_index").val($("#city").val());
	   		}
	   	    $("#btn_submit").removeAttr("disabled");
	   	}
	});
	}
});

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}

// 提交
$("#btn_submit").click(function(){

	if(Validator.Validate(f, 3)){
		 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		 $("#btn_reset").attr("disabled", "true");
		 $("#btn_back").attr("disabled", "true");
		 f.submit();
	}
});

function setOnlyNum() {
	$(this).css("ime-mode", "disabled");
	$(this).attr("t_value", "");
	$(this).attr("o_value", "");
	$(this).bind("dragenter",function(){
		return false;
	});
	$(this).keypress(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).keyup(function (){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/))this.value=this.t_value;else this.t_value=this.value;if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/))this.o_value=this.value;
	}).blur(function (){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/))this.value=this.o_value;else{if(this.value.match(/^\.\d+$/))this.value=0+this.value;if(this.value.match(/^\.$/))this.value=0;this.o_value=this.value;}
		if(this.value.length == 0) this.value = 0;
	});
}


function deleteImageOrVideo(id, type, file_path){
	 var k = window.confirm("您确定要删除吗?");
	 if (k) {
			$.post("AuditEntp.do?method=deleteImageOrVideo" , {
				id : id ,type: type, file_path : file_path
			}, function(d){
				if(d == "success"){
					$("#" + type + "_div").remove();
					$("#entp_logo_s").val("");
				}
			});
	 } 
}

function getmain_pd_class_names() {
	var main_pd_class_ids  =$("#main_pd_class_ids").val();
	var main_pd_class_names  =$("#main_pd_class_names").val();
	var url = "${ctx}/CsAjax.do?method=listPdClass&main_pd_class_ids=" + main_pd_class_ids  +"&main_pd_class_names=" + main_pd_class_names;
	$.dialog({
		title:  "选择主营产品",
		width:  770,
		height: 450,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

function getLatlng(obj){
	var url = "${ctx}/CsAjax.do?method=getBMap&result_id=" + obj;
	$.dialog({
		title:  "选择坐标",
		width:  900,
		height: 520,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}

//]]></script>
</body>
</html>
