package net.sourceforge.pmd.lang.java.rule.stanly.calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import net.sourceforge.pmd.lang.java.ast.ASTAllocationExpression;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTLiteral;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.symboltable.Scope;
import net.sourceforge.pmd.lang.java.symboltable.SourceFileScope;
import net.sourceforge.pmd.lang.java.symboltable.VariableNameDeclaration;

public class LackOfCohesion extends AbstractCalculator {
	/** Collects for each method of the current class, which local attributes are accessed. */
	private Map<String, Set<String>> methodAttributeAccess;
	/** The name of the current method. */
	private String currentMethodName;
	/**
	 * Base entry point for the visitor - the class declaration.
	 * The metrics are initialized. Then the other nodes are visited. Afterwards
	 * the metrics are evaluated against fixed thresholds.
	 */
	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceDeclaration node, Object data) {
		methodAttributeAccess = new HashMap<String, Set<String>>();

		double loc = calculateLOC();
	}

	private int calculateLOC() {
		int loc = determineMethodPairs();
		//double totalMethodPairs = calculateTotalMethodPairs();
		//if (totalMethodPairs > 0) {
		//	loc = methodPairs / totalMethodPairs;
		//}
		return loc;
	}

	/**
	 * Uses the {@link #methodAttributeAccess} map to detect method pairs, that use at least
	 * one common attribute of the class.
	 * @return
	 */
	private int determineMethodPairs() {
		List<String> methods = new ArrayList<String>(methodAttributeAccess.keySet());
		int methodCount = methods.size();
		int pairs = 0;

		if (methodCount > 1) {
			for (int i = 0; i < methodCount - 1; i++) {
				String firstMethodName = methods.get(i);
				String secondMethodName = methods.get(i + 1);
				Set<String> accessesOfFirstMethod = methodAttributeAccess.get(firstMethodName);
				Set<String> accessesOfSecondMethod = methodAttributeAccess.get(secondMethodName);
				Set<String> combinedAccesses = new HashSet<String>();

				combinedAccesses.addAll(accessesOfFirstMethod);
				combinedAccesses.addAll(accessesOfSecondMethod);

				if (combinedAccesses.size() < (accessesOfFirstMethod.size() + accessesOfSecondMethod.size())) {
					pairs++;
				}
			}
		}
		return pairs;
	}

	@Override
    public void calcMetric(Stack<ElementNode> entryStack,ASTMethodDeclaration node, Object data) {
        currentMethodName = node.getFirstChildOfType(ASTMethodDeclarator.class).getImage();
        methodAttributeAccess.put(currentMethodName, new HashSet<String>());
        
        //Object result = super.visit(node, data);
        
        //currentMethodName = null;
        
        return;
    }
	/**
	 * The primary expression node is used to detect access to attributes and method calls.
	 * If the access is not for a foreign class, then the {@link #methodAttributeAccess} map is
	 * updated for the current method.
	 */
	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTPrimaryExpression node, Object data) {
		if (isForeignAttributeOrMethod(node)) {
			//if (isAttributeAccess(node)
			//    || (isMethodCall(node) && isForeignGetterSetterCall(node))) {
			//    atfdCounter++;
			//}
		} else {
			if (currentMethodName != null) {
				Set<String> methodAccess = methodAttributeAccess.get(currentMethodName);
				String variableName = getVariableName(node);
				VariableNameDeclaration variableDeclaration = findVariableDeclaration(variableName, node.getScope().getEnclosingClassScope());
				if (variableDeclaration != null) {
					methodAccess.add(variableName);
				}
			}
		}

		return;
	}

	private boolean isForeignAttributeOrMethod(ASTPrimaryExpression node) {
		boolean result = false;
		String nameImage = getNameImage(node);

		if (nameImage != null && (!nameImage.contains(".") || nameImage.startsWith("this."))) {
			result = false;
		} else if (nameImage == null && node.getFirstDescendantOfType(ASTPrimaryPrefix.class).usesThisModifier()) {
			result = false;
		} else if (nameImage == null && node.hasDecendantOfAnyType(ASTLiteral.class, ASTAllocationExpression.class)) {
			result = false;
		} else {
			result = true;
		}

		return result;
	}
	
	private String getNameImage(ASTPrimaryExpression node) {
        ASTPrimaryPrefix prefix = node.getFirstDescendantOfType(ASTPrimaryPrefix.class);
        ASTName name = prefix.getFirstDescendantOfType(ASTName.class);

        String image = null;
        if (name != null) {
            image = name.getImage();
        }
        return image;
    }
	
	private String getVariableName(ASTPrimaryExpression node) {
        ASTPrimaryPrefix prefix = node.getFirstDescendantOfType(ASTPrimaryPrefix.class);
        ASTName name = prefix.getFirstDescendantOfType(ASTName.class);

        String variableName = null;
        
        if (name != null) {
            int dotIndex = name.getImage().indexOf(".");
            if (dotIndex == -1) {
                variableName = name.getImage();
            } else {
                variableName = name.getImage().substring(0, dotIndex);
            }
        }
        
        return variableName;
    }
	private VariableNameDeclaration findVariableDeclaration(String variableName, Scope scope) {
        VariableNameDeclaration result = null;
        
        for (VariableNameDeclaration declaration : scope.getVariableDeclarations().keySet()) {
            if (declaration.getImage().equals(variableName)) {
                result = declaration;
                break;
            }
        }
        
        if (result == null && scope.getParent() != null && !(scope.getParent() instanceof SourceFileScope)) {
            result = findVariableDeclaration(variableName, scope.getParent());
        }
        
        return result;
    }
}
