package de.slag.webgui.basic.call.builder;

import de.slag.webgui.basic.PropertiesSupplier;

public abstract class AbstractBasicCallBuilder {
	
	private PropertiesSupplier propertiesSupplier;

	public AbstractBasicCallBuilder(PropertiesSupplier propertiesSupplier) {
		super();
		this.propertiesSupplier = propertiesSupplier;
	}
	
	protected PropertiesSupplier getPropertiesSupplier() {
		return propertiesSupplier;
	}

}
