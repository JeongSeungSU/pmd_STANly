package net.sourceforge.pmd.lang.java.rule.stanly.metriccalculator.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ProjectDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.Relations;

public class ResponseForClass implements AbstractAfterCalculator {
	
	public void calcMetric(ProjectDomain node)
	{
		visitClasses((ElementNode)node);
	}
	
	public int getCallRelationCount(ElementNode node)
	{
		int count = 0;
		for(DomainRelation rel:node.getRelationTargets())
		{
			if(rel.getRelation() == Relations.CALLS)
				count++;
		}
		return count;
	}
	
	public int calcMetric(ClassDomain node)
	{
		int rfc = 0;
		for(ElementNode child:node.getChildren())
		{
			if(child.getType() == ElementNodeType.METHOD || child.getType() == ElementNodeType.CONSTRUCTOR)
			{
				rfc++;
				rfc += getCallRelationCount(child);
			}
		}
		return rfc;
	}
	
	private void addRFCtoPackage(int rfc, ElementNode node)
	{
		ElementNode packageNode = node;
		while(packageNode != null && packageNode.getType() != ElementNodeType.PACKAGE)
			packageNode = packageNode.getParent();
		if(packageNode == null)	return;
		((PackageDomain)packageNode).metric.addRFC(rfc);
	}
	private void addRFCtoLibrary(int rfc, ElementNode node)
	{
		ElementNode libraryNode = node;
		while(libraryNode != null && libraryNode.getType() != ElementNodeType.LIBRARY)
			libraryNode = libraryNode.getParent();
		if(libraryNode == null)	return;
		((LibraryDomain)libraryNode).metric.addRFC(rfc);
	}
	
	public void visitClasses(ElementNode node)
	{
		int rfc;
		for(ElementNode child:node.getChildren())
		{
			if(child.getType() == ElementNodeType.CLASS)
			{
				rfc = calcMetric((ClassDomain)child);
				((ClassDomain)child).metric.setRFC(rfc);
				if(rfc > 0)
				{
					addRFCtoPackage(rfc,child);
					addRFCtoLibrary(rfc,child);
				}
			}
			visitClasses(child);
		}
	}
}
