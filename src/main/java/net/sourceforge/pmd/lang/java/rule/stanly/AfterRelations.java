package net.sourceforge.pmd.lang.java.rule.stanly;

import java.util.ArrayList;
import java.util.List;

import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.AbstractAfterCalculator;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.Fat;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class AfterRelations {
	private static ProjectDomain projectNode = null;
	private RelationManager manager = null;
	private List<AbstractAfterCalculator> calculators = null;

	public AfterRelations(ProjectDomain projectNode, RelationManager manager) {
		// TODO Auto-generated constructor stub
		this.projectNode = projectNode;
		this.manager = manager;

		if(calculators == null)
		{
			calculators = new ArrayList<AbstractAfterCalculator>();
			calculators.add(new Fat());
		}
	}
	public void analysis(){
		// TODO Auto-generated method stub
		FindTarget();
		RemoveNullTargetRelations();
		for(AbstractAfterCalculator calculator:calculators)
			calculator.calcMatric(projectNode);
	}

	public void FindTarget(){
		List<DomainRelation> domainRelation = manager.getDomainRelationList();
		String targetString;
		ElementNode sourceNode;
		ElementNode targetNode;

		for(DomainRelation relation:domainRelation)
		{
			//sourceString = domainRelation.get(i).getSource();

			targetString = relation.getTarget();
			sourceNode = relation.getSourceNode();
			targetNode = sourceNode.getParent().findNode(targetString);

			if(targetNode == null)//찾을수 없느 관계는 추후 삭제함 YHC
				;//domainRelation.remove(relation);
			else
			{
				relation.setTargetNode(targetNode);
				//System.out.println(targetNode.getFullName() + " == " + targetString);
				System.out.println(sourceNode.getFullName() + " --" + relation.getRelation().name() + "--> " + targetNode.getFullName());
			}
		}		
	}
	public void RemoveNullTargetRelations(){//찾을수 없느 관계는 여기서 삭제함 YHC
		List<DomainRelation> domainRelation = manager.getDomainRelationList();
		ElementNode sourceNode;
		ElementNode targetNode;
		for(int i=0;i<domainRelation.size();i++)
		{
			if(domainRelation.get(i).getTargetNode() == null)	domainRelation.remove(i--);
			else
			{
				DomainRelation relation = domainRelation.get(i);
				sourceNode = relation.getSourceNode();
				targetNode = relation.getTargetNode();
				sourceNode.AddRelationTarget(relation);
				targetNode.AddRelationSource(relation);
			}
		}
	}
}
