package de.slag.webgui.basic;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.slag.basic.model.Token;
import de.slag.webgui.basic.call.builder.ConfigCallBuilder;
import de.slag.webgui.basic.call.builder.LoginCallBuilder;
import de.slag.webgui.basic.call.builder.RunDefaultCallBuilder;
import de.slag.webgui.basic.call.builder.SecureLoginCallBuilder;

@ManagedBean
@SessionScoped
public class IndexController {

	private PropertiesSupport propertiesSupport = PropertiesSupport.getInstance();

	private String result;

	public String getVersion() {
		return IndexController.class.getPackage().getImplementationVersion();
	}

	public void submit() throws Exception {
		result = null;
		final Properties properties = propertiesSupport.getProperties();
		final List<String> collect = properties.keySet().stream().map(key -> (String) key)
				.map(key -> key + ": " + properties.getProperty(key)).collect(Collectors.toList());

		final List<String> results = new ArrayList<>();
		results.add("---CONFIGRURATION---");
		results.addAll(collect);

		results.add("---LOGIN---");
		final Token call = new SecureLoginCallBuilder(() -> propertiesSupport.getProperties()).build().call()
				.orElseThrow(() -> new RuntimeException("no token recieved"));
		
		final String tokenString = call.getTokenString();
		results.add("token: " + tokenString);

		propertiesSupport.getProperties().put(PropertiesSupplier.FRONTEND_CURRENT_TOKEN, tokenString);

		results.add("---PUT CONFIG---");
		final String resultConfig = new ConfigCallBuilder(() -> propertiesSupport.getProperties()).build().call();
		results.add("result config: " + resultConfig);

		results.add("---RUN DEFAULT---");
		final String runDefaultResult = new RunDefaultCallBuilder(() -> propertiesSupport.getProperties()).build()
				.call();
		results.add("result run default: " + runDefaultResult);

		results.add("---FINISH---");
		result = String.join("\n", results);
	}

	public String getResult() {
		return result;
	}

}
