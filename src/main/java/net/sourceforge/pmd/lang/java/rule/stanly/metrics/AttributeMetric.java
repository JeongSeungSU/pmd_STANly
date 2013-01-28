package net.sourceforge.pmd.lang.java.rule.stanly.metrics;

public class AttributeMetric {
	private int Instructions;
	private int ELOC;
	private int CC;
	public int getInstructions() {
		return Instructions;
	}
	public void setInstructions(int instructions) {
		Instructions = instructions;
	}
	public int getELOC() {
		return ELOC;
	}
	public void setELOC(int eLOC) {
		ELOC = eLOC;
	}
	public int getCC() {
		return CC;
	}
	public void setCC(int cC) {
		CC = cC;
	}
}
