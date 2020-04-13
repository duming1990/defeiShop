<%@ include file="/commons/pages/taglibs.jsp" %>
<logic-el:messagesPresent>
  <link href="${ctx}/commons/styles/message/message.css" rel="stylesheet" type="text/css" />
  <div class="flash warning" align="left"><html-el:messages id="error"> ${error}</html-el:messages></div>
</logic-el:messagesPresent>
<logic-el:messagesPresent message="true">
  <link href="${ctx}/commons/styles/message/message.css" rel="stylesheet" type="text/css" />
  <div class="flash notice" align="left"><html-el:messages id="message" message="true">${message}</html-el:messages></div>
</logic-el:messagesPresent>
