package it.uniroma3.dia.tknn.classifier;

import it.uniroma3.dia.tknn.classifier.methods.MyPAUM;
import it.uniroma3.dia.tknn.classifier.methods.PaumClassifier;
import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.model.MyThread;
import it.uniroma3.dia.tknn.model.PAUMInput;
import it.uniroma3.dia.tknn.model.PaumOutput;
import it.uniroma3.dia.tknn.model.Record;
import it.uniroma3.dia.tknn.performance.contingency.ContingencyMatrix;
import it.uniroma3.dia.tknn.setup.DataSetManager;

import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class PAUMManager extends MyThread{
	public static Logger log = Logging.getInstance(PAUMManager.class); 
	private String threadClass;
	private PaumClassifier pc; 
	private PAUMInput myInput; 
	private ContingencyMatrix cm = new ContingencyMatrix(); 
	private PaumHelper myParent; 
	
	public PAUMManager(String className, PAUMInput myInput, PaumHelper paumHelper){
		this.threadClass = className; 
		this.pc = new PaumClassifier(0.1f,1); 
		this.myInput = myInput; 
		this.myParent = paumHelper; 
	}
	
	
	private PaumOutput train(){
		return this.pc.training(myInput.getVector(), myInput.getTotNumFeatures(), myInput.getClassLabels(), myInput.getNumTraining()); 		 
	}

	private void classify(PaumOutput out){
		Set<Record> testSet = DataSetManager.getNewTestSetInstance(); 
		Iterator<Record> it = testSet.iterator(); 
		
		while(it.hasNext()){
			Record r = it.next(); 
			//log.debug("Class Real "+r.getClassName()); 
			//log.debug("Structure"+r.getStructure()); 
			float[] rValues = r.getPaumVector().getValues(); 
			int result = 0; 
			for(int i =0; i<rValues.length; i++){
			//	log.debug("rValues "+rValues[i]); 
			//	log.debug("W[i]"+w[i]); 
				result += rValues[i]*out.getW()[i]; 
			//	log.debug("Result"+result ); 
			}
			
			result += out.getB(); 
			if(result>0){
				r.setPredictedClass(this.threadClass); 
			}
		}
		
	}
	@Override
	public void run() {
		log.info("Starting "+this.threadClass+" Thread"); 
		//this.executeClassification();
		PaumOutput out= this.train();
		log.info("Training "+this.threadClass+" Complete! "); 
		this.classify(out);
		this.myParent.addResult(this.threadClass, this.cm); 
		super.end= 1; 
	}
	
}
