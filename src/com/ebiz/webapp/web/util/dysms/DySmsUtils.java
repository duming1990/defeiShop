package com.ebiz.webapp.web.util.dysms;

import sun.util.logging.resources.logging;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.ebiz.webapp.web.Keys;

public class DySmsUtils{
	//产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

	public static SendSmsResponse sendMessage(String message, String mobile,String templateCode){
		SendSmsResponse sendSmsResponse = null;
		try {
			//可自助调整超时时间
	        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
	        System.setProperty("sun.net.client.defaultReadTimeout", "10000");
	
	        //初始化acsClient,暂不支持region化
	        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", Keys.ali_access_key_id, Keys.ali_access_key_secret);
	        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
	        IAcsClient acsClient = new DefaultAcsClient(profile);
	
	        //组装请求对象-具体描述见控制台-文档部分内容
	        SendSmsRequest request = new SendSmsRequest();
	        //必填:待发送手机号
	        request.setPhoneNumbers(mobile);
	        //必填:短信签名-可在短信控制台中找到
	        request.setSignName(Keys.dysms_sign_name);
	        //必填:短信模板-可在短信控制台中找到
	        request.setTemplateCode(templateCode);
	        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
	        request.setTemplateParam(message);
	
	        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
	        //request.setSmsUpExtendCode("90997");
	
	        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
	        //request.setOutId("yourOutId");
	
	        //hint 此处可能会抛出异常，注意catch
        	sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("aliyun短信异常："+e.getMessage());
		}
        return sendSmsResponse;
        
	}
}