<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/EntpInfoAudit.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id"/>
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="p_index" styleId="p_index" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
     <tr>
        <th colspan="4">基本信息</th>
      </tr>
     <c:if test="${empty af.map.id}">
      <tr>
      	<td class="title_item"><span id="p_index_span" style="color: #F00;">*</span>注册会员：</td>
      	<td colspan="3"><html-el:text property="add_user_name" maxlength="50" styleClass="webinput" styleId="user_name" style="width:200px" onclick="openChildForUser();" readonly="true" />
      		<html-el:hidden property="add_user_id" styleId="person_user_id" />
      		&nbsp;
            <a class="butbase" style="cursor:pointer;" onclick="openChildForUser();" ><span class="icon-search">选择</span></a>
      	</td>
      </tr>
      </c:if>
      <c:if test="${not empty af.map.id}">
      <tr>
      	<td class="title_item"><span id="p_index_span" style="color: #F00;">*</span>注册会员：</td>
      	<td colspan="3">${af.map.add_user_name}</td>
      </tr>
      </c:if>
      <tr>
        <th colspan="4">联系方式</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>联系人姓名：</td>
        <td colspan="3"><html-el:text property="entp_linkman" maxlength="10" styleClass="webinput" styleId="entp_linkman" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>联系人手机：</td>
        <td colspan="3"><html-el:text property="entp_tel" maxlength="20" styleClass="webinput" styleId="entp_tel" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item">联系人QQ：</td>
        <td colspan="3"><html-el:text property="qq" maxlength="50" styleClass="webinput" styleId="qq" style="width:200px" /></td>
      </tr>
      <tr>
        <th colspan="4">公司信息</th>
      </tr>
     <%--  <tr>
        <td class="title_item"><span style="color: #F00;">*</span>申请人姓名：</td>
        <td colspan="3"><html-el:text property="entp_sname" maxlength="20" styleClass="webinput" styleId="entp_sname" style="width:200px" /></td>
      </tr> --%>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>所在地区：</td>
        <td colspan="3" id="city_div"><html-el:select property="province" styleId="province" styleClass="pi_prov" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="city" styleId="city" styleClass="pi_city" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select>
          &nbsp;
          <html-el:select property="country" styleId="country" styleClass="pi_dist" style="width:120px;">
            <html-el:option value="">请选择...</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>详细地址：</td>
        <td colspan="3"><html-el:text property="entp_addr" maxlength="100" styleClass="webinput" styleId="entp_addr" style="width:400px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>商家地理位置：</td>
        <td colspan="3"><html-el:text property="entp_latlng" readonly="true" maxlength="128" style="width:250px;" styleClass="webinput" styleId="entp_latlng" />
          &nbsp;
          <input type="button" value="维护坐标" onclick="getLatlng('entp_latlng')" class="bgButton" /></td>
      </tr>
      <tr>
        <th colspan="4">店铺信息</th>
      </tr>
      <c:if test="${not empty af.map.entp_no}">
        <tr>
          <td width="14%" nowrap="nowrap" class="title_item">商家编号：</td>
          <td colspan="3">${af.map.entp_no}</td>
        </tr>
      </c:if>
      <tr>
        <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>店铺名称：</td>
        <td colspan="3"><html-el:text property="entp_name" maxlength="50" styleClass="webinput" onblur="validate($('#entp_name').val());" styleId="entp_name" style="width:300px"/></td>
      </tr>

      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>门头照片：</td>
        <td colspan="3"><c:if test="${not empty (af.map.entp_logo)}">
            <div id="image_path_div"><a href="${ctx}/${af.map.entp_logo}"  target="_blank"> <img height="100" src="${ctx}/${af.map.entp_logo}@s400x400" /></a>(<a href="javascript:void(0);" onclick= "deleteImageOrVideo('${af.map.id}', 'image_path', '${af.map.entp_logo}') ">删除图片</a>)</div>
            <html-el:hidden styleId="entp_logo_s" property="entp_logo_s" value="${af.map.entp_logo}"/>
          </c:if>
          <br />
          <html-el:file property="entp_logo" styleId="entp_logo" style="width:45%" styleClass="webinput"/>
          <br/>
          <c:url var="urlps" value="/IndexPs.do" />
          <span>说明：[图片比例]建议：1:1 [图片尺寸] 建议：[600px * 600px] ，图片大小不超过2M。<a href="${urlps}" class="label label-danger" target="_blank">[在线编辑图片]</a></span>
        </td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>店铺简介：</td>
        <td colspan="3"><html-el:textarea property="entp_desc" styleId="entp_desc" rows="7" style="width:60%" styleClass="webtextarea" /></td>
      </tr>
      <tr>
        <td class="title_item">商家详细信息：</td>
        <td colspan="2"><html-el:textarea property="entp_content" styleId="entp_content" style="width:650px;height:200px;visibility:hidden;" styleClass="webinput"></html-el:textarea>
          <div>点击【第一排】顺数【最后一个】按钮可实现全屏编辑</div></td>
      </tr>
	  <tr>
        <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>有无营业执照：</td>
        <td nowrap="nowrap" colspan="3"><html-el:select property="is_has_yinye_no" styleId="is_has_yinye_no">
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
          <c:if test="${not empty af.map.entp_licence_img}"> &nbsp;&nbsp;&nbsp;<a href="${ctx}/${af.map.entp_licence_img}" target="_blank">查看</a>
            <html-el:hidden property="entp_licence_img_s" value="${af.map.entp_licence_img}"/>
          </c:if>
        </td>
      </tr>
      <tbody id="notHasYinYeNo" style="display:none;">
        <tr>
          <td class="title_item"><span style="color: #F00;">*</span>身份证号：</td>
          <td colspan="3"><html-el:text property="id_card_no" maxlength="20" styleClass="webinput" styleId="id_card_no" style="width:200px" /></td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证正面：</td>
          <td nowrap="nowrap" colspan="3"><html-el:file property="img_id_card_zm" maxlength="15" styleClass="webinput" styleId="img_id_card_zm" style="width:25%" />
            <c:if test="${not empty af.map.img_id_card_zm}"> &nbsp;&nbsp;&nbsp;<a href="${ctx}/${af.map.img_id_card_zm}" target="_blank">查看</a>
              <html-el:hidden property="img_id_card_zm_s" value="${af.map.img_id_card_zm}"/>
            </c:if>
            <span style="color: #F00;">&nbsp;店铺所属人(法人)身份证正面图片，大小500K以内，文字须清晰可见。</span> </td>
        </tr>
        <tr>
          <td class="title_item" nowrap="nowrap"><span style="color: #F00;">*</span>身份证背面：</td>
          <td nowrap="nowrap" colspan="3"><html-el:file property="img_id_card_fm" maxlength="15" styleClass="webinput" styleId="img_id_card_fm" style="width:25%" />
            <c:if test="${not empty af.map.img_id_card_fm}"> &nbsp;&nbsp;&nbsp;<a href="${ctx}/${af.map.img_id_card_fm}" target="_blank">查看</a>
              <html-el:hidden property="img_id_card_fm_s" value="${af.map.img_id_card_fm}"/>
            </c:if>
            <span style="color: #F00;">&nbsp;店铺所属人(法人)身份证背面图片，大小500K以内，文字须清晰可见。</span> </td>
        </tr>
      </tbody>	

      <tr id="s_tr" style="display: none;">
        <td class="title_item">合伙人名称：</td>
        <td colspan="3"><span id="s_name"></span>&nbsp;，电话：<span id="s_mobile" class="label label-warning"></span></td>
      </tr>
      <tr>
        <td colspan="4" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$(document).ready(function(){
	
	$("#city_div").citySelect({
        data:getAreaDic(),
        province:"${af.map.province}",
        city:"${af.map.city}",
        country:"${af.map.country}",
        province_required:true,
        city_required:true,
        country_required:false,
    });
	
	var editor = KindEditor.create("textarea[id='entp_content']");
		
	var regMobile = /^((\(\d{2,3}\))|(\d{3}\-))?((1[3-9]\d{9}))$/;
	var regPhone = /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/; 

	$("#entp_name").attr("datatype","Require").attr("msg","店铺名称必须填写");
	$("#user_name").attr("datatype","Require").attr("msg","注册会员必须选择");

	$("#entp_logo").attr("dataType", "Filter" ).attr("require", "false").attr("msg", "门头照片必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
	<c:if test="${empty (af.map.entp_logo)}">
		$("#entp_logo").attr("require", "true");
	</c:if>
	
	$("#entp_desc").attr("datatype","Limit").attr("min","1").attr("max","1500").attr("msg","商家简介在1500个汉字之内");
	$("#entp_addr").attr("datatype","Require").attr("msg","详细地址必须填写");
	$("#entp_linkman").attr("datatype","Require").attr("msg","商家联系人必须填写");
	$("#entp_tel").attr("datatype","Require").attr("msg","联系电话必须填写,且格式要正确！");
	$("#yy_sj_between").attr("datatype","Require").attr("msg","营业时间必须填写");
	
	$("#order_value").focus(setOnlyNum);
	$("#entp_addr").attr("datatype","Require").attr("msg","请正确填店铺具体地址");
	
	
	$("#is_has_yinye_no").change(function(){
		var thisValue = $(this).val();
		if(thisValue == 1){
			$("#hasYinYeNo").show();
			$("#notHasYinYeNo").hide();
			$("#entp_licence").attr("datatype","Require").attr("msg","营业执照编码必须填写");
			$("#entp_licence_img").attr("dataType", "Filter" ).attr("msg", "营业执照扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_zm").removeAttr("datatype");
			$("#img_id_card_fm").removeAttr("datatype");
			$("#id_card_no").removeAttr("datatype");
		}else{
			$("#notHasYinYeNo").show();
			$("#hasYinYeNo").hide();
			$("#id_card_no").attr("datatype","Require").attr("msg","身份证号必须填写");
			$("#img_id_card_zm").attr("dataType", "Filter" ).attr("msg", "身份证正面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#img_id_card_fm").attr("dataType", "Filter" ).attr("msg", "身份证反面扫描件必须上传且格式必须是(bmp,gif,jpeg,jpg,png,png)").attr("accept", "bmp, gif, jpeg, jpg, png");
			$("#entp_licence").removeAttr("datatype");
			$("#entp_licence_img").removeAttr("datatype");
		}
	});
	$("#is_has_yinye_no").change();
	
	<c:if test="${not empty (af.map.entp_licence_img)}">
	$("#entp_licence_img").removeAttr("datatype");
    </c:if>	
	<c:if test="${not empty (af.map.img_id_card_zm)}">
	$("#img_id_card_zm").removeAttr("datatype");
	</c:if>	
	<c:if test="${not empty (af.map.img_id_card_fm)}">
	$("#img_id_card_fm").removeAttr("datatype");
	</c:if>	
	
	// 提交
	$("#btn_submit").click(function(){
		$("#entp_content").val(editor.html());
		if(Validator.Validate(f, 3)){
			 $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
			 $("#btn_reset").attr("disabled", "true");
			 $("#btn_back").attr("disabled", "true");
			 f.submit();
		}
	});

});     

//地市选择
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
   		if(thisVal.length != 0) {
   			$("#p_index").val(thisVal);
   		} else {
   			$("#p_index").val($("#city").val());
   		}
	$.post("${ctx}/CsAjax.do?method=jugdeHasSeviceCenterByPindex",{p_index: thisVal},function(data){
		if (!data.ret) {
	   	}else{
	   	    $("#btn_submit").removeAttr("disabled");
	   	    $("#s_tr").show();
	   	    $("#s_name").text(data.s_name);
	   	    $("#s_mobile").text(data.s_mobile);
	   	}
	});
	}
});

function myConfirm(tip, submit){ 
	$.jBox.confirm(tip, "${app_name}", submit, { buttons: { '确定': true, '取消': false} });
}


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
			$.post("EntpInfoAudit.do?method=deleteImageOrVideo" , {
				id : id ,type: type, file_path : file_path
			}, function(d){
				if(d == "success"){
					$("#" + type + "_div").remove();
					$("#entp_logo_s").val("");
				}
			});
	 } 
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

function validate(value){
	if ("" != value) {
			$.ajax({
				type: "POST" , 
				url: "${ctx}/CsAjax.do" , 
				data:"method=validate&type=entp_name&value="+value+ "&id=${af.map.id}&t=" + new Date(),
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

function openChildForUser(){
	
	var url = "${ctx}/BaseCsAjax.do?method=chooseUserInfo&dir=admin&user_type=2"+ "&t=" + new Date().getTime();
	$.dialog({
		title:  "选择注册会员",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:9999,
		content:"url:"+url
	});
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
