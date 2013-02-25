package net.sourceforge.pmd.lang.java.rule.stanly;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTAllocationExpression;
import net.sourceforge.pmd.lang.java.ast.ASTArgumentList;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTExtendsList;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTImplementsList;
import net.sourceforge.pmd.lang.java.ast.ASTInitializer;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTNameList;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.ASTReferenceType;
import net.sourceforge.pmd.lang.java.ast.ASTResultType;
import net.sourceforge.pmd.lang.java.ast.ASTStatement;
import net.sourceforge.pmd.lang.java.ast.ASTType;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArguments;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.symboltable.NameDeclaration;

public class RelationManager {
		
	private DomainRelationList 	 RelationList;
	private MethodAnlaysis		 RelationChainManager;
	
	public RelationManager() {
		//Array? Linked?
		//DomainRelationList = new LinkedList<DomainRelation>();
		RelationList = new DomainRelationList();
		RelationChainManager 	= new MethodAnlaysis(RelationList); 
	}
	
	/**
	 * 도메인 리스트 얻어오..
	 * @since 2013. 2. 13.오 4:37:55
	 * @author YangHyunchul
	 * @param 
	 * @return List
	 */
	public List<DomainRelation >getDomainRelationList(){
		return RelationList.GetList();
	}
	
	/**
	 * 클래스 타입을 스트링으로 뽑아내기..
	 * @since 2013. 2. 5.오후 7:53:55
	 * @author JeongSeungsu
	 * @param type AST Class,Interface타입들
	 * @return
	 */
	private String ClassOrInterfaceTypeToString(ASTClassOrInterfaceType type)
	{
		String ClassName = "";
		String ArgumentList = "";
		ASTTypeArguments typeargument = type.getFirstDescendantOfType(ASTTypeArguments.class);
		if(typeargument != null)
		{
			ArgumentList += "<";
			List<ASTTypeArgument> argument = typeargument.findChildrenOfType(ASTTypeArgument.class);
			for(ASTTypeArgument ar : argument)
			{
				ASTClassOrInterfaceType typename = ar.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
				if(typename == null)
					ArgumentList += "?";
				else
					ArgumentList += ClassOrInterfaceTypeToString(typename);
				ArgumentList += ",";
			}
			ArgumentList = ArgumentList.substring(0,ArgumentList.length()-1);
			ArgumentList += ">";
		}

		if(type.getType() != null) // Class 파일이 있을경우 바로 전체 클레스 경로를 입력함 YHC
			ClassName = type.getType().getName();
		else if(type.getQualifiedName() != null)
			ClassName = type.getQualifiedName();
		else
			ClassName = type.getImage();
		return ClassName+ArgumentList;
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
			RelationList.AddRelation(Relations.ISOFTYPE,elementnode.getFullName(),ClassOrInterfaceTypeToString(((ASTClassOrInterfaceType)type.get(0))),elementnode,null);
			
			//Contains
			RelationList.AddRelation(Relations.CONTAINS,elementnode.getParent().getFullName(),ClassOrInterfaceTypeToString(((ASTClassOrInterfaceType)type.get(0))),elementnode.getParent(),null);
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
				RelationList.AddRelation(Relations.EXTENDS,elementnode.getFullName(),ClassOrInterfaceTypeToString(type),elementnode,null);
			}
		}
		if(Implementslist.size() > 0 )
		{
			List<ASTClassOrInterfaceType> list = Implementslist.get(0).findChildrenOfType(ASTClassOrInterfaceType.class);
			for(ASTClassOrInterfaceType type : list)
			{
				//Imeplements
				RelationList.AddRelation(Relations.IMPLEMENTS, elementnode.getFullName(), ClassOrInterfaceTypeToString(type),elementnode,null);
			}
		}	
	}
	
	void AddRelation(ASTMethodDeclaration node,ElementNode elementnode)
	{
	
		//Relation return
		ASTResultType resulttype = (ASTResultType)node.getFirstChildOfType(ASTResultType.class);
		if(resulttype != null)
		{
			ASTClassOrInterfaceType type = resulttype.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
			if(type != null)
			{
				RelationList.AddRelation(Relations.RETURNS,elementnode.getFullName(),ClassOrInterfaceTypeToString(type),elementnode,null);
			}
		}
		//Relation has param
		ASTMethodDeclarator hasparam = (ASTMethodDeclarator)node.getFirstChildOfType(ASTMethodDeclarator.class);
		
		if(hasparam != null)
		{
			List<ASTType> param = hasparam.findDescendantsOfType(ASTType.class);
			if(param.size() > 0)
			{
				for(ASTType tmp :  param)
				{
					ASTClassOrInterfaceType paramtype = tmp.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
					if(paramtype == null)
						continue;
					RelationList.AddRelation(Relations.HASPARAM, elementnode.getFullName(), ClassOrInterfaceTypeToString(paramtype),elementnode,null);
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
					String className = tmp.getImage();
					
					if(tmp.getType() != null)
						className = tmp.getType().getName();
					else if(tmp.getQualifiedName() != null)
						className = tmp.getQualifiedName();
					else
						className = tmp.getImage() + " (Cannot Found!)";
					RelationList.AddRelation(Relations.THROWS,elementnode.getFullName(),className,elementnode,null);
				}
			}
		}

		//Relation method ,Access 
		RelationChainManager.AnalysisMethodCallandAccess(node, elementnode);
	}
	void AddRelation(ASTConstructorDeclaration node,ElementNode elementnode)
	{
	
	}
	
	void AddRelation(ASTInitializer node,ElementNode elementnode)
	{
	
	}
	void AddRelation(ASTReferenceType node, ElementNode elementnode) {
		if (node.getFirstParentOfType(ASTBlock.class) == null)
			return;
		ASTClassOrInterfaceType ci = node.getFirstChildOfType(ASTClassOrInterfaceType.class);
		// if(!(elementnode instanceof MethodDomain)) return;
		// System.out.println(.getImage());
		if (ci == null)
			return;
		RelationList.AddRelation(Relations.REFERENCES, elementnode.getFullName(),
				ClassOrInterfaceTypeToString(ci),elementnode,null);
	}
}
	

