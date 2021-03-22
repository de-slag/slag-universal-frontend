package de.slag.webgui.basic.call;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;

import de.slag.basic.model.EntityDto;
import de.slag.basic.model.Token;
import de.slag.webgui.basic.PropertiesSupplier;
import de.slag.webgui.basic.PropertiesSupport;
import de.slag.webgui.basic.call.builder.EntityCallBuilder;
import de.slag.webgui.basic.call.builder.RecieveTypesCallBuilder;
import de.slag.webgui.basic.call.builder.SecureLoginCallBuilder;

public class CallSupportImpl implements CallSupport {

	private PropertiesSupport propertiesSupport = PropertiesSupport.getInstance();

	private Token getToken() {
		final SecureLoginCall call = new SecureLoginCallBuilder(() -> propertiesSupport.getProperties()).build();
		final Optional<Token> tokenOptional = call0(call);
		return tokenOptional.orElseThrow(() -> new RuntimeException("no token recieved"));
	}

	@Override
	public Collection<String> getTypes() {
		final Token token = getToken();
		propertiesSupport.getProperties().put(PropertiesSupplier.FRONTEND_CURRENT_TOKEN, token.getTokenString());
		final RecieveTypesCall call = new RecieveTypesCallBuilder(() -> propertiesSupport.getProperties()).build();
		return call0(call);
	}

	@Override
	public Map<String, String> getEntityValues(String type, Long id) {
		final Token token = getToken();
		propertiesSupport.getProperties().put(PropertiesSupplier.FRONTEND_CURRENT_TOKEN, token.getTokenString());
		final EntityCall call = new EntityCallBuilder(() -> propertiesSupport.getProperties()).withType(type).withId(id)
				.build();
		final EntityDto entityDto = call0(call);

		Map<String, String> valueMap = new HashMap<>();
		final ArrayList<String> properties = entityDto.getProperties();
		properties.forEach(p -> {
			final String[] split = p.split("=");
			String key = split[0];
			String value = split[1];
			valueMap.put(key, value);

		});
		return valueMap;

	}

	private <T> T call0(Callable<T> callable) {
		try {
			return callable.call();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
