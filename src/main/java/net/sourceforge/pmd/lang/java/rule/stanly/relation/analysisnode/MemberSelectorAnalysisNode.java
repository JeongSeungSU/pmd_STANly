package net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTMemberSelector;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArguments;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnalysisException;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodAnlaysis;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.MethodResult;

public class MemberSelectorAnalysisNode extends AbstractASTAnalysisNode {

	public MemberSelectorAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException 
	{
		ASTMemberSelector memberselector = (ASTMemberSelector)analysisnode;
		String ArgumentList = "";
		
		ASTTypeArguments typeargument = memberselector.getFirstDescendantOfType(ASTTypeArguments.class);
		if(typeargument != null)
		{
			ArgumentList += "<";
			List<ASTTypeArgument> argument = typeargument.findChildrenOfType(ASTTypeArgument.class);
			for(ASTTypeArgument ar : argument)
			{
				ArgumentList += MethodAnlysistor.ProcessMethodCallAndAccess(ar, sourcenode).TypeName;
				ArgumentList += ",";
			}
			ArgumentList = ArgumentList.substring(0,ArgumentList.length()-1);
			ArgumentList += ">";
		}
		return new MethodResult(ArgumentList +"."+ memberselector.getImage(),MethodAnlysistor.GetUnknownTypeName(),true);
	}

}
