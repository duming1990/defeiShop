<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>请选择类别</title>
<base target="_self" />
<link href="${ctx}/commons/styles/green/base.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/commons/scripts/jqztree/styles/customer/zTreeStyle.css" rel="stylesheet" type="text/css" />
</head>
<body>

<div class="pageClass">
  <ul id="treePd" class="tree">
  </ul>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
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
	// zTreeObj.expandAll(true);
	
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
	var cls_name = "";
	var cls_id = "";
	
    $.map(checkedNodes, function(treeObj){
    	cls_id = treeObj.cls_id;
   		cls_name = treeObj.name;
   		cls_level = treeObj.cls_level;
    })
	var api = frameElement.api, W = api.opener;

	 if("" != cls_id && null != cls_id && "" != cls_name && null != cls_name) {
		 if ("${af.map.s_type}" == "comm") {//商品选择
			 
			$.post("${ctx}/CsAjax.do?method=getCommNo",{cls_id: cls_id},function(data){
				if(null != data){
					W.document.getElementById("comm_no").value = data.comm_no;	
					 W.document.getElementById("cls_id").value = cls_id;
					 W.document.getElementById("cls_name").value = cls_name;
					 api.close();
				}	
			});
		 
		 } else {
			 
// 			 if ("${not empty af.map.queryAttr}") {//查询下面的子属性
// 				 W.queryAttr(cls_id);
// 			 }
			 W.document.getElementById("cls_id").value = cls_id;
			 W.document.getElementById("cls_name").value = cls_name;
			 api.close();
		 }
	}
  
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
