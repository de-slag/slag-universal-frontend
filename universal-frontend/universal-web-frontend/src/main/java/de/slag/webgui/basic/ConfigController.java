package de.slag.webgui.basic;

import java.util.Arrays;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang.StringUtils;

@ManagedBean
@SessionScoped
public class ConfigController {

	private static final String NEWLINE = "\n";

	private static final String EQUAL_SIGN = "=";

	private PropertiesSupport propertiesSupport = PropertiesSupport.getInstance();

	private String configText;

	private String status = "created";

	public String getConfigText() {
		return configText;
	}

	public void setConfigText(String configText) {
		this.configText = configText;
	}

	public void generateDefaults() {
		if (StringUtils.isNotBlank(configText)) {
			status = "not overwritten existing config";
			return;
		}
		configText = "";
		String format = "%s" + EQUAL_SIGN + NEWLINE;
		configText += String.format(format, PropertiesSupplier.FRONTEND_BACKEND_URL);
		configText += String.format(format, PropertiesSupplier.FRONTEND_USER);
		configText += String.format(format, PropertiesSupplier.FRONTEND_PASSWORD);
	}

	public void save() {
		propertiesSupport.getProperties().clear();
		if (StringUtils.isEmpty(configText)) {
			return;
		}
		final List<String> asList = Arrays.asList(configText.split(NEWLINE));
		asList.forEach(keyValue -> {
			final String[] split = keyValue.split("=");
			if (split.length != 2) {
				return;
			}
			final String key = split[0];
			final String value = split[1];
			propertiesSupport.getProperties().put(key, value);
		});

		status = "saved";
	}

	public String getStatus() {
		return status;
	}

}
