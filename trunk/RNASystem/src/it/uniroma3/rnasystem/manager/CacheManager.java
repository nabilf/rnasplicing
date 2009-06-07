package it.uniroma3.rnasystem.manager;

import it.uniroma3.rnasystem.exception.CacheException;
import it.uniroma3.rnasystem.model.RNAClass;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.utils.SwitchEnum;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

public class CacheManager {
	
	private static Logger log = Logger.getLogger(CacheManager.class); 
	
	public static Map<RNAClass,List<RNASequence>> loadMapCache(String path) throws CacheException{
		Map<RNAClass, List<RNASequence>> map = null; 
		log.debug("Loading cache from "+path); 
		File f = new File(path); 
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			InputStreamReader isr=new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(isr);
			String linea="";
			
			map = new HashMap<RNAClass, List<RNASequence>>(); 
			map.put(RNAClass.EI,new LinkedList<RNASequence>());
			map.put(RNAClass.IE,new LinkedList<RNASequence>());
			map.put(RNAClass.N,new LinkedList<RNASequence>());
			
			int i=0; 
			
			while(linea!=null) {
			       linea=br.readLine();
			       if(linea!=null){
			    	  //log.debug("LINE "+linea); 
			    	   RNASequence rna = new RNASequence(); 
			    	   String[] value = linea.split(";"); 
			    	   rna.setMd5ID(value[0]);
			    	   RNAClass realClass = SwitchEnum.getRNAClass(value[1]); 
			    	   rna.setRealClass(realClass); 
			    	   rna.setSequence(value[2]); 
			    	   rna.setStructure(value[3]); 
			    	   rna.setExtendedStructure(value[4]);
			    	   log.info("RNA "+rna.toString()); 
			    	   map.get(realClass).add(rna); 
			    	   i++; 
			       	}
			       
			}
			log.debug("TOTAL rna sequence "+i ); 
		} catch (FileNotFoundException e) {
			log.error("File not Found! "+path);
			throw new CacheException(e.getMessage()); 
		} catch (IOException e) {
			log.error("Cannot read File "+path); 
			throw new CacheException(e.getMessage());
		}
		return map; 
	}
	
	public static List<RNASequence> loadListCache(String path)throws CacheException{
		List<RNASequence> list = null; 
		log.debug("Reading cache "+path);
		File f = new File(path); 
		
		FileInputStream fis;
		try {
			fis = new FileInputStream(f);
			InputStreamReader isr=new InputStreamReader(fis);
			BufferedReader br=new BufferedReader(isr);
			String linea=""; 
			
			list = new LinkedList<RNASequence>(); 
			
			while(linea!=null) {
			       linea=br.readLine();
			       if(linea!=null){
			    	   //log.debug("LINE "+linea); 
			    	   RNASequence rna = new RNASequence(); 
			    	   String[] value = linea.split(";"); 
			    	   rna.setMd5ID(value[0]); 
			    	   rna.setRealClass(SwitchEnum.getRNAClass(value[1])); 
			    	   rna.setSequence(value[2]); 
			    	   rna.setStructure(value[3]); 
			    	   rna.setExtendedStructure(value[4]);
			    	   log.debug(rna.toString()); 
			    	   list.add(rna);
			       }
			}
		} catch (FileNotFoundException e) {
			log.error("File not Found! "+path); 
		} catch (IOException e) {
			log.error("Cannot read File "+path); 
		}

		return list; 
	}

	
	public static void writeGraphCache(List<RNASequence> rnas, String path, String clazz){
		File f = new File(path); 
		if(!f.exists()) {
			log.debug("File non exists"); 
			f.mkdirs();
		}
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(
					new FileOutputStream(path+clazz)); 
			int i = 0; 
			for (RNASequence rna : rnas) {
				oos.writeObject(rna); 
				oos.write("\n".getBytes()); 
				i++; 
				oos.flush(); 
				
			}
			log.debug("Object writed for class "+clazz+" : "+i); 
			oos.close();
			
		} catch (FileNotFoundException e) {
			log.error("Unable to find cache file "+path, e);
		} catch (IOException e) {
			log.error("Unable to read cache file "+path, e); 
		}
	}
	public static Map<RNAClass, List<RNASequence>> readCache(String path, Map<RNAClass, List<RNASequence>> dataset) throws CacheException{
		log.debug("Loading cache for "+path); 
		
		try {
			
			Set<RNAClass> keySet = dataset.keySet(); 
			Iterator<RNAClass> it = keySet.iterator(); 
			
			while(it.hasNext()){
				RNAClass key = it.next();
				log.debug("Cache Path: "+path+key.toString()+".txt"); 
				
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path+key.toString()+".txt"));
				List<RNASequence> rnas = new LinkedList<RNASequence>(); 
				int i =0; 
				while(i< dataset.get(key).size()){
					log.debug("Object  "+i); 
					RNASequence rna =(RNASequence)ois.readObject(); 
					rnas.add(rna);
					i++; 
				}
				log.debug("Read "+rnas.size()+" graph");
				dataset.put(key, rnas); 
				
				ois.close(); 
			}
			
		} catch (FileNotFoundException e) {
			log.error("Unable to find cache file "+path, e);
			throw new CacheException(e.getMessage()); 
		} catch (IOException e) {
			log.error("Unable to read cache file "+path, e);  
		} catch (ClassNotFoundException e) {
			log.error("Class RNASequence not found", e); 
		}
		return dataset; 
	}
	
}
