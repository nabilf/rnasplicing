package it.uniroma3.rnaclassifier.paum.classifier;

import it.uniroma3.rnaclassifier.main.GenericClassifier;
import it.uniroma3.rnaclassifier.paum.algorithm.Paum;
import it.uniroma3.rnaclassifier.paum.exception.PAUMException;
import it.uniroma3.rnaclassifier.paum.helper.PaumHelper;
import it.uniroma3.rnaclassifier.paum.models.PAUMInput;
import it.uniroma3.rnaclassifier.paum.models.PAUMOutput;
import it.uniroma3.rnaclassifier.performance.contingency.ContingencyMatrixManager;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class PaumClassifier extends GenericClassifier{
	private static Logger log = Logger.getLogger(PaumClassifier.class); 
	private Paum classifier; 
	
	@Override
	protected void setup(Set<RNASequence> trainingSet, Set<RNASequence> testSet) throws PAUMException{
		
		this.classifier = new Paum(
											RNASystemConfiguration.ETA,
											RNASystemConfiguration.TAUN,
											RNASystemConfiguration.TAUP, 
											RNASystemConfiguration.TOTALLOOPS); 
	}

	@Override
	protected ContingencyMatrixManager work(Set<RNASequence> trainingSet, Set<RNASequence> testSet) {
		PaumHelper helper = new PaumHelper(); 
		Map<RNAClass, PAUMInput> map = helper.createPaumInput(trainingSet); 
		ContingencyMatrixManager cm = new ContingencyMatrixManager(); 
		
		Set<RNAClass> keySet = map.keySet(); 
		Iterator<RNAClass> it = keySet.iterator(); 
		while(it.hasNext()){
			RNAClass clazz = it.next(); 
			PAUMOutput out = this.train(map.get(clazz)); 
			cm = this.classify(testSet, out, clazz, cm);
		}
		return cm; 
	}
	
	private PAUMOutput train(PAUMInput input){
		PAUMOutput out=null;
		try {
			out = classifier.training(input);
		} catch (PAUMException e) {
			log.error("Paum Exception "+e.getMessage()); 
		} 
		return out; 
		
	}
	
	private ContingencyMatrixManager classify(Set<RNASequence> testSet, PAUMOutput out, RNAClass clazz, ContingencyMatrixManager cm2){
		PaumHelper helper = new PaumHelper(); 
		
		Iterator<RNASequence> it = testSet.iterator(); 
		 
		while(it.hasNext()){
			RNASequence r = it.next(); 
			
			float[] rValues = helper.createPaumVector(r).getValues(); 
			int result = 0; 
			//log.debug("r values "+rValues.length); 
			//log.debug(out.getW().length); 
			for(int i =0; i<rValues.length-1; i++){
				//log.debug("rValues "+rValues[i]); 
				//log.debug("W[i]"+out.getW()[i]); 
				result += rValues[i]*out.getW()[i]; 
				//log.debug("Result"+result ); 
			}
			result += out.getB(); 
			if(result>0){
				r.setPredictedClass(clazz); 
                log.debug("REAL CLASS "+r.getRealClass()); 
                log.debug("PREDICTED CLASS "+r.getPredictedClass()); 
                log.debug("Classification Result "+r.getPredictedClass().equals(r.getRealClass())); 
			} 
			cm2.fillMatrix(r);
		}
		 
		return cm2; 
	}
}
