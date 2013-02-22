package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTAssignmentOperator;
import net.sourceforge.pmd.lang.java.ast.ASTCastExpression;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimitiveType;
import net.sourceforge.pmd.lang.java.ast.ASTType;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class CastExpressionAnalysisNode extends AbstractASTAnalysisNode {

	public CastExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTCastExpression castexpression = (ASTCastExpression)analysisnode;
		if( castexpression.jjtGetNumChildren() > 3)
			throw new MethodAnalysisException("castExpression인데 자식이3개라 이상함...");
		
		ASTType type = (ASTType)castexpression.jjtGetChild(0);

		AbstractJavaNode castType = type.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		
		if( MacroFunctions.NULLTrue(castType)){
			castType = type.getFirstDescendantOfType(ASTPrimitiveType.class);
		}
		String casttypename = MethodAnlysistor.ProcessMethodCallAndAccess(castType, sourcenode).TypeName;
		casttypename = MethodAnlysistor.TypeSperateApplyer(casttypename);
		
		return new MethodResult(casttypename,"",true);
	}

}
