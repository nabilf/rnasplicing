package it.uniroma3.rnaclassifier.performance.contingency;

import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;


public class ContingencyMatrixManager {
	
	
	public  ContingencyMatrix contingency_n ;
	public  ContingencyMatrix contingency_ei ;
	public  ContingencyMatrix contingency_ie ;
	
	public  ContingencyMatrix microMeasure;
	
	public ContingencyMatrixManager(){
		init(); 
	}
	public ContingencyMatrixManager(ContingencyMatrix cmEI, ContingencyMatrix cmIE, ContingencyMatrix cmN){
		this.contingency_ei = cmEI;
		this.contingency_ie = cmIE; 
		this.contingency_n = cmN; 
		this.microMeasure = new ContingencyMatrix(); 
		fillMicroMatrix(); 
	}
	
	public  void init(){
		contingency_n = new ContingencyMatrix();
		contingency_ei = new ContingencyMatrix();
		contingency_ie = new ContingencyMatrix();
		microMeasure = new ContingencyMatrix();
	}
	public void fillEIMatrix(RNASequence r){
		contingency_ei.add(
				r.getPredictedClass().equals(RNAClass.EI.toString()), 
				r.getRealClass().equals(RNAClass.EI.toString())); 
	}
	public void fillIEMatrix(RNASequence r){
		contingency_ie.add(
				r.getPredictedClass().equals(RNAClass.IE.toString()), 
				r.getRealClass().equals(RNAClass.IE.toString())); 
	}
	public void fillNMatrix(RNASequence r){
		contingency_n.add(
				r.getPredictedClass().equals(RNAClass.N.toString()), 
				r.getRealClass().equals(RNAClass.N.toString())); 
	}
	public void fillMatrix(RNASequence r , String clazz){
		if(clazz.equalsIgnoreCase(RNAClass.EI.toString())){
			fillEIMatrix(r); 
		}else if (clazz.equalsIgnoreCase(RNAClass.IE.toString())){
			fillIEMatrix(r); 
		}else if(clazz.equalsIgnoreCase(RNAClass.N.toString())){
			fillNMatrix(r); 
		}
	}
	public  void fillMatrix(RNASequence r){
		
		//contingency_n.add(predetta,real);
		contingency_ei.add(
					r.getPredictedClass().equals(RNAClass.EI.toString()), 
					r.getRealClass().equals(RNAClass.EI.toString())); 
		
		contingency_n.add(
				r.getPredictedClass().equals(RNAClass.N.toString()), 
				r.getRealClass().equals(RNAClass.N.toString())); 
		
		contingency_ie.add(
				r.getPredictedClass().equals(RNAClass.IE.toString()), 
				r.getRealClass().equals(RNAClass.IE.toString())); 
		
		microMeasure.addMatrix(contingency_n);
		microMeasure.addMatrix(contingency_ie);
		microMeasure.addMatrix(contingency_ei);
	}
	
	public  void fillMicroMatrix(){
		microMeasure.addMatrix(contingency_n);
		microMeasure.addMatrix(contingency_ie);
		microMeasure.addMatrix(contingency_ei);
		
		
	}
	public void printMatrix(int i) {
		contingency_ei.print("EI "+i); 
		contingency_ie.print("IE "+i); 
		contingency_n.print("N "+i); 
		microMeasure.print("MICRO "+i); 
		
	}
}
