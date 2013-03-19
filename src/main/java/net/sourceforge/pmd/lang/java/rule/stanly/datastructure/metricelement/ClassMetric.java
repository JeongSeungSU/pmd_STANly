package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.metricelement;

public class ClassMetric {
	private int Classes;
	private int Methods;
	private int Fields;
	private int LOC;
	private int Fat;
	private int AfferentCoupling;
	private int EfferentCoupling;
	private float WMC;
	private int DIT;
	private int NOC;
	private int CBO;
	private float RFC;
	private int LCOM;
	
	public int getClasses() {
		return Classes;
	}
	public void addClasses(int classes) {
		Classes += classes;
	}	
	public int getMethods() {
		return Methods;
	}
	public void addMethods(int methods) {
		Methods += methods;
	}
	public int getFields() {
		return Fields;
	}
	public void addFields(int fields) {
		Fields += fields;
	}
	public int getLOC() {
		return LOC;
	}
	public void setLOC(int lOC) {
		LOC = lOC;
	}
	public void addLOC(int lOC) {
		LOC += lOC;
	}
	public int getFat() {
		return Fat;
	}
	public void setFat(int fat) {
		Fat = fat;
	}
	public int getAfferentCoupling() {
		return AfferentCoupling;
	}
	public void setAfferentCoupling(int afferentCoupling) {
		AfferentCoupling = afferentCoupling;
	}
	public int getEfferentCoupling() {
		return EfferentCoupling;
	}
	public void setEfferentCoupling(int efferentCoupling) {
		EfferentCoupling = efferentCoupling;
	}
	public float getWMC() {
		return WMC;
	}
	public void addWMC(float wMC) {
		WMC += wMC;
	}
	public int getDIT() {
		return DIT;
	}
	public void setDIT(int dIT) {
		DIT = dIT;
	}
	public int getNOC() {
		return NOC;
	}
	public void setNOC(int nOC) {
		NOC = nOC;
	}
	public float getCBO() {
		return CBO;
	}
	public void addCBO(int cBO) {
		CBO += cBO;
	}
	public float getRFC() {
		return RFC;
	}
	public void setRFC(float rFC) {
		RFC = rFC;
	}
	public int getLCOM() {
		return LCOM;
	}
	public void addLCOM(int lCOM) {
		LCOM += lCOM;
	}
}
