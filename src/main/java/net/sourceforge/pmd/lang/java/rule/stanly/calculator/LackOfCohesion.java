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
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryPrefix;
import net.sourceforge.pmd.lang.java.ast.ASTPrimarySuffix;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ClassDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.LibraryDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.PackageDomain;
import net.sourceforge.pmd.lang.java.symboltable.Scope;
import net.sourceforge.pmd.lang.java.symboltable.SourceFileScope;
import net.sourceforge.pmd.lang.java.symboltable.VariableNameDeclaration;

public class LackOfCohesion extends AbstractCalculator {
	/** Collects for each method of the current class, which local attributes are accessed. */
	private Stack<Map<String, Set<String>>> localStack;
	//private Map<String, Set<String>> methodAttributeAccess;
	/** The name of the current method. */
	/**
	 * Base entry point for the visitor - the class declaration.
	 * The metrics are initialized. Then the other nodes are visited. Afterwards
	 * the metrics are evaluated against fixed thresholds.
	 */
	public LackOfCohesion()
	{
		localStack = new Stack<Map<String, Set<String>>>();
	}
	
	@Override
	public void preCalcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceDeclaration node, Object data) {
		localStack.push(new HashMap<String, Set<String>>());
		//methodAttributeAccess = 
	}
	@Override
	public void calcMetric(Stack<ElementNode> entryStack,ASTClassOrInterfaceDeclaration node, Object data) {
		int lcom = determineMethodPairs();
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		while(entryStack.size() > 0)
		{
			if(entryStack.peek() instanceof ClassDomain)
				((ClassDomain)entryStack.peek()).metric.addLCOM(lcom);
			else if(entryStack.peek() instanceof PackageDomain)
				((PackageDomain)entryStack.peek()).metric.addLCOM(lcom);
			else if(entryStack.peek() instanceof LibraryDomain)
				((LibraryDomain)entryStack.peek()).metric.addLCOM(lcom);
			nodeList.add(0,entryStack.pop());
			
		}
		for(ElementNode n:nodeList)
			entryStack.push(n);
		
		localStack.pop();
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
		Map<String, Set<String>> methodAttributeAccess = localStack.peek();
		List<String> methods = new ArrayList<String>(methodAttributeAccess.keySet());
		int methodCount = methods.size();
		int pairs = 0;

		if (methodCount > 1) {
			for (int i = 0; i < methodCount - 1; i++) {
				String firstMethodName = methods.get(i);
				Set<String> accessesOfFirstMethod = methodAttributeAccess.get(firstMethodName);
				
				for(int j = i+1; j < methodCount; j++)
				{
					String secondMethodName = methods.get(j);
					
					Set<String> accessesOfSecondMethod = methodAttributeAccess.get(secondMethodName);
					
					Set<String> combinedAccesses = new HashSet<String>();
					combinedAccesses.addAll(accessesOfFirstMethod);
					combinedAccesses.addAll(accessesOfSecondMethod);
	
					if (combinedAccesses.size() < (accessesOfFirstMethod.size() + accessesOfSecondMethod.size())) {
						pairs++;
					}
				}
			}
		}
		return pairs;
	}

	@Override
    public void calcMetric(Stack<ElementNode> entryStack,ASTMethodDeclaration node, Object data) {
		//String currentMethodName = getCurrentMethodName(entryStack);
       
        
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
		if(localStack.size() == 0)	return;
		Map<String, Set<String>> methodAttributeAccess = localStack.peek();
		String currentMethodName = getCurrentMethodName(entryStack);
		if (isForeignAttributeOrMethod(node)) {
			//if (isAttributeAccess(node)
			//    || (isMethodCall(node) && isForeignGetterSetterCall(node))) {
			//    atfdCounter++;
			//}
		} else {
			if (currentMethodName != null) {
				if(!methodAttributeAccess.containsKey(currentMethodName))
					 methodAttributeAccess.put(currentMethodName, new HashSet<String>());
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
	private String getCurrentMethodName(Stack<ElementNode> entryStack)
	{
		String name = null;
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		while(entryStack.size() > 0 && entryStack.peek().getType() != ElementNodeType.METHOD && entryStack.peek().getType() != ElementNodeType.CONSTRUCTOR)
			nodeList.add(0,entryStack.pop());
		if(entryStack.size() != 0)
			name = entryStack.peek().getName();
		for(ElementNode n:nodeList)
			entryStack.push(n);
		return name;
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
		ASTPrimarySuffix suffix = node.getFirstDescendantOfType(ASTPrimarySuffix.class);
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
        else if(suffix != null)
        {
        	int dotIndex = suffix.getImage().indexOf(".");
        	if (dotIndex == -1) {
                variableName = suffix.getImage();
            } else {
                variableName = suffix.getImage().substring(0, dotIndex);
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
