package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * A<b,c,d>에서 <>부분
 * TargetResult = X 
 * TypeName		= argument마다 타입 <T,AB<C>>이런것
 * IsProcess	= true
 * @since 2013. 2. 19.오전 4:09:03
 * @author JeongSeungsu
 */
public class TypeArgumentAnalysisNode extends AbstractASTAnalysisNode{

	public TypeArgumentAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTTypeArgument argument = (ASTTypeArgument)analysisnode; 
		ASTClassOrInterfaceType typename = argument.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		RelationResult result = new RelationResult("","",true);
		
		if(typename == null)
			result.TypeName = "?";
		else
			result = MethodAnlysistor.ProcessMethodCallAndAccess(typename, sourcenode);
		
		return result;
	}

}
