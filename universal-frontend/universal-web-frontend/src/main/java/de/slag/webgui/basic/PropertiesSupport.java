package de.slag.webgui.basic;

import java.util.Properties;

public class PropertiesSupport {

	private static PropertiesSupport instance = new PropertiesSupport();

	public static PropertiesSupport getInstance() {
		return instance;
	}

	private PropertiesSupport() {

	}

	private Properties properties = new Properties();

	public Properties getProperties() {
		return properties;
	}

}
