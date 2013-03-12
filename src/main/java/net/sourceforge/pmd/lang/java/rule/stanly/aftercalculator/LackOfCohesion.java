package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.Relations;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.FieldDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.MethodDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class LackOfCohesion implements AbstractAfterCalculator {

	public void calcMetric(ProjectDomain node) {
		// TODO Auto-generated method stub
		DepthFirstTravesalToLoc(node);
	}
	
	public void DepthFirstTravesalToLoc(ElementNode node)
	{
		int LCOM = 0;
		if(node.getType() == ElementNodeType.CLASS)
		{
			LCOM = CalculateLCOM((ClassDomain)node);
			((ClassDomain)node).metric.addLCOM(LCOM);
			
			ElementNode parentnode = node.getParent();
			while(parentnode != null)
			{
				if(parentnode.getType() == ElementNodeType.PACKAGE)
					((PackageDomain)parentnode).metric.addLCOM(LCOM);
				if(parentnode.getType() == ElementNodeType.LIBRARY)
					((LibraryDomain)parentnode).metric.addLCOM(LCOM);
				parentnode = parentnode.getParent();
			}
		}

		for(ElementNode childnode:node.getChildren())
			DepthFirstTravesalToLoc(childnode);
	}
	private int CalculateLCOM(ClassDomain classnode)
	{
		List<MethodDomain> MethodList = new ArrayList<MethodDomain>();
		List<FieldDomain>  FieldList = new ArrayList<FieldDomain>();
		
		for(ElementNode childnode :classnode.getChildren())
		{
			if(childnode instanceof MethodDomain)
				MethodList.add((MethodDomain)childnode);
			if(childnode instanceof FieldDomain)
				FieldList.add((FieldDomain)childnode);
		}
		
		Map<Integer,List<FieldDomain>> SetList = new HashMap<Integer, List<FieldDomain>>();
		
		for(int i = 0 ; i < MethodList.size(); i++)
		{
			MethodDomain methodnode = MethodList.get(i);
			List<FieldDomain> AccessFieldToMethodList= new ArrayList<FieldDomain>();
			
			for(DomainRelation methodrelations:methodnode.getRelationTargets())
			{
				for(FieldDomain classfield : FieldList)
				{
					if( ( methodrelations.getRelation() == Relations.ACCESSES ) &&
						( methodrelations.getTargetNode() ==  classfield) )
						{
							AccessFieldToMethodList.add(classfield);
						}
				}
			}
			SetList.put(i, AccessFieldToMethodList);
		}
		
		int P = 0;
		int Q = 0;
		for(int i = 0 ; i< SetList.size() - 1; i++)
		{
			for(int j = i+1; j< SetList.size(); j++)
			{
				if(IsIntersection(SetList.get(i),SetList.get(j)))
					Q++;
				else
					P++;
			}
		}
	
		return Q > P ? 0 : P - Q;  
	}
	
	private boolean IsIntersection(List<FieldDomain> data1, List<FieldDomain> data2)
	{
		for(FieldDomain field1 : data1)
		{
			for(FieldDomain field2 : data2)
			{
				if(field1 == field2)
					return true;
			}
		}
		return false;
	}
	
}
