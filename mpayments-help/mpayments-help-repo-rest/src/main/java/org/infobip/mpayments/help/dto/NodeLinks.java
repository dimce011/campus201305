package org.infobip.mpayments.help.dto;

public class NodeLinks {
	ParentHref parent;
	ChildrenHref children;
	ContentHref content;
	public ContentHref getContent() {
		return content;
	}

	public void setContent(ContentHref content) {
		this.content = content;
	}

	SelfHref self;
	public SelfHref getSelf() {
		return self;
	}

	public void setSelf(SelfHref self) {
		this.self = self;
	}

	public ChildrenHref getChildren() {
		return children;
	}

	public void setChildren(ChildrenHref children) {
		this.children = children;
	}

	public ParentHref getParent() {
		return parent;
	}

	public void setParent(ParentHref parent) {
		this.parent = parent;
	}

}
