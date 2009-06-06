package it.uniroma3.rnafolding.main;

import it.uniroma3.rnafolding.cache.FoldingCache;
import it.uniroma3.rnafolding.exception.RNAFoldingException;
import it.uniroma3.rnafolding.interfaces.FoldingAlgorithm;
import it.uniroma3.rnasystem.utils.RNAFileFilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

public class RNAFolding {

	private static Logger log = Logger.getLogger(RNAFolding.class);

	
	public static String fold (String sequence, String faClass, String outputDir, String clazz) throws RNAFoldingException{
		FoldingAlgorithm fc = RNAFolding.init(faClass, outputDir, clazz); 
		
		return RNAFolding.foldAndSave(sequence, fc); 
	}
	
	public static void fold(List<String> sequences, String faClass, String outputDir, String clazz) throws RNAFoldingException{
		FoldingAlgorithm fc = RNAFolding.init(faClass, outputDir, clazz); 
		
		if(sequences != null & sequences.size()!= 0) {
			for (String sequence : sequences) {
				RNAFolding.foldAndSave(sequence, fc);
			}
		}
		 
	}
	public static void mergeCache(String outputDir, List<String> classes){
		File dest = new File(outputDir);
		log.debug("Output Directory "+outputDir); 
		FileOutputStream fos;
		try{	
			fos = new FileOutputStream(dest);
			for (String clazz : classes) {
				File dir = new File(clazz); 
				log.debug("Directory "+dir.getName()); 
				if(dir.exists()){
					FilenameFilter rnafilter = new RNAFileFilter();
					
					File[] files = dir.listFiles(rnafilter); 
					
					log.debug("File list size "+files.length); 
					for (int i = 0; i < files.length; i++) {
						File f = files[i]; 
						String line="";
						if(f.exists()){
							FileInputStream fis = new FileInputStream(f); 
						    BufferedReader br=new BufferedReader(new InputStreamReader(fis));
						    int c = 0; 
						    while (c!=-1){
						    	
						    	c=br.read();
						    	if(c!= -1)
						    		line +=((char)c);
						    }
						    //log.debug("Line to write"+line); 
						    fos.write((line+"\n").getBytes());
						    fos.flush(); 
						    
						    fis.close(); 
						    br.close(); 
						}
					}
				}else{
					log.debug("Directory "+dir.getName()+" not exist! ");
				}
				
				dir.delete(); 
			}
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}			
	}
	private static String foldAndSave(String sequence, FoldingAlgorithm fc) throws RNAFoldingException{
		log.debug("Sequence :"+sequence); 
		return fc.execute(sequence); 
	}
	
	private static FoldingAlgorithm init(String faClass, String outputDir, String clazz) throws RNAFoldingException{
		
		log.debug("Folding Algorithm: "+faClass.toString()); 
		FoldingAlgorithm fa;
		try {
			fa = (FoldingAlgorithm) Class.forName(faClass).newInstance();
		} catch (InstantiationException e) {
			log.error("Unable to istantiate "+faClass+" class");
			throw new RNAFoldingException(e.getMessage()); 
		} catch (IllegalAccessException e) {
			log.error("Unable to access "+faClass+" class");
			throw new RNAFoldingException(e.getMessage()); 		
		} catch (ClassNotFoundException e) {
			log.error("Class "+faClass+" not found");
			throw new RNAFoldingException(e.getMessage()); 
		}
		return new FoldingCache(fa,outputDir, clazz);
	} 
}
