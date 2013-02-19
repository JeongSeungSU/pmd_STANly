package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.Relations;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class NumberOfChildren extends AbstractAfterCalculator {

	public void calcMetric(ProjectDomain node)
	{
		visitClasses(node);
	}
	
	public int calcMetric(ClassDomain node)
	{	
		int noc = 0;
		for(DomainRelation rel:node.getRelationSources())
		{
			if(rel.getRelation() == Relations.EXTENDS)
				noc++;
		}
		return noc;
	}
	
	public void visitClasses(ElementNode node)
	{
		int noc;
		for(ElementNode child:node.getChildren())
		{
			if(child.getType() == ElementNodeType.CLASS)
			{
				noc = calcMetric((ClassDomain)child);
				((ClassDomain)child).metric.setNOC(noc);
			}
			visitClasses(child);
		}
	}
}
