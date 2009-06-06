package it.uniroma3.rnaclassifier.model;

public class PerformanceMeasure {
	
	private String RNAClassEnum; 
	
	private double accuracy; 
	private double errorRate; 
	private double precision; 
	private double recall; 
	private double f1Measure; 
	private double breakEven;
	
	public PerformanceMeasure(){
		
	}
	public PerformanceMeasure(String classEnum, double accuracy,
			double breakEven, double errorRate, double measure,
			double precision, double recall) {
		super();
		this.accuracy = accuracy;
		this.breakEven = breakEven;
		this.errorRate = errorRate;
		this.f1Measure = measure;
		this.precision = precision;
		this.recall = recall;
	}
	public String getRNAClassEnum() {
		return RNAClassEnum;
	}
	public void setRNAClassEnum(String classEnum) {
		RNAClassEnum = classEnum;
	}
	public double getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}
	public double getErrorRate() {
		return errorRate;
	}
	public void setErrorRate(double errorRate) {
		this.errorRate = errorRate;
	}
	public double getPrecision() {
		return precision;
	}
	public void setPrecision(double precision) {
		this.precision = precision;
	}
	public double getRecall() {
		return recall;
	}
	public void setRecall(double recall) {
		this.recall = recall;
	}
	public double getF1Measure() {
		return f1Measure;
	}
	public void setF1Measure(double measure) {
		f1Measure = measure;
	}
	public double getBreakEven() {
		return breakEven;
	}
	public void setBreakEven(double breakEven) {
		this.breakEven = breakEven;
	} 
	
	
	
}
