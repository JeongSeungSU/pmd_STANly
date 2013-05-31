package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTExclusiveOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class ExclusiveOrExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public ExclusiveOrExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,
			ElementNode sourcenode) throws MethodAnalysisException {
		//
		ASTExclusiveOrExpression exclusiveorexpression = (ASTExclusiveOrExpression)analysisnode;
		MethodResult result1 = MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)exclusiveorexpression.jjtGetChild(0), sourcenode);
		MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)exclusiveorexpression.jjtGetChild(0), sourcenode);
		return result1;
	}

}
