package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAssignmentOperator;
import net.sourceforge.pmd.lang.java.ast.ASTCastExpression;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimitiveType;
import net.sourceforge.pmd.lang.java.ast.ASTType;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * (int)a; 같은 캐스팅 연산자
 * TargetResult =  Casting타입
 * TypeName		=  unknown
 * IsProcess	=  true
 * @since 2013. 3. 19.오후 9:37:35
 * @author JeongSeungsu
 */
public class CastExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public CastExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		ASTCastExpression castexpression = (ASTCastExpression)analysisnode;
		if( castexpression.jjtGetNumChildren() > 3)
			throw new RelationAnalysisException("castExpression인데 자식이3개라 이상함...");
		
		ASTType type = (ASTType)castexpression.jjtGetChild(0);

		AbstractJavaNode castType = type.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		
		if( MacroFunctions.NULLTrue(castType)){
			castType = type.getFirstDescendantOfType(ASTPrimitiveType.class);
		}
		String casttypename = MethodAnlysistor.ProcessMethodCallAndAccess(castType, sourcenode).TypeName;
		casttypename = MethodAnlysistor.TypeSperateApplyer(casttypename);
		
		return new RelationResult(casttypename,"unknown",true);
	}

}
