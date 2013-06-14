package org.infobip.mpayments.help.dto;

public class Content {
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public SelfNodeLink get_links() {
		return _links;
	}
	public void set_links(SelfNodeLink _links) {
		this._links = _links;
	}
	
	String html = "";
	SelfNodeLink _links = new SelfNodeLink();
}
