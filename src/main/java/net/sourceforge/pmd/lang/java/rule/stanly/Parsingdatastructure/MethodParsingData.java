package net.sourceforge.pmd.lang.java.rule.stanly.Parsingdatastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.tools.ant.taskdefs.Tar;
import org.apache.tools.ant.types.selectors.TypeSelector;

public class MethodParsingData {
	List<MethodTokenizeData> MethodTokenizedDataList;
	String TypeSeperate = "StanlyTypeIndicate";
	
	public MethodParsingData()
	{
		MethodTokenizedDataList = new ArrayList<MethodTokenizeData>();
	}
	
	public void MakeTokenizedData(String Target)
	{
		MethodTokenizeData nowdata = new MethodTokenizeData("", "", "", "");
		
		boolean Type = false;
		boolean Argument = false;
		boolean TypeArgument = false;
		
		for(int i =0 ; i < Target.length(); i++)
		{
			switch(Target.charAt(i))
			{
				case 'S':
					if(DetermineTypeSeperat(Target,i) > i)
					{
						Type = true;
						i = DetermineTypeSeperat(Target,i);
					}
					break;
				case '<':
					TypeArgument = true;
					break;
				case '(':
					Argument = true;
					break;
				case '.':
					AddTokenizedData(nowdata);
					nowdata = new MethodTokenizeData("", "" , "", "");
					continue;
			}
			
			if(Type)
			{
				int result = TypeMode(Target,i);
				nowdata.Type = Target.substring(i,result);
				i = result + TypeSeperate.length() - 1;
				Type = false;
			}
			else if(TypeArgument)
			{
				int result = ArgumentOrTypeMode(Target,i, '<', '>');
				nowdata.TypeArgument = Target.substring(i+1,result);
				i = result;
				TypeArgument = false;
			}
			else if(Argument)
			{
				int result = ArgumentOrTypeMode(Target,i, '(', ')');
				nowdata.Argument = Target.substring(i+1,result);
				if(nowdata.Argument.equals(""))
					nowdata.Argument = " ";
				i = result;
				Argument = false;
			}
			else
				nowdata.Content += Target.charAt(i);
		}
		AddTokenizedData(nowdata);
	}

	private int ArgumentOrTypeMode(String Target, int nowpos, char start, char end)
	{
		Stack<Integer> TopofSeperateSearch = new Stack<Integer>();
		int Count = nowpos;
		
		for( ; Count < Target.length() ; Count++)
		{
			char c = Target.charAt(Count);
			if(c == start)
			{
				TopofSeperateSearch.push(Count);
			}
			else if(c == end)
			{
				TopofSeperateSearch.pop();
				if(TopofSeperateSearch.size() == 0)
					break;
			}
		}
		return Count;
	}
	private int TypeMode(String Target, int nowPos)
	{
		int result = DetermineTypeSeperat(Target,nowPos);
		
		for( ; result < Target.length(); result++)
		{
			if(Target.charAt(result) == 'S')
			{
				int returnpos = DetermineTypeSeperat(Target,result);
				if(result < returnpos)
					break;
			}
		}
		return result;
	}
	private int DetermineTypeSeperat(String Target, int nowPos)
	{
		int typeSeperateLenth = TypeSeperate.length();
		
		if (Target.charAt(nowPos) == 'S') 
		{
			if (Target.length() - 1 >= nowPos + typeSeperateLenth) 
			{
				String typedetermine = Target.substring(nowPos, nowPos + typeSeperateLenth);
				if (typedetermine.equals(TypeSeperate))
					return nowPos + typeSeperateLenth;
				else
					return nowPos;
			}
		}
		return nowPos;
	}

	
	private void AddTokenizedData(MethodTokenizeData data)
	{
		MethodTokenizedDataList.add(data);
	}
	
}
