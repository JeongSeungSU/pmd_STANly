package net.sourceforge.pmd.lang.java.rule.matrics;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class DepthOfInheritanceTree extends AbstractJavaRule {
	public Object visit(ASTCompilationUnit node, Object data)
	{	
		/*ASTPackageDeclaration a;
		a = node.getPackageDeclaration();
		//System.out.println(a.getPackageNameImage());*/
		return super.visit(node,data);
	}
	public Object visit(ASTClassOrInterfaceDeclaration node, Object data)
	{	
		return super.visit(node,data);
	}
	public Object visit(ASTMethodDeclaration node, Object data)
	{	
		return super.visit(node,data);	
	}
}
