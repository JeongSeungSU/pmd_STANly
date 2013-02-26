package net.sourceforge.pmd.lang.java.rule.stanly.metrics;

public class PackageSetMetric{
	private int NumberOfMethods;//Number of Method
	private int NumberOfPackages;//Number of Packages
	private int NumberOfClasses;// inner class
	private int NumberOfClass;	// unit class + inner class
	private int NumberOfFields;
	//private int LOC;
	private int totalELOC;
	private int totalUnit;
	private int TotalCC;
	private int Fat;
	private float Tangled;
	
	public float getClassesPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfClasses / (float)NumberOfClass;//ClassesPerClass;
	}
	
	public float getMethodsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfMethods / (float)NumberOfClass;
	}

	public float getFieldsPerClass() {
		return NumberOfClass == 0 ? 0 : (float)NumberOfFields / (float)NumberOfClass;
	}
	/*public int getLOC() {
		return LOC;
	}
	public void setLOC(int loc) {
		LOC = loc;
	}
	public void addLOC(int loc) {
		// TODO Auto-generated method stub
		LOC += loc;
	}*/
	
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
	public void setFat(float fAT) {
		Fat = (int) fAT;
	}
	public float getTangled() {
		return Tangled;
	}
	public void setTangled(float tangled) {
		Tangled = tangled;
	}
	public int getNumberOfMethods() {
		return NumberOfMethods;
	}
	public void addNumberOfMethods(int numberOfPackages) {
		NumberOfMethods += numberOfPackages;
	}
	public int getNumberOfPakcages() {
		return NumberOfPackages;
	}
	public void addNumberOfPackages(int numberOfPackages) {
		NumberOfPackages += numberOfPackages;
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
	public float getAverageELOC() {
		return (float)totalELOC / (float)totalUnit;
	}
	public int getTotalELOC() {
		return totalELOC;
	}
	public void addTotalELOC(int totalELOC) {
		this.totalELOC += totalELOC;
	}
	public int getTotalUnit() {
		return totalUnit;
	}
	public void addTotalUnit(int totalUnit) {
		this.totalUnit += totalUnit;
	}
	
}
