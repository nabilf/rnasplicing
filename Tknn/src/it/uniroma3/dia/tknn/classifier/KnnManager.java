package it.uniroma3.dia.tknn.classifier;

import it.uniroma3.dia.tknn.classifier.methods.KnnClassifier;
import it.uniroma3.dia.tknn.exception.KernelException;
import it.uniroma3.dia.tknn.kernel.TreeKernel;
import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.main.ThreadManager;
import it.uniroma3.dia.tknn.model.MyThread;
import it.uniroma3.dia.tknn.model.PerformanceMeasure;
import it.uniroma3.dia.tknn.model.Record;
import it.uniroma3.dia.tknn.performance.PerformanceManager;
import it.uniroma3.dia.tknn.performance.contingency.ContingencyMatrixManager;
import it.uniroma3.dia.tknn.setup.DataSetManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * 
 * @author kali
 *
 */
public class KnnManager extends MyThread {
	public static Logger log = Logging.getInstance(KnnManager.class); 
	
	private int k; // numero di nearest neighbrs
	private ContingencyMatrixManager cm = new ContingencyMatrixManager(); 
	private ThreadManager myParent; 
	private List<PerformanceMeasure> list;
	
	public KnnManager(int k, ThreadManager parent){
		this.k = k; 
		this.myParent = parent; 
		this.list = new LinkedList<PerformanceMeasure>(); 
	}
	public void executeClassification(){
		
		Set<Record> testSet = DataSetManager.getNewTestSetInstance(); 
		Iterator<Record> it = testSet.iterator(); 
		
		//List<KernelThread> threadList = new LinkedList<KernelThread>(); 
		int t =0; 
		while(it.hasNext()){
			Record r = it.next(); 
			//KernelThread kt = new KernelThread(r, this.k);
			
			this.classify(r); 
			cm.fillMatrix(r); 
			
			//kt.start(); 
			log.info("Classified Record ["+this.k+"]."+t+" di "+testSet.size()); 
			t++; 
			//threadList.add(kt); 
		}

    	log.info("Filling Contingency Matrix complete"); 
		
		cm.printMatrix(this.k); 
		
    	PerformanceManager pm = new PerformanceManager(cm.contingency_ei, cm.contingency_ie, cm.contingency_n,cm.microMeasure); 
    	
//		list = new LinkedList<PerformanceMeasure>();
//		PerformanceManager pm = new PerformanceManager(cm.contingency_ei); 
//		PerformanceMeasure ei= pm.calculatePerformance(); 
//		ei.setRNAClassEnum(RNAClassEnum.EI.toString()); 
//		list.add(ei); 
//		
//		pm = new PerformanceManager(cm.contingency_ie); 
//		PerformanceMeasure ie= pm.calculatePerformance(); 
//		ie.setRNAClassEnum(RNAClassEnum.IE.toString()); 
//		list.add(ie); 
//		
//		pm = new PerformanceManager(cm.contingency_n); 
//		PerformanceMeasure n= pm.calculatePerformance(); 
//		n.setRNAClassEnum(RNAClassEnum.N.toString()); 
//		list.add(n); 
//		
//		pm = new PerformanceManager(cm.microMeasure); 
//		PerformanceMeasure micro = pm.calculatePerformance(); 
//		micro.setRNAClassEnum(RNAClassEnum.MICRO.toString()); 
//		list.add(micro); 
//		
//		pm = new PerformanceManager(); 
//		PerformanceMeasure macro = pm.calcMacroPerformance(ie, ei, n); 
//		macro.setRNAClassEnum(RNAClassEnum.MACRO.toString()); 
//		list.add(macro); 
		
		this.myParent.addResult(this.k,pm.getList()); 
	}
	
	@Override
	public void run() {
		this.executeClassification();
		super.end= 1; 
	}	
	private void classify(Record myRecord){
		
		Set<Record> trainingSet = DataSetManager.getNewTrainingSetInstance(); 
		Iterator<Record> intIt = trainingSet.iterator(); 
		
		while(intIt.hasNext()){
			Record current = intIt.next(); 
			
			TreeKernel sk= new TreeKernel(myRecord.getTree(), current.getTree()); 
			//SequenceKernel sk = new SequenceKernel(myRecord.getStructure(), current.getStructure()); 
			try {
				sk.execute();
				//tk.execute(); 
			} catch (KernelException e) {
				log.error("Sequence Kernel Exception"); 
			} 
			//current.setK(tk.getK()); 
			current.setK(sk.getK());
		}
		
		KnnClassifier knn = new KnnClassifier(this.k);
		knn.classify(myRecord, trainingSet); 
	}
	

}
