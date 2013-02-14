package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;

/**
 * 
 * @since 2013. 2. 14.오전 1:59:14
 * @author JeongSeungsu
 */
public class ChainManager {
	private RelationChainNode RootNode;
	
	void CreateChain()
	{
		//여기서 막 지멋대로 만든다...
	}
	
	String GetTargetString(AbstractJavaNode node)
	{
		String LastTargetString = "";
		try
		{
			LastTargetString = RootNode.Process(node);
		}
		catch(ChainException e)
		{
			e.PrintCauseException();
		}
		
		return LastTargetString;
	}
}
