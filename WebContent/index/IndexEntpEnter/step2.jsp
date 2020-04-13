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
    <c:url var="url" value="/IndexEntpEnter.do?method=step3" />
    <form id="stepForm" action="${url}" method="post" name="stepForm">
      <div class="panel">
        <div class="panel-nav">
          <div class="progress-item passed">
            <div class="number">1</div>
            <div class="progress-desc">入驻须知</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item ongoing">
            <div class="number">2</div>
            <div class="progress-desc">公司信息认证</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item tobe">
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
            <div class="title"> <span>联系方式</span> </div>
            <div class="panel-body">
              <div class="panel-tit"><span>卖家入驻联系人信息</span></div>
              <div class="cue">用于入驻过程中接收${app_name}反馈的入驻通知，请务必正确填写。</div>
              <div class="list">
                <div class="item">
                  <div class="label"> <em>*</em> <span>性别：</span> </div>
                  <div class="value">
                    <div class="value-checkbox">
                      <div class="value-item selected">
                        <input name="sex" class="ui-radio" id="sex-0" type="radio" value="0" checked="checked" />
                        <label for="sex-0" class="ui-radio-label">男</label>
                      </div>
                      <div class="value-item ">
                        <input name="sex" class="ui-radio" id="sex-1" type="radio" value="1" />
                        <label for="sex-1" class="ui-radio-label">女</label>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>联系人姓名：</span> </div>
                  <div class="value">
                    <input class="text" type="text" size="20" value="${af.map.entp_linkman}" maxlength="20" name="entp_linkman" id="entp_linkman" />
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <em>*</em> <span>联系人手机：</span> </div>
                  <div class="value">
                    <input class="text" type="text" size="20" value="${af.map.entp_tel}" maxlength="11" name="entp_tel" id="entp_tel" />
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <span>联系人邮箱：</span> </div>
                  <div class="value">
                    <input class="text" type="text" size="20" value="${af.map.entp_email}" maxlength="50" name="entp_email" id="entp_email" />
                  </div>
                </div>
                <div class="item">
                  <div class="label"> <span>联系人QQ：</span> </div>
                  <div class="value">
                    <input class="text" type="text" size="20" value="${af.map.qq}" maxlength="20" name="qq" id="qq" />&nbsp;<span style="font-size: 12px">请正确填写QQ，将做为您的店铺客服！</span>
                  </div>
                </div>
              </div>
            </div>
            <div class="btn-group mt0">
              <input name="entp_id" type="hidden" value="${af.map.id}" />
              <input class="btn" id="nextStepBtn" type="submit" value="下一步，完善公司信息" />
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
<script type="text/javascript">//<![CDATA[
var f_step = $("#stepForm").get(0);                             
$(document).ready(function(){
	
	$("#entp_linkman").attr("datatype","Require").attr("msg","请正确填写联系人姓名");
	$("#entp_tel").attr("datatype","Mobile").attr("msg","请正确填写联系人手机");
	//$("#qq").attr("datatype","QQ").attr("msg","请正确填写联系人QQ");
	//$("#entp_email").attr("datatype","Email").attr("msg","请正确填写联系人邮箱");
	
	$("#sex-${sex}").attr("checked", true);
});	

f_step.onsubmit = function () {

	if(Validator.Validate(this,3)){
		$("#addZdy_tbody").remove();
        $("#nextStepBtn").attr("value", "正在提交...").attr("disabled", "true");
		return true;
	}
	return false;
}

//]]></script>
</body>
</html>
