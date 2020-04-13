<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base target="_self" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <jsp:include page="../_public_head_back.jsp" flush="true" />
    <jsp:include page="../../public_page_in_head.jsp" flush="true" />
    <title>选择测试用户</title>
    <style type="text/css">
        body {
            background:#F8F8F8;
            font-family:"微软雅黑", "宋体";
        }
        fieldset {
            border:1px solid #CCC/*A3C0E8*/;
            text-align:left;
            margin:0 auto;
            margin:5px;
            border-radius:2px;
            background:#FFF;
        }
        fieldset a {
            height:30px;
            line-height:30px;
            background:#009900;
            color:#FFF;
            margin:2px 5px;
            padding:2px 8px;
            border-radius: 5px;
        }
        fieldset a:hover {
            background:#00B800;
            text-decoration:none;
            color:#FFF;/*color:#FFF0AC;*/
        }
        fieldset a:visited {
            color:#FFF;
            text-decoration:none;
        }
        legend {
            font-weight: bold;
        }
        .dataContent {
            width:99%;
            overflow:hidden;/*border:1px solid #A3C0E8;*/
            margin:5px auto;
            overflow:auto;/*height:320px;*/
        }
        .ul_selpd {
            padding-left:30px;
        }
        .ul_selpd li {/*width:210px;*/
            width:20%;
            line-height:18px;
            overflow:hidden;
            float:left;
            padding:5px;
            margin:5px;
            text-align:center;
        }
        .ul_selpd li b {
            float:right;
            font-weight:normal;
            padding-left:5px;
            font-size:12px;
        }
        .dataContent input,label{
            cursor:pointer;
        }
        .cked {
            background:#FFF0AC;/*#F1F8FF;*/
            border-radius: 5px;
        }
        .cked label {
            color:#FF5809;
            text-decoration:underline;/*#F1F8FF;*/
        }
        .fdul li{float:left;}
    </style>
</head>
<body>
<div style="background:#FFFFFF;">&nbsp;</div>
<div align="center" style="height:490px;overflow:auto;">
    <fieldset>
        <legend>已选&nbsp;<span id="user_count">${af.map.user_count}</span>人</legend>
        <div id="chZj" style="height:80px;overflow:auto;">
            <ul id="fdul" class="fdul">
                <c:forEach items="${userInfo2List}" var="cus">
                    <li><a href="javascript:void(0);" id="a_${cus.id}">${cus.user_name}</a></li>
                </c:forEach>
            </ul>
        </div>
    </fieldset>
    <div>
        <html-el:form action="/admin/ClickFarming.do">
            <html-el:hidden property="method" value="listUser" />
            <html-el:hidden property="link_id" styleId="link_id"/>
            <html-el:hidden property="link_gz_id" styleId="link_gz_id"/>
            <html-el:hidden property="user_ids" styleId="user_ids"/>
            <html-el:hidden property="user_names" styleId="user_names"/>
            <html-el:hidden property="user_count" styleId="user_count"/>
            <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" class="tableClass">
                <tr>
                    <td align="left" nowrap="nowrap">用户名称：
                        <html-el:text property="user_name_like" styleClass="webinput" size="40" />
                        &nbsp;&nbsp; <a class="butbase" id="btn_search" style="cursor:pointer;"/><span class="icon-find">查询</span></a></td>
                </tr>
            </table>
        </html-el:form>
    </div>
    <form id="listForm" name="listForm" method="post">
        <div>
            <div>
                <ul class="ul_selpd">
                    <li style="width:100%;text-align:left;"><input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAllUser(this);"/><label>全选</label></li>
                    <c:forEach var="cur" items="${entityList}" varStatus="vs1">
                        <li title="${cur.user_name}">
                            <input name="pks" type="checkbox" id="pks_${cur.id}" value="${cur.id}+${cur.user_name}" onclick="checkWk(this)"/>
                            <label for="pks_${cur.id}">
                                <span style="font-size:14px;">${fn:escapeXml(cur.user_name)}</span><br/>
                            </label>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </form>
    <div class="clear"></div>
    <div class="pageClass">
        <form id="bottomPageForm" name="bottomPageForm" method="post" action="ClickFarming.do">
            <table width="98%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
                        <script type="text/javascript">
                            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
                            pager.addHiddenInputs("method", "listUser");
                            pager.addHiddenInputs("user_name_like", "${fn:escapeXml(af.map.user_name_like)}");
                            pager.addHiddenInputs("link_id", "${fn:escapeXml(af.map.link_id)}");
                            pager.addHiddenInputs("link_gz_id", "${fn:escapeXml(af.map.link_gz_id)}");
                            pager.addHiddenInputs("user_ids", "${fn:escapeXml(af.map.user_ids)}");
                            pager.addHiddenInputs("user_names", "${fn:escapeXml(af.map.user_names)}");
                            pager.addHiddenInputs("user_count", "${fn:escapeXml(af.map.user_count)}");
                            document.write(pager.toString());
                        </script></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div class="clear"></div>
<c:if test="${empty has_fs_over}">
    <div style="height:30px;width:100%;text-align:right;padding-top:10px;background:#EEEEEE;border-top:1px solid #CCCCCC;">
        <a class="butbase" href="#" onclick="returnVal();" ><span class="icon-ok">确定</span></a>
        &nbsp; <a class="butbase" href="#" onclick="gb()" >
        <span class="icon-remove">关闭</span></a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </div>
</c:if>
<script type="text/javascript" src="${ctx}/commons/scripts/jquery.js"></script>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
    var f = document.forms[0];
    $("#btn_search").click(function(){
        f.submit();

    });
    $(".Tab tr").mouseover(function(){
        $(this).addClass("over");
    }).mouseout(function(){
        $(this).removeClass("over");
    })
    $(".Tab tr:even").addClass("alteven");
    $(".Tab tr:odd").addClass("altodd");

    if ("" != "${af.map.user_ids}") {
        var user_ids = "${af.map.user_ids}";
        var custIdsArray = new Array();
        custIdsArray = user_ids.split(",");
        var cks = document.getElementsByName("pks");
        for ( var i = 0; i < cks.length; i++) {
            for ( var j = 0; j < custIdsArray.length; j++) {
                if (cks[i].value.split("+")[0] == custIdsArray[j]) {
                    cks[i].checked = true;
                    $(cks[i]).parent().addClass("cked");
                }
            }
        }
    }

});

//单选
function checkWk(e) {
    var wkIdsArray = new Array();
    var wkNamesArray = new Array();
    if (e.checked) { //选中
        $(e).parent().addClass("cked");//改变样式
        /******************************************/
        if ("" != $("#user_ids").val()) {
            wkIdsArray = $("#user_ids").val().split(",");
        }
        wkIdsArray.push($(e).val().split("+")[0]);
        var ids = wkIdsArray.join(",");
        $("input[name='user_ids']").val(ids);
        /******************************************/
        if ("" != $("#user_names").val()) {
            wkNamesArray = $("#user_names").val().split(",");
        }
        wkNamesArray.push($(e).val().split("+")[1]);
        var names = wkNamesArray.join(",");
        $("input[name='user_names']").val(names);
        /******************************************/
        $("#fdul").append('<li><a href="javascript:void(0);" id="a_' + $(e).val().split("+")[0] + '">' + $(e).val().split("+")[1] + '</a></li>');
    } else { //取消选中
        $(e).parent().removeClass("cked");
        /******************************************/
        var twkArray = new Array();
        if ("" != $("#user_ids").val()) {
            wkIdsArray = $("#user_ids").val().split(",");
        }
        for ( var i = 0; i < wkIdsArray.length; i++) {
            if (wkIdsArray[i] != $(e).val().split("+")[0]) {
                twkArray.push(wkIdsArray[i]);
            }
        }
        var ids = twkArray.join(",");
        $("input[name='user_ids']").val(ids);
        /******************************************/
        var twknArray = new Array();
        if ("" != $("#user_names").val()) {
            wkNamesArray = $("#user_names").val().split(",");
        }
        for ( var i = 0; i < wkNamesArray.length; i++) {
            if (wkNamesArray[i] != $(e).val().split("+")[1]) {
                twknArray.push(wkNamesArray[i]);
            }
        }
        var names = twknArray.join(",");
        $("input[name='user_names']").val(names);
        /******************************************/
        $("a", "#fdul").each(function(){
            var aid = $(this).attr("id").split("_")[1];
            if ($(e).val().split("+")[0] == aid) {
                $(this).parent().remove();
            }
        });
    }
    var user_ids = $("input[name='user_ids']").val();
    var user_names = $("input[name='user_names']").val();
    var user_count = user_ids.split(",").length;
    $("input[name='user_count']").val(user_count);
    document.getElementById("user_count").innerHTML=user_count;
}

function checkAllUser(e) {
    var wkIdsArray = new Array();
    var wkNamesArray = new Array();
    if (e.checked) { //选中
        for (var i = e.form.elements.length - 1; i > 0; i--) {
            if (e.form.elements[i].type == "checkbox" && e.form.elements[i].disabled == false && !e.form.elements[i].checked) {
                e.form.elements[i].checked = e.checked;
                $(e.form.elements[i]).parent().addClass("cked");//改变样式
                if ("" != $("#user_ids").val()) {
                    wkIdsArray = $("#user_ids").val().split(",");
                }
                wkIdsArray.push($(e.form.elements[i]).val().split("+")[0]);
                var ids = wkIdsArray.join(",");
                $("input[name='user_ids']").val(ids);
                /******************************************/
                if ("" != $("#user_names").val()) {
                    wkNamesArray = $("#user_names").val().split(",");
                }
                wkNamesArray.push($(e.form.elements[i]).val().split("+")[1]);
                var names = wkNamesArray.join(",");
                $("input[name='user_names']").val(names);
                /******************************************/
                $("#fdul").append('<li><a href="javascript:void(0);" id="a_' + $(e.form.elements[i]).val().split("+")[0] + '">' + $(e.form.elements[i]).val().split("+")[1] + '</a></li>');
            }
        }
    }else{
        for (var i = e.form.elements.length - 1; i > 0; i--) {
            if (e.form.elements[i].type == "checkbox" && e.form.elements[i].disabled == false && e.form.elements[i].checked) {
                e.form.elements[i].checked = false;
                //取消选中
                $(e.form.elements[i]).parent().removeClass("cked");
                /******************************************/
                var twkArray = new Array();
                if ("" != $("#user_ids").val()) {
                    wkIdsArray = $("#user_ids").val().split(",");
                }
                for ( var j = 0; j < wkIdsArray.length; j++) {
                    if (wkIdsArray[j] != $(e.form.elements[i]).val().split("+")[0]) {
                        twkArray.push(wkIdsArray[j]);
                    }
                }
                var ids = twkArray.join(",");
                $("input[name='user_ids']").val(ids);
                /******************************************/
                var twknArray = new Array();
                if ("" != $("#user_names").val()) {
                    wkNamesArray = $("#user_names").val().split(",");
                }
                for ( var j = 0; j < wkNamesArray.length; j++) {
                    if (wkNamesArray[j] != $(e.form.elements[i]).val().split("+")[1]) {
                        twknArray.push(wkNamesArray[j]);
                    }
                }
                var names = twknArray.join(",");
                $("input[name='user_names']").val(names);
                /******************************************/
                $("a", "#fdul").each(function(){

                    var aid = $(this).attr("id").split("_")[1];
                    if ($(e.form.elements[i]).val().split("+")[0] == aid) {
                        $(this).parent().remove();
                        return false;
                    }
                });
            }
        }
    }
    var user_ids = $("input[name='user_ids']").val();
    var user_names = $("input[name='user_names']").val();
    var user_count = user_ids.split(",").length;
    $("input[name='user_count']").val(user_count);
    document.getElementById("user_count").innerHTML=user_count;
}

function returnVal(){
    var api = frameElement.api, W = api.opener;
    W.document.getElementById("user_id").value = $("input[name='user_ids']").val();
    W.document.getElementById("user_name").value = $("input[name='user_names']").val();
    api.close();
}
function gb(){
    var api = frameElement.api, W = api.opener;
    api.close();
}
//]]></script>
</body>
</html>
