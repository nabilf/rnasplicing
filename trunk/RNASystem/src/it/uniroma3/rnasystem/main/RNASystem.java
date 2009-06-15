package it.uniroma3.rnasystem.main;

import it.uniroma3.rnasystem.manager.ClassifierManager;
import it.uniroma3.rnasystem.manager.DataSetManager;
import it.uniroma3.rnasystem.manager.FoldingManager;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.reader.DataSetReader;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;
import it.uniroma3.rnasystem.setup.RNASystemSetup;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class RNASystem {

	private static Logger log = Logger.getLogger(RNASystem.class); 
	
	public static void main(String[] args) {
		RNASystemSetup.init(); 
		
		String sourceDataSetPath;
		try {
			log.info("Caricamento Dataset "); 
			sourceDataSetPath = RNASystemConfiguration.dataSetPath+RNASystemConfiguration.uciDataSetName; 
			//sourceDataSetPath = ConfigurationLoader.get(Constant.DATASET_PATH)+
			//					ConfigurationLoader.get(Constant.UCIDATASET_NAME);
			
			Map<RNAClass, List<RNASequence>> dataset = DataSetReader.read(sourceDataSetPath);; 
			log.info("DataSet Caricato; istanze : n*"+dataset.size() ); 
			
			//FOLDING
			dataset = FoldingManager.fold(dataset); 
				
			//BUILDING 
			//BuilderManager.build(dataset); 
			
			//Building Training and Test Set; 
			DataSetManager.createDataSets(dataset,RNASystemConfiguration.trainingSetFactor); 
			
			//CLASSIFY
			ClassifierManager.classify(DataSetManager.trainingSet, DataSetManager.testSet); 
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			

		log.info("THE END"); 
	}
	

}
