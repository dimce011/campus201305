package com.bluelotussoftware.example.jsf;

public class DocumentCvor {
	String key;
	String title;
	String category;
	String type;
	String self_href;
	String parent_href;
	String children_href;

	public DocumentCvor() {

	}

	public DocumentCvor(String key, String title, String category, String type, String self_href, String parent_href) {
		super();
		this.key = key;
		this.title = title;
		this.category = category;
		this.type = type;
		this.self_href = self_href;
		this.parent_href = parent_href;
		// this.children_href = children_href;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSelf_href() {
		return self_href;
	}

	public void setSelf_href(String self_href) {
		this.self_href = self_href;
	}

	public String getParent_href() {
		return parent_href;
	}

	public void setParent_href(String parent_href) {
		this.parent_href = parent_href;
	}

	public String getChildren_href() {
		return children_href;
	}

	public void setChildren_href(String children_href) {
		this.children_href = children_href;
	}

}
