package it.uniroma3.dia.tknn.performance;

import java.util.LinkedList;
import java.util.List;

import it.uniroma3.dia.tknn.model.PerformanceMeasure;
import it.uniroma3.dia.tknn.model.RNAClassEnum;
import it.uniroma3.dia.tknn.performance.contingency.ContingencyMatrix;

public class PerformanceManager {

	private ContingencyMatrix cm;
	private List<PerformanceMeasure> list;
	
	public PerformanceManager(){
		
	}
	public PerformanceManager(ContingencyMatrix cm){
		this.cm = cm; 
	}
	public PerformanceManager(ContingencyMatrix cm1, ContingencyMatrix cm2, ContingencyMatrix cm3, ContingencyMatrix cm4){
		this.list = new LinkedList<PerformanceMeasure>(); 
		this.createList(cm1, cm2, cm3, cm4); 
	}
	
	private void createList(ContingencyMatrix cm1, ContingencyMatrix cm2, ContingencyMatrix cm3, ContingencyMatrix cm4) {
		list = new LinkedList<PerformanceMeasure>();
		PerformanceManager pm = new PerformanceManager(cm1); 
		PerformanceMeasure ei= pm.calculatePerformance(); 
		ei.setRNAClassEnum(RNAClassEnum.EI.toString()); 
		list.add(ei); 
		
		this.cm = cm1; 
		PerformanceMeasure ie= this.calculatePerformance(); 
		ie.setRNAClassEnum(RNAClassEnum.IE.toString()); 
		list.add(ie); 
		
		this.cm = cm2; 
		PerformanceMeasure n= this.calculatePerformance(); 
		n.setRNAClassEnum(RNAClassEnum.N.toString()); 
		list.add(n); 
		
		this.cm = cm3; 
		PerformanceMeasure micro = this.calculatePerformance(); 
		micro.setRNAClassEnum(RNAClassEnum.MICRO.toString()); 
		list.add(micro); 
		
		PerformanceMeasure macro = this.calcMacroPerformance(ie, ei, n); 
		macro.setRNAClassEnum(RNAClassEnum.MACRO.toString()); 
		list.add(macro); 

		
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
	public PerformanceMeasure calcMacroPerformance(PerformanceMeasure ie, PerformanceMeasure ei, PerformanceMeasure n){
		PerformanceMeasure macro = new PerformanceMeasure(); 
	
		macro.setAccuracy((ie.getAccuracy()+ei.getAccuracy()+n.getAccuracy())/3); 
		macro.setErrorRate((ie.getErrorRate()+ei.getErrorRate()+n.getErrorRate())/3); 
		macro.setPrecision((ie.getPrecision()+ei.getPrecision()+n.getPrecision())/3); 
		macro.setRecall((ie.getRecall()+ei.getRecall()+n.getRecall())/3);
		macro.setF1Measure((ie.getF1Measure()+ei.getF1Measure()+n.getF1Measure())/3); 
		macro.setBreakEven((ie.getBreakEven()+ei.getBreakEven()+n.getBreakEven())/3); 
		
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
	public List<PerformanceMeasure> getList() {
		return list;
	}
	public void setList(List<PerformanceMeasure> list) {
		this.list = list;
	}
	
}
