<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<meta content="${app_name}会员中心关键字" name="keywords" />
<meta content="${app_name}会员中心介绍" name="description" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/colorbox/style3/colorbox.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
	<div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <html-el:form action="/admin/ImportBi.do"  enctype="multipart/form-data">
        <html-el:hidden property="queryString" styleId="queryString" />
        <html-el:hidden property="method" styleId="method" value="save" />
        <html-el:hidden property="mod_id" styleId="mod_id" />
        <html-el:hidden property="id" styleId="id" />
        <html-el:hidden property="is_audit" styleId="is_audit" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">编号：</td>
      <td colspan="2">${fn:escapeXml(af.map.index_no)}</td>
    </tr>
    <tr>
      <td width="14%" nowrap="nowrap" class="title_item">运营中心：</td>
      <td colspan="2">${fn:escapeXml(af.map.map.servicecenter_name)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">数量：</td>
      <td colspan="2">${fn:escapeXml(af.map.sum_count)}</td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">福利金总数：</td>
      <td colspan="2"><fmt:formatNumber pattern="#,##0.00" value="${af.map.bi_sum}"/>&nbsp;元</td>
    </tr>
   
    <tr>
      <td nowrap="nowrap" class="title_item">支付凭证：</td>
      <td colspan="2"><c:if test="${not empty fn:substringBefore(af.map.file_path, '.')}"> <img src="${ctx}/${af.map.file_path}@s400x400" height="100" /> </c:if>
        <c:if test="${empty fn:substringBefore(af.map.file_path, '.')}"> <img src="${ctx}/images/no_img.gif" /> </c:if></td>
    </tr>
    
      <tr>
        <td nowrap="nowrap" class="title_item">添加时间：</td>
        <td  colspan="2" height="24"><fmt:formatDate value="${af.map.add_date}" pattern="yyyy-MM-dd hh:mm:ss" var="_up_date" />
          <c:out value="${_up_date}"/></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">备注：</td>
        <td  colspan="2" height="24">${af.map.remark}</td>
      </tr>
    
    <tr>
        <th colspan="3">明细</th>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">福利金明细：</td>
        <td colspan="2" width="88%"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left" >
            <tr>
              <td align="center" width="5%">序号</td>
              <td align="center" width="33%">手机号码</td>
              <td align="center" width="33%">用户名称</td>
              <td align="center" width="15%">导入名称</td>
              <td align="center" width="33%">福利金金额</td>
            </tr>
            <tbody>
              <c:forEach var="cur" items="${sonList}" varStatus="vs" >
                <tr>
                  <td align="center" id="edit_son" >${vs.count }</td>
                  <td align="center" >${cur.mobile}</td>
                  <td align="center" >${cur.user_name}</td>
                  <td align="center" >${cur.import_user_name}</td>
                  <td align="center" ><fmt:formatNumber pattern="#,##0.00" value="${cur.bi_no}"/></td>
                </tr>
              </c:forEach>
            </tbody>
          </table></td>
      </tr>
    <tr>
        <th colspan="3">审核信息</th>
      </tr>
        <tr>
          <td nowrap="nowrap" class="title_item">当前审核状态：</td>
          <td nowrap="nowrap" colspan="2"><c:choose>
              <c:when test="${af.map.audit_state eq -1}"><span class="tip-danger">管理员审核不通过</span></c:when>
              <c:when test="${af.map.audit_state eq 0}"><span class="tip-default">待审核</span></c:when>
              <c:when test="${af.map.audit_state eq 1}"><span class="tip-success">管理员审核通过</span></c:when>
            </c:choose></td>
        </tr>
        <tr>
        <td width="12%" nowrap="nowrap" class="title_item"><span style="color: #F00;">*</span>审核状态：</td>
        <td colspan="2" width="88%"><html-el:select property="audit_state" styleId="audit_state">
            <html-el:option value="">请选择...</html-el:option>
            <html-el:option value="0">待审核</html-el:option>
            <html-el:option value="1">审核通过</html-el:option>
            <html-el:option value="-1">审核不通过</html-el:option>
          </html-el:select></td>
      </tr>
      <tr>
      	<td width="12%" nowrap="nowrap" class="title_item">审核说明：</td>
      	<td colspan="2">
      		<html-el:textarea property="audit_desc" styleClass="webinput" styleId="audit_desc"  style="width:500px; height:80px;" ></html-el:textarea>
      	</td>
      </tr>
       <tr>
        <td colspan="3" align="center"><html-el:button property="" value="审 核" styleClass="bgButton" styleId="btn_submit" />
            &nbsp;
            <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      <tr>
      <td nowrap="nowrap" class="title_item">提示：</td>
      	<td>确认福利金明细金额和支付凭证金额是否一致</td>
      </tr>
  </table>
  </html-el:form>
  <div class="clear"></div>
</div>
<script type="text/javascript" src="${ctx}/scripts/colorbox/jquery.colorbox.min.js"></script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	$("a.viewImg").colorbox({rel:'viewImg'});
	$("a.viewImglunbo").colorbox({rel:'viewImglunbo'});
	
	$("#audit_state").attr("dataType", "Require").attr("msg", "请选择审核状态！");
	$("#audit_desc").attr("datatype","Limit").attr("min","0").attr("max","125").attr("msg","审核说明在125个汉字之内");
	
	var f = document.forms[0];
	
	$("#btn_submit").click(function(){
		if(Validator.Validate(f, 3)){
            $("#btn_submit").attr("value", "正在提交...").attr("disabled", "true");
            $("#btn_reset").attr("disabled", "true");
            $("#btn_back").attr("disabled", "true");
			f.submit();
		}else{
			return false;
		}
	});
});

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true" />
</body>
</html>
