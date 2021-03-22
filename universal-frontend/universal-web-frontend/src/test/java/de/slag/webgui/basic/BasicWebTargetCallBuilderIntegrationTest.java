package de.slag.webgui.basic;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.slag.basic.model.ConfigProperty;
import de.slag.basic.model.Token;
import de.slag.webgui.basic.BasicWebTargetCallBuilder.HttpMethod;

/**
 * This test application tests a basic backend implmentation using
 * {@link BasicWebTargetCallBuilder}.
 * 
 * @author slipp
 *
 */

public class BasicWebTargetCallBuilderIntegrationTest {

	private static final Log LOG = LogFactory.getLog(BasicWebTargetCallBuilderIntegrationTest.class);

	private static final String BASIC_TARGET_URL = "http://localhost:18080/slag-basic-backend3";

	public static void main(String[] args) throws Exception {

		final BasicWebTargetCall okCall = new BasicWebTargetCallBuilder().withTarget(BASIC_TARGET_URL)
				.withEndpoint("/demo/ok").build();

		final Response okResponse = okCall.call();
		String okResponseString = okResponse.readEntity(String.class);
		if (!okResponseString.startsWith("ok")) {
			throw new RuntimeException("not successful: " + okResponseString);
		}
		LOG.info("succes: ok-test");

		final BasicWebTargetCall loginCall = new BasicWebTargetCallBuilder().withTarget(BASIC_TARGET_URL)
				.withEndpoint("/login").withAcceptedResponseType(MediaType.APPLICATION_JSON).build();

		Response loginResponse = loginCall.call();
		Token tokenEntity = loginResponse.readEntity(Token.class);
		String token = tokenEntity.getTokenString();
		if (token == null) {
			throw new RuntimeException("not succesful: " + token);
		}
		LOG.info("succes: login-test");

		ConfigProperty configProperty = new ConfigProperty();
		configProperty.setKey("abc");
		configProperty.setValue("123");

		Response configPropertyResponse = new BasicWebTargetCallBuilder().withTarget(BASIC_TARGET_URL)
				.withEndpoint("/configproperty").withMethod(HttpMethod.PUT).withEntity(configProperty).withToken(token)
				.build().call();

		int status = configPropertyResponse.getStatus();
		if (status != 200) {
			throw new RuntimeException("not succesful: put config property");
		}
		LOG.info("succes: configproperty-test");

		Response runDefaultResponse = new BasicWebTargetCallBuilder().withTarget(BASIC_TARGET_URL)
				.withEndpoint("/rundefault").withToken(token).withAcceptedResponseType(MediaType.APPLICATION_JSON)
				.build().call();

		int status2 = runDefaultResponse.getStatus();
		String runDefaultResponseString = runDefaultResponse.readEntity(String.class);
		runDefaultResponseString.getClass();

	}
}
