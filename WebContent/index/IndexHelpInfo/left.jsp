<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/commons/font-awesome/css/font-awesome.min.css"  />
<div id="sidebar" class="site-sidebar" >
  <div class="side-menu">
    <c:url var="url" value="javascript:void(0);" />
    <c:forEach items="${helpModule10000000List}" var="cur" varStatus="vs">
      <h3><i class="fa fa-plus-square"></i> ${cur.mod_name}</h3>
      <ul style="display: none;">
        <c:forEach items="${cur.map.helpModuleChiList}" var="curChi" varStatus="vs">
          <c:url var="url" value="/IndexHelpInfo.do?method=view&h_mod_id=${curChi.h_mod_id}" />
          <c:set var="hoverclass" value="" />
          <c:if test="${curChi.h_mod_id eq af.map.h_mod_id}">
            <c:set var="hoverclass" value="hover" />
          </c:if>
          <li id="h_mod_id_${curChi.h_mod_id}"><a class="${hoverclass}" href="${url}">${curChi.mod_name}</a></li>
        </c:forEach>
      </ul>
    </c:forEach>
  </div>
</div>
<script type="text/javascript">//<![CDATA[
$(document).ready(function(){
	$("#sidebar h3").each(function(){
		$(this).click(function(){
			var $this = $(this);
	        var $next_dd = $this.next();
			if($next_dd.css('display') != 'none') {//显示
				$this.find("i").removeClass().addClass("fa fa-plus-square");
				$this.attr("title","展开节点");
			}else{
				$this.find("i").removeClass().addClass("fa fa-minus-square");
				$this.attr("title","收起节点");
			}
		  
		    $next_dd.slideToggle('normal');
		});
	});
	
	$("#h_mod_id_${af.map.h_mod_id}").parent().prev().click();
	
});
//]]>
</script>
