package it.uniroma3.dia.tknn.classifier;

import it.uniroma3.dia.tknn.constant.Constant;
import it.uniroma3.dia.tknn.main.ThreadManager;
import it.uniroma3.dia.tknn.main.ThreadUtility;
import it.uniroma3.dia.tknn.model.MyThread;
import it.uniroma3.dia.tknn.model.PAUMInput;
import it.uniroma3.dia.tknn.model.RNAClassEnum;
import it.uniroma3.dia.tknn.model.Record;
import it.uniroma3.dia.tknn.model.RecordComparator;
import it.uniroma3.dia.tknn.model.SparseFeatureVector;
import it.uniroma3.dia.tknn.performance.PerformanceManager;
import it.uniroma3.dia.tknn.performance.contingency.ContingencyMatrix;
import it.uniroma3.dia.tknn.performance.contingency.ContingencyMatrixManager;
import it.uniroma3.dia.tknn.setup.DataSetManager;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class PaumHelper {

	private Map<String, ContingencyMatrix> paumMap;
	private ThreadManager parent; 
	
	public static short[] ieLabels; 
	public static short[] eiLabels; 
	public static short[] nLabels; 
	
	public PaumHelper(ThreadManager parent){
		this.paumMap = new HashMap<String, ContingencyMatrix>(); 	
		this.parent = parent; 
	}
	
	public void preProcess(){
		Set<Record> trainingSet = DataSetManager.getNewTrainingSetInstance(); 
		LinkedList<Record> trainingSetList = new LinkedList<Record>(); 
		
		trainingSetList.addAll(trainingSet); 
		Collections.sort(trainingSetList, new RecordComparator(Constant.COMPARE_CLASS)); 
		
		SparseFeatureVector[] vector = new SparseFeatureVector[trainingSetList.size()]; 
		int totNumFeatures = 0; 
		int i =0; 
		for(Record r : trainingSetList){
			totNumFeatures = r.getData().length(); 
			vector[i] = r.getPaumVector(); 
			i++; 
		}
		
		this.createLabels(trainingSetList); 
		
		PAUMInput eiInput = new PAUMInput(eiLabels,(trainingSet.size()) , totNumFeatures, vector); 
		PAUMInput ieInput = new PAUMInput(ieLabels,(trainingSet.size()) , totNumFeatures, vector);
		PAUMInput nInput = new PAUMInput(nLabels,(trainingSet.size()) , totNumFeatures, vector);
		
		PAUMManager paumEI = new PAUMManager(RNAClassEnum.EI.toString(), eiInput, this); 
		PAUMManager paumIE = new PAUMManager(RNAClassEnum.IE.toString(), ieInput, this); 
		PAUMManager paumN = new PAUMManager(RNAClassEnum.N.toString(), nInput, this);
		
		paumEI.start(); 
		paumIE.start(); 
		paumN.start(); 
		
		LinkedList<MyThread> threadList = new LinkedList<MyThread>(); 
		threadList.add(paumEI); 
		threadList.add(paumN); 
		threadList.add(paumIE); 
		
		ThreadUtility.wait(threadList);
		
		ContingencyMatrix cmEI = this.paumMap.get(RNAClassEnum.EI.toString()); 
		ContingencyMatrix cmIE = this.paumMap.get(RNAClassEnum.IE.toString()); 
		ContingencyMatrix cmN = this.paumMap.get(RNAClassEnum.N.toString()); 
		
		cmEI.print("EI"); 
		cmIE.print("IE"); 
		cmN.print("N"); 
		
		ContingencyMatrixManager cman = new ContingencyMatrixManager(cmEI, cmIE, cmN);
		
		PerformanceManager pm = new PerformanceManager(cman.contingency_ei, cman.contingency_ie, cman.contingency_n, cman.microMeasure); 
		parent.addResult(1, pm.getList()); 
		
	}	
	
	private void createLabels(List<Record> trainingSet){
		int setSize = trainingSet.size(); 
		
		ieLabels = new short[setSize];
		eiLabels = new short[setSize];
		nLabels = new short[setSize];
		
		Iterator<Record> it = trainingSet.iterator(); 
		int i =0; 
		while(it.hasNext()){
			Record r = it.next(); 
			short ieLabel = 0; 
			short eiLabel = 0; 
			short nLabel = 0; 
			System.out.println("Class Name"+r.getClassName()); 
			if(r.getClassName().equalsIgnoreCase("EI")){
				//log.debug("EI");
				eiLabel = 1; 
				ieLabel = nLabel = -1; 
			}else if(r.getClassName().equalsIgnoreCase("IE")){
				//log.debug("IE");
				ieLabel = 1; 
				eiLabel = nLabel = -1; 
			}else if (r.getClassName().equalsIgnoreCase("N")){
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
	}
	public synchronized void addResult(String clazz, ContingencyMatrix cm){
		this.paumMap.put(clazz,cm); 
	}
	
}
