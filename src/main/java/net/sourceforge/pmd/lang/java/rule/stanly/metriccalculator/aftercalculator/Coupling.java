package net.sourceforge.pmd.lang.java.rule.stanly.metriccalculator.aftercalculator;

import java.util.HashMap;
import java.util.Map;

import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.domainelement.ProjectDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation.DomainRelation;

public class Coupling implements AbstractAfterCalculator {

	public void calcMetric(ProjectDomain node)
	{
		visitChildren(node);
	}	

	private void calcMetric(PackageDomain node)
	{
		int afferent = calcAfferentCoupling(node,node).size();
		int efferent = calcEfferentCoupling(node,node).size();
		
		node.metric.setAfferentCoupling(afferent);
		node.metric.setEfferentCoupling(efferent);
		
		float instability = (afferent + efferent == 0) ? 0 : (float)efferent / (float)(afferent + efferent);
		
		node.metric.setInstability(instability);
		
		int numOfAbstract = node.metric.getNumberOfAbstract();
		int numOfUnit = node.metric.getUnits();
		float abstractness = (numOfUnit == 0) ? 0 : (float)numOfAbstract / (float)numOfUnit;
		
		node.metric.setAbstractness(abstractness);
		
		float distance = (abstractness + instability - 1) / (float)Math.sqrt(2);		
		node.metric.setDistance(distance);
		
		
		ElementNode libraryNode = node;
		do libraryNode = libraryNode.getParent();
		while(libraryNode != null && !(libraryNode instanceof LibraryDomain));
		
		if(libraryNode != null)
		{
			((LibraryDomain)libraryNode).metric.addDistance(distance);
			((LibraryDomain)libraryNode).metric.addDistanceAbsolute(Math.abs(distance));
		}
	}
	private void calcMetric(ClassDomain node)
	{
		int afferent = calcAfferentCoupling(node,node).size();
		int efferent = calcEfferentCoupling(node,node).size();
		
		node.metric.setAfferentCoupling(afferent);
		node.metric.setEfferentCoupling(efferent);
	}
	private void visitChildren(ElementNode node)
	{
		for(ElementNode child:node.getChildren())
		{
			visitChildren(child);
			if(child.getType() == ElementNodeType.CLASS)
			{
				calcMetric((ClassDomain)child);
			}
			else if(child.getType() == ElementNodeType.PACKAGE)
			{
				calcMetric((PackageDomain)child);
			}
		}
	}
	
	/*private int calcEfferentCoupling(ElementNode ancestor,ElementNode node)
	{
		int count = 0;

		
		for(DomainRelation rel:node.getRelationTargets())//자기자신 노드의 Relation우선 계산
		{
			if(rel.getTargetNode().isAncestor(ancestor))	continue;
			count++;
		}
		
		for(ElementNode child:node.getChildren())
		{	
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(rel.getTargetNode().isAncestor(ancestor))	continue;
				count++;
			}
			if(child.getChildren().size() > 0)
				count += calcEfferentCoupling(ancestor,child);
		}
		return count;
	}
	
	private int calcAfferentCoupling(ElementNode ancestor,ElementNode node)
	{
		int count = 0;
		
		for(DomainRelation rel:node.getRelationSources())//자기자신 노드의 Relation우선 계산
		{
			if(rel.getSourceNode().isAncestor(ancestor))	continue;
			count++;
		}
		
		for(ElementNode child:node.getChildren())
		{	
			for(DomainRelation rel:child.getRelationSources())
			{
				if(rel.getSourceNode().isAncestor(ancestor))	continue;
				count++;
			}
			if(child.getChildren().size() > 0)
				count += calcAfferentCoupling(ancestor,child);
		}
		return count;
	}*/
	
	private Map<String,Integer> calcEfferentCoupling(ElementNode ancestor,ElementNode node)
	{
		Map<String,Integer> relations = new HashMap<String,Integer>();
		
		for(DomainRelation rel:node.getRelationTargets())
		{
			if(rel.getTargetNode().isAncestor(ancestor))	continue;
			{
				String key = rel.getTargetNode().getFullName();
				Integer value = 0;
				if(relations.containsKey(key))
					value = relations.get(key);
				value++;
				relations.put(key,value);
			}
		}
		
		
		for(ElementNode child:node.getChildren())
		{
			//srcSplit = child.getFullName().split("\\.");			
			for(DomainRelation rel:child.getRelationTargets())
			{
				if(rel.getTargetNode().isAncestor(ancestor))	continue;
				{
					String key = rel.getTargetNode().getFullName();
					Integer value = 0;
					if(relations.containsKey(key))
						value = relations.get(key);
					value++;
					relations.put(key,value);
				}
			}
			if(child.getChildren().size() > 0)
				relations.putAll(calcEfferentCoupling(ancestor,child));
		}
		return relations;
	}
	
	private Map<String,Integer> calcAfferentCoupling(ElementNode ancestor,ElementNode node)
	{
		Map<String,Integer> relations = new HashMap<String,Integer>();
		
		for(DomainRelation rel:node.getRelationSources())
		{
			if(rel.getSourceNode().isAncestor(ancestor))	continue;
			{
				String key = rel.getSourceNode().getFullName();
				Integer value = 0;
				if(relations.containsKey(key))
					value = relations.get(key);
				value++;
				relations.put(key,value);
			}
		}
		
		
		for(ElementNode child:node.getChildren())
		{
			//srcSplit = child.getFullName().split("\\.");			
			for(DomainRelation rel:child.getRelationSources())
			{
				if(rel.getSourceNode().isAncestor(ancestor))	continue;
				{
					String key = rel.getSourceNode().getFullName();
					Integer value = 0;
					if(relations.containsKey(key))
						value = relations.get(key);
					value++;
					relations.put(key,value);
				}
			}
			if(child.getChildren().size() > 0)
				relations.putAll(calcAfferentCoupling(ancestor,child));
		}
		return relations;
	}
}
