<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body style="margin-bottom: 100px">
<div style="text-align:left;">
  <%@ include file="/commons/pages/messages.jsp" %>
  <br/>
  <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td height="32"><c:if test="${not empty af.map.level_4}" var="level_4"><span style="color:#ccc;">增加〖${af.map.mod_name}〗的子目录</span></c:if>
        <c:if test="${not level_4}"><a href='javascript:showTable("addDept");'>增加<span style="color:#FF0000;">〖${af.map.mod_name}〗</span>的子目录</a></c:if></td>
    </tr>
    <tr>
      <td><html-el:form action="/admin/HelpModule">
          <html-el:hidden property="method" value="save" />
          <html-el:hidden property="h_mod_id" />
          <table id="addDept" width="98%" border="0" cellpadding="0" cellspacing="0" class="tableClass" style="display:none;">
            <tr>
              <th colspan="2">增加目录详细信息</th>
            </tr>
            <tr>
              <td width="17%" class="title_item">目录名称：</td>
              <td width="83%"><html-el:text property="mod_name" maxlength="20" style="width:240px;" value="" /></td>
            </tr>
            <tr>
              <td class="title_item">目录链接：</td>
              <td><html-el:text property="mod_url" styleId="mod_url_id_add" readonly="true" maxlength="100" style="width:240px;color:#ccc" value="" />
                &nbsp; [添加 <a style="text-decoration:none;" onclick="$('#mod_url_id_add').val('HelpInfo.do?method=single');">单个数据</a> <a style="text-decoration:none;" onclick="$('#mod_url_id_add').val('HelpInfo.do?method=list');">多个数据</a> 链接]&nbsp; <a style="text-decoration:none;" onclick="$('#mod_url_id_add').val('');">[清空链接]</a>
                <div class="sysTip" style="padding:2px;">说明：<br />1、目录链接为空：代表此节点不能维护数据。<br />2、单个数据：代表此节点只能添加一条数据。 <br />3、多个数据：代表此节点可以添加多条数据。</div></td>
            </tr>
            <tr>
              <td class="title_item">目录说明：</td>
              <td><html-el:text property="mod_desc" maxlength="100" style="width:240px;" value="" /></td>
            </tr>
            <tr>
              <td class="title_item">排序值：</td>
              <td><html-el:text property="order_value" size="4" />
                值越大，显示越靠前，范围：0-9999</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td><html-el:submit value="保存 " styleClass="bgButton"/>
                &nbsp;<html-el:reset value="重填 " onclick="this.form.reset();" styleClass="bgButton"/>
                &nbsp;<html-el:button property="back" value=" 返回 " onclick="history.back();" styleClass="bgButton"/></td>
            </tr>
          </table>
        </html-el:form></td>
    </tr>
    <tr>
      <td height="32"><a href='javascript:showTable("editDept");'>修改<span style="color:#FF0000;">〖${af.map.mod_name}〗</span>目录信息</a></td>
    </tr>
    <tr>
      <td><html-el:form action="/admin/HelpModule.do">
          <html-el:hidden property="method" value="save" />
          <html-el:hidden property="id" />
          <html-el:hidden property="h_mod_id" />
          <table id="editDept" width="98%" border="0" cellpadding="0" cellspacing="0" class="tableClass" style="display:none;">
            <tr>
              <th colspan="2">修改目录详细信息</th>
            </tr>
            <tr>
              <td width="17%" class="title_item">目录名称：</td>
              <td width="83%"><html-el:text property="mod_name" maxlength="20" style="width:240px;" /></td>
            </tr>
            <tr>
              <td class="title_item">目录链接：</td>
              <td><html-el:text property="mod_url" styleId="mod_url_id_add_edit" readonly="true" maxlength="100" style="width:240px;color:#ccc" />
                &nbsp; [添加 <a style="text-decoration:none;" onclick="$('#mod_url_id_add_edit').val('HelpInfo.do?method=single');">单个数据</a> <a style="text-decoration:none;" onclick="$('#mod_url_id_add_edit').val('HelpInfo.do?method=list');">多个数据</a> 链接]&nbsp; <a style="text-decoration:none;" onclick="$('#mod_url_id_add_edit').val('');">[清空链接]</a>
                <div class="sysTip" style="padding:2px;">说明：<br />1、目录链接为空：代表此节点不能维护数据。<br />2、单个数据：代表此节点只能添加一条数据。 <br />3、多个数据：代表此节点可以添加多条数据。</div></td>
            </tr>
            <tr>
              <td class="title_item">目录说明：</td>
              <td><html-el:text property="mod_desc" maxlength="100" style="width:240px;" /></td>
            </tr>
            <tr>
              <td class="title_item">排序值：</td>
              <td><html-el:text property="order_value" size="4" />
                值越大，显示越靠前，范围：0-9999</td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td><html-el:submit value=" 保存 " styleClass="bgButton" />
                &nbsp;<html-el:reset value=" 重填 " onclick="this.form.reset();" styleClass="bgButton"/>
                &nbsp;<html-el:button property="back" value=" 返回 " onclick="history.back();" styleClass="bgButton"/></td>
            </tr>
          </table>
        </html-el:form></td>
    </tr>
    <tr>
      <td height="32"><c:if test="${af.map.is_lock eq 1}"><span style="color:#ccc;">删除目录〖${af.map.mod_name}〗</span></c:if>
        <c:if test="${af.map.is_lock eq 0}"><a href='javascript:if(confirm("确认删除？如有子目录也将会被全部删除！"))location.href="HelpModule.do?method=delete&amp;h_mod_id=${af.map.h_mod_id}&amp;id=${af.map.id}";'>删除目录<span style="color:#FF0000;">〖${af.map.mod_name}〗</span></a></c:if></td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>

<script type="text/javascript">
//<![CDATA[
$("input[type='text'],input[type='password']").css("border","1px solid #ccc");
$("input[type='text']").not("[readonly]").focus(function(){
	$(this).addClass("row_focus");
}).blur(function(){
	$(this).removeClass("row_focus");
});

var f_0 = document.forms[0];
f_0.mod_name.setAttribute("datatype","Require");
f_0.mod_name.setAttribute("msg","目录名称必须填写");

//f_0.mod_url.setAttribute("datatype","Require");
//f_0.mod_url.setAttribute("msg","目录链接必须填写");

f_0.order_value.setAttribute("datatype","Number");
f_0.order_value.setAttribute("require","true");
f_0.order_value.setAttribute("msg","排序值填写有误！必须为数字！");

f_0.onsubmit = function () {
	return Validator.Validate(this, 3);
}

var f_1 = document.forms[1];
f_1.mod_name.setAttribute("datatype","Require");
f_1.mod_name.setAttribute("msg","目录名称必须填写");

//f_1.mod_url.setAttribute("datatype","Require");
//f_1.mod_url.setAttribute("msg","目录链接必须填写");

f_1.order_value.setAttribute("datatype","Number");
f_1.order_value.setAttribute("require","true");
f_1.order_value.setAttribute("msg","排序值填写有误！必须为数字！");

f_1.onsubmit = function () {
	return Validator.Validate(this, 3);
}

function showTable(id) {
	$("#" + id).toggle();
}
//]]>
</script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>
