package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTMultiplicativeExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;

public class MultiplicativeExpressionAnalysisNode extends
		AbstractASTAnalysisNode {

	public MultiplicativeExpressionAnalysisNode(
			DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTMultiplicativeExpression multiplicativeexpression = (ASTMultiplicativeExpression) analysisnode;
		MethodResult result = new MethodResult("",MethodAnlysistor.GetUnknownTypeName(),true);
		for(int i = 0 ; i < multiplicativeexpression.jjtGetNumChildren(); i++)
		{
			AbstractJavaNode childnode = (AbstractJavaNode) multiplicativeexpression.jjtGetChild(i);
			result = MethodAnlysistor.ProcessMethodCallAndAccess(childnode, sourcenode);
			
			if(!result.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
				return result;
		}
		return result;
	}

}
