package de.adorsys.tanserver.rest.to;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class ResourceTo {
	
	@JsonProperty("_links")
	private Map<String, Object> links = new HashMap<String, Object>();

	public Map<String, Object> getLinks() {
		return links;
	}

	public void setLinks(Map<String, Object> links) {
		this.links = links;
	}

}
