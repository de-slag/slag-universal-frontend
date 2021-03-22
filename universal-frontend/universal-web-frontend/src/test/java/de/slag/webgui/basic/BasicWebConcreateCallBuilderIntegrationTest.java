package de.slag.webgui.basic;

import java.util.Properties;
import java.util.concurrent.Callable;

import de.slag.basic.model.Token;
import de.slag.webgui.basic.call.builder.ConfigCallBuilder;
import de.slag.webgui.basic.call.builder.RunDefaultCallBuilder;
import de.slag.webgui.basic.call.builder.SecureLoginCallBuilder;

public class BasicWebConcreateCallBuilderIntegrationTest {

	public static void main(String[] args) {
		new Runner().run();
	}
}

class Runner implements Runnable, BasicWebGuiIntegrationTest {

	private Properties properties = new Properties();

	public Runner() {
		properties.putAll(loadDefaultProperties());
	}

	@Override
	public void run() {
		final Token token = call(new SecureLoginCallBuilder(() -> properties).build()).get();

		final String tokenString = token.getTokenString();
		if (tokenString == null) {
			throw new RuntimeException();
		}

		properties.put(PropertiesSupplier.FRONTEND_CURRENT_TOKEN, tokenString);

		final String call = call(new ConfigCallBuilder(() -> properties).build());
		call.getClass();
		

		final String defaultResult = call(new RunDefaultCallBuilder(()->properties).build());
		defaultResult.getClass();
		

	}

	private <T> T call(Callable<T> callable) {
		try {
			return callable.call();
		} catch (Exception e) {
			throw new IntegrationTestException(e);
		}
	}

	class IntegrationTestException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public IntegrationTestException(Throwable e) {
			super(e);
		}

	}
}
