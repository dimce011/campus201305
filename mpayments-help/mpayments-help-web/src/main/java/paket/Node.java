package paket;

import java.util.LinkedList;

public class Node {


	String ID;
	String name;
	Node parent = null;
	

	public Node getParent() {
		return parent;
	}
	public void setParent(Node parent) {
		this.parent = parent;
	}
	public LinkedList<Node> getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(LinkedList<Node> childNodes) {
		this.childNodes = childNodes;
	}
	LinkedList<Node> childNodes = null;


	public Node(String iD, String name, LinkedList<Node> childNodes,Node parent) {
		super();
		ID = iD;
		this.name = name;
		this.parent=parent;
		this.childNodes = childNodes;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LinkedList<Node> getDeca() {
		return childNodes;
	}
	public void setDeca(LinkedList<Node> deca) {
		this.childNodes = deca;
	}
	

}
