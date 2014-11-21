package com.warcgenerator.gui.components;

import javax.swing.Action;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class CustomTreeNode extends DefaultMutableTreeNode {
	private Integer id;
	private CustomTreeNodeType type = CustomTreeNodeType.DEFAULT_NODE;
	// Used for referring an object which is relationed with this tree node
	private Object referencedObject;
	private Action action;

	public CustomTreeNode() {
		super();
	}
	
	public CustomTreeNode(Object userObject) {
		super(userObject);
	}
	
	public CustomTreeNode(Object userObject, Boolean allowsChildren) {
		super(userObject, allowsChildren);
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}
	
	public CustomTreeNodeType getType() {
		return type;
	}

	public void setType(CustomTreeNodeType type) {
		this.type = type;
	}

	public Object getReferencedObject() {
		return referencedObject;
	}

	public void setReferencedObject(Object referencedObject) {
		this.referencedObject = referencedObject;
	}
}
