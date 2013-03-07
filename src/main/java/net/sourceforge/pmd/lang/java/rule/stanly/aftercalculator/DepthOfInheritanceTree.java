package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.Relations;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class DepthOfInheritanceTree implements AbstractAfterCalculator {
	
	
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
		if(node.getType() == ElementNodeType.CLASS)
			return 1;
		else //interface or enum
			return 0;
	}
	
	private void addDITtoPackage(int dit, ElementNode node)
	{
		ElementNode packageNode = node;
		while(packageNode != null && packageNode.getType() != ElementNodeType.PACKAGE)
			packageNode = packageNode.getParent();
		if(packageNode == null)	return;
		((PackageDomain)packageNode).metric.addDIT(dit);
	}
	private void addDITtoLibrary(int dit, ElementNode node)
	{
		ElementNode libraryNode = node;
		while(libraryNode != null && libraryNode.getType() != ElementNodeType.LIBRARY)
			libraryNode = libraryNode.getParent();
		if(libraryNode == null)	return;
		((LibraryDomain)libraryNode).metric.addDIT(dit);
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
				if(dit > 0)
				{
					addDITtoPackage(dit,child);
					addDITtoLibrary(dit,child);
				}
			}
			visitClasses(child);
		}
	}
	
}
