package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.FieldDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.MethodDomain;
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
		List<DomainRelation> relations = getChilrenRelation(node);
		node.metric.setFat(relations.size());
		addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void calcMatric(PackageDomain node)
	{
		calculateChildren(node);
		List<DomainRelation> relations = getChilrenRelation(node);
		node.metric.setFat(relations.size());
		addToLibraryDomain(node.getParent(),node.metric.getFat());
	}
	
	public void calcMatric(ClassDomain node)
	{
		List<DomainRelation> relations = getChilrenRelation(node);
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
	
	List<DomainRelation> getChilrenRelation(ElementNode node)
	{
		List<DomainRelation> relations = new ArrayList<DomainRelation>();
		for(ElementNode child:node.getChildren())
		{
			for(DomainRelation rel:child.getRelationSources())
			{
				if(node != rel.getSourceNode().getParent())	continue;
				boolean flag = false;
				for(DomainRelation dup:relations)
					if(dup.getSource().equals(rel.getSource()) && dup.getTarget().equals(rel.getTarget()))
						flag = true;
				if(flag == false)
					relations.add(rel);
			}
		}
		return relations;
	}
}
