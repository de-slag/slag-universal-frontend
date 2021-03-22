package de.slag.webgui.basic.call.builder;

import javax.ws.rs.core.MediaType;

import de.slag.webgui.basic.BasicWebTargetCall;
import de.slag.webgui.basic.BasicWebTargetCallBuilder;
import de.slag.webgui.basic.Builder;
import de.slag.webgui.basic.PropertiesSupplier;
import de.slag.webgui.basic.call.RunDefaultCall;

public class RunDefaultCallBuilder extends AbstractBasicCallBuilder implements Builder<RunDefaultCall> {

	public RunDefaultCallBuilder(PropertiesSupplier propertiesSupplier) {
		super(propertiesSupplier);
	}

	@Override
	public RunDefaultCall build() {
		final String backendUrl = getPropertiesSupplier().getBackendUrl();
		final String currentToken = getPropertiesSupplier().getCurrentToken();
		
		
		final BasicWebTargetCall call = new BasicWebTargetCallBuilder().withTarget(backendUrl)
				.withEndpoint("/rundefault").withToken(currentToken).withAcceptedResponseType(MediaType.APPLICATION_JSON)
				.build();
		return new RunDefaultCall() {

			@Override
			public String call() throws Exception {
				return call.call().readEntity(String.class);
			}
		};
	}

}
