package it.uniroma3.rnaclassifier.knn.classifier;

import it.uniroma3.rnaclassifier.knn.exception.KNNException;
import it.uniroma3.rnaclassifier.knn.models.KnnClass;
import it.uniroma3.rnaclassifier.main.GenericClassifier;
import it.uniroma3.rnaclassifier.performance.contingency.ContingencyMatrixManager;
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
                renewClassList(); 
        }       

        @Override
        protected ContingencyMatrixManager work(Set<RNASequence> trainingSet,Set<RNASequence> testSet) {
                log.info("Classificazione con k = "+k); 
                
                ContingencyMatrixManager cm = new ContingencyMatrixManager(); 
                
                List<RNASequence> trainingSetList = new LinkedList<RNASequence>(); 
                trainingSetList.addAll(trainingSet);
                
                RNAKernel kernel = new RNAKernel(); 
                
                Iterator<RNASequence> it = testSet.iterator(); 
                int c =0; 
                
                while(it.hasNext()){
                    
                		RNASequence rna =it.next(); 
                        
                        for (RNASequence sequence : trainingSetList) {
                                kernel.computeDistance(rna, sequence);
                        }
                       // printList(trainingSetList); 
                        Collections.sort(trainingSetList, new  RNASequenceComparator(Constant.SEQUENCE_COMPARE_K));
                       // printList(trainingSetList); 
                        renewClassList(); 
                        
						for(int i =0; i <=this.k; i++  ){
							compareClass(i,trainingSetList);
						}
                         
						Collections.sort(this.classList); 
                        int newK = this.k; 
                        while (verifyFrequency() && k<trainingSetList.size()-1){
                                compareClass(newK,trainingSetList); 
                                newK++; 
                        }        
                        Collections.sort(this.classList); 
                        rna.setPredictedClass(this.classList.get(0).getClassName()); 
                        
                        
                        log.debug("*** Iteration "+c+" K = "+this.k+" ***"); 
                        log.debug("FREQ of "+this.classList.get(0).toString() +":"+this.classList.get(0).getFrequency());
                        log.debug("FREQ of "+this.classList.get(1).toString() +":"+this.classList.get(1).getFrequency());
                        log.debug("FREQ of "+this.classList.get(2).toString() +":"+this.classList.get(2).getFrequency());
                       
                        log.debug("REAL CLASS "+rna.getRealClass()); 
                        log.debug("PREDICTED CLASS "+rna.getPredictedClass()); 
                        log.debug("Classification Result "+rna.getPredictedClass().equals(rna.getRealClass())); 
                        
                        cm.fillMatrix(rna); 
                        c++; 
                }
                return cm; 
        } 
        public boolean verifyFrequency(){
//                return ((this.ei.getFrequency() == this.ie.getFrequency()) || this.ei.getFrequency() == this.n.getFrequency() ||
//                                this.ie.getFrequency() == this.ei.getFrequency() || this.ie.getFrequency() == this.n.getFrequency() ||
//                                this.n.getFrequency() == this.ie.getFrequency() || this.n.getFrequency() == this.ei.getFrequency());
        	return (this.classList.get(0).getFrequency()==this.classList.get(1).getFrequency());  
        }
        public void compareClass(int i, List<RNASequence> trainingSetList){
                if(trainingSetList.get(i).getRealClass()==(RNAClass.EI)){
                        this.ei.setFrequency(this.ei.getFrequency()+1);   
                }else if(trainingSetList.get(i).getRealClass()==(RNAClass.IE)){
                        this.ie.setFrequency(this.ie.getFrequency()+1);  
                }else if(trainingSetList.get(i).getRealClass()==(RNAClass.N)){
                        this.n.setFrequency(this.n.getFrequency()+1); 
                }
        }
        
        public void renewClassList(){
            this.ei = new KnnClass(RNAClass.EI); 
            this.ie = new KnnClass(RNAClass.IE);    
            this.n = new KnnClass(RNAClass.N);
            
            this.classList = new LinkedList<KnnClass>(); 
            classList.add(this.n); 
            classList.add(this.ie); 
            classList.add(this.ei);
        }
public void printList(List<RNASequence> list){
	for (RNASequence s : list) {
		log.debug("RNA class "+s.getRealClass()+" distance "+s.getDistance()); 
	}
}

}
