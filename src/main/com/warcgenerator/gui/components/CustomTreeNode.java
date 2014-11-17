package com.warcgenerator.gui.components;

import javax.swing.Action;
import javax.swing.tree.DefaultMutableTreeNode;

@SuppressWarnings("serial")
public class CustomTreeNode extends DefaultMutableTreeNode {
	private Integer id;
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
}
