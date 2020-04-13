<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>

<c:if test="${isEnabledNewsCustomFields eq 1}">

<c:if test="${not empty extraNewsInfoCustomFieldsList or not empty newsInfoCustomFieldsList or not empty newsInfoCustomFieldContentList}">
<tr>
  <th colspan="2">自定义字段基本信息</th>
  <th><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" id="imgAddZdy" title="添加自定义字段" /></th>
</tr>
<tbody id="addZdy_tbody" style="display:none;">
  <tr>
    <td colspan="3"><table width="100%" border="0" cellpadding="0" cellspacing="0" class="tableClass" id="table_zdy">
        <c:if test="${not empty extraNewsInfoCustomFieldsList}">
          <c:forEach var="cur" items="${extraNewsInfoCustomFieldsList}">
            <c:if test="${cur.type eq 1}">
              <tr id="tr_${cur.id}">
                <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
                  ${cur.title_name }:</td>
                <td align="left" width="77%"><html-el:text property="zdy_content_${cur.id}" styleId="zdy_${cur.id}"  styleClass="webinput" />
                  <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
                <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" onclick="addZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="添加"/></td>
              </tr>
            </c:if>
            <c:if test="${cur.type eq 2}">
              <tr id="tr_${cur.id}">
                <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
                  ${cur.title_name }：</td>
                <td align="left" width="77%"><html-el:textarea property="zdy_content_${cur.id}" styleId="zdy_content_${cur.id}" style="width:500px;height:200px;visibility:hidden;"></html-el:textarea>
                  <div>1、此处上传的图片不会自动缩放，请用相关做图软件缩放成您想要的大小！</div>
                  <div>2、点击【第一排】顺数【最后一个】按钮可实现全屏编辑</div>
                  <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
                <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" onclick="addZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="添加"/></td>
              </tr>
            </c:if>
            <c:if test="${cur.type eq 3}">
              <tr id="tr_${cur.id}">
                <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
                  ${cur.title_name }:</td>
                <td align="left" width="77%"><c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
                    <label for="zdy_${cur_son.id}" style="width:80px;">
                      <input type="radio" name="zdy_content_${cur.id}" id="zdy_${cur_son.id}" value="${cur_son.id}" />
                      &nbsp;${cur_son.type_name} </label>
                    &nbsp;&nbsp; </c:forEach>
                  <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
                <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" onclick="addZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="添加"/></td>
              </tr>
            </c:if>
            <c:if test="${cur.type eq 4}">
              <tr id="tr_${cur.id}">
                <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
                  ${cur.title_name }:</td>
                <td align="left" width="77%"><c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
                    <label for="zdy_${cur_son.id}" style="width:80px;">
                      <input type="checkbox" name="zdy_content_${cur.id}" id="zdy_${cur_son.id}" value="${cur_son.id}" />
                      &nbsp;${cur_son.type_name} </label>
                    &nbsp;&nbsp; </c:forEach>
                  <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
                <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" onclick="addZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="添加"/></td>
              </tr>
            </c:if>
            <c:if test="${cur.type eq 5}">
              <tr id="tr_${cur.id}">
                <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
                  ${cur.title_name }:</td>
                <td align="left" width="77%"><html-el:select property="zdy_content_${cur.id}" styleId="zdy_${cur.id}" style="width:80px">
                    <html-el:option value="">请选择...</html-el:option>
                    <c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
                      <html-el:option value="${cur_son.id}">${cur_son.type_name}</html-el:option>
                    </c:forEach>
                  </html-el:select>
                  <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
                <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/+.gif" style="vertical-align:middle; cursor: pointer;" onclick="addZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="添加"/></td>
              </tr>
            </c:if>
          </c:forEach>
        </c:if>
      </table></td>
  </tr>
</tbody>
<tbody id="delZdy_tbody">
  <c:if test="${not empty newsInfoCustomFieldsList}">
    <c:forEach var="cur" items="${newsInfoCustomFieldsList}">
      <c:if test="${cur.type eq 1}">
        <tr id="tr_${cur.id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.title_name }:</td>
          <td align="left" width="77%"><html-el:text property="zdy_content_${cur.id}" styleId="zdy_${cur.id}"  styleClass="webinput" />
            <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 2}">
        <tr id="tr_${cur.id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.title_name }：</td>
          <td align="left" width="77%"><html-el:textarea property="zdy_content_${cur.id}" styleId="zdy_content_${cur.id}" style="width:500px;height:200px;visibility:hidden;"></html-el:textarea>
            <div>1、此处上传的图片不会自动缩放，请用相关做图软件缩放成您想要的大小！</div>
            <div>2、点击【第一排】顺数【最后一个】按钮可实现全屏编辑</div>
            <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 3}">
        <tr id="tr_${cur.id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.title_name }:</td>
          <td align="left" width="77%"><c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
              <label for="zdy_${cur_son.id}" style="width:80px;">
                <input type="radio" name="zdy_content_${cur.id}" id="zdy_${cur_son.id}" value="${cur_son.id}" />
                &nbsp;${cur_son.type_name} </label>
              &nbsp;&nbsp; </c:forEach>
            <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 4}">
        <tr id="tr_${cur.id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.title_name }:</td>
          <td align="left" width="77%"><c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
              <label for="zdy_${cur_son.id}" style="width:80px;">
                <input type="checkbox" name="zdy_content_${cur.id}" id="zdy_${cur_son.id}" value="${cur_son.id}" />
                &nbsp;${cur_son.type_name} </label>
              &nbsp;&nbsp; </c:forEach>
            <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 5}">
        <tr id="tr_${cur.id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.title_name }:</td>
          <td align="left" width="77%"><html-el:select property="zdy_content_${cur.id}" styleId="zdy_${cur.id}" style="width:80px">
              <html-el:option value="">请选择...</html-el:option>
              <c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
                <html-el:option value="${cur_son.id}">${cur_son.type_name}</html-el:option>
              </c:forEach>
            </html-el:select>
            <html-el:hidden property="zdy_column" value="${cur.id},${cur.type},${cur.title_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.id}','${cur.type}','${cur.is_required}','${cur.title_name}')" title="删除"/></td>
        </tr>
      </c:if>
    </c:forEach>
  </c:if>
  <c:if test="${not empty newsInfoCustomFieldContentList}">
    <c:forEach var="cur" items="${newsInfoCustomFieldContentList}">
      <c:if test="${cur.type eq 1}">
        <tr id="tr_${cur.custom_field_id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.custom_field_name }:</td>
          <td align="left" width="77%"><html-el:text property="zdy_content_${cur.custom_field_id}" styleId="zdy_${cur.custom_field_id}"  styleClass="webinput" value="${cur.custom_field_content}"/>
            <html-el:hidden property="zdy_column" value="${cur.custom_field_id},${cur.type},${cur.custom_field_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.custom_field_id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.custom_field_id}','${cur.type}','${cur.is_required}','${cur.custom_field_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 2}">
        <tr id="tr_${cur.custom_field_id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.custom_field_name }：</td>
          <td align="left" width="77%"><html-el:textarea value="${cur.custom_field_content}" property="zdy_content_${cur.custom_field_id}" styleId="zdy_${cur.custom_field_id}" style="width:500px;height:200px;visibility:hidden;"></html-el:textarea>
            <div>1、此处上传的图片不会自动缩放，请用相关做图软件缩放成您想要的大小！</div>
            <div>2、点击【第一排】顺数【最后一个】按钮可实现全屏编辑</div>
            <html-el:hidden property="zdy_column" value="${cur.custom_field_id},${cur.type},${cur.custom_field_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.custom_field_id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.custom_field_id}','${cur.type}','${cur.is_required}','${cur.custom_field_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 3}">
        <tr id="tr_${cur.custom_field_id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.custom_field_name }:</td>
          <td align="left" width="77%"><c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
              <label for="zdy_${cur_son.id}" style="width:80px;" >
                <c:if test="${cur.custom_field_content eq cur_son.id}" var="isChecked">
                  <input type="radio" name="zdy_content_${cur.custom_field_id}" id="zdy_${cur_son.id}" value="${cur_son.id}" checked="checked" />
                  &nbsp; ${cur_son.type_name} </c:if>
                <c:if test="${not isChecked}">
                  <input type="radio" name="zdy_content_${cur.custom_field_id}" id="zdy_${cur_son.id}" value="${cur_son.id}" />
                  &nbsp; ${cur_son.type_name} </c:if>
              </label>
              &nbsp;&nbsp; </c:forEach>
            <html-el:hidden property="zdy_column" value="${cur.custom_field_id},${cur.type},${cur.custom_field_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.custom_field_id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.custom_field_id}','${cur.type}','${cur.is_required}','${cur.custom_field_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 4}">
        <tr id="tr_${cur.custom_field_id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.custom_field_name }:</td>
          <td align="left" width="77%"><c:set var="fieldContent" value=",${cur.custom_field_content}," />
            <c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
              <c:set var="fieldsSonId" value=",${cur_son.id}," />
              <label for="zdy_${cur_son.id}" style="width:80px;" >
                <c:if test="${fn:contains(fieldContent, fieldsSonId)}" var="isContains">
                  <input type="checkbox" name="zdy_content_${cur.custom_field_id}" id="zdy_${cur_son.id}" value="${cur_son.id}" checked="checked" />
                </c:if>
                <c:if test="${not isContains}" >
                  <input type="checkbox" name="zdy_content_${cur.custom_field_id}" id="zdy_${cur_son.id}" value="${cur_son.id}" />
                </c:if>
                &nbsp; ${cur_son.type_name} </label>
            </c:forEach>
            <html-el:hidden property="zdy_column" value="${cur.custom_field_id},${cur.type},${cur.custom_field_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.custom_field_id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.custom_field_id}','${cur.type}','${cur.is_required}','${cur.custom_field_name}')" title="删除"/></td>
        </tr>
      </c:if>
      <c:if test="${cur.type eq 5}">
        <tr id="tr_${cur.custom_field_id}">
          <td width="15%" class="title_item"><c:if test="${cur.is_required eq 1}"><span style="color: #F00;">*</span></c:if>
            ${cur.custom_field_name }:</td>
          <td align="left" width="77%"><select name="zdy_content_${cur.custom_field_id}" id="zdy_${cur.custom_field_id}" style="width:80px">
              <option value="">请选择...</option>
              <c:forEach items="${cur.newsInfoCustomFieldsSonList}" var="cur_son">
                <c:if test="${cur.custom_field_content eq cur_son.id}" var="isSelected">
                  <option value="${cur_son.id}" selected="selected">${cur_son.type_name}</option>
                </c:if>
                <c:if test="${not isSelected}">
                  <option value="${cur_son.id}" >${cur_son.type_name}</option>
                </c:if>
              </c:forEach>
            </select>
            <html-el:hidden property="zdy_column" value="${cur.custom_field_id},${cur.type},${cur.custom_field_name},${cur.is_required},${cur.order_value}"  styleClass="webinput" /></td>
          <td width="8%" align="center" id="td_${cur.custom_field_id}"><img src="../../images/x.gif" style="vertical-align:middle; cursor: pointer;" onclick="delZdy('${cur.custom_field_id}','${cur.type}','${cur.is_required}','${cur.custom_field_name}')" title="删除"/></td>
        </tr>
      </c:if>
    </c:forEach>
  </c:if>
</tbody>
</c:if>
</c:if>