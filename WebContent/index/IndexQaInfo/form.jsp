<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线提问 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link href="${ctx}/styles/indexv3/css/top.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/nmnetwork.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/netlist.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/bottom.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv2/css/left_sort.css" rel="stylesheet" type="text/css" />
</head>
<body>
<jsp:include page="../../_header.jsp" flush="true" />
<!--first start -->
<div class="newnavdown">
  <c:url var="url" value="/index.do"></c:url>
  <div class="neone"><a href="${url}">首页</a> &gt; 咨询投诉  &gt; <strong>在线提问</strong> </div>
</div>

<!-- list start -->
<div class="listcont"> 
  <!-- left -->
  <div class="listleft">
    <div class="listab1">
      <div class="listit2">热门快报</div>
      <ul class="listul2">
        <jsp:include page="../../public/_index_tjzx.jsp" flush="true" />
      </ul>
    </div>
    <div class="listbot2"></div>
    <div class="listab1 martop8">
      <div class="listit2">品牌推荐</div>
      <ul class="rp1">
        <jsp:include page="../../public/_index_qytgfw.jsp" flush="true" />
      </ul>
    </div>
    <div class="listbot2"></div>
  </div>
  <!-- right -->
  <div class="listright">
    <%-- <div class="picone"><img src="${ctx}/styles/indexv2/images/qainfo.jpg" height="85" /></div> --%>
    <div style="padding: 5px;"></div>
    <div class="listnews">
      <div class="black">在线留言</div>
      <div class="stop7"></div>
      <c:url var="url" value="IndexQaInfo.do" />
      <html-el:form action="${url}" styleClass="qaForm">
        <html-el:hidden property="method" value="save"/>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="part16">
          <tr>
            <td height="27" align="right"><span style="color:red;">*</span>问题：</td>
            <td><html-el:text property="q_title" maxlength="120" styleClass="part18" styleId="q_title" style="width:400px;"/></td>
          </tr>
          <tr>
            <td align="right"><span style="color:red;">*</span>内容：</td>
            <td><html-el:textarea property="q_content"  style="width:367px; height:80px;" styleId="q_content" styleClass="part17c"/></td>
          </tr>
          <tr>
            <td height="27" align="right"><span style="color:red;">*</span>姓名：</td>
            <td><html-el:text property="q_name" maxlength="30" style="width:140px" styleClass="part18" styleId="q_name" /></td>
          </tr>
          <tr>
            <td height="27" align="right">手机：</td>
            <td><html-el:text property="q_tel" maxlength="30" style="width:140px" styleClass="part18" styleId="q_tel" /></td>
          </tr>
          <tr>
            <td height="27" align="right"><span style="color:red;">*</span>电子邮箱：</td>
            <td><html-el:text property="q_email" maxlength="30" style="width:140px" styleClass="part18" styleId="q_email" /></td>
          </tr>
          <tr>
            <td height="27" align="right">联系地址：</td>
            <td><html-el:text property="q_addr" maxlength="30" styleClass="part18" styleId="q_addr" style="width:300px;"/></td>
          </tr>
          <tr>
            <td valign="top">&nbsp;</td>
            <td height="70"><label>
                <input class="but_advisory" type="button" name="button" value="提交" id="btn_submit" />
                <input name="button3" type="reset" class="but_advisory2"  value="重置" />
              </label></td>
          </tr>
        </table>
      </html-el:form>
    </div>
  </div>
</div>
<div class="clear"></div>
<!-- list end -->
<jsp:include page="../../_footer.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.min.js"></script> 

<script type="text/javascript" src="${ctx}/scripts/news.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript">
//<![CDATA[
$(document).ready( function() {
	//$("input[type='radio'][name='vote_id']").eq(0).attr("dataType", "Group").attr("msg", "请选择至少一项！");
	
	//$("input[type='radio'][name='q_type_id']").eq(0).attr("dataType", "Group").attr("msg", "请选择问题类型！");
	$("#q_title").attr("dataType", "Require").attr("msg", "问题必须填写");
	$("#q_name").attr("dataType", "Require").attr("msg", "姓名必须填写");
	$("#q_email").attr("dataType", "Email").attr("msg", "电子邮箱格式错误");
	//$("#q_tel").attr("dataType", "Mobile").attr("msg", "手机格式错误");
	$("#q_content").attr("dataType","Limit").attr("min","1").attr("max","500").attr("msg","内容必须填写,且在500个汉字之内");

	var f1 = $(".qaForm").get(0);
	
	$("#btn_submit").click(function(){
		var val_link_tel = $("#q_tel").val();
		if (val_link_tel.length <= 0){
			$("#q_tel").removeAttr("dataType");
		}else{
			$("#q_tel").attr("dataType", "Mobile").attr("msg", "手机格式错误");
		}
		if (Validator.Validate(f1, 1)){
	        $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	        $("#btn_reset").attr("disabled", "true");
			f1.submit();
		}
	});
});
//]]>
//]]>
</script>
</body>
</html>
