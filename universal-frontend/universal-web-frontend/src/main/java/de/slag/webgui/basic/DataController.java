package de.slag.webgui.basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.slag.webgui.basic.call.CallSupport;

@ManagedBean
@SessionScoped
public class DataController {

	private PropertiesSupport propertiesSupport = PropertiesSupport.getInstance();

	private CallSupport callSupport = CallSupport.build();

	private String status;

	private String type;

	private String id;

	private Collection<String> types = new ArrayList<>();

	private Collection<Attribute> attributes = new ArrayList<>();

	public String getVersion() {
		return DataController.class.getPackage().getImplementationVersion();
	}

	public void init() {
		type = null;
		id = null;
		types.clear();
		types.addAll(callSupport.getTypes());
		attributes.clear();
		status = "initialized";
	}

	public void submit() {
		attributes.clear();
		final Map<String, String> entityValues = callSupport.getEntityValues(type, Long.valueOf(id));
		entityValues.keySet().forEach(key -> {
			attributes.add(new Attribute(key, entityValues.get(key)));
		});

		status = String.format("submitted. Type: %s, Id: %s", type, id);
	}

	public String getStatus() {
		return status;
	}

	public List<?> getData() {
		return new ArrayList<>(attributes);
	}

	public Collection<?> getTypes() {
		return types;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void open(String s) {
		if (!s.startsWith("TYPE")) {
			status = "ERROR: no bean representing value " + s;
			return;
		}
		final String[] split = s.split(":");
		type = split[1];
		id = split[2];
		submit();
	}

	public class Attribute {
		private String key;
		private String value;

		public Attribute(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

	}
}
