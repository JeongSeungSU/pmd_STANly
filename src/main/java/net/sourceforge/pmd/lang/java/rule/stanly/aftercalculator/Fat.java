package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageSetDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class Fat extends AbstractAfterCalculator{
	
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
		Set<String> relations = getChilrenRelation(node,node);
		node.metric.setFat(relations.size());
		addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void calcMetric(PackageDomain node)
	{
		calculateChildren(node);
		Set<String> relations = getChilrenRelation(node,node);		
		node.metric.setFat(relations.size());
		addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void calcMetric(ClassDomain node)
	{
		Set<String> relations = getChilrenRelation(node,node);
		node.metric.setFat(relations.size());
		addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void addToLibraryDomain(ElementNode node,int fat)
	{
		while(node != null && !(node instanceof LibraryDomain))
			node = node.getParent();
		if(node instanceof LibraryDomain)
			((LibraryDomain)node).metric.addFat(fat);
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
	
	Set<String> getChilrenRelation(ElementNode ancestor,ElementNode node)
	{
		String[] ancSplit = ancestor.getFullName().split("\\.");
		String[] tarSplit = null;
		String[] srcSplit = null;
		Set<String> relations = new HashSet<String>();
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
					relations.add(srcSplit[ancSplit.length] + ">" + tarSplit[ancSplit.length]);
				}
			}
			if(child.getChildren().size() > 0)
				relations.addAll(getChilrenRelation(ancestor,child));
		}
		return relations;
	}
	
	
}
