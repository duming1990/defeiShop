<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册会员中心 - ${app_name}</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
<!-- main start -->
<div class="mainbox mine">
  <jsp:include page="../_nav.jsp" flush="true"/>
    <div class="clear"></div>
      <%@ include file="/commons/pages/messages.jsp" %>
      <html-el:form action="/customer/MyShippingAddress">
        <html-el:hidden property="id" />
      </html-el:form>
      <form id="listForm" name="listForm" method="post" action="MyShippingAddress.do?method=delete">
        <html-el:hidden property="method" value="list" />
        <div class="all">
        <button class="bgButtonFontAwesome" type="button" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);"><i class="fa fa-minus-square"></i>删除所选</button>
        &nbsp;
        <button class="bgButtonFontAwesome" type="button" onclick="location.href='MyShippingAddress.do?method=add&mod_id=${af.map.mod_id}&par_id=${af.map.par_id}'"><i class="fa fa-plus-square"></i>添加</button>
        </div>
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable" align="left">
          <tr class="tite2">
            <th width="4%" align="center" nowrap="nowrap"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" /></th>
            <th align="center" nowrap="nowrap"><strong>收货人信息</strong></th>
            <th width="20%" align="center" nowrap="nowrap"><strong>操作</strong></th>
          </tr>
          <c:forEach var="cur" items="${shippingAddressList}" varStatus="vs">
            <tr>
              <td align="center" nowrap="nowrap"><input name="pks" type="checkbox" id="pks" value="${cur.id}" /></td>
              <td valign="top"><table width="100%" border="0" cellspacing="0" cellpadding="0" class="backTable">
                  <tr>
                    <td width="10%" nowrap="nowrap">收货人姓名：</td>
                    <td align="left">${fn:escapeXml(cur.rel_name)}</td>
                  </tr>
                  <tr>
                    <td >省份：</td>
                    <td align="left">${fn:escapeXml(cur.map.full_name)}</td>
                  </tr>
                  <tr>
                    <td>详细地址：</td>
                    <td align="left">${fn:escapeXml(cur.rel_addr)}</td>
                  </tr>
                  <tr>
                    <td>手机号码：</td>
                    <td align="left">${fn:escapeXml(cur.rel_phone)}</td>
                  </tr>
                </table></td>
              <td align="center">
                <c:if test="${cur.is_default eq 0}"><span style="cursor: pointer; margin-left:7px;" onclick="doNeedMethod(null, 'MyShippingAddress.do', 'updateDefault','id=${cur.id}&'+$('#bottomPageForm').serialize())"> 设置为默认地址 </span></c:if>
                <c:if test="${cur.is_default eq 1}"><span style="color:#999;" title="地址已默认">设置为默认地址 </span></c:if>
                <br />
                <c:if test="${cur.is_del eq 0}"> <span style="cursor: pointer; margin-right:7px;" onclick="confirmUpdate(null, 'MyShippingAddress.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">修改</span> | <span style="cursor: pointer; margin-left:7px;" onclick="confirmDelete(null, 'MyShippingAddress.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())">删除</span> </c:if>
                <c:if test="${cur.is_del eq 1}"> <span style="color:#999;" title="已删除<，不能修改"> 修改</span><span style="color:#999;" title="已删除，不能再次删除"> 删除</span> </c:if></td>
            </tr>
          </c:forEach>
        </table>
      </form>
   <div class="clear"></div>
   <div class="black">
      <form id="bottomPageForm" name="bottomPageForm" method="post" action="MyShippingAddress.do">
        <table width="98%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td height="10"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
              <script type="text/javascript">
				var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		        pager.addHiddenInputs("method", "list");
				pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
				pager.addHiddenInputs("par_id", "${af.map.par_id}");
				pager.addHiddenInputs("rel_name", "${af.map.rel_name}");
		        pager.addHiddenInputs("rel_province", "${fn:escapeXml(af.map.rel_province)}");
		        pager.addHiddenInputs("rel_city", "${af.map.rel_city}");
				pager.addHiddenInputs("country", "${af.map.country}");
				pager.addHiddenInputs("rel_addr", "${af.map.rel_addr}");
				pager.addHiddenInputs("rel_phone", "${af.map.rel_phone}");
				pager.addHiddenInputs("rel_tel", "${af.map.rel_tel}");
				pager.addHiddenInputs("rel_email", "${af.map.rel_email}");
				pager.addHiddenInputs("rel_zip","${af.map.rel_zip}");
		        document.write(pager.toString());
            	</script></td>
          </tr>
        </table>
      </form>
    </div>
</div>
<!-- main end -->
<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
	var f = document.forms[0];

	$("#btn_submit").click(function(){
		this.form.submit();
	});
});
//]]></script>
</body>
</html>