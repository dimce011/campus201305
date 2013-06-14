package org.infobip.mpayments.help.dto;

import java.util.List;

public class EmbeddedChildren {
	public List<DocumentCvor> documents;
	
	public SelfNodeLink get_links() {
		return _links;
	}
	public void set_links(SelfNodeLink _links) {
		this._links = _links;
	}
	
	SelfNodeLink _links = new SelfNodeLink();
}
