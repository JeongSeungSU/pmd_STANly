package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;

public abstract class RelationChainNode {
	private RelationChainNode NextNode = null;
	private RelationChainNode PrevNode = null;
	private String ChainName = null;
	private String AnalysisClassName = null; 
	
	public RelationChainNode() {
		// TODO Auto-generated constructor stub
	}
	public RelationChainNode(RelationChainNode nextnode,RelationChainNode prevnode, String chainname, String analysisclassname)
	{
		NextNode 			= nextnode;
		PrevNode 			= prevnode;
		ChainName 			= chainname;
		AnalysisClassName 	= analysisclassname;
	}
	
	/**
	 * 재귀형식에서 제대로 path를 못뽑아 줄수 있음...
	 * @since 2013. 2. 14.오전 1:51:57
	 * @author JeongSeungsu
	 * @return
	 */
	public String GetFullPath()
	{
		RelationChainNode node = this;
		while(node.PrevNode != null)
		{
			node = node.PrevNode;
		}
		String FullPath = "";
		
		while(node.NextNode != null)
		{
			FullPath += node.toString() + "\n"; 
			node = node.NextNode;
		}
		return FullPath;
	}
	
	public String Process(AbstractJavaNode analysisNode) throws ChainException
	{
		String ReturnValue = "";
		ChainNodeResult result = null;
		
		if(AnalysisClassName.matches(analysisNode.toString()))
		{
			result = Solve(analysisNode);
			if(result.IsProcess)
				return result.TargetResult;
			
			ReturnValue = result.TargetResult;
			
			if(NextNode == null)
			{
				throw new ChainException(GetFullPath(),result);
			}
			else
			{
				ReturnValue += NextNode.Process(result.NextProecessNode);
			}
		}
		else
		{
			if(NextNode == null)
			{
				throw new ChainException(GetFullPath(),result);
			}
			ReturnValue += NextNode.Process(analysisNode);
		}
			
		

		return ReturnValue;
	}
	
	abstract ChainNodeResult Solve(AbstractJavaNode analysisNode);
	
	abstract AbstractJavaNode NextAnalysisNode();
	
	public void SetNextNode(RelationChainNode node)
	{
		NextNode = node;
	}
	public void SetChainNodeName(String name)
	{
		ChainName = name;
	}
	public void SetAnalysisClassName(String classname)
	{
		AnalysisClassName = classname;
	}
	
	@Override
	public String toString()
	{
		if(ChainName != null)
			return ChainName;
		else
			return super.toString();
	}
	
}
