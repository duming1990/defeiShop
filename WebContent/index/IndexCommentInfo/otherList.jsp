<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="description" content="${fn:escapeXml(fnx:abbreviate(entpInfo.entp_desc, 2 * 50, ''))}" />
<meta name="keywords" content="${fn:escapeXml(entpInfo.entp_name)}" />
<title>${fn:escapeXml(entpInfo.entp_name)} - ${app_name}</title>
<link href="${ctx}/styles/indexv3/css/top.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/indexv3/css/fonts.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/homev2/css/entp.css" rel="stylesheet" type="text/css"  />
</head>
<body>
<jsp:include page="../../public/index/_index_comment.jsp" flush="true" />

<div class="tabbox" id="div_pj" style="margin-top: 10px;">
  <ul class="tabbtn">
   <c:url var="url" value="/IndexCommentInfo.do?method=otherList&link_f_id=${af.map.link_f_id}&commentCount=${af.map.commentCount}&commentLevel1Count=${af.map.commentLevel1Count}&commentLevel2Count=${af.map.commentLevel2Count}&commentLevel3Count=${af.map.commentLevel3Count}" />
    <li id="pjli" onclick="location.href='${url}'"><span>全部评论
    <c:if test="${not empty commentCount}">(${commentCount})</c:if>
    </span></li>
     <c:url var="url" value="/IndexCommentInfo.do?method=otherList&link_f_id=${af.map.link_f_id}&comm_level=1&commentCount=${af.map.commentCount}&commentLevel1Count=${af.map.commentLevel1Count}&commentLevel2Count=${af.map.commentLevel2Count}&commentLevel3Count=${af.map.commentLevel3Count}" />
    <li id="pjli1" onclick="location.href='${url}'"><span>好评
    <c:if test="${not empty commentLevel1Count}">(${commentLevel1Count})</c:if></span></li>
    <c:url var="url" value="/IndexCommentInfo.do?method=otherList&link_f_id=${af.map.link_f_id}&comm_level=2&commentCount=${af.map.commentCount}&commentLevel1Count=${af.map.commentLevel1Count}&commentLevel2Count=${af.map.commentLevel2Count}&commentLevel3Count=${af.map.commentLevel3Count}" />
    <li id="pjli2" onclick="location.href='${url}'"><span>中评
    <c:if test="${not empty commentLevel2Count}">(${commentLevel2Count})</c:if></span></li>
    <c:url var="url" value="/IndexCommentInfo.do?method=otherList&link_f_id=${af.map.link_f_id}&comm_level=3&commentCount=${af.map.commentCount}&commentLevel1Count=${af.map.commentLevel1Count}&commentLevel2Count=${af.map.commentLevel2Count}&commentLevel3Count=${af.map.commentLevel3Count}" />
    <li id="pjli3" onclick="location.href='${url}'"><span>差评
    <c:if test="${not empty commentLevel3Count}">(${commentLevel3Count})</c:if></span></li>
  </ul>
  <div class="tabcon" id="pjcontent1">
    <c:forEach items="${commentInfoList}" var="cur">
      <div class="item">
        <div class="user">
          <div class="u-icon"> <img height="50" width="50" src="${ctx}/styles/images/userlevel0.gif" alt="jd_271569334"/></div>
          <div class="u-name"> <a href="javascript:void(0);">${fn:escapeXml(cur.map.secretString)}</a> </div>
          </div>
        <div class="i-item">
          <div class="comment-content">
            <dl>
              <dt>商品优点：</dt>
              <dd> ${fn:escapeXml(cur.comm_good)}</dd>
            </dl>
            <dl>
              <dt>商品不足：</dt>
              <dd> ${fn:escapeXml(cur.comm_bad)}</dd>
            </dl>
            <dl>
              <dt>购买心得：</dt>
              <dd> ${fn:escapeXml(cur.comm_experience)}</dd>
            </dl>
            <div class="dl-extra"> </div>
            <s class="clr"></s>
            <dl>
              <dt>评论日期：</dt>
              <dd>
                <fmt:formatDate pattern="yyyy-MM-dd" value="${cur.comm_time}" />
              </dd>
            </dl>
          </div>
        </div>
      </div>
    </c:forEach>
    <c:if test="${empty commentInfoList}">
      <div style="padding: 20px">暂无评论。</div>
    </c:if>
     <div class="tabcon" id="pjcontent2" style="display: none;">
    <c:forEach items="${commentInfoList}" var="cur">
      <div class="item">
        <div class="user">
          <div class="u-icon"> <img height="50" width="50" src="${ctx}/styles/images/userlevel0.gif" alt="jd_271569334"/></div>
          <div class="u-name"> <a href="javascript:void(0);">${fn:escapeXml(cur.map.secretString)}</a> </div>
          <span class="u-level"><span style="color:"> 普通会员</span></span> </div>
        <div class="i-item">
          <div class="o-topic"> <span class="star sa${cur.comm_score}"></span><span><a class="date-comment" href="javascript:void(0);">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${cur.comm_time}" />
            </a><em class="fr hl_blue"></em></span> </div>
          <div class="comment-content">
            <dl>
              <dt>优点：</dt>
              <dd> ${fn:escapeXml(cur.comm_good)}</dd>
            </dl>
            <dl>
              <dt>不足：</dt>
              <dd> ${fn:escapeXml(cur.comm_bad)}</dd>
            </dl>
            <dl>
              <dt>心得：</dt>
              <dd> ${fn:escapeXml(cur.comm_experience)}</dd>
            </dl>
            <div class="dl-extra"> </div>
            <s class="clr"></s>
            <dl>
              <dt>购买日期：</dt>
              <dd>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${cur.buy_date}" />
              </dd>
            </dl>
          </div>
        </div>
      </div>
    </c:forEach>
    <c:if test="${empty commentInfoList}">
      <div style="padding: 20px">暂无评论。</div>
    </c:if>
  </div>
  <div class="tabcon" id="pjcontent3" style="display: none;">
    <c:forEach items="${commentInfoList}" var="cur">
      <div class="item">
        <div class="user">
          <div class="u-icon"> <img height="50" width="50" src="${ctx}/styles/images/userlevel0.gif" alt="jd_271569334"/></div>
          <div class="u-name"> <a href="javascript:void(0);">${fn:escapeXml(cur.map.secretString)}</a> </div>
          <span class="u-level"><span style="color:"> 普通会员</span></span> </div>
        <div class="i-item">
          <div class="o-topic"> <span class="star sa${cur.comm_score}"></span><span><a class="date-comment" href="javascript:void(0);">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${cur.comm_time}" />
            </a><em class="fr hl_blue"></em></span> </div>
          <div class="comment-content">
            <dl>
              <dt>优点：</dt>
              <dd> ${fn:escapeXml(cur.comm_good)}</dd>
            </dl>
            <dl>
              <dt>不足：</dt>
              <dd> ${fn:escapeXml(cur.comm_bad)}</dd>
            </dl>
            <dl>
              <dt>心得：</dt>
              <dd> ${fn:escapeXml(cur.comm_experience)}</dd>
            </dl>
            <div class="dl-extra"> </div>
            <s class="clr"></s>
            <dl>
              <dt>购买日期：</dt>
              <dd>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${cur.buy_date}" />
              </dd>
            </dl>
          </div>
        </div>
      </div>
      <c:if test="${empty commentInfoList}">
        <div style="padding: 20px">暂无评论。</div>
      </c:if>
    </c:forEach>
  </div>
  <div class="tabcon" id="pjcontent4" style="display: none;">
    <c:forEach items="${commentInfoList}" var="cur">
      <div class="item">
        <div class="user">
          <div class="u-icon"> <img height="50" width="50" src="${ctx}/styles/images/userlevel0.gif" alt="jd_271569334"/></div>
          <div class="u-name"> <a href="javascript:void(0);">${fn:escapeXml(cur.map.secretString)}</a> </div>
          <span class="u-level"><span style="color:"> 普通会员</span></span> </div>
        <div class="i-item">
          <div class="o-topic"> <span class="star sa${cur.comm_score}"></span><span><a class="date-comment" href="javascript:void(0);">
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${cur.comm_time}" />
            </a><em class="fr hl_blue"></em></span> </div>
          <div class="comment-content">
            <dl>
              <dt>优点：</dt>
              <dd> ${fn:escapeXml(cur.comm_good)}</dd>
            </dl>
            <dl>
              <dt>不足：</dt>
              <dd> ${fn:escapeXml(cur.comm_bad)}</dd>
            </dl>
            <dl>
              <dt>心得：</dt>
              <dd> ${fn:escapeXml(cur.comm_experience)}</dd>
            </dl>
            <div class="dl-extra"> </div>
            <s class="clr"></s>
            <dl>
              <dt>购买日期：</dt>
              <dd>
                <fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${cur.buy_date}" />
              </dd>
            </dl>
          </div>
        </div>
      </div>
    </c:forEach>
    <c:if test="${empty commentInfoList}">
      <div style="padding: 20px">暂无评论。</div>
    </c:if>
  </div>
  </div>
  <div class="pageClass" style="margin-top: 5px;">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="IndexCommentInfo.do">
      <table width="98%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="10" align="right"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "otherList");
            pager.addHiddenInputs("link_id", "${af.map.link_id}");
            pager.addHiddenInputs("comm_level", "${af.map.comm_level}");
            pager.addHiddenInputs("commentCount", "${af.map.commentCount}");
            pager.addHiddenInputs("link_f_id", "${af.map.link_f_id}");
            document.write(pager.toString());
            </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/scripts/tabs/tabs.switch.min.js"></script> 
<script type="text/javascript">//<![CDATA[
 $(document).ready(function(){
	 $("#pjli${af.map.comm_level}").addClass("current");
 });  
	                                 

//]]>
</script>
</body>
</html>
