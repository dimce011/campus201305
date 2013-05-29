package com.bluelotussoftware.example.jsf;

import java.util.List;



public class DocumentCvor {

	String title;
	String category;
	String self_href;
	String parent_href;
	String children_href;
	String content_href;
	List <Paragraph> list;

	public DocumentCvor() {

	}

	public DocumentCvor(String title, String category, String self_href, String parent_href) {
		super();
		this.title = title;
		this.category = category;
		this.self_href = self_href;
		this.parent_href = parent_href;
		// this.content_href = content_href;
		// this.children_href = children_href;
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

	public String getContent_href() {
		return content_href;
	}

	public void setContent_href(String content_href) {
		if (content_href.equals("")) {
			this.content_href = content_href;
		} else {
			this.content_href = "http://localhost:8080/helprepo/documents" + content_href;
		}
	}
	
	public List<Paragraph> getList() {
		return list;
	}

	public void setList(List<Paragraph> list) {
		this.list = list;
	}


}
