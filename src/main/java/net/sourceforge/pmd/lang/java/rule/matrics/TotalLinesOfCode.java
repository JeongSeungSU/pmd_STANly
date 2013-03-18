package net.sourceforge.pmd.lang.java.rule.matrics;


import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.*;
import net.sourceforge.pmd.lang.java.rule.AbstractStatisticalJavaRule;
import net.sourceforge.pmd.stat.DataPoint;
import net.sourceforge.pmd.util.NumericConstants;

public class TotalLinesOfCode extends AbstractStatisticalJavaRule{

	@Override
	public Object visit(ASTCompilationUnit node, Object data) {
		return visit((JavaNode)node,data);
	}

	@Override
	public Object visit(JavaNode node, Object data) {
		int numNodes = 0;

		
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			JavaNode n = (JavaNode) node.jjtGetChild(i);
			Integer treeSize = (Integer) n.jjtAccept(this, data);
			numNodes += treeSize.intValue();
			
		}

		if (ASTCompilationUnit.class.isInstance(node)) {
			// Add 1 to account for base node
			DataPoint point = new DataPoint();
			point.setNode(node);
			point.setScore(1.0 * numNodes);
			point.setMessage(getMessage());
			addDataPoint(point);
		}

		////System.out.print(node.getClass().getName() +" : "+ numNodes + "\n" );
		return Integer.valueOf(numNodes);
	}

	/**
	 * Count the number of children of the given Java node. Adds one to count the
	 * node itself.
	 * 
	 * @param node
	 *          java node having children counted
	 * @param data
	 *          node data
	 * @return count of the number of children of the node, plus one
	 */
	protected Integer countNodeChildren(Node node, Object data) {
		Integer nodeCount = null;
		int lineCount = 0;
		for (int i = 0; i < node.jjtGetNumChildren(); i++) {
			nodeCount = (Integer) ((JavaNode) node.jjtGetChild(i)).jjtAccept(this, data);
			lineCount += nodeCount.intValue();
		}
		return ++lineCount;
	}

	@Override
	public Object visit(ASTForStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTDoStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTIfStatement node, Object data) {

		Integer lineCount = countNodeChildren(node, data);

		if (node.hasElse()) {
			lineCount++;
		}

		return lineCount;
	}

	@Override
	public Object visit(ASTWhileStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTBreakStatement node, Object data) {
		return NumericConstants.ONE;
	}

	@Override
	public Object visit(ASTCatchStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTContinueStatement node, Object data) {
		return NumericConstants.ONE;
	}

	@Override
	public Object visit(ASTFinallyStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTReturnStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTSwitchStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTSynchronizedStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTThrowStatement node, Object data) {
		return NumericConstants.ONE;
	}

	@Override
	public Object visit(ASTStatementExpression node, Object data) {

		// "For" update expressions do not count as separate lines of code
		if (node.jjtGetParent() instanceof ASTStatementExpressionList) {
			return NumericConstants.ZERO;
		}

		return NumericConstants.ONE;
	}

	@Override
	public Object visit(ASTLabeledStatement node, Object data) {
		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTLocalVariableDeclaration node, Object data) {

		// "For" init declarations do not count as separate lines of code
		if (node.jjtGetParent() instanceof ASTForInit) {
			return NumericConstants.ZERO;
		}

		/*
		 * This will count variables declared on the same line as separate NCSS
		 * counts. This violates JavaNCSS standards, but I'm not convinced that's a
		 * bad thing here.
		 */

		return countNodeChildren(node, data);
	}

	@Override
	public Object visit(ASTSwitchLabel node, Object data) {
		return countNodeChildren(node, data);
	}
	
	@Override
	  public Object visit(ASTImportDeclaration node, Object data) {
        return NumericConstants.ONE;
    }

	@Override
	  public Object visit(ASTPackageDeclaration node, Object data) {
		return NumericConstants.ONE;
	}

	
	@Override
	public Object[] getViolationParameters(DataPoint point) {
		return new String[] {
				((ASTCompilationUnit) point.getNode()).getImage(),
		String.valueOf((int) point.getScore()) };
    }
	@Override
    public Object visit(ASTClassOrInterfaceDeclaration node, Object data) {
	return (Integer)visit((JavaNode) node, data)+1;
    }

	@Override
    public Object visit(ASTClassOrInterfaceBodyDeclaration node, Object data) {
	return (Integer)visit((JavaNode) node, data);
    }

	@Override
    public Object visit(ASTEnumDeclaration node, Object data) {
	return NumericConstants.ONE;
    }

	@Override
    public Object visit(ASTMemberValue node, Object data) {
	return NumericConstants.ONE;
    }
	
	@Override
    public Object visit(ASTFieldDeclaration node, Object data) {
	return NumericConstants.ONE;
    }
    
	@Override
    public Object visit(ASTAssertStatement node, Object data) {
	return NumericConstants.ONE;
    }
	
	@Override
    public Object visit(ASTMethodDeclaration node, Object data) {
	return (Integer)visit((JavaNode) node, data)+1;
    }
    

	@Override
    public Object visit(ASTConstructorDeclaration node, Object data) {
	return (Integer)visit((JavaNode)node, data)+1;
    }

	@Override
    public Object visit(ASTExplicitConstructorInvocation node, Object data) {
	return countNodeChildren(node, data);
    }
	
	
}
