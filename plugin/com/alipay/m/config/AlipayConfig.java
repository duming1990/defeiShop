package com.alipay.m.config;

import com.ebiz.webapp.web.Keys;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。

 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {

	// ↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	// 合作身份者ID，以2088开头由16位纯数字组成的字符串
	public static String partner = Keys.alipay_partner_m;

	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串
	public static String seller_id = partner;

	public static String app_id = Keys.alipay_app_id;

	// 商户的私钥
	// 如何生成公私钥流程图：如何生成公私钥：http://help.alipay.com/support/help_detail.htm?help_id=397433&keyword=%C8%E7%BA%CE%C9%FA
	// 如何上传公钥：https://cshall.alipay.com/enterprise/cateQuestion.htm?cateType=EE&cateId=250287&pcateId=250119
	// 1、登陆支付宝商家，上传公钥rsa_public_key.pem里面的值（去掉换行）
	// 2、将rsa_private_key_pkcs8.txt，的内容拷贝到下面的private_key私钥中（去掉换行）
	public static String private_key = Keys.alipay_private_key_m;

	// 支付宝的公钥，无需修改该值
	// 重点不需要更换 大坑
	public static String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// public static String ali_public_key =
	// "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC10ymu+iDe1p/vzG7uQUsZA04eJj1DI1FAgQO/d1k6v/3OjLnf0ZJWTpS0cxDnbybc1DpdcjQpK82YfUTDgoxfOubGksdVTmAYOEk4ILn1Ujmsh4AWct1zez/4HJWsGw8ZtSHd/uRu41RWJx2gxMTfPZoS0GGdsATl4AWs1Qem2wIDAQAB";

	// 江西的
	// public static String ali_public_key_rsa2 =
	// "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1q6BmYGbbYAmqaNfh3+GVWLPOEmR4lwP/fgiQ2mwttcPuEhseKi+kYjeF/2xMbS0SAw6tpK6ULsXkSoFC18bNARfp/XvCM2N74aK7+P/JAncD+7VM2bsseiR7kt2ylVDUZ1p3zp0vqCZST8RaaewK8SKBqUMgsFslw3AYd6KwvxClgJ60oBRGHZpw2zkIeIKcEkgg1zoHP0NBVqydN0UzTBz8NAye3kID373cU8VGo1pF96Vflt2DWFa5abfCwBcnkY+V0IziA/S1Dl6VhMB8Ur4vo2c0pr8bX8VEvS525HAWkMU4qh8fBXbAIUnUDCcEEp6eNSfRt0lBcnckJ3M2QIDAQAB";

	// ↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 签名方式 不需修改
	public static String sign_type = "RSA";

	// 签名方式 不需修改
	public static String sign_type2 = "RSA2";

}
