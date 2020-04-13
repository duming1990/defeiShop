<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
      <th colspan="2">推送基本信息</th>
    </tr>
    <c:if test="${af.map.title_is_strong eq 1}" var="is_strong">
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">标题：</td>
        <td width="88%"><span style="color:${af.map.title_color}; font-weight:bold;">
          <c:out value="${af.map.title}" />
          </span></td>
      </tr>
    </c:if>
    <c:if test="${not is_strong}">
      <tr>
        <td width="12%" nowrap="nowrap" class="title_item">标题：</td>
        <td width="88%"><span style="color:${af.map.title_color}">
          <c:out value="${af.map.title}" />
          </span></td>
      </tr>
    </c:if>
    <!--  
      <c:if test="${(af.map.is_use_direct_uri eq 0)}">
        <tr>
          <td nowrap="nowrap" class="title_item">标题直接URI：</td>
          <td><c:out value="${af.map.direct_uri}" /></td>
        </tr>
      </c:if>
      <tr>
        <td nowrap="nowrap" class="title_item">关键字：</td>
        <td><c:out value="${af.map.keyword}" /></td>
      </tr>
    
    <tr>
      <td nowrap="nowrap" class="title_item">所属语言：</td>
      <td><c:choose>
          <c:when test="${af.map.locale_name eq 'zh_CN'}"> 中文简体</c:when>
          <c:when test="${af.map.locale_name eq 'zh_TW'}">中文繁体</c:when>
          <c:when test="${af.map.locale_name eq 'en'}"> 英文</c:when>
        </c:choose></td>
    </tr>
      -->
    <tr>
      <td nowrap="nowrap" class="title_item">短标题：</td>
      <td><c:out value="${af.map.title_short}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">副标题：</td>
      <td><c:out value="${af.map.title_sub}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">作者：</td>
      <td><c:out value="${af.map.author}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">信息来源：</td>
      <td><c:out value="${af.map.info_source}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">所属区域：</td>
      <td><c:out value="${af.map.map.full_name}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">摘要：</td>
      <td>${af.map.summary}</td>
    </tr>
    <c:if test="${af.map.mod_id ne '1003002000'}">
      <tr>
        <td nowrap="nowrap" class="title_item">资讯类型：</td>
        <td>${af.map.map.type_name}</td>
      </tr>
    </c:if>
    <tr>
      <td nowrap="nowrap" class="title_item">内容：</td>
      <td>${af.map.content}</td>
    </tr>
    <c:if test="${not empty (af.map.image_path)}">
      <tr>
        <td nowrap="nowrap" class="title_item">推送主图：</td>
        <td><img src="${ctx}/${fn:substringBefore(af.map.image_path, '.')}_240.${fn:substringAfter(af.map.image_path, '.')}" title="${fn:escapeXml(af.map.image_desc)}" /></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">主图说明：</td>
        <td><c:out value="${af.map.image_desc}" /></td>
      </tr>
    </c:if>
    <c:if test="${not empty (af.map.video_path)}">
      <tr>
        <td nowrap="nowrap" class="title_item">视频地址：</td>
        <td><div align="left" style="margin-top:10px; margin-bottom:10px;">
            <script type="text/javascript" src="${ctx}/commons/scripts/mediaPlayer.js"></script>
            <script type="text/javascript" src="${ctx}/scripts/flash/AC_RunActiveContent.js"></script>
            <script type="text/javascript">//<![CDATA[
				  <c:if test="${fn:substringAfter(af.map.video_path, '.') eq 'flv'}" var="is_flv">
				  playFlv("${ctx}/${af.map.video_path}", "${ctx}/${af.map.image_path}", "400", "300", "${ctx}/scripts/flash/jcplayer");
				  </c:if>
				  <c:if test="${not is_flv}">
				  var mediaPlayerFactory = new MediaPlayerFactory(null, 400, 300, "${ctx}/${af.map.video_path}");
				  mediaPlayerFactory.init();
				  document.write(mediaPlayerFactory.getMediaPlayer(2)); 
				  </c:if>
				 //]]></script>
          </div></td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="title_item">视频说明：</td>
        <td><c:out value="${af.map.video_desc}" /></td>
      </tr>
    </c:if>
    <c:if test="${not empty attachmentList}">
      <tr>
        <td nowrap="nowrap" class="title_item">已上传的附件：</td>
        <td><c:forEach var="cur" items="${attachmentList}" varStatus="vs">${vs.count}、<a href="${ctx}/${cur.save_path}">${cur.file_name}</a><br />
          </c:forEach></td>
      </tr>
    </c:if>
    <tr>
      <td nowrap="nowrap" class="title_item">发布时间：</td>
      <td><fmt:formatDate value="${af.map.pub_time}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item" nowrap="nowrap">
      是否使用失效时间：
      </td>
      <td><c:choose>
          <c:when test="${af.map.is_use_invalid_date eq 0}"> 不使用</c:when>
          <c:when test="${af.map.is_use_invalid_date eq 1}"> 使用</c:when>
        </c:choose>
      </td>
    </tr>
    <c:if test="${(af.map.is_use_invalid_date eq 1) and (not empty (af.map.invalid_date))}">
      <tr>
        <td nowrap="nowrap" class="title_item">失效时间：</td>
        <td><fmt:formatDate value="${af.map.invalid_date}" pattern="yyyy-MM-dd"/></td>
      </tr>
    </c:if>
    <tr>
      <td nowrap="nowrap" class="title_item">排序值：</td>
      <td><c:out value="${af.map.order_value}" /></td>
    </tr>
    <tr>
      <td nowrap="nowrap" class="title_item">信息状态：</td>
      <td><c:choose>
          <c:when test="${af.map.info_state eq 0}"> 默认未审核 </c:when>
          <c:when test="${af.map.info_state eq 1}"> 区县级已审核 </c:when>
          <c:when test="${af.map.info_state eq 2}"> 市级已审核 </c:when>
          <c:when test="${af.map.info_state eq 3}"> 省级已审核 </c:when>
          <c:when test="${af.map.info_state eq 4}"> 商务部已审核 </c:when>
          <c:when test="${af.map.info_state eq 5}"> 已发布 </c:when>
          <c:when test="${af.map.info_state eq -1}"> 区县级未审核 </c:when>
          <c:when test="${af.map.info_state eq -2}"> 市级未审核 </c:when>
          <c:when test="${af.map.info_state eq -3}"> 省级未审核 </c:when>
          <c:when test="${af.map.info_state eq -4}"> 商务部未审核 </c:when>
          <c:otherwise> 不发布 </c:otherwise>
        </c:choose></td>
    </tr>
    <tr>
      <td colspan="2" align="center"><input type="button" value="返 回" class="bgButton" onclick="history.back();" /></td>
    </tr>
  </table>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<jsp:include page="../public_page.jsp" flush="true" />
</body>
</html>
