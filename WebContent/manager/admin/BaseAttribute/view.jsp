<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/scripts/jquery-ui/themes/base/jquery-ui.custom.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <table style="display: none;">
    <tr id="xzHidden" >
      <td align="center" nowrap="nowrap"><input class="flag_has_items" name="type_name" id="type_name" type="text" /></td>
      <td align="center" nowrap="nowrap"><input  name="type_show_name" id="type_show_name" type="text" /></td>
      <td align="center" nowrap="nowrap"><input name="order_value_son" id="order_value_son" type="text"  maxlength="4" size="4" /></td>
      <td align="center" nowrap="nowrap"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" id="imgDelTr" title="删除"/></td>
    </tr>
  </table>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th colspan="2">父属性基本信息</th>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">属性名称：</td>
        <td width="88%"><c:out value="${af.map.attr_name}"></c:out></td>
      </tr>
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">属性显示名称：</td>
        <td width="88%"><c:out value="${af.map.attr_show_name}"></c:out></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">属性类型：</td>
        <td>
        <c:choose>
        	<c:when test="${af.map.type eq 1}">简单文本框</c:when>
        	<c:when test="${af.map.type eq 2}">可编辑文本框</c:when>
        	<c:when test="${af.map.type eq 3}">单选</c:when>
        	<c:when test="${af.map.type eq 4}">多选</c:when>
        	<c:when test="${af.map.type eq 5}">下拉框选择</c:when>
        </c:choose>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">是否必填：</td>
        <td>
        	<c:choose>
        		<c:when test="${af.map.is_required eq 1}">是</c:when>
        		<c:when test="${af.map.is_required eq 0}">否</c:when>
        	</c:choose>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">是否显示：</td>
        <td>
        	<c:choose>
        		<c:when test="${af.map.is_show eq 1}">是</c:when>
        		<c:when test="${af.map.is_show eq 0}">否</c:when>
        	</c:choose>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">是否锁定：</td>
        <td>
        	<c:choose>
        		<c:when test="${af.map.is_lock eq 1}">已锁定</c:when>
        		<c:when test="${af.map.is_lock eq 0}">未锁定</c:when>
        	</c:choose>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">备注：</td>
        <td><c:out value="${af.map.remark}"></c:out></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">排序值：</td>
        <td><c:out value="${af.map.order_value}"></c:out></td>
      </tr>
      <tr id="xz_hidden_tbody" >
        <td colspan="2"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
            <tr>
              <th width="30%">子属性名称</th>
              <th width="40%">子属性显示名称</th>
              <th width="30%">排序值</th>
            </tr>
                <c:forEach var="cur" items="${sonList}" varStatus="vs">
                  <tr>
                    <td align="center" nowrap="nowrap"><c:out value="${cur.attr_name}"></c:out></td>
                    <td align="center" nowrap="nowrap"><c:out value="${cur.attr_show_name}"></c:out></td>
                    <td align="center" nowrap="nowrap"><c:out value="${cur.order_value }"></c:out></td>
                  </tr>
                </c:forEach>
          </table></td>
      </tr>
      <tr id="bangding_tbody" >
        <td colspan="2"><table width="100%" border="0"  cellpadding="0" cellspacing="0"  align="left"  class="tableClass">
			<c:if test="${empty pdClassList}">
            	<tr>
              		<th colspan="2" align="center"><font color="red">没有任何类别绑定此属性</font></th>
            	</tr>	
            </c:if>
            <c:if test="${not empty pdClassList}">
	            <tr>
	              <th align="center" nowrap="nowrap" width="30%">序号</th>
	              <th align="center" nowrap="nowrap" width="40%">已绑定此属性的类别名称</th>
	              <th align="center" nowrap="nowrap" width="30%">查看</th>
	            </tr>
                <c:forEach var="cur" items="${pdClassList}" varStatus="vs">
                  <tr>
                    <td align="center" nowrap="nowrap"><c:out value="${vs.count}"></c:out></td>
                    <td align="center" nowrap="nowrap"><c:out value="${cur.map.par_cls_name}"></c:out><span style="color: #0066FF;font-weight: bold;">【${fn:escapeXml(cur.map.cls_name)}】</span></td>
                    <td align="center" nowrap="nowrap">
						<a href="BasePdClass.do?method=linkAttribute&cls_id=${cur.cls_id }&pd_class_type=${cur.map.pd_class_type }&mod_id=${cur.map.mod_id}"><font color="green">查看绑定此类别的所有属性</font></a>                    	
                    </td>
                  </tr>
                </c:forEach>
            </c:if>
          </table></td>
      </tr>
      <tr>
        <td colspan="2" align="center">
          <html-el:button property="" value="返 回" styleClass="bgButton" styleId="btn_back" onclick="history.back();" /></td>
      </tr>
      <tr id= "tr_img_preview" style="display:none">
        <td colspan="2" id="img_preview" ></td>
      </tr>
    </table>

</div>
<script type="text/javascript" src="${ctx}/commons/scripts/validator.js"></script>

<script type="text/javascript" src="${ctx}/scripts/jquery-ui/external/jquery.bgiframe.js"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-ui/ui/minified/jquery-ui.custom.min.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	
});
//]]></script>
<jsp:include page="../public_page.jsp" flush="true"/>
</body>
</html>