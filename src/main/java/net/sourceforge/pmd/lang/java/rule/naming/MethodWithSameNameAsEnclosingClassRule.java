package net.sourceforge.pmd.lang.java.rule.naming;

import java.util.List;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import net.sourceforge.pmd.lang.java.rule.Violation;
import net.sourceforge.pmd.lang.java.rule.ViolationController;

public class MethodWithSameNameAsEnclosingClassRule extends AbstractJavaRule {

    @Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
        List<ASTMethodDeclarator> methods = node.findDescendantsOfType(ASTMethodDeclarator.class);
        for (ASTMethodDeclarator m: methods) {
            if (m.hasImageEqualTo(node.getImage())) {
                //addViolation(data, m);
                ViolationController.AddViolation(Violation.NAMING, data, m, "Method with same name as enclosing Class");
            }
        }
        return super.visit(node, data);
    }
}
