package de.slag.webgui.basic.call.builder;

import java.util.Objects;

import javax.ws.rs.core.MediaType;

import de.slag.basic.model.Token;
import de.slag.webgui.basic.BasicWebTargetCall;
import de.slag.webgui.basic.BasicWebTargetCallBuilder;
import de.slag.webgui.basic.Builder;
import de.slag.webgui.basic.PropertiesSupplier;
import de.slag.webgui.basic.call.LoginCall;

public class LoginCallBuilder extends AbstractBasicCallBuilder implements Builder<LoginCall> {

	public LoginCallBuilder(PropertiesSupplier propertiesSupplier) {
		super(propertiesSupplier);
	}

	public LoginCall build() {
		final String backendUrl = getPropertiesSupplier().getBackendUrl();
		Objects.requireNonNull(backendUrl,
				String.format("property not setted: '%s'", PropertiesSupplier.FRONTEND_BACKEND_URL));
		
		final String user = getPropertiesSupplier().getUser();
		Objects.requireNonNull(user,
				String.format("property not setted: '%s'", PropertiesSupplier.FRONTEND_USER));
		
		final String password = getPropertiesSupplier().getPassword();
		Objects.requireNonNull(password,
				String.format("property not setted: '%s'", PropertiesSupplier.FRONTEND_PASSWORD));

		final BasicWebTargetCall loginCall = new BasicWebTargetCallBuilder().withTarget(backendUrl)
				.withEndpoint("/login").withAcceptedResponseType(MediaType.APPLICATION_JSON)
				.addQueryParam("username", user).addQueryParam("password", password).build();

		return new LoginCall() {

			@Override
			public Token call() throws Exception {
				return loginCall.call().readEntity(Token.class);
			}
		};
	}
}
