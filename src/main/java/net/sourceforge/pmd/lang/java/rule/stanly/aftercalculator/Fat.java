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
			if(child instanceof LibraryDomain)		calcMatric((LibraryDomain)child);
			if(child instanceof PackageSetDomain)	calcMatric((PackageSetDomain)child);
			if(child instanceof PackageDomain)		calcMatric((PackageDomain)child);
			if(child instanceof ClassDomain)		calcMatric((ClassDomain)child);
			//if(child instanceof MethodDomain)		calcMatric((MethodDomain)child);
			//if(child instanceof FieldDomain)		calcMatric((FieldDomain)child);
		}
	}
	
	public void calcMatric(ProjectDomain node)
	{
		calculateChildren(node);
	}
	
	public void calcMatric(LibraryDomain node)
	{
		calculateChildren(node);
		//추후 추가해야함 LibraryDomain테스트 케이스를 만들어야함 
	}
	
	public void calcMatric(PackageSetDomain node)
	{
		calculateChildren(node);
		Set<String> relations = getChilrenRelation(node,node);
		node.metric.setFat(relations.size());
		addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void calcMatric(PackageDomain node)
	{
		calculateChildren(node);
		Set<String> relations = getChilrenRelation(node,node);		
		node.metric.setFat(relations.size());
		addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void calcMatric(ClassDomain node)
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
	public void calcMatric(MethodDomain node)
	{
		calculateChildren(node);
	}
	
	public void calcMatric(FieldDomain node)
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
			if(srcSplit.length > 4 && srcSplit[4].equals("RegistryStrategy"))
				System.out.println("");
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(!rel.getTargetNode().isAncestor(ancestor))	continue;
				tarSplit = rel.getTargetNode().getFullName().split("\\.");
				if(ancSplit.length < srcSplit.length && ancSplit.length < tarSplit.length && 
					!srcSplit[ancSplit.length].equals(tarSplit[ancSplit.length]))
				{
					//if(!rel.getSource().startsWith(ancestor))	continue;
					//String distStrSrc = rel.getSource().substring(ancestor.length());
					//System.out.println(distStrSrc);

					//if(node != rel.getSourceNode().getParent())	continue;
					//boolean flag = false;					
					//for(HashMap<String,String> dup:relations) //중복 확인
					//	if(dup.getSource().equals(rel.getSource()) && dup.getTarget().equals(rel.getTarget()))
					//		flag = true;
					//if(flag == false)
					System.out.println(ancestor.getFullName() + " : " + child.getFullName() + " > " + rel.getTargetNode().getFullName());
					relations.add(srcSplit[ancSplit.length] + ">" + tarSplit[ancSplit.length]);
				}
			}
			if(child.getChildren().size() > 0)
				relations.addAll(getChilrenRelation(ancestor,child));
		}
		return relations;
	}
	
	
}
