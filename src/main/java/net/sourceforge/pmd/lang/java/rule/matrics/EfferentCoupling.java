package net.sourceforge.pmd.lang.java.rule.matrics;

import net.sourceforge.pmd.lang.java.ast.ASTBlock;
import net.sourceforge.pmd.lang.java.ast.ASTBlockStatement;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTSwitchStatement;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;


public class EfferentCoupling extends AbstractJavaRule {
	
	/*@Override
    public Object visit(ASTMethodDeclaration node, Object data) {
    	ASTBlock ast_block = node.getFirstChildOfType(ASTBlock.class);
    	System.out.println(ast_block.getImage());
    	return super.visit(node, data);
    }
	

	@Override
    public Object visit(ASTBlockStatement node, Object data) {
    	//ASTBlock ast_block = node.getFirstChildOfType(ASTBlock.class);
    	//System.out.println(ast_block.getImage());
    	return super.visit(node, data);
    }
	
	@Override
    public Object visit(ASTSwitchStatement node, Object data) {
    	//ASTBlock ast_block = node.getFirstChildOfType(ASTBlock.class);
    	//System.out.println(ast_block.getImage());
    	return super.visit(node, data);
    }*/
}
