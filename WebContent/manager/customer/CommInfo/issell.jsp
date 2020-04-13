<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base target="_self" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
<title>商品上架</title>
</head>
<body>
<div align="center">
  <html-el:form action="/customer/CommInfo.do" >
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveSell" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="par_id" />
    <html-el:hidden property="mod_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
      <tr>
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>是否上架：</td>
        <td colspan="2" width="88%"><html-el:select property="is_sell" styleId="is_sell">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select></td>
      </tr>
      <tr id="up_date_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>上架时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.up_date}" pattern="yyyy-MM-dd" var="_up_date" />
          <html-el:text property="up_date" styleId="up_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',onpicked:DayFunc})" style="cursor:pointer;" value="${_up_date}" /></td>
      </tr>
      <tr id="down_date_tr">
        <td nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>下架时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.down_date}" pattern="yyyy-MM-dd" var="_down_date" />
          <html-el:text property="down_date" styleId="down_date" size="10" maxlength="20" styleClass="webinput" readonly="true" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" style="cursor:pointer;" value="${_down_date}" /></td>
      </tr>
      <tr>
        <td colspan="3" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
        </td>
      </tr>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jBox/jbox.min.manager.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("#is_sell").attr("dataType", "Require").attr("msg", "请选择是否上架！");
	
	var f = document.forms[0];

	// 通过判断是否上架控制上架时间和下架时间的显示
	if("1" != $("#is_sell").val()){
		$("#up_date_tr").hide();
        $("#down_date_tr").hide();
	}
	$("#is_sell").change(function(){
       if("1" == $(this).val()){
           $("#up_date_tr").show();
           $("#down_date_tr").show();
           }else{
             $("#up_date").val("");
              $("#down_date").val("");  
              $("#up_date_tr").hide();
              $("#down_date_tr").hide();
            }
	});
	
	$("#btn_submit").click(function(){
		var api = frameElement.api, W = api.opener;
		if(Validator.Validate(f, 1)){
			if("1" == $("#is_sell").val()){
			 if($("#up_date").val()>=$("#down_date").val()){
					alert("下架时间必须大于上架时间");
					return false;
				}
			}
			$.jBox.tip("加载中...", "loading");
			 window.setTimeout(function () {
				 $.ajax({
						type: "POST",
						url: "${ctx}/manager/customer/CommInfo.do?method=saveSell",
						data: $(f).serialize(),
						dataType: "json",
						error: function(){alert("数据加载请求失败！");},
						success: function(data){
							if(data.ret == 1){
								 W.refreshPar();
								 api.close();
							}
						}
				   });
				 
			 }, 1500);
		}
		return false;
	});
});

function DayFunc(){
	var c = $dp.cal;
	var todate = new Date(c.getP('y'),c.getP('M')-1,c.getP('d'));
	todate.setDate(todate.getDate()+15);
	$("#down_date").val(todate.format("yyyy-MM-dd")); 
}
//]]></script>
</body>
</html>
