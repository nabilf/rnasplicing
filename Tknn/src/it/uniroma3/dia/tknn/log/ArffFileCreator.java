package it.uniroma3.dia.tknn.log;

import it.uniroma3.dia.tknn.model.Record;
import it.uniroma3.dia.tknn.model.SparseFeatureVector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;

public class ArffFileCreator {
	private static Logger log = Logger.getLogger(PerformanceLogger.class); 
	private static FileOutputStream fos ;
	
	public static void openFile(String pathName){
		
		File fileToSave = new File(pathName);
		try {
			fos = new FileOutputStream(fileToSave);
		} catch (FileNotFoundException e) {
			log.error ("Unable to create file ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void writeFile(Set<Record> set){
		
		try {
			String relation = "@RELATION rna \n";
			fos.write(relation.getBytes());
			
			for(int i =0; i<140; i++){
				String attribute = "@ATTRIBUTE "+i+" numeric \n"; 
				fos.write(attribute.getBytes()); 
			}
			String clazz = "@ATTRIBUTE class {IE, EI, N} \n\n\n";
			fos.write(clazz.getBytes());
			String data = "@DATA \n"; 
			fos.write(data.getBytes()); 
			
			Iterator<Record> it = set.iterator(); 
			while(it.hasNext()){
				Record r = it.next(); 
				SparseFeatureVector v = r.getPaumVector(); 
				String attribute = ""; 
				System.out.println("len"+v.getValues().length);
				for(int i =0; i<v.getValues().length; i++){
					attribute += v.getValues()[i]+","; 
				}
				attribute += r.getClassName()+"\n"; 
				fos.write(attribute.getBytes()); 
			}
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	public static void closeFile(){
		try {
			fos.close();
		} catch (IOException e) {
			log.error("Unable to close file"); 
		} 
	}
	
}
