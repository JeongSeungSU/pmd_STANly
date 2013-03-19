package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.analysisnode;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTMemberSelector;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArgument;
import net.sourceforge.pmd.lang.java.ast.ASTTypeArguments;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationResult;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.RelationAnalyst;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.exception.RelationAnalysisException;

/**
 * 자바의 문법중 특이한것 <B,C>A.abc() 여기서 <>안의 내용들
 * TargetResult =  <A,B,C>이런식으로 나옴
 * TypeName		=  unknown
 * IsProcess	=  true 
 * @since 2013. 3. 19.오후 9:44:57
 * @author JeongSeungsu
 */
public class MemberSelectorAnalysisNode extends AbstractASTAnalysisNode {

	public MemberSelectorAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, RelationResult> PrimaryExpressionList,
			RelationAnalyst anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RelationResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws RelationAnalysisException 
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
		return new RelationResult(ArgumentList +"."+ memberselector.getImage(),MethodAnlysistor.GetUnknownTypeName(),true);
	}

}
