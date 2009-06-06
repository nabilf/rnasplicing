package it.uniroma3.rnasystem.reader;


import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class DataSetReader {

	public static short[] ieLabels; 
	public static short[] eiLabels; 
	public static short[] nLabels; 
	
	private static Logger log = Logger.getLogger(DataSetReader.class); 
	
	public static Map<RNAClass, List<RNASequence>> read(String dataSetPath) {
				
		/*Load file and create dataset */
		File dataSetFile = new File(dataSetPath); 
		InputStream is;
		Map<RNAClass,List<RNASequence>> dataset = null; 
		try {
			is = new FileInputStream(dataSetFile);
			Scanner sc = new Scanner(is);
		
			dataset = new HashMap<RNAClass,List<RNASequence>>(); 
			
			while(sc.hasNextLine()) {
				String record = sc.nextLine(); 
				StringTokenizer tk = new StringTokenizer(record, "\t");
				String className = tk.nextToken();
				String data = tk.nextToken();
				RNASequence rna = new RNASequence(RNAClass.getRNAClass(className), data, null); 
				if(dataset.get(RNAClass.getRNAClass(className))==null){
				
					List<RNASequence> classData = new LinkedList<RNASequence>(); 
					dataset.put(RNAClass.getRNAClass(className),classData); 
				}
				dataset.get(RNAClass.getRNAClass(className)).add(rna); 
			}
			
			 
		} catch (FileNotFoundException e) {
			log.error("DataSet File not Found in "+dataSetPath);
			
		}
		return dataset; 
	}

}
