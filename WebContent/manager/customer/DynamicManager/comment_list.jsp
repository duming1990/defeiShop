<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>评论信息</title>
</head>
<body>
<c:if test="${empty entityList}"><div>暂无评论~</div></c:if>
<c:forEach items="${entityList}" var="cur">
	<div style="margin-bottom: 15px;padding: 10px;border: 5px solid #dedede;">
	<span style="margin-left: 20px;color: blue;">${cur.add_user_name}</span>
	<c:if test="${cur.comment_type eq 2}">
		<span style="font-size: 13px;">回复</span>
		<span style="color: blue;">${cur.link_user_name}</span>
	</c:if>:
	<span style="font-size: 13px;font-family: serif;">${cur.content}</span></br>
	<button onclick="shanchu(${cur.id})" style="color: mediumvioletred;cursor: pointer;float: right;border: 5px solid #dedede;border-radius:15px;">删除</button>
	</div>
</c:forEach>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript">
function shanchu(id) {
	if (confirm("确认要删除该评论吗？")) {
		$.post("${ctx}/manager/customer/DynamicManager.do?method=deleteComment",{id : id},function(data){
			if(data.ret==1){
				alert(data.msg);
				var api = frameElement.api, W = api.opener;
				W.refreshPage();
				api.close();
				
			} else {
				alert("删除失败！");
			}
		});
	}
	return false;
}
</script>
</body>
</html>