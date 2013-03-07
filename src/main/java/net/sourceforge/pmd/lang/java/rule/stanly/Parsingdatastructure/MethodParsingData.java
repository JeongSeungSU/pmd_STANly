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
	
	public MethodParsingData(String data)
	{
		MethodTokenizedDataList = new ArrayList<MethodTokenizeData>();
		MakeTokenizedData(data);
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
					InitArgumentOrTypeTokenizedData(nowdata);
					nowdata = new MethodTokenizeData("","");
					continue;
				default:
					nowdata.Content += Target.charAt(i);
			}
		}
		AddTokenizedData(nowdata);
		InitArgumentOrTypeTokenizedData(nowdata);

	}
 	public boolean CompareMatchingFullnameEndSubname(MethodParsingData data)
 	{
 		MethodParsingData bigsizedata = null;
 		MethodParsingData smallsizedata = null;
 		
 		if(this.Size() > data.Size())
 		{
 			bigsizedata 	= this;
 			smallsizedata 	= data;
 		}
 		else
 		{
 			bigsizedata 	= data;
 			smallsizedata 	= this;
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
	private void InitArgumentOrTypeTokenizedData(MethodTokenizeData data)
	{
		for(String typeargument :data.TypeArgument)
		{
			MethodParsingData typeargumentdata = new MethodParsingData();
			typeargumentdata.MakeTokenizedData(typeargument);
			data.TypeArgumentTonkenizeList.add(typeargumentdata);
		}
		
		for(String argument :data.Argument)
		{
			MethodParsingData argumentdata = new MethodParsingData();
			argumentdata.MakeTokenizedData(argument);
			data.ArgumentTokenizeList.add(argumentdata);
		}
	}
	public String GetContentTokenData(int start,int end )
	{
		String UpperName = "";
		for(int i = start; i < end; i++)
			UpperName += this.GetTokenizeData(i).Content + ".";
		
		UpperName = UpperName.substring(0,UpperName.length()-1);
		return UpperName;
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
			if (Target.length() >= nowPos + typeSeperateLenth) 
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
						int start = DetermineTypeSeperat(stringdata,i);
						result = TypeMode(stringdata,start);
						if(result + TypeSeperate.length() > stringdata.length())
						{
							int z =0;
						}
						nowString += stringdata.substring(i, result + TypeSeperate.length());
						i = result + TypeSeperate.length() - 1;
					}
					else nowString += stringdata.charAt(i);
					break;
				case '<':
					result = ArgumentOrTypeMode(stringdata,i, '<', '>');
					nowString += stringdata.substring(i, result+1);
					i = result;
					break;
				case '(':
					result = ArgumentOrTypeMode(stringdata,i, '(', ')');
					nowString += stringdata.substring(i, result+1);
					i = result;
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
