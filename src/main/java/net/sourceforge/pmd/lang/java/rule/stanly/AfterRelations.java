package net.sourceforge.pmd.lang.java.rule.stanly;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



import net.sourceforge.pmd.lang.java.rule.stanly.Parsingdatastructure.MethodParsingData;
import net.sourceforge.pmd.lang.java.rule.stanly.Parsingdatastructure.MethodTokenizeData;
import net.sourceforge.pmd.lang.java.rule.stanly.Util.MacroFunctions;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.AbstractAfterCalculator;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.Coupling;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.DepthOfInheritanceTree;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.Fat;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.NumberOfChildren;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.PackagetSetAverage;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.ResponseForClass;
import net.sourceforge.pmd.lang.java.rule.stanly.aftercalculator.Tangled;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNode;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ElementNodeType;
import net.sourceforge.pmd.lang.java.rule.stanly.element.MethodDomain;
import net.sourceforge.pmd.lang.java.rule.stanly.element.ProjectDomain;

public class AfterRelations {
	private static ProjectDomain projectNode = null;
	private RelationManager manager = null;
	private List<AbstractAfterCalculator> calculators = null;
	
	private DomainRelationList CallOrAccessList = new DomainRelationList();

	public AfterRelations(ProjectDomain projectNode, RelationManager manager) {
		// TODO Auto-generated constructor stub
		AfterRelations.projectNode = projectNode;
		this.manager = manager;

		if(calculators == null)
		{
			calculators = new ArrayList<AbstractAfterCalculator>();
			calculators.add(new Fat());
			calculators.add(new DepthOfInheritanceTree());
			calculators.add(new NumberOfChildren());
			calculators.add(new PackagetSetAverage());
			calculators.add(new ResponseForClass());
			calculators.add(new Tangled());
			calculators.add(new Coupling());
			//calculators.add(new ComponentDepandency());
		}
	}
	
	public void analysisAnother(){
		// TODO Auto-generated method stub
		System.out.println("Relation 계산 시작");
		FindTarget();
	}
	public void analysisunknown()
	{
		UnknownRelationAnalysis();

		manager.getDomainRelationList().addAll(CallOrAccessList.GetList());
		
		RemoveNullTargetRelations();
		System.out.println("Relation 계산 끝");
		
		
		
		
		//데이터 검증
		System.out.println("데이터 검증 시작");
		DataVerification(manager.getDomainRelationList(),projectNode);
		System.out.println("데이터 검증 끝");
		
		
		System.out.println("metric 계산 시작");
		for(AbstractAfterCalculator calculator:calculators)
			calculator.calcMetric(projectNode);
	}
	private void DataVerification(List<DomainRelation> relationlist, ElementNode node)
	{
		List<DomainRelation> SourceList = new ArrayList<DomainRelation>();
		List<DomainRelation> TargetList = new ArrayList<DomainRelation>();
		for(int i = 0 ; i < relationlist.size() ; i++)
		{
			DomainRelation relation = relationlist.get(i);
			if(relation.getSourceNode().getFullName().equals(node.getFullName()))
				SourceList.add(relation);
			if(relation.getTargetNode().getFullName().equals(node.getFullName()))
				TargetList.add(relation);
		}
		if(node.getRelationSources().size() != TargetList.size())
			System.out.println(node.getFullName() + " 가 Target인 List 수 이상 ");
		if(node.getRelationTargets().size() != SourceList.size())
			System.out.println(node.getFullName() + " 가 Source인 List 수 이상 ");
		
		for(ElementNode childnode : node.getChildren())
			DataVerification(relationlist,childnode);
	}

	public void FindTarget(){
		List<DomainRelation> domainRelation = manager.getDomainRelationList();
		String targetString;
		ElementNode sourceNode;
		ElementNode targetNode = null;

		for(DomainRelation relation:domainRelation)
		{
			//sourceString = domainRelation.get(i).getSource();
			//if(relation.getSource().equals("org.simpleframework.xml.convert.AnnotationStrategy.write"))
			//	System.out.println("");
			
			targetString = relation.getTarget();
			sourceNode = relation.getSourceNode();
			
			
			// Unknown:Calls,Access처리중 JSS
			if(relation.getRelation() != Relations.UNKNOWN)
			{
				targetNode = sourceNode.getParent().findNode(targetString);


				// 찾을수 없느 관계는 추후 삭제함 YHC
				if (targetNode != null) 
				{
					relation.setTargetNode(targetNode);
					sourceNode.AddRelationTarget(relation);
					targetNode.AddRelationSource(relation);
				}
			}
		}		
	}
	public void RemoveNullTargetRelations(){//찾을수 없느 관계는 여기서 삭제함 YHC
		List<DomainRelation> domainRelation = manager.getDomainRelationList();
		for(int i=0;i<domainRelation.size();i++)
		{
			if(domainRelation.get(i).getTargetNode() == null || domainRelation.get(i).getRelation() == Relations.UNKNOWN)	
				domainRelation.remove(i--);
			/*else
			{
				DomainRelation relation = domainRelation.get(i);
				sourceNode = relation.getSourceNode();
				targetNode = relation.getTargetNode();
				sourceNode.AddRelationTarget(relation);
				targetNode.AddRelationSource(relation);
			}*/
		}
		System.out.println("Number of Relations : " + domainRelation.size());
	}
	public void makePackageSet() {
		// TODO Auto-generated method stub
		for(ElementNode libd:projectNode.getChildren())
			makePackageSet(libd);
		//makePackageSet(projectNode);
	}
	public void makePackageSet(ElementNode node)
	{

		if(node.getChildren().size() == 0)	return;
		boolean newFlag = false;
		ElementNode newNode = null;
		List<String> packageSetNames = new ArrayList<String>();
		String[] minStr = node.getChildren().get(0).getFullName().split("\\."); 
		String[] newStr;
		for(ElementNode child:node.getChildren())
		{
			String[] cmpStr = child.getName().split("\\.");
			newStr = getMinimumMatch(minStr,cmpStr);
			if(newStr.length > 0)
			{
				newFlag = true;
				minStr = newStr;
			}
			else 
			{
				if(newFlag)
					packageSetNames.add(mergeStrArray(minStr));
				newFlag = false;
				minStr = cmpStr;
			}
		}
		if(newFlag)
			packageSetNames.add(mergeStrArray(minStr));
		for(String packageSetName:packageSetNames)
		{
			newNode = node.addChildren(ElementNodeType.PACKAGESET, packageSetName);
			for(int i=0;i<node.getChildren().size();i++)
			{
				ElementNode child = node.getChildren().get(i); 
				if(newNode == child)	continue;
				if(	child.getName().startsWith(packageSetName) )
				{
					if(child.getName().equals(packageSetName))
						child.setName(".");
					else
						child.setName(child.getName().substring(packageSetName.length()+1));
					newNode.addChildren(child);
					child.setParent(newNode);
					node.getChildren().remove(i--);
				}
			}
			makePackageSet(newNode);
		}
	}
	
	/**
	 *
	 * @since 2013. 2. 23.오전 1:23:06
	 * @author JeongSeungsu
	 * @param relation
	 */
	private void UnknownRelationAnalysis()
 {
		List<DomainRelation> domainRelation = manager.getDomainRelationList();
		String targetString;

		for (Iterator<DomainRelation> it = domainRelation.iterator(); it.hasNext();) 
		{
			DomainRelation relation = it.next();
			if (relation.getRelation() != Relations.UNKNOWN)
				continue;

			targetString = relation.getTarget();

			MethodParsingData data = new MethodParsingData();
			data.MakeTokenizedData(targetString);

			//요기서 찾는다.
			FindCallOrAccessTargetNode(relation,data);
		}
	}
 
 	//제작중
 	private String FindCallOrAccessTargetNode(DomainRelation relation,MethodParsingData data)
 	{
 		String ReturnData = "unknown";
 		for(int index = 0 ; index < data.Size(); index++)
 		{
 			MethodTokenizeData prevtokendata = data.GetTokenizeData(index-1);
 			MethodTokenizeData tokendata = data.GetTokenizeData(index);
 			
 			//arguemnt가 있다면 메서드이다.
 			if(tokendata.Argument.size() != 0)
 			{
 				//여기서 Method와 이름이 같은 모든 노드를 찾는다.
 				List<ElementNode> nodeList = SearchNodeNameMethod(tokendata.Content);
 				
 				String UpperClass = "";
 				String MethodClassType = "";
 				if(MacroFunctions.NULLTrue(prevtokendata))
 				{
 					UpperClass = tokendata.Type;
 					MethodClassType = tokendata.Type;
 				}
 				else
 				{
 					UpperClass = prevtokendata.Content;
 					MethodClassType = prevtokendata.Type;
 				}
 					
				
				if(IsPrimitiveType(UpperClass))
					return "unknown";
				if(IsUnknownType(UpperClass))
					return "unknown";
				
				List<ElementNode> ClassList = SearchNodeNameClassOrInterface(UpperClass);
				if (ClassList.size() <= 0)
					return "unknown";
				else if (ClassList.size() > 1) 
				{
					MethodParsingData parsedata = null;
					String UpperName = "";
					if(MethodClassType.equals(""))
						UpperName = data.GetContentTokenData(0, index);
					else
					{
						parsedata = new MethodParsingData();
						parsedata.MakeTokenizedData(MethodClassType);
						UpperName = parsedata.GetContentTokenData(0, parsedata.Size());
					}
					
					for(Iterator<ElementNode> it = ClassList.iterator(); it.hasNext(); )
					{
						ElementNode Classnode = it.next();
						String fullname = Classnode.getFullName();
						if(!MethodClassType.equals("unknown"))
						{
							if(!fullname.equals(UpperName))
								it.remove();
						}
					}
				}
				if(ClassList.size() == 0 )
					return "unknown";	//여기 수정해야됨
				ElementNode classelementnode;
				classelementnode = ClassList.get(0);
				// 그 메서드의 부모 클래스를 가져왔다!!

				// 부모 클래스
				for (Iterator<ElementNode> it = nodeList.iterator(); it.hasNext();) 
				{
					ElementNode methodnode = it.next();
					if (methodnode.getParent() != classelementnode)
						it.remove();
				}

				//자기 클래스가 아닐 때 
				if (nodeList.size() == 0) 
				{
					// 상속노드리스트를 가져온다.
					List<ElementNode> HierarchyNodeList = GetHierarchyNode(classelementnode);

					boolean IsEnd = false;
					for (ElementNode node : HierarchyNodeList) 
					{
						List<ElementNode> childList = node.getChildren();
						for (ElementNode childnode : childList) 
						{
							if (childnode.getType() == ElementNodeType.METHOD && childnode.getName().equals(tokendata.Content)) 
							{
								nodeList.add(childnode);
								IsEnd = true;
							}
						}
						if(IsEnd)
							break;
					}
				}
				
				//////// Argument분석
				if (nodeList.size() > 1) 
				{
					List<String> convertargumentlist = new ArrayList<String>();
					for(MethodParsingData argument : tokendata.ArgumentTokenizeList)
						convertargumentlist.add(FindCallOrAccessTargetNode(relation,argument));

					//////////////argument비교
					boolean matchingmethod = false;
					ElementNode MatchingMethodNode = null;
					for(ElementNode methodnode :nodeList)
					{
						List<String> ParameterList = ((MethodDomain)methodnode).parameters;
						boolean ismatchingParameter = CompareArgument(ParameterList, convertargumentlist);
						//Argument가 매칭되는것이 있다면!!
						if(ismatchingParameter)
						{
							matchingmethod = true;
							DomainRelation newrelation = CallOrAccessList.AddRelation(Relations.CALLS, relation.getSource() , methodnode.getFullName() , relation.getSourceNode(), methodnode);
							if(!MacroFunctions.NULLTrue(newrelation))
							{
								relation.getSourceNode().AddRelationTarget(newrelation);
								methodnode.AddRelationSource(newrelation);
							}
							MatchingMethodNode = methodnode; 
							break;
						}
					}
					//매칭된 것이 없다면!!
					if(!matchingmethod)
						prevtokendata.Content = "unknown";
					else
					{
						List<DomainRelation> RelationList = MatchingMethodNode.getRelationTargets();
						for(DomainRelation searchreturnlation : RelationList)
						{
							if(searchreturnlation.getRelation() == Relations.RETURNS)
								prevtokendata.Content = searchreturnlation.getTarget();
						}
						prevtokendata.Content = "unknown";
					}
					
				}
				else if(nodeList.size() == 1)
				{
					DomainRelation newrelation = CallOrAccessList.AddRelation(Relations.CALLS, relation.getSource() , nodeList.get(0).getFullName() , relation.getSourceNode(), nodeList.get(0));
					if(!MacroFunctions.NULLTrue(newrelation))
					{
						relation.getSourceNode().AddRelationTarget(newrelation);
						nodeList.get(0).AddRelationSource(newrelation);
					}
					
					MethodParsingData parsingdata = new MethodParsingData();
					String ReturnType = ((MethodDomain)nodeList.get(0)).returntype;
					parsingdata.MakeTokenizedData(ReturnType);
					tokendata.Content = parsingdata.GetTokenizeData(parsingdata.Size()-1).Content;
					tokendata.Type = ReturnType;
				}
				else
				{
					
					//암것도 안함
				}
			}
 			else //Access이다...
 			{
 				//타입이 없다면 처리할 필요 없다.
 				if(tokendata.Type.equals(""))
 					continue;
 				//content가 없다면 걍 타입 리턴..
 				if(tokendata.Content.equals(""))
 					return tokendata.Type;
 				
 				MethodParsingData typeparsingdata = new MethodParsingData();
 				typeparsingdata.MakeTokenizedData(tokendata.Type);
 				
 				MethodTokenizeData typetokenizedata = typeparsingdata.GetTokenizeData(typeparsingdata.Size()-1);
 				
 				String Datatype = typetokenizedata.Content;
 				String ClassName = "";
 				
 				if(IsPrimitiveType(Datatype))
 				{
 					ReturnData = Datatype;
 					tokendata.Content = Datatype;
 					continue;
 				}
 				
 				if(IsUnknownType(Datatype))
 				{
 					if(tokendata.Content.equals(""))
 						ReturnData = Datatype;
 					else
 						ReturnData = tokendata.Content;
 					continue;
 				}
 				
				ReturnData = Datatype;

				if(!MacroFunctions.NULLTrue(prevtokendata))
					ClassName = prevtokendata.Content;
				else
				{
					tokendata.Content = Datatype;
					continue;
				}
					
				List<ElementNode> ClassNode = SearchNodeNameClassOrInterface(ClassName);
				
				if( ClassNode.size() > 1)
				{}//System.out.println("문제있다. ACCESS부분이다. 같은 이름의 클래스가 2개 ㅠㅠ");
				else if(ClassNode.size() == 0)
					return "unknown";
				
				ElementNode TargetNode = null;
				
				//고쳐야됨
				for(int i = 0 ; i < ClassNode.size(); i++)
				{
					TargetNode = SearchOneClassNodeField(ClassNode.get(i),tokendata.Content);
					if(!MacroFunctions.NULLTrue(TargetNode))
						break;
				}
				
				if(MacroFunctions.NULLTrue(TargetNode))
				{
					tokendata.Content = Datatype;
					continue;
				}
				
				
				DomainRelation newrelation = CallOrAccessList.AddRelation(Relations.ACCESSES, relation.getSource(), TargetNode.getFullName(), relation.getSourceNode(), TargetNode);
				if(!MacroFunctions.NULLTrue(newrelation))
				{
					relation.getSourceNode().AddRelationTarget(newrelation);
					TargetNode.AddRelationSource(newrelation);
				}
 			}
 		}
		return ReturnData;
		
 		
 	}
 	private boolean CompareArgument(List<String> methodargument, List<String> analysisarguments )
 	{
 		//아규먼트 갯수가 다르면 당연히 매칭되지 않았다.
 		if( methodargument.size() != analysisarguments.size())
 			return false;
 		
 		for(int i = 0; i< analysisarguments.size(); i++)
		{
			if(analysisarguments.size() - 1 < i)	//얜 뭐지?????
				return false;
			
			//unknown이나 null이면 무조건 통과
			if(!analysisarguments.get(i).equals("unknown") || !analysisarguments.get(i).equals("null"))
			{
				MethodParsingData methodargumentdata = new MethodParsingData(methodargument.get(i));
				MethodParsingData analysisargumentsdata = new MethodParsingData(analysisarguments.get(i));
				if(!CompareMatchingFullnameEndSubname(methodargumentdata,analysisargumentsdata))
					return false;
			}
		}
 		return true;
 	}
 	private boolean CompareMatchingFullnameEndSubname(MethodParsingData data1, MethodParsingData data2)
 	{
 		MethodParsingData bigsizedata = null;
 		MethodParsingData smallsizedata = null;
 		
 		if(data1.Size() > data2.Size())
 		{
 			bigsizedata 	= data1;
 			smallsizedata 	= data2;
 		}
 		else
 		{
 			bigsizedata 	= data2;
 			smallsizedata 	= data1;
 		}
 		boolean IsMatching = true;
 		for(int smallindex = smallsizedata.Size()-1 ; smallindex >= 0  ; smallindex--)
 		{
 			int bigindex = bigsizedata.Size() - smallindex - 1;
 			String smallsizetokenstring = smallsizedata.GetTokenizeData(smallindex).Content;
 			String bigsizetokenstring 	= bigsizedata.GetTokenizeData(bigindex).Content;
 			
 			if(!smallsizetokenstring.equals(bigsizetokenstring))
 				IsMatching = false;
 		}
 		return IsMatching;
 		
 	}
 	private boolean IsPrimitiveType(String data)
 	{
 		if(data.equals(boolean.class.toString()))
 			return true;
 		else if(data.equals(char.class.toString()))
 			return true;
 		else if(data.equals(byte.class.toString()))
 			return true;
 		else if(data.equals(short.class.toString()))
 			return true;
 		else if(data.equals(int.class.toString()))
 			return true;
 		else if(data.equals(long.class.toString()))
 			return true;
 		else if(data.equals(float.class.toString()))
 			return true;
 		else if(data.equals(double.class.toString()))
 			return true;
 		else if(data.equals(String.class.toString()))
 			return true;
 		else
 			return false;
 	}
 	private boolean IsUnknownType(String data)
 	{
 		if(data.equals("unknown"))
 			return true;
 		else
 			return false;
 	}
	private List<ElementNode> GetHierarchyNode(ElementNode elementNode )
 	{
 		List<ElementNode> HierarchyList = new ArrayList<ElementNode>();
 		
 		ElementNode nowNode = elementNode;
 		
		List<DomainRelation> targetdomainlist = nowNode.getRelationTargets();
		for (DomainRelation relation : targetdomainlist) 
		{
			if (   relation.getRelation() == Relations.EXTENDS
				|| relation.getRelation() == Relations.IMPLEMENTS) 
			{
				HierarchyList.add(relation.getTargetNode());
				HierarchyList.addAll(GetHierarchyNode(relation.getTargetNode()));
			}
		}
 		return HierarchyList;
 	}
	private List<ElementNode> SearchNodeNameMethod(String name)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		
		SearchRecursiveNameNode(name, projectNode, ElementNodeType.METHOD, nodeList);
		
		return nodeList;
	}
	private List<ElementNode> SearchNodeNameField(String name)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		
		SearchRecursiveNameNode(name, projectNode, ElementNodeType.METHOD, nodeList);
		
		return nodeList;
	}
	private ElementNode SearchOneClassNodeField(ElementNode classnode, String fieldname)
	{
		for(ElementNode node : classnode.getChildren())
		{
			if( node.getType() == ElementNodeType.FIELD)
			{
				if(node.getName().equals(fieldname))
					return node;
			}
		}
		return null;
	}
	private List<ElementNode> SearchNodeNameClassOrInterface(String name)
	{
		List<ElementNode> nodeList = new ArrayList<ElementNode>();
		
		SearchRecursiveNameNode(name, projectNode, ElementNodeType.CLASS, nodeList);
		SearchRecursiveNameNode(name, projectNode, ElementNodeType.INTERFACE, nodeList);
		
		return nodeList;
	}
	private void SearchRecursiveNameNode(String name,ElementNode node, ElementNodeType type, List<ElementNode> SearchData)
	{
		if(node.getType() == type && node.getName().equals(name))
			SearchData.add(node);

		for(ElementNode childnode :node.getChildren())
			SearchRecursiveNameNode(name,childnode, type, SearchData);
	}
	
	private String mergeStrArray(String[] strs)
	{
		String mergeStr = strs[0];
		for(int i=1;i<strs.length;i++)
			mergeStr += "." + strs[i];
		return (mergeStr);
	}
	private String[] getMinimumMatch(String[] strA, String[] strB)
	{
		List<String> strC = new ArrayList<String>();
		int length = strA.length < strB.length ? strA.length : strB.length;
		for(int i=0;i<length;i++)
		{
			if(strA[i].equals(strB[i]))
				strC.add(strA[i]);
		}
		return (String[])strC.toArray(new String[0]);
	}

}
