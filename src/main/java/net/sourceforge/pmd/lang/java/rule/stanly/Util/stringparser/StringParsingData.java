package net.sourceforge.pmd.lang.java.rule.stanly.Util.stringparser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.tools.ant.taskdefs.Tar;
import org.apache.tools.ant.types.selectors.TypeSelector;

/**
 * 예) net.sourceforge.pmd.lang.java.rule.stanly.Util.stringparser 
 * -> [net,sourceforge,pmd,lang,java,rule,stanly,Util,stringparser]처럼 tokenize 된 데이터를 가짐..
 * 일반화 할 필요가 있음...Type따라 그리고 ,나 <따라서...
 * @since 2013. 3. 20.오전 12:49:58
 * @author JeongSeungsu
 */
public class StringParsingData {
	/**
	 * Tokenize된 데이터 List
	 */
	List<StringTokenizeData> MethodTokenizedDataList;
	String TypeSeperate = "StanlyTypeIndicate";
	
	public StringParsingData()
	{
		MethodTokenizedDataList = new ArrayList<StringTokenizeData>();
	}
	
	public StringParsingData(String data)
	{
		MethodTokenizedDataList = new ArrayList<StringTokenizeData>();
		MakeTokenizedData(data);
	}
	
	/**
	 * 현재 가지고 있는 데이터를 Tokenize 한다.
	 * @since 2013. 3. 20.오전 12:51:24
	 * @author JeongSeungsu
	 * @param Target
	 */
	public void MakeTokenizedData(String Target)
	{
		StringTokenizeData nowdata = new StringTokenizeData("", "");
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
					nowdata = new StringTokenizeData("","");
					continue;
				default:
					nowdata.Content += Target.charAt(i);
			}
		}
		AddTokenizedData(nowdata);
		InitArgumentOrTypeTokenizedData(nowdata);

	}
 	/**
 	 * String A = pmd.lang.java.rule.stanly.Util.stringparser;
 	 * String B = stanly.Util.stringparser;
 	 * 일때 A와 B를 비교해서SUB에 위치하면 true 리턴
 	 * @since 2013. 3. 20.오전 12:51:45
 	 * @author JeongSeungsu
 	 * @param data
 	 * @return
 	 */
 	public boolean CompareMatchingFullnameEndSubname(StringParsingData data)
 	{
 		StringParsingData bigsizedata = null;
 		StringParsingData smallsizedata = null;
 		
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
	/**
	 * (A,B,C) 또는 <T,CB> 이런 데이터를 다시 Tokenie 시켜주는 함수..
	 * @since 2013. 3. 20.오전 12:52:32
	 * @author JeongSeungsu
	 * @param data
	 */
	private void InitArgumentOrTypeTokenizedData(StringTokenizeData data)
	{
		for(String typeargument :data.TypeArgument)
		{
			StringParsingData typeargumentdata = new StringParsingData();
			typeargumentdata.MakeTokenizedData(typeargument);
			data.TypeArgumentTonkenizeList.add(typeargumentdata);
		}
		
		for(String argument :data.Argument)
		{
			StringParsingData argumentdata = new StringParsingData();
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

	private void AddTokenizedData(StringTokenizeData data)
	{
		MethodTokenizedDataList.add(data);
	}
	
	public StringTokenizeData GetTokenizeData(int index)
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
