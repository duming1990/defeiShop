<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

 <div class="tabbox" id="div_content">
   <ul class="tabbtn">
     <li id="pdli2"><span>商品评价</span></li>
   </ul>
   <div class="tabcon" id="pdcontent2">
     <div id="comment" class="m m2" style="display: block;">
       <div class="mtcomm">
         <h2>商品评价</h2>
       </div>
       <div class="mc">
         <div id="i-comment">
           <div class="rate"> <strong>${commentLevel1Rate}<span>%</span></strong> <br/>
             <span>好评度</span> </div>
           <div class="percent">
             <dl>
               <dt>好评<span>(${commentLevel1Rate}%)</span></dt>
               <dd>
                 <div style="width: ${commentLevel1Rate}px;"></div>
               </dd>
             </dl>
             <dl>
               <dt>中评<span>(${commentLevel2Rate}%)</span></dt>
               <dd class="d1">
                 <div style="width: ${commentLevel2Rate}%;"> </div>
               </dd>
             </dl>
             <dl>
               <dt>差评<span>(${commentLevel3Rate}%)</span></dt>
               <dd class="d1">
                 <div style="width: ${commentLevel3Rate}%;"> </div>
               </dd>
             </dl>
           </div>
           <div class="actor" style="width: 400px;"> <em>前六位评价用户：</em>
             <ul>
             <c:forEach items="${commentInfoList6}" var="cur" varStatus="vs">
               <li>${vs.count}. ${fn:escapeXml(cur.comm_uname)} </li>
             </c:forEach>
             </ul>
             <div class="clr"></div>
           </div>
         </div>
       </div>
     </div>
   </div>
 </div>
    


