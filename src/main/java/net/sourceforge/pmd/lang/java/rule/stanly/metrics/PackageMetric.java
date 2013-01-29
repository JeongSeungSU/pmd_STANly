package net.sourceforge.pmd.lang.java.rule.stanly.metrics;

public class PackageMetric {
	private int	Units;
	private float ClassesPerClass;
	private float MethodsPerClass;
	private float FieldsPerClass;
	private int LOC;
	private float ELOCPerUnit;
	private float CC;
	private float FAT;
	private float ADCPerUnit;
	private int AfferentCoupling;
	private int EfferentCoupling;
	private float Abstractness;
	private float Instability;
	private float Distance;
	private float WMC;
	private float DIT;
	private float NOC;
	private float CBO;
	private float RFC;
	private float LCOM;
	
	public int getUnits() {
		return Units;
	}
	public void setUnits(int units) {
		Units = units;
	}
	public float getClassesPerClass() {
		return ClassesPerClass;
	}
	public void setClassesPerClass(float classesPerClass) {
		ClassesPerClass = classesPerClass;
	}
	public float getMethodsPerClass() {
		return MethodsPerClass;
	}
	public void setMethodsPerClass(float methodsPerClass) {
		MethodsPerClass = methodsPerClass;
	}
	public float getFieldsPerClass() {
		return FieldsPerClass;
	}
	public void setFieldsPerClass(float fieldsPerClass) {
		FieldsPerClass = fieldsPerClass;
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
	public float getELOCPerUnit() {
		return ELOCPerUnit;
	}
	public void setELOCPerUnit(float eLOCPerUnit) {
		ELOCPerUnit = eLOCPerUnit;
	}
	public float getCC() {
		return CC;
	}
	public void setCC(float cC) {
		CC = cC;
	}
	public float getFAT() {
		return FAT;
	}
	public void setFAT(float fAT) {
		FAT = fAT;
	}
	public float getADCPerUnit() {
		return ADCPerUnit;
	}
	public void setADCPerUnit(float aDCPerUnit) {
		ADCPerUnit = aDCPerUnit;
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
	public float getAbstractness() {
		return Abstractness;
	}
	public void setAbstractness(float abstractness) {
		Abstractness = abstractness;
	}
	public float getInstability() {
		return Instability;
	}
	public void setInstability(float instability) {
		Instability = instability;
	}
	public float getDistance() {
		return Distance;
	}
	public void setDistance(float distance) {
		Distance = distance;
	}
	public float getWMC() {
		return WMC;
	}
	public void setWMC(float wMC) {
		WMC = wMC;
	}
	public float getDIT() {
		return DIT;
	}
	public void setDIT(float dIT) {
		DIT = dIT;
	}
	public float getNOC() {
		return NOC;
	}
	public void setNOC(float nOC) {
		NOC = nOC;
	}
	public float getCBO() {
		return CBO;
	}
	public void setCBO(float cBO) {
		CBO = cBO;
	}
	public float getRFC() {
		return RFC;
	}
	public void setRFC(float rFC) {
		RFC = rFC;
	}
	public float getLCOM() {
		return LCOM;
	}
	public void setLCOM(float lCOM) {
		LCOM = lCOM;
	}
}
