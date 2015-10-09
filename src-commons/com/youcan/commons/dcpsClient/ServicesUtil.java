package com.youcan.commons.dcpsClient;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import com.youcan.core.util.FileUtil;

public class ServicesUtil {
	
	/**
	 * @Title: checkDcpsService
	 * @Description: 检查DcpsService是否正常
	 * @param @param configFile
	 * @return void
	 * @throws
	 */
	@SuppressWarnings("static-method")
	public void checkDcpsService(String configFile) {
		try {
			XMLConfiguration config = new XMLConfiguration(configFile);
			@SuppressWarnings("unchecked")
			List<HierarchicalConfiguration> services = config.configurationsAt("client.service");
			if (services != null && services.size() > 0) {
				HierarchicalConfiguration sub;
				String serviceId = null;
				Class<?> c = null;
				for (int i = 0; i < services.size(); i++) {
					sub = services.get(i);
					serviceId = sub.getString("[@id]");
					try {
						c = Class.forName("com.youcan.commons.dcpsClient.services.DcpsService" + serviceId.substring(0, 1).toUpperCase() + serviceId.substring(1));
						System.out.println("found DcpsService " + c.getName());
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
	}


	/*
	 * 根据配置文件生成DcpsService的子类java文件
	 */
	@SuppressWarnings("unchecked")
	public static void buildDcpsService(String configFile) {
		try {
			XMLConfiguration config = new XMLConfiguration(configFile);
			List<HierarchicalConfiguration> services = config.configurationsAt("others.service");
			if (services != null && services.size() > 0) {
				HierarchicalConfiguration sub;
				String serviceId = null;
				String javaClassName = null;
				StringBuilder javaCode = null;
				int defaultCallBack = 0;
				boolean defaultDebug = false;
				int defaultPriority = 0;
				String defaultTemplate = "";
				String javaPackageName = "com.youcan.commons.dcpsClient.services";
				List<HierarchicalConfiguration> params;
				for (int i = 0; i < services.size(); i++) {
					sub = services.get(i);
					serviceId = sub.getString("[@id]", "default");
					defaultCallBack = sub.getInt("callBack", 0);
					defaultDebug = sub.getBoolean("debug", false);
					defaultPriority = sub.getInt("priority", 0);
					defaultTemplate = sub.getString("template", "default");

					javaClassName = "DcpsService" + serviceId.substring(0, 1).toUpperCase() + serviceId.substring(1);
					javaCode = new StringBuilder();
					javaCode.append("package ").append(javaPackageName).append(";");
					javaCode.append("import com.youcan.commons.dcpsClient.*;");
					javaCode.append("public class ").append(javaClassName).append(" extends DcpsService {");
					params = sub.configurationsAt("params.param");
					StringBuilder sbAppend = new StringBuilder();
					if (params != null && params.size() > 0) {
						HierarchicalConfiguration param;
						String paramType = null;
						String paramName = null;
						for (int j = 0; j < params.size(); j++) {
							param = params.get(j);
							paramType = param.getString("[@type]");
							paramName = param.getString(".");
							javaCode.append("private ").append(paramType).append(' ').append(paramName).append(";");
							javaCode.append("public void set").append(paramName.substring(0, 1).toUpperCase() + paramName.substring(1))
								.append('(').append(paramType).append(' ').append(paramName)
								.append("){this.").append(paramName).append('=').append(paramName).append(";}");
							sbAppend.append("sb.append(\"<").append(paramName).append(">\").append(").append(paramName).append(").append(\"</").append(paramName).append(">\");");
						}
					}
					
					javaCode.append("public ").append(javaClassName).append("() {").append("super();}");
					javaCode.append("@Override protected void setDefaultHeader() {");
					javaCode.append("header.setServiceId(\"").append(serviceId).append("\");");
					javaCode.append("header.setClientId(DcpsClientConfig.clientId);");
					javaCode.append("header.setCallBack(").append(defaultCallBack).append(");");
					javaCode.append("header.setDebug(").append(defaultDebug).append(");");
					javaCode.append("header.setPriority(").append(defaultPriority).append(");");
					javaCode.append("header.setTemplate(\"").append(defaultTemplate).append("\");");
					javaCode.append("}");
					
					javaCode.append("@Override protected StringBuffer makeReq() {StringBuffer sb = new StringBuffer();").append(sbAppend).append("return sb;}}");
					
					FileUtil.writeFile("gen/" + javaPackageName.replace('.', '/') + '/' + javaClassName + ".java", javaCode, "UTF-8");
					System.out.println("build dcpsClient service " + javaPackageName + '.' + javaClassName + ".java Successful!");
				}
			}
		} catch (ConfigurationException e) {
			System.out.println("Can't build services from configuration with error:");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String configFile = "config-file/ext-commons-dcpsClient.xml";
		buildDcpsService(configFile);
	}
}
