package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAndExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

/**
 * 
 * @since 2013. 2. 21.오후 6:49:29
 * @author JeongSeungsu
 */
public class AndExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public AndExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTAndExpression andexpression = (ASTAndExpression)analysisnode;
		MethodResult childresult = new MethodResult("",MethodAnlysistor.GetUnknownTypeName(),true);
		
		for(int i = 0 ; i < andexpression.jjtGetNumChildren(); i++)
		{
			AbstractJavaNode childnode = (AbstractJavaNode)andexpression.jjtGetChild(i);
			childresult = MethodAnlysistor.ProcessMethodCallAndAccess(childnode, sourcenode);
			
			if(!childresult.TypeName.equals(MethodAnlysistor.GetUnknownTypeName()))
				return childresult;
		}
		return childresult;
	}

}
