package com.ebiz.webapp.web;

/**
 * @author Wu,Yang
 */
public class WEIXINSMS {

	// 微信模板id:订单支付成功，商家提示信息======1
	public static final String weixin_orderSucces_sms_entp = "5-4lhrekYNE00I7csfpfYEV8cKw7lHcoYXPH4wJ96X0";

	// 详细内容 {{first.DATA}}
	// 订单号：{ keyword1.DATA}
	// 订单金额：{keyword2.DATA元}
	// 买家：{keyword3.DATA}
	// 订单状态：{keyword5.DATA}
	// {{remark.DATA}}
	// 在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
	// 示例：
	// 您好，你的店铺内新增一笔支付订单，请及时处理发货。
	// 订单号：1231456
	// 订单金额：13元
	// 买家：小王子
	// 订单状态: 已支付
	// 如有需要，请及时跟买家联系

	// 微信模板id:订单支付成功，客户收到的提示信息======2
	public static final String weixin_PaySucces_sms_userinfo = "u39_v3r_DyaanVvajC3bPONZR63-TeLEYmu4llenHKs";

	// 详细内容：{{first.DATA}}
	// 商品信息：{keyword2.DATA}
	// 支付金额：{keyword3.DATA元}
	// {{remark.DATA}}
	// 在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
	// 内容示例
	// 尊敬的会员【xxx】，您好，您的订单已支付成功，我们将尽快为您发货。
	// 商品信息：洗面奶
	// 支付金额：13元
	// 详情进入会员中心查看。

	// 微信模板id:订单支付成功，代言人收到的信息========3
	public static final String weixin_orderSucces_sms_spokesman = "onjxKKN96glR7uuyHznsItk43W6pffFV019AgIKzGEc";

	// 详细内容：{{first.DATA}}
	// 订单编号：{ keyword1.DATA }
	// 订单金额：{keyword2.DATA}
	// 预计收益：{keyword3.DATA元}
	// {{remark.DATA}}
	// 在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
	// 内容示例
	// 尊敬的会员【XXX】您好，恭喜您获得一笔新的订单代言收益，代言收益将会在订单完成后到账。
	// 订单编号：1231456
	// 订单金额：10元
	// 预计收益：2元
	// 请之后留意你的收益信息

	// 微信模板id:订单发货成功，客户收的的信息========4
	public static final String weixin_orderSucces_sms_ordersShipment = "6vJ716vsyueQcYAmyAZAxZAe4X8D5C3ifHyTIMxZdWE";

	// 详细内容：{{first.DATA}}
	// 订单编号：{ keyword1.DATA }
	// 物流公司：{ keyword2.DATA }
	// 物流单号：{ keyword3.DATA }
	// {{remark.DATA}}

	// 在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
	// 内容示例
	// 尊敬的会员【XXX】您好，，您在九个挑夫商城购买的宝贝已经发货，请注意及时查收。。
	// 订单编号：1231456
	// 物流公司：京东
	// 物流单号：3401212
	// 详情进入会员中心查看。

	// 微信模板id:代言费到账=======5
	public static final String weixin_sms_endorsements = "7BoIRpFsMG1fMlgx7W7hc_u3t6Uo8Px5eU04mTaEsGA";

	// 详细内容：{{first.DATA}}
	// 到账时间：{ keyword1.DATA }
	// 到账金额：{ keyword2.DATA }
	// 收益产品：{ keyword3.DATA }
	// {{remark.DATA}}
	// 在发送时，需要将内容中的参数（{{.DATA}}内为参数）赋值替换为需要的信息
	// 内容示例
	// 尊敬的会员【XXX】您好，您有一笔收益已经到账
	// 到账时间：2018年3月21日
	// 到账金额：10元
	// 收益产品：洗面奶
	// 详情进入我的钱包查看。

	// 微信模板id:退款到账，客户 =======6
	public static final String weixin_sms_refund = "JXf6i7RQAPQ-hcUQVXYDeUT3YC1j1onkD0BJoFWVYLE";

	// 详细内容：{{first.DATA}}
	// 退款金额：{ keyword1.DATA }
	// 商品信息：{ keyword2.DATA }
	// 订单编号：{ keyword3.DATA }
	// {{remark.DATA}}
	// 示例
	// 详细内容：你有一笔新的退款到账，请查收
	// 退款金额：23元
	// 商品信息：洗面奶
	// 订单编号：4643164653
	// {{remark.DATA}}
	// 详情进入我的钱包查看。

	// 成为会员通知 =====7
	public static final String weixin_sms_vipFrist = "C1KfORq6j8Tf7OPZdy-ZRIdqjUBSncOZDvjXV0nhOY0";

	// 详细内容：{{first.DATA}}
	// 会员号：{ keyword1.DATA }
	// 商户地址：{ keyword2.DATA }
	// 会员信息：{ keyword3.DATA }
	// 会员手机号：{ keyword4.DATA }
	// 有效期：{ keyword1.DATA }
	// {{remark.DATA}}
	// 示例
	// 恭喜你，已成为九个挑夫的会员
	// 会员号：123
	// 商户地址：九个挑夫店铺
	// 会员信息：王小兵
	// 会员手机号：18895664563
	// 有效期：2019.3.21
	// 详情请进入店铺咨询

	// 商品审核成功通知 =====8
	public static final String weixin_sms_goodsAuditSuccess = "cdv1DFQSrGPwsMwCoIhW8gvbdN2axXXUYzeamL0fpHs";

	// 详细内容：{{first.DATA}}
	// 商品名称：{ keyword1.DATA }
	// 审核时间：{ keyword2.DATA }
	// {{remark.DATA}}
	// 示例
	// 你的商品通过审核了哦
	// 商品名称：{ keyword1.DATA }
	// 审核时间：{ 2018年3月21日17:45:20 }
	// 如有疑问，请拨打0539-13123456

	// 商品审核失败通知 =====9
	public static final String weixin_sms_goodsAuditFailure = "2mGUoBrLSFj-WKU-J1fHTa5p5aZ_Nd4oHvgaF-cI9T4";

	// 详细内容：{{first.DATA}}
	// 商品名称：{ keyword1.DATA }
	// 失败原因：{ keyword2.DATA }
	// {{remark.DATA}}
	// 示例
	// 你的商品没有通过审核哦，麻烦你尽快去修改
	// 商品名称：{ keyword1.DATA }
	// 失败原因：{ keyword2.DATA }
	// 若你想了解更多详情，可点击进入

	// 会员到期提醒 =====10
	public static final String weixin_sms_vipEnd = "FOZ1iDZzhoyl5RBgrTfzZQx4iQJAzaRSZeHZ5JgtKag";

	// 详细内容：{{first.DATA}}
	// 你的{{name.DATA}}有效期至{{expDate.DATA}}
	// {{remark.DATA}}
	// 示例
	// 你好，你的会员即将到期，请你注意
	// 你的九个挑夫会员有效期至2019年3.21,
	// 备注，请尽快续费，防止过期将不能继续享受会员权益

	// 实名认证审核通知 =====11
	public static final String weixin_sms_realNameAudit = "cCBRG48Ku6emg03j4QRV4FS4BpT-bUwqQJ9IH_L9Z2s";

	// 详细内容：{{first.DATA}}
	// 审核状态：{ keyword1.DATA }
	// 操作时间：{ keyword2.DATA }
	// {{remark.DATA}}
	// 示例
	// 你的实名认证已通过，请及时查看
	// 审核状态：审核通过
	// 操作时间：2018年3月21日
	// 感谢你的支持，祝你购物愉快

	// 退款申请驳回通知 =====12
	public static final String weixin_sms_refundMoneyFailure = "buIe1i-Zs3MMoXdSzAWufqg3cAbXz0udYAWvnQQ5t5Y";

	// {{first.DATA}}
	// 退款金额：{ keyword1.DATA }
	// 商品详情：{ keyword2.DATA }
	// 订单编号：{ keyword3.DATA }
	// {{remark.DATA}}
	// 示例
	// 你的退款申请被商家驳回，请与商家协商沟通解决
	// 退款金额：￥23元
	// 商品详情：洗面奶
	// 订单编号：134563

	// 资产变动通知 =====13
	public static final String weixin_sms_modifyAccontMoney = "Ry0bvZsvSCK3USw5BcPrnUafe1I-TZI3L9nKktqOE1E";

	// {{first.DATA}}
	// 交易时间：{ keyword1.DATA }
	// 交易类型：{ keyword2.DATA }
	// 交易金额：{ keyword3.DATA }
	// 交易渠道：{ keyword4.DATA }
	// 订单编号：{ keyword5.DATA }
	// {{remark.DATA}}
	// 示例
	// 你的账户1889562345发生了以下的资金变动
	// 交易时间：2018年3.21 18:23:21
	// 交易类型：消费
	// 交易金额：￥23元
	// 交易渠道：九个挑夫
	// 订单编号：123456748

}
