package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.relation;


/**
 * 한 역할의 결과물...
 * @since 2013. 2. 14.오전 1:40:40
 * @author JeongSeungsu
 */
public class RelationResult {
	
	public RelationResult()
	{}
	public RelationResult(String targetresult, String typename , boolean isprocess) 
	{
		TargetResult 	= targetresult;
		TypeName 		= typename;
		IsProcess 		= isprocess;
	}
	/**
	 * 처리된 결과...
	 */
	public String TargetResult;
	
	/**
	 * 타입 이름
	 */
	public String TypeName;
	
	/**
	 * 여기서 완벽하게 처리 되었냐 아니냐...
	 */
	public boolean IsProcess;
}
