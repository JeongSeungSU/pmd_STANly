package net.sourceforge.pmd.lang.java.rule.stanly;

import java.util.LinkedList;
import java.util.List;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import net.sourceforge.pmd.AbstractPropertySource;
import net.sourceforge.pmd.PMDConfiguration;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTArgumentList;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTExtendsList;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTImplementsList;
import net.sourceforge.pmd.lang.java.ast.ASTInitializer;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTNameList;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.ASTResultType;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.util.designer.CreateXMLRulePanel;

public class RelationManager {
		
	private List<DomainRelation> DomainRelationList;
	
	public RelationManager() {
		//Array? Linked?
		DomainRelationList = new LinkedList<DomainRelation>();
	}
	private void AddRelation(Relations relationkind,String source, String target)
	{
		if(target.equals("String"))
			return;
		DomainRelation relation = new DomainRelation();
		relation.setRelation(relationkind);
		relation.setSource(source);
		relation.setTarget(target);
		System.out.println("Source : "+relation.getSource()+" -> \t "+ relation.getRelation().toString() + 
							"-> \t Target : " + relation.getTarget());
		DomainRelationList.add(relation);
		
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
			AddRelation(Relations.ISOFTYPE,elementnode.getFullName(),((ASTClassOrInterfaceType)type.get(0)).getImage());
			
			//Contains
			AddRelation(Relations.CONTAINS,elementnode.getParent().getFullName(),((ASTClassOrInterfaceType)type.get(0)).getImage());
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
				//Extends
				AddRelation(Relations.EXTENDS,elementnode.getFullName(),type.getImage());
			}
		}
		if(Implementslist.size() > 0 )
		{
			List<ASTClassOrInterfaceType> list = Implementslist.get(0).findChildrenOfType(ASTClassOrInterfaceType.class);
			for(ASTClassOrInterfaceType type : list)
			{
				//Imeplements
				AddRelation(Relations.IMPLEMENTS, elementnode.getFullName(), type.getImage());
			}
		}	
	}
	
	void AddRelation(ASTMethodDeclaration node,ElementNode elementnode)
	{
		//Relation return
		ASTResultType resulttype = (ASTResultType)node.getFirstChildOfType(ASTResultType.class);
		if(resulttype != null)
		{
			List<ASTClassOrInterfaceType> type = resulttype.findDescendantsOfType(ASTClassOrInterfaceType.class);
			if(type.size() > 0)
			{
				AddRelation(Relations.RETURNS,elementnode.getFullName(),type.get(0).getImage());
			}
		}
		//Relation has param
		ASTMethodDeclarator hasparam = (ASTMethodDeclarator)node.getFirstChildOfType(ASTMethodDeclarator.class);
		
		if(hasparam != null)
		{
			List<ASTClassOrInterfaceType> param = hasparam.findDescendantsOfType(ASTClassOrInterfaceType.class);
			if(param.size() > 0)
			{
				for(ASTClassOrInterfaceType tmp :  param)
				{
					AddRelation(Relations.HASPARAM, elementnode.getFullName(), tmp.getImage());
				}
			}
		}
		
		//Relation throws
		ASTNameList throwsList = (ASTNameList)node.getFirstChildOfType(ASTNameList.class);
		
		if(throwsList != null)
		{
			List<ASTName> throwsname = throwsList.findDescendantsOfType(ASTName.class);
			if(throwsname.size() > 0)
			{
				for(ASTName tmp :  throwsname)
				{
					AddRelation(Relations.THROWS,elementnode.getFullName(),tmp.getImage());
				}
			}
		}
		
		// Relation calls
		ASTBlock block = node.getFirstChildOfType(ASTBlock.class);
		if (block != null) {
			List<ASTArguments> ArgumentList = block
					.findDescendantsOfType(ASTArguments.class);

			for (ASTArguments argument : ArgumentList) {
				String CalledObjectName = "Super";
				String CalledMethodName = "";
				String CalledArgument = "";

				Node parentnode = argument.getNthParent(2);
				ASTPrimaryPrefix MethodNamePrefix = parentnode
						.getFirstDescendantOfType(ASTPrimaryPrefix.class);
				if (MethodNamePrefix == null) {
					continue;
				}
				ASTName methodName = MethodNamePrefix
						.getFirstChildOfType(ASTName.class);
				if (methodName == null) {
					ASTPrimarySuffix calledMethod = parentnode
							.getFirstChildOfType(ASTPrimarySuffix.class);
					if (calledMethod == null) {
						continue;
					}

					CalledMethodName = calledMethod.getImage();
				} else {
					if (methodName.getNameDeclaration() == null) {
						String[] tmp = methodName.getImage().split("\\.");
						if(tmp.length == 1 )
						{
							CalledMethodName = tmp[0];
							CalledObjectName = "Object";
						}
						else
						{
							CalledMethodName = tmp[1];
							CalledObjectName = tmp[0];
						}
					} else {
						String[] tmp = methodName.getImage().split("\\.");
						if (tmp.length == 1) {
							CalledMethodName = tmp[0];
							CalledObjectName = elementnode.getParent()
									.getFullName();
						} else {
							CalledMethodName = tmp[1];
							JavaNode typenode = (JavaNode) methodName
									.getNameDeclaration().getNode()
									.getNthParent(1);
							if (typenode.getClass() == ASTVariableDeclarator.class) {
								typenode = (JavaNode) typenode.getNthParent(1);
							}
							JavaNode MethodCallType = typenode
									.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
							if( MethodCallType == null)
							{
								continue;
							}
							CalledObjectName = MethodCallType.getImage();
						}
					}
				}
				//argumentlist extract
				ASTArgumentList methodArgumentList = argument.getFirstChildOfType(ASTArgumentList.class);
				
				//methodArgumentList.
				
				
				
				AddRelation(Relations.CALLS, elementnode.getFullName(),
						CalledObjectName + "." + CalledMethodName + "(" + CalledArgument+ ")");

			}
		}		
	}
	
	void AddRelation(ASTConstructorDeclaration node,ElementNode elementnode)
	{
	
	}
	
	void AddRelation(ASTInitializer node,ElementNode elementnode)
	{
	
	}
	
}
	

