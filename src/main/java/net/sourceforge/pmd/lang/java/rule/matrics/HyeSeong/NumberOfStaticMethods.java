package net.sourceforge.pmd.lang.java.rule.matrics.HyeSeong;

import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;

public class NumberOfStaticMethods extends AbstractJavaRule{

	private Stack<StackEntry> entryStack;
	private StackEntry entry;
	
	public NumberOfStaticMethods()
	{
		entryStack = new Stack<StackEntry>();
	}
	@Override
    public Object visit(ASTCompilationUnit node, Object data) {
		

		Object obj = super.visit(node, data);
		while(!entryStack.empty())
		{
			System.out.println(entryStack.peek().getClassName()+"   : " +entryStack.peek().getCount());
			entryStack.pop();
		}
    	return obj;
    }
	@Override
    public Object visit(ASTMethodDeclaration node, Object data) {
		
		if(node.isStatic())
			entry.addCount();
		
    	return super.visit(node, data);
    }
	@Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
		entry = new StackEntry(node,node.getImage());
		Object obj = super.visit(node, data);
		entryStack.push(entry);
		return obj;
    }
}
