package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimitiveType;
import net.sourceforge.pmd.lang.java.ast.ASTResultType;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode.AbstractASTAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode.DomainRelationList;

public class ResultTypeAnalysisNode extends AbstractASTAnalysisNode {

	public ResultTypeAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
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
				return new MethodResult("",MethodAnlysistor.GetUnknownTypeName(),false);
			MethodResult result = MethodAnlysistor.ProcessMethodCallAndAccess(primitivetype, sourcenode);
			ResultTypeName = result.TypeName; 
			Target = result.TargetResult;
		}
		
		
		return new MethodResult(Target,ResultTypeName,true);
	}

}
