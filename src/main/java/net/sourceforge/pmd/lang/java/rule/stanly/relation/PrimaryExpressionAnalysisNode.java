package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTAllocationExpression;
import net.sourceforge.pmd.lang.java.ast.ASTArguments;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.Relations;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

/**
 * TargetResult = 
 * TypeName		= 
 * IsProcess	= 
 * @since 2013. 2. 19.오전 4:05:45
 * @author JeongSeungsu
 */
public class PrimaryExpressionAnalysisNode extends AbstractASTParserNode{


	public PrimaryExpressionAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException {
		
		String LastString = "";
		boolean ToggleMethod = false;
		ASTPrimaryExpression primaryexpression = (ASTPrimaryExpression)analysisnode;
		
		MethodResult PrefixResult = MethodAnlysistor.ProcessMethodCallAndAccess(
									(AbstractJavaNode)primaryexpression.jjtGetChild(0), sourcenode);
		
		
		
		for(int i = 1; i < primaryexpression.jjtGetNumChildren(); i++)
		{
			String NowString = "";
			
			Node ChildrenNode = primaryexpression.jjtGetChild(i);
			MethodResult SuffixResult = MethodAnlysistor.ProcessMethodCallAndAccess((AbstractJavaNode)ChildrenNode, sourcenode);
			

			LastString += NowString;
		}
		
		if(ToggleMethod)
			RelationList.AddRelation(Relations.CALLS, sourcenode.getFullName(),LastString,sourcenode,null);
		else
			RelationList.AddRelation(Relations.ACCESSES, sourcenode.getFullName(),LastString,sourcenode,null);
		
		return new MethodResult(LastString,"unknown",true);
	}

}
