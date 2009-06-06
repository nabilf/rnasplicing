package it.uniroma3.rnafolding.cache;

import it.uniroma3.rnafolding.algorithm.ExtendedDB;
import it.uniroma3.rnafolding.exception.RNAFoldingException;
import it.uniroma3.rnafolding.interfaces.FoldingAlgorithm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

public class FoldingCache implements FoldingAlgorithm{

	private static Logger log = Logger.getLogger(FoldingCache.class); 
	
	private File cacheDir; 
	private FoldingAlgorithm decorated; 
	
	private String clazz; 
	
	public FoldingCache(FoldingAlgorithm decorated, String cacheDirPath, String clazz){
		log.debug("Setup Folding Cache to dir "+cacheDirPath); 
		this.clazz = clazz; 
		//System.getProperty("user.home")+
		this.cacheDir = new File(cacheDirPath); 
		if(!this.cacheDir.exists()) {
			this.cacheDir = new File(cacheDirPath);
			//"/Documents/Java/Workspace/RNAFolding/cache/"
			log.info("CACHE DIR "+cacheDirPath+" NOT FOUND!!! Creating "+this.cacheDir);
			if(!this.cacheDir.exists()) {
				this.cacheDir.mkdirs();
			}
		}
		this.decorated = decorated;		
	}
	
	public String execute(String sequence) throws RNAFoldingException {
		log.debug("Verifying cache for "+sequence); 
		if(!this.isInCache(sequence)) {
			log.debug("Cache non exists: start folding"); 
			try {
				this.runAndSave(sequence);
			} catch (IOException e) {
				throw new RNAFoldingException("Unable to save cache "+ e.getMessage());
			}
			log.debug("Run And Save folding "); 
		}
		log.debug("Using cache for sequence: "+sequence);
		return this.loadCached(sequence);
	}
	private void runAndSave(String sequence) throws IOException, RNAFoldingException {
		String structure= this.decorated.execute(sequence);
		String dbExtended = ExtendedDB.execute(structure); 
		log.debug("Save Result in Cache"); 
		File fileToSave = this.codeSequence(sequence);
		FileOutputStream fos = new FileOutputStream(fileToSave);
		String toWrite = (fileToSave.getName()+";"+this.clazz+";"+sequence+";"+structure+";"+dbExtended+";").toUpperCase(); 
		
		fos.write(toWrite.getBytes()); 
		fos.close();
	}

	private String loadCached(String sequence) {
		log.debug("Loading cache for "+sequence); 
		File cached = this.codeSequence(sequence);
		String line = ""; 
		
		InputStream is;
		try {
			is = new FileInputStream(cached);
		    BufferedReader br=new BufferedReader(new InputStreamReader(is));
		    int i = 0; 
		    
		    do{
		    	i=br.read();
		        line +=((char)i);
		    }while (i!=-1);
		    
		    is.close();
			
		} catch (FileNotFoundException e) {
			log.error("File not found "+cached); 
			log.error(e); 
		} catch (IOException e) {
			log.error(e); 
		}
		log.debug("Cache loaded! "); 
		return line;
	}

	private boolean isInCache(String sequence) {
		File cached = this.codeSequence(sequence);
		return cached.exists();
	}

	private File codeSequence(String sequence) {
		String nameMd5;
		try {
			nameMd5 = MD5.getInstance().hashData(sequence);
			File cached = new File(this.cacheDir+File.separator+nameMd5+".rna"); 
			return cached;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException();
	}

}
