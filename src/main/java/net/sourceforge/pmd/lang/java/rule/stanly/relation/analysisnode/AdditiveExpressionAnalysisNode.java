package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAdditiveExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;

/**
 * primaryexpression혹 리턴타입이 있는것중 하나가 리턴 없으면 마지막꺼...
 * @since 2013. 2. 21.오후 6:49:34
 * @author JeongSeungsu
 */
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
			
			if(result.TypeName.equalsIgnoreCase(MethodAnlysistor.GetUnknownTypeName()))
				ReturnType = result.TargetResult;
			else
				return result;
		}
		
		return new MethodResult(ReturnType,MethodAnlysistor.GetUnknownTypeName(),false);
	}

}
