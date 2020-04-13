<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
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
  <%@ include file="/commons/pages/messages.jsp" %>
  <fieldset style="padding: 10px;margin: 5px;">
  <legend style="color: #F00;font-weight: bold;"><span style="color: #0066FF;font-weight: bold;">【${fn:escapeXml(basePdClass.cls_name)}】</span>已授权的属性</legend>
  <c:if test="${empty basePdAttributeLinkList}">
    <center>
      <font style="color:red;font-weight: bold;">暂无已授权属性，请在“未授权的属性”中选择</font>
    </center>
  </c:if>
  <c:if test="${not empty basePdAttributeLinkList}">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="20%" nowrap="nowrap">主属性名称 / 显示名称</th>
        <th nowrap="nowrap">子属性显示名称</th>
        <th width="10%" nowrap="nowrap">操作</th>
      </tr>
      <c:forEach var="curLink" items="${basePdAttributeLinkList}">
        <tr>
          <td style="text-align:left;">[${fn:escapeXml(curLink.attr_name)}] / [${fn:escapeXml(curLink.attr_show_name)}]</td>
          <td><c:forEach var="curSonLink" items="${basePdAttributeSonList}">
              <c:if test="${curLink.id eq curSonLink.attr_id}"> ${fn:escapeXml(curSonLink.attr_show_name)}&nbsp; </c:if>
            </c:forEach>
          </td>
          <td align="center"><span style="cursor:pointer;" onclick="if(confirm('确定删除？')){location.href='BasePdClass.do?method=changeLinkAttribute&cls_id=${af.map.cls_id}&attr_id=${curLink.id}&pd_class_type=${af.map.pd_class_type}&mod_id=${af.map.mod_id}&change=delete'};"><img src="${ctx}/styles/images/shanchu.gif" width="55" height="18" /></span> </td>
        </tr>
      </c:forEach>
    </table>
  </c:if>
  </fieldset>
  <fieldset style="padding: 10px;margin: 5px;">
  <legend style="color: #F00;font-weight: bold;">未授权的属性</legend>
  <html-el:form action="/admin/BasePdClass">
    <html-el:hidden property="method" styleId="method" value="linkAttribute" />
    <html-el:hidden property="mod_id" styleId="mod_id" />
    <html-el:hidden property="cls_id" styleId="cls_id" />
    <html-el:hidden property="pd_class_type" styleId="pd_class_type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">属性名称：
                <html-el:text property="attr_name_like" styleId="attr_name_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;&nbsp;属性显示名称：
                <html-el:text property="attr_show_name_like" styleId="attr_show_name_like" style="width:80px;" styleClass="webinput"/>
                &nbsp;&nbsp;是否锁定：
                <html-el:select property="is_lock">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未锁定</html-el:option>
                  <html-el:option value="1">已锁定</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <html-el:button property="btn_submit" styleId="btn_submit" value="查 询" styleClass="bgButton" />
                &nbsp;&nbsp;
                <html-el:button property="back" styleId="back" styleClass="bgButton" value="返 回" onclick="history.back();" />
              </td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <html-el:form action="/admin/BasePdClass">
    <div style="text-align: left; padding: 5px;">
      <html-el:button property="btn_submit_insert" value="保存属性" styleClass="bgButton" styleId="btn_submit_insert" />
      &nbsp;
      <html-el:button property="attribute_insert" value="添加属性并绑定" styleClass="bgButton" styleId="attribute_insert" onclick="location.href='BasePdAttribute.do?method=add&mod_id=${af.map.mod_id}&cls_id=${af.map.cls_id}&pd_class_type=${af.map.pd_class_type }';"/>
      <html-el:hidden property="method" styleId="method" value="changeLinkAttribute" />
      <html-el:hidden property="mod_id" styleId="mod_id" />
      <html-el:hidden property="cls_id" styleId="cls_id" />
      <html-el:hidden property="pd_class_type" styleId="pd_class_type" />
      <html-el:hidden property="change" styleId="change" value="insert" />
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
        <th width="20%" nowrap="nowrap">主属性名称 / 显示名称</th>
        <th nowrap="nowrap">子属性显示名称</th>
      </tr>
      <c:forEach var="cur" items="${basePdAttributeList}">
        <tr>
          <td style="text-align:center;"><input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}"/>
          </td>
          <td style="text-align:left;">[${fn:escapeXml(cur.attr_name)}] / [${fn:escapeXml(cur.attr_show_name)}]</td>
          <td><c:forEach var="curSon" items="${basePdAttributeSonList}">
              <c:if test="${cur.id eq curSon.attr_id}"> ${fn:escapeXml(curSon.attr_show_name)}&nbsp; </c:if>
            </c:forEach>
          </td>
        </tr>
      </c:forEach>
    </table>
  </html-el:form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BasePdClass.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "linkAttribute");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("attr_name_like", "${fn:escapeXml(af.map.attr_name_like)}");
            pager.addHiddenInputs("attr_show_name_like", "${fn:escapeXml(af.map.attr_show_name_like)}");
			pager.addHiddenInputs("is_lock", "${af.map.is_lock}");
			pager.addHiddenInputs("cls_id", "${af.map.cls_id}");
			pager.addHiddenInputs("pd_class_type", "${af.map.pd_class_type}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
  </fieldset>
</div>

<script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
<script type="text/javascript" src="${ctx}/commons/scripts/cs.js"></script>
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[

$("#btn_submit").click(function(){

    $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
    $("#btn_submit_insert").attr("disabled", "true");
    $("#back").attr("disabled", "true");
    this.form.submit();
});

$("#btn_submit_insert").click(function(){
	if (!notEmpty()){
		alert("请至少选择一项！");
		return false;
	}
    $("#btn_submit_insert").attr("value", "正在提交...").attr("disabled", "true");
    $("#btn_submit").attr("disabled", "true");
    $("#back").attr("disabled", "true");
    this.form.submit();
});

function notEmpty() {
	var pks = document.getElementsByName("pks");
	for(var i=0; i<pks.length; i++){
		if (pks[i].checked) return true;
	}
}
//]]></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
