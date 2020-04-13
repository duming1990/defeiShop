<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="mainbox mine" style="height: 1300px;">
    <html-el:form action="/admin/PoorManager.do" enctype="multipart/form-data">
    <html-el:hidden property="queryString" styleId="queryString" />
    <html-el:hidden property="method" styleId="method" value="saveStep3" />
    <html-el:hidden property="id" styleId="id" />
     <html-el:hidden property="mod_id" styleId="mod_id"/>
    <html-el:hidden property="poor_id" styleId="poor_id" value="${af.map.poor_id}"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
	<div class="all">
      <ul class="nav nav-tabs" id="nav_ul">
          <li onclick="goStep(1)"><a><span>基本信息</span></a></li>
          <li onclick="goStep(2)"><a><span>家庭成员</span></a></li>
          <li onclick="goStep(3)" class="active"><a><span>帮扶措施</span></a></li>
          <li onclick="goStep(4)"><a><span>帮扶责任人</span></a></li>
      </ul>
    </div>
       <tr>
        <th colspan="15">帮扶措施</th>
      </tr>
      <tr> 
        <td style="text-align:center" width="30%" class="title_item" nowrap="nowrap">帮扶单位</td>
        <td style="text-align:center" width="52%" class="title_item" nowrap="nowrap">项目内容</td>
        <td style="text-align:center" width="10%" nowrap="nowrap" class="title_item">时间</td>
        <td style="text-align:center" width="8%" align="center" class="title_item" nowrap="nowrap" >
        <div id="divFile"> <img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="InputAddTr" title="再添加一个111111111111111111" />
        </div>
        </td>
      </tr>
       <tr id="InputHidden" style="display:none;">
          <td  width="30%" align="center">
              <html-el:text property="dan_wei_name" styleId="dan_wei_name" maxlength="255" style="width:80%"  styleClass="webinput" />
          </td>
          <td  width="52%" align="center">
              <html-el:textarea rows="5" property="content"  styleId="content"  style="width:80%;margin-left: 10%;" styleClass="webinput" />
          </td>
          <td width="10%" align="center"><fmt:formatDate value="${af.map.help_date}" pattern="yyyy-MM-dd" var="help_date" />
          <html-el:text property="help_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${help_date}" style="cursor:pointer;text-align:center;" title="点击选择日期" /></td>
          <td  width="8%" align="center"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="InputDelTr" title="删除"/></td>
        </tr>
        <tbody id="InputShow">
        </tbody>
     <c:if test="${not empty cuoshiList}">
      <c:forEach var="entity" items="${cuoshiList}" varStatus="avs">
          <tbody id="_InputShow">
          <tr data_type="clone_tr">
          <td  width="30%" align="center">
              	<html-el:text property="dan_wei_name" styleId="dan_wei_name" maxlength="50"style="width:80%" styleClass="webinput" value="${entity.dan_wei_name}" />
          </td>
           <td  width="52%" align="center">
              	<html-el:textarea rows="5" property="content" styleId="content" styleClass="webinput" value="${entity.content}" style="width:80%;margin-left: 10%;"/>
          </td>
        <td width="10%" align="center">
          <fmt:formatDate value="${entity.help_date}" pattern="yyyy-MM-dd" var="help_date" />
          <html-el:text property="help_date" size="10" maxlength="10" readonly="true" onclick="WdatePicker();" value="${help_date}" style="cursor:pointer;text-align:center;" title="点击选择日期" />
        </td>
             <td  width="8%" align="center"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="_InputDelTr" title="删除"/></td>
           </tr>
         </tbody>
       </c:forEach>
     </c:if>
      <tr>
        <td colspan="4" align="center">
       <html-el:submit property="" value="保存" styleClass="bgButton" styleId="btn_submit" />
      &nbsp;
      <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" />
      </td>
      </tr>
    </table>
  </html-el:form>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/calendar/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.cs.js"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.form.min.js"></script>
<script type="text/javascript"><!--//<![CDATA[
var f = document.forms[0];
$(document).ready(function(){
	changeRequireAttr();
	$(".toolbar a").removeClass("on");
	$(".toolbar a:eq(3)").addClass("on");
	 
	$("tr", "#InputShow").each(function(){
		var lastTR = $(this);
		$("td:last", $(this)).click(function (){
			$(this).parent().remove();
		}).css("cursor", "pointer");
	});
	
    $("#InputAddTr").click(function (){
    	$("#InputHidden").clone().attr("data_type","clone_tr").appendTo($("#InputShow")).show();
    	var lastTR = $("tr:last", "#InputShow");
    	changeRequireAttr();
    	$("#InputDelTr", lastTR).click(function (){
    		$(this).parent().parent().remove();
    	});
    });
 
    $("tr", "tbody[id='_InputShow']").each(function(){
 		var lastTR = $(this);
 		$("td:last", $(this)).click(function (){
 			$(this).parent().remove();
 		}).css("cursor", "pointer");
 	});		
})  
//提交
f.onsubmit = function(){
	if(Validator.Validate(f, 3)){
           $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
           $("#btn_reset").attr("disabled", "true");
           $("#btn_back").attr("disabled", "true");
		f.submit();
	}
return false;
};

function changeRequireAttr(){
	$("body").find("tr[data_type='clone_tr'] td input[name='dan_wei_name']").each(function(){
		  $(this).attr("dataType", "Require").attr("msg", "请填写帮扶单位!");
	});
	$("body").find("tr[data_type='clone_tr'] td textarea[name='content']").each(function(){
		  $(this).attr("dataType", "Limit").attr("min", "1").attr("max", "256").attr("msg", "请填写项目内容!");
	});
	$("body").find("tr[data_type='clone_tr'] td input[name='help_date']").each(function(){
		  $(this).attr("dataType", "Require").attr("msg", "请填写帮扶时间!");
	});
}

function goStep(step){
	var id = document.getElementById('poor_id').value;
	var mod_id = document.getElementById('mod_id').value;
	if(id != "" && id != null){
		location.href="${ctx}/manager/admin/PoorManager.do?method=step"+step+"&poor_id="+id+"&mod_id="+mod_id;
	}else{
		alert("请先完成贫困户基本信息填写!");
	}
}
//]]></script> 
</body>
</html>