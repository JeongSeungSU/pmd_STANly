package net.sourceforge.pmd.lang.java.rule.stanly.metriccalculator.aftercalculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ProjectDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.relation.analysisnode.DomainRelation;

public class ComponentDepandency implements AbstractAfterCalculator {
	
	Set<String> PackageRel = null;
	Set<String> UnitRel = null;
	List<String> PackagesName = null;
	List<String> UnitsName = null;
	Set<String> alreadDone = null;
	
	public void calcMetric(ProjectDomain node)
	{
		PackageRel = new HashSet<String>();
		UnitRel = new HashSet<String>();
		PackagesName = new ArrayList<String>();
		UnitsName = new ArrayList<String>();
		visitChildren(node);
	}

	private Set<String> calcCD(String startNodeName,Set<String> visited,List<String> nameSet,Set<String> rel)
	{
		Set<String> newRel= new HashSet<String>();
		Set<String> visitable;
		visited.add(startNodeName);
		for(String nextNodeName:nameSet)
		{
			if(visited.contains(nextNodeName))	continue;
			String key1 = startNodeName + ">" + nextNodeName;
			if(!rel.contains(key1))	continue;
			
			visitable = calcCD(nextNodeName,visited,nameSet,rel);
			
			for(String newRelTarget:visitable)
				rel.add(startNodeName + ">" + newRelTarget);
			
			newRel.addAll(visitable);
			newRel.add(nextNodeName);
			
		}
		
		
		return newRel;
	}
	
	private void fillVisitable(Set<String> visitable, List<String> nameSet, String nodeName,Set<String> rel)
	{
		for(String nextName:nameSet)
		{
			if(nextName.equals(nameSet))	continue;
			visitable.add(nextName);
		}
	}
	
	private float calcACD(List<String> nameSet,Set<String> rel)
	{
		Set<String> visitable;
		boolean doneFlag;
		alreadDone = new HashSet<String>();
		for(String nodeName:nameSet)
		{
			visitable = new HashSet<String>();			
			alreadDone.add(nodeName);
			doneFlag = false;
			for(String touch:alreadDone)
			{
				if(touch.equals(nodeName))	continue;
				if(rel.contains(nodeName+">"+touch) && rel.contains(touch+">"+nodeName))
				{
					doneFlag = true;
					fillVisitable(visitable,nameSet,nodeName,rel);					
					break;
				}
			}
			if(!doneFlag)
			{
				Set<String> visited = new HashSet<String>();
				visitable.addAll(calcCD(nodeName,visited,nameSet,rel));
			}
			for(String newRelTarget:visitable)
				rel.add(nodeName + ">" + newRelTarget);
			//rel.putAll(calcCD(nodeName,nameSet,rel));
			////System.out.println(nodeName + " " + visitable.size() );
		}
		int N = nameSet.size();
		/*for(int k=0;k<N;k++)
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
		}*/
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
			//System.out.println(strRel.size());
		}*/
	}
	
	public void calcMetric(PackageDomain node)
	{
		Set<String> innerRelations = getUnitRelation(node,node,true);
		List<String> innerNameSet = new ArrayList<String>();
		
		UnitRel.addAll(innerRelations);
		UnitRel.addAll(getUnitRelation(node,node,false));
		
		PackageRel.addAll(getPackageRelation(node,node));
		
		
		PackagesName.add(node.getFullName());
		for(ElementNode child:node.getChildren())
			innerNameSet.add(child.getFullName());
		UnitsName.addAll(innerNameSet);
		/*		
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
		*/
		
		//for(ElementNode child:node.getChildren())
		//	innerNameSet.add(child.getFullName());
		
		float acd = calcACD(innerNameSet,innerRelations);
		node.metric.setACDPerUnit(acd);
		
		
		/*
		List<String> strRel;
		if(relations.size() > 0)
		{
			strRel = new ArrayList<String>(relations.keySet());
			Collections.sort(strRel);
			//System.out.println(strRel.size());
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

	private Set<String> getUnitRelation(ElementNode ancestor,ElementNode node, boolean sameAnc)
	{
		String tarUnitName;
		String srcUnitName;
		Set<String> relations = new HashSet<String>();
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
					relations.add(key);
				}
			}
			if(child.getChildren().size() > 0)
				relations.addAll(getUnitRelation(ancestor,child,sameAnc));
		}
		return relations;
	}
	
	
	private Set<String> getPackageRelation(ElementNode ancestor,ElementNode node )
	{
		Set<String> relations = new HashSet<String>();
		for(ElementNode child:node.getChildren())
		{
	
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(rel.getTargetNode().isAncestor(ancestor) == true)	continue;
				String key = rel.getSourceNode().getPackageName() + ">" + rel.getTargetNode().getPackageName();
				relations.add(key);

			}
			if(child.getChildren().size() > 0)
				relations.addAll(getPackageRelation(ancestor,child));
		}
		return relations;
	}
	
	
}
