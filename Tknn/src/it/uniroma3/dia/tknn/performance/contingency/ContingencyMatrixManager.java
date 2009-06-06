package it.uniroma3.dia.tknn.performance.contingency;

import it.uniroma3.dia.tknn.model.RNAClassEnum;
import it.uniroma3.dia.tknn.model.Record;

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
	public void fillEIMatrix(Record r){
		contingency_ei.add(
				r.getPredictedClass().equals(RNAClassEnum.EI.toString()), 
				r.getClassName().equals(RNAClassEnum.EI.toString())); 
	}
	public void fillIEMatrix(Record r){
		contingency_ie.add(
				r.getPredictedClass().equals(RNAClassEnum.IE.toString()), 
				r.getClassName().equals(RNAClassEnum.IE.toString())); 
	}
	public void fillNMatrix(Record r){
		contingency_n.add(
				r.getPredictedClass().equals(RNAClassEnum.N.toString()), 
				r.getClassName().equals(RNAClassEnum.N.toString())); 
	}
	public void fillMatrix(Record r , String clazz){
		if(clazz.equalsIgnoreCase(RNAClassEnum.EI.toString())){
			fillEIMatrix(r); 
		}else if (clazz.equalsIgnoreCase(RNAClassEnum.IE.toString())){
			fillIEMatrix(r); 
		}else if(clazz.equalsIgnoreCase(RNAClassEnum.N.toString())){
			fillNMatrix(r); 
		}
	}
	public  void fillMatrix(Record r){
		
		//contingency_n.add(predetta,real);
		contingency_ei.add(
					r.getPredictedClass().equals(RNAClassEnum.EI.toString()), 
					r.getClassName().equals(RNAClassEnum.EI.toString())); 
		
		contingency_n.add(
				r.getPredictedClass().equals(RNAClassEnum.N.toString()), 
				r.getClassName().equals(RNAClassEnum.N.toString())); 
		
		contingency_ie.add(
				r.getPredictedClass().equals(RNAClassEnum.IE.toString()), 
				r.getClassName().equals(RNAClassEnum.IE.toString())); 
		
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
		// TODO Auto-generated method stub
		
	}
}
