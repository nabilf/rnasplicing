package it.uniroma3.dia.tknn.classifier.methods;

import it.uniroma3.dia.tknn.model.RNAClass;
import it.uniroma3.dia.tknn.model.RNAClassEnum;
import it.uniroma3.dia.tknn.model.Record;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

public class KnnClassifier{
	private static Logger log = Logger.getLogger(KnnClassifier.class); 
	private int k ; 
	private RNAClass n ; 
	private RNAClass ie; 
	private RNAClass ei ;
	private List<Record> trainingSetList; 
	
	public KnnClassifier(int k) {
		super();
		this.k = k;
		this.ei = new RNAClass(RNAClassEnum.EI); 
		this.ie = new RNAClass(RNAClassEnum.IE);
		this.n = new RNAClass(RNAClassEnum.N);
		trainingSetList = new LinkedList<Record>(); 
	
	}
	public void classify(Record r, Set<Record> trainingSet){
		log.info("Classificazione con k = "+k); 
		trainingSetList.addAll(trainingSet); 	
		Collections.sort(trainingSetList); 
		
		List<RNAClass> classes = new LinkedList<RNAClass>(); 
		classes.add(n); 
		classes.add(ie); 
		classes.add(ei);
		
		for(int i =0; i <=k; i++  ){
			compareClass(i);
		}
		log.info("verify "+verifyFrequency()); 
		
		while (verifyFrequency() && k<this.trainingSetList.size()-1){
			this.k = k+1; 
			compareClass(k); 
		}
		
		log.debug("EI freq "+ei.getFrequency()); 
		log.debug("IE Frequency "+ie.getFrequency()); 
		log.debug("N frequency "+n.getFrequency());
 
		Collections.sort(classes); 
		log.debug(RNAClassEnum.EI.toString()); 
		r.setPredictedClass(classes.get(0).getClassName().toString()); 
		//log.info("REAL Class "+r.getClassName()); 
		//log.info("Predicted Class "+r.getPredictedClass()); 
	}
	public boolean verifyFrequency(){
		return ((ei.getFrequency() == ie.getFrequency()) || ei.getFrequency() == n.getFrequency() ||
				ie.getFrequency() == ei.getFrequency() || ie.getFrequency() == n.getFrequency() ||
				n.getFrequency() == ie.getFrequency() || n.getFrequency() == ei.getFrequency());
	}
	public void compareClass(int i){
		if(trainingSetList.get(i).getClassName().equalsIgnoreCase(RNAClassEnum.EI.toString())){
			ei.setFrequency(ei.getFrequency()+1);
			log.debug("ei ");
		}else if(trainingSetList.get(i).getClassName().equalsIgnoreCase(RNAClassEnum.IE.toString())){
			ie.setFrequency(ie.getFrequency()+1);  
			log.debug("ie ");
		}else if(trainingSetList.get(i).getClassName().equalsIgnoreCase(RNAClassEnum.N.toString())){
			n.setFrequency(n.getFrequency()+1); 
			log.debug("n ");
		}
	}
}
