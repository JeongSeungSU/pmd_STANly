package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTResource;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode.AbstractASTAnalysisNode;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode.DomainRelationList;

public class ResourceAnalysisNode extends AbstractASTAnalysisNode {

	public ResourceAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,
			ElementNode sourcenode) throws MethodAnalysisException {

		ASTResource resource = (ASTResource)analysisnode;
		return null;
	}

}
