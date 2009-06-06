package it.uniroma3.rnaclassifier.manager;

import it.uniroma3.rnaclassifier.model.PerformanceMeasure;
import it.uniroma3.rnaclassifier.performance.contingency.ContingencyMatrix;

import java.util.List;


public class PerformanceManager {

	private ContingencyMatrix cm;
	
	public PerformanceManager(){
		
	}
	public PerformanceManager(ContingencyMatrix cm){
		this.cm = cm; 
	}
	
	public PerformanceMeasure calculatePerformance(){
		PerformanceMeasure pm = new PerformanceMeasure(); 
		pm.setAccuracy(this.calcAccuracy()); 
		pm.setErrorRate(this.calcErrorRate()); 
		pm.setPrecision(this.calcPrecision()); 
		pm.setRecall(this.calcRecall()); 
		pm.setF1Measure(this.calcF1Mesurement()); 
		pm.setBreakEven(this.calcBreakEven()); 
		
		return pm; 
		
	}
	public PerformanceMeasure calcMacroPerformance(List<PerformanceMeasure> list){
		PerformanceMeasure macro = new PerformanceMeasure(); 

		for (PerformanceMeasure pm : list) { 	
			macro.setAccuracy(macro.getAccuracy()+pm.getAccuracy()); 
			macro.setErrorRate(macro.getErrorRate()+pm.getErrorRate()); 
			macro.setPrecision(macro.getPrecision()+pm.getPrecision()); 
			macro.setRecall(macro.getRecall()+pm.getRecall());
			macro.setF1Measure(macro.getF1Measure()+pm.getF1Measure()); 
			macro.setBreakEven(macro.getBreakEven()+pm.getBreakEven()); 
		
		}
		int size = list.size(); 
		macro.setAccuracy(macro.getAccuracy()/size); 
		macro.setErrorRate(macro.getErrorRate()/size); 
		macro.setPrecision(macro.getPrecision()/size); 
		macro.setRecall(macro.getRecall()/size);
		macro.setF1Measure(macro.getF1Measure()/size); 
		macro.setBreakEven(macro.getBreakEven()/size); 
	
		return macro;
		
		
	}
	private  double calcErrorRate() {
		return (this.cm.get(true, false) + this.cm.get(false, true)) / this.cm.getAll();
	}
	
	private double calcAccuracy() {
		return 1 - calcErrorRate();
	}
	
	private double calcPrecision() {
		return cm.get(true, true) / (cm.get(true, true)+cm.get(true, false));
	}
	
	private double calcRecall( ) {
		return cm.get(true, true) / (cm.get(true, true)+cm.get(false, true));
	}
	
	private double calcBreakEven( ) {
		return (calcPrecision()+calcRecall())/2;
	}
	
	private double calcF1Mesurement( ) {
		return (2*calcPrecision()*calcRecall())/(calcPrecision()+calcRecall());
	}	
}
