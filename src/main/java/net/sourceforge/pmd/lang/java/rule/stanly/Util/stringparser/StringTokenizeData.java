package net.sourceforge.pmd.lang.java.rule.stanly.Util.stringparser;

import java.util.ArrayList;
import java.util.List;

public class StringTokenizeData {
	public String Type;
	public String Content;
	public List<String> Argument;
	public List<String> TypeArgument;
	
	public List<StringParsingData> ArgumentTokenizeList;
	public List<StringParsingData> TypeArgumentTonkenizeList;
	
	public StringTokenizeData(String type, String content)
	{
		Type 		 	 			= type;
		Content 		 			= content;
		Argument 	 	 			= new ArrayList<String>();
		TypeArgument 	 			= new ArrayList<String>();
		ArgumentTokenizeList 	 	= new ArrayList<StringParsingData>();
		TypeArgumentTonkenizeList 	= new ArrayList<StringParsingData>(); 
	}
}