package de.slag.webgui.basic.call.builder;

import java.util.Optional;

import de.slag.basic.model.Token;
import de.slag.webgui.basic.Builder;
import de.slag.webgui.basic.PropertiesSupplier;
import de.slag.webgui.basic.call.LoginCall;
import de.slag.webgui.basic.call.SecureLoginCall;

public class SecureLoginCallBuilder implements Builder<SecureLoginCall> {

	private final PropertiesSupplier propertiesSupplier;

	public SecureLoginCallBuilder(PropertiesSupplier propertiesSupplier) {
		super();
		this.propertiesSupplier = propertiesSupplier;
	}

	@Override
	public SecureLoginCall build() {
		final LoginCall build = new LoginCallBuilder(propertiesSupplier).build();
		return new SecureLoginCall() {

			@Override
			public Optional<Token> call() throws Exception {
				final Token token = build.call();
				if (token == null || token.getTokenString() == null) {
					return Optional.empty();
				}

				return Optional.of(token);
			}
		};
	}

}
