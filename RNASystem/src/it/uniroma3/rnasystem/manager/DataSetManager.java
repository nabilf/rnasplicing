package it.uniroma3.rnasystem.manager;


import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.reader.DataSetReader;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class DataSetManager {
	public  static Set<RNASequence> trainingSet; 
	public  static Set<RNASequence> testSet;
	
	private static Logger log = Logger.getLogger(DataSetReader.class); 

	public static void createDataSets(Map<RNAClass, List<RNASequence>> dataset, float trainingSetFactor) {
		
		 log.debug("Training set factor "+trainingSetFactor); 
		try {
			trainingSet = new HashSet<RNASequence>(); 
			testSet = new HashSet<RNASequence>();  
			
			Set<RNAClass> key = dataset.keySet(); 
			Iterator<RNAClass> it = key.iterator(); 
			while(it.hasNext()){
				RNAClass className = it.next();
				int trainingCont = Math.round(dataset.get(className).size() * trainingSetFactor); 
				trainingSet.addAll(dataset.get(className).subList(0, trainingCont)); 
				testSet.addAll(dataset.get(className).subList(trainingCont, dataset.get(className).size())); 
			}
			
			log.info("Training Set "+trainingSet.size());
			log.info("Test Set "+testSet.size());
			
			//createLabels(trainingSet);
		} catch (Exception e) {
			log.error("Error", e); 
		}
	}	
}
