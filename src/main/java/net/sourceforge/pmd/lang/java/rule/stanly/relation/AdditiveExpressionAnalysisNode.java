package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAdditiveExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class AdditiveExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public AdditiveExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTAdditiveExpression additiveexpression = (ASTAdditiveExpression)analysisnode;
		String ReturnType = "";
		for(int i = 0; i < additiveexpression.jjtGetNumChildren(); i++)
		{
			AbstractJavaNode childnode = (AbstractJavaNode) additiveexpression.jjtGetChild(i); 
			MethodResult result = MethodAnlysistor.ProcessMethodCallAndAccess(childnode, sourcenode);
			
			if(result.TypeName.equalsIgnoreCase("unknown"))
				ReturnType = result.TargetResult;
			else
				return result;
		}
		
		return new MethodResult(ReturnType,"unknown",false);
	}

}
