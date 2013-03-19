package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sourceforge.pmd.lang.java.ast.ASTAdditiveExpression;
import net.sourceforge.pmd.lang.java.ast.ASTAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTAssignmentOperator;
import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTCastExpression;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTConditionalOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTEqualityExpression;
import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTInclusiveOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTLiteral;
import net.sourceforge.pmd.lang.java.ast.ASTMemberSelector;
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
import net.sourceforge.pmd.lang.java.ast.ASTResource;
import net.sourceforge.pmd.lang.java.ast.ASTResultType;
import net.sourceforge.pmd.lang.java.ast.ASTShiftExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTUnaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.AbstractASTAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.AdditiveExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.AndExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.ArgumentsAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.AssignmentOperatorAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.CastExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.ClassOrInterfaceTypeAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.ConditionalExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.EnumDeclarationAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.EqualityExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.ExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.InclusiveOrExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.LiteralAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.MemberSelectorAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.MultiplicativeExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.NameAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.PostfixExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.PreDecrementExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.PreIncrementExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.PrimaryExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.PrimaryPrefixAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.PrimarySuffixAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.PrimitiveTypeAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.ResourceAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.ResultTypeAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.ShiftExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.TypeArgumentAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode.UnaryExpressionAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 모든 Relation처리 및 관리 
 * @since 2013. 2. 14.오전 1:59:14
 * @author JeongSeungsu
 */
public class RelationAnalyst {
	private static Logger LOG = Logger.getLogger(RelationAnalyst.class);
	
	private DomainRelationList RelationList = null; 
	private Map<ASTPrimaryExpression, RelationResult> processedPrimaryExpression;
	private Map<String,AbstractASTAnalysisNode> ASTParserNodeList;
	final private String ReturnTypeSperator = "StanlyTypeIndicate"; 
	final private String UnknownTypeName = "unknown";
	
			
	public RelationAnalyst(DomainRelationList relationlist)
	{
		RelationList 				= relationlist;
		processedPrimaryExpression 	= new HashMap<ASTPrimaryExpression, RelationResult>();
		ASTParserNodeList			= new HashMap<String, AbstractASTAnalysisNode>();
		CreateASTParserNodeList();
	}
	
	/**
	 * AST분석하기 위해서 ParserNodeList에 추가
	 * 여기서 추가된 모든 Node들이 서로 연관하며 String 처리함
	 * @since 2013. 3. 19.오후 10:04:00
	 * @author JeongSeungsu
	 */
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
		//Literal
		ASTParserNodeList.put(ASTLiteral.class.toString(), new LiteralAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ASTEnumDeclaration
		ASTParserNodeList.put(ASTEnumDeclaration.class.toString(), new EnumDeclarationAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ASTMemberSelector
		ASTParserNodeList.put(ASTMemberSelector.class.toString(), new MemberSelectorAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ASTResultType
		ASTParserNodeList.put(ASTResultType.class.toString(), new ResultTypeAnalysisNode(RelationList,processedPrimaryExpression,this));
		
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
		//ASTShiftExpression
		ASTParserNodeList.put(ASTShiftExpression.class.toString(), new ShiftExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ASTEqualityExpression
		ASTParserNodeList.put(ASTEqualityExpression.class.toString(), new EqualityExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ASTInclusiveOrExpression
		ASTParserNodeList.put(ASTInclusiveOrExpression.class.toString(), new InclusiveOrExpressionAnalysisNode(RelationList,processedPrimaryExpression,this));
		//ASTResource
		ASTParserNodeList.put(ASTResource.class.toString(), new ResourceAnalysisNode(RelationList,processedPrimaryExpression,this));
	}
	
	/**
	 * ASTclass에 따라서 매칭되는 처리 analysisnode를 찾으러 감
	 * @since 2013. 3. 19.오후 10:05:12
	 * @author JeongSeungsu
	 * @param node
	 * @return 있으면 처리할 Node 리턴 없으면 예외처리..
	 * @throws RelationAnalysisException
	 */
	private AbstractASTAnalysisNode MacthingASTParserNode(AbstractJavaNode node) throws RelationAnalysisException
	{
		if(MacroFunctions.NULLTrue(node))
			throw new RelationAnalysisException("MacthingASTParserNode 함수에 Null값이 들어옴");
		
		String nodename = node.getClass().toString();
		
		if(!ASTParserNodeList.containsKey(nodename))
			throw new RelationAnalysisException("ASTParserNodeList에 "+ nodename +"이 없습니다.");
		
		return ASTParserNodeList.get(nodename);
	}
	public String TypeSperateApplyer(String Type)
	{
		return ReturnTypeSperator + Type + ReturnTypeSperator;
	}
	public String GetUnknownTypeName()
	{
		return UnknownTypeName;
	}
	public RelationResult ProcessMethodCallAndAccess(AbstractJavaNode node, ElementNode sourcenode) throws RelationAnalysisException
	{
		AbstractASTAnalysisNode parsernode = MacthingASTParserNode(node);
		
		return parsernode.AnalysisAST(node, sourcenode);
	}
	
	/**
	 * MethodDeclaration내부 모든 것을 파싱... 
	 * @since 2013. 3. 19.오후 10:06:19
	 * @author JeongSeungsu
	 * @param method
	 * @param sourcenode
	 */
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
		catch(RelationAnalysisException methodexception)
		{
			if(!methodexception.getMessage().equals("처리할 필요 없음"))
			{
				LOG.error(methodexception.getMessage(),methodexception);
			}
		}
		catch(Exception e)
		{
			LOG.error(e);
		}
		
	}

	
	
}
