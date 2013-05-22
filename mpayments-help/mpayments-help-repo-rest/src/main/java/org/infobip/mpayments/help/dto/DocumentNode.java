package org.infobip.mpayments.help.dto;

import java.util.ArrayList;
import java.util.Iterator;

public class DocumentNode {

	String key;
	String title;
	String category;
	String type;
	String selfPath;
	ArrayList<DocumentNode> children = null;
	String parent = null;

	public DocumentNode() {
		children= new ArrayList<DocumentNode>();
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
		this.selfPath = selfPath;
	}

	//	@Override public String toString() {
	//		StringBuilder result = new StringBuilder();
	//		String NEW_LINE = System.getProperty("line.separator");
	//
	//		result.append(this.getClass().getName() + " Object {" + NEW_LINE);
	//		result.append(" key: " + key + NEW_LINE);
	//		result.append(" title " + title + NEW_LINE);
	//		result.append(" category " + category + NEW_LINE );
	//		result.append(" type: " + type + NEW_LINE);
	//		result.append(" selfPath: " + selfPath + NEW_LINE);
	//		result.append(" parent: " + parent + NEW_LINE);
	//		result.append(" selfPath: " + selfPath + NEW_LINE);
	//		for(Iterator<DocumentNode> i = this.getChildren().iterator(); i.hasNext(); ) {
	//			DocumentNode item = i.next();
	//			result.append(" children " + item + NEW_LINE);
	//		}
	//		result.append("}");
	//
	//		return result.toString();
	//}
}
