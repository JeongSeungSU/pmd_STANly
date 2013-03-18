package net.sourceforge.pmd.lang.java.rule.matrics.HyeSeong;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class NumberOfInterface extends AbstractJavaRule{

	
	private int count;
	
	public NumberOfInterface()
	{
		count=0;
	}
	@Override
	public Object visit(ASTCompilationUnit node, Object data) {
		
	
		Object obj = super.visit(node, data);
			//System.out.println("인터페이스 갯수: "+count);
		return obj;
	}

	@Override
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		if(node.isInterface())
			count++;
		
		return super.visit(node, data);
	}

}