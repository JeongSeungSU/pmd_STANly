package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class ExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public ExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTExpression expression = (ASTExpression)analysisnode;
		if(expression.jjtGetNumChildren() > 1)
			throw new MethodAnalysisException("Expresssion 차일드 노드가2개다... 이건 큰 문제");
		
		MethodResult result = MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode) expression.jjtGetChild(0), sourcenode);
		
		return result;
	}

}
