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
    <html-el:hidden property="method" styleId="method" value="saveStep2" />
    <html-el:hidden property="poor_id" styleId="poor_id" value="${af.map.poor_id}"/>
     <html-el:hidden property="mod_id" styleId="mod_id"/>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="backTable">
	<div class="all">
      <ul class="nav nav-tabs" id="nav_ul">
          <li onclick="goStep(1)"><a><span>基本信息</span></a></li>
          <li onclick="goStep(2)" class="active" ><a><span>家庭成员</span></a></li>
          <li onclick="goStep(3)"><a><span>帮扶措施</span></a></li>
          <li onclick="goStep(4)"><a><span>帮扶责任人</span></a></li>
      </ul>
    </div>
       <tr>
        <th colspan="15">贫困户家庭成员情况</th>
      </tr>
      <tr> 
        <td style="text-align:center" width="5%" nowrap="nowrap" class="title_item">成员姓名</td>
        <td style="text-align:center" width="5%" class="title_item" nowrap="nowrap">年龄</td>
        <td style="text-align:center" width="14%" class="title_item" nowrap="nowrap">身份证</td>
        <td style="text-align:center" width="5%" class="title_item" nowrap="nowrap">与户主关系</td>
        <td style="text-align:center" width="5%" class="title_item" nowrap="nowrap">劳动能力</td>
        <td style="text-align:center" align="center" class="title_item" nowrap="nowrap"  width="5%">
        <div id="divFile"> <img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="InputAddTr" title="再添加一个" />
        </div>
        </td>
      </tr>
       <tr id="InputHidden" style="display:none;">
          <td  width="5%" align="center">
              <html-el:text property="family_name" styleId="family_name" maxlength="15" style="width:100px" styleClass="webinput" />
          </td>
          <td  width="5%" align="center">
              <html-el:text property="age" styleId="age" maxlength="15"  style="width:100px" styleClass="webinput" />
          </td>
          <td  width="14%" align="center">
          	<html-el:text property="id_card" maxlength="120" styleId="id_card" style="width:200px"/>
          </td>
          <td  width="5%" align="center">
              <html-el:text property="relation_ship" styleId="relation_ship" maxlength="50" style="width:200px" styleClass="webinput" />
          </td>
           <td  width="5%" align="center">
              <html-el:text property="work_power" styleId="work_power" maxlength="50" style="width:100px" styleClass="webinput" />
          </td>
              <td  width="5%" align="center"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="InputDelTr" title="删除"/></td>
        </tr>
        <tbody id="InputShow">
        </tbody>
     <c:if test="${not empty familyList}">
      <c:forEach var="entity" items="${familyList}" varStatus="avs">
          <tbody id="_InputShow">
          <tr data_type="clone_tr">
          <td  width="5%" align="center">
              	<html-el:text property="family_name" styleId="family_name" maxlength="15" style="width:60px" styleClass="webinput" value="${entity.family_name}" />
          </td>
           <td  width="5%" align="center">
              	<html-el:text property="age" styleId="age" maxlength="18" style="width:120px" styleClass="webinput" value="${entity.age}" />
          </td>
          <td  width="5%" align="center">
              	<html-el:text property="id_card" styleId="id_card" maxlength="15" style="width:200px" styleClass="webinput" value="${entity.id_card}" />
          </td>
          <td  width="14%" align="center">
              	<html-el:text property="relation_ship" styleId="relation_ship" maxlength="15" style="width:200px" styleClass="webinput" value="${entity.relation_ship}" />
          </td>
          <td  width="5%" align="center">
              	<html-el:text property="work_power" styleId="work_power" maxlength="15" style="width:36px" styleClass="webinput" value="${entity.work_power}" />
          </td>
             <td  width="5%" align="center"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="_InputDelTr" title="删除"/></td>
           </tr>
         </tbody>
       </c:forEach>
     </c:if>
      <tr>
      <td colspan="6" align="center">
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
	 $(".toolbar a:eq(2)").addClass("on");
	 
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
	
function goStep(step){
	var id = document.getElementById('poor_id').value;
	var mod_id = document.getElementById('mod_id').value;
	if(id != "" && id != null){
		location.href="${ctx}/manager/admin/PoorManager.do?method=step"+step+"&poor_id="+id+"&mod_id="+mod_id;
	}else{
		alert("请先完成贫困户基本信息填写!");
	}
}

function changeRequireAttr(){
	$("body").find("tr[data_type='clone_tr'] td input[name='family_name']").each(function(){
		  $(this).attr("dataType", "Require").attr("msg", "请填写家庭成员姓名!");
	});
	$("body").find("tr[data_type='clone_tr'] td select[name='id_card']").each(function(){
		  $(this).attr("dataType", "Require").attr("msg", "请填写正确格式的身份证号码!");
	});
	$("body").find("tr[data_type='clone_tr'] td input[name='relation_ship']").each(function(){
		  $(this).attr("dataType", "Require").attr("msg", "请填写与户主关系!");
	});
	$("body").find("tr[data_type='clone_tr'] td input[name='work_power']").each(function(){
		  $(this).attr("dataType", "Require").attr("msg", "请填写劳动能力!");
	});
	$("body").find("tr[data_type='clone_tr'] td input[name='age']").each(function(){
		  $(this).attr("dataType", "Require").attr("msg", "请填写家庭成员年龄!");
	});
}
//]]></script> 
</body>
</html>