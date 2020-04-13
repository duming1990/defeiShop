<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<link rel="stylesheet" type="text/css" href="${ctx}/styles/indexEntp/css/common.css"/>
	<!--底部-->
<%-- <c:forEach items="${baseLink10000List}" var="cur"> --%>
<%-- 	<c:if test="${cur.pre_number eq 7}"> --%>
	  		<div class="footer">
	  		<div class="areas"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10091,'txt',${cur.id});">编辑底部copyright</a></div>
	  			<div class="footer_o clearfix">
	  			<div class="areas"><a  style="cursor:pointer" class="beautybg" onclick="editFloor(${af.map.mod_id},10090,'txt',${cur.id});">编辑</a></div>
						
		  				
	  				<div class="footer_o_o fl">
	  					<img src="${ctx}/styles/indexEntp/images/logo.png" alt="logo" width="200" height="57">
	  				</div>
	  				
<%-- 	  				<c:forEach items="${cur.map.baseLink10090List}" var="cur2" varStatus="vs2"> --%>
<%-- 	  						<c:if test="${vs2.index % 2 eq 0}"> --%>
<!-- 	  							<div class="footer_o_t fl"> -->
<%-- 				  					<p>${cur2.title}</p> --%>
<%-- 				  					<span>${cur2.pre_varchar}</span> --%>
<!-- 				  				</div> -->
<%-- 	  						</c:if> --%>
<%-- 			  				<c:if test="${vs2.index % 2 ne 0}"> --%>
<!-- 	  							<div class="footer_o_t fl footer_o_s fl"> -->
<%-- 				  					<p>${cur2.title}</p> --%>
<%-- 				  					<span>${cur2.pre_varchar}</span> --%>
<!-- 				  				</div> -->
<%-- 	  						</c:if> --%>
<%-- 		  			</c:forEach> --%>
	  				<div class="footer_o_t fl">
	  					<p>全国热线</p>
	  					<span>400-696-4500</span>
	  				</div>
	  				<div class="footer_o_t footer_o_s fl">
	  					<p>市场合作</p>
	  					<span>gongweijian@qidada.com</span>
	  				</div>
	  				
	  				
	  				<div class="footer_o_f fr">
	  					<img src="${ctx}/styles/indexEntp/images/footer_qrcode_pic.gif" alt="ewm" style="width:217px;height:127px">
	  				</div>
	  			</div>
	  			
	  			<div class="footer_t">
	  				网站备案：京ICP备16057408号-1 版权所有：中财华商集团版权所有COPYRIGHT     All rights reserved 
	  			</div>
	  		</div>
<%--  </c:if> --%>
<%--  </c:forEach> --%>