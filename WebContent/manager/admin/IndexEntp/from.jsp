<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
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
  <html-el:form action="/admin/BaseLink" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="save" />
    <html-el:hidden property="link_type" styleId="link_type" />
    <html-el:hidden property="id" styleId="id" />
    <html-el:hidden property="par_id" styleId="par_id" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="4">基本信息</th>
      </tr>
       <tr>
        <td class="title_item"><font color="red">*</font>基本信息：</td>
        <td colspan="3" width="93%">
         <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          <tr id="addTr">
          <td align="center" width="15%">标题</td>
           <td align="center" width="15%">内容</td>
          <td align="center" width="20%">图片  
          <div style="color:rgb(241, 42, 34); padding: 5px;"> 
	          <c:if test="${af.map.link_type eq 10150}">[图片尺寸] 建议：[456px * 370px] </c:if>
         	  <c:if test="${af.map.link_type eq 10160}">[图片尺寸] 建议：[85px * 85px] </c:if>
         	  <c:if test="${af.map.link_type eq 10170}">[图片尺寸] 建议：[1326px * 636px] </c:if>
         	  <c:if test="${af.map.link_type eq 10180}">[图片尺寸] 建议：[540px * 320px] </c:if>
         	  <c:if test="${af.map.link_type eq 10190}">[图片尺寸] 建议：[1165px * 588px] </c:if>
         </div>
         </td>
           <td align="center" width="10%">排序值</td>
           <td align="center" width="5%"><div id="divFile"> <img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="WlAddTr" title="再添加一个" /></div></td>
          </tr>
          </table></td>
      </tr>
      <tr>
        <td colspan="4" align="center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
          &nbsp;
          <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
          &nbsp;
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
     </table>
  </html-el:form>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript"><!--//<![CDATA[

$(document).ready(function(){
	$("#btn_submit").click(function(){
// 		if($("[id^='tBodyHidden']").size()==0){
// 			alert("请添加数据!");
// 		 return;
// 		}
		if(Validator.Validate(this.form, 1)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			this.form.submit();
		}
	});

});
var i=0;
$("#WlAddTr").click(function (){
	var html = '<tr id="tBodyHidden_'+i+'">';
	html +='<td align="center" nowrap="nowrap"><input name="title" maxlength="60" id="title'+i+'" type="text" class="webinput" style="width:120px;"/></td>';
	html +='<td align="center" nowrap="nowrap"><input name="content" maxlength="60" id="content'+i+'" type="text" class="webinput" style="width:120px;"/></td>';
	html +='<td align="center"><img src="${ctx}/images/no_img.gif" width="100" height="100" id="file_hidden'+i+'_img"/>';
	html +='<input name="image_path" type="hidden" id="file_hidden'+i+'" class="file_hidden_'+i+'" />';
	html +='<div class="files-warp" id="user_logo_warp"><div class="btn-files"> <span>上传图片</span>';
	html +='<input name="file_hidden_file" type="file" id="file_hidden'+i+'_file" class="file_hidden_'+i+'" style="width:220px;height:150px;"/></div>';
	html +='<div class="progress"> <span class="bar"></span><span class="percent">0%</span ></div></div></td>';
	html +='<td align="center"><input name="order_values" maxlength="60" id="order_values'+i+'" type="text" class="webinput" value="0" style="width: 40px;"/></td>';
	html +='<td align="center"><img src="../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="DelTr2" title="删除" onclick="deleteRow('+i+')"/></td>';
	html +='</tr>';
    $("#addTr").after(html);
    	$("#title"+i).attr("dataType", "Require").attr("msg", "标题描述不能为空");
    	$("#order_values"+i).attr("dataType", "Require").attr("msg", "排序值不能为空");
// 		<c:if test="${af.map.link_type eq 60}">
// 			$("#pre_varchars"+i).attr("dataType", "Require").attr("msg", "副标题必须填写");     
// 	    </c:if>
	    $("#file_hidden"+i).attr("dataType", "Filter" ).attr("require", "true").attr("msg", "图片的格式必须是(gif,jpeg,jpg,png)").attr("accept", "gif, jpeg, jpg,png");
	var btn_name = "上传图片";
	upload("file_hidden"+i, "image", btn_name, "${ctx}");
	i++;
    document.getElementById("num").value= i;
});
function deleteRow(i){
	$("#tBodyHidden_"+i).remove();
}

//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>