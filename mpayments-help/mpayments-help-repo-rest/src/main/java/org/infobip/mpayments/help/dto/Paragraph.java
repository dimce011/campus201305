package org.infobip.mpayments.help.dto;

public class Paragraph {
	String key;
	public String getKey() {
		return key;
	}
	public void setKey(String id) {
		this.key = id;
	}

	public SelfNodeLink get_links() {
		return _links;
	}
	public void set_links(SelfNodeLink _links) {
		this._links = _links;
	}
	
	SelfNodeLink _links = new SelfNodeLink();
}
