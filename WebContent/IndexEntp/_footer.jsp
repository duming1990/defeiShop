<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>

<c:set var="indexClass" value="" />
<c:if test="${empty isIndex}">
  <c:set var="indexClass" value="nopre" />
</c:if>
<div class="footer ${indexClass}">
  <div class="footer_o clearfix">
    <div class="footer_o_o fl">
      <img src="${ctx}/styles/indexEntp/images/logo.png" alt="logo" width="200" height="57">
    </div>
    <c:forEach items="${baseLink10090List}" var="cur2">
      <div class="footer_o_t fl">
        <p>${cur2.title }</p>
        <span>${cur2.pre_varchar}</span>
      </div>
    </c:forEach>
    <div class="footer_o_f fr">
      <img src="${ctx}/styles/indexEntp/images/footer_qrcode_pic.gif" alt="ewm" style="width:217px;height:127px">
     <a href="https://zzlz.gsxt.gov.cn/businessCheck/verifKey.do?showType=p&serial=91340100MA2RB74D6U-SAIC_CHECK_10002091340100MA2RB74D6U1563183321035&signData=MEQCILo2Eu3rnvq7FScaJJm5/x7htNHesm0opYJyiRwVS7G6AiB25W6TKwM0HNTRinaqQ+6wSCi6pTDYT5sUnExQhI066g==" target="_blank"><img src="${ctx}/images/static.png" style="width:50px;margin:27px;    float: right;"></img></a>
	 
	</div>

 
  </div>
		<div class="footer_t">${baseLink10091.pre_varchar}</div>
</div>
