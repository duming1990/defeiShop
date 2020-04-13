package com.ebiz.webapp.web.listener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.validator.GenericValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseData;
import com.ebiz.webapp.domain.BaseLink;
import com.ebiz.webapp.domain.SysSetting;
import com.ebiz.webapp.service.Facade;
import com.ebiz.webapp.web.Keys;

/**
 * @author Wu,Yang
 */
public class ResourceListener implements ServletContextListener {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void contextDestroyed(ServletContextEvent sec) {
		ServletContext application = sec.getServletContext();
		application.removeAttribute("c_n");
		application.removeAttribute("html_br");
		application.removeAttribute("sys_encoding");
	}

	@Override
	public void contextInitialized(ServletContextEvent sec) {
		ServletContext servletContext = sec.getServletContext();
		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		Facade facade = (Facade) wac.getBean("facade");

		initInternalResource(sec, facade);
	}

	public void initInternalResource(ServletContextEvent sec, Facade facade) {
		ServletContext application = sec.getServletContext();
		application.setAttribute("c_n", "\n");
		application.setAttribute("html_br", "<br />");
		application.setAttribute("sys_encoding", "UTF-8");

		Properties prop = new Properties();
		try {
			prop.load(ResourceListener.class.getClassLoader().getResourceAsStream("/webapp.config.properties"));
			application.setAttribute("app_name", prop.getProperty("app_name"));
			application.setAttribute("file_domain", prop.getProperty("file_domain"));
			application.setAttribute("app_name_min", prop.getProperty("app_name_min"));
			application.setAttribute("app_domain", prop.getProperty("app_domain"));
			application.setAttribute("app_domain_no_www", prop.getProperty("app_domain_no_www"));
			application.setAttribute("app_domain_m", prop.getProperty("app_domain_m"));
			application.setAttribute("app_keywords", prop.getProperty("app_keywords"));

			// 这个app_description需要从sysSeting里面获取
			SysSetting sysSetting = new SysSetting();
			sysSetting.setTitle(Keys.APP_DESCRIPTION);
			sysSetting = facade.getSysSettingService().getSysSetting(sysSetting);
			if (null != sysSetting) {
				application.setAttribute(Keys.APP_DESCRIPTION, sysSetting.getContent());
			}
			application.setAttribute("app_copyright", prop.getProperty("app_copyright"));

			application.setAttribute("app_is_dandian", prop.getProperty("app_is_dandian"));

			application.setAttribute("app_cls_level", prop.getProperty("app_cls_level"));

			application.setAttribute("app_tel", prop.getProperty("app_tel"));
			application.setAttribute("app_fax", prop.getProperty("app_fax"));
			application.setAttribute("app_beian_no", prop.getProperty("app_beian_no"));
			application.setAttribute("app_jingying_no", prop.getProperty("app_jingying_no"));
			application.setAttribute("app_name_eg", prop.getProperty("app_name_eg"));

			application.setAttribute("app_gongan_no", prop.getProperty("app_gongan_no"));
			application.setAttribute("app_gongan_url", prop.getProperty("app_gongan_url"));
			application.setAttribute("app_addr", prop.getProperty("app_addr"));

			application.setAttribute("p_index_city", prop.getProperty("p_index_city"));
			application.setAttribute("p_index_city_name", prop.getProperty("p_index_city_name"));
			application.setAttribute("map_baidu_key", prop.getProperty("map_baidu_key"));
			application.setAttribute("map_tengxun_key", prop.getProperty("map_tengxun_key"));
			application.setAttribute("map_gaode_key", prop.getProperty("map_gaode_key"));

			application.setAttribute("quanguo_p_index", prop.getProperty("quanguo_p_index"));
			application.setAttribute("quanguo_p_name", prop.getProperty("quanguo_p_name"));

			application.setAttribute("is_zhonghui", prop.getProperty("is_zhonghui"));

			// 支付开关:是否开启支付宝、微信、银联支付
			application.setAttribute("is_open_pay_alipay", prop.getProperty("is_open_pay_alipay"));
			application.setAttribute("is_open_pay_weixin", prop.getProperty("is_open_pay_weixin"));
			application.setAttribute("is_open_pay_union", prop.getProperty("is_open_pay_union"));
			application.setAttribute("is_open_bi_dian", prop.getProperty("is_open_bi_dian"));

			// 申请支付是否成功，如果成功开放充值等功能 如果不开放 关闭普通会员的充值功能
			application.setAttribute("pay_type_is_audit_success", prop.getProperty("pay_type_is_audit_success"));

			application.setAttribute("pay_type_0_url", prop.getProperty("pay_type_0_url"));

			// CC客服ID
			application.setAttribute("cc_kefu_id", prop.getProperty("cc_kefu_id"));
			application.setAttribute("baidu_default_point", prop.getProperty("baidu_default_point"));

		} catch (IOException e) {
			e.printStackTrace();
		}

		// 用户等级以及比率
		BaseData baseData = new BaseData();
		baseData.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_200.getIndex());
		baseData.setIs_del(0);
		List<BaseData> baseData200List = facade.getBaseDataService().getBaseDataList(baseData);
		Keys.keysBaseData200List = baseData200List;

		BaseData baseData900 = new BaseData();
		baseData900.setType(Keys.BASE_DATA_ID.BASE_DATA_ID_900.getIndex());
		baseData900.setIs_del(0);
		List<BaseData> baseData900List = facade.getBaseDataService().getBaseDataList(baseData900);
		Keys.keysBaseData900List = baseData900List;

		BaseLink blk = new BaseLink();
		blk.setLink_type(20);
		blk.setIs_del(0);
		blk.getRow().setCount(16);
		List<BaseLink> baseLink20List = facade.getBaseLinkService().getBaseLinkList(blk);
		for (BaseLink bi : baseLink20List) {
			if (null != bi.getContent() && GenericValidator.isInt(bi.getContent())) {
				Integer c_id = Integer.valueOf(bi.getContent());
				BaseClass baseClassSon = new BaseClass();
				baseClassSon.setPar_id(c_id);
				baseClassSon.setIs_del(0);
				baseClassSon.setIs_show(0);
				List<BaseClass> baseClassSonList = facade.getBaseClassService().getBaseClassList(baseClassSon);
				if (null != baseClassSonList && baseClassSonList.size() > 0) {
					for (BaseClass temp : baseClassSonList) {
						BaseClass baseClassSonSon = new BaseClass();
						baseClassSonSon.setPar_id(temp.getCls_id());
						baseClassSonSon.setIs_del(0);
						baseClassSonSon.setIs_show(0);
						List<BaseClass> baseClassSonSonList = facade.getBaseClassService().getBaseClassList(
								baseClassSonSon);
						temp.getMap().put("baseClassSonSonList", baseClassSonSonList);
					}
				}
				bi.setBaseClassList(baseClassSonList);
			}
		}

		application.setAttribute(Keys.slideNavList, baseLink20List);

		String ctxService = application.getRealPath(File.separator);
		if (!ctxService.endsWith(File.separator)) {
			ctxService = ctxService + File.separator;
		}
		Keys.ctxService = ctxService;

		// 启动获取微信token
		// facade.getAutoRunService().autoGetWeixinToken();

		application.setAttribute("rmb_to_fanxianbi_rate", Keys.rmb_to_fanxianbi_rate);
	}
}
