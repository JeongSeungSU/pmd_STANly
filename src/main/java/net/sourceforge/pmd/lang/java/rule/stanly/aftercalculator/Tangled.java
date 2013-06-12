package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageSetDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class Tangled implements AbstractAfterCalculator {
	public void calcMetric(ProjectDomain node)
	{
		visitChildren((ElementNode)node);
	}
	
	private void calculateTangle(PackageSetDomain node)
	{		
		Map<String,Integer> relations = getChilrenRelation(node,node);
		Set<String> set = relations.keySet();
		ArrayList<String> list = new ArrayList<String>();
		list.addAll(set);
		Collections.sort(list);
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
		
		node.metric.setTangled(totalCount == 0 ? 0 : (float)tangleCount / (float)totalCount);
		System.out.println(node.getFullName() + " " + node.metric.getTangled());
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
			//srcSplit = child.getFullName().split("\\.");			
			for(DomainRelation rel:child.getRelationTargets())
			{
				try{
					
				
				if(!rel.getTargetNode().isAncestor(ancestor))	continue;
				if(rel.getSourceNode().getPackageName().equals(rel.getTargetNode().getPackageName()))	continue;
				
				String tarStr,srcStr;
				int len = ancestor.getFullName().split("\\.").length;
				if(rel.getSourceNode().getPackageName().equals(ancestor.getFullName()))
				{
					srcSplit = ancestor.getFullName().split("\\.");
					srcStr = srcSplit[srcSplit.length-1];
				}
				else
				{
					srcSplit = rel.getSourceNode().getPackageName().split("\\.");
					srcStr = srcSplit[len];
				}
				if(rel.getTargetNode().getPackageName().equals(ancestor.getFullName()))
				{
					tarSplit = ancestor.getFullName().split("\\.");
					tarStr = tarSplit[tarSplit.length-1];
				}
				else
				{
					tarSplit = rel.getTargetNode().getPackageName().split("\\.");
					tarStr = tarSplit[len];
				}
				String key = srcStr + ">" + tarStr;
				Integer value = 0;
				if(relations.containsKey(key))
					value = relations.get(key);
				value++;
				relations.put(key,value);
				
				/*tarSplit = rel.getTargetNode().getFullName().split("\\.");
				if(ancSplit.length < srcSplit.length && ancSplit.length < tarSplit.length && 
					!srcSplit[ancSplit.length].equals(tarSplit[ancSplit.length]))
				{
					String key = srcSplit[ancSplit.length] + ">" + tarSplit[ancSplit.length];
					Integer value = 0;
					if(relations.containsKey(key))
						value = relations.get(key);
					value++;
					relations.put(key,value);
				}*/
				}
				catch(Exception e)
				{
					continue;
				}
			}
			if(child.getChildren().size() > 0)
			{
				Map<String,Integer> childrel = getChilrenRelation(ancestor,child);
				Set<String> childset = childrel.keySet();
				for(String key:childset)
				{
					if(relations.containsKey(key))
						relations.put(key,relations.get(key)+childrel.get(key));
					else
						relations.put(key,childrel.get(key));
				}
			}
		}
		return relations;
	}
}

	