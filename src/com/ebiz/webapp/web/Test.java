package com.ebiz.webapp.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Test {
	public static void main(String[] args) {
		System.out.println("--------------*************");
		// **************************敏感词 start*****************************
		// String s = "你是逗比吗？ｆｕｃｋ！fUcK,你竟然用法轮功，法@!轮!%%%功";
		// System.out.println("解析问题 : " + s);
		// System.out.println("解析字数 : " + s.length());
		// String re;
		// long nano = System.nanoTime();
		// re = WordFilter.doFilter(s);
		// nano = (System.nanoTime() - nano);
		// System.out.println("解析时间 : " + nano + "ns");
		// System.out.println("解析时间 : " + nano / 1000000 + "ms");
		// System.out.println("解析结果 : " + re);
		// System.out.println();
		//
		// nano = System.nanoTime();
		// System.out.println("是否包含敏感词： " + WordFilter.isContains(s));
		// nano = (System.nanoTime() - nano);
		// System.out.println("解析时间 : " + nano + "ns");
		// System.out.println("解析时间 : " + nano / 1000000 + "ms");
		//
		// System.out.println("GMT时间 : " + new Date().getTime());
		// System.out.println(DateTools.getStringDate(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss+08:00"));
		// **************************敏感词 end*****************************
		try {
			// String appKey = "kop8fdbf4f6342e88b9dd91ba39bf0fc";
			// String appSec = "kop04c2a790f438881dee22f039f9335";
			// boolean testEnv = true;
			// KameilaService kameilaService = new KameilaService(appKey,appSec,testEnv);
			// KoalaResult<List<ProductSku>> result = kameilaService.getProductList();
			// if(result.getCode() == 200){//
			// System.out.println("卡美啦商品列表接口调用成功: " + result.getData().size());
			// }else {
			// System.out.println("卡美啦商品列表接口调用失败： " + result.getMsg());
			// }

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// **************************水印 start*****************************
		// CodeCreator creator = new CodeCreator();
		// CodeModel info = new CodeModel();
		// info.setWidth(400);
		// info.setHeight(400);
		// info.setFontSize(24);
		// info.setContents("http://www.sohu.com");
		// info.setContents("万水千山只等闲\n小荷才楼尖尖角\n谁家新燕啄春泥\n无边光景一时新\n万水千山只等闲\n小荷才楼尖尖角");
		// info.setLogoFile(new File("F:/jdimgs/6.jpg"));
		// info.setDesc("你怎么会知道我的苦，我是一页孤舟，漂流在尘世中asdfsaf33333ａｂＣＤ1234567890");
		// info.setLogoDesc("一叶浮萍归大海，adsasfbhtjg人生何处不相逢");
		// info.setLogoDesc("一叶浮萍");
		// creator.createCodeImage(info, "F:/jdimgs/dest." + info.getFormat());
		// **************************水印 end*****************************

//		HttpClient httpclient = new HttpClient();
//		PostMethod post = new PostMethod("http://api.jd.yuan.cn/account/sign/in");
//		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//		post.setRequestHeader("Content-type", "application/json; charset=utf-8");
//		post.setRequestHeader("X-YUAN-APPKEY", Keys.jd_yuan_appkey);
//
//		StringBuffer accountJson = new StringBuffer("");
//		String appkey = Keys.jd_yuan_appkey;
//		String appsecret = Keys.jd_yuan_secret;
//		String timestamp = String.valueOf(new Date().getTime());
//		String signature = EncryptUtilsV2.MD5Encode(
//				"appkey=" + appkey + "&appsecret=" + appsecret + "&timestamp=" + timestamp + "#").toLowerCase();
//		accountJson.append("{");
//		accountJson.append("\"appkey\":\"" + appkey + "\"");
//		accountJson.append(",\"timestamp\":" + timestamp + "");
//		accountJson.append(",\"signature\":\"" + signature + "\"");
//		accountJson.append("}");
//		post.setRequestBody(accountJson.toString());
//		try {
//			httpclient.executeMethod(post);
//			String info = new String(post.getResponseBody(), "utf-8");
//			System.out.println("accountJson：" + accountJson.toString());
//			System.out.println("登陆信息：" + info);
//		} catch (HttpException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("登陆信息111：" + e.getMessage());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("登陆信息222：" + e.getMessage());
//		}

		// SendSmsResponse sendSmsResponse = DySmsUtils.sendMessage("{\"code\":\""+"123456"+"\"}", "18756951507",
		// SmsTemplate.sms_01);

//		StringBuffer message = new StringBuffer("{\"code\":\""+"12345678"+"\",");
//		message.append("\"shop\":\""+"太湖县九个挑夫运营中心"+"\",");
//		message.append("\"order\":\""+"201806121053491670"+"\",");
//		String str = "安徽省安庆市太湖县晋熙镇外环路文博园后门根雕馆后面太湖恩源科技有限公司";
//		message.append("\"address\":\""+(str.length()>20?str.substring(0,20):str)+"\"");
//		message.append("}");
//		SendSmsResponse sendSmsResponse = DySmsUtils.sendMessage(message.toString(), "18756951507", SmsTemplate.sms_04_02);
//		System.out.println("==============="+sendSmsResponse.getCode());
//		System.out.println("===============msg:"+sendSmsResponse.getMessage());

//		try {
//			ImageChangeSize.compressImage("F://107b6ba2-52bf-4302-9583-b696ddfdce0f.jpg", 414, 233);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpResponse response = null;
//
//        String url = "http://39.106.161.49:8080/mobile/base/sendmsg?user_id=109&totel=18756951507";
//        System.out.println("SCANER:"+url);
//        HttpPost http_post = new HttpPost(url);
//        try {
//            response = httpclient.execute(http_post);
//            System.out.println("httpclient:statusCode"+"httpclient执行完毕");
//            int statusCode = response.getStatusLine().getStatusCode();
//            System.out.println("httpclient:statusCode"+ String.valueOf(statusCode));
//            if (response.getEntity() != null) {
//                StringBuilder sb = new StringBuilder();
//                InputStream inputStream = response.getEntity().getContent();
//
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(inputStream));
//                String s = reader.readLine();
//                for (; s != null; s = reader.readLine()) {
//                    sb.append(s);
//                }
//                System.out.println("httpclient"+sb.toString());
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("httpclient:Exception"+ e.getMessage());
//        }

//		try {
//			FtpUtils.uploadFile("/www/stopwd.txt", new File("F:\\PythonUpdate\\ninePorters\\stopwd.txt"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println(e.getMessage());
//		}

		try {
			String siteUrl = "http://taihuwrcs.9tiaofu.com/service/BalanceDeskService.do?method=login";
			siteUrl = siteUrl + "&user_name=" + "ADMIN" + "&password=" + "AD";
			System.out.println("LOGIN:账号密码验证");
			URL url = new URL(siteUrl);
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setRequestMethod("POST");
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestProperty("Accept-Charset", "UTF-8");
			httpUrlConnection.connect();
			if (httpUrlConnection.getResponseCode() == 200) {
				// 获取输入流
				InputStream inputStream = httpUrlConnection.getInputStream();
				InputStreamReader ir = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(ir);
				String readerLine = "";
				StringBuffer stringBuffer = new StringBuffer("");// 中转变量
				while ((readerLine = bufferedReader.readLine()) != null) {
					stringBuffer.append(readerLine);
				}
				System.out.println("=========LOGIN RESULT:" + stringBuffer.toString());
				bufferedReader.close();
				ir.close();
				inputStream.close();
				httpUrlConnection.disconnect();
			} else {
				System.out.println("LOGIN请求失败");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
			System.out.println("LOGIN URLException:" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("LOGIN IOException:" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("LOGIN Exception:" + e.getMessage());
		}

	}

}