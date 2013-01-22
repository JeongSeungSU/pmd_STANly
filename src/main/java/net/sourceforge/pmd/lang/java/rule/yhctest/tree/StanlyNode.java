package net.sourceforge.pmd.lang.java.rule.yhctest.tree;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.java.rule.yhctest.StanlyMetrics;


public abstract class StanlyNode {


	
	protected StanlyMetrics metrics;
	protected StanlyNodeType type;
	
	protected StanlyNode parent;
	protected List<StanlyNode> children;
	
	protected String name;
	int ChildrenCount=0;
	public StanlyNode(StanlyNode parent, StanlyNodeType type,String name)
	{
		this.parent = parent;
		this.type = type;
		this.name = name;
		this.children = new ArrayList<StanlyNode>();
		metrics = new StanlyMetrics();
	}

	public StanlyNode(StanlyNodeType type,String name)
	{
		this.parent = null;
		this.type = type;
		this.name = name;
		this.children = new ArrayList<StanlyNode>();
		metrics = new StanlyMetrics();
	}
	
	public StanlyNodeType getType() {
		return type;
	}

	public String getFullName() {
		if(parent.type == StanlyNodeType.FOLDER)	
			return name;
		return parent.getFullName() + '.' + name;
	}
	
	public String getName() {
		return name;
	}

	public StanlyNode getParent() {
		return parent;
	}

	public final List<StanlyNode> getChildren() {
		return children;
	}
	public StanlyNode getLastChild()
	{
		return children.get(ChildrenCount-1);
	}
	public StanlyNode addChildren(StanlyNodeType type, String name)
	{
		StanlyNode newNode = null;
		switch(type)
		{
			case PROJECT:
				newNode = new StanlyProjectNode(this, type,name);
				break;
			case FOLDER: 
				newNode = new StanlyFolderNode(this, type,name);
				break;
			case PACKAGE:
				newNode = new StanlyPackageNode(this, type,name);
				break;
			case CLASS:
				newNode = new StanlyClassNode(this,type,name);
				break;
			case ENUM:
				newNode = new StanlyEnumNode(this, type,name);
				break;
			case INTERFACE:
				newNode = new StanlyInterfaceNode(this, type,name);
				break;
			case ATTRIBUTE:
				newNode = new StanlyAttributeNode(this,type,name);
				break;
			case CONSTRUCTOR: 
				newNode = new StanlyConstructorNode(this, type,name);
				break;
			case METHOD:
				newNode = new StanlyMethodNode(this, type,name);
				break;
		}
		
		if(newNode != null)
		{
			children.add(newNode);
			ChildrenCount++;
		}
		//else
		//	return null;
		
		return newNode;
	}
	public StanlyMetrics getMetrics() {
		return metrics;
	}

}
