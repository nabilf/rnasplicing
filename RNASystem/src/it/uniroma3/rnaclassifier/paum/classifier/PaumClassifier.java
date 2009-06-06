package it.uniroma3.rnaclassifier.paum.classifier;

import it.uniroma3.rnaclassifier.main.GenericClassifier;
import it.uniroma3.rnaclassifier.paum.algorithm.Paum;
import it.uniroma3.rnaclassifier.paum.exception.PAUMException;
import it.uniroma3.rnaclassifier.paum.helper.PaumHelper;
import it.uniroma3.rnaclassifier.paum.models.PAUMInput;
import it.uniroma3.rnaclassifier.paum.models.PAUMOutput;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PaumClassifier extends GenericClassifier{
	
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
	protected void work(Set<RNASequence> trainingSet, Set<RNASequence> testSet) {
		PaumHelper helper = new PaumHelper(); 
		Map<RNAClass, PAUMInput> map = helper.createPaumInput(trainingSet); 
		
		Set<RNAClass> keySet = map.keySet(); 
		Iterator<RNAClass> it = keySet.iterator(); 
		while(it.hasNext()){
			RNAClass clazz = it.next(); 
			PAUMOutput out = this.train(map.get(clazz)); 
			this.classify(testSet, out, clazz); 
		}
	}
	
	private PAUMOutput train(PAUMInput input){
		Paum paum = new Paum(); 
		PAUMOutput out=null;
		try {
			out = paum.training(input);
		} catch (PAUMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return out; 
		
	}
	
	private void classify(Set<RNASequence> testSet, PAUMOutput out, RNAClass clazz){
		PaumHelper helper = new PaumHelper(); 
		
		Iterator<RNASequence> it = testSet.iterator(); 
		 
		while(it.hasNext()){
			RNASequence r = it.next(); 
			
			float[] rValues = helper.createPaumVector(r).getValues(); 
			int result = 0; 
			for(int i =0; i<rValues.length; i++){
			//	log.debug("rValues "+rValues[i]); 
			//	log.debug("W[i]"+w[i]); 
				result += rValues[i]*out.getW()[i]; 
			//	log.debug("Result"+result ); 
			}
			
			System.out.println("Result "+result);
			result += out.getB(); 
			if(result>0){
				r.setPredictedClass(clazz); 
			} 
		}
	}
}
