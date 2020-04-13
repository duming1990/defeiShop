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
  <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
    <tr>
      <th colspan="2">站内通知</th>
    </tr>
    <tr>
      <td class="title_item" width="20%">发送人：</td>
      <td>${send_user_name}</td>
    </tr>
    <tr>
      <td class="title_item" width="20%">信息主题：</td>
      <td>${fn:escapeXml(af.map.msg_title)}</td>
    </tr>
    <tr>
      <td class="title_item">信息内容：</td>
      <td>${af.map.msg_content}</td>
    </tr>
    <tr>
      <td class="title_item">发送时间：</td>
      <td><fmt:formatDate value="${af.map.send_time}" pattern="yyyy-MM-dd HH:mm" /></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><c:choose>
          <c:when test="${af.map.is_closed eq 1}">
            <input type="button" name="add" id="add" class="bgButton" value="回 复" disabled="disabled"/>
          </c:when>
          <c:otherwise>
            <input type="button" name="add" id="add" class="bgButton" value="回 复" onclick="location.href='MsgReceiver.do?method=add&mod_id=${af.map.mod_id}&id=${af.map.id}&send_user_id=${af.map.user_id}&revert_id=${revert_id}&msg_id=${af.map.msg_id}';" />
          </c:otherwise>
        </c:choose>
        <input type="button" value="返 回" class="bgButton" onclick="location.href='MsgReceiver.do?method=list&mod_id=${af.map.mod_id}';" /></td>
    </tr>
    <c:if test="${not empty rMsgList}">
      <tr>
        <th colspan="2">回复列表</th>
      </tr>
      <tr>
        <td colspan="2"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
            <tr class="column_title">
              <td width="10%">回复信息</td>
              <td width="25%">回复主题</td>
              <td colspan="2">回复内容</td>
            </tr>
            <c:forEach items="${rMsgList}" var="cur">
              <tr>
                <td>${cur.map.r_user_name}Re:${send_user_name}</td>
                <td>${cur.msg_title} </td>
                <td colspan="2">${cur.msg_content}
                  <div align="right" style="color:#999">回复时间：
                    <fmt:formatDate value="${cur.send_time}" pattern="yyyy年MM月dd日 HH时mm分" />
                  </div></td>
              </tr>
              <c:forEach items="${cur.msgList}" var="cur1">
                <tr>
                  <td><span style="color:#0066FF;">${send_user_name}Re:${cur.map.r_user_name}</span></td>
                  <td>${cur1.msg_title} </td>
                  <td colspan="2">${cur1.msg_content}
                    <div align="right"  style="color:#999">回复时间：
                      <fmt:formatDate value="${cur1.send_time}" pattern="yyyy年MM月dd日 HH时mm分" />
                    </div></td>
                </tr>
              </c:forEach>
            </c:forEach>
          </table></td>
      </tr>
    </c:if>
  </table>
</div>

<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
