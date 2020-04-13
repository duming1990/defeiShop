<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!doctype html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>红包-${app_name}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <script src="${ctx}/styles/hongbao/js/mathlib-min.js"></script>
    <script src="${ctx}/styles/hongbao/js/k3d-min.js"></script>
    <script src="${ctx}/styles/hongbao/js/radiation.js"></script>
	<link rel="stylesheet" href="${ctx}/styles/hongbao/css/hongbao.css">
</head>
<body style="background-color: rgba(0,0,0,0);overflow:hidden;">
<canvas id="canvas" style="position: relative; background-color: #fed261;"></canvas>
    <div class="container" id="container">
        <div class="RedBox">
            <div class="topcontent">
                <h2 class="bounceInDown">${app_name}红利</h2>
                <span class="text flash"><b><fmt:formatNumber value="${sum}" pattern="#0.00" type="number"/></b>元</span>
                <div class="avatar">
                    <div id="open" style="width:80px;height:80px;"></div>
                </div>
                <div class="description1 flipInX" onclick="location.href='${ctx}/m/MMyQianBao.do?method=walletList';">查看返利记录</div>
				<div class="description1 flipInX" onclick="location.href='${ctx}/m/index.do?method=xiugaiSysOperLog';">不再显示</div>
				<div class="description1 flipInX" onclick="history.back();">返回</div>
            </div>
        </div>
    </div>
    <script>
        var oOpen = document.getElementById("open");
        var oClose = document.getElementById("open");
        var oContainer = document.getElementById("container");

        oChai.onclick = function (){
            oChai.setAttribute("class", "rotate");
        };
    </script>
</body>
</html>