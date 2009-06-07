package it.uniroma3.rnaclassifier.paum.helper;

import it.uniroma3.rnaclassifier.paum.models.PAUMInput;
import it.uniroma3.rnaclassifier.paum.models.SparseFeatureVector;
import it.uniroma3.rnasystem.constant.Constant;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNAComparator;
import it.uniroma3.rnasystem.model.RNASequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class PaumHelper {

	private static Logger log = Logger.getLogger(PaumHelper.class); 
	private short[] ieLabels; 
	private short[] eiLabels; 
	private short[] nLabels; 
	
	private int totalNumberOfFeatures; 
	
	public short[] createClassLabels(Set<RNASequence> set){
		 
		int setSize = set.size(); 
		this.ieLabels = new short[setSize]; 
		this.eiLabels = new short[setSize]; 
		this.nLabels = new short[setSize]; 
		
		Iterator<RNASequence> it = set.iterator(); 
		int i =0; 
		while(it.hasNext()){
			RNASequence r = it.next(); 
			short ieLabel = 0; 
			short eiLabel = 0; 
			short nLabel = 0; 
			
			if(r.getRealClass() == RNAClass.EI){
				//log.debug("EI");
				eiLabel = 1; 
				ieLabel = nLabel = -1; 
				
			}else if(r.getRealClass() == RNAClass.IE){
				//log.debug("IE");
				ieLabel = 1; 
				eiLabel = nLabel = -1; 
			}else if (r.getRealClass() == RNAClass.N){
				//log.debug("N");
				nLabel = 1; 
				ieLabel = eiLabel = -1; 
			}
			ieLabels[i] = ieLabel; 
			//System.out.print("IE" +ieLabel+" ");
			eiLabels[i] = eiLabel;
			//System.out.print("EI" +eiLabel+" "); 
			nLabels[i] = nLabel;
			//System.out.print("N" +nLabel+" ");
			//System.out.println();
			i++;
		}
		return null; 

	}
	public SparseFeatureVector[] createDataLearning(Set<RNASequence> set){
		LinkedList<RNASequence> trainingSetList = new LinkedList<RNASequence>(); 
		
		trainingSetList.addAll(set); 
		Collections.sort(trainingSetList, new RNAComparator(Constant.COMPARE_REAL_CLASS)); 
		
		SparseFeatureVector[] vector = new SparseFeatureVector[trainingSetList.size()]; 
		this.totalNumberOfFeatures = 0; 
		int i =0; 
		for(RNASequence r : trainingSetList){
			this.totalNumberOfFeatures = r.getSequence().length(); 
			
			vector[i] = createPaumVector(r); 
			i++; 
		}
		log.debug("DataLearning Size "+vector.length); 
		
		return vector; 
	}
	public SparseFeatureVector createPaumVector(RNASequence r){
		log.debug("RNA : "+r.toString()); 
		SparseFeatureVector v = new SparseFeatureVector(r.getStructure().length()); 
		log.debug("Vector size " +v.getLen()); 
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
		return v; 
	}
	
	public  Map<RNAClass,PAUMInput> createPaumInput(Set<RNASequence> set){
	
		SparseFeatureVector[] vector = this.createDataLearning(set); 
		this.createClassLabels(set); 
		Map<RNAClass, PAUMInput> map = new HashMap<RNAClass, PAUMInput>();
		log.debug("Total Number of Features "+this.totalNumberOfFeatures); 
		map.put(RNAClass.EI, new PAUMInput(this.eiLabels, set.size(), this.totalNumberOfFeatures,vector)); 
		map.put(RNAClass.IE, new PAUMInput(this.ieLabels, set.size(), this.totalNumberOfFeatures,vector)); 
		map.put(RNAClass.N, new PAUMInput(this.nLabels, set.size(), this.totalNumberOfFeatures,vector));
		return map; 
	}
}
