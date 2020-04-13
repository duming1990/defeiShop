package com.ebiz.webapp.web;

/**
 * @author Wu,Yang
 */
public class SMS {

	// public static final String uri = "http://222.73.117.158/msg/";
	//
	// public static final String account = "vipmoba_zhonghui";
	//
	// public static final String pswd = "zhonghui@2016";

	// public static final String app_name = "【" + Keys.app_name + "】";

	// public static final String app_huizhuan_name = "【汇赚合伙人】";

	public static final String app_name = Keys.app_name;

	public static final String app_tel = Keys.app_tel;

	public static final String hb_01 = "手机号";

	public static final String hb_02 = "银行卡";

	public static final String hb_03 = "密码";

	// 1、会员注册：（短信提醒）
	public static final String sms_01 = "{0}是您本次注册验证码！欢迎加入" + app_name + "，如非本人操作，请忽略此短信。";

	// 2、密码找回：（短信提醒）
	public static final String sms_02 = "{0}是您本次密码找回的验证码！您正在进行" + app_name + "密码找回操作。";

	// 3、换绑手机、银行卡：（短信提醒）
	public static final String sms_03 = "{0}是您本次换绑{1}的验证码！您正在进行" + app_name + "帐户换绑{2}操作。";

	// 3、设置手机、银行卡：（短信提醒）
	public static final String sms_03_2 = "{0}是您本次设置{1}的验证码！您正在进行" + app_name + "帐户设置{2}操作。";

	// 4、消费会员：支付完成后：（短信提醒）
	public static final String sms_04 = "您正在{1}商家消费的订单已支付成功，订单号{2}";

	// 4、消费会员：自提实物定订单支付完成后：（短信提醒）
	public static final String sms_04_02 = "{0}是您本次订单的自取密码！您正在{1}商家消费的订单已支付成功，订单号{2}，商家地址：{3}，尽快到店自提！";

	// 5、即将过期订单：（站内信提醒）
	public static final String sms_05 = "{0}家人您好！您的订单（订单号{1}，订单密码{2}）。将在24小时后过期，请在有效期内及时消费，过期退款将产生费用";

	// 6、 消费完成订单（站内信提醒））
	public static final String sms_06 = "{0}家人您好！您订单号为{1}的订单已成功消费，欢迎再次通过" + app_name + "购买产品或服务，感谢您的支持！";

	// 7、商家申请通过（站内信提醒）：
	public static final String sms_07 = "{0}家人您好！您的商家申请已经通过审核，恭喜您正式成为认证商家，请及时登录官网查看";

	// 8、商家申请驳回（站内信提醒）：
	public static final String sms_08 = "{0}家人您好！,您商家申请未能通过审核，请您登录个人中心查看具体原因！如有疑问请查看" + app_name + "帮助中心或拨打" + app_tel;

	// 9、店铺新订单：（站内信提醒）
	public static final String sms_09 = "{0}家人您好！,您有一笔新的订单，请及时处理！";

	// 10、认证会员新订单：（站内信提醒）
	public static final String sms_10 = "{0}家人您好！,您有一笔新的订单，请及时处理！";

	// 11、区域合伙人通过（站内信提醒）：
	public static final String sms_11 = "{0}家人您好！，您的合伙人申请已经通过审核，恭喜您成为{1}区域合伙人！";

	// 12、区域合伙人驳回（站内信提醒）
	public static final String sms_12 = "{0}家人您好！，您申请的区域合伙人请求未被通过，如有疑问请查看" + app_name + "帮助中心或拨打" + app_tel;

	// 18、提现驳回（短信提醒）
	public static final String sms_18 = "{0}家人您好！您的本次提现未能成功，请联系客服了解详情";

	// 19、提现成功（短信提醒+站内信提醒）
	public static final String sms_19 = "{0}家人您好！您提现的{1}元已经成功到账，请及时查收！";

	// 20、异常订单提醒（短信提醒）
	public static final String sms_20 = "系统管理员您好！出现异常订单，请立即登录系统查看！异常订单号：{0}";

	// 20、rfid退回发送消息
	public static final String sms_21 = "亲，您好！标签回收成功，恭喜你获得{0}余额，可以登录微信端进行查看！";

	// 1、订单失效提醒1
	public static final String sms_yh_01 = "亲，您在" + app_name + "订单号为{0}的订单，将于明天失效！请在有效期内及时消费！";

	// 退款发送提醒
	public static final String sms_tk = "用户{0}，订单号{1}，{2}秒已申请退款，请及时登录账户查看!";

	// 9、店铺发货提醒：（短信信提醒）
	public static final String sms_fh = "亲，您有一笔订单超过12小时未发货，请及时处理！";

	public static final String sms_22 = "亲，${message}";

	public static final String sms_23 = "亲，${message}";

	// 9、店铺新订单：（站内信提醒）
	public static final String sms_24 = "{0}家人您好！,您有一笔新的线下活动订单，请及时处理！";

}
