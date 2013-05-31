package net.sourceforge.pmd.lang.java.rule.naming;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.lang.java.rule.Violation;
import net.sourceforge.pmd.lang.java.rule.ViolationController;

public class AvoidDollarSignsRule extends AbstractJavaRule {

    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        if (node.getImage().indexOf('$') != -1) {
            //addViolation(data, node);
            ViolationController.AddViolation(Violation.NAMING, data, node, "Avoid using dollar signs in class/interface names");
            return data;
        }
        return super.visit(node, data);
    }

    public Object visit(ASTVariableDeclaratorId node, Object data) {
        if (node.getImage().indexOf('$') != -1) {
            //addViolation(data, node);
        	ViolationController.AddViolation(Violation.NAMING, data, node, "Avoid using dollar signs in variable names");
            return data;
        }
        return super.visit(node, data);
    }

    public Object visit(ASTMethodDeclarator node, Object data) {
        if (node.getImage().indexOf('$') != -1) {
            //addViolation(data, node);
        	ViolationController.AddViolation(Violation.NAMING, data, node, "Avoid using dollar signs in method names");
            return data;
        }
        return super.visit(node, data);
    }

}
