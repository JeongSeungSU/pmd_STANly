package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTMemberSelector;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * TargetResult = b().ca.asdb(asfd(),bca) 이런것들!!!!!!!!전부 싹다 값은 아마 argument가 들가면 (+type+ab)이런식으로 될것임...재귀라... 
 * TypeName		= unknown
 * IsProcess	= 현재 처리된것이 Argument면 true 아니면 false
 * @since 2013. 2. 19.오전 4:12:02
 * @author JeongSeungsu
 */
public class PrimarySuffixAnalysisNode extends AbstractASTAnalysisNode {

	public PrimarySuffixAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see net.sourceforge.pmd.lang.java.rule.stanly.relation.AbstractASTAnalysisNode#AnalysisAST(net.sourceforge.pmd.lang.java.ast.AbstractJavaNode, net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode)
	 */
	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
	{
		boolean IsArgument = false;
		String NowString = "";
		ASTPrimarySuffix SuffixNode = (ASTPrimarySuffix)analysisnode;  
		ASTArguments arguments = SuffixNode.getFirstDescendantOfType(ASTArguments.class);
		if(!MacroFunctions.NULLTrue(arguments))
		{
			//파라미터 채우기...
			NowString = MethodAnlysistor.ProcessMethodCallAndAccess(arguments,sourcenode).TargetResult;
			IsArgument = true;
		}
		else
		{
			ASTMemberSelector selector = SuffixNode.getFirstDescendantOfType(ASTMemberSelector.class);
			if(!MacroFunctions.NULLTrue(selector))
				NowString += MethodAnlysistor.ProcessMethodCallAndAccess(selector,sourcenode).TargetResult;
			else
				NowString += "."+SuffixNode.getImage();
		}
		return new RelationResult(NowString, MethodAnlysistor.GetUnknownTypeName(),IsArgument);
	}

}
