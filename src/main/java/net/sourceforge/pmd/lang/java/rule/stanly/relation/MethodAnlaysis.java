package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAdditiveExpression;
import net.sourceforge.pmd.lang.java.ast.ASTAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTAssignmentOperator;
import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTCastExpression;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTMultiplicativeExpression;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPostfixExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPreDecrementExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPreIncrementExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimitiveType;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTUnaryExpression;
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
	private Map<String,AbstractASTAnalysisNode> ASTParserNodeList;
	final private String ReturnTypeSperator = "StanlyTypeIndicate"; 
			
	public MethodAnlaysis(DomainRelationList relationlist)
	{
		RelationList 				= relationlist;
		processedPrimaryExpression 	= new HashMap<ASTPrimaryExpression, MethodResult>();
		ASTParserNodeList			= new HashMap<String, AbstractASTAnalysisNode>();
		CreateASTParserNodeList();
	}
	
	private void CreateASTParserNodeList()
	{
		//primary Expression
		ASTParserNodeList.put(ASTPrimaryExpression.class.toString(), new PrimaryExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//PrimaryPrefix
		ASTParserNodeList.put(ASTPrimaryPrefix.class.toString(), new PrimaryPrefixAnalysisNode(RelationList,processedPrimaryExpression,this));
		//PrimarySuffix
		ASTParserNodeList.put(ASTPrimarySuffix.class.toString(), new PrimarySuffixAnalysisNode(RelationList,processedPrimaryExpression,this));
		//Arguments
		ASTParserNodeList.put(ASTArguments.class.toString(), new ArgumentsAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ClassOrInterfaceType
		ASTParserNodeList.put(ASTClassOrInterfaceType.class.toString(), new ClassOrInterfaceTypeAnalysisNode(RelationList,processedPrimaryExpression,this));
		//Name
		ASTParserNodeList.put(ASTName.class.toString(), new NameAnalysisNode(RelationList,processedPrimaryExpression,this));
		//TypeArguments
		ASTParserNodeList.put(ASTTypeArgument.class.toString(), new TypeArgumentAnalysisNode(RelationList,processedPrimaryExpression,this));
		//PrimitiveType
		ASTParserNodeList.put(ASTPrimitiveType.class.toString(), new PrimitiveTypeAnalysisNode(RelationList,processedPrimaryExpression,this));
		
		//argumentList아래것들...
		//PreDecrementExpression
		ASTParserNodeList.put(ASTPreDecrementExpression.class.toString(), new PreDecrementExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//PreIncrementExpression
		ASTParserNodeList.put(ASTPreIncrementExpression.class.toString(), new PreIncrementExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//UnaryExpression
		ASTParserNodeList.put(ASTUnaryExpression.class.toString(), new UnaryExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//PostfixExpression
		ASTParserNodeList.put(ASTPostfixExpression.class.toString(), new PostfixExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ConditionalExpression
		ASTParserNodeList.put(ASTConditionalExpression.class.toString(), new ConditionalExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//AdditiveExpression
		ASTParserNodeList.put(ASTAdditiveExpression.class.toString(), new AdditiveExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//MultiplicativeExpression
		ASTParserNodeList.put(ASTMultiplicativeExpression.class.toString(), new MultiplicativeExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//CastExpression
		ASTParserNodeList.put(ASTCastExpression.class.toString(), new CastExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//AssignmentOperator
		ASTParserNodeList.put(ASTAssignmentOperator.class.toString(), new AssignmentOperatorAnalysisNode(RelationList,processedPrimaryExpression,this));
		//AndExpression
		ASTParserNodeList.put(ASTAndExpression.class.toString(), new AndExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//Expression???
		ASTParserNodeList.put(ASTExpression.class.toString(), new ExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		
	}
	
	private AbstractASTAnalysisNode MacthingASTParserNode(AbstractJavaNode node) throws MethodAnalysisException
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
		AbstractASTAnalysisNode parsernode = MacthingASTParserNode(node);
		
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
