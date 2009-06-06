//package it.uniroma3.dia.tknn.kernel;
//
//import it.uniroma3.dia.tknn.classifier.methods.KnnClassifier;
//import it.uniroma3.dia.tknn.exception.KernelException;
//import it.uniroma3.dia.tknn.log.Logging;
//import it.uniroma3.dia.tknn.model.Record;
//import it.uniroma3.dia.tknn.setup.DataSetManager;
//
//import java.util.Iterator;
//import java.util.Set;
//
//import org.apache.log4j.Logger;
//
///**
// * Thread singolo per il calcolo della distanza presente tra il record
// * del test set e l'intero set di training. 
// * 
// * @author kali
// *
// */
//public class KernelThread extends Thread {
//	public static Logger log = Logging.getInstance(KernelThread.class); 
//
//	private Record myRecord ; //Record del test set
//	private int k; // numero di nearest neighbors
//	private int end = 0; //flag di lavoro finito
//	
//	
//	public KernelThread(Record myRecord, int k){
//		this.myRecord = myRecord; 
//		this.k = k; 
//	}
//
//	private void classify(){
//		
//		Set<Record> trainingSet = DataSetManager.getNewTrainingSetInstance(); 
//		Iterator<Record> intIt = trainingSet.iterator(); 
//		
//		while(intIt.hasNext()){
//			Record current = intIt.next(); 
//			
//			//TreeKernel tk= new TreeKernel(this.myRecord.getTree(), current.getTree()); 
//			SequenceKernel sk = new SequenceKernel(this.myRecord.getData(), current.getData()); 
//			try {
//				sk.execute();
//				//tk.execute(); 
//			} catch (KernelException e) {
//				log.error("Sequence Kernel Exception"); 
//			} 
//			//current.setK(tk.getK()); 
//			current.setK(sk.getK());
//		}
//		
//		KnnClassifier knn = new KnnClassifier(k);
//		knn.classify(this.myRecord, trainingSet); 
//	}
//	
//	@Override
//	public void run() {
//		log.info("Starting thread n*"+this.k); 
//		this.classify();
//		this.end= 1; 
//		log.info("Thread "+this.k+"ENDED WORK ! ");
//		
//	}
//	public int getEnd() {
//		return end;
//	}
//	public void setEnd(int end) {
//		this.end = end;
//	}	
//}
