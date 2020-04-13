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
    <c:url var="url" value="/IndexEntpEnter.do?method=step2" />
    <form id="stepForm" action="${url}" method="post" name="stepForm">
      <div class="panel">
        <div class="panel-nav">
          <div class="progress-item ongoing">
            <div class="number">1</div>
            <div class="progress-desc">入驻须知</div>
            <div class="arrow-background"></div>
            <div class="arrow-foreground"></div>
          </div>
          <div class="progress-item tobe">
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
            <div class="title">协议确定</div>
            <div class="textareay">
              <div class="agreement"> ${newsInfo.content} </div>
            </div>
            <div class="btn-group">
              <input name="agreement" type="hidden" value="1">
              <input name="nextStepBtn" class="btn" type="submit" value="同意以上协议，下一步">
            </div>
          </div>
          <div class="bg-bottom"></div>
        </div>
      </div>
    </form>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript">//<![CDATA[
var f_s = $("#stepForm").get(0);                             
$(document).ready(function(){
	
});	

//]]></script>
</body>
</html>
