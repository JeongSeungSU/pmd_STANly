package net.sourceforge.pmd.lang.java.rule.yhctest.calculator;

import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;


public class CyclomaticComplexity extends AbstractJavaRule {
	
	public Object visit(ASTCompilationUnit node, Object data)
	{
		return super.visit(node, data);
	}
}
