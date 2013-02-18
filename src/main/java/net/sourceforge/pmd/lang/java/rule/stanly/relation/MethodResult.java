package net.sourceforge.pmd.lang.java.rule.stanly.relation;


/**
 * 한 역할의 결과물...
 * @since 2013. 2. 14.오전 1:40:40
 * @author JeongSeungsu
 */
public class MethodResult {
	/**
	 * 처리된 결과...
	 */
	public String TargetResult;
	
	/**
	 * 다음에 처리할 노드...
	 */
	public String TypeName;
	
	/**
	 * 여기서 완벽하게 처리 되었냐 아니냐...
	 */
	public boolean IsProcess;
}
