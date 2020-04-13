<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/SysModule.do">
    <html-el:hidden property="queryString" />
    <html-el:hidden property="method" value="save" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="mod_id_old" value="${af.map.id}" />
    <html-el:hidden property="is_copy" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">菜单基本信息</th>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>上级菜单ID：</td>
        <td><html-el:text property="par_id" maxlength="11"  styleClass="webinput" styleId="par_id" style="width:100px" />
          &nbsp;请核对后填写，只能是数字</td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>菜单ID：</td>
        <td><html-el:text property="id" maxlength="11"  styleClass="webinput" styleId="id" style="width:100px" />
          &nbsp;<span id="type_name_tip" style="display:none;"></span>
          &nbsp;请核对后填写，只能是数字</td>
      </tr>
      <tr>
        <td width="15%" class="title_item"><span style="color: #F00;">*</span>菜单名称：</td>
        <td width="85%"><html-el:text property="mod_name" maxlength="20" styleClass="webinput" styleId="mod_name" style="width:200px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>菜单描述：</td>
        <td><html-el:text property="mod_desc" maxlength="20" styleClass="webinput" styleId="mod_desc" style="width:400px" /></td>
      </tr>
      <tr>
        <td class="title_item">菜单图标：</td>
        <td><html-el:hidden  property="font_awesome" styleId="font_awesome" />
          <span><i class="${af.map.font_awesome}" id="font_awesome_i" style="font-size: 20px;"></i></span> &nbsp; <a class="butbase" onclick="getFontIcon();"><span class="icon-search">选择</span></a></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>菜单组：</td>
        <td><html-el:select property="mod_group">
            <c:forEach items="${modGroups}" var="cur">
              <html-el:option value="${cur.index}">${cur.name}</html-el:option>
            </c:forEach>
          </html-el:select></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>菜单URL：</td>
        <td><html-el:text property="mod_url" maxlength="100"  styleClass="webinput" styleId="mod_url" style="width:80%" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>菜单层级：</td>
        <td><html-el:text property="mod_level" maxlength="1" styleClass="webinput" styleId="mod_level" style="width:50px" /></td>
      </tr>
      <tr>
        <td class="title_item"><span style="color: #F00;">*</span>菜单权限：</td>
        <td><html-el:text property="ppdm_code" maxlength="2" styleClass="webinput" styleId="ppdm_code" style="width:50px" /></td>
      </tr>
      <tr>
        <td class="title_item">排序值：</td>
        <td><html-el:text property="order_value"  maxlength="11" size="11" styleClass="webinput" styleId="order_value"  />
          值越大，显示越靠前，范围：0-9999 </td>
      </tr>
      <c:if test="${af.map.is_lock eq 0}">
        <tr>
          <td class="title_item">是否锁定：</td>
          <td><html-el:select property="is_lock" styleId="is_lock">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select>
            （锁定后，数据将无法删除） </td>
        </tr>
      </c:if>
      <tr>
        <td class="title_item">是否删除：</td>
        <td><html-el:select property="is_del" styleId="is_del">
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="1">是</html-el:option>
          </html-el:select>
        </td>
      </tr>
      <tr>
        <td class="title_item">是否公用：</td>
        <td><html-el:select property="is_public" styleId="is_public">
            <html-el:option value="0">否</html-el:option>
            <html-el:option value="9">是</html-el:option>
          </html-el:select>
        </td>
      </tr>
      <tr>
        <td class="title_item">提示：</td>
        <td><span style="color: #E80909;">请勿随意修改基础数据，如需修改请和管理员确认！</span> </td>
      </tr>
      <c:if test="${af.map.is_del eq 1}">
        <tr>
          <td class="title_item">是否删除：</td>
          <td><html-el:select property="is_del" styleId="is_del">
              <html-el:option value="0">否</html-el:option>
              <html-el:option value="1">是</html-el:option>
            </html-el:select></td>
        </tr>
      </c:if>
      <c:if test="${(af.map.type ne 200) and (af.map.type ne 300) and (af.map.type ne 1000)}">
        <tr>
          <td colspan="2" style="text-align:center"><html-el:button property="" value="保 存" styleClass="bgButton" styleId="btn_submit" />
            &nbsp;
            <html-el:button property="" value="重 填" styleClass="bgButton" styleId="btn_reset" onclick="this.form.reset();" />
            &nbsp;
            <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
        </tr>
      </c:if>
    </table>
  </html-el:form>
</div>
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[

var f = document.forms[0];

$("#mod_name").attr("datatype","Require").attr("msg","菜单名称必须填写");
$("#id").attr("datatype","Number").attr("msg","必须是正整数");
$("#par_id").attr("datatype","Number").attr("msg","必须是正整数");
$("#mod_level").attr("datatype","Number").attr("msg","必须是正整数");
$("#ppdm_code").attr("datatype","Number").attr("msg","必须是正整数");
$("#order_value").attr("datatype","Number").attr("msg","排序值必须在0~9999之间的正整数");

// 提交
var type_name_exits = '<span id="tip" style="color:red;">对不起，该菜单ID已经被使用</span>';
$("#btn_submit").click(function(){
	if(Validator.Validate(f, 3)){
		$.post("?method=checkModid",{mod_id : $("#id").val(), id:"${af.map.id}"},function(data){
			$("#type_name_tip").empty();
			if(data == "1"){
				$("#id").focus();
				$("#type_name_tip").show().append(type_name_exits);
			} else {
		        $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
		        $("#btn_reset").attr("disabled", "true");
		        $("#btn_back").attr("disabled", "true");
				f.submit();
			}
		});
	}
});

function getFontIcon(){
	var url = "${ctx}/CsAjax.do?method=getFontIcon&t=" + Math.random();
	$.dialog({
		title:  "选择图标",
		width:  1200,
		height: 800,
        lock:true ,
        zIndex: 9999,
		content:"url:"+url
	});
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
