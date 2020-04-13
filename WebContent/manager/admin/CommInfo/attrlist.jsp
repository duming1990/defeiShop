<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>套餐规格管理</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<script type="text/javascript" src="${ctx}/scripts/lhgdialog/lhgdialog.min.js?skin=discuz"></script>
</head>
<body>
<!-- main start -->
<div>
 <div style="text-align: left;padding: 5px;">
  <c:url var="url" value="/manager/admin/CommInfo.do?method=addattr&amp;comm_id=${af.map.comm_id}" />
       <button class="bgButtonFontAwesome" type="button" id="add" name="add" onclick="location.href='${url}';"><i class="fa fa-plus-square"></i>添加规格</button>
    <button class="bgButtonFontAwesome" type="button" onclick="selectHasAttr('${commInfo.cls_id}','${ids}','${af.map.comm_id}');">
      <i class="fa fa-check-circle-o"></i>从规格库选择</button>
    </div>
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <td><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
          <tr>
            <th nowrap="nowrap" width="20%">套餐主规格名称 </th>
            <th nowrap="nowrap" width="25%">套餐子规格 </th>
            <th width="10%">添加时间</th>
            <th width="10%">排序值</th>
            <th width="10%" nowrap="nowrap">操作</th>
          </tr>
          <c:forEach var="cur" items="${list_BaseAttribute}" varStatus="vs">
            <tr>
              <td align="left" nowrap="nowrap"><a title="查看" href="CommInfo.do?method=viewattr&amp;id=${cur.id}">${fn:escapeXml(cur.attr_name)}</a></td>
              <td align="left"><c:forEach items="${cur.map.list_baseAttributeSon}" var="son">
                  <c:out value="${son.attr_name}"></c:out>
                  &nbsp; </c:forEach></td>
              <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
              <td align="center"><c:out value="${cur.order_value }"></c:out></td>
              <td align="center" nowrap="nowrap">
                <a class="butbase" onclick="doNeedMethod(null, 'CommInfo.do','editattr', 'id=${cur.id}&comm_id=${cur.link_id}' )"><span class="icon-edit">修改</span></a>
                <c:if test="${isLock or isDel}">&nbsp;
                 <a class="butbase but-disabled"><span class="icon-remove">删除</span></a>
                </c:if>
                <!-- 如果有对应规格的产品销售,则不能删除,暂时先不做判断  -->
                <c:if test="${not isLock and not isDel}">
                  <a class="butbase" onclick="confirmNeedMethod('确定删除吗？', 'CommInfo.do', 'deleteattr','id=${cur.id}&comm_id=${cur.link_id}' )"><span class="icon-remove">删除</span></a>
                </c:if>
                </td>
            </tr>
          </c:forEach>
        </table></td>
    </tr>
     <tr>
        <td colspan="2" style="text-align:center">
          <html-el:button property="" value="保存套餐规格，下一步填写套餐价格" styleClass="bgButton" styleId="btn_submit"/></td>
      </tr>
  </table>
</div>
<!-- main end --> 

<script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script> 
<script type="text/javascript">//<![CDATA[

                                          
$("#btn_submit").click(function(){
	var api = frameElement.api, W = api.opener;
	W.SetTczh();
	api.close();  
});
                                  
                                          

//]]></script>
<jsp:include page="../../../_public_page.jsp" flush="true"/>
</body>
</html>