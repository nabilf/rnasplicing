package it.uniroma3.dia.tknn.main;

import it.uniroma3.dia.tknn.classifier.KnnManager;
import it.uniroma3.dia.tknn.classifier.PAUMManager;
import it.uniroma3.dia.tknn.classifier.PaumHelper;
import it.uniroma3.dia.tknn.log.PerformanceLogger;
import it.uniroma3.dia.tknn.log.Logging;
import it.uniroma3.dia.tknn.model.MyThread;
import it.uniroma3.dia.tknn.model.PerformanceMeasure;
import it.uniroma3.dia.tknn.performance.contingency.ContingencyMatrix;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class ThreadManager {
	public static Logger log = Logging.getInstance(ThreadManager.class); 
	
	private Map<Integer, List<PerformanceMeasure>> knnMap; 
	private Map<String, ContingencyMatrix> paumMap; 
	
	public ThreadManager(){
		this.knnMap = new HashMap<Integer, List<PerformanceMeasure>>(); 
		
	}
	public void start(){
	
		try {
			//Set<Record> testSet = DataSetManager.getNewTestSetInstance(); 
			
			
			List<MyThread> threadList = new LinkedList<MyThread>();
			
			//startKNNClassification(threadList); 
			startPAUMClassification(threadList); 
			
			ThreadUtility.wait(threadList); 
			this.printReport(); 
			log.info(" ---- End -----"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void startPAUMClassification(List<MyThread> threadList) {
		PaumHelper helper = new PaumHelper(this); 
		helper.preProcess(); 
	}
	private void startKNNClassification(List<MyThread> threadList){
		int k = 11; //DataSetReader.trainingSet.size()-1; //numero di thread
		//Per ogni record del test set, istanzio un thread che lo verifica per tutto il training set.
		for(int i = 1; i<k; i++){
		
			KnnManager km = new KnnManager(i, this); 
			km.start(); 
			threadList.add(km); 
			log.info("Starting Kernel Manager Thread K = "+i );
		}
	}
	private void printReport(){
		PerformanceLogger.openFile(); 
		PerformanceLogger.writeReport(this.knnMap); 
		PerformanceLogger.closeFile(); 
	}
	public synchronized void addResult(int key, List<PerformanceMeasure> value){
		this.knnMap.put(key,value); 
	}

}
