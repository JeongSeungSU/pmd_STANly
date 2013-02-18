package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.List;

import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public abstract class AbstractASTParserNode {
	private List<DomainRelation> relationlist;
	
	public AbstractASTParserNode(List<DomainRelation> list)
	{
		relationlist = list;
	}
	
	abstract public MethodResult AnalysisAST(AbstractJavaNode analysisnode, ElementNode sourcenode);
}
