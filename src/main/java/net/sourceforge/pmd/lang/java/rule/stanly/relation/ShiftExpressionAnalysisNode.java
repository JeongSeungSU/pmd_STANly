package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTShiftExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class ShiftExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public ShiftExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTShiftExpression shiftexpression = (ASTShiftExpression)analysisnode;
		return MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)shiftexpression.jjtGetChild(0), sourcenode);
	}

}
