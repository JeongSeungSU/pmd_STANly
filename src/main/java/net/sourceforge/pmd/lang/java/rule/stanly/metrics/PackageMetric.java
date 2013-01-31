package net.sourceforge.pmd.lang.java.rule.stanly.metrics;

public class PackageMetric {
	private int NumberOfMethods;//Number of Method
	private int NumberOfClasses;
	private int NumberOfClass;	
	private int NumberOfFields;
	private int TotalCC;
	private int	Units;//Num Of Class
	private int LOC;
	
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
	
	
	public int getNumberOfMethods() {
		return NumberOfMethods;
	}
	public void addNumberOfMethods(int numberOfMethods) {
		NumberOfMethods += numberOfMethods;
	}
	public int getNumberOfClasses() {
		return NumberOfClasses;
	}
	public void addNumberOfClasses(int numberOfClasses) {
		NumberOfClasses += numberOfClasses;
	}
	public int getNumberOfClass() {
		return NumberOfClass;
	}
	public void addNumberOfClass(int numberOfClass) {
		NumberOfClass += numberOfClass;
	}
	public int getNumberOfFields() {
		return NumberOfFields;
	}
	public void addNumberOfFields(int numberOfFields) {
		NumberOfFields += numberOfFields;
	}
	
	public int getUnits() {
		return Units;
	}
	public void addUnits(int units) {
		Units += units;
	}
	public float getClassesPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfClasses / (float)NumberOfClass;//ClassesPerClass;
	}
	
	public float getMethodsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfMethods / (float)NumberOfClass;
	}

	public float getFieldsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfFields / (float)NumberOfClass;
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
		return (float)LOC / (float)Units;
	}	
	public float getAverageCC() {
		return NumberOfMethods == 0 ? 0 : (float)TotalCC / (float)NumberOfMethods;
	}
	public float getCC() {
		return TotalCC;
	}
	public void addCC(int cC) {
		TotalCC += cC;
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
