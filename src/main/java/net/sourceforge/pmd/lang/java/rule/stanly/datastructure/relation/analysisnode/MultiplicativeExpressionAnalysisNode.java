package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTMultiplicativeExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 곱셈 
 * A * B * C 에서 A 하나를 봅아 분석해 리턴
 * TargetResult =  x
 * TypeName		=  하나를 뽑아 분석해 리턴
 * IsProcess	=  true
 * @since 2013. 3. 19.오후 9:46:17
 * @author JeongSeungsu
 */
public class MultiplicativeExpressionAnalysisNode extends
		AbstractASTAnalysisNode {

	public MultiplicativeExpressionAnalysisNode(
			DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTMultiplicativeExpression multiplicativeexpression = (ASTMultiplicativeExpression) analysisnode;
		RelationResult result = new RelationResult("",MethodAnlysistor.GetUnknownTypeName(),true);
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
