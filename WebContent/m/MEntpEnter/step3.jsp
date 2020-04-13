<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>触屏版-${app_name}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<meta http-equiv="Pragma" content="no-cache">
<jsp:include page="../_public_in_head.jsp" flush="true" />
<link href="${ctx}/m/styles/css/my/my-v1.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/index/css/btns.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/m/js/date/app1/css/date.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../_header.jsp" flush="true" />
<div class="content">
  <!--article-->
  <form action="/m/MEntpEnter" enctype="multipart/form-data" method="post" class="ajaxForm0">
    <input type="hidden" name="id" id="id" />
    <input type="hidden" name="method" value="step3" />
    <input type="hidden" name="mod_id" id="mod_id" value="${af.map.mod_id}"/>
    <input type="hidden" name="queryString" id="queryString" />
    <input type="hidden" name="entp_id" id=""entp_id"" value="${af.map.id}" />
    <div class="set-site" id="city_div">
      <ul>
      	<li>
      		<span class="grey-name">店铺名称：</span>
          <input style="width: 70%" name="entp_name" id="entp_name" type="text" autocomplete="off" maxlength="38" value="${af.map.entp_name}" class="buy_input">
        </li>
        <li class="select"><span class="grey-name">店铺类型：</span>
           <select name="is_nx_entp" id="is_nx_entp">
                <c:forEach items="${shopTypes}" var="cur">
                   	<option value="${cur.index}">${cur.name}</option>
                </c:forEach>
           </select>
        </li>
        
        <li class="select"><span class="grey-name" style="width: 30%">所属行业：</span>
          <select name="hy_cls_id" id="hy_cls_id" style="width: 70%">
            <option value="">请选择...</option>
            <c:forEach var="cur" items="${baseHyClassList}" varStatus="vs">
              <option value="${cur.cls_id}">${cur.cls_name}</option>
            </c:forEach>
          </select>
        </li>
        <li>
      		<span class="grey-name">经营范围：</span>
      		<input name="main_pd_class_ids" type="hidden" id="main_pd_class_ids" value="${af.map.main_pd_class_ids}" />
          	<input style="width: 50%" onclick="getMain_pd_class_names();" name="main_pd_class_names" id="main_pd_class_names" type="text" readonly="true" autocomplete="off" maxlength="38" value="${af.map.main_pd_class_names}" class="buy_input">
        	<div class="files-warp">
		          <div class="btn-files"> <span>选择</span>
		            <input onclick="getMain_pd_class_names();" type="button" />
		          </div>
		        </div>
        </li>
        
<!--         <li class="select"><span class="grey-name" style="width: 35%">线下店铺所属行业</span> -->
<!--           <select name="xianxia_cls_id" id="xianxia_cls_id" style="width: 65%"> -->
<!--             <option value="">请选择...</option> -->
<%--             <c:forEach var="cur" items="${baseXianxiaClassList}" varStatus="vs"> --%>
<%--               <option value="${cur.cls_id}">${cur.cls_name}</option> --%>
<%--             </c:forEach> --%>
<!--           </select> -->
<!--         </li> -->
<!--         <li> -->
<!--       		<span class="grey-name" style="width: 35%">线下店铺经营范围：</span> -->
<%--       		<input name="xianxia_pd_class_ids" type="hidden" id="xianxia_pd_class_ids" value="${af.map.xianxia_pd_class_ids}" /> --%>
<%--           	<input style="width: 50%" onclick="getXianxia_pd_class_names();" name="xianxia_pd_class_names" id="xianxia_pd_class_names" type="text" readonly="true" autocomplete="off" maxlength="38" value="${af.map.xianxia_pd_class_names}" class="buy_input"> --%>
<!--         	<div class="files-warp"> -->
<!-- 		          <div class="btn-files"> <span>选择</span> -->
<!-- 		            <input onclick="getXianxia_pd_class_names();" type="button" /> -->
<!-- 		          </div> -->
<!-- 		        </div> -->
<!--         </li> -->
        
        <div class="item" style="border-bottom: 1px solid #C8C8C8;">
        <li style="text-align: center;">
        	<span style="width: 80%;padding-left: 60px;padding-top: 20px;">
		        <div style="float:left;">
		        <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
		        <c:if test="${not empty af.map.entp_logo}">
                   <c:set var="img" value=" ${ctx}/${af.map.entp_logo}@s400x400" />
                </c:if>
		        <img src="${img}" width="100" height="100" id="entp_logo_img" />
		        <input type="hidden" name="entp_logo" id="entp_logo" value="${af.map.entp_logo}" />
		        <div class="files-warp" id="entp_logo_warp">
		          <div class="btn-files"> <span>添加附件</span>
		            <input id="entp_logo_file" type="file" name="entp_logo_file"  accept="image/*" />
		          </div>
		          <div class="progress progress_m" style=""> <span class="bar bar_m"></span><span class="percent">0%</span > </div>
		        </div>
		        </div>
		  	</span>
        </li>
        </div>
         <li>
      		<span class="grey-name">店铺简介：</span>
          	<input style="width: 70%" name="entp_desc" id="entp_desc" type="text" autocomplete="off" maxlength="38" value="${af.map.entp_desc}" class="buy_input">
        </li>
        <div class="item" style="display:none;">
           <div class="label"> <em>*</em> <span>折扣规则：</span> </div>
           <div class="value">
             <select name="fanxian_rule" id="fanxian_rule" style="width:150px;">
               <c:forEach var="cur" items="${baseData700List}" varStatus="vs">
                 <option value="${cur.id}">${cur.type_name}</option>
               </c:forEach>
             </select>
           </div>
         </div>
      </ul>
    </div>
    <div class="box submit-btn"> <a class="com-btn" id="btn_submit0">保存</a> </div>
  </form>
</div>
<jsp:include page="../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.m.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/iscroll/iscroll-5.1.2.min.js"></script>
<script type="text/javascript" src="${ctx}/m/js/date/app1/js/date.js?v20160406"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/area.js"></script>
<script type="text/javascript" src="${ctx}/scripts/citySelect/citySelect.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<c:url var="urlhome" value="/m/MMyHome.do" />
<c:url var="toStep3" value="/m/MMyHome.do" />
<c:url var="toStep3" value="/m/MEntpEnter.do?method=toStep3" />
<c:url var="toStep4" value="/m/MEntpEnter.do?method=toStep4" />
<script type="text/javascript">//<![CDATA[

$(document).ready(function() {
	
	$("#entp_desc").attr("datatype","Limit").attr("min","1").attr("max","1500").attr("msg","商家简介在1500个汉字之内");
	$("#entp_name").attr("datatype","Require").attr("msg","店铺名称必须填写");
	$("#main_pd_class_names").attr("datatype","Require").attr("msg","主营产品必须填写");
	$("#fanxian_rule").attr("datatype","Require").attr("msg","请选择返现政策");
	
	var btn_name = "上传门头照片";
	if ("" != "${af.map.entp_logo}") {
		btn_name = "重新上传门头照片";
	}
	upload("entp_logo", "image", btn_name, "${ctx}");
	
	$("#hy_cls_id").val("${af.map.hy_cls_id}");
// 	$("#xianxia_cls_id").val("${af.map.xianxia_cls_id}");
	
	
    window.setTimeout(function () {$("#entp_name").val("${af.map.entp_name}");}, 100);
    
    
    $("#hy_cls_id").change(function(){
    	$("#main_pd_class_ids").val("");
    	$("#main_pd_class_names").val("");
    });
//     $("#xianxia_cls_id").change(function(){
//     	$("#xianxia_pd_class_ids").val("");
//     	$("#xianxia_pd_class_names").val("");
//     });
	
	var f0 = $(".ajaxForm0").get(0);
	$("#btn_submit0").click(function(){
		if (Validator.Validate(f0, 1)) {
			Common.loading();
				$.ajax({
					type: "POST",
					url: "?method=step4",
					data: $(f0).serialize(),
					dataType: "json",
					error: function(request, settings) {},
					success: function(data) {
						Common.hide();
						if (data.code == 1) {
							mui.toast(data.msg);
							window.setTimeout(function () {
								goUrl('${toStep4}',0);
							},1000);
						} else {
							mui.toast(data.msg);
						}
					}
				});	
			return true;
		}
		return false;
	});

	$("#id_card_").change(function(){
		var thisVal = $(this).val();
		if(null != thisVal && '' != thisVal){
			$("#id_card").val(thisVal);
		}
	});
	
});

function getMain_pd_class_names() {
	var hy_cls_id = $("#hy_cls_id").val()
	if(null == hy_cls_id || hy_cls_id == ""){
		mui.toast("请先选择所属行业");
		return false;
	}
	var main_pd_class_ids  =$("#main_pd_class_ids").val();
	var main_pd_class_names  =$("#main_pd_class_names").val();
	var url = "${ctx}/Register.do?method=listPdClass&main_pd_class_ids=" + main_pd_class_ids +"&main_pd_class_names=" + main_pd_class_names +"&hy_cls_id=" + hy_cls_id;
	$.dialog({
		title:  "选择主营产品",
		width:  770,
		height: 500,
		padding: 0,
		max: false,
        min: false,
        fixed: true,
        lock: true,
		content:"url:"+ encodeURI(url)
	});
}
function getXianxia_pd_class_names() {
	var xianxia_cls_id = $("#xianxia_cls_id").val()
	if(null == xianxia_cls_id || xianxia_cls_id == ""){
		mui.toast("请先选择线下店铺所属行业");
		return false;
	}
	var xianxia_pd_class_ids = $("#xianxia_pd_class_ids").val();
	var xianxia_pd_class_names = $("#xianxia_pd_class_names").val();
	var url = "${ctx}/Register.do?method=listPdClass&main_pd_class_ids=" + xianxia_pd_class_ids +"&main_pd_class_names=" + xianxia_pd_class_names +"&hy_cls_id=" + xianxia_cls_id+"&is_xianxia=true";
	$.dialog({
		title:  "选择主营产品",
		width:  770,
		height: 500,
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
