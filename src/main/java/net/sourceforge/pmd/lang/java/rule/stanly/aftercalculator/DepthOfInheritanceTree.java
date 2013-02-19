package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.Relations;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class DepthOfInheritanceTree extends AbstractAfterCalculator {
	
	
	public void calcMetric(ProjectDomain node)
	{
		visitClasses(node);
	}
	
	public int calcMetric(ClassDomain node)
	{
		if(node.metric.getDIT() != 0)	
			return node.metric.getDIT();
		for(DomainRelation rel:node.getRelationTargets())
		{
			if(rel.getRelation() == Relations.EXTENDS)
			{
				return 1 + calcMetric((ClassDomain)rel.getTargetNode());
			}
		}
		return 1;
	}
	
	public void visitClasses(ElementNode node)
	{
		int dit;
		for(ElementNode child:node.getChildren())
		{
			if(child.getType() == ElementNodeType.CLASS)
			{
				dit = calcMetric((ClassDomain)child);
				((ClassDomain)child).metric.setDIT(dit);
			}
			visitClasses(child);
		}
	}
	
}
