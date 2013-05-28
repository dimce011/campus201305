package org.infobip.mpayments.help.dto;

public class Paragraph {
	String key;
	public String getKey() {
		return key;
	}
	public void setKey(String id) {
		this.key = id;
	}
	public String getLink() {
		return _links;
	}
	public void setLink(String link) {
		this._links = link;
	}
	String _links;

}
