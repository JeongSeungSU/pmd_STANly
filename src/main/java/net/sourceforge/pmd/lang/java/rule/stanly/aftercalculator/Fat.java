package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageSetDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class Fat implements AbstractAfterCalculator{
	
	public void calculateChildren(ElementNode node)
	{
		for(ElementNode child:node.getChildren())
		{
			if(child instanceof LibraryDomain)		calcMetric((LibraryDomain)child);
			if(child instanceof PackageSetDomain)	calcMetric((PackageSetDomain)child);
			if(child instanceof PackageDomain)		calcMetric((PackageDomain)child);
			if(child instanceof ClassDomain)		calcMetric((ClassDomain)child);
			//if(child instanceof MethodDomain)		calcMetric((MethodDomain)child);
			//if(child instanceof FieldDomain)		calcMetric((FieldDomain)child);
		}
	}
	
	public void calcMetric(ProjectDomain node)
	{
		calculateChildren(node);
	}
	
	public void calcMetric(LibraryDomain node)
	{
		calculateChildren(node);
		//추후 추가해야함 LibraryDomain테스트 케이스를 만들어야함 
	}
	
	public void calcMetric(PackageSetDomain node)
	{
		calculateChildren(node);
		Map<String,Integer> relations = getPackageRelation(node,node);
		node.metric.setFat(relations.size());
		addToLibraryFatPackage(node.getParent(),node.metric.getFat());
		
		/*List<String> strRel;
		if(relations.size() > 0)
		{
			strRel = new ArrayList<String>(relations.keySet());
			Collections.sort(strRel);
			System.out.println(strRel.size());
		}*/
	}
	
	public void calcMetric(PackageDomain node)
	{
		calculateChildren(node);
		Map<String,Integer> relations = getChilrenRelation(node,node);		
		node.metric.setFat(relations.size());
		addToLibraryFatUnit(node.getParent(),node.metric.getFat());
	}
	
	public void calcMetric(ClassDomain node)
	{
		Map<String,Integer> relations = getChilrenRelation(node,node);
		node.metric.setFat(relations.size());
		//addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void addToLibraryFatPackage(ElementNode node,int fat)
	{
		while(node != null && !(node instanceof LibraryDomain))
			node = node.getParent();
		if(node instanceof LibraryDomain)
			((LibraryDomain)node).metric.addFatPackages(fat);
	}
	public void addToLibraryFatUnit(ElementNode node,int fat)
	{
		while(node != null && !(node instanceof LibraryDomain))
			node = node.getParent();
		if(node instanceof LibraryDomain)
			((LibraryDomain)node).metric.addFatUnits(fat);
	}
	/*
	public void calcMetric(MethodDomain node)
	{
		calculateChildren(node);
	}
	
	public void calcMetric(FieldDomain node)
	{
		calculateChildren(node);
	}*/
	
	private Map<String,Integer> getChilrenRelation(ElementNode ancestor,ElementNode node)
	{
		String[] ancSplit = ancestor.getFullName().split("\\.");
		String[] tarSplit = null;
		String[] srcSplit = null;
		Map<String,Integer> relations = new HashMap<String,Integer>();
		for(ElementNode child:node.getChildren())
		{
			srcSplit = child.getFullName().split("\\.");			
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(!rel.getTargetNode().isAncestor(ancestor))	continue;
				tarSplit = rel.getTargetNode().getFullName().split("\\.");
				if(ancSplit.length < srcSplit.length && ancSplit.length < tarSplit.length && 
					!srcSplit[ancSplit.length].equals(tarSplit[ancSplit.length]))
				{
					String key = srcSplit[ancSplit.length] + ">" + tarSplit[ancSplit.length];
					Integer value = 0;
					if(relations.containsKey(key))
						value = relations.get(key);
					value++;
					relations.put(key,value);
				}
			}
			if(child.getChildren().size() > 0)
				relations.putAll(getChilrenRelation(ancestor,child));
		}
		return relations;
	}
	private Map<String,Integer> getPackageRelation(ElementNode ancestor,ElementNode node)
	{
		Map<String,Integer> relations = new HashMap<String,Integer>();
		for(ElementNode child:node.getChildren())
		{		
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(!rel.getTargetNode().isAncestor(ancestor))	continue;
				if(!rel.getSourceNode().getPackageName().equals(rel.getTargetNode().getPackageName()))
				{
					String key = rel.getSourceNode().getPackageName() + ">" + rel.getTargetNode().getPackageName();
					Integer value = 0;
					if(relations.containsKey(key))
						value = relations.get(key);
					value++;
					relations.put(key,value);
				}
			}
			if(child.getChildren().size() > 0)
				relations.putAll(getPackageRelation(ancestor,child));
		}
		return relations;
	}
	
}
