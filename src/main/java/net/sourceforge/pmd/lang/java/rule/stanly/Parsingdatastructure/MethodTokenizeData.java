package net.sourceforge.pmd.lang.java.rule.stanly.Parsingdatastructure;

import java.util.ArrayList;
import java.util.List;

public class MethodTokenizeData {
	public String Type;
	public String Content;
	public List<String> Argument;
	public List<String> TypeArgument;
	
	public List<MethodParsingData> ArgumentTokenizeList;
	public List<MethodParsingData> TypeArgumentTonkenizeList;
	
	public MethodTokenizeData(String type, String content)
	{
		Type 		 	 			= type;
		Content 		 			= content;
		Argument 	 	 			= new ArrayList<String>();
		TypeArgument 	 			= new ArrayList<String>();
		ArgumentTokenizeList 	 	= new ArrayList<MethodParsingData>();
		TypeArgumentTonkenizeList 	= new ArrayList<MethodParsingData>(); 
	}
}