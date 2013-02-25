package net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelation;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class ComponentDepandency extends AbstractAfterCalculator {
	public void calcMetric(ProjectDomain node)
	{
		visitChildren(node);
	}

	public void calcMetric(PackageDomain node)
	{
		Map<String,Integer> relations = getChilrenRelation(node,node);

		int N = node.getChildren().size();
		for(int k=0;k<N;k++)
		{
			for(int i=0;i<N;i++)
			{
				for(int j=0;j<N;j++)
				{
					String key1 = node.getChildren().get(i).getName() + ">" + node.getChildren().get(j).getName();
					if(relations.containsKey(key1))	continue;
					String key2 = node.getChildren().get(i).getName() + ">" + node.getChildren().get(k).getName();
					String key3 = node.getChildren().get(k).getName() + ">" + node.getChildren().get(j).getName();
					if(relations.containsKey(key2) && relations.containsKey(key3))
						relations.put(key1, 1);
				}
			}
		}
		float mccd = (N * (N-1));
		float ccd = relations.size();
		float acd = ccd / mccd * 100;
		
		node.metric.setACDPerUnit(acd);
		
		List<String> strRel;
		if(relations.size() > 0)
		{
			strRel = new ArrayList<String>(relations.keySet());
			Collections.sort(strRel);
			System.out.println(strRel.size());
		}
	}

	private void visitChildren(ElementNode node)
	{		
		for(ElementNode child:node.getChildren())
		{
			visitChildren(child);
			if(child.getType() == ElementNodeType.PACKAGE)
			{
				calcMetric((PackageDomain)child);
			}
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
					Integer value = 1;
					relations.put(key,value);
				}
			}
			if(child.getChildren().size() > 0)
				relations.putAll(getChilrenRelation(ancestor,child));
		}
		return relations;
	}
}
