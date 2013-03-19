package net.sourceforge.pmd.lang.java.rule.stanly.datastructure.metricelement;

public class PackageMetric {
	private int NumberOfMethods;//Number of Method
	private int NumberOfClasses;// inner class
	private int NumberOfClass;	// unit class + inner class
	private int NumberOfAbstract;//abstract class
	private int NumberOfFields;
	private int	Units;//Num Of Class
	private int TotalCC;
	private int LOC;
	
	private int Fat;
	private float ACDPerUnit;
	private int AfferentCoupling;
	private int EfferentCoupling;	
	public int getTotalDIT() {
		return totalDIT;
	}
	public void setTotalDIT(int totalDIT) {
		this.totalDIT = totalDIT;
	}
	public int getTotalNOC() {
		return totalNOC;
	}
	public void setTotalNOC(int totalNOC) {
		this.totalNOC = totalNOC;
	}
	public int getTotalLCOM() {
		return totalLCOM;
	}
	public void setTotalLCOM(int totalLCOM) {
		this.totalLCOM = totalLCOM;
	}
	public void setNumberOfMethods(int numberOfMethods) {
		NumberOfMethods = numberOfMethods;
	}
	public void setNumberOfClasses(int numberOfClasses) {
		NumberOfClasses = numberOfClasses;
	}
	public void setNumberOfClass(int numberOfClass) {
		NumberOfClass = numberOfClass;
	}
	public void setNumberOfAbstract(int numberOfAbstract) {
		NumberOfAbstract = numberOfAbstract;
	}
	public void setNumberOfFields(int numberOfFields) {
		NumberOfFields = numberOfFields;
	}
	public void setUnits(int units) {
		Units = units;
	}
	public void setTotalCC(int totalCC) {
		TotalCC = totalCC;
	}
	public void setTotalCBO(int totalCBO) {
		this.totalCBO = totalCBO;
	}
	public void setTotalRFC(int totalRFC) {
		this.totalRFC = totalRFC;
	}
	private float Abstractness;
	private float Instability;
	private float Distance;
	//private float WMC;
	private int totalDIT;
	private int totalNOC;
	private int totalCBO;
	private int totalRFC;
	private int totalLCOM;
	
	
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
	public int getNumberOfAbstract() {
		return NumberOfAbstract;
	}
	public void addNumberOfAbstract(int numberOfAbstract) {
		NumberOfAbstract += numberOfAbstract;
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
	public int getTotalCC() {
		return TotalCC;
	}
	public void addCC(int cC) {
		TotalCC += cC;
	}
	public int getFat() {
		return Fat;
	}
	public void setFat(int fAT) {
		Fat = fAT;
	}
	public float getACDPerUnit() {
		return ACDPerUnit;
	}
	public void setACDPerUnit(float aCDPerUnit) {
		ACDPerUnit = aCDPerUnit;
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
		return NumberOfClass == 0 ? 0 : (float)TotalCC / (float)NumberOfClass;
	}
	//public void setWMC(float wMC) {
	//	WMC = wMC;
	//}
	public float getDIT() {
		return (float)totalDIT / (float)this.NumberOfClass;
	}
	public void addDIT(int dIT) {
		totalDIT += dIT;
	}
	public float getNOC() {
		return (float)totalNOC / (float)this.NumberOfClass;
	}
	public void addNOC(int nOC) {
		totalNOC += nOC;
	}
	public int getTotalCBO() {
		return totalCBO;
	}
	public float getAverageCBO() {
		return NumberOfClass == 0 ? 0 : (float)totalCBO / (float)NumberOfClass;
	}
	public void addCBO(int cBO) {
		totalCBO += cBO;
	}
	
	public float getAverageRFC() {
		return NumberOfClass == 0 ? 0 : (float)totalRFC / (float)NumberOfClass;
	}
	public int getTotalRFC() {
		return totalRFC;
	}
	public void addRFC(int rFC) {
		totalRFC += rFC;
	}
	public float getAverageLCOM() {
		return NumberOfClass == 0 ? 0 : (float)totalLCOM / (float)NumberOfClass;
	}
	public int getLCOM() {
		return totalLCOM;
	}
	public void addLCOM(int lCOM) {
		totalLCOM += lCOM;
	}
}
