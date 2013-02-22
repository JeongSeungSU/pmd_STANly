package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTConditionalExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class ConditionalExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public ConditionalExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTConditionalExpression conditionalexpression = (ASTConditionalExpression)analysisnode;
		if(conditionalexpression.jjtGetNumChildren() != 3)
			throw new MethodAnalysisException("ConditionalExpression자식이 3개가 아니라니 ... 분기문인데...");
		
		AbstractJavaNode childnode1 = (AbstractJavaNode) conditionalexpression.jjtGetChild(1);
		AbstractJavaNode childnode2 = (AbstractJavaNode) conditionalexpression.jjtGetChild(2);
		
		MethodResult childresult1 = MethodAnlysistor.ProcessMethodCallAndAccess(childnode1, sourcenode);
		MethodResult childresult2 = MethodAnlysistor.ProcessMethodCallAndAccess(childnode2, sourcenode);
		
		if(!childresult1.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
			return childresult1;
		else if(!childresult2.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
			return childresult2;
		else if(!childresult1.TypeName.equals(""))
			return childresult1;
		else
			return childresult2;
	}

}
