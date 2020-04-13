<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="${fn:escapeXml(fnx:abbreviate(af.map.content, 2 * 50, ''))}" />
<meta name="keywords" content="${fn:escapeXml(af.map.title)}" />
<title>意见反馈 - ${app_name}</title>
<jsp:include page="../../_public_header.jsp" flush="true" />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/newscontent.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/styles/index/css/buy.css"  />
<style type="">
#content img{max-width: 100% !important;height: auto !important;}
#bd{
    width: 1200px;
    top:0px;
}
</style>
</head>
<body class="pg-deal pg-deal-default pg-deal-detail" id="deal-default" style="position: static;">
<jsp:include page="../../_header.jsp" flush="true" />
<div class="bdw">
  <div id="bd" class="cf">
    <div class="newnavdown" style="padding-bottom:10px;">
      <c:url var="url" value="/index.do"></c:url>
      <div class="neone"><a href="${url}">首页</a> &gt; <strong>意见反馈</strong> </div>
    </div>
    <!----内容开始---->
    <div id="content">
      <div class="mainbox" id="msg_tip" style="display: none;">
        <h1 class="headline"><span class="headline__content">意见反馈</span></h1>
        <div class="mainbox">
          <div class="finish-box">
            <p style=" font-size:18px; line-height:40px; font-weight:bold;display:block;">感谢您反馈的意见，我们会继续努力。</p>
          </div>
          <div class="form-submit" style="text-align:center;">
            <input id="J-order-pay-button" type="submit" class="btn btn-large btn-pay" name="commit" value="关 闭" onclick="location.href='${url}'"/>
          </div>
        </div>
        </div>
        <div class="pg-commitment" id="msg_submit">
          <h1 style="text-align:center;margin-bottom:15px;">${fn:escapeXml(af.map.title)}</h1>
          <div class="conditions">
            <html-el:form action="/IndexMsg.do" enctype="multipart/form-data" styleClass="ajax_form">
              <html-el:hidden property="method" value="save" />
              <div class="form-field form-field--mobile">
                <label><span style="color:red;">* </span>留言内容</label>
                <textarea name="msg_content" id="msg_content" class="f-text" style="width:520px;height:200px;" ></textarea>
              </div>
              <div class="form-field form-field--mobile">
                <label><span style="color:red;">* </span>手机号</label>
                <input type="text" name="msg_title" id="msg_title" class="f-text J-mobile" maxlength="11"/>
              </div>
              <div class="form-field form-field--mobile">
                <label><span style="color:red;">* </span>验证码</label>
                <input type="text" id="captcha" class="f-text" name="verificationCode" placeholder="验证码" autocomplete="off" style="width:65px;" maxlength="4" />
                <img style="display: none;vertical-align: bottom;padding-left: 5px;" src="" name="codeimage" id="codeimage" border="0" /> </div>
              <div class="form-field" align="left">
                <input  type="button" name="commit" id="btn_submit" class="btn" value="保存" />
                <input  type="button" name="commit" id="btn_reset" class="btn" value="重填" onclick="this.form.reset();" />
                <input  type="button" name="commit" id="btn_back" class="btn" value="返回" onclick="history.back();" />
              </div>
            </html-el:form>
          </div>
        </div>
      </div>
    </div>
    <!----内容结束----->
    <!---------侧边开始------------>
    <jsp:include page="./left.jsp" flush="true"/>
    <!---------侧边结束------------>
  </div>
</div>
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#captcha").focus(function(){
        $("#codeimage").attr("src", "${ctx}/images/VerificationCode.jpg?t=" + new Date().getTime()).attr({"width":90,"height":26}).show();
    });
	
	$("#captcha").attr("datatype","Require").attr("msg","请填写验证码！");
	$("#msg_content").attr("datatype","Limit").attr("min","1").attr("max","500").attr("msg","内容必须填写,且在1到500个汉字之内");
	$("#msg_title").attr("datatype","Mobile").attr("Require","true").attr("msg","请输入正确的手机号！");
	var $form  = $(".ajax_form");
	var f = $form.get(0);
	$("#btn_submit").click(function(){

		if(Validator.Validate(f, 3)){
			$.jBox.tip("数据提交中...", 'loading');
			// $("#btn_submit").attr("value", "正在提交").attr("disabled", "true");
			window.setTimeout(function () { 
				$.ajax({
					type: "POST",
					url: "${ctx}/IndexMsg.do",
					data: $form.serialize(),
					dataType: "json",
					error: function(request, settings) {$.jBox.tip("数据请求失败", "error");},
					success: function(data) {
						if(data.ret == "1"){
							$("#msg_content").val("");
							$("#msg_title").val("");
							$("#captcha").val("");
							$.jBox.tip(data.msg, "success");
							$("#msg_submit").hide();
							$("#msg_tip").show();
						} else {
							$.jBox.tip(data.msg, "info");
						}
					}
				});	
	    	}, 1000);
		} 
	});
});

//提交
//]]></script>
</body>
</html>
