<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="../../commons/pages/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="在线照片制作软件_照片处理软件_照片编辑器_图片处理 在线图片裁切,大小调整,旋转,滤镜(非主流效果)" />
<title>在线ps_Photoshop在线精简版 - ${app_name}</title>
<link rel="stylesheet" href="${ctx}/commons/bootstrap/default/bootstrap.min.css" media="screen">
<link rel="stylesheet" href="${ctx}/commons/bootstrap/my.css">
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="${ctx}/commons/bootstrap/js/html5shiv.js"></script>
      <script src="${ctx}/commons/bootstrap/js/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script> 
<script type="text/javascript" src="${ctx}/commons/bootstrap/js/bootstrap.min.js"></script>
</head>
<c:set var="isAdmin" value="false" />
<c:set var="bodyPadding" value="" />
<c:if test="${not empty af.map.isAdmin}">
  <c:set var="bodyPadding" value="padding-top: 0px;" />
  <c:set var="isAdmin" value="true" />
</c:if>
<body style="${bodyPadding}">
<c:if test="${not isAdmin}">
<%--   <jsp:include page="../../_header.jsp" flush="true" /> --%>
</c:if>
<div class="container-fluid content-bg">
  <div class="content-container">
    <div class="content col-xs-12 col-sm-12">
      <div class="content-wrapper">
        <div class="tabbable" id="tabs-91053"><!-- Only required for left/right tabs -->
          <ul class="nav nav-tabs">
            <li class="active"><a data-toggle="tab" href="#panel_1">在线PS(简约版)</a></li>
            <li><a data-toggle="tab" href="#panel_2">在线PS(标准版)</a></li>
            <li><a data-toggle="tab" href="#panel_3">在线PS(高手版)</a></li>
            <li><a data-toggle="tab" href="#panel_4">在线PS(GIF版)</a></li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane active" id="panel_1">
              <div class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" src="http://www.webps.cn/express.html"></iframe>
              </div>
              <p class="text-center" style="margin: 5px"><a href="http://www.webps.cn/express.html" target="_blank" class="btn btn-primary">在新窗口打开</a></p>
            </div>
            <div class="tab-pane" id="panel_2">
              <div class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" src="http://www.webps.cn/ps.html"></iframe>
              </div>
              <p class="text-center" style="margin: 5px"><a href="http://www.webps.cn/ps.html" target="_blank" class="btn btn-primary">在新窗口打开</a></p>
            </div>
            <div class="tab-pane" id="panel_3">
              <div class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" src="http://www.webps.cn/photo.html"></iframe>
              </div>
              <p class="text-center" style="margin: 5px"><a href="http://www.webps.cn/photo.html" target="_blank" class="btn btn-primary">在新窗口打开</a></p>
            </div>
            <div class="tab-pane" id="panel_4">
              <div class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" src="http://www.webps.cn/gif.html"></iframe>
              </div>
              <p class="text-center" style="margin: 5px"><a href="http://www.webps.cn/gif.html" target="_blank" class="btn btn-primary">在新窗口打开</a></p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</body>
</html>