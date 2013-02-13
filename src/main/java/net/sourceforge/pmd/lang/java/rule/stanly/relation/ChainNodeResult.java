package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;

/**
 * 한 역할의 결과물...
 * @since 2013. 2. 14.오전 1:40:40
 * @author JeongSeungsu
 */
public class ChainNodeResult {
	/**
	 * 처리된 결과...
	 */
	public String TargetResult;
	/**
	 * 여기서 완벽하게 처리 되었냐 아니냐...
	 */
	public boolean IsProcess;
	
	/**
	 * 다음에 처리할 노드...
	 */
	public AbstractJavaNode NextProecessNode;
}
