package de.slag.basic.backend.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.LogFactoryImpl;

import de.slag.basic.model.ConfigProperty;
import de.slag.basic.model.Token;


/**
 * This Test-Application tests the {@link BasicBackendController} endpoint direcly.
 * @author slipp
 *
 */
public class BasicBackendControllerIntegrationTest implements Runnable {

	private static final Log LOG = LogFactoryImpl.getLog(BasicBackendControllerIntegrationTest.class);

	private static final String BASE_URL = "http://localhost:18080/slag-basic-backend3";

	public static void main(String[] args) {
		new BasicBackendControllerIntegrationTest().run();
	}

	private WebTarget okWebTarget;
	private WebTarget loginWebTarget;
	private WebTarget configWebTarget;
	private WebTarget runDefaultgWebTarget;

	public BasicBackendControllerIntegrationTest() {
		okWebTarget = ClientBuilder.newClient().target(BASE_URL + "/demo/ok");
		loginWebTarget = ClientBuilder.newClient().target(BASE_URL + "/login");
		configWebTarget = ClientBuilder.newClient().target(BASE_URL + "/configproperty");
		runDefaultgWebTarget = ClientBuilder.newClient().target(BASE_URL + "/rundefault");
	}

	@Override
	public void run() {
		testOk();

		// test token
		Response response = loginWebTarget.request(MediaType.APPLICATION_JSON).get();
		Token token = response.readEntity(Token.class);
		final String tokenString = token.getTokenString();

		// test config
		ConfigProperty configProperty = new ConfigProperty();
		configProperty.setKey("abc");
		configProperty.setValue("123");

		LOG.info("test config property...");
		Response put = configWebTarget.queryParam("token", tokenString).request()
				.put(Entity.entity(configProperty, MediaType.APPLICATION_JSON));

		int status = put.getStatus();
		LOG.info("config property done. Status: " + status);

		// test run default
		LOG.info("test run default ...");
		String runDefaultResult = runDefaultgWebTarget.queryParam("token", tokenString).request(MediaType.APPLICATION_JSON)
				.get().readEntity(String.class);
		if (!runDefaultResult.endsWith("all done")) {
			throw new RuntimeException("not succesful: " + runDefaultResult);
		}
		LOG.info("run default done, result: " + runDefaultResult);

	}

	private void testOk() {
		Builder request = okWebTarget.request(MediaType.TEXT_PLAIN);
		Response response = request.get();
		String readEntity = response.readEntity(String.class);
		if (!readEntity.startsWith("ok")) {
			throw new RuntimeException();
		}
		LOG.info("test 'OK' successful.");
	}
}
