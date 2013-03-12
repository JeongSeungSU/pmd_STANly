package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class ComponentDepandency implements AbstractAfterCalculator {
	
	Map<String,Integer> PackageRel = null;
	Map<String,Integer> UnitRel = null;
	List<String> PackagesName = null;
	List<String> UnitsName = null;
	public void calcMetric(ProjectDomain node)
	{
		PackageRel = new HashMap<String,Integer>();
		UnitRel = new HashMap<String,Integer>();
		PackagesName = new ArrayList<String>();
		UnitsName = new ArrayList<String>();
		visitChildren(node);
	}

	private float calcACD(List<String> nameSet,Map<String,Integer> rel)
	{
		int N = nameSet.size();
		for(int k=0;k<N;k++)
		{
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(i == j)	continue;
					String key1 = nameSet.get(i) + ">" + nameSet.get(j);
					if(rel.containsKey(key1))	continue;
					String key2 = nameSet.get(i) + ">" + nameSet.get(k);
					String key3 = nameSet.get(k) + ">" + nameSet.get(j);
					if(rel.containsKey(key2) && rel.containsKey(key3))
						rel.put(key1, 1);
				}
			}
		}
		double mccd = (N * (N-1));
		double ccd = rel.size();
		double acd = ccd / mccd * 100;
		
		return (float)acd;
	}
	private void calcMetric(LibraryDomain node)
	{
		
	//	node.metric.setACDPackage(acd);
		/*
		float packageACD = calcACD(PackagesName,PackageRel);				
		node.metric.setACDPackage(packageACD);
		
		float unitACD = calcACD(UnitsName,UnitRel);
		node.metric.setACDUnit(unitACD);
		*/
		
		/*List<String> strRel;
		if(UnitRel.size() > 0)
		{
			strRel = new ArrayList<String>(UnitRel.keySet());
			Collections.sort(strRel);
			System.out.println(strRel.size());
		}*/
	}
	
	public void calcMetric(PackageDomain node)
	{
		Map<String,Integer> relations = getUnitRelation(node,node,true);
		
		UnitRel.putAll(relations);
		UnitRel.putAll(getUnitRelation(node,node,false));
		
		PackageRel.putAll(getPackageRelation(node,node));
		
		
		PackagesName.add(node.getFullName());
		for(ElementNode child:node.getChildren())
			UnitsName.add(child.getFullName());
		
		int N = node.getChildren().size();
		for(int k=0;k<N;k++)
		{
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					if(i == j)	continue;
					String key1 = node.getChildren().get(i).getFullName() + ">" + node.getChildren().get(j).getFullName();
					if(relations.containsKey(key1))	continue;
					String key2 = node.getChildren().get(i).getFullName() + ">" + node.getChildren().get(k).getFullName();
					String key3 = node.getChildren().get(k).getFullName() + ">" + node.getChildren().get(j).getFullName();
					if(relations.containsKey(key2) && relations.containsKey(key3))
						relations.put(key1, 1);
				}
			}
		}
		float mccd = (N * (N-1));
		float ccd = relations.size();
		float acd = ccd / mccd * 100;
		
		node.metric.setACDPerUnit(acd);
		
		
		/*
		List<String> strRel;
		if(relations.size() > 0)
		{
			strRel = new ArrayList<String>(relations.keySet());
			Collections.sort(strRel);
			System.out.println(strRel.size());
		}*/
	}

	private void visitChildren(ElementNode node)
	{		
		for(ElementNode child:node.getChildren())
		{
			visitChildren(child);
			if(child.getType() == ElementNodeType.PACKAGE)
				calcMetric((PackageDomain)child);
			else if(child.getType() == ElementNodeType.LIBRARY)
				calcMetric((LibraryDomain)child);
		}
	}

	private Map<String,Integer> getUnitRelation(ElementNode ancestor,ElementNode node, boolean sameAnc)
	{
		String tarUnitName;
		String srcUnitName;
		Map<String,Integer> relations = new HashMap<String,Integer>();
		for(ElementNode child:node.getChildren())
		{		
			srcUnitName = child.getUnitName();
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(rel.getTargetNode().isAncestor(ancestor) != sameAnc)	continue;
				tarUnitName = rel.getTargetNode().getUnitName();
				if(!srcUnitName.equals(tarUnitName))
				{
					//String key = rel.getSourceNode().getPackageName() + "."  + srcSplit[ancSplit.length] + ">" + 
					//			 rel.getTargetNode().getPackageName() + "."  + tarSplit[ancSplit.length];
					String key = rel.getSourceNode().getUnitName() + ">" + rel.getTargetNode().getUnitName();
					Integer value = 1;
					relations.put(key,value);
				}
			}
			if(child.getChildren().size() > 0)
				relations.putAll(getUnitRelation(ancestor,child,sameAnc));
		}
		return relations;
	}
	
	
	private Map<String,Integer> getPackageRelation(ElementNode ancestor,ElementNode node )
	{
		Map<String,Integer> relations = new HashMap<String,Integer>();
		for(ElementNode child:node.getChildren())
		{
	
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(rel.getTargetNode().isAncestor(ancestor) == true)	continue;
				String key = rel.getSourceNode().getPackageName() + ">" + rel.getTargetNode().getPackageName();
				Integer value = 1;
				relations.put(key,value);

			}
			if(child.getChildren().size() > 0)
				relations.putAll(getPackageRelation(ancestor,child));
		}
		return relations;
	}
	
	
}
