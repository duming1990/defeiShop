<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/commons/pages/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${app_name}</title>
    <jsp:include page="../_public_head_back.jsp" flush="true"/>
    
<style type="">
    .buttonOper{
     	text-align: center;
    }
    .button1{
        color: #fff;
	    background-color: #5bc0de;
	    border-color: #46b8da;
	    user-select: none;
	    background-image: none;
	    border: 1px solid transparent;
	    border-radius: 4px;
	    margin: 2px;
	    padding: 2px 12px;
	    font-size: 14px;
	    line-height: 1.9;
	    cursor:pointer
    }
    </style>
</head>
<body>
<div class="divContent">
    <c:if test="${userInfo.user_type eq 1}">
        <div class="subtitle">
            <h3>系统首页</h3>
        </div>
        <div class="subtitle02">
            <h3>总体统计</h3>
        </div>
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="tableClass">
                <tr>
                    <td width="" rowspan="3" align="center" class="font40">店<br/>
                        铺
                    </td>
                    <td width="" class="bg_tip">入驻店铺总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                               onclick="doNeedMethod(null, 'EntpInfoAudit.do', 'list','mod_id=1003001000')">${entpInfoCount}</span>
                        个，
                    </td>
                    <td width="" rowspan="3" align="center" class="font40">会<br/>
                        员
                    </td>
                    <td width="" class="bg_tip">注册会员总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                               onclick="doNeedMethod(null, 'UserInfo.do', 'list','mod_id=1002001000&user_type=2')">${userInfoCount}</span>
                        个，
                    </td>
                    <td width="" rowspan="3" align="center" class="font40">商<br/>
                        品
                    </td>
                    <td width="" class="bg_tip">商城商品总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                               onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1010002000')">${commInfoCount}</span>
                        个，
                    </td>
                </tr>
                <tr>
                    <td>今天入驻店铺总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                onclick="doNeedMethod(null, 'EntpInfoAudit.do', 'list','mod_id=1003001000&today_date=${ndate}')">${entpInfoRzCount}</span></strong>
                        个，
                    </td>
                    <td>今天注册个人会员总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                  onclick="doNeedMethod(null, 'UserInfo.do', 'list','mod_id=1002001000&user_type=2&st_add_date=${ndate}&en_add_date=${ndate}&user_type=2')">${userInfoZcgrCount}</span></strong>
                        个，
                    </td>
                    <td>今天新增商品总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1010002000&today_date=${ndate}')">${commInfoZjCount}</span></strong>
                        个，
                    </td>
                </tr>
                <tr>
                    <td>待审核入驻店铺总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                 onclick="doNeedMethod(null, 'EntpInfoAudit.do', 'list','mod_id=1003001000&audit_state=0')">${entpInfoShCount}</span></strong>
                        个。
                    </td>
                    <td>今天注册店铺用户总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                  onclick="doNeedMethod(null, 'UserInfo.do', 'list','mod_id=1002001000&user_type=2&&st_add_date=${ndate}&en_add_date=${ndate}&user_type=3')">${userInfoZcqyCount}</span></strong>
                        个。
                    </td>
                    <td>待审核商品总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                               onclick="doNeedMethod(null, 'CommInfo.do', 'list','mod_id=1010002000&audit_state=0')">${commInfoDshCount}</span></strong>
                        个。
                    </td>
                </tr>
            </table>
        </div>
        <div class="subtitle02">
            <h3>审核统计</h3>
        </div>
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="tableClass">
                <tr>
                    <td width="" rowspan="2" align="center" class="font40">合伙<br/>人</td>
                    <td width="" class="bg_tip">入驻合伙人总数共为<span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                               onclick="doNeedMethod(null, 'ServiceInfoAudit.do', 'list','mod_id=1003002000')">${serviceCenterInfoSumCount}</span>
                        个，
                    </td>
                    <td width="" rowspan="2" align="center" class="font40">实名<br/>认证</td>
                    <td width="" class="bg_tip">实名认证总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                               onclick="doNeedMethod(null, 'UserAudit.do', 'list','mod_id=1003006000')">${baseAuditRecordCount}</span>
                        个，
                    </td>
                    <td width="" rowspan="2" align="center" class="font40">贫困<br/>户</td>
                    <td width="" class="bg_tip">贫困户总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                              onclick="doNeedMethod(null, 'PoorManager.do', 'list','mod_id=1003008000&audit_state=1')">${poorInfoCount}</span>
                        个，
                    </td>
                </tr>
                <tr>
                    <td>待审核入驻合伙人总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                  onclick="doNeedMethod(null, 'ServiceInfoAudit.do', 'list','mod_id=1003002000&amp;audit_state=0')">${serviceCenterInfoCount}</span></strong>
                        个。
                    </td>
                    <td>待审核实名认证总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                 onclick="doNeedMethod(null, 'UserAudit.do', 'list','mod_id=1003006000&audit_state=0')">${baseAuditRecord0Count}</span></strong>
                        个。
                    </td>
                    <td>待审核贫困户总数为 <strong><span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                onclick="doNeedMethod(null, 'PoorManager.do', 'list','mod_id=1003008000&audit_state=0')">${poorInfo0Count}</span></strong>
                        个。
                    </td>
                </tr>
            </table>
        </div>
        <div class="subtitle02">
            <h3>订单统计</h3>
        </div>
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="tableClass">
                <tr>
                    <td width="" rowspan="2" align="center" class="font40">订<br/>单</td>
                    <td class="bg_tip">
                        订单总数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                     onclick="doNeedMethod(null, 'OrderQuery.do', 'list','mod_id=1005100003')">${orderInfoTodaySum.map.cnt}</span>
                        笔。
                    </td>
                </tr>
                <tr>
                    <td>今日订单数共为 <span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                      onclick="doNeedMethod(null, 'OrderQuery.do', 'list','mod_id=1005100003&st_add_date=${ndate}&en_add_date=${ndate}')">${todayOrderCount}</span>
                        笔。
                    </td>
                </tr>
            </table>
        </div>
        <div class="subtitle02">
            <h3>客户提现申请</h3>
        </div>
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="tableClass">
                <tr>
                    <td class="bg_tip">提现申请<span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                 onclick="doNeedMethod(null, 'MoneyUserQuery.do', 'list','mod_id=1004002000&info_state=0')"> ${userMoneyApplyCount}</span>
                        笔待处理。
                    </td>
                </tr>
            </table>
        </div>
        <div class="subtitle02">
            <h3>商家结算申请</h3>
        </div>
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="tableClass">
                <tr>
                    <td class="bg_tip">商家结算申请<span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                   onclick="doNeedMethod(null, 'MerchantCheckTwo.do', 'list','mod_id=1004001000&is_check=0')"> ${enptMoneyApplyCount}</span>笔待处理。
                    </td>
                </tr>
            </table>
        </div>
        <div class="subtitle02">
            <h3>扶贫金提现申请</h3>
        </div>
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="tableClass">
                <tr>
                    <td class="bg_tip">扶贫金提现申请<span title="点击查看" class="tip-danger" style="cursor: pointer;"
                                                    onclick="doNeedMethod(null, 'MoneyAidUserQuery.do', 'list','mod_id=1004003000&info_state=0')"> ${fupinMoneyApplyCount}</span>笔待处理。
                    </td>
                </tr>
            </table>
        </div>
        <div>
            <table width="100%" border="0" cellspacing="0" cellpadding="6" id="tip_ind" class="tableClass">
                <tr>
                    <td class="buttonOper">无人超市</td>
                    <td class="buttonOper"> <a onclick = "toOthersSystem(1);"><button class = "button1">一键登录</button></a> </td>
                    <td class="buttonOper">无人柜</td>
                    <td class="buttonOper"> <a onclick="toOthersSystem(2);"><button class = "button1">一键登录</button></a> </td>
                    <td class="buttonOper">仓库系统</td>
                    <td class="buttonOper"> <a onclick="toOthersSystem(3);"><button class = "button1">一键登录</button></a> </td>
                </tr>
            </table>
        </div>
    </c:if>
</div>
<script type="text/javascript" src="${ctx}/commons/scripts/pager.js">;</script>
<script type="text/javascript">//<![CDATA[
var browser = navigator.appName
var b_version = navigator.appVersion
var version = parseFloat(b_version)
if (browser == 'Microsoft Internet Explorer' && version <= 6) {
    alert("您的浏览器版本过低，为了系统的正常使用，请升级到最新版本或使用360浏览器！");
}
function toOthersSystem(type){
		 $.post("?method=toOthersSystem",{type:type},function(data){
		   	 if(data.ret == 1){
		   		var tempwindow=window.open('_blank'); // 先打开页面
		   		tempwindow.location=data.msg; // 后更改页面地址
		   	 }
		 });
}
//]]></script>
</body>
</html>
