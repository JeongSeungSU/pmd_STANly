package net.sourceforge.pmd.lang.java.rule.stanly.element;

import java.util.ArrayList;
import java.util.List;



public abstract class ElementNode {


	
	protected ElementNodeType type;
	
	protected ElementNode parent;
	protected List<ElementNode> children;
	
	protected String name;
	int ChildrenCount=0;
	public ElementNode(ElementNode parent, ElementNodeType type,String name)
	{
		this.parent = parent;
		this.type = type;
		this.name = name;
		this.children = new ArrayList<ElementNode>();
	}

	public ElementNode(ElementNodeType type,String name)
	{
		this.parent = null;
		this.type = type;
		this.name = name;
		this.children = new ArrayList<ElementNode>();
	}
	
	public ElementNodeType getType() {
		return type;
	}

	public String getFullName() {
		if(parent.type == ElementNodeType.LIBRARY)	
			return name;
		return parent.getFullName() + '.' + name;
	}
	
	public String getName() {
		return name;
	}

	public ElementNode getParent() {
		return parent;
	}

	public final List<ElementNode> getChildren() {
		return children;
	}
	public ElementNode getLastChild()
	{
		if(ChildrenCount == 0)	
			return null;
		return children.get(ChildrenCount-1);
	}
	public ElementNode addChildren(ElementNodeType type, String name)
	{
		ElementNode newNode = null;
		switch(type)
		{
			case PROJECT:
				newNode = new ProjectDomain(this,type,name);
				break;
			case LIBRARY: 
				newNode = new LibraryDomain(this,type,name);
				break;
			case PACKAGESET:
				newNode = new PackageSetDomain(this,type,name);
				break;
			case PACKAGE:
				newNode = new PackageDomain(this,type,name);
				break;
			case CLASS:
			case INTERFACE:
			case ENUM:
				newNode = new ClassDomain(this,type,name);
				break;
			case FIELD:
				newNode = new FieldDomain(this,type,name);
				break;
			case METHOD:
			case CONSTRUCTOR:
				newNode = new MethodDomain(this,type,name);
				break;
		}
		
		if(newNode != null)
		{
			int idx = 0;
			for(ElementNode node:children)//이름 오름차순 정렬
			{
				if(newNode.name.compareTo(node.name) < 0)
					break;
				idx++;
			}
			children.add(idx, newNode);
			
			ChildrenCount++;
		}
		//else
		//	return null;
		
		return newNode;
	}

}
