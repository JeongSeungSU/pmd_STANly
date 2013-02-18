package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

/**
 * 
 * @since 2013. 2. 14.오전 1:59:14
 * @author JeongSeungsu
 */
public class MethodAnlaysis {
	private List<DomainRelation> RelationList = null; 
	private Map<ASTPrimaryExpression, MethodResult> processedPrimaryExpression;
	private Map<String,AbstractASTParserNode> ASTParserNodeList;
	
	public MethodAnlaysis(List<DomainRelation> relationlist)
	{
		RelationList 				= relationlist;
		processedPrimaryExpression 	= new HashMap<ASTPrimaryExpression, MethodResult>();
		ASTParserNodeList			= new HashMap<String, AbstractASTParserNode>();
	}
	
	private void CreateASTParserNodeList()
	{
		ASTParserNodeList.put(ASTPrimaryExpression.class.toString(), new PrimaryExpressionAnalysisNode(RelationList));
		
	}
	
	private AbstractASTParserNode MacthingASTParserNode(AbstractJavaNode node) throws MethodAnalysisException
	{
		String nodename = node.getClass().toString();
		
		if(ASTParserNodeList.containsKey(nodename))
		{
			return ASTParserNodeList.get(nodename);
		}
		throw new MethodAnalysisException("NodeList가 존재하지 않아...맵에");
	}
	
	private void ProcessMethodCallAndAccess(AbstractJavaNode node, ElementNode sourcenode) throws MethodAnalysisException
	{
		AbstractASTParserNode parsernode = MacthingASTParserNode(node);
		
		parsernode.AnalysisAST(node, sourcenode);
	}
	
	public void AnalysisMethodCallandAccess(ASTMethodDeclarator method,ElementNode sourcenode)
	{
		try
		{
			ASTBlock block = method.getFirstChildOfType(ASTBlock.class);
			if (block != null) 
			{
				List<ASTPrimaryExpression> PrimaryExpressionList = block.findChildrenOfType(ASTPrimaryExpression.class);
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
