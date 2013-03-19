package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;

/**
 * TargetResult = X 
 * TypeName		= argument마다 타입 <T,AB<C>>이런것
 * IsProcess	= true
 * @since 2013. 2. 19.오전 4:09:03
 * @author JeongSeungsu
 */
public class TypeArgumentAnalysisNode extends AbstractASTAnalysisNode{

	public TypeArgumentAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTTypeArgument argument = (ASTTypeArgument)analysisnode; 
		ASTClassOrInterfaceType typename = argument.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		MethodResult result = new MethodResult("","",true);
		
		if(typename == null)
			result.TypeName = "?";
		else
			result = MethodAnlysistor.ProcessMethodCallAndAccess(typename, sourcenode);
		
		return result;
	}

}
