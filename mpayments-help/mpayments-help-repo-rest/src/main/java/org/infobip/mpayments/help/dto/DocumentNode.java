package org.infobip.mpayments.help.dto;

import java.util.ArrayList;

public class DocumentNode {

	String key;
	String title;
	String category;
	String type;
	String selfPath;
	ArrayList<DocumentNode> children = null;
	DocumentNode parent = null;

	public DocumentNode() {

	}

	public DocumentNode(String key, String title, String category, String type, DocumentNode parent, String selfPath) {
		super();
		this.key = key;
		this.title = title;
		this.category = category;
		this.type = type;
		this.parent = parent;
		this.selfPath = selfPath;
		children = new ArrayList<DocumentNode>();
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

	public ArrayList<DocumentNode> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<DocumentNode> children) {
		this.children = children;
	}

	public DocumentNode getParent() {
		return parent;
	}

	public void setParent(DocumentNode parent) {
		this.parent = parent;
	}

	public void addChild(DocumentNode dn) {
		children.add(dn);
	}

	public String getSelfPath() {
		return selfPath;
	}

	public void setSelfPath(String selfPath) {
		this.selfPath = selfPath;
	}

}
