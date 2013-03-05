package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTInclusiveOrExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class InclusiveOrExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public InclusiveOrExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		//여기 INT OR BYTE일 수 있음
		return new MethodResult("","int",true);
	}

}
