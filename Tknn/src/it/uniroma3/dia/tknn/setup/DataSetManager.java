package it.uniroma3.dia.tknn.setup;

import it.uniroma3.dia.tknn.algorithm.Nussinov;
import it.uniroma3.dia.tknn.algorithm.RNAFolding;
import it.uniroma3.dia.tknn.algorithm.TreeBuilder;
import it.uniroma3.dia.tknn.config.ConfigurationLoader;
import it.uniroma3.dia.tknn.constant.Constant;
import it.uniroma3.dia.tknn.model.Record;
import it.uniroma3.dia.tknn.model.SparseFeatureVector;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class DataSetManager {

	private static Logger log = Logger.getLogger(DataSetManager.class); 
	
	public static void init(){
		try {
			
			DataSetReader.init(ConfigurationLoader.get(Constant.DATASET_PATH), 
					Float.valueOf(ConfigurationLoader.get(Constant.TRAINING_SET_FACTOR)));
			
			Iterator<Record> tIt = DataSetReader.trainingSet.iterator(); 
			while(tIt.hasNext()){
				Record r = tIt.next();
				buildRecord(r); 
				
			}
			log.info("Build Training Set Complete"); 
			Iterator<Record> it2 = DataSetReader.testSet.iterator(); 
			while(it2.hasNext()){
				Record r = it2.next(); 
				buildRecord(r); 
			}
			log.info("Build test Set complete"); 
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void buildRecord(Record r){
		Nussinov folding;
		
		String masterSequence = (r).getData(); 
		folding= new Nussinov(masterSequence); 
		folding.execute(); 
		r.setStructure(folding.getResult()); 
		//System.out.println("Structure size "+r.getStructure().length());
		//System.out.println("Sequence size "+r.getData().length());
		TreeBuilder builder = new TreeBuilder(r.getData(), folding.getResult()); 
		try {
			builder.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 		
		r.setTree(builder.getTree()); 
		createPAUMVector(r); 
	}
	private static void createPAUMVector(Record r){
		SparseFeatureVector v = new SparseFeatureVector(r.getStructure().length()); 
		int[] indexes = new int[v.getLen()]; 
		float[] values = new float[v.getLen()]; 
		
		for(int i =0; i<v.getLen(); i++){
			indexes[i] = i; 
			float value = 0; 
			if(r.getStructure().charAt(i) == '.'){
				value = 0; 
			}else if(r.getStructure().charAt(i) == '('){
				value = 1; 
			}else if(r.getStructure().charAt(i) == ')'){
				value = 2; 
			}
			values[i] = value; 
		}
		v.setIndexes(indexes); 
		v.setValues(values); 
		r.setPaumVector(v); 
	}
	
	public static Set<Record> getNewTrainingSetInstance(){
		return DataSetReader.getNewTrainingSetInstance(); 
	}
	public static Set<Record> getNewTestSetInstance(){
		return DataSetReader.getNewTestSetInstance(); 
	}
}
