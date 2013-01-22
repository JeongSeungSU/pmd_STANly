package net.sourceforge.pmd.lang.java.rule.matrics;

import net.sourceforge.pmd.lang.java.ast.ASTPackageDeclaration;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.design.ExcessiveNodeCountRule;
import net.sourceforge.pmd.stat.DataPoint;


public class NumberOfPackages extends ExcessiveNodeCountRule {

	public NumberOfPackages() {
		// TODO Auto-generated constructor stub
        super(ASTPackageDeclaration.class);
        setProperty(MINIMUM_DESCRIPTOR, 30d);
	}
	
    public Object visit(ASTPackageDeclaration node, Object data) {
    	return visit((JavaNode)node,data);
    }

	@Override
	public Object[] getViolationParameters(DataPoint point) {
		return new String[] {
				((ASTPackageDeclaration) point.getNode()).getPackageNameImage(),
				String.valueOf((int) point.getScore()) };
	}
	
}
