<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/RfidBlackList">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">黑名单管理</th>
      </tr>
       <tr>
         <td width="14%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>关联店铺名称：</td>
         <td colspan="2" width="88%">
          <html-el:hidden property="own_entp_id" styleId="own_entp_id" /> 
          <html-el:text property="own_entp_name" styleId="entp_name" maxlength="125" style="width:280px" styleClass="webinput" readonly="true"/>
           &nbsp;
       	 <a class="butbase" onclick="chooseEntpInfo();"><span class="icon-search">选择</span></a>
       </td>
       </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>电子标签ID：</td>
        <td width="85%"><html-el:text property="rfid_id" maxlength="20" styleClass="webinput" styleId="rfid_id" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item">是否删除：</td>
        <td><html-el:select property="is_del" styleId="is_del">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>（删除后，数据将无法修改） </td>
      </tr>
      <tr>
        <td colspan="2" style="text-align: center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#own_entp_id").attr("datatype", "Require").attr("msg", "请选择关联店铺名称"); 
	$("#rfid_id").attr("datatype", "Require").attr("msg", "电子标签ID必须填写"); 

	$("#btn_submit").click(function(){
		if(Validator.Validate(this.form, 3)){
	       $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
	       $("#btn_reset").attr("disabled", "true");
	       $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	});

	});
                                          
function chooseEntpInfo(){  
	                                                       
	var url = "${ctx}/BaseCsAjax.do?method=chooseEntpInfo&dir=admin";        
	$.dialog({
		title:  "选择名称", 
		width:  770,
		height: 550,
        lock:true ,
		content:"url:"+url
	});
}
			
//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
