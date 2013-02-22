package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPostfixExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class PostfixExpressionAnalysisNode extends AbstractASTAnalysisNode{

	public PostfixExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTPostfixExpression postfixexpression = (ASTPostfixExpression)analysisnode;
		if(postfixexpression.jjtGetNumChildren() > 1)
			throw new MethodAnalysisException("PostfixExpression자식이 2개네? 말도안되..");
		
		AbstractJavaNode childnode = (AbstractJavaNode) postfixexpression.jjtGetChild(0);
		MethodResult result = MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)childnode , sourcenode) ;
		
		return result;
	}

}
