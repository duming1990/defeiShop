<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${naviString}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
</head>
<body>
<div class="divContent">
  <div class="subtitle">
    <h3>${naviString}</h3>
  </div>
  <c:set var="base_name" value="内容" />
  <c:if test="${af.map.type eq 10}"><c:set var="base_name" value="用户类型" /></c:if>
  <c:if test="${af.map.type eq 30}"><c:set var="base_name" value="热门搜索" /></c:if>
  <c:if test="${af.map.type eq 200}"><c:set var="base_name" value="会员等级" /></c:if>
  <c:if test="${af.map.type eq 301}"><c:set var="base_name" value="标签类型" /></c:if>
  <c:if test="${af.map.type eq 500}"><c:set var="base_name" value="积分获取规则" /></c:if>
  <c:if test="${af.map.type eq 600}"><c:set var="base_name" value="手续费管理" /></c:if>
  <c:if test="${af.map.type eq 800}"><c:set var="base_name" value="消费币获取规则" /></c:if>
  <c:if test="${af.map.type eq 900}"><c:set var="base_name" value="币种兑换规则" /></c:if>
  <c:if test="${af.map.type eq 1000}"><c:set var="base_name" value="余额获取规则" /></c:if>
  <c:if test="${af.map.type eq 1100}"><c:set var="base_name" value="时间管理" /></c:if>
  <c:if test="${af.map.type eq 2000}"><c:set var="base_name" value="内部商城配置" /></c:if>
  <c:if test="${af.map.type eq 1500}"><c:set var="base_name" value="汇赚公司收益" /></c:if>
  <c:if test="${af.map.type eq 1800}"><c:set var="base_name" value="合伙人缴费金额" /></c:if>
  <c:if test="${af.map.type eq 1900}"><c:set var="base_name" value="扶贫规则" /></c:if>
  <c:if test="${af.map.type eq 5000}"><c:set var="base_name" value="包邮价格" /></c:if>
  <c:if test="${af.map.type eq 3100}"><c:set var="base_name" value="公司性质" /></c:if>
  <c:if test="${af.map.type eq 3200}"><c:set var="base_name" value="安全问题" /></c:if>
  <c:if test="${af.map.type eq 9000}"><c:set var="base_name" value="敏感词库" /></c:if>
  <c:if test="${af.map.type eq 2100}"><c:set var="base_name" value="促销时间设置" /></c:if>
  <c:if test="${af.map.type eq 1000}"><c:set var="is_pre_number2" value="比例" /></c:if>
  <c:if test="${af.map.type eq 100}"><c:set var="base_name" value="消费提现金额设置" /></c:if>
  <c:if test="${af.map.type eq 16000}"><c:set var="base_name" value="设备厂商名称" /></c:if>
  <c:if test="${af.map.type eq 17000}"><c:set var="base_name" value="商品进出库类型" /></c:if>
  <html-el:form action="/admin/BaseData">
    <html-el:hidden property="method" value="list" />
    <html-el:hidden property="mod_id" />
    <html-el:hidden property="type" />
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClassSearch">
      <tr>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="0" align="left">
            <tr>
              <td width="6%" nowrap="nowrap">${base_name}：
              <html-el:text property="type_name_like" styleId="type_name_like" style="width:80px;" styleClass="webinput"/>
            <c:if test="${af.map.type eq 17000}">
                 &nbsp;&nbsp;进/出库：
               <html-el:select property="pre_number">
                 <html-el:option value="">全部</html-el:option>
                 <html-el:option value="0">进库</html-el:option>
                 <html-el:option value="1">出库</html-el:option>
               </html-el:select> 
             </c:if>
                &nbsp;&nbsp;是否锁定：
                <html-el:select property="is_lock">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未锁定</html-el:option>
                  <html-el:option value="1">已锁定</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;是否删除：
                <html-el:select property="is_del">
                  <html-el:option value="">全部</html-el:option>
                  <html-el:option value="0">未删除</html-el:option>
                  <html-el:option value="1">已删除</html-el:option>
                </html-el:select>
                &nbsp;&nbsp;
                <html-el:submit value="查 询" styleClass="bgButton" /></td>
            </tr>
          </table></td>
      </tr>
    </table>
  </html-el:form>
  <%@ include file="/commons/pages/messages.jsp" %>
  <form id="listForm" name="listForm" method="post" action="BaseData.do?method=delete">
    <div style="text-align: left; padding: 5px;">
      <logic-el:match name="popedom" value="+4+">
        <input type="button" name="delete" id="delete" class="bgButton" value="删除所选" onclick="this.form.action += '&' + $('#bottomPageForm').serialize();confirmDeleteAll(this.form);" />
      </logic-el:match>
      <logic-el:match name="popedom" value="+1+">
       <c:if test="${((af.map.type eq 6000) or (af.map.type eq 15000) or (af.map.type eq 16000) or (af.map.type eq 6500) or (af.map.type eq 6610) or (af.map.type eq 6620)or (af.map.type eq 6630)) && empty only_has_one_data}">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='BaseData.do?method=add&mod_id=${af.map.mod_id}&type=${af.map.type}';" />
      </c:if>
       <c:if test="${(af.map.type ne 6000) && (af.map.type ne 15000) && (af.map.type ne 16000) && (af.map.type ne 6500) && (af.map.type ne 6610) && (af.map.type ne 6620) && (af.map.type ne 6630) }">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='BaseData.do?method=add&mod_id=${af.map.mod_id}&type=${af.map.type}';" />
      </c:if>
      <c:if test="${af.map.type eq 16000}">
        <input type="button" name="add" id="add" class="bgButton" value="添 加" onclick="location.href='BaseData.do?method=add&mod_id=${af.map.mod_id}&type=${af.map.type}';" />
      </c:if>
      </logic-el:match>
    </div>
    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass">
      <tr>
        <th width="5%" nowrap="nowrap"> <c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
            <input name="chkAll" type="checkbox" id="chkAll" value="-1" onclick="checkAll(this);" />
          </c:if>
          <c:if test="${not isDelete}"> 序号 </c:if>
        </th>
        <th width="6%">编码</th>
        <th nowrap="nowrap" width="12%">${base_name}</th>
        <c:if test="${af.map.type eq 100}">
        <th nowrap="nowrap" width="8%">消费金额</th>
        <th nowrap="nowrap" width="8%">提现金额</th>
        </c:if>
        <c:if test="${af.map.type eq 101}">
       	 <th nowrap="nowrap" width="8%">余额分红比例</th>
        </c:if>
        <c:if test="${af.map.type eq 17000}"><th width="5%">进/出库</th></c:if>
        <th>详细说明</th>
        <c:if test="${not empty is_pre_number2}"><th width="5%">${is_pre_number2}</th></c:if>
        <th width="8%">添加时间</th>
        <c:if test="${af.map.type ne 17000}"><th width="6%">排序值</th></c:if>
        <th width="6%">是否锁定</th>
        <th width="6%">是否删除</th>
        <c:if test="${fn:contains(popedom, '+2+') or fn:contains(popedom, '+4+')}" var="isContains">
          <th width="12%" nowrap="nowrap">操作</th>
        </c:if>
      </tr>
      <c:forEach var="cur" items="${baseDataList}" varStatus="vs">
        <tr>
          <td align="center" nowrap="nowrap"><c:if test="${fn:contains(popedom, '+4+')}" var="isDelete">
              <c:set var="isLock" value="${cur.is_lock eq 1}"></c:set>
              <c:set var="isDel" value="${cur.is_del eq 1}"></c:set>
              <c:if test="${isLock or isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" disabled="disabled"/>
              </c:if>
              <c:if test="${not isLock and not isDel}">
                <input name="pks" type="checkbox" id="pks" value="${cur.id}" />
              </c:if>
            </c:if>
            <c:if test="${not isDelete}"> ${vs.count} </c:if></td>
          <td align="center">${cur.id}</td>
          <td nowrap="nowrap" align="center">${fn:escapeXml(cur.type_name)}</td>
           <c:if test="${af.map.type eq 100}">
           <td nowrap="nowrap" align="center"><fmt:formatNumber var="number" value="${cur.pre_number}" pattern="0.00"/>${number}元</td>
           <td nowrap="nowrap" align="center"><fmt:formatNumber var="number" value="${cur.pre_number2}" pattern="0.00"/>${number}元</td>
           </c:if>
            <c:if test="${af.map.type eq 101}">
           <td nowrap="nowrap" align="center"><fmt:formatNumber var="number" value="${cur.pre_number}" pattern="0.00"/>${number}%</td>
           </c:if>
           <c:if test="${af.map.type eq 17000}">
           <td align="center"><c:choose>
              <c:when test="${cur.pre_number eq 0}"><span style=" color:#060;">进库</span></c:when>
              <c:when test="${cur.pre_number eq 1}"><span style=" color:#F00;">出库</span></c:when>
            </c:choose></td>
            </c:if>
          <td align="center"><c:out value="${cur.remark}"></c:out>
          <c:if test="${not empty is_pre_number2}">
          <fmt:formatNumber var="number" value="${cur.pre_number2}" pattern="0.########"/>
          <td align="center">${cur.pre_number2}%</td>
          </c:if>
          </td>
          <td align="center"><fmt:formatDate value="${cur.add_date}" pattern="yyyy-MM-dd"/></td>
          <c:if test="${af.map.type ne 17000}"><td align="center"><c:out value="${cur.order_value }"></c:out></td></c:if>
          <td align="center"><c:choose>
              <c:when test="${cur.is_lock eq 0}"><span style=" color:#060;">未锁定</span></c:when>
              <c:when test="${cur.is_lock eq 1}"><span style=" color:#ccc;">已锁定</span></c:when>
            </c:choose></td>
          <td align="center"><c:choose>
              <c:when test="${cur.is_del eq 0}"><span style=" color:#060;">未删除</span></c:when>
              <c:when test="${cur.is_del eq 1}"><span style=" color:#F00;">已删除</span></c:when>
            </c:choose></td>
          <c:if test="${isContains}" >
            <td align="center" nowrap="nowrap">
              <logic-el:match name="popedom" value="+2+">
              <a class="butbase" onclick="confirmUpdate(null, 'BaseData.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-edit">修改</span></a>
              </logic-el:match>
              <logic-el:match name="popedom" value="+4+">
                <c:if test="${isLock or isDel}">&nbsp;
                <a class="butbase but-disabled"  onclick="javascript:void(0);"  title="已锁定或删除，不能删除"><span class="icon-remove">删除</span></a>
                </c:if>
                <c:if test="${not isLock and not isDel}">
                 <a class="butbase" onclick="confirmDelete(null, 'BaseData.do', 'id=${cur.id}&' + $('#bottomPageForm').serialize())"><span class="icon-remove">删除</span></a>
                </c:if>
              </logic-el:match></td>
          </c:if>
        </tr>
       <c:if test="${vs.last eq true}">
          <c:set var="i" value="${vs.count}" />
        </c:if>
      </c:forEach>
      <c:forEach begin="${i}" end="${af.map.pager.pageSize - 1}">
        <tr align="center">
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <c:if test="${not empty is_pre_number2}">
          <td>&nbsp;</td>
          </c:if>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </c:forEach>
    </table>
  </form>
  <div class="pageClass">
    <form id="bottomPageForm" name="bottomPageForm" method="post" action="BaseData.do">
      <table width="98%" border="0" align="center" cellpadding="0" cellspacing="0" >
        <tr>
          <td height="10" ><script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
            <script type="text/javascript">
            var pager = new Pager(document.bottomPageForm, ${af.map.pager.recordCount}, ${af.map.pager.pageSize}, ${af.map.pager.currentPage});
            pager.addHiddenInputs("method", "list");
            pager.addHiddenInputs("mod_id", "${af.map.mod_id}");
            pager.addHiddenInputs("type_name_like", "${fn:escapeXml(af.map.type_name_like)}");
			pager.addHiddenInputs("is_del", "${af.map.is_del}");
			pager.addHiddenInputs("is_lock", "${af.map.is_lock}");
			pager.addHiddenInputs("type", "${af.map.type}");
            document.write(pager.toString());
        </script></td>
        </tr>
      </table>
    </form>
  </div>
</div>

<script type="text/javascript" src="${ctx}/scripts/rowEffect.js"></script>
</body>
<jsp:include page="../public_page.jsp" flush="true" />
</html>
