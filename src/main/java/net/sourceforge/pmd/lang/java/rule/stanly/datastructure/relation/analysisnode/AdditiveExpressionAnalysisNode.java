package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAdditiveExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * primaryexpression혹 리턴타입이 있는것중 하나가 리턴 없으면 마지막꺼...
 * TargetResult =  +로 표현된 것들중 하나의 PrimaryExpression의 타입을 리턴
 * TypeName		=  unknown
 * IsProcess	=  true
 * @since 2013. 2. 21.오후 6:49:34
 * @author JeongSeungsu
 */
public class AdditiveExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public AdditiveExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTAdditiveExpression additiveexpression = (ASTAdditiveExpression)analysisnode;
		String ReturnType = "";
		for(int i = 0; i < additiveexpression.jjtGetNumChildren(); i++)
		{
			AbstractJavaNode childnode = (AbstractJavaNode) additiveexpression.jjtGetChild(i); 
			RelationResult result = MethodAnlysistor.ProcessMethodCallAndAccess(childnode, sourcenode);
			
			if(result.TypeName.equalsIgnoreCase(MethodAnlysistor.GetUnknownTypeName()))
				ReturnType = result.TargetResult;
			else
				return result;
		}
		
		return new RelationResult(ReturnType,MethodAnlysistor.GetUnknownTypeName(),false);
	}

}
