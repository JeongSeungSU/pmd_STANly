package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimitiveType;
import net.sourceforge.pmd.lang.java.ast.ASTResultType;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * return 값
 * 
 * @since 2013. 3. 19.오후 9:50:20
 * @author JeongSeungsu
 */
public class ResultTypeAnalysisNode extends AbstractASTAnalysisNode {

	public ResultTypeAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		
		ASTResultType resulttype = (ASTResultType)analysisnode;
		
		String ResultTypeName = MethodAnlysistor.GetUnknownTypeName();
		String Target = "";
		
		ASTClassOrInterfaceType classtype = resulttype.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		if(!MacroFunctions.NULLTrue(classtype))
		{
			ResultTypeName = MethodAnlysistor.ProcessMethodCallAndAccess(classtype, sourcenode).TypeName;
			Target = ResultTypeName;
		}
		else
		{
			ASTPrimitiveType primitivetype = resulttype.getFirstDescendantOfType(ASTPrimitiveType.class);
			if(MacroFunctions.NULLTrue(primitivetype))
				return new RelationResult("",MethodAnlysistor.GetUnknownTypeName(),false);
			RelationResult result = MethodAnlysistor.ProcessMethodCallAndAccess(primitivetype, sourcenode);
			ResultTypeName = result.TypeName; 
			Target = result.TargetResult;
		}
		
		
		return new RelationResult(Target,ResultTypeName,true);
	}

}
