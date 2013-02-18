package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

/**
 * 
 * @since 2013. 2. 14.오전 1:59:14
 * @author JeongSeungsu
 */
public class MethodAnlaysis {
	private DomainRelationList RelationList = null; 
	private Map<ASTPrimaryExpression, MethodResult> processedPrimaryExpression;
	private Map<String,AbstractASTParserNode> ASTParserNodeList;
	final private String ReturnTypeSperator = "+-*/[]"; 
			
	public MethodAnlaysis(DomainRelationList relationlist)
	{
		RelationList 				= relationlist;
		processedPrimaryExpression 	= new HashMap<ASTPrimaryExpression, MethodResult>();
		ASTParserNodeList			= new HashMap<String, AbstractASTParserNode>();
		CreateASTParserNodeList();
	}
	
	private void CreateASTParserNodeList()
	{
		//primary Expression
		ASTParserNodeList.put(ASTPrimaryExpression.class.toString(), new PrimaryExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//Arguments
		ASTParserNodeList.put(ASTArguments.class.toString(), new ArgumentsAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ClassOrInterfaceType
		ASTParserNodeList.put(ASTClassOrInterfaceType.class.toString(), new ClassOrInterfaceTypeAnalysisNode(RelationList,processedPrimaryExpression,this));
		//Name
		ASTParserNodeList.put(ASTName.class.toString(), new NameAnalysisNode(RelationList,processedPrimaryExpression,this));
		//TypeArguments
		ASTParserNodeList.put(ASTTypeArgument.class.toString(), new TypeArgumentAnalysisNode(RelationList,processedPrimaryExpression,this));
	}
	
	private AbstractASTParserNode MacthingASTParserNode(AbstractJavaNode node) throws MethodAnalysisException
	{
		String nodename = node.getClass().toString();
		
		if(!ASTParserNodeList.containsKey(nodename))
		{
			throw new MethodAnalysisException("ASTParserNodeList에 "+ nodename +"이 없습니다.");
		}
		return ASTParserNodeList.get(nodename);
	}
	public String TypeSperateApplyer(String Type)
	{
		return ReturnTypeSperator + Type + ReturnTypeSperator;
	}
	
	public MethodResult ProcessMethodCallAndAccess(AbstractJavaNode node, ElementNode sourcenode) throws MethodAnalysisException
	{
		AbstractASTParserNode parsernode = MacthingASTParserNode(node);
		
		return parsernode.AnalysisAST(node, sourcenode);
	}
	
	public void AnalysisMethodCallandAccess(ASTMethodDeclaration method,ElementNode sourcenode)
	{
		try
		{
			ASTBlock block = method.getFirstChildOfType(ASTBlock.class);
			if (block != null) 
			{
				List<ASTPrimaryExpression> PrimaryExpressionList = block.findDescendantsOfType(ASTPrimaryExpression.class);
				for(ASTPrimaryExpression node : PrimaryExpressionList)
				{
					ProcessMethodCallAndAccess(node,sourcenode);
				}
			}
		}
		catch(MethodAnalysisException methodexception)
		{
			methodexception.PrintCauseException();
			methodexception.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	
	
}
