package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageSetDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class PackagetSetAverage extends AbstractAfterCalculator {

	public void calcMetric(ProjectDomain node)
	{
		visitClasses(node);
	}
	
	public void calcMetric(PackageSetDomain node)
	{
		for(ElementNode child:node.getChildren())
		{
			int numberOfPackages = 0;
			int numberOfMethods = 0;
			int numberOfClasses = 0;// inner class
			int numberOfClass = 0;	// unit class + inner class
			int numberOfFields = 0;
			int CC = 0;
			int Unit = 0;
			int ELOC = 0;			
			if(child.getType() == ElementNodeType.PACKAGESET)			   
			{				
				numberOfPackages = ((PackageSetDomain)child).metric.getNumberOfPakcages();
				numberOfMethods = ((PackageSetDomain)child).metric.getNumberOfMethods();
				numberOfClasses = ((PackageSetDomain)child).metric.getNumberOfClasses();
				numberOfClass = ((PackageSetDomain)child).metric.getNumberOfClass();
				numberOfFields = ((PackageSetDomain)child).metric.getNumberOfFields();
				CC = ((PackageSetDomain)child).metric.getTotalCC();
				Unit = ((PackageSetDomain)child).metric.getTotalUnit();
				ELOC = ((PackageSetDomain)child).metric.getTotalELOC();
			}
			else if(child.getType() == ElementNodeType.PACKAGE)
			{
				numberOfPackages = 1;
				numberOfMethods = ((PackageDomain)child).metric.getNumberOfMethods();
				numberOfClasses = ((PackageDomain)child).metric.getNumberOfClasses();
				numberOfClass = ((PackageDomain)child).metric.getNumberOfClass();
				numberOfFields = ((PackageDomain)child).metric.getNumberOfFields();
				CC = ((PackageDomain)child).metric.getTotalCC();
				Unit = ((PackageDomain)child).metric.getUnits();
				ELOC = ((PackageDomain)child).metric.getLOC();
			}
			node.metric.addNumberOfPackages(numberOfPackages);
			node.metric.addNumberOfMethods(numberOfMethods);
			node.metric.addNumberOfClasses(numberOfClasses);
			node.metric.addNumberOfClass(numberOfClass);
			node.metric.addNumberOfFields(numberOfFields);
			node.metric.addCC(CC);
			node.metric.addTotalUnit(Unit);
			node.metric.addTotalELOC(ELOC);
		}
	}
	
	public void visitClasses(ElementNode node)
	{
		for(ElementNode child:node.getChildren())
		{
			visitClasses(child);
			if(child.getType() == ElementNodeType.PACKAGESET)
			{
				calcMetric((PackageSetDomain)child);
			}
		}
	}
}
