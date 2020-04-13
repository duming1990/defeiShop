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
      <td width="15%" class="title_item">接收人：</td>
      <td><c:if test="${af.map.is_send_all eq 1}" var="isall">全部用户</c:if>
        <c:if test="${not isall}">
          <c:forEach var="cur" items="${mrList}"> [${cur.receiver_user_mobile}] </c:forEach>
        </c:if></td>
    </tr>
    <tr>
      <td width="15%" class="title_item">信息主题：</td>
      <td>${af.map.msg_title}</td>
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
      <td class="title_item">信息状态：</td>
      <td><c:choose>
          <c:when test="${af.map.info_state eq 0}"> 草稿 </c:when>
          <c:when test="${af.map.info_state eq 1}"> 已经发送 </c:when>
          <c:otherwise> 已经删除 </c:otherwise>
        </c:choose></td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td><input type="button" class="bgButton" value="返回" onclick="history.back();" /></td>
    </tr>
    <c:if test="${not empty rMsgList}">
      <tr>
        <th colspan="2">回复列表</th>
      </tr>
      <tr>
        <td colspan="2"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
            <tr>
              <th width="20%">回复人</th>
              <th width="25%">回复主题</th>
              <th>回复内容</th>
              <th width="5%">操作</th>
            </tr>
            <c:forEach items="${rMsgList}" var="cur">
              <tr>
                <td>${cur.map.r_user_name}</td>
                <td>${cur.msg_title} </td>
                <td>${cur.msg_content}
                  <div align="right" style="color:#999">回复时间：
                    <fmt:formatDate value="${cur.send_time}" pattern="yyyy年MM月dd日 HH时mm分" />
                  </div></td>
                <td align="center"><input type="button" name="add" id="add" class="bgButton" value="回复" onclick="location.href='Msg.do?method=apply&mod_id=${af.map.mod_id}&send_user_id=${cur.user_id}&msg_id=${cur.id}';" /></td>
              </tr>
              <c:forEach items="${cur.msgList}" var="cur1">
                <tr>
                  <td><span style="color:#0066FF;">我回复给${cur.map.r_user_name}:</span></td>
                  <td>${cur1.msg_title} </td>
                  <td colspan="2">${cur1.msg_content}
                    <div align="right" style="color:#999">回复时间：
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

</body>
</html>
