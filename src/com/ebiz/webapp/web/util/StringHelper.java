package com.ebiz.webapp.web.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ebiz.webapp.domain.BaseClass;
import com.ebiz.webapp.domain.BaseProvince;
import com.ebiz.webapp.domain.HelpModule;
import com.ebiz.webapp.domain.SysModule;
import com.ebiz.webapp.service.Facade;
import com.ebiz.webapp.web.Keys;

/**
 * @author Jin,QingHua
 */
public class StringHelper {

	/**
	 * @param list list of object SysModule
	 * @return String
	 */
	public static String getTreeNodesFromSysModuleList(List<SysModule> sysModuleList) {

		StringBuffer sb = new StringBuffer();

		for (SysModule sysModule : sysModuleList) {
			String _mod_id = String.valueOf(sysModule.getMod_id());
			String _par_id = String.valueOf(sysModule.getPar_id());
			if ("0".equals(_par_id)) {
				_par_id = "-1";
			}
			String _text = StringUtils.replace(sysModule.getMod_name(), ":", "&#58;");
			if (StringUtils.isEmpty(_text)) {
				continue;
			}
			String _hint = _text;
			String _url = StringUtils.replace(sysModule.getMod_url(), ":", "&#58;");

			sb.append("\ntree.nodes[\"").append(_par_id).append("_").append(_mod_id).append("\"]=\"");
			sb.append("text:").append(_text).append(";");
			if (_hint.length() > 0) {
				sb.append("hint:").append(_hint).append(";");
			}
			if ((_url != null) && (_url.length() > 0)) {
				sb.append("url:").append(_url).append(";");
			} else {
				sb.append("url:").append("Frames.do?method=main").append(";");
			}

			sb.append("data:").append("mod_id=").append(_mod_id).append(";");

			sb.append("\";");
		}
		return sb.toString();
	}

	public static String getjQzTreeNodesFromSysModuleList(List<SysModule> sysModuleList) {

		StringBuffer sb = new StringBuffer("[");
		String target = "mainFrame";
		for (SysModule sysModule : sysModuleList) {
			String mod_id = String.valueOf(sysModule.getMod_id());
			String par_id = String.valueOf(sysModule.getPar_id());
			String mod_url = sysModule.getMod_url();
			String mod_name = sysModule.getMod_name();
			if (StringUtils.isBlank(mod_url)) {
				mod_url = "Frames.do?method=main";
			}
			if (!"0".equals(par_id)) {
				// { mod_id:1, par_id:0, name:"基本功能演示", "url":"", "target":"mainFrame"},
				sb.append("{");
				sb.append("\"mod_id\":").append(mod_id).append(",");
				sb.append("\"par_id\":").append(par_id).append(",");
				sb.append("\"name\":").append("\"").append(mod_name).append("\"").append(",");
				String linkUrl = "?";
				if (StringUtils.contains(mod_url, linkUrl)) {
					linkUrl = "&";
				}
				sb.append("\"url\":").append("\"").append(mod_url).append(linkUrl).append("mod_id=").append(mod_id)
						.append("\"").append(",");
				sb.append("\"target\":").append("\"").append(target).append("\"");
				sb.append("},");
			}
		}
		if (sb.length() > 1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("];");
		return sb.toString();
	}

	public static String getTreeNodesFromBaseClassList(Facade facade, Integer cls_scope, boolean isCanSelectedAll) {
		StringBuffer sb = new StringBuffer("[");
		String target = "_self";
		BaseClass basePdClass = new BaseClass();
		basePdClass.setIs_del(0);// 0：未删除
		basePdClass.setCls_scope(cls_scope);
		// basePdClass.getMap().put("no_have_self", "1");
		List<BaseClass> basePdClassList = facade.getBaseClassService().getBaseClassList(basePdClass);
		for (BaseClass entity : basePdClassList) {
			String cls_id = String.valueOf(entity.getCls_id());
			String par_id = String.valueOf(entity.getPar_id());
			String mod_url = "#";
			String cls_name = StringUtils.replace((entity.getCls_name()).trim(), "\r\n", "");
			sb.append("{");
			sb.append("\"cls_id\":").append(cls_id).append(",");
			sb.append("\"par_id\":").append(par_id).append(",");
			sb.append("\"cls_level\":").append(entity.getCls_level()).append(",");
			sb.append("\"name\":").append("\"").append(cls_name).append("\"").append(",");
			sb.append("\"is_lock\":").append("\"").append(entity.getIs_lock()).append("\"").append(",");
			if (entity.getCls_level() == 0 && !isCanSelectedAll) {
				sb.append("\"nocheck\":").append(true).append(",");
			}
			sb.append("\"url\":").append("\"").append(mod_url).append("\"").append(",");
			sb.append("\"target\":").append("\"").append(target).append("\"");
			sb.append("},");
		}

		if (sb.length() > 1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}
		sb.append("]");
		return sb.toString();
	}

	public static String getTreeNodesFromBaseClassList(Facade facade, Integer cls_scope, boolean isCanSelectedAll,
			boolean isNoCheckFar) {
		StringBuffer sb = new StringBuffer("[");
		String target = "_self";
		BaseClass basePdClass = new BaseClass();
		basePdClass.setIs_del(0);// 0：未删除
		basePdClass.setCls_scope(cls_scope);
		// basePdClass.getMap().put("no_have_self", "1");
		List<BaseClass> basePdClassList = facade.getBaseClassService().getBaseClassList(basePdClass);
		for (BaseClass entity : basePdClassList) {
			if (isNoCheckFar && entity.getCls_id().intValue() == 1) {
				continue;
			}
			String cls_id = String.valueOf(entity.getCls_id());
			String par_id = String.valueOf(entity.getPar_id());
			String mod_url = "#";
			String cls_name = StringUtils.replace((entity.getCls_name()).trim(), "\r\n", "");
			sb.append("{");
			sb.append("\"cls_id\":").append(cls_id).append(",");
			sb.append("\"par_id\":").append(par_id).append(",");
			sb.append("\"cls_level\":").append(entity.getCls_level()).append(",");
			sb.append("\"name\":").append("\"").append(cls_name).append("\"").append(",");
			sb.append("\"is_lock\":").append("\"").append(entity.getIs_lock()).append("\"").append(",");
			if (!entity.getCls_level().equals(Integer.valueOf(Keys.app_cls_level))
					&& !(entity.getCls_level().equals(Integer.valueOf(Keys.app_cls_level))) && !isCanSelectedAll
					&& isNoCheckFar) {
				sb.append("\"nocheck\":").append(true).append(",");
			}
			sb.append("\"url\":").append("\"").append(mod_url).append("\"").append(",");
			sb.append("\"target\":").append("\"").append(target).append("\"");
			sb.append("},");
		}

		if (sb.length() > 1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}

		sb.append("]");
		return sb.toString();
	}

	public static String getNaviStringFromSysModuleList(List<SysModule> sysModuleList) {
		return getNaviStringFromSysModuleList(sysModuleList, " -&gt; ");
	}

	public static String getNaviStringFromSysModuleList(List<SysModule> sysModuleList, String separator) {
		ArrayList<String> arrayList = new ArrayList<String>();
		for (SysModule sysModule : sysModuleList) {
			arrayList.add(sysModule.getMod_name());
		}
		return (StringUtils.join(arrayList, separator));
	}

	/**
	 * @author Wu,Yang
	 * @version 2009-11-09 15:34
	 */
	public static String getAreaStringFromBaseProvince(List<BaseProvince> baseProvinceList) {
		return getAreaStringFromBaseProvince(baseProvinceList, "");
	}

	/**
	 * @author Wu,Yang
	 * @version 2009-11-09 15:34
	 */
	public static String getAreaStringFromBaseProvince(List<BaseProvince> baseProvinceList, String separator) {
		ArrayList<String> arrayList = new ArrayList<String>();
		for (BaseProvince baseProvince : baseProvinceList) {
			if (!"0".equals(String.valueOf(baseProvince.getP_index()))) {
				arrayList.add(baseProvince.getP_name());
			}
		}
		return (StringUtils.join(arrayList, separator));
	}

	/**
	 ********************* 帮助中心 Begin************************
	 */

	/**
	 * @author Wu,Yang
	 */
	public static String getTreeNodesForHelpModule(List<HelpModule> sysModuleList) {
		StringBuffer sb = new StringBuffer();

		for (HelpModule sysModule : sysModuleList) {
			String _mod_id = String.valueOf(sysModule.getH_mod_id());
			String _par_id = String.valueOf(sysModule.getPar_id());
			if ("0".equals(_par_id)) {
				_par_id = "-1";
			}
			String _text = StringUtils.replace(sysModule.getMod_name(), ":", "&#58;");
			if (StringUtils.isEmpty(_text)) {
				continue;
			}
			String _hint = _text;
			String _url = StringUtils.replace(sysModule.getMod_url(), ":", "&#58;");

			sb.append("\ntree.nodes[\"").append(_par_id).append("_").append(_mod_id).append("\"]=\"");
			sb.append("text:").append(_text).append(";");
			if (_hint.length() > 0) {
				sb.append("hint:").append(_hint).append(";");
			}
			if ((_url != null) && (_url.length() > 0)) {
				sb.append("url:").append(_url).append(";");
			} else {
				sb.append("url:").append("HelpInfo.do?method=helpMain").append(";");
			}

			sb.append("data:").append("h_mod_id=").append(_mod_id).append(";");
			sb.append("\";");
		}
		return sb.toString();

	}

	/**
	 * @author Wu,Yang
	 */
	public static String getTreeNodesFromHelpModuleList(List<HelpModule> helpModuleList) {

		StringBuffer sb = new StringBuffer();

		for (HelpModule helpModule : helpModuleList) {

			String _id = String.valueOf(helpModule.getH_mod_id());
			String _par_id = String.valueOf(helpModule.getPar_id());

			if ("0".equals(_par_id)) {
				_par_id = "-1";
			}
			String _text = StringUtils.replace(helpModule.getMod_name(), ":", "&#58;");
			if (StringUtils.isEmpty(_text)) {
				continue;
			}
			String _hint = _text;
			// String _url = StringUtils.replace(sysModule.getMod_url(), ":",
			// "&#58;");

			sb.append("\ntree.nodes[\"").append(_par_id).append("_").append(_id).append("\"]=\"");
			sb.append("text:").append(_text).append(";");
			if (_hint.length() > 0) {
				sb.append("hint:").append(_hint).append(";");
			}

			sb.append("url:").append("HelpModule.do").append(";");

			sb.append("data:").append("method=edit");
			sb.append("&par_id=").append(_par_id);
			sb.append("&h_mod_id=").append(_id).append(";");

			sb.append("\";");
		}
		return sb.toString();
	}

	/**
	 * @author Wu,Yang
	 */
	public static String getNaviStringForHelpModule(List<HelpModule> helpModuleList) {
		return getNaviStringForHelpModule(helpModuleList, " &gt; ");
	}

	/**
	 * @author Wu,Yang
	 */
	public static String getNaviStringForHelpModule(List<HelpModule> helpModuleList, String separator) {
		ArrayList<String> modNameList = new ArrayList<String>();
		for (HelpModule helpModule : helpModuleList) {
			modNameList.add(helpModule.getMod_name());
		}
		return (StringUtils.join(modNameList, separator));
	}

	/**
	 ********************* 帮助中心 End************************
	 */

	/**
	 * 获取指定的分类，并且设置可以选择的分类级别
	 * 
	 * @param facade
	 * @param cls_scope
	 * @param isCanSelectedAll
	 * @param isNoCheckFar
	 * @param level 分类级别，1 2 3开始
	 * @return
	 */
	public static String getNewsTreeNodesFromBaseClassList(Facade facade, Integer cls_scope, boolean isCanSelectedAll,
			boolean isNoCheckFar, String level) {
		if (level == null || "".equals(level) || !StringUtils.isNumeric(level)) {
			level = Keys.app_cls_level;
		}
		StringBuffer sb = new StringBuffer("[");
		String target = "_self";
		BaseClass basePdClass = new BaseClass();
		basePdClass.setIs_del(0);// 0：未删除
		basePdClass.setCls_scope(cls_scope);
		// basePdClass.getMap().put("no_have_self", "1");
		List<BaseClass> basePdClassList = facade.getBaseClassService().getBaseClassList(basePdClass);
		for (BaseClass entity : basePdClassList) {
			if (isNoCheckFar && entity.getCls_id().intValue() == 1) {
				continue;
			}
			String cls_id = String.valueOf(entity.getCls_id());
			String par_id = String.valueOf(entity.getPar_id());
			String mod_url = "#";
			String cls_name = StringUtils.replace((entity.getCls_name()).trim(), "\r\n", "");
			sb.append("{");
			sb.append("\"cls_id\":").append(cls_id).append(",");
			sb.append("\"par_id\":").append(par_id).append(",");
			sb.append("\"cls_level\":").append(entity.getCls_level()).append(",");
			sb.append("\"name\":").append("\"").append(cls_name).append("\"").append(",");
			sb.append("\"is_lock\":").append("\"").append(entity.getIs_lock()).append("\"").append(",");
			if (!entity.getCls_level().equals(Integer.valueOf(level))
					&& !(entity.getCls_level().equals(Integer.valueOf(2))) && !isCanSelectedAll && isNoCheckFar) {
				sb.append("\"nocheck\":").append(true).append(",");
			}
			sb.append("\"url\":").append("\"").append(mod_url).append("\"").append(",");
			sb.append("\"target\":").append("\"").append(target).append("\"");
			sb.append("},");
		}

		if (sb.length() > 1) {
			sb.deleteCharAt(sb.lastIndexOf(","));
		}

		sb.append("]");
		return sb.toString();
	}
}
