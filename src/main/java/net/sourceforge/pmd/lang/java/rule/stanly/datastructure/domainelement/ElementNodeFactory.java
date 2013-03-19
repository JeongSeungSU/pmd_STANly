package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement;

public class ElementNodeFactory {

	public static ElementNode CreateElementNode(ElementNode parent, ElementNodeType type,String name)
	{
		ElementNode node = CreateElementNode(type,name);
		node.setParent(parent);
		return node;
	}
	public static ElementNode CreateElementNode(ElementNodeType type, String name)
	{
		ElementNode newNode = null;
		switch(type)
		{
			case PROJECT:
				newNode = new ProjectDomain(type,name);
				break;
			case LIBRARY: 
				newNode = new LibraryDomain(type,name);
				break;
			case PACKAGESET:
				newNode = new PackageSetDomain(type,name);
				break;
			case PACKAGE:
				newNode = new PackageDomain(type,name);
				break;
			case CLASS:
			case INTERFACE:
			case ENUM:
			case ANNOTATION:
				newNode = new ClassDomain(type,name);
				break;
			case FIELD:
				newNode = new FieldDomain(type,name);
				break;
			case METHOD:
			case CONSTRUCTOR:
				newNode = new MethodDomain(type,name);
				break;
		}
		
		return newNode;
	}
}
