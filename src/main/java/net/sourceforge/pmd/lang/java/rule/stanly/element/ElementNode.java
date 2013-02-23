package net.sourceforge.pmd.lang.java.rule.stanly.element;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;



public abstract class ElementNode {


	
	protected ElementNodeType type;
	
	protected ElementNode parent;
	protected List<ElementNode> children;
	protected List<DomainRelation> relationSources;
	protected List<DomainRelation> relationTargets;
	
	protected String name;
	int ChildrenCount=0;
	public ElementNode(ElementNode parent, ElementNodeType type,String name)
	{
		this.parent = parent;
		this.type = type;
		this.name = name;
		this.children = new ArrayList<ElementNode>();
		this.relationSources = new ArrayList<DomainRelation>();
		this.relationTargets = new ArrayList<DomainRelation>();
	}

	public ElementNode(ElementNodeType type,String name)
	{
		this(null,type,name);
		//this.parent = null;
		//this.type = type;
		//this.name = name;
		//this.children = new ArrayList<ElementNode>();
	}
	
	public ElementNodeType getType() {
		return type;
	}

	public List<DomainRelation> getRelationSources()
	{
		return relationSources;
	}
	public List<DomainRelation> getRelationTargets()
	{
		return relationTargets;
	}
	public void AddRelationSource(DomainRelation relation)
	{
		//if(relationSources == null)
		this.relationSources = new ArrayList<DomainRelation>();
		relationSources.add(relation);
	}
	public void AddRelationTarget(DomainRelation relation)
	{
		//if(relationTargets == null)
		this.relationTargets = new ArrayList<DomainRelation>();
		relationTargets.add(relation);
	}
	public String getFullName() {
		if(parent.type == ElementNodeType.LIBRARY)	
			return name;
		if(name.equals("."))//PackageSet의 경우 "."은 부모를 의미
			return parent.getFullName(); 
		
		return parent.getFullName() + '.' + name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String newName) {
		name = newName;
	}

	public ElementNode getParent() {
		return parent;
	}
	
	public void setParent(ElementNode parent) {
		this.parent = parent;
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
	public ElementNode addChildren(ElementNode newNode)
	{
		int idx = 0;
		for(ElementNode node:children)//이름 오름차순 정렬
		{
			int val = newNode.name.compareTo(node.name);
			if(val < 0)		break;
			idx++;
		}
		children.add(idx, newNode);
		
		ChildrenCount++;
		return newNode;
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
			case ANNOTATION:
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
			addChildren(newNode);
		//else
		//	return null;
		
		return newNode;
	}
	public ElementNode findNode(String targetString) {
		// TODO Auto-generated method stub
		ElementNode targetNode = null;
		//targetString.startsWith(prefix)
		if(type == ElementNodeType.LIBRARY)
		{
			for(ElementNode childnode:getChildren())
			{				
				if(targetString.startsWith(childnode.getFullName()))
				{
					targetNode = childnode.findNode(targetString);
					if(targetNode != null) break;
				}
			}
		}
		else if(targetString.startsWith(getFullName()))
		{
			String subString = targetString.substring(getFullName().length()); 
			
			while(subString.startsWith("<"))
			{
				targetString = removeTemplate(targetString,getFullName().length());
				subString = targetString.substring(getFullName().length()); 
			}
			if(targetString.equals(getFullName()))
				targetNode = this;
			else if(subString.startsWith("."))
			{
				for(ElementNode childnode:getChildren())
				{
					
					if(targetString.startsWith(childnode.getFullName()) &&
						(targetString.equals(childnode.getFullName()) ||
						targetString.charAt(childnode.getFullName().length()) == '.' ||
						targetString.charAt(childnode.getFullName().length()) == '<' ||
						targetString.charAt(childnode.getFullName().length()) == '('))
					{
						targetNode = childnode.findNode(targetString);
						if(targetNode != null) break;
					}
				}
			}
			else if(subString.startsWith("("))
				targetNode = this;
			else targetNode = getParent().findNode(targetString);
		}
		else targetNode = getParent().findNode(targetString);
		
		return targetNode;
	}

	private String removeTemplate(String targetString, int sp) {
		int opencount=0;
		String substr = "";
		int ep=sp;
		for(int i=sp;i<targetString.length();i++)
		{
			if(targetString.charAt(i) == '<')
				opencount++;
			
			if(opencount > 0)
				ep++;
			
			if(targetString.charAt(i) == '>')
			{
				opencount--;
				if(opencount == 0)
					break;
			}
			
		}
		
		String newStr = targetString.substring(0, sp);
		newStr += targetString.substring(ep, targetString.length());
		return newStr;
	}
	public boolean isAncestor(ElementNode ancestor)
	{
		ElementNode node = this;
		while(node != null && node != ancestor)
			node = node.parent;
		
		if(node != null)	return true;
		else				return false;
	}
}
