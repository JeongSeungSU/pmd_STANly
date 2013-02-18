package net.sourceforge.pmd.lang.java.rule.stanly.relation;

import java.util.Map;

import net.sourceforge.pmd.lang.java.ast.ASTClassOrInterfaceType;
import net.sourceforge.pmd.lang.java.ast.ASTName;
import net.sourceforge.pmd.lang.java.ast.ASTPrimaryExpression;
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
public class NameAnalysisNode extends AbstractASTParserNode {

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
			return new MethodResult(name.getImage(),"unknown",false);
		}
		
		JavaNode typenode = (JavaNode)tmp.getNode().getNthParent(1);
		if (typenode.getClass() == ASTVariableDeclarator.class) {
			typenode = (JavaNode) typenode.getNthParent(1);
		}

		//primitive type을 넣어야 한다... 음.. 이게 여기만 들어가는건지 여기저기 들어갈지는 모름...
		AbstractJavaNode CallType = typenode.getFirstDescendantOfType(ASTClassOrInterfaceType.class);
		
		if( MacroFunctions.NULLTrue(CallType)){
			return new MethodResult(name.getImage(),"unknown",false);
		}

		MethodResult result = new MethodResult();
		result.TypeName 	= MethodAnlysistor.ProcessMethodCallAndAccess(CallType, sourcenode).TypeName;
		result.TargetResult = name.getImage(); 
		result.IsProcess	= true;
		
		return result;
		
	}

}
