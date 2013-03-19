package net.sourceforge.pmd.lang.java.rule.stanly.metriccalculator.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ProjectDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.Relations;

public class NumberOfChildren implements AbstractAfterCalculator {

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
	
	private void addNOCtoPackage(int noc, ElementNode node)
	{
		ElementNode packageNode = node;
		while(packageNode != null && packageNode.getType() != ElementNodeType.PACKAGE)
			packageNode = packageNode.getParent();
		if(packageNode == null)	return;
		((PackageDomain)packageNode).metric.addNOC(noc);
	}
	private void addNOCtoLibrary(int noc, ElementNode node)
	{
		ElementNode libraryNode = node;
		while(libraryNode != null && libraryNode.getType() != ElementNodeType.LIBRARY)
			libraryNode = libraryNode.getParent();
		if(libraryNode == null)	return;
		((LibraryDomain)libraryNode).metric.addNOC(noc);
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
				if(noc > 0)
				{
					addNOCtoPackage(noc,child);
					addNOCtoLibrary(noc,child);
				}
			}
			visitClasses(child);
		}
	}
}
