package net.sourceforge.pmd.lang.java.rule.yhctest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public abstract class StanlyNode {

	public static final int PROJECT = 0;
	public static final int FOLDER = 1;
	public static final int PACKAGE = 2;
	public static final int CLASS = 3;
	public static final int ENUM = 4;
	public static final int INTERFACE = 5;
	public static final int ATTRIBUTE = 6;
	public static final int CONSTRUCTOR = 7;
	public static final int METHOD = 8;
	
	
	StanlyMetrics metrics;
	public int type;
	
	public StanlyNode parent;
	public List<StanlyNode> children;
	
	public String name;
	
	public StanlyNode(int type,String name)
	{
		this.type = type;
		this.name = name;
		this.children = new ArrayList<StanlyNode>();
	}
}
