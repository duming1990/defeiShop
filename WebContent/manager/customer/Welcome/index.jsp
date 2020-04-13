<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/commons/pages/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${app_name}</title>
<jsp:include page="../_public_head_back.jsp" flush="true" />
<link href="${ctx}/styles/entps/css/admin.css" rel="stylesheet" type="text/css" />
</head>
<style>
.subtitle02 h3{
	display: block;
    font-size: 1.17em;
    -webkit-margin-before: 1em;
    -webkit-margin-after: 1em;
    -webkit-margin-start: 0px;
    -webkit-margin-end: 0px;
    font-weight: bold;
}
</style>
<body>
<div class="mainbox mine" style="margin: 8px;margin-bottom: 130px;">
  <div class="subtitle02">
    <h3 >总体统计</h3>
  </div>
  <div>
    <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="backTable">
      <tr>
        <td width="" rowspan="3" align="center" class="font40">商<br/>品</td>
        <td width="" class="bg_tip">商品总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1300500100')">${commInfoCount}</span> 个，</td>
        
        <td width="" rowspan="3" align="center" class="font40">订<br/>单</td>
        <td width="" class="bg_tip">订单总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'MyOrderEntp.do', 'list','mod_id=1300300100')">${orderInfoCount}</span> 个，</td>
        
        
        
      </tr>
      <tr>
        <td>今天新增商品总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1300500100&today_date=${ndate}')">${commInfoZjCount}</span></strong> 个，</td>
        <td>今今日订单数共为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'MyOrderEntp.do', 'list','mod_id=1300300100&queryToday=${ndate}')">${todayOrderCount}</span></strong> 个，</td>
      </tr>
      <tr>
        <td>待审核商品总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;" onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1300500100&audit_state=0')">${commInfoDshCount}</span></strong> 个。</td>
        <td><strong><span  class="tip-danger" style="cursor: pointer;"></span></strong></td>
      </tr>
    </table>
  </div>  
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script> 
</body>
</html>
