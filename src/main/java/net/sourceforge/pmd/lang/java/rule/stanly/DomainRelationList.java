package net.sourceforge.pmd.lang.java.rule.stanly;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;

public class DomainRelationList {
	private List<DomainRelation> RelationList;
	
	public DomainRelationList()
	{
		RelationList = new ArrayList<DomainRelation>();
	}
	
	public List<DomainRelation> GetList()
	{
		return RelationList;
	}
	
	public DomainRelation AddRelation(Relations relationkind,String source, String target,ElementNode sourceNode,ElementNode targetNode)
	{		
		
		//중복 제거 YHC
		for(DomainRelation relation: RelationList)
		{
			String src = relation.getSource();
			if(!src.equals(source))	break;
			ElementNode srcNode = relation.getSourceNode();
			Relations rel = relation.getRelation();
			String tar = relation.getTarget();
			if(srcNode == sourceNode && rel == relationkind && tar.equals(target))
				return relation;//중복제거
		}
		
		DomainRelation relation = new DomainRelation();
		relation.setRelation(relationkind);
		relation.setSource(source);
		relation.setTarget(target);
		
		relation.setSourceNode(sourceNode);
		relation.setTargetNode(targetNode);
		
		//System.out.println("Source : "+relation.getSource()+" -> \t "+ relation.getRelation().toString() + 
			//					"-> \t Target : " + relation.getTarget());
		
		RelationList.add(0,relation);
		
		//요기서 DomainComposition을 추가함
		return relation;
	}
}
