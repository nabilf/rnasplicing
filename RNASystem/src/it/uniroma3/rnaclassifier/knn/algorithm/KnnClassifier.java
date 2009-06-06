	package it.uniroma3.rnaclassifier.knn.algorithm;

import it.uniroma3.rnaclassifier.knn.exception.KNNException;
import it.uniroma3.rnaclassifier.knn.models.KnnClass;
import it.uniroma3.rnaclassifier.main.GenericClassifier;
import it.uniroma3.rnakernels.main.RNAKernel;
import it.uniroma3.rnasystem.constant.Constant;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.model.RNASequenceComparator;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;


public class KnnClassifier extends GenericClassifier{
	
	private static Logger log = Logger.getLogger(KnnClassifier.class); 
	//Voronoi size
	private int k;
	private KnnClass n ; 
	private KnnClass ie; 
	private KnnClass ei ;
	
	//Map of class Frequency : <InstanceClass, frequency>
	private List<KnnClass> classList; 
	
	@Override
	protected void setup(Set<RNASequence> trainingSet,Set<RNASequence> testSet) throws KNNException{
		this.k = RNASystemConfiguration.K; 
		if(this.k == 0){
			throw new KNNException("Valore di k non impostato "); 
		}
		this.ei = new KnnClass(RNAClass.EI); 
		this.ie = new KnnClass(RNAClass.IE);	
		this.n = new KnnClass(RNAClass.N);
		
		this.classList = new LinkedList<KnnClass>(); 
		classList.add(n); 
		classList.add(ie); 
		classList.add(ei);
	}	

	@Override
	protected void work(Set<RNASequence> trainingSet,Set<RNASequence> testSet) {
		log.info("Classificazione con k = "+k); 
		List<RNASequence> trainingSetList = new LinkedList<RNASequence>(); 
		trainingSetList.addAll(trainingSet);
		
		RNAKernel kernel = new RNAKernel(); 
		
		Iterator<RNASequence> it = testSet.iterator(); 
		int i =0; 
		while(it.hasNext()){
			RNASequence rna =it.next(); 
			
			for (RNASequence sequence : trainingSetList) {
				kernel.computeDistance(rna, sequence);
			}
			
			Collections.sort(trainingSetList, new  RNASequenceComparator(Constant.SEQUENCE_COMPARE_K)); 
			
			while (verifyFrequency() && k<trainingSetList.size()-1){
				this.k = k+1; 
				compareClass(k,trainingSetList); 
			}	 
			Collections.sort(this.classList); 
			rna.setPredictedClass(this.classList.get(0).getClassName()); 
			i++; 
		}
	} 
	public boolean verifyFrequency(){
		return ((ei.getFrequency() == ie.getFrequency()) || ei.getFrequency() == n.getFrequency() ||
				ie.getFrequency() == ei.getFrequency() || ie.getFrequency() == n.getFrequency() ||
				n.getFrequency() == ie.getFrequency() || n.getFrequency() == ei.getFrequency());
	}
	public void compareClass(int i, List<RNASequence> trainingSetList){
		if(trainingSetList.get(i).getRealClass()==(RNAClass.EI)){
			ei.setFrequency(ei.getFrequency()+1);	
		}else if(trainingSetList.get(i).getRealClass()==(RNAClass.IE)){
			ie.setFrequency(ie.getFrequency()+1);  
		}else if(trainingSetList.get(i).getRealClass()==(RNAClass.N)){
			n.setFrequency(n.getFrequency()+1); 
		}
	}


}
