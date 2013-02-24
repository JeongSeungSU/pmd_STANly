package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTEnumDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTFormalParameter;
import net.sourceforge.pmd.lang.java.ast.ASTLocalVariableDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
import net.sourceforge.pmd.lang.java.ast.ASTPrimitiveType;
import net.sourceforge.pmd.lang.java.ast.ASTType;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclarator;
import net.sourceforge.pmd.lang.java.ast.AbstractJavaNode;
import net.sourceforge.pmd.lang.java.ast.JavaNode;
import net.sourceforge.pmd.lang.java.rule.stanly.DomainRelationList;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.symboltable.NameDeclaration;

/**
 * TargetResult = 걍 타입의 이름
 * TypeName		= ClassName+ArgumentList ==> abc<a<b>> 이런식
 * IsProcess	= 타입을 찾을시 true 못찾을시 false
 * @since 2013. 2. 19.오전 4:03:19
 * @author JeongSeungsu
 */
public class NameAnalysisNode extends AbstractASTAnalysisNode {

	public NameAnalysisNode(DomainRelationList relationlist,
			Map<ASTPrimaryExpression, MethodResult> PrimaryExpressionList,
			MethodAnlaysis anlysis) {
		super(relationlist, PrimaryExpressionList, anlysis);
		// TODO Auto-generated constructor stub
	}

	@Override
	public MethodResult AnalysisAST(AbstractJavaNode analysisnode,ElementNode sourcenode) throws MethodAnalysisException {
		//primitive type도 나오도록!! 해야됨
		ASTName name = (ASTName)analysisnode;
		NameDeclaration tmp = (NameDeclaration)name.getNameDeclaration();
		
		if(MacroFunctions.NULLTrue(tmp)){
			return new MethodResult(name.getImage(),MethodAnlysistor.GetUnknownTypeName(),false);
		}
		
		JavaNode typenode1 = (JavaNode)tmp.getNode().getNthParent(1);
		JavaNode typenode2 = null; 
		
		if (typenode1.getClass() == ASTVariableDeclarator.class)
		{
			typenode2 = (JavaNode) typenode1.getFirstParentOfType(ASTLocalVariableDeclaration.class);
			if(MacroFunctions.NULLTrue(typenode2))
				typenode2 = (JavaNode) typenode1.getFirstParentOfType(ASTFieldDeclaration.class);
		}
		else if(typenode1.getClass() == ASTMethodDeclaration.class)
			typenode2 = (JavaNode)typenode1.getFirstParentOfType(ASTClassOrInterfaceDeclaration.class);
		else if(typenode1.getClass() == ASTFormalParameter.class)
			typenode2 = (JavaNode)typenode1.getFirstChildOfType(ASTType.class);
		else
			throw new MethodAnalysisException("ASTName에서" + typenode1.getClass().toString() + "타입을 못찾음...");

		AbstractJavaNode CallType = null;
		
		if(MacroFunctions.NULLTrue(typenode2))
			CallType = (AbstractJavaNode)typenode1.getFirstParentOfType(ASTEnumDeclaration.class);
		else
			CallType = typenode2.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		
		if( MacroFunctions.NULLTrue(CallType)){
			CallType = typenode2.getFirstDescendantOfType(ASTPrimitiveType.class);
			if(MacroFunctions.NULLTrue(CallType))
				return new MethodResult(name.getImage(),MethodAnlysistor.GetUnknownTypeName(),false);
		}

		MethodResult result = new MethodResult();
		result.TypeName 	= MethodAnlysistor.ProcessMethodCallAndAccess(CallType, sourcenode).TypeName;
		result.TargetResult = name.getImage(); 
		result.IsProcess	= true;
		
		return result;
		
	}

}
