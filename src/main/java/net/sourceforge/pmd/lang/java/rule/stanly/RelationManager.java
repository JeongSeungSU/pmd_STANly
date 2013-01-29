package net.sourceforge.pmd.lang.java.rule.stanly;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTExtendsList;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTImplementsList;
import net.sourceforge.pmd.lang.java.ast.ASTInitializer;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class RelationManager {
		
	private List<DomainRelation> DomainRelationList;
	
	public RelationManager() {
		//Array? Linked?
		DomainRelationList = new LinkedList<DomainRelation>();
	}
	/**
	 * Is of Type, contains
	 * @since 2013. 1. 30.오전 12:36:13
	 * @author JeongSeungsu
	 * @param node
	 */
	void AddRelation(ASTFieldDeclaration node,ElementNode elementnode)
	{
		List<ASTClassOrInterfaceType> type = node.findDescendantsOfType(ASTClassOrInterfaceType.class);
		if(type.size() > 0)
		{
			//Is of Type
			DomainRelation isoftype = new DomainRelation();
			isoftype.setRelation(Relations.ISOFTYPE);
			isoftype.setSource(elementnode.getFullName());
			isoftype.setTarget(((ASTClassOrInterfaceType)type.get(0)).getImage());
			DomainRelationList.add(isoftype);
			
			//Contains
			ASTClassOrInterfaceDeclaration parentnode = node.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class);
			if(parentnode != null)
			{
				DomainRelation contains = new DomainRelation();
				contains.setRelation(Relations.CONTAINS);
				contains.setSource(parentnode.getImage());
				contains.setTarget(((ASTClassOrInterfaceType)type.get(0)).getImage());
				DomainRelationList.add(contains);
			}
		}
	}
	
	/**
	 * implements, extends
	 * @since 2013. 1. 30.오전 1:49:19
	 * @author JeongSeungsu
	 * @param node
	 * @param elementnode
	 */
	void AddRelation(ASTClassOrInterfaceDeclaration node, ElementNode elementnode)
	{
		List<ASTExtendsList> extendlist = node.findDescendantsOfType(ASTExtendsList.class);
		List<ASTImplementsList> Implementslist = node.findDescendantsOfType(ASTImplementsList.class);
		if(extendlist.size() > 0)
		{
			List<ASTClassOrInterfaceType> list = extendlist.get(0).findChildrenOfType(ASTClassOrInterfaceType.class);
			for(ASTClassOrInterfaceType type : list)
			{
				DomainRelation relationimplements = new DomainRelation();
				relationimplements.setRelation(Relations.EXTENDS);
				relationimplements.setSource(elementnode.getFullName());
				relationimplements.setTarget(type.getImage());
				DomainRelationList.add(relationimplements);
			}
		}
		if(Implementslist.size() > 0 )
		{
			List<ASTClassOrInterfaceType> list = Implementslist.get(0).findChildrenOfType(ASTClassOrInterfaceType.class);
			for(ASTClassOrInterfaceType type : list)
			{
				DomainRelation relationextends = new DomainRelation();
				relationextends.setRelation(Relations.IMPLEMENTS);
				relationextends.setSource(elementnode.getFullName());
				relationextends.setTarget(type.getImage());
				DomainRelationList.add(relationextends);
			}
						
		}	
	}
	
	void AddRelation(ASTMethodDeclaration node,ElementNode elementnode)
	{
		
	}
	
	void AddRelation(ASTConstructorDeclaration node,ElementNode elementnode)
	{
	
	}
	
	void AddRelation(ASTInitializer node,ElementNode elementnode)
	{
	
	}
	
}
	

