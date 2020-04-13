<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择类别</title>
<base target="_self" />
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/commons/scripts/jqztree/styles/customer/zTreeStyle.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="pageClass">
  <ul id="treePd" class="tree">
  </ul>
</div>
 
<script type="text/javascript" src="${ctx}/commons/scripts/jqztree/ztree.min.js"></script> 
<script type="text/javascript">//<![CDATA[
var zTreeObj;
$(document).ready(function(){
	var setting = {
		isSimpleData: true,
		checkable : true,
		checkStyle: "radio",
		checkRadioType: "all",
		treeNodeKey: "cls_id",
		treeNodeParentKey: "par_id",
		showLine: true,
		//addDiyDom: addDiyDom,
		callback: {change:	zTreeOnChange},
		root:{ isRoot:true,nodes:[]}
	};
	zNodes =${clazzTree};
	zTreeObj = $("#treePd").zTree(setting, zNodes);
	zTreeObj.expandAll(false);
	
	<c:if test="${not empty af.map.hideParentNode}">
	var nodes = zTreeObj.getNodes();
	for(var i = 0; i < nodes.length; i++){
		if(nodes[i].isParent){
			nodes[i].nocheck  = true;
			zTreeObj.updateNode(nodes[i],true);
		}
	}
	</c:if>
});

function zTreeOnChange() {
	var checkedNodes = zTreeObj.getCheckedNodes();
	var cls_name;
	var cls_id;
	var cls_level;
	
    $.map(checkedNodes, function(treeObj){
    	cls_id = treeObj.cls_id;
   		cls_name = treeObj.name;
   		cls_level = treeObj.cls_level;
    })
	var api = frameElement.api, W = api.opener;
    <c:if test="${not empty af.map.isPd}" var="isPd">
	 if("" != cls_id && null != cls_id && "" != cls_name && null != cls_name){
		 <c:if test="${(not empty af.map.entp_chanage_class) and (af.map.entp_chanage_class eq 1)}">
			 W.document.getElementById("hy_cls_id").value = cls_id;
			 W.document.getElementById("hy_cls_name").value = cls_name;
		 </c:if>
		 <c:if test="${(not empty af.map.main_pd_class_id) and (af.map.main_pd_class_id eq 1)}">
			 W.document.getElementById("main_pd_class_id").value = cls_id;
			 W.document.getElementById("main_pd_class_name").value = cls_name;
		 </c:if>
		 <c:if test="${(not empty af.map.entp_chanage_class) and (af.map.entp_chanage_class eq 2)}">
		 	W.document.getElementById("xianxia_cls_id").value = cls_id;
		 	W.document.getElementById("xianxia_cls_name").value = cls_name;
 		 </c:if>
 		 
 		<c:if test="${(empty af.map.entp_chanage_class) and (empty af.map.main_pd_class_id)}">
			 W.document.getElementById("cls_id").value = cls_id;
			 W.document.getElementById("cls_name").value = cls_name;
			 <c:if test="${not empty af.map.isLevel}">
			 W.document.getElementById("cls_level").value = cls_level;
			</c:if>			 
		 </c:if>
		 <c:if test="${not empty af.map.getPdno}">
			$.ajax({
				type: "POST",
				url: "BasePdClass.do?method=getRootClsInfo",
				data: "cls_id=" + cls_id + "&cls_scope=${af.map.cls_scope}",
				dataType: "json",
				error: function(request, settings) {alert("数据查询错误！");},
				success: function(data) {
					W.document.getElementById("par_cls_id").value = data.root_id;
					<c:if test="${empty af.map.cls_scope}">
					W.document.getElementById("pd_no").value = data.pd_no;
					</c:if>
					<c:if test="${af.map.cls_scope eq 6}">
					W.document.getElementById("comm_no").value = data.pd_no;
					</c:if>
					<c:if test="${af.map.cls_scope eq 1}">
					W.CheckClsId();
					</c:if>
					api.close();
				}
			});
		  </c:if>
		  <c:if test="${empty af.map.getPdno}">
		  	api.close();
		  </c:if>
		}
    </c:if>
    
    <c:if test="${not isPd}">
   
    W.document.getElementById("par_id").value = cls_id;
	W.document.getElementById("par_name").value = cls_name;
	W.document.getElementById("cls_level").value = Number(cls_level)+1;
	api.close();
    </c:if>
  

}

function returnBillSn(val){

}

function addDiyDom(treeId, zTreeNode) {

	if(zTreeNode.isParent){
		alert(zTreeNode.name);
		zTreeNode.name = "testName";
		zTreeObj.updateNode(zTreeNode, true);
	}

}

//]]></script>
</body>
</html>
