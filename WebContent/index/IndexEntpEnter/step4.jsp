<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我要开店 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/login_reg.css"  />
</head>
<body class="pg-unitive-signup theme--www">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="merSteps">
  <div class="w1200">
    <c:url var="url" value="/IndexEntpEnter.do?method=step5" />
    <form id="stepForm" action="${url}" method="post" name="stepForm">
      <div class="panel">
        <div class="panel-nav">
          <div class="progress-item passed">
            <div class="number">1</div>
            <div class="progress-desc">入驻须知</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item passed">
            <div class="number">2</div>
            <div class="progress-desc">公司信息认证</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item ongoing">
            <div class="number">3</div>
            <div class="progress-desc">店铺信息认证</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item tobe">
            <div class="number">4</div>
            <div class="progress-desc">等待审核</div>
          </div>
        </div>
        <div class="panel-content">
          <div class="bg-top"></div>
          <div class="bg-warp">
            <div class="title"> <span>店铺信息提交</span> </div>
            <div class="panel-body">
              <div class="list">
                <div class="item">
                  <div class="label"> <em>*</em> <span>店铺名称：</span> </div>
                  <div class="value">
                    <input class="text" type="text" size="20" value="${af.map.entp_name}" maxlength="20" name="entp_name" id="entp_name">
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>店铺类型：</span> </div>
                  <div class="value">
                    <select name="is_nx_entp" id="is_nx_entp">
                      <c:forEach items="${shopTypes}" var="cur">
                        <option value="${cur.index}">${cur.name}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>所属行业：</span> </div>
                  <div class="value">
                    <select name="hy_cls_id" id="hy_cls_id" style="width: 400px">
                      <option value="">请选择...</option>
                      <c:forEach var="cur" items="${baseHyClassList}" varStatus="vs">
                        <option value="${cur.cls_id}">${cur.cls_name}</option>
                      </c:forEach>
                    </select>
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>经营范围：</span> </div>
                  <div class="value">
                    <input name="main_pd_class_ids" type="hidden" id="main_pd_class_ids" value="${af.map.main_pd_class_ids}" />
                    <input type="textarea" name="main_pd_class_names" id="main_pd_class_names" readonly="readonly" value="${af.map.main_pd_class_names}" rows="2" class="text" onclick="getmain_pd_class_names();" style="width:600px;"/>
                    &nbsp;
                    <input type="button" value="选择" onclick="getmain_pd_class_names();" class="btn" style="padding: 2px 10px 2px;font-weight: normal;" />
                  </div>
                </div>
<!--                 <div class="item"> -->
<!--                   <div class="label"> <em>*</em> <span>线下店铺所属行业：</span> </div> -->
<!--                   <div class="value"> -->
<!--                     <select name="xianxia_cls_id" id="xianxia_cls_id" style="width: 400px"> -->
<!--                       <option value="">请选择...</option> -->
<%--                       <c:forEach var="cur" items="${baseClass_XianXia_List}" varStatus="vs"> --%>
<%--                         <option value="${cur.cls_id}">${cur.cls_name}</option> --%>
<%--                       </c:forEach> --%>
<!--                     </select> -->
<!--                   </div> -->
<!--                 </div> -->
<!--                 <div class="item"> -->
<!--                   <div class="label"> <em>*</em> <span>线下店铺经营范围：</span> </div> -->
<!--                   <div class="value"> -->
<%--                     <input name="xianxia_pd_class_ids" type="hidden" id="xianxia_pd_class_ids" value="${af.map.xianxia_pd_class_ids}" /> --%>
<%--                     <input type="textarea" name="xianxia_pd_class_names" id="xianxia_pd_class_names" readonly="readonly" value="${af.map.xianxia_pd_class_names}" rows="2" class="text" onclick="getXianxia_pd_class_names();" style="width:600px;"/> --%>
<!--                     &nbsp; -->
<!--                     <input type="button" value="选择" onclick="getXianxia_pd_class_names();" class="btn" style="padding: 2px 10px 2px;font-weight: normal;" /> -->
<!--                   </div> -->
<!--                 </div> -->
                <div class="item">
                  <div class="label"> <em>*</em> <span>门头照片：</span> </div>
                  <div class="value">
                    <c:set var="img" value="${ctx}/commons/swfupload/style/images/no_image.jpg" />
                    <c:if test="${not empty af.map.entp_logo}">
                      <c:set var="img" value=" ${ctx}/${af.map.entp_logo}@s400x400" />
                    </c:if>
                    <img src="${img}" height="100" id="entp_logo_img" />
                    <input type="hidden" name="entp_logo" id="entp_logo" value="${af.map.entp_logo}" />
                    <div class="files-warp" id="entp_logo_warp">
                      <div class="btn-files"> <span>添加附件</span>
                        <input id="entp_logo_file" type="file" name="entp_logo_file"/>
                      </div>
                      <div class="progress"> <span class="bar"></span><span class="percent">0%</span > </div>
                    </div>
                    <span>说明：门头照片大小不能超过2M!图片格式支持：gif,jpg,jpeg,png,bmp,ico。</span> </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>店铺简介：</span> </div>
                  <div class="value">
                    <input type="textarea" name="entp_desc" id="entp_desc" value="${af.map.entp_desc}" rows="7" class="text" style="width:600px;" />
                  </div>
                </div>
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
              </div>
            </div>
            <div class="btn-group mt0">
              <input name="entp_id" type="hidden" value="${af.map.id}" />
              <c:url var="url" value="/IndexEntpEnter.do?method=step3" />
              <input class="btn btn-w" id="js-pre-step" type="button" value="上一步" onclick="location.href='${url}'" >
              <input class="btn" id="nextStepBtn" type="submit" value="下一步，提交审核">
            </div>
          </div>
          <div class="bg-bottom"></div>
        </div>
      </div>
    </form>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript" src="${ctx}/commons/kindeditor/kindeditor.min.js"></script>
<script type="text/javascript">//<![CDATA[
var f_step = $("#stepForm").get(0);                               
$(document).ready(function(){
	
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
	$("#xianxia_cls_id").val("${af.map.xianxia_cls_id}");
	
	
    window.setTimeout(function () {$("#entp_name").val("${af.map.entp_name}");}, 100);
    
    
    $("#hy_cls_id").change(function(){
    	$("#main_pd_class_ids").val("");
    	$("#main_pd_class_names").val("");
    });
    $("#xianxia_cls_id").change(function(){
    	$("#xianxia_pd_class_ids").val("");
    	$("#xianxia_pd_class_names").val("");
    });
    
});	

f_step.onsubmit = function () {

	if(Validator.Validate(this,1)){
		$("#addZdy_tbody").remove();
        $("#nextStepBtn").attr("value", "正在提交...").attr("disabled", "true");
		return true;
	}
	return false;
}

function getmain_pd_class_names() {
	var hy_cls_id = $("#hy_cls_id").val()
	if(null == hy_cls_id || hy_cls_id == ""){
		$.jBox.tip("请先选择所属行业", "info");
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
		$.jBox.tip("请先选择线下店铺所属行业", "info");
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
