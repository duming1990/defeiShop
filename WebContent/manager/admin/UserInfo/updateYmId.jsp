<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/commons/styles/nav.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
</head>
<body>
<div class="divContent">
   <div style="padding:5px;" class="tip-success">
    <h3>【${user_name_par}】的上级：</h3>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="table-advanced">
      <tr>
        <th>用户名</th>
        <th>姓名</th>
        <th width="12%">身份</th>
        <th width="12%">添加时间</th>
      </tr>
        <tr>
          <td align="center">${fn:escapeXml(uipar.user_name)}</td>
          <td align="center">${fn:escapeXml(uipar.real_name)}</td>
          <c:set var="shenfen" value="会员" />
          <c:if test="${uipar.is_entp eq 1}">
          <c:set var="shenfen" value="商家" />
          </c:if>
          <c:if test="${uipar.is_fuwu eq 1}">
          <c:set var="shenfen" value="合伙人" />
          </c:if>
          <td align="center">${shenfen}</td>
          <td align="center"><fmt:formatDate value="${uipar.add_date}" pattern="yyyy-MM-dd" /></td>
          </td>
        </tr>
    </table>
    <div class="tip-danger">
     <h3>上级变更</h3>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="5%" nowrap="nowrap">
					<div>
					<html-el:form action="/admin/UserInfo" styleClass="searchForm">
	 				 &nbsp;变更上级：
	 				 <html-el:hidden property="user_id" styleId="user_id" />
		             <html-el:text property="user_name" maxlength="20" styleClass="webinput" styleId="user_name" style="width:200px" readonly="true"/>
		             &nbsp;
		             <a class="butbase" onclick="getUserInfo()" ><span class="icon-search">选择</span></a>
	  				 &nbsp;
	  				 <html-el:button property="" value="确定变更" styleClass="bgButton" styleId="btn_submit" />
	  				 </html-el:form>
	  				</div>
 				 </td>
 			 </tr>
 	 </table>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[

function getUserInfo(){
	var url = "${ctx}/BaseCsAjax.do?method=getUserInfo";
	$.dialog({
		title:  "选择用户",
		width:  770,
		height: 550,
        lock:true ,
        zIndex:999,
		content:"url:"+url
	});
}    
var f = document.forms[0];

$(document).ready(function(){
	
	$("#user_name").attr("datatype","Require").attr("msg","请选择变更上级用户！");
	//提交
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 1)){
			var userParId  = $("#user_id").val();
			var submit = function (v, h, f) {
			    if (v == true) {
			    	$.jBox.tip("数据提交中...", 'loading');
			    	window.setTimeout(function () { 
			    	 $.post("?method=saveUpdateYmId",{user_id:"${userInfo.id}",userParId:userParId},function(data){
				    	 jBox.tip(data.msg, 'info');
				    	 window.setTimeout(function () {
				    	  if(data.ret == 1){
				    		window.location.reload();
				    	  }
				    	 }, 1000);
					 });
			    	}, 1000);
			    } 
			    return true;
			};
			// 自定义按钮
			$.jBox.confirm("该操作请慎用，你确定要变更上级吗?", "系统提示", submit, { buttons: { '确定': true, '取消': false} });
		}
	});

});
                                          
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
