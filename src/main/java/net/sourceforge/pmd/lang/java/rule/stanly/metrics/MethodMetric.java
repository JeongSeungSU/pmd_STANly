package net.sourceforge.pmd.lang.java.rule.stanly.metrics;


public class MethodMetric {
	private int LOC;
	private int CC;
	
	public int getLOC() {
		return LOC;
	}
	public void setLOC(int lOC) {
		LOC = lOC;
	}
	public int getCC() {
		return CC;
	}
	public void setCC(int cC) {
		CC = cC;
	}
	public void addLOC(int i) {
		// TODO Auto-generated method stub
		LOC += i;
	}
}