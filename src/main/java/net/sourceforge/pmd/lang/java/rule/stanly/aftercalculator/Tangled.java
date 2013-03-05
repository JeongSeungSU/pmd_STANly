package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageSetDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class Tangled extends AbstractAfterCalculator {
	public void calcMetric(ProjectDomain node)
	{
		visitChildren((ElementNode)node);
	}
	
	private void calculateTangle(PackageSetDomain node)
	{		
		Map<String,Integer> relations = getChilrenRelation(node,node);
		Set<String> set = relations.keySet();
		
		int totalCount = 0;
		int tangleCount = 0;
		Iterator<String> it = set.iterator();
		while (it.hasNext()) {
			String oriKey = it.next();
			String[] rel = oriKey.split("\\>");
			String tngKey = rel[1] + ">" + rel[0];
			
			totalCount += relations.get(oriKey);
			
			if(relations.containsKey(tngKey))
			{
				int val1 = relations.get(oriKey);
				int val2 = relations.get(tngKey);
				
				tangleCount += (val1<val2) ? val1 : val2;
			}
		}
		tangleCount /= 2; // 2번 카운팅 되므로 나누기 2를 해준다.
		
		node.metric.setTangled(totalCount == 0 ? 0 : tangleCount / totalCount);
	}
	
	private void visitChildren(ElementNode node)
	{
		for(ElementNode child:node.getChildren())
		{
			if(child.getType() == ElementNodeType.PACKAGESET)
			{
				calculateTangle((PackageSetDomain)child);
			}
			visitChildren(child);
		}
	}
	
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
}
