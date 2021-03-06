package org.infobip.mpayments.help.dto;

import java.util.List;

public class DocumentCvor {
	String category;
	String self_href;
	String parent_href;

	String children_href;
	String content_href;
	
	String path;
	String title;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getCraetedBy() {
		return craetedBy;
	}

	public void setCraetedBy(String craetedBy) {
		this.craetedBy = craetedBy;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getReseller() {
		return reseller;
	}

	public void setReseller(String reseller) {
		this.reseller = reseller;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	String mimeType = "";
	String craetedBy = "";
	String version = "";
	String language = "";
	String reseller = "";
	String access = "";
	
	Embedded _embedded = new Embedded();
//	List <Paragraph> paragraphs;
	NodeLinks _links = new NodeLinks();
	
	public Embedded get_embedded() {
		return _embedded;
	}

	public void set_embedded(Embedded _embedded) {
		this._embedded = _embedded;
	}

	public NodeLinks get_links() {
		return _links;
	}

	public void set_links(NodeLinks _links) {
		this._links = _links;
	}

	public DocumentCvor() {

	}

	public DocumentCvor(String title, String category, String self_href, String parent_href) {
		super();
		this.title = title;
		this.category = category;
		this.self_href = self_href;
		this.parent_href = parent_href;
		
		
		_links.setSelf(new SelfHref(self_href));
		_links.setParent(new ParentHref(parent_href));
		
		System.out.println("_links "+_links.getParent());
		//this.content_href = content_href;
		// this.children_href = children_href;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/*public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}*/

	/*public String getSelf_href() {
		_links.setSelf(new SelfHref(self_href));
		return self_href;
	}*/

	public void setSelf_href(String self_href) {
		this.self_href = self_href;
	}

	/*public String getParent_href() {
		return parent_href;
	}*/

	public void setParent_href(String parent_href) {
		_links.setParent(new ParentHref(parent_href));
		this.parent_href = parent_href;
	}

	/*public String getChildren_href() {
		return children_href;
	}*/

	public void setChildren_href(String children_href) {
		_links.setChildren(new ChildrenHref(children_href));
		this.children_href = children_href;
	}

	/*public String getContent_href() {
		return content_href;
	}*/

	public void setContent_href(String content_href) {
	//	_embedded.getContent().get_links().setSelf(new SelfHref(content_href));
		_links.setContent(new ContentHref(content_href));
		this.content_href = content_href;
	}
	
	/*public List<Paragraph> getParagraphs() {
		return _embedded.getParagraphs();
	}

	public void setParagraphs(List<Paragraph> paragraphs) {
		_embedded.setParagraphs(paragraphs);
	}*/

}
