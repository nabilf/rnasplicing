package it.uniroma3.rnakernels.main;

import it.uniroma3.rnakernels.exception.RNAKernelException;
import it.uniroma3.rnakernels.kernel.IKernel;
import it.uniroma3.rnasystem.model.RNASequence;
import it.uniroma3.rnasystem.setup.RNASystemConfiguration;

import org.apache.log4j.Logger;

public class RNAKernel{

	private static Logger log = Logger.getLogger(RNAKernel.class);
	private static IKernel kernel; 
	
	public RNAKernel(){
		try {
		RNAKernel.kernel= (IKernel) Class.forName(RNASystemConfiguration.kernelClass).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void computeDistance(RNASequence rna1, RNASequence rna2) {
		//log.debug("*** Kernel Manager ***"); 
		try {			
			rna2.setDistance(RNAKernel.kernel.execute(rna1,rna2, RNASystemConfiguration.NORMALIZE)); 
		} catch (RNAKernelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
