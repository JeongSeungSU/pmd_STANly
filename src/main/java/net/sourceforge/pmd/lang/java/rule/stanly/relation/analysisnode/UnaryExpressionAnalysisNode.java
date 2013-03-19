package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTUnaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;

public class UnaryExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public UnaryExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTUnaryExpression unaryexpression = (ASTUnaryExpression)analysisnode;
		if(unaryexpression.jjtGetNumChildren() > 1)
			throw new MethodAnalysisException("UnaryExpression자식이 1개가 넘어 이거 구현해줘야됨");

		AbstractJavaNode childnode = (AbstractJavaNode)unaryexpression.jjtGetChild(0);
		MethodResult result = MethodAnlysistor.ProcessMethodCallAndAccess(childnode, sourcenode);
		
		return result;
	}

}
