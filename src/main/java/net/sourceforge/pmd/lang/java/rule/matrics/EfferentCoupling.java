package net.sourceforge.pmd.lang.java.rule.matrics;

import net.sourceforge.pmd.lang.ecmascript.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;


public class EfferentCoupling extends AbstractJavaRule {
	
	@Override
    public Object visit(ASTMethodDeclaration node, Object data) {
    	ASTBlock ast_block = node.getFirstChildOfType(ASTBlock.class);
    	System.out.println(ast_block.getImage());
    	return super.visit(node, data);
    }
	
	@Override
    public Object visit(ASTFieldDeclaration node, Object data) {
    	ASTBlock ast_block = node.getFirstChildOfType(ASTBlock.class);
    	System.out.println(node.isStatic());
    	return super.visit(node, data);
    }
	
	@Override
    public Object visit(ASTBlockStatement node, Object data) {
    	//ASTBlock ast_block = node.getFirstChildOfType(ASTBlock.class);
    	//System.out.println(ast_block.getImage());
    	return super.visit(node, data);
    }
	
	@Override
    public Object visit(ASTCompilationUnit node, Object data) {
    

		//ASTBlock ast_block = node.getFirstChildOfType(ASTBlock.class);
    	//System.out.println(ast_block.getImage());
    	return super.visit(node, data);
    }
}
