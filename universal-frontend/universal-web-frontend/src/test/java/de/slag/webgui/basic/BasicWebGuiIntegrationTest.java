package de.slag.webgui.basic;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public interface BasicWebGuiIntegrationTest {

	default Properties loadDefaultProperties() {
		final Properties properties = new Properties();
		final InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("default.properties");
		try {
			properties.load(resourceAsStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return properties;
	}

}
