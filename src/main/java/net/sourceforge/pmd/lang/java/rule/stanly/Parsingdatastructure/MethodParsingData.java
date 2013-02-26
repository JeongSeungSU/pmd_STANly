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
		MethodTokenizeData nowdata = new MethodTokenizeData("", "");
		int result;
		
		for(int i =0 ; i < Target.length(); i++)
		{
			switch(Target.charAt(i))
			{
				case 'S':
					if(DetermineTypeSeperat(Target,i) > i)
					{
						i = DetermineTypeSeperat(Target, i);
						result = TypeMode(Target, i);
						nowdata.Type = Target.substring(i, result);
						i = result + TypeSeperate.length() - 1;
					}
					else nowdata.Content += Target.charAt(i);
					break;
				case '<':
					result = ArgumentOrTypeMode(Target,i, '<', '>');
					nowdata.TypeArgument = DotSeparator(Target.substring(i+1,result));
					i = result;
					break;
				case '(':
					result = ArgumentOrTypeMode(Target,i, '(', ')');
					nowdata.Argument = DotSeparator(Target.substring(i+1,result));
					i = result;
					break;
				case '.':
					AddTokenizedData(nowdata);
					nowdata = new MethodTokenizeData("","");
					continue;
				default:
					nowdata.Content += Target.charAt(i);
			}
		}
		AddTokenizedData(nowdata);
		
		for(String typeargument :nowdata.TypeArgument)
		{
			MethodParsingData typeargumentdata = new MethodParsingData();
			typeargumentdata.MakeTokenizedData(typeargument);
			nowdata.TypeArgumentTonkenizeList.add(typeargumentdata);
		}
		
		for(String argument :nowdata.Argument)
		{
			MethodParsingData argumentdata = new MethodParsingData();
			argumentdata.MakeTokenizedData(argument);
			nowdata.TypeArgumentTonkenizeList.add(argumentdata);
		}
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
	
	public MethodTokenizeData GetTokenizeData(int index)
	{
		if(index < 0 || index > MethodTokenizedDataList.size() - 1)
			return null;
		return MethodTokenizedDataList.get(index);
	}
	
	public int Size()
	{
		return MethodTokenizedDataList.size();
	}
	
	
	public List<String> DotSeparator(String stringdata)
	{
		String nowString = "";
		List<String> StringList= new ArrayList<String>(); 
		int result;
		
		for(int i =0 ; i < stringdata.length(); i++)
		{
			switch(stringdata.charAt(i))
			{
				case 'S':
					if(DetermineTypeSeperat(stringdata,i) > i)
					{
						i = DetermineTypeSeperat(stringdata,i);
						result = TypeMode(stringdata,i);
						i = result + TypeSeperate.length() - 1;
					}
					else nowString += stringdata.charAt(i);
					break;
				case '<':
					i = ArgumentOrTypeMode(stringdata,i, '<', '>');
					break;
				case '(':
					i = ArgumentOrTypeMode(stringdata,i, '(', ')');
					break;
				case ',':
					StringList.add(nowString);
					nowString = "";
					continue;
				default:
					nowString += stringdata.charAt(i);
			}
		}
		StringList.add(nowString);
		
		return StringList;
	}
}
