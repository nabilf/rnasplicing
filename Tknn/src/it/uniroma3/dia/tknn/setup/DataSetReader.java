package it.uniroma3.dia.tknn.setup;

import it.uniroma3.dia.tknn.model.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class DataSetReader {

	public  static Set<Record> trainingSet; 
	public  static  Set<Record> testSet;

	public static short[] ieLabels; 
	public static short[] eiLabels; 
	public static short[] nLabels; 
	
	private static Logger log = Logger.getLogger(DataSetReader.class); 
	
	public static void init(String path, float trainingSetFactor) {
				
		/*Load file and create dataset */
		File dataSetFile = new File(path); 
		InputStream is;
		
		try {
			is = new FileInputStream(dataSetFile);
			Scanner sc = new Scanner(is);
		
			Map<String,List<String>> dataset = new HashMap<String,List<String>>(); 
			
			while(sc.hasNextLine()) {
				String record = sc.nextLine(); 
				StringTokenizer tk = new StringTokenizer(record, "\t");
				String className = tk.nextToken();
				String data = tk.nextToken();
				if(dataset.get(className)==null){
					List<String> classData = new LinkedList<String>(); 
					dataset.put(className,classData); 
				}
				dataset.get(className).add(data); 
			}
			
			trainingSet = new HashSet<Record>(); 
			testSet = new HashSet<Record>();  
			
			
			Set<String> key = dataset.keySet(); 
			Iterator<String> it = key.iterator(); 
			while(it.hasNext()){
				String className = it.next();
				int trainingCont = Math.round(dataset.get(className).size() * trainingSetFactor); 
				trainingSet.addAll(createSet(className, 0, trainingCont, dataset.get(className))); 
				testSet.addAll(createSet(className,trainingCont, dataset.get(className).size(), dataset.get(className))); 
			}
			
			log.info("Training Set "+trainingSet.size());
			log.info("Test Set "+testSet.size());
			
			createLabels(trainingSet); 
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void createLabels(Set<Record> trainingSet){
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
//		System.out.println("IE");
//		print(ieLabels);
//		System.out.println("EI");
//		print(eiLabels);
//		System.out.println("N");
//		print(nLabels); 
	}
	private static void print(short[] a){
		for (int i =0; i<a.length; i++){
			System.out.print(a[i]+" ");
		}
		System.out.println(" \n");
	}
	private  static Set<Record> createSet(String className, int start, int end, List<String> list ){
		Set<Record> set = new HashSet<Record>();

		for(int i=start; i<end; i++){
			Record r = new Record(); 
			r.setData(list.get(i)); 
			r.setClassName(className); 
			set.add(r); 
		}	
		return set; 
	}
	
	public static Set<Record> getNewTrainingSetInstance(){
		Set<Record> set = new HashSet<Record>(); 
		
		Iterator<Record> it = trainingSet.iterator(); 
		while(it.hasNext()){
			Record r = it.next().clone(); 
			set.add(r); 
		}
		return set; 
	}
	public static Set<Record> getNewTestSetInstance(){
		Set<Record> set = new HashSet<Record>(); 
		
		Iterator<Record> it = testSet.iterator(); 
		while(it.hasNext()){
			Record r = it.next().clone(); 
			set.add(r); 
		}
		return set; 
	}	
	

}
