package de.slag.webgui.basic.call;

import java.util.Collection;
import java.util.Map;

public interface CallSupport {

	static CallSupport build() {
		return new CallSupportImpl();
	}

	Collection<String> getTypes();

	Map<String, String> getEntityValues(String type, Long id);

}
