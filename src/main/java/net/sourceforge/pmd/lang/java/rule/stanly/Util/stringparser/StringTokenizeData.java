package net.sourceforge.pmd.lang.java.rule.stanly.Util.stringparser;

import java.util.ArrayList;
import java.util.List;

/**
 * . 및 Type을 기준으로 Tokenize된 데이터 이다.
 * @since 2013. 3. 20.오전 12:47:05
 * @author JeongSeungsu
 */
public class StringTokenizeData {
	/**
	 * Type content가 가지고 있는 타입
	 */
	public String Type;
	/**
	 * .. 사이의 String 값
	 */
	public String Content;
	/**
	 * ccc(a,b,c)에서 ()안의 값을 List화
	 */
	public List<String> Argument;
	/**
	 * .A<T,List<T>>에서 <>안의 값을 다시 List화 시킨것
	 */
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