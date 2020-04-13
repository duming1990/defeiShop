<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<link href="${ctx}/styles/indexv3/css/global.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/styles/entp/homev2/css/entp.css" rel="stylesheet" type="text/css"  />
 <div id="J_showBuyerList">
<c:if test="${not empty entityList}">
            <table class="tb-list">
              <thead>
                <tr>
                  <th class="tb-buyer">买家</th>
                  <th class="tb-price">拍下价格</th>
                  <th class="tb-amount">数量</th>
                  <th class="tb-start">下单时间</th>
                  <th class="tb-sku">款式和型号</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach items="${entityList}" var="cur">
                <tr>
                  <td class="tb-buyer">
                  <span class="tb-sellnick">${cur.map.secretString}</span></td>
                  <td class="tb-price"><em class="tb-rmb">¥</em><em class="tb-rmb-num"><fmt:formatNumber value="${cur.good_price}" pattern="0.##" /></em></td>
                  <td class="tb-amount">${cur.good_count}</td>
                  <td class="tb-start"><fmt:formatDate value="${cur.order_info_add_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                  <td class="tb-sku">
                  <c:if test="${not empty cur.map.comm_tczh_nameList}">
                  <div class="tb-sku-wrap ">
                  <c:forEach items="${cur.map.comm_tczh_nameList}" var="cur2">
                      <p>${cur2}</p>
                  </c:forEach>
                    </div>
                    </c:if>
                    </td>
                </tr>
                </c:forEach>
              </tbody>
            </table>
           <div class="spagecont">
            <c:url value="/entp/IndexEntpInfo.do" var="url" />
              <form id="bottomPageForm" name="bottomPageForm" method="post" action="${url}">
                <table width="96%" border="0" align="center" cellpadding="0" cellspacing="0"  style="font: 12px/1.5 tahoma,arial,'Hiragino Sans GB',\5b8b\4f53,sans-serif;">
                  <tr>
                    <td height="40" align="center"><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
                      <script type="text/javascript">
		            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
		            pager.addHiddenInputs("method", "getCommInfoBuyHistory");
		            pager.addHiddenInputs("link_id", "${af.map.link_id}");
		            document.write(pager.toString());
            		</script></td>
                  </tr>
                </table>
              </form>
            </div>
		</c:if>
		<c:if test="${empty entityList}">
		<div style="padding-top:5px;padding-left:5px;">
		<p class="attention naked">暂时还没有买家购买此宝贝。</p> </div>
		</c:if>
          </div>
