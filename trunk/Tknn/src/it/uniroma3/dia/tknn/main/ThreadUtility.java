package it.uniroma3.dia.tknn.main;

import it.uniroma3.dia.tknn.model.MyThread;

import java.util.List;

import org.apache.log4j.Logger;

public class ThreadUtility {
	private static Logger log = Logger.getLogger(ThreadUtility.class); 
	
	public static void wait(List<MyThread> threadList){
		boolean wait = true;
		while(wait) {
		    try {
				Thread.sleep(10000);
				int totalWorkEnded =0; 
			    for(int i = 0; i < threadList.size(); i++) {
			    	totalWorkEnded = totalWorkEnded + threadList.get(i).getEnd(); 
			    }
			    log.info("Total Work Ended (KernelManager)"+totalWorkEnded); 
			    log.info("Thread list Size (KernelManager)" + threadList.size()); 
				
		    	if(totalWorkEnded == threadList.size()) {
		        	wait = false;
		        	log.info("---- Classification Complete! -----"); 
		        }   
		    	else{
		    		log.info("KernelManager Thread Still Working "+ (threadList.size()-totalWorkEnded)); 
		    		totalWorkEnded = 0; 
		    		log.info("---- ThreadManager still Running! -----"); 
		    	}
			} catch (InterruptedException e) {
		
				log.error("Exception "); 
			}

		}
	}
}
