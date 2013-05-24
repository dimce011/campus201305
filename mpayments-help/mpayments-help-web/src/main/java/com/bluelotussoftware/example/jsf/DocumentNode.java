package com.bluelotussoftware.example.jsf;

import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class DocumentNode {

	String key;
	String title;
	String category;
	String type;
	String selfPath;
	String link;
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	ArrayList<DocumentNode> children = null;
	String parent = null;

	public DocumentNode() {

	}

	public DocumentNode(String key, String title, String category, String type, String parent, String selfPath) {
		super();
		this.key = key;
		this.title = title;
		this.category = category;
		this.type = type;
		this.parent = parent;
		this.selfPath = selfPath;
		children = new ArrayList<DocumentNode>();
		this.link = "http://localhost:8080/helprepo" + selfPath + "/content";
		
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

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void addChild(DocumentNode dn) {
		children.add(dn);
	}

	public String getSelfPath() {
		return selfPath;
	}

	public void setSelfPath(String selfPath) {
		this.link = "http://localhost:8080/helprepo" + selfPath + "/content";
		this.selfPath = selfPath;
	}
	@Override
	public String toString() {
		 return title;
		//return "DocumentNode " + "[ key: " + key + ", title: " + title + ", category: " + category + ", type: " + type + ", parent: " + parent + ", selfPath: " + selfPath + "children: " + children;
	}

}
